package com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.imp;

import com.landray.kmss.sys.filestore.scheduler.third.wps.center.service.WPSCenterResultExecutor;
import com.landray.kmss.util.SpringBeanUtil;

import static com.landray.kmss.sys.filestore.scheduler.third.wps.center.constant.WPSCenterConstant.*;

/**
 * 转换结果处理工厂
 */
public class WpsCenterResultExecutorFactory {

    /**
     * 选择转换结果处理策略
     * @param result
     * @return
     */
    public WPSCenterResultExecutor getResultExecutor(String result) {
        if (WPS_CENTER_SUCCESS.equalsIgnoreCase(result)) { // 只是请求转换成功
            return (WPSCenterResultExecutor) SpringBeanUtil.getBean("wpsCenterHandUpResultExecutor");
        } else if(WPS_CENTER_SUCCESS_CALLBACK.equalsIgnoreCase(result)) { // 回调回来的成功
            return (WPSCenterResultExecutor) SpringBeanUtil.getBean("wpsCenterSuccessResultExecutor");
        } else { // 失败
            return (WPSCenterResultExecutor) SpringBeanUtil.getBean("wpsCenterFailResultExecutor");
        }
    }

}
