package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.UserUtil;

public class SysOrgGetCurrentUserInfo implements IXMLDataBean {
	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	/**
	 * 获取当前用户的id、fdName、fdMobileNo、fdEmail
	 * 
	 * @param request
	 *            RequestContext
	 * @return rntList List
	 */
	@Override
    public List<Map<String, Object>> getDataList(RequestContext request)
			throws Exception {
		String id = UserUtil.getUser().getFdId();
		SysOrgPerson curUser = (SysOrgPerson) sysOrgPersonService
				.findByPrimaryKey(id);

		List<Map<String, Object>> rntList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String fdName = curUser.getFdName();
		String fdMobileNo = curUser.getFdMobileNo();
		String fdEmail = curUser.getFdEmail();

		map.put("id", id);
		map.put("fdName", fdName);
		map.put("fdMobileNo", fdMobileNo);
		map.put("fdEmail", fdEmail);
		rntList.add(map);
		return rntList;
	}
}
