package com.landray.kmss.sys.organization.service;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;

/**
 * 组织根据场景隔离业务对象接口
 * 
 * @author
 * @version 1.0 2018-05-21
 */
public interface ISysOrgAreaVisibleService {

	/**
	 * 流程身份切换时，根据当前用户获取该场所下的角色
	 * 
	 * @param person
	 * @return
	 */
	public List<Map<String, String>> getOrgProcessByArea(RequestContext context)
			throws Exception;

}
