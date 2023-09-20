package com.landray.kmss.sys.time.util;

import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-29
 */
public class SysTimeImportUtil {
	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static DecimalFormat formatter = new DecimalFormat(
			"####################");

	/**
	 * 获取Excel单元格的字符串值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		String rtnStr;
		switch (cell.getCellType()) {
		case BOOLEAN:
			rtnStr = new Boolean(cell.getBooleanCellValue()).toString();
			break;
		case FORMULA: {
			rtnStr = formatter.format(cell.getNumericCellValue());
			break;
		}
		case NUMERIC: {
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
				SimpleDateFormat sdf = null;
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				rtnStr = sdf.format(cell.getDateCellValue());
			} else {
				Double d = cell.getNumericCellValue();
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
				rtnStr = cell.getRichStringCellValue().getString();
				cell.setCellValue(d);
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
			}
			break;
		}
		case BLANK:
		case ERROR:
			rtnStr = "";
			break;
		default:
			rtnStr = cell.getRichStringCellValue().getString();
		}
		return formatString(rtnStr.trim());
	}

	/**
	 * 去除字符串中的无法辨认的字符
	 * 
	 * @param s
	 * @return
	 */
	public static String formatString(String s) {
		StringBuffer rtnStr = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 128 && c <= 255 && EXCEPT_STRING.indexOf(c) == -1) {
				continue;
			}
			rtnStr.append(c);
		}
		return rtnStr.toString();
	}


}
