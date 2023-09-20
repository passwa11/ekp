package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class MeetingHandler extends BaseHandler{
    /**
     *{"msgid":"5935786683775673543_1603877328","action":"send","from":"ken","tolist":["icef","test"],"roomid":"wr2vOpDgAAN4zVWKbS","msgtime":1603877328914,"msgtype":"meeting","meeting":{"topic":"夕会","starttime":1603877400,"endtime":1603881000,"address":"","remarks":"","meetingtype":102,"meetingid":1210342560,"status":1}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject meeting = msgObj.getJSONObject("meeting");
        if(meeting!=null){
            String topic = meeting.getString("topic");
            String address = meeting.getString("address");
            String remarks = meeting.getString("remarks");
            if(encrypter!=null) {
                topic = ChatdataUtil.encry(encrypter,topic);
                address = ChatdataUtil.encry(encrypter,address);
                remarks = ChatdataUtil.encry(encrypter,remarks);
            }
            Long meetingid = meeting.getLong("meetingid");
            main.setFdMeetingId(meetingid+"");
            main.setFdAddress(address);
            main.setFdTitle(topic);
            main.setFdContent(remarks);
        }
        return main;
    }
}
