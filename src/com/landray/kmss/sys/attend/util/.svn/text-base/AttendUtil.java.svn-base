package com.landray.kmss.sys.attend.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.ua.UserAgent;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.xform.maindata.util.MainDataUtil;
import com.landray.kmss.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AttendUtil {
	// 缓存数据
	private static Map<String, JSONObject> datasMap = new HashMap<String, JSONObject>();
	private static Map<String, String> dingAttendMap = new HashMap<String, String>();

	private static ISysAttendConfigService sysAttendConfigService;

	private static ISysAttendConfigService getSysAttendConfigService() {
		if (sysAttendConfigService == null) {
			sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil.getBean("sysAttendConfigService");
		}
		return sysAttendConfigService;
	}

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AttendUtil.class);

	private static ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	private static ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		if (sysTimeLeaveRuleService == null) {
			sysTimeLeaveRuleService = (ISysTimeLeaveRuleService) SpringBeanUtil.getBean("sysTimeLeaveRuleService");
		}
		return sysTimeLeaveRuleService;
	}

	public static int getWeek(Date date) {
		Calendar ca = Calendar.getInstance();
		if (date != null) {
			ca.setTime(date);
		}
		int week = ca.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0) {
			week = 7;
		}
		return week;
	}

	/**
	 * 分隔List
	 * 
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static List<List> splitList(List list, int pageSize) {
		int listSize = list.size(); // list的大小
		int page = (listSize + (pageSize - 1)) / pageSize; // 页数
		List<List> listArray = new ArrayList<List>(); // 创建list数组,用来保存分割后的list
		for (int i = 0; i < page; i++) {
			List subList = new ArrayList();
			for (int j = 0; j < listSize; j++) {
				int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
				if (pageIndex == (i + 1)) {
					subList.add(list.get(j));
				}

				if ((j + 1) == ((j + 1) * pageSize)) {
					break;
				}
			}
			listArray.add(subList); // 将分割后的list放入对应的数组的位中
		}
		return listArray;
	}

	public static Date getMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getDate(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取中间时间
	 * @description:
	 * @param date
	 * @param day
	 * @return: java.util.Date
	 * @author: wangjf
	 * @time: 2022/3/3 9:51 下午
	 */
	public static Date getMiddleDate(Date date, int day){
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getEndDate(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date addDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	/**
	 * 增加时间
	 * @param date
	 * @return
	 */
	public static Date addDate(Date date,int addType,int addNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(addType, addNumber);
		return calendar.getTime();
	}
	/**
	 * 去除秒
	 * @param date
	 * @return
	 */
	public static Date removeSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取月份中最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static int getLastDayOfMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int maxDay = ca.getActualMaximum(Calendar.DATE);
		return maxDay;
	}

	/**
	 * 某月有多少个工作日
	 * 
	 * @param date
	 * @param workDays
	 *            计算星期几
	 * @return
	 */
	public static int getTotalDaysOfMonth(Date date, String[] workDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		int week = cal.get(Calendar.DAY_OF_WEEK);// 本月第一天星期几
		boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
		if (isFirstSunday) {
			week = week - 1;
			if (week == 0) {
				week = 7;
			}
		}
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int count = 0;
		for (String w : workDays) {
			int workWeek = Integer.parseInt(w);
			int offset = workWeek - week >= 0 ? workWeek - week + 1 : workWeek - week + 8;
			count = count + (days - offset) / 7 + 1;
		}
		return count;

	}

	public static Date joinYMDandHMS(Date ymd, Date hms) {
		if (ymd == null || hms == null) {
			return null;
		}
		Date rtnDate = new Date(ymd.getTime());
		rtnDate.setHours(hms.getHours());
		rtnDate.setMinutes(hms.getMinutes());
		rtnDate.setSeconds(hms.getSeconds());
		return rtnDate;
	}

	public static boolean isSameDate(Date fdStartDate, Date fdEndDate) {
		if (fdStartDate == null || fdEndDate == null) {
			return false;
		}
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(fdStartDate);

		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(fdEndDate);

		return ca1.get(Calendar.YEAR) == ca2.get(Calendar.YEAR) && ca1.get(Calendar.MONTH) == ca2.get(Calendar.MONTH)
				&& ca1.get(Calendar.DAY_OF_MONTH) == ca2.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHMinutes(Date date) {
		return date.getHours() * 60 + date.getMinutes();
	}

	public static String buildLikeHql(String field, List<String> valueList) {
		if (StringUtil.isNull(field) || valueList == null || valueList.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for (int i = 0; i < valueList.size(); i++) {
			String prefix = i == 0 ? "" : " or ";
			sb.append(prefix + field + " like '%" + valueList.get(i) + "%'");
		}
		sb.append(")");
		return sb.toString();
	}

	public static String getClientType(RequestContext request) {
		HttpServletRequest hRequest = request.getRequest();
		String uaStr = hRequest.getHeader("User-Agent");

		if (StringUtil.isNotNull(uaStr)) {
			UserAgent ua = new UserAgent(uaStr);
			int clientType = MobileUtil.getClientType(request);
			switch (clientType) {
			case MobileUtil.KK_IPHONE:
			case MobileUtil.KK_ANDRIOD:
			case MobileUtil.KK5_IPHONE:
			case MobileUtil.KK5_ANDRIOD:
				return ResourceUtil.getString("sysAttendMain.export.fdClientInfo.kk", "sys-attend");
			case MobileUtil.DING_ANDRIOD:
				return ResourceUtil.getString("sysAttendMain.export.fdClientInfo.ding", "sys-attend");
			case MobileUtil.THIRD_WEIXIN:
			case MobileUtil.THIRD_WXWORK:
				return ResourceUtil.getString("sysAttendMain.export.fdClientInfo.weixin", "sys-attend");
			default:
				return ua.getBrowser().getName();
			}
		}
		return "";
	}

	/**
	 * 获取设备类型,如:kk,ding
	 * 
	 * @return
	 */
	public static String getDeviceClientType(HttpServletRequest request) {
		int clientType = MobileUtil.getClientType(request);
		if (clientType >= 7 && clientType <= 10) {
			return "kk";
		}
		if (clientType == 11) {
			return "ding";
		}
		return "";
	}

	public static String getOperatingSystem(RequestContext request) {
		HttpServletRequest hRequest = request.getRequest();
		String uaStr = hRequest.getHeader("User-Agent");

		if (StringUtil.isNotNull(uaStr)) {
			UserAgent ua = new UserAgent(uaStr);
			return ua.getOperatingSystem().getName();
		}
		return "";
	}

	/**
	 * 判断坐标类型
	 * 
	 * @param fdLatLng
	 * @return
	 */
	public static int getCoordType(String fdLatLng) {
		if (fdLatLng.indexOf("bd09:") > -1) {
			return 3;
		}
		if (fdLatLng.indexOf("gcj02:") > -1) {
			return 5;
		}
		return 3;
	}

	/**
	 * 格式化坐标
	 * 
	 * @param fdLatLng
	 * @return
	 */
	public static String formatCoord(String fdLatLng) {
		if (StringUtil.isNull(fdLatLng)) {
			return "";
		}
		String prefix = "bd09:";
		fdLatLng = fdLatLng.replace(";", ",");
		if (fdLatLng.indexOf("gcj02:") > -1 || fdLatLng.indexOf("bd09:") > -1) {
			return fdLatLng;
		}
		return prefix + fdLatLng;
	}

	public static String getStatStatusRender(net.sf.json.JSONObject json) {
		String value=json.getString("value");
		if (StringUtil.isNull(value)) {
			return "";
		}
		if ("03".equals(value) || ",03".equals(value)) {
			return ResourceUtil.getString("sysAttendStatMonth.fdAbsent",
					"sys-attend");
		}
		if ("01".equals(value) || ",01".equals(value)) {
			return ResourceUtil.getString("sysAttendStatMonth.fdStatus",
					"sys-attend");
		}
		if ("13".equals(value) || ",13".equals(value)) {
			return ResourceUtil.getString("sysAttendMain.fdStatus.overtime",
					"sys-attend");
		}
		if ("02".equals(value) || ",02".equals(value)) {
			return ResourceUtil.getString("sysAttendReport.fdStatus.rest",
					"sys-attend");
		}
		StringBuffer txt = new StringBuffer();
		if (value.indexOf("071") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.unSign", "sys-attend"));
		}
		if (value.indexOf("081") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.late", "sys-attend"));
		}
		if (value.indexOf("091") > -1) {
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil
							.getString("sysAttendMain.fdStatus.late",
									"sys-attend"))
					.append(" ")
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("141") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("211") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdStatus", "sys-attend"));
		}
		if (value.indexOf("101") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.unSign", "sys-attend"));
		}
		if (value.indexOf("111") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.left", "sys-attend"));
		}
		if (value.indexOf("121") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.left", "sys-attend"))
					.append(" ")
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("151") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("212") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdStatus", "sys-attend"));
		}
		if (value.indexOf("072") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.unSign", "sys-attend"));
		}
		if (value.indexOf("082") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.late", "sys-attend"));
		}
		if (value.indexOf("092") > -1) {
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.late", "sys-attend"))
					.append(" ")
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("142") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("213") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdWorkType.onwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdStatus", "sys-attend"));
		}
		if (value.indexOf("102") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.unSign", "sys-attend"));
		}
		if (value.indexOf("112") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.left", "sys-attend"));
		}
		if (value.indexOf("122") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendMain.fdStatus.left", "sys-attend"))
					.append(" ")
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("152") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdOutside", "sys-attend"));
		}
		if (value.indexOf("214") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(
					ResourceUtil.getString("sysAttendMain.fdWorkType.offwork",
					"sys-attend"))
					.append(ResourceUtil.getString(
							"sysAttendStatMonth.fdStatus", "sys-attend"));
		}
		if (value.indexOf("05") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendStatMonth.fdTrip",
					"sys-attend"));
		}
		if (value.indexOf("06") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendStatMonth.fdOutgoing",
					"sys-attend"));

			if(json.containsKey("fdOutgoingTime")){
				net.sf.json.JSONArray jsonArray = (net.sf.json.JSONArray) json.get("fdOutgoingTime");
				txt.append("(").append(Joiner.on(";").join(jsonArray)).append(")");
			}
		}
		if (value.indexOf("04") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append(ResourceUtil.getString("sysAttendMain.fdStatus.askforleave", "sys-attend"));
			//请假明细时间
			Object fdOffTimeObj =json.get("fdOffTime");
			net.sf.json.JSONObject fdOffTimeJson =null;
			if(fdOffTimeObj !=null){
				fdOffTimeJson =json.getJSONObject("fdOffTime");
			}
			StringBuilder offValue=new StringBuilder();;
			if (json.containsKey("offMap")) {
				Map<String, net.sf.json.JSONObject> offMap=(Map<String, net.sf.json.JSONObject>) json.get("offMap");
				if(!offMap.isEmpty()) {
					for(net.sf.json.JSONObject offObj : offMap.values()) {
						String leaveType =  offObj.getString("fdOffTypeText");
						String fdStatType = (String) offObj.get("fdStatType");
						String fdNoonText = (String) offObj.get("fdNoonText");
						String fdOffType = (String) offObj.get("fdOffType");
						if ("2".equals(fdStatType)
								&& StringUtil.isNotNull(fdNoonText)) {
							leaveType += "(" + fdNoonText + ")";
						}
						if(StringUtil.isNotNull(leaveType)){
							offValue.append("(").append(leaveType).append(")");
						}
						if(fdOffTimeJson !=null){
							for (Iterator it = fdOffTimeJson.keys(); it.hasNext(); ) {
								String key = (String) it.next();
								if(key.indexOf(fdOffType) > -1){
									offValue.append("(").append(fdOffTimeJson.get(key)).append(")");
								}
							}
						}
						offValue.append("\n");
					}
				}
			}
			txt.append(offValue);
		}
		if (value.indexOf(",03") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append( ResourceUtil.getString("sysAttendStatMonth.fdAbsent","sys-attend"));
		}
		if (value.indexOf(",13") > -1) {
			txt.append(StringUtil.isNotNull(txt.toString()) ? "\n" : "");
			txt.append( ResourceUtil.getString("sysAttendMain.fdStatus.overtime","sys-attend"));
		}
		return txt.toString();
	}
	/**
	 * <p>
	 * 废除该方法，如果要调用配置类请参考 {@link BaseAppConfig#getAppConfigByClassName}
	 * <pre>
	 *     //获取钉钉集成开关
	 * 		String attendanceEnableds ="";
	 * 		BaseAppConfig dingConfig =BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.ding.model.DingConfig");
	 * 		if(dingConfig!=null){
	 * 			attendanceEnableds = dingConfig.getDataMap().get("attendanceEnabled");
	 *      }
	 * </pre>
	 * </p>
	 * @param modelName
	 * @param method
	 * @return: java.lang.Object
	 */
	@Deprecated
	public static Object getAppConfigValue(String modelName, String method) {
		if (com.landray.kmss.util.ModelUtil.isExisted(modelName)) {
			try {
				Class clz = com.landray.kmss.util.ClassUtils.forName(modelName);
				Object instance = clz.newInstance();
				Object value = clz.getMethod(method, null).invoke(instance, null);
				return value;
			} catch (Exception e) {
			}
		}
		return "";
	}

	// 判断kk是否集成开启
	public static boolean isEnableKKConfig() {
		try {
			JSONObject data = new JSONObject();
			if (datasMap.containsKey("kk")) {
				data = datasMap.get("kk");
			}
			boolean flag = false;
			if (data.containsKey("updteTime")) {
				Long updteTime = (Long) data.get("updteTime");
				Boolean isEnable = data.getBoolean("isEnable");
				int expiresTime = 2 * 60 * 1000;// 默认有效期(2分钟)
				Long nowTime = System.currentTimeMillis();
				flag = nowTime - updteTime > expiresTime;
				if (!flag) {
					return isEnable;
				}
			}
			if (data.isEmpty() || flag) {
				String className = "com.landray.kmss.third.im.kk.service.spring.KkImConfigServiceImp";
				if (com.landray.kmss.util.ModelUtil.isExisted(className)) {
					Class clz = com.landray.kmss.util.ClassUtils.forName(className);
					Object instance = clz.newInstance();
					Method method = clz.getMethod("setBaseDao", IBaseDao.class);
					method.invoke(instance, new Object[] { (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao") });
					Boolean value = (Boolean) clz.getMethod("isEnableKKConfig", null).invoke(instance, null);
					boolean isEnable = Boolean.TRUE.equals(value);
					data.put("isEnable", isEnable);
					data.put("updteTime", System.currentTimeMillis());
					datasMap.put("kk", data);

					return isEnable;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断钉钉或蓝钉是否集成开启
	 * @param
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/5/25 3:32 下午
	 */
	public static boolean isEnableDingConfig() {
		JSONObject data = new JSONObject();
		if (datasMap.containsKey("ding")) {
			data = datasMap.get("ding");
		}
		boolean flag = false;
		if (data.containsKey("updteTime")) {
			Long updteTime = (Long) data.get("updteTime");
			Boolean isEnable = data.getBoolean("isEnable");
			int expiresTime = 2 * 60 * 1000;// 默认有效期(2分钟)
			Long nowTime = System.currentTimeMillis();
			flag = nowTime - updteTime > expiresTime;
			if (!flag) {
				return isEnable;
			}
		}
		if (data.isEmpty() || flag) {
			boolean isEnable = false;
			//钉钉
			BaseAppConfig dingConfig = BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.ding.model.DingConfig");
			if(dingConfig != null){
				String value = dingConfig.getDataMap().get("dingEnabled");
				isEnable = "true".equals(value);
			}
			// 蓝钉
			BaseAppConfig lDingConfig = BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.lding.model.LdingConfig");
			if(lDingConfig != null){
				String value = lDingConfig.getDataMap().get("ldingEnabled");
				isEnable = "true".equals(value);
			}
			data.put("isEnable", isEnable);
			data.put("updteTime", System.currentTimeMillis());
			datasMap.put("ding", data);
			return isEnable;
		}
		return false;
	}

	/**
	 * 判断钉钉集成是否开启(非蓝钉)
	 * @param
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/5/25 3:32 下午
	 */
	public static boolean isEnableDing() {
		BaseAppConfig dingConfig = BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.ding.model.DingConfig");
		if(dingConfig != null){
			String value = dingConfig.getDataMap().get("dingEnabled");
			return  "true".equals(value);
		}
		return false;
	}

	/**
	 * 判断企业微信集成是否开启
	 * @param
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/5/25 3:31 下午
	 */
	public static boolean isEnableWx() {
		BaseAppConfig weixinWorkConfig = BaseAppConfig.getAppConfigByClassName("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
		if(weixinWorkConfig != null){
			String value = weixinWorkConfig.getDataMap().get("wxEnabled");
			return  "true".equals(value);
		}
		return false;
	}

	public static String getLeaveTypeText(Integer leaveType) throws Exception {
		if (leaveType != null) {
			SysTimeLeaveRule sysTimeLeaveRule = getLeaveRuleByType(leaveType);
			if (sysTimeLeaveRule != null) {
				return sysTimeLeaveRule.getFdName();
			}
		}
		return "";
	}

	/**
	 * 根据假期编号获取请假规则
	 * 
	 * @param leaveType
	 * @return
	 */
	public static SysTimeLeaveRule getLeaveRule(String leaveType) {
		SysTimeLeaveRule sysTimeLeaveRule = getSysTimeLeaveRuleService().getLeaveRuleByType(leaveType);
		return sysTimeLeaveRule;
	}

	/**
	 * 该方法废弃,不建议使用
	 * 
	 * @param leaveName
	 *            假期名称
	 * @return
	 */
	public static SysTimeLeaveRule getLeaveRuleByName(String leaveName) {
		SysTimeLeaveRule sysTimeLeaveRule = getSysTimeLeaveRuleService().getLeaveRuleByName(leaveName);
		return sysTimeLeaveRule;
	}

	/**
	 * 主要是兼容处理(注意参数类型为Integer类型)
	 * 
	 * @param bussType
	 * @return
	 */
	public static SysTimeLeaveRule getLeaveRuleByType(Integer bussType) {
		if (bussType == null) {
			return null;
		}
		List<SysTimeLeaveRule> ruleList = getSysTimeLeaveRuleService().getLeaveRuleList(String.valueOf(bussType));
		for (SysTimeLeaveRule rule : ruleList) {
			if (bussType.equals(Integer.valueOf(rule.getFdSerialNo()))) {
				return rule;
			}
		}
		return null;
	}

	/**
	 * startTime和endTime为日期时间类型，返回分割好的日期列表 <br>
	 * 例子：startTime为2018-01-01 11:11，endTime为2018-01-03 00:00<br>
	 * 返回[2018-01-01 11:11, 2018-01-02 00:00, 2018-01-03 00:00]
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Date> getDateListByTime(Date startTime, Date endTime) {
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startTime);
		Calendar cal = Calendar.getInstance();
		for (cal.setTime(AttendUtil.getDate(startTime, 1)); cal.getTime().compareTo(endTime) < 0; cal.add(Calendar.DATE,
				1)) {
			dateList.add(cal.getTime());
		}
		dateList.add(endTime);
		return dateList;
	}

	/**
	 * startDate和endDate为日期类型，startNoon开始上下午标识，endNoon结束上下午标识
	 * 
	 * @param startDate
	 * @param endDate
	 * @param startNoon
	 * @param endNoon
	 * @return
	 */
	public static List<Date> getDateListByHalfDay(Date startDate, Date endDate, Integer startNoon, Integer endNoon) {
		if (startNoon == null || endNoon == null) {
			return getDateListByDay(startDate, endDate);
		}
		List<Date> dateList = new ArrayList<Date>();
		Date startTime = null;
		Date endTime = null;
		Calendar cal = Calendar.getInstance();
		if (startNoon == 1) {
			startTime = AttendUtil.getDate(startDate, 0);
		} else if (startNoon == 2) {
			startTime = AttendUtil.getDate(startDate, 0);
			cal.setTime(startTime);
			cal.set(Calendar.HOUR_OF_DAY, 12);
			startTime = cal.getTime();
		}
		if (endNoon == 1) {
			endTime = AttendUtil.getDate(endDate, 0);
			cal.setTime(endTime);
			cal.set(Calendar.HOUR_OF_DAY, 12);
			endTime = cal.getTime();
		} else if (endNoon == 2) {
			endTime = AttendUtil.getDate(endDate, 1);
		}
		if (startTime != null && endTime != null) {
			dateList = getDateListByTime(startTime, endTime);
		}
		return dateList;
	}

	/**
	 * startDate和endDate为日期类型
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDateListByDay(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();
		Date startTime = AttendUtil.getDate(startDate, 0);
		Date endTime = AttendUtil.getDate(endDate, 1);
		Calendar cal = Calendar.getInstance();
		for (cal.setTime(startTime); cal.getTime().compareTo(endTime) <= 0; cal.add(Calendar.DATE, 1)) {
			dateList.add(cal.getTime());
		}
		return dateList;
	}

	/**
	 * 判断是否出差/请假/外出
	 * 
	 * @param fdStatus
	 * @return
	 */
	public static boolean isAttendBuss(String fdStatus) {
		if ("4".equals(fdStatus) || "5".equals(fdStatus) || "6".equals(fdStatus)) {
			return true;
		}
		return false;
	}

	public static SysOrgElement findOrgElement(List<SysOrgElement> orgList, String orgId) {
		for (SysOrgElement ele : orgList) {
			if (ele.getFdId().equals(orgId)) {
				return ele;
			}
		}
		return null;
	}

	/**
	 * 获取钉钉配置信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map getDingAttendConfig() throws Exception {
		if (!dingAttendMap.isEmpty()) {
			return dingAttendMap;
		}
		Object service = SpringBeanUtil.getBean("thirdDingClockService");
		Class<?> clz = service.getClass();
		Map map = (Map) clz.getMethod("getAttendConfig", null).invoke(service, null);
		if (map == null || map.isEmpty()) {
			logger.warn("thirdDingClockService.getAttendConfig() is empty!");
			map = new HashMap<String, String>();
		} else {
			String corpid = (String) map.get("corpid");
			String agentid = (String) map.get("corpid");
			if (StringUtil.isNotNull(corpid) && StringUtil.isNotNull(agentid)) {
				dingAttendMap = map;
			} else {
				logger.warn(
						"thirdDingClockService.getAttendConfig() is empty!corpid:" + corpid + ";agentid:" + agentid);
			}
		}
		return map;
	}

	public static boolean getAttendTrip() {
		boolean isWork = false;// 按工作日计算
		try {
			SysAttendConfig attendConfig = getSysAttendConfigService().getSysAttendConfig();
			// 自然日计算
			if (attendConfig == null || attendConfig.getFdTrip() == null
					|| Boolean.TRUE.equals(attendConfig.getFdTrip())) {
				isWork = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isWork;
	}

	/**
	 * 返回值true:表示当月会按当前考勤组规则计算应出勤天数,不管是否更换过考勤组
	 * 
	 * @return
	 */
	public static boolean getAttendShouldDayCfg() {
		boolean result = false;
		try {
			SysAttendConfig attendConfig = getSysAttendConfigService().getSysAttendConfig();
			// 自然日计算
			if (attendConfig != null && attendConfig.getFdShouldDayCfg() != null
					&& Boolean.TRUE.equals(attendConfig.getFdShouldDayCfg())) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 某些版本sqlserver获取布尔类型为数字
	 * 
	 * @param bValue
	 * @return
	 */
	public static Boolean getBooleanField(Object bValue) {
		if (bValue == null) {
			return false;
		}
		if (bValue instanceof Number) {
			return ((Number) bValue).intValue() == 1;
		}
		if (bValue instanceof Boolean) {
			return ((Boolean) bValue).booleanValue();
		}
		return false;
	}

	/**
	 * 非emoji表情字符判断
	 * 
	 * @param codePoint
	 * @return
	 */
	private static boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 检测是否有emoji字符
	 * 
	 * @param source
	 *            需要判断的字符串
	 * @return 一旦含有就抛出
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isNotEmojiCharacter(codePoint)) {
				// 判断确认有表情字符
				return true;
			}
		}
		return false;
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtil.isNull(source)) {
			return source;
		}
		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	/**
	 * 获取取整后加班时间(分钟)
	 * 
	 */
	public static int getOverTime(SysAttendCategory cate, int overTime) {
		// 小班统计最小单位换算成分钟
		if (cate != null && cate.getFdMinUnitHour() != null) {
			int fdRoundingType = cate.getFdRoundingType();
			int minUnitMinute = (int) (cate.getFdMinUnitHour() * 60);
			int minute = overTime % minUnitMinute;
			int newOverTime = 0;
			if (fdRoundingType != 0) {
				if (fdRoundingType == 1) {
					// 向上取整
					newOverTime = overTime - minute + (minute > 0 ? +minUnitMinute : 0);
				} else if (fdRoundingType == 2) {
					// 向下取整
					newOverTime = overTime - minute;
				}
				overTime = newOverTime;
			}
		}
		return overTime;
	}

	/**
	 * 根据触发获取
	 * @param model 为空默认为流程
	 * @param fdId 主文档ID
	 * @return
	 */
	public static String getDictUrl(IBaseModel model, String fdId) {
		if(model == null) {
			return "/km/review/km_review_main/kmReviewMain.do?method=view&fdId=" + fdId;
		}
		SysDictModel dictModel = SysDataDict.getInstance().getModel(ModelUtil.getModelClassName(model));
		return MainDataUtil.formatModelUrl(fdId, dictModel.getUrl());
	}
	
	
	/**
	 * 获取打卡日期
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date getDateTime(Date date,Integer type) {
		Calendar cal=Calendar.getInstance();
		if(date!=null) {
			long dateTime=DateUtil.getDateTimeNumber(new Date(), date);
			cal.setTime(new Date(dateTime));
		}
		//如果是跨天则加一天
		if(Integer.valueOf(2).equals(type)) {
			cal.add(Calendar.DATE, 1);
		}
		return cal.getTime();
	}
	
	/**
	 * 判断日期是否为零点：（12点。前提是类型是按半天的类型）
	 * @param time
	 * @return
	 */
	public static boolean isHalfDay(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		if((cal.get(Calendar.HOUR_OF_DAY) == 12)){
			return true;
		}
		return false;
	}
	/**
	 * 判断日期是否为半天设置的12点：（0|23时，0|59分, 0|59秒）
	 * @param time
	 * @return
	 */
	public static boolean isZeroDay(Date time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		if((cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0)
				|| (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59)){
			return true;
		}
		return false;
	}
	/**
	 * 判断日期是否为零点：（0|23时，0|59分）
	 * @param date
	 * @return
	 */
	public static boolean isDay(Date date) {
		if(date == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if((cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0)
			|| (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59)){			
			return true;
		}
		return false;
	}

	/**
	 * 合并多个List
	 * @param lists
	 * @return
	 */
	public static List unionList(List... lists){
		List allList =new ArrayList();
		for (List list: lists) {
			if(CollectionUtils.isNotEmpty(list)){
				allList.addAll(list);
			}
		}
		return  allList;
	}

	/**
	 * 根据已知数据获取班制数量
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static int getWorkTimeCount(Map<String, List<List<net.sf.json.JSONObject>>>  userWorksMap) throws Exception {
		int maxCount = 1;
		if(userWorksMap !=null) {
			for (String key : userWorksMap.keySet()) {
				List<List<net.sf.json.JSONObject>> works = userWorksMap.get(key);
				if (works.size() > maxCount) {
					maxCount = works.size();
				}
			}
		}
		return maxCount;
	}

	/**
	 * 判断打卡时间是否在班次的配置的时间范围内
	 * @param signTime 打卡时间
	 * @param date 计算日期
	 * @param workConfig 打卡时间配置
	 * @return
	 * @throws Exception
	 */
	public static Date getTimeAreaDateOfDate( Date signTime,Date date, Map<String, Object> workConfig) throws Exception {
		if (workConfig == null || signTime ==null || date ==null) {
			return null;
		}
		//去除秒
		Date workDate =AttendUtil.removeSecond(signTime);
		//班次的最早打卡时间
		Date beginTime = (Date) workConfig.get("fdStartTime");
		//班次的最晚打卡时间
		Date overTime = (Date) workConfig.get("fdEndTime");
		if (beginTime == null || overTime == null) {
			return null;
		}
		//班次最晚打卡时间是今日还是明日
		Integer fdEndOverTimeType = (Integer) workConfig.get("endOverTimeType");
		//判断打卡时间是否在这个最早最晚的范围内
		beginTime = AttendUtil.joinYMDandHMS(date, beginTime);
		if (overTime != null) {
			if (Integer.valueOf(2).equals(fdEndOverTimeType)) {
				//如果是次日，则加一
				overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(date, 1), overTime);
			} else {
				overTime = AttendUtil.joinYMDandHMS(date, overTime);
			}
		}
		//时间在某个排班时间区域内，则以其为打卡日归类
		if (beginTime.getTime() <= workDate.getTime() && workDate.getTime() <= overTime.getTime()) {
			return AttendUtil.addDate(beginTime, 0);
		}
		return null;
	}
	/**
	 * 根据某个时间。获取一天最后的时间。
	 * @param date 是0点则会加上23:59:59 。如果传进来是1点。则不处理
	 * @return
	 */
	public static Date getDayLastTime(Date date){
		if(date !=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int days =calendar.get(Calendar.DATE);
			//时间加上23点59.59秒的时间还是当天，则默认加上。否则根据实际时间来
			//23点59分59秒的毫秒数
			Integer twelve = 24 * 60 * 60 * 1000 - 1000;
			calendar.add(Calendar.MILLISECOND,twelve);
			int newDay =calendar.get(Calendar.DATE);
			if(newDay ==days){
				return calendar.getTime();
			}
		}
		return date;
	}

	/**
	 *
	 * @description: 比较两个float数据是否相等
	 * @param a
	 * @param b
	 * @return: boolean
	 * @author: wangjf
	 * @time: 2022/3/24 5:22 下午
	 */
	public static boolean isEquals(Float a, Float b){
		if(a == null && b==null){
			return true;
		}
		if(a == null || b==null){
			return false;
		}
		//一般如果两个浮点数之差的绝对值小于或等于某一个可接受的误差(即精度，比如0.00000001即可)，就认为它们是相等的
		return Math.abs(a-b) < 0.000001;
	}

	/**
	 * 对班次的列表按时间排序
	 * @param signTimeList
	 */
	public static void sortSignTime(List<Map<String, Object>> signTimeList){
		Collections.sort(signTimeList,
				new Comparator<Map<String, Object>>() {
					@Override
					public int compare(Map<String, Object> o1,
									   Map<String, Object> o2) {
						Date signTime1 = (Date) o1.get("signTime");
						Integer overTimeType = (Integer) o1.get("overTimeType");
						//次日则加一天
						if(Integer.valueOf(2).equals(overTimeType)) {
							signTime1.setDate(signTime1.getDate()+1);
						}
						Date signTime2 = (Date) o2.get("signTime");
						Integer overTimeType2 = (Integer) o2.get("overTimeType");
						//次日则加一天
						if(Integer.valueOf(2).equals(overTimeType2)) {
							signTime2.setDate(signTime2.getDate()+1);
						}
						return signTime1.compareTo(signTime2);
					}
				});
	}
	public static void sortDateList(List<Date> dateList) {
		if (dateList == null || dateList.isEmpty()) {
			return;
		}
		Collections.sort(dateList,
				new Comparator<Date>() {
					@Override
					public int compare(Date o1, Date o2) {
						return o1.compareTo(o2);
					}
				});
	}
	/**
	 * 获取某个班次的签到时间
	 * @param signTimeConfiguration
	 * @return
	 */
	public static Date getSignTime(Map<String, Object> signTimeConfiguration){
		Long fdWorkDateLongTime = (Long) signTimeConfiguration.get("fdWorkDate");
		Date workDate = AttendUtil.getDate(new Date(fdWorkDateLongTime), 0);
		Date signTime = (Date) signTimeConfiguration.get("signTime");
		signTime = overTimeTypeProcess(signTimeConfiguration, AttendUtil.joinYMDandHMS(workDate, signTime));
		return signTime;
	}

	/**
	 *  获取某个班次的最早打卡
	 * @param workConfig 班次信息
	 * @param workDate 日期
	 * @return
	 */
	public static Date getWorkConfigFdBeginTime(Map<String, Object> workConfig,Date workDate){
		//班次的最早打卡时间
		Date beginTime = (Date) workConfig.get("fdStartTime");
		if (beginTime == null) {
			return null;
		}
		Integer fdBeginTimeType =workConfig.containsKey("fdBeginTimeType")? (Integer) workConfig.get("fdBeginTimeType"):1;
		//最早打卡。等于2则是前1日
		if (Integer.valueOf(2).equals(fdBeginTimeType)) {
			//如果是次日，则加一
			beginTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(workDate, -1), beginTime);
		} else {
			beginTime = AttendUtil.joinYMDandHMS(workDate, beginTime);
		}
		return beginTime;
	}

	/**
	 *  获取某个班次的最晚打卡
	 * @param workConfig 班次信息
	 * @param workDate 日期
	 * @return
	 */
	public static Date getWorkConfigFdOverTime(Map<String, Object> workConfig,Date workDate){

		//上班班次的最晚打卡时间
		Date overTime = (Date) workConfig.get("fdEndTime");
		if(overTime ==null) {
			return null;
		}
		//下班的配置，是今日还是明日
		Integer fdEndOverTimeType=(Integer)workConfig.get("endOverTimeType");

		if(Integer.valueOf(2).equals(fdEndOverTimeType)){
			//如果是次日，则加一
			overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(workDate, 1), overTime);
		}else{
			overTime = AttendUtil.joinYMDandHMS(workDate, overTime);
		}
		return overTime;
	}

	/**
	 * 	跨天排班打卡处理
	 * @param signTimeConfiguration
	 * 	排班时间配置
	 * @param signTime
	 * 	该排班配置的打卡时间
	 * @return
	 */
	public static Date overTimeTypeProcess(Map<String, Object> signTimeConfiguration, Date signTime) {
		if(isOverTimeType(signTimeConfiguration))
		{
			signTime = AttendUtil.addDate(signTime, 1);
		}
		return signTime;
	}
	/**
	 * 	判断是否为跨天排班打卡
	 * @param signTimeConfiguration
	 * @return
	 */
	public static boolean isOverTimeType(Map<String, Object> signTimeConfiguration) {
		Integer fdOverTimeType = signTimeConfiguration.get("overTimeType") == null ? 1
				: (Integer) signTimeConfiguration.get("overTimeType");
		return fdOverTimeType == 2;
	}

	/**
	 * 半小时取整
	 * @param daysMins
	 * @return
	 */
	public static int floor(int daysMins){
		return (int) (Math.floor(daysMins / 30.0) * 30);
	}

	/**
	 * 返回弹性延迟下班分钟数
	 * @param category 考勤组
	 * @param onTime 上班打卡时间
	 * @param signMap 上班标准打卡信息
	 */
	public static long getFlexTimes(SysAttendCategory category, Date onTime,Map<String, Object> signMap) {
		long tempTime = 0L;
		if (category == null || category.getFdIsFlex() == false || category.getFdFlexTime() == 0) {
			return tempTime;
		}
		if (onTime == null || !signMap.containsKey("signTime")) {
			return tempTime;
		}
		//上班标准打卡时间
		Date fdStartTime = joinYMDandHMS(onTime, (Date) signMap.get("signTime"));
		//上班弹性时间
		long flexTime = category.getFdFlexTime() * 60000;
		//计算弹性时间
		if (onTime.getTime() > fdStartTime.getTime()) {
			if (onTime.getTime() - fdStartTime.getTime() <= flexTime) {
				tempTime = onTime.getTime() - fdStartTime.getTime();
			} else {
				tempTime = flexTime;
			}
		}
//		} else {
//			if (fdStartTime.getTime() - onTime.getTime() <= flexTime) {
//				tempTime = onTime.getTime() - fdStartTime.getTime();
//			} else {
//				tempTime = 0L - flexTime;
//			}
//		}
		return tempTime;
	}

	/**
	 * 获取实际加班时间
	 * @param overTime
	 * @param attendBaseTime
	 * @return
	 */
	public static Date[] getOverDate(AttendComparableTime overTime, AttendComparableTime attendBaseTime,Long tempTime) {
		Date[] dates = new Date[2];
		//标准下班时间 + 弹性延迟时间
		Date fdEndTime = new Date(attendBaseTime.getEndDate().getTime() + tempTime);
		dates[0] = overTime.getStartDate().getTime() < fdEndTime.getTime() ? fdEndTime : overTime.getStartDate();
		dates[1] = overTime.getEndDate().getTime() > fdEndTime.getTime() ? overTime.getEndDate():fdEndTime;
		return dates;
	}
}
