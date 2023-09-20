package com.landray.kmss.sys.attachment.integrate.wps.authen;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.sys.authentication.ssoclient.Logger;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.sys.authentication.util.UUID;

public class WpsOfficeLRTokenGenerator extends WpsOfficeTokenGenerator {
	public static final String SECURITY_KEY_PRIVATE = "security.key.private";
	public static final String SECURITY_KEY_PUBLIC = "security.key.public";

	private static final String DATETIME_FORMAT = "yyyyMMddHHmmss";
	private static final int DATETIME_LENGTH = DATETIME_FORMAT.length();
	private static final int RANDOM_STRING_LENGTH = 32;
	private static final int KEY_SIZE = 1024;

	private static Log logger = LogFactory.getLog(WpsOfficeLRTokenGenerator.class);
	
	@Override
	protected void init(String publicKey, String privateKey) throws Exception {
		setPublicKey(publicKey);
		setPrivateKey(privateKey);
	}

	/**
	 * 私钥
	 */
	private String privateKey;

	private void setPrivateKey(String privateKey) throws Exception {
		if (StringUtil.isNull(privateKey)) {
			this.privateKey = null;
			decryptionCipher = null;
		} else {
			this.privateKey = privateKey;
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new PKCS8EncodedKeySpec(StringUtil
					.toBytes(privateKey));
			Key priKey = keyFactory.generatePrivate(keySpec);
			decryptionCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			decryptionCipher.init(Cipher.DECRYPT_MODE, priKey);
		}
	}

	/**
	 * 公钥
	 */
	private String publicKey;

	private void setPublicKey(String publicKey) throws Exception {
		if (StringUtil.isNull(publicKey)) {
			this.publicKey = null;
			encryptionCipher = null;
		} else {
			this.publicKey = publicKey;
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new X509EncodedKeySpec(StringUtil
					.toBytes(publicKey));
			Key pubKey = keyFactory.generatePublic(keySpec);
			encryptionCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			encryptionCipher.init(Cipher.ENCRYPT_MODE, pubKey);
		}
	}

	/**
	 * 随机生成公钥和私钥
	 * 
	 * @throws Exception
	 */
	public void generateKeys() throws Exception {
		Security
				.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(KEY_SIZE);
		KeyPair key = keyGen.generateKeyPair();
		setPublicKey(StringUtil.toHexString(key.getPublic().getEncoded()));
		setPrivateKey(StringUtil.toHexString(key.getPrivate().getEncoded()));
	}

	/**
	 * 解密器
	 */
	private Cipher decryptionCipher;

	/**
	 * 解密
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private synchronized byte[] decrypt(byte[] input) throws Exception {
		return decryptionCipher.doFinal(input);
	}

	/**
	 * 加密器
	 */
	private Cipher encryptionCipher;

	/**
	 * 加密
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	private synchronized byte[] encrypt(byte[] input) throws Exception {
		return encryptionCipher.doFinal(input);
	}

	@Override
	public WpsOfficeToken generateTokenByTokenString(String tokenString) {
		if (StringUtil.isNull(tokenString) || ("\"\"").equals(tokenString)) {
            return null;
        }
//		if (decryptionCipher == null) {
//			if (Logger.isDebugEnabled())
//				Logger.debug("解密器未产生，不能解密token信息。");
//			return null;
//		}
		try {
			byte[] cipherText = StringUtil.toBytes(tokenString);
			String tokenInfo = new String(decrypt(cipherText), "UTF-8");
			tokenInfo = tokenInfo.substring(0, tokenInfo.length()
					- RANDOM_STRING_LENGTH);
			String username = tokenInfo.substring(DATETIME_LENGTH, tokenInfo
					.length()
					- DATETIME_LENGTH);
			String expireStr = tokenInfo.substring(tokenInfo.length()
					- DATETIME_LENGTH);
			DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT0"));
			Date expireTime = dateFormat.parse(expireStr);
			if (Logger.isDebugEnabled()) {
				Logger.debug("成功获取Token信息，用户名：" + username + "，过期时间："
						+ expireTime);
			}
			return new WpsOfficeToken(expireTime.getTime(), username, tokenString);
		} catch (Exception e) {
			Logger.warn("解密Token信息发生错误：" + tokenString, e);
			return null;
		}
	}

	@Override
	public com.landray.kmss.sys.attachment.integrate.wps.authen.WpsOfficeToken generateTokenByUserName(String username) {
		if (StringUtil.isNull(username)) {
            return null;
        }
//		if (encryptionCipher == null) {
//			if (Logger.isDebugEnabled())
//				Logger.debug("加密器未产生，不能加密token信息。");
//			return null;
//		}
		try {
			//系统时间 + 用户名 + 过期时间 + UUID
			//例如20110222111133liyong20110222191133ADEDDERWSDFRYJHVXDYRTUHGJGFKJKAS
			//14 + 用户名长度 + 14 + 32
			username = username.trim();
			DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT0"));
			long time = System.currentTimeMillis();
			String tokenInfo = dateFormat.format(new Date(time));
			tokenInfo += username;
			time += getMaxAge() * 1000;
			tokenInfo += dateFormat.format(new Date(time));
			tokenInfo += UUID.randomUUID().toString().replaceAll("-", "");
			byte[] cipherText = tokenInfo.getBytes("UTF-8");
			String tokenString = StringUtil.toHexString(encrypt(cipherText));
			return new WpsOfficeToken(time, username, tokenString);
		} catch (Exception e) {
			Logger.warn("加密Token信息发生错误，用户名：" + username, e);
			return null;
		}
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	@Override
	protected String getKeyFileString() {
		String rtnVal = SECURITY_KEY_PUBLIC + "=" + publicKey + "\r\n";
		rtnVal += SECURITY_KEY_PRIVATE + "=" + privateKey;
		return rtnVal;
	}

	public static void main(String[] args) throws Exception {
//		LRTokenGenerator generator = new LRTokenGenerator();
//		generator.setCookieName("LRToken");
//		generator.setDomain("yezq.com");
//		generator.setMaxAge(12 * 3600);
//		generator.generateKeys();
//		generator.storeToKeyFile("d:/token");
//		System.out.println("已经生成密钥文件");
		
		String localServerName = "http://sdfsdf/asdf.jsp;sdf?sdf"; 
		if (localServerName.indexOf("?") > 0) {
			localServerName = localServerName.substring(0, localServerName
					.indexOf("?"));
		}
		if (localServerName.indexOf(";") > 0) {
			localServerName = localServerName.substring(0, localServerName
					.indexOf(";"));
		}
		logger.info(localServerName);
	}
}
