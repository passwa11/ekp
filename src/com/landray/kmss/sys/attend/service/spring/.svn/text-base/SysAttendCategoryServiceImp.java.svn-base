package com.landray.kmss.sys.attend.service.spring;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attend.cache.SysAttendUserCacheUtil;
import com.landray.kmss.sys.attend.cache.SysAttendUserCategoryListDto;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryDao;
import com.landray.kmss.sys.attend.dao.ISysAttendMainDao;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExctime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryLocation;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTime;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendNotifyRemindLog;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryExctimeService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryTimeService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendDeviceService;
import com.landray.kmss.sys.attend.service.ISysAttendHisCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendNotifyRemindLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPersonUtil;
import com.landray.kmss.sys.attend.util.AttendPlugin;
import com.landray.kmss.sys.attend.util.AttendThreadPoolManager;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.quartz.model.SysQuartzJob;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
import com.landray.kmss.sys.time.service.ISysTimeHolidayDetailService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 签到事项业务接口实现
 * 
 * @author
 * @version 1.0 2017-05-24
 */
public class SysAttendCategoryServiceImp extends BaseServiceImp
		implements ISysAttendCategoryService, IXMLDataBean,
		ApplicationContextAware {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendCategoryServiceImp.class);

	private ISysAttendCategoryExctimeService sysAttendCategoryExctimeService;
	private ISysAttendCategoryTimeService sysAttendCategoryTimeService;
	private ISysAttendConfigService sysAttendConfigService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysAttendMainDao sysAttendMainDao;
	private ISysQuartzCoreService sysQuartzCoreService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
	private ISysQuartzJobService sysQuartzJobService;
	private ISysTimeHolidayService sysTimeHolidayService;
	private ISysTimeCountService sysTimeCountService;
	private ISysAttendDeviceService sysAttendDeviceService;
	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysAttendNotifyRemindLogService sysAttendNotifyRemindLogService;
	private ApplicationContext applicationContext;
	private ThreadLocal<Map<String, List>> signTimesThreadLocal = new ThreadLocal<Map<String, List>>();
	private ThreadLocal<Boolean> isEnableKKConfigThreadLocal = new ThreadLocal<Boolean>();

	private ISysAttendHisCategoryService sysAttendHisCategoryService;
	private ISysAttendHisCategoryService getSysAttendHisCategoryService(){
		if(sysAttendHisCategoryService ==null){
			sysAttendHisCategoryService = (ISysAttendHisCategoryService) SpringBeanUtil.getBean("sysAttendHisCategoryService");
		}
		return sysAttendHisCategoryService;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysAttendCategory category = (SysAttendCategory) modelObj;
		if(CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			if (category.getFdEffectTime() ==null || (category.getFdEffectTime()).before(AttendUtil.getDate(new Date(), 0))) {
				throw new RuntimeException(ResourceUtil.getString("sysAttendHisCategory.tip2"));
			}
		}
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			// 排班
			category.setFdIsFlex(false);
		}
		if(Integer.valueOf(4).equals(category.getFdShiftType())){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
			category.setFdStartTime(cal.getTime());
			cal.set(Calendar.HOUR_OF_DAY,23);
			cal.set(Calendar.MINUTE,59);
			cal.set(Calendar.SECOND,59);
			category.setFdEndTime(cal.getTime());
		}


		String fdId = super.add(modelObj);
		//保存考勤组的历史版本信息
		addHisCategory(category);
		// 对于第三方系统发起的事项,初始化签到记录(便于统计)
		initAttendMain(category);
		sysQuartzCoreService.delete(category, null);
		saveRemindQuart(category);
		saveMissSignQuart(category,null);
		saveStatusQuart(category);
		saveEffectTimeQuart(category);
		// 设置缓存
		setCategoryCache(category);

		return fdId;
	}

	@SuppressWarnings("unchecked")
	private void initAttendMain(SysAttendCategory category) throws Exception {
		if (StringUtil.isNotNull(category.getFdAppId())
				&& AttendConstant.FDTYPE_CUST == category.getFdType()) {
			List<SysOrgElement> targets = category.getFdTargets();
			List<SysOrgElement> excTargets = category.getFdExcTargets();
			targets = sysOrgCoreService.expandToPerson(targets);
			if (!excTargets.isEmpty()) {
				excTargets = sysOrgCoreService.expandToPerson(excTargets);
				targets.removeAll(excTargets);
			}
			for (SysOrgElement elem : targets) {
				SysAttendMain sysAttendMain = new SysAttendMain();
				sysAttendMain.setFdCategory(category);
				sysAttendMain.setDocCreateTime(new Date());
				sysAttendMain.setDocCreator((SysOrgPerson) elem);
				sysAttendMain.setFdStatus(0);
				sysAttendMainDao.add(sysAttendMain);
			}
		}
	}

	/**
	 * 更新签到组当日的签到记录
	 * @param category
	 * @throws Exception
	 */
	private void updateAttendMain(SysAttendCategory category) throws Exception {
		if (StringUtil.isNotNull(category.getFdAppId())
				&& AttendConstant.FDTYPE_CUST == category.getFdType()) {
			List<SysOrgElement> targets = AttendPersonUtil.expandToPerson(category.getFdTargets());
			List<SysOrgElement> fdExcTargets = category.getFdExcTargets();
			if (!fdExcTargets.isEmpty()) {
				List<SysOrgElement> excTargets = AttendPersonUtil.expandToPerson(fdExcTargets);
				targets.removeAll(excTargets);
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysAttendMain.fdCategory.fdId=:fdCategoryId");
			hqlInfo.setParameter("fdCategoryId", category.getFdId());
			List<SysAttendMain> mainList = sysAttendMainDao.findList(hqlInfo);
			List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
			for (SysAttendMain main : mainList) {
				SysOrgPerson ele = main.getDocCreator();
				if (main.getFdStatus() == 0) {// 未签到记录
					if (targets.contains(ele)) {
						tmpList.add(ele);
					} else {
						sysAttendMainDao.delete(main);
					}
				} else {// 正常或迟到的记录
					if (main.getFdOutPerson() != null) {// 企业外部人员
						if (Boolean.TRUE.equals(category.getFdUnlimitOuter())) {
							main.setDocStatus(0);
						} else {
							main.setDocStatus(1);
						}
					} else if (!targets.contains(ele)) {// 签到范围外人员
						if (Boolean.TRUE.equals(category.getFdUnlimitTarget())) {
							main.setDocStatus(0);
							main.setFdOutTarget(true);
						} else {
							main.setDocStatus(1);
						}
					} else {// 签到范围内人员
						main.setDocStatus(0);
						main.setFdOutTarget(false);
						tmpList.add(ele);
					}
					sysAttendMainDao.update(main);
				}
			}
			// 新增对象初始化
			targets.removeAll(tmpList);
			for (SysOrgElement elem : targets) {
				SysAttendMain sysAttendMain = new SysAttendMain();
				sysAttendMain.setFdCategory(category);
				sysAttendMain.setDocCreateTime(new Date());
				sysAttendMain.setDocCreator((SysOrgPerson) elem);
				sysAttendMain.setFdStatus(0);
				sysAttendMainDao.add(sysAttendMain);
			}
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysAttendCategory category = (SysAttendCategory) modelObj;
		List<SysAttendCategoryLocation>  locations =category.getFdLocations();
		if(Boolean.TRUE.equals(category.getFdCanMap())){
			//如果开启的是地图验证，则判断地图配置
			if(CollectionUtils.isEmpty(locations)){
				throw new RuntimeException(ResourceUtil.getString("sys-attend:sysAttendMainLog.fdAddress.error"));
			}
			//如果地址存在，地址名称为空。则认为数据丢失
			for (SysAttendCategoryLocation location:locations) {
				if(StringUtil.isNull(location.getFdLocation()) || StringUtil.isNull(location.getFdLatLng())){
					throw new RuntimeException(ResourceUtil.getString("sys-attend:sysAttendMainLog.fdAddress.error"));
				}
			}
		}
		category.setDocAlterTime(new Date());
		category.setDocAlteror(UserUtil.getUser());
		if (category.getFdType() == AttendConstant.FDTYPE_ATTEND
				&& category.getFdWork() != null && category.getFdWork() == 2) {
			category.setFdLateToFullAbsTime(null);
			category.setFdLeftToFullAbsTime(null);
		}
		if (category.getFdType() == AttendConstant.FDTYPE_ATTEND 
				&& category.getFdEffectTime()!=null && AttendUtil.getDate(new Date(), 0).before(category.getFdEffectTime())) {
			category.setFdStatus(0);
		}
		super.update(category);
		// 保存提醒打卡，缺卡补卡定时任务
		sysQuartzCoreService.delete(category, null);
		saveRemindQuart(category);
		saveMissSignQuart(category,null);
		saveStatusQuart(category);
		saveEffectTimeQuart(category); 
		// 更新第三方发起事项的签到记录
		updateAttendMain(category);
		//一定要在更新签到记录以后在新增
		addHisCategory(category);
		setCategoryCache(category);
	}
	/**
	 * 保存考勤组信息到历史表
	 * @param category
	 * @throws Exception
	 */
	@Override
	public SysAttendHisCategory addHisCategory(SysAttendCategory category) throws Exception {
		//暂时只记录考勤组
		String contentJson =null;
		if(CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			String tip = getSysAttendHisCategoryService().checkCategoryTarget(category);
			if(StringUtil.isNotNull(tip)){
				throw new RuntimeException(tip);
			}
			//进行中状态
			if (CategoryUtil.CATEGORY_FD_STATUS_TRUE.equals(category.getFdStatus())) {
				PropertyPreFilters filters = new PropertyPreFilters();
				PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
				excludefilter.addExcludes(CategoryUtil.EXCLUDE_PROPERTIES);
				contentJson = com.alibaba.fastjson.JSONObject.toJSONString(category, excludefilter,
						SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
						SerializerFeature.DisableCircularReferenceDetect
				);
			}
			Date now=new Date();
			SysAttendHisCategory hisCategoryTwo = getSysAttendHisCategoryService().getLastVersionFdId(category.getFdId(),now);
			//清空假日的缓存
			if(hisCategoryTwo !=null) {
				KmssCache cache = new KmssCache(SysTimeHoliday.class);
				String timeHolidayMapKey = String.format(CategoryUtil.HOLIDAY_CACHE_MAP_KEY, hisCategoryTwo.getFdId());
				cache.put(timeHolidayMapKey, null);
				String timeHolidayDayMapKey = String.format(CategoryUtil.HOLIDAY_DAY_CACHE_MAP_KEY, hisCategoryTwo.getFdId());
				cache.put(timeHolidayDayMapKey, null);
				//清理该考勤组所属变更的组织人员
				Set<SysOrgElement> elements = (Set<SysOrgElement>) getSysAttendHisCategoryService().getChangeOrgByHisId(hisCategoryTwo.getFdId());
				if(CollectionUtils.isNotEmpty(elements)) {
					SysAttendUserCacheUtil.clearUserCache(elements.stream().map(e -> e.getFdId()).collect(Collectors.toList()), now);
				}
			}
			SysAttendHisCategory hisCategory = getSysAttendHisCategoryService().addHisCategory(category, contentJson);
			return hisCategory;
		}
		return null;
	}
	@Override
	public void updateCategoryOver(String id,String fdStatusFlag) throws Exception {
		SysAttendCategory model = (SysAttendCategory) this.findByPrimaryKey(id);
		// 添加日志信息
		if (UserOperHelper.allowLogOper("updateStatus", getModelName())) {
			UserOperContentHelper.putUpdate(model)
					.putSimple("fdStatus", model.getFdStatus(),
							Integer.valueOf(AttendConstant.FINISHED));
		}
		// 清除缓存
		setCategoryCache(model);
		sysQuartzCoreService.delete(model, null);
		Date deleteQuartzDate =null;
		if(CategoryUtil.ENABLE_FLAG.equals(fdStatusFlag)){
			//今日失效
			deleteQuartzDate =DateUtil.getDate(1);
		}else{
			//明日失效
			deleteQuartzDate =DateUtil.getDate(2);
		}
		//缺卡定时任务的执行时间
		saveMissSignQuart(model,deleteQuartzDate);

		//执行完定时任务操作以后。设置状态
		model.setFdStatus(Integer.valueOf(AttendConstant.FINISHED));
		super.update(model);
		if(CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(model.getFdType())) {
			//判断是今日还是明日生效
			model.setFdStatusFlag(fdStatusFlag);
			//结束状态需要停止该考勤组的考勤对象时间
			getSysAttendHisCategoryService().addHisCategory(model, null);
		}
	}

	@Override
	public void updateStatus(String categoryId, int fdStatus) throws Exception {
		SysAttendCategory model = (SysAttendCategory) this
				.findByPrimaryKey(categoryId, null, true);
		if (model != null) {
			model.setFdStatus(fdStatus);
			if (fdStatus > 1) {
				super.update(model);
				sysQuartzCoreService.delete(model, null);
			} else {
				this.update(model);
			}
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysAttendCategory category = (SysAttendCategory) modelObj;
		UserOperHelper.logDelete(category);
		category.setFdStatus(Integer.valueOf(AttendConstant.DEL));
		super.update(modelObj);
		// 清除缓存
		setCategoryCache(category);
		sysQuartzCoreService.delete(category, null);
	}

	/**
	 * 获取用户的签到事项、默认当前用户当前所在的考勤组
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public com.alibaba.fastjson.JSONArray getAttendCategorys(HttpServletRequest request)
			throws Exception {
		// 获取签到组
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		sb.append("sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=2 and (sysAttendCategory.fdAppId='' or sysAttendCategory.fdAppId is null)");
		if (ISysAuthConstant.IS_AREA_ENABLED) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
		}
		hqlInfo.setWhereBlock(sb.toString());
		// hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
		hqlInfo.setOrderBy("sysAttendCategory.docCreateTime desc");
		List<SysAttendCategory> categories = this.findList(hqlInfo);
		List<SysAttendCategory> custCates = new ArrayList<SysAttendCategory>();
		for (SysAttendCategory cate : categories) {
			if (this.isNeededSigningByTarget(cate)) {
				custCates.add(cate);
			}
		}
		com.alibaba.fastjson.JSONArray list = this.filterAttendCategory(custCates, null, true, null);

		String categoryId = getCategory(UserUtil.getUser(),new Date(),true);
		//获取考勤组 2021-09-30冲刺任务修改此方法
		SysAttendCategory category =CategoryUtil.getCategoryById(categoryId);
		if (category !=null) {
			// 此刻是否跨天打卡
			boolean isAcrossDay = this.isAcrossDay(new Date(),
					UserUtil.getUser(), category);
			Date signDate = AttendUtil.getDate(new Date(), 0);
			if (isAcrossDay) {
				signDate = AttendUtil.getDate(new Date(), -1);
				categoryId = getCategory(UserUtil.getUser(),new Date(),true);
				if(StringUtil.isNull(categoryId)){
					return list;
				}
			}
			com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
			json.put("isAcrossDay", isAcrossDay);
			json.put("fdId", category.getFdId());
			json.put("fdName", category.getFdName());
			// 打卡日期
			json.put("signDate", signDate.getTime());
			// 班次时间
			String workTime = "";
			List<SysAttendCategoryWorktime> workTimes = getWorktimes(
					category, signDate, UserUtil.getUser());
			if (workTimes != null && !workTimes.isEmpty()) {
				for (SysAttendCategoryWorktime record : workTimes) {
					String startTime = DateUtil.convertDateToString(
							record.getFdStartTime(), "HH:mm");
					String endTime = DateUtil.convertDateToString(
							record.getFdEndTime(), "HH:mm");
					String _workTime = startTime + "-" + endTime;
					if(Integer.valueOf(2).equals(record.getFdOverTimeType())) {
						_workTime+="("+ResourceUtil.getString("sysAttendCategory.fdEndDay.secondDay", "sys-attend")+")";
					}
					if (StringUtil.isNull(workTime)) {
						workTime = _workTime;
					} else {
						workTime = workTime + ";" + _workTime;
					}
				}
				if (StringUtil.isNotNull(workTime)) {
					workTime = "(" + workTime + ")";
				}
			}
			json.put("workTimes", workTime);
			// 是否休息日(包括节假日)
			json.put("isRestDay",
					StringUtil.isNull(getAttendCategory(UserUtil.getKMSSUser().getPerson(),
							signDate)) ? "true" : "false");
			json.put("fdType", 1);
			// 判断考勤打卡设备数是否限制
			String fdDeviceIds = getUserDeviceIds(request);
			json.put("fdDeviceIds", fdDeviceIds);
			list.add(0, json);
		}

		return list;
	}

	// 获取用户设备号
	private String getUserDeviceIds(HttpServletRequest request)
			throws Exception {
		SysAttendConfig config = this.sysAttendConfigService
				.getSysAttendConfig();
		String fdDeviceIds = "";
		logger.warn("打开前——初始化，准备获取用户已打卡的设备码");
		if (config != null) {
			if ((Boolean.TRUE.equals(config.getFdClientLimit())
					&& Boolean.TRUE.equals(config.getFdDeviceLimit()))
					|| Boolean.TRUE.equals(config.getFdSameDeviceLimit())) {
				// 支持kk,钉钉
				logger.warn("打开前——允许获取用户已打卡设备码");
				String fdDeviceType = AttendUtil.getDeviceClientType(request);
				fdDeviceIds = this.sysAttendDeviceService
						.getUserDeviceIds(fdDeviceType);
				logger.warn("打开前——用户已打卡设备码信息；类型：" + fdDeviceType + ";设备码："
						+ fdDeviceIds);

			} else {
				logger.warn("打开前——未开启相应设置，不允许获取用户已打卡设备码");
			}
		}
		return fdDeviceIds;
	}

	/**
	 * 判断打卡时间是否是跨天打卡时间点
	 * @param date 某个时间点
	 * @param person 人员
	 * @param category 考勤组
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isAcrossDay(Date date, SysOrgElement person,
			SysAttendCategory category) throws Exception {

		if (date == null || person == null || category == null) {
			return false;
		}
		Date lastDate = AttendUtil.getDate(date, -1);
		//昨天是休息日
		boolean isLastRest = StringUtil.isNull(this.getAttendCategory(person, lastDate));
		if(!isLastRest){
			//如果用户昨天在考勤组内，则判断其昨天考勤组的考勤内容
			String categoryId = this.getCategory(person,lastDate);
			if(StringUtil.isNull(categoryId)){
				return false;
			}
			//昨天是休息日，则拿昨天所在考勤组重新计算是否在最晚打卡 最早打卡时间内
			SysAttendCategory upCategory =CategoryUtil.getCategoryById(categoryId);
			if(upCategory !=null){
				return checkSignBetweenTime(person,date,upCategory,lastDate);
			}
		}
		//拿当前所在考勤组计算是否跨天（在打卡时间范围内）
		return false;
	}

	/**
	 * 判断当前时间 是否在考勤的允许打卡时间范围内
	 * @param person 人员
	 * @param date 当前时间
	 * @param category 考勤组
	 * @param workDay 计算日
	 * @return
	 * @throws Exception
	 */
	private Boolean checkSignBetweenTime(SysOrgElement person,Date date,SysAttendCategory category,Date workDay) throws Exception {
		//考勤组排班时间列表
		List<Map<String, Object>> signTimeList = getAttendSignTimes(category, workDay, person);
		if (signTimeList.isEmpty()) {
			return false;
		}
		Date isBeginDate = getTimeAreaDateOfDate(date,workDay,signTimeList);
		if(isBeginDate !=null){
			return true;
		}
		return false;
	}
	/**
	 * 判断当前时间 是否在考勤的允许打卡时间范围内
	 * @param date
	 * @param category
	 * @return
	 */
	private Boolean checkBetweenTime(SysOrgElement person,Date date,SysAttendCategory category,Date workDay) throws Exception {
		Date nowDate = AttendUtil.getDate(date, 0);
		Integer fdShiftType = category.getFdShiftType();
		Integer fdSameWTime = category.getFdSameWorkTime();
		if (fdShiftType == null || fdShiftType == 0 || fdShiftType == 2) {
			// 固定班制或自定义
			if (fdSameWTime == null || fdSameWTime == 0) {
				// 一周相同上下班
				if (Integer.valueOf(2).equals(category.getFdEndDay())) {
					//Date fdStartTime = category.getFdStartTime();
					Date fdEndTime = category.getFdEndTime();
					//fdStartTime = AttendUtil.joinYMDandHMS(lastDate,fdStartTime);
					fdEndTime = AttendUtil.joinYMDandHMS(nowDate, fdEndTime);
					if (date.getTime() > fdEndTime.getTime()) {
						return false;
					}
					return true;
				}
			} else if (fdSameWTime == 1) {
				// 一周不同上下班
				List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
				if (tSheets != null && !tSheets.isEmpty()) {
					SysAttendCategoryTimesheet lastTimeSheet = getTimeSheet(category, workDay);
					if (lastTimeSheet != null) {
						Integer fdEndDay = lastTimeSheet.getFdEndDay();
						if (Integer.valueOf(2).equals(fdEndDay)) {
							//Date fdStartTime = lastTimeSheet.getFdStartTime1();
							Date fdEndTime = lastTimeSheet.getFdEndTime2();
							//fdStartTime = AttendUtil.joinYMDandHMS(lastDate,fdStartTime);
							fdEndTime = AttendUtil.joinYMDandHMS(nowDate,fdEndTime);
							if (date.getTime() > fdEndTime.getTime()) {
								return false;
							}
							return true;
						}
					}
				}
			}
		} else if (Integer.valueOf(1).equals(fdShiftType)) {
			// 排班 跨天
			//查找当前人员排班情况
			List<SysAttendCategoryWorktime> worktimeList =getWorktimes(category,workDay,person);
			//传入的时间 是否在排班的班次最早最晚打卡时间范围内。
			boolean isReturn = false;
			boolean isOld = false;
			for (SysAttendCategoryWorktime worktime:worktimeList) {
				//历史没有配置排班的结束时间时
				if(worktime.getFdBeginTime() ==null || worktime.getFdOverTime() ==null){
					isOld =true;
					break;
				}
				//从昨天的开始时间
				Date beginTime=worktime.getFdBeginTime();
				beginTime =AttendUtil.joinYMDandHMS(workDay, beginTime);

				Date overTime = worktime.getFdOverTime();
				 //跨天
				overTime =AttendUtil.joinYMDandHMS(Integer.valueOf("2").equals(worktime.getFdEndOverTimeType())?nowDate:workDay, overTime);
				//当前时间
				if (date.getTime() <= overTime.getTime() && date.getTime() >= beginTime.getTime()) {
					isReturn =true;
					break;
				}
			}
			if(isOld) {
				if (!Integer.valueOf(2).equals(category.getFdEndDay())) {
					return false;
				}
				//Date fdStartTime = category.getFdStartTime();
				Date fdEndTime = category.getFdEndTime();
				//fdStartTime = AttendUtil.joinYMDandHMS(lastDate,fdStartTime);
				fdEndTime = AttendUtil.joinYMDandHMS(nowDate, fdEndTime);
				if (date.getTime() > fdEndTime.getTime()) {
					return false;
				}
			}else{
				return isReturn;
			}
			return true;
		}
		return false;
	}


	@Override
	public List<SysAttendCategoryWorktime> getWorktimes(
			SysAttendCategory category, Date date, SysOrgElement element)
			throws Exception {
		List<SysAttendCategoryWorktime> worktimes = new ArrayList<SysAttendCategoryWorktime>();
		Integer fdShiftType = category.getFdShiftType();
		Integer fdSameWTime = category.getFdSameWorkTime();

		if (Integer.valueOf(0).equals(fdShiftType)
				&& Integer.valueOf(1).equals(fdSameWTime)) {
			List<SysAttendCategoryTimesheet> tSheets = category
					.getFdTimeSheets();
			for (SysAttendCategoryTimesheet tSheet : tSheets) {
				if (StringUtil.isNotNull(tSheet.getFdWeek())
						&& tSheet.getFdWeek()
								.indexOf(AttendUtil.getWeek(date)
										+ "") > -1) {
					worktimes = tSheet.getAvailWorkTime();
					break;
				}
			}
		} else if (Integer.valueOf(1).equals(fdShiftType) && element!=null) {
			// 排班制
			worktimes = getWorkTimeOfTimeArea(element, date);
		} else {
			worktimes = category.getAvailWorkTime();
		}
		if(!worktimes.isEmpty()){
			Collections.sort(worktimes,
					new Comparator<SysAttendCategoryWorktime>() {
						@Override
						public int compare(SysAttendCategoryWorktime w1,
								SysAttendCategoryWorktime w2) {
							if (w1.getFdStartTime() != null
									&& w2.getFdStartTime() != null) {
								return w1.getFdStartTime()
										.compareTo(w2.getFdStartTime());
							}
							return 0;
						}
					});
		}
		return worktimes;
	}

	/**
	 * 是否需要打卡
	 * @param cate 考勤组
	 * @param date 日期
	 * @param fdStartTime 开始时间
	 * @param fdEndTime 结束时间
	 * @param orgEle 人员
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isNeedSign(SysAttendCategory cate,Date date,long fdStartTime,long fdEndTime,SysOrgElement orgEle) throws Exception {
		boolean isNeeded = false;
		Integer fdShiftType = cate.getFdShiftType();
		Integer fdPeriodType = cate.getFdPeriodType();
		if (Integer.valueOf(0).equals(fdShiftType)|| (fdShiftType == null&& AttendConstant.FDPERIODTYPE_WEEK.equals(
				String.valueOf(fdPeriodType)))) {// 固定班制
			isNeeded = this.isNeededSigningByPeriod(cate, date,
					fdStartTime, fdEndTime);
		} else if (Integer.valueOf(1).equals(fdShiftType)) {
			// 排班
			List<SysAttendCategoryWorktime> workTimeList = getWorkTimeOfTimeArea(
					orgEle, date);
			if (!workTimeList.isEmpty()) {
				isNeeded = true;
			}
		} else if (Integer.valueOf(2).equals(fdShiftType)	|| (fdShiftType == null
				&& AttendConstant.FDPERIODTYPE_CUST.equals(
				String.valueOf(fdPeriodType)))) { // 自定义
			isNeeded = this.isNeededSigningByCust(cate, date,
					fdStartTime,
					fdEndTime);
		}else if (Integer.valueOf(3).equals(fdShiftType)) { // 综合工时
			isNeeded = this.isNeededSigningByPeriod(cate, date,
					fdStartTime, fdEndTime);

		}else if (Integer.valueOf(4).equals(fdShiftType)) {
			// 不定时工作制
			isNeeded = this.isNeededSigningByPeriod(cate, date,
					fdStartTime, fdEndTime);

		}
		return isNeeded;
	}
	@Override
	public com.alibaba.fastjson.JSONArray filterAttendCategory(List<SysAttendCategory> list,
			Date date, boolean filterTarget, SysOrgElement orgEle)
			throws Exception {
		if (list.isEmpty()) {
			return new com.alibaba.fastjson.JSONArray();
		}
		long fdStartTime = DateUtil.getDate(0).getTime();
		long fdEndTime = DateUtil.getDate(1).getTime();
		if (date != null) {
			fdStartTime = AttendUtil.getDate(date, 0).getTime();
			fdEndTime = AttendUtil.getDate(date, 1).getTime();
		}
		List<SysAttendCategory> newList = new ArrayList<SysAttendCategory>();
		for (SysAttendCategory cate : list) {
			boolean isNeeded = false;
			if (AttendConstant.FDTYPE_ATTEND == cate.getFdType()) {
				// 考勤
				isNeeded =isNeedSign(cate,date,fdStartTime,fdEndTime,orgEle);
			} else if (AttendConstant.FDTYPE_CUST == cate.getFdType()) {
				// 签到
				Integer fdPeriodType = cate.getFdPeriodType();
				if (AttendConstant.FDPERIODTYPE_WEEK
						.equals(String.valueOf(fdPeriodType))) {
					// 根据时间过滤是否应打卡
					isNeeded = this.isNeededSigningByPeriod(cate, date,
							fdStartTime,
							fdEndTime);
				}
				// 自定义打卡时间
				if (AttendConstant.FDPERIODTYPE_CUST
						.equals(String.valueOf(fdPeriodType))) {
					isNeeded = this.isNeededSigningByCust(cate, date,
							fdStartTime,
							fdEndTime);
				}
			}

			if (!isNeeded) {
				continue;
			}
			// 排除人员过滤
			if (filterTarget) {
				isNeeded = this.isNeededSigningByExcTarget(cate);
			}
			if (isNeeded) {
				newList.add(cate);
			}
		}
		com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
		if(CollectionUtils.isNotEmpty(newList)) {
			Collections.sort(newList, new Comparator<SysAttendCategory>() {
				@Override
				public int compare(SysAttendCategory o1, SysAttendCategory o2) {
					return o1.getFdType().compareTo(o2.getFdType());
				}
			});
			for (SysAttendCategory cate : newList) {
				com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
				json.put("fdId", cate.getFdId());
				json.put("fdName", cate.getFdName());
				json.put("fdType", cate.getFdType());
				String workTime = "";
				List<SysAttendCategoryWorktime> workTimes = getWorktimes(cate,
						date, orgEle);
				if (workTimes != null
						&& AttendConstant.FDTYPE_ATTEND == cate.getFdType()) {
					for (SysAttendCategoryWorktime record : workTimes) {
						String startTime = DateUtil.convertDateToString(
								record.getFdStartTime(), "HH:mm");
						String endTime = DateUtil.convertDateToString(
								record.getFdEndTime(), "HH:mm");
						String _workTime = startTime + "-" + endTime;
						if (StringUtil.isNull(workTime)) {
							workTime = _workTime;
						} else {
							workTime = workTime + ";" + _workTime;
						}
					}
					if (StringUtil.isNotNull(workTime)) {
						workTime = "(" + workTime + ")";
					}
				}
				json.put("workTimes", workTime);
				array.add(json);
			}
		}
		return array;
	}

	/**
	 * 根据日期判断是否需要打卡
	 * @param cate 考勤组
	 * @param date 日期
	 * @param fdStartTime 开始时间
	 * @param fdEndTime 结束时间
	 * @return 是否需要打卡
	 * @throws Exception
	 */
	private boolean isNeededSigningByPeriod(SysAttendCategory cate, Date date,
			long fdStartTime, long fdEndTime) throws Exception {

		boolean isNeeded = false;
// 一周不同上下班
		if (Integer.valueOf(1).equals(cate.getFdType())
				&& (Integer.valueOf(0).equals(cate.getFdShiftType()))
				&& Integer.valueOf(1).equals(cate.getFdSameWorkTime())) {

			List<SysAttendCategoryTimesheet> tSheets = cate.getFdTimeSheets();
			if (tSheets != null && !tSheets.isEmpty()) {
				for (SysAttendCategoryTimesheet tSheet : tSheets) {
					String fdWeek = tSheet.getFdWeek();
					if (StringUtil.isNotNull(fdWeek)
							&& fdWeek.indexOf(
									"" + AttendUtil.getWeek(date)) > -1) {
						isNeeded = true;
					}
				}
			}

		}else{
			String fdWeek = cate.getFdWeek();
			if (StringUtil.isNotNull(fdWeek)
					&& fdWeek.indexOf("" + AttendUtil.getWeek(date)) > -1) {
				isNeeded = true;
			}
		}

		// 排除日期
		List<SysAttendCategoryExctime> excTimes = cate.getFdExcTimes();
		if (!excTimes.isEmpty()) {
			boolean isEctTimeResult = false;
			for (SysAttendCategoryExctime time : excTimes) {
				if (time.getFdExcTime() == null) {
					continue;
				}
				long excTime = time.getFdExcTime().getTime();
				if (excTime >= fdStartTime && excTime < fdEndTime) {
					isEctTimeResult = true;
					break;
				}
			}
			if (isEctTimeResult) {
				return false;
			}
		}

		if (!isNeeded) {// 休息日
			boolean isTimeResult = false;
			// 追加日期
			List<SysAttendCategoryTime> fdTimes = cate.getFdTimes();
			for (SysAttendCategoryTime time : fdTimes) {
				if (time.getFdTime() == null) {
					continue;
				}
				long fdTime = time.getFdTime().getTime();
				if (fdTime >= fdStartTime && fdTime < fdEndTime) {
					isTimeResult = true;
					break;
				}
			}
			// 节假日补班
			if (cate.getFdType() == 1
					&& isHolidayPatchDay(cate.getFdId(), date)) {
				isTimeResult = true;
			}
			return isTimeResult;
		}
		// 工作日
		if (Integer.valueOf(1).equals(cate.getFdType()) && isHoliday(cate.getFdId(), date)) {// 节假日
			return false;
		}

		return isNeeded;
	}

	/**
	 * 是否法定节假日补班
	 * @param categoryId
	 * @param date
	 * @return
	 */
	@Override
	public boolean isHolidayPatchDay(String categoryId, Date date)
			throws Exception {
		Map map = this.getHolidayPatchMap(categoryId);
		if(map !=null && !map.isEmpty()) {
			String dateKey = categoryId + "_"
					+ AttendUtil.getDate(date, 0).getTime();
			if (map.containsKey(dateKey)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isHoliday(String categoryId, Date date) throws Exception {
		Map map = this.getHolidayDayMap(categoryId);
		if(map !=null && !map.isEmpty()) {
			String key = categoryId + "_" + AttendUtil.getDate(date, 0).getTime();
			if (map.containsKey(key)) {
				return true;
			}
		}
		return false;
	}

	private boolean isNeededSigningByCust(SysAttendCategory cate, Date date,
			long fdStartTime, long fdEndTime) throws Exception {
		boolean isNeeded = false;
		// 签到日期
		List<SysAttendCategoryTime> fdTimes = cate.getFdTimes();
		if (fdTimes.isEmpty()) {
			return false;
		}

		for (SysAttendCategoryTime time : fdTimes) {
			if (time.getFdTime() == null) {
				return false;
			}
			long fdTime = time.getFdTime().getTime();
			if (fdTime >= fdStartTime && fdTime < fdEndTime) {
				isNeeded = true;
				break;
			}
		}
		//排除节假日
		if(isHoliday(cate.getFdId(),date)){
			return false;
		}
		return isNeeded;
	}

	private boolean isNeededSigningByExcTarget(SysAttendCategory cate)
			throws Exception {
		if (cate.getFdType() == 1) {
			return true;
		}
		// 例外签到对象
		List<SysOrgElement> fdExcTargets = cate.getFdExcTargets();
		if (fdExcTargets == null || fdExcTargets.isEmpty()) {
			return true;
		}
		List ids = AttendPersonUtil.expandToPersonIds(fdExcTargets);
		if (ids.contains(UserUtil.getKMSSUser().getUserId())) {
			return false;
		}
		return true;
	}

	private boolean isNeededSigningByTarget(SysAttendCategory cate)
			throws Exception {
		// 签到对象
		List<SysOrgElement> fdTargets = cate.getFdTargets();
		if (fdTargets == null || fdTargets.isEmpty()) {
			return false;
		}
		List ids = AttendPersonUtil.expandToPersonIds(fdTargets);
		return ids.contains(UserUtil.getKMSSUser().getUserId());
	}

	@Override
	public JSONArray findConflictElement(String elementIds,
										 String exceptCategoryId) throws Exception {
		JSONArray array = new JSONArray();
		String[] elementIdArray = elementIds.split(";");
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
				.getBean("sysOrgElementService");
		for (String elementId : elementIdArray) {
			SysOrgElement element = (SysOrgElement) sysOrgElementService
					.findByPrimaryKey(elementId);
			Object[] result = ((ISysAttendCategoryDao) getBaseDao())
					.findExistCategory(element.getFdHierarchyId(),
							exceptCategoryId);
			if (result != null) {
				JSONObject obj = new JSONObject();
				obj.put("categoryName", (String) result[0]);
				obj.put("errorType", (String) result[1]);
				obj.put("elementId", element.getFdId());
				obj.put("elementName", element.getFdName());
				// 添加日志信息
				if(UserOperHelper.allowLogOper("findConflictElement",getModelName())){
					UserOperContentHelper.putFind(elementId, element.getFdName(),getModelName());
				}
				array.add(obj);
			}
		}
		return array;
	}

	@Override
	public String getAttendCategory(Date date) throws Exception {
		String categoryId = this.getCategory(UserUtil.getUser(),date);
		return categoryId;
	}

	@Override
	public String getAttendCategory(SysOrgElement orgEle) throws Exception {
		if (orgEle == null) {
			return null;
		}
		String categoryId = this.getCategory(orgEle,new Date());
		return categoryId;
	}

	@Override
	public String getAttendCategory(SysOrgElement orgEle, Date date)
			throws Exception {
		if (orgEle == null || date == null) {
			return null;
		}

		List<SysAttendCategory> list = new ArrayList<SysAttendCategory>();
		String categoryId = this.getCategory(orgEle,date);

		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category =CategoryUtil.getCategoryById(categoryId);
			list.add(category);
		}
		com.alibaba.fastjson.JSONArray datas = this.filterAttendCategory(list, date, true, orgEle);
		if (!datas.isEmpty()) {
			com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) datas.get(0);
			String fdId = (String) json.get("fdId");
			return fdId;
		}

		return null;
	}

	private void setCategoryCache(SysAttendCategory mainModel) {
		if (mainModel.getFdType() == 1) {
			clearCache();
			// 清空法定节假日缓存
			clearTimeHolidayCache();
			//清空人员跟排班的缓存
			clearSignTimesCache();
			try {
				//清理该考勤组内所有的历史考勤组配置信息
				Set<String>  hisCategoryIds = sysAttendHisCategoryService.getAllCategorys(Lists.newArrayList(mainModel.getFdId()),false);
				if(CollectionUtils.isNotEmpty(hisCategoryIds)) {
					for (String hisCategoryId : hisCategoryIds) {
						CategoryUtil.HIS_CATEGORY_CACHE_MAP.remove(hisCategoryId);
						CategoryUtil.CATEGORY_CACHE_MAP.remove(hisCategoryId);
					}
				} else {
					CategoryUtil.HIS_CATEGORY_CACHE_MAP.clear();
					CategoryUtil.CATEGORY_CACHE_MAP.clear();
				}
			}catch (Exception e){
				//如果异常，则全部清理
				CategoryUtil.HIS_CATEGORY_CACHE_MAP.clear();
				CategoryUtil.CATEGORY_CACHE_MAP.clear();
			}
			CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.clear();
			CategoryUtil.CATEGORY_USERS_CACHE_MAP.clear();
		}
	}

	private void clearCache() {
		// 清除缓存
		KmssCache cache = new KmssCache(SysAttendCategory.class);
		cache.clear();
	}

	private void clearTimeHolidayCache() {
		// 清空法定节假日缓存
		KmssCache cache = new KmssCache(SysTimeHoliday.class);
		cache.clear();
	}
	/**
	 * 获取用户考勤组
	 * 不过滤是否在排班、考勤时间范围
	 * @param element 人员
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCategory(SysOrgElement element,Date date) throws Exception {
		return getCategory(element,date,true);
	}
	/**
	 * 获取用户考勤组
	 * 不过滤是否在排班、考勤时间范围
	 * @param element 人员
	 * @param date 日期
	 * @param isExc 是否过滤排除人员
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getCategory(SysOrgElement element,Date date,Boolean  isExc) throws Exception {
		//人员跟日期在考勤组中存在的情况 则从缓存中获取
		SysAttendCategory sysAttendCategory = getCategoryInfo(element,date,isExc);
		if(sysAttendCategory!=null){
			return sysAttendCategory.getFdId();
		}
		return null;
	}
	/**
	 * 获取用户考勤组
	 * @param element 人员
	 * @param date 日期
	 * @param isExc 是否过滤排除人员
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysAttendCategory getCategoryInfo(SysOrgElement element,Date date,Boolean  isExc) throws Exception {
		//人员跟日期在考勤组中存在的情况 则从缓存中获取
		String key =SysAttendUserCacheUtil.getUserCategoryKey(element.getFdId());
		SysAttendUserCategoryListDto userCategoryListCatche = (SysAttendUserCategoryListDto) CategoryUtil.USER_CATEGORY_CACHE_MAP.get(key);
		if(userCategoryListCatche !=null){
			String categoryId = userCategoryListCatche.get(date);
			if(StringUtil.isNotNull(categoryId)) {
				return CategoryUtil.getCategoryById(categoryId);
			}
		}
		if(isExc ==null || isExc){
			//如果过滤排除人员，则全局排除人员也过滤
			// 全局例外考勤人员
			if (CategoryUtil.isGlobalExcTarget(CategoryUtil.getGlobalExcTargetMap(), element)) {
				return null;
			}
		}
		SysAttendCategory category = getSysAttendHisCategoryService().getCategoryByUserAndDate(element,date, CategoryUtil.CATEGORY_FD_TYPE_TRUE,isExc);
		if(category !=null){
			return category;
		}
		return null;
	}
	/**
	 * 获取某个法定节假日集合
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getHolidayMap(String categoryId) throws Exception {
		KmssCache cache = new KmssCache(SysTimeHoliday.class);
		String timeHolidayMapKey =String.format(CategoryUtil.HOLIDAY_CACHE_MAP_KEY,categoryId);
		String timeHolidayDayMapKey =String.format(CategoryUtil.HOLIDAY_DAY_CACHE_MAP_KEY,categoryId);
		Map timeHolidayMap = (Map) cache.get(timeHolidayMapKey);
		if (timeHolidayMap ==null || timeHolidayMap.isEmpty()) {
			HashMap result = new HashMap();
			HashMap patchMap = new HashMap();
			SysAttendCategory cate = CategoryUtil.getCategoryById(categoryId);
			SysTimeHoliday holiday =null;
			if(cate ==null){
				cate = (SysAttendCategory) findByPrimaryKey(categoryId);
				holiday = cate.getFdHoliday();
			}else{
				if(cate.getFdHoliday() !=null) {
					holiday = (SysTimeHoliday) sysTimeHolidayService.findByPrimaryKey(cate.getFdHoliday().getFdId());
				}
			}
			if(cate==null){
				return;
			}
			if (holiday != null) {
				List<SysTimeHolidayDetail> list = holiday.getFdHolidayDetailList();
				if (list != null && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						SysTimeHolidayDetail detail = (SysTimeHolidayDetail) list
								.get(i);
						Date fdStartDay = detail.getFdStartDay();
						Date fdEndDay = detail.getFdEndDay();
						if (fdEndDay.compareTo(fdStartDay) >= 0) {
							boolean flag = true;
							Calendar ca = Calendar.getInstance();
							ca.setTime(fdStartDay);
							while (flag) {
								String key = categoryId + "_"
										+ AttendUtil
												.getDate(ca.getTime(),
														0)
												.getTime();
								result.put(key, ca.getTime());
								if (AttendUtil.isSameDate(ca.getTime(),
										fdEndDay)) {
									flag = false;
								} else {
									ca.add(Calendar.DATE, 1);
								}
							}
						}

					}
				}
				// 补班
				List<SysTimeHolidayPach> pachList = this.sysTimeHolidayService
						.getHolidayPachs(holiday.getFdId());
				if (!pachList.isEmpty()) {
					for (SysTimeHolidayPach pach : pachList) {
						String key = categoryId + "_" + AttendUtil.getDate(pach.getFdPachTime(),0).getTime();
						patchMap.put(key,pach.getFdPachTime());
						//节假日
						result.remove(key);
					}
				}
			}
			cache.put(timeHolidayMapKey, patchMap);
			//记录考勤组对应的节假日
			cache.put(timeHolidayDayMapKey, result);
		}
	}
	/**
	 * 获取节假日的集合、排除补办日
	 * @return
	 * @throws Exception
	 */
	private Map getHolidayDayMap(String categoryId) throws Exception {
		KmssCache cache = new KmssCache(SysTimeHoliday.class);
		String key =String.format(CategoryUtil.HOLIDAY_DAY_CACHE_MAP_KEY,categoryId);
		Map timeHolidayPatchMap = (Map) cache.get(key);
		if (timeHolidayPatchMap ==null || timeHolidayPatchMap.isEmpty()) {
			getHolidayMap(categoryId);
			timeHolidayPatchMap = (Map) cache.get(key);
		}
		return timeHolidayPatchMap;
	}
	/**
	 * 获取节假日补班日期集合
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map getHolidayPatchMap(String categoryId) throws Exception {
		KmssCache cache = new KmssCache(SysTimeHoliday.class);
		String key =String.format(CategoryUtil.HOLIDAY_CACHE_MAP_KEY,categoryId);
		Map timeHolidayPatchMap = (Map) cache.get(key);
		if (timeHolidayPatchMap ==null || timeHolidayPatchMap.isEmpty()) {
			getHolidayMap(categoryId);
			timeHolidayPatchMap = (Map) cache.get(key);
		}
		return timeHolidayPatchMap;
	}

	/**
	 * 业务模块的签到组状态定时任务
	 * 
	 * @param category
	 * @throws Exception
	 */
	private void saveStatusQuart(SysAttendCategory category) throws Exception {
		if (category.getFdStatus() != 1) {
			return;
		}
		if (AttendConstant.FDTYPE_CUST == category.getFdType()) {
			if (StringUtil.isNull(category.getFdAppId())) {
				return;
			}
			List<SysAttendCategoryTime> times = category.getFdTimes();
			if (times.isEmpty()) {
				return;
			}
			SysAttendCategoryTime time = times.get(0);
			Date fdEndTime = category.getFdEndTime();
			if (fdEndTime != null && time.getFdTime() != null) {
				SysQuartzModelContext qContext1 = new SysQuartzModelContext();
				qContext1.setQuartzSubject(ResourceUtil.getString(
						"sys-attend:sysAttendMain.category.fdApp.job")
						+ "-" + category.getFdName());
				qContext1
						.setQuartzKey(
								"sysAttendCategory_fdApp_handle");
				JSONObject param = new JSONObject();
				param.put("fdCategoryId", category.getFdId());
				qContext1.setQuartzParameter(param.toString());
				Calendar cal = Calendar.getInstance();
				cal.setTime(time.getFdTime());
				cal.set(Calendar.HOUR_OF_DAY, fdEndTime.getHours());
				cal.add(Calendar.MINUTE, fdEndTime.getMinutes());
				qContext1.setQuartzCronExpression(
						cal.get(Calendar.SECOND)
								+ " " + cal.get(Calendar.MINUTE)
								+ " " + cal.get(Calendar.HOUR_OF_DAY)
								+ " " + cal.get(Calendar.DAY_OF_MONTH)
								+ " " + (cal.get(Calendar.MONTH) + 1)
								+ " ? " + cal.get(Calendar.YEAR));
				qContext1.setQuartzJobService("sysAttendCategoryJobService");
				qContext1.setQuartzJobMethod("updateStatus");
				sysQuartzCoreService.saveScheduler(qContext1, category);

			}
		}
	}

	/**
	 * 考勤组生效状态定时任务
	 * 
	 * @param category
	 * @throws Exception
	 */
	private void saveEffectTimeQuart(SysAttendCategory category)
			throws Exception {
		if (category.getFdStatus() != 0) {
			return;
		}
		if (StringUtil.isNotNull(category.getFdAppId())) {
			return;
		}

		SysQuartzModelContext qContext1 = new SysQuartzModelContext();
		qContext1.setQuartzSubject(ResourceUtil.getString(
				"sys-attend:sysAttendMain.category.fdEffectTime.job")
				+ "-" + category.getFdName());
		qContext1
				.setQuartzKey(
						"sysAttendCategory_fdEffectTime_handle");
		JSONObject param = new JSONObject();
		param.put("fdCategoryId", category.getFdId());
		param.put("fdOperType", "fdEffectTime");
		qContext1.setQuartzParameter(param.toString());
		Calendar cal = Calendar.getInstance();
		cal.setTime(category.getFdEffectTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.add(Calendar.MINUTE, 0);
		qContext1.setQuartzCronExpression(
				cal.get(Calendar.SECOND)
						+ " " + cal.get(Calendar.MINUTE)
						+ " " + cal.get(Calendar.HOUR_OF_DAY)
						+ " " + cal.get(Calendar.DAY_OF_MONTH)
						+ " " + (cal.get(Calendar.MONTH) + 1)
						+ " ? " + cal.get(Calendar.YEAR));
		qContext1.setQuartzJobService("sysAttendCategoryJobService");
		qContext1.setQuartzJobMethod("updateStatus");
		sysQuartzCoreService.saveScheduler(qContext1, category);
	}

	/**
	 * 生成缺卡定时任务，非排班的缺卡记录产生
	 * @param category
	 * @param deleteQuartzDate 定时任务的运行周期
	 * @throws Exception
	 */
	@Override
	public void saveMissSignQuart(SysAttendCategory category,Date deleteQuartzDate) throws Exception {
		if (!Integer.valueOf(1).equals(category.getFdStatus())) {
			return;
		}
		//考勤组类型
		if (AttendConstant.FDTYPE_ATTEND == category.getFdType()) {
			// 考勤
			Date now=new Date();
			if (Integer.valueOf(0).equals(category.getFdShiftType()) && Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
				// 一周不同上下班
				List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
				if (tSheets != null && !tSheets.isEmpty()) {
					for (int k = 0; k < tSheets.size(); k++) {
						SysAttendCategoryTimesheet tSheet = tSheets.get(k);
						if (StringUtil.isNull(tSheet.getFdWeek())) {
							continue;
						}
						Date endTime = AttendUtil.joinYMDandHMS(now,tSheet.getFdEndTime2());
						//最晚时间是否是次日
						boolean isAcross=Integer.valueOf(2).equals(tSheet.getFdEndDay())?true:false;
						saveMissSignQuartComm(
								tSheet.getFdId(),
								tSheet.getFdWeek(),
								endTime,
								isAcross,
								tSheet.getFdWeek().split("[,;]"),
								category,
								deleteQuartzDate
						);
					}
				}
			} else if (!Integer.valueOf(1).equals(category.getFdShiftType())) {
				//最晚时间是否是次日
				boolean isAcross=Integer.valueOf(2).equals(category.getFdEndDay())?true:false;
				// 一周相同上下班(非排班)
				Date endTime = AttendUtil.joinYMDandHMS(now,category.getFdEndTime());
				saveMissSignQuartComm(
						category.getFdId(),
						category.getFdWeek(),
						endTime,
						isAcross,
						null,category,
						deleteQuartzDate
						);
			}
		}
	}

	/**
	 * 生成缺卡记录的定时任务
	 * @param key
	 * @param title
	 * @param endTime
	 * @param category
	 * @param deleteQuartzDate 触发时间
	 * @throws Exception
	 */
	private void saveMissSignQuartComm(String key,
									   String title,
									   Date endTime,
									   Boolean isAcross,
									   String [] weeks,
									   SysAttendCategory category,
									   Date deleteQuartzDate
									   ) throws Exception {
		SysQuartzModelContext quartzContext1 = new SysQuartzModelContext();
		String quartKey =String.format("sysAttendCategory_miss_sign%s",key);
		quartzContext1.setQuartzKey(quartKey);
		String tempTitle="";
		if(StringUtil.isNotNull(title)){
			tempTitle+=String.format("-星期(%s)",title);
		}
		quartzContext1.setQuartzSubject(String.format("%s(%s)%s","【签到服务】生成缺卡定时任务",category.getFdName(),tempTitle));
		endTime =AttendUtil.removeSecond(endTime);
		//最晚打卡时间生成的日期。取日
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		int days =calendar.get(Calendar.DATE);
		//时间加上2分钟的 日大于原本的日 则判定生成跨天的缺卡
		calendar.add(Calendar.MINUTE,2);
		int newDay =calendar.get(Calendar.DATE);
		//例如：最晚打卡时间是07-05 23:59分，运行定时任务时，会增加2分钟 07-06 00:01执行。这时候，默认不跨天是07-06日。所以需要减去1天进行处理。
		if(newDay > days){
			isAcross =Boolean.TRUE;
		}
		List<String> newWeeks=Lists.newArrayList();
		//跨天的情况，把星期+1
		if (weeks != null) {
			for (String week : weeks) {
				if (StringUtil.isNotNull(week)) {
					if(Boolean.TRUE.equals(isAcross)){
						week =String.valueOf(Integer.valueOf(week)+1);
					}
					newWeeks.add(week);
				}
			}
		}
		quartzContext1.setQuartzCronExpression(getCronExpression(endTime, 2, newWeeks.toArray(new String[newWeeks.size()])));
		JSONObject parameter1 = new JSONObject();
		parameter1.put("fdCategoryId",category.getFdId());
		parameter1.put("isAcross",isAcross);
		//最晚打卡时间加上59秒
		parameter1.put("fdEndTime",AttendUtil.addDate(endTime,Calendar.SECOND,59).getTime());
		parameter1.put("quartKey",quartKey);
		//运行的周期
		parameter1.put("weeks", Joiner.on(";").join(newWeeks));
		if(deleteQuartzDate !=null){
			//指定删除定时任务的日期
			parameter1.put("deleteQuartzDate",deleteQuartzDate.getTime());
		}else{
			parameter1.put("deleteQuartzDate",0L);
		}
		quartzContext1.setQuartzParameter(parameter1.toString());
		quartzContext1.setQuartzJobService("sysAttendMainJobService");
		quartzContext1.setQuartzJobMethod("executeCategory");
		sysQuartzCoreService.saveScheduler(quartzContext1,category);
	}
	/**
	 * 保存打卡提醒定时任务
	 * 
	 * @param category
	 * @throws Exception
	 */
	private void saveRemindQuart(SysAttendCategory category) throws Exception {
		if (!Integer.valueOf(1).equals(category.getFdStatus())) {
			return;
		}
		if (AttendConstant.FDTYPE_ATTEND == category.getFdType()) {
			// 考勤
			if (Integer.valueOf(0).equals(category.getFdShiftType())
					&& Integer.valueOf(1)
							.equals(category.getFdSameWorkTime())) {// 一周不同上下班

				List<SysAttendCategoryTimesheet> tSheets = category
						.getFdTimeSheets();
				if (tSheets != null && !tSheets.isEmpty()) {
					for (int k = 0; k < tSheets.size(); k++) {
						SysAttendCategoryTimesheet tSheet = tSheets.get(k);
						if (StringUtil.isNull(tSheet.getFdWeek())) {
							continue;
						}
						List<SysAttendCategoryWorktime> worktimes = tSheet
								.getAvailWorkTime();
						for (int x = 0; x < worktimes.size(); x++) {
							SysAttendCategoryWorktime worktime = worktimes
									.get(x);
							// 上班
							if (category.getFdNotifyOnTime() != null
									&& category.getFdNotifyOnTime()
											.intValue() != 0
									&& worktime.getFdStartTime() != null) {
								SysQuartzModelContext quartzContext1 = new SysQuartzModelContext();
								quartzContext1
										.setQuartzKey(
												"sysAttendCategory_remind_onwork"
														+ "_" + k + "_" + x);
								quartzContext1.setQuartzSubject(
										ResourceUtil.getString(
												"sys-attend:sysAttendCategory.quartz.onwork.subject")
												+ "-" + category.getFdName()
												+ "("
												+ tSheet.getFdWeek() + ")");
								quartzContext1
										.setQuartzCronExpression(
												getCronExpression(
														worktime.getFdStartTime(),
														-(category
																.getFdNotifyOnTime()
																.intValue()),
														tSheet.getFdWeek()
																.split("[,;]")));
								JSONObject parameter1 = new JSONObject();
								parameter1.put("fdCategoryId",category.getFdId());
								parameter1.put("fdWorkType",AttendConstant.SysAttendMain.FD_WORK_TYPE[0]);
								parameter1.put("fdWorkId", worktime.getFdId());
								parameter1.put("dayOffset",
										getOffsetDayNum(
												worktime.getFdStartTime(),
												-(category.getFdNotifyOnTime()
														.intValue())));// 多少分钟前提醒，可能是前一天
								quartzContext1.setQuartzParameter(
										parameter1.toString());
								quartzContext1
										.setQuartzJobService(
												"sysAttendCategoryService");
								quartzContext1
										.setQuartzJobMethod("sendWorkRemind");
								sysQuartzCoreService.saveScheduler(
										quartzContext1,
										category);
							}

							// 下班
							if (category.getFdNotifyOffTime() != null
									&& category.getFdNotifyOffTime()
											.intValue() != 0
									&& worktime.getFdEndTime() != null) {
								SysQuartzModelContext quartzContext2 = new SysQuartzModelContext();
								quartzContext2
										.setQuartzKey(
												"sysAttendCategory_remind_offwork"
														+ "_" + k + "_" + x);
								quartzContext2.setQuartzSubject(
										ResourceUtil.getString(
												"sys-attend:sysAttendCategory.quartz.offwork.subject")
												+ "-" + category.getFdName()
												+ "("
												+ tSheet.getFdWeek() + ")");
								quartzContext2
										.setQuartzCronExpression(
												getCronExpression(
														worktime.getFdEndTime(),
														category.getFdNotifyOffTime()
																.intValue(),
														tSheet.getFdWeek()
																.split("[,;]")));
								JSONObject parameter2 = new JSONObject();
								parameter2.put("fdCategoryId",
										category.getFdId());
								parameter2.put("fdWorkType",
										AttendConstant.SysAttendMain.FD_WORK_TYPE[1]);
								parameter2.put("fdWorkId", worktime.getFdId());
								parameter2.put("dayOffset",
										getOffsetDayNum(worktime.getFdEndTime(),
												category.getFdNotifyOffTime()
														.intValue()));// 多少分钟后提醒，可能是下一天
								quartzContext2.setQuartzParameter(
										parameter2.toString());
								quartzContext2
										.setQuartzJobService(
												"sysAttendCategoryService");
								quartzContext2
										.setQuartzJobMethod("sendWorkRemind");
								sysQuartzCoreService.saveScheduler(
										quartzContext2,
										category);
							}
						}
					}
				}
			} else if (!Integer.valueOf(1).equals(category.getFdShiftType())) {// 一周相同上下班(非排班)
				List<SysAttendCategoryWorktime> worktimes = category
						.getAvailWorkTime();
				for (int i = 0; i < worktimes.size(); i++) {

					SysAttendCategoryWorktime worktime = worktimes.get(i);

					// 上班
					if (category.getFdNotifyOnTime() != null
							&& category.getFdNotifyOnTime().intValue() != 0
							&& worktime.getFdStartTime() != null) {
						SysQuartzModelContext quartzContext1 = new SysQuartzModelContext();
						quartzContext1
								.setQuartzKey(
										"sysAttendCategory_remind_onwork" + i);
						quartzContext1.setQuartzSubject(
								ResourceUtil.getString(
										"sys-attend:sysAttendCategory.quartz.onwork.subject")
										+ "-" + category.getFdName());
						quartzContext1
								.setQuartzCronExpression(
										getCronExpression(
												worktime.getFdStartTime(),
												-(category.getFdNotifyOnTime()
														.intValue()),
												null));
						JSONObject parameter1 = new JSONObject();
						parameter1.put("fdCategoryId", category.getFdId());
						parameter1.put("fdWorkType",
								AttendConstant.SysAttendMain.FD_WORK_TYPE[0]);
						parameter1.put("fdWorkId", worktime.getFdId());
						parameter1.put("dayOffset",
								getOffsetDayNum(worktime.getFdStartTime(),
										-(category.getFdNotifyOnTime()
												.intValue())));// 多少分钟前提醒，可能是前一天
						quartzContext1
								.setQuartzParameter(parameter1.toString());
						quartzContext1
								.setQuartzJobService(
										"sysAttendCategoryService");
						quartzContext1.setQuartzJobMethod("sendWorkRemind");
						sysQuartzCoreService.saveScheduler(quartzContext1,
								category);
					}

					// 下班
					if (category.getFdNotifyOffTime() != null
							&& category.getFdNotifyOffTime().intValue() != 0
							&& worktime.getFdEndTime() != null) {
						SysQuartzModelContext quartzContext2 = new SysQuartzModelContext();
						quartzContext2
								.setQuartzKey(
										"sysAttendCategory_remind_offwork" + i);
						quartzContext2.setQuartzSubject(
								ResourceUtil.getString(
										"sys-attend:sysAttendCategory.quartz.offwork.subject")
										+ "-" + category.getFdName());
						quartzContext2
								.setQuartzCronExpression(
										getCronExpression(
												worktime.getFdEndTime(),
												category.getFdNotifyOffTime()
														.intValue(),
												null));
						JSONObject parameter2 = new JSONObject();
						parameter2.put("fdCategoryId", category.getFdId());
						parameter2.put("fdWorkType",
								AttendConstant.SysAttendMain.FD_WORK_TYPE[1]);
						parameter2.put("fdWorkId", worktime.getFdId());
						parameter2.put("dayOffset",
								getOffsetDayNum(worktime.getFdEndTime(),
										category.getFdNotifyOffTime()
												.intValue()));// 多少分钟后提醒，可能是下一天
						quartzContext2
								.setQuartzParameter(parameter2.toString());
						quartzContext2
								.setQuartzJobService(
										"sysAttendCategoryService");
						quartzContext2.setQuartzJobMethod("sendWorkRemind");
						sysQuartzCoreService.saveScheduler(quartzContext2,
								category);
					}

				}
			}

		} else if (AttendConstant.FDTYPE_CUST == category.getFdType()) {// 签到
			Date fdStartTime = category.getFdStartTime();
			if (category.getFdRule() != null
					&& !category.getFdRule().isEmpty()) {
				SysAttendCategoryRule rule = category.getFdRule().get(0);
				if (rule.getFdInTime() != null) {
					fdStartTime = rule.getFdInTime();
				}
			}

			if (category.getFdNotifyOnTime() != null
					&& category.getFdNotifyOnTime().intValue() != 0
					&& fdStartTime != null) {
				SysQuartzModelContext quartzContext = new SysQuartzModelContext();
				quartzContext.setQuartzSubject(
						ResourceUtil.getString(
								"sys-attend:sysAttendCategory.quartz.custom.subject")
								+ "-" + category.getFdName());
				quartzContext.setQuartzCronExpression(getCronExpression(
						fdStartTime,
						-(category.getFdNotifyOnTime().intValue()),
						null));
				quartzContext
						.setQuartzJobService("sysAttendCategoryService");
				quartzContext.setQuartzJobMethod("sendCustomRemind");
				JSONObject param = new JSONObject();
				param.put("fdCategoryId", category.getFdId());
				param.put("dayOffset", getOffsetDayNum(fdStartTime,
						-(category.getFdNotifyOnTime().intValue())));// 多少分钟前提醒，可能是前一天
				quartzContext.setQuartzParameter(param.toString());
				sysQuartzCoreService.saveScheduler(quartzContext,
						category);
			}
		}
	}

	/**
	 * 定时任务触发的规则
	 * @param time 时间
	 * @param offset 增加多少分钟
	 * @param weeks 星期几运行
	 * @return
	 */
	private String getCronExpression(Date time, int offset, String[] weeks) {
		return getCronExpression(time,offset,null,weeks);
	}
	/**
	 * 定时任务触发的规则
	 * @param time 时间
	 * @param offset 增加多少时间。配合类型使用，负数标识减少
	 * @param offSetType 增加类型
	 * @param weeks 星期几运行
	 * @return
	 */
	private String getCronExpression(Date time, int offset,Integer offSetType, String[] weeks) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		//没指定增加的时间类型，默认是分钟
		if(offSetType ==null) {
			cal.add(Calendar.MINUTE, offset);
		}else{
			cal.add(offSetType, offset);
		}
		String weekPart = "";
		if (weeks != null) {
			for (String week : weeks) {
				if (StringUtil.isNotNull(week)) {
					String prefix = StringUtil
							.isNotNull(weekPart) ? "," : "";
					weekPart += prefix
							+ (Integer.valueOf(week) % 7 + 1);
				}
			}
		}
		if (StringUtil.isNull(weekPart)) {
			weekPart = "*";
		}
		String exp = cal.get(Calendar.SECOND) + " " + cal.get(Calendar.MINUTE)
				+ " " + cal.get(Calendar.HOUR_OF_DAY) + " ? * " + weekPart
				+ " * ";
		return exp;
	}

	private int getOffsetDayNum(Date nowTime, int offsetTime) {// 通知在几天后发送
		int nowMins = nowTime.getHours() * 60 + nowTime.getMinutes();
		int notifyTime = nowMins + offsetTime;
		if (notifyTime >= 0 || notifyTime <= -1440) {
			return notifyTime / 1440;
		} else {
			return -1;
		}
	}

	/**
	 * 定时任务发送考勤组提醒
	 * @param context
	 * @throws Exception
	 */
	@Override
	public void sendWorkRemind(SysQuartzJobContext context) throws Exception {
		JSONObject param = JSONObject.fromObject(context.getParameter());
		logger.debug("考勤提醒定时任务启动:" + param.toString());
		String fdCategoryId = param.getString("fdCategoryId");
		if (StringUtil.isNull(fdCategoryId)) {
			logger.warn("考勤提醒定时任务退出,考勤组fdCategoryId为空!");
			return;
		}
		SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
		Integer dayOffset = Integer.valueOf(param.getInt("dayOffset"));
		Date curTime = DateUtil.getDate(0 - dayOffset.intValue());
		//考勤组如果没有在历史表找到，则去查找原始考勤组对应日期的历史考勤组
		if(category ==null){
			category =CategoryUtil.getLastVersionCategoryFdId(fdCategoryId,curTime);
		}
		if (category == null) {
			logger.warn(
					"考勤提醒定时任务退出,考勤组为空,并删除相应定时任务!fdCategoryId:" + fdCategoryId);
			List<SysQuartzJob> jobList = getSysQuartzJobService()
					.findList(
							"fdModelName='com.landray.kmss.sys.attend.model.SysAttendCategory' and fdModelId='"
									+ fdCategoryId + "'",
							null);
			if (jobList != null && !jobList.isEmpty()) {
				for (SysQuartzJob job : jobList) {
					getSysQuartzJobService().delete(job);
				}
			}
			return;
		}

		Integer fdWorkType = Integer.valueOf(param.getInt("fdWorkType"));// 上班通知or下班通知
		String fdWorkId = param.getString("fdWorkId");
		if(StringUtil.isNull(fdWorkId)){
			logger.warn("参数fdWorkId为空，发送提醒失败,考勤组名称："+category.getFdName());
			return;
		}
		// 今天是否需要发代办
		Integer fdPeriodType = category.getFdPeriodType();

		Date nextTime = DateUtil.getDate(1 - dayOffset.intValue());
		long fdStartTime = curTime.getTime();
		long fdEndTime = nextTime.getTime();
		boolean isNeeded = false;
		if (AttendConstant.FDPERIODTYPE_WEEK
				.equals(String.valueOf(fdPeriodType))) {
			isNeeded = isNeededSigningByPeriod(category, curTime,
					fdStartTime,
					fdEndTime);
		}
		if (AttendConstant.FDPERIODTYPE_CUST
				.equals(String.valueOf(fdPeriodType))) {
			isNeeded = isNeededSigningByCust(category, curTime,
					fdStartTime,
					fdEndTime);
		}
		if (!isNeeded) {
			logger.warn(
					"考勤提醒定时任务退出,当天不需要考勤打卡!fdCategoryId:" + fdCategoryId
							+ ";curTime:" + curTime);
			return;
		}

		// 标准打卡时间
		Date signTime = null;
		List<SysAttendCategoryWorktime> workTimeList = category.getAllWorkTime();
		if (workTimeList != null && !workTimeList.isEmpty()) {
			for (SysAttendCategoryWorktime workTime : workTimeList) {
				if (fdWorkId.equals(workTime.getFdId())) {
					if (fdWorkType == 1) {
						signTime = workTime.getFdEndTime();
					} else {
						signTime = workTime.getFdStartTime();
					}
				}
			}
		}
		String signTimeTxt = DateUtil.convertDateToString(signTime, "HH:mm");
		if (StringUtil.isNull(signTimeTxt)) {
			logger.warn("标准打卡时间为空!fdCategoryId:" + fdCategoryId);
			return;
		}
		// 考勤组人员
		List<String> fdTargetIds = this.getAttendOrgElementIdList(category.getFdId(),curTime,CategoryUtil.CATEGORY_FD_TYPE_TRUE,false);

		if (fdTargetIds == null || fdTargetIds.isEmpty()) {
			logger.warn(
					"考勤提醒定时任务退出,该考勤组没有考勤人员!fdCategoryId:" + fdCategoryId);
			return;
		}
		// 用户组分割
		int maxCount = 100;
		List<List> groupLists = new ArrayList<List>();
		if (fdTargetIds.size() <= maxCount) {
			groupLists.add(fdTargetIds);
		} else {
			groupLists = AttendUtil.splitList(fdTargetIds, maxCount);
		}
		for (int i = 0; i < groupLists.size(); i++) {
			//人员分100个一批。发送待办
			SendWorkReminTask task=new SendWorkReminTask(signTime,signTimeTxt,fdWorkType,category,groupLists.get(i),curTime,nextTime,fdWorkId,baseDao);
			//异步处理
			KMSSCommonThreadUtil.execute(task);
		}
	}

	/**
	 * 发送待办的异步线程
	 */
	class SendWorkReminTask implements Runnable{
		/**
		 * 标准打卡时间
		 */
		Date signTime = null;
		/**
		 * 打卡时间格式
		 */
		String signTimeTxt = null;
		/**
		 * 打卡类型-上班或者下班
		 */
		Integer fdWorkType;
		/**
		 * 考勤组
		 */
		SysAttendCategory category;

		/**
		 * 打卡人
		 */
		List<String> fdTargetIds;
		Date curTime;
		Date nextTime;
		String fdWorkId;
		IBaseDao baseDao;
		public SendWorkReminTask(Date signTime,
								 String signTimeTxt,
								 Integer fdWorkType,
								 SysAttendCategory category,
								 List<String> fdTargetIds,
								 Date curTime,
								 Date nextTime,
								 String fdWorkId,
								 IBaseDao baseDao
								 ) {
			this.signTime = signTime;
			this.signTimeTxt = signTimeTxt;
			this.fdWorkType = fdWorkType;
			this.category = category;
			this.fdTargetIds = fdTargetIds;
			this.curTime = curTime;
			this.nextTime = nextTime;
			this.fdWorkId = fdWorkId;
			this.baseDao = baseDao;
		}

		@Override
		public void run() {
			TransactionStatus status = null;
			boolean isException =false;
			try {
				if(signTime ==null || signTimeTxt ==null || category ==null || fdTargetIds ==null ||	curTime ==null ||	nextTime ==null ){
					return;
				}
				status = TransactionUtils.beginNewTransaction();

				// 过滤已打卡人员。只根据考勤组时间来。fdWorkId 条件删除
				String where = " fd_category_his_id=:fdCategoryId and fd_work_id=:fdWorkId and fd_work_type=:fdWorkType "
						+ " and (doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross) "
						+ " or (fd_base_work_time>=:beginTime and fd_base_work_time<:endTime)) "
						+ " and " + HQLUtil.buildLogicIN("doc_creator_id", fdTargetIds);
				String signedSql = "select doc_creator_id from sys_attend_main where "
						+ where
						+" group by doc_creator_id ";
				//根据人员查找，如果找到了。就剔除掉
				List signedList = baseDao.getHibernateSession().createNativeQuery(signedSql).setParameter("fdIsAcross", false)
						.setString("fdCategoryId", category.getFdId())
						.setDate("beginTime", curTime)
						.setDate("endTime", nextTime)
						.setString("fdWorkId", fdWorkId)
						.setInteger("fdWorkType", fdWorkType).list();
				if (!signedList.isEmpty()) {
					fdTargetIds.removeAll(signedList);
				}
				if (CollectionUtils.isEmpty(fdTargetIds)) {
					logger.debug("考勤提醒定时任务退出,需要发送待办人员为空!fdCategoryId:" + category.getFdName());
				} else {
					// 发待办
					NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);
					String subject = "";
					Date workTime = new Date();
					workTime.setHours(signTime.getHours());
					if (fdWorkType.intValue() == AttendConstant.SysAttendMain.FD_WORK_TYPE[0]) {
						subject = ResourceUtil.getString(
								"sysAttendCategory.notify.remind.onwork.subject",
								"sys-attend", null, new String[]{signTimeTxt,
										String.valueOf(category.getFdNotifyOnTime())});
						workTime.setMinutes(signTime.getMinutes());
					} else if (fdWorkType.intValue() == AttendConstant.SysAttendMain.FD_WORK_TYPE[1]) {
						subject = ResourceUtil.getString(
								"sysAttendCategory.notify.remind.offwork.subject",
								"sys-attend", null, new String[]{signTimeTxt,
										String.valueOf(category.getFdNotifyOffTime())});
						Integer min = category.getFdNotifyOffTime() == null ? 0 : category.getFdNotifyOffTime();
						workTime.setMinutes(signTime.getMinutes() + min * 2);
					}
					notifyContext.setFdEndTime(workTime.getTime());
					String title = ResourceUtil.getString(
							"sysAttendCategory.notify.attend.remind.title", "sys-attend");
					List<SysOrgElement> targets = AttendPersonUtil.getSysOrgElementById(fdTargetIds);

					notifyContext.setSubject(subject);
					notifyContext.setContent(subject);
					notifyContext.setNotifyTarget(targets);
					notifyContext.setNotifyType("todo");
					// 钉钉提醒不消失，故改成待阅
					notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
					notifyContext.setLink("/sys/attend/mobile/index_forward.jsp");
					notifyContext.setFdAppType("weixin;ding;wxwork");
					notifyContext.setFdNotifyEKP(false);
					sysNotifyMainCoreService.sendNotify(category, notifyContext, null);
					// kk消息单独发送
					notifyContext.setSubject(title);
					notifyContext.setFdAppType("kk");
					notifyContext.setFdAppReceiver("kk_system");
					notifyContext.setFdNotifyEKP(false);
					logger.debug(
							"考勤提醒定时任务运行完成,提醒打卡人员:" + fdTargetIds.toString());
					sysNotifyMainCoreService.sendNotify(category, notifyContext, null);

					addRemindLog(subject, targets, "todo", new Date(), category.getFdId(), category.getFdName());
				}
				TransactionUtils.commit(status);
			}catch (Exception e){
				isException =true;
				e.printStackTrace();
			}finally {
				if(status !=null && isException){
					TransactionUtils.rollback(status);
				}
			}
		}
	}

	private void addRemindLog(String fdSubject, List<SysOrgElement> fdTargets,
			String fdNotifyType, Date fdTime, String fdCategoryId,
			String fdCategoryName) throws Exception {
		SysAttendNotifyRemindLog log = new SysAttendNotifyRemindLog();
		log.setFdId(IDGenerator.generateID());
		log.setFdSubject(fdSubject);
		log.setFdTargets(fdTargets);
		log.setFdNotifyType(fdNotifyType);
		log.setFdTime(fdTime);
		log.setFdCategoryId(fdCategoryId);
		log.setFdCategoryName(fdCategoryName);
		sysAttendNotifyRemindLogService.add(log);
	}

	// 签到提醒
	@Override
	public void sendCustomRemind(SysQuartzJobContext context) throws Exception {
		JSONObject param = JSONObject.fromObject(context.getParameter());
		String fdCategoryId = param.getString("fdCategoryId");
		if (StringUtil.isNull(fdCategoryId)) {
			return;
		}
		SysAttendCategory category = (SysAttendCategory) this
				.findByPrimaryKey(fdCategoryId, null, true);
		if (category == null) {
			List<SysQuartzJob> jobList = getSysQuartzJobService()
					.findList(
							"fdModelName='com.landray.kmss.sys.attend.model.SysAttendCategory' and fdModelId='"
									+ fdCategoryId + "'",
							null);
			if (jobList != null && !jobList.isEmpty()) {
				for (SysQuartzJob job : jobList) {
					getSysQuartzJobService().delete(job);
				}
			}
			return;
		}
		Integer dayOffset = Integer.valueOf(param.getInt("dayOffset"));

		// 今天是否需要发代办
		Integer fdPeriodType = category.getFdPeriodType();
		Date curTime = DateUtil.getDate(0 - dayOffset.intValue());
		Date nextTime = DateUtil.getDate(1 - dayOffset.intValue());
		long fdStartTime = curTime.getTime();
		long fdEndTime = nextTime.getTime();
		boolean isNeeded = false;
		if (AttendConstant.FDPERIODTYPE_WEEK
				.equals(String.valueOf(fdPeriodType))) {
			isNeeded = isNeededSigningByPeriod(category, curTime,
					fdStartTime,
					fdEndTime);
		}
		if (AttendConstant.FDPERIODTYPE_CUST
				.equals(String.valueOf(fdPeriodType))) {
			isNeeded = isNeededSigningByCust(category, curTime,
					fdStartTime,
					fdEndTime);
		}
		if (!isNeeded) {
			return;
		}

		// 过滤排除人员
		List<String> fdTargetIds = sysOrgCoreService
				.expandToPersonIds(category.getFdTargets());
		List<String> fdExcTargetIds = sysOrgCoreService
				.expandToPersonIds(category.getFdExcTargets());
		fdTargetIds.removeAll(fdExcTargetIds);

		// 过滤已打卡人员
		String where = " fd_category_id=:fdCategoryId and doc_create_time>=:beginTime and "
				+ "doc_create_time<:endTime and fd_work_id is null and fd_work_type is null and fd_status!=0";
		String signedSql = "select distinct doc_creator_id from sys_attend_main where "
				+ where;
		String countSql = "select count(distinct doc_creator_id) as count from sys_attend_main where "
				+ where;
		Number count = (Number) baseDao.getHibernateSession().createNativeQuery(countSql).setString("fdCategoryId", category.getFdId()).setDate("beginTime", curTime).setDate("endTime", nextTime).uniqueResult();
		if (count.intValue() == fdTargetIds.size()) {
			return;// 全部人已打卡
		}

		int page = 1, rowSize = 1000,
				pageCount = (count.intValue() % rowSize == 0)
						? count.intValue() / rowSize
						: (count.intValue() / rowSize + 1);
		pageCount = pageCount <= 0 ? 1 : pageCount;
		while (page <= pageCount) {
			List signedList = baseDao.getHibernateSession().createNativeQuery(signedSql).setString("fdCategoryId", category.getFdId()).setDate("beginTime", curTime).setDate("endTime", nextTime).setFirstResult((page - 1) * rowSize).setMaxResults(rowSize).list();
			if (!signedList.isEmpty()) {
				fdTargetIds.removeAll(signedList);
			}
			page++;
		}

		if (fdTargetIds.isEmpty()) {
			return;// 全部人已打卡
		}

		// 发待办
		NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);
		String subject = "";
		if (category.getFdNotifyOnTime().intValue() == 0) {
			subject = ResourceUtil.getString("sys-attend",
					"sysAttendCategory.notify.custom.now.subject",
					null);
		} else {
			subject = ResourceUtil.getString(
					"sysAttendCategory.notify.custom.subject",
					"sys-attend",
					null, new String[] {
							String.valueOf(category.getFdNotifyOnTime())
					});
		}
		String title = ResourceUtil.getString(
				"sysAttendCategory.notify.cust.remind.title", "sys-attend");
		notifyContext.setSubject(subject);
		notifyContext.setContent(subject);
		List<SysOrgElement> targets = sysOrgCoreService
				.expandToPerson(fdTargetIds);
		notifyContext.setNotifyTarget(targets);
		notifyContext.setNotifyType("todo");
		// 钉钉提醒不消失，故改成待阅
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		String notifyLink = "/sys/attend/mobile/index.jsp";
		if (StringUtil.isNotNull(category.getFdAppUrl())) {
			notifyLink = "/sys/attend/mobile/import/sign_inner.jsp?categoryId="
					+ category.getFdId();
		}
		if("true".equals(category.getFdPermState())){
			notifyLink = "/km/imeeting/mobile/maxhub/signReminder.jsp";
		}
		notifyContext.setLink(notifyLink);
		notifyContext.setFdAppType("weixin;ding;wxwork");
		notifyContext.setFdNotifyEKP(false);
		sysNotifyMainCoreService.sendNotify(category, notifyContext, null);

		// kk消息单独发送
		notifyContext.setSubject(title);
		notifyContext.setFdAppType("kk");
		notifyContext.setFdAppReceiver("kk_system");
		notifyContext.setFdNotifyEKP(false);
		sysNotifyMainCoreService.sendNotify(category, notifyContext, null);
	}

	@Override
	public Map<String, Object> getSignExtend() throws Exception {
		IExtension[] extensions = AttendPlugin.getExtensions();
		Map<String, Object> map = new HashMap<String, Object>();

		for (IExtension extension : extensions) {
			JSONObject obj = new JSONObject();
			String modelName = Plugin.getParamValueString(extension,
					"modelName");
			obj = new JSONObject();
			obj.accumulate("moduleName",
					Plugin.getParamValueString(extension, "moduleName"));
			obj.accumulate("modelKey",
					Plugin.getParamValueString(extension, "modelKey"));
			obj.accumulate("modelName", modelName);
			map.put(modelName, obj);
		}
		return map;
	}

	@Override
	public List findSignCategorysByLeader(SysOrgElement orgEle)
			throws Exception {
		List<String> list = new ArrayList<String>();
		List cateList = this.findList(
				"sysAttendCategory.fdType=2 and (sysAttendCategory.fdAppId='' or sysAttendCategory.fdAppId is null) and sysAttendCategory.fdStatus=1 and sysAttendCategory.fdManager.fdId='"
						+ orgEle.getFdId() + "'",
				null);
		for (int i = 0; i < cateList.size(); i++) {
			SysAttendCategory cate = (SysAttendCategory) cateList.get(i);
			list.add(cate.getFdId());
		}
		return list;
	}

	@Override
	public List findCategorysByLeader(SysOrgElement orgEle, int fdType)
			throws Exception {
		List<String> list = new ArrayList<String>();
		StringBuffer where = new StringBuffer();
		where.append(
				"sysAttendCategory.fdStatus in(1,2) and sysAttendCategory.fdManager.fdId='"
						+ orgEle.getFdId() + "'");
		if (fdType == 1) {
			where.append(" and sysAttendCategory.fdType=" + fdType);
		}
		else if (fdType == 2) {
			where.append(
					" and (sysAttendCategory.fdAppId='' or sysAttendCategory.fdAppId is null) and sysAttendCategory.fdType="
							+ fdType);
		}
		List<String> cateList = this.findValue("sysAttendCategory.fdId",where.toString(), null);
		if(CollectionUtils.isNotEmpty(cateList)){
			list.addAll(cateList);
			if(fdType==1){
				//查询所有的历史考勤组ID
				Set<String> hisCategoryIds = getSysAttendHisCategoryService().getAllCategorys(cateList,orgEle);
				if(CollectionUtils.isNotEmpty(hisCategoryIds)){
					list.addAll(hisCategoryIds);
				}
			}
		}
		return list;
	}

	@Override
	public List findCategorysByAppId(String fdAppId) throws Exception {
		List<SysAttendCategory> categories = new ArrayList<SysAttendCategory>();
		if (StringUtil.isNotNull(fdAppId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysAttendCategory.fdAppId=:appId and sysAttendCategory.fdStatus!=3");
			hqlInfo.setParameter("appId", fdAppId);
			hqlInfo.setOrderBy("sysAttendCategory.docCreateTime desc");
			categories = this.findList(hqlInfo);
		}
		return categories;
	}

	@Override
	public List findCateIdsByAuthId(List<String> authId, Integer fdType)
			throws Exception {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		String recordSql = "SELECT DISTINCT r.fd_doc_id FROM sys_attend_cate_areaders r LEFT JOIN sys_attend_category c ON r.fd_doc_id=c.fd_id WHERE c.fd_status=1 and c.fd_type=:fdType and r.auth_all_reader_id in(:userId) UNION "
				+ "SELECT DISTINCT e.fd_doc_id FROM sys_attend_cate_aeditors e LEFT JOIN sys_attend_category c ON e.fd_doc_id=c.fd_id WHERE c.fd_status=1 and c.fd_type=:fdType and e.auth_all_editor_id in(:userId) ";
		List<String> recordList = baseDao.getHibernateSession().createNativeQuery(recordSql).setInteger("fdType", fdType).setParameterList("userId", authId).list();
		return recordList;
	}

	@Override
	public List findCateIdsByReaderId(String readerId, Integer fdType)
			throws Exception {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		String recordSql = "SELECT DISTINCT r.fd_doc_id FROM sys_attend_cate_areaders r LEFT JOIN sys_attend_category c ON r.fd_doc_id=c.fd_id WHERE c.fd_status=1 and c.fd_type=:fdType and r.auth_all_reader_id=:userId";
		List<String> recordList = baseDao.getHibernateSession().createNativeQuery(recordSql).setInteger("fdType", fdType).setString("userId", readerId).list();
		return recordList;
	}

	@Override
	public List findCateIdsByEditorId(String editorId, Integer fdType)
			throws Exception {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		String recordSql = "SELECT DISTINCT e.fd_doc_id FROM sys_attend_cate_aeditors e LEFT JOIN sys_attend_category c ON e.fd_doc_id=c.fd_id WHERE c.fd_status=1 and c.fd_type=:fdType and e.auth_all_editor_id=:userId";
		List<String> recordList = baseDao.getHibernateSession().createNativeQuery(recordSql).setInteger("fdType", fdType).setString("userId", editorId).list();
		return recordList;
	}

	@Override
	public Map<String, SysAttendCategory> getCategoryMap() throws Exception {
		Map<String, SysAttendCategory> cateMap = new HashMap<String, SysAttendCategory>();
		List<SysAttendCategory> cateList = findList(
				"sysAttendCategory.fdType=1", "");
		if (cateList == null || cateList.isEmpty()) {
			return null;
		}
		for (SysAttendCategory cate : cateList) {
			cateMap.put(cate.getFdId(), cate);
		}
		return cateMap;
	}

	/**
	 * 获取某天考勤组打卡时间点相关信息
	 */
	@Override
	public List getAttendSignTimes(SysAttendCategory category, Date date)
			throws Exception {
		return this.getAttendSignTimes(category, date, null);
	}

	@Override
	public List getAttendSignTimes(SysOrgElement ele, Date date)
			throws Exception {
		String fdCategoryId = this.getCategory(ele,date);
		if (StringUtil.isNull(fdCategoryId)) {
			return new ArrayList<Map<String, Object>>();
		}
		SysAttendCategory category =CategoryUtil.getCategoryById(fdCategoryId);
		if(category ==null){
			return new ArrayList<Map<String, Object>>();
		}
		//已增加缓存处理
		return this.getAttendSignTimes(category, date, ele);
	}

	/**
	 * 清空人员排班缓存。
	 * 用于其他模块调用
	 * @throws Exception
	 */
	@Override
	public void clearSignTimesCache(){
		CategoryUtil.USER_WORKTIME_CACHE_MAP.clear();
	}

	/**
	 * 供排班管理获取用户考勤组 对应的排班情况。
	 * 不过滤排除人员
	 * 兼容排班管理中的参数。
	 * @param ele
	 * @param date
	 * @param need 自然日 是否需要获取工时
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAttendSignTimesOfTime(SysOrgElement ele, Date date, boolean need)
			throws Exception {
		//不过滤排除人员
		String fdCategoryId = this.getCategory(ele,date,false);
		if (StringUtil.isNull(fdCategoryId)) {
			return new ArrayList<Map<String, Object>>();
		}
		SysAttendCategory category =CategoryUtil.getCategoryById(fdCategoryId);
		if(category ==null){
			return new ArrayList<Map<String, Object>>();
		}
		long fdStartTime = DateUtil.getDate(0).getTime();
		long fdEndTime = DateUtil.getDate(1).getTime();
		if (date != null) {
			fdStartTime = AttendUtil.getDate(date, 0).getTime();
			fdEndTime = AttendUtil.getDate(date, 1).getTime();
		}
		//过滤是否需要打卡，如果需要则取打卡详情
		boolean isNeedSign = isNeedSign(category, date,fdStartTime,fdEndTime,ele);
		if(isNeedSign) {
			//已增加缓存处理
			return this.getAttendSignTimes(category, date, ele, need);
		}
		return null;
	}

	@Override
	public List getAttendSignTimes(SysOrgElement ele, Date date, boolean need)
			throws Exception {
		String fdCategoryId = this.getAttendCategory(ele,date);
		if (StringUtil.isNull(fdCategoryId)) {
			return new ArrayList<Map<String, Object>>();
		}
		//已增加缓存处理
		SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
		return this.getAttendSignTimes(category, date, ele, need);
	}

	@Override
	public List getAttendSignTimes(SysAttendCategory category, Date date,
			SysOrgElement ele) throws Exception {
		String key=String.format("%s_%s_%s",category.getFdId(),DateUtil.convertDateToString(date,"yyyy-MM-dd"),ele==null?null:ele.getFdId());
		List<Map<String, Object>> signTimeList = (List<Map<String, Object>>) CategoryUtil.USER_WORKTIME_CACHE_MAP.get(key);

		if(CollectionUtils.isNotEmpty(signTimeList) && category.getFdShiftType() != 3){
			List<Map<String, Object>> tempSignTimeList =  new ArrayList();
			//因为缓存 会有model懒加载，这里重新赋值
			for (Map<String, Object> m : signTimeList) {
				//缓存中存在，克隆一个列表。防止外部改动
				Map<String,Object> newMap =new HashMap<String, Object>();
				for (Map.Entry<String, Object> oldMap : m.entrySet()) {
					newMap.put(oldMap.getKey(), oldMap.getValue());
				}
				tempSignTimeList.add(newMap);
			}
			return tempSignTimeList;
		}
		List<SysAttendCategoryWorktime> workTimeList = category.getAvailWorkTime();
		if(workTimeList.size() == 0 && category.getFdShiftType() == 3){
			SysAttendCategoryWorktime worktime1 = new SysAttendCategoryWorktime();
			worktime1.setFdIsAvailable(true);
			worktime1.setFdStartTime(DateUtil.getTimeByNubmer((1000 * 60 * 60 * 9)));
			worktime1.setFdEndTime(DateUtil.getTimeByNubmer((1000 * 60 * 60 * 18)));
			worktime1.setFdOverTimeType(1);
			workTimeList.add(worktime1);
		}

		signTimeList = new ArrayList<Map<String, Object>>();
		List<SysAttendCategoryRule> ruleList = category.getFdRule();
		SysAttendCategoryRule rule = ruleList.get(0);
		Integer fdShiftType = category.getFdShiftType();
		Integer fdSameWTime = category.getFdSameWorkTime();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("categoryId", category.getFdId());
		m.put("fdType", category.getFdType());
		m.put("fdOutside", rule.getFdOutside());
		//是否允许手机打卡
		if((category.getFdCanMap()==null||category.getFdCanMap())||(category.getFdCanWifi()==null||category.getFdCanWifi())){
		    m.put("fdCanMobile", true);
		}else{
		    m.put("fdCanMobile", false);
		}
		m.put("fdLateTime", rule.getFdLateTime());
		m.put("fdLeftTime", rule.getFdLeftTime());
		m.put("fdLimit", rule.getFdLimit());
		m.put("fdLocations", category.getFdLocations());
		m.put("fdBusSetting", category.getBusSetting());
		m.put("fdWifiConfigs", category.getFdWifiConfigs());
		//是否弹性的开启
		m.put("fdIsFlex", category.getFdIsFlex());
		//上下班弹性的时间
		m.put("fdFlexTime", category.getFdFlexTime());
		m.put("fdOsdReviewType", category.getFdOsdReviewType() == null ? 0
				: category.getFdOsdReviewType());
		m.put("fdShiftType", category.getFdShiftType());
		Boolean isEnableKKConfig = isEnableKKConfigThreadLocal.get();
		if(isEnableKKConfig == null){
			isEnableKKConfig = AttendUtil.isEnableKKConfig();
		}
		m.put("fdSecurityMode", Boolean.TRUE.equals(isEnableKKConfig)
				? category.getFdSecurityMode() : "");
		m.put("fdWorkDate", date.getTime());
		//当天是否需要打卡
		boolean isNeedSign = isNeedSign(category,date,date.getTime(),date.getTime(),ele);
		m.put("isNeedSign", isNeedSign);

		SysAttendConfig config = this.sysAttendConfigService
				.getSysAttendConfig();
		com.alibaba.fastjson.JSONObject cfgJson = new com.alibaba.fastjson.JSONObject();
		if (config != null) {
			boolean isEnableKK = isEnableKKConfig == null ? false : isEnableKKConfig.booleanValue();
			cfgJson.put("fdClientLimit",
					isEnableKK ? Boolean.TRUE.equals(config.getFdClientLimit())
							: false);
			cfgJson.put("fdClient", config.getFdClient());
			cfgJson.put("fdDeviceLimit",
					isEnableKK ? Boolean.TRUE.equals(config.getFdDeviceLimit())
							: false);
			cfgJson.put("fdDeviceCount", config.getFdDeviceCount());
			cfgJson.put("fdDeviceExcMode", config.getFdDeviceExcMode());
		}
		m.put("attendCfgJson", cfgJson.toString());
		boolean isAcrossDay =false;
		if ((Integer.valueOf(0).equals(fdShiftType) )
				&& Integer.valueOf(1).equals(fdSameWTime)) {// 一周不同上下班
			Date signDate = new Date();
			if (date != null) {
				signDate = date;
			}
			List<SysAttendCategoryTimesheet> tSheets = category
					.getFdTimeSheets();
			if (tSheets != null && !tSheets.isEmpty()) {
				SysAttendCategoryTimesheet tSheet = getTimeSheet(category,
						signDate);
				tSheet = tSheet == null ? tSheets.get(0) : tSheet;// 周末默认取第一个
				workTimeList = tSheet.getAvailWorkTime();

				m.put("fdStartTime", tSheet.getFdStartTime1());
				m.put("fdEndTime", tSheet.getFdEndTime2());
				isAcrossDay = Integer.valueOf(2).equals(tSheet.getFdEndDay());
				m.put("isAcrossDay", isAcrossDay);
				m.put("fdStartTime2", tSheet.getFdStartTime2());
				m.put("fdEndTime1", tSheet.getFdEndTime1());

				//午休开始结束时间根据 当日、次日的标识改变时间
				//午休开始时间
				Date tempRestStartTime =  AttendUtil.joinYMDandHMS(date,tSheet.getFdRestStartTime());
				tempRestStartTime = Integer.valueOf(2).equals(tSheet.getFdRestStartType())?AttendUtil.addDate(tempRestStartTime,1):tempRestStartTime;
				//午休结束时间
				Date tempRestEndTime = AttendUtil.joinYMDandHMS(date,tSheet.getFdRestEndTime());
				tempRestEndTime = Integer.valueOf(2).equals(tSheet.getFdRestEndType())?AttendUtil.addDate(tempRestEndTime,1):tempRestEndTime;

				m.put("fdRestStartTime",tempRestStartTime);
				m.put("fdRestEndTime", tempRestEndTime);
				m.put("fdRestStartType", tSheet.getFdRestStartType());
				m.put("fdRestEndType", tSheet.getFdRestEndType());
				//总工时
				m.put("fdWorkTimeHour", tSheet.getFdTotalTime());
			}
		} else {
			// 一周相同上下班。考勤组中排班规则的
			m.put("fdStartTime", category.getFdStartTime());
			m.put("fdEndTime", category.getFdEndTime());
			isAcrossDay =Integer.valueOf(2).equals(category.getFdEndDay());
			m.put("isAcrossDay", isAcrossDay);
			m.put("fdStartTime2", category.getFdStartTime2());
			m.put("fdEndTime1", category.getFdEndTime1());

			//午休开始结束时间根据 当日、次日的标识改变时间
			//午休开始时间
			Date tempRestStartTime =  AttendUtil.joinYMDandHMS(date,category.getFdRestStartTime());
			tempRestStartTime = Integer.valueOf(2).equals(category.getFdRestStartType())?AttendUtil.addDate(tempRestStartTime,1):tempRestStartTime;
			//午休结束时间
			Date tempRestEndTime = AttendUtil.joinYMDandHMS(date,category.getFdRestEndTime());
			tempRestEndTime = Integer.valueOf(2).equals(category.getFdRestEndType())?AttendUtil.addDate(tempRestEndTime,1):tempRestEndTime;

			m.put("fdRestStartTime", tempRestStartTime);
			m.put("fdRestEndTime", tempRestEndTime);
			m.put("fdRestStartType", category.getFdRestStartType());
			m.put("fdRestEndType", category.getFdRestEndType());
			//总工时
			m.put("fdWorkTimeHour", category.getFdTotalTime());
		}
		boolean isTimeArea = Integer.valueOf(1).equals(fdShiftType);
		if (isTimeArea && ele !=null) {
			m.remove("fdRestStartTime");
			m.remove("fdRestEndTime");
			// 排班
			com.alibaba.fastjson.JSONObject result = this.sysTimeCountService.getWorkTimes(ele, date);
			workTimeList =convertWorkTimeOfTimeArea(result);
			if (workTimeList.isEmpty()) {
				logger.debug("该用户" + ele.getFdId() + "无法找到相应排班班次信息!日期:" + date);
				return signTimeList;
			}
			m.put("isHoliday", result.getBoolean("isHoliday"));
			// 自定义假期区间
			com.alibaba.fastjson.JSONArray vacations = result.getJSONArray("vacations");
			if (vacations != null && !vacations.isEmpty()) {
				com.alibaba.fastjson.JSONObject vacation = (com.alibaba.fastjson.JSONObject) vacations.get(0);
				long fdStartTime = ((Number) vacation.get("fdStartTime"))
						.longValue();
				long fdEndTime = ((Number) vacation.get("fdEndTime")).longValue();
				int tempRestStartTime =AttendUtil.getHMinutes(DateUtil.getTimeByNubmer(fdStartTime));
				vacation.put("fdStartTime",tempRestStartTime );
				int tempRestEndTime =AttendUtil.getHMinutes(DateUtil.getTimeByNubmer(fdEndTime));
				vacation.put("fdEndTime",tempRestEndTime );

			}
			m.put("vacations", vacations != null ? vacations.toString() : "[]");

			com.alibaba.fastjson.JSONArray workTimes = result.getJSONArray("workTimes");
			if (workTimes != null && workTimes.size() == 1) {
				// 只有一个班次时，才有午休时间
				com.alibaba.fastjson.JSONObject work = workTimes.getJSONObject(0);
				if (work != null && work.containsKey("fdRestStartTime")
						&& work.containsKey("fdRestEndTime")) {
					Number fdRestStartTime = (Number) work.get("fdRestStartTime");
					Number fdRestEndTime = (Number) work.get("fdRestEndTime");
					Integer fdRestEndType = 1;
					//午休结束时间的标识，次日还是当日
					if(work.containsKey("fdRestEndType")){
						fdRestEndType = (Integer) work.get("fdRestEndType");
					}
					Integer fdRestStartType = 1;
					//午休结束时间的标识，次日还是当日
					if(work.containsKey("fdRestStartType")){
						fdRestStartType = (Integer) work.get("fdRestStartType");
					}
					if (fdRestStartTime != null && fdRestEndTime != null) {
						//午休开始结束时间 根据当日、次日的标识 改变时间
						Date tempRestStartTime = AttendUtil.joinYMDandHMS(date,DateUtil.getTimeByNubmer(fdRestStartTime.longValue()));
						tempRestStartTime = Integer.valueOf(2).equals(fdRestStartType)?AttendUtil.addDate(tempRestStartTime,1):tempRestStartTime;

						Date tempRestEndTime = AttendUtil.joinYMDandHMS(date,DateUtil.getTimeByNubmer(fdRestEndTime.longValue()));
						tempRestEndTime = Integer.valueOf(2).equals(fdRestEndType)?AttendUtil.addDate(tempRestEndTime,1):tempRestEndTime;
						m.put("fdRestStartTime", tempRestStartTime);
						m.put("fdRestEndTime", tempRestEndTime);
						m.put("fdRestStartType", fdRestStartType);
						m.put("fdRestEndType", fdRestEndType);
					}
				}
			}
		}
		m.put("endOverTimeType", isAcrossDay?2:1);
		m.put("fdWorkTimeSize", workTimeList.size());

		for (SysAttendCategoryWorktime record : workTimeList) {
			if(record ==null ||  record.getFdEndTime() ==null ||  record.getFdStartTime() ==null ){
				continue;
			}
			Integer workTimeMins = record.getFdEndTime().getHours() * 60
					+ record.getFdEndTime().getMinutes()
					- record.getFdStartTime().getHours() * 60
					- record.getFdStartTime().getMinutes();
			if(Integer.valueOf(2).equals(record.getFdOverTimeType())) {
				Date startTime = AttendUtil.getDateTime(record.getFdStartTime(), 1);
				Date endTime = AttendUtil.getDateTime(record.getFdEndTime(), 2);
				workTimeMins=(int) ((endTime.getTime()-startTime.getTime())/(60*1000));
			}
			//上班
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fdWorkTimeId", record.getFdId());
			// 签到时间点
			if(category.getFdShiftType() == 3){
				map.put("signTime", DateUtil.convertStringToDate("2023-01-01 09:00:00"));
//				map.put("signTime", AttendUtil.removeSecond(date));
			}else{
				map.put("signTime", record.getFdStartTime());
			}
			map.put("fdWorkType",
					AttendConstant.SysAttendMain.FD_WORK_TYPE[0]);
			map.put("workTimeMins", workTimeMins);
			map.put("isTimeArea", isTimeArea ? "true" : "false");
			//上班卡 。最晚打卡时间的跨天标识 默认是1
			map.put("overTimeType", 1);
			map.putAll(m);
			Integer fdEndOverTimeType = record.getFdEndOverTimeType()==null?1:record.getFdEndOverTimeType();
			//为每一个班次设置最早最晚打卡时间、排班类型
			if (isTimeArea && record.getFdBeginTime() !=null && record.getFdOverTime() !=null) {
				map.put("fdStartTime",record.getFdBeginTime());
				map.put("fdEndTime",record.getFdOverTime());
				map.put("endOverTimeType", fdEndOverTimeType);
				//表示排班新配置
				map.put("isTimeAreNew", Boolean.TRUE);
				map.put("isAcrossDay", Integer.valueOf(2).equals(fdEndOverTimeType)?true:false);
			}
			signTimeList.add(map);
			//下班
			map = new HashMap<String, Object>();
			map.put("fdWorkTimeId", record.getFdId());

			if(category.getFdShiftType() == 3){
				long totle = (long)(category.getFdTotalTime() * 1000 * 60 * 60);
				Date signTimeEndTime =AttendUtil.removeSecond(new Date(date.getTime()+totle));
//				map.put("signTime", signTimeEndTime);
				map.put("signTime", DateUtil.convertStringToDate("2023-01-01 18:00:00"));
			}else{
				map.put("signTime", record.getFdEndTime());
			}

			map.put("fdWorkType",
					AttendConstant.SysAttendMain.FD_WORK_TYPE[1]);
			map.put("workTimeMins", workTimeMins);
			map.put("isTimeArea", isTimeArea ? "true" : "false");
			map.put("overTimeType", record.getFdOverTimeType() == null ? 1 : record.getFdOverTimeType());
			map.putAll(m);
			//为每一个班次设置最早最晚打卡时间、排班类型
			if (isTimeArea && record.getFdBeginTime() !=null && record.getFdOverTime() !=null) {
				map.put("fdStartTime",record.getFdBeginTime());
				map.put("fdEndTime",record.getFdOverTime());
				map.put("endOverTimeType", fdEndOverTimeType);
				//表示排班新配置
				map.put("isTimeAreNew",Boolean.TRUE);
				//班次是否支持跨天
				map.put("isAcrossDay", Integer.valueOf(2).equals(fdEndOverTimeType)?true:false);
			}

			signTimeList.add(map);
		}
		
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
		//当天的总工时
		Float standWorkTimeHour =0F;
		if(isTimeArea){
			//排班类型的总工时
			int workMis = SysTimeUtil.getStandWorkTime(ele.getFdId(),date,signTimeList);
			standWorkTimeHour =Float.valueOf(workMis / 60f);
		}

		int workNum = 0;
		int i=0;
		for(Map<String, Object> map : signTimeList) {
			int next = i + 1;
			Object nextSignTime = null, pSignTime = null;
			Object nextOverTimeType=null;
			if (next < signTimeList.size()) {
				Map<String, Object> nMap = signTimeList.get(i + 1);
				nextSignTime = (Date) nMap.get("signTime");
				nextOverTimeType = (Integer) nMap.get("overTimeType");
			}
			if (i - 1 >= 0) {
				Map<String, Object> pMap = signTimeList.get(i - 1);
				pSignTime = (Date) pMap.get("signTime");
			}
			if (i % 2 == 0) {
				workNum++;
			}
			map.put("fdWorkNum", workNum);// 第几个班次
			map.put("nextSignTime", nextSignTime);// 下个打卡时间
			map.put("pSignTime", pSignTime);// 前个打卡时间
			map.put("nextOverTimeType", nextOverTimeType);// 下个打卡时间跨天标识
			//当天的总工时
			if(isTimeArea) {
				map.put("fdWorkTimeHour", standWorkTimeHour);
			}
			i++;
		}


		CategoryUtil.USER_WORKTIME_CACHE_MAP.put(key,signTimeList);
		List<Map<String, Object>> tempSignTimeList =  new ArrayList();
		if(CollectionUtils.isNotEmpty(signTimeList)){
			//因为缓存 会有model懒加载，这里重新赋值
			for (Map<String, Object> tempMap: signTimeList) {
				//缓存中存在，克隆一个列表。防止外部改动
				Map<String,Object> newMap =new HashMap<String, Object>();
				for (Map.Entry<String, Object> oldMap : tempMap.entrySet()) {
					newMap.put(oldMap.getKey(), oldMap.getValue());
				}
				tempSignTimeList.add(newMap);
			}
		}
		return tempSignTimeList;
	}

	@Override
	public void initThreadLocalConfig() {
		Boolean isEnableKKConfig = isEnableKKConfigThreadLocal.get();
		if(isEnableKKConfig == null){
			isEnableKKConfigThreadLocal.set(AttendUtil.isEnableKKConfig());
		}
		Map<String, List> signTimeMap = signTimesThreadLocal.get();
		if(signTimeMap == null){
			signTimesThreadLocal.set(new HashMap<String, List>(5000));
			signTimeMap = signTimesThreadLocal.get();
		}
	}
	
	@Override
	public void releaseThreadLocalConfig() {
		isEnableKKConfigThreadLocal.remove();
		isEnableKKConfigThreadLocal.set(null);
		signTimesThreadLocal.remove();
		signTimesThreadLocal.set(null);
	}
	
	/**
	 * 获取休息日最近一次排班信息
	 * @param category
	 * @param date 排班日期
	 * @param ele
	 * @param workDate 打卡日期
	 * @return
	 * @throws Exception
	 */
	public List getAttendRestSignTimes(SysAttendCategory category, Date date,
			SysOrgElement ele, Date workDate) throws Exception {
		List<Map<String, Object>> signTimeList = getAttendSignTimes(category,date,ele);
		if(CollectionUtils.isNotEmpty(signTimeList)){
			for (Map<String, Object> m :signTimeList) {
				m.put("fdWorkDate", workDate.getTime());
			}
		}
		return signTimeList;
	}

	/**
	 * 获取用户排班信息
	 * @param category
	 * @param date
	 * @param ele
	 * @param needed
	 *            排班制时生效(true时,若当天没有排班则获取最近的排班信息)
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getAttendSignTimes(SysAttendCategory category,
			Date date, SysOrgElement ele, boolean needed) throws Exception {

		List<Map<String, Object>> signTimeList = getAttendSignTimes(category,
				date, ele);
		Integer fdShiftType = category.getFdShiftType();
		boolean isTimeArea = Integer.valueOf(1).equals(fdShiftType);
		if (!isTimeArea) {
			return signTimeList;
		}
		if (!needed) {
			return signTimeList;
		}
		boolean isRest =false;
		if (signTimeList.isEmpty()) {
			//过去一周
			for (int i = 1; i < 7; i++) {
				Date tempDate =AttendUtil.getDate(date, 0 - i);
				signTimeList = getAttendRestSignTimes(category,tempDate, ele, date);
				if (!signTimeList.isEmpty()) {
					break;
				}
			}
			isRest =true;
		}
		if (signTimeList.isEmpty()) {
			for (int i = 1; i < 7; i++) {
				//未来一周
				Date tempDate =AttendUtil.getDate(date, i);
				signTimeList = getAttendRestSignTimes(category, tempDate, ele, date);
				if (!signTimeList.isEmpty()) {
					break;
				}
			}
			isRest =true;
		}
		//排班制，如果排班日期当天没有排班，则找到最近的排班时，当天的最早开始日期为昨天的最晚(昨天跨天的情况)
		//当天最晚为23:59:59
		if(isRest && CollectionUtils.isNotEmpty(signTimeList)){
			//昨天的排班
			Date yesteday =AttendUtil.getDate(date,-1);
			Date startTime =AttendUtil.getDate(date,0);
			Date endDate =AttendUtil.getEndDate(date,0);
			List<Map<String, Object>> yestedaySignList = getAttendSignTimes(category,yesteday, ele);
			if(CollectionUtils.isNotEmpty(yestedaySignList)){
				Map<String, Object> workConfig = yestedaySignList.get(yestedaySignList.size()-1);
				//班次的最晚打卡时间
				Date tempStartTime = (Date) workConfig.get("fdEndTime");
				//下班的最晚打卡时间配置，是今日还是明日
				Integer fdEndOverTimeType = (Integer) workConfig.get("endOverTimeType");
				if (tempStartTime != null) {
					if (Integer.valueOf(2).equals(fdEndOverTimeType)) {
						//如果是次日，则是今日的日期+最晚打卡时间 +1 分钟
						startTime =AttendUtil.addDate(AttendUtil.joinYMDandHMS(date, tempStartTime),Calendar.MINUTE,1);
					}
				}
			}
			int endOverTimeType =1;
			//明天
			Date tomorrow =AttendUtil.getDate(date,1);
			List<Map<String, Object>> tomorrowSignList = getAttendSignTimes(category,tomorrow, ele);
			if(CollectionUtils.isNotEmpty(tomorrowSignList)){
				Map<String, Object> workConfig = tomorrowSignList.get(0);
				//班次的最早打卡时间
				Date tempStartTime = (Date) workConfig.get("fdStartTime");
				//第2天最早打卡时间。减去1分钟
				endDate =AttendUtil.addDate(AttendUtil.joinYMDandHMS(tomorrow, tempStartTime),Calendar.MINUTE,-1);
				//最后的打卡时间，跟明天是同1天。则属于跨天。否则还是当天。主要是23.59分的场景
				if(AttendUtil.getDate(endDate,0).getTime() == tomorrow.getTime()) {
					endOverTimeType = 2;
				}
			} else {
				endOverTimeType =1;
			}
			//休息日不区分多班次，只用一个班次，最早为昨天的最晚或0点，最晚为固定的23.59.59
			List<Map<String, Object>> returnSignTimeList =new ArrayList<>();
			Map<String, Object> goWorkSigin = signTimeList.get(0);
			goWorkSigin.put("fdStartTime",startTime);
			goWorkSigin.put("fdEndTime",endDate);
			//非跨天的标识。休息日没有跨天的概念
			goWorkSigin.put("endOverTimeType",endOverTimeType);
			goWorkSigin.put("overTimeType", 1);
			returnSignTimeList.add(goWorkSigin);

			Map<String, Object> outWorkSigin = signTimeList.get(1);
			outWorkSigin.put("fdStartTime",startTime);
			outWorkSigin.put("fdEndTime",endDate);
			//非跨天的标识
			outWorkSigin.put("endOverTimeType",endOverTimeType);
			outWorkSigin.put("overTimeType", 1);
			returnSignTimeList.add(outWorkSigin);
			return returnSignTimeList;
		}
		return signTimeList;
	}

	/**
	 * 根据人员查找其排班的班次信息
	 * @param element
	 * @param date
	 * @return 返回班次信息对应该天是按1天还是半天的时间统计
	 * @throws Exception
	 */
	@Override
	public Float getWorkTimeAreaTotalDay(
			SysOrgElement element, Date date) throws Exception {
		//缓存某一天
		com.alibaba.fastjson.JSONObject result = this.sysTimeCountService.getWorkTimes(element, date);
		com.alibaba.fastjson.JSONArray workTimes = result.getJSONArray("workTimes");
		//没有排班，则是0，有排班没有配置统计天，默认是1天
		if(CollectionUtils.isNotEmpty(workTimes)){
			com.alibaba.fastjson.JSONObject record =(com.alibaba.fastjson.JSONObject)  workTimes.get(0);
			Object obj = record.get("fdTotalDay");
			if(obj !=null){
				return Float.valueOf(obj.toString());
			}else{
				return 1F;
			}
		}
		return 0F;
	}

	/**
	 * 将得到的排班类型的签到数据，转成对象
	 * @param result
	 * @return
	 * @throws Exception
	 */
	private List<SysAttendCategoryWorktime> convertWorkTimeOfTimeArea( com.alibaba.fastjson.JSONObject result) throws Exception {
		List<SysAttendCategoryWorktime> workTimeList = new ArrayList<SysAttendCategoryWorktime>();
		if(result ==null){
			return workTimeList;
		}
		com.alibaba.fastjson.JSONArray workTimes = result.getJSONArray("workTimes");
		if (workTimes == null || workTimes.isEmpty()) {
			return workTimeList;
		}
		Boolean isHoliday = result.getBoolean("isHoliday");
		if (isHoliday == null || isHoliday.booleanValue()) {
			return workTimeList;
		}
		//自定义假期列表
		com.alibaba.fastjson.JSONArray vacations = result.getJSONArray("vacations");
		if (vacations != null && !vacations.isEmpty()) {
			// 判断某天是否需要上班
			com.alibaba.fastjson.JSONObject vacation = (com.alibaba.fastjson.JSONObject) vacations.get(0);
			int fdStartTime = ((Number) vacation.get("fdStartTime")).intValue();
			int fdEndTime = ((Number) vacation.get("fdEndTime")).intValue();
			// 获取班次最早最晚上下班时间
			List<Integer> signList = new ArrayList<Integer>();
			for(Object workTime : workTimes)
			{
				com.alibaba.fastjson.JSONObject record = (com.alibaba.fastjson.JSONObject) workTime;
				signList.add(((Number) record.get("fdStartTime")).intValue());
				Integer endTime = ((Number) record.get("fdEndTime")).intValue();
				Integer fdOverTimeType=1;//默认当天排班
				if(record.containsKey("fdOverTimeType")) {
					fdOverTimeType = (Integer) record.get("fdOverTimeType");
				}
				if(endTime!=null&&Integer.valueOf(2).equals(fdOverTimeType)) {
					endTime=(int) (endTime+DateUtil.DAY);
				}
				signList.add(endTime);
			}
			Collections.sort(signList);
			int minSignTime = signList.get(0);
			int maxSignTime = signList.get(signList.size() - 1);

			if(fdStartTime >= maxSignTime || fdEndTime<= minSignTime){
				// 所有上班时间点都在休假区间,则不需打卡
				//补班的开始时间，大于等于 班次的最晚打卡时间
				//补班的最晚时间 小于等于 班次的最早打卡时间
				return workTimeList;
			}
//			if (minSignTime >= fdStartTime && minSignTime <= fdEndTime
//					&& maxSignTime >= fdStartTime && maxSignTime <= fdEndTime) {
//				// 所有上班时间点都在休假区间,则不需打卡
//				return workTimeList;
//			}
		}

		for(Object workTime : workTimes)
		{
			com.alibaba.fastjson.JSONObject record = (com.alibaba.fastjson.JSONObject) workTime;
			Number fdStartTime = (Number) record.get("fdStartTime");
			Number fdEndTime = (Number) record.get("fdEndTime");
			//默认当天排班
			Integer fdOverTimeType=1;
			if(record.containsKey("fdOverTimeType")) {
				fdOverTimeType = (Integer) record.get("fdOverTimeType");
			}
			//最早打卡时间
			Number fdBeginTime = (Number) record.get("fdBeginTime");
			Number fdOverTime = (Number) record.get("fdOverTime");
			//默认当天排班
			Integer fdEndOverTimeType=1;
			if(record.containsKey("fdEndOverTimeType")) {
				fdEndOverTimeType = (Integer) record.get("fdEndOverTimeType");
			}
			SysAttendCategoryWorktime wt = new SysAttendCategoryWorktime();
			wt.setFdStartTime(DateUtil.getTimeByNubmer(fdStartTime.longValue()));
			wt.setFdEndTime(DateUtil.getTimeByNubmer(fdEndTime.longValue()));
			wt.setFdIsAvailable(true);
			wt.setFdOverTimeType(fdOverTimeType);
			// 不能作为fdWordId使用,只用于区分同个班次
			wt.setFdId(IDGenerator.generateID());

			if(fdBeginTime !=null && fdOverTime !=null) {
				wt.setFdBeginTime(DateUtil.getTimeByNubmer(fdBeginTime.longValue()));
				wt.setFdOverTime(DateUtil.getTimeByNubmer(fdOverTime.longValue()));
				wt.setFdEndOverTimeType(fdEndOverTimeType);
			}
			workTimeList.add(wt);
		}
		return workTimeList;
	}

	/**
	 * 获取排班制的班次信息,如:上下班时间
	 * 
	 * @param element
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysAttendCategoryWorktime> getWorkTimeOfTimeArea(
			SysOrgElement element, Date date) throws Exception {
		List<SysAttendCategoryWorktime> workTimeList = new ArrayList<SysAttendCategoryWorktime>();
		if(element ==null){
			return workTimeList;
		}
		com.alibaba.fastjson.JSONObject result = this.sysTimeCountService.getWorkTimes(element, date);

		com.alibaba.fastjson.JSONArray workTimes = result.getJSONArray("workTimes");
		if (workTimes == null || workTimes.isEmpty()) {
			return workTimeList;
		}
		Boolean isHoliday = result.getBoolean("isHoliday");
		if (isHoliday == null || isHoliday.booleanValue()) {
			return workTimeList;
		}
		//自定义假期列表
		com.alibaba.fastjson.JSONArray vacations = result.getJSONArray("vacations");
		if (vacations != null && !vacations.isEmpty()) {
			// 判断某天是否需要上班
			com.alibaba.fastjson.JSONObject vacation = (com.alibaba.fastjson.JSONObject) vacations.get(0);
			int fdStartTime = ((Number) vacation.get("fdStartTime")).intValue();
			int fdEndTime = ((Number) vacation.get("fdEndTime")).intValue();
			// 获取班次最早最晚上下班时间
			List<Integer> signList = new ArrayList<Integer>();
			for(Object workTime : workTimes)
			{
				com.alibaba.fastjson.JSONObject record = (com.alibaba.fastjson.JSONObject) workTime;
				signList.add(((Number) record.get("fdStartTime")).intValue());
				Integer endTime = ((Number) record.get("fdEndTime")).intValue();
				Integer fdOverTimeType=1;//默认当天排班
				if(record.containsKey("fdOverTimeType")) {
					fdOverTimeType = (Integer) record.get("fdOverTimeType");
				}
				if(endTime!=null&&Integer.valueOf(2).equals(fdOverTimeType)) {
					endTime=(int) (endTime+DateUtil.DAY);
				}
				signList.add(endTime);
			}
			Collections.sort(signList);
			int minSignTime = signList.get(0);
			int maxSignTime = signList.get(signList.size() - 1);

			if(fdStartTime >= maxSignTime || fdEndTime<= minSignTime){
				// 所有上班时间点都在休假区间,则不需打卡
				//补班的开始时间，大于等于 班次的最晚打卡时间
				//补班的最晚时间 小于等于 班次的最早打卡时间
				return workTimeList;
			}
//			if (minSignTime >= fdStartTime && minSignTime <= fdEndTime
//					&& maxSignTime >= fdStartTime && maxSignTime <= fdEndTime) {
//				// 所有上班时间点都在休假区间,则不需打卡
//				return workTimeList;
//			}
		}

		for(Object workTime : workTimes)
		{
			com.alibaba.fastjson.JSONObject record = (com.alibaba.fastjson.JSONObject) workTime;
			Number fdStartTime = (Number) record.get("fdStartTime");
			Number fdEndTime = (Number) record.get("fdEndTime");
			//默认当天排班
			Integer fdOverTimeType=1;
			if(record.containsKey("fdOverTimeType")) {
				fdOverTimeType = (Integer) record.get("fdOverTimeType");
			}
			//最早打卡时间
			Number fdBeginTime = (Number) record.get("fdBeginTime");
			Number fdOverTime = (Number) record.get("fdOverTime");
			//默认当天排班
			Integer fdEndOverTimeType=1;
			if(record.containsKey("fdEndOverTimeType")) {
				fdEndOverTimeType = (Integer) record.get("fdEndOverTimeType");
			}
			SysAttendCategoryWorktime wt = new SysAttendCategoryWorktime();
			wt.setFdStartTime(DateUtil.getTimeByNubmer(fdStartTime.longValue()));
			wt.setFdEndTime(DateUtil.getTimeByNubmer(fdEndTime.longValue()));
			wt.setFdIsAvailable(true);
			wt.setFdOverTimeType(fdOverTimeType);
			// 不能作为fdWordId使用,只用于区分同个班次
			wt.setFdId(IDGenerator.generateID());

			if(fdBeginTime !=null && fdOverTime !=null) {
				wt.setFdBeginTime(DateUtil.getTimeByNubmer(fdBeginTime.longValue()));
				wt.setFdOverTime(DateUtil.getTimeByNubmer(fdOverTime.longValue()));
				wt.setFdEndOverTimeType(fdEndOverTimeType);
			}
			workTimeList.add(wt);
		}
		return workTimeList;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		String search = requestInfo.getParameter("search");
		String where = "sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1";
		if (StringUtil.isNotNull(search)) {
			where = where + " and sysAttendCategory.fdName like '%" + search
					+ "%'";
		}
		List<SysAttendCategory> cateList = findList(where,
				"sysAttendCategory.docCreateTime desc");
		HashMap<String, String> node = null;
		for (SysAttendCategory category : cateList) {
			node = new HashMap<String, String>();
			node.put("id", category.getFdId());
			node.put("name", category.getFdName());
			rtnList.add(node);
		}
		return rtnList;
	}

	private SysAttendCategoryTimesheet getTimeSheet(SysAttendCategory category,
			Date date) {
		if (category == null || date == null) {
			return null;
		}
		List<SysAttendCategoryTimesheet> tSheets = category
				.getFdTimeSheets();
		for (SysAttendCategoryTimesheet tSheet : tSheets) {
			if (StringUtil.isNotNull(tSheet.getFdWeek())
					&& tSheet.getFdWeek()
							.indexOf(AttendUtil.getWeek(date)
									+ "") > -1) {
				return tSheet;
			}
		}
		return null;
	}

	/**
	 * 更新某个考勤组的当日的人员打卡记录
	 * @param categoryId 考勤组ID(原始ID)
	 * @param orgIds 人员列表
	 * @param alterPerson 最后修改人员
	 * @throws Exception
	 */
	@Override
	public void updateAttendMainRecord(String categoryId, List<String> orgIds,SysOrgPerson alterPerson)
			throws Exception {
		try {
			//获取考勤组的详情
			SysAttendCategory cate = (SysAttendCategory) findByPrimaryKey(categoryId);
			if (cate != null) {
				if (AttendConstant.FDTYPE_ATTEND != cate.getFdType().intValue()) {
					return;
				}
				if (cate.getFdStatus() == 1) {
					//指定人员为空，则直接找当前考勤组下所有的人员
					if(CollectionUtils.isEmpty(orgIds) && StringUtil.isNotNull(categoryId)) {
						orgIds= this.getAttendPersonIds(categoryId,new Date());
					}
					if(CollectionUtils.isNotEmpty(orgIds)) {
						logger.warn("编辑考勤组-执行重新计算生成当日所有的考勤数据");
						//执行重新计算生成当日所有的考勤数据
						List<Date> dateList =new ArrayList<>();
						dateList.add(AttendUtil.getDate(new Date(),0));
						AttendStatThread task = new AttendStatThread();
						task.setDateList(dateList);
						task.setOrgList(orgIds);
						task.setFdMethod("restat");
						task.setFdIsCalMissed("false");
						task.setFdOperateType("create");
						task.setLogId(null);
						AttendThreadPoolManager manager = AttendThreadPoolManager
								.getInstance();
						if (!manager.isStarted()) {
							manager.start();
						}
						manager.submit(task);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注意:该方法不通用,workTimesList格式过 根据打卡时间,判断用户打卡记录所属班次(-1表示无效)
	 * 
	 * @param workTimesList
	 *            某天的打卡班次列表
	 * @param docCreateTime
	 *            打卡时间
	 * @return
	 */
	private int getWorkTimeCount(List<Map<String, Object>> workTimesList,
			Date docCreateTime) {
		int workCount = workTimesList.size() / 2;
		for (int i = 0; i < workCount; i++) {
			Map<String, Object> startMap = workTimesList.get(2 * i); // 上班
			Map<String, Object> endMap = workTimesList.get(2 * i + 1); // 下班

			Map<String, Object> pEndMap = null; // 上个班次下班时间
			Map<String, Object> nStartMap = null; // 下个班次上班时间

			Date startSignTime = (Date) startMap.get("signTime");
			Date endSignTime = (Date) endMap.get("signTime");
			Date pEndSignTime = null;
			Date nStartSignTime = null;

			if ((i - 1) >= 0) {// 是否存在上个班次
				pEndMap = workTimesList.get(2 * i - 1);
				pEndSignTime = (Date) pEndMap.get("signTime");
			}
			if ((i + 1) < workCount) {// 是否存在下个班次
				nStartMap = workTimesList.get(2 * (i + 1));
				nStartSignTime = (Date) nStartMap.get("signTime");
			}

			if (nStartSignTime != null
					&& AttendUtil.getHMinutes(docCreateTime) < AttendUtil
							.getHMinutes(nStartSignTime)) {
				if (!endMap.containsKey("isSigned")) {// 该班次打过卡,则会增加该标识
					return i;
				}
			}
			if (nStartSignTime == null) {
				return i;
			}

		}
		return -1;
	}

	private void resetAttendMain(SysAttendMain main, int fdStatus,
			SysAttendCategory category,SysAttendHisCategory hisCategory, Map workTime, int fdWorkType) throws Exception {
		List tempList=new ArrayList();
		tempList.add(category);
		com.alibaba.fastjson.JSONArray datas = this.filterAttendCategory(tempList, main.getDocCreateTime(), true, main.getDocCreator());
		if (!datas.isEmpty()) {
			//如果当天是休息日则打卡状态都是正常
			if (main.getFdStatus() == 1 || main.getFdStatus() == 2
					|| main.getFdStatus() == 3) {
				main.setFdStatus(fdStatus);
			}
		}else{
			//休息日标识正常
			main.setFdStatus(1);
		}
		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		main.setFdHisCategory(hisCategory);
		main.setWorkTime(getWorkTime(category, (String) workTime.get("fdWorkTimeId")));
		main.setFdWorkType(fdWorkType);
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			main.setFdWorkKey(fdWorkId);
		}
		Date signTime = (Date) workTime.get("signTime");
		Date fdBaseWorkTime = AttendUtil.joinYMDandHMS(main.getFdWorkDate(),
				signTime);
		main.setFdBaseWorkTime(fdBaseWorkTime);
	}

	/**
	 * 最晚正常上班打卡时间
	 * @param map
	 * @return 根据配置返回标准上班打卡时间的，最晚分钟数。
	 */
	@Override
	public int getShouldOnWorkTime(Map map) {
		//迟到时间
		Integer fdLateTime = (Integer) map.get("fdLateTime");
		//是否弹性上班
		Boolean fdIsFlex = (Boolean) map.get("fdIsFlex");
		//允许的弹性时间
		Integer fdFlexTime = (Integer) map.get("fdFlexTime");
		//打卡时间
		Date signTime = (Date) map.get("signTime");

		fdLateTime = fdLateTime == null ? 0 : fdLateTime;
		fdIsFlex = fdIsFlex == null ? false : fdIsFlex.booleanValue();
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;
		//标准打卡时间的分钟数
		int signTimeMin = signTime.getHours() * 60 + signTime.getMinutes();
		int shouldSignTime = signTimeMin;
		//弹性多少时间。
		if (fdIsFlex) {
			shouldSignTime = shouldSignTime + fdFlexTime;
		} else {
			//允许迟到多久。分钟数
			shouldSignTime = shouldSignTime + fdLateTime;
		}
		//根据配置返回标准上班打卡时间的，最晚分钟数。
		return shouldSignTime;
	}

	/**
	 * 最晚正常上班打卡时间
	 * @param map 班次信息
	 * @param signTime 打卡时间(合并日期以后 直接增加分钟数，没合并日期获取结果后再合并。同理)
	 * @return 返回弹性以后的上班时间
	 */
	@Override
	public Date getShouldOnWorkTime(Map map,Date signTime) {
		//是否弹性上班
		Boolean fdIsFlex = (Boolean) map.get("fdIsFlex");
		//允许的弹性时间
		Integer fdFlexTime = (Integer) map.get("fdFlexTime");
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;
		if(Boolean.TRUE.equals(fdIsFlex)){
			return AttendUtil.addDate(signTime,Calendar.MINUTE,fdFlexTime);
		}
		return signTime;
	}
	/**
	 * 最早正常下班打卡时间
	 * @param map 班次信息
	 * @param signTime 打卡时间(合并日期以后 直接增加分钟数，没合并日期获取结果后再合并。同理)
	 * @return 返回弹性以后的下班时间
	 */
	@Override
	public Date getShouldOffWorkTime(Map map,Date signTime) {
		//是否弹性上班
		Boolean fdIsFlex = (Boolean) map.get("fdIsFlex");
		//允许的弹性时间
		Integer fdFlexTime = (Integer) map.get("fdFlexTime");
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;
		if(Boolean.TRUE.equals(fdIsFlex)){
			return AttendUtil.addDate(signTime,Calendar.MINUTE,-fdFlexTime);
		}
		return signTime;
	}

	/**
	 * 最早正常下班打卡时间
	 * 
	 * @param pSignTime 同个班次上班用户打卡时间
	 * @param map 班次相关信息
	 * @return
	 */
	@Override
	public int getShouldOffWorkTime(Date pSignTime, Map map) {

		//早退时间
		Integer fdLeftTime = (Integer) map.get("fdLeftTime");
		//是否弹性上班
		Boolean fdIsFlex = (Boolean) map.get("fdIsFlex");
		//允许的弹性上班时间
		Integer fdFlexTime = (Integer) map.get("fdFlexTime");
		//打卡时间
		Date signTime = (Date) map.get("signTime");
		//当前打卡的上个标准打卡时间
		Date pSignTime1 = map.containsKey("pSignTime")?(Date) map.get("pSignTime"):signTime;
		//是否为次日打卡
		Integer fdOverTimeType = map.get("overTimeType") == null ? 1 : (Integer) map.get("overTimeType");

		fdLeftTime = fdLeftTime == null ? 0 : fdLeftTime;
		fdIsFlex = fdIsFlex == null ? false : fdIsFlex.booleanValue();
		fdFlexTime = fdFlexTime == null ? 0 : fdFlexTime;
		//下班打卡时间的分钟数
		int signTimeMin = signTime.getHours() * 60 + signTime.getMinutes();
		int shouldSignTime = 0;
		if (fdIsFlex) {
			// pSignTimeMin 实际上班打卡时间。pMin 提前打卡分钟数 --goWorkSignTimeMin 标准上班打卡时间
			int pSignTimeMin = 0, pMin = 0,goWorkSignTimeMin=0;
			if (pSignTime != null) {
				//上班的打卡时间分钟数
				pSignTimeMin = pSignTime.getHours() * 60 + pSignTime.getMinutes();
				//标准打卡时间。
				if(pSignTime1 !=null) {
					goWorkSignTimeMin = pSignTime1.getHours() * 60 + pSignTime1.getMinutes();
				}
				//标准打卡时间 - 实际打卡时间 = 超出的分钟数。

				pMin = goWorkSignTimeMin - pSignTimeMin;
				if(pMin > 0){
					//表示早到、超过早到的弹性时间。那么用最大的时间
					//浮动打卡修改为 基准打卡之后,禁用浮动之前
					pMin = 0;
					/*if(pMin > fdFlexTime){
						pMin = fdFlexTime;
					}*/
				}else{
					//迟到时间超过弹性时间。则就是标准下班时间
					if(Math.abs(pMin) > fdFlexTime){
						//迟到的时间，超过弹性时间。则算是标准下班时间
						pMin = 0;
					}
				}
			}
			//实际要求下班打卡时间，减去超出时间。。。 负 负 得正。
			shouldSignTime = signTimeMin - pMin;
		} else {
			//标准下班打卡时间 - 允许早退的时间。则是正常范围内的打卡时间
			shouldSignTime = signTimeMin - fdLeftTime;
		}
		// 如果是跨天排班 时间加上24小时
		if (fdOverTimeType == 2) {
			shouldSignTime = shouldSignTime + 24 * 60;
		}
		return shouldSignTime;
	}

	private SysAttendMain getSignRecord(List<SysAttendMain> mainList,
			Map workTime) {
		if (mainList == null || mainList.isEmpty()) {
			return null;
		}
		String fdWorkId = (String) workTime.get("fdWorkTimeId");
		Integer fdWorkType = (Integer) workTime.get("fdWorkType");

		for (SysAttendMain main : mainList) {
			SysAttendCategoryWorktime work = main.getWorkTime();
			if (fdWorkId.equals(work.getFdId())
					&& fdWorkType.equals(main.getFdWorkType())) {
				return main;
			}
		}
		return null;
	}


	/**
	 * 获取用户当天考勤记录(过滤跨天的打卡记录)
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	private Map<String,List<SysAttendMain>> getUserAttendMain(List<String> orgList) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sb = new StringBuffer();
		sb.append(HQLUtil.buildLogicIN("sysAttendMain.docCreator.fdId", orgList));
		sb.append(" and sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime");
		sb.append(" and sysAttendMain.fdHisCategory.fdType=:fdType and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross=0)");
		sb.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		hqlInfo.setWhereBlock(sb.toString());
		hqlInfo.setParameter("beginTime",
				AttendUtil.getDate(new Date(), 0));
		hqlInfo.setParameter("endTime",
				AttendUtil.getDate(new Date(), 1));
		hqlInfo.setParameter("fdType", 1);
		hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
		List<SysAttendMain> resultList = this.sysAttendMainDao.findList(hqlInfo);
		Map<String, List<SysAttendMain>> userMap = new HashMap<String, List<SysAttendMain>>();
		for (SysAttendMain main : resultList) {
			String docCreatorId = main.getDocCreator().getFdId();
			if (!userMap.containsKey(docCreatorId)) {
				userMap.put(docCreatorId, new ArrayList<SysAttendMain>());
			}
			List<SysAttendMain> recordList = userMap.get(docCreatorId);
			recordList.add(main);
		}
		return userMap;
	}

	@Override
	public JSONArray getHolidayPatchDay(String categoryId) throws Exception {
		JSONArray jsonArr = new JSONArray();
		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category = CategoryUtil.getCategoryById(categoryId);
			if(category ==null) {
				category = (SysAttendCategory) findByPrimaryKey(categoryId);
			}
			if (category != null
					&& Integer.valueOf(1).equals(category.getFdType())
					&& Integer.valueOf(1).equals(category.getFdStatus())) {
				// 排班节假日及补班
				if (Integer.valueOf(1).equals(category.getFdShiftType())) {
					jsonArr = getTimeAreaHolidayPatchDay();
					return jsonArr;
				}
				SysTimeHoliday holiday = category.getFdHoliday();
				if (holiday != null) {
					//获取假期明细
					//转换Json的时候 未存储假期详情。这里直接新读取
					holiday = (SysTimeHoliday) sysTimeHolidayService.findByPrimaryKey(holiday.getFdId());

					List hDetailList = holiday.getFdHolidayDetailList();
					// 节假日
					if (hDetailList != null && !hDetailList.isEmpty()) {
						for (int i = 0; i < hDetailList.size(); i++) {
							SysTimeHolidayDetail detail = (SysTimeHolidayDetail) hDetailList
									.get(i);
							Date fdStartDay = detail.getFdStartDay();
							Date fdEndDay = detail.getFdEndDay();
							if (fdStartDay != null && fdEndDay != null
									&& fdEndDay.compareTo(fdStartDay) >= 0) {
								boolean flag = true;
								Calendar ca = Calendar.getInstance();
								ca.setTime(fdStartDay);
								while (flag) {
									JSONObject json = new JSONObject();
									json.accumulate("date",
											DateUtil.convertDateToString(
													ca.getTime(),
													DateUtil.TYPE_DATE, null));
									json.accumulate("type", "1");
									jsonArr.add(json);
									if (AttendUtil.isSameDate(ca.getTime(),
											fdEndDay)) {
										flag = false;
									} else {
										ca.add(Calendar.DATE, 1);
									}
								}
							}
						}
						// 补班
						List<SysTimeHolidayPach> patchList = this.sysTimeHolidayService
								.getHolidayPachs(holiday.getFdId());
						// 添加日志信息
						if (UserOperHelper.allowLogOper("getHolidayPatchDay",
								"com.landray.kmss.sys.attend.model.SysAttendMain")) {
							UserOperContentHelper.putFinds(patchList);
						}
						if (!patchList.isEmpty()) {
							for (SysTimeHolidayPach patch : patchList) {
								if (patch.getFdPachTime() != null) {
									JSONObject json = new JSONObject();
									json.accumulate("date",
											DateUtil.convertDateToString(
													patch.getFdPachTime(),
													DateUtil.TYPE_DATE, null));
									json.accumulate("type", "2");
									jsonArr.add(json);
								}
							}
						}
					}
				}
			}
		}
		return jsonArr;
	}

	private JSONArray getTimeAreaHolidayPatchDay() throws Exception {
		JSONArray jsons = this.sysTimeCountService
				.getHolidayPachDay(UserUtil.getUser().getFdId());
		return jsons;
	}

	@Override
	public List getTimeAreaAttendPersonIds(List<SysOrgElement> areaMembers,List<String> cateIds,Date date)
			throws Exception {
		List<String> orgIds = new ArrayList<String>();
		if (areaMembers == null) {
			return orgIds;
		}
		List<String> targetIds = AttendPersonUtil.expandToPersonIds(areaMembers);
		List<List> groupList = AttendUtil.splitList(targetIds, 1000);
		for (int i = 0; i < groupList.size(); i++) {
			List<String> tmpList = groupList.get(i);
			List<SysOrgElement> targets = AttendPersonUtil.getSysOrgElementById(tmpList);
			if(CollectionUtils.isNotEmpty(cateIds)) {
				List<String> userIds = getAttendPersonIds(cateIds, date, true);
				if(CollectionUtils.isNotEmpty(userIds)){
					for (SysOrgElement ele : targets) {
						if (userIds.contains(ele.getFdId())) {
							orgIds.add(ele.getFdId());
						}
					}
				}
			}
			/*for (SysOrgElement ele : targets) {
				//根据人员日期，和考勤组判断是否存在
				String cateId = this.getAttendCategory(ele,date);
				if (cateIds.contains(cateId)) {
					orgIds.add(ele.getFdId());
				}
			}*/
		}

		Set<String> setIds = new HashSet<String>();
		setIds.addAll(orgIds);
		orgIds.clear();
		orgIds.addAll(setIds);
		return orgIds;
	}

	@Override
	public List getAttendPersonIds(String categoryId,Date date)
			throws Exception {
		List<String> orgIds = new ArrayList<String>();
		if (StringUtil.isNull(categoryId)) {
			return orgIds;
		}
		List<String> targetIds =getAttendOrgElementIdList(categoryId,date,CategoryUtil.CATEGORY_FD_TYPE_TRUE,false);
		if(CollectionUtils.isNotEmpty(targetIds)){
			orgIds.addAll(targetIds);
		}
		Set<String> setIds = new HashSet<String>();
		setIds.addAll(orgIds);
		orgIds.clear();
		orgIds.addAll(setIds);
		return orgIds;
	}

	@Override
	public List<SysOrgElement> getAttendPersons(String categoryId,Date date) throws Exception {
		List<SysOrgElement> orgList = new ArrayList<SysOrgElement>();
		if (StringUtil.isNull(categoryId)) {
			return orgList;
		}
		SysAttendHisCategory hisCategory =CategoryUtil.getHisCategoryById(categoryId);
		if(hisCategory == null){
			hisCategory =getSysAttendHisCategoryService().getLastVersionFdId(categoryId);
		}
		if(hisCategory ==null) {
			return orgList;
		}

		List<SysOrgElement> targetIds = getAttendOrgElementList(hisCategory.getFdId(), date, CategoryUtil.CATEGORY_FD_TYPE_TRUE);
		if (CollectionUtils.isNotEmpty(targetIds)) {
			orgList.addAll(targetIds);
		}
		Set<SysOrgElement> setIds = new HashSet<SysOrgElement>();
		setIds.addAll(orgList);
		orgList.clear();
		orgList.addAll(setIds);

		return orgList;
	}
	/**
	 * 所有考勤组的人员
	 * @param date 指定日期
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> getAttendPersonIds(Date date) throws Exception {
		List<String> orgIds = new ArrayList<String>();
		Set<String> cateList =  getSysAttendHisCategoryService().getAllCategorys(date,CategoryUtil.CATEGORY_FD_TYPE_TRUE);
		if (!cateList.isEmpty()) {
			for (String category : cateList) {
				List<String> targets = getAttendOrgElementIdList(category,date,CategoryUtil.CATEGORY_FD_TYPE_TRUE,false);
				if(CollectionUtils.isNotEmpty(targets)){
					orgIds.addAll(targets);
				}
			}
		}
		Set<String> setIds = new HashSet<String>();
		setIds.addAll(orgIds);
		orgIds.clear();
		orgIds.addAll(setIds);
		return orgIds;
	}

	/**
	 * 根据原始考勤ID 获取对应日期的所有考勤人员
	 * @param categoryIds 原始考勤组
	 * @param beginDate 开始日期
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> getAttendPersonIds(List<String> categoryIds,Date beginDate,boolean isOld) throws Exception {
		List<String> users = new ArrayList<String>();
		for (String id:categoryIds) {
			List<String> targetIds =getAttendOrgElementIdList(id,beginDate,CategoryUtil.CATEGORY_FD_TYPE_TRUE,isOld);
			if(CollectionUtils.isNotEmpty(targetIds)){
				users.addAll(targetIds);
			}
		}
		return users;
	}

	@Override
	public SysAttendCategoryWorktime getWorkTimeByRecord(List workTimesList,
			Date fdSignedTime, Integer fdWorkType) throws Exception {
		if (workTimesList == null || workTimesList.isEmpty()) {
			return null;
		}
		SysAttendCategoryWorktime workTime = new SysAttendCategoryWorktime();
		int workCount = workTimesList.size() / 2;
		for (int i = 0; i < workCount; i++) {
			Map<String, Object> startMap = (Map<String, Object>) workTimesList
					.get(2 * i); // 上班
			Map<String, Object> endMap = (Map<String, Object>) workTimesList
					.get(2 * i + 1); // 下班
			Date startSignTime = (Date) startMap.get("signTime");
			Date endSignTime = (Date) endMap.get("signTime");
			Date workDate = new Date((Long) startMap.get("fdWorkDate"));
			Integer fdOverTimeType = (Integer) endMap.get("overTimeType");

			startSignTime = AttendUtil.joinYMDandHMS(workDate,
					startSignTime);
			endSignTime = AttendUtil.joinYMDandHMS(workDate, endSignTime);
			// 跨天排班最后打卡时间加一天
			if (fdOverTimeType == 2) {
				endSignTime = AttendUtil.addDate(endSignTime, 1);
			}
			if (Integer.valueOf(0).equals(fdWorkType)) {
				if (endSignTime.after(fdSignedTime)) {
					workTime.setFdStartTime((Date) startMap.get("signTime"));
					workTime.setFdEndTime((Date) endMap.get("signTime"));
					workTime.setFdOverTimeType(fdOverTimeType);
					return workTime;
				}
			} else {
				if ((i + 1) < workCount) {// 是否存在下班次
					Map<String, Object> nStartMap = (Map<String, Object>) workTimesList
							.get(2 * (i + 1));
					Date nStartSignTime = (Date) nStartMap.get("signTime");
					nStartSignTime = AttendUtil.joinYMDandHMS(workDate,
							nStartSignTime);
					if (fdSignedTime.after(startSignTime)
							&& fdSignedTime.before(nStartSignTime)) {
						workTime.setFdStartTime(
								(Date) startMap.get("signTime"));
						workTime.setFdEndTime((Date) endMap.get("signTime"));
						workTime.setFdOverTimeType(fdOverTimeType);
						return workTime;
					}
				} else {
					if (fdSignedTime.after(startSignTime)) {
						workTime.setFdStartTime(
								(Date) startMap.get("signTime"));
						workTime.setFdEndTime((Date) endMap.get("signTime"));
						workTime.setFdOverTimeType(fdOverTimeType);
						return workTime;
					}
				}
			}
		}
		return null;
	}

	@Override
	public SysAttendCategoryWorktime getWorkTime(SysAttendCategory category,
			String fdWorkId) {
		if (Integer.valueOf(1).equals(category.getFdShiftType())) {
			return null;
		}
		List<SysAttendCategoryWorktime> workTimes = category.getAllWorkTime();
		for (SysAttendCategoryWorktime work : workTimes) {
			if (work.getFdId().equals(fdWorkId)) {
				return work;
			}
		}
		return null;
	}

	/**
	 * 渲染用户排班班次信息
	 * 
	 * @param signTimeConfigurations
	 *            用户考勤班次配置
	 * @param userSignedWorkRecords
	 *            用户数据库中已有的考勤数据
	 */
	@Override
	public void doWorkTimesRender(List<Map<String, Object>> signTimeConfigurations,
			List<?> userSignedWorkRecords) {
		//如果为非排班制的配置信息，则不处理
		if (!signTimeConfigurations.isEmpty()) {
			Map<String, Object> map = signTimeConfigurations.get(0);
			Integer fdShiftType = (Integer) map.get("fdShiftType");
			if (!Integer.valueOf(1).equals(fdShiftType)) {
				return;
			}
		}
		if (userSignedWorkRecords == null || userSignedWorkRecords.isEmpty()) {
			return;
		}
		for (int k = 0; k < userSignedWorkRecords.size(); k++) {
			Object obj = userSignedWorkRecords.get(k);
			Integer fdWorkType = 0;
			String fdWorkKey = "";
			Timestamp fdSignedTime = null;
			Boolean fdIsAcross = false;// 是否跨天

			if (obj instanceof SysAttendMain) {
				SysAttendMain main = (SysAttendMain) obj;
				//上下班类型
				fdWorkType = main.getFdWorkType();
				//排班班次标识 同班次标识相同
				fdWorkKey = main.getFdWorkKey();
				//考勤时间
				fdSignedTime = new Timestamp(main.getDocCreateTime().getTime());
				//是否为跨天打卡的数据
				fdIsAcross = main.getFdIsAcross();
			} else {
				Map<String, Object> record = (Map<String, Object>) obj;
				//上下班类型
				fdWorkType = (Integer) record.get("fdWorkType");
				//排班班次标识 同班次标识相同
				fdWorkKey = (String) record.get("fdWorkKey");
				//考勤时间
				Object docCreateTime = record.get("docCreateTime");
				if (docCreateTime instanceof Timestamp) {
					fdSignedTime = (Timestamp) docCreateTime;
				} else {
					fdSignedTime = new Timestamp((Long) docCreateTime);
				}
				//是否为跨天打卡的数据
				fdIsAcross = (Boolean) record.get("fdIsAcross");
			}
			//考勤所属天的计算
			Date workDate = AttendUtil.getDate(fdSignedTime, 0);
			if (Boolean.TRUE.equals(fdIsAcross)) {
				workDate = AttendUtil.getDate(fdSignedTime, -1);
			}

			int workCount = signTimeConfigurations.size() / 2;
			for (int i = 0; i < workCount; i++) {
				Map<String, Object> startWorkTimeConfiguration = signTimeConfigurations.get(2 * i); // 上班时间配置
				Map<String, Object> offWorkTimeConfiguration = signTimeConfigurations.get(2 * i + 1); // 下班时间配置
				//是否有下班最晚打卡时间的标识
				//判断时间是否在排班的最早最晚打卡时间范围内
				Boolean isTimeAreNew = (Boolean) startWorkTimeConfiguration.get("isTimeAreNew");
				fdWorkKey = StringUtil.isNotNull(fdWorkKey) ? fdWorkKey : (String) startWorkTimeConfiguration.get("fdWorkTimeId");
				if(Boolean.TRUE.equals(isTimeAreNew) && startWorkTimeConfiguration.containsKey("endOverTimeType")) {
					//新版本的话，以排班实时的 班次ID来计算
//					fdWorkKey = (String)startWorkTimeConfiguration.get("fdWorkTimeId");
					//上班班次的最早打卡时间
					Date beginTime = (Date) startWorkTimeConfiguration.get("fdStartTime");
					if(beginTime !=null) {
						beginTime = AttendUtil.joinYMDandHMS(workDate, beginTime);
					}
					//上班班次的最晚打卡时间
					Date overTime = (Date) startWorkTimeConfiguration.get("fdEndTime");
					//下班的配置，是今日还是明日
					Integer fdEndOverTimeType=(Integer)offWorkTimeConfiguration.get("endOverTimeType");
					if(overTime !=null) {
						if(Integer.valueOf(2).equals(fdEndOverTimeType)){
							//如果是次日，则加一
							overTime = AttendUtil.joinYMDandHMS(AttendUtil.getDate(workDate, 1), overTime);
						}else{
							overTime = AttendUtil.joinYMDandHMS(workDate, overTime);
						}
					}
					//当前打卡时间在此范围内。则表示是该班次
					if(beginTime.getTime() <= fdSignedTime.getTime() && fdSignedTime.getTime() <= overTime.getTime()){
						//在同一个最早最晚打卡时间，则认定为同一个班次
						startWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);
						offWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);
						break;
					}
				}else {
					//如果配置的时间为空，则兼容历史数据
					//设置的上班打卡时间配置
					Date startSignTime = (Date) startWorkTimeConfiguration.get("signTime");
					//设置的下班打卡时间配置
					Date endSignTime = (Date) offWorkTimeConfiguration.get("signTime");
					//是否为次日打卡时间
					Integer fdOverTimeType=(Integer)offWorkTimeConfiguration.get("overTimeType");
					startSignTime = AttendUtil.joinYMDandHMS(workDate, startSignTime);
					endSignTime = AttendUtil.joinYMDandHMS(Integer.valueOf(2).equals(fdOverTimeType)?AttendUtil.getDate(workDate, 1): workDate, endSignTime);
					//上班时间
					if (Integer.valueOf(0).equals(fdWorkType)) {
						if (endSignTime.after(fdSignedTime)) {
							startWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);// 同班次时fdWorkKey相同
							offWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);
							break;
						}
					} else {
						if ((i + 1) < workCount) {// 是否存在下班次
							Map<String, Object> nStartMap = signTimeConfigurations
									.get(2 * (i + 1));
							Date nStartSignTime = (Date) nStartMap.get("signTime");
							nStartSignTime = AttendUtil.joinYMDandHMS(workDate,
									nStartSignTime);
							if (fdSignedTime.after(startSignTime)
									&& nStartSignTime.after(fdSignedTime)) {
								startWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);// 同班次时fdWorkKey相同
								offWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);
								break;
							}
						} else {
							if (fdSignedTime.after(startSignTime)) {
								startWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);// 同班次时fdWorkKey相同
								offWorkTimeConfiguration.put("fdWorkTimeId", fdWorkKey);
								break;
							}
						}
					}
				}
			}
		}

	}

	/**
	 * 判断是否是同一个班次
	 * @param workTimeMap 班次配置信息
	 * @param fdWorkId 打卡记录中的班次id
	 * @param fdWorkType 班次类型，0 上班 1下班
	 * @param fdWorkKey 配置中的班次id
	 * @return
	 */
	@Override
	public boolean isSameWorkTime(Map<String, Object> workTimeMap,
			String fdWorkId, Integer fdWorkType, String fdWorkKey) {
		String workTimeId = (String) workTimeMap.get("fdWorkTimeId");
		Integer workType = (Integer) workTimeMap.get("fdWorkType");
		Integer shiftType = (Integer) workTimeMap.get("fdShiftType");

		if (StringUtil.isNull(workTimeId) || workType == null) {
			logger.warn("isSameWorkTime -->workTimeId:" + workTimeId
					+ ";workType:" + workType + ";fdWorkKey:" + fdWorkKey);
			return false;
		}

		if (Integer.valueOf(1).equals(shiftType)) {
			if (workTimeId.equals(fdWorkKey) && workType.equals(fdWorkType)) {
				return true;
			}
			return false;
		}
		if (workTimeId.equals(fdWorkId) && workType.equals(fdWorkType)) {
			return true;
		}
		return false;
	}

	private SysOrgElement filterAttendPerson(List<SysOrgElement> eleList,
			String docCreatorId) {
		if (eleList == null || eleList.isEmpty()) {
			return null;
		}
		for (SysOrgElement ele : eleList) {
			if (ele.getFdId().equals(docCreatorId)) {
				return ele;
			}
		}
		return null;
	}

	@Override
	public Boolean isStatSignReader(SysAttendCategory category) {
		if (UserUtil.getKMSSUser().isAdmin()) {
			return true;
		}
		if (UserUtil.checkRoles(
				Arrays.asList(new String[] { "ROLE_SYSATTEND_STAT_READER",
						"ROLE_SYSATTEND_SIGN_READER" }))) {
			return true;
		}
		if (UserUtil.checkUserModel(category.getFdManager())
				|| UserUtil.checkUserModels(category.getAuthAllReaders())) {
			return true;
		}
		if (Boolean.TRUE.equals(category.getFdIsAllowView())
				&& UserUtil.checkUserModels(category.getFdTargets())) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean isAttendNeeded(SysAttendCategory category, Date date)
			throws Exception {
		if (category == null || date == null) {
			return false;
		}
		List<SysAttendCategory> list = new ArrayList<SysAttendCategory>();
		list.add(category);
		com.alibaba.fastjson.JSONArray datas = this.filterAttendCategory(list, date, true, null);
		if (!datas.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isOnlyDingAttend() {
		try {
			String fdCategoryId = this.getAttendCategory(new Date());
			if (StringUtil.isNull(fdCategoryId)) {
				return false;
			}
			SysAttendCategory category = CategoryUtil.getCategoryById(fdCategoryId);
			if(category == null) {
				category = (SysAttendCategory) this.findByPrimaryKey(fdCategoryId);
			} 
			Boolean fdCanMap = category.getFdCanMap();
			Boolean fdCanWifi = category.getFdCanWifi();
			Boolean dingClock = category.getFdDingClock();
			if (Boolean.FALSE.equals(fdCanMap)
					&& Boolean.FALSE.equals(fdCanWifi)
					&& Boolean.TRUE.equals(dingClock)) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public List findCategorysByTimeArea() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysAttendCategory.fdStatus=1 and sysAttendCategory.fdType=1 and sysAttendCategory.fdShiftType=1");
		List<SysAttendCategory> cateList = this.findList(hqlInfo);
		return cateList;
	}

	@Override
	public boolean isHoliday(String categoryId, Date date, SysOrgElement orgEle,
			boolean isTimeArea) throws Exception {
		boolean isHoliday = false;
		if (isTimeArea) {
			com.alibaba.fastjson.JSONObject result = this.sysTimeCountService.getWorkTimes(orgEle, date);
			if (result != null) {
				Boolean holiday = result.getBoolean("isHoliday");
				if (Boolean.TRUE.equals(holiday)) {
					isHoliday = true;
				}
				// 若工作区间在自定义假期区间内，则认为是节假日
				if (!isHoliday) {
					com.alibaba.fastjson.JSONArray workTimes = result.getJSONArray("workTimes");
					com.alibaba.fastjson.JSONArray vacations = result.getJSONArray("vacations");
					if (vacations != null && !vacations.isEmpty()
							&& workTimes != null && !workTimes.isEmpty()) {
						// 判断某天是否需要上班
						com.alibaba.fastjson.JSONObject vacation = (com.alibaba.fastjson.JSONObject) vacations.get(0);
						int fdStartTime = ((Number) vacation.get("fdStartTime")).intValue();
						int fdEndTime = ((Number) vacation.get("fdEndTime")).intValue();
						// 获取班次最早最晚上下班时间
						List<Integer> signList = new ArrayList<Integer>();
						for (int i = 0; i < workTimes.size(); i++) {
							com.alibaba.fastjson.JSONObject record = (com.alibaba.fastjson.JSONObject) workTimes.get(i);
							signList.add(((Number) record.get("fdStartTime")).intValue());
							Integer endTime = ((Number) record.get("fdEndTime")).intValue();
							Integer fdOverTimeType=1;//默认当天排班
							if(record.containsKey("fdOverTimeType")) {
								fdOverTimeType = (Integer) record.get("fdOverTimeType");
							}
							if(endTime!=null&&Integer.valueOf(2).equals(fdOverTimeType)) {
								endTime=(int) (endTime+DateUtil.DAY);
							}
							signList.add(endTime);
						}
						Collections.sort(signList);
						int minSignTime = signList.get(0);
						int maxSignTime = signList.get(signList.size() - 1);
						if (minSignTime >= fdStartTime
								&& minSignTime <= fdEndTime
								&& maxSignTime >= fdStartTime
								&& maxSignTime <= fdEndTime) {
							// 所有上班时间点都在休假区间,则不需打卡
							return true;
						}
					}

					if(vacations != null && !vacations.isEmpty() && (workTimes == null || workTimes.isEmpty())) {
						//有休假区间，但是没有上班时间点则不需打卡
						return true;
					}
				}
			}
			return isHoliday;
		}
		return this.isHoliday(categoryId, date);
	}

	public ISysAttendCategoryExctimeService
			getSysAttendCategoryExctimeService() {
		return sysAttendCategoryExctimeService;
	}

	public void setSysAttendCategoryExctimeService(
			ISysAttendCategoryExctimeService sysAttendCategoryExctimeService) {
		this.sysAttendCategoryExctimeService = sysAttendCategoryExctimeService;
	}

	public ISysAttendCategoryTimeService getSysAttendCategoryTimeService() {
		return sysAttendCategoryTimeService;
	}

	public void setSysAttendCategoryTimeService(
			ISysAttendCategoryTimeService sysAttendCategoryTimeService) {
		this.sysAttendCategoryTimeService = sysAttendCategoryTimeService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttendMainDao(ISysAttendMainDao sysAttendMainDao) {
		this.sysAttendMainDao = sysAttendMainDao;
	}

	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public ISysQuartzJobService getSysQuartzJobService() {
		if (sysQuartzJobService == null) {
			sysQuartzJobService = (ISysQuartzJobService) SpringBeanUtil.getBean(
					"sysQuartzJobService");
		}
		return sysQuartzJobService;
	}

	public void setSysAttendConfigService(
			ISysAttendConfigService sysAttendConfigService) {
		this.sysAttendConfigService = sysAttendConfigService;
	}

	public void
			setSysTimeHolidayService(
					ISysTimeHolidayService sysTimeHolidayService) {
		this.sysTimeHolidayService = sysTimeHolidayService;
	}

	public void
			setSysTimeCountService(ISysTimeCountService sysTimeCountService) {
		this.sysTimeCountService = sysTimeCountService;
	}

	public void setSysAttendDeviceService(
			ISysAttendDeviceService sysAttendDeviceService) {
		this.sysAttendDeviceService = sysAttendDeviceService;
	}

	public ISysAttendStatJobService getSysAttendStatJobService() {
		if (sysAttendStatJobService == null) {
			sysAttendStatJobService = (ISysAttendStatJobService) SpringBeanUtil
					.getBean("sysAttendStatJobService");
		}
		return sysAttendStatJobService;
	}

	public void setSysAttendNotifyRemindLogService(
			ISysAttendNotifyRemindLogService sysAttendNotifyRemindLogService) {
		this.sysAttendNotifyRemindLogService = sysAttendNotifyRemindLogService;
	}

	/**
	 * 校验是否可以换班
	 * @param applyPerson 申请人
	 * @param exchangePerson 替换人
	 * @param exchangeDate 申请换班日期
	 * @param returnDate 还班日期
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysAttendExchangeResult validatorCanExchangeWorkTime(SysOrgElement applyPerson, SysOrgElement exchangePerson, Date exchangeDate, Date returnDate) throws Exception {
		SysAttendExchangeResult result = new SysAttendExchangeResult();
		//自己与自己 还班时间早于或等于换班时间,换班不还班【不可换班】
		if(applyPerson.equals(exchangePerson) && (returnDate == null || (exchangeDate.compareTo(returnDate)>=0))) {
			result.setReturnState(false);
			result.setMessage("自己与自己 还班时间早于或等于换班时间,换班不还班【不可换班】");
			return result;
		}
		//不同人，不能跨天
		if(!applyPerson.equals(exchangePerson)&&(returnDate != null && !exchangeDate.equals(returnDate))) {
			result.setReturnState(false);
			result.setMessage("自己与他人，换班日期与还班日期不是同一天【不可换班】");
			return result;
		}
		//还班日期
		Date replaceDate = applyPerson.equals(exchangePerson)?returnDate:exchangeDate;
		
		SysTimeArea applyTimeArea = this.sysTimeCountService.getTimeArea(applyPerson);
		SysTimeArea exchangeTimeArea = this.sysTimeCountService.getTimeArea(exchangePerson);
		//两个人都有区域组
		if(null == applyTimeArea || null == exchangeTimeArea) {
			result.setReturnState(false);
			result.setMessage("自己与他人，申请人或替换人没有区域组【不可换班】");
			return result;
		}
		//不同区域组不能换班
//		if(!applyTimeArea.equals(exchangeTimeArea)) {
//			result.setReturnState(false);
//			result.setMessage("自己与他人，区域组不同【不可换班】");
//			return result;
//		}
		//区域组需要为个人排班
		if(Boolean.FALSE.equals(applyTimeArea.getFdIsBatchSchedule()) || Boolean.FALSE.equals(exchangeTimeArea.getFdIsBatchSchedule())) {
			result.setReturnState(false);
			result.setMessage("区域组不是个人排班【不可换班】");
			return result;
		}
		
		com.alibaba.fastjson.JSONObject applyWorkTimes = this.sysTimeCountService.getWorkTimes(applyPerson, exchangeDate);
		com.alibaba.fastjson.JSONObject exchangeWorkTimes = this.sysTimeCountService.getWorkTimes(exchangePerson, replaceDate);
		com.alibaba.fastjson.JSONArray applyWorkTimesArray = applyWorkTimes.getJSONArray("workTimes");
		//未排班
		if(applyWorkTimesArray ==null || applyWorkTimesArray.isEmpty()) {
			result.setReturnState(false);
			result.setMessage("申请人未排班【不可换班】");
			return result;
		}
		com.alibaba.fastjson.JSONObject applyWorkTimeObject = (com.alibaba.fastjson.JSONObject) applyWorkTimesArray.get(0);
		//申请人换班日期的班次id
		String applyCommonWorkId = applyWorkTimeObject.getString("fdCommonWorkId");
		com.alibaba.fastjson.JSONArray exchangeWorkTimesArray = exchangeWorkTimes.getJSONArray("workTimes");
		//未排班
		if(exchangeWorkTimesArray == null || exchangeWorkTimesArray.isEmpty()) {
			result.setReturnState(false);
			result.setMessage("替换人未排班【不可换班】");
			return result;
		}
		String appyCategoryId = this.getAttendCategory(applyPerson, exchangeDate);
		String exchangeCategoryId = this.getAttendCategory(exchangePerson, replaceDate);
		if(StringUtil.isNull(appyCategoryId) || StringUtil.isNull(exchangeCategoryId)) {
			result.setReturnState(false);
			result.setMessage("申请人或替换人的没有考勤组【不可换班】");
			return result;
		}

		SysAttendCategory appyCategory = CategoryUtil.getCategoryById(appyCategoryId);
		SysAttendCategory exchangeCategory =  CategoryUtil.getCategoryById(exchangeCategoryId);
		if(appyCategory== null || exchangeCategory == null) {
			result.setReturnState(false);
			result.setMessage("申请人或替换人的没有考勤组【不可换班】");
			return result;
		}
		//两个人的考勤组必须为排班
		if(!AttendConstant.FD_SHIFT_TYPE[1].equals(appyCategory.getFdShiftType()) || !AttendConstant.FD_SHIFT_TYPE[1].equals(exchangeCategory.getFdShiftType())) {
			result.setReturnState(false);
			result.setMessage("申请人或替换人的考勤组不是排班【不可换班】");
			return result;
		}
		com.alibaba.fastjson.JSONObject exchangeWorkTimeObject = (com.alibaba.fastjson.JSONObject) exchangeWorkTimesArray.get(0);
		//替换人换班日期的班次id
		String exchangeCommonWorkId = exchangeWorkTimeObject.getString("fdCommonWorkId");
		//不能同一班次换班
		if(applyCommonWorkId!=null && applyCommonWorkId.equals(exchangeCommonWorkId)) {
			result.setReturnState(false);
			result.setMessage("同一班次换班【不可换班】");
			return result;
		}
		//前一天的班次
		com.alibaba.fastjson.JSONObject lastApplyWorkTimes = this.sysTimeCountService.getWorkTimes(applyPerson, AttendUtil.getDate(exchangeDate, -1));
		com.alibaba.fastjson.JSONObject lastExchangeWorkTimes = this.sysTimeCountService.getWorkTimes(exchangePerson, AttendUtil.getDate(replaceDate, -1));
		//申请人前一天的班次时间
		com.alibaba.fastjson.JSONArray lastApplyWorkTimesArray = lastApplyWorkTimes.getJSONArray("workTimes");
		//替换人前一天的班次时间
		com.alibaba.fastjson.JSONArray lastExchangeWorkTimesArray = lastExchangeWorkTimes.getJSONArray("workTimes");
		//后一天的班次
		com.alibaba.fastjson.JSONObject nextApplyWorkTimes = this.sysTimeCountService.getWorkTimes(applyPerson, AttendUtil.getDate(exchangeDate, 1));
		com.alibaba.fastjson.JSONObject nextExchangeWorkTimes = this.sysTimeCountService.getWorkTimes(exchangePerson, AttendUtil.getDate(replaceDate, 1));
		//申请人后一天的班次时间
		com.alibaba.fastjson.JSONArray nextApplyWorkTimesArray = nextApplyWorkTimes.getJSONArray("workTimes");
		//替换人后一天的班次时间
		com.alibaba.fastjson.JSONArray nextExchangeWorkTimesArray = nextExchangeWorkTimes.getJSONArray("workTimes");
		//检验替换后的班次时间与申请人/替换人前一天的班次时间或后一天的班次时间是否有冲突
		if(checkDateCross(exchangeWorkTimesArray, lastApplyWorkTimesArray,AttendUtil.getDate(exchangeDate, -1),exchangeDate) 
				|| checkDateCross(nextApplyWorkTimesArray,exchangeWorkTimesArray, exchangeDate,AttendUtil.getDate(exchangeDate, 1)) 
				|| checkDateCross(applyWorkTimesArray, lastExchangeWorkTimesArray,AttendUtil.getDate(replaceDate, -1),replaceDate)
				|| checkDateCross(nextExchangeWorkTimesArray,applyWorkTimesArray, replaceDate,AttendUtil.getDate(replaceDate, 1))) {
			result.setReturnState(false);
			result.setMessage("班次时间冲突【不可换班】");
			return result;
		}
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(4);
		fdTypes.add(5);
		fdTypes.add(7);
		List<String> eleIdList = new ArrayList<String>();
		eleIdList.add(applyPerson.getFdId());
		eleIdList.add(exchangePerson.getFdId());
		//获取申请人和替换人的换班日期的请假/出差/外出流程
		List<SysAttendBusiness> applyBusList = getSysAttendBusinessService().findBussList(eleIdList, exchangeDate, AttendUtil.getEndDate(exchangeDate, 1),fdTypes);
		//考虑跨天打卡 获取申请人换班当天的流程
		List applyBusRecordList = getSysAttendBusinessService().genUserBusiness(applyPerson, exchangeDate, applyBusList);
		//申请人或替班人有一人存在同一日期/排班时间段有请假/出差/外出的情况【不可换班】
		if(applyBusRecordList !=null && !applyBusRecordList.isEmpty()) {
			result.setReturnState(false);
			result.setMessage("申请人存在同一日期/排班时间段有请假/出差/外出的情况【不可换班】");
			return result;
		}
		//获取申请人和替换人的还班日期的请假/出差/外出流程
		List<SysAttendBusiness> exchangeBusList = getSysAttendBusinessService().findBussList(eleIdList, replaceDate, AttendUtil.getEndDate(replaceDate, 1),fdTypes);
		//考虑跨天打卡 获取替换人还班当天的流程
		List exchangeBusRecordList = getSysAttendBusinessService().genUserBusiness(exchangePerson, exchangeDate, applyBusList);
		if(exchangeBusRecordList !=null && !exchangeBusRecordList.isEmpty()) {
			result.setReturnState(false);
			result.setMessage("替换人存在同一日期/排班时间段有请假/出差/外出的情况【不可换班】");
			return result;
		}
		result.setReturnState(true);
		return result;
	}

	/**
	 * 检验时间存在交集
	 * @param exchangeWorkTimesArray
	 * @param lastApplyWorkTimesArray
	 * @return
	 */
	private boolean checkDateCross(com.alibaba.fastjson.JSONArray exchangeWorkTimesArray,
			com.alibaba.fastjson.JSONArray lastApplyWorkTimesArray,Date startDate,Date endDate) {
		boolean flag= false;
		for(Object applyObject : lastApplyWorkTimesArray) {
			com.alibaba.fastjson.JSONObject applyJSONObject = (com.alibaba.fastjson.JSONObject) applyObject;
			Long fdStartTime = applyJSONObject.getLong("fdStartTime");
			Long fdEndTime = applyJSONObject.getLong("fdEndTime");
			Date startTime = DateUtil.getTimeByNubmer(fdStartTime.longValue());
			Date endTime = DateUtil.getTimeByNubmer(fdEndTime.longValue());
			Date applyStartDate = AttendUtil.joinYMDandHMS(startDate, startTime);
			Date applyEndDate = AttendUtil.joinYMDandHMS(startDate, endTime);
			Integer fdOverTimeType =applyJSONObject.getInteger("fdOverTimeType");
			if(Integer.valueOf(2).equals(fdOverTimeType)) {
				applyEndDate = AttendUtil.addDate(applyEndDate, 1);
			}
			for(Object exchangeObject : exchangeWorkTimesArray) {
				com.alibaba.fastjson.JSONObject exchangeJSONObject = (com.alibaba.fastjson.JSONObject) exchangeObject;
				Long exStartTime = exchangeJSONObject.getLong("fdStartTime");
				Long exEndTime = exchangeJSONObject.getLong("fdEndTime");
				Integer exOverTimeType =exchangeJSONObject.getInteger("fdOverTimeType");
				Date exchangeStartTime = DateUtil.getTimeByNubmer(exStartTime.longValue());
				Date exchangeEndTime = DateUtil.getTimeByNubmer(exEndTime.longValue());
				Date exchangeStartDate = AttendUtil.joinYMDandHMS(endDate, exchangeStartTime);
				Date exchangeEndDate = AttendUtil.joinYMDandHMS(endDate, exchangeEndTime);
				if(Integer.valueOf(2).equals(exOverTimeType)) {
					exchangeEndDate = AttendUtil.addDate(exchangeEndDate, 1);
				}
				if(applyStartDate.getTime()<=exchangeStartDate.getTime() && applyEndDate.getTime()>=exchangeStartDate.getTime() 
						|| (applyStartDate.getTime()>=exchangeStartDate.getTime() && applyEndDate.getTime()<=exchangeEndDate.getTime())
						|| (applyStartDate.getTime()<=exchangeEndDate.getTime() && applyEndDate.getTime()>=exchangeEndDate.getTime())) {
					flag = true;
					break;
				}
			}
			if(flag) {
				break;
			}
		}
		return flag;
	}
	
	private ISysAttendBusinessService sysAttendBusinessService;
	public ISysAttendBusinessService getSysAttendBusinessService() {
		if (sysAttendBusinessService == null) {
			sysAttendBusinessService = (ISysAttendBusinessService) SpringBeanUtil.getBean(
					"sysAttendBusinessService");
		}
		return sysAttendBusinessService;
	}

	/**
	 * 获取考勤组的考勤人员
	 * @param categoryId
	 * @param date 具体日期
	 * @param fdType 1考勤组，2是签到组
	 * @return 所有人员的人员对象
	 * @throws Exception
	 */
	private List<SysOrgElement> getAttendOrgElementList(String categoryId,Date date,Integer fdType) throws Exception{
		List<SysOrgElement> categoryOrgs = (List<SysOrgElement>) CategoryUtil.CATEGORY_USERS_CACHE_MAP.get(categoryId);
		if(CollectionUtils.isNotEmpty(categoryOrgs)) {
			return categoryOrgs;
		}
		//考勤对象
		List<SysOrgElement> targets = getSysAttendHisCategoryService().getCategoryTargetOrg(categoryId,date,fdType);
		if(CollectionUtils.isEmpty(targets)) {
			return null;
		}
		/**
		 * 根据考勤组获取考勤组的考勤人员
		 * 排除考勤组例外人员
		 * 排除全局例外人员
		 * 2021-07-12
		 */
		List<String> targetIds =targets.stream().map(e->e.getFdId()).collect(Collectors.toList());
		List<SysOrgElement> orgList = AttendPersonUtil.expandToPerson(targetIds);
		if(CollectionUtils.isNotEmpty(orgList)) {
			List<SysOrgElement> resultList =new ArrayList();
			List<String>  excUserElementList =null;
			//排除对象
			List<SysOrgElement> excOrgs = getSysAttendHisCategoryService().getCategoryExcOrg(categoryId,date,fdType);
			if( CollectionUtils.isNotEmpty(excOrgs)) {
				excUserElementList = AttendPersonUtil.expandToPersonIds(excOrgs);
			}
			//排除全局例外人员
			Map<String,String> globalExcElementMap =CategoryUtil.getGlobalExcTargetMap();
			for (SysOrgElement ele : orgList) {
				//过滤考勤组排除人员
				if(excUserElementList != null && excUserElementList.contains(ele.getFdId())) {
					continue;
				}
				//过滤全局排除人员
				if(globalExcElementMap != null && globalExcElementMap.get(ele.getFdId()) !=null) {
					continue;
				}
				resultList.add(ele);
			}
			//排除不属于该考勤组的人员
			if(CollectionUtils.isNotEmpty(resultList)){
				SysAttendUserCacheUtil.removeNotInCurrentCategoryId(categoryId,resultList,date);
			}
			CategoryUtil.CATEGORY_USERS_CACHE_MAP.put(categoryId,resultList);
			return resultList;
		}
		return orgList;
	}
	/**
	 * 获取考勤组的考勤人员
	 * @param categoryId 分类ID
	 * @param date 日期
	 * @param fdType 签到对象，1是考勤组，2是签到组
	 * @return 所有人员的ID串
	 * @throws Exception
	 */
	public List<String> getAttendOrgElementIdList(String categoryId,Date date,Integer fdType,boolean isOld) throws Exception{
		List<String> categoryOrgs = (List<String>) CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.get(categoryId);
		if(logger.isDebugEnabled()){
			logger.debug("考勤组下的人员获取，缓存数据:日期{},人员{}",date,Joiner.on(";").join(categoryOrgs));
		}
		if(CollectionUtils.isNotEmpty(categoryOrgs)) {
			return categoryOrgs;
		}
		//考勤对象
		List<SysOrgElement>  targets =null;
		if(isOld){
			targets =getSysAttendHisCategoryService().getOldCategoryTargetOrg(categoryId, date, fdType);
		} else {
			targets =getSysAttendHisCategoryService().getCategoryTargetOrg(categoryId, date, fdType);
		}
		if(CollectionUtils.isEmpty(targets)) {
			return null;
		}
		if(logger.isDebugEnabled()){
			logger.debug("考勤组下的人员获取，配置的考勤对象:",targets.size());
		}
		/**
		 * 根据考勤组获取考勤组的考勤人员
		 * 排除考勤组例外人员
		 * 排除全局例外人员
		 * 2021-07-12
		 */
		List<String> orgIdsList = AttendPersonUtil.expandToPersonIds(targets);
		if(CollectionUtils.isNotEmpty(orgIdsList)) {

			List<String> resultList =new ArrayList();
			List<String>  excUserElementList =null;
			//排除对象
			List<SysOrgElement> excOrgs =null;
			if(isOld) {
				excOrgs = getSysAttendHisCategoryService().getOldCategoryExcOrg(categoryId, date, fdType);
			} else {
				excOrgs = getSysAttendHisCategoryService().getCategoryExcOrg(categoryId, date, fdType);
			}
			if( CollectionUtils.isNotEmpty(excOrgs)) {
				excUserElementList = AttendPersonUtil.expandToPersonIds(excOrgs);
			}
			//排除全局例外人员
			Map<String,String> globalExcElementMap =CategoryUtil.getGlobalExcTargetMap();
			for (String tempElemnt:orgIdsList) {
				if(excUserElementList != null && excUserElementList.contains(tempElemnt)) {
					continue;
				}
				if(globalExcElementMap !=null && globalExcElementMap.get(tempElemnt) !=null) {
					continue;
				}
				resultList.add(tempElemnt);
			}
//			if(logger.isDebugEnabled()){
//				logger.debug("考勤组下的人员获取，解析完后所有人数:",resultList.size());
//			}
//			//排除不属于该考勤组的人员
//			List<SysOrgElement> categoryAllElement = AttendPersonUtil.getSysOrgElementById(resultList);
//			if(CollectionUtils.isNotEmpty(categoryAllElement)){
//				SysAttendUserCacheUtil.removeNotInCurrentCategoryId(categoryId,categoryAllElement,date);
//				if(logger.isDebugEnabled()){
//					logger.debug("考勤组下的人员获取，剔除完后所有人数:",categoryAllElement.size());
//				}
//				//筛选之后如果存在转换成id进行存储
//				if(CollectionUtils.isNotEmpty(categoryAllElement)) {
//					resultList = categoryAllElement.stream().map(item -> item.getFdId()).collect(Collectors.toList());
//					if(logger.isDebugEnabled()){
//						logger.debug("考勤组下的人员获取，存储缓存:",resultList.size());
//					}
//					CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.put(categoryId, resultList);
//					return resultList;
//				}
//			} else {
//				return Lists.newArrayList();
//			}
		}
		return orgIdsList;
	}

	/**
	 * 获取当前打卡时间点对应的上下班班次详情
	 * @param category 历史考勤组
	 * @param fdWorkTimeId 班次ID
	 * @param fdWorkType 班次类型0，1
	 * @param _signTime 标准打卡时间
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysAttendCategoryWorktime getCurrentWorkTime(SysAttendCategory category,String fdWorkTimeId,String fdWorkType,String _signTime) throws Exception {

		List<SysAttendCategoryWorktime> workTimeList = getWorktimes(category,
						new Date(), UserUtil.getUser());
		if (workTimeList != null && !workTimeList.isEmpty()) {
			boolean isTimeArea = Integer.valueOf(1)
					.equals(category.getFdShiftType());
			for (SysAttendCategoryWorktime workTime : workTimeList) {
				if (workTime.getFdId().equals(fdWorkTimeId) && !isTimeArea) {
					return workTime;
				}
				if (isTimeArea) {
					int signTime = workTime.getFdStartTime().getHours() * 60
							+ workTime.getFdStartTime().getMinutes();
					if ("1".equals(fdWorkType)) {
						signTime = workTime.getFdEndTime().getHours() * 60
								+ workTime.getFdEndTime().getMinutes();
					}
					//24*60;
					if("1".equals(fdWorkType)&&Integer.valueOf(2).equals(workTime.getFdOverTimeType())) {
						signTime+=1440;
					}
					if (_signTime!=null && (signTime + "").equals(_signTime)) {
						return workTime;
					}
				}

			}
		}
		return null;
	}
	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 * @param workDate 打卡时间
	 * @param searchDate 计算考勤组时间
	 * @param category 考勤组
	 * @param ele 人员
	 * @return 如果在打卡的范围内，则返回打卡时间所在的日期。可以判断null的话，就是不在排班考勤范围内，
	 * @throws Exception
	 */
	@Override
	public Date getTimeAreaDateOfDate(Date workDate,Date searchDate, SysAttendCategory category,SysOrgElement ele) throws Exception {
		Date returnDate =null;
		//考勤组排班时间列表
		List<Map<String, Object>> signTimeList = getAttendSignTimes(category, searchDate, ele);
		if (signTimeList.isEmpty()) {
			// 休息日也允许同步数据
			signTimeList = getAttendSignTimes(category, searchDate, ele, true);
		}
		return getTimeAreaDateOfDate(workDate,searchDate,signTimeList);
	}

	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 * @param workDate 打卡时间
	 * @param date 排班日期
	 * @param workConfig 具体的班次信息
	 * @return 如果在打卡的范围内，则返回打卡时间所在的日期。可以判断null的话，就是不在排班考勤范围内，
	 * @throws Exception
	 */
	@Override
	public Date getTimeAreaDateOfDate( Date workDate,Date date, Map<String, Object> workConfig) throws Exception {
		return AttendUtil.getTimeAreaDateOfDate(workDate,date,workConfig);
	}
	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 * @param workDate 实际打卡时间
	 * @param date 计算日期
	 * @param signTimeList 排班列表
	 * @return 如果在打卡的范围内，则返回打卡时间所在的日期。可以判断null的话，就是不在排班考勤范围内，
	 * @throws Exception
	 */
	@Override
	public Date getTimeAreaDateOfDate(Date workDate ,Date date, List<Map<String, Object>> signTimeList) throws Exception {
		Date returnDate =null;
		if(CollectionUtils.isEmpty(signTimeList)) {
			return null;
		}
		//判断时间是否在排班的最早最晚打卡时间范围内
		for(Map<String, Object> workConfig:signTimeList) {
			returnDate =getTimeAreaDateOfDate(workDate,date,workConfig);
			if(returnDate !=null){
				return returnDate;
			}
		}
		return null;
	}

	/**
	 * 判断是否是补假日期，true表示是补假日期，false表示不是补假日期
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isPatchHolidayDay(String categoryId, Date date) throws Exception {
		ISysTimeHolidayDetailService sysTimeHolidayDetailService = (ISysTimeHolidayDetailService) SpringBeanUtil.getBean("sysTimeHolidayDetailService");
		SysAttendCategory cate = CategoryUtil.getCategoryById(categoryId);
		if(cate != null && date != null && cate.getFdHoliday() != null){
			String d = DateUtil.convertDateToString(date,"yyyy-MM-dd");
			HQLInfo info = new HQLInfo();
			info.setWhereBlock("fdPatchHolidayDay like :date and fdHoliday.fdId=:fdId");
			info.setParameter("date","%" + d + "%");
			info.setParameter("fdId", cate.getFdHoliday().getFdId());
			List<SysTimeHolidayDetail> sysTimeHolidayDetails = sysTimeHolidayDetailService.findList(info);
			if(!ArrayUtil.isEmpty(sysTimeHolidayDetails)){
				return true;
			}
		}
		return false;
	}
}
