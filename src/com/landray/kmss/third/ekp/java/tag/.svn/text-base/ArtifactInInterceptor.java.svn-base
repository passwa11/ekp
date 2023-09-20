package com.landray.kmss.third.ekp.java.tag;

import java.io.InputStream;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
 
public class ArtifactInInterceptor extends AbstractPhaseInterceptor<Message> {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ArtifactInInterceptor.class);
     
    public ArtifactInInterceptor() {
        //这儿使用receive，接收的意思
        super(Phase.RECEIVE);
    }
 
    @Override
    public void handleMessage(Message message){
         
        try {
            InputStream is = message.getContent(InputStream.class);
            //这里可以对流做处理，从流中读取数据，然后修改为自己想要的数据
             
            //处理完毕，写回到message中
            //在这里需要注意一点的是，如果修改后的soap消息格式不符合webservice框架格式，比如：框架封装后的格式为
            //<soap:Envelope xmlns:soap="http://www.w3.org/2001/12/soap-envelope" <soap:Body>
            //<这里是调用服务端方法的命名空间><这是参数名称> 
            //这里才是真正的消息
            //</这里是调用服务端方法的命名空间></这是参数名称>
            //</soap:Body>
            //</soap:Envelope>
            //如果是以上这种格式，在暴露的接口方法里才会真正接收到消息，而如果请求中在body里边，没有加方法命名空间和参数名称，直接就是消息体
            //那接口方法里是接收不到消息的，因为cxf是按照上面的这种格式去解析的，所以如果不符合上面的格式，就应该在这里做处理
            //……………………处理代码……………………
             
            if(is != null) {
                message.setContent(InputStream.class, is);
            }
        } catch (Exception e) {
            log.error("Error when split original inputStream. CausedBy : "+"\n"+e);
        }
    }
}

