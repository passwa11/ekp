package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 角色线操作，Ajax调用
 * 
 * @author 叶中奇
 * @version 创建时间：2008-11-21 下午04:47:25
 */
public class SysOrgRoleLineOption implements IXMLDataBean {

	private ISysOrgRoleLineService sysOrgRoleLineService = null;

	public void setSysOrgRoleLineService(
			ISysOrgRoleLineService sysOrgRoleLineService) {
		this.sysOrgRoleLineService = sysOrgRoleLineService;
	}

	@Override
    public List<Map<String, String>> getDataList(RequestContext requestInfo)
			throws Exception {
		List<Map<String, String>> rtnList = null;
		try {
			String confId = requestInfo.getParameter("confId");
			if (StringUtil.isNull(confId)) {
				return null;
			}
			String method = requestInfo.getParameter("method");
			if ("quickAdd".equals(method)) {
				rtnList = sysOrgRoleLineService.quickAdd(requestInfo);
			} else if ("delete".equals(method)) {
				rtnList = sysOrgRoleLineService.deleteChildren(requestInfo);
			} else if ("deleteAll".equals(method)) {
				rtnList = sysOrgRoleLineService.deleteAllChildren(requestInfo);
			} else if ("move".equals(method)) {
				rtnList = sysOrgRoleLineService.move(requestInfo);
			} else {
				throw new UnexpectedRequestException();
			}
			if (rtnList == null) {
				Map<String, String> node = new HashMap<String, String>();
				node.put("success", "true");
				rtnList = new ArrayList<Map<String, String>>();
				rtnList.add(node);
			}
		} catch (Exception e) {
			Map<String, String> node = new HashMap<String, String>();
			node.put("success", "false");
			node.put("message", ResourceUtil.getString(
					"sysOrgRoleLine.msg.error", "sys-organization", requestInfo
							.getLocale(), e.toString()));
			rtnList = new ArrayList<Map<String, String>>();
			rtnList.add(node);
		}
		return rtnList;
	}
}
