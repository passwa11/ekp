package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.cache.ConvertCallbackCache;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.IWPSCenterCallbackResult;
import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.WPSCenterResultExecutor;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

import static com.landray.kmss.sys.filestore.scheduler.third.wps.center.constant.WPSCenterConstant.*;

/**
 * 处理回调情况
 */
public class WPSCenterCallbackResultImp implements IWPSCenterCallbackResult {
    private static final Logger logger = LoggerFactory.getLogger(WPSCenterCallbackResultImp.class);
    private static int CONVERT_EXPIRE_TIME = 30 * 60 * 1000;

    private static WpsCenterResultExecutorFactory wpsCenterResultExecutorFactory = null;

    public WpsCenterResultExecutorFactory getWPSCenterResultExecutorFactory() {
        if (wpsCenterResultExecutorFactory == null) {
            wpsCenterResultExecutorFactory = (WpsCenterResultExecutorFactory) SpringBeanUtil.getBean("wpsCenterResultExecutorFactory");
        }
        return wpsCenterResultExecutorFactory;
    }
    @Override
    public void doCallbackResult(String taskId, String status) {
        try {
            String object = ConvertCallbackCache.getInstance().get(taskId);
            if(StringUtil.isNull(object)) {
                return;
            }
            SysFileConvertQueue sysFileConvertQueue = JSONObject.parseObject(object, SysFileConvertQueue.class);
            String result = WPS_CENTER_FAILURE;
            if("success".equalsIgnoreCase(status)) {
                result = WPS_CENTER_SUCCESS_CALLBACK;
            }

            if(logger.isDebugEnabled()) {
                logger.debug("WPS中台转换获取到回调的结果是:{}", result);
            }

            WPSCenterResultExecutor wpsCenterResultExecutor = getWPSCenterResultExecutorFactory()
                    .getResultExecutor(result);
            wpsCenterResultExecutor.doResult(null, sysFileConvertQueue, taskId);
        } catch (Exception e) {
            logger.error("error:", e);
        }


    }
    /**
     * 如果转换已经分配，但是长时间没有回调，则将转换标记为失败
     * 而失败中，如果转换次数少于3次，则会重新分配转换
     */
    @Override
    public void cleanCallbackCache(SysQuartzJobContext context) {
        TransactionStatus status = null;
        Throwable throwable = null;

        try {
            List<String> cacheKeys = ConvertCallbackCache.getInstance().getKeys();
            if(cacheKeys.isEmpty()) {
                return;
            }
            
            status = TransactionUtils.beginNewTransaction();
            if(logger.isDebugEnabled()) {
                logger.debug("清空无回调缓存数据：{}", cacheKeys.size());
            }

            for (String key : cacheKeys) {
                String object = ConvertCallbackCache.getInstance().get(key);
                SysFileConvertQueue sysFileConvertQueue = null;
                try {
                    //#172654 设了taskid的缓存后，无法反序列化为sysFileConvertQueue，导致失败队列的缓存无法清理。
                    sysFileConvertQueue = JSONObject.parseObject(object, SysFileConvertQueue.class);
                }catch (Exception e){

                }
                if(sysFileConvertQueue != null) {
                    Long expireTime = sysFileConvertQueue.getExpireTime();
                    if (expireTime == null) {
                        ConvertCallbackCache.getInstance().remove(key);
                        ConvertCallbackCache.getInstance().remove(sysFileConvertQueue.getFdId());
                        continue;
                    }
                    Long currentTime = System.currentTimeMillis();
                    int lagTime = CONVERT_EXPIRE_TIME;

                    // 超出时间没有回调，则转换为失败队列
                    if ((currentTime - expireTime) > lagTime) {
                        WPSCenterResultExecutor wpsCenterResultExecutor = getWPSCenterResultExecutorFactory()
                                .getResultExecutor(WPS_CENTER_FAILURE);
                        wpsCenterResultExecutor.doResult(null, sysFileConvertQueue, key);
                    }
                }
            }

            TransactionUtils.commit(status);
        } catch (Exception e) {
            throwable = e;
            logger.error("error:", e);
        } finally {
            if (throwable != null && status != null) {
                TransactionUtils.rollback(status);
            }
        }
    }
}
