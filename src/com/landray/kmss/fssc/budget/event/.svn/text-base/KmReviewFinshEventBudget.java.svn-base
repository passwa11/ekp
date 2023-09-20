package com.landray.kmss.fssc.budget.event;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.fssc.budget.model.FsscBudgetExecute;
import com.landray.kmss.fssc.budget.service.IFsscBudgetExecuteService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

/**
 * 流程结束事件(预算占用改为使用状态)
 */
public class KmReviewFinshEventBudget implements IEventListener{
	private IFsscBudgetExecuteService fsscBudgetExecuteService;
	
	public IFsscBudgetExecuteService getFsscBudgetExecuteService() {
		if(fsscBudgetExecuteService==null){
			fsscBudgetExecuteService = (IFsscBudgetExecuteService) SpringBeanUtil.getBean("fsscBudgetExecuteService");
		}
		return fsscBudgetExecuteService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			KmReviewMain main=(KmReviewMain)execution.getMainModel();
			//没有预算模块，不进行操作
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				HQLInfo hqlInfo=new HQLInfo();
				hqlInfo.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName and fdType='2'");
				hqlInfo.setParameter("fdModelId", main.getFdId());
				hqlInfo.setParameter("fdModelName", KmReviewMain.class.getName());
				
				List<FsscBudgetExecute> list=getFsscBudgetExecuteService().findList(hqlInfo);
				for (FsscBudgetExecute fsscBudgetExecute : list) {
					fsscBudgetExecute.setFdType("3");
					getFsscBudgetExecuteService().update(fsscBudgetExecute);
				}
			}
			
		}
	}
}
