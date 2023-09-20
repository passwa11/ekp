package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class VideoHandler extends BaseHandler{

    /**
     *{"msgid":"17955920891003447432_1603875627","action":"send","from":"kenshin","tolist":["wmGAgeDQAAHuRJbt4ZQI_1cqoQcf41WQ"],"roomid":"","msgtime":1603875626823,"msgtype":"video","video":{"md5sum":"d06fc80c01d6fbffcca3b229ba41eac6","filesize":15169724,"play_length":108,"sdkfileid":"MzAzMjYxMzAzNTYzMzgzMjMyMzQwMjAxMDAwMjA0MDBlNzc4YzAwNDEwZDA2ZmM4MGMwMWQ2ZmJmZmNjYTNiMjI5YmE0MWVhYzYwMjAxMDQwMjAxMDAwNDAwEjhORGRmTVRZNE9EZzFNREEyTlRjM056QXpORjgxTWpZeE9USTBOek5mTVRZd016ZzNOVFl5Tnc9PRogNTIzNGQ1NTQ5N2RhNDM1ZDhlZTU5ODk4NDQ4NzRhNDk="}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject video = msgObj.getJSONObject("video");
        if(video!=null){
            String sdkfileid = video.getString("sdkfileid");
            String md5sum = video.getString("md5sum");
            Integer filesize = video.getInteger("filesize");
            Integer play_length = video.getInteger("play_length");
            main.setFdFileId(sdkfileid);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(filesize);
            main.setFdPlayLength(play_length);
        }
        return main;
    }

    @Override
    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception {
        ThirdWeixinChatDataMain main = new ThirdWeixinChatDataMain();
        if(msgObj!=null){
            String sdkfileid = msgObj.getString("sdkfileid");
            String md5sum = msgObj.getString("md5sum");
            Integer filesize = msgObj.getInteger("filesize");
            Integer play_length = msgObj.getInteger("play_length");
            main.setFdFileId(sdkfileid);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(filesize);
            main.setFdPlayLength(play_length);
        }
        return main;
    }
}
