package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class EmotionHandler extends BaseHandler{

    /**
     * {"msgid":"6623217619416669654_1603875612","action":"send","from":"icef","tolist":["wmErxtDgAAhteCglUZH2kUt3rq431qmg"],"roomid":"","msgtime":1603875611148,"msgtype":"emotion","emotion":{"type":1,"width":290,"height":290,"imagesize":962604,"md5sum":"94c2b0bba52cc456cb8221b248096612","sdkfileid":"4eE1ESTVNalE1TnprMFh6RTJNRE00TnpVMk1UST0aIDc0NzI2NjY1NzE3NTc0Nzg2ZDZlNzg2YTY5NjY2MTYx"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject emotion = msgObj.getJSONObject("emotion");
        if(emotion!=null){
            main.setFdEmotionType(emotion.getInteger("type"));
            main.setFdWidth(emotion.getInteger("width"));
            main.setFdHeight(emotion.getInteger("height"));
            String fileId = emotion.getString("sdkfileid");
            String md5sum = emotion.getString("md5sum");
            Integer imagesize = emotion.getInteger("imagesize");
            main.setFdFileId(fileId);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(imagesize);
        }
        return main;
    }

    @Override
    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception {
        ThirdWeixinChatDataMain main = new ThirdWeixinChatDataMain();
        if(msgObj!=null){
            main.setFdEmotionType(msgObj.getInteger("type"));
            main.setFdWidth(msgObj.getInteger("width"));
            main.setFdHeight(msgObj.getInteger("height"));
            String fileId = msgObj.getString("sdkfileid");
            String md5sum = msgObj.getString("md5sum");
            Integer imagesize = msgObj.getInteger("imagesize");
            main.setFdFileId(fileId);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(imagesize);
        }
        return main;
    }
}
