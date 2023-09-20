package com.landray.kmss.sys.oms.out.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.util.PasswordUtil;

/**
 * 个人封装
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OrgPersonContext extends OrgElementContext {
	private SysOrgPerson orgPerson;

	public SysOrgPerson getOrgPerson() {
		return orgPerson;
	}

	public OrgPersonContext(SysOrgPerson orgPerson) {
		super(orgPerson);
		this.orgPerson = orgPerson;
	}

	/*
	 * 手机号码
	 */
	public String getFdMobileNo() {
		return orgPerson.getFdMobileNo();
	}

	/*
	 * 办公电话
	 */
	public String getFdWorkPhone() {
		return orgPerson.getFdWorkPhone();
	}

	/*
	 * 邮件地址
	 */
	public String getFdEmail() {
		return orgPerson.getFdEmail();
	}

	/*
	 * 登录名
	 */
	public String getFdLoginName() {
		return orgPerson.getFdLoginName();
	}

	/*
	 * 密码
	 */
	public String getFdPassword() {
		return PasswordUtil.desDecrypt(orgPerson.getFdInitPassword());
	}

	/*
	 * 考勤卡号
	 */
	public String getFdAttendanceCardNumber() {
		return orgPerson.getFdAttendanceCardNumber();
	}

	/*
	 * 岗位列表
	 */
	public List getFdPosts() {
		return getPosts(orgPerson.getFdPosts());
	}

	private List getPosts(List orgPosts) {
		List posts = new ArrayList();
		for (int i = 0; i < orgPosts.size(); i++) {
			posts.add(new OrgPostContext((SysOrgPost) orgPosts.get(i)));
		}
		return posts;
	}
}
