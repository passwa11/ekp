package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.integrate.IMeetingIntegratePluginUtil;
import com.landray.kmss.km.imeeting.integrate.interfaces.CommonVideoMetting;
import com.landray.kmss.km.imeeting.integrate.interfaces.CommonVideoMettingException;
import com.landray.kmss.km.imeeting.integrate.interfaces.CommonVideoMettingPerson;
import com.landray.kmss.km.imeeting.integrate.interfaces.IMeetingVideoPlugin;
import com.landray.kmss.km.imeeting.integrate.interfaces.IMeetingVideoProvider;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutVedioService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncMappingService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmImeetingOutVedioServiceImp extends BaseServiceImp implements
		IKmImeetingOutVedioService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingOutVedioServiceImp.class);


	private ISysOrgCoreService sysOrgCoreService;

	private IKmImeetingSyncMappingService kmImeetingSyncMappingService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setKmImeetingSyncMappingService(
			IKmImeetingSyncMappingService kmImeetingSyncMappingService) {
		this.kmImeetingSyncMappingService = kmImeetingSyncMappingService;
	}

	@Override
	public JSONObject addImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<IMeetingVideoPlugin> plugins = IMeetingIntegratePluginUtil
				.getVedioExtensionList();
		Map<String, String> resultMap = null;
		for (IMeetingVideoPlugin plugin : plugins) {
			IMeetingVideoProvider provider = plugin.getProvider();
			if(!provider.isEnabled()){
				continue;
			}
			CommonVideoMetting commonVideoMetting = this
					.initCommonVideoMeeting(kmImeetingMain);
			try {
				resultMap = provider.orderVideoMeeting(commonVideoMetting);
				KmImeetingSyncMapping mapping = new KmImeetingSyncMapping();
				mapping.setFdAppUuid(resultMap.get("conferenceId"));
				mapping.setFdMeetingId(kmImeetingMain.getFdId());
				mapping.setFdKey(plugin.getKey());
				mapping.setExtMsg(JSONObject.fromObject(resultMap).toString());
				kmImeetingSyncMappingService.getBaseDao().add(mapping);
			} catch (CommonVideoMettingException e) {
				logger.error("ekp会议接出到视频会议失败，失败原因:" + e.getKey() + ":"
						+ e.getMessage());
			}
		}
		return null;
	}

	@Override
	public JSONObject updateImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<IMeetingVideoPlugin> plugins = IMeetingIntegratePluginUtil
				.getVedioExtensionList();
		for (IMeetingVideoPlugin plugin : plugins) {
			IMeetingVideoProvider provider = plugin.getProvider();
			if(!provider.isEnabled()){
				continue;
			}
			String key = plugin.getKey();
			KmImeetingSyncMapping map = getSynMap(kmImeetingMain, key);
			if (map == null) {
				continue;
			}
			CommonVideoMetting commonVideoMetting = this
					.initCommonVideoMeeting(kmImeetingMain);
			try {
				provider.updateVideoMeeting(map.getFdAppUuid(),
						commonVideoMetting);
			} catch (CommonVideoMettingException e) {
				logger.error("ekp会议同步更新到视频会议失败，失败原因:" + e.getKey() + ":"
						+ e.getMessage());
			}
		}
		return null;
	}

	@Override
	public JSONObject deleteImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		return this.cacelImeeting(kmImeetingMain);
	}

	@Override
	public JSONObject cacelImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<IMeetingVideoPlugin> plugins = IMeetingIntegratePluginUtil
				.getVedioExtensionList();
		for (IMeetingVideoPlugin plugin : plugins) {
			IMeetingVideoProvider provider = plugin.getProvider();
			if(!provider.isEnabled()){
				continue;
			}
			String key = plugin.getKey();
			KmImeetingSyncMapping map = getSynMap(kmImeetingMain, key);
			if (map == null) {
				continue;
			}
			try {
				provider.cancelVideoMeeting(map.getFdAppUuid(), "");
			} catch (CommonVideoMettingException e) {
				logger.error("ekp会议同步取消频会议失败，失败原因:" + e.getKey() + ":"
						+ e.getMessage());
			}
		}
		return null;
	}

	@Override
	public String getVideoMeetingUrl(String meetingId) throws Exception {
		List<IMeetingVideoPlugin> plugins = IMeetingIntegratePluginUtil
				.getVedioExtensionList();
		String url = null;
		for (IMeetingVideoPlugin plugin : plugins) {
			IMeetingVideoProvider provider = plugin.getProvider();
			if(!provider.isEnabled()){
				continue;
			}
			String key = plugin.getKey();
			String conferenceUrl = getConferenceUrl(meetingId, key);
			if (StringUtil.isNull(conferenceUrl)) {
                continue;
            }
			SysOrgPerson me = UserUtil.getUser();
			url = provider.getVideoMeetingUrl(
					conferenceUrl,
					new CommonVideoMettingPerson(me.getFdName(), me
							.getFdLoginName()));
			if (StringUtil.isNotNull(url)) {
                break;
            }
		}
		return url;
	}

	private String getConferenceUrl(String meetingId, String key)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping");
		hqlInfo.setWhereBlock("kmImeetingSyncMapping.fdMeetingId =:fdMeetingId and kmImeetingSyncMapping.fdKey =:fdKey");
		hqlInfo.setParameter("fdMeetingId", meetingId);
		hqlInfo.setParameter("fdKey", key);
		List<KmImeetingSyncMapping> mappings = this.getBaseDao().findList(
				hqlInfo);
		if (!mappings.isEmpty()) {
			KmImeetingSyncMapping mapping = mappings.get(0);
			JSONObject json = JSONObject.fromObject(mapping.getExtMsg());
			String conferenceUrl = json.getString("conferenceId");
			if (StringUtil.isNotNull(conferenceUrl)) {
				return conferenceUrl;
			}
		}
		return null;
	}

	/**
	 * 初始化一个会议对象
	 */
	private CommonVideoMetting initCommonVideoMeeting(
			KmImeetingMain kmImeetingMain) throws Exception {
		List<CommonVideoMettingPerson> commonVideoMettingPersons = new ArrayList<CommonVideoMettingPerson>();
		// 与会人员
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
			List<SysOrgPerson> persons = sysOrgCoreService.expandToPerson(kmImeetingMain
					.getFdAttendPersons());
			for (SysOrgPerson person : persons) {
				CommonVideoMettingPerson commonVideoMettingPerson = new CommonVideoMettingPerson(
						person.getFdName(), person.getFdLoginName());
				if (StringUtil.isNotNull(person.getFdMobileNo())) {
					commonVideoMettingPerson.setMobile(person.getFdMobileNo());
				}
				if (StringUtil.isNotNull(person.getFdEmail())) {
					commonVideoMettingPerson.setEmail(person.getFdEmail());
				}
				commonVideoMettingPersons.add(commonVideoMettingPerson);
			}
		}
		int duration = (int) (kmImeetingMain.getFdHoldDuration() / 60d / 1000d);
		String startDate = DateUtil.convertDateToString(
				kmImeetingMain.getFdHoldDate(), DateUtil.PATTERN_DATETIME)
				+ ":00";
		CommonVideoMetting commonVideoMetting = new CommonVideoMetting(
				kmImeetingMain.getFdName(), duration, startDate,
				commonVideoMettingPersons);
		if (kmImeetingMain.getFdHost() != null) {
			CommonVideoMettingPerson chair = new CommonVideoMettingPerson(
					kmImeetingMain.getFdHost().getFdName(), kmImeetingMain
							.getFdHost().getFdLoginName());
			if (StringUtil
					.isNotNull(kmImeetingMain.getFdHost().getFdMobileNo())) {
				chair.setMobile(kmImeetingMain.getFdHost().getFdMobileNo());
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdHost().getFdEmail())) {
				chair.setEmail(kmImeetingMain.getFdHost().getFdEmail());
			}
			commonVideoMetting.setChair(chair);
		}
		return commonVideoMetting;
	}

	// 获取会议对应的映射对象
	private KmImeetingSyncMapping getSynMap(KmImeetingMain kmImeetingMain,
			String key)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping");
		hqlInfo.setWhereBlock("kmImeetingSyncMapping.fdMeetingId =:fdMeetingId");
		hqlInfo.setParameter("fdMeetingId", kmImeetingMain.getFdId());
		List<KmImeetingSyncMapping> mappings = this.getBaseDao().findList(
				hqlInfo);
		if (!mappings.isEmpty()) {
			for (KmImeetingSyncMapping mapping : mappings) {
				if (key.equals(mapping.getFdKey())) {
					return mapping;
				}
			}
		}
		return null;
	}


}