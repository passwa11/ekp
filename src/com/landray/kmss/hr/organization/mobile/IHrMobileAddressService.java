package com.landray.kmss.hr.organization.mobile;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;

import net.sf.json.JSONObject;

public interface IHrMobileAddressService {

	//地址本数据
	public List addressList(RequestContext xmlContext) throws Exception;
	
	//获取详细信息
	public List detailList(RequestContext xmlContext) throws Exception;

	//搜索结果数据
	public List searchList(RequestContext xmlContext) throws Exception;

	// 人员数据
	public JSONObject personList(RequestContext xmlContext) throws Exception;

	// 所有人员数据
	public JSONObject personDetailList(RequestContext xmlContext)
			throws Exception;
	
	/**
	 * 获取同部门成员
	 */
	public List getDeptMembers(RequestContext xmlContext) throws Exception;

	/**
	 * 获取下属组织架构路径
	 */
	public List getSubordinatePath(RequestContext xmlContext)
			throws Exception;

	/**
	 * 获取下属组织架构
	 */
	public List getAllSubordinateOrgs(RequestContext xmlContext)
			throws Exception;

	/**
	 * 获取公共群组路径
	 */
	public List commonGroupPath(RequestContext xmlContext)
			throws Exception;

	/**
	 * 获取公共群组分类
	 */
	public List commonGroupCate(RequestContext xmlContext)
			throws Exception;

	/**
	 * 获取公共群组
	 */
	public List commonGroupList(RequestContext xmlContext)
			throws Exception;

	/**
	 * 获取个人群组
	 */
	public List myGroupList(RequestContext xmlContext)
			throws Exception;
	
	//高级地址本自定义数据
	public List addCustomList(RequestContext requestContext) throws Exception;
}
