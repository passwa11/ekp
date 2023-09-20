package com.landray.kmss.third.ding.service.spring;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.service.IThirdDingNotifyService;

import net.sf.json.JSONArray;

public class ThirdDingNotifyServiceImp extends ExtendDataServiceImp
		implements IThirdDingNotifyService, DingConstant {

	private IThirdDingNotifyService thirdDingNotifyWFService;

	public IThirdDingNotifyService getThirdDingNotifyWFService() {
		return thirdDingNotifyWFService;
	}

	public void setThirdDingNotifyWFService(
			IThirdDingNotifyService thirdDingNotifyWFService) {
		this.thirdDingNotifyWFService = thirdDingNotifyWFService;
	}

	public IThirdDingNotifyService getThirdDingNotifyWRService() {
		return thirdDingNotifyWRService;
	}

	public void setThirdDingNotifyWRService(
			IThirdDingNotifyService thirdDingNotifyWRService) {
		this.thirdDingNotifyWRService = thirdDingNotifyWRService;
	}

	private IThirdDingNotifyService thirdDingNotifyWRService;

	private IThirdDingNotifyService getServiceProvider() {
		DingConfig config = DingConfig.newInstance();
		String notifyApiType = config.getNotifyApiType();
		if ("WR".equals(notifyApiType)) {
			return thirdDingNotifyWRService;
		}
		return thirdDingNotifyWFService;
	}

	@Override
	public void synchroError(SysQuartzJobContext context) {
		getServiceProvider().synchroError(context);
	}

	@Override
	public void handle(boolean del) throws Exception {
		getServiceProvider().handle(del);
	}

	@Override
	public void cleaningAllNotify() throws Exception {
		getServiceProvider().cleaningAllNotify();
	}

	@Override
	public String updateCleaningNotify(String userId) throws Exception {
		return getServiceProvider().updateCleaningNotify(userId);
	}

	@Override
	public String cleaningBytool(JSONArray data) throws Exception {
		return getServiceProvider().cleaningBytool(data);
	}
	

}
