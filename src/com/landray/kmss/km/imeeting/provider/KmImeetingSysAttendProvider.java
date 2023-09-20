package com.landray.kmss.km.imeeting.provider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONObject;

public class KmImeetingSysAttendProvider {

	private IKmImeetingMainService kmImeetingService;

	public void setKmImeetingService(IKmImeetingMainService kmImeetingService) {
		this.kmImeetingService = kmImeetingService;
	}

	public JSONObject getInitSysAttendCategory(String modelId) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) kmImeetingService.findByPrimaryKey(modelId);
		if (kmImeetingMain != null) {
			JSONObject json = new JSONObject();
			// 默认取会议名称为签到事项名称
			json.accumulate("subject", kmImeetingMain.getFdName());
			// 签到时间
			json.accumulate("date",kmImeetingMain.getFdHoldDate().getTime());
			Date holdDate = kmImeetingMain.getFdHoldDate();
			Calendar beforeHoldDate = Calendar.getInstance();
			beforeHoldDate.setTime(holdDate);
			beforeHoldDate.add(Calendar.HOUR_OF_DAY, -1);
			// 打卡开始时间
			json.accumulate("startTime",
					DateUtil.convertDateToString(beforeHoldDate.getTime(), DateUtil.TYPE_TIME, null));
			// 打卡关闭时间
			json.accumulate("endTime",
					DateUtil.convertDateToString(
							kmImeetingMain.getFdFinishDate(),
							DateUtil.TYPE_TIME, null));
			// 签到人
			List<SysOrgElement> attendTargets = new ArrayList<SysOrgElement>();
			attendTargets.addAll(kmImeetingMain.getFdAttendPersons());
			attendTargets.addAll(kmImeetingMain.getFdParticipantPersons());
			StringBuffer targetIds = new StringBuffer();
			StringBuffer targetNames = new StringBuffer();
			for(int i = 0 ; i < attendTargets.size();i++){
				SysOrgElement attendTarget = attendTargets.get(i);
				targetIds.append(attendTarget.getFdId());
				targetNames.append(attendTarget.getFdName());
				if (i < attendTargets.size() - 1) {
					targetIds.append(";");
					targetNames.append(";");
				}
			}
			json.accumulate("targetIds", targetIds.toString());
			json.accumulate("targetNames", targetNames.toString());
			if (kmImeetingMain.getFdEmcee() != null) {
				json.accumulate("managerId", kmImeetingMain.getFdEmcee().getFdId());
				json.accumulate("managerName", kmImeetingMain.getFdEmcee().getFdName());
			}
			// 应用相关
			json.accumulate("appId", modelId);
			json.accumulate("appName", ResourceUtil.getString("module.km.imeeting", "km-imeeting"));
			json.accumulate("appUrl", "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=" + modelId);
			return json;
		}
		return null;
	}

}
