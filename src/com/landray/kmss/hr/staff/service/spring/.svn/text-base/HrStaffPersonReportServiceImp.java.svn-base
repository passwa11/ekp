package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffPersonReport;
import com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoSettingNewService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonReportService;
import com.landray.kmss.hr.staff.util.PeriodDateUtil;
import com.landray.kmss.hr.staff.util.ReportResult;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class HrStaffPersonReportServiceImp extends BaseServiceImp implements
		IHrStaffPersonReportService {
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private IHrOrganizationRankService hrOrganizationRankService;
	private IHrStaffPersonInfoSettingNewService hrStaffPersonInfoSettingService;
	private IHrStaffMoveRecordService hrStaffMoveRecordService;
	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	private ISysOrgElementService sysOrgElementService;

	
	public void setHrStaffMoveRecordService(IHrStaffMoveRecordService hrStaffMoveRecordService) {
		this.hrStaffMoveRecordService = hrStaffMoveRecordService;
	}
	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}
	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}
	public void setHrOrganizationRankService(
			IHrOrganizationRankService hrOrganizationRankService) {
		this.hrOrganizationRankService = hrOrganizationRankService;
	}
	public void setHrStaffPersonInfoSettingService(
			IHrStaffPersonInfoSettingNewService hrStaffPersonInfoSettingService) {
		this.hrStaffPersonInfoSettingService = hrStaffPersonInfoSettingService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private HrStaffPersonReport getHrStaffPersonReport(IExtendForm form,
			RequestContext requestContext) throws Exception {
		HrStaffPersonReport report = null;
		String method = requestContext.getParameter("method_GET");
		if ("view".equals(method)) {
			report = (HrStaffPersonReport) findByPrimaryKey(form.getFdId());
		} else {
			report = (HrStaffPersonReport) convertFormToModel(form,
					new HrStaffPersonReport(), requestContext);
		}
		// 如果是所有，则需要清空时间区域
		if ("all".equals(report.getFdPeriod())) {
			report.setFdBeginPeriod(null);
			report.setFdEndPeriod(null);
		} else if (!"custom".equals(report.getFdPeriod())) {
			Date[] periodDates = getPeriodDates(report.getFdPeriod());
			if (periodDates != null) {
				report.setFdBeginPeriod(periodDates[0]);
				report.setFdEndPeriod(periodDates[1]);
			}
		}
		String queryIds =report.getFdQueryIds();
		if(StringUtil.isNotNull(queryIds)) {
			queryIds =URLDecoder.decode(queryIds,"UTF-8");
			report.setFdQueryIds(queryIds);
		}
		return report;
	}

	@Override
	public ReportResult statChart(IExtendForm form,
			RequestContext requestContext) throws Exception {
		HrStaffPersonReport report = getHrStaffPersonReport(form,
				requestContext);
		ReportResult result = null;

		// 统计data
		if ("reportStaffNum".equals(report.getFdReportType())) { // 人数统计
			result = buildChartForStaffNum(report);
		} else if ("reportAge".equals(report.getFdReportType())) { // 年龄结构
			result = buildChartForAge(report);
		} else if ("reportPersonnelMonthlyReportStaffEntryAndExit".equals(report.getFdReportType())) { // 年龄结构
			result = buildChartForPersonnelMonthlyReportStaffEntryAndExit(report);
		} else if ("reportWorkTime".equals(report.getFdReportType())) { // 司龄分布
			result = buildChartForWorkTime(report);
		} else if ("reportEducation".equals(report.getFdReportType())) { // 学历分布
			result = buildChartForEducation(report);
		} else if ("reportStaffingLevel".equals(report.getFdReportType())) { // 职务等级
			result = buildChartForStaffingLevel(report);
		} else if ("reportStatus".equals(report.getFdReportType())) { // 异动情况
			result = buildChartForStatus(report);
		} else if ("reportMarital".equals(report.getFdReportType())) { // 婚姻状况
			result = buildChartForMarital(report);
		}

		// 题头相关信息
		result.getTitle().put("text", report.getFdName());

		String queryNames =report.getFdQueryNames();
		if(StringUtil.isNotNull(queryNames)) {
			queryNames =URLDecoder.decode(queryNames,"UTF-8");
			queryNames=queryNames.replaceAll(";", ",");
		}
		if ("reportPersonnelMonthlyReportStaffEntryAndExit".equals(report.getFdReportType())){
			HQLInfo hqlInfo1 = new HQLInfo();
			hqlInfo1.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType");
			hqlInfo1.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
			@SuppressWarnings("unchecked")
			List<SysOrgElement> list1 = sysOrgElementService.findList(hqlInfo1);
			HQLInfo hqlInfo2 = new HQLInfo();
			hqlInfo2.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType and sysOrgElement.hbmParent=:hbmParent");
			hqlInfo2.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_DEPT);
			hqlInfo2.setParameter("hbmParent", list1.get(0));
			@SuppressWarnings("unchecked")
			List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo2);
			String str = "";
			for(int i=0;i<list.size();i++){
				if(i==list.size()-1)
					str+=list.get(i).getFdName();
				else
					str=str+list.get(i).getFdName()+",";
			}
			queryNames=str;
		}
		// X轴信息
		result.addXAxis("", "category", queryNames);
		// Y轴信息
		result.addYAxis(ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdPersonNumber"),
				"value", "");
		return result;
	}
	
	private ReportResult buildChartForPersonnelMonthlyReportStaffEntryAndExit(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();
		HQLInfo hqlInfo1 = new HQLInfo();
		hqlInfo1.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType");
		hqlInfo1.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
		@SuppressWarnings("unchecked")
		List<SysOrgElement> list1 = sysOrgElementService.findList(hqlInfo1);
		HQLInfo hqlInfo2 = new HQLInfo();
		hqlInfo2.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType and sysOrgElement.hbmParent=:hbmParent");
		hqlInfo2.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_DEPT);
		hqlInfo2.setParameter("hbmParent", list1.get(0));
		@SuppressWarnings("unchecked")
		List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo2);
		// 统计data
		
		String fdStaffType = report.getFdStaffType();
		int fdMonth = report.getFdMonth();
		String queryIds = report.getFdQueryIds();
		List<Double> counts = null;
		Map<String, Object> seriesData = null;
//		String whereBlock = null;
		for (String personnelMonthlyReportStaffEntryAndExit : getPersonnelMonthlyReportStaffEntryAndExit()) {
			counts = new ArrayList<Double>();
			seriesData = new HashMap<String, Object>();
			for(int i=0;i<list.size();i++){
//				hqlInfo = getBaseHQLInfoForOverview();
				if(personnelMonthlyReportStaffEntryAndExit=="当月流动率%"){
					Map<String, Object> seriesData0  = result.getSeriesData().get(0);
					List<Double> counts0 = (List<Double>) seriesData0.get("data");
					Map<String, Object> seriesData1  = result.getSeriesData().get(1);
					List<Double> counts1 = (List<Double>) seriesData1.get("data");
					Map<String, Object> seriesData2  = result.getSeriesData().get(2);
					List<Double> counts2 = (List<Double>) seriesData2.get("data");
					Map<String, Object> seriesData3  = result.getSeriesData().get(3);
					List<Double> counts3 = (List<Double>) seriesData3.get("data");
					counts.add((counts2.get(i)+counts3.get(i))/((counts0.get(i)+counts1.get(i))/2)*100);
					continue;
				}
				if(personnelMonthlyReportStaffEntryAndExit=="当月离职率%"){
					Map<String, Object> seriesData0  = result.getSeriesData().get(0);
					List<Double> counts0 = (List<Double>) seriesData0.get("data");
					Map<String, Object> seriesData1  = result.getSeriesData().get(1);
					List<Double> counts1 = (List<Double>) seriesData1.get("data");
					Map<String, Object> seriesData3  = result.getSeriesData().get(3);
					List<Double> counts3 = (List<Double>) seriesData3.get("data");
					counts.add((counts3.get(i))/((counts0.get(i)+counts1.get(i))/2)*100);
					continue;
				}
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock("count(*)");
				String whereBlock = "1=1";
				if(personnelMonthlyReportStaffEntryAndExit=="累计离职率%"){
					Map<String, Object> seriesData8  = result.getSeriesData().get(8);
					List<Double> counts8 = (List<Double>) seriesData8.get("data");
					Double a = getCountForPersonnelMonthlyReportStaffEntryAndExit(hqlInfo, whereBlock, personnelMonthlyReportStaffEntryAndExit,
							list.get(i).getFdId(),fdMonth,fdStaffType,list.get(i));
					counts.add(counts8.get(i)/a*100);
					continue;
				}
				counts.add(
						getCountForPersonnelMonthlyReportStaffEntryAndExit(hqlInfo, whereBlock, personnelMonthlyReportStaffEntryAndExit,
								list.get(i).getFdId(),fdMonth,fdStaffType,list.get(i)));
			}
//			if (StringUtil.isNotNull(queryIds)) {
//				for (String id : queryIds.split(";")) {
//					hqlInfo = getBaseHQLInfoForOverview();
//					whereBlock = hqlInfo.getWhereBlock();
//					counts.add(
//							getCountForPersonnelMonthlyReportStaffEntryAndExit(hqlInfo, whereBlock, personnelMonthlyReportStaffEntryAndExit,
//									id));
//				}
//			} else {
//				hqlInfo = getBaseHQLInfoForOverview();
//				whereBlock = hqlInfo.getWhereBlock();
//				counts.add(getCountForPersonnelMonthlyReportStaffEntryAndExit(hqlInfo, whereBlock, personnelMonthlyReportStaffEntryAndExit,
//						""));
//			}
			if ("null".equals(personnelMonthlyReportStaffEntryAndExit)) {
				personnelMonthlyReportStaffEntryAndExit = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			seriesData.put("name", personnelMonthlyReportStaffEntryAndExit);
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}
	/**
	 * 人数统计
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForStaffNum(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();
		// 获取部门人数
		String queryIds = report.getFdQueryIds();
		List<Long> counts = new ArrayList<Long>();
		HQLInfo hqlInfo = null;
		for (String id : queryIds.split(";")) {
			hqlInfo = buildQueryHql(report);
			counts.add(getCountForDept(hqlInfo, id));
		}

		// 统计data
		Map<String, Object> seriesData = new HashMap<String, Object>();
		seriesData.put("name", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.type."
						+ report.getFdReportType()));
		seriesData.put("type", "bar");
		seriesData.put("data", counts);
		result.getSeriesData().add(seriesData);

		return result;
	}

	/**
	 * 年龄结构
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForAge(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		int fdAgeRange = report.getFdAgeRange();
		int maxRange = 55; // 最大上限
		int minRange = 25; // 最小下限

		List<int[]> ageRangList = getAgeRange(fdAgeRange, maxRange, minRange);
		// 增加最小下限
		ageRangList.add(0, new int[] { 0, minRange });
		int ageRangeSize = ageRangList.size();
		for (int i = 0; i <= ageRangeSize; i++) {
			boolean flag = (i == ageRangeSize);
			int[] ageRang = !flag ? ageRangList.get(i) : null;
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = getBaseHQLInfoForOverview();
					whereBlock = hqlInfo.getWhereBlock();
					counts.add(
							getCountForAge(hqlInfo, whereBlock, ageRang, id));
				}
			} else {
				hqlInfo = getBaseHQLInfoForOverview();
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(getCountForAge(hqlInfo, whereBlock, ageRang, ""));
			}

			if (flag) {
				seriesData.put("name", ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown"));
			} else if (i == ageRangList.size() - 1) {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.age.over", "hr-staff", null,
						ageRang[0]));

			} else if (i == 0) {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.age.under", "hr-staff", null,
						ageRang[1]));
			} else {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.age", "hr-staff", null,
						new Object[] { ageRang[0], ageRang[1] }));
			}
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	/**
	 * 司龄分布
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForWorkTime(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = new ArrayList<Long>();
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = new HashMap<String, Object>();
		String whereBlock = null;

		List<int[]> ageRangList = getAgeRangeForWorkTime();
		int ageRangSize = ageRangList.size();
		for (int i = 0; i <= ageRangSize; i++) {
			boolean flag = (i == ageRangSize);
			int[] ageRang = !flag ? ageRangList.get(i) : null;
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = getBaseHQLInfoForOverview();
					whereBlock = hqlInfo.getWhereBlock();
					counts
							.add(getCountForWorkTime(hqlInfo, whereBlock,
									ageRang,
									id));
				}
			} else {
				hqlInfo = getBaseHQLInfoForOverview();
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(
						getCountForWorkTime(hqlInfo, whereBlock, ageRang, ""));
			}
			if (flag) {
				seriesData.put("name", ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown"));
			} else if (i == ageRangList.size() - 1) {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.workTime.over", "hr-staff", null,
						ageRang[0]));
			} else if (i == 0) {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.workTime.under", "hr-staff", null,
						ageRang[1]));
			} else {
				seriesData.put("name", ResourceUtil.getString(
						"hrStaffPersonReport.workTime", "hr-staff", null,
						new Object[] { ageRang[0], ageRang[1] }));
			}
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	/**
	 * 学历分布
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForEducation(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		for (String education : getEducations()) {
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = getBaseHQLInfoForOverview();
					whereBlock = hqlInfo.getWhereBlock();
					counts.add(
							getCountForEducation(hqlInfo, whereBlock, education,
									id));
				}
			} else {
				hqlInfo = getBaseHQLInfoForOverview();
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(getCountForEducation(hqlInfo, whereBlock, education,
						""));
			}
			if ("null".equals(education)) {
				education = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}

			seriesData.put("name", education);
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	/**
	 * 职务等级
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForStaffingLevel(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		for (Object[] staffingLevel : getStaffingLevels()) {
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			for (String id : queryIds.split(";")) {
				hqlInfo = buildQueryHql(report);
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(getCountForStaffingLevel(hqlInfo, whereBlock,
						staffingLevel[0].toString(), id));
			}

			String staffing = staffingLevel[1].toString();
			if ("null".equals(staffing)) {
				staffing = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}

			seriesData.put("name", staffing);
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	/**
	 * 异动情况
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForStatus(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		String[] fdStatus = report.getFdStatus().split(";|,");

		for (String fdStatu : fdStatus) {
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = buildQueryHql(report);
					whereBlock = hqlInfo.getWhereBlock();
					counts.add(getCountForStatus(hqlInfo, whereBlock, fdStatu,
							id));
				}
			} else {
				hqlInfo = buildQueryHql(report);
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(getCountForStatus(hqlInfo, whereBlock, fdStatu,
						""));
			}


			seriesData.put("name",
					ResourceUtil
							.getString("hr-staff:hrStaffPersonInfo.fdStatus."
									+ fdStatu));
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	/**
	 * 婚姻状况
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult buildChartForMarital(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		for (String marital : getMaritals()) {
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = getBaseHQLInfoForOverview();
					whereBlock = hqlInfo.getWhereBlock();
					counts
							.add(getCountForMarital(hqlInfo, whereBlock,
									marital,
									id));
				}
			} else {
				hqlInfo = getBaseHQLInfoForOverview();
				whereBlock = hqlInfo.getWhereBlock();
				counts
						.add(getCountForMarital(hqlInfo, whereBlock,
								marital,
								""));
			}


			if ("null".equals(marital)) {
				marital = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}

			seriesData.put("name", marital);
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	@Override
	public JSON statList(IExtendForm form, RequestContext requestContext)
			throws Exception {
		HrStaffPersonReport report = getHrStaffPersonReport(form,
				requestContext);
		Map<String, Object> result = null;
		if ("reportStaffNum".equals(report.getFdReportType())) { // 人数统计
			result = buildListForStaffNum(report);
		} else if ("reportPersonnelMonthlyReportStaffEntryAndExit".equals(report.getFdReportType())) { // 年龄结构
			result = buildListForPersonnelMonthlyReportStaffEntryAndExit(report);
		} else if ("reportAge".equals(report.getFdReportType())) { // 年龄结构
			result = buildListForAge(report);
		} else if ("reportWorkTime".equals(report.getFdReportType())) { // 司龄分布
			result = buildListForWorkTime(report);
		} else if ("reportEducation".equals(report.getFdReportType())) { // 学历分布
			result = buildListForEducation(report);
		} else if ("reportStaffingLevel".equals(report.getFdReportType())) { // 职务等级
			result = buildListForStaffingLevel(report);
		} else if ("reportStatus".equals(report.getFdReportType())) { // 异动情况
			result = buildListForStatus(report);
		} else if ("reportMarital".equals(report.getFdReportType())) { // 婚姻状况
			result = buildListForMarital(report);
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 人数统计
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForStaffNum(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		HQLInfo hqlInfo = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("count", getCountForDept(hqlInfo, orgIds[i]));
			data.put("orgName", orgNames[i]);
			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = new HashMap<String, Object>();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		fileds.put("count", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdPersonNumber"));
		result.put("fileds", fileds);
		return result;
	}
	private Map<String, Object> buildListForPersonnelMonthlyReportStaffEntryAndExit(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		HQLInfo hqlInfo1 = new HQLInfo();
		hqlInfo1.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType");
		hqlInfo1.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_ORG);
		@SuppressWarnings("unchecked")
		List<SysOrgElement> list1 = sysOrgElementService.findList(hqlInfo1);
		HQLInfo hqlInfo2 = new HQLInfo();
		hqlInfo2.setWhereBlock( "sysOrgElement.fdOrgType = :fdOrgType and sysOrgElement.hbmParent=:hbmParent");
		hqlInfo2.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_DEPT);
		hqlInfo2.setParameter("hbmParent", list1.get(0));
		@SuppressWarnings("unchecked")
		List<SysOrgElement> list = sysOrgElementService.findList(hqlInfo2);
//		String[] orgIds = report.getFdQueryIds().split(";");
//		String[] orgNames = report.getFdQueryNames().split(";");

		List<String> personnelMonthlyReportStaffEntryAndExits = getPersonnelMonthlyReportStaffEntryAndExit();

		String fdStaffType = report.getFdStaffType();
		int fdMonth = report.getFdMonth();
//		HQLInfo hqlInfo = null;
//		String whereBlock = null;
//		for (int i = 0; i < orgIds.length; i++) {
		for(SysOrgElement sysOrgElement : list){
//			hqlInfo = buildQueryHql1(report);
			data = new HashMap<String, Object>();
			data.put("orgId", sysOrgElement.getFdId());
			data.put("orgName", sysOrgElement.getFdName());

//			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < personnelMonthlyReportStaffEntryAndExits.size(); j++) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock("count(*)");
				String whereBlock = "1=1";
				data.put("z_count_" + j, getCountForPersonnelMonthlyReportStaffEntryAndExit(hqlInfo,
						whereBlock, personnelMonthlyReportStaffEntryAndExits.get(j), sysOrgElement.getFdId(),fdMonth,fdStaffType,sysOrgElement));
			}

			datas.add(data);
		}
//		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < personnelMonthlyReportStaffEntryAndExits.size(); j++) {
			String personnelMonthlyReportStaffEntryAndExit = personnelMonthlyReportStaffEntryAndExits.get(j);
			if ("null".equals(personnelMonthlyReportStaffEntryAndExit)) {
				personnelMonthlyReportStaffEntryAndExit = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			fileds.put("z_count_" + j, personnelMonthlyReportStaffEntryAndExit);
		}

		result.put("fileds", fileds);
		return result;
	}
	
	/**
	 * 年龄结构
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForAge(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		int fdAgeRange = report.getFdAgeRange();
		int maxRange = 60; // 最大上限
		int minRange = 20; // 最小下限
		List<int[]> ageRangList = getAgeRange(fdAgeRange, maxRange, minRange);
		// 增加最小下限
		ageRangList.add(0, new int[] { 0, minRange });

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < ageRangList.size(); j++) {
				data.put("z_count_" + j, getCountForAge(hqlInfo, whereBlock,
						ageRangList.get(j), orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < ageRangList.size(); j++) {
			int[] ageRang = ageRangList.get(j);
			if (j == ageRangList.size() - 1) {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.age.over", "hr-staff", null,
						ageRang[0]));
			} else if (j == 0) {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.age.under", "hr-staff", null,
						ageRang[1]));
			} else {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.age", "hr-staff", null,
						new Object[] { ageRang[0], ageRang[1] }));
			}
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 司龄分布
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForWorkTime(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		int fdAgeRange = report.getFdAgeRange();
		int maxRange = 30; // 最大上限
		int minRange = 0; // 最小下限
		List<int[]> ageRangList = getAgeRange(fdAgeRange, maxRange, minRange);
		// 修改最小下限
		int[] first = ageRangList.get(0);
		first[0] = 0;
		ageRangList.set(0, first);

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < ageRangList.size(); j++) {
				data.put("z_count_" + j, getCountForWorkTime(hqlInfo,
						whereBlock, ageRangList.get(j), orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < ageRangList.size(); j++) {
			int[] ageRang = ageRangList.get(j);
			if (j == ageRangList.size() - 1) {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.workTime.over", "hr-staff", null,
						ageRang[0]));
			} else if (j == 0) {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.workTime.under", "hr-staff", null,
						ageRang[1]));
			} else {
				fileds.put("z_count_" + j, ResourceUtil.getString(
						"hrStaffPersonReport.workTime", "hr-staff", null,
						new Object[] { ageRang[0], ageRang[1] }));
			}
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 学历分布
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForEducation(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		List<String> educations = getEducations();

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < educations.size(); j++) {
				data.put("z_count_" + j, getCountForEducation(hqlInfo,
						whereBlock, educations.get(j), orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < educations.size(); j++) {
			String education = educations.get(j);
			if ("null".equals(education)) {
				education = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			fileds.put("z_count_" + j, education);
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 职务等级
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForStaffingLevel(
			HrStaffPersonReport report) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		List<Object[]> staffingLevels = getStaffingLevels();

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < staffingLevels.size(); j++) {
				data.put("z_count_" + j, getCountForStaffingLevel(hqlInfo,
						whereBlock, staffingLevels.get(j)[0].toString(),
						orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < staffingLevels.size(); j++) {
			String staffingLevel = staffingLevels.get(j)[1].toString();
			if ("null".equals(staffingLevel)) {
				staffingLevel = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			fileds.put("z_count_" + j, staffingLevel);
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 异动情况
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForStatus(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		String[] fdStatus = report.getFdStatus().split(";");

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < fdStatus.length; j++) {
				data.put("z_count_" + j, getCountForStatus(hqlInfo, whereBlock,
						fdStatus[j], orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < fdStatus.length; j++) {
			fileds.put("z_count_" + j, ResourceUtil
					.getString("hr-staff:hrStaffPersonInfo.fdStatus."
							+ fdStatus[j]));
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 婚姻状况
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> buildListForMarital(HrStaffPersonReport report)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 获取部门人数
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = null;
		String[] orgIds = report.getFdQueryIds().split(";");
		String[] orgNames = report.getFdQueryNames().split(";");

		List<String> maritals = getMaritals();

		HQLInfo hqlInfo = null;
		String whereBlock = null;
		for (int i = 0; i < orgIds.length; i++) {
			hqlInfo = buildQueryHql(report);
			data = new HashMap<String, Object>();
			data.put("orgId", orgIds[i]);
			data.put("orgName", orgNames[i]);

			whereBlock = hqlInfo.getWhereBlock();
			for (int j = 0; j < maritals.size(); j++) {
				data.put("z_count_" + j, getCountForMarital(hqlInfo,
						whereBlock, maritals.get(j), orgIds[i]));
			}

			datas.add(data);
		}
		result.put("datas", datas);

		// 字段
		Map<String, Object> fileds = getOrderMap();
		fileds.put("orgName", ResourceUtil
				.getString("hr-staff:hrStaffPersonReport.fdDept"));
		for (int j = 0; j < maritals.size(); j++) {
			String education = maritals.get(j);
			if ("null".equals(education)) {
				education = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			fileds.put("z_count_" + j, education);
		}

		result.put("fileds", fileds);
		return result;
	}

	/**
	 * 获取所有的职务
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getStaffingLevels() throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "null", "null" });
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setSelectBlock("sysOrganizationStaffingLevel.fdId, sysOrganizationStaffingLevel.fdName");
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setOrderBy("sysOrganizationStaffingLevel.fdLevel");
		list.addAll(sysOrganizationStaffingLevelService.findValue(hqlInfo));
		return list;
	}

	/**
	 * 获取所有婚姻
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getMaritals() throws Exception {
		List<HrStaffPersonInfoSettingNew> settings = hrStaffPersonInfoSettingService
				.getByType("fdMaritalStatus");
		List<String> maritals = new ArrayList<String>();
		if (settings != null) {
			for (HrStaffPersonInfoSettingNew setting : settings) {
				maritals.add(setting.getFdName());
			}
		}
		maritals.add("null");
		return maritals;
	}

	/**
	 * 获取所有学历
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getEducations() throws Exception {
		List<HrStaffPersonInfoSettingNew> settings = hrStaffPersonInfoSettingService
				.getByType("fdHighestEducation");
		List<String> educations = new ArrayList<String>();
		if (settings != null) {
			for (HrStaffPersonInfoSettingNew setting : settings) {
				educations.add(setting.getFdName());
			}
		}
		educations.add("null");
		return educations;
	}
	private List<String> getPersonnelMonthlyReportStaffEntryAndExit() throws Exception {
		
		List<String> personnelMonthlyReportStaffEntryAndExit = new ArrayList<String>(){
			{	
				add("月初总数");
				add("月末总数");
				add("当月新进员工人数");
				add("当月离职员工人数");
				add("当月调入人数");
				add("当月调出人数");
				add("当月流动率%");
				add("当月离职率%");
				add("累计离职人数");
				add("累计离职率%");
			}
		};
		
		return personnelMonthlyReportStaffEntryAndExit;
	}
	/**
	 * 获取年龄区间
	 * 
	 * @param fdAgeRange
	 * @param maxRange
	 * @param minRange
	 * @return
	 */
	private List<int[]> getAgeRange(int fdAgeRange, int maxRange, int minRange) {
		List<int[]> list = new ArrayList<int[]>();
		int _current = minRange;
		while (_current < maxRange) {
			int b = _current;
			_current += fdAgeRange;
			list.add(new int[] { b, _current });
			if (_current > maxRange) {
				break;
			}
		}
		// 加入最大上限
		list.add(new int[] { _current, 100 });
		return list;
	}

	/**
	 * 获取区间日期
	 * 
	 * @param requestContext
	 * @return
	 */
	private Date[] getPeriodDates(String fdPeriod) {
		Date[] dates = null;
		if ("thisMonth".equals(fdPeriod)) { // 本月
			dates = PeriodDateUtil.getCurrentMonth();
		} else if ("lastMonth".equals(fdPeriod)) { // 上一月
			dates = PeriodDateUtil.getLastMonth();
		} else if ("thisSeason".equals(fdPeriod)) { // 本季
			dates = PeriodDateUtil.getCurrentSeason();
		} else if ("lastSeason".equals(fdPeriod)) { // 上一季
			dates = PeriodDateUtil.getLastSeason();
		} else if ("thisYear".equals(fdPeriod)) { // 本年
			dates = PeriodDateUtil.getCurrentYear();
		} else if ("lastYear".equals(fdPeriod)) { // 上一年
			dates = PeriodDateUtil.getLastYear();
		}
		return dates;
	}

	/**
	 * 年龄区间
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param staffingLevelId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForAge(HQLInfo hqlInfo, String whereBlock,
			int[] ageRang, String orgId) throws Exception {
		if (ageRang != null) {
			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.YEAR, -ageRang[0]);

			Calendar c2 = Calendar.getInstance();
			c2.add(Calendar.YEAR, -ageRang[1]);

			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdDateOfBirth >= :benimTime and hrStaffPersonInfo.fdDateOfBirth < :endTime)");
			hqlInfo.setParameter("benimTime", c2.getTime());
			hqlInfo.setParameter("endTime", c1.getTime());
		} else {
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdDateOfBirth is null");
		}
		if (StringUtil.isNotNull(orgId)) {
			return getCountForDept(hqlInfo, orgId);
		} else {
			return (Long) hrStaffPersonInfoService.findValue(hqlInfo).get(0);
		}

	}

	/**
	 * 司龄区间
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param staffingLevelId
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForWorkTime(HQLInfo hqlInfo, String whereBlock,
			int[] ageRang, String orgId) throws Exception {
		if (ageRang != null) {
			Calendar c1 = Calendar.getInstance();
			c1.add(Calendar.YEAR, -ageRang[0]);
			Calendar c2 = Calendar.getInstance();
			c2.add(Calendar.YEAR, -ageRang[1]);

			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdTimeOfEnterprise >= :benimTime and hrStaffPersonInfo.fdTimeOfEnterprise < :endTime)");
			hqlInfo.setParameter("benimTime", c2.getTime());
			hqlInfo.setParameter("endTime", c1.getTime());
		} else {
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdTimeOfEnterprise is null");
		}

		return getCountForDept(hqlInfo, orgId);
	}

	/**
	 * 获取状态数量
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param education
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForStatus(HQLInfo hqlInfo, String whereBlock,
			String fdStatus, String orgId) throws Exception {
		hqlInfo.setWhereBlock(whereBlock
				+ " and hrStaffPersonInfo.fdStatus =:_fdStatus");
		hqlInfo.setParameter("_fdStatus", fdStatus);
		if (StringUtil.isNotNull(orgId)) {
			return getCountForDept(hqlInfo, orgId);
		} else {
			List<Long> list = hrStaffPersonInfoService.findValue(hqlInfo);
			return list.get(0);
		}

	}

	/**
	 * 获取职务数量
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param education
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForStaffingLevel(HQLInfo hqlInfo, String whereBlock,
			String staffingLevelId, String orgId) throws Exception {
		long count = 0;
		if ("null".equals(staffingLevelId)) {
			// 统计组织机构人员
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdStaffingLevel is null)");
			count += getCountForDept(hqlInfo, orgId);
			// 统计人事档案人员
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdStaffingLevel is null)");
			count += getCountForDept(hqlInfo, orgId);
		} else {
			hqlInfo.setParameter("staffingLevelId", staffingLevelId);

			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdStaffingLevel.fdId = :staffingLevelId)");
			count += getCountForDept(hqlInfo, orgId);
			// 统计人事档案人员
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and (hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdStaffingLevel.fdId = :staffingLevelId)");
			count += getCountForDept(hqlInfo, orgId);
		}
		return count;
	}

	/**
	 * 获取婚姻数量
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param education
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForMarital(HQLInfo hqlInfo, String whereBlock,
			String marital, String orgId) throws Exception {
		if ("null".equals(marital)) {
			hqlInfo.setWhereBlock(whereBlock
					+ " and (hrStaffPersonInfo.fdMaritalStatus is null or hrStaffPersonInfo.fdMaritalStatus = :fdMaritalStatus)");
			hqlInfo.setParameter("fdMaritalStatus", "");
		} else {
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and hrStaffPersonInfo.fdMaritalStatus = :fdMaritalStatus");
			hqlInfo.setParameter("fdMaritalStatus", marital);
		}

		return getCountForDept(hqlInfo, orgId);
	}

	/**
	 * 获取学历数量
	 * 
	 * @param hqlInfo
	 * @param whereBlock
	 * @param education
	 * @param orgId
	 * @return
	 * @throws Exception
	 */
	private Long getCountForEducation(HQLInfo hqlInfo, String whereBlock,
			String education, String orgId) throws Exception {
		if ("null".equals(education)) {
			hqlInfo.setWhereBlock(whereBlock
					+ " and (hrStaffPersonInfo.fdHighestEducation is null or hrStaffPersonInfo.fdHighestEducation = :fdHighestEducation)");
			hqlInfo.setParameter("fdHighestEducation", "");

		} else {
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and hrStaffPersonInfo.fdHighestEducation = :fdHighestEducation");
			hqlInfo.setParameter("fdHighestEducation", education);
		}

		return getCountForDept(hqlInfo, orgId);
	}
	private Double getCountForPersonnelMonthlyReportStaffEntryAndExit(HQLInfo hqlInfo, String whereBlock,
			String personnelMonthlyReportStaffEntryAndExit, String orgId,int fdMonth,String fdStaffType,SysOrgElement sysOrgElement) throws Exception {
//		if ("null".equals(personnelMonthlyReportStaffEntryAndExit)) {
//			hqlInfo.setWhereBlock(whereBlock
//					+ " and (hrStaffPersonInfo.fdHighestEducation is null or hrStaffPersonInfo.fdOrgRank.getFdId() = :fdHighestEducation)");
//			hqlInfo.setParameter("fdHighestEducation", "");

//		} else {
			if(personnelMonthlyReportStaffEntryAndExit=="月初总数"){
				whereBlock+=" and hrStaffPersonInfo.fdEntryTime < :fdEntryTime3";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime = new Date();
				if(fdMonth==1)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				Timestamp timestamp = new Timestamp(fdEntryTime.getTime());
				hqlInfo.setParameter("fdEntryTime3", timestamp);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="月末总数"){
				whereBlock+=" and hrStaffPersonInfo.fdEntryTime < :fdEntryTime4";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime = new Date();
				if(fdMonth==1)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
						DateUtil.TYPE_DATE, null);
				Timestamp timestamp = new Timestamp(fdEntryTime.getTime());
				hqlInfo.setParameter("fdEntryTime4", timestamp);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="累计离职率%"){
				whereBlock+=" and hrStaffPersonInfo.fdEntryTime < :fdEntryTime4";
				String whereBlock1 = "1=1";
				whereBlock1+=" and hrStaffPersonInfo.fdEntryTime < :fdEntryTime6";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime = new Date();
				Date fdEntryTime1 = new Date();
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
						DateUtil.TYPE_DATE, null);
				Timestamp timestamp = new Timestamp(fdEntryTime.getTime());
				Timestamp timestamp1 = new Timestamp(fdEntryTime1.getTime());
				hqlInfo.setParameter("fdEntryTime4", timestamp);
				HQLInfo hqlInfo1 = new HQLInfo();
				hqlInfo1.setParameter("fdEntryTime6", timestamp1);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
				whereBlock1+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo1.setParameter("fdStatus", status);
				whereBlock+=" and hrStaffPersonInfo.fdStaffType like :fdStaffType";
				whereBlock1+=" and hrStaffPersonInfo.fdStaffType like :fdStaffType";
				if(fdStaffType.equals("海格正编")){
					hqlInfo.setParameter("fdStaffType", "%正编%");
					hqlInfo1.setParameter("fdStaffType", "%正编%");
				}
				if(fdStaffType.equals("派遣员工")){
					hqlInfo.setParameter("fdStaffType", "%派遣%");
					hqlInfo1.setParameter("fdStaffType", "%派遣%");
				}
				if(fdStaffType.equals("实习生")){
					hqlInfo.setParameter("fdStaffType", "%实习生%");
					hqlInfo1.setParameter("fdStaffType", "%实习生%");
				}
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo1.setWhereBlock(whereBlock1);
				hqlInfo1.setSelectBlock("count(*)");
			return (getCountForDept1(hqlInfo, orgId)+getCountForDept1(hqlInfo1, orgId))/2;
			}
			if(personnelMonthlyReportStaffEntryAndExit=="累计离职人数"){
				whereBlock+=" and hrStaffPersonInfo.fdLeaveTime BETWEEN :fdLeaveTime1 and :fdLeaveTime2";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdLeaveTime1 = new Date();
				Date fdLeaveTime2 = new Date();
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
					fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==2)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==3)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==4)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==5)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==6)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==7)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==8)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==9)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==10)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==11)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==12)
						fdLeaveTime2=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
							DateUtil.TYPE_DATE, null);
				Timestamp timestamp1 = new Timestamp(fdLeaveTime1.getTime());
				Timestamp timestamp2 = new Timestamp(fdLeaveTime2.getTime());
				hqlInfo.setParameter("fdLeaveTime1", timestamp1);
				hqlInfo.setParameter("fdLeaveTime2", timestamp2);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="当月新进员工人数"){
				whereBlock+=" and hrStaffPersonInfo.fdEntryTime BETWEEN :fdEntryTime1 and :fdEntryTime2";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime1 = new Date();
				Date fdEntryTime2 = new Date();
				if(fdMonth==1)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==2)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==3)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==4)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==5)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==6)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==7)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==8)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==9)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==10)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==11)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==12)
					fdEntryTime2=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
							DateUtil.TYPE_DATE, null);
				Timestamp timestamp1 = new Timestamp(fdEntryTime1.getTime());
				Timestamp timestamp2 = new Timestamp(fdEntryTime2.getTime());
				hqlInfo.setParameter("fdEntryTime1", timestamp1);
				hqlInfo.setParameter("fdEntryTime2", timestamp1);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="当月调入人数"){
				List<SysOrgElement> list = sysOrgElementService.findAllChildElement(sysOrgElement, SysOrgConstant.ORG_TYPE_DEPT);
				List<String> deptIds = new ArrayList<String>();
				for(SysOrgElement sysOrgElement1 : list){
					deptIds.add(sysOrgElement1.getFdId());
				}
				HQLInfo hqlInfo3 = new HQLInfo();
				hqlInfo3.setSelectBlock("fdPersonInfo.fdId");
				String whereBlock3 = "hrStaffMoveRecord.fdBeforeFirstDeptName!=hrStaffMoveRecord.fdAfterFirstDeptName"
						+ " and hrStaffMoveRecord.fdAfterDept.fdId in (:deptIds)";
				whereBlock3+=" and hrStaffMoveRecord.fdMoveDate BETWEEN :fdEntryTime1 and :fdEntryTime2";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime1 = new Date();
				Date fdEntryTime2 = new Date();
				if(fdMonth==1)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==2)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==3)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==4)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==5)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==6)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==7)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==8)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==9)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==10)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==11)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==12)
					fdEntryTime2=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
							DateUtil.TYPE_DATE, null);
				Timestamp timestamp1 = new Timestamp(fdEntryTime1.getTime());
				Timestamp timestamp2 = new Timestamp(fdEntryTime2.getTime());
				hqlInfo3.setWhereBlock(whereBlock3);
				hqlInfo3.setParameter("deptIds",deptIds);
				hqlInfo3.setParameter("fdEntryTime1", timestamp1);
				hqlInfo3.setParameter("fdEntryTime2", timestamp2);
				List list2 = hrStaffMoveRecordService.findValue(hqlInfo3);
				if(list2.isEmpty())
					return (Double) 0.0;
				List<String> status = new ArrayList<String>();
				List<String> list3 = new ArrayList<String>();
				for(int i=0;i<list2.size();i++){
					list3.add((String) list2.get(i));
				}
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				whereBlock+=" and hrStaffPersonInfo.fdId in (:list3)";
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setParameter("fdStatus", status);
				hqlInfo.setParameter("list3", list3);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="当月调出人数"){
				List<SysOrgElement> list = sysOrgElementService.findAllChildElement(sysOrgElement, SysOrgConstant.ORG_TYPE_DEPT);
				List<String> deptIds = new ArrayList<String>();
				for(SysOrgElement sysOrgElement1 : list){
					deptIds.add(sysOrgElement1.getFdId());
				}
				HQLInfo hqlInfo3 = new HQLInfo();
				hqlInfo3.setSelectBlock("fdPersonInfo.fdId");
				String whereBlock3 = "hrStaffMoveRecord.fdBeforeFirstDeptName!=hrStaffMoveRecord.fdAfterFirstDeptName"
						+ " and hrStaffMoveRecord.fdBeforeDept.fdId in (:deptIds)";
				whereBlock3+=" and hrStaffMoveRecord.fdMoveDate BETWEEN :fdEntryTime1 and :fdEntryTime2";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdEntryTime1 = new Date();
				Date fdEntryTime2 = new Date();
				if(fdMonth==1)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
				fdEntryTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==2)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==3)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==4)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==5)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==6)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==7)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==8)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==9)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==10)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==11)
					fdEntryTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==12)
					fdEntryTime2=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
							DateUtil.TYPE_DATE, null);
				Timestamp timestamp1 = new Timestamp(fdEntryTime1.getTime());
				Timestamp timestamp2 = new Timestamp(fdEntryTime2.getTime());
				hqlInfo3.setWhereBlock(whereBlock3);
				hqlInfo3.setParameter("deptIds",deptIds);
				hqlInfo3.setParameter("fdEntryTime1", timestamp1);
				hqlInfo3.setParameter("fdEntryTime2", timestamp2);
				List list2 = hrStaffMoveRecordService.findValue(hqlInfo3);
				if(list2.isEmpty())
					return (Double) 0.0;
				List<String> status = new ArrayList<String>();
				List<String> list3 = new ArrayList<String>();
				for(int i=0;i<list2.size();i++){
					list3.add((String) list2.get(i));
				}
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				whereBlock+=" and hrStaffPersonInfo.fdId in (:list3)";
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setParameter("fdStatus", status);
				hqlInfo.setParameter("list3", list3);
			}
			if(personnelMonthlyReportStaffEntryAndExit=="当月离职员工人数"){
				whereBlock+=" and hrStaffPersonInfo.fdLeaveTime BETWEEN :fdLeaveTime1 and :fdLeaveTime2";
				Date beginDay = DateUtil.getBeginDayOfMonth();
				Calendar cal = Calendar.getInstance();
				Date fdLeaveTime1 = new Date();
				Date fdLeaveTime2 = new Date();
				if(fdMonth==1)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-01-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==2)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==3)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==4)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==5)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==6)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==7)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==8)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==9)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==10)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==11)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==12)
					fdLeaveTime1=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
						DateUtil.TYPE_DATE, null);
				if(fdMonth==1)
					fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-02-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==2)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-03-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==3)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-04-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==4)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-05-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==5)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-06-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==6)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-07-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==7)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-08-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==8)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-09-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==9)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-10-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==10)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-11-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==11)
						fdLeaveTime2=DateUtil.convertStringToDate(DateUtil.getNowYear().toString()+"-12-01",
							DateUtil.TYPE_DATE, null);
					if(fdMonth==12)
						fdLeaveTime2=DateUtil.convertStringToDate((DateUtil.getNowYear()+1)+"-01-01",
							DateUtil.TYPE_DATE, null);
				Timestamp timestamp1 = new Timestamp(fdLeaveTime1.getTime());
				Timestamp timestamp2 = new Timestamp(fdLeaveTime2.getTime());
				hqlInfo.setParameter("fdLeaveTime1", timestamp1);
				hqlInfo.setParameter("fdLeaveTime2", timestamp2);
				List<String> status = new ArrayList<String>();
				status.add("trial");
				status.add("official");
				status.add("temporary");
				status.add("trialDelay");
				status.add("practice");

				whereBlock+=" and hrStaffPersonInfo.fdStatus in (:fdStatus)";
				hqlInfo.setParameter("fdStatus", status);
			}
//		}

			whereBlock+=" and hrStaffPersonInfo.fdStaffType like :fdStaffType";
			if(fdStaffType.equals("海格正编"))
			hqlInfo.setParameter("fdStaffType", "%正编%");
			if(fdStaffType.equals("派遣员工"))
				hqlInfo.setParameter("fdStaffType", "%派遣%");
			if(fdStaffType.equals("实习生"))
				hqlInfo.setParameter("fdStaffType", "%实习生%");
			hqlInfo.setWhereBlock(whereBlock);
		return getCountForDept1(hqlInfo, orgId);
	}
	
	/**
	 * 获取一个按KEY排序的MAP对象
	 * 
	 * @return
	 */
	private Map<String, Object> getOrderMap() {
		return new TreeMap<String, Object>(new Comparator<String>() {
			@Override
			public int compare(String obj1, String obj2) {
				// 降序排序
				return obj1.compareTo(obj2);
			}
		});
	}

	/**
	 * 人数统计
	 * 
	 * @param parent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long getCountForDept(HQLInfo hqlInfo, String parentId)
			throws Exception {
		List<Long> list = null;
		long count = 0;
		if (StringUtil.isNotNull(parentId)) {
			String whereBlock = hqlInfo.getWhereBlock();
			hqlInfo.setParameter("parentId", "%" + parentId + "%");

			// 统计人事档案人员
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdHierarchyId like :parentId");
		}
		list = hrStaffPersonInfoService.findValue(hqlInfo);
		count += list.get(0);

		return count;
	}
	@SuppressWarnings("unchecked")
	public Double getCountForDept1(HQLInfo hqlInfo, String parentId)
			throws Exception {
		List<Long> list = null;
		Double count = 0.0;
		if (StringUtil.isNotNull(parentId)) {
			String whereBlock = hqlInfo.getWhereBlock();
			hqlInfo.setParameter("parentId", "%" + parentId + "%");

			// 统计人事档案人员
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdHierarchyId like :parentId");
		}
		list = hrStaffPersonInfoService.findValue(hqlInfo);
		count += list.get(0);

		return count;
	}
	@SuppressWarnings("unchecked")
	public Long getCountForFirstDept(HQLInfo hqlInfo, String Id)
			throws Exception {
		List<Long> list = null;
		long count = 0;
		if (StringUtil.isNotNull(Id)) {
			String whereBlock = hqlInfo.getWhereBlock();
			hqlInfo.setParameter("id", "%" + Id + "%");

			// 统计人事档案人员
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdOrgRank.fdId like :id");
		}
		list = hrStaffPersonInfoService.findValue(hqlInfo);
		count += list.get(0);

		return count;
	}
	/**
	 * 构建入职期间和员工状态的HQL
	 * 
	 * @param report
	 * @param parentId
	 * @return
	 */
	private HQLInfo buildQueryHql(HrStaffPersonReport report) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		StringBuffer whereBlock = new StringBuffer("1 = 1");
		// 入职期间 过滤
		Date fdBeginPeriod = report.getFdBeginPeriod();
		Date fdEndPeriod = report.getFdEndPeriod();
		if (fdBeginPeriod != null) {
			whereBlock
					.append(" and hrStaffPersonInfo.fdTimeOfEnterprise > :fdBeginPeriod");
			// 在oracle下，时间判断有点问题，所以这里的时间还需要退一秒
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdBeginPeriod);
			cal.add(Calendar.SECOND, -1);

			hqlInfo.setParameter("fdBeginPeriod", cal.getTime());
		}
		if (fdEndPeriod != null) {
			whereBlock
					.append(" and hrStaffPersonInfo.fdTimeOfEnterprise <= :fdEndPeriod");
			hqlInfo.setParameter("fdEndPeriod", fdEndPeriod);
		}

		// 员工状态（非异动报表才会有加入这个过滤）
		if (!"reportStatus".equals(report.getFdReportType())) {
			String fdStatus = report.getFdStatus();
			if (StringUtil.isNotNull(fdStatus)) {
				boolean isNull = false;
				List<String> _fdStatus = new ArrayList<String>();
				for (String _fdStatu : fdStatus.split(";|,")) {
					if ("official".equals(_fdStatu)) {
						isNull = true;
					}
					_fdStatus.add(_fdStatu);
				}
				whereBlock
						.append(" and (hrStaffPersonInfo.fdStatus in (:fdStatus)");
				if (isNull) {
					whereBlock.append(" or hrStaffPersonInfo.fdStatus is null");
				}
				whereBlock.append(")");
				hqlInfo.setParameter("fdStatus", _fdStatus);
			}
		}

		hqlInfo.setWhereBlock(whereBlock.toString());
		return hqlInfo;
	}
	private HQLInfo buildQueryHql1(HrStaffPersonReport report) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		StringBuffer whereBlock = new StringBuffer("1 = 1");
		// 入职期间 过滤
		Date fdBeginPeriod = report.getFdBeginPeriod();
		Date fdEndPeriod = report.getFdEndPeriod();
		if (fdBeginPeriod != null) {
			whereBlock
					.append(" and hrStaffPersonInfo.fdTimeOfEnterprise > :fdBeginPeriod");
			// 在oracle下，时间判断有点问题，所以这里的时间还需要退一秒
			Calendar cal = Calendar.getInstance();
			cal.setTime(fdBeginPeriod);
			cal.add(Calendar.SECOND, -1);

			hqlInfo.setParameter("fdBeginPeriod", cal.getTime());
		}
		if (fdEndPeriod != null) {
			whereBlock
					.append(" and hrStaffPersonInfo.fdTimeOfEnterprise <= :fdEndPeriod");
			hqlInfo.setParameter("fdEndPeriod", fdEndPeriod);
		}

		// 员工状态（非异动报表才会有加入这个过滤）
//		if (!"reportStatus".equals(report.getFdReportType())) {
//			String fdStatus = report.getFdStatus();
//			if (StringUtil.isNotNull(fdStatus)) {
//				boolean isNull = false;
//				List<String> _fdStatus = new ArrayList<String>();
//				for (String _fdStatu : fdStatus.split(";|,")) {
//					if ("official".equals(_fdStatu)) {
//						isNull = true;
//					}
//					_fdStatus.add(_fdStatu);
//				}
//				whereBlock
//						.append(" and (hrStaffPersonInfo.fdStatus in (:fdStatus)");
//				if (isNull) {
//					whereBlock.append(" or hrStaffPersonInfo.fdStatus is null");
//				}
//				whereBlock.append(")");
//				hqlInfo.setParameter("fdStatus", _fdStatus);
//			}
//		}

		hqlInfo.setWhereBlock(whereBlock.toString());
		return hqlInfo;
	}
	/**
	 * 概况图表（饼图）
	 */
	@Override
	public ReportResult statOverviewChart(String type) throws Exception {
		ReportResult result = null;
		// 统计data
		if ("staffSex".equals(type)) { // 在职员工性别比例
			result = buildOverviewChartForStaffSex();
		} else if ("education".equals(type)) { // 在职员工学历分布
			result = buildOverviewChartForEducation();
		} else if ("workTime".equals(type)) { // 在职员工司龄分布
			result = buildOverviewChartForWorkTime();
		} else if ("staffType".equals(type)) { // 在职员工类别分布
			result = buildOverviewChartForStaffType();
		}
		return result;
	}

	/**
	 * 概况统计报表只统计在职状态的员工
	 * 
	 * @return
	 */
	private HQLInfo getBaseHQLInfoForOverview() {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		// 只统计“试用、正式、试用延期、临时、实习生”状态的员工
		List<String> status = new ArrayList<String>();
		status.add("trial");
		status.add("official");
		status.add("temporary");
		status.add("trialDelay");
		status.add("practice");

		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStatus in (:fdStatus)");
		hqlInfo.setParameter("fdStatus", status);
		return hqlInfo;
	}

	@SuppressWarnings("unchecked")
	private ReportResult buildOverviewChartForStaffSex() throws Exception {
		ReportResult result = new ReportResult();
		// 获取性别
		List<String> sexList = new ArrayList<String>();
		sexList.add(null); // 未知
		sexList.add("M"); // 男性
		sexList.add("F"); // 女性

		// 获取人数
		List<Long> counts = new ArrayList<Long>();
		StringBuffer xAxis = new StringBuffer();
		HQLInfo hqlInfo = null;
		List<Long> list = null;
		JSONObject obj = new JSONObject();
		for (String sex : sexList) {
			long count = 0L;
			hqlInfo = getBaseHQLInfoForOverview();
			String whereBlock = hqlInfo.getWhereBlock();
			if (sex == null) {
				// 统计组织机构人员
				hqlInfo
						.setWhereBlock(whereBlock
								+ " and hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdSex is null");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count += list.get(0);
				// 统计人事档案人员
				hqlInfo
						.setWhereBlock(whereBlock
								+ " and hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdSex is null");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count += list.get(0);
				if (count > 0) {
					xAxis
							.append(
									ResourceUtil
											.getString("hr-staff:hrStaffPersonReport.unknown"))
							.append(",");
					obj.accumulate("unknown", count);
				}
			} else {
				hqlInfo.setParameter("fdSex", sex);
				// 统计组织机构人员
				hqlInfo
						.setWhereBlock(whereBlock
								+ " and hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdOrgPerson.fdSex = :fdSex");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count += list.get(0);
				// 统计人事档案人员
				hqlInfo
						.setWhereBlock(whereBlock
								+ " and hrStaffPersonInfo.fdOrgPerson is null and hrStaffPersonInfo.fdSex = :fdSex");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count += list.get(0);
				if (count > 0) {
					xAxis
							.append(
									ResourceUtil
											.getString("hr-staff:hrStaff.overview.report.staffSex."
													+ sex)).append(",");
				}
				if ("F".equals(sex)) {
					obj.accumulate("female", count);
				} else {
					obj.accumulate("male", count);
				}
			}


			if (count > 0) {
				counts.add(count);
			}
		}

		// 统计data
		Map<String, Object> seriesData = new HashMap<String, Object>();
		seriesData.put("type", "pie"); // 饼图
		seriesData.put("radius", "50%"); // 半径
		seriesData.put("data", counts);
		seriesData.put("title", ResourceUtil.getString("hr-staff:hrStaff.overview.report.staffSex"));
		result.getSeriesData().add(seriesData);

		// X轴信息
		if (xAxis.length() > 0) {
			xAxis.deleteCharAt(xAxis.length() - 1);
		}
		result.addXAxis("", "category", xAxis.toString());
		JSONArray statistics = new JSONArray();
		statistics.add(obj);
		result.setStatistics(statistics);
		return result;
	}

	@SuppressWarnings("unchecked")
	private ReportResult buildOverviewChartForEducation() throws Exception {
		ReportResult result = new ReportResult();
		// 获取所有配置的学历
		List<String> educationList = getEducations();
		JSONArray statistics = new JSONArray();
		List<Long> counts = new ArrayList<Long>();
		StringBuffer xAxis = new StringBuffer();
		HQLInfo hqlInfo = null;
		List<Long> list = null;
		for (String education : educationList) {
			long count = 0L;
			hqlInfo = getBaseHQLInfoForOverview();
			String whereBlock = hqlInfo.getWhereBlock();
			JSONObject obj = new JSONObject();
			if ("null".equals(education)) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and (hrStaffPersonInfo.fdHighestEducation is null or hrStaffPersonInfo.fdHighestEducation=:fdHighestEducation)");
				hqlInfo.setParameter("fdHighestEducation", "");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					xAxis
							.append(
									ResourceUtil
											.getString("hr-staff:hrStaffPersonReport.unknown"))
							.append(",");
					counts.add(count);
					obj.accumulate("name", ResourceUtil
							.getString("hr-staff:hrStaffPersonReport.unknown"));
					obj.accumulate("value", count);
					statistics.add(obj);
				}
			} else {
				hqlInfo.setParameter("education", education);
				hqlInfo
						.setWhereBlock(whereBlock
								+ " and hrStaffPersonInfo.fdHighestEducation = :education");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					xAxis.append(education).append(",");
					counts.add(count);
					obj.accumulate("name", education);
					obj.accumulate("value", count);
					statistics.add(obj);
				}
			}
		}

		// 统计data
		Map<String, Object> seriesData = new HashMap<String, Object>();
		seriesData.put("type", "bar"); // 柱形图
		seriesData.put("data", counts);
		seriesData.put("name", "人数");
		seriesData.put("title", ResourceUtil.getString("hr-staff:hrStaff.overview.report.education"));
		result.getSeriesData().add(seriesData);

		// X轴信息
		if (xAxis.length() > 0) {
			xAxis.deleteCharAt(xAxis.length() - 1);
		}
		result.addXAxis("", "category", xAxis.toString());
		result.setStatistics(statistics);
		return result;
	}

	@SuppressWarnings("unchecked")
	private ReportResult buildOverviewChartForWorkTime() throws Exception {
		ReportResult result = new ReportResult();
		// 获取时间段
		List<int[]> workTimeList = getAgeRangeForWorkTime();

		List<Long> counts = new ArrayList<Long>();
		StringBuffer xAxis = new StringBuffer();
		HQLInfo hqlInfo = null;
		List<Long> list = null;
		JSONArray statistics = new JSONArray();
		for (int i = 0; i <= workTimeList.size(); i++) {
			long count = 0L;
			hqlInfo = getBaseHQLInfoForOverview();
			String whereBlock = hqlInfo.getWhereBlock();
			JSONObject obj = new JSONObject();
			if (i == 0) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and hrStaffPersonInfo.fdTimeOfEnterprise is null");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					xAxis
							.append(
									ResourceUtil
											.getString("hr-staff:hrStaffPersonReport.unknown"))
							.append(",");
					counts.add(count);
					obj.accumulate(ResourceUtil
							.getString("hr-staff:hrStaffPersonReport.unknown"),
							count);
					obj.accumulate("name",ResourceUtil
							.getString("hr-staff:hrStaffPersonReport.unknown"));
				    obj.accumulate("value", count);
					statistics.add(obj);
				}
			} else {
				int[] ageRang = workTimeList.get(i - 1);

				Calendar c1 = Calendar.getInstance();
				c1.add(Calendar.YEAR, -ageRang[0]);
				Calendar c2 = Calendar.getInstance();
				c2.add(Calendar.YEAR, -ageRang[1]);

				hqlInfo
						.setWhereBlock(whereBlock
								+ " and (hrStaffPersonInfo.fdTimeOfEnterprise >= :benimTime and hrStaffPersonInfo.fdTimeOfEnterprise < :endTime)");
				hqlInfo.setParameter("benimTime", c2.getTime());
				hqlInfo.setParameter("endTime", c1.getTime());
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					if (i == workTimeList.size()) {
						xAxis.append(
								ResourceUtil.getString(
										"hrStaffPersonReport.workTime.over",
										"hr-staff", null, ageRang[0])).append(
								",");
						obj.accumulate("name", ResourceUtil.getString(
								"hrStaffPersonReport.workTime.over",
								"hr-staff", null, ageRang[0]));
						obj.accumulate("value", count);
					} else if (i == 1) {
						xAxis.append(
								ResourceUtil.getString(
										"hrStaffPersonReport.workTime.under",
										"hr-staff", null, ageRang[1])).append(
								",");
						obj.accumulate("name", ResourceUtil.getString(
								"hrStaffPersonReport.workTime.over",
								"hr-staff", null, ageRang[1]));
						obj.accumulate("value", count);
					} else {
						xAxis.append(
								ResourceUtil.getString(
										"hrStaffPersonReport.workTime",
										"hr-staff", null, new Object[] {
												ageRang[0], ageRang[1] }))
								.append(",");

						obj.accumulate("name", ResourceUtil.getString(
								"hrStaffPersonReport.workTime",
								"hr-staff", null, new Object[] {
										ageRang[0], ageRang[1] }));
						obj.accumulate("value", count);
					}
					counts.add(count);
					statistics.add(obj);
				}
			}
		}

		// 统计data
		Map<String, Object> seriesData = new HashMap<String, Object>();
		seriesData.put("type", "bar"); // 柱形图
		seriesData.put("data", counts);
		seriesData.put("title", ResourceUtil.getString("hr-staff:hrStaff.overview.report.workTime"));
		result.getSeriesData().add(seriesData);

		// X轴信息
		if (xAxis.length() > 0) {
			xAxis.deleteCharAt(xAxis.length() - 1);
		}
		result.addXAxis("", "category", xAxis.toString());
		result.setStatistics(statistics);
		return result;
	}

	@SuppressWarnings("unchecked")
	private ReportResult buildOverviewChartForStaffType() throws Exception {
		ReportResult result = new ReportResult();
		// 获取人员类别
		List<String> staffTypeList = getStaffTypes();

		List<Long> counts = new ArrayList<Long>();
		StringBuffer xAxis = new StringBuffer();
		HQLInfo hqlInfo = null;
		List<Long> list = null;
		//
		JSONArray statistics = new JSONArray();
		for (String staffType : staffTypeList) {
			long count = 0L;
			hqlInfo = getBaseHQLInfoForOverview();
			String whereBlock = hqlInfo.getWhereBlock();
			JSONObject obj = new JSONObject();
			if ("null".equals(staffType)) {
				hqlInfo.setWhereBlock(whereBlock
						+ " and (hrStaffPersonInfo.fdStaffType is null or hrStaffPersonInfo.fdStaffType=:fdStaffType)");
				hqlInfo.setParameter("fdStaffType", "");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					xAxis
							.append(
									ResourceUtil
											.getString("hr-staff:hrStaffPersonReport.unknown"))
							.append(",");
					counts.add(count);
					obj.accumulate("name", ResourceUtil
							.getString("hr-staff:hrStaffPersonReport.unknown"));
					obj.accumulate("value",
							count);
					statistics.add(obj);
				}
			} else {
				hqlInfo.setParameter("staffType", staffType);
				hqlInfo.setWhereBlock(whereBlock
						+ " and hrStaffPersonInfo.fdStaffType = :staffType");
				list = hrStaffPersonInfoService.findValue(hqlInfo);
				count = list.get(0);
				if (count > 0) {
					xAxis.append(staffType).append(",");
					counts.add(count);
					obj.accumulate("name", staffType);
					obj.accumulate("value", count);
					statistics.add(obj);
				}
			}

		}

		// 统计data
		Map<String, Object> seriesData = new HashMap<String, Object>();
		seriesData.put("type", "pie"); // 饼图
		List<String> radius = new ArrayList<String>();
		radius.add("30%");
		radius.add("50%");
		seriesData.put("radius", radius); // 半径
		seriesData.put("data", counts);
		seriesData.put("title", ResourceUtil.getString("hr-staff:hrStaff.overview.report.staffType"));
		result.getSeriesData().add(seriesData);
		// X轴信息
		if (xAxis.length() > 0) {
			xAxis.deleteCharAt(xAxis.length() - 1);
		}
		result.addXAxis("", "category", xAxis.toString());
		result.setStatistics(statistics);
		return result;
	}

	private List<int[]> getAgeRangeForWorkTime() {
		List<int[]> list = new ArrayList<int[]>();
		// 3年以下、3~5年、5~10年、10~15年、15年以上
		list.add(new int[] { 0, 3 });
		list.add(new int[] { 3, 5 });
		list.add(new int[] { 5, 10 });
		list.add(new int[] { 10, 15 });
		list.add(new int[] { 15, 100 });
		return list;
	}

	/**
	 * 获取人员类型
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getStaffTypes() throws Exception {
		List<HrStaffPersonInfoSettingNew> settings = hrStaffPersonInfoSettingService
				.getByType("fdStaffType");
		List<String> staffTypes = new ArrayList<String>();
		staffTypes.add("null");
		if (settings != null) {
			for (HrStaffPersonInfoSettingNew setting : settings) {
				staffTypes.add(setting.getFdName());
			}
		}
		return staffTypes;
	}

	private Long getCountForSex(HQLInfo hqlInfo, String whereBlock,
			String sex, String orgId) throws Exception {
		if (StringUtil.isNull(sex)) {
			hqlInfo.setWhereBlock(whereBlock
					+ " and hrStaffPersonInfo.fdSex is null");
		} else {
			hqlInfo
					.setWhereBlock(whereBlock
							+ " and hrStaffPersonInfo.fdSex = :_fdSex");
			hqlInfo.setParameter("_fdSex", sex);
		}

		return getCountForDept(hqlInfo, orgId);
	}

	/**
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	private ReportResult getChartSex(HrStaffPersonReport report)
			throws Exception {
		ReportResult result = new ReportResult();

		// 统计data
		String queryIds = report.getFdQueryIds();
		List<Long> counts = null;
		HQLInfo hqlInfo = null;
		Map<String, Object> seriesData = null;
		String whereBlock = null;

		List<String> sexList = new ArrayList<String>();
		sexList.add("F"); // 女性
		sexList.add("M"); // 男性
		sexList.add(null); // 未知
		JSONArray statistics = new JSONArray();
		for (int i = 0; i < sexList.size(); i++) {
			counts = new ArrayList<Long>();
			seriesData = new HashMap<String, Object>();
			JSONObject obj = new JSONObject();
			if (StringUtil.isNotNull(queryIds)) {
				for (String id : queryIds.split(";")) {
					hqlInfo = getBaseHQLInfoForOverview();
					whereBlock = hqlInfo.getWhereBlock();
					counts.add(
							getCountForSex(hqlInfo, whereBlock, sexList.get(i),
									id));
				}
			} else {
				hqlInfo = getBaseHQLInfoForOverview();
				whereBlock = hqlInfo.getWhereBlock();
				counts.add(getCountForSex(hqlInfo, whereBlock, sexList.get(i),
						""));
			}
			String fdSexName = "";
			if (StringUtil.isNull(sexList.get(i))) {
				fdSexName = ResourceUtil
						.getString("hr-staff:hrStaffPersonReport.unknown");
			}
			if ("F".equals(sexList.get(i))) {
				fdSexName = ResourceUtil
						.getString(
								"hr-staff:hrStaff.overview.report.staffSex.F");
			}
			if ("M".equals(sexList.get(i))) {
				fdSexName = ResourceUtil
						.getString(
								"hr-staff:hrStaff.overview.report.staffSex.M");
			}
				seriesData.put("name", fdSexName);
			seriesData.put("type", "bar");
			seriesData.put("data", counts);
			result.getSeriesData().add(seriesData);
		}

		return result;
	}

	@Override
	public String getStaffMobileStat(String orgId) throws Exception {
		JSONObject statistic = new JSONObject();
		ReportResult result = null;
		HrStaffPersonReport report = new HrStaffPersonReport();
		report.setFdQueryIds(orgId);
		report.setFdAgeRange(10);
		ArrayList ageList = (ArrayList) buildChartForAge(report)
				.getSeriesData();
		statistic.accumulate("age", ageList);
		ArrayList worktimeList = (ArrayList) buildChartForWorkTime(report)
				.getSeriesData();
		statistic.accumulate("worktime", worktimeList);
		ArrayList edutionList = (ArrayList) buildChartForEducation(report)
				.getSeriesData();
		statistic.accumulate("edution", edutionList);
		ArrayList marray = (ArrayList) buildChartForMarital(report)
				.getSeriesData();
		statistic.accumulate("marital", marray);
		report.setFdStatus(
				"retire;leave;dismissal;trialDelay;temporary;official;practice;trial");
		ArrayList staffStatus = (ArrayList) buildChartForStatus(report)
				.getSeriesData();
		statistic.accumulate("staffStatus", staffStatus);
		ArrayList staffSex = (ArrayList) getChartSex(report).getSeriesData();
		statistic.accumulate("staffSex", staffSex);
		return statistic.toString();
	}
}