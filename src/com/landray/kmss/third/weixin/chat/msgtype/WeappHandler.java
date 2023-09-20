package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class WeappHandler extends BaseHandler{

    /**
     * {"msgid":"11930598857592605935_1603875608","action":"send","from":"kens","tolist":["wmGAgeDQAAsgQetTQGqRbMxrkodpM3fA"],"roomid":"","msgtime":1603875608691,"msgtype":"weapp","weapp":{"title":"开始聊天前请仔细阅读服务须知事项","description":"客户需同意存档聊天记录","username":"xxx@app","displayname":"服务须知"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject weapp = msgObj.getJSONObject("weapp");
        if(weapp!=null){
            String title = weapp.getString("title");
            String description = weapp.getString("description");
            String username = weapp.getString("username");
            String displayname = weapp.getString("displayname");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                description = ChatdataUtil.encry(encrypter,description);
            }
            main.setFdTitle(title);
            main.setFdContent(description);
            main.setFdDisplayName(displayname);
            main.setFdUsername(username);
        }
        return main;
    }
}
