package com.landray.kmss.sys.restservice.server.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;

import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;

/**
 * 辅助工具类
 * 
 * @author  
 */
public class SysRsUtil {

	/**
	 * 简单的时间运算
	 * 
	 * @param calField
	 *            日历字段
	 * @param num
	 *            时间差值
	 * @return 时间结果
	 */
	public static Date getTime(int calField, int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(calField, num);

		return cal.getTime();
	}

	/**
	 * 校验IP是否存在于指定的字符串中
	 */
	public static boolean isExistedIp(String ip, String allIpStr) {
		if (StringUtil.isNull(ip) || StringUtil.isNull(allIpStr)) {
			return true;
		}

		String[] arr = ip.split("\\.");
		if (arr == null || arr.length < 1) {
            return false;
        }
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]).append(".").append(arr[1]).append(".").append(arr[2]).append(".*");
		String p1 = sb.toString();

		sb = new StringBuilder();
		sb.append(arr[0]).append(".").append(arr[1]).append(".*.*");
		String p2 = sb.toString();

		if (allIpStr.contains(ip) || allIpStr.contains(p1) || allIpStr.contains(p2)) {
			return true;
		}

		return false;
	}

	/**
	 * 使用消息摘要加密口令
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptPwd(String password) throws Exception {
		String cipherText = null;

		if (StringUtil.isNotNull(password)) {
			byte[] pBytes = password.getBytes("UTF8");
			cipherText = MD5Util.getMD5String(pBytes);
		}

		return cipherText;
	}

	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		String exceptionStack = null;

		if (t != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);

			try {
				t.printStackTrace(pw);
				exceptionStack = sw.toString();
			} finally {
				try {
					sw.close();
					pw.close();
				} catch (IOException e) {
				}
			}
		}

		return exceptionStack;
	}
}
