package com.landray.kmss.hr.staff.util;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Soylar
 */
public class Excel02Util {
    /**
     * 设置导出excel的普通字体的样式
     * @param wb
     * @return
     */
    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(new XSSFColor(Color.black));
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(new XSSFColor(Color.black));
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(new XSSFColor(Color.black));
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(new XSSFColor(Color.black));
        // 设置自动换行
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    public static XSSFCellStyle getTitleStyle(XSSFWorkbook wb) {
        XSSFColor xssfColor = new XSSFColor(Color.black);
        XSSFCellStyle cellStyle = wb.createCellStyle();
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(xssfColor);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(xssfColor);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(xssfColor);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(xssfColor);
        //设置自动换行
        cellStyle.setWrapText(true);

        return cellStyle;
    }

    public static void setCell(XSSFRow row, XSSFCellStyle bodyStyle, int columnIndex, Object val) {
        XSSFCell cell1 = row.createCell(columnIndex);
        cell1.setCellStyle(bodyStyle);
        if (val != null) {
            if (val instanceof BigInteger) {
                double val1 = ((BigInteger) val).doubleValue();
                cell1.setCellValue(val1);
            } else if (val instanceof Integer) {
                double val1 = ((Integer) val).doubleValue();
                cell1.setCellValue(val1);
            }  else if (val instanceof Double) {
                double val1 = ((Double) val).doubleValue();
                cell1.setCellValue(val1);
            } else if (val instanceof BigDecimal) {
                double val1 = ((BigDecimal) val).doubleValue();
                cell1.setCellValue(val1);
            } else {
                String val1 = val != null ? val.toString() : "";
                cell1.setCellValue(val1);
            }
        } else {
            cell1.setCellValue("");
        }
    }

    /**
     * 将导出的数据进行或者列合并
     * @param sheet
     * @param bodyStyle
     * @param text
     * @author:石婷婷
     */
    public static void insertMerge(XSSFSheet sheet,XSSFCellStyle bodyStyle, String text, int startColIndex, int endColIndex, int startRowIndex,int endRowIndex) {
        XSSFRow row = sheet.getRow(startRowIndex);
        XSSFCell cell = row.createCell(startColIndex);
        cell.setCellStyle(bodyStyle);
        cell.setCellValue(text);
        CellRangeAddress region = new CellRangeAddress(startRowIndex, endRowIndex, startColIndex, endColIndex);
        sheet.addMergedRegion(region);
        // 为合并行添加边框
        RegionUtil.setBorderTop(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, sheet);
    }
}
