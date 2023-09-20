package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public interface IChatdataMsgHandler {

    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject obj, Cipher encrypter) throws Exception;

    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception;

}