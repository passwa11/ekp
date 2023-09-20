package com.landray.kmss.sys.filestore.scheduler.third;

import com.landray.kmss.sys.filestore.queue.service.ConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.constant.ConstantParameter;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.service.IDianjuConvertFile;
import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.ConfigUtil;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.FoxitConvertFile;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 内存异常转换队列直接调用转换接口转换
 * 用户查询失败的队列后调用
 */
@Deprecated
public class ConvertServerMediator {
    /**
     *点聚服务
     */
    private IDianjuConvertFile dianjuNormalConvertFile = null;
    private IDianjuConvertFile getDianjuNormalConvertFileImpl() {
        if (dianjuNormalConvertFile == null) {
            dianjuNormalConvertFile = (IDianjuConvertFile) SpringBeanUtil.getBean("dianjuConvertNormalSchedulerImpl");
        }

        return dianjuNormalConvertFile;
    }

    private IDianjuConvertFile dianjuConvertFile = null;
    private IDianjuConvertFile getDianjuConvertFileImpl() {
        if (dianjuConvertFile == null) {
            dianjuConvertFile = (IDianjuConvertFile) SpringBeanUtil.getBean("dianjuConvertCircuitBreakImpl");
        }

        return dianjuConvertFile;
    }

    /**
     * 队列管理组件
     */
    private static ConvertQueue failConvertQueue = null;
    private static ConvertQueue getFailConvertQueue() {
        if(failConvertQueue == null) {
            failConvertQueue = (ConvertQueue) SpringBeanUtil.getBean("failConvertQueueImpl");
        }

        return failConvertQueue;
    }

    private static volatile FoxitConvertFile foxitConvertFile = null;
    public FoxitConvertFile getFoxitConvertFile() {
        if(foxitConvertFile == null) {
            foxitConvertFile = (FoxitConvertFile) SpringBeanUtil.getBean("foxitConvertFileImpl");
        }

        return foxitConvertFile;
    }

    public void doConvertQueue(String convertType) {
        if("dianju".equals(convertType)) {
            if (StringUtil.isNotNull(ConfigUtil.configValue("enabledCircuitBreak"))
                    && "true".equals(ConfigUtil.configValue("enabledCircuitBreak"))) {
                // 使用熔断机制
                getDianjuConvertFileImpl().doDistributeConvertQueue(ConstantParameter.CONVERT_DIANJU);
            } else {
                // 不使用熔断机制
                getDianjuNormalConvertFileImpl().doDistributeConvertQueue(ConstantParameter.CONVERT_DIANJU);
            }
        } else if ("foxit".equals(convertType)) {
            getFoxitConvertFile().doDistributeConvertQueue("foxit");
        }
    }
}
