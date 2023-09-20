package com.landray.kmss.km.imeeting.service.stat.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.stat.KmImeetingStatExecutor;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 统计执行器抽象类
 */
public abstract class AbstractKmImeetingStatExecutor implements
		KmImeetingStatExecutor {

	/**
	 * 执行查询(chart)
	 */
	@Override
	public final StatResult executeStatChart(Map<String, Object> parameters)
			throws Exception {
		StatResult statResult = buildStatChartResult(
				getQueryChartData(parameters),
				parameters);
		return statResult;
	}

	/**
	 * 查询数据(chart)
	 */
	protected abstract List<Map<String, ?>> getQueryChartData(
			Map<String, Object> parameters) throws Exception;

	/**
	 * 组装成统计结果集的格式(chart)
	 */
	protected abstract StatResult buildStatChartResult(
			List<Map<String, ?>> qureyData, Map<String, Object> parameters);

	/**
	 * 执行查询(list)
	 */
	@Override
	public JSON executeStatList(Map<String, Object> parameters)
			throws Exception {
		JSON statResult = buildStatListResult(getQueryListData(parameters),
				parameters);
		return statResult;
	}


	/**
	 * 查询数据(list)
	 */
	protected abstract List<Map<String, ?>> getQueryListData(
			Map<String, Object> parameters) throws Exception;


	/**
	 * 组装成统计结果集的格式(list)
	 */
	protected abstract JSON buildStatListResult(List<Map<String, ?>> qureyData,
			Map<String, Object> parameters);

	/**
	 * 详情(listDetail)
	 */
	@Override
	public JSON executeStatListDetail(Map<String, Object> parameters)
			throws Exception {
		JSON statResult = buildStatListDetailResult(
				getQueryListDetailData(parameters), parameters);
		return statResult;
	}

	/**
	 * 查询数据(listDetail)
	 */
	protected Map<String, ?> getQueryListDetailData(
			Map<String, Object> parameters) throws Exception {
		return null;
	}

	/**
	 * 组装成统计结果集的格式(list)
	 */
	protected JSON buildStatListDetailResult(Map<String, ?> qureyData,
			Map<String, Object> parameters) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> fileds = new LinkedHashMap<String, String>();
		fileds.put("fdName",
				ResourceUtil.getString("kmImeetingMain.fdName", "km-imeeting"));// 会议名称
		fileds.put("fdHostName",
				ResourceUtil.getString("kmImeetingMain.fdHost", "km-imeeting"));// 主持人
		fileds.put("fdPlace",
				ResourceUtil.getString("kmImeetingMain.fdPlace", "km-imeeting"));// 会议地点
		fileds.put("fdHoldDate", ResourceUtil.getString(
				"kmImeetingMain.fdHoldDate", "km-imeeting"));// 召开时间
		fileds.put("fdFinishDate", ResourceUtil.getString(
				"kmImeetingMain.fdFinishDate", "km-imeeting"));// 结束时间
		fileds.put("docCreator", ResourceUtil.getString(
				"kmImeetingMain.docCreator", "km-imeeting"));// 会议发起人
		fileds.put("docDept",
				ResourceUtil.getString("kmImeetingMain.docDept", "km-imeeting"));// 组织部门
		result.put("fileds", fileds);
		List<KmImeetingMain> items = (List<KmImeetingMain>) qureyData
				.get("datas");
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		if (items != null && !items.isEmpty()) {
			for (KmImeetingMain item : items) {
				Map<String, Object> filedData = new HashMap<String, Object>();
				filedData.put("url",
						"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
								+ item.getFdId());
				filedData.put("fdName", item.getFdName());
				String fdHostName = "";
				if(item.getFdHost()!=null){
					fdHostName=item.getFdHost().getFdName();
				}
				filedData.put("fdHostName", fdHostName);
				String fdPlace = "";
				if (item.getFdPlace() != null) {
					fdPlace = item.getFdPlace().getFdName();
				}
				if (StringUtil.isNotNull(item.getFdOtherPlace())) {
					fdPlace += item.getFdOtherPlace();
				}
				filedData.put("fdPlace", fdPlace);
				filedData.put("fdHoldDate", DateUtil.convertDateToString(
						item.getFdHoldDate(), DateUtil.PATTERN_DATETIME));
				filedData.put("fdFinishDate", DateUtil.convertDateToString(
						item.getFdFinishDate(), DateUtil.PATTERN_DATETIME));
				filedData.put("docCreator", item.getDocCreator().getFdName());
				filedData.put("docDept", item.getDocDept().getFdName());
				datas.add(filedData);
			}
		}
		result.put("datas", datas);
		result.put("page", qureyData.get("page"));
		return JSONObject.fromObject(result);
	}

}
