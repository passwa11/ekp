package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncMapping;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutCacheService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncMappingService;
import com.landray.kmss.km.imeeting.synchro.ImeetingSynchroPluginData;
import com.landray.kmss.km.imeeting.synchro.SynchroPlugin;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingSynchroProvider;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroCommonMetting;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroMeetingResponse;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * 通用同步接出实现
 */
public class KmImeetingOutCacheServiceImp extends BaseServiceImp implements
		IKmImeetingOutCacheService {

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
		List<ImeetingSynchroPluginData> extensionList = SynchroPlugin
				.getExtensionList();
		for (ImeetingSynchroPluginData plugin : extensionList) {
			IMeetingSynchroProvider provier = plugin.getProvider();
			if (!provier.isSynchroEnable()) {
				continue;
			}
			String key = plugin.getKey();
			Set<String> requiredAttendees = getSyncPersonId(kmImeetingMain);
			SynchroCommonMetting data = new SynchroCommonMetting(
					kmImeetingMain.getFdName(), kmImeetingMain.getFdHoldDate(),
					kmImeetingMain.getFdFinishDate(), requiredAttendees,
					kmImeetingMain.getDocCreator().getFdId());
			data.setOptionalAttendees(new HashSet<String>());
			data.setResourceAttendees(new HashSet<String>());
			String location = "";
			if(kmImeetingMain.getFdPlace()!=null){
				location += kmImeetingMain.getFdPlace().getFdName();
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				location += kmImeetingMain.getFdOtherPlace();
			}
			data.setLocation(location);
			data.setBody(kmImeetingMain.getFdMeetingAim());
			String uuid = provier.sendMeeting(data);
			// 保存同步映射
			KmImeetingSyncMapping mapping = new KmImeetingSyncMapping();
			if (uuid.contains("###")) {
				mapping.setFdAppUuid(uuid.substring(0, uuid.indexOf("###")));
				mapping.setFdAppIcalId(uuid.substring(uuid.indexOf("###") + 3));
			} else {
				mapping.setFdAppUuid(uuid);
			}
			mapping.setFdMeetingId(kmImeetingMain.getFdId());
			mapping.setFdKey(key);
			kmImeetingSyncMappingService.getBaseDao().add(mapping);
		}
		return null;
	}

	@Override
	public JSONObject updateImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<ImeetingSynchroPluginData> extensionList = SynchroPlugin
				.getExtensionList();
		for (ImeetingSynchroPluginData plugin : extensionList) {
			IMeetingSynchroProvider provier = plugin.getProvider();
			if (!provier.isSynchroEnable()) {
				continue;
			}
			String key = plugin.getKey();
			String uuid = getUUId(kmImeetingMain, key);
			if (StringUtil.isNull(uuid)) {
				continue;
			}
			Set<String> requiredAttendees = getSyncPersonId(kmImeetingMain);
			SynchroCommonMetting data = new SynchroCommonMetting(
					kmImeetingMain.getFdName(), kmImeetingMain.getFdHoldDate(),
					kmImeetingMain.getFdFinishDate(), requiredAttendees,
					kmImeetingMain.getDocCreator().getFdId());
			data.setOptionalAttendees(new HashSet<String>());
			data.setResourceAttendees(new HashSet<String>());
			String location = "";
			if (kmImeetingMain.getFdPlace() != null) {
				location += kmImeetingMain.getFdPlace().getFdName();
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				location += kmImeetingMain.getFdOtherPlace();
			}
			data.setLocation(location);
			data.setBody(kmImeetingMain.getFdMeetingAim());
			provier.updateMeeting(uuid, data);
		}
		return null;
	}

	@Override
	public JSONObject deleteImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<ImeetingSynchroPluginData> extensionList = SynchroPlugin
				.getExtensionList();
		for (ImeetingSynchroPluginData plugin : extensionList) {
			IMeetingSynchroProvider provier = plugin.getProvider();
			if (!provier.isSynchroEnable()) {
				continue;
			}
			String key = plugin.getKey();
			String uuid = getUUId(kmImeetingMain, key);
			if (StringUtil.isNull(uuid)) {
				continue;
			}
			Set<String> requiredAttendees = getSyncPersonId(kmImeetingMain);
			SynchroCommonMetting data = new SynchroCommonMetting(
					kmImeetingMain.getFdName(), kmImeetingMain.getFdHoldDate(),
					kmImeetingMain.getFdFinishDate(), requiredAttendees,
					kmImeetingMain.getDocCreator().getFdId());
			data.setOptionalAttendees(new HashSet<String>());
			data.setResourceAttendees(new HashSet<String>());
			String location = "";
			if (kmImeetingMain.getFdPlace() != null) {
				location += kmImeetingMain.getFdPlace().getFdName();
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				location += kmImeetingMain.getFdOtherPlace();
			}
			data.setLocation(location);
			provier.deleteMeeting(uuid, data);
		}
		return null;
	}

	@Override
	public JSONObject cacelImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<ImeetingSynchroPluginData> extensionList = SynchroPlugin
				.getExtensionList();
		for (ImeetingSynchroPluginData plugin : extensionList) {
			IMeetingSynchroProvider provier = plugin.getProvider();
			if (!provier.isSynchroEnable()) {
				continue;
			}
			String key = plugin.getKey();
			String uuid = getUUId(kmImeetingMain, key);
			if (StringUtil.isNull(uuid)) {
				continue;
			}
			Set<String> requiredAttendees = getSyncPersonId(kmImeetingMain);
			SynchroCommonMetting data = new SynchroCommonMetting(
					kmImeetingMain.getFdName(), kmImeetingMain.getFdHoldDate(),
					kmImeetingMain.getFdFinishDate(), requiredAttendees,
					kmImeetingMain.getDocCreator().getFdId());
			data.setOptionalAttendees(new HashSet<String>());
			data.setResourceAttendees(new HashSet<String>());
			String location = "";
			if (kmImeetingMain.getFdPlace() != null) {
				location += kmImeetingMain.getFdPlace().getFdName();
			}
			if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				location += kmImeetingMain.getFdOtherPlace();
			}
			data.setLocation(location);
			data.setBody(kmImeetingMain.getFdMeetingAim());
			provier.cancelMeeting(uuid, data,
					kmImeetingMain.getCancelMeetingReason());
		}
		return null;
	}

	@Override
	public List<SynchroMeetingResponse> getMeetingResponseList(
			KmImeetingMain kmImeetingMain) throws Exception {
		List<SynchroMeetingResponse> result = new ArrayList<SynchroMeetingResponse>();
		List<ImeetingSynchroPluginData> extensionList = SynchroPlugin
				.getExtensionList();
		if (!extensionList.isEmpty()) {
			ImeetingSynchroPluginData plugin = extensionList.get(0);
			IMeetingSynchroProvider provier = plugin.getProvider();
			if (!provier.isSynchroEnable()) {
				return result;
			}
			String key = plugin.getKey();
			String uuid = this.getUUId(kmImeetingMain, key);
			if (StringUtil.isNull(uuid)) {
				return result;
			}
			result = provier.getMeetingResponseList(kmImeetingMain
					.getDocCreator().getFdId(), uuid);
		}
		return result;
	}

	// 获取会议对应的UUID
	private String getUUId(KmImeetingMain kmImeetingMain, String key)
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
					return mapping.getFdAppUuid();
				}
			}
		}
		return null;
	}

	// 获取参会人员Id
	private Set<String> getSyncPersonId(KmImeetingMain kmImeetingMain)
			throws Exception {
		Set<String> set = new HashSet<String>();
		// 主持人
		if (kmImeetingMain.getFdHost() != null) {
			set.add(kmImeetingMain.getFdHost().getFdId());
		}
		// 与会人员
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
			List<String> personIds = sysOrgCoreService
					.expandToPersonIds(kmImeetingMain.getFdAttendPersons());
			set.addAll(personIds);
		}
		// 列席人员
		if (kmImeetingMain.getFdParticipantPersons() != null
				&& !kmImeetingMain.getFdParticipantPersons().isEmpty()) {
			List<String> personIds = sysOrgCoreService
					.expandToPersonIds(kmImeetingMain.getFdParticipantPersons());
			set.addAll(personIds);
		}
		return set;
	}


}
