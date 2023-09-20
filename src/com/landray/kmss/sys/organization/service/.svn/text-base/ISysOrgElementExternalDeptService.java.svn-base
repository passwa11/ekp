package com.landray.kmss.sys.organization.service;

import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.webservice.eco.org.SysEcoExtPorp;

import java.util.List;
import java.util.Map;

/**
 * 外部组织扩展
 *
 * @author 潘永辉 Mar 17, 2020
 */
public interface ISysOrgElementExternalDeptService extends ISysOrgDeptService {

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
	 * @param dept
	 * @param tableName
	 * @param props
	 * @param isAdd
	 * @throws Exception
	 */
	public void save(SysOrgDept dept, String tableName, List<SysEcoExtPorp> props, boolean isAdd) throws Exception;

	/**
	 *  内部组织转换成外部组织
	 * @description:
	 * @param outParent 外部父组织
	 * @param inSysOrgElement 内部父部门
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/24 4:31 下午
	 */
	void updateTransformOut(SysOrgElement outParent,SysOrgElement inSysOrgElement)throws Exception;

}
