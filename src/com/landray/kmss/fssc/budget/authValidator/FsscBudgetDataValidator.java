package com.landray.kmss.fssc.budget.authValidator;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
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
public class FsscBudgetDataValidator implements IAuthenticationValidator {
	
	private IFsscBudgetDataService fsscBudgetDataService;
	
	public void setFsscBudgetDataService(IFsscBudgetDataService fsscBudgetDataService) {
		this.fsscBudgetDataService = fsscBudgetDataService;
	}
	
	private ISysOrgCoreService sysOrgCoreService;
	
	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	/**
	   * 判断当前登录人是否为财务人员或者财务管理员
	   */
	  @Override
      public boolean validate(ValidatorRequestContext validatorrequestcontext)
				throws Exception {
			boolean auth=Boolean.TRUE;  //默认有权限
			String userId=UserUtil.getUser().getFdId();
			String fdId =validatorrequestcontext.getParameter("fdId");
			FsscBudgetData budgetData=(FsscBudgetData) fsscBudgetDataService.findByPrimaryKey(fdId,FsscBudgetData.class,true);//该ID可能并不是公司ID
			if(budgetData!=null){
				EopBasedataCompany company=budgetData.getFdCompany();
				if(company!=null){
					auth=EopBasedataAuthUtil.isManagerOrStaff(company.getFdId());  //公司不为空，校验是否是财务人员或者财务管理员
				}
				EopBasedataCostCenter costCenter=budgetData.getFdCostCenter();
				if(!auth&&costCenter!=null){
					List<SysOrgElement> costCenterAuth=new ArrayList<>();
					ArrayUtil.concatTwoList(costCenter.getFdFirstCharger(), costCenterAuth);  //第一负责人
					ArrayUtil.concatTwoList(costCenter.getFdSecondCharger(), costCenterAuth);  //第二负责人
					ArrayUtil.concatTwoList(costCenter.getFdManager(), costCenterAuth);  //业务财务经理
					ArrayUtil.concatTwoList(costCenter.getFdBudgetManager(), costCenterAuth);  //预算管理员
					List<String> personIds=sysOrgCoreService.expandToPersonIds(costCenterAuth);
					if(personIds.contains(userId)){
						auth=true;
					}
				}
				EopBasedataProject project=budgetData.getFdProject();
				if(!auth&&project!=null){
					String fdManagerId=project.getFdProjectManager()!=null?project.getFdProjectManager().getFdId():"";
					if(userId.equals(fdManagerId)){
						auth=true;
					}
				}
			}
			return auth;
		}
}
