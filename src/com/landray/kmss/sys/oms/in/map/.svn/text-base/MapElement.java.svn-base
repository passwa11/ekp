package com.landray.kmss.sys.oms.in.map;

import java.util.Date;
import java.util.Map;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 组织元素Map实现
 * 
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapElement extends DefaultOrgElement implements IOrgElement {
	public static String NAME = "name";
	public static String TYPE = "type";
	public static String SHORT_NAME = "shortName";
	public static String NO = "no";
	public static String ORDER = "order";
	public static String KEYWORD = "keyword";
	public static String IS_AVAILABLE = "isAvailable";
	public static String IS_BUSINESS = "isBusiness";
	public static String MEMO = "memo";
	public static String THIS_LEADER = "thisLeader";
	public static String SUPER_LEADER = "superLeader";
	public static String PARENT = "parent";

	private Map mapElement;
	private SysOrgElement sysOrgElement;

	public MapElement(Map mapElement) {
		this.mapElement = mapElement;
	}

	public Object getOrgElement() {
		return mapElement;
	}

	/**
	 * 回写组织机构对象元素
	 */
	@Override
    public SysOrgElement getSysOrgElement() {
		return sysOrgElement;
	}

	@Override
    public void setSysOrgElement(SysOrgElement sysOrgElement) {
		this.sysOrgElement = sysOrgElement;
	}

	/**
	 * @return 组织机构名称
	 */
	@Override
    public String getName() {
		return (String) mapElement.get(NAME);
	}

	/**
	 * @return 组织机构简称
	 */
	@Override
    public String getShortName() {
		return (String) mapElement.get(SHORT_NAME);
	}

	/**
	 * @return 组织机构编号
	 */
	@Override
    public String getNo() {
		return (String) mapElement.get(NO);
	}

	/**
	 * @return 排序号
	 */
	@Override
    public Integer getOrder() {
		return (Integer) mapElement.get(ORDER);
	}

	/**
	 * @return 关键字
	 */
	@Override
    public String getKeyword() {
		return (String) mapElement.get(KEYWORD);
	}

	/**
	 * @return 是否有效
	 */
	@Override
    public Boolean getIsAvailable() {
		return (Boolean) mapElement.get(IS_AVAILABLE);
	}

	/**
	 * @return 是否业务相关
	 */
	@Override
    public Boolean getIsBusiness() {
		return (Boolean) mapElement.get(IS_BUSINESS);
	}

	/**
	 * @return 备注
	 */
	@Override
    public String getMemo() {
		return (String) mapElement.get(MEMO);
	}

	/**
	 * 本级领导对应键关键字
	 */
	@Override
    public String getThisLeader() {
		return (String) mapElement.get(THIS_LEADER);
	}

	/**
	 * 上级领导对应键关键字
	 */
	@Override
    public String getSuperLeader() {
		return (String) mapElement.get(SUPER_LEADER);
	}

	/**
	 * @return 上级部门/机构对应键关键字
	 */
	@Override
    public String getParent() {
		return (String) mapElement.get(PARENT);
	}

	/**
	 * @return 当前记录的更新状态标识，默认为更新标识
	 */
	@Override
    public int getRecordStatus() {
		return SysOrgConstant.OMS_OP_FLAG_UPDATE;
	}

	@Override
    public void setRecordStatus(int recordStatus) {
	}

	@Override
    public Date getAlterTime() {
		return null;
	}

	@Override
    public String getId() {
		return null;
	}

	public String getLang() {
		return null;
	}

	public String getRtx() {
		return null;
	}

	public String getWechat() {
		return null;
	}
}
