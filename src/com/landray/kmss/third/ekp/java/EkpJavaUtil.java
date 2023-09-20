package com.landray.kmss.third.ekp.java;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;

public class EkpJavaUtil {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EkpJavaUtil.class);

	private static final String desKey = "kmssSysConfigKey";

	private static DESEncrypt des = null;

	public static DESEncrypt getDesEncrypt() throws Exception {
		if (des == null) {
			des = new DESEncrypt(desKey);
		}
		return des;
	}
	
	
	public static boolean isEncryped(String pass) {
		if (StringUtil.isNull(pass)) {
			return false;
		}
		try {
			getDesEncrypt().decryptString(pass);
			return true;
		} catch (IllegalBlockSizeException e) {
			return false;
		} catch (BadPaddingException e) {
			return false;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	public static void main(String[] args) {
		String s = EkpJavaUtil.desDecrypt("Pavy8LaXY1fPRK6TTEWSsw==");
		System.out.println(s);
		System.out.println(MD5Util.getMD5String(s));
	}

	public static String desDecrypt(String pass) {
		if (StringUtil.isNull(pass)) {
			return pass;
		}
		String passOri = null;
		try {
			passOri = getDesEncrypt().decryptString(pass);
			if (isMessyCode(passOri)) {
				return pass;
			}
			return passOri;
		} catch (IllegalBlockSizeException e) {
			logger.error("", e);
			return pass;
		} catch (BadPaddingException e) {
			logger.error("", e);
			return pass;
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return pass;
	}

	public static String desEncrypt(String pass) {
		if (StringUtil.isNull(pass)) {
			return pass;
		}
		try {
			return getDesEncrypt().encryptString(pass).replaceAll("\r", "");
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			logger.error("", e);
			e.printStackTrace();
		}
		return pass;
	}

	private static boolean isMessyCode(String strName) {
		try {
			Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
			Matcher m = p.matcher(strName);
			String after = m.replaceAll("");
			String temp = after.replaceAll("\\p{P}", "");
			char[] ch = temp.trim().toCharArray();

			int length = (ch != null) ? ch.length : 0;
			for (int i = 0; i < length; i++) {
				char c = ch[i];
				if (!Character.isLetterOrDigit(c)) {
					String str = "" + ch[i];
					if (!str.matches("[\u4e00-\u9fa5]+")) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
