package com.landray.kmss.sys.oms.in.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.landray.kmss.constant.SysOrgConstant;

/**
 * 组织元素Map实现
 * 
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapOMSFacade implements SysOrgConstant {

	public static List getOMSObject(List hrList) {
		List omsList = new ArrayList();
		for (int i = 0; i < hrList.size(); i++) {
			omsList.add(getOMSObject(hrList.get(i)));
		}
		return omsList;
	}

	public static Object getOMSObject(Object object) {
		Map map = (Map) object;
		Integer type = (Integer) map.get(MapElement.TYPE);
		switch (type) {
		case ORG_TYPE_ORG:
			return new MapOrg(map);
		case ORG_TYPE_DEPT:
			return new MapDept(map);
		case ORG_TYPE_PERSON:
			return new MapPerson(map);
		case ORG_TYPE_POST:
			return new MapPost(map);
		case ORG_TYPE_GROUP:
			return new MapGroup(map);
		}
		return new MapElement(map);
	}

}
