package com.landray.kmss.sys.organization.interfaces;

import java.util.List;

public interface ISysOrgPerson extends ISysOrgElement {
	/**
	 * @return 手机号
	 */
	public abstract String getFdMobileNo();

	/**
	 * @return Email地址
	 */
	public abstract String getFdEmail();

	/**
	 * @return 登录名
	 */
	public abstract String getFdLoginName();

	/**
	 * @return 所有岗位
	 */
	public abstract List getFdPosts();
}
