package com.landray.kmss.tic.soap.connector.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.tic.soap.connector.impl.TicSoapProjectFactory;

public class TicSoapInitializeBean implements InitializingBean{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TicSoapInitializeBean.class);
	
	@Override
    public void afterPropertiesSet() throws Exception {
		logger.info("SOAPUI初始化工作,创建比较耗时的操作~");
		TimeCounter.logCurrentTime("TicSoapInitializeBean-init", true, this.getClass());
		TicSoapProjectFactory.getWsdlProjectInstance();
		TimeCounter.logCurrentTime("TicSoapInitializeBean-init", false, this.getClass());
		logger.info("SOAPUI初始化工作完成~");
	}

}
