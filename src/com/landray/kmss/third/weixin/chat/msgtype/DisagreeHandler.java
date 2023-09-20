package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class DisagreeHandler extends BaseHandler{
    /**
     * {"msgid":"17972321270926900092_1603875944","action":"send","from":"wmErxtDgAA9AW32YyyuYRimKr7D1KWlw","tolist":["kenshin"],"roomid":"","msgtime":1603875944122,"msgtype":"disagree","disagree":{"userid":"wmErxtDgAA9AW32YyyuYRimKr7D1KWlw","disagree_time":1603875944122}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject disagree = msgObj.getJSONObject("disagree");
        if(disagree!=null){
            String userid = disagree.getString("userid");
            Long agree_time = disagree.getLong("disagree_time");
            main.setFdUserId(userid);
            main.setFdAgreeTime(agree_time);
        }
        return main;
    }
}
