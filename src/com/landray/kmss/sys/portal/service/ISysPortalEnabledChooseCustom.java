package com.landray.kmss.sys.portal.service;

/**
 * 通过扩展点实现，根据模块业务逻辑判断组件是否可被选择
 * 扩展点id：com.landray.kmss.sys.portal.enabled.choose.custom
 */
public interface ISysPortalEnabledChooseCustom {
    // 业务模块逻辑判断，obj保留传值可能
    public Boolean enableChoose(Object obj);
}
