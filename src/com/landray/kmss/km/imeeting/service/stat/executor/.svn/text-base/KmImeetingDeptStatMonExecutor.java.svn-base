package com.landray.kmss.km.imeeting.service.stat.executor;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.PeriodModel;
import com.landray.kmss.common.model.PeriodTypeModel;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.util.StatAxis;
import com.landray.kmss.km.imeeting.util.StatExecutorUtil;
import com.landray.kmss.km.imeeting.util.StatHqlInfo;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门会议吞吐量环比执行器
 */
public class KmImeetingDeptStatMonExecutor extends
		AbstractKmImeetingStatExecutor {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingDeptStatMonExecutor.class);


	private IBaseDao baseDao;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	protected List<Map<String, ?>> getQueryChartData(
			Map<String, Object> parameters)
			throws Exception {
		List<Map<String, ?>> dataList = new ArrayList<Map<String, ?>>();
		List<String> orgIds = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondIds"));
		List<String> orgNames = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondNames"));
		List periods = getPeriods(parameters);
		if (orgIds != null && !orgIds.isEmpty()) {
			for (int i = 0; i < orgIds.size(); i++) {
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("orgId", orgIds.get(i));
				valueMap.put("orgName", orgNames.get(i));
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
							tmpMap.put("orgId", orgIds.get(i));
							tmpMap.put("orgName", orgNames.get(i));
							int counts = 0;
							Double timeDate = 0.0;
							StatHqlInfo hqlInfo = getHQL(period, orgIds.get(i),
									parameters);
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
							for (int j = 0; j < q.list().size(); j++) {
								Object[] result = (Object[]) q.list().get(j);
								counts = counts
										+ Integer.valueOf(result[0].toString());
								if (Integer.valueOf(result[0].toString()) > 0) {
									if (result[1] == null) {
										result[1] = 0;
									}
									// 精确到小数点后一位
									timeDate = timeDate + Double
											.valueOf(result[1].toString())
											/ (1000 * 60 * 60);
								}
							}

							// 获取周期性会议信息
							Map<String, Object> rangeMainResult = getStatInRangeMain(
									period, orgIds.get(i), parameters);
							int rangeMainCounts = (Integer) rangeMainResult
									.get("counts");
							if (rangeMainCounts > 0) {
								counts += rangeMainCounts;
								timeDate += (Double) rangeMainResult
										.get("timeDate") / (1000 * 60 * 60);
							}

							tmpMap.put("counts", counts);
							if (timeDate > 0L) {
								tmpMap.put("sum",
										NumberUtil.roundDecimal(
												timeDate.doubleValue()));
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

	private Map<String, Object> getStatInRangeMain(PeriodModel period,
			String orgId, Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select kmImeetingMain.fdId, ");
		sb.append("kmImeetingMain.fdHoldDuration, ");
		sb.append("kmImeetingMain.fdRecurrenceStr, ");
		sb.append("kmImeetingMain.fdHoldDate, ");
		sb.append("kmImeetingMain.fdFinishDate, ");
		sb.append("kmImeetingMain.fdRecurrenceLastEnd ");
		sb.append(
				"from " + KmImeetingMain.class.getName() + " kmImeetingMain ");
		sb.append(
				"where kmImeetingMain.isNotify='1' and kmImeetingMain.docStatus<>'41' and (kmImeetingMain.fdRecurrenceStr is not null) ");// 发送会议通知且非取消的会议才统计
		// 组装组织部门条件
		StatHqlInfo tmpHqlInfo = StatExecutorUtil.getOrgHqlInfo(
				baseDao.getHibernateSession(), "kmImeetingMain.docDept",
				orgId);
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMain.fdTemplate",
				(String) parameters.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装时间条件
		if (period.getFdStart() != null) {
			sb.append(" and kmImeetingMain.fdRecurrenceLastEnd >= :start");
			hqlInfo.setParameter("start",
					StatExecutorUtil.clearTime(period.getFdStart()));
		}
		if (period.getFdEnd() != null) {
			sb.append(" and kmImeetingMain.fdHoldDate <= :end");
			hqlInfo.setParameter("end", period.getFdEnd());
		}
		hqlInfo.setHqlBlock(sb.toString());
		Query q = baseDao.getHibernateSession().createQuery(
				hqlInfo.getHqlBlock());
		q.setCacheable(true);
		q.setCacheMode(CacheMode.NORMAL);
		q.setCacheRegion("km-imeeting");
		for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
			q.setParameter(tmpParam.getName(), tmpParam.getValue());
		}
		int counts = 0;
		Double timeDate = 0.0;
		for (int j = 0; j < q.list().size(); j++) {
			Object[] result = (Object[]) q.list().get(j);
			if (result[1] == null) {
				result[1] = 0;
			}
			Double curTimeDate = Double.valueOf(result[1].toString());
			String recurrenceStr = result[2].toString();
			if (StringUtil.isNotNull(recurrenceStr)) {
				List<Date> dates = RecurrenceUtil.getExcuteDateList(
						recurrenceStr,
						(Timestamp) result[3], period.getFdStart(),
						period.getFdEnd());
				for (Date date : dates) {
					Date newEndDate = new Date(
							date.getTime() + ((Timestamp) result[4]).getTime()
									- ((Timestamp) result[3]).getTime());
					if (newEndDate.getTime() > ((Timestamp) result[5])
							.getTime()) {
						break;
					}
					counts++;
					timeDate += curTimeDate;
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("counts", counts);
		result.put("timeDate", timeDate);
		return result;
	}

	private StatHqlInfo getHQL(PeriodModel period, String orgId,
			Map<String, Object> parameters)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select count( kmImeetingMain.fdId), ");
		sb.append("sum(kmImeetingMain.fdHoldDuration + 0.0) ");
		sb.append("from " + KmImeetingMain.class.getName() + " kmImeetingMain ");
		// sb.append("where 1=1 ");
		sb.append(
				"where kmImeetingMain.isNotify='1' and kmImeetingMain.docStatus<>'41' and (kmImeetingMain.fdRecurrenceStr is null or kmImeetingMain.fdRecurrenceStr='NO') ");// 发送会议通知且非取消的会议才统计
		// 组装组织部门条件
		StatHqlInfo tmpHqlInfo = StatExecutorUtil.getOrgHqlInfo(
				baseDao.getHibernateSession(), "kmImeetingMain.docDept", orgId);
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMain.fdTemplate",
				(String) parameters
						.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
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
					(String) item.get("orgName")
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
					(String) item.get("orgName")
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
		return getQueryChartData(parameters);
	}

	@Override
	protected JSON buildStatListResult(List<Map<String, ?>> qureyData,
			Map<String, Object> parameters) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> fileds = new LinkedHashMap<String, String>();
		fileds.put("orgName", ResourceUtil.getString(
				"kmImeetingStat.deptScope", "km-imeeting"));// 组织名称
		fileds.put("periodName", ResourceUtil.getString(
				"kmImeetingStat.fdDateType.scope", "km-imeeting"));// 统计范围
		fileds.put("counts", ResourceUtil.getString(
				"kmImeetingStat.filed.counts", "km-imeeting"));// 会议总数(件)
		fileds.put("sum", ResourceUtil.getString("kmImeetingStat.filed.sum",
				"km-imeeting"));// 会议时长
		result.put("fileds", fileds);
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (Map<String, ?> item : qureyData) {
			String orgName = (String) item.get("orgName");
			List<Map<String, Object>> valueList = (List<Map<String, Object>>) item
					.get("datas");
			for (Map<String, Object> value : valueList) {
				Map<String, Object> filedData = new HashMap<String, Object>();
				filedData.put("orgId", item.get("orgId"));
				filedData.put("orgName", orgName);
				filedData.put("periodId", value.get("periodId"));
				filedData.put("periodName", value.get("periodName"));
				filedData.put("counts", value.get("counts"));
				filedData.put("sum", value.get("sum"));
				datas.add(filedData);
			}
		}
		result.put("datas", datas);
		return JSONObject.fromObject(result);
	}

	@Override
	protected Map<String, ?> getQueryListDetailData(
			Map<String, Object> parameters) throws Exception {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		StatHqlInfo hqlInfo = getListDetailWhereBlock(parameters);
		String fromBlock = " from " + KmImeetingMain.class.getName()
				+ " kmImeetingMain ";
		Query query = executeQuery(
"select count( kmImeetingMain.fdId) "
				+ fromBlock
						+ hqlInfo.getHqlBlock(), hqlInfo.getParameterList());
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
		// sb.append("where 1=1 ");
		sb.append("where kmImeetingMain.isNotify='1' and kmImeetingMain.docStatus<>'41' ");// 发送会议通知且非取消的会议才统计
		String orgId = (String) parameters.get("orgId");
		// 组装组织部门条件
		StatHqlInfo tmpHqlInfo = StatExecutorUtil.getOrgHqlInfo(
				baseDao.getHibernateSession(), "kmImeetingMain.docDept", orgId);
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMain.fdTemplate",
				(String) parameters.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
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
