package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONObject;

public interface IHrOrganizationRankService extends IExtendDataService {

    public abstract List<HrOrganizationRank> findByFdGrade(HrOrganizationGrade fdGrade) throws Exception;

	/**
	 * <p>导入</p>
	 * @param inputStream
	 * @param locale
	 * @return
	 * @author sunj
	 */
	public abstract JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	/**
	 * <p>导出</p>
	 * @param hqlInfo
	 * @param request
	 * @return
	 * @author sunj
	 */
	public abstract WorkBook export(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;

	/**
	 * <p>下载导入模板</p>
	 * @param request
	 * @return
	 * @author sunj
	 */
	public abstract WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception;

	public HrOrganizationRank findByName(String fdName) throws Exception;

	public List<HrOrganizationRank> findByGradeId(String gradeId) throws Exception;

	public abstract boolean checkNameUnique(String fdName, String fdId,
			String fdGradeId) throws Exception;

	public HrOrganizationRank findRankByName(String fdName) throws Exception;
}
