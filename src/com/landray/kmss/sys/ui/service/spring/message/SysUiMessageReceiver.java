package com.landray.kmss.sys.ui.service.spring.message;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.ui.compressor.PcCompressService;

/**
 * PC端静态资源压缩切换集群消息接收器
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-23
 */
public class SysUiMessageReceiver implements IMessageReceiver {

    protected IMessageQueue messageQueue = new UniqueMessageQueue();

    @Override
    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void receiveMessage(IMessage message) throws Exception {
        if(!(message instanceof SysUiMessage)){
            return;
        }
        SysUiMessage sysUiMessage = (SysUiMessage) message;
        //执行切换线程
        PcCompressService.channelCompressRecource(sysUiMessage.isUseCompress());
    }
}
