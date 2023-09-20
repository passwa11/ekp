package com.landray.kmss.fssc.config.util;

import java.util.List;
import java.util.Map;

public class FormDataUtil {

	public static List getDetailByKey(Map<String, Object> map,String detailName){
		List list=null;
		for (String key: map.keySet()) {
			if(detailName.equals(key)){
				Object obj=map.get(key);
				if(obj instanceof List){
					return (List)obj;
				}
			}
		}
		return list;
	}
	
	
	public static Object getValueByKey(Map<String, Object> map,String name){
		for (String key: map.keySet()) {
			if(name.equals(key)){
				return map.get(key);
			}
		}
		return null;
	}
	
}
