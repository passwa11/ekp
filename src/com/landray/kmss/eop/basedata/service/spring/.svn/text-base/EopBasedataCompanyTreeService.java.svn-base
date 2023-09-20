package com.landray.kmss.eop.basedata.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCompanyTreeService implements IXMLDataBean{
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String valid=requestInfo.getParameter("valid");
		String group=requestInfo.getParameter("group");
		String parentId=requestInfo.getParameter("parentId");
		String where = "1=1";
		if(StringUtil.isNotNull(valid)){
			where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable",Boolean.valueOf(valid));
		}else{
			where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdIsAvailable",true);
		}
		if(StringUtil.isNotNull(group)){
			where = StringUtil.linkString(where, " and ", "eopBasedataCompany.fdGroup.fdId is null");
		}
		if(StringUtil.isNotNull(parentId)){
			where = StringUtil.linkString(where, " and ", "1=2");
		}else{
//			where = StringUtil.linkString(where, " and ", "1=2");
		}
		List<SysOrgElement> financial=EopBasedataAuthUtil.getFinanceStaffAndManagerList(null);
		SysOrgElement user=UserUtil.getUser();
		if(!UserUtil.checkRole("ROLE_EOPBASEDATA_COMPANY")){
    		//没有公司权限，财务只能看到有权限看到的公司，普通人无权限查看
			if(financial.contains(user)){//财务
				List<EopBasedataCompany> companyList=eopBasedataCompanyService.findCompanyByUserId(user.getFdId());
	    		if(!ArrayUtil.isEmpty(companyList)){
	    			where=StringUtil.linkString(where, " and ", HQLUtil.buildLogicIN("eopBasedataCompany.fdId", 
	    					ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";"))));
	    		}
			}else{//普通用户
				where = StringUtil.linkString(where, " and ", "1=2"); //拼接不可能条件
			}
    	}
		hqlInfo.setSelectBlock("new map(eopBasedataCompany.fdName as text,eopBasedataCompany.fdId as value)");
		hqlInfo.setWhereBlock(where);
		return eopBasedataCompanyService.findList(hqlInfo);
	}

}
