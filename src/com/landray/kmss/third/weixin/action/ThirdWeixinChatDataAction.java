package com.landray.kmss.third.weixin.action;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.third.weixin.chat.util.ChatdataUtil;
import com.landray.kmss.third.weixin.forms.ThirdWeixinChatGroupForm;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatDataMain;
import com.landray.kmss.third.weixin.model.ThirdWeixinChatGroup;
import com.landray.kmss.third.weixin.oms.WxOmsConfig;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatDataMainService;
import com.landray.kmss.third.weixin.service.IThirdWeixinChatGroupService;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThirdWeixinChatDataAction extends ExtendAction {

    private static final Logger logger = LoggerFactory.getLogger(ThirdWeixinChatDataAction.class);

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        return null;
    }

    public ActionForward genRsaKey(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        TimeCounter.logCurrentTime("Action-genRsaKey", true, getClass());
        KmssMessages messages = new KmssMessages();
        JSONObject json = new JSONObject();
        json.put("status", 1);
        json.put("msg", "成功");
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            keygen.initialize(2048);
            KeyPair pair = keygen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            byte[] privBytes = priv.getEncoded();
            byte[] pubBytes = pub.getEncoded();

            String publicKey = new String(
                    org.bouncycastle.util.encoders.Base64.encode(pubBytes));
            String privateKey = new String(
                    org.bouncycastle.util.encoders.Base64.encode(privBytes));
            logger.info("publicKey:"+publicKey);
            logger.info("privateKey:"+privateKey);

            StringBuffer buffer = new StringBuffer();
            buffer.append("-----BEGIN PUBLIC KEY-----\r\n");
            for (int i = 0; i < publicKey.length(); i++) {
                if (i > 0 && i % 64 == 0) {
                    buffer.append("\r\n");
                }
                buffer.append(publicKey.charAt(i));
            }
            buffer.append("\r\n");
            buffer.append("-----END PUBLIC KEY-----");
            json.put("publicKey",buffer.toString());
            json.put("privateKey",privateKey);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            messages.addError(e);
            json.put("status", 0);
            json.put("msg", e.getMessage());
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(json.toString());
        TimeCounter.logCurrentTime("Action-genRsaKey", false, getClass());
        return null;
    }
}
