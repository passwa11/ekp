package com.landray.kmss.km.smissive.util;

public class SmissiveUtil {

	public static String getCnDate(String dateStr) {
		StringBuilder sb = new StringBuilder();
		String[] dataArr = dateStr.split("");
		int first = 0;
		for (int i = 1; i < dataArr.length; i++) {
			if ("-".equals(dataArr[i])) {
				first = first + 1;
				if (first == 1) {
					sb.append("年");
				}
				if (first == 2) {
					sb.append("月");
				}
			} else if (i < 5) {
				sb.append(transferNumToChinese(dataArr[i]));
			} else if (i == 6 && "1".equals(dataArr[i])) {
				sb.append("十");
			} else if (i == 7) {
				sb.append(transferNumToChinese(dataArr[i]));
			} else if (i == 9) {
				if ("1".equals(dataArr[i])) {
					sb.append("十");
				}
				if ("2".equals(dataArr[i])) {
					sb.append("二十");
				}
				if ("3".equals(dataArr[i])) {
					sb.append("三十");
				}
			} else if (i == dataArr.length - 1) {
				sb.append(transferNumToChinese(dataArr[i]))
						.append("日");
			}
		}
		return sb.toString();
	}

	private static String transferNumToChinese(String str) {
		switch (str) {
			case "0":
				return "〇";
			case "1":
				return "一";
			case "2":
				return "二";
			case "3":
				return "三";
			case "4":
				return "四";
			case "5":
				return "五";
			case "6":
				return "六";
			case "7":
				return "七";
			case "8":
				return "八";
			case "9":
				return "九";
			default:
				return "";
		}
	}
}
