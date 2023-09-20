package com.landray.kmss.sys.news.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 选择作者刷新所属部门
 * @author linxiuxian
 *
 */
public class SysNewsAuthorService implements IXMLDataBean {
	protected ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();

		String authorId = requestInfo.getParameter("authorId");
		if (StringUtils.isBlank(authorId)) {
			return lists;
		}
		SysOrgElement author = sysOrgCoreService.findByPrimaryKey(authorId);
		// 设置所属部门
		SysOrgElement dept = author.getFdParent();
		Map<String, String> maps = new HashMap<String, String>();
		if (dept != null) {
			maps.put("fdDepartmentId", dept.getFdId());
			maps.put("fdDepartmentName", dept.getDeptLevelNames());
		}
		lists.add(maps);
		return lists;
	}

}
