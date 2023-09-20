package com.landray.kmss.km.signature.service.spring;

import org.slf4j.Logger;

import com.landray.kmss.km.signature.service.IKmSignaturePasswordEncoder;
import com.landray.kmss.sys.profile.password.interfaces.PasswordConfusionMD5;

public class KmSignaturePasswordEncoder extends PasswordConfusionMD5 implements
		IKmSignaturePasswordEncoder {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmSignaturePasswordEncoder.class);

	public KmSignaturePasswordEncoder() {
	}

	@Override
    public String encodePassword(String password) {
		try {
			return super.encrypt(password);
		} catch (Exception e) {
			logger.error("签章加密失败：", e);
		}
		return null;
	}
}
