package com.landray.kmss.third.weixin.work.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.StringUtil;

public class ThirdWeixinChatdataSyncConfig  extends BaseAppConfig {

    public ThirdWeixinChatdataSyncConfig() throws Exception {
        super();
    }

    @Override
    public String getJSPUrl() {
        return null;
    }

    public String getLastSyncSeq() {
        String value = getValue("lastSyncSeq");
        if(StringUtil.isNull(value)){
            value = "0";
        }
        return value;
    }

    public void setLastSyncSeq(String lastSyncSeq) {
        setValue("lastSyncSeq",lastSyncSeq);
    }

}
