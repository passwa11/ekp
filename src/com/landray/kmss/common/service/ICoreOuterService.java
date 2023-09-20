package com.landray.kmss.common.service;


/**
 * 机制标记接口，实现或者继承该接口的类将<b>自动被机制分发器发现</b>并调用。
 * <br/>
 * 注意：该接口只是标记接口，一般不扩展IBaseCoreOuterService（即不声明新方法）
 * @author 叶中奇
 */
public interface ICoreOuterService extends IBaseCoreOuterService {

    /**
     * 机制转换时，日志操作对象在requestContext中的属性值
     */
    public static final String CORE_SERVICE_LOG_OPER_KEY = "____ICoreOuterService.LogOper.key____";
    
    /**
     * 机制转换时，日志操作类型在requestContext中的属性值
     */
    public static final String CORE_SERVICE_LOGTYPE_KEY = "____ICoreOuterService.LogType.key____";
}
