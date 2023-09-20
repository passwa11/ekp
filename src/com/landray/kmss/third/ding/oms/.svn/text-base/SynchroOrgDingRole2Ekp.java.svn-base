package com.landray.kmss.third.ding.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import net.sf.json.JSONObject;

/**
 * 钉钉到EKP组织架构同步接口
 */
public interface SynchroOrgDingRole2Ekp {

	/**
	 * 处理钉钉角色回调
	 */
	public void saveOrUpdateRolesCallback(JSONObject roleCallbackData)
			throws Exception;

	/**
	 * 处理人员角色变更
	 * 
	 * @param plainTextJson
	 * @throws Exception
	 */
	public void saveOrUpdateUserRolesCallback(JSONObject plainTextJson)
			throws Exception;

	/**
	 * 同步角色的定时任务
	 * @param context
	 */
	public void synDingRoles(SysQuartzJobContext context);

	/**
	 * 同步角色
	 * @param context
	 */
	public void synchro(SysQuartzJobContext context) throws Exception;

}
