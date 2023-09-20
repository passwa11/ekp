package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class CollectHandler extends BaseHandler{
    /**
     * {"msgid":"2500536226619379797_1576034482","action":"send","from":"nick","tolist":["XuJinSheng","15108264797"],"roomid":"wrjc7bDwYAOAhf9quEwRRxyyoMm0QAAA","msgtime":1576034482344,"msgtype":"collect","collect":{"room_name":"这是一个群","creator":"nick","create_time":"2019-12-11 11:21:22","title":"这是填表title","details":[{"id":1,"ques":"表项1，文本","type":"Text"},{"id":2,"ques":"表项2，数字","type":"Number"},{"id":3,"ques":"表项3，日期","type":"Date"},{"id":4,"ques":"表项4，时间","type":"Time"}]}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject collect = msgObj.getJSONObject("collect");
        if(collect!=null){
            String title = collect.getString("title");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
            }
            main.setFdTitle(title);
            collect.remove("title");
            main.setFdExtendContent(collect.toString());
        }
        return main;
    }
}
