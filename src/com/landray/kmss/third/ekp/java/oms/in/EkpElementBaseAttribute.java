package com.landray.kmss.third.ekp.java.oms.in;

import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;

public class EkpElementBaseAttribute implements IOMSElementBaseAttribute {

	private String elementId;

	private String elementKeyword;

	private String elementLdapDN = null;

	private String elementName;

	private String elementNumber;

	private Integer elementType;

	private String elementUUID;
	
	private boolean elementIsExternal;

	@Override
    public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	@Override
    public String getElementKeyword() {
		return elementKeyword;
	}

	public void setElementKeyword(String elementKeyword) {
		this.elementKeyword = elementKeyword;
	}

	@Override
    public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@Override
    public String getElementNumber() {
		return elementNumber;
	}

	public void setElementNumber(String elementNumber) {
		this.elementNumber = elementNumber;
	}

	@Override
    public Integer getElementType() {
		return elementType;
	}

	public void setElementType(Integer elementType) {
		this.elementType = elementType;
	}

	@Override
    public String getElementUUID() {
		return elementUUID;
	}

	public void setElementUUID(String elementUUID) {
		this.elementUUID = elementUUID;
	}

	@Override
    public String getElementLdapDN() {
		return elementLdapDN;
	}

	@Override
	public boolean isElementKeywordNeedSynchro() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean isElementNumberNeedSynchro() {
		// TODO 自动生成的方法存根
		return false;
	}

}
