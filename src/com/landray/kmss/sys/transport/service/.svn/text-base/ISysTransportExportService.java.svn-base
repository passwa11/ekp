package com.landray.kmss.sys.transport.service;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.util.excel.WorkBook;

public interface ISysTransportExportService extends IBaseService
{
	public WorkBook buildWorkBook(String fdId, Locale locale) throws Exception;
	public WorkBook buildWorkBook(String fdId, Locale locale, List modelList) throws Exception;
	
	/**
	 * 明细表通用下载模板
	 * @param request
	 * @param response
	 * @param locale
	 * @throws Exception
	 */
	public void deTableDownloadTemplate(HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception;
	
	/**
	 * 明细表通用导出模板 阅读状态
	 * 
	 * @param request
	 * @param response
	 * @param locale
	 * @throws Exception
	 */
	public void detailsTableExportData(HttpServletRequest request,
			HttpServletResponse response, Locale locale) throws Exception;

	/**
	 * 明细表通用导出模板 编辑状态
	 * 
	 * @param request
	 * @param response
	 * @param locale
	 * @throws Exception
	 */
	public void detailsTableExportDataInEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
