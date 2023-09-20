package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class VoipdocshareHandler extends BaseHandler{

    /**
     * {"msgid":"16527954622422422847_1594199256","action":"send","from":"18002520162","tolist":["wo137MCgAAYW6pIiKKrDe5SlzEhSgwbA"],"msgtime":1594199235014,"msgtype":"voip_doc_share","voipid":"gr2751c98b19300571f8afb3b74514bd32","voip_doc_share":{"filename":"欢迎使用微盘.pdf.pdf","md5sum":"ff893900f24e55e216e617a40e5c4648","filesize":4400654,"sdkfileid":"CpsBKjAqZUlLdWJMd2gvQ1JxMzd0ZjlpdW5mZzJOOE9JZm5kbndvRmRqdnBETjY0QlcvdGtHSFFTYm95dHM2VlllQXhkUUN5KzRmSy9KT3pudnA2aHhYZFlPemc2aVZ6YktzaVh3YkFPZHlqNnl2L2MvcGlqcVRjRTlhZEZsOGlGdHJpQ2RWSVNVUngrVFpuUmo3TGlPQ1BJemlRPT0SOE5EZGZNVFk0T0RnMU16YzVNVGt5T1RJMk9GODFNelUyTlRBd01qQmZNVFU1TkRFNU9USTFOZz09GiA3YTcwNmQ2Zjc5NjY3MDZjNjY2Zjc4NzI3NTZmN2E2YQ=="}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        String voipid = msgObj.getString("voipid");
        main.setFdVoteId(voipid);
        JSONObject voip_doc_share = msgObj.getJSONObject("voip_doc_share");
        if(voip_doc_share!=null){
            String sdkfileid = voip_doc_share.getString("sdkfileid");
            String md5sum = voip_doc_share.getString("md5sum");
            Integer filesize = voip_doc_share.getInteger("filesize");
            String filename = voip_doc_share.getString("filename");
            if(encrypter!=null) {
                filename = ChatdataUtil.encry(encrypter,filename);
            }
            main.setFdFileId(sdkfileid);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(filesize);
            main.setFdTitle(filename);
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
            String filename = msgObj.getString("filename");
            main.setFdFileId(sdkfileid);
            main.setFdFileMd5(md5sum);
            main.setFdFileSize(filesize);
            main.setFdTitle(filename);
        }
        return main;
    }
}
