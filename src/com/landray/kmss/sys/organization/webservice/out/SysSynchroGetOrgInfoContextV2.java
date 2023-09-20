package com.landray.kmss.sys.organization.webservice.out;

public class SysSynchroGetOrgInfoContextV2 extends SysSynchroGetOrgInfoContext {
	
	public Boolean getIsBusiness() {
		return isBusiness;
	}

	public void setIsBusiness(Boolean isBusiness) {
		this.isBusiness = isBusiness;
	}

	public String getExtendPara() {
		return extendPara;
	}

	public void setExtendPara(String extendPara) {
		this.extendPara = extendPara;
	}

	private Boolean isBusiness = null;

	private String extendPara = null;

}
