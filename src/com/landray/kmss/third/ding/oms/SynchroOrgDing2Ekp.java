package com.landray.kmss.third.ding.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import net.sf.json.JSONObject;
/**
 * 钉钉到EKP组织架构同步接口
 */
public interface SynchroOrgDing2Ekp {
	
	/**
	 * 生成映射关系
	 */
	public void generateMapping() throws Exception;

	/**
	 * 定时任务
	 */
	public void triggerSynchro(SysQuartzJobContext context);
	
	/**
	 * 处理钉钉推送回来的部门信息
	 * flag=true为add,flag=false为update
	 */
	public void saveOrUpdateCallbackDept(JSONObject element, boolean flag) throws Exception;

	/**
	 * 删除钉钉推送回来的部门
	 */
	public void deleteCallbackDept(Long deptId) throws Exception;

	/**
	 * 处理单个钉钉用户信息
	  * flag=true为add,flag=false为update
	 */
	public void saveOrUpdateCallbackUser(JSONObject element, boolean flag) throws Exception;
	
	/**
	 * 删除钉钉推送回来的部门
	 */
	public void deleteCallbackUser(String userid) throws Exception;

	/**
	 * 更新ekp用户手机号码
	 */
	public void saveOrUpdateCallBackMobile(JSONObject element) throws Exception;
	
	/**
	 * 后台组织架构同步
	 */
	public void triggerSynchro() throws Exception;

	/**
	 * 处理钉钉管理员的回调 org_admin_add org_admin_remove
	 * 
	 * @param admin_userid
	 *            钉钉userId
	 * @param isAdd
	 *            true:新增 ；false:删除
	 */
	public void saveOrUpdateCallbackDingAdmin(String admin_userid,
			boolean isAdd);


}
