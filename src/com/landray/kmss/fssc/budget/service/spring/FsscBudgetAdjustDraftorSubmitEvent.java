package com.landray.kmss.fssc.budget.service.spring;

import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 起草人提交事件，占用预算
 * @author 
 *
 */
public class FsscBudgetAdjustDraftorSubmitEvent implements IEventListener{
protected IFsscBudgetAdjustMainService fsscBudgetAdjustMainService;
	
	public void setFsscBudgetAdjustMainService(IFsscBudgetAdjustMainService fsscBudgetAdjustMainService) {
		if(fsscBudgetAdjustMainService==null){
			fsscBudgetAdjustMainService=(IFsscBudgetAdjustMainService) SpringBeanUtil.getBean("fsscBudgetAdjustMainService");
		}
		this.fsscBudgetAdjustMainService = fsscBudgetAdjustMainService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscBudgetAdjustMain fsscBudgetAdjust = (FsscBudgetAdjustMain) execution.getMainModel();
		//只处理驳回状态
		if(!SysDocConstant.DOC_STATUS_REFUSE.equals(fsscBudgetAdjust.getDocStatus())){
			return;
		}
		fsscBudgetAdjustMainService.operationBudget(fsscBudgetAdjust, "create");
	}
}
