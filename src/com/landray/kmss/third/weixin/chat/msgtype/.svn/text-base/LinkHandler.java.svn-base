package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class LinkHandler extends BaseHandler{

    /**
     *
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject link = msgObj.getJSONObject("link");
        if(link!=null){
            String title = link.getString("title");
            String description = link.getString("description");
            String link_url = link.getString("link_url");
            String image_url = link.getString("image_url");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                description = ChatdataUtil.encry(encrypter,description);
                link_url = ChatdataUtil.encry(encrypter,link_url);
                image_url = ChatdataUtil.encry(encrypter,image_url);
            }
            main.setFdTitle(title);
            main.setFdContent(description);
            main.setFdLinkUrl(link_url);
            main.setFdImageUrl(image_url);
        }

        return main;
    }
}
