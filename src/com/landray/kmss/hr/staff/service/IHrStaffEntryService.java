package com.landray.kmss.hr.staff.service;

import com.landray.kmss.hr.staff.forms.HrStaffEntryForm;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.excel.WorkBook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public interface IHrStaffEntryService extends IExtendDataService {
	/**
	 * 复制招聘申请里面的简历附件到人事档案
	 * 并且更新人员状态为已入职
	 * @param hrStaffPersonInfoTemp
	 * @param fdId
	 * @throws Exception
	 */
	public void clonHrApplicantAtt(HrStaffPersonInfo hrStaffPersonInfoTemp, String fdId) throws Exception;
	/**
	 * 手机号有唯一约束,通过手机号查找待入职员工
	 * 
	 * @param fdMobileNo
	 * @return
	 * @throws Exception
	 */
	public abstract HrStaffEntry findByFdMobileNo(String fdMobileNo)
			throws Exception;

	public void updateCheck(HrStaffEntryForm entryForm) throws Exception;

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
	 * @param baseForm
	 * @return
	 * @throws Exception
	 */
	public KmssMessage saveImportData(HrStaffEntryForm entryForm)
			throws Exception;

	/**
	 * 导出
	 * 
	 * @param ids
	 * @param fdStatus
	 * @return
	 * @throws Exception
	 */
	public WorkBook export(List<String> ids, String fdStatus) throws Exception;

	public Long getCountByDept(String deptId) throws Exception;

	public String getPersonInfo(String fdId) throws Exception;

}
