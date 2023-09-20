package com.landray.kmss.util;

import java.util.Locale;

public class TimeNumberUtil {
	/**
	 * 根据当前语言环境，获得月份的相应字符串
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthShowText(int month) {
		String monthStr = String.valueOf(month);
		String locale = ResourceUtil.getLocaleStringByUser();
		if ("en-us".equals(locale) || "en".equals(locale)) {
			switch (month) {
			case 1:
				return "January";
			case 2:
				return "February";
			case 3:
				return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August ";
			case 9:
				return "September ";
			case 10:
				return "October ";
			case 11:
				return "November";
			case 12:
				return "December";
			}
		}
		return monthStr;
	}

	/**
	 * 根据相应的语言环境，获得数字的相应字符串
	 * 
	 * @param number
	 * @return
	 */
	public static String getNumberShowText(int number) {
		String str = String.valueOf(number);
		String locale = ResourceUtil.getLocaleStringByUser();
		if ("en-us".equals(locale) || "en".equals(locale)) {
			switch (number) {
			case 1:
				return "First";
			case 2:
				return "Second";
			case 3:
				return "Third";
			case 4:
				return "Fourth";
			case 5:
				return "Fifth";
			}
		}
		return str;
	}

	/**
	 * 获取时间数字的展现格式
	 * 
	 * @param value
	 * @param key
	 * @param locale
	 * @return
	 */
	public static String getTimeNumberString(Long value, String key,
			Locale locale) {
		String message = ResourceUtil.getString(key, locale);
		long lValue = 0;
		if (value != null) {
			lValue = value.longValue();
		}
		Context context = new Context(lValue, message);
		return context.getResult();
	}

	private static class Context {
		private long remainValue;

		private String message;

		private boolean itemRequired = false;

		protected Context(long remainValue, String message) {
			super();
			this.remainValue = remainValue;
			this.message = message;
		}

		protected String getResult() {
			String[] msgs = message.split("\\(");
			StringBuffer result = new StringBuffer();
			int index;
			appendBlock(result, msgs[0], true);
			for (int i = 1; i < msgs.length; i++) {
				index = msgs[i].indexOf(')');
				appendBlock(result, msgs[i].substring(0, index), false);
				appendBlock(result, msgs[i].substring(index + 1), true);
			}
			return result.toString();
		}

		private void appendBlock(StringBuffer result, String block,
				boolean required) {
			String[] blocks = block.split("\\{");
			StringBuffer value = new StringBuffer(blocks[0]);
			int index;
			for (int i = 1; i < blocks.length; i++) {
				index = blocks[i].indexOf('}');
				value.append(getItemValue(blocks[i].substring(0, index)));
				value.append(blocks[i].substring(index + 1));
			}

			if (itemRequired || required) {
				result.append(value);
			}
		}

		private String getItemValue(String expression) {
			long interval = 0;
			switch (expression.charAt(0)) {
			case 'Y':
				interval = DateUtil.YEAR;
				break;
			case 'M':
				interval = DateUtil.MONTH;
				break;
			case 'D':
				interval = DateUtil.DAY;
				break;
			case 'h':
				interval = DateUtil.HOUR;
				break;
			case 'm':
				interval = DateUtil.MINUTE;
				break;
			case 's':
				interval = DateUtil.SECOND;
				break;
			}

			if (expression.length() == 1) {
				long value = remainValue / interval;
				if (value > 0) {
					remainValue = remainValue % interval;
					itemRequired = true;
				}
				return String.valueOf(value);
			} else {
				double value = 1.0 * remainValue / interval;
				if (value > 0) {
					remainValue = 0;
					itemRequired = true;
				}
				return String.valueOf(NumberUtil.roundDecimal(
						new Double(value), expression.substring(1)));
			}
		}
	}
}
