package com.landray.kmss.sys.filestore.scheduler.third.wps.center.api;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.integrate.wps.interfaces.ISysAttachmentWpsCenterOfficeProvider;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertRedisCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.domain.ConvertGoing;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.util.WPSCenterFileUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.landray.kmss.sys.filestore.scheduler.third.wps.center.constant.WPSCenterConstant.WPS_CENTER_SUCCESS;

/**
 * 请求第三方接口实现
 */
public class WPSCenterApiImpl implements  WPSCenterApi{
    private static final Logger logger = LoggerFactory.getLogger(WPSCenterApiImpl.class);
    private static final String WPS_CENTER_CONVERT_SUCCESS = "1"; // 转换成功
    private static final String WPS_CENTER_CONVERT_FAILURE = "0"; // 转换失败
    private static final Integer WPS_CENTER_QUEUE_CONVERT_STATE = 4; // 转换状态是成功
    private static ISysAttachmentWpsCenterOfficeProvider
            attachmentWpsCenterOfficeProvider= null;
    public  ISysAttachmentWpsCenterOfficeProvider getISysAttachmentWpsCenterOfficeProvider() {
        if(attachmentWpsCenterOfficeProvider == null) {
            attachmentWpsCenterOfficeProvider = (ISysAttachmentWpsCenterOfficeProvider) SpringBeanUtil
                    .getBean("wpsCenterProvider");
        }

        return attachmentWpsCenterOfficeProvider;
    }

    private static ISysFileConvertQueueService sysFileConvertQueueService = null;
    private ISysFileConvertQueueService getSysFileConvertQueueService() {
        if(sysFileConvertQueueService == null) {
            sysFileConvertQueueService =  (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
        }

        return sysFileConvertQueueService;
    }

    private static ISysAttMainCoreInnerService sysAttMainCoreInnerService = null;
    private ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
        if(sysAttMainCoreInnerService == null) {
            sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        }

        return sysAttMainCoreInnerService;
    }

    private ISysAttUploadService sysAttUploadService = null;
    private ISysAttUploadService getAttUploadService() {
        if (sysAttUploadService == null) {
            sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        }
        return sysAttUploadService;
    }
    /**
     * 文件转换
     * @param taskId
     * @param filePath
     * @param deliveryTaskQueue
     * @return
     * @throws Exception
     */
    @Override
    public String doConvertFile(String taskId, String filePath, SysFileConvertQueue deliveryTaskQueue)
            throws Exception{
        //转换文件
        String result = getISysAttachmentWpsCenterOfficeProvider().wpsCenterConvertFile(taskId, filePath,
                deliveryTaskQueue.getFdAttMainId(), deliveryTaskQueue.getFdFileName(),
                deliveryTaskQueue.getFdConverterKey());

        if (logger.isDebugEnabled()) {
            logger.debug("***Wps中台**转换结果{}",result);
        }
        return result;
    }

    /**
     * 同步转换
     * @param sysAttMain
     * @param json  需要包含：convertFileName、converterKey、queryNum（轮询次数--可选）
     * @return
     * @throws Exception
     */
    @Override
    public Boolean syncConvertFile(SysAttMain sysAttMain, String json) throws Exception {
        try {
            if(logger.isDebugEnabled()) {
                logger.debug("接收到业务模块传递和参数:{}", json);
            }
            JSONObject jsonObject = JSONObject.parseObject(json);
            String converterKey = jsonObject.getString("converterKey");
            String convertFileName = jsonObject.getString("convertFileName");
            String queryNum = jsonObject.getString("queryNum"); // 轮询次数
            SysAttFile sysAttFile = getSysAttMainCoreInnerService().getFile(sysAttMain.getFdId());

            // 转换好的文件存放位置
            String downloadPath = sysAttFile.getFdFilePath() + "_convert" + "/" + convertFileName;
            // EKP下载地址
            String downloadUrl = WPSCenterFileUtil.getFilePath(sysAttMain.getFdFileName(), sysAttMain.getFdId());
            JSONObject object = new JSONObject();
            object.put("taskId", UUID.randomUUID().toString()); // 任务ID
            object.put("fileDownloadUrl", downloadUrl);    // 文件下载地址，即EKP下载地址
            object.put("fdAttMainId", sysAttMain.getFdId());  // 附件ID
            object.put("fdFileName", sysAttMain.getFdFileName()); // 文件名
            object.put("convertKey", converterKey);  // 转换到什么格式
            object.put("downloadPath", downloadPath);  // 转换后文件下载到目录路径
            object.put("queryNum", queryNum);  // 同步转换轮询次数

            String strObject = JSONObject.toJSONString(object);

            if(logger.isDebugEnabled()) {
                logger.debug("发起同步请求转换的参数：{}", strObject);
            }

            return getISysAttachmentWpsCenterOfficeProvider()
                    .wpsCenterSyncConvertFile(strObject);

        } catch (Exception e) {
          logger.error("error:", e);
        }

        return false;
    }

    /**
     * 异步转换:
     * 1.如果队列中存在，且已经转换成功，则返回“1”并调用成功回调接口
     * 2.如果队列中存在，且除了成功状态的其它状态，则都重新发起转换请求
     * 3.不存在队列中，则重新发起转换请求
     * @param sysAttMain
     * @param json  需要包含：convertFileName、convertType、taskId、converterKey
     * @return   0：失败  1：成功  其它：在转换中
     * @throws Exception
     */
    @Override
    public String asyncConvertFile(SysAttMain sysAttMain, String json, WPSCenterCallBusiness callBusiness)
            throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug("接收到业务模块传递的参数：{}", json);
        }

        JSONObject jsonObject = JSONObject.parseObject(json);
        SysFileConvertQueue sysFileConvertQueue = getConvertQueue(sysAttMain, json);
        String convertFileName = jsonObject.getString("convertFileName");

         // 已经存在于队列中
        if(sysFileConvertQueue != null) {
            Boolean converted = isConverted(sysAttMain, sysFileConvertQueue, convertFileName);
            if(converted) { // 已转换
                if(callBusiness != null) {
                    callBusiness.successCallback(jsonObject.getString("taskId"));
                }

                return WPS_CENTER_CONVERT_SUCCESS;
            }

        }

        // 不存在于队列中
        return doConvert(sysAttMain, json, callBusiness);

    }

    /**
     * 转换处理
     *
     * @param sysAttMain
     * @param json
     * @return
     * @throws Exception
     */
    public String doConvert(SysAttMain sysAttMain, String json, WPSCenterCallBusiness callBusiness) throws Exception {
        // 不存在队列，则创建队列并添加到数据库
        SysFileConvertQueue deliveryTaskQueue = createQueue(sysAttMain, json);
        String taskId = deliveryTaskQueue.getFdId(); //任务ID,实际是业务模块传递的taskId

        //下载地址
        String filePath = WPSCenterFileUtil.getFilePath(deliveryTaskQueue.getFdFileName(),
                deliveryTaskQueue.getFdAttMainId());
        // 添加缓存
        addCache(deliveryTaskQueue, taskId, filePath);
        //转换文件
        String result = doConvertFile(taskId, filePath,deliveryTaskQueue);

        if(logger.isDebugEnabled()) {
            logger.debug("【业务模块操作】转换服务转换的结果是：{}" ,result);
        }

        if (!WPS_CENTER_SUCCESS.equalsIgnoreCase(result)) {
            logger.error("【业务模块操作】转换失败,回调业务模块的失败接口.");
            if(callBusiness != null) {
                callBusiness.failureCallback(deliveryTaskQueue.getFdId());
            }

            return WPS_CENTER_CONVERT_FAILURE;
        }

        return deliveryTaskQueue.getFdId();
    }

    /**
     * 添加缓存
     * @param deliveryTaskQueue
     * @param taskId
     * @param filePath
     */
    public void addCache(SysFileConvertQueue deliveryTaskQueue, String taskId, String filePath) {

        // 信息写入缓存给回调使用
        ConvertGoing convertGoing = new ConvertGoing(taskId,
                deliveryTaskQueue.getFdFileId(),
                deliveryTaskQueue.getFdAttMainId(),
                deliveryTaskQueue.getFdConverterKey());

        String jsonConvertGoing = JSONObject.toJSONString(convertGoing);
        if(logger.isDebugEnabled()) {
            logger.debug("【业务模块操作】WPS中台发起转换前,保存到redis的对象信息：" + jsonConvertGoing);
        }

        ConvertRedisCache.getInstance().put(taskId, jsonConvertGoing);
        if(logger.isDebugEnabled()) {
            logger.debug("【业务模块操作】转换服务的信息fileId:{}, attMainId:{},convertKey:{},taskId:{}, filePath:{}",
                    deliveryTaskQueue.getFdFileId(),
                    deliveryTaskQueue.getFdAttMainId(),
                    deliveryTaskQueue.getFdConverterKey(),
                    taskId, filePath);
        }

        String object = JSONObject.toJSONString(deliveryTaskQueue);
        // 添加缓存中
        ConvertCallbackCache.getInstance().put(taskId, object);
    }

    /**
     * 创建队列
     * @param sysAttMain
     * @param json
     * @return
     */
    public SysFileConvertQueue createQueue(SysAttMain sysAttMain, String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String fileName = sysAttMain.getFdFileName();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);

        SysFileConvertQueue deliveryTaskQueue = new SysFileConvertQueue();
        deliveryTaskQueue.setFdId(jsonObject.getString("taskId"));
        deliveryTaskQueue.setFdConverterKey(jsonObject.getString("converterKey"));
        deliveryTaskQueue.setFdConverterType(jsonObject.getString("convertType"));
        deliveryTaskQueue.setFdFileName(fileName);
        deliveryTaskQueue.setFdFileExtName(extName);
        deliveryTaskQueue.setFdAttMainId(sysAttMain.getFdId());
        deliveryTaskQueue.setFdFileId(sysAttMain.getFdFileId());

        return deliveryTaskQueue;
    }

    /**
     * 是否已经存在于队列中
     * @param sysAttMain
     * @param json
     * @return
     * @throws Exception
     */
    public SysFileConvertQueue getConvertQueue(SysAttMain sysAttMain, String json) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String convertType = jsonObject.getString("convertType");
        String converterKey = jsonObject.getString("converterKey");

        HQLInfo hql = new HQLInfo();
        String strWhere = "(sysFileConvertQueue.fdFileId=:fdFileId or sysFileConvertQueue.fdAttMainId=:fdAttMainId) " +
                " and sysFileConvertQueue.fdConverterKey=:fdConverterKey";
        if(StringUtil.isNotNull(convertType)) {
            strWhere += " and sysFileConvertQueue.fdConverterType=:convertType ";
            hql.setParameter("convertType", convertType);
        }
        hql.setWhereBlock(strWhere);
        hql.setParameter("fdFileId", sysAttMain.getFdFileId());
        hql.setParameter("fdAttMainId", sysAttMain.getFdId());
        hql.setParameter("fdConverterKey", converterKey);
        SysFileConvertQueue convertQueue = (SysFileConvertQueue) getSysFileConvertQueueService().findFirstOne(hql);

        return convertQueue;
    }

    /**
     * 是否已经转换
     * @param sysAttMain
     * @param sysFileConvertQueue
     * @param convertFileName
     * @return
     * @throws Exception
     */
    public Boolean isConverted(SysAttMain sysAttMain, SysFileConvertQueue sysFileConvertQueue, String convertFileName)
            throws Exception {
        int convertStatus = sysFileConvertQueue.getFdConvertStatus();
        boolean converted = false;

        if(convertStatus == WPS_CENTER_QUEUE_CONVERT_STATE) {
            SysAttFile sysAttFile = getSysAttMainCoreInnerService().getFile(sysAttMain.getFdId());

            if (sysAttFile != null) {
                String FilePath = getAttUploadService().getAbsouluteFilePath(sysAttFile.getFdId());
                String bakf = FilePath + "_bak";
                SysAttFile file = getAttUploadService().getFileById(sysAttFile.getFdId());
                String pathPrefix = file.getFdCata() == null ? null : file.getFdCata().getFdPath();
                converted = SysFileLocationUtil.getProxyService(file.getFdAttLocation()).doesFileExist(bakf,pathPrefix);
            }
        }

        return converted;
    }

}
