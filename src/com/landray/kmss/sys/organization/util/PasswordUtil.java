package com.landray.kmss.sys.organization.util;

import org.apache.commons.codec.binary.Base64;

import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.StringUtil;

public class PasswordUtil {
	private static int CharMode(int iN) {
		if (iN >= 48 && iN <= 57)// 数字
        {
            return 1;
        }
		if (iN >= 65 && iN <= 90)// 大写字母
        {
            return 2;
        }
		if (iN >= 97 && iN <= 122)// 小写
        {
            return 4;
        } else {
            return 8; // 特殊字符
        }
	}

	// bitTotal函数
	// 计算出当前密码当中一共有多少种模式
	private static int bitTotal(int num) {
		int modes = 0;
		for (int i = 0; i < 4; i++) {
			if ((num & 1) == 1) {
                modes++;
            }
			num >>>= 1;
		}
		return modes;
	}

	// checkStrong函数
	// 返回密码的强度级别
	private static int checkPasswdRate(String sPW) {
		if (sPW == null || sPW.length() <= 0) {
            return 0;
        }
		// 密码太短
		int Modes = 0;
		char[] sss = sPW.toCharArray();
		for (int i = 0; i < sss.length; i++) {
			// 测试每一个字符的类别并统计一共有多少种模式.
			Modes |= CharMode(sss[i]);
		}
		return bitTotal(Modes);
	}

	// 返回值
	// 密码组成元素：数字，大写，符号，字符
	// 0. 传入的字符为空
	// 1. 密码长度大于6位, 只有一种组合.
	// 2. 密码长度大于6位, 且有两种组合.
	// 3. 密码长度大于6位, 且有三种组合.
	// 4. 密码长度大于6位, 且有四种组合.
	public static int pwdStrength(String str) {
		return checkPasswdRate(str);
	}

	public static String desDecrypt(String text) {
		if (StringUtil.isNull(text)) {
			return text;
		}
		try {
			DESEncrypt des = new DESEncrypt("kmssPropertiesKey");
			return des.decryptString(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new String(Base64.decodeBase64(text.getBytes()));
		}
	}

	public static String desEncrypt(String text) {
		if (StringUtil.isNull(text)) {
			return text;
		}
		// try {
		// text = new String(Base64.decodeBase64(text.getBytes()));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			DESEncrypt des = new DESEncrypt("kmssPropertiesKey");
			return des.encryptString(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

	public static void main(String[] args) {
		String s = "0iSZDqd3sTlVDBDCT8+fE0SqKWAhoETfGClMFBpdq4lJlU6Sgvo3c27mPEbC8A9v";
		System.out.println(desDecrypt(s));
	}
}
