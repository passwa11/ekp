package com.landray.kmss.sys.filestore.queue.dto;

import java.util.Date;

/**
 * 查询SQL使用的参数
 */
public class QueryParameter {
    private String[] converterKeys; // 转换类型
    private String convertType; // 转换的厂商
    private int status; // 状态
    private int PageNo; // 第几页
    private int RowSize; // 行数
    private Integer convertNumber; // 转换次数
    private Date date;

    public QueryParameter(String[] converterKeys, String convertType, int status, int pageNo,
                          int rowSize, Integer convertNumber, Date date) {
        this.converterKeys = converterKeys;
        this.convertType = convertType;
        this.status = status;
        PageNo = pageNo;
        RowSize = rowSize;
        this.convertNumber = convertNumber;
        this.date = date;
    }

    public QueryParameter() {

    }

    public String[] getConverterKeys() {
        return converterKeys;
    }

    public void setConverterKeys(String[] converterKeys) {
        this.converterKeys = converterKeys;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageNo() {
        return PageNo;
    }

    public void setPageNo(int pageNo) {
        PageNo = pageNo;
    }

    public int getRowSize() {
        return RowSize;
    }

    public void setRowSize(int rowSize) {
        RowSize = rowSize;
    }

    public Integer getConvertNumber() {
        return convertNumber;
    }

    public void setConvertNumber(Integer convertNumber) {
        this.convertNumber = convertNumber;
    }

    public String getConvertType() {
        return convertType;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
