package com.landray.kmss.third.ding.notify.service.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.ding.notify.dao.IThirdDingNotifyLogDao;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyLogService;

public class ThirdDingNotifyLogServiceImp extends ExtendDataServiceImp
		implements IThirdDingNotifyLogService, ApplicationListener {

	@Override
	public void clear(int days) throws Exception {
		((IThirdDingNotifyLogDao) getBaseDao()).clear(30);
	}

	@Override
    public void onApplicationEvent(ApplicationEvent event) {

		// DingUtils.removeDingApiService(org);

	}

}
