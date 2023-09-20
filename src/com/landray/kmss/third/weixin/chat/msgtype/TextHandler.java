package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class TextHandler extends BaseHandler{

    /**
     * {"msgid":"CAQQluDa4QUY0On2rYSAgAMgzPrShAE=","action":"send","from":"XuJinSheng","tolist":["icefog"],"roomid":"","msgtime":1547087894783,"msgtype":"text","text":{"content":"test"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject text = msgObj.getJSONObject("text");
        if(text!=null){
            String content = text.getString("content");
            if(encrypter!=null) {
                content = ChatdataUtil.encry(encrypter,content);
            }
            main.setFdContent(content);
        }
        return main;
    }
}
