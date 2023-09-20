package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class LocationHandler extends BaseHandler{

    /**
     * {"msgid":"2641513858500683770_1603876152","action":"send","from":"icefog","tolist":["wmN6etBgAA0sbJ3invMvRxPQDFoq9uWA"],"roomid":"","msgtime":1603876152141,"msgtype":"location","location":{"longitude":116.586285899,"latitude":39.911125799,"address":"北京市xxx区xxx路xxx大厦x座","title":"xxx管理中心","zoom":15}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject location = msgObj.getJSONObject("location");
        if(location!=null){
            Double longitude = location.getDouble("longitude");
            Double latitude = location.getDouble("latitude");
            String address = location.getString("address");
            String title = location.getString("title");
            Integer zoom = location.getInteger("zoom");
            if(encrypter!=null) {
                title = ChatdataUtil.encry(encrypter,title);
                address = ChatdataUtil.encry(encrypter,address);
            }
            main.setFdTitle(title);
            main.setFdContent(address);
            main.setFdLongitude(longitude);
            main.setFdLatitude(latitude);
            main.setFdZoom(zoom);
        }

        return main;
    }
}
