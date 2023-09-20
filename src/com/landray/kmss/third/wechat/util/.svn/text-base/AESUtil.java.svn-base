package com.landray.kmss.third.wechat.util;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

public class AESUtil {
	private final static String encoding = "UTF-8";
	private final static byte[] iv = { 0x38, 0x37, 0x36, 0x30, 0x34, 0x33,
			0x32, 0x37, 0x38, 0x37, 0x36, 0x35, 0x31, 0x33, 0x32, 0x31 };

// 解密license方法
	public static String reCryptograph(String strCryptograph) {
		String strReturn = new String();
		int intTemp;
		int intCrypt;
		String strTemp = new String();
		intTemp = (int) strCryptograph.charAt(strCryptograph.length() - 1);
		strTemp = strCryptograph.substring(0, strCryptograph.length() - 1);
		for (int i = 0; i < strTemp.length(); i++) {
			intCrypt = (int) strTemp.charAt(i);
			intCrypt = intCrypt ^ (intTemp % 32);
			strReturn += (char) intCrypt;
		}
		StringBuffer strRe = new StringBuffer(strReturn);
		strRe = strRe.reverse();
		strReturn = new String(strRe);
		return strReturn;
	}
	/**
	 * 
	 * @param content
	 *            需要加密的内容
	 * @return 密文
	 * @throws Exception
	 */
	public static synchronized String encrypt(String content, String theKey)
			throws Exception {
		byte[] sendBytes = null;
		try {
			sendBytes = theKey.getBytes(encoding);
			content = URLEncoder.encode(content, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));
		engine
				.init(true, new ParametersWithIV(new KeyParameter(sendBytes),
						iv));
		byte[] enc = new byte[engine.getOutputSize(content.getBytes().length)];
		int size1 = engine.processBytes(content.getBytes(), 0, content
				.getBytes().length, enc, 0);
		int size2 = engine.doFinal(enc, size1);
		byte[] encryptedContent = new byte[size1 + size2];
		System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
		String key = new String(Hex.encode(encryptedContent));
		return key;
	}

	/**
	 * 
	 * @param Key
	 *            密文
	 * @return 明文
	 * @throws Exception
	 */
	public static synchronized String decrypt(String Key, String theKey) throws Exception {
		byte[] sendBytes = null;
		try {
			sendBytes = theKey.getBytes(encoding);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		byte[] encryptedContent = hex2byte(Key);
		BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
				new CBCBlockCipher(new AESFastEngine()));

		engine.init(false,
				new ParametersWithIV(new KeyParameter(sendBytes), iv));
		byte[] dec = new byte[engine.getOutputSize(encryptedContent.length)];
		int size1 = engine.processBytes(encryptedContent, 0,
				encryptedContent.length, dec, 0);
		int size2 = engine.doFinal(dec, size1);
		byte[] decryptedContent = new byte[size1 + size2];
		System.arraycopy(dec, 0, decryptedContent, 0, decryptedContent.length);
		String content = new String(new String(decryptedContent));
		content = URLDecoder.decode(content, "utf-8");
		return content;
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
/*
		String content = "偶!@sdgse只是大概vs俄大概v俄大概v俄大概regsrergsegrege#$%^&*()~<>:{}|}?.';]\\dan";
		String encryptStr = null;
		try {
			encryptStr = encrypt(content, "12dc125f000db610cab6bfe4b0dae71c");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("加密后=" + encryptStr);

		String decryptStr = null;
		try {
			decryptStr = decrypt(encryptStr, "12dc125f000db610cab6bfe4b0dae71c");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("解密后=" + decryptStr);*/
		
//		String temp="9c9fbb34b83fe1155d981122b4e553cd46c9ab0a567e4f01115aeb9ef8587a163399888425ede5bb03b0daabb6fbd5cd3977845bc48b6d20e9319a178aca35ef1f1dabc66aeed1d79f9fddb82602e7c6c3ca883913b81386b62a5cc145b41adfa29f7ce7c51e587b363ac53314c292875e103ae6cbaf4451810b74f7223a5af13e8307d1c83712f0071a2fd18ea4131e97d7d2720104a6e5331f0ab2138542bc";
		String temp="9c9fbb34b83fe1155d981122b4e553cd46c9ab0a567e4f01115aeb9ef8587a163399888425ede5bb03b0daabb6fbd5cd3977845bc48b6d20e9319a178aca35ef1f1dabc66aeed1d79f9fddb82602e7c6c3ca883913b81386b62a5cc145b41adfa29f7ce7c51e587b363ac53314c292875e103ae6cbaf4451810b74f7223a5af13e8307d1c83712f0071a2fd18ea4131e97d7d2720104a6e5331f0ab2138542bc";
		String license="147c858431693d430077b1f49718c436";
		String randomCode="7B2834E92FE045E8957A32A81E0840E9";
		String aesKey = license.substring(0, license.length() / 2)+ randomCode.substring(0, randomCode.length() / 2);
		String key="";
		try {
			key = AESUtil.decrypt(temp, aesKey);
			System.out.println("解密后数据======================");
			System.out.println(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
