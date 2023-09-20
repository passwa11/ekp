package com.landray.kmss.hr.staff.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.util.ReportResult;

import net.sf.json.JSON;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public interface IHrStaffPersonReportService extends IBaseService {
	/**
	 * 执行统计(图表)
	 */
	public ReportResult statChart(IExtendForm form,
			RequestContext requestContext) throws Exception;

	/**
	 * 执行统计(列表)
	 */
	public JSON statList(IExtendForm form, RequestContext requestContext)
			throws Exception;

	/**
	 * 概况图表（饼图）
	 */
	public ReportResult statOverviewChart(String type) throws Exception;

	public String getStaffMobileStat(String orgId) throws Exception;
}
