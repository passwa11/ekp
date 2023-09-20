package com.landray.kmss.sys.oms.in.interfaces;

import com.landray.kmss.constant.SysOrgConstant;


/**
 * 接入第三方平台组织机构元素默认实现
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */

public class DefaultOrgPost extends DefaultOrgElement implements IOrgPost {
	private String[] persons;
	
	/**
	 * @return 岗位下的个人列表对应键关键字数组
	 */
	@Override
    public String[] getPersons(){
		return persons;
	}
	public void setPersons(String[] persons){
		this.persons = persons;
	}
	
	/**
	 * 组织架构类型
	 */
	@Override
    public Integer getOrgType() {
		return SysOrgConstant.ORG_TYPE_POST;
	}
}
