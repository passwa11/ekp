package com.landray.kmss.util;

import com.github.promeg.pinyinhelper.Pinyin;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;

/**
 * @author yezhengping
 *	用于tinypinyin的调用
 */
public class KmssPinyinUtil {

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String hanyu = "单u芳";
//		System.out.println(ChinesePinyinComparator.getPinyinString(hanyu));
//		System.out.println(Pinyin.toPinyin(hanyu, ""));
//		String pinyin = Pinyin.toPinyin(hanyu, "");
//		System.out.println(toPinyin(hanyu,"",true));
//	}

	/**
	 * 按照原来的转换逻辑转换拼音
	 * 
	 * @param c
	 * @param isLowercase
	 * @return
	 */
	public static String toPinyin(char c,boolean isLowercase){

		String pinyin = Pinyin.toPinyin(c);
		if(pinyin != null && isLowercase) {
            return pinyin.toLowerCase();
        } else {
            return pinyin;
        }

	}
	
	/**
	 * 按照原来的转换逻辑转换拼音
	 * 
	 * @param c
	 * @param isLowercase
	 * @return
	 */
	public static String toPinyin(String str,String speartor,boolean isLowercase){

		String pinyin = Pinyin.toPinyin(str, speartor);
		if(pinyin != null && isLowercase) {
            return pinyin.toLowerCase();
        } else {
            return pinyin;
        }

	}
}
