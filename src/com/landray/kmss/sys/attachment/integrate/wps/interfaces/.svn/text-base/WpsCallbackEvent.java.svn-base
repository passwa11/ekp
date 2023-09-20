package com.landray.kmss.sys.attachment.integrate.wps.interfaces;

import org.springframework.context.ApplicationEvent;

/**
 * Wps回调事件，事件源对象为{@link ICallbackParameter}
 * 业务模块在监听到这个事件的时候，应该根据ICallbackParameter.getFdModelName来判断是否属于自己处理的范畴
 * @author 陈进科
 * 2020年4月17日
 */
public class WpsCallbackEvent extends ApplicationEvent {

    private static final long serialVersionUID = 98326389576952354L;

    public WpsCallbackEvent(Object source) {
        super(source);
    }

}
