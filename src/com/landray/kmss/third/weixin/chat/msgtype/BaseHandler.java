package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class BaseHandler implements IChatdataMsgHandler{
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        String action = msgObj.getString("action");
        String from = msgObj.getString("from");
        String msgid = msgObj.getString("msgid");
        JSONArray tolist = msgObj.getJSONArray("tolist");
        String roomid = msgObj.getString("roomid");
        Long msgtime = msgObj.getLong("msgtime");
        String msgtype = msgObj.getString("msgtype");
        ThirdWeixinChatDataMain main = new ThirdWeixinChatDataMain();
        main.setFdMsgId(msgid);
        main.setFdMsgType(msgtype);
        main.setFdMsgAction(action);
        main.setFdFrom(from);
        main.setFdToList(tolist==null?null:tolist.toString());
        main.setFdRoomId(roomid);
        main.setFdMsgTime(msgtime);
        if(tolist!=null && tolist.size()==1){
            main.setFdTo(tolist.getString(0));
        }
        if(encrypter==null) {
            main.setFdEncryType(1);
        }else{
            main.setFdEncryType(3);
        }
        return main;
    }

    @Override
    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception {
        return null;
    }
}
