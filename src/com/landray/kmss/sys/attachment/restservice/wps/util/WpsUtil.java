package com.landray.kmss.sys.attachment.restservice.wps.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.SignUtil;
import org.apache.commons.codec.binary.Base64;

import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

public class WpsUtil {
	public static String getSignature(Map<String, String> params, String appSecret)
			throws Exception {
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
		StringBuilder contents = new StringBuilder("");
		for (String key : keys) {
			if ("_w_signature".equals(key)) {
				continue;
			}
			contents.append(key + "=").append(params.get(key));
			// System.out.println("key:" + key + ",value:" + params.get(key));
		}
		contents.append("_w_secretkey=").append(appSecret);

		// 进行hmac sha1 签名验证通过可以免登录下载
		byte[] bytes = genHMAC(contents.toString(), appSecret);

		// 字符串经过Base64编码
		String sign = new String(Base64.encodeBase64(bytes));
		// String sign = encodeBase64String(bytes);
		try {
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sign;
	}

	public static Map<String, String> paramToMap(String paramStr) {
		String[] params = paramStr.split("&");
		Map<String, String> resMap = new HashMap<String, String>();
		for (int i = 0; i < params.length; i++) {
			String[] param = params[i].split("=");
			if (param.length >= 2) {
				String key = param[0];
				String value = param[1];
				for (int j = 2; j < param.length; j++) {
					value += "=" + param[j];
				}
				resMap.put(key, value);
			}
		}
		return resMap;
	}

	public static byte[] genHMAC(String data, String key) throws Exception {
		try {
			// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKeySpec signinKey = new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA1");
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



	public static boolean isWord(String fileExt) {
		if (StringUtil.isNotNull(fileExt)) {
			String typeStr = "doc,dot,wps,wpt,docx,dotx,docm,dotm";
			typeStr = typeStr.replaceAll(" ", "");
			String[] typeArr = typeStr.split(",");
			List typeList = Arrays.asList(typeArr);
			if (typeList.contains(fileExt)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isExcel(String fileExt) {
		if (StringUtil.isNotNull(fileExt)) {
			String typeStr = "xls,xlt,et,xlsx,xltx,csv,xlsm,xltm";
			typeStr = typeStr.replaceAll(" ", "");
			String[] typeArr = typeStr.split(",");
			List typeList = Arrays.asList(typeArr);
			if (typeList.contains(fileExt)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPpt(String fileExt) {
		if (StringUtil.isNotNull(fileExt)) {
			String typeStr = "ppt,pptx,pptm,ppsx,ppsm,pps,potx,potm,dpt";
			typeStr = typeStr.replaceAll(" ", "");
			String[] typeArr = typeStr.split(",");
			List typeList = Arrays.asList(typeArr);
			if (typeList.contains(fileExt)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPdf(String fileExt) {
		if (StringUtil.isNotNull(fileExt) && "pdf".equals(fileExt.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 参数签名
	 * @param value
	 * @param key
	 * @return
	 */
	public static String sign(String value, String key) {
		String signStr = value + "::" + key;
		return MD5Util.getMD5String(signStr);
	}

	/**
	 * 用户操作权限签名
	 * @param map
	 * @return
	 */
	public static String generateUserCtlSignature(Map<String,String> map) {
		if (map == null) {
			return null;
		}
		String userId = map.get("_w_userid");
		//复制 1：有权限 0：无权限
		String copy = map.get("_w_copy");
		//导出 PDF 1：有权限 0：无权限
		String export = map.get("_w_export");
		//打印 1：有权限 0：无权限
		String print = map.get("_w_print");
		String value = copy + "##" + export + "##" + print;
		return sign(value, userId);
	}

	/**
	 * 校验参数签名
	 * @param value
	 * @param key
	 * @param signature
	 * @return
	 */
	public static boolean checkSignature(String value,String key,String signature){
		String sign = sign(value, key);
		if (StringUtils.equals(sign, signature)) {
			return true;
		}
		return false;
	}

	public static String MD5_32(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte[] b = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
                    i += 256;
                }
				if (i < 16) {
                    buf.append("0");
                }
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}
