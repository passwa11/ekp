package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class RevokeHandler extends BaseHandler{

    /**
     *{"msgid":"15775510700152506326_1603875615","action":"recall","from":"kenshin","tolist":["wmUu0zBgAALV7ZymkcMyxvbTe8YdWxxA"],"roomid":"","msgtime":1603875615723,"msgtype":"revoke","revoke":{"pre_msgid":"14822339130656386894_1603875600"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject revoke = msgObj.getJSONObject("revoke");
        if(revoke!=null){
            String extendContent = revoke.toString();
            if(encrypter!=null){
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdExtendContent(extendContent);
        }
        return main;
    }
}
