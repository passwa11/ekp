package com.landray.kmss.third.im.kk.queue.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.third.im.kk.queue.service.IKkNotifyQueueErrorService;

public class KkNotifyQueueErrorAction extends ExtendAction {

	private IKkNotifyQueueErrorService kkNotifyQueueErrorService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (kkNotifyQueueErrorService == null) {
			kkNotifyQueueErrorService = (IKkNotifyQueueErrorService) getBean(
					"kkNotifyQueueErrorService");
        }
		return kkNotifyQueueErrorService;
    }

}
