package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class VoiceHandler extends BaseHandler{

    /**
     *{"msgid":"10958372969718811103_1603875609","action":"send","from":"wmGAgeDQAAdBjb8CK4ieMPRm7Cqm-9VA","tolist":["kenshin"],"roomid":"","msgtime":1603875609704,"msgtype":"voice","voice":{"md5sum":"9db09c7fa627c9e53f17736c786a74d5","voice_size":6810,"play_length":10,"sdkfileid":"kcyZjZqOXhETGYxajB2Zkp5Rk8zYzh4RVF3ZzZGdXlXNWRjMUoxVGZxbzFTTDJnQ2YxL0NraVcxUUJNK3VUamhEVGxtNklCbjZmMEEwSGRwN0h2cU1GQTU1MDRSMWdTSmN3b25ZMkFOeG5hMS90Y3hTQ0VXRlVxYkR0Ymt5c3JmV2VVcGt6UlNXR1ZuTFRWVGtudXVldDRjQ3hscDBrMmNhMFFXVnAwT3Y5NGVqVGpOcWNQV2wrbUJwV01TRm9xWmNDRVVrcFY5Nk9OUS9GbXIvSmZvOVVZZjYxUXBkWnMvUENkVFQxTHc2N0drb2pJT0FLZnhVekRKZ1FSNDU3ZnZtdmYvTzZDOG9DRXl2SUNIOHc9PRI0TkRkZk56ZzRNVE13TVRjMk5qQTRNak0yTmw4ek5qRTVOalExTjE4eE5qQXpPRGMxTmpBNRogNzM3MDY2NmM2YTc5Njg3NDdhNzU3NDY0NzY3NTY4NjY="}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject voice = msgObj.getJSONObject("voice");
        if(voice!=null){
            String sdkfileid = voice.getString("sdkfileid");
            String md5sum = voice.getString("md5sum");
            Integer filesize = voice.getInteger("voice_size");
            Integer play_length = voice.getInteger("play_length");
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
            Integer filesize = msgObj.getInteger("voice_size");
            Integer play_length = msgObj.getInteger("play_length");
            main.setFdFileId(sdkfileid);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(filesize);
            main.setFdPlayLength(play_length);
        }
        return main;
    }
}
