package com.landray.kmss.sys.time.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.sys.time.forms.SysTimeImportForm;
import com.landray.kmss.util.KmssMessage;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-29
 */
public interface ISysTimeImportService {

	/**
	 * 构建一个导入模板文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildTempletWorkBook() throws Exception;

	/**
	 * 导入数据
	 * 
	 * @param importForm
	 * @param isRollBack
	 * @return
	 * @throws Exception
	 */
	public KmssMessage saveImportData(SysTimeImportForm importForm,
			boolean isRollBack) throws Exception;

}
