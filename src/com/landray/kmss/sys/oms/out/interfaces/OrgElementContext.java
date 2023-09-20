package com.landray.kmss.sys.oms.out.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 组织架构元素封装
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OrgElementContext {

	private SysOrgElement orgElement;

	public SysOrgElement getOrgElement() {
		return orgElement;
	}

	public OrgElementContext(SysOrgElement orgElement) {
		setOrgElement(orgElement);
	}

	public void setOrgElement(SysOrgElement orgElement) {
		this.orgElement = orgElement;
	}

	public String getFdId() {
		return orgElement.getFdId();
	}

	/*
	 * 类型
	 */
	public Integer getFdOrgType() {
		return orgElement.getFdOrgType();
	}

	/*
	 * 名称
	 */
	public String getFdName() {
		return orgElement.getFdNameOri();
	}

	/*
	 * 编号
	 */
	public String getFdNo() {
		return orgElement.getFdNo();
	}

	/*
	 * 排序号
	 */
	public Integer getFdOrder() {
		return orgElement.getFdOrder();
	}

	/*
	 * 关键字
	 */
	public String getFdKeyword() {
		return orgElement.getFdKeyword();
	}

	/*
	 * 是否有效
	 */
	public Boolean getFdIsAvailable() {
		return orgElement.getFdIsAvailable();
	}

	/*
	 * 是否业务相关
	 */
	public Boolean getFdIsBusiness() {
		return orgElement.getFdIsBusiness();
	}

	/*
	 * 导入的数据的主键OMS
	 */
	public String getFdImportInfo() {
		return orgElement.getFdImportInfo();
	}

	/*
	 * 描述
	 */
	public String getFdMemo() {
		return orgElement.getFdMemo();
	}

	/*
	 * 本级领导
	 */
	public OrgElementContext getThisLeader() {
		if (orgElement.getHbmThisLeader() == null) {
            return null;
        }
		return new OrgElementContext(orgElement.getHbmThisLeader());
	}

	/*
	 * 上级领导
	 */
	public OrgElementContext getSuperLeader() {
		if (orgElement.getHbmSuperLeader() == null) {
            return null;
        }
		return new OrgElementContext(orgElement.getHbmSuperLeader());
	}

	/*
	 * 父
	 */
	public OrgElementContext getFdParent() {
		if (orgElement.getHbmParent() == null) {
            return null;
        }
		return new OrgElementContext(orgElement.getHbmParent());
	}

	/*
	 * 父机构
	 */
	public OrgElementContext getFdParentOrg() {
		if (orgElement.getHbmParentOrg() == null) {
            return null;
        }
		return new OrgElementContext(orgElement.getHbmParentOrg());
	}

	public String getFdHierarchyId() {
		return orgElement.getFdHierarchyId();
	}

	public Boolean getFdIsAbandon() {
		Boolean abandon = orgElement.getFdIsAbandon();
		if (null == abandon) {
			return Boolean.FALSE;
		}
		return abandon;
	}

}
