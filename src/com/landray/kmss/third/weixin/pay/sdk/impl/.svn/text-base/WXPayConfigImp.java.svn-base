package com.landray.kmss.third.weixin.pay.sdk.impl;

import com.landray.kmss.third.weixin.pay.sdk.IWXPayDomain;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class WXPayConfigImp extends WXPayConfig {

    private String appID;
    private String mchID;
    private String key;
    private IWXPayDomain wxPayDomain;
    private String certFilePath;

    public WXPayConfigImp(String appID,String mchID,String key,IWXPayDomain wxPayDomain,String certFilePath){
        this.appID = appID;
        this.mchID = mchID;
        this.key = key;
        this.wxPayDomain = wxPayDomain;
        this.certFilePath = certFilePath;
    }

    @Override
    public String getAppID() {
        return this.appID;
    }

    @Override
    public String getMchID() {
        return this.mchID;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public InputStream getCertStream() throws FileNotFoundException {
        if(certFilePath==null){
            return null;
        }
        return new FileInputStream(new File(certFilePath));
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return this.wxPayDomain;
    }
}
