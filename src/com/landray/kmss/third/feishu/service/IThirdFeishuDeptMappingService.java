package com.landray.kmss.third.feishu.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.feishu.model.ThirdFeishuDeptMapping;

import net.sf.json.JSONObject;

public interface IThirdFeishuDeptMappingService extends IExtendDataService {

	public ThirdFeishuDeptMapping findByEkpId(String ekpId) throws Exception;

	public ThirdFeishuDeptMapping findByFeishuId(String feishuId)
			throws Exception;

	public void addMapping(SysOrgElement dept) throws Exception;

	/**
	 * @throws Exception
	 *             部门映射初始化
	 */
	public void updateDept(JSONObject json) throws Exception;

	public void addMapping(SysOrgElement dept, String feishuId)
			throws Exception;
}
