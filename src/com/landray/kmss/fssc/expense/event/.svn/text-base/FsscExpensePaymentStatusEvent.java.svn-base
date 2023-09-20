package com.landray.kmss.fssc.expense.event;

import com.landray.kmss.fssc.common.constant.FsscCommonConstant;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;

/**
 * 付款节点进入事件，将报销单付款状态更新为待付款
 * @author 
 *
 */
public class FsscExpensePaymentStatusEvent implements IEventListener{
	private IFsscExpenseMainService fsscExpenseMainService;
	public void setFsscExpenseMainService(IFsscExpenseMainService fsscExpenseMainService) {
		this.fsscExpenseMainService = fsscExpenseMainService;
	}
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		if(!FsscCommonConstant.FSSC_PAYMENT_STATUS_2.equals(main.getFdPaymentStatus())){
			main.setFdPaymentStatus(FsscCommonConstant.FSSC_PAYMENT_STATUS_1);
			fsscExpenseMainService.getBaseDao().update(main);
		}
	}

}
