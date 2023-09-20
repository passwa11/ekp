package com.landray.kmss.hr.staff.event;

import com.landray.kmss.hr.staff.model.HrNumberConfig;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;


/**
 * hr入职流程提交（编号需要+1）
 * 
 * @author 蛋蛋
 *
 */
public class HrRzSubmitEven implements IEventListener {

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		HrNumberConfig hrNumberConfig = new HrNumberConfig();
		hrNumberConfig.hrNumberAddOne();
	}
	
}
