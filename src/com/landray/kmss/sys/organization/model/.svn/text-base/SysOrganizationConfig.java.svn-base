package com.landray.kmss.sys.organization.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 参数设置
 * 
 * @author 潘永辉 2016-7-6
 * 
 */
public class SysOrganizationConfig extends BaseAppConfig {
	/**
	 * 部门全路径
	 */
	public static final int DEPT_LEVEL_ALL = 1;
	/**
	 * 仅末级部门
	 */
	public static final int DEPT_LEVEL_ONLY_LAST = 2;
	/**
	 * 最近的X级
	 */
	public static final int DEPT_LEVEL_LATELY = 3;

	/**
	 * 默认人员权限的缓存时间（分钟）
	 */
	public static final int DEFAULT_CACHE_EXPIRE = 240;

	protected String orderGroupPerson; // 是否开启获取群组成员排序
	
	protected String realTimeSearch; // 是否实时搜索
	protected String isRelation; // 岗位名称关联部门名称
	protected String keepGroupUnique; // 是否保持组织架构和群组名唯一
	protected int kmssOrgDeptLevelDisplay; // 部门层级显示
	protected int kmssOrgDeptLevelDisplayLength; // 最近的X级
	protected int kmssOrgAddressDeptLevelDisplay; // 地址本部门层级显示
	protected int kmssOrgAddressDeptLevelDisplayLength; // 地址本最近的X级
	protected String showStaffingLevel;

	// #58684 如开启必填后，可以按类型（机构、部门、人员、岗位）自定义编号，前端录入的时候按类型校验唯一性，高级数据导入时同样需校验唯一性
	protected String isNoRequired; // 编号是否必填，默认非必填
	
	protected String isLoginSpecialChar; // 是否开启登录名允许特殊字符

	protected String isUserAuthCacheEnable;	//用户权限缓存是否开启
	protected Integer userAuthCacheExpire;	//用户权限缓存过期时间（分钟）
	protected String userAuthCacheLimitIds;	//用户权限缓存缓存范围

	public SysOrganizationConfig() throws Exception {
		super();
		String realTimeSearch = super.getValue("realTimeSearch");
		if (StringUtil.isNull(realTimeSearch)) {
			realTimeSearch = "true";
		}
		super.setValue("realTimeSearch", realTimeSearch);
		String isRelation = super.getValue("isRelation");
		if (StringUtil.isNull(isRelation)) {
			isRelation = "false";
		}
		
		super.setValue("isRelation", isRelation);
		String keepGroupUnique = super.getValue("keepGroupUnique");
		if (StringUtil.isNull(keepGroupUnique)) {
			keepGroupUnique = "false";
		}
		super.setValue("keepGroupUnique", keepGroupUnique);
		String kmssOrgDeptLevelDisplay = super.getValue(
				"kmssOrgDeptLevelDisplay");
		if (StringUtil.isNull(kmssOrgDeptLevelDisplay)) {
			// 默认是“仅末级部门”
			kmssOrgDeptLevelDisplay = String.valueOf(DEPT_LEVEL_ONLY_LAST);
		}
		super.setValue("kmssOrgDeptLevelDisplay", kmssOrgDeptLevelDisplay);
		String kmssOrgDeptLevelDisplayLength = super.getValue(
				"kmssOrgDeptLevelDisplayLength");
		if (StringUtil.isNull(kmssOrgDeptLevelDisplayLength)) {
			kmssOrgDeptLevelDisplayLength = "1";
		}
		super.setValue("kmssOrgDeptLevelDisplayLength",
				kmssOrgDeptLevelDisplayLength);

		String kmssOrgAddressDeptLevelDisplay = super.getValue(
				"kmssOrgAddressDeptLevelDisplay");
		if (StringUtil.isNull(kmssOrgAddressDeptLevelDisplay)) {
			// 默认是“仅末级部门”
			kmssOrgAddressDeptLevelDisplay = String
					.valueOf(DEPT_LEVEL_ONLY_LAST);
		}
		super.setValue("kmssOrgAddressDeptLevelDisplay",
				kmssOrgAddressDeptLevelDisplay);
		String kmssOrgAddressDeptLevelDisplayLength = super.getValue(
				"kmssOrgAddressDeptLevelDisplayLength");
		if (StringUtil.isNull(kmssOrgAddressDeptLevelDisplayLength)) {
			kmssOrgAddressDeptLevelDisplayLength = "1";
		}
		super.setValue("kmssOrgAddressDeptLevelDisplayLength",
				kmssOrgAddressDeptLevelDisplayLength);

		showStaffingLevel = super.getValue("showStaffingLevel");
		if (StringUtil.isNull(showStaffingLevel)) {
			showStaffingLevel = "true";
		}
		super.setValue("showStaffingLevel", showStaffingLevel);

		isNoRequired = super.getValue("isNoRequired");
		if (StringUtil.isNull(isNoRequired)) {
			isNoRequired = "false";
		}
		super.setValue("isNoRequired", isNoRequired);
		
		isLoginSpecialChar = super.getValue("isLoginSpecialChar");
		if (StringUtil.isNull(isLoginSpecialChar)) {
			isLoginSpecialChar = "false";
		}
		super.setValue("isLoginSpecialChar", isLoginSpecialChar);
		
		
		String orderGroupPerson = super.getValue("orderGroupPerson");
		if (StringUtil.isNull(orderGroupPerson)) {
			orderGroupPerson = "true";
		} 
		super.setValue("orderGroupPerson", orderGroupPerson);

		String isUserAuthCacheEnable = super.getValue("isUserAuthCacheEnable");
		if (StringUtil.isNull(isUserAuthCacheEnable)) {
			isUserAuthCacheEnable = "false";
		}
		super.setValue("isUserAuthCacheEnable", isUserAuthCacheEnable);

		String userAuthCacheExpire = super.getValue("userAuthCacheExpire");
		if (StringUtil.isNull(userAuthCacheExpire)) {
			this.userAuthCacheExpire = DEFAULT_CACHE_EXPIRE;
			super.setValue("userAuthCacheExpire", String.valueOf(this.userAuthCacheExpire));
		} else {
			this.userAuthCacheExpire = Integer.parseInt(userAuthCacheExpire);
		}

		// 默认关闭登录名大小写敏感
		String loginNameCase = getDataMap().get("loginNameCase");
		if (StringUtil.isNull(loginNameCase)) {
			loginNameCase = "1";
		}
		super.setValue("loginNameCase", loginNameCase);

		StringBuilder idBuilder = new StringBuilder();
		if(getDataMap().containsKey("userAuthCacheLimitIds")){
			idBuilder.append(getDataMap().get("userAuthCacheLimitIds"));
		}else{
			Map<String,String> map = new HashMap();
			for(String key : getDataMap().keySet()){
				if(key.startsWith("userAuthCacheLimitIds")){
					map.put(key,getDataMap().get(key));
				}
			}
			for (int i = 0; i < map.size(); i++) {
				String key = "userAuthCacheLimitIds" + i;
				idBuilder.append(map.get(key));
			}
		}
		super.setValue("userAuthCacheLimitIds", idBuilder.toString());

		StringBuilder nameBuilder = new StringBuilder();
		if(getDataMap().containsKey("userAuthCacheLimitNames")){
			nameBuilder.append(getDataMap().get("userAuthCacheLimitNames"));
		}else{
			Map<String,String> map = new HashMap();
			for(String key : getDataMap().keySet()){
				if(key.startsWith("userAuthCacheLimitNames")){
					map.put(key,getDataMap().get(key));
				}
			}
			for (int i = 0; i < map.size(); i++) {
				String key = "userAuthCacheLimitNames" + i;
				nameBuilder.append(map.get(key));
			}
		}
		super.setValue("userAuthCacheLimitNames", nameBuilder.toString());
	}

	@Override
	public String getJSPUrl() {
		return "/sys/organization/sysOrg_config.jsp";
	}

	

	public String getOrderGroupPerson() {
		//默认开启
		String orderGroupPerson = (String) getDataMap().get("orderGroupPerson");
		if (StringUtil.isNull(orderGroupPerson)) {
			orderGroupPerson = "true";
		}
		return orderGroupPerson;
	}
	
	public String getIsRelation() {
		String isRelation = (String) getDataMap().get("isRelation");
		if (StringUtil.isNull(isRelation)) {
			isRelation = "false";
		}
		return isRelation;
	}

	public String getRealTimeSearch() {
		String realTimeSearch = (String) getDataMap().get("realTimeSearch");
		if (StringUtil.isNull(realTimeSearch)) {
			realTimeSearch = "true";
		}
		return realTimeSearch;
	}

	public String getKeepGroupUnique() {
		String keepGroupUnique = (String) getDataMap().get("keepGroupUnique");
		if (StringUtil.isNull(keepGroupUnique)) {
			realTimeSearch = "false";
		}
		return keepGroupUnique;
	}

	public boolean isKeepGroupUnique() {
		String keepGroupUnique = (String) getDataMap().get("keepGroupUnique");
		if (StringUtil.isNotNull(keepGroupUnique)
				&& "true".equals(keepGroupUnique)) {
			return true;
		}
		return false;
	}

	public int getKmssOrgDeptLevelDisplay() {
		String _kmssOrgDeptLevelDisplay = (String) getDataMap()
				.get("kmssOrgDeptLevelDisplay");
		if (StringUtil.isNull(_kmssOrgDeptLevelDisplay)) {
			// 默认是“仅末级部门”
			kmssOrgDeptLevelDisplay = DEPT_LEVEL_ONLY_LAST;
		} else {
			kmssOrgDeptLevelDisplay = Integer.valueOf(_kmssOrgDeptLevelDisplay);
		}
		return kmssOrgDeptLevelDisplay;
	}

	public int getKmssOrgDeptLevelDisplayLength() {
		String _kmssOrgDeptLevelDisplayLength = (String) getDataMap()
				.get("kmssOrgDeptLevelDisplayLength");
		if (StringUtil.isNull(_kmssOrgDeptLevelDisplayLength)) {
			kmssOrgDeptLevelDisplayLength = 1;
		} else {
			kmssOrgDeptLevelDisplayLength = Integer
					.valueOf(_kmssOrgDeptLevelDisplayLength);
		}
		return kmssOrgDeptLevelDisplayLength;
	}

	public int getKmssOrgAddressDeptLevelDisplay() {
		String _kmssOrgAddressDeptLevelDisplay = (String) getDataMap()
				.get("kmssOrgAddressDeptLevelDisplay");
		if (StringUtil.isNull(_kmssOrgAddressDeptLevelDisplay)) {
			// 默认是“仅末级部门”
			kmssOrgAddressDeptLevelDisplay = DEPT_LEVEL_ONLY_LAST;
		} else {
			kmssOrgAddressDeptLevelDisplay = Integer
					.valueOf(_kmssOrgAddressDeptLevelDisplay);
		}
		return kmssOrgAddressDeptLevelDisplay;
	}

	public int getKmssOrgAddressDeptLevelDisplayLength() {
		String _kmssOrgAddressDeptLevelDisplayLength = (String) getDataMap()
				.get("kmssOrgAddressDeptLevelDisplayLength");
		if (StringUtil.isNull(_kmssOrgAddressDeptLevelDisplayLength)) {
			kmssOrgAddressDeptLevelDisplayLength = 1;
		} else {
			kmssOrgAddressDeptLevelDisplayLength = Integer
					.valueOf(_kmssOrgAddressDeptLevelDisplayLength);
		}
		return kmssOrgAddressDeptLevelDisplayLength;
	}

	public String getShowStaffingLevel() {
		String showStaffingLevel = (String) getDataMap()
				.get("showStaffingLevel");
		if (StringUtil.isNull(showStaffingLevel)) {
			showStaffingLevel = "true";
		}
		return showStaffingLevel;
	}
	
	public boolean isNoRequired() {
		String isNoRequired = (String) getDataMap().get("isNoRequired");
		if (StringUtil.isNotNull(isNoRequired)
				&& "true".equals(isNoRequired)) {
			return true;
		}
		return false;
	}

	public String getIsLoginSpecialChar() {
		String isLoginSpecialChar = (String) getDataMap()
				.get("isLoginSpecialChar");
		if (StringUtil.isNull(isLoginSpecialChar)) {
			isLoginSpecialChar = "false";
		}
		return isLoginSpecialChar;
	}
	
	@Override
	public String getModelDesc() {
		return ResourceUtil.getString("sys-organization:sysOrgPerson.config.title");
	}

	public Boolean getIsUserAuthCacheEnable() {
		return "true".equalsIgnoreCase(getDataMap().get("isUserAuthCacheEnable"));
	}

	public Integer getUserAuthCacheExpire() {
		String userAuthCacheExpire = getDataMap().get("userAuthCacheExpire");
		if (StringUtil.isNull(userAuthCacheExpire)) {
			this.userAuthCacheExpire = DEFAULT_CACHE_EXPIRE;
		} else {
			this.userAuthCacheExpire = Integer.parseInt(userAuthCacheExpire);
		}
		return this.userAuthCacheExpire;
	}

	public String getUserAuthCacheLimitIds() {
		return getDataMap().get("userAuthCacheLimitIds");
	}

	/**
	 * 登录时大小写敏感
	 * 1：保持原样
	 * 2：区分大小写
	 * 3：忽略大小写
	 */
	public String getLoginNameCase() {
		String loginNameCase = getDataMap().get("loginNameCase");
		if (StringUtil.isNull(loginNameCase)) {
			loginNameCase = "1";
		}
		return loginNameCase;
	}

}
