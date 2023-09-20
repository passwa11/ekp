package com.landray.kmss.fssc.budget.authValidator;

import java.util.List;

import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.sys.authentication.filter.FilterContext;
import com.landray.kmss.sys.authentication.filter.HQLFragment;
import com.landray.kmss.sys.authentication.filter.IAuthenticationFilter;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;


/*******************************************************************************
 * 校验当前人员是否有预算查看权限
 * 1、公司下的财务人员和财务管理员
 * 2、有成本中心维度的第一、第二负责人、业务财务经理、预算管理员
 * 3、有项目的校验是否为项目经理
 * @author
 * @version 1.0 2020-4-22
 * 
 */
public class FsscBudgetDataFilter implements IAuthenticationFilter {
	
	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}

	/**
	   * 判断当前登录人预算查询列表权限
	   */

	@Override
	public int getAuthHQLInfo(FilterContext ctx) throws Exception {
		String whereBlock = "";
		String joinBlock = "";
		SysOrgElement user=UserUtil.getUser();
    	HQLFragment hqlFragment = new HQLFragment();
    	if(!UserUtil.checkRole("ROLE_FSSCBUDGET_DATA_VIEW")){//没有查看所有权限
			//多级.可能会有问题，所以先查找一遍
			List<String> costCenterIdList=EopBasedataAuthUtil.getCostCenterAuth(null);
			List<String> projectIdList=EopBasedataAuthUtil.getProjectAuth(null);
    		if(EopBasedataAuthUtil.isManagerOrStaff(null)||!ArrayUtil.isEmpty(costCenterIdList)||!ArrayUtil.isEmpty(projectIdList)){ //公司财务人员或者公司财务管理员|登录人为成本中心负责人，预算管理员
    			List<EopBasedataCompany> companyList=eopBasedataCompanyService.findCompanyByUserId(user.getFdId());
    			if(EopBasedataAuthUtil.isManagerOrStaff(null)&&!ArrayUtil.isEmpty(companyList)){
    				joinBlock+=" left join fsscBudgetData.fdCompany company ";
    				whereBlock=HQLUtil.buildLogicIN("company.fdId", ArrayUtil.convertArrayToList(ArrayUtil.joinProperty(companyList, "fdId", ";")[0].split(";")));
    			}
    			if(!ArrayUtil.isEmpty(costCenterIdList)){
    				joinBlock+=" left join fsscBudgetData.fdCostCenter costCenter ";
    				whereBlock=StringUtil.linkString(whereBlock, " or ", HQLUtil.buildLogicIN("costCenter.fdId", costCenterIdList));
    			}
    			if(!ArrayUtil.isEmpty(projectIdList)){
    				joinBlock+=" left join fsscBudgetData.fdProject project ";
    				whereBlock=StringUtil.linkString(whereBlock, " or ", HQLUtil.buildLogicIN("project.fdId", projectIdList));
    			}
    			joinBlock+=" left join fsscBudgetData.docCreator creator ";
    			whereBlock=StringUtil.linkString(whereBlock, " or ", "creator.fdId=:docCreatorId");
    			joinBlock+=" left join fsscBudgetData.fdPerson person ";
    			whereBlock=StringUtil.linkString(whereBlock, " or ", "person.fdId=:docCreatorId");
    			HQLParameter hqlParameter=new HQLParameter("docCreatorId",user.getFdId());
    			hqlFragment.setParameter(hqlParameter);
    		}else{
    			joinBlock+=" left join fsscBudgetData.docCreator creator ";
    			whereBlock=StringUtil.linkString(whereBlock, " and ", "creator.fdId=:docCreatorId");
    			HQLParameter hqlParameter=new HQLParameter("docCreatorId",user.getFdId());
    			hqlFragment.setParameter(hqlParameter);
    		}
    		
    	}
    	hqlFragment.setJoinBlock(joinBlock);
		hqlFragment.setWhereBlock(whereBlock);
		ctx.setHqlFragment(hqlFragment);
		return FilterContext.RETURN_VALUE;
	}
}
