package com.landray.kmss.fssc.voucher.listener;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;

/**
 * 自动生成凭证
 */
public class FsscVoucherAutoCreateListener implements IEventListener{

	private IFsscVoucherMainService fsscVoucherMainService;

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if(execution == null || execution.getMainModel() == null){
			return;
		}
		IBaseModel model = execution.getMainModel();
		fsscVoucherMainService.addOrUpdateVoucher(model);
	}

	public void setFsscVoucherMainService(IFsscVoucherMainService fsscVoucherMainService) {
		this.fsscVoucherMainService = fsscVoucherMainService;
	}
}
