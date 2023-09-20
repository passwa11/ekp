package com.landray.kmss.km.forum.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import com.landray.kmss.util.StringUtil;

/**
 * 敏感词检测工具类
 * 
 * @author tanyouhao
 * @date 2014-8-19
 *
 */
public class SensitiveWordCheckUtil {
	private static Map containerWoekMap = null;
	public static String isNeedAcquire = "false";
	/**
	 * 初始化敏感词库
	 * 
	 * @param keys
	 * @return
	 */
	private static Map initKeyMap(String[] keys){
		if (containerWoekMap == null|| "true".equals(isNeedAcquire)) {
			containerWoekMap = new CaseInsensitiveMap(keys.length);
			String key = null;
			Map nowMap = null;
			Map<String, String> newWorMap = null;

			for (int i = 0; i < keys.length; i++) {
				key = keys[i]; // 关键字
				nowMap = containerWoekMap;
				for (int j = 0; j < key.length(); j++) {
					char keyChar = key.charAt(j);
					Object wordMap = nowMap.get(keyChar);

					if (wordMap != null) {
						nowMap = (Map) wordMap;
					} else { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
						newWorMap = new CaseInsensitiveMap();
						newWorMap.put("isEnd", "0"); // 不是最后一个
						nowMap.put(keyChar, newWorMap);
						nowMap = newWorMap;
					}

					if (j == key.length() - 1) {
						nowMap.put("isEnd", "1"); // 最后一个
					}
				}
			}

		}
		return containerWoekMap;
	}
	
	/**
	 * 检测是否含有敏感词
	 * 
	 * @param txt
	 *             待检测文本
	 * @param keys
	 *             敏感词
	 * @return true 含有 false 不含有
	 */
	 public static boolean CheckSensitiveWord(String txt,String[] keys){  
		    txt = txt.replace(" ", "");
	        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况  
	        int matchFlag = 0;     //匹配标识数默认为0  
	        char word = 0;  
	        Map keysMap = initKeyMap(keys);
	        for(int i = 0; i < txt.length() ; i++){  
	            word = txt.charAt(i);  
	            keysMap = (Map) keysMap.get(word);     //获取指定key  
	            if(keysMap != null){     //存在，则判断是否为最后一个  
	                matchFlag++;     //找到相应key，匹配标识+1   
	                if("1".equals(keysMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数  
	                    flag = true;       //结束标志位为true     
	                    break;  
	                }  
	            }  
	            else{   
	            	//不存在，直接返回  
	            	keysMap = initKeyMap(keys);
	                continue;  
	            }  
	        }  
	        return flag;  
	    }  
	 
	 /**
	 * 检测文本中敏感词
	 * 
	 * @param txt
	 *            待检测文本
	 * @param keys
	 *            敏感词
	 * @return 文本中的所有敏感词
	 */
	 public static List<String> getSensitiveWord(String txt,String[] keys){ 
		txt = txt.replace(" ", "");
	    List<String> senWordsList = new ArrayList<String>(); // 所含敏感词集合
		String senWord = "";//敏感词
		int matchFlag = 0; // 匹配标识
		char word = 0;
		Map keysMap = initKeyMap(keys);
		for (int i = 0; i < txt.length(); i++) {
			word = txt.charAt(i);
			keysMap = (Map) keysMap.get(word); // 获取指定key
			if (keysMap != null) {// 存在，则判断是否为最后一个
				senWord += String.valueOf(word);
				matchFlag++; // 找到相应key，匹配标识+1
				if ("1".equals(keysMap.get("isEnd"))) { // 如果为最后一个匹配规则,结束循环，返回匹配标识数
					if(!senWordsList.contains(senWord)){//去重
						senWordsList.add(senWord);
					}
					senWord = "";
					continue;
				}
			} else {
				// 不存在，直接返回
				senWord = ""; 
				keysMap = initKeyMap(keys);
				continue;
			}
		}
		return senWordsList;
	}  
	 
	 public static void main(String[] args) {
		String[] keys = new String[]{"我草"};
		String txt = "描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的全部日志。描述：采集任务每次执行时，采集日志的上限设置。设置为0，则是采集服务器指定时间戳之后的fuck我 草全部日志";
		boolean isHas = SensitiveWordCheckUtil.CheckSensitiveWord(txt, keys);
		System.out.println("是否含有:" + isHas);
		List<String> senWords = SensitiveWordCheckUtil.getSensitiveWord(txt, keys);
	    System.out.println(senWords);
	}
	
	 /**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtil.isNull(source)) {
			return source;
		}
		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 *            需要判断的字符串
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isNotEmojiCharacter(codePoint)) {
				// 判断确认有表情字符
				return true;
			}
		}
		return false;
	}

	/**
	 * 非emoji表情字符判断
	 * 
	 * @param codePoint
	 * @return
	 */
	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}
}
