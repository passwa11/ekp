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


/**
 * 转换成功处理
 */
public class  WPSCenterSuccessResultExecutor implements WPSCenterResultExecutor {
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
        // 清除缓存
        ConvertCallbackCache.getInstance().remove(taskId);
        ConvertCallbackCache.getInstance().remove(deliveryTaskQueue.getFdId());
        //确认已经转换好
        String desc =  "转换服务转换成功【WPS中台】:" + getISysAttachmentWpsCenterOfficeProvider().getWpsUrl2Converter();
        ConvertQueueState convertQueueState = getConvertedStateFactory().getConvertedState(ConvertState.CONVERT_OTHER_FINISH);
        convertQueueState.updateConvertQueue(null, deliveryTaskQueue, new ConvertQueueInfo("", desc));
        return true;
    }
}

