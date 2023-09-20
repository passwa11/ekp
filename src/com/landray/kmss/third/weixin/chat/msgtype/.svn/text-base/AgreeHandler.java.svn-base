package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.restservice.auth.OauthConstants;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class AgreeHandler extends BaseHandler{
    /**
     * {"msgid":"8891446340739254950_1603875826","action":"send","from":"wmGAgeDQAAvQeaTqWwkMTxGMkvI7OOuQ","tolist":["kenshin"],"roomid":"","msgtime":1603875826656,"msgtype":"agree","agree":{"userid":"wmGAgeDQAAvQeaTqWwkMTxGMkvI7OOuQ","agree_time":1603875826656}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject agree = msgObj.getJSONObject("agree");
        if(agree!=null){
            String userid = agree.getString("userid");
            Long agree_time = agree.getLong("agree_time");
            main.setFdUserId(userid);
            main.setFdAgreeTime(agree_time);
        }
        return main;
    }
}
