package com.landray.kmss.fssc.budget.event;

import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

/**
 * 流程废弃事件(清除预算占用)
 */
public class KmReviewAbandonEventBudget implements IEventListener{
private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			KmReviewMain main=(KmReviewMain)execution.getMainModel();
			//没有预算模块，不进行操作
			if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
				if(getFsscBudgetOperatService()!=null){
					JSONObject object = new JSONObject();
					object.put("fdModelName", KmReviewMain.class.getName());
					object.put("fdModelId", main.getFdId());
					getFsscBudgetOperatService().deleteFsscBudgetExecute(object);
				}
			}
			
		}
	}
}
