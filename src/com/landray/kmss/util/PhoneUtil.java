package com.landray.kmss.util;

import java.util.regex.Pattern;

public class PhoneUtil  {
//	public static  String   MOBILE_FORMAT_REGULAR = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[189])\\d{8}$";
	
	public static  Boolean checkFormat(String mobileNo){
		if(mobileNo.indexOf("-") != -1 ){
			//判断是否有+
			if (mobileNo.startsWith("+")){
				String regex = "^(\\+\\d{1,5})(\\-)(\\d{6,11})";
				//+86是国内手机号，其他为+XX是不国内手机号
				if (mobileNo.startsWith("+86")){
					regex = "^(\\+86-1)(\\d{10})";
				}
				return  Pattern.matches(regex, mobileNo);
			}else{
				String regex = "^(\\d{1,5})(\\-)(\\d{6,11})";
				//+86是国内手机号，其他为+XX是不国内手机号
				if (mobileNo.startsWith("86")){
					regex = "^(86-1)(\\d{10})";
				}
				return  Pattern.matches(regex, mobileNo);
			}
		}else{
			if (mobileNo.startsWith("+")){
				String regex = "^(\\+\\d{1,5})(\\d{6,11})";
				//+86是国内手机号，其他为+XX是不国内手机号
				if (mobileNo.startsWith("+86")){
					regex = "^(\\+861)(\\d{10})";
				}
				return  Pattern.matches(regex, mobileNo);
			}else{
				//既没有+也没有-的手机号默认为国内手机号
				String regex = "^(1)(\\d{10})";
				if (mobileNo.startsWith("86")){
					regex = "^(861)(\\d{10})";
				}
				return  Pattern.matches(regex, mobileNo);
			}
		}

//		return  Pattern.matches(MOBILE_FORMAT_REGULAR, mobileNo);
	}
}
