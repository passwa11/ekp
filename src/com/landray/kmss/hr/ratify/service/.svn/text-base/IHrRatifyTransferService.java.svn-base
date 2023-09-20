package com.landray.kmss.hr.ratify.service;

import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.excel.WorkBook;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

public interface IHrRatifyTransferService extends IHrRatifyMainService {

	public void schedulerJob(SysQuartzJobContext context) throws Exception;

	/**
	 * <p>员工关系-人事调动列表</p>
	 * @param hqlInfo
	 * @param type
	 * @return
	 * @author sunj
	 * @param request 
	 */
	public Page getTransferManagePage(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;

	/**
	 * <p>导出</p>
	 * @param hqlInfo
	 * @param request
	 * @return
	 * @author sunj
	 */
	public WorkBook export(HQLInfo hqlInfo, HttpServletRequest request) throws Exception;

	/**
	 * <p>批量导入员工异动信息</p>
	 * @param inputStream
	 * @param docTemplate
	 * @param locale
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception;

	/**
	 * <p>下载模板</p>
	 *  request
	 * @return
	 * @author sunj
	 */
	public WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception;

}
