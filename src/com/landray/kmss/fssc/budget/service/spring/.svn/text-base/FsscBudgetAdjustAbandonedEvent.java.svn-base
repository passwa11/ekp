package com.landray.kmss.fssc.budget.service.spring;

import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.util.SpringBeanUtil;

/***
 * 预算调整流程废弃将占用金额释放
 * **/

public class FsscBudgetAdjustAbandonedEvent implements IEventListener {
	
	protected IFsscBudgetAdjustMainService fsscBudgetAdjustMainService;
	
	public void setFsscBudgetAdjustMainService(IFsscBudgetAdjustMainService fsscBudgetAdjustMainService) {
		if(fsscBudgetAdjustMainService==null){
			fsscBudgetAdjustMainService=(IFsscBudgetAdjustMainService) SpringBeanUtil.getBean("fsscBudgetAdjustMainService");
		}
		this.fsscBudgetAdjustMainService = fsscBudgetAdjustMainService;
	}

	@Override
    public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		LbpmProcess self = execution.getProcessInstance();
		if (self == null) {
			return;
		}
		String modelName = self.getFdModelName();
		if (!"com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain".equals(
				modelName)) {
			return;
		}
		FsscBudgetAdjustMain fsscBudgetAdjust = (FsscBudgetAdjustMain) execution.getMainModel();
		fsscBudgetAdjustMainService.operationBudget(fsscBudgetAdjust, "delete");
	}
}

