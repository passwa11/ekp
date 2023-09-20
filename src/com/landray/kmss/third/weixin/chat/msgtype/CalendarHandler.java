package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class CalendarHandler extends BaseHandler{
    /**
     * {"msgid":"2345881211604379705_1603877680","action":"send","from":"ken","tolist":["icef","test"],"roomid":"wr2LO0CAAAFrTZCGWWAxBA","msgtime":1603877680795,"msgtype":"calendar","calendar":{"title":"xxx业绩复盘会","creatorname":"test","attendeename":["aaa","bbb"],"starttime":1603882800,"endtime":1603886400,"place":"","remarks":""}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject calendar = msgObj.getJSONObject("calendar");
        String extendContent = calendar==null?null:calendar.toString();
        if(calendar!=null){
            String title = calendar.getString("title");
//            String creatorname = calendar.getString("creatorname");
//            JSONArray attendeename = calendar.getJSONArray("attendeename");
//            Long starttime = calendar.getLong("starttime");
//            Long endtime = calendar.getLong("endtime");
//            String place = calendar.getString("place");
//            String remarks = calendar.getString("remarks");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
//                place = ChatdataUtil.encry(encrypter,place);
//                remarks = ChatdataUtil.encry(encrypter,remarks);
//                calendar.put("place",place);
//                calendar.put("remarks",remarks);
            }
            main.setFdTitle(title);
            main.setFdExtendContent(extendContent);
        }

        return main;
    }
}
