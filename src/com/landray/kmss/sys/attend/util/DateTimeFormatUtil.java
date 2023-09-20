package com.landray.kmss.sys.attend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;



/**
 * 特定的日期、时间格式解析类 具体见main方法示例
 *
 * {name:'二〇一二年三月四日',value:'$CN$yyyy'年'M'月'd'日''},
 * {name:'二〇一二年三月',value:'$CN$yyyy'年'M'月''}, {name:'三月四日',value:'$CN$M'月'd'日''},
 * {name:'周三',value:'$CN$'周'E'}, {name:'M',value:'MMM(1)'},
 * {name:'M-12',value:'MMM(1)-yy'}
 * 
 *
 */



public class DateTimeFormatUtil {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(DateTimeFormatUtil.class);
	
	public Date getDate()
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return calendar.getTime();
	}
	
	/**
	 * 获得相应日期时间格式的时间
	 * 首先获取特殊定义格式的时间  含$,(
	 * 然后获得标准格式时间
	 * 
	 * @param str
	 * @return
	 */
	public String getDateTime(String str) {
		return getDateTime(null, str);
	}

	public String getDateTime(Date date, String str) {
		String returnValue="-1";
		if(str.indexOf("$")!=-1 || str.indexOf("(")!=-1)
		{
			returnValue=getDateTimeCn(str);
			if(!"-1".equals(returnValue))
			{
				return returnValue;
			}
		}
		SimpleDateFormat sDateFormat=new SimpleDateFormat(str,Locale.ENGLISH);
		sDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (date == null) {
			date = getDate();
		}
		returnValue = sDateFormat.format(date);
		if(returnValue.indexOf("时")!=-1 && (returnValue.indexOf("AM")!=-1 || returnValue.indexOf("PM")!=-1))
		{
			returnValue=returnValue.replace("AM", "上午");
			returnValue=returnValue.replace("PM", "下午");
		}
		if("E".equals(str))
		{
			sDateFormat=new SimpleDateFormat(str,Locale.CHINA);
			sDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			returnValue = sDateFormat.format(date);
		}
		return returnValue;
	}
	
	/**
	 * 获取日期数字
	 * @param date
	 * @return
	 */
	public String getDateTimeNuber(Date date) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		int week_index = c.get(Calendar.DAY_OF_WEEK);
		if(week_index<0){
			week_index = 0;
		}
		return String.valueOf(week_index);
	}

	/**
	 * 转换特定的中国国情日期时间格式
	 * @param str
	 * @return
	 */
	private String getDateTimeCn(String str)
	{
		if(str.indexOf("$CN$")!=-1)
		{
			str=str.substring(4);
			SimpleDateFormat sDateFormat=new SimpleDateFormat(str,Locale.CHINA);
			sDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String dateString=sDateFormat.format(new Date());
			if("'周'E".equals(str))
			{
				return getNumberCn(dateString.substring(0, 1)+dateString.substring(3));
			}
			return getNumberCn(dateString);
		}
		else if(str.indexOf("(")!=-1)
		{
			SimpleDateFormat sDateFormatMMM=new SimpleDateFormat("MMM",Locale.ENGLISH);
			sDateFormatMMM.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String sMMM=sDateFormatMMM.format(getDate());
			SimpleDateFormat sDateFormatyy=new SimpleDateFormat("yy",Locale.CHINESE);
			sDateFormatyy.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String syy=sDateFormatyy.format(getDate());
			if("MMM(1)".equals(str))
			{
				return sMMM.substring(0,1);
			}
			else if("MMM(1)-yy".equals(str))
			{
				return sMMM.substring(0,1).concat("-").concat(syy);
			}
		}
		return "-1";
	}
	
	/**
	 * 将字符串中的所有的数字转为大写数字 返回
	 * @param str
	 * @return
	 */
	private String getNumberCn(String str)
	{
		StringBuffer newString=new StringBuffer();
		int i=0;
		StringBuffer tempStr=new StringBuffer();
		for(char chr:str.toCharArray())
		{
		      if(chr>=48 && chr<=57)
		      {
		    	  i++;
		    	  tempStr.append(chr);
		    	  
		      }
		      else 
		      {
		    	  getNumberCn_call(i,tempStr,newString);
		    	  tempStr=new StringBuffer();
		    	  i=0;
		      	  newString.append(chr);
		      }
		}
		getNumberCn_call(i,tempStr,newString);
		return newString.toString();
	}
	
	private void getNumberCn_call(int i,StringBuffer tempStr,StringBuffer newString)
	{
		if (i >= 1 && i < 4) {
			int num = Integer.parseInt(tempStr.toString());
			newString.append(convertNumberToCn(num));
		} else if (i == 4) {
			for (char c : tempStr.toString().toCharArray()) {
				int num = Character.getNumericValue(c);
				newString.append(convertNumberToCn(num));
			}
		}
	  
	}
	
	/**
	 * 将单个的数字转为大写的数字返回
	 * @param s
	 * @return
	 */
	public String convertNumberToCn(int num)
	{
		String numCn="";
		switch (num) {
		case 1:
			numCn= "一";
			break;
		case 2:
			numCn= "二";
			break;
		case 3:
			numCn= "三";
			break;
		case 4:
			numCn= "四";
			break;
		case 5:
			numCn= "五";
			break;
		case 6:
			numCn= "六";
			break;
		case 7:
			numCn= "七";
			break;
		case 8:
			numCn= "八";
			break;
		case 9:
			numCn= "九";
			break;
		case 0:
			numCn= "〇";
			break;
		
		}
		if("".equals(numCn))
		{
			if(num==10)
			{
				numCn="十";
			}
			else if(num>10)
			{
				numCn=((num/10>1)?
						(convertNumberToCn(num/10)):"")+"十"+convertNumberToCn(num%10);
			}
		}
		return numCn;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DateTimeFormatUtil dtfnCn=new DateTimeFormatUtil();
		logger.info("周三"+"   --:--  "+dtfnCn.getDateTime("$CN$'周'E"));
		logger.info("二〇一二年三月四日" + "   --:--  "
				+ dtfnCn.getDateTime("yyyy'年'M'月'd'日'"));
		logger.info("三月四日"+"   --:--  "+dtfnCn.getDateTime("$CN$M'月'd'日'"));
		logger.info("01:30:55 PM"+"   --:--  "+dtfnCn.getDateTime("hh:mm:ss a"));
		logger.info("M"+"   --:--  "+dtfnCn.getDateTime("MMM(1)"));
		logger.info("M-12"+"   --:--  "+dtfnCn.getDateTime("MMM(1)-yy"));
		logger.info("04 March 2012"+"   --:--  "+dtfnCn.getDateTime("dd MMMMM yyyy"));
		logger.info("Mar-12"+"   --:--  "+dtfnCn.getDateTime("MMM-yy"));
		logger.info("March-12"+"   --:--  "+dtfnCn.getDateTime("MMMMM-yy"));
		
		logger.info("2012-3-14 1:30 PM"+"   --:--  "+dtfnCn.getDateTime("yyyy-M-dd H:m a"));
		logger.info("2012-3-14 13:30"+"   --:--  "+dtfnCn.getDateTime("yyyy-M-dd HH:mm"));
		logger.info("04-Mar-12"+"   --:--  "+dtfnCn.getDateTime("dd-MMM-yy"));
		logger.info("04 March 2012"+"   --:--  "+dtfnCn.getDateTime("dd MMMMM yyyy"));
		logger.info("3-14-12 1:30 PM"+"   --:--  "+dtfnCn.getDateTime("M-dd-yy H:m a"));
		logger.info("14-Mar-2012"+"   --:--  "+dtfnCn.getDateTime("dd-MMM-yyyy"));
		
		logger.info("13时30分55秒"+"   --:--  "+dtfnCn.getDateTime("HH'时'mm'分'ss'秒'"));
		logger.info("下午1时30分"+"   --:--  "+dtfnCn.getDateTime("ah'时'mm'分'"));
		logger.info("下午1时30分55秒"+"   --:--  "+dtfnCn.getDateTime("ah'时'mm'分'ss'秒'"));
		logger.info("十三时三十分"+"   --:--  "+dtfnCn.getDateTime("$CN$HH'时'mm'分'"));
		logger.info("下午一时三十分"+"   --:--  "+dtfnCn.getDateTime("$CN$ah'时'mm'分'"));
		logger.info("1:30:55 PM"+"   --:--  "+dtfnCn.getDateTime("h:mm:ss a"));
		logger.info("30:55.2"+"   --:--  "+dtfnCn.getDateTime("mm:ss.SSS"));
		logger.info("3-14-01 1:30 PM"+"   --:--  "+dtfnCn.getDateTime("M-dd-yy h:mm a"));
		logger.info("3-14-01 13:30"+"   --:--  "+dtfnCn.getDateTime("M-dd-yy HH:mm"));
		logger.info("yyyy-M-dd H:m a"+"   --:--  "+dtfnCn.getDateTime("yyyy-M-dd H:m a"));
		logger.info("E"+"   --:--  "+dtfnCn.getDateTime("E"));
		
		logger.info("TimeZoneDefaultId:"+TimeZone.getDefault().getID());
		logger.info("---------------------");
		
		logger.info("defaultDate:"+dtfnCn.getDate().toLocaleString());
		try {
			logger.info("1111defaultDate:"+ dtfnCn.getDateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2022-10-29")), "yyyy-M-dd HH:mm"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
