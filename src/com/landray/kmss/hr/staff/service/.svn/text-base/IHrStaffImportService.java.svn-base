package com.landray.kmss.hr.staff.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.forms.HrStaffImportForm;
import com.landray.kmss.util.KmssMessage;

/**
 * 导入基类
 * 
 * @author 潘永辉 2017-1-13
 * 
 */
public interface IHrStaffImportService extends IBaseService {

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
	public KmssMessage saveImportData(HrStaffImportForm importForm,
			boolean isRollBack)
			throws Exception;

}
