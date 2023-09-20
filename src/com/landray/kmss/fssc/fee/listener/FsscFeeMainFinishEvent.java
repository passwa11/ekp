package com.landray.kmss.fssc.fee.listener;

import java.util.Date;

import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.service.IFsscFeeMainService;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.util.SpringBeanUtil;

public class FsscFeeMainFinishEvent implements IEventListener{
	
	private IFsscFeeMainService  fsscFeeMainService;
	
	
	public IFsscFeeMainService getFsscFeeMainService() {
		if(fsscFeeMainService==null){
			fsscFeeMainService = (IFsscFeeMainService) SpringBeanUtil.getBean("fsscFeeMainService");
		}
		return fsscFeeMainService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		FsscFeeMain main = (FsscFeeMain) execution.getMainModel();
		main.setDocPublishTime(new Date());
		getFsscFeeMainService().update(main);
	}

}
