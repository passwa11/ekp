package com.landray.kmss.sys.filestore.scheduler.third.dianju.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名
 */
public class HmacSignSha {
	private static final Logger logger = LoggerFactory.getLogger(HmacSignSha.class);

	private final static String CHAR_SET = "UTF-8";
	private final static String HAMC_SHA1 = "HmacSHA1";
	/**
	* hmac_sha1 加密
	* @param content 内容
	* @param appKey 加密秘钥
	* @return 经过base64编码后的hmac
	*/
	public static String getHmacSignSha1(String content,String appKey){
		return getHmacSign(content, appKey, HAMC_SHA1);
	}
	/**
	* hmac+签名算法 加密
	* @param content 内容
	* @param key 加密秘钥
	* @param hamaAlgorithm hamc签名算法名称:例如HmacMD5,HmacSHA1,HmacSHA256
	* @return 经过base64编码后的hmac
	*/
	public static String getHmacSign(String content,String key,String hamaAlgorithm){
		byte[] result = null;
		try {
			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), hamaAlgorithm);
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance(hamaAlgorithm);
			// 用给定密钥初始化 Mac 对象
			mac.init(signinKey);
			// 完成 Mac 操作
			byte[] rawHmac;
			rawHmac = mac.doFinal(content.getBytes(CHAR_SET));
			// 此处使用base64加密，可根据自己项目内实现更改。
			result = Base64.encodeBase64(rawHmac);
		} catch (NoSuchAlgorithmException e) {
			logger.error("签名失败：{}", e);
		} catch (InvalidKeyException e) {
			logger.error("签名失败：{}", e);
		} catch (IllegalStateException | UnsupportedEncodingException e) {
			logger.error("签名失败：{}", e);
		}
		
		if (null != result) {
			return new String(result);
		} else {
			return null;
		}
	}
}
