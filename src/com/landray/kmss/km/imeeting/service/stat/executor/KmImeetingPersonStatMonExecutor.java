package com.landray.kmss.km.imeeting.service.stat.executor;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.PeriodModel;
import com.landray.kmss.common.model.PeriodTypeModel;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.util.StatAxis;
import com.landray.kmss.km.imeeting.util.StatExecutorUtil;
import com.landray.kmss.km.imeeting.util.StatHqlInfo;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员统计吞吐量环比执行器
 */
public class KmImeetingPersonStatMonExecutor extends
		AbstractKmImeetingStatExecutor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingPersonStatMonExecutor.class);


	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	protected List<Map<String, ?>> getQueryChartData(
			Map<String, Object> parameters)
			throws Exception {
		List<Map<String, ?>> dataList = new ArrayList<Map<String, ?>>();
		List<String> personIds = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondIds"));
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		personIds = sysOrgCoreService.expandToPersonIds(personIds);
		List periods = getPeriods(parameters);
		if (personIds != null && !personIds.isEmpty()) {
			for (int i = 0; i < personIds.size(); i++) {
				SysOrgElement person = sysOrgCoreService
						.findByPrimaryKey(personIds.get(i));
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("personId", personIds.get(i));
				valueMap.put("personName", person.getFdName());
				List<Map<String, Object>> valueList = new ArrayList<Map<String, Object>>();
				if (periods != null && !periods.isEmpty()) {
					for (Object tmpObj : periods) {
						Long periodId = (Long) tmpObj;
						PeriodModel period = null;
						try {
							period = PeriodTypeModel.getSinglePeriod(periodId);
						} catch (Exception e) {
							logger.debug("解析时间区间错误：" + e);
						}
						if (period != null) {
							Map<String, Object> tmpMap = new HashMap<String, Object>();
							tmpMap.put("periodId", periodId);
							tmpMap.put("periodName", period.getFdName());
							tmpMap.put("personId", personIds.get(i));
							tmpMap.put("personName", person.getFdName());
							StatHqlInfo hqlInfo = getHQL(period,
									personIds.get(i), null, parameters);
							Query q = baseDao.getHibernateSession()
									.createQuery(hqlInfo.getHqlBlock());
							q.setCacheable(true);
							q.setCacheMode(CacheMode.NORMAL);
							q.setCacheRegion("km-imeeting");
							for (HQLParameter tmpParam : hqlInfo
									.getParameterList()) {
								q.setParameter(tmpParam.getName(),
										tmpParam.getValue());
							}
							Object[] result = (Object[]) q.list().get(0);
							Long counts = (Long) result[0];
							tmpMap.put("counts", counts);
							if (counts > 0L) {
								if (result[1] == null) {
									result[1] = 0;
								}
								// 精确到小数点后一位
								Double timeDate = (Double) result[1]
										/ (1000 * 60 * 60);
								tmpMap.put(
										"sum",
										NumberUtil
												.roundDecimal(
														timeDate,
														ResourceUtil
																.getMessage("{km-imeeting:decimal.format}")));
							} else {
								tmpMap.put("sum", 0);
							}
							valueList.add(tmpMap);
						}
					}
				}
				valueMap.put("datas", valueList);
				dataList.add(valueMap);
			}
		}
		return dataList;
	}

	private StatHqlInfo getHQL(PeriodModel period, String personId,
			String fdOperateType,
			Map<String, Object> parameters)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select count(kmImeetingMainFeedback.fdId), ");
		sb.append("sum(kmImeetingMainFeedback.fdMeeting.fdHoldDuration + 0.0) ");
		sb.append("from " + KmImeetingMainFeedback.class.getName()
				+ " kmImeetingMainFeedback ");
		// sb.append("where 1=1 ");
		sb.append("where kmImeetingMainFeedback.fdMeeting.isNotify='1' and kmImeetingMainFeedback.fdMeeting.docStatus<>'41' ");// 发送会议通知且非取消的会议才统计
		Object isWorkbench = parameters.get("isWorkbench");
		if ("1".equals(isWorkbench)) {
			// 工作台只统计已参加的
			sb.append(
					" and kmImeetingMainFeedback.fdMeeting.fdFinishDate < :nowDate ");
			hqlInfo.setParameter("nowDate", new Date());
		}
		// 回执类型:01参加 02不参加 03找人代理
		if (StringUtil.isNotNull(fdOperateType)) {
			sb.append(" and kmImeetingMainFeedback.fdOperateType=:fdOperateType ");
			hqlInfo.setParameter("fdOperateType", fdOperateType);
		}
		// 组装人员条件
		StatHqlInfo tmpHqlInfo = StatExecutorUtil.getOrgHqlInfo(
				baseDao.getHibernateSession(),
				"kmImeetingMainFeedback.docCreator", personId);
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMainFeedback.fdMeeting.fdTemplate",
				(String) parameters.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装时间条件
		tmpHqlInfo = StatExecutorUtil.getTimeScopeHqlInfo(
				baseDao.getHibernateSession(),
				"kmImeetingMainFeedback.fdMeeting", period.getFdStart(),
				period.getFdEnd());
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		hqlInfo.setHqlBlock(sb.toString());
		return hqlInfo;
	}

	/**
	 * 获取时间区间
	 */
	private List getPeriods(Map<String, Object> parameters) {
		Object start = parameters.get("fdStartProdDate");
		Object end = parameters.get("fdEndProdDate");
		List periods = null;
		if (start instanceof PeriodModel) {
			try {
				periods = PeriodTypeModel.getPeriods(
						Long.valueOf(((PeriodModel) start).getFdId()),
						Long.valueOf(((PeriodModel) end).getFdId()), null);
			} catch (Exception e) {
				logger.debug("计算时间区间错误：" + e);
			}
		}
		return periods;
	}

	@Override
	protected StatResult buildStatChartResult(List<Map<String, ?>> qureyData,
			Map<String, Object> parameters) {
		StatResult result = new StatResult();
		Map<String, Object> title = new HashMap<String, Object>();// 题头信息
		title.put("text", (String) parameters.get("fdName"));
		result.setTitle(title);

		List<StatAxis> y = new ArrayList<StatAxis>();// y轴信息
		y.add(new StatAxis(ResourceUtil.getString(
				"kmImeetingStat.filed.counts", "km-imeeting"), "value", null));// 会议总数(件)
		y.add(new StatAxis(ResourceUtil.getString("kmImeetingStat.filed.sum",
				"km-imeeting"), "value", null));// 会议时长(小时)
		result.setyAxis(y);

		String xDataString = "";// x轴内容
		List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();// y轴内容
		for (int i = 0; i < qureyData.size(); i++) {
			Map<String, ?> item = (Map<String, ?>) qureyData.get(i);
			List<Map<String, Object>> valueList = (List<Map<String, Object>>) item
					.get("datas");
			String totalString = "", sumString = "";// 会议总数、会议时长
			for (Map<String, Object> value : valueList) {
				if (i == 0) {
					xDataString += value.get("periodName").toString() + ",";
				}
				totalString += value.get("counts").toString() + ",";
				sumString += value.get("sum").toString() + ",";
			}
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put(
					"name",
					(String) item.get("personName")
							+ ResourceUtil.getString(
									"kmImeetingStat.filed.counts",
									"km-imeeting"));
			totalMap.put("type", "bar");
			totalMap.put("yAxisIndex", "0");
			totalMap.put("data", StringUtil.isNull(totalString) ? ""
					: totalString.substring(0, totalString.length() - 1));
			seriesData.add(totalMap);
			Map<String, Object> sumMap = new HashMap<String, Object>();
			sumMap.put(
					"name",
					(String) item.get("personName")
							+ ResourceUtil.getString(
									"kmImeetingStat.filed.sum", "km-imeeting"));
			sumMap.put("type", "line");
			sumMap.put("yAxisIndex", "1");
			sumMap.put(
					"data",
					StringUtil.isNull(sumString) ? "" : sumString.substring(0,
							sumString.length() - 1));
			seriesData.add(sumMap);
		}

		List<StatAxis> x = new ArrayList<StatAxis>();// x轴信息对象
		x.add(new StatAxis(null, "category", xDataString.substring(0,
				xDataString.length() - 1)));
		result.setxAxis(x);

		result.setSeriesData(seriesData);
		return result;
	}

	@Override
	protected List<Map<String, ?>> getQueryListData(
			Map<String, Object> parameters) throws Exception {
		List<Map<String, ?>> dataList = new ArrayList<Map<String, ?>>();
		List<String> personIds = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondIds"));
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
				.getBean("sysOrgCoreService");
		personIds = sysOrgCoreService.expandToPersonIds(personIds);
		List periods = getPeriods(parameters);
		if (personIds != null && !personIds.isEmpty()) {
			for (int i = 0; i < personIds.size(); i++) {
				SysOrgElement person = sysOrgCoreService
						.findByPrimaryKey(personIds.get(i));
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("personId", personIds.get(i));
				valueMap.put("personName", person.getFdName());
				List<Map<String, Object>> valueList = new ArrayList<Map<String, Object>>();
				if (periods != null && !periods.isEmpty()) {
					for (Object tmpObj : periods) {
						Long periodId = (Long) tmpObj;
						PeriodModel period = null;
						try {
							period = PeriodTypeModel.getSinglePeriod(periodId);
						} catch (Exception e) {
							logger.debug("解析时间区间错误：" + e);
						}
						if (period != null) {
							Map<String, Object> tmpMap = new HashMap<String, Object>();
							tmpMap.put("periodId", periodId);
							tmpMap.put("periodName", period.getFdName());
							tmpMap.put("personId", personIds.get(i));
							tmpMap.put("personName", person.getFdName());

							Object[] result = getQueryResult(period,
									personIds.get(i), null, parameters);
							Long allCount = (Long) result[0];
							tmpMap.put("allCounts", allCount);// 总参加人数

							Long unknowCounts = allCount;
							Long tmpCounts = allCount;
							result = getQueryResult(period, personIds.get(i),
									"02", parameters);
							tmpCounts = (Long) result[0];
							unknowCounts -= tmpCounts;// 减去不参加人数
							tmpMap.put("unattendCounts", tmpCounts);// 不参加人数

							result = getQueryResult(period, personIds.get(i),
									"03", parameters);
							tmpCounts = (Long) result[0];
							unknowCounts -= tmpCounts;// 减去代理人数
							tmpMap.put("proxyCounts", tmpCounts);// 找人代理

							result = getQueryResult(period, personIds.get(i),
									"01", parameters);
							tmpCounts = (Long) result[0];
							unknowCounts -= tmpCounts;// 减去参加人数
							tmpMap.put("attendCounts", tmpCounts);// 参加人数
							if (tmpCounts > 0L) {
								if (result[1] == null) {
									result[1] = 0;
								}
								// 精确到小数点后一位
								Double timeDate = (Double) result[1]
										/ (1000 * 60 * 60);
								tmpMap.put(
										"attendSum",
										NumberUtil
												.roundDecimal(
														timeDate,
														ResourceUtil
																.getMessage("{km-imeeting:decimal.format}")));
							} else {
								tmpMap.put("attendSum", 0);// 参加时长
							}

							tmpMap.put("unknowCounts", unknowCounts);// 待定人数

							if (allCount > 0L) {
								tmpMap.put(
										"attendPercent",
										NumberUtil.roundDecimal(
												tmpCounts * 100 / allCount,
												ResourceUtil
														.getMessage("{km-imeeting:decimal.format}"))
												+ "%");
							} else {
								tmpMap.put("attendPercent", "0%");// 参加比
							}

							valueList.add(tmpMap);
						}
					}
				}
				valueMap.put("datas", valueList);
				dataList.add(valueMap);
			}
		}
		return dataList;
	}

	@Override
	protected JSON buildStatListResult(List<Map<String, ?>> qureyData,
			Map<String, Object> parameters) {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, String> fileds = new LinkedHashMap<String, String>();

		fileds.put("personName", ResourceUtil.getString(
				"kmImeetingStat.personScope", "km-imeeting"));// 统计人员
		fileds.put("periodName", ResourceUtil.getString(
				"kmImeetingStat.fdDateType.scope", "km-imeeting"));// 统计区间
		fileds.put("allCounts", ResourceUtil.getString(
				"kmImeetingStat.filed.counts", "km-imeeting"));// 会议总数(件)
		fileds.put("attendCounts", ResourceUtil.getString(
				"kmImeetingStat.filed.attendCounts", "km-imeeting"));// 参加数(件)
		fileds.put("unattendCounts", ResourceUtil.getString(
				"kmImeetingStat.filed.unAttendCounts", "km-imeeting"));// 不参加数(件)
		fileds.put("proxyCounts", ResourceUtil.getString(
				"kmImeetingStat.filed.proxyCounts", "km-imeeting"));// 找人代理(件)
		fileds.put("unknowCounts", ResourceUtil.getString(
				"kmImeetingStat.filed.unknowCounts", "km-imeeting"));// 待定数(件)
		fileds.put("attendSum", ResourceUtil.getString(
				"kmImeetingStat.filed.attendSum", "km-imeeting"));// 参会时长(小时)
		fileds.put("attendPercent", ResourceUtil.getString(
				"kmImeetingStat.filed.attendPercent", "km-imeeting"));// 参加比

		result.put("fileds", fileds);

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (Map<String, ?> item : qureyData) {
			List<Map<String, Object>> valueList = (List<Map<String, Object>>) item
					.get("datas");
			for (Map<String, Object> value : valueList) {
				Map<String, Object> filedData = new HashMap<String, Object>();
				filedData.put("personId", item.get("personId"));
				filedData.put("personName", item.get("personName"));
				filedData.put("periodId", value.get("periodId"));
				filedData.put("periodName", value.get("periodName"));
				filedData.put("allCounts", value.get("allCounts"));
				filedData.put("attendCounts", value.get("attendCounts"));
				filedData.put("unattendCounts", value.get("unattendCounts"));
				filedData.put("proxyCounts", value.get("proxyCounts"));
				filedData.put("unknowCounts", value.get("unknowCounts"));
				filedData.put("attendSum", value.get("attendSum"));
				filedData.put("attendPercent", value.get("attendPercent"));
				datas.add(filedData);
			}
		}
		result.put("datas", datas);
		return JSONObject.fromObject(result);
	}

	private Object[] getQueryResult(PeriodModel period, String personId,
			String fdOperateType,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = getHQL(period, personId, fdOperateType,
				parameters);
		Query q = baseDao.getHibernateSession().createQuery(
				hqlInfo.getHqlBlock());
		q.setCacheable(true);
		q.setCacheMode(CacheMode.NORMAL);
		q.setCacheRegion("km-imeeting");
		for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
			q.setParameter(tmpParam.getName(), tmpParam.getValue());
		}
		return (Object[]) q.list().get(0);
	}

	@Override
	protected Map<String, ?> getQueryListDetailData(
			Map<String, Object> parameters) throws Exception {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		StatHqlInfo hqlInfo = getListDetailWhereBlock(parameters);
		String fromBlock = " from " + KmImeetingMain.class.getName()
				+ " kmImeetingMain ," + KmImeetingMainFeedback.class.getName()
				+ " kmImeetingMainFeedback ";
		Query query = executeQuery("select count( kmImeetingMain.fdId) "
				+ fromBlock
						+ hqlInfo.getHqlBlock(), hqlInfo.getParameterList());
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		int total = 0;
		total = ((Long) query.iterate().next()).intValue();
		if (total > 0L) {
			Page page = new Page();
			String rowsize = (String) parameters.get("rowsize");
			if (StringUtil.isNotNull(rowsize)) {
				page.setRowsize(Integer.valueOf(rowsize));
			}
			String pageNo = (String) parameters.get("pageno");
			if (StringUtil.isNotNull(pageNo)) {
				page.setPageno(Integer.valueOf(pageNo));
			}
			page.setTotalrows(total);
			page.excecute();
			String orderBy = "order by kmImeetingMain.fdHoldDate desc ";
			query = executeQuery(
					"select kmImeetingMain" + fromBlock
					+ hqlInfo.getHqlBlock() + orderBy,
					hqlInfo.getParameterList());
			query.setFirstResult(page.getStart());
			query.setMaxResults(page.getRowsize());

			tmpMap.put("datas", query.list());
			tmpMap.put("page", page);
		}
		return tmpMap;
	}

	@Override
	protected JSON buildStatListDetailResult(Map<String, ?> qureyData,
			Map<String, Object> parameters) {
		return super.buildStatListDetailResult(qureyData, parameters);
	}

	private StatHqlInfo getListDetailWhereBlock(Map<String, Object> parameters)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("where kmImeetingMain.isNotify='1' and kmImeetingMain.docStatus<>'41' ");// 发送会议通知且非取消的会议才统计
		// 组装人员条件
		String personId = (String) parameters.get("personId");
		StatHqlInfo tmpHqlInfo = new StatHqlInfo();
		sb.append(" and kmImeetingMain.kmImeetingMainFeedbacks.fdId=kmImeetingMainFeedback.fdId ");
		sb.append(" and kmImeetingMainFeedback.docCreator.fdId=:personId ");
		hqlInfo.setParameter("personId", personId);
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMain.fdTemplate",
				(String) parameters.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append(" and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装时间条件
		String periodId = (String) parameters.get("periodId");
		PeriodModel period = null;
		try {
			period = PeriodTypeModel.getSinglePeriod(periodId);
		} catch (Exception e) {
			logger.debug("解析时间区间错误：" + e);
		}
		// 组装时间条件
		tmpHqlInfo = StatExecutorUtil.getTimeScopeHqlInfo(
				baseDao.getHibernateSession(), "kmImeetingMain",
				period.getFdStart(), period.getFdEnd());
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		hqlInfo.setHqlBlock(sb.toString());
		return hqlInfo;
	}

	/**
	 * 组装查询对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	protected Query executeQuery(String hql, List<HQLParameter> params) {
		Query query = baseDao.getHibernateSession().createQuery(hql);
		for (HQLParameter para : params) {
			query.setParameter(para.getName(), para.getValue());
		}
		return query;
	}

}
