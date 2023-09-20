package com.landray.kmss.sys.organization.webservice.eco;

import java.util.List;

import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoOrg;


/**
 * 生态组织WebService接口上下文
 * 
 * @author panyh
 *
 *         2020年9月7日 下午3:06:24
 */
public class SysSynchroEcoContext {

	/**
	 * 批量新增或更新的组织数据
	 */
	private List<SysEcoOrg> orgs;

	/**
	 * 上级组织ID，为空时返回所有一级组织
	 */
	private String parent;

	/**
	 * 根据ID查询数据
	 */
	private String id;

	/**
	 * 查询状态
	 */
	private Boolean isAvailable;

	/**
	 * 查询
	 */
	private String search;

	/**
	 * 设置需要返回的组织架构类型 说明: 可为空. 设置需要返回的组织架构类型,JSON数组
	 * 如:[{"type":"person"},{"type":"post"},{"type":"dept"}]等, 值为空, 表示全部.
	 * 
	 */
	private String returnOrgType;

	public List<SysEcoOrg> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<SysEcoOrg> orgs) {
		this.orgs = orgs;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getReturnOrgType() {
		return returnOrgType;
	}

	public void setReturnOrgType(String returnOrgType) {
		this.returnOrgType = returnOrgType;
	}

}
