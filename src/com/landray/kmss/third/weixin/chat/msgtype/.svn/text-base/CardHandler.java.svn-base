package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class CardHandler extends BaseHandler{
    /**
     * {"msgid":"13714216591700685558_1603875680","action":"send","from":"kenshin","tolist":["wmGAgeDQAAy2Dtr0F8aK4dTuatfm-5Rg"],"roomid":"","msgtime":1603875680377,"msgtype":"card","card":{"corpname":"微信联系人","userid":"wmGAgeDQAAGjFmfnP7A3j2JxQDdLNhSw"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject card = msgObj.getJSONObject("card");
        if(card!=null){
            String corpname = card.getString("corpname");
            String userid = card.getString("userid");
            main.setFdUserId(userid);
            main.setFdCorpName(corpname);
        }
        return main;
    }
}
