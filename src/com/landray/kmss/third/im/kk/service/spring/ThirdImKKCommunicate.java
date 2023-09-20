package com.landray.kmss.third.im.kk.service.spring;

import com.landray.kmss.sys.zone.interfaces.ICommunicate;
import com.landray.kmss.third.im.kk.constant.KeyConstants;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ThirdImKKCommunicate implements ICommunicate {

	private IKkImConfigService kkImConfigService;

	private IKkImConfigService getKkImConfigService() {
		if (kkImConfigService == null) {
			kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	@Override
	public boolean isEnable() {
		String status = getKkImConfigService().getValuebyKey(KeyConstants.KK_CONFIG_SATUS);
		if (StringUtil.isNull(status)) {
			return false;
		}
		return new Boolean(status);
	}

}
