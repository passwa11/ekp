package com.landray.kmss.sys.oms.out.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;

/**
 * 岗位封装
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OrgPostContext extends OrgElementContext {
	SysOrgPost orgPost;

	public SysOrgPost getOrgPost() {
		return orgPost;
	}

	public OrgPostContext(SysOrgPost orgPost) {
		super(orgPost);
		this.orgPost = orgPost;
	}

	/*
	 * 个人列表
	 */
	public List getFdPersons() {
		return getPersons(orgPost.getFdPersons());
	}

	private List getPersons(List orgPersons) {
		List persons = new ArrayList();
		for (int i = 0; i < orgPersons.size(); i++) {
			persons.add(new OrgPersonContext((SysOrgPerson) orgPersons.get(i)));
		}
		return persons;
	}

}
