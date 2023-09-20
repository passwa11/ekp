package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONObject;

public interface IHrOrganizationGradeService extends IExtendDataService {

	/**
	 * <p>通过name获取职等</p>
	 * @param fdName
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public HrOrganizationGrade getHrOrgGradeByName(String fdName) throws Exception;

	/**
	 * <p>效验职等编码是否唯一</p>
	 * @param fdCode
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public boolean checkCodeUnique(String fdCode, String fdId) throws Exception;

	/**
	 * <p>下载导入模板</p>
	 * @param request
	 * @return
	 * @author sunj
	 */
	WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception;

	/**
	 * <p>导出</p>
	 * @param hqlInfo
	 * @param request
	 * @return
	 * @author sunj
	 */
	WorkBook export(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;

	/**
	 * <p>导入</p>
	 * @param inputStream
	 * @param locale
	 * @return
	 * @author sunj
	 */
	JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	public boolean checkNameUnique(String fdCode, String fdId) throws Exception;
}
