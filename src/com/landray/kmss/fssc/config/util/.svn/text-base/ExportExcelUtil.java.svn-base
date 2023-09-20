package com.landray.kmss.fssc.config.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 导出excel的工具类，目前实现功能如下：
 * 导出简单的excel
 * 导出多sheet的excel
 * 导出多sheet页，每个sheet页多个表格区域
 * 支持主题切换，通过配置ExportExcelBean下的theme实现，主题详情见 ExportExcelTheme
 * 支持表格的相同标题行合并，通过配置ExportExcelBean下的horizontalMergerColumnHeaders实现
 * 支持表格的相同数据列合并，通过配置ExportExcelBean下的verticalMergerColumnHeaders实现
 * 支持sheet页数据加密，通过配置ExportExcelBean下的protectSheet实现
 * 支持自动生成数据的序号
 *
 * @author ZhangFZ
 * @date 2019/12/9 17:36
 **/
public class ExportExcelUtil {

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     * 单Sheet页导出
     * @param out     可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcel(ExportExcelBean excel, OutputStream out) {
        List<ExportExcelBean> list = new ArrayList<>();
        List<ExportExcelBean> exportExcelBeans = new ArrayList<>();
        exportExcelBeans.add(excel);
        ExportExcelBean exportExcelBean = new ExportExcelBean();
        exportExcelBean.setSheetName(excel.getSheetName());
        exportExcelBean.setList(exportExcelBeans);
        list.add(exportExcelBean);
        exportExcelMoreSheetMoreTable(list, out);
    }

    /**
     * 这是一个通用的方法，导出excel2007版，后缀为.xlsx。
     * 多Sheet页导出，多sheet页中对应多table
     * @param out     可以将EXCEL文档导出到本地文件或者网络中
     */
    public static void exportExcelMoreSheetMoreTable(List<ExportExcelBean> list, OutputStream out){
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (ExportExcelBean exportExcelBeanList : list){
            creatMoreTableSheet(exportExcelBeanList, workbook);
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建excel
     */
    private static void creatMoreTableSheet(ExportExcelBean excelBean, XSSFWorkbook workbook){
        List<ExportExcelBean> list = excelBean.getList();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(excelBean.getSheetName());
        // 设置表格默认列宽度为16个字节
        sheet.setDefaultColumnWidth(16);

        int listCount = 0;
        for (ExportExcelBean excel : list){
            // 设置主题样式，默认DEFAULT
            ExportExcelTheme exportExcelTheme = excel.getTheme();
            if(exportExcelTheme == null){
                exportExcelTheme = new ExportExcelTheme();
            }
            XSSFColor[] theme = exportExcelTheme.theme;
            XSSFCellStyle titleStyle = createTitleStyle(workbook, theme[0], theme[1]);
            XSSFCellStyle titleStyleOne = createLineStyle(workbook, theme[2]);
            XSSFCellStyle titleStyleTwo = createLineStyle(workbook, theme[3]);
            // 产生表格标题行
            createTitle(excel, sheet, titleStyle, listCount);

            // 产生表格数据
            createExcelData(excel, sheet, titleStyleOne, titleStyleTwo, listCount);

            // 与下一个表格之间空出两行
            listCount = listCount + excel.getDataList().size() + 2;
        }

        // 设置sheet页是否加密
        if(StringUtils.isNotBlank(excelBean.getProtectSheet())){
            sheet.protectSheet(excelBean.getProtectSheet());
        }
    }

    /**
     * 设置表格的标题
     */
    private static void createTitle(ExportExcelBean excel, XSSFSheet sheet, XSSFCellStyle titleStyle, int listCount){
        // 用于标志横向合并标题
        Integer horizontalNum = null;
        Object horizontalValue = null;
        XSSFRow row = sheet.createRow(listCount);
        for (int i = 0; i < excel.getHeaders().size(); i++) {
            XSSFCell cell = row.createCell(i);
            // 设置标题样式
            cell.setCellStyle(titleStyle);

            XSSFRichTextString text = new XSSFRichTextString(excel.getHeaders().get(i));
            cell.setCellValue(text);

            // 横向合并标题
            if(excel.getHorizontalMergerColumnHeaders() != null
                    && excel.getHorizontalMergerColumnHeaders().contains(excel.getHeaders().get(i))){
                if(horizontalNum == null){
                    horizontalNum = i;
                    horizontalValue = excel.getHeaders().get(i);
                }else{
                    // 当前列与前一列不相等，开启合并，并重新赋值
                    if(!horizontalValue.equals(excel.getHeaders().get(i))){
                        if(i - horizontalNum > 1){
                            CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i-1);
                            sheet.addMergedRegion(cra);
                        }
                        horizontalNum = i;
                        horizontalValue = excel.getHeaders().get(i);
                        // 当前为最后一列，开启合并
                    }else if(i == excel.getHeaders().size() - 1){
                        if(i - horizontalNum >= 1){
                            CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i);
                            sheet.addMergedRegion(cra);
                        }
                    }
                }
            }else{
                if(horizontalNum != null && i - horizontalNum > 1){
                    CellRangeAddress cra = new CellRangeAddress(listCount, listCount, horizontalNum, i-1);
                    sheet.addMergedRegion(cra);
                }else{
                    horizontalNum = null;
                    horizontalValue = null;
                }
            }
        }
    }

    /**
     * 设置表格的数据内容
     */
    private static void createExcelData(ExportExcelBean excel, XSSFSheet sheet, XSSFCellStyle titleStyleOne, XSSFCellStyle titleStyleTwo, int listCount){
        // 用于标注竖向合并数据
        Integer[] verticalNum = new Integer[excel.getKeys().size()];
        Object[] verticalValue = new Object[excel.getKeys().size()];
        //循环放置表格中的值
        for (int i = 0; i < excel.getDataList().size(); i++) {
            int line = i + listCount + 1;
            XSSFRow row = sheet.createRow(line);
            //产生序号，1,2,3,4,5...的递增序号，不需要，header去掉‘序号’就可以了
            if("序号".equals(excel.getHeaders().get(0))){
                XSSFCell cell = row.createCell(0);
                // 设置隔行样式
                if(i % 2 == 0){
                    cell.setCellStyle(titleStyleOne);
                }else{
                    cell.setCellStyle(titleStyleTwo);
                }
                cell.setCellValue(i + 1 + "");
            }

            Map<String, Object> obj = excel.getDataList().get(i);
            for (int j = 0; j < excel.getKeys().size(); j++) {
                if (obj.get(excel.getKeys().get(j)) != null) {
                    XSSFCell cell = row.createCell(j);
                    // 设置隔行样式
                    if(i % 2 == 0){
                        cell.setCellStyle(titleStyleOne);
                    }else{
                        cell.setCellStyle(titleStyleTwo);
                    }
                    if (obj.get(excel.getKeys().get(j)) == null) {
                    	cell.setCellValue("");
	                } else {
	                    cell.setCellValue(obj.get(excel.getKeys().get(j)) + "");
	                }

                    // 纵向合并数据
                    if(excel.getVerticalMergerColumnHeaders() != null
                            && excel.getVerticalMergerColumnHeaders().contains(excel.getHeaders().get(j))){
                        if(verticalNum[j] == null){
                            verticalNum[j] = line;
                            verticalValue[j] = obj.get(excel.getKeys().get(j));
                        }else{
                            // 当前列与前一列不相等，开启合并，并重新赋值
                            if(verticalValue[j] != null && ! verticalValue[j].equals(obj.get(excel.getKeys().get(j)))){
                                if(line - verticalNum[j] > 1){
                                    CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line-1, j, j);
                                    sheet.addMergedRegion(cra);
                                }
                                verticalNum[j] = line;
                                verticalValue[j] = obj.get(excel.getKeys().get(j));
                                // 当前为最后一列，开启合并
                            }else if(i == excel.getDataList().size() - 1){
                                if(line - verticalNum[j] >= 1){
                                    CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line, j, j);
                                    sheet.addMergedRegion(cra);
                                }
                            }
                        }
                    }else{
                        if(verticalNum[j] != null && line - verticalNum[j] > 1){
                            CellRangeAddress cra = new CellRangeAddress(verticalNum[j], line-1, j, j);
                            sheet.addMergedRegion(cra);
                        }else{
                            verticalNum[j] = null;
                            verticalValue[j] = null;
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置标题的样式
     */
    private static XSSFCellStyle createTitleStyle(XSSFWorkbook workbook, XSSFColor bgColor, XSSFColor fontColor){
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(bgColor);
        createBorder(style);
        // 生成一个字体
        Short fontSize = 13;
        XSSFFont font = createFont(workbook, fontColor, fontSize);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * 设置样式
     */
    private static XSSFCellStyle createLineStyle(XSSFWorkbook workbook, XSSFColor bgColor){
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(bgColor);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        createBorder(style);
        return style;
    }

    /**
     * 设置边框
     */
    private static void createBorder(XSSFCellStyle style){
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.WHITE1.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.WHITE1.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.WHITE1.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.WHITE1.getIndex());
    }

    /**
     * 生成字体样式
     * @return 字体样式
     */
    private static XSSFFont createFont(XSSFWorkbook workbook, XSSFColor color, short fontSize){
        XSSFFont font = workbook.createFont();
        font.setColor(color);
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        return font;
    }

    /**
     * 根据浏览器处理导出文件的名字
     * @param exportFileName 文件名字
     */
    public static void updateNameUnicode(HttpServletRequest request, HttpServletResponse response, String exportFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel");
        //根据浏览器类型处理文件名称
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        String firefox = "firefox";
        //若是火狐
        if (agent.contains(firefox)) {
            exportFileName = new String(exportFileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        } else {//其他浏览器
            exportFileName = java.net.URLEncoder.encode(exportFileName, "UTF-8");
        }
        //保存导出的excel的名称
        response.setHeader("Content-Disposition", "attachment;filename=" + exportFileName + ".xlsx");
    }
}
