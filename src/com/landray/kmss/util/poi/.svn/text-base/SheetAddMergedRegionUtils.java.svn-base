package com.landray.kmss.util.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class SheetAddMergedRegionUtils {

	//替换3.10 org.apache.poi.hssf.util.CellRangeAddress.CellRangeAddress(int, int, int, int)
	public static void addMergedRegion(Sheet sheet, CellRangeAddress cellRangeAddress) {
		if (cellRangeAddress.getFirstRow() < cellRangeAddress.getLastRow()
				|| cellRangeAddress.getFirstColumn() < cellRangeAddress.getLastColumn()) {
			sheet.addMergedRegion(cellRangeAddress);
		}
	}

	//替换3.10 org.apache.poi.hssf.util.Region.Region(int, short, int, short)
	public static void addMergedRegionForRegion(Sheet sheet,CellRangeAddress cellRangeAddress) {
		
		if (cellRangeAddress.getFirstRow() < cellRangeAddress.getFirstColumn()
				|| cellRangeAddress.getLastRow() < cellRangeAddress.getLastColumn()) {
			sheet.addMergedRegion(cellRangeAddress);
		}
	}
	

}
