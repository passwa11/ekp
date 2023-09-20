package com.landray.kmss.sys.organization.webservice.out;

public class SysSynchroGetOrgBaseInfoContext extends SysSynchroGetOrgContext {
	/**
	 *      设置需要返回的组织架构中需要返回的信息列表
	 * 仅getElementsBaseInfo调用时, 有效, 设置需要返回的基本信息,JSON数组,格式如:
	 *      [{"type":"no"},{"type":"order"},{"type":"keyword"}],值如果为空仅返回id,type,name信息.
	 */
	private String returnType;

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	
}
