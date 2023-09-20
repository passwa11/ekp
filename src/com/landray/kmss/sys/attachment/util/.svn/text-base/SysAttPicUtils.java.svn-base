package com.landray.kmss.sys.attachment.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;

import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.DbUtils;

public class SysAttPicUtils {

	public final static String r = "Landray@rfvghkliycb";

	private static final List<String> IMG_TYPES = new ArrayList<String>();

	static {
		IMG_TYPES.add("jpg");
		IMG_TYPES.add("jpeg");
		IMG_TYPES.add("ico");
		IMG_TYPES.add("bmp");
		IMG_TYPES.add("gif");
		IMG_TYPES.add("png");
		IMG_TYPES.add("tif");
	}

	/**
	 * 是否图片
	 */
	public static boolean isImageType(String type) {
		type = type.toLowerCase();
		for (String imgType : IMG_TYPES) {
			if (imgType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成含有随机盐的加密参数
	 */
	public static String generate(String key) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		key = md5Hex(key + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = key.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = key.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 校验加密参数是否正确
	 */
	public static boolean verify(String key, String md5) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = md5.charAt(i);
			cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
			cs2[i / 3] = md5.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(key + salt).equals(new String(cs1));
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	private static String md5Hex(String src) {
		try {
			src += SysAttPicUtils.r;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取图片预览url
	 * 
	 * @param request
	 * @param fdAttId
	 * @return
	 */
	public static String getPreviewUrl(HttpServletRequest request,
			String fdAttId) {
		String viewPicHref = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&open=1";
		if (MobileUtil.getClientType(request) >= 6) {
			long timestamp = DbUtils.getDbTimeMillis();
			viewPicHref = "/resource/pic/attachment.do?method=view";
			viewPicHref += "&t=" + String.valueOf(timestamp);
			viewPicHref += "&k=" + SysAttPicUtils
					.generate(String.valueOf(timestamp) + fdAttId);
		}
		viewPicHref += "&fdId=" + fdAttId;
		return viewPicHref;
	}

}
