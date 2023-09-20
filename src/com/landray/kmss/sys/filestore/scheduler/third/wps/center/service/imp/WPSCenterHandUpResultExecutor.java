package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.imp;

import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.WPSCenterResultExecutor;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 请求转换成功后，先挂起等待回调
 */
public class WPSCenterHandUpResultExecutor implements WPSCenterResultExecutor {
    /**
     * 记录状态组件
     */
    private static ConvertedStateFactory convertedStateFactory = null;
    public ConvertedStateFactory getConvertedStateFactory() {
        if (convertedStateFactory == null) {
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil.getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }

    private static ISysAttachmentWpsCenterOfficeProvider
            attachmentWpsCenterOfficeProvider= null;
    public  ISysAttachmentWpsCenterOfficeProvider getISysAttachmentWpsCenterOfficeProvider() {
        if(attachmentWpsCenterOfficeProvider == null) {
            attachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
                    .getBean("wpsCenterProvider");
        }

        return attachmentWpsCenterOfficeProvider;
    }
    @Override
    public Boolean doResult(ISysFileConvertDataService dataService, SysFileConvertQueue deliveryTaskQueue, String taskId) throws Exception{
        if(StringUtil.isNull(ConvertCallbackCache.getInstance().get(taskId))) {
            return true;
        }
        String desc =  "成功请求服务转换,等待回调.【WPS中台】:" + getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
        ConvertQueueState convertQueueState = getConvertedStateFactory().getConvertedState(ConvertState.CONVERT_STATE_TASK_ASSIGNED);
        convertQueueState.updateConvertQueue(null, deliveryTaskQueue, new ConvertQueueInfo("", desc));
        deliveryTaskQueue.setExpireTime(System.currentTimeMillis());

        return true;
    }
}

