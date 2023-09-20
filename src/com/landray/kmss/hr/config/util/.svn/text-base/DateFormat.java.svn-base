package com.landray.kmss.hr.config.util;

import java.util.Calendar;
import java.util.Date;

public class DateFormat {
	   /**
	 	 * 获取下个月N日5.31分 毫秒數
	 	 * @param startDate
	 	 * @param workDay
	 	 * @return
	 	 */
	    public static Long getLastNDay(Date fdStartTime, int n) {
			Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
			ca.setTime(fdStartTime);
			// 申请时间下个月4日17:31
			ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH)+1, n, 0, 0, 0);
			long set_close = ca.getTimeInMillis();
			return set_close;
		}
	    
	    /**
	 	 * 获取指定日期后N个工作日的时间的毫秒数
	 	 * @param startDate
	 	 * @param workDay
	 	 * @return
	 	 */
	    public static Long getWorkDay(Date startDate, int workDay) {
	        Calendar c1 = Calendar.getInstance();
	        c1.setTime(startDate);
	        for (int i = 0; i < workDay; i++) {
	            c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
	            if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) 
	            	|| Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
	                workDay++;
	                continue;
	            }
	        }
	        c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DATE), 0, 0, 0);
	        return c1.getTimeInMillis();
	    }
	    
	    
	    public static boolean isWeekend(Date date) throws Exception {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	            return true;
	        } else{
	            return false;
	        }
	 
	 }
}
