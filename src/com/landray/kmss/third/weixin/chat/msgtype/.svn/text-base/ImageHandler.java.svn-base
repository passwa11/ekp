package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class ImageHandler extends BaseHandler{

    /**
     *{"msgid":"CAQQvPnc4QUY0On2rYSAgAMgooLa0Q8=","action":"send","from":"XuJinSheng","tolist":["icefog"],"roomid":"","msgtime":0,"msgtype":"image","image":{"md5sum":"50de8e5ae8ffe4f1df7a93841f71993a","filesize":70961,"sdkfileid":"CtYBMzA2OTAyMDEwMjA0NjIzMDYwMDIwMTAwMDIwNGI3ZmU0MDZlMDIwMzBmNTliMTAyMDQ1YzliNTQ3NzAyMDQ1YzM3M2NiYzA0MjQ2NjM0MzgzNTM0NjEzNTY1MmQzNDYxMzQzODJkMzQzMTYxNjEyZDM5NjEzOTM2MmQ2MTM2NjQ2NDY0NjUzMDY2NjE2NjM1MzcwMjAxMDAwMjAzMDExNTQwMDQxMDUwZGU4ZTVhZThmZmU0ZjFkZjdhOTM4NDFmNzE5OTNhMDIwMTAyMDIwMTAwMDQwMBI4TkRkZk1UWTRPRGcxTVRBek1ETXlORFF6TWw4eE9UUTVOamN6TkRZMlh6RTFORGN4TWpNNU1ERT0aIGEwNGQwYWUyM2JlYzQ3NzQ5MjZhNWZjMjk0ZTEyNTkz"}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject image = msgObj.getJSONObject("image");
        if(image!=null){
            String sdkfileid = image.getString("sdkfileid");
            String md5sum = image.getString("md5sum");
            Integer filesize = image.getInteger("filesize");
            main.setFdFileId(sdkfileid);
            main.setFdFileSize(filesize);
            main.setFdFileMd5(md5sum);
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
            main.setFdFileId(sdkfileid);
            main.setFdFileSize(filesize);
            main.setFdFileMd5(md5sum);
        }
        return main;
    }
}
