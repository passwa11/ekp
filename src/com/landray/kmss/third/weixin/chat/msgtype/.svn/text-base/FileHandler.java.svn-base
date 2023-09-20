package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class FileHandler extends BaseHandler{

    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject file = msgObj.getJSONObject("file");
        if(file!=null){
            String sdkfileid = file.getString("sdkfileid");
            String md5sum = file.getString("md5sum");
            String filename = file.getString("filename");
            if(encrypter!=null) {
                filename = ChatdataUtil.encry(encrypter,filename);
            }
            String fileext = file.getString("fileext");
            Integer filesize = file.getInteger("filesize");
            main.setFdFileId(sdkfileid);
            main.setFdFileSize(filesize);
            main.setFdFileMd5(md5sum);
            main.setFdTitle(filename);
            main.setFdFileExt(fileext);
        }
        return main;
    }

    @Override
    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception {
        ThirdWeixinChatDataMain main = new ThirdWeixinChatDataMain();
        if(msgObj!=null){
            String sdkfileid = msgObj.getString("sdkfileid");
            String md5sum = msgObj.getString("md5sum");
            String filename = msgObj.getString("filename");
            String fileext = msgObj.getString("fileext");
            Integer filesize = msgObj.getInteger("filesize");
            main.setFdFileId(sdkfileid);
            main.setFdFileSize(filesize);
            main.setFdFileMd5(md5sum);
            main.setFdTitle(filename);
            main.setFdFileExt(fileext);
        }
        return main;
    }
}
