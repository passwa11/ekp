package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class VoteHandler extends BaseHandler{
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        String votetitle = msgObj.getString("votetitle");
        String voteid = msgObj.getString("voteid");
        main.setFdVoteId(voteid);
        String extendContent = msgObj.toString();
        if(encrypter!=null) {
            votetitle = ChatdataUtil.encry(encrypter,votetitle);
            extendContent = ChatdataUtil.encry(encrypter,extendContent);
        }
        main.setFdTitle(votetitle);
        main.setFdExtendContent(extendContent);
        return main;
    }
}
