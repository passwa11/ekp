package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class DocmsgHandler extends BaseHandler{
    /**
     * {"msgid":"9732089160923053207_1603877765","action":"send","from":"ken","tolist":["icef","test"],"roomid":"wrJawBCQAAStr3jxVxEH","msgtime":1603877765291,"msgtype":"docmsg","doc":{"title":"测试&演示客户","doc_creator":"test","link_url":"https://doc.weixin.qq.com/txdoc/excel?docid=xxx"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject doc = msgObj.getJSONObject("doc");
        if(doc!=null){
            String title = doc.getString("title");
            String link_url = doc.getString("link_url");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                link_url = ChatdataUtil.encry(encrypter,link_url);
            }
            main.setFdTitle(title);
            main.setFdLinkUrl(link_url);
            String doc_creator = doc.getString("doc_creator");
            main.setFdUserId(doc_creator);
        }
        return main;
    }
}
