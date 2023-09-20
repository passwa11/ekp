package com.landray.kmss.sys.organization.service;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;

import java.util.List;

/**
 * 外部岗位扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public interface ISysOrgElementExternalPostService extends ISysOrgPostService {

	/**
	 * 保存数据（WebService接口导入）
	 * 
	 * @param post
	 * @param isAdd
	 * @throws Exception
	 */
	public void save(SysOrgPost post, boolean isAdd) throws Exception;

	/**
	 * 岗位转外部岗位
	 * @description:
	 * @param outParent
	 * @param sysOrgElementList
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/24 5:13 下午
	 */
	void updateTransformOut(SysOrgElement outParent, List<SysOrgElement> sysOrgElementList)throws Exception;

}
