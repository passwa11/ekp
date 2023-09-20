package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.ThirdWeixinChatDataFile;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.work.service.spring.ThirdWeixinChatDataServiceImp;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;

public class MixedHandler extends BaseHandler{

    private static final Logger logger = LoggerFactory.getLogger(MixedHandler.class);

    /**
     *{"msgid":"DAQQluDa4QUY0On4kYSABAMgzPrShAE=","action":"send","from":"HeMiao","tolist":["HeChangTian","LiuZeYu"],"roomid":"wr_tZ2BwAAUwHpYMwy9cIWqnlU3Hzqfg","msgtime":1577414359072,"msgtype":"mixed","mixed":{"item":[{"type":"text","content":"{\"content\":\"你好[微笑]\\n\"}"},{"type":"image","content":"{\"md5sum\":\"368b6c18c82e6441bfd89b343e9d2429\",\"filesize\":13177,\"sdkfileid\":\"CtYBMzA2OTAyMDEwMjA0NjIzMDYwMDIwMTAwMDWwNDVmYWY4Y2Q3MDIwMzBmNTliMTAyMDQwYzljNTQ3NzAyMDQ1ZTA1NmFlMjA0MjQ2NjM0NjIzNjY2MzYzNTMyMmQzNzYxMzQ2NDJkMzQ2MjYxNjQyZDM4MzMzMzM4MmQ3MTYyMzczMTM4NjM2NDYxMzczMjY2MzkwMjAxMDAwMjAzMDIwMDEwMDQxMDM2OGI2YzE4YzgyZTY0NDFiZmQ4OWIyNDNlOWQyNDI4MDIwMTAyMDIwMTAwMDQwMBI4TkRkZk2UWTRPRGcxTVRneE5URTFNRGc1TVY4eE1UTTFOak0yTURVeFh6RTFOemMwTVRNek5EYz0aIDQzMTY5NDFlM2MxZDRmZjhhMjEwY2M0NDQzZGUXOTEy\"}"}]}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject mixed = msgObj.getJSONObject("mixed");
        if(mixed!=null){
            JSONArray item = mixed.getJSONArray("item");
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
                    if(inner !=null && StringUtil.isNotNull(inner.getFdFileId())){
                        ThirdWeixinChatDataFile file = new ThirdWeixinChatDataFile(inner.getFdFileId(),inner.getFdTitle(),inner.getFdFileSize(),inner.getFdFileMd5(),inner.getFdFileExt());
                        main.getInnerFiles().add(file);
                    }
                }
            }
            String extendContent = mixed.toString();
            if(encrypter!=null) {
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdExtendContent(extendContent);
        }
        return main;
    }

    private IChatdataMsgHandler getMsgHandler(String msgtype){
        msgtype = msgtype.replaceAll("_","");
        IChatdataMsgHandler handler = (IChatdataMsgHandler) SpringBeanUtil.getBean(msgtype+"Handler");
        return handler;
    }
}
