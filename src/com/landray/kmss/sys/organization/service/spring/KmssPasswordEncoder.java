package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.sys.organization.service.IKmssPasswordEncoder;
import com.landray.kmss.sys.organization.util.PasswordEncoderUtils;
import com.landray.kmss.sys.profile.password.interfaces.IPasswordConfusionEncrypt;
import com.landray.kmss.sys.profile.password.util.PasswordConfusionUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密/校验器
 * security5为提高安全性，取消了salt参数并由其内部实现，为保持历史数据的一致性，此类改为自己实现
 */
public class KmssPasswordEncoder implements IKmssPasswordEncoder, PasswordEncoder {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmssPasswordEncoder.class);
	private final IPasswordConfusionEncrypt legacyMd5Encrypt;
	private IPasswordConfusionEncrypt currentEncrypt;

	public KmssPasswordEncoder(String algorithm) {
		//默认为MD5;
		if (StringUtil.isNull(algorithm)) {
			algorithm = "MD5";
		}
		/*
		EKP在V16之前都是使用MD5算法作为encoder的实现，应信创要求，需要支持国密算法SM3
		为了支持历史数据，保留MD5，其它算法的支持交由IPasswordConfusionEncrypt来完成
		为了兼容旧数据，encode 和 match方法默认优先使用currentEncrypt来支持，其中match可能要经历两次校验
		 */
		try {
			this.legacyMd5Encrypt = PasswordConfusionUtil.getEncrypt("MD5");
		} catch (Exception var2) {
			throw new IllegalArgumentException("No such algorithm [MD5]");
		}
		//如果配置还是MD5,那就没必要初始化currentEncrypt
		try{
			if(!"MD5".equalsIgnoreCase(algorithm)){
				currentEncrypt  = PasswordConfusionUtil.getEncrypt(algorithm);
			}
		}catch(Exception var2){
			throw new IllegalArgumentException(var2);
		}

	}

	/**
	 * 此方法用于兼容旧代码，PasswordEncoder接口已改为encode与matches
	 */
	@Override
    public String encodePassword(String password) {
		return encode(password);
	}

	@Override
	public String encode(CharSequence charSequence) {
		try {
			String encrypt = null;
			if(currentEncrypt!=null){
				encrypt = this.currentEncrypt.encrypt(charSequence.toString());
			}else{
				encrypt = this.legacyMd5Encrypt.encrypt(charSequence.toString());
			}
			return encrypt;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new IllegalArgumentException("Cannot encode [" + charSequence + "]");
		}
	}

	@Override
	public boolean matches(CharSequence rawPass, String encPass) {
		String pass1 = "" + encPass;
		String pass2 = this.encode(rawPass);
		if(PasswordEncoderUtils.equals(pass1, pass2)){
			//如果第一次就匹配成功了，就直接返回true，否则使用legacyMd5Encrypt再match一次
			return true;
		}else{
			try {
				pass2 = this.legacyMd5Encrypt.encrypt(rawPass.toString());
				boolean match = PasswordEncoderUtils.equals(pass1, pass2);
				return match;
			} catch (Exception e) {
				return false;
			}

		}
	}

}
