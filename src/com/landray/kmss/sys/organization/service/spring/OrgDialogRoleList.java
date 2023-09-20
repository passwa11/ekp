package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.service.ISysOrgRoleService;
import com.landray.kmss.util.StringUtil;

public class OrgDialogRoleList implements IXMLDataBean, SysOrgConstant {
	private ISysOrgRoleService sysOrgRoleService;

	@Override
	@SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) throws Exception {
		
		String whereBlock = "sysOrgRole.fdIsAvailable= :fdIsAvailable";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		String para = xmlContext.getParameter("fdConfId");
		hqlInfo.setJoinBlock("left join sysOrgRole.fdRoleConf fdRoleConf");
		if (StringUtil.isNull(para)) {
			whereBlock += " and fdRoleConf.fdId is null";
		} else {
			whereBlock += " and fdRoleConf.fdId=:fdRoleConfId";
			hqlInfo.setParameter("fdRoleConfId", para);
		}

		para = xmlContext.getParameter("ismuti");
		if (StringUtil.isNull(para)) {
			whereBlock = whereBlock + " and sysOrgRole.fdIsMultiple= :fdIsMultiple";
			hqlInfo.setParameter("fdIsMultiple", Boolean.FALSE);
		}

		// 由于数据量不大，先查找，在结果中过滤数据
		int orgType = Integer.parseInt(xmlContext.getParameter("orgType"));
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysOrgRole.fdOrder, sysOrgRole."
				+ SysLangUtil.getLangFieldName("fdName"));
		List findList = sysOrgRoleService.findList(hqlInfo);
		List rtnMapList = new ArrayList();
		for (int i = 0; i < findList.size(); i++) {
			SysOrgRole elem = (SysOrgRole) findList.get(i);
			if (StringUtil.isNull(elem.getFdRtnValue())) {
                continue;
            }
			int rtnType = Integer.parseInt(elem.getFdRtnValue());
			if ((orgType & rtnType) == rtnType
					|| (orgType == ORG_TYPE_ROLE && ((rtnType & ORG_TYPE_POST) == ORG_TYPE_POST) || (rtnType & ORG_TYPE_PERSON) == ORG_TYPE_PERSON)) {
				rtnMapList.add(OrgDialogUtil.getResultEntry(elem));
			}
		}
		return rtnMapList;
	}

	public void setSysOrgRoleService(ISysOrgRoleService sysOrgRoleService) {
		this.sysOrgRoleService = sysOrgRoleService;
	}
}
