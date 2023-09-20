package com.landray.kmss.third.weixin.work.oms;

import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import net.sf.json.JSONObject;

/**
 * 企业微信到EKP组织架构同步接口
 */
public interface SynchroOrgWxwork2Ekp {
	
	/**
	 * 定时任务
	 */
	public void triggerSynchro(SysQuartzJobContext context);
	
	/**
	 * 处理企业微信推送回来的部门信息 flag=true为add,flag=false为update
	 */
	public void saveOrUpdateCallbackDept(JSONObject element, boolean flag) throws Exception;

	/**
	 * 删除企业微信推送回来的部门
	 */
	public void deleteCallbackDept(Integer deptId) throws Exception;

	/**
	 * 处理单个企业微信用户信息 flag=true为add,flag=false为update
	 */
	public void saveOrUpdateCallbackUser(JSONObject element, boolean flag) throws Exception;
	
	/**
	 * 删除企业微信推送回来的用户
	 */
	public void deleteCallbackUser(String userid) throws Exception;

	
}
