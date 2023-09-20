package com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.imp;

import com.landray.kmss.sys.filestore.constant.ConvertState;
import com.landray.kmss.sys.filestore.model.SysFileConvertConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.util.ConvertQueueCallbackExtensionUtil;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.WpsUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.domain.WPSCloudConvertHandlerResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.service.WPSCloudConvertHandler;
import com.landray.kmss.sys.filestore.scheduler.third.wps.cloud.util.WPSCloudFileUtil;
import com.landray.kmss.sys.filestore.scheduler.third.wps.interfaces.IWpsConvertScheduler;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.state.ConvertQueueInfo;
import com.landray.kmss.sys.filestore.state.ConvertQueueState;
import com.landray.kmss.sys.filestore.state.ConvertedStateFactory;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;
import java.util.List;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.*;

/**
 * WPS云转OFD PDF功能
 */
public class WPSConvertSchedulerImp extends AbstractQueueScheduler
        implements IWpsConvertScheduler {

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(WPSConvertSchedulerImp.class);

    /**
     * 组件
     */
   private static WPSCloudConvertHandlerFactory wpsCloudConvertHandlerFactory = null;
   private WPSCloudConvertHandlerFactory getWpsCloudConvertHandlerFactory() {
       if (wpsCloudConvertHandlerFactory == null) {
           wpsCloudConvertHandlerFactory = (WPSCloudConvertHandlerFactory) SpringBeanUtil.getBean("wpsCloudConvertHandlerFactory");
       }

       return wpsCloudConvertHandlerFactory;
   }


    /**
     * 记录状态组件
     */
    private static ConvertedStateFactory convertedStateFactory = null;
    private ConvertedStateFactory getConvertedStateFactory() {
        if (convertedStateFactory == null) {
            convertedStateFactory = (ConvertedStateFactory) SpringBeanUtil.getBean("convertedStateFactory");
        }

        return convertedStateFactory;
    }


    @Override
    protected String getThreadName() {
        return "wpsConvertScheduler";
    }

    public void setDataService(ISysFileConvertDataService dataService) {
        this.dataService = dataService;
    }

    @Override
    protected void doDistributeConvertQueue(String encryptionMode) {
        //【集成管理】->【其它应用集成】->【WPS集成】中的【在线预览配置】中需要开启【启用在线预览服务】
        // 没有开启直接返回
        //开启WPS云,但不是linux,直接返回
    	// 转换队列没有勾选WPS在线转换
        if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS,false)) {
            return;
        }

        TransactionStatus status = null;
        Throwable throwable = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            List<SysFileConvertQueue> tasks = dataService.getUnassignedTasksByKeysAndType(THIRD_CONVERTER_WPS,
                    new String[]{CONVERT_TO_OFD, CONVERT_TO_PDF}, null);

            for (SysFileConvertQueue deliveryTaskQueue : tasks) {
                    //没有文件名，只有后缀的视为无效文件
                    if (rejectConvert(deliveryTaskQueue)) {
                        continue;
                    }

                    String filePath = WPSCloudFileUtil.getDownloadUrl(deliveryTaskQueue);
                    deliveryTaskQueue.setFdFileDownUrl(filePath);

                    confirmTransaction( "begin", false, deliveryTaskQueue);
                    WPSCloudConvertHandler wpsCloudConvertHandler =
                            getWpsCloudConvertHandlerFactory().getHandlerChain();
                    Boolean result =  wpsCloudConvertHandler.execute(deliveryTaskQueue,
                            new WPSCloudConvertHandlerResult(true, true, "", ""));
                    if(result) {
                        SysFileConvertQueue sysFileConvertQueue = deliveryTaskQueue;
                        sysFileConvertQueue.setFdConvertStatus(SysFileConvertConstant.SUCCESS);
                        ConvertQueueCallbackExtensionUtil.execute(sysFileConvertQueue);
                    }
                    confirmTransaction("end", result,  deliveryTaskQueue);
                }

            TransactionUtils.commit(status);

        } catch (Exception e) {
            throwable = e;
            logger.error("文件存储加密线程执行出错", e);
        }finally {
            if (throwable != null && status != null) {
                TransactionUtils.rollback(status);
            }
        }
    }


    /**
     * 拒绝转换
     * @param deliveryTaskQueue
     * @return
     * @throws Exception
     */
   public Boolean rejectConvert(SysFileConvertQueue deliveryTaskQueue) throws Exception{
       String queueFileName = deliveryTaskQueue.getFdFileName();
       //没有文件名，只有后缀的视为无效文件
       if (".doc".equalsIgnoreCase(queueFileName) || ".docx".equalsIgnoreCase(queueFileName)) {
           dataService.setRemoteConvertQueue(null, "taskInvalid",
                   deliveryTaskQueue.getFdId(), "",
                   "文件名：" + queueFileName + ",转换失败，因为文件名为【.doc】或【.docx】:"
                           + WpsUtil.configInfo("thirdWpsSetRedUrl"));
           return true;
       }

       return false;
   }

    /**
     * 事务处理
     * @param success 转换成功与否
     * @param convertQueue
     * @param status begin:开始进入转换  end 结束转换
     */

    public void confirmTransaction(String status, boolean success, SysFileConvertQueue convertQueue) {
        try {
            if("begin".equals(status)) {
                String desc = "分配给转换服务【WPS】:" + WpsUtil.configInfo("thirdWpsSetRedUrl");
                ConvertQueueState convertQueueState = getConvertedStateFactory()
                        .getConvertedState(ConvertState.CONVERT_STATE_TASK_ASSIGNED);
                convertQueueState.updateConvertQueue(null, convertQueue, new ConvertQueueInfo("", desc));

            } else {
                if(success) {
                    String desc = "转换服务转换成功【WPS】:" + WpsUtil.configInfo("thirdWpsSetRedUrl");
                    ConvertQueueState convertQueueState = getConvertedStateFactory()
                            .getConvertedState(ConvertState.CONVERT_OTHER_FINISH);
                    convertQueueState.updateConvertQueue(null, convertQueue, new ConvertQueueInfo("", desc));

                } else {
                    String desc = "分配任务的时候发送消息到转换服务不成功，请检查转换服务【WPS】:"
                            + WpsUtil.configInfo("thirdWpsSetRedUrl");
                    Integer convertNumber = convertQueue.getFdConvertNumber();
                    if(convertNumber >= 2) {
                        ConvertQueueState convertQueueState = getConvertedStateFactory()
                                .getConvertedState(ConvertState.CONVERT_OTHER_FINISH_FAILURE);
                        convertQueueState.updateConvertQueue(null, convertQueue,
                                new ConvertQueueInfo("", "转换失败,如需请重新分发"));
                    } else {
                        ConvertQueueState convertQueueState = getConvertedStateFactory()
                                .getConvertedState(ConvertState.CONVERT_STATE_TASK_UNASSIGNED);
                        convertQueueState.updateConvertQueue(null, convertQueue, new ConvertQueueInfo("", desc));
                    }


                    if(logger.isDebugEnabled()) {
                        logger.debug("WPS转换:WPS转换失败.队列ID:{}", convertQueue.getFdId());
                    }

                }
            }

        } catch (Exception e) {
            logger.error("WPS转换更新表sys_File_Convert_Queue异常:{}", e);
        }

    }
}
