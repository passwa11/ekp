package com.landray.kmss.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelUtil {
	/**
	 * 获取某个单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static Object getCellValue(HSSFCell cell) {
		String retStr = "";
		if (cell == null) {
            return retStr;
        }
		switch (cell.getCellType()) {
		case NUMERIC:
			return new Double(cell.getNumericCellValue());
		case STRING:
			retStr = cell.getStringCellValue();
			break;
		case FORMULA:
			return new Double(cell.getNumericCellValue());
		case BLANK:
			retStr = "";
			break;
		default:
			retStr = cell.getStringCellValue();
		}
		return retStr;
	}

	public static String getFileName(String fileName,
			HttpServletRequest request) throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent");
		String filename = fileName;
		if (userAgent.contains("MSIE")
				|| (userAgent.contains("rv") && userAgent.contains("Trident"))
				|| userAgent.contains("Edge")) {
			filename = URLEncoder.encode(filename, "UTF-8");
		} else if (userAgent.contains("Mozilla")) {
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		} else {
			filename = URLEncoder.encode(filename, "UTF-8");
		}
		return filename;
	}
}
