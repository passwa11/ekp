package com.landray.kmss.third.im.kk.util;

import java.util.Random;

import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class KKConfigUtil {

	/**
	 * 随机产生字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }
	
	/**
	 * 获取当前时间戳并用aec加密 (kk console加密使用)
	 * @return
	 * @throws Exception 
	 */
	public static String getCurrDateSign(String secretkey) throws Exception{
		String retStrFormatNowDate = String.valueOf(System.currentTimeMillis());
		String sign = null;
		IKkImConfigService kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
		String encryptType = kkImConfigService.getValuebyKey(KeyConstants.ENCRYP_TTYPE);
		if (StringUtil.isNotNull(encryptType) && "2".equals(encryptType)) {
			sign = AES.encrypt2str(retStrFormatNowDate, secretkey);
		} else {
			sign = AESTool.encryptAES(retStrFormatNowDate, secretkey);
		}
		return sign;
	}

	/**
	 * <p>kk serverj加密使用</p>
	 * @param serverjAuthId
	 * @param secretkey
	 * @return
	 * @author 孙佳
	 */
	public static String getKKCurrDateSign(String serverjAuthId, String secretkey) {
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String content = serverjAuthId + "|" + timestamp;
		String sign = AES.encrypt2str(content, secretkey);
		return sign;
	}

}
