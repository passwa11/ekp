package com.landray.kmss.sys.filestore.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Signature {
	/**
	 * 签名方法
	 * 
	 * @param params
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public static String getSignature(Map<String, String> params, String appId,
			String appSecret) throws Exception {
		String signKey = "signature";
		String appidKey = "appid";
		List<String> keys = new ArrayList();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			keys.add(entry.getKey());
		}
		// 将所有参数按key的升序排序
		Collections.sort(keys, new Comparator<String>() {
			@Override
            public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		// 构造签名的源字符串
		StringBuilder contents = new StringBuilder(appId);
		for (String key : keys) {
			if (key == signKey || key == appidKey) {
				continue;
			}
			contents.append(key).append(params.get(key));
			// System.out.println("key:" + key + ",value:" + params.get(key));
		}
		contents.append(appSecret);
		// System.out.println("appSecret======>>>>>>>>>>" + appSecret
		// + "===contents===" + contents);
		// 进行hmac sha1 签名
		byte[] bytes = genHMAC(contents.toString(), appSecret);
		String sign;
		try {
			sign = Base64.encodeBase64String(bytes);
			sign = URLEncoder.encode(sign, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("当前环境不支持UTF-8，请检查!", e);
		}
		// System.out.println("sign======>>>>>>>>>>" + sign);
		return sign;
	}
   

	public static byte[] genHMAC(String data, String key) throws Exception {
		try {
			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKeySpec signinKey = new SecretKeySpec(key.getBytes("utf-8"),
					"HmacSHA1");
			// 生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance("HmacSHA1");
			// 用给定密钥初始化 Mac 对象
			mac.init(signinKey);
			// 完成 Mac 操作
			byte[] rawHmac = mac.doFinal(data.getBytes("utf-8"));
			return rawHmac;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
