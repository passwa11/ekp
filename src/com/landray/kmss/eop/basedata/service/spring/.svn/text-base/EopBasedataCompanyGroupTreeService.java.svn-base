package com.landray.kmss.eop.basedata.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyGroupService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.util.StringUtil;

public class EopBasedataCompanyGroupTreeService implements IXMLDataBean{
	private IEopBasedataCompanyGroupService eopBasedataCompanyGroupService;
	public void setEopBasedataCompanyGroupService(IEopBasedataCompanyGroupService eopBasedataCompanyGroupService) {
		this.eopBasedataCompanyGroupService = eopBasedataCompanyGroupService;
	}
	private IEopBasedataCompanyService eopBasedataCompanyService;
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String valid = requestInfo.getParameter("valid");
		HQLInfo hqlInfo = new HQLInfo();
		String parentId=requestInfo.getParameter("parentId");
		String where = "1=1";
		if(StringUtil.isNotNull(valid)){
			where = StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
		}
		if(StringUtil.isNotNull(parentId)){
			where = StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.hbmParent.fdId=:parentId");
			hqlInfo.setParameter("parentId",parentId);
		}else{
			where = StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.hbmParent.fdId is null");
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setSelectBlock("new map(eopBasedataCompanyGroup.fdName as text,eopBasedataCompanyGroup.fdId as value,'group' as type)");
		return eopBasedataCompanyGroupService.findList(hqlInfo);
	}

}
