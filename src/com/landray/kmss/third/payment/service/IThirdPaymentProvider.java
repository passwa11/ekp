package com.landray.kmss.third.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IThirdPaymentProvider {

	/**
	 * 是否启用支付功能
	 * @return
	 * @throws Exception
	 */
	boolean isPayEnable()
			throws Exception;

	/**
	 * 是否支持对应的支付服务
	 * @param payService 支付服务编码，参考ThirdPaymentConstant
	 * @return
	 */
	boolean isSupport(int payService);

	JSONObject unifiedorder(String modelName, String modelId, String fdKey, String desc, Double money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception;

	JSONObject queryOrder(String modelName, String modelId, String fdKey) throws Exception;

	JSONObject updateOrder(String modelName, String modelId, String fdKey) throws Exception;

	JSONObject closeOrder(String modelName, String modelId, String fdKey) throws Exception;

	boolean isModuleExist() throws Exception;

	String getConfigUrl();
}
