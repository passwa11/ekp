package com.landray.kmss.sys.attachment.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.landray.kmss.sys.config.util.LicenseUtil;
import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;

public final class SysAttCryptUtil {

	private static final String securityKey = "kmssAttachmentKey";

	/**
	 * 根据附件相关信息，加密信息
	 * 
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String fileInfo) throws Exception {
		DESEncrypt dESEncrypt = new DESEncrypt(securityKey);
		String encodeStr = dESEncrypt.encryptString(fileInfo);
		return URLEncoder.encode(encodeStr, "UTF-8");
	}

	/**
	 * 根据密钥信息，解密
	 * 
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptKey) throws Exception {
		DESEncrypt dESEncrypt = new DESEncrypt(securityKey);
		encryptKey = URLDecoder.decode(encryptKey, "UTF-8");
		encryptKey = encryptKey.replace(' ', '+');
		return dESEncrypt.decryptString(encryptKey);
	}

	private static String licenseKey = null;

	private static String getLicenseKey() {

		if (StringUtil.isNotNull(licenseKey)) {
			return licenseKey;
		}

		licenseKey = StringUtil.isNull(LicenseUtil.get("license-customer-id"))
				? securityKey : MD5Util.getMD5String(
						String.valueOf(LicenseUtil.get("license-customer-id")));

		return licenseKey;

	}

	/**
	 * 根据附件相关信息，加密信息--加密key为licenseId
	 * 
	 * @param fileInfo
	 * @return
	 * @throws Exception
	 */
	public static String encryptByLicenseId(String fileInfo) throws Exception {

		DESEncrypt dESEncrypt = new DESEncrypt(getLicenseKey());
		String encodeStr = dESEncrypt.encryptString(fileInfo);
		return URLEncoder.encode(encodeStr, "UTF-8");

	}

	/**
	 * 根据密钥信息，解密--解密key为licenseId
	 * 
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByLicenseId(String encryptKey)
			throws Exception {

		DESEncrypt dESEncrypt = new DESEncrypt(getLicenseKey());
		encryptKey = URLDecoder.decode(encryptKey, "UTF-8");
		encryptKey = encryptKey.replace(' ', '+');
		return dESEncrypt.decryptString(encryptKey);
	}

}
