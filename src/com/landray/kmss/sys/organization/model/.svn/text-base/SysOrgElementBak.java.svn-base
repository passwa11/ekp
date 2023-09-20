package com.landray.kmss.sys.organization.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.util.ObjectUtil;

/**
 * 组织架构元素
 * 
 * @author 叶中奇
 */
@SuppressWarnings("unchecked")
public class SysOrgElementBak extends BaseModel implements SysOrgConstant,
		IBaseModel {
	private static final long serialVersionUID = -8078238937733505633L;

	static {
		ModelConvertor_Common.addCacheClass(SysOrgElementBak.class);
	}

	/*
	 * 类型
	 */
	private Integer fdOrgType = 0;

	public Integer getFdOrgType() {
		return fdOrgType;
	}

	public void setFdOrgType(Integer fdOrgType) {
		this.fdOrgType = fdOrgType;
	}

	/*
	 * 层级ID
	 */
	protected String fdHierarchyId = BaseTreeConstant.HIERARCHY_ID_SPLIT
			+ getFdId() + BaseTreeConstant.HIERARCHY_ID_SPLIT;

	public String getFdHierarchyId() {
		return fdHierarchyId;
	}

	public void setFdHierarchyId(String fdHierarchyId) {
		this.fdHierarchyId = fdHierarchyId;
	}

	/*
	 * 名称
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/*
	 * 名称拼音
	 */
	private String fdNamePinYin;

	/*
	 * 编号
	 */
	private String fdNo;

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	/*
	 * 排序号
	 */
	private Integer fdOrder;

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/*
	 * 关键字
	 */
	private String fdKeyword;

	public String getFdKeyword() {
		return fdKeyword;
	}

	public void setFdKeyword(String fdKeyword) {
		this.fdKeyword = fdKeyword;
	}

	/*
	 * 是否有效
	 */
	private Boolean fdIsAvailable;

	public Boolean getFdIsAvailable() {
		if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	/*
	 * 是否业务相关
	 */
	private Boolean fdIsBusiness;

	public Boolean getFdIsBusiness() {
		if (fdIsBusiness == null) {
            fdIsBusiness = Boolean.TRUE;
        }
		return fdIsBusiness;
	}

	public void setFdIsBusiness(Boolean fdIsBusiness) {
		this.fdIsBusiness = fdIsBusiness;
	}

	/*
	 * 导入的数据的对应键值 update by wubing date:2006-12-14
	 */
	private String fdImportInfo;

	public String getFdImportInfo() {
		return fdImportInfo;
	}

	public void setFdImportInfo(String fdImportInfo) {
		this.fdImportInfo = fdImportInfo;
	}

	/*
	 * OMS导入时使用的字段，业务扩展时使用
	 */
	private String fdLdapDN;

	public String getFdLdapDN() {
		return fdLdapDN;
	}

	public void setFdLdapDN(String fdLdapDN) {
		this.fdLdapDN = fdLdapDN;
	}

	/*
	 * OMS导入时使用的字段，业务上务任何意义
	 */
	private Boolean fdFlagDeleted;

	public Boolean getFdFlagDeleted() {
		return fdFlagDeleted;
	}

	public void setFdFlagDeleted(Boolean fdFlagDeleted) {
		this.fdFlagDeleted = fdFlagDeleted;
	}

	/*
	 * 描述
	 */
	private String fdMemo;

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}

	/*
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/*
	 * 最后修改时间
	 */
	private Date fdAlterTime = new Date();

	public Date getFdAlterTime() {
		return fdAlterTime;
	}

	public void setFdAlterTime(Date fdAlterTime) {
		this.fdAlterTime = fdAlterTime;
	}

	/*
	 * 本级领导
	 */
	private String fdThisLeaderId;

	/*
	 * 上级领导
	 */
	private String fdSuperLeaderId;

	// private SysOrgElementBak fdParentOrg;
	//
	// public SysOrgElementBak getFdParentOrg() {
	// return fdParentOrg;
	// }
	//
	// public SysOrgElementBak getHbmParentOrg() {
	// return fdParentOrg;
	// }

	private String fdParentOrgId;

	private String fdParentId;

	/*
	 * 父
	 */
	// private SysOrgElementBak fdParent;
	//
	// public SysOrgElementBak getFdParent() {
	// return fdParent;
	// }
	//
	// public void setFdParent(SysOrgElementBak parent) {
	// this.fdParent = parent;
	// }
	//
	// public SysOrgElementBak getHbmParent() {
	// return fdParent;
	// }
	/*
	 * 子（此关系由父维护，不提供set方法）
	 */
	private List fdChildren;

	public List getFdChildren() {
		List rtnVal = new ArrayList();
		if (getHbmChildren() != null) {
            rtnVal.addAll(getHbmChildren());
        }
		return rtnVal;
	}

	public List getHbmChildren() {
		return fdChildren;
	}

	public void setHbmChildren(List children) {
		this.fdChildren = children;
	}

	/*
	 * 所属群组
	 */
	private List fdGroups;

	public List getFdGroups() {
		List rtnVal = new ArrayList();
		if (getHbmGroups() != null) {
            rtnVal.addAll(getHbmGroups());
        }
		return rtnVal;
	}

	public void setFdGroups(List groups) {
		new KmssCache(SysOrgElementBak.class).clear();
		if (this.fdGroups == groups) {
            return;
        }
		if (this.fdGroups == null) {
            this.fdGroups = new ArrayList();
        } else {
            this.fdGroups.clear();
        }
		if (groups != null) {
            this.fdGroups.addAll(groups);
        }
	}

	public List getHbmGroups() {
		return fdGroups;
	}

	public void setHbmGroups(List groups) {
		this.fdGroups = groups;
	}

	/*
	 * 岗位列表（个人使用）
	 */
	private List fdPosts = null;

	public List getFdPosts() {
		List rtnVal = new ArrayList();
		if (getHbmPosts() != null) {
            rtnVal.addAll(getHbmPosts());
        }
		return rtnVal;
	}

	public void setFdPosts(List posts) {
		if (this.fdPosts == posts) {
            return;
        }
		if (this.fdPosts == null) {
            this.fdPosts = new ArrayList();
        } else {
            this.fdPosts.clear();
        }
		if (posts != null) {
            this.fdPosts.addAll(posts);
        }
	}

	public List getHbmPosts() {
		return fdPosts;
	}

	public void setHbmPosts(List posts) {
		this.fdPosts = posts;
	}

	/*
	 * 个人列表（岗位使用）
	 */
	public List fdPersons = null;

	public List getFdPersons() {
		List rtnVal = new ArrayList();
		if (getHbmPersons() != null) {
            rtnVal.addAll(getHbmPersons());
        }
		return rtnVal;
	}

	public void setFdPersons(List persons) {
		if (this.fdPersons == persons) {
            return;
        }
		if (this.fdPersons == null) {
            this.fdPersons = new ArrayList();
        } else {
            this.fdPersons.clear();
        }
		if (persons != null) {
            this.fdPersons.addAll(persons);
        }
	}

	public List getHbmPersons() {
		return fdPersons;
	}

	public void setHbmPersons(List persons) {
		this.fdPersons = persons;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
            return true;
        }
		if (object == null) {
            return false;
        }
		if (!(object instanceof SysOrgElementBak)) {
            return false;
        }
		BaseModel objModel = (BaseModel) object;
		return ObjectUtil.equals(objModel.getFdId(), this.getFdId(), false);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.put("fdParent.fdId", "fdParentId");
			toFormPropertyMap.put("fdParent.fdName", "fdParentName");
			toFormPropertyMap.put("hbmThisLeader.fdId", "fdThisLeaderId");
			toFormPropertyMap.put("hbmThisLeader.fdName", "fdThisLeaderName");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

	/*
	 * 是否已废弃,true表示禁用
	 */
	protected Boolean fdIsAbandon = new Boolean(false);

	public Boolean getFdIsAbandon() {
		return fdIsAbandon;
	}

	public void setFdIsAbandon(Boolean fdIsAbandon) {
		this.fdIsAbandon = fdIsAbandon;
	}

	/**
	 * @return fdNamePinYin
	 */
	public String getFdNamePinYin() {
		return fdNamePinYin;
	}

	/**
	 * @param fdNamePinYin
	 *            要设置的 fdNamePinYin
	 */
	public void setFdNamePinYin(String fdNamePinYin) {
		this.fdNamePinYin = fdNamePinYin;
	}

	/**
	 * 管理员 (部门与机构使用)
	 */
	protected List<SysOrgElementBak> authElementAdmins;

	/**
	 * @return 管理员
	 */
	public List<SysOrgElementBak> getAuthElementAdmins() {
		return authElementAdmins;
	}

	/**
	 * @param authAreaAdmin
	 *            管理员
	 */
	public void setAuthElementAdmins(List<SysOrgElementBak> authElementAdmins) {
		this.authElementAdmins = authElementAdmins;
	}

	public void setFdParentOrgId(String fdParentOrgId) {
		this.fdParentOrgId = fdParentOrgId;
	}

	public String getFdParentOrgId() {
		return fdParentOrgId;
	}

	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	public String getFdParentId() {
		return fdParentId;
	}

	public void setFdThisLeaderId(String fdThisLeaderId) {
		this.fdThisLeaderId = fdThisLeaderId;
	}

	public String getFdThisLeaderId() {
		return fdThisLeaderId;
	}

	public void setFdSuperLeaderId(String fdSuperLeaderId) {
		this.fdSuperLeaderId = fdSuperLeaderId;
	}

	public String getFdSuperLeaderId() {
		return fdSuperLeaderId;
	}

	public void setFdOrgEmail(String fdOrgEmail) {
		this.fdOrgEmail = fdOrgEmail;
	}

	public String getFdOrgEmail() {
		return fdOrgEmail;
	}

	private String fdOrgEmail;

	/**
	 * 名称简拼
	 */
	private String fdNameSimplePinyin;

	public String getFdNameSimplePinyin() {
		return fdNameSimplePinyin;
	}

	public void setFdNameSimplePinyin(String fdNameSimplePinyin) {
		this.fdNameSimplePinyin = fdNameSimplePinyin;
	}
	
	/**
	 * 是否外部组织
	 */
	private Boolean fdIsExternal;

	public Boolean getFdIsExternal() {
		// 默认是内部组织
		if (fdIsExternal == null) {
			fdIsExternal = false;
		}
		return fdIsExternal;
	}

	public void setFdIsExternal(Boolean fdIsExternal) {
		this.fdIsExternal = fdIsExternal;
	}

}
