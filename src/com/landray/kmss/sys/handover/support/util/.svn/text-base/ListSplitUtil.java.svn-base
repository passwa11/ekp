package com.landray.kmss.sys.handover.support.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List工具类，拆分list
 * @author zqx
 *
 */
public class ListSplitUtil {
	
	public static final int MAX_LENGTH = 999;
	/**
	 * 按size将一个list拆分为若干个list
	 * @param list
	 * @param size
	 * @return
	 */
	public static List<List<String>> splitList(List<String> list,int size) {
		if(size == 0) {
            size = MAX_LENGTH;
        }
		int length = list.size();
		// 计算可以分成多少组
		int num = (length + size - 1) / size; 
		List<List<String>> newList = new ArrayList<>(num);
	    for (int i = 0; i < num; i++) {
	    	// 开始位置
	        int fromIndex = i * size;
	        // 结束位置
	        int toIndex = (i + 1) * size < length ? (i + 1) * size : length;
	        newList.add(list.subList(fromIndex, toIndex));
	    }
		return newList;
	}
}
