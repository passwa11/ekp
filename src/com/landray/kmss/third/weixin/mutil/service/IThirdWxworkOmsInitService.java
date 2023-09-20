package com.landray.kmss.third.weixin.mutil.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.weixin.mutil.api.WxmutilApiService;

import net.sf.json.JSONObject;
/**
 * 组织初始化业务对象接口
 * 
 * @author
 * @version 1.0 2017-06-08
 */
public interface IThirdWxworkOmsInitService extends IBaseService {
	/**
	 * @param wxCpService
	 * @throws Exception
	 *             获取微信企业号的所有人员
	 */
	public void getWxAllPersons(WxmutilApiService wxmutilApiService,
			String fdWxKey) throws Exception;

	/**
	 * @param wxCpService
	 * @throws Exception
	 *             获取微信企业号的所有部门
	 */
	public void getWxAllDeparts(WxmutilApiService wxmutilApiService,
			String fdWxKey) throws Exception;

	/**
	 * @throws Exception
	 *             获取EKP系统的所有人员
	 */
	public void getEKPAllPersons() throws Exception;

	/**
	 * @throws Exception
	 *             获取EKP系统的所有部门
	 */
	public void getEKPAllDeparts(String wxKey) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 *             微信企业号和EKP系统的部门匹配（完整部门层级路径+部门名称）
	 */
	public Map<String, List> deptMatch(String fdWxKey) throws Exception;

	/**
	 * @param map
	 * @throws Exception
	 *             保存匹配不上的部门信息
	 */
	public void deptSave(Map<String, List> map) throws Exception;

	/**
	 * @throws Exception
	 *             部门初始化
	 */
	public void updateDept(JSONObject json, String fdWxKey) throws Exception;

	/**
	 * @return
	 * @throws Exception
	 *             微信企业号和EKP系统的人员匹配（登录名/手机号/邮件）
	 */
	public Map<String, List> personMatch(String fdWxKey) throws Exception;

	/**
	 * @param map
	 * @throws Exception
	 *             保存匹配不上的人员信息
	 */
	public void personSave(Map<String, List> map) throws Exception;

	/**
	 * @throws Exception
	 *             人员初始化
	 */
	public void updatePerson(JSONObject json, String fdWxKey) throws Exception;
	
	public void updatePerson() throws Exception;

	/**
	 * @param fdId
	 * @param wxId
	 * @param type
	 * @throws Exception
	 *             在企业号中删除对应的部门或者人员
	 */
	public void updateWx(String fdId, String wxId, String type, String fdWxKey)
			throws Exception;

	/**
	 * @param fdId
	 * @param fdEKPId
	 * @param type
	 * @throws Exception
	 *             在EKP中设置匹配的部门或者人员
	 */
	public boolean updateEKP(String fdId, String fdEKPId, String type)
			throws Exception;
}
