package com.landray.kmss.fssc.expense.event;

import java.util.Date;

import com.landray.kmss.fssc.expense.model.FsscExpenseShareMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseShareMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 事后分摊流程结束
 * @author hejinbo
 *
 */
public class FsscExpenseShareMainFinishEvent implements IEventListener{
	
	private IFsscExpenseShareMainService FsscExpenseShareMainService;

	public IFsscExpenseShareMainService getFsscExpenseShareMainService() {
		if(FsscExpenseShareMainService==null){
			FsscExpenseShareMainService = (IFsscExpenseShareMainService) SpringBeanUtil.getBean("fsscExpenseShareMainService");
		}
		return FsscExpenseShareMainService;
	}
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscExpenseShareMain main = (FsscExpenseShareMain) execution.getMainModel();
		main.setDocPublishTime(new Date());
		getFsscExpenseShareMainService().update(main);
	}

}
