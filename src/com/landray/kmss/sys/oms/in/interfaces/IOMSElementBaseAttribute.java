package com.landray.kmss.sys.oms.in.interfaces;

public interface IOMSElementBaseAttribute {
	/**
	 * 组织架构名称 可为空
	 */
	public String getElementName();

	/**
	 * 组织架构编号 可为空
	 */
	public String getElementNumber();

	/**
	 * 组织架构关键字 可为空
	 */
	public String getElementKeyword();

	/**
	 * 组织架构ldapDN 可为空,用于ldap同步
	 */
	public String getElementLdapDN();

	/**
	 * 组织架构,外部系统指定的唯一标识 不可为空
	 */
	public String getElementUUID();

	/**
	 * 组织架构类型 不可为空
	 */
	public Integer getElementType();

	/**
	 * 组织架构ID信息 可为空. 如为空,ekpj系统中组织架构唯一标识由系统自动生成.
	 * 如不为空,在ekpj系统中,该值作为ekpj系统中组织架构唯一标识
	 */
	public String getElementId();

	/**
	 * 是否需要同步编号信息
	 * 
	 * @return
	 */
	public boolean isElementNumberNeedSynchro();

	/**
	 * 是否需要同步关键字信息
	 * 
	 * @return
	 */
	public boolean isElementKeywordNeedSynchro();
	
}
