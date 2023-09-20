package com.landray.kmss.km.imeeting.service.stat.executor;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.km.imeeting.model.KmImeetingUse;
import com.landray.kmss.km.imeeting.util.StatAxis;
import com.landray.kmss.km.imeeting.util.StatExecutorUtil;
import com.landray.kmss.km.imeeting.util.StatHqlInfo;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议室使用率执行器
 */
public class KmImeetingResourceStatExecutor extends
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
		List<String> resIds = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondIds"));
		List<String> resNames = StatExecutorUtil
				.formatCondtionParameter(parameters.get("queryCondNames"));
		for (int i = 0; i < resIds.size(); i++) {
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			String resId = resIds.get(i);
			String resName = resNames.get(i);
			tmpMap.put("resId", resId);
			tmpMap.put("resName", resName);
			int counts = 0;
			Double timeDate = 0.0;

			// 获取普通会议信息
			StatHqlInfo hqlInfo = getNormalMainHQL(resId, parameters);
			NativeQuery q = baseDao.getHibernateSession().createNativeQuery(hqlInfo.getHqlBlock());
			for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
				q.setParameter(tmpParam.getName(), tmpParam.getValue());
			}
			for (int j = 0; j < q.list().size(); j++) {
				Object[] result = (Object[]) q.list().get(j);
				counts = counts + Integer.valueOf(result[0].toString());
				if (Integer.valueOf(result[0].toString()) > 0) {
					if (result[1] == null) {
						result[1] = 0;
					}
					// 精确到小数点后一位
					timeDate = timeDate + Double.valueOf(result[1].toString())
							/ (1000 * 60 * 60);
				}
			}
			// 获取周期性会议信息
			Map<String, Object> rangeMainResult = getStatInRangeMain(resId,
					parameters);
			int rangeMainCounts = (Integer) rangeMainResult.get("counts");
			if (rangeMainCounts > 0) {
				counts += rangeMainCounts;
				timeDate += (Double) rangeMainResult.get("timeDate")
						/ (1000 * 60 * 60);
			}

			String templateId = (String) parameters.get("fdTemplateId");
			if (StringUtil.isNull(templateId)) {
				// 获取普通预约信息
				hqlInfo = getNormalBookHQL(resId, parameters);
				q = baseDao.getHibernateSession().createNativeQuery(hqlInfo.getHqlBlock());
				for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
					q.setParameter(tmpParam.getName(), tmpParam.getValue());
				}
				for (int j = 0; j < q.list().size(); j++) {
					Object[] result = (Object[]) q.list().get(j);
					counts = counts + Integer.valueOf(result[0].toString());
					if (Integer.valueOf(result[0].toString()) > 0) {
						if (result[1] == null) {
							result[1] = 0;
						}
						// 精确到小数点后一位
						timeDate = timeDate
								+ Double.valueOf(result[1].toString());
					}
				}
				// 获取周期性预约信息
				Map<String, Object> rangeResult = getStatInRangeBook(resId,
						parameters);
				int rangeCounts = (Integer) rangeResult.get("counts");
				if (rangeCounts > 0) {
					counts += rangeCounts;
					timeDate += (Double) rangeResult.get("timeDate");
				}
			}

			tmpMap.put("counts", counts);
			if (timeDate > 0L) {
				tmpMap.put("sum",
						NumberUtil.roundDecimal(timeDate.doubleValue()));
			} else {
				tmpMap.put("sum", 0);
			}
			dataList.add(tmpMap);
		}
		return dataList;
	}

	private Map<String, Object> getStatInRangeMain(String resId,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select m.fd_id, ");
		sb.append("m.fd_hold_duration, ");
		sb.append("m.fd_recurrence_str, ");
		sb.append("m.fd_hold_date, ");
		sb.append("m.fd_finish_date, ");
		sb.append("m.fd_recurrence_last_end ");
		sb.append(
				"from km_imeeting_main m left join km_imeeting_vice_places d on m.fd_id=d.fd_meeting_id ");
		sb.append(
				"where m.is_notify='1' and m.doc_status<>'41' and (m.fd_recurrence_str is not null) ");// 发送会议通知且非取消的会议才统计
		// 组装会议室资源条件
		if (StringUtil.isNotNull(resId)) {
			sb.append(
					" and (m.fd_place_id=:resIdForMeeting or d.fd_res_id=:resIdForVice) ");
			hqlInfo.setParameter("resIdForMeeting", resId);
			hqlInfo.setParameter("resIdForVice", resId);
		}
		// 组装会议类型条件
		String templateId = (String) parameters.get("fdTemplateId");
		if (StringUtil.isNotNull(templateId)) {
			List<String> ids = ArrayUtil.convertArrayToList(templateId
					.split(";"));
			sb.append(" and " + HQLUtil.buildLogicIN("m.fd_template_id",
					ids));
		}
		// 组装时间条件
		Object startTime = parameters.get("fdStartDate");
		Object endTime = parameters.get("fdEndDate");
		if (startTime != null) {
			sb.append(" and m.fd_recurrence_last_end >= :start");
			hqlInfo.setParameter("start",
					StatExecutorUtil.clearTime(startTime));
		}
		if (endTime != null) {
			sb.append(" and m.fd_hold_date <= :end");
			hqlInfo.setParameter("end", StatExecutorUtil.clearTime(endTime));
		}
		hqlInfo.setHqlBlock(sb.toString());
		sb.append(" group by m.fd_id ) T1");
		NativeQuery q = baseDao.getHibernateSession().createNativeQuery(hqlInfo.getHqlBlock());
		q.setCacheable(true);
		q.setCacheMode(CacheMode.NORMAL);
		q.setCacheRegion("km-imeeting");
		q.addScalar("fd_id",StandardBasicTypes.STRING).addScalar("fd_hold_duration",StandardBasicTypes.DOUBLE)
				.addScalar("fd_recurrence_str",StandardBasicTypes.STRING).addScalar("fd_hold_date",StandardBasicTypes.TIMESTAMP)
				.addScalar("fd_finish_date",StandardBasicTypes.TIMESTAMP).addScalar("fd_recurrence_last_end",StandardBasicTypes.TIMESTAMP);
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
						(Timestamp) result[3], (Date) startTime,
						(Date) endTime);
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

	// 获取周期性会议预定的统计数据
	private Map<String, Object> getStatInRangeBook(String resId,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select b.fd_id, ");
		sb.append("b.fd_hold_duration, ");
		sb.append("b.fd_recurrence_str, ");
		sb.append("b.fd_hold_date, ");
		sb.append("b.fd_finish_date, ");
		sb.append("b.fd_recurrence_last_end ");
		sb.append("from km_imeeting_book b ");
		sb.append(
				" where 1=1 and b.fd_hold_duration is not null and b.fd_recurrence_str is not null");// 周期性会议单独拿出来计算统计
		// 组装会议室资源条件
		if (StringUtil.isNotNull(resId)) {
			sb.append(" and b.fd_place_id=:resIdForBook ");
			hqlInfo.setParameter("resIdForBook", resId);
		}
		Object startTime = parameters.get("fdStartDate");
		Object endTime = parameters.get("fdEndDate");
		if (startTime != null) {
			sb.append(" and b.fd_recurrence_last_end >= :start");
			hqlInfo.setParameter("start",
					StatExecutorUtil.clearTime(startTime));
		}
		if (endTime != null) {
			sb.append(" and b.fd_hold_date <= :end");
			hqlInfo.setParameter("end", StatExecutorUtil.clearTime(endTime));
		}
		hqlInfo.setHqlBlock(sb.toString());
		NativeQuery q = baseDao.getHibernateSession().createNativeQuery(hqlInfo.getHqlBlock());
		q.setCacheable(true);
		q.setCacheMode(CacheMode.NORMAL);
		q.setCacheRegion("km-imeeting");
		q.addScalar("fd_id",StandardBasicTypes.STRING).addScalar("fd_hold_duration",StandardBasicTypes.DOUBLE)
				.addScalar("fd_recurrence_str",StandardBasicTypes.STRING).addScalar("fd_hold_date",StandardBasicTypes.TIMESTAMP)
				.addScalar("fd_finish_date",StandardBasicTypes.TIMESTAMP).addScalar("fd_recurrence_last_end",StandardBasicTypes.TIMESTAMP);
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
						(Timestamp) result[3], (Date) startTime,
						(Date) endTime);
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

	private StatHqlInfo getNormalMainHQL(String resId,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select  count(fdId) as c, ");
		sb.append("sum(duration + 0.0) as s from (");

		sb.append(
				"select m.fd_id as fdId,sum(m.fd_hold_duration) as duration ");
		sb.append(
				"from km_imeeting_main m left join km_imeeting_vice_places d on m.fd_id=d.fd_meeting_id ");
		sb.append(
				"where m.is_notify='1' and m.doc_status<>'41' and (m.fd_recurrence_str is null or m.fd_recurrence_str='NO') ");// 发送会议通知且非取消的会议才统计
		// 组装会议室资源条件
		if (StringUtil.isNotNull(resId)) {
			sb.append(
					" and (m.fd_place_id=:resIdForMeeting or d.fd_res_id=:resIdForVice) ");
			hqlInfo.setParameter("resIdForMeeting", resId);
			hqlInfo.setParameter("resIdForVice", resId);
		}
		// 组装会议类型条件
		String templateId = (String) parameters.get("fdTemplateId");
		if (StringUtil.isNotNull(templateId)) {
			List<String> ids = ArrayUtil.convertArrayToList(templateId
					.split(";"));
			sb.append(" and " + HQLUtil.buildLogicIN("m.fd_template_id",
							ids));
		}
		// 组装时间条件
		StatHqlInfo tmpHqlInfo = getTimeScopeSqlInfo("m",
				parameters.get("fdStartDate"), parameters.get("fdEndDate"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append(" and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}
		sb.append(" group by m.fd_id ) T1");
		String hqlBlock = sb.toString();
		if (StringUtil.isNotNull(hqlBlock)) {
			hqlBlock = "select * from (" + hqlBlock + " ) total";
		}
		hqlInfo.setHqlBlock(hqlBlock);
		return hqlInfo;
	}

	private StatHqlInfo getNormalBookHQL(String resId,
			Map<String, Object> parameters) throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		String templateId = (String) parameters.get("fdTemplateId");
		if (StringUtil.isNull(templateId)) {
			sb.append("select count( b.fd_id) as c, ");
			sb.append(" sum( b.fd_hold_duration + 0.0 ) as s ");
			sb.append("from km_imeeting_book b ");
			sb.append(
					" where 1=1 and b.fd_hold_duration is not null and (b.fd_recurrence_str is null or b.fd_recurrence_str='NO')");// 周期性会议单独拿出来计算统计，这里不统计
			// 组装会议室资源条件
			if (StringUtil.isNotNull(resId)) {
				sb.append(" and b.fd_place_id=:resIdForBook ");
				hqlInfo.setParameter("resIdForBook", resId);
			}
			// 组装时间条件
			StatHqlInfo tmpHqlInfo = getTimeScopeSqlInfo("b",
					parameters.get("fdStartDate"), parameters.get("fdEndDate"));
			if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
				sb.append(" and " + tmpHqlInfo.getHqlBlock() + " ");
				hqlInfo.setParameter(tmpHqlInfo.getParameterList());
			}
		}
		String hqlBlock = sb.toString();
		if (StringUtil.isNotNull(hqlBlock)) {
			hqlBlock = "select * from (" + hqlBlock + " ) total";
		}
		hqlInfo.setHqlBlock(hqlBlock);
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
		y.add(new StatAxis(ResourceUtil.getString(
				"kmImeetingStat.filed.place.sum", "km-imeeting"), "value", null));// 会议占用时长(小时)
		result.setyAxis(y);

		String xDataString = "", totalString = "", sumString = "";// x轴内容、会议总数、会议时长
		for (Map<String, ?> item : qureyData) {
			xDataString += item.get("resName").toString() + ",";
			totalString += item.get("counts").toString() + ",";
			sumString += item.get("sum").toString() + ",";
		}

		List<StatAxis> x = new ArrayList<StatAxis>();// x轴信息对象
		x.add(new StatAxis(null, "category", xDataString.substring(0,
				xDataString.length() - 1)));
		result.setxAxis(x);
		result.setxAxisRotate("-40");

		List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();// y轴内容
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("name", ResourceUtil.getString(
				"kmImeetingStat.filed.counts", "km-imeeting"));// 会议总数(件)
		totalMap.put("type", "bar");
		totalMap.put("yAxisIndex", "0");
		totalMap.put(
				"data",
				StringUtil.isNull(totalString) ? "" : totalString.substring(0,
						totalString.length() - 1));
		seriesData.add(totalMap);
		Map<String, Object> sumMap = new HashMap<String, Object>();
		sumMap.put("name", ResourceUtil.getString(
				"kmImeetingStat.filed.place.sum",
				"km-imeeting"));// 会议占用时长(小时)
		sumMap.put("type", "line");
		sumMap.put("yAxisIndex", "1");
		sumMap.put(
				"data",
				StringUtil.isNull(sumString) ? "" : sumString.substring(0,
						sumString.length() - 1));
		seriesData.add(sumMap);
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
		fileds.put("resName",
				ResourceUtil.getString("kmImeetingRes.fdPlace", "km-imeeting"));// 会议室
		fileds.put("counts", ResourceUtil.getString(
				"kmImeetingStat.filed.counts", "km-imeeting"));// 会议总数(件)
		fileds.put("sum", ResourceUtil.getString(
				"kmImeetingStat.filed.place.sum", "km-imeeting"));// 会议占用时长(小时)
		result.put("fileds", fileds);

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (Map<String, ?> item : qureyData) {
			Map<String, Object> filedData = new HashMap<String, Object>();
			filedData.put("resId", item.get("resId"));
			filedData.put("resName", item.get("resName"));
			filedData.put("counts", item.get("counts"));
			filedData.put("sum", item.get("sum"));
			datas.add(filedData);
		}
		result.put("datas", datas);
		return JSONObject.fromObject(result);
	}

	@Override
	protected Map<String, ?> getQueryListDetailData(
			Map<String, Object> parameters) throws Exception {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		StatHqlInfo hqlInfo = getListDetailSqlBlock(parameters);
		NativeQuery query = baseDao.getHibernateSession().createNativeQuery(
				"select count(*) from ( " + hqlInfo.getHqlBlock() + " ) total");
		for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
			query.setParameter(tmpParam.getName(), tmpParam.getValue());
		}
		int total = Integer.valueOf(query.list().get(0).toString());
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
			String orderBy = "order by fdHoldDate desc ";
			query = baseDao.getHibernateSession().createNativeQuery(
					"select * from ( " + hqlInfo.getHqlBlock() + " ) total "
							+ orderBy);
			for (HQLParameter tmpParam : hqlInfo.getParameterList()) {
				query.setParameter(tmpParam.getName(), tmpParam.getValue());
			}
			// 实体化
			query.addScalar("fdId", StandardBasicTypes.STRING)
					.addScalar("fdPlace", StandardBasicTypes.STRING)
					.addScalar("fdName", StandardBasicTypes.STRING)
					.addScalar("fdHoldDate", StandardBasicTypes.TIMESTAMP)
					.addScalar("fdFinishDate", StandardBasicTypes.TIMESTAMP)
					.addScalar("personName", StandardBasicTypes.STRING)
					.addScalar("docStatus", StandardBasicTypes.STRING)
					.addScalar("isMeeting", StandardBasicTypes.BOOLEAN);
			query.setResultTransformer(Transformers
					.aliasToBean(KmImeetingUse.class));
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
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> fileds = new LinkedHashMap<String, String>();
		fileds.put("fdName",
				ResourceUtil.getString("kmImeetingMain.fdName", "km-imeeting"));// 会议名称
		fileds.put("fdPlace",
				ResourceUtil.getString("kmImeetingMain.fdPlace", "km-imeeting"));// 会议地点
		fileds.put("fdHoldDate", ResourceUtil.getString(
				"kmImeetingMain.fdHoldDate", "km-imeeting"));// 召开时间
		fileds.put("fdFinishDate", ResourceUtil.getString(
				"kmImeetingMain.fdFinishDate", "km-imeeting"));// 结束时间
		result.put("fileds", fileds);
		List<KmImeetingUse> items = (List<KmImeetingUse>) qureyData
				.get("datas");
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		if (items != null && !items.isEmpty()) {
			for (KmImeetingUse item : items) {
				Map<String, Object> filedData = new HashMap<String, Object>();
				if (item.getIsMeeting()) {
					filedData.put("url",
							"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
									+ item.getFdId());
				}
				String fdName = item.getFdName();
				if (!item.getIsMeeting()) {
					fdName = "("
							+ ResourceUtil.getString("kmImeetingBook.book",
									"km-imeeting") + ")" + fdName;
				}
				filedData.put("fdName", fdName);
				String fdPlace = "";
				if (item.getFdPlace() != null) {
					fdPlace = item.getFdPlace();
				}
				filedData.put("fdPlace", fdPlace);
				filedData.put("fdHoldDate", DateUtil.convertDateToString(
						item.getFdHoldDate(), DateUtil.PATTERN_DATETIME));
				filedData.put("fdFinishDate", DateUtil.convertDateToString(
						item.getFdFinishDate(), DateUtil.PATTERN_DATETIME));
				datas.add(filedData);
			}
		}
		result.put("datas", datas);
		result.put("page", qureyData.get("page"));
		return JSONObject.fromObject(result);
	}

	private StatHqlInfo getListDetailSqlBlock(Map<String, Object> parameters)
			throws Exception {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("select m.fd_id as fdId,ra.fd_name as fdPlace,m.fd_name as fdName ,m.fd_hold_date as fdHoldDate,m.fd_finish_date as fdFinishDate,pa.fd_name as personName,m.doc_status as docStatus,1 as isMeeting ");
		sb.append("from km_imeeting_main m,sys_org_element pa,km_imeeting_res ra ");
		sb.append("where m.doc_creator_id=pa.fd_id and m.fd_place_id=ra.fd_id and m.is_notify='1' and m.doc_status<>'41' ");// 发送会议通知且非取消的会议才统计
		// 组装会议室资源条件
		String resId = (String) parameters.get("resId");
		if (StringUtil.isNotNull(resId)) {
			sb.append(" and m.fd_place_id=:resIdForMeeting ");
			hqlInfo.setParameter("resIdForMeeting", resId);
		}
		// 组装会议类型条件
		String templateId = (String) parameters.get("fdTemplateId");
		if (StringUtil.isNotNull(templateId)) {
			List<String> ids = ArrayUtil.convertArrayToList(templateId
					.split(";"));
			sb.append(" and " + HQLUtil.buildLogicIN("m.fd_template_id", ids));
		}
		// 组装时间条件
		StatHqlInfo tmpHqlInfo = getTimeScopeSqlInfo("m",
				parameters.get("fdStartDate"), parameters.get("fdEndDate"));
		if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
			sb.append(" and " + tmpHqlInfo.getHqlBlock() + " ");
			hqlInfo.setParameter(tmpHqlInfo.getParameterList());
		}

		// #9427 如果筛选条件没有会议模板时，会议室统计还要包括对会议室预约情况的统计.....
		if (StringUtil.isNull(templateId)) {
			sb.append(" union  select b.fd_id as fdId,rb.fd_name as fdPlace,b.fd_name as fdName,b.fd_hold_date as fdHoldDate,b.fd_finish_date as fdFinishDate,pb.fd_name as personName,'30' as docStatus,0 as isMeeting ");
			sb.append("from km_imeeting_book b,sys_org_element pb,km_imeeting_res rb ");
			sb.append(" where b.doc_creator_id=pb.fd_id and b.fd_place_id=rb.fd_id  and b.fd_hold_duration is not null");

			// 组装会议室资源条件
			if (StringUtil.isNotNull(resId)) {
				sb.append(" and b.fd_place_id=:resIdForBook ");
				hqlInfo.setParameter("resIdForBook", resId);
			}
			// 组装时间条件
			tmpHqlInfo = getTimeScopeSqlInfo("b",
					parameters.get("fdStartDate"), parameters.get("fdEndDate"));
			if (StringUtil.isNotNull(tmpHqlInfo.getHqlBlock())) {
				sb.append(" and " + tmpHqlInfo.getHqlBlock() + " ");
				hqlInfo.setParameter(tmpHqlInfo.getParameterList());
			}
		}

		hqlInfo.setHqlBlock(sb.toString());
		return hqlInfo;
	}

	private StatHqlInfo getTimeScopeSqlInfo(String docItemName,
			Object startTime, Object endTime) {
		StatHqlInfo hqlInfo = new StatHqlInfo();
		StringBuffer tmpWhereBlock = new StringBuffer();
		if (startTime != null) {
			String id = IDGenerator.generateID();
			tmpWhereBlock.append(docItemName).append(
					".fd_hold_date >= :startTime" + id);
			hqlInfo.setParameter("startTime" + id,
					StatExecutorUtil.clearTime(startTime));
		}
		if (endTime != null) {
			String id = IDGenerator.generateID();
			if (StringUtil.isNotNull(tmpWhereBlock.toString())) {
				tmpWhereBlock.append(" and " + docItemName).append(
						".fd_hold_date <= :endTime" + id);
			} else {
				tmpWhereBlock.append(docItemName).append(
						".fd_hold_date <= :endTime" + id);
			}
			hqlInfo.setParameter("endTime" + id,
					StatExecutorUtil.fillTime(endTime));
		}
		hqlInfo.setHqlBlock(tmpWhereBlock.toString());
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
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		for (HQLParameter para : params) {
			query.setParameter(para.getName(), para.getValue());
		}
		return query;
	}

}
