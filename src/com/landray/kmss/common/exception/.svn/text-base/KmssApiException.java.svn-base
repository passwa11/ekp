package com.landray.kmss.common.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.util.RequestUtils;

/**
 * API接口方法使用的异常，只接受一个KmssMessage
 * @author 陈进科
 * 2019-03-07
 */
public class KmssApiException extends KmssRuntimeException {

    private static final long serialVersionUID = 458759875987549L;

    public KmssApiException(KmssMessage msg, Throwable cause){
        super(msg,cause);
    }
    
    private boolean appendRc = false;
    /**
     * @param appendRc  true：在getMessage时添加root cause的message
     */
    public void setAppendRc(boolean appendRc){
        this.appendRc = appendRc;
    }
    @Override
    public String getMessage() {
        try {
            KmssMessages msgs = getKmssMessages();
            StringBuilder sb = new StringBuilder();
            //only one
            KmssMessage kmssMessage = msgs.getMessages().get(0);
            sb.append(getMessageInfo(kmssMessage));
            if(this.appendRc){
                sb.append(" cause by: ").append(super.getMessage());
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return super.getMessage();
    }
    
    public String getCode(){
        try{
            KmssMessage kmssMessage = getKmssMessages().getMessages().get(0);
            return kmssMessage.getMessageKey();
        }catch(Exception e){
        }
        return RestResponse.ERROR_CODE_500;
    }

    private String getMessageInfo(KmssMessage msg) {
        HttpServletRequest currentRequest = Plugin.currentRequest();
        Locale locale = null;
        if(currentRequest!=null){
            locale = RequestUtils.getUserLocale(currentRequest, null);
        }
        Object[] params = msg.getParameter();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof KmssMessage) {
                    params[i] = getMessageInfo((KmssMessage) params[i]);
                }
            }
            return ResourceUtil.getString(msg.getMessageKey(), null, locale,
                    params);
        } else {
            return ResourceUtil.getString(msg.getMessageKey(),locale);
        }
    }
}
