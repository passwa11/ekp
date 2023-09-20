package com.landray.kmss.km.imeeting.service.stat.executor;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员会议吞吐量执行器
 */
public class KmImeetingPersonStatExecutor extends
		AbstractKmImeetingStatExecutor {

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
		for (int i = 0; i < personIds.size(); i++) {
			SysOrgElement person = sysOrgCoreService.findByPrimaryKey(personIds
					.get(i));
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			String personId = personIds.get(i);
			String personName = person.getFdName();
			tmpMap.put("personId", personId);
			tmpMap.put("personName", personName);
			StatHqlInfo hqlInfo = getHQL(personId, null, parameters);
			Query q = baseDao.getHibernateSession().createQuery(
					hqlInfo.getHqlBlock());
			q.setCacheable(true);
			q.setCacheMode(CacheMode.NORMAL);
			q.setCacheRegion("km-imeeting");
			for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
				q.setParameter(tmpParam.getName(), tmpParam.getValue());
			}
			Object[] result = (Object[]) q.list().get(0);
			Long counts = (Long) result[0];
			tmpMap.put("counts", counts);
			if (counts > 0L) {
				if (result[1] == null) {
					result[1] = 0;
				}
				// 精确到小数点后一位
				Double timeDate = (Double) result[1] / (1000 * 60 * 60);
				tmpMap.put("sum",
						NumberUtil.roundDecimal(timeDate, ResourceUtil
								.getMessage("{km-imeeting:decimal.format}")));
			} else {
				tmpMap.put("sum", 0);
			}
			dataList.add(tmpMap);
		}
		return dataList;
	}

	private StatHqlInfo getHQL(String personId, String fdOperateType,
			Map<String, Object> parameters)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select count( kmImeetingMainFeedback.fdId), ");
		sb.append("sum(kmImeetingMainFeedback.fdMeeting.fdHoldDuration + 0.0) ");
		sb.append("from " + KmImeetingMainFeedback.class.getName()
				+ " kmImeetingMainFeedback ");
		// sb.append(" where 1=1 ");
		sb.append("where kmImeetingMainFeedback.fdMeeting.isNotify='1' and kmImeetingMainFeedback.fdMeeting.docStatus<>'41' ");//
		// 发送会议通知且非取消的会议才统计
		// 回执类型:01参加 02不参加 03找人代理
		if (StringUtil.isNotNull(fdOperateType)) {
			sb.append(" and kmImeetingMainFeedback.fdOperateType=:fdOperateType ");
			hqlInfo.setParameter("fdOperateType", fdOperateType);
		}
		// 组装人员条件
		StatHqlInfo tmpHqlInfo = StatExecutorUtil.getOrgHqlInfo(
				baseDao.getHibernateSession(),
				"kmImeetingMainFeedback.docCreator",
				personId);
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
				"kmImeetingMainFeedback.fdMeeting",
				parameters.get("fdStartDate"), parameters.get("fdEndDate"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		hqlInfo.setHqlBlock(sb.toString());
		return hqlInfo;
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

		String xDataString = "", totalString = "", sumString = "";// x轴内容、会议总数、会议时长
		for (Map<String, ?> item : qureyData) {
			xDataString += item.get("personName").toString() + ",";
			totalString += item.get("counts").toString() + ",";
			sumString += item.get("sum").toString() + ",";
		}

		List<StatAxis> x = new ArrayList<StatAxis>();// x轴信息对象
		if (StringUtil.isNotNull(xDataString)) {
			x.add(new StatAxis(null, "category", xDataString.substring(0,
					xDataString.length() - 1)));
		}
		result.setxAxis(x);
		result.setxAxisRotate("-40");

		List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();// y轴内容
		if (StringUtil.isNotNull(totalString)) {
			Map<String, Object> totalMap = new HashMap<String, Object>();
			totalMap.put("name", ResourceUtil.getString(
					"kmImeetingStat.filed.counts", "km-imeeting"));// 会议总数(件)
			totalMap.put("type", "bar");
			totalMap.put("yAxisIndex", "0");
			totalMap.put("data", StringUtil.isNull(totalString) ? ""
					: totalString.substring(0, totalString.length() - 1));
			seriesData.add(totalMap);
			Map<String, Object> sumMap = new HashMap<String, Object>();
			sumMap.put("name", ResourceUtil.getString(
					"kmImeetingStat.filed.sum", "km-imeeting"));// 会议时长(小时)
			sumMap.put("type", "line");
			sumMap.put("yAxisIndex", "1");
			sumMap.put(
					"data",
					StringUtil.isNull(sumString) ? "" : sumString.substring(0,
							sumString.length() - 1));
			seriesData.add(sumMap);
		}
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
		for (int i = 0; i < personIds.size(); i++) {
			SysOrgElement person = sysOrgCoreService.findByPrimaryKey(personIds
					.get(i));
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			String personId = personIds.get(i);
			String personName = person.getFdName();
			tmpMap.put("personId", personId);
			tmpMap.put("personName", personName);

			Object[] result = getQueryResult(personId, null, parameters);
			Long allCount = (Long) result[0];
			tmpMap.put("allCounts", allCount);// 总参加人数
			
			Long unknowCounts = allCount;
			Long tmpCounts = allCount;
			result = getQueryResult(personId, "02", parameters);
			tmpCounts = (Long) result[0];
			unknowCounts -= tmpCounts;// 减去不参加人数
			tmpMap.put("unattendCounts", tmpCounts);// 不参加人数
			
			result = getQueryResult(personId, "03", parameters);
			tmpCounts = (Long) result[0];
			unknowCounts -= tmpCounts;// 减去代理人数
			tmpMap.put("proxyCounts", tmpCounts);// 找人代理
			
			result = getQueryResult(personId, "01", parameters);
			tmpCounts = (Long) result[0];
			unknowCounts -= tmpCounts;// 减去参加人数
			tmpMap.put("attendCounts", tmpCounts);// 参加人数
			if (tmpCounts > 0L) {
				if (result[1] == null) {
					result[1] = 0;
				}
				// 精确到小数点后一位
				Double timeDate = (Double) result[1] / (1000 * 60 * 60);
				tmpMap.put("attendSum", NumberUtil
						.roundDecimal(timeDate, ResourceUtil
								.getMessage("{km-imeeting:decimal.format}")));
			} else {
				tmpMap.put("attendSum", 0);// 参加时长
			}

			tmpMap.put("unknowCounts", unknowCounts);// 待定人数

			if (allCount > 0L) {
				tmpMap.put(
						"attendPercent",
						NumberUtil
								.roundDecimal(
										tmpCounts * 100 / allCount,
										ResourceUtil
												.getMessage("{km-imeeting:decimal.format}"))
								+ "%");
			} else {
				tmpMap.put("attendPercent", "0%");// 参加比
			}

			dataList.add(tmpMap);
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
			Map<String, Object> filedData = new HashMap<String, Object>();
			filedData.put("personId", item.get("personId"));
			filedData.put("personName", item.get("personName"));
			filedData.put("allCounts", item.get("allCounts"));
			filedData.put("attendCounts", item.get("attendCounts"));
			filedData.put("unattendCounts", item.get("unattendCounts"));
			filedData.put("proxyCounts", item.get("proxyCounts"));
			filedData.put("unknowCounts", item.get("unknowCounts"));
			filedData.put("attendSum", item.get("attendSum"));
			filedData.put("attendPercent", item.get("attendPercent"));
			datas.add(filedData);
		}
		result.put("datas", datas);
		return JSONObject.fromObject(result);
	}

	private Object[] getQueryResult(String personId, String fdOperateType,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = getHQL(personId, fdOperateType, parameters);// 01:参加
		Query q = baseDao.getHibernateSession().createQuery(hqlInfo.getHqlBlock());
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
			query = executeQuery("select kmImeetingMain" + fromBlock
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
		String personId = (String) parameters.get("personId");
		// 组装人员条件
		StatHqlInfo tmpHqlInfo = new StatHqlInfo();
		sb.append(" and kmImeetingMain.kmImeetingMainFeedbacks.fdId=kmImeetingMainFeedback.fdId ");
		sb.append(" and kmImeetingMainFeedback.docCreator.fdId=:personId ");
		hqlInfo.setParameter("personId", personId);
		// 组装会议类型条件
		tmpHqlInfo = StatExecutorUtil.getCategoryHqlInfo(
				"kmImeetingMain.fdTemplate",
				(String) parameters.get("fdTemplateId"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append("and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		// 组装时间条件
		tmpHqlInfo = StatExecutorUtil.getTimeScopeHqlInfo(
				baseDao.getHibernateSession(), "kmImeetingMain",
				parameters.get("fdStartDate"), parameters.get("fdEndDate"));
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
