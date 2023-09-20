package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class NewsHandler extends BaseHandler{

    /**
     * {"msgid":"118732825779547782215","action":"send","from":"kens","tolist":["icef","test"],"roomid":"wrErxtDgAA0jgXE5","msgtime":1603876045165,"msgtype":"news","info":{"item":[{"title":"service ","description":"test","url":"http://xxx","picurl":"https://www.qq.com/xxx.jpg"}]}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject info = msgObj.getJSONObject("info");
        if(info!=null){
            String extendContent = info.toString();
            if(encrypter!=null) {
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdExtendContent(extendContent);
        }
        return main;
    }
}
