package com.landray.kmss.km.reviewext.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;

public class KmReviewHrAttendJiTiQianKaListener implements IEventListener {

	private static final Logger logger = LoggerFactory.getLogger(KmReviewHrAttendJiTiQianKaListener.class);

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		if (execution.getMainModel() instanceof KmReviewMain) {
			logger.debug("receive KmReviewEqbSignListener,parameter=" + parameter);
			JSONObject params = JSONObject.parseObject(parameter);
			KmReviewMain mainModel = (KmReviewMain) execution.getMainModel();
		}
	}

}
