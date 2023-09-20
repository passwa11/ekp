package com.landray.kmss.third.im.kk.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.cogroup.interfaces.AbstractCogroupConfigService;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * <P>获取kk群协作所需参数</P>
 * @author 孙佳
 */
public class KKCogroupServiceImp extends AbstractCogroupConfigService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KKCogroupServiceImp.class);

	private IKkImConfigService kkImConfigService;

	private IKkImConfigService getKkImConfigService() {
		if (kkImConfigService == null) {
			kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
		}
		return kkImConfigService;
	}

	@Override
    public String getValuebyKey(String key) {
		return getKkImConfigService().getValuebyKey(key);
	}
}
