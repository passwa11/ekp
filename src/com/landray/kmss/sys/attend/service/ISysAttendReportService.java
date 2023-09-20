package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.dao.HQLInfo;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;
/**
 * 月统计报表业务对象接口
 * 
 * @author 
 * @version 1.0 2017-07-27
 */
public interface ISysAttendReportService extends IBaseService {

	/**
	 * 查询月统计实时数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Page statList(RequestContext request) throws Exception;
	/**
	 * 获取月报表导出的HQL语句
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HQLInfo getExportHqlInfo(RequestContext request) throws Exception;
	/**
	 * 导出月统计实时数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(HQLInfo hqlInfo,RequestContext request) throws Exception;

	/**
	 * 是不是sysAttendReport的可阅读者
	 * 
	 * @return
	 */
	public boolean isReportReader();

	/**
	 * 定时任务发送考勤报表
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void sendAttendReport(SysQuartzJobContext context) throws Exception;
	public JSONObject syncAttendDatabase(RequestContext request) throws Exception;
	/**
	 * 保存sysAttendReport对应的月考勤报表
	 * 
	 * @param request
	 * @param fdReportId
	 * @throws Exception
	 */
	public void saveReportMonth(RequestContext request, String fdReportId)
			throws Exception;

	/**
	 * 删除sysAttendReport对应的月考勤报表
	 * 
	 * @param request
	 * @param fdReportId
	 * @throws Exception
	 */
	public void deleteReportMonth(RequestContext request, String fdReportId)
			throws Exception;

	/**
	 * 批量删除sysAttendReport对应的月考勤报表
	 * 
	 * @param request
	 * @param fdReportIds
	 * @throws Exception
	 */
	public void deleteReportMonth(RequestContext request, String[] fdReportIds)
			throws Exception;

	/**
	 * 查询月统计报表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Page monthList(RequestContext request) throws Exception;

	/**
	 * 导出月统计报表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportMonthExcel(RequestContext request)
			throws Exception;

	/**
	 * 查询日期区间统计数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Page listPeriod(RequestContext request) throws Exception;

	/**
	 * 导出日期区间统计数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportPeriod(HQLInfo hqlInfo,RequestContext request) throws Exception;
	/**
	 * 获取月报表导出的HQL语句 按照时间区间
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public HQLInfo getExportPeriodHqlInfo(RequestContext request) throws Exception;
	int syncAttendDatabase(String begin, String end) throws Exception;

}
