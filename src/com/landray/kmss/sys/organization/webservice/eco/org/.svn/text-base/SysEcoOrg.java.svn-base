package com.landray.kmss.sys.organization.webservice.eco.org;

import java.util.List;

import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

/**
 * 生态组织
 * 
 * @author panyh
 *
 *         2020年9月9日 下午5:20:10
 */
public class SysEcoOrg {
	// =============== 公共属性 ==================
	// 有且存在ID：更新，无或不存在：新增
	private String id;
	// 类型
	private String type;
	// 名称
	private String name;
	// 编号
	private String no;
	// 排序号
	private Integer order;
	// 所属组织
	private SysOrgObject parent;
	// 是否有效
	private Boolean isAvailable;
	// 扩展属性表名
	private String table;

	// =============== 组织属性 ==================
	// 负责人
	private List<SysOrgObject> admins;
	// 组织范围
	private SysEcoRange range;

	// =============== 岗位属性 ==================
	// 岗位领导
	private SysOrgObject thisLeader;
	// 人员列表
	private List<SysOrgObject> persons;
	// 备注
	private String memo;

	// =============== 人员属性 ==================
	// 登录名
	private String loginName;
	// 密码（新增时使用）
	private String password;
	// 手机号码
	private String mobileNo;
	// 邮件地址
	private String email;
	// 所属岗位
	private List<SysOrgObject> posts;

	// =============== 组织(人员)扩展属性 ==================
	private List<SysEcoExtPorp> extProps;

	public String getId() {
		if (StringUtil.isNull(id)) {
			id = IDGenerator.generateID();
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public SysOrgObject getParent() {
		return parent;
	}

	public void setParent(SysOrgObject parent) {
		this.parent = parent;
	}

	public Boolean getIsAvailable() {
		if (isAvailable == null) {
			isAvailable = true;
		}
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<SysOrgObject> getAdmins() {
		return admins;
	}

	public void setAdmins(List<SysOrgObject> admins) {
		this.admins = admins;
	}

	public SysEcoRange getRange() {
		return range;
	}

	public void setRange(SysEcoRange range) {
		this.range = range;
	}

	public SysOrgObject getThisLeader() {
		return thisLeader;
	}

	public void setThisLeader(SysOrgObject thisLeader) {
		this.thisLeader = thisLeader;
	}

	public List<SysOrgObject> getPersons() {
		return persons;
	}

	public void setPersons(List<SysOrgObject> persons) {
		this.persons = persons;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SysOrgObject> getPosts() {
		return posts;
	}

	public void setPosts(List<SysOrgObject> posts) {
		this.posts = posts;
	}

	public List<SysEcoExtPorp> getExtProps() {
		return extProps;
	}

	public void setExtProps(List<SysEcoExtPorp> extProps) {
		this.extProps = extProps;
	}

}
