package com.landray.kmss.tic.jdbc.util;

import org.apache.commons.lang.StringUtils;

public class JdbcSqlServerUtil{
	/**
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean typeSmallDateTime(String columnType) {
		return "SMALLDATETIME".equals(columnType.toUpperCase()) ? true : false;
	}

	/**
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean typeDate(String columnType) {
		return "DATE".equals(columnType.toUpperCase()) ? true : false;
	}

	/**
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean typeDateTime(String columnType) {
		return "DATETIME".equals(columnType.toUpperCase()) ? true : false;
	}

	/**
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean typeDateTime2(String columnType) {
		return "DATETIME2".equals(columnType.toUpperCase()) ? true : false;
	}

	/**
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean typeDateTimeOffset(String columnType) {
		return "DATETIMEOFFSET".equals(columnType.toUpperCase()) ? true : false;
	}

	/**
	 * 验证符合上面种的一种
	 * 
	 * @param columnType
	 * @return
	 */
	public static boolean validateRQType4SqlServer(String columnType) {
		return typeDate(columnType) || typeDateTime(columnType)
				|| typeDateTime2(columnType) || typeSmallDateTime(columnType)
				|| typeDateTimeOffset(columnType);
	}

	public static boolean validataColumnType4RQ(String columnType) {
		return validateRQType4SqlServer(columnType);
	}
	
	/**
	 * 验证是否是timestamp类型
	 */
	
	public static boolean validateColumnType4Timestamp(String columnType) {
		if (StringUtils.isNotEmpty(columnType)) {
			columnType = columnType.trim();
			int indexNum = columnType.indexOf("(");
			columnType = columnType.substring(0, indexNum);
			columnType=columnType.toUpperCase();
			if ("TIMESTAMP".equals(columnType)) {
				return true;
			} else {
				return false;
			}
		}
		  return false;
	}
}
