package com.landray.kmss.fssc.budget.event;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.util.SendSFUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;

/**
 * 流程结束事件(回传sf数据)-审批通过
 */
public class SFKmReviewFinshEvent implements IEventListener{
	
	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			KmReviewMain main=(KmReviewMain) execution.getMainModel();
			SendSFUtil.sendSF(main, "OA审批通过");
		}
	}
}
