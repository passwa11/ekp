package com.landray.kmss.hr.organization.constant;

public interface HrOrgConstant {

	/**
	 * 机构
	 */
	public final static int HR_TYPE_ORG = 0x1;

	/**
	 * 部门
	 */
	public final static int HR_TYPE_DEPT = 0x2;

	/**
	 * 岗位
	 */
	public final static int HR_TYPE_POST = 0x4;

	/**
	 * 个人
	 */
	public final static int HR_TYPE_PERSON = 0x8;


	/**
	 * 机构或部门
	 */
	public final static int HR_TYPE_HRORDEPT = HR_TYPE_ORG | HR_TYPE_DEPT;

	/**
	 * 岗位或个人
	 */
	public final static int HR_TYPE_POSTORPERSON = HR_TYPE_POST | HR_TYPE_PERSON;

	/**
	 * 所有组织架构
	 */
	public final static int HR_TYPE_ALLORG = HR_TYPE_HRORDEPT | HR_TYPE_POSTORPERSON;

	/**
	 * 所有类型
	 */
	public final static int HR_TYPE_ALL = HR_TYPE_ALLORG;

	/**
	 * 组织架构类型默认值
	 */
	public final static int HR_TYPE_DEFAULT = HR_TYPE_ALL;

	// ========================组织架构标记========================
	/**
	 * 有效
	 */
	public final static int HR_FLAG_AVAILABLEYES = 0x100;

	/**
	 * 无效
	 */
	public final static int HR_FLAG_AVAILABLENO = 0x200;

	/**
	 * 不管是否有效
	 */
	public final static int HR_FLAG_AVAILABLEALL = HR_FLAG_AVAILABLEYES | HR_FLAG_AVAILABLENO;

	/**
	 * 有效性的默认值
	 */
	public final static int HR_FLAG_AVAILABLEDEFAULT = HR_FLAG_AVAILABLEYES;

	/**
	 * 业务相关
	 */
	public final static int HR_FLAG_BUSINESSYES = 0x400;

	/**
	 * 业务无关
	 */
	public final static int HR_FLAG_BUSINESSNO = 0x800;

	/**
	 * 不管是否业务相关
	 */
	public final static int HR_FLAG_BUSINESSALL = HR_FLAG_BUSINESSYES | HR_FLAG_BUSINESSNO;

	/**
	 * 是否业务相关的默认值
	 */
	public final static int HR_FLAG_BUSINESSDEFAULT = HR_FLAG_BUSINESSYES;

	/**
	 * 不管任何标记
	 */
	public final static int HR_FLAG_ALL = HR_FLAG_AVAILABLEALL | HR_FLAG_BUSINESSALL;

	/**
	 * 标记默认值
	 */
	public final static int HR_FLAG_DEFAULT = HR_FLAG_AVAILABLEDEFAULT | HR_FLAG_BUSINESSDEFAULT;

}
