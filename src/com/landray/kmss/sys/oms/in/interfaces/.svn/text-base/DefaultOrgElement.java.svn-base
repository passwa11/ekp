package com.landray.kmss.sys.oms.in.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接入第三方平台组织机构元素默认实现
 *
 * @author 吴兵
 * @version 1.0 2006-11-29
 */

public class DefaultOrgElement implements IOrgElement {

	/**
	 * 回写组织机构对象元素
	 */
	@Override
    public SysOrgElement getSysOrgElement() {
		return null;
	}

	@Override
    public void setSysOrgElement(SysOrgElement sysOrgElement) {

	}

	private String name;

	/**
	 * @return 组织机构名称
	 */
	@Override
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String shortName;

	/**
	 * @return 组织机构简称
	 */
	@Override
    public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	private String no;

	/**
	 * @return 组织机构编号
	 */
	@Override
    public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	/**
	 *组织架构类型
	 */
	private Integer orgType;

	@Override
    public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	private Integer order;

	/**
	 * @return 排序号
	 */
	@Override
    public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	private String keyword;

	/**
	 * @return 关键字
	 */
	@Override
    public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private String importInfo;

	/**
	 * @return 与外部系统关联
	 */
	@Override
    public String getImportInfo() {
		return importInfo;
	}

	public void setImportInfo(String importInfo) {
		this.importInfo = importInfo;
	}

	private Boolean isAvailable;

	/**
	 * @return 是否有效
	 */
	@Override
    public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	private Boolean isBusiness;

	/**
	 * @return 是否业务相关
	 */
	@Override
    public Boolean getIsBusiness() {
		return isBusiness;
	}

	public void setIsBusiness(Boolean isBusiness) {
		this.isBusiness = isBusiness;
	}

	private String memo;

	/**
	 * @return 备注
	 */
	@Override
    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private String thisLeader;

	/**
	 * 本级领导对应键关键字
	 */
	@Override
    public String getThisLeader() {
		return thisLeader;
	}

	public void setThisLeader(String thisLeader) {
		this.thisLeader = thisLeader;
	}

	private String superLeader;

	/**
	 * 上级领导对应键关键字
	 */
	@Override
    public String getSuperLeader() {
		return superLeader;
	}

	public void setSuperLeader(String superLeader) {
		this.superLeader = superLeader;
	}

	private String parent;

	/**
	 * @return 上级部门/机构对应键关键字
	 */
	@Override
    public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	private int recordStatus;

	/**
	 * @return 当前记录的更新状态标识
	 */
	@Override
    public int getRecordStatus() {
		return recordStatus;
	}

	@Override
    public void setRecordStatus(int recordStatus) {
		this.recordStatus = recordStatus;
	}

	private String id;

	@Override
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private Date alterTime;

	@Override
    public Date getAlterTime() {
		return alterTime;
	}

	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	private List requiredOms;

	public void setRequiredOms(List requiredOms) {
		this.requiredOms = requiredOms;
	}

	@Override
    public List getRequiredOms() {
		return requiredOms;
	}

	private String ldapDN;

	@Override
    public String getLdapDN() {
		return ldapDN;
	}

	public void setLdapDN(String ldapDN) {
		this.ldapDN = ldapDN;
	}

	@Override
    public Map getDynamicMap() {
		return dynamicMap;
	}

	public void setDynamicMap(Map dynamicMap) {
		this.dynamicMap = dynamicMap;
	}

	@Override
    public Map getCustomMap() {
		return customMap;
	}

	public void setCustomMap(Map customMap) {
		this.customMap = customMap;
	}

	private Map dynamicMap = new HashMap();

	private Map customMap = new HashMap();

	private ViewRange viewRange;

	@Override
    public ViewRange getViewRange() {
		return viewRange;
	}

	public void setViewRange(ViewRange viewRange) {
		this.viewRange = viewRange;
	}

	private HideRange hideRange;

	@Override
    public HideRange getHideRange() {
		return hideRange;
	}

	public void setHideRange(HideRange hideRange) {
		this.hideRange = hideRange;
	}

	private String creator;

	@Override
    public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	private String[] authElementAdmins;

	@Override
    public String[] getAuthElementAdmins() {
		return authElementAdmins;
	}

	public void setAuthElementAdmins(String[] authElementAdmins) {
		this.authElementAdmins = authElementAdmins;
	}

	@Override
    public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	private String orgEmail;

	private Boolean isExternal;

	@Override
	public Boolean getExternal() {
		return isExternal;
	}

	public void setExternal(Boolean external) {
		isExternal = external;
	}
}
