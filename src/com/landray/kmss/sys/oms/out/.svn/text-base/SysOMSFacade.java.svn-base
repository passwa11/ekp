package com.landray.kmss.sys.oms.out;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.oms.out.interfaces.OrgDeptContext;
import com.landray.kmss.sys.oms.out.interfaces.OrgElementContext;
import com.landray.kmss.sys.oms.out.interfaces.OrgGroupContext;
import com.landray.kmss.sys.oms.out.interfaces.OrgOrgContext;
import com.landray.kmss.sys.oms.out.interfaces.OrgPersonContext;
import com.landray.kmss.sys.oms.out.interfaces.OrgPostContext;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;

/**
 * 
 * 
 * @author 吴兵
 * @version 1.0 2006-12-01
 */

public class SysOMSFacade {
	
	public static List getOMSObject(List list){
		List omsList = new ArrayList();
		for(int i=0;i<list.size();i++){
			omsList.add(getOMSObject(list.get(i)));
		}
		return omsList;
	}
	
	public static OrgElementContext getOMSObject(Object object){
		if(object instanceof SysOrgOrg){
			return new OrgOrgContext((SysOrgOrg)object);
		}
		if(object instanceof SysOrgPerson){
			SysOrgPerson person = (SysOrgPerson)object;
			person.getFdPosts();
			return new OrgPersonContext(person);
		}
		if(object instanceof SysOrgDept){
			return new OrgDeptContext((SysOrgDept)object);
		}
		if(object instanceof SysOrgPost){
			SysOrgPost post = (SysOrgPost)object;
			post.getFdPersons();
			return new OrgPostContext(post);
		}
		if(object instanceof SysOrgGroup){
			SysOrgGroup group = (SysOrgGroup)object;
			group.getFdMembers();
			return new OrgGroupContext(group);
		}
		return new OrgElementContext((SysOrgElement)object);
	}
	
}
