package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class TodoHandler extends BaseHandler{

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
        JSONObject todo = msgObj.getJSONObject("todo");
        if(todo!=null){
            String title = todo.getString("title");
            String content = todo.getString("content");
            String extendContent = todo.toString();
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                content = ChatdataUtil.encry(encrypter,content);
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdTitle(title);
            main.setFdContent(content);
            main.setFdExtendContent(extendContent);
        }


        return main;
    }
}
