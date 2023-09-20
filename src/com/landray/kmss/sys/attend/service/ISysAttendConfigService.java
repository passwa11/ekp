package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendConfig;

import net.sf.json.JSONObject;

public interface ISysAttendConfigService extends IBaseService {
	// 获取kk极速打卡配置参数
	public JSONObject getKKConfig() throws Exception;

	/**
	 * 获取考勤通用配置信息(数据已配置缓存)
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysAttendConfig getSysAttendConfig() throws Exception;
}
