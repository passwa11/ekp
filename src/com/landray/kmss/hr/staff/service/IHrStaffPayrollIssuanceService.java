package com.landray.kmss.hr.staff.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.forms.HrStaffPayrollIssuanceForm;
import com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance;

import net.sf.json.JSONObject;

public interface IHrStaffPayrollIssuanceService extends IBaseService {

	/**
	 * 构建一个导入模板文档
	 * 
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildTempletWorkBook(String fdExportDeptId)
			throws Exception;
	
	public HrStaffPayrollIssuance sendPayrollByEmail(
			HrStaffPayrollIssuance hrStaffPayrollIssuance,
			HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm)
			throws Exception;
	
	public JSONObject getSalaryContent(String fdId) throws Exception;
	
	public JSONObject updateSalaryTodoDone(String fdId) throws Exception;

}
