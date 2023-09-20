package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.util.StringUtil;

public class SysOrgGroupServiceImp extends SysOrgElementServiceImp implements
		ISysOrgGroupService, IXMLDataBean {

	@Override
    public SysOrgGroup findGroup(String keyword) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdKeyword=:fdKeyword");
		hqlInfo.setParameter("fdKeyword", keyword);
		List<SysOrgGroup> groupList = findList(hqlInfo);

		for (SysOrgGroup group : groupList) {
			String fdKeyword = group.getFdKeyword();
			if (StringUtil.isNotNull(fdKeyword) && fdKeyword.equals(keyword)) {
				return group;
			}
		}

		return null;
	}

	/*
	 * 获取群组名
	 */
	@Override
    public List<String> getDataList(RequestContext requestInfo)
			throws Exception {
		List<String> rtnList = new ArrayList<String>();
		String groupId = requestInfo.getParameter("groupId");
		IBaseModel model = findByPrimaryKey(groupId);
		if (model != null) {
			SysOrgGroup sysOrgGroup = (SysOrgGroup) model;
			rtnList.add(sysOrgGroup.getFdName());
		}

		return rtnList;
	}
}
