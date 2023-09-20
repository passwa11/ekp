package com.landray.kmss.third.weixin.pay.sdk.impl;

import com.landray.kmss.third.weixin.pay.sdk.IWXPayDomain;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConfig;
import com.landray.kmss.third.weixin.pay.sdk.WXPayConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WXPayDomainImp implements IWXPayDomain {

    private static String domain = WXPayConstants.DOMAIN_API;

    private static final Logger logger = LoggerFactory
            .getLogger(WXPayDomainImp.class);

    @Override
    public void report(String domain, long elapsedTimeMillis, Exception ex) {
        if(ex!=null){
            logger.error("调用接口失败:"+domain,ex);
        }
    }

    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo(domain,true );
    }
}
