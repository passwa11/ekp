package com.landray.kmss.third.weixin.chat.msgtype;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;

import javax.crypto.Cipher;

public class MeetingvoicecallHandler extends BaseHandler{

    /**
     * {"msgid":"17952229780246929345_1594197637","action":"send","from":"wo137MCgAAYW6pIiKKrDe5SlzEhSgwbA","tolist":["wo137MCgAAYW6pIiKKrDe5SlzEhSgwbA"],"msgtime":1594197581203,"msgtype":"meeting_voice_call","voiceid":"grb8a4c48a3c094a70982c518d55e40557","meeting_voice_call":{"endtime":1594197635,"sdkfileid":"CpsBKjAqd0xhb2JWRUJldGtwcE5DVTB6UjRUalN6c09vTjVyRnF4YVJ5M24rZC9YcHF3cHRPVzRwUUlaMy9iTytFcnc0SlBkZDU1YjRNb0MzbTZtRnViOXV5WjUwZUIwKzhjbU9uRUlxZ3pyK2VXSVhUWVN2ejAyWFJaTldGSkRJVFl0aUhkcVdjbDJ1L2RPbjJsRlBOamJaVDNnPT0SOE5EZGZNVFk0T0RnMU16YzVNVGt5T1RJMk9GOHhNalk0TXpBeE9EZzJYekUxT1RReE9UYzJNemM9GiA3YTYyNzA3NTY4Nzc2MTY3NzQ2MTY0NzA2ZTc4NjQ2OQ==","demofiledata":[{"filename":"65eb1cdd3e7a3c1740ecd74220b6c627.docx","demooperator":"wo137MCgAAYW6pIiKKrDe5SlzEhSgwbA","starttime":1594197599,"endtime":1594197609}],"sharescreendata":[{"share":"wo137MCgAAYW6pIiKKrDe5SlzEhSgwbA","starttime":1594197624,"endtime":1594197624}]}}
     * @param msgObj
     * @param encrypter
     * @return
     * @throws Exception
     */
    @Override
    public ThirdWeixinChatDataMain buildChatDataMain(JSONObject msgObj, Cipher encrypter) throws Exception {
        ThirdWeixinChatDataMain main = super.buildChatDataMain(msgObj,encrypter);
        JSONObject meeting_voice_call = msgObj.getJSONObject("meeting_voice_call");
        String voiceid = msgObj.getString("voiceid");
        main.setFdVoteId(voiceid);
        if(meeting_voice_call!=null){
            String sdkfileid = meeting_voice_call.getString("sdkfileid");
            main.setFdFileId(sdkfileid);
            String extendContent = meeting_voice_call.toString();
            if(encrypter!=null) {
                extendContent = ChatdataUtil.encry(encrypter,extendContent);
            }
            main.setFdExtendContent(extendContent);
        }
        return main;
    }

    @Override
    public ThirdWeixinChatDataMain buildInnerChatDataMain(JSONObject msgObj) throws Exception {
        ThirdWeixinChatDataMain main = new ThirdWeixinChatDataMain();
        if(msgObj!=null){
            String sdkfileid = msgObj.getString("sdkfileid");
            main.setFdFileId(sdkfileid);
        }
        return main;
    }
}
