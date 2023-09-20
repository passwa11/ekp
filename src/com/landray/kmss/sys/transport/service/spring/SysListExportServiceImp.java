package com.landray.kmss.sys.transport.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.transport.service.ISysListExportService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.List;

public class SysListExportServiceImp extends BaseServiceImp implements ISysListExportService{
    
    public HSSFWorkbook buildWorkBook(String[] Headers) throws Exception {
        final HSSFWorkbook wb = new HSSFWorkbook();
        final HSSFSheet sheet1 = wb.createSheet("列表数据导出");
        this.buildTemplate(wb, sheet1,Headers);
        return wb;
    }
    
    @Override
    public HSSFWorkbook exportWorkBook(String[] Headers, List list) throws Exception {
        HSSFRow row = null;
        int rowCount = 1;
        final HSSFWorkbook workbook = this.buildWorkBook(Headers);
        final HSSFSheet sheet1 = workbook.getSheetAt(0);
		for (Object data : list) {
			row = sheet1.createRow(rowCount++);
			List dataLast = (List)data;
			List value=(List) dataLast.get(1);
			 this.buileOneNcCustomer(workbook,row,value);
		}
        return workbook;
    }
    
    private HSSFCellStyle getCellStyle(final HSSFWorkbook wb, final boolean isCenter) {
        final HSSFCellStyle style = wb.createCellStyle();
        if (isCenter) {
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        style.setWrapText(true);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
        return style;
    }
    
    private void setBackgroundColor(final HSSFCellStyle style, final boolean isMain) {
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (isMain) { 
            style.setFillForegroundColor((short)44);
        }
        else {
            style.setFillForegroundColor((short)22);
        }
    }
    
    private HSSFFont getTitleFont(final HSSFWorkbook wb, final boolean isRed) {
        final HSSFFont font = wb.createFont();
        font.setBold(true);
        if (isRed) {
            font.setColor((short)10);
        }
        return font;
    }
    
    private void buildTemplate(final HSSFWorkbook wb, final HSSFSheet sheet,String[] Headers) {
        HSSFRow row = null;
        HSSFCell cell = null;
        HSSFCellStyle style = null;
        sheet.setDefaultColumnWidth(20);
        row = sheet.createRow(0);
        int cellIndex = 0;
                for (int i = 0; i < Headers.length; ++i) {
                    cell = row.createCell(cellIndex++);
                    style = this.getCellStyle(wb, true);
                    this.setBackgroundColor(style, true);
                    style.setFont(this.getTitleFont(wb, false));
                    cell.setCellStyle(style);
                    cell.setCellValue(Headers[i]);
                }
        final HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex((short)44, (byte)91, (byte)(-101), (byte)(-43));
        palette.setColorAtIndex((short)22, (byte)(-39), (byte)(-39), (byte)(-39));
    }
    
    private void buileOneNcCustomer(HSSFWorkbook workbook,HSSFRow row,List values) throws Exception {
        HSSFCell cell = null;
        int cellIndex = 0;

        HSSFCellStyle textStyle = workbook.createCellStyle();
        HSSFDataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));

        for (Object value : values) {
        	List valueLast=(List) value;
            cell = row.createCell(cellIndex++);
            cell.setCellValue(valueLast.get(1).toString());
            cell.setCellStyle(textStyle);//设置单元格格式为"文本"
            cell.setCellType(CellType.STRING);
        }
       
    }
    
    
}
