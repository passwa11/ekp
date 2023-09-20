package com.landray.kmss.sys.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 默认排序、默认密码设置
 * 
 * @author yezhengping 2018-08-01
 *
 */
public class SysOrgDefaultConfig extends BaseAppConfig  {

	protected Integer orgPersonDefaultOrder; // 默认人员排序号
	protected Integer orgOrgDefaultOrder; // 默认机构排序号
	protected Integer orgPostDefaultOrder; // 默认岗位排序号
	protected Integer orgGroupDefaultOrder; // 默认群组排序号
	protected Integer orgDeptDefaultOrder; // 默认部门排序号
	protected String orgDefaultPassword; //默认初始密码
	
	public SysOrgDefaultConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		//无参构造初始化newInstance-时，没有缓存数据默认密码为空展示也为空，当为空时此处需要设置默认密码 #152413
		getOrgDefaultPassword();
	}
	
	@Override
	public String getJSPUrl() {
		// TODO Auto-generated method stub
		return "/sys/organization/sysOrg_default_config.jsp";
	}
	
	
	public Integer getOrgPersonDefaultOrder() {
		String _orgPersonDefaultOrder = (String) getDataMap()
				.get("orgPersonDefaultOrder");
		if (StringUtil.isNotNull(_orgPersonDefaultOrder)) {
			orgPersonDefaultOrder = Integer
					.valueOf(_orgPersonDefaultOrder);
		}
		return orgPersonDefaultOrder;
	}
	
	public Integer getOrgOrgDefaultOrder() {
		String _orgOrgDefaultOrder = (String) getDataMap()
				.get("orgOrgDefaultOrder");
		if (StringUtil.isNotNull(_orgOrgDefaultOrder)) {
			orgOrgDefaultOrder = Integer
					.valueOf(_orgOrgDefaultOrder);
		}
		return orgOrgDefaultOrder;
	}
	
	public Integer getOrgPostDefaultOrder() {
		String _orgPostDefaultOrder = (String) getDataMap()
				.get("orgPostDefaultOrder");
		if (StringUtil.isNotNull(_orgPostDefaultOrder)) {
			orgPostDefaultOrder = Integer
					.valueOf(_orgPostDefaultOrder);
		}
		return orgPostDefaultOrder;
	}
	
	public Integer getOrgGroupDefaultOrder() {
		String _orgGroupDefaultOrder = (String) getDataMap()
				.get("orgGroupDefaultOrder");
		if (StringUtil.isNotNull(_orgGroupDefaultOrder)) {
			orgGroupDefaultOrder = Integer
					.valueOf(_orgGroupDefaultOrder);
		}
		return orgGroupDefaultOrder;
	}
	
	public Integer getOrgDeptDefaultOrder() {
		String _orgDeptDefaultOrder = (String) getDataMap()
				.get("orgDeptDefaultOrder");
		if (StringUtil.isNotNull(_orgDeptDefaultOrder)) {
			orgDeptDefaultOrder = Integer
					.valueOf(_orgDeptDefaultOrder);
		}
		return orgDeptDefaultOrder;
	}
	
	public String getOrgDefaultPassword() {
		String orgDefaultPassword = getDataMap().get("orgDefaultPassword");
		//后台默认值配置中，默认将初始密码设置为：123456 #152413
		if(StringUtil.isNull(orgDefaultPassword)){
			orgDefaultPassword = "123456";
			getDataMap().put("orgDefaultPassword",orgDefaultPassword);
		}
		return orgDefaultPassword;
	}

	@Override
	public void save() throws Exception {
		this.getOrgDefaultPassword();
		super.save();
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-organization:appConfig.SysOrgDefaultConfig");
	}
	
}
