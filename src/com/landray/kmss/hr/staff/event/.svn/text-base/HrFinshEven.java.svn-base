package com.landray.kmss.hr.staff.event;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;


/**
 * hr流程结束事件
 * 
 * @author 蛋蛋
 *
 */
public class HrFinshEven implements IEventListener {

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (!(execution.getMainModel() instanceof KmReviewMain)) {
			return;
		}
		KmReviewMain main=(KmReviewMain)execution.getMainModel();
		main.getExtendDataModelInfo();
	}
	
}
