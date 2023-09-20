package com.landray.kmss.sys.portal.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 门户时间参数工具类
 * 
 * @author tanyouhao
 *
 */
public class PortletTimeUtil {
	public static final String MONTH ="month";//月
	public static final String SEASON ="season";//季
	public static final String HALF_YEAR ="halfYear";//半年
	public static final String YEAR="year";//年
	
	/**
	 * 时间返回之前的时间
	 * 
	 * @param scope 范围
	 * @return
	 */
	public static Date getDateByScope(String scope){
		Calendar now=Calendar.getInstance();
		if(scope.equals(MONTH)){
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-30);
		}else if(scope.equals(SEASON)){
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-120);
		}else if(scope.equals(HALF_YEAR)){
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-180);
		}else if(scope.equals(YEAR)){
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-365);
		}
		return now.getTime();
	}
}
