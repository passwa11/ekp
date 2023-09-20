package com.landray.kmss.third.ldap.oms.in;

import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;

public class LdapElementBaseAttribute implements IOMSElementBaseAttribute {
	private String elementKeyword;

	private String elementLdapDN;

	private String elementNumber;

	private String elementUUID;

	private Integer elementType;

	private String elementName;

	/**
	 * 设置elementId为空, ekpj系统中的组织架构唯一标识由系统自动生成
	 */
	private String elementId = null;

	@Override
    public String getElementId() {
		return elementId;
	}

	@Override
    public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@Override
    public String getElementKeyword() {
		return elementKeyword;
	}

	@Override
    public String getElementLdapDN() {
		return elementLdapDN;
	}

	@Override
    public String getElementNumber() {
		return elementNumber;
	}

	@Override
    public String getElementUUID() {
		return elementUUID;
	}

	public void setElementKeyword(String elementKeyword) {
		this.elementKeyword = elementKeyword;
	}

	public void setElementLdapDN(String elementLdapDN) {
		this.elementLdapDN = elementLdapDN;
	}

	public void setElementNumber(String elementNumber) {
		this.elementNumber = elementNumber;
	}

	public void setElementUUID(String elementUUID) {
		this.elementUUID = elementUUID;
	}

	public void setElementType(Integer elementType) {
		this.elementType = elementType;
	}

	@Override
    public Integer getElementType() {
		return this.elementType;
	}

	private boolean elementKeywordNeedSynchro;

	private boolean elementNumberNeedSynchro;

	@Override
	public boolean isElementKeywordNeedSynchro() {
		// TODO 自动生成的方法存根
		return elementKeywordNeedSynchro;
	}

	@Override
	public boolean isElementNumberNeedSynchro() {
		// TODO 自动生成的方法存根
		return elementNumberNeedSynchro;
	}

	public void setElementKeywordNeedSynchro(boolean elementKeywordNeedSynchro) {
		this.elementKeywordNeedSynchro = elementKeywordNeedSynchro;
	}

	public void setElementNumberNeedSynchro(boolean elementNumberNeedSynchro) {
		this.elementNumberNeedSynchro = elementNumberNeedSynchro;
	}

	private String elementMapping;

	public String getElementMapping() {
		return elementMapping;
	}

	public void setElementMapping(String elementMapping) {
		this.elementMapping = elementMapping;
	}

}
