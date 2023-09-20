package com.landray.kmss.util.excel;

import com.landray.kmss.common.service.IBaseService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.util.List;

/**
 * @author xuwh
 * @date 2022/6/2 9:51
 * @description
 */
public interface WriteExcelDataDelegated {
    /**
     * EXCEL写数据委托类  针对不同的情况自行实现
     *
     * @param startRowCount 开始行
     * @param pageSize      查询数据大小
     * @throws Exception
     */
    public abstract  List<List<String>> writeExcelData(Integer startRowCount,Integer pageSize) throws Exception;

}
