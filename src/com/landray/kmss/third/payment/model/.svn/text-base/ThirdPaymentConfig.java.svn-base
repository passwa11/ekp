package com.landray.kmss.third.payment.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import org.slf4j.Logger;


public class ThirdPaymentConfig extends BaseAppConfig {

	public ThirdPaymentConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ThirdPaymentConfig.class);

	public static ThirdPaymentConfig newInstance() {
		ThirdPaymentConfig config = null;
		try {
			config = new ThirdPaymentConfig();
		} catch (Exception e) {
			logger.error("", e);
		}
		return config;
	}

	@Override
	public String getJSPUrl() {
		// TODO Auto-generated method stub
		return "/third/payment/payment_config.jsp";
	}

	public boolean isPaymentServiceEnable(Integer paymentService){
		String value = getValue("paymentService"+paymentService);
		if("true".equals(value)){
			return true;
		}
		return false;
	}

}
