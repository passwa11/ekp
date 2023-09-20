package com.landray.kmss.sys.oms.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.oms.forms.SysOmsExcelImportForm;
import com.landray.kmss.util.KmssMessages;

/**
 * 组织机构基本信息导入
 * 
 * @author
 * 
 */
public interface ISysOmsExcelImportService {

	/**
	 * 导入数据
	 * 
	 * @param importForm
	 * @return
	 * @throws Exception
	 */
	public KmssMessages importData(SysOmsExcelImportForm excelImportForm) throws Exception;
	
	/**
	 * 构建导出的数据到工作本中
	 * 
	 * @throws Exception
	 */
	public HSSFWorkbook buildWorkBook() throws Exception;
	
	/**
	 * 构建导出的数据到工作本中
	 * 
	 * @throws Exception
	 */
	public HSSFWorkbook buildWorkBookNotEmpty(String identy) throws Exception;

	public JSONObject checkData(SysOmsExcelImportForm excelImportForm) throws Exception;
	

}
