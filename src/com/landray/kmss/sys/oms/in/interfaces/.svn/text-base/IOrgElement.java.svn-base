package com.landray.kmss.sys.oms.in.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接入第三方平台组织机构元素接口
 *
 * @author 吴兵
 * @version 1.0 2006-11-29
 */

public interface IOrgElement {
	public static String MID_PREFIX = "_";
	public static String ORGF_PREFIX = "_1_";
	public static String DEPT_PREFIX = "_2_";
	public static String PERSON_PREFIX = "_8_";
	public static String POST_PREFIX = "_4_";
	public static String GROUP_PREFIX = "_10_";

	/**
	 * 回写组织机构对象元素
	 */
	public abstract SysOrgElement getSysOrgElement();

	public abstract void setSysOrgElement(SysOrgElement sysOrgElement);

	/**
	 * @return 组织机构名称
	 */
	public abstract String getName();

	/**
	 * @return 组织机构简称
	 */
	public abstract String getShortName();

	/**
	 * @return 组织机构编号
	 */
	public abstract String getNo();

	/**
	 * @return 排序号
	 */
	public abstract Integer getOrder();

	/**
	 * @return 组织架构类型
	 */
	public abstract Integer getOrgType();

	/**
	 * @return 关键字
	 */
	public abstract String getKeyword();

	/**
	 * @return 与外部系统关联
	 */
	public abstract String getImportInfo();

	/**
	 * @return 是否有效
	 */
	public abstract Boolean getIsAvailable();

	/**
	 * @return 是否业务相关
	 */
	public abstract Boolean getIsBusiness();

	/**
	 * @return 备注
	 */
	public abstract String getMemo();

	/**
	 * 本级领导对应键关键字
	 */
	public String getThisLeader();

	/**
	 * 上级领导对应键关键字
	 */
	public String getSuperLeader();

	/**
	 * @return 上级部门/机构对应键关键字
	 */
	public abstract String getParent();

	/**
	 * @return 当前记录的更新状态标识
	 */
	public abstract int getRecordStatus();

	public abstract void setRecordStatus(int recordStatus);

	public abstract String getId();

	public abstract Date getAlterTime();

	/**
	 * 需要同步的属性集合，为null，同步所有属性，如果有该属性则只同步该属性中的值
	 *
	 * @return
	 */
	public List getRequiredOms();

	/**
	 * 扩展属性，例如AD同步是保存的是DN
	 *
	 * @return
	 */
	public String getLdapDN();

	public Map getDynamicMap();

	public Map<String, Object> getCustomMap();

	public ViewRange getViewRange();

	public String getCreator();

	public String[] getAuthElementAdmins();

	public String getOrgEmail();

	public Boolean getExternal();
	/**
	 * 隐藏范围
	 */
	public HideRange getHideRange();
}
