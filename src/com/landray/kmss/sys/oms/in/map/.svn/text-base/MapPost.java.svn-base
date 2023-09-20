package com.landray.kmss.sys.oms.in.map;

import java.util.Map;

import com.landray.kmss.sys.oms.in.interfaces.IOrgPost;

/**
 * 组织元素Map实现
 * 
 * @author 吴兵
 * @version 3.0 2009-10-26
 */

public class MapPost extends MapElement implements IOrgPost {
	public static String PERSONS = "persons";
	private Map mapOrgPost;

	public MapPost(Map mapOrgPost) {
		super(mapOrgPost);
		this.mapOrgPost = mapOrgPost;
	}

	/**
	 * @return 岗位下的个人列表对应键关键字数组
	 */
	@Override
    public String[] getPersons() {
		return (String[]) mapOrgPost.get(PERSONS);
	}
}
