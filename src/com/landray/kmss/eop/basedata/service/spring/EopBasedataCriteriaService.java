package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.service.IEopBasedataBudgetItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyGroupService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataInnerOrderService;
import com.landray.kmss.eop.basedata.service.IEopBasedataProjectService;
import com.landray.kmss.eop.basedata.service.IEopBasedataWbsService;
import com.landray.kmss.util.StringUtil;
/**
 * 公司等基础数据筛选条件公用类
 * 
 * @author XIEXX
 *
 */
public class EopBasedataCriteriaService implements IXMLDataBean{
	
	protected IEopBasedataCompanyGroupService eopBasedataCompanyGroupService;
	
	public void setEopBasedataCompanyGroupService(IEopBasedataCompanyGroupService eopBasedataCompanyGroupService) {
		this.eopBasedataCompanyGroupService = eopBasedataCompanyGroupService;
	}
	
	protected IEopBasedataCompanyService eopBasedataCompanyService;
	
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	protected IEopBasedataCostCenterService eopBasedataCostCenterService;
	
	public void setEopBasedataCostCenterService(IEopBasedataCostCenterService eopBasedataCostCenterService) {
		this.eopBasedataCostCenterService = eopBasedataCostCenterService;
	}
	
	protected IEopBasedataProjectService eopBasedataProjectService;
	
	public void setEopBasedataProjectService(IEopBasedataProjectService eopBasedataProjectService) {
		this.eopBasedataProjectService = eopBasedataProjectService;
	}

	protected IEopBasedataWbsService eopBasedataWbsService;
	
	public void setEopBasedataWbsService(IEopBasedataWbsService eopBasedataWbsService) {
		this.eopBasedataWbsService = eopBasedataWbsService;
	}

	protected IEopBasedataInnerOrderService eopBasedataInnerOrderService;
	
	public void setEopBasedataInnerOrderService(IEopBasedataInnerOrderService eopBasedataInnerOrderService) {
		this.eopBasedataInnerOrderService = eopBasedataInnerOrderService;
	}

	protected IEopBasedataBudgetItemService eopBasedataBudgetItemService;
	
	public void setEopBasedataBudgetItemService(IEopBasedataBudgetItemService eopBasedataBudgetItemService) {
		this.eopBasedataBudgetItemService = eopBasedataBudgetItemService;
	}
	
	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnList=new ArrayList<>();
		String queryType=requestInfo.getParameter("queryType");
		String fdIsAvailable=requestInfo.getParameter("fdIsAvailable");
		HQLInfo hqlInfo = new HQLInfo();
		String where="";
		if("fdCompanyGroup".equals(queryType)){
			hqlInfo = new HQLInfo();
			where="";
			String parentId=requestInfo.getParameter("parentId");//上级ID
			hqlInfo.setSelectBlock("new map(eopBasedataCompanyGroup.fdId as value,eopBasedataCompanyGroup.fdName as text)");
			if(StringUtil.isNotNull(parentId)){
				where=StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.hbmParent.fdId=:parentId");
				hqlInfo.setParameter("parentId", parentId);
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.hbmParent.fdId is null");
			}
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataCompanyGroup.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataCompanyGroupService.findList(hqlInfo);
		}else if("fdCompany".equals(queryType)){
			hqlInfo = new HQLInfo();
			String group=requestInfo.getParameter("group");
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataCompany.fdId as value,eopBasedataCompany.fdName as text)");
			if(StringUtil.isNotNull(group)){
				where=StringUtil.linkString(where, " and ", "eopBasedataCompany.fdGroup.fdId=:group");
				hqlInfo.setParameter("group", group);
			}
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataCompany.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataCompanyService.findList(hqlInfo);
		}else if("costCenterGroup".equals(queryType)||"costCenter".equals(queryType)){
			hqlInfo = new HQLInfo();
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataCostCenter.fdId as value,eopBasedataCostCenter.fdName as text)");
			where=StringUtil.linkString(where, " and ", "eopBasedataCostCenter.fdIsGroup=:fdIsGroup");
			if("costCenterGroup".equals(queryType)){//成本中心组
				hqlInfo.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_GROUP);
			}else if("costCenter".equals(queryType)){//成本中心
				hqlInfo.setParameter("fdIsGroup", EopBasedataConstant.FSSC_BASE_COST_CENTER_TYPE_CENTER);
			}
			where=StringUtil.linkString(where, " and ", "eopBasedataCostCenter.fdCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataCostCenter.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataCostCenterService.findList(hqlInfo);
		}else if("project".equals(queryType)){
			hqlInfo = new HQLInfo();
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataProject.fdId as value,eopBasedataProject.fdName as text)");
			where=StringUtil.linkString(where, " and ", "eopBasedataProject.fdCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataProject.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataProject.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataProjectService.findList(hqlInfo);
		}else if("wbs".equals(queryType)){
			hqlInfo = new HQLInfo();
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataWbs.fdId as value,eopBasedataWbs.fdName as text)");
			where=StringUtil.linkString(where, " and ", "eopBasedataWbs.fdCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataWbs.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataWbs.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataWbsService.findList(hqlInfo);
		}else if("innerOrder".equals(queryType)){
			hqlInfo = new HQLInfo();
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataInnerOrder.fdId as value,eopBasedataInnerOrder.fdName as text)");
			where=StringUtil.linkString(where, " and ", "eopBasedataInnerOrder.fdCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataInnerOrder.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataInnerOrder.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataInnerOrderService.findList(hqlInfo);
		}else if("budgetItemCom".equals(queryType)){
			hqlInfo = new HQLInfo();
			String fdCompanyId=requestInfo.getParameter("fdCompanyId");
			String fdStatus=requestInfo.getParameter("fdStatus");
			where="";
			hqlInfo.setSelectBlock("new map(EopBasedataBudgetItem.fdId as value,EopBasedataBudgetItem.fdName as text)");
			where=StringUtil.linkString(where, " and ", "EopBasedataBudgetItem.fdCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "EopBasedataBudgetItem.fdStatus=:fdStatus");
				hqlInfo.setParameter("fdStatus", fdStatus);
			}else{
				where=StringUtil.linkString(where, " and ", "EopBasedataBudgetItem.fdStatus=:fdStatus");
				hqlInfo.setParameter("fdStatus", "0");
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataBudgetItemService.findList(hqlInfo);
		}else if("budgetItem".equals(queryType)){
			hqlInfo = new HQLInfo();
			where="";
			hqlInfo.setSelectBlock("new map(eopBasedataBudgetItem.fdCode as value,eopBasedataBudgetItem.fdName as text)");
			if(StringUtil.isNotNull(fdIsAvailable)){
				where=StringUtil.linkString(where, " and ", "eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdIsAvailable", Boolean.valueOf(fdIsAvailable));
			}else{
				where=StringUtil.linkString(where, " and ", "eopBasedataBudgetItem.fdIsAvailable=:fdIsAvailable");
				hqlInfo.setParameter("fdStatus", true);
			}
			hqlInfo.setWhereBlock(where);
			rtnList = eopBasedataBudgetItemService.findList(hqlInfo);
		}
		return rtnList;
	}

}
