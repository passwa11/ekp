package com.landray.kmss.eop.basedata.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 常用的数据操作方法。
 * 
 * @author 
 * @version 1.0 2018-09-07
 */
public class EopBasedataNumberUtil {
	/**
	 * 判断两个浮点数是否相等
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isEqual(double d1,double d2){
		return Math.abs(d1-d2)<0.0000001d;
	}
	/**
	 * 获得两数相加的结果,保留n位小数
	 * @param firstDouble
	 * @param secondDouble
	 * @return double
	 */
	public static double getAddition(double firstDouble,double secondDouble,int n) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));
		return firstBigDecimal.add(secondBigDecimal).setScale(n, RoundingMode.HALF_UP).doubleValue();
	}
	/**
	 * 获得两数相加的结果
	 * @param firstDouble
	 * @param secondDouble
	 * @return double
	 */
	public static double getAddition(double firstDouble,double secondDouble) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));;
		return firstBigDecimal.add(secondBigDecimal).doubleValue();
	}
	
	/**
	 * 获得两数相减的结果
	 * @param firstDouble
	 * @param secondDouble
	 * @param n
	 * @return double
	 */
	public static double getSubtraction(double firstDouble,double secondDouble,int n) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));  //直接new BigDecimal(double)会多出很多位小数导致精度偏差0.01
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));
		return firstBigDecimal.subtract(secondBigDecimal).setScale(n, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * 获得两数相减的结果
	 * @param firstDouble
	 * @param secondDouble
	 * @param n
	 * @return double
	 */
	public static double getSubtraction(double firstDouble,double secondDouble) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble)); //直接new BigDecimal(double)会多出很多位小数导致精度偏差
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));
		return firstBigDecimal.subtract(secondBigDecimal).doubleValue();
	}
	
	/**
	 * 获得两数相乘的结果
	 * @param firstDouble
	 * @param secondDouble
	 * @param n
	 * @return double
	 */
	public static double getMultiplication(double firstDouble,double secondDouble,int n) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));;
		return firstBigDecimal.multiply(secondBigDecimal).setScale(n, RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * 获得两数相乘的结果
	 * @param firstDouble
	 * @param secondDouble
	 * @param n
	 * @return double
	 */
	public static double getMultiplication(double firstDouble,double secondDouble) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));;
		return firstBigDecimal.multiply(secondBigDecimal).doubleValue();
	}
	
	/**
	 * 获得两数相除的结果，精度为小数点后n位，四舍五入<br/>
	 * 当被除数为0时，系统不进行运算，直接返回0<br/>
	 * 根据业务情况，请在运算前判别被除数是否为0
	 * @param firstDouble 除数
	 * @param secondDouble  被除数
	 *  @param n
	 * @return double
	 */
	public static double getDivide(double firstDouble,double secondDouble,int n) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));;
		if(secondBigDecimal.compareTo(BigDecimal.ZERO)==0)
		{
			return 0;
		}
		return firstBigDecimal.divide(secondBigDecimal, n, RoundingMode.HALF_UP).doubleValue();
	}
	/**
	 * 获得两数相除的结果，精度为小数点后n位，四舍五入<br/>
	 * 当被除数为0时，系统不进行运算，直接返回0<br/>
	 * 根据业务情况，请在运算前判别被除数是否为0
	 * @param firstDouble 除数
	 * @param secondDouble  被除数
	 *  @param n
	 * @return double
	 */
	public static double getDivide(double firstDouble,double secondDouble) {
		BigDecimal firstBigDecimal=new BigDecimal(String.valueOf(firstDouble));
		BigDecimal secondBigDecimal=new BigDecimal(String.valueOf(secondDouble));;
		if(secondBigDecimal.compareTo(BigDecimal.ZERO)==0)
		{
			return 0;
		}
		return firstBigDecimal.divide(secondBigDecimal,2,RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * 将金额格式化为千分位显示，保留两位小数
	 * 
	 * @param money
	 * @return
	 */
	public static String formatThousandth(double money) {
		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		double rtnVal = doubleToUp(Double.parseDouble(String.valueOf(money)));
		return df.format(rtnVal);
	}

	/**
	 * 金额去除千分位
	 * 
	 * @param money
	 * @return
	 * @throws ParseException
	 */
	public static double removeThousandth(String money) throws Exception {
		DecimalFormat df = new DecimalFormat("###,###,###.##");
		return df.parse(money).doubleValue();
	}
	
	/**
	 * 四舍五入，保留两位小数
	 */
	public static double doubleToUp(double d){
		return (double)Math.round(d*100)/100;
	}
	
	/**
	 * 四舍五入，保留两位小数
	 */
	public static double doubleToUp(String s){
        BigDecimal decimal = new BigDecimal(s);
		BigDecimal setScale = decimal.setScale(2,RoundingMode.HALF_UP);
		return setScale.doubleValue();
	}
	/**
	 * 判断是否为数字类型
	 */
	public static boolean isNumber(Object obj){
		boolean isNumber=true;
		try {
			Number num=(Number) obj;
		} catch (Exception e) {
			isNumber=false;
		} 
		return isNumber||isScientific(obj);
	}
	
	/**
	 * 判断是否为科学计数法
	 */
	public static boolean isScientific(Object obj){
		String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";//科学计数法正则表达式  
	    Pattern pattern = Pattern.compile(regx);  
	    return pattern.matcher(String.valueOf(obj)).matches();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(isNumber(2));
		System.out.println(isNumber(2.0));
		System.out.println(isNumber(77777777));
		System.out.println(isNumber(0.0002));
		System.out.println(isNumber(1.0E-38));
	} 
}
