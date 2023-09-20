package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class RedpacketHandler extends BaseHandler{

    /**
     *{"msgid":"333590477316965370_1603877439","action":"send","from":"kens","tolist":["1000000444696"],"roomid":"","msgtime":1603877439038,"msgtype":"redpacket","redpacket":{"type":1,"wish":"恭喜发财，大吉大利","totalcnt":1,"totalamount":3000}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject redpacket = msgObj.getJSONObject("redpacket");
        if(redpacket!=null){
            String extendContent = redpacket.toString();
            if(encrypter!=null){
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdExtendContent(extendContent);
        }
        return main;
    }
}
