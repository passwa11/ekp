package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.fssc.common.constant.FsscCommonConstant;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;

/**
 * 付款节点处理人通过事件，将状态设置为已付款
 * @author wangjinman
 *
 */
public class FsscExpensePaymentNodePassEvent implements IEventListener{
	private IFsscExpenseMainService fsscExpenseMainService;
	public void setFsscExpenseMainService(IFsscExpenseMainService fsscExpenseMainService) {
		this.fsscExpenseMainService = fsscExpenseMainService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		if(main!=null){
			main.setFdPaymentStatus("2");
			fsscExpenseMainService.getBaseDao().update(main);
		}
	}

}
