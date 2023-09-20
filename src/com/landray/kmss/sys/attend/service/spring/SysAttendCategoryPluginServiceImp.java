package com.landray.kmss.sys.attend.service.spring;

import java.lang.reflect.Method;
import java.util.Date;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryRuleForm;
import com.landray.kmss.sys.attend.forms.SysAttendCategoryTimeForm;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryPluginService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendPlugin;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class SysAttendCategoryPluginServiceImp implements ISysAttendCategoryPluginService {

	@SuppressWarnings("unchecked")
	@Override
	public void initFormSetting(SysAttendCategoryForm sysAttendCategoryForm, String modelName, String fdModelId)
			throws Exception {
		sysAttendCategoryForm.setFdType("2");// 签到类型：自定义
		sysAttendCategoryForm.setFdPeriodType("2");// 签到周期周期类型 ：自定义
		IExtension extension = AttendPlugin.getExtension(modelName);
		String providerName = Plugin.getParamValueString(extension, "initProvider");
		Object provider = SpringBeanUtil.getBean(providerName);
		String initMethod = Plugin.getParamValueString(extension, "initMethod");
		if (provider != null && StringUtil.isNotNull(initMethod)) {
			Class<?> clz = provider.getClass();
			Method method = clz.getMethod(initMethod, new Class[] { String.class });
			JSONObject result = (JSONObject) method.invoke(provider, fdModelId);
			if (result != null) {
				// 签到事项名称
				sysAttendCategoryForm.setFdName(result.has("subject") ? result.getString("subject") : "");
				// 签到时间
				String fdInTime = "";
				// 签到日期
				if (result.has("date")) {
					SysAttendCategoryTimeForm fdTime = new SysAttendCategoryTimeForm();
					Date date = new Date(result.getLong("date"));
					fdTime.setFdCategoryId(sysAttendCategoryForm.getFdId());
					fdTime.setFdTime(DateUtil.convertDateToString(date,
							DateUtil.TYPE_DATE, null));
					sysAttendCategoryForm.getFdTimes().add(fdTime);
					fdInTime = DateUtil.convertDateToString(date,
							DateUtil.TYPE_TIME, null);
				}
				// 签到时间
				AutoArrayList fdRule = sysAttendCategoryForm.getFdRule();
				SysAttendCategoryRuleForm ruleForm = null;
				if (fdRule.isEmpty()) {
					ruleForm = new SysAttendCategoryRuleForm();
					fdRule.add(ruleForm);
				}
				ruleForm = (SysAttendCategoryRuleForm) fdRule.get(0);
				ruleForm.setFdLateTime(AttendConstant.FD_SIGN_LATETIME);
				ruleForm.setFdInTime(fdInTime);
				sysAttendCategoryForm.setFdRule(fdRule);

				// 最早打卡时间
				sysAttendCategoryForm.setFdStartTime(result.getString("startTime"));
				// 打卡关闭时间
				sysAttendCategoryForm.setFdEndTime(result.getString("endTime"));
				// 签到人员
				if (result.has("targetIds") && result.has("targetNames")) {
					sysAttendCategoryForm.setFdTargetIds(result.getString("targetIds"));
					sysAttendCategoryForm.setFdTargetNames(result.getString("targetNames"));
				}
				if (result.has("managerId") && result.has("managerName")) {
					sysAttendCategoryForm.setFdManagerId(result.getString("managerId"));
					sysAttendCategoryForm.setFdManagerName(result.getString("managerName"));
				}
				// 与应用相关信息
				sysAttendCategoryForm.setFdAppId(result.has("appId") ? result.getString("appId") : "");
				sysAttendCategoryForm.setFdAppName(result.has("appName") ? result.getString("appName") : "");
				sysAttendCategoryForm.setFdAppKey(Plugin.getParamValueString(extension, "modelKey"));
				sysAttendCategoryForm.setFdAppUrl(result.has("appUrl") ? result.getString("appUrl") : "");
			}
		}
	}

}
