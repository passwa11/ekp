package com.landray.kmss.util.excel;

import org.apache.poi.ss.usermodel.CellStyle;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/** excel对象集
 * @author xuwh
 * @date 2022/6/16 15:54
 * @description
 */
public class ExcelObject {

    private List<String> sheetName;//页签名
    private List<String> cloumTittle;//列名
    private CellStyle cellStyle;//表头样式
    private WriteExcelDataDelegated writeExcelDataDelegated;//回调接口
    private OutputStream outputStream;//输出流
    private Integer perWriteRowCount ;//每次向excel的写入最大行，根据数据量的大小来设置每次写入的最大数据量
    private Integer perSheetWriteTimes ;//向excel中写入的次数。 根据每次写入的最大数据和总数来计算出写入的次数
    private Integer sheetCount ;//向excel中sheet需要创建的数量
    private Integer totalRowCount ;//总条数
    private Integer memoryRows ;//在内存当中保持行
    private String password = null ;// 设置密码
    private CellStyle contentSytle;//内容样式
    //页签名
    public List<String> getSheetName() {
        return sheetName;
    }

    // 页签名的设置数量要和 页签数 sheetCount 一致
    public void setSheetName(List<String> sheetName) {
        this.sheetName = sheetName;
    }
    //列标题名称
    public List<String> getCloumTittle() {
        return cloumTittle;
    }

    //设置列标题名称
    public void setCloumTittle(List<String> cloumTittle) {
        this.cloumTittle = cloumTittle;
    }

    //设置列标题单元格样式
    public CellStyle getCellStyle() {
        return cellStyle;
    }

    //设置列标题单元格样式，为空则取默认样式
    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }
    //回调函数
    public WriteExcelDataDelegated getWriteExcelDataDelegated() {
        return writeExcelDataDelegated;
    }
    //回调函数，需自行实现 WriteExcelDataDelegated 接口
    public void setWriteExcelDataDelegated(WriteExcelDataDelegated writeExcelDataDelegated) {
        this.writeExcelDataDelegated = writeExcelDataDelegated;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Integer getPerWriteRowCount() {
        return perWriteRowCount;
    }

    //设置每一次写入的数据量大小
    public void setPerWriteRowCount(Integer perWriteRowCount) {
        this.perWriteRowCount = perWriteRowCount;
    }

    public Integer getPerSheetWriteTimes() {
        return perSheetWriteTimes;
    }

    //设置需要写入excel的次数
    public void setPerSheetWriteTimes(Integer perSheetWriteTimes) {
        this.perSheetWriteTimes = perSheetWriteTimes;
    }

    public Integer getTotalRowCount() {
        return totalRowCount;
    }

    //设置导出的总数据量大小
    public void setTotalRowCount(Integer totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    public Integer getMemoryRows() {
        if(memoryRows == null)
            memoryRows = 100;
        return memoryRows;
    }

    //设置初始化excel的内存中的最大行，默认100
    public void setMemoryRows(Integer memoryRows) {
        this.memoryRows = memoryRows;
    }

    public String getPassword() {
        return password;
    }

    //设置页签是否加密
    public void setPassword(String password) {
        this.password = password;
    }

    public CellStyle getContentSytle() {
        return contentSytle;
    }

    //设置内容单元格样式
    public void setContentSytle(CellStyle contentSytle) {
        this.contentSytle = contentSytle;
    }

    public Integer getSheetCount() {
        return sheetCount;
    }

    //设置sheet页数量 需要与sheetName 保持一致
    public void setSheetCount(Integer sheetCount) {
        this.sheetCount = sheetCount;
    }
}
