package com.landray.kmss.third.weixin.work.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.cms.interfaces.ICMSProvider;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;

import com.landray.kmss.third.weixin.work.api.WxworkApiService;
import com.landray.kmss.third.weixin.work.model.WeixinWorkConfig;
import com.landray.kmss.util.HQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.third.weixin.work.spi.model.WxworkOmsRelationModel;
import com.landray.kmss.third.weixin.work.spi.service.IWxworkOmsRelationService;
import com.landray.kmss.third.weixin.work.util.WxworkHttpClientUtil;
import com.landray.kmss.third.weixin.work.util.WxworkUtils;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class WeixinWorkCalendarProvider implements ICMSProvider {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinWorkCalendarProvider.class);

	private IWxworkOmsRelationService wxworkOmsRelationService = null;

	@Override
	public ICMSProvider getNewInstance(String personId) throws Exception {
		return this;
	}

	private List<String> getWeixinUserIds(List<String> relatePersonIds) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdAppPkId is not null and "+HQLUtil.buildLogicIN("fdEkpId", relatePersonIds));
		List<String> pkIds = wxworkOmsRelationService.findValue(hqlInfo);
		if (pkIds == null || pkIds.size() == 0) {
			logger.info("通过EKP的fdId查找中间映射表发现找不到对应的微信人员("+relatePersonIds+")，请先维护中间映射表数据");
		}
		return pkIds;
	}

	@Override
	public String addCalElement(String personId, SyncroCommonCal syncroCommonCal) throws Exception {
		String rtn = null;
		if(StringUtil.isNotNull(syncroCommonCal.getRecurrentStr())){
			logger.debug("重复日程不进行同步...");
			return rtn;
		}
		if (StringUtil.isNull(personId)) {
            personId = syncroCommonCal.getPersonId();
        }
		WxworkOmsRelationModel relation = wxworkOmsRelationService
				.findByEkpId(personId);
		if (relation == null) {
			logger.warn("找不到该人员的映射，不进行同步，" + personId);
			return null;
		}
		String userid = relation.getFdAppPkId();
		if (StringUtil.isNotNull(userid)) {
			rtn = addCalendar(userid,
					syncroCommonCal);
		}else{
			logger.warn("人员的映射关系中，微信用户ID为空，不进行同步，" + personId);
			return null;
		}
		return rtn;
	}

	public String addCalendar(String userid, SyncroCommonCal cal)
			throws Exception {
		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		String agentId = WeixinWorkConfig.newInstance().getWxAgentid();
		String token = wxworkApiService.getAccessTokenByAgentid(agentId);
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/oa/schedule/add?access_token="
				+ token;

		JSONObject msgObj = buildCalendarObj(cal);
		msgObj.getJSONObject("schedule").put("organizer", userid);

		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		int errcode = result.getIntValue("errcode");
		if (errcode == 0) {
			return result.getString("schedule_id");
		} else {
			throw new Exception("新增日程失败," + result.toString());
		}
	}

	@Override
	public boolean updateCalElement(String personId,
			SyncroCommonCal syncroCommonCal) throws Exception {
		if (StringUtil.isNotNull(syncroCommonCal.getRecurrentStr())) {
			logger.debug("重复日程不进行同步...");
			return true;
		}
		if (StringUtil.isNull(personId)) {
            personId = syncroCommonCal.getPersonId();
        }
		WxworkOmsRelationModel relation = wxworkOmsRelationService
				.findByEkpId(personId);
		if (relation == null) {
			logger.warn("找不到该人员的映射，不进行同步，" + personId);
			return false;
		}
		String userid = relation.getFdAppPkId();
		if (StringUtil.isNotNull(userid)) {
			updateCalendar(syncroCommonCal);
			return true;
		}else{
			logger.warn("人员的映射关系中，微信用户ID为空，不进行同步，" + personId);
		}
		return false;
	}

	private JSONObject buildCalendarObj(SyncroCommonCal cal) throws Exception {
		JSONObject msgObj = new JSONObject();
		JSONObject schedule = new JSONObject();
		Date start = cal.getEventStartTime();
		Date end = cal.getEventFinishTime();
		long start_time = start.getTime() / 1000L;
		long end_time = end.getTime() / 1000L;
		if (end_time <= start_time) {
			end_time = start_time + 1;
		}
		schedule.put("start_time", start_time);
		schedule.put("end_time", end_time);
		schedule.put("summary", WxworkUtils.getString(cal.getSubject(), 128));
		schedule.put("description", WxworkUtils.getString(cal.getContent(), 512));
		schedule.put("location", WxworkUtils.getString(cal.getEventLocation(), 128));
		JSONObject reminders = new JSONObject();
		// 获取日程提醒时间
		Integer reminderTime = getReminderTime(cal);
		if (reminderTime != null) {
			reminders.put("is_remind", 1);
			reminders.put("is_repeat", 0);
			reminders.put("remind_before_event_secs", reminderTime);
		} else {
			reminders.put("is_remind", 0);
			reminders.put("is_repeat", 0);
		}
		List<String> relatePersonIds = cal.getRelatedPersonIds();
		if(relatePersonIds!=null){
			List<String> weixinIds = getWeixinUserIds(relatePersonIds);
			if(weixinIds!=null){
				JSONArray attendees = new JSONArray();
				for(String weixinId:weixinIds){
					JSONObject o = new JSONObject();
					o.put("userid",WxworkUtils.getString(weixinId, 64));
					attendees.add(o);
				}
				schedule.put("attendees",attendees);
			}
		}
		schedule.put("reminders", reminders);
		msgObj.put("schedule", schedule);

		return msgObj;
	}

	public void updateCalendar(SyncroCommonCal cal)
			throws Exception {
		String personId = cal.getPersonId();
		WxworkOmsRelationModel relation = wxworkOmsRelationService
				.findByEkpId(personId);
		if (relation == null) {
			throw new Exception("找不到该人员的映射，不进行同步，" + personId);
		}
		String userid = relation.getFdAppPkId();
		if (StringUtil.isNull(userid)) {
			throw new Exception("找不到该人员的映射，不进行同步，" + personId);
		}

		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		String agentId = WeixinWorkConfig.newInstance().getWxAgentid();
		String token = wxworkApiService.getAccessTokenByAgentid(agentId);
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/oa/schedule/update?access_token="
				+ token;

		JSONObject msgObj = buildCalendarObj(cal);
		msgObj.getJSONObject("schedule").put("schedule_id", cal.getUuid());
		msgObj.getJSONObject("schedule").put("organizer", userid);

		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		int errcode = result.getIntValue("errcode");
		if (errcode == 0) {

		} else {
			throw new Exception("更新日程失败," + result.toString());
		}
	}

	@Override
	public boolean deleteCalElement(String personId, String uuid) throws Exception {

		WxworkApiService wxworkApiService = WxworkUtils.getWxworkApiService();
		String agentId = WeixinWorkConfig.newInstance().getWxAgentid();
		String token = wxworkApiService.getAccessTokenByAgentid(agentId);
		String url = WxworkUtils.getWxworkApiUrl()
				+ "/oa/schedule/del?access_token="
				+ token;
		JSONObject msgObj = new JSONObject();
		msgObj.put("schedule_id", uuid);
		JSONObject result = WxworkHttpClientUtil.httpPost(url, msgObj, null,
				JSONObject.class);
		int errcode = result.getIntValue("errcode");
		if (errcode == 0) {
			return true;
		} else {
			throw new Exception("删除日程失败," + result.toString());
		}
	}

	@Override
	public List<SyncroCommonCal> getCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(全量)
		List<SyncroCommonCal> weixinAllCals = new ArrayList<SyncroCommonCal>();
		return weixinAllCals;
	}

	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/新增)
		List<SyncroCommonCal> weixinAddCals = new ArrayList<SyncroCommonCal>();
		return weixinAddCals;
	}

	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/删除)
		List<SyncroCommonCal> weixinDelCals = new ArrayList<SyncroCommonCal>();
		return weixinDelCals;
	}

	@Override
	public List<SyncroCommonCal> getUpdatedCalElements(String personId, Date date) throws Exception {
		// 从EKP同步到钉钉，暂时返回为空(增量/更新)
		List<SyncroCommonCal> weixinUpdateCals = new ArrayList<SyncroCommonCal>();
		return weixinUpdateCals;
	}

	@Override
	public String getCalType() {
		return "event";
	}

	@Override
	public List<String> getPersonIdsToSyncro() {
		String sql = "select fd_id from sys_org_element e where fd_is_available = 1 and fd_org_type = '8' and "
				+ "fd_id in (select distinct fd_ekp_id from wxwork_oms_relation_model m where m.fd_ekp_id = e.fd_id)";
		List<String> fdIds = new ArrayList<String>();
		try {
			fdIds = wxworkOmsRelationService.getBaseDao().getHibernateSession()
					.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdIds;
	}

	@Override
	public boolean isNeedSyncro(String personId) {
		WxworkOmsRelationModel relation = null;
		try {
			relation = wxworkOmsRelationService
					.findByEkpId(personId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (relation == null) {
			return false;
		}
		if (StringUtil.isNull(relation.getFdAppPkId())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isSynchroEnable() throws Exception {
		WeixinWorkConfig config = new WeixinWorkConfig();
		String enable = config.getWxCalendarEnabled();
		if ("true".equals(enable)) {
			return true;
		}
		return false;
	}

	public IWxworkOmsRelationService getWxworkOmsRelationService() {
		return wxworkOmsRelationService;
	}

	public void setWxworkOmsRelationService(
			IWxworkOmsRelationService wxworkOmsRelationService) {
		this.wxworkOmsRelationService = wxworkOmsRelationService;
	}

	private Integer getReminderTime(SyncroCommonCal cal) throws Exception {
		List<SysNotifyRemindMain> list = getSysNotifyRemindMainService()
				.getSysNotifyRemindMainList(cal.getCalendarId());
		List<Integer> beforeTimeList = new ArrayList<>();
		if (!list.isEmpty() && list.size() > 0) {
			for (SysNotifyRemindMain sysNotifyRemindMain : list) {
				// ekp 存入的时间单位不一致 有 分 时 天 周 统一抓换成分
				int beforeTime = 0;
				if ("minute".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer
							.valueOf(sysNotifyRemindMain.getFdBeforeTime());
				} else if ("hour".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 60;
				} else if ("day".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 1440;
				} else if ("week".equals(sysNotifyRemindMain.getFdTimeUnit())) {
					beforeTime = Integer.valueOf(
							sysNotifyRemindMain.getFdBeforeTime()) * 10080;
				}
				beforeTimeList.add(beforeTime);
			}
			// 可能存在多条数据 取最小的一条 所以进行排序
			Collections.sort(beforeTimeList);
			Integer newbeforeTime = beforeTimeList.get(0);
			if (newbeforeTime > 0 && newbeforeTime < 3) { // 大于一天按一天传
				newbeforeTime = 0;
			} else if (newbeforeTime >= 3 && newbeforeTime < 10) {
				newbeforeTime = 300;
			} else if (newbeforeTime >= 10 && newbeforeTime < 30) {
				newbeforeTime = 900;
			} else if (newbeforeTime >= 30 && newbeforeTime < 120) {
				newbeforeTime = 3600;
			} else {
				newbeforeTime = 86400;
			}
			return newbeforeTime;
		}
		return null;
	}

	private ISysNotifyRemindMainService sysNotifyRemindMainService = null;

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
					.getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}

}
