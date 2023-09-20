package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class SphfeedHandler extends BaseHandler{

    /**
     * {"msgid":"5702551662099334532_1619511584_external","action":"send","from":"yangzhu1","tolist":["wmJSb5CgAA4aWXWndJspQGpJMDbsMwMA"],"roomid":"","msgtime":1619511584444,"msgtype":"sphfeed","sphfeed":{"feed_type":4,"sph_name":"云游天地旅行家","feed_desc":"瑞士丨盖尔默缆车，名副其实的过山车~\n\n#旅行#风景#热门"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject sphfeed = msgObj.getJSONObject("sphfeed");
        if(sphfeed!=null){
            main.setFdExtendContent(sphfeed.toString());
        }
        return main;
    }
}
