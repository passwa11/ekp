package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.hr.organization.model.HrOrganizationConPost;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONObject;

public interface IHrOrganizationConPostService extends IExtendDataService {

	public abstract List<HrOrganizationConPost> findByFdPerson(HrStaffPersonInfo fdPerson) throws Exception;

	public abstract List<HrOrganizationConPost> findByFdPost(HrOrganizationElement fdPost) throws Exception;

    public abstract List<HrOrganizationConPost> findByFdDept(HrOrganizationDept fdDept) throws Exception;

	public abstract List<HrOrganizationConPost> findByFdDuty(SysOrganizationStaffingLevel fdDuty) throws Exception;

	/**
	 * <p>下载模块</p>
	 * @param request
	 * @return
	 * @author sunj
	 */
	public abstract WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception;

	/**
	 * <p>导入数据</p>
	 * @param inputStream
	 * @param locale
	 * @return
	 * @author sunj
	 */
	public abstract JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	/**
	 * <p>批量结束兼职</p>
	 * @param ids
	 * @author sunj
	 */
	public abstract void updateFinishConPostByIds(String[] ids) throws Exception;
}
