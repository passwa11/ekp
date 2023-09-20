package com.landray.kmss.elec.device.util;

import java.util.HashMap;
import java.util.Map;

/**
*@author yucf
*@date  2019年7月14日
*@Description                渠道参数上下文
*/

public abstract class ElecChannelContextUtil {

	//当前操作上下文数据传递
	private static ThreadLocal<Map<String,Object>> context = new ThreadLocal<Map<String,Object>>(){
		   @Override
           protected synchronized Map<String, Object> initialValue() {
               return new HashMap<String, Object>();
       }
	};
	
	/**
	 *   参数值放入上下文
	 * @param key          参数key
	 * @param obj          参数值
	 */
	public static void put(String key, Object obj){
		context.get().put(key, obj);
	}
	
	/**
	 *  取上下文某一参数值
	 * @param key          参数key
	 * @return
	 */
	public static Object get(String key){
		return context.get().get(key);
	}
	
	/**
	 *  去除上下文某一参数
	 * @param key
	 * @return
	 */
	public static Object remove(String key){
		return context.get().remove(key);
	}
	
	/**
	 * 清除上下文所有参数
	 */
	public static void clear() {
		context.get().clear();
	}
}
