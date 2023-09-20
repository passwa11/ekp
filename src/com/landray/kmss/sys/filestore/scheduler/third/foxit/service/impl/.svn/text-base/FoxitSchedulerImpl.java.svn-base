package com.landray.kmss.sys.filestore.scheduler.third.foxit.service.impl;

import com.landray.kmss.sys.filestore.circuitbreak.CircuitBreakRegister;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.FoxitConvertFile;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.service.FoxitScheduler;
import com.landray.kmss.sys.filestore.scheduler.third.foxit.util.FoxitUtil;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import  static com.landray.kmss.sys.filestore.constant.ConvertConstant.THIRD_CONVERTER_FOXIT;
import static com.landray.kmss.sys.filestore.scheduler.third.foxit.constant.FoxitConstant.FOXIT_SERVER_NAME;

/**
 * 继承公共的调度任务接口
 */
public class FoxitSchedulerImpl extends AbstractQueueScheduler implements FoxitScheduler {
    /**
     * data 服务
     */
    public void setDataService(ISysFileConvertDataService dataService) {
        this.dataService = dataService;
    }

    private static volatile FoxitConvertFile foxitConvertFile = null;
    private FoxitConvertFile getFoxitConvertFile() {
        if(foxitConvertFile == null) {
            foxitConvertFile = (FoxitConvertFile) SpringBeanUtil.getBean("foxitConvertFileImpl");
        }

        return foxitConvertFile;
    }


    @Override
    protected String getThreadName() {
        return "Foxit Convert";
    }

    @Override
    protected void doDistributeConvertQueue(String encryptionMode) {

        //未开启转换，直接返回
        if(!FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_FOXIT,false)) {
            return;
        }


        // 是否使用熔断
        if (StringUtil.isNotNull(FoxitUtil.configValue("enabledCircuitBreak"))
                && "true".equals(FoxitUtil.configValue("enabledCircuitBreak"))) {
            // 使用熔断机制
            CircuitBreakRegister.register(FOXIT_SERVER_NAME);
        } else {
            // 不使用熔断机制
            CircuitBreakRegister.remove(FOXIT_SERVER_NAME);
        }

        getFoxitConvertFile().doDistributeConvertQueue(FOXIT_SERVER_NAME);
    }
}
