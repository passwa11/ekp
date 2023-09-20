package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class ExternalredpacketHandler extends BaseHandler{

    /**
     * {"msgid":"8632214264349267353_1603786184","action":"send","from":"woJ7ijBwAAmqwojT8r_DaNMbr_NAvaag","tolist":["woJ7ijBwAA6SjS_sIyPLZtyEPJlT7Cfw","tiny-six768"],"roomid":"wrJ7ijBwAAG1vly_DzVI72Ghc-PtA5Dw","msgtime":1603786183955,"msgtype":"external_redpacket","redpacket":{"type":1,"wish":"恭喜发财，大吉大利","totalcnt":2,"totalamount":20}}
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
