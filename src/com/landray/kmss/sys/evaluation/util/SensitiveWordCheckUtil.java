package com.landray.kmss.sys.evaluation.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * 敏感词检测工具类
 * @author 
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
	public static int CheckSensitiveWord(String txt, int beginIndex,
			String[] keys) {
		boolean flag = false; // 敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0; // 匹配标识数默认为0
		char word = 0;
		Map nowMap = initKeyMap(keys);
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word); // 获取指定key
			if (nowMap != null) { // 存在，则判断是否为最后一个
				matchFlag++; // 找到相应key，匹配标识+1
				if ("1".equals(nowMap.get("isEnd"))) { // 如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true; // 结束标志位为true
				}
			} else { // 不存在，直接返回
				break;
			}
		}
		if (matchFlag < 1 || !flag) { // 长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
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
	public static Set<String> getSensitiveWord(String txt, String[] keys) {
		Set<String> sensitiveWordSet = new HashSet<String>();
		txt = txt.replace(" ", "");
		for (int i = 0; i < txt.length(); i++) {
			int length = CheckSensitiveWord(txt, i, keys); // 判断是否包含敏感字符
			if (length > 0) { // 存在,加入list中
				sensitiveWordSet.add(txt.substring(i, i + length));
				i = i + length - 1; // 减1的原因，是因为for会自增
			}
		}

		return sensitiveWordSet;
	}
	 public static void main(String[] args) {
		String[] keys = new String[] { "妈的" };
		String txt = "描述：采集任务每次执行时，敏感词采集日志的上限设置妈妈的";
		Set<String> senWords = SensitiveWordCheckUtil.getSensitiveWord(txt,
				keys);
		    System.out.println(senWords);
	}
}
