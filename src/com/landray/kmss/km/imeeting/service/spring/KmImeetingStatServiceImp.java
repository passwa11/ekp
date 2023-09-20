package com.landray.kmss.km.imeeting.service.spring;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.PeriodModel;
import com.landray.kmss.common.model.PeriodTypeModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.km.imeeting.service.IKmImeetingStatService;
import com.landray.kmss.km.imeeting.service.stat.KmImeetingStatExecutor;
import com.landray.kmss.km.imeeting.util.StatExecutorPlugin;
import com.landray.kmss.km.imeeting.util.StatResult;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSON;

/**
 * 会议统计业务接口实现
 */
public class KmImeetingStatServiceImp extends BaseServiceImp implements IKmImeetingStatService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingStatServiceImp.class);

	/**
	 * 执行统计(图表)
	 */
	@Override
	public StatResult statChart(IExtendForm form, RequestContext requestContext)
			throws Exception {
		Map<String, Object> parameters = formatParameter(form, requestContext);
		String statType = (String) parameters.get("fdType");
		StatResult result = null;
		if (StringUtil.isNotNull(statType)) {
			IExtension ext = StatExecutorPlugin.getExtensionForStat(statType);
			if (ext != null) {
				KmImeetingStatExecutor executor = StatExecutorPlugin
						.getStat(ext);
				result = executor.executeStatChart(parameters);
				result.setChartType(StatExecutorPlugin.getChartType(ext));
			}
		}
		if (UserOperHelper.allowLogOper("statChart", getModelName())) {
			String[] statTypes = statType.split("\\.");
			switch (statTypes[0]) {
			case "dept":
				UserOperHelper.setEventType("statMon".equals(statTypes[1])
						? "部门会议吞吐量环比预览" : "部门会议吞吐量预览");
				break;
			case "person":
				UserOperHelper.setEventType("statMon".equals(statTypes[1])
						? "个人会议吞吐量环比预览" : "个人会议吞吐量预览");
				break;
			case "resource":
				UserOperHelper.setEventType("statMon".equals(statTypes[1])
						? "会议室使用率环比预览" : "会议室使用率预览");
				break;
			}
		}
		return result;
	}

	/**
	 * 执行统计(列表)
	 */
	@Override
	public JSON statList(IExtendForm form, RequestContext requestContext)
			throws Exception {
		Map<String, Object> parameters = formatParameter(form, requestContext);
		String statType = (String) parameters.get("fdType");
		JSON result = null;
		if (StringUtil.isNotNull(statType)) {
			IExtension ext = StatExecutorPlugin.getExtensionForStat(statType);
			if (ext != null) {
				KmImeetingStatExecutor executor = StatExecutorPlugin
						.getStat(ext);
				result = executor.executeStatList(parameters);
			}
		}
		return result;
	}

	/**
	 * 执行统计(列表详情)
	 */
	@Override
	public JSON statListDetail(IExtendForm form, RequestContext requestContext)
			throws Exception {
		Map<String, Object> parameters = formatParameter(form, requestContext);
		String statType = (String) parameters.get("fdType");
		JSON result = null;
		if (StringUtil.isNotNull(statType)) {
			IExtension ext = StatExecutorPlugin.getExtensionForStat(statType);
			if (ext != null) {
				KmImeetingStatExecutor executor = StatExecutorPlugin
						.getStat(ext);
				result = executor.executeStatListDetail(parameters);
			}
		}
		return result;
	}

	protected Map<String, Object> formatParameter(Object propObj,
			RequestContext requestContext) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(getParameterMap(requestContext));
		paramMap.putAll(convertObjectToMap(propObj));
		String tempStart = (String) paramMap.get("fdStartDate");
		String tempEnd = (String) paramMap.get("fdEndDate");
		String fdDateType = (String) paramMap.get("fdDateType");
		if ("7".equals(fdDateType) || fdDateType == null) {
			if (StringUtil.isNotNull(tempStart)) {
				paramMap.put("fdStartDate", DateUtil.convertStringToDate(
						tempStart, DateUtil.PATTERN_DATE));
			} else {
				paramMap.put("fdStartDate", null);
			}
			if (StringUtil.isNotNull(tempEnd)) {
				paramMap.put("fdEndDate", DateUtil.convertStringToDate(tempEnd,
						DateUtil.PATTERN_DATE));
			} else {
				paramMap.put("fdEndDate", null);
			}
		} else {
			if (StringUtil.isNotNull(tempStart)) {
				PeriodModel start = PeriodTypeModel.getSinglePeriod(tempStart);
				paramMap.put("fdStartProdDate", start);
				paramMap.put("fdStartDate", start.getFdStart());
			} else {
				paramMap.put("fdStartProdDate", null);
				paramMap.put("fdStartDate", null);
			}
			if (StringUtil.isNotNull(tempEnd)) {
				PeriodModel end = PeriodTypeModel.getSinglePeriod(tempEnd);
				paramMap.put("fdEndProdDate", end);
				paramMap.put("fdEndDate", end.getFdEnd());
			} else {
				paramMap.put("fdEndProdDate", null);
				paramMap.put("fdEndDate", null);
			}
		}
		return paramMap;
	}

	protected Map<String, Object> convertObjectToMap(Object thisObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class clazz = null;
		try {
			clazz = com.landray.kmss.util.ClassUtils.forName(thisObj.getClass().getName());
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				String method = methods[i].getName();
				boolean isProp = methods[i].getParameterTypes().length < 1;
				if (method.startsWith("get") && isProp) {
					try {
						Object value = methods[i].invoke(thisObj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase()
									+ key.substring(1);
							map.put(key, value);
						}
					} catch (Exception e) {
						logger.error(thisObj.getClass().getName() + "类对象读取属性\""
								+ method + "\"出错:" + e);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("无对应类文件" + e);
		}
		return map;
	}

	private Map<String, Object> getParameterMap(RequestContext requestContext) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		if (requestContext != null) {
			Map<String, String[]> params = requestContext.getParameterMap();
			for (Entry<String, String[]> entry : params.entrySet()) {
				String[] para = entry.getValue();
				if (para == null) {
                    continue;
                }
				if (para.length == 0) {
                    continue;
                }
				rtnMap.put(entry.getKey(), para[0]);
			}
		}
		return rtnMap;
	}


}
