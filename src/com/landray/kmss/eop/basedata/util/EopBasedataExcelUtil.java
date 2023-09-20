package com.landray.kmss.eop.basedata.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class EopBasedataExcelUtil {
	/**
	 * 获取某个单元格的值,所有返回值强制格式化为string类型，主要防止 excel本来是字符类型，由于编辑后造成格式改变的问题
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(HSSFCell cell) {
		String retStr = "";
		if (cell == null) {
			return retStr;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
	
	/**
	 * @author 
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(HSSFRow row) {
		if (row == null) {
			return true;
		}
		int cellNum = row.getLastCellNum();
		for (int c = 0; c < cellNum; c++) {
			HSSFCell cell = row.getCell(c);
			if (null != getCellValue(cell)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @author 
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isBlankRow(Row row) {
		if (row == null) {
			return true;
		}
		int cellNum = row.getLastCellNum();
		for (int c = 0; c < cellNum; c++) {
			Cell cell = row.getCell(c);
			if (null != getCellValue(cell)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取某个单元格的值,所有返回值强制格式化为string类型，主要防止 excel本来是字符类型，由于编辑后造成格式改变的问题
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String retStr = "";
		if (cell == null) {
			return retStr;
		}
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
}

