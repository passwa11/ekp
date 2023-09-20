package com.landray.kmss.hr.organization.service;

import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.hr.organization.model.HrOrganizationPostSeq;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONObject;

public interface IHrOrganizationPostSeqService extends IExtendDataService {

	/**
	 * <p>下载模板</p>
	 * @param request
	 * @return
	 */
	WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception;

	/**
	 * <p>导入数据</p>
	 * @param inputStream
	 * @param locale
	 * @return
	 */
	JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	/**
	 * <p>通过name获取岗位序列</p>
	 * @param fdName
	 * @return
	 * @throws Exception
	 */
	public HrOrganizationPostSeq findByName(String fdName) throws Exception;

	public boolean checkNameUnique(String fdName, String fdId) throws Exception;
}
