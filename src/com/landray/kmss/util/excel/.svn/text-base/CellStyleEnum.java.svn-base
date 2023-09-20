package com.landray.kmss.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * excel单元格样式枚举
 */
public enum CellStyleEnum {

	/**
	 * 水平居中
	 */
	ALIGNMENT_ALIGN_CENTER,
	/**
	 * 垂直居中
	 */
	VERTICALALIGNMENT_VERTICAL_CENTER;
	
	/**
	 * 根据枚举来设置样式
	 */
	public static void geneCellStyle(HSSFCellStyle style, CellStyleEnum cellStyle){
		switch (cellStyle) {
		case ALIGNMENT_ALIGN_CENTER:
			style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			break;
		case VERTICALALIGNMENT_VERTICAL_CENTER:
			style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			break;
		default:
			break;
		}
	}
	//新导出样式 适用于新的API
	public static void geneCellStyle(CellStyle style, CellStyleEnum cellStyle){
		switch (cellStyle) {
			case ALIGNMENT_ALIGN_CENTER:
				style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
				break;
			case VERTICALALIGNMENT_VERTICAL_CENTER:
				style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
				break;
			default:
				break;
		}
	}
}
