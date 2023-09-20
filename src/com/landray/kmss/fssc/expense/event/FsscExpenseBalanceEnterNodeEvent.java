package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetOperatService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

import net.sf.json.JSONObject;

/**
 * 起草节点进入事件，释放预算数据
 * @author wangjinman
 *
 */
public class FsscExpenseBalanceEnterNodeEvent implements IEventListener{
	private IFsscCommonBudgetOperatService fsscBudgetOperatService;
	
	public IFsscCommonBudgetOperatService getFsscBudgetOperatService() {
		if(fsscBudgetOperatService==null){
			fsscBudgetOperatService = (IFsscCommonBudgetOperatService) SpringBeanUtil.getBean("fsscBudgetOperatService");
		}
		return fsscBudgetOperatService;
	}
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseBalance main = (FsscExpenseBalance) execution.getMainModel();
		if(main==null){
			return;
		}
		//没有预算模块，不进行操作
		if(FsscCommonUtil.checkHasModule("/fssc/budget/")){
			if(getFsscBudgetOperatService()!=null){
				JSONObject object = new JSONObject();
				object.put("fdModelName", FsscExpenseBalance.class.getName());
				object.put("fdModelId", main.getFdId());
				getFsscBudgetOperatService().deleteFsscBudgetExecute(object);
			}
		}
	}

}
