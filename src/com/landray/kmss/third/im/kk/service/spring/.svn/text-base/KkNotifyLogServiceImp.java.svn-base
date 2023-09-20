package com.landray.kmss.third.im.kk.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.dao.IKkNotifyLogDao;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.third.im.kk.service.IKkNotifyLogService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * kk待办集成日志业务接口实现
 * 
 * @author
 * @version 1.0 2012-04-13
 */
public class KkNotifyLogServiceImp extends BaseServiceImp implements
		IKkNotifyLogService {

	private IKkImConfigService kkImConfigService;

	public IKkImConfigService getKkImNotifyService() {
		if (kkImConfigService == null) {
			kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	@Override
    public void backUp() throws Exception {
		((IKkNotifyLogDao) getBaseDao()).backUp();
	}

	@Override
    public void clean() throws Exception {
		String logDays = getKkImNotifyService().getValuebyKey(KeyConstants.EKP_LOGBAK_DAYS);
		((IKkNotifyLogDao) getBaseDao()).clean(logDays);
	}

}
