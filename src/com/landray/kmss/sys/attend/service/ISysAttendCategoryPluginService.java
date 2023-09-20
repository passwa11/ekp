package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.attend.forms.SysAttendCategoryForm;

/**
 * 签到事项拓展服务接口
 */
public interface ISysAttendCategoryPluginService {

	public void initFormSetting(SysAttendCategoryForm sysAttendCategoryForm, String servicebean, String fdModelId)
			throws Exception;

}
