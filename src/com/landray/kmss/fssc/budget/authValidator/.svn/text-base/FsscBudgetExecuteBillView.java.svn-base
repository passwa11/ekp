package com.landray.kmss.fssc.budget.authValidator;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.ArrayUtil;


/*******************************************************************************
 * 校验单据查看页面查看预算按钮
 * @author
 * @version 1.0 2021-6-8
 * 
 */
public class FsscBudgetExecuteBillView implements IAuthenticationValidator {
	
	private IFsscBudgetExecuteService fsscBudgetExecuteService;
	
	public void setFsscBudgetExecuteService(IFsscBudgetExecuteService fsscBudgetExecuteService) {
		this.fsscBudgetExecuteService = fsscBudgetExecuteService;
	}
	
	/**
	   * 判断当前登录人是否为财务人员或者财务管理员
	   */
	  @Override
      public boolean validate(ValidatorRequestContext validatorrequestcontext)
				throws Exception {
			boolean auth=Boolean.TRUE;  //默认有权限
			String fdModelId =validatorrequestcontext.getParameter("fdModelId");
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock(" fsscBudgetExecute.fdModelId=:fdModelId");
			hqlInfo.setParameter("fdModelId", fdModelId);
			hqlInfo.setSelectBlock("fsscBudgetExecute.fdId");
			List<String> idList=fsscBudgetExecuteService.findList(hqlInfo);//该ID可能并不是公司ID
			if(ArrayUtil.isEmpty(idList)){//未占用、使用预算，隐藏按钮
				auth=Boolean.FALSE;
			}
			return auth;
		}
}
