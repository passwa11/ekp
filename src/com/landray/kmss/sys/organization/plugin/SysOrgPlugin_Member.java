package com.landray.kmss.sys.organization.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.util.StringUtil;

public class SysOrgPlugin_Member implements ISysOrgRolePlugin {
	@Override
	public List<SysOrgElement> parse(SysOrgRolePluginContext context)
			throws Exception {
		if (context == null) {
            return new ArrayList<SysOrgElement>();
        }
		SysOrgElement element = context.getBaseElement();
		SysOrgRole role = context.getRole();
		// boolean excludeMe = "excludeme".equals(role.getParameter("type"));
		String endlevel = role.getParameter("endlevel");
		int lv = Integer.parseInt(role.getParameter("level"));
		int elv = 0;
		if (StringUtil.isNull(endlevel)) {
			elv = lv;
		} else {
			elv = Integer.parseInt(endlevel);
		}

		return getMembers(element.getAllParent(false), lv, elv);
	}

	private List<SysOrgElement> getMembers(List<SysOrgElement> members, int s,
			int e) {
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		if (s < 0) {
            s = members.size() + s;
        }
		if (e < 0) {
            e = members.size() + e;
        }
		int step = s > e ? -1 : 1;
		for (int i = s; i >= 0 && i < members.size()
				&& (s > e ? i >= e : i <= e); i += step) {
			List<SysOrgElement> childList = members.get(i).getFdChildren();
			for (SysOrgElement elem : childList) {
				if(SysOrgConstant.ORG_TYPE_POST == elem.getFdOrgType()){
					List<?> ss = elem.getFdPersons();
					for (int j = 0;j < ss.size(); j++) {
						SysOrgElement soeOri = ((SysOrgElement) ss.get(i));
					     
					    SysOrgElement soe = new SysOrgElement();
					    
					    cloneOrg(soe,soeOri);
						
						if (soe.getFdIsAvailable() && rtnList != null
								&& !rtnList.contains(soe)) {
							soe.setFdOrder(elem.getFdOrder());
							rtnList.add(soe);
						}
					}
					rtnList.add(elem);
				}else if (SysOrgConstant.ORG_TYPE_PERSON == elem.getFdOrgType()) {
					rtnList.add(elem);
				}
			}
		}
		Collections.sort(rtnList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				SysOrgElement org1 = (SysOrgElement) o1;
				SysOrgElement org2 = (SysOrgElement) o2;
				Integer i1 = org1.getFdOrder() == null ? Integer.MAX_VALUE : org1.getFdOrder();
				Integer i2 = org2.getFdOrder() == null ? Integer.MAX_VALUE : org2.getFdOrder();
				if (org1.getFdOrgType().equals(org2.getFdOrgType())) {						 
					if (i1.equals(i2)) {
						return 0;
					} else if (i1 > i2) {
						return 1;
					} else {
						return -1;
					}
				} else if (org1.getFdOrgType() > org2.getFdOrgType()) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		
		return rtnList;
	}
	
	/**
	 * 克隆所需的属性
	 * 
	 * @param newOrg
	 * @param oldOrg
	 */
	private void cloneOrg(SysOrgElement newOrg,SysOrgElement oldOrg){
		newOrg.setFdId(oldOrg.getFdId());
		newOrg.setFdName(oldOrg.getFdName());
		newOrg.setFdOrgType(oldOrg.getFdOrgType());
		newOrg.setFdIsAvailable(oldOrg.getFdIsAvailable());
		newOrg.setFdHierarchyId(oldOrg.getFdHierarchyId());
		newOrg.setFdParent(oldOrg.getFdParent());
		newOrg.setFdIsBusiness(oldOrg.getFdIsBusiness());
		newOrg.setFdPersons(oldOrg.getFdPersons());
		newOrg.setFdMemo(oldOrg.getFdMemo());
		newOrg.setFdOrder(oldOrg.getFdOrder());
	}

}
