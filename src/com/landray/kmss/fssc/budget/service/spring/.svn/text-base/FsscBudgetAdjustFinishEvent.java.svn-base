package com.landray.kmss.fssc.budget.service.spring;

import java.util.Date;

import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetAdjustMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.util.SpringBeanUtil;

/***
 * 预算调整流程结束，将借出成本中心对应的金额转移到借入成本中心
 * **/
public class FsscBudgetAdjustFinishEvent implements IEventListener {
	
	protected IFsscBudgetAdjustMainService fsscBudgetAdjustMainService;
	
	public void setFsscBudgetAdjustMainService(IFsscBudgetAdjustMainService fsscBudgetAdjustMainService) {
		if(fsscBudgetAdjustMainService==null){
			fsscBudgetAdjustMainService=(IFsscBudgetAdjustMainService) SpringBeanUtil.getBean("fsscBudgetAdjustMainService");
		}
		this.fsscBudgetAdjustMainService = fsscBudgetAdjustMainService;
	}

	@Override
    public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
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
		fsscBudgetAdjust.setDocPublishTime(new Date());
		fsscBudgetAdjustMainService.operationBudget(fsscBudgetAdjust, "publish");
	}
}

