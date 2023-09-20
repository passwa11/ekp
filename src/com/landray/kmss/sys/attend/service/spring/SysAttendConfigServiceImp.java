package com.landray.kmss.sys.attend.service.spring;

import com.google.common.collect.Lists;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.cache.SysAttendUserCacheUtil;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;

public class SysAttendConfigServiceImp extends BaseServiceImp
		implements ISysAttendConfigService {

	private ISysQuartzCoreService sysQuartzCoreService;
	
	private ThreadLocal<KmssCache> cacheThreadLocal = new ThreadLocal<KmssCache>();

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		savePushReportQuart((SysAttendConfig) modelObj);
		clearCache();
	}

	private void savePushReportQuart(SysAttendConfig config) throws Exception {
		sysQuartzCoreService.delete(config, null);
		if (config.getFdPushLeader() != null && config.getFdPushLeader()) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzKey("sysAttendConfig_pushReport");
			quartzContext.setQuartzSubject(ResourceUtil.getString(
					"sys-attend:sysAttendConfig.quartz.pushReport.subject"));
			
			quartzContext.setQuartzCronExpression(
					"0 " + config.getFdPushTime().getMinutes() + " "
							+ config.getFdPushTime().getHours() + " "
							+ config.getFdPushDate().intValue() + " * ? * ");
			JSONObject param = new JSONObject();
			param.put("fdPushLeader",
					config.getFdPushLeader() ? "true" : "false");
			quartzContext.setQuartzParameter(param.toString());
			quartzContext.setQuartzJobService("sysAttendReportService");
			quartzContext.setQuartzJobMethod("sendAttendReport");
			sysQuartzCoreService.saveScheduler(quartzContext, config);
		}
	}

	@Override
	public JSONObject getKKConfig() throws Exception {
		JSONObject json = new JSONObject();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysAttendConfig.class.getName());
		SysAttendConfig sysAttendConfig = (SysAttendConfig)this.findFirstOne(hqlInfo);
		SysAttendConfig model = new SysAttendConfig();
		if (sysAttendConfig != null) {
			model = sysAttendConfig;
		}
		json.put("fdSpeedAttend", model.getFdSpeedAttend()==null ? false:model.getFdSpeedAttend().booleanValue());
		json.put("fdSpeedStartTime",
				model.getFdSpeedStartTime() == null ? ""
						: DateUtil.convertDateToString(
								model.getFdSpeedStartTime(), "HH:mm"));
		json.put("fdSpeedEndTime",
				model.getFdSpeedEndTime() == null ? ""
						: DateUtil.convertDateToString(
								model.getFdSpeedEndTime(), "HH:mm"));
		return json;
	}

	@Override
	public SysAttendConfig getSysAttendConfig() throws Exception {
		KmssCache cache = cacheThreadLocal.get();
		if(cache != null)
		{			
			SysAttendConfig sysAttendConfig = (SysAttendConfig) cache.get("SYS_ATTEND_CONFIG");
			if(sysAttendConfig != null)
			{
				return sysAttendConfig;
			}
		} else {
			cacheThreadLocal.set(new KmssCache(SysAttendConfig.class));
			cache = cacheThreadLocal.get();
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysAttendConfig.class.getName());
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1);
		SysAttendConfig config = (SysAttendConfig) this.findFirstOne(hqlInfo);
		if (config !=null) {
			config.getFdExcTargetIds();
			config.getFdExcTargetNames();
			cache.put("SYS_ATTEND_CONFIG", config);
			return config;
		}
		return null;
	}

	/**
	 * 该方法在业务执行update之后执行
	 * @throws Exception
	 */
	private void clearCache() throws Exception {
		// 清除缓存
		KmssCache cache = new KmssCache(SysAttendConfig.class);
		cache.clear();

		KmssCache cacheConfig = cacheThreadLocal.get();
		if (cacheConfig != null) {
			cacheConfig.clear();
		}
		/**
		 * 获取通用配置中全局排除人员。
		 * 清空这些人所在考勤组的缓存。
		 * 并且考勤组与人员关系缓存全部清除。
		 */
		CategoryUtil.CATEGORY_USERIDS_CACHE_MAP.clear();
		CategoryUtil.CATEGORY_USERS_CACHE_MAP.clear();
		SysAttendConfig config =getSysAttendConfig();
		if(config !=null && StringUtil.isNotNull(config.getFdExcTargetIds())){
			SysAttendUserCacheUtil.clearUserCache(Lists.newArrayList(config.getFdExcTargetIds().split(";")),null);
		}
	}

	public void setSysQuartzCoreService(
					ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

}
