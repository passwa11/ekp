package com.landray.kmss.sys.mportal.util;

import org.apache.commons.lang3.StringUtils;

import com.landray.kmss.sys.mportal.model.SysMportalTypeConfig;

public class SysMportalConfigUtil {
	
	/**
	 * 获取门户类型
	 * 0:简单门户
	 * 1:复合门户
	 * @return
	 */
	public static Integer getSysMportalType() {		
		try {	
			SysMportalTypeConfig sysMportalTypeConfig = new SysMportalTypeConfig();
			String sysMportalType = (String) sysMportalTypeConfig.getDataMap().get("sysMportalType");
			if(StringUtils.isNoneBlank(sysMportalType)) {
				Integer type = Integer.valueOf(sysMportalType);
				return type;
			}	
			return SysMportalConstant.MPORTAL_TYPE_SIMPLE;
		}catch(Exception e) {
			return SysMportalConstant.MPORTAL_TYPE_SIMPLE;
		}		
	}

}
