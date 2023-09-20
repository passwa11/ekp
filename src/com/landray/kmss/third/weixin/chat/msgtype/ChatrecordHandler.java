package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.ThirdWeixinChatDataFile;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;

public class ChatrecordHandler extends BaseHandler{

    private static final Logger logger = LoggerFactory.getLogger(ChatrecordHandler.class);

    /**
     * {"msgid":"11354299838102555191_1603875658","action":"send","from":"ken","tolist":["icef"],"roomid":"","msgtime":1603875657905,"msgtype":"chatrecord","chatrecord":{"title":"群聊","item":[{"type":"ChatRecordText","msgtime":1603875610,"content":"{\"content\":\"test\"}","from_chatroom":false},{"type":"ChatRecordText","msgtime":1603875620,"content":"{\"content\":\"test2\"}","from_chatroom":false}]}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject chatrecord = msgObj.getJSONObject("chatrecord");
        if(chatrecord!=null){
            String title = chatrecord.getString("title");
            JSONArray item = chatrecord.getJSONArray("item");
            if(item!=null){
                for(int i=0;i<item.size();i++){
                    JSONObject o = item.getJSONObject(i);
                    String type = o.getString("type");
                    String content = o.getString("content");
                    if(StringUtil.isNull(type) || StringUtil.isNull(content)){
                        continue;
                    }
                    IChatdataMsgHandler handler = getMsgHandler(type);
                    if(handler==null){
                        logger.warn("找不到 "+type+" 对应的消息处理器，该消息不处理。消息内容："+msgObj.toString());
                        continue;
                    }
                    ThirdWeixinChatDataMain inner = handler.buildInnerChatDataMain(JSONObject.parseObject(content));
                    if(inner!=null && StringUtil.isNotNull(inner.getFdFileId())){
                        ThirdWeixinChatDataFile file = new ThirdWeixinChatDataFile(inner.getFdFileId(),inner.getFdTitle(),inner.getFdFileSize(),inner.getFdFileMd5(),inner.getFdFileExt());
                        main.getInnerFiles().add(file);
                    }
                }
            }
            String chatrecordStr = chatrecord.toString();
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                chatrecordStr = ChatdataUtil.encry(encrypter,chatrecordStr);
            }
            main.setFdTitle(title);
            main.setFdExtendContent(chatrecordStr);
        }
        return main;
    }

    private IChatdataMsgHandler getMsgHandler(String msgtype){
        msgtype = msgtype.replace("ChatRecord","");
        msgtype = msgtype.toLowerCase();
        msgtype = msgtype.replaceAll("_","");
        IChatdataMsgHandler handler = (IChatdataMsgHandler) SpringBeanUtil.getBean(msgtype+"Handler");
        return handler;
    }
}
