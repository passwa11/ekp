package com.landray.kmss.sys.ui.service.spring.message;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

/**
 *  pc端切换静态压缩资源集群消息体
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-23
 */
public class SysUiMessage implements IMessage {
    /**
     * 是否使用压缩资源
     */
    private boolean isUseCompress = false;

    public SysUiMessage() {
    }

    public SysUiMessage(boolean isUseCompress) {
        this.isUseCompress = isUseCompress;
    }

    public boolean isUseCompress() {
        return isUseCompress;
    }

    public void setUseCompress(boolean useCompress) {
        isUseCompress = useCompress;
    }
}
