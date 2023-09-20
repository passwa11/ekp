package com.landray.kmss.util.excel;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustomList;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.enums.ValueLabel;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel 导出工具类，适用于数据量几十万及以上的数据导出
 * by ：xuwh
 */
public class ExcelMillionOutput {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ExcelMillionOutput.class);

    private  ExcelObject excelObject;


    public ExcelMillionOutput(ExcelObject excelObject){
        this.excelObject = excelObject;
    }
    //默认样式
    private static CellStyle buildStyle(SXSSFWorkbook wb, String type,
                                 String color) {
        CellStyle style = wb.createCellStyle();
        style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.index);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        style.setRightBorderColor(IndexedColors.BLACK.index);
        Font font = wb.createFont();
        font.setFontName("Microsoft YaHei");
        if ("red".equals(color)) {
            font.setColor(HSSFFont.COLOR_RED);
        }
        if ("title".equals(type)) { // 表格标题，粗体，居中，12号字
            style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            style.setFont(font);
        } else if ("columnTitle".equals(type)) { // 表格列标题，粗体，居中，10号字
            style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
            font.setFontHeightInPoints((short) 10);
            font.setBold(true);
            style.setFont(font);
        } else if ("content".equals(type)) { // 内容，10号字
            font.setFontHeightInPoints((short) 10);
        }
        return style;
    }



    private  SXSSFWorkbook sxssfWorkbook;

    public SXSSFWorkbook getSxssfWorkbook() {
        return sxssfWorkbook;
    }

    public void setSxssfWorkbook(SXSSFWorkbook sxssfWorkbook) {
        this.sxssfWorkbook = sxssfWorkbook;
    }

    //初始化生成 excel
    public  void initExcel() {
        Integer memeoryRows = excelObject.getMemoryRows();
        SXSSFWorkbook wb = new SXSSFWorkbook(memeoryRows); // 在内存当中保持 memeoryRows,默认值为100 行 ,  超过的数据放到硬盘中
        Integer sheetCount = excelObject.getSheetCount();//sheet页签数量
        List<String> sheetName = excelObject.getSheetName();//获取签名称
        List<String> titles = excelObject.getCloumTittle();//获取列标题
        CellStyle titleStyle =  excelObject.getCellStyle();//获取列标题样式
        String password = excelObject.getPassword();
        if(titleStyle==null)
             titleStyle = buildStyle(wb, "title",null);//默认行标题样式
        // 根据总记录数创建sheet并分配列标题
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet sheet = wb.createSheet(sheetName.get(i));
            if(StringUtil.isNotNull(password)){//设置密码锁定
                sheet.protectSheet(password);
            }
            SXSSFRow headRow = sheet.createRow(0);
            for (int j = 0; j < titles.size(); j++) {
                SXSSFCell headRowCell = headRow.createCell(j);
                int count = calculateChineseNumber(titles.get(j));
                sheet.setColumnWidth(j, (titles.get(j).length() - count) * 256*2 + (count + 1) * 256*2*2);
                headRowCell.setCellValue(titles.get(j));
                headRowCell.setCellStyle(titleStyle);
            }
        }
        setSxssfWorkbook(wb);
    }

    //创建出excel并且写入流中
    public void createExcel() throws Exception {
        SXSSFWorkbook wb = getSxssfWorkbook();
        Integer  totalRowCount = excelObject.getTotalRowCount();//获取数据总数
        Integer  perWriteRowCount = excelObject.getPerWriteRowCount();//获取每次写入的数据大小
        Integer perWriteRowCountTimes = excelObject.getPerSheetWriteTimes();//获取写入次数
        WriteExcelDataDelegated writeExcelDataDelegated = excelObject.getWriteExcelDataDelegated();//获取回调函数
        OutputStream outputStream = excelObject.getOutputStream();//获取流信息
        CellStyle contentStyle = excelObject.getContentSytle();//获取内容样式
        if(contentStyle == null){
            contentStyle = buildStyle(wb,"content",null);
        }
        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        Integer pageSize = perWriteRowCount;//最大数
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);
            for (int j = 1; j <= perWriteRowCountTimes; j++) {
                Integer startRowCount = (j - 1) * perWriteRowCount;//起始行
                int endRowCount = startRowCount + pageSize ;
                List<List<String>> mapList = writeExcelDataDelegated.writeExcelData(startRowCount,pageSize);
                if(mapList != null && !mapList.isEmpty()){
                    createRow(startRowCount+1,endRowCount,eachSheet,mapList,contentStyle);
                }
            }
        }
        //将excel写入流中
        excelWriteToStream(wb,outputStream);
    }

    private void createRow(int startRowCount ,int endRowCount,SXSSFSheet eachSheet ,List<List<String>> mapList ,CellStyle contentStyle){
        for (int i = startRowCount; i <= endRowCount; i++) {
            SXSSFRow eachDataRow = eachSheet.createRow(i);
            if ((i - startRowCount) < mapList.size()) {
                List<String> list = mapList.get(i-startRowCount);
                List<String> fieldList = new ArrayList<String>();
                for (int j = 0; j < list.size(); j++) {
                    fieldList.add(list.get(j));
                    createCell(fieldList,eachDataRow,eachSheet,contentStyle);//创建单元格
                }
            }
        }
    }

    /**
     * @author xuwh
     * @param  fieldList 所导出的字段值集合
     * @param eachDataRow 每一个单元格
     * @param eachSheet 所在的sheet
     * @param style 单元格样式
     * @date 2022/6/6 10:11
     * @description
     */
    private  void createCell (List<String> fieldList,SXSSFRow eachDataRow,SXSSFSheet eachSheet,CellStyle style){
        int index = 0;
        for(String str : fieldList){
            SXSSFCell cell = eachDataRow.createCell(index);
            if(StringUtil.isNotNull(str)){//如果这个值不为空，给予默认样式，单元格宽度自动。如果存在样式请自定义
                int count = ExcelMillionOutput.calculateChineseNumber(str);
                eachSheet.setColumnWidth(index, (str.length() - count) * 256*2 + (count + 1) * 256*2*2);//设置列宽
            }
            cell.setCellStyle(style);
            cell.setCellValue(str);
            index ++ ;
        }
    }



    //将excel写入流中
    private  void excelWriteToStream(SXSSFWorkbook wb, OutputStream outputStream) throws IOException {
        try {
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //计算中文个数
    public static int calculateChineseNumber(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            for (int i =0; i <= matcher.groupCount(); i++) {
                count = count +1;
            }
        }
        return count;
    }
}
