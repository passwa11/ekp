package com.landray.kmss.sys.oms.in.map;

import java.util.Map;

import com.landray.kmss.sys.oms.in.interfaces.IOrgGroup;

/**
 * 组织元素Map实现
 * 
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapGroup extends MapElement implements IOrgGroup {
	private Map mapOrgGroup;
	public static String MEMBERS = "members";

	public MapGroup(Map mapOrgGroup) {
		super(mapOrgGroup);
		this.mapOrgGroup = mapOrgGroup;
	}

	/**
	 * @return 群组成员对应键关键字数组
	 */
	@Override
    public String[] getMembers() {
		return (String[]) mapOrgGroup.get(MEMBERS);
	}
}
