package com.landray.kmss.sys.organization.service;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;

import java.util.List;
import java.util.Map;

/**
 * 外部人员扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public interface ISysOrgElementExternalPersonService extends ISysOrgPersonService {

	/**
	 * 获取扩展属性
	 * 
	 * @param external
	 * @param fdId
	 * @param isView
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getExtProp(SysOrgElementExternal external, String fdId, boolean isView)
			throws Exception;

	/**
	 * 保存数据（WebService接口导入）
	 * 
	 * @param person
	 * @param tableName
	 * @param props
	 * @param isAdd
	 * @throws Exception
	 */
	public void save(SysOrgPerson person, String tableName, List<SysEcoExtPorp> props, boolean isAdd) throws Exception;

	/**
	 * 人员转外部人员
	 * @description:
	 * @param outParent
	 * @param sysOrgElementList
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/24 5:13 下午
	 */
	void updateTransformOut(SysOrgElement outParent, List<SysOrgElement> sysOrgElementList)throws Exception;

}
