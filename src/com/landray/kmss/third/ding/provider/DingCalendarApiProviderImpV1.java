package com.landray.kmss.third.ding.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.request.OapiCalendarCreateRequest;
import com.dingtalk.api.request.OapiCalendarCreateRequest.OpenCalendarReminderVo;
import com.dingtalk.api.request.OapiCalendarDeleteRequest;
import com.dingtalk.api.response.OapiCalendarCreateResponse;
import com.dingtalk.api.response.OapiCalendarDeleteResponse;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.km.calendar.cms.interfaces.SyncroCommonCal;
import com.landray.kmss.km.calendar.model.KmCalendarSyncMapping;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncMappingService;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class DingCalendarApiProviderImpV1 implements IDingCalendarApiProvider {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingCalendarApiProviderImpV1.class);

	private ISysNotifyRemindMainService sysNotifyRemindMainService = null;

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
					.getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}

	private IKmCalendarSyncMappingService kmCalendarSyncMappingService = null;

	public IKmCalendarSyncMappingService getKmCalendarSyncMappingService() {
		if (kmCalendarSyncMappingService == null) {
			kmCalendarSyncMappingService = (IKmCalendarSyncMappingService) SpringBeanUtil
					.getBean("kmCalendarSyncMappingService");
		}
		return kmCalendarSyncMappingService;
	}

	@Override
	public String addCalendar(String token, String duserid, SyncroCommonCal cal)
			throws Exception {
		String url = DingConstant.DING_PREFIX + "/topapi/calendar/create"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("【钉钉接口  创建日程】" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCalendarCreateRequest request = new OapiCalendarCreateRequest();
		OapiCalendarCreateRequest.OpenCalendarCreateVo creatVo = new OapiCalendarCreateRequest.OpenCalendarCreateVo();
		creatVo.setUuid(cal.getCalendarId());
		creatVo.setBizId(cal.getCalendarId());
		creatVo.setCreatorUserid(duserid);
		creatVo.setDescription(cal.getContent());

		OapiCalendarCreateRequest.OpenCalendarSourceVo source = new OapiCalendarCreateRequest.OpenCalendarSourceVo();
		source.setTitle(cal.getSubject());
		if (StringUtil.isNotNull(cal.getRelationUrl())) {
			if (cal.getRelationUrl().startsWith("http")) {
				source.setUrl(cal.getRelationUrl());
			} else {
				source.setUrl(StringUtil.formatUrl(cal.getRelationUrl()));
			}
		} else {
			source.setUrl(StringUtil.formatUrl(
					"/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId="
							+ cal.getCalendarId()));
		}
		creatVo.setSource(source);

		if (cal.isAllDayEvent()) {
			Date start = cal.getEventStartTime();
			Date end = cal.getEventFinishTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
			calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
			calendar.set(java.util.Calendar.MINUTE, 0);
			calendar.set(java.util.Calendar.SECOND, 0);
			calendar.set(java.util.Calendar.MILLISECOND, 0);
			end = calendar.getTime();
			OapiCalendarCreateRequest.DatetimeVo endDV = new OapiCalendarCreateRequest.DatetimeVo();
			endDV.setUnixTimestamp(end.getTime());
			creatVo.setEndTime(endDV);

			calendar = Calendar.getInstance();
			calendar.setTime(start);
			calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
			calendar.set(java.util.Calendar.MINUTE, 0);
			calendar.set(java.util.Calendar.SECOND, 0);
			calendar.set(java.util.Calendar.MILLISECOND, 0);
			start = calendar.getTime();
			OapiCalendarCreateRequest.DatetimeVo startDV = new OapiCalendarCreateRequest.DatetimeVo();
			startDV.setUnixTimestamp(start.getTime());
			creatVo.setStartTime(startDV);
		} else {
			OapiCalendarCreateRequest.DatetimeVo startDV = new OapiCalendarCreateRequest.DatetimeVo();
			startDV.setUnixTimestamp(cal.getEventStartTime().getTime());
			creatVo.setStartTime(startDV);
			OapiCalendarCreateRequest.DatetimeVo endDV = new OapiCalendarCreateRequest.DatetimeVo();
			endDV.setUnixTimestamp(cal.getEventFinishTime().getTime());
			creatVo.setEndTime(endDV);
		}

		creatVo.setCalendarType("notification");
		creatVo.setReceiverUserids(Arrays.asList(duserid));
		creatVo.setSummary(cal.getSubject());
		creatVo.setLocation(cal.getEventLocation());

		OpenCalendarReminderVo remainder = new OpenCalendarReminderVo();
		// 获取日程提醒时间
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
			if (newbeforeTime > 1440) { // 大于一天按一天传
				newbeforeTime = 1440;
			}
			remainder.setMinutes(newbeforeTime.longValue());
			remainder.setRemindType("app");
			creatVo.setReminder(remainder);
		}
		request.setCreateVo(creatVo);
		OapiCalendarCreateResponse response = client.execute(request, token);
		logger.debug("钉钉返回新增的日程：" + response.getBody());
		if (response.getErrcode() == 0) {
			return response.getResult().getDingtalkCalendarId();
		} else {
			logger.error("钉钉返回新增的日程：" + response.getBody());
			throw new KmssRuntimeException(new KmssMessage(
					response.getBody()));
		}
	}

	@Override
	public boolean delCalendar(String token, String duserid, String uuid)
			throws Exception {
		String rtn = null;
		String url = DingConstant.DING_PREFIX + "/topapi/calendar/delete"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("【钉钉接口】日程删除：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiCalendarDeleteRequest req = new OapiCalendarDeleteRequest();
		req.setUserid(duserid);
		req.setCalendarId(uuid);
		OapiCalendarDeleteResponse rsp = client.execute(req, token);
		rtn = rsp.getBody();
		logger.debug("钉钉返回删除的日程：" + rtn);
		if (rsp.getErrcode() == 0) {
			return true;
		} else {
			logger.error("钉钉返回新增的日程：" + rtn);
			throw new KmssRuntimeException(new KmssMessage(rtn));
		}
	}

	@Override
	public boolean updateCalElement(String token, String personId,
			String duserid,
			SyncroCommonCal syncroCommonCal) throws Exception {
		boolean delFlag = delCalendar(token, duserid,
				syncroCommonCal.getUuid());
		if (delFlag) {
			String uuid = addCalendar(token, duserid, syncroCommonCal);
			if (StringUtil.isNotNull(uuid)) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"fdAppKey=:fdAppKey and fdAppUuid =:fdAppUuid");
				hqlInfo.setParameter("fdAppKey", syncroCommonCal.getAppKey());
				hqlInfo.setParameter("fdAppUuid", syncroCommonCal.getUuid());
				List<KmCalendarSyncMapping> list = getKmCalendarSyncMappingService()
						.findList(hqlInfo);
				for (KmCalendarSyncMapping mapping : list) {
					mapping.setFdAppUuid(uuid);
					getKmCalendarSyncMappingService().update(mapping);
				}
				return true;
			} else {
				logger.error("钉钉日程新建失败，导致修改钉钉日程失败("
						+ syncroCommonCal.getCalendarId() + ")");
				throw new Exception("钉钉日程删除失败，导致修改钉钉日程失败("
						+ syncroCommonCal.getCalendarId() + ")");
			}
		} else {
			logger.error("钉钉日程删除失败，导致修改钉钉日程失败("
					+ syncroCommonCal.getCalendarId() + ")");
			throw new Exception("钉钉日程删除失败，导致修改钉钉日程失败("
					+ syncroCommonCal.getCalendarId() + ")");
		}
	}



	@Override
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date) throws Exception {
		return null;
	}


	@Override
	public List<SyncroCommonCal> getDeletedCalElements(String personId, Date date) throws Exception {
		return null;
	}
}
