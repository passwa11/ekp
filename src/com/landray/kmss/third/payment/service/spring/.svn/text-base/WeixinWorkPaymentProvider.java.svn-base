package com.landray.kmss.third.payment.service.spring;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.payment.service.IThirdPaymentProvider;
import com.landray.kmss.third.payment.util.ThirdPaymentUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class WeixinWorkPaymentProvider implements IThirdPaymentProvider {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(WeixinWorkPaymentProvider.class);

	public static Map<String, String> getConfig() throws Exception {
		Class<?> cls;
		try {
			cls = Class.forName("com.landray.kmss.third.weixin.work.model.WeixinWorkConfig");
			BaseAppConfig config = (BaseAppConfig) cls.newInstance();
			return config.getDataMap();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	private String getAccessToken() throws Exception {
		Class<?> cls;
		try {
			cls = Class.forName("com.landray.kmss.third.ding.util.DingUtils");
			Method method = cls.getMethod("getDingApiService");
			Object dingApiService = method.invoke(null);

			method = dingApiService.getClass().getMethod("getAccessToken");
			String token = (String) method.invoke(dingApiService);
			logger.info("token:" + token);
			return token;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}


	private static final Integer[] SUPPORTED_TYPES = { 1 };

	@Override
	public boolean isPayEnable() throws Exception {
		boolean moduleExist = ThirdPaymentUtil.moduleExist("/third/weixin");
		if(moduleExist==false){
			return false;
		}
		Map<String,String> configMap = getConfig();
		String wxEnabled = configMap.get("wxEnabled");
		String wxPayEnable = configMap.get("wxPayEnable");
		if("true".equals(wxEnabled) && "true".equals(wxPayEnable)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isSupport(int payService) {
		if (Arrays.asList(SUPPORTED_TYPES).contains(payService)) {
			return true;
		}
		return false;
	}

	@Override
	public JSONObject unifiedorder(String modelName, String modelId, String fdKey, String desc, Double money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception {
		IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil");
		if (staticProxy != null) {
			return (JSONObject)staticProxy.invoke("unifiedorder", modelName,modelId,fdKey,desc,money.intValue(),toUser,merchantId,tradeType);
		}else{
			throw new Exception("找不到类‘com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil’");
		}

	}

	@Override
	public JSONObject queryOrder(String modelName, String modelId, String fdKey) throws Exception {
		IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil");
		if (staticProxy != null) {
			Map resultMap = (Map)staticProxy.invoke("orderQuery", modelName,modelId,fdKey);
			if(resultMap==null){
				throw new Exception("获取订单信息失败");
			}
			JSONObject data = new JSONObject(resultMap);
			data.put("payment_status",data.getString("trade_state"));
			data.put("pay_time",getPayTime(data.getString("time_end")));
			data.put("payment_status_desc",data.getString("trade_state_desc"));
			JSONObject result = new JSONObject();
			result.put("data",data);
			return result;
		}else{
			throw new Exception("找不到类‘com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil’");
		}
	}

	@Override
	public JSONObject updateOrder(String modelName, String modelId, String fdKey) throws Exception {
		IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil");
		if (staticProxy != null) {
			Map resultMap = (Map)staticProxy.invoke("updateOrderPayData", modelName,modelId,fdKey);
			if(resultMap==null){
				throw new Exception("更新订单信息失败");
			}
			JSONObject data = new JSONObject(resultMap);
			data.put("payment_status",data.getString("trade_state"));
			data.put("pay_time",getPayTime(data.getString("time_end")));
			data.put("payment_status_desc",data.getString("trade_state_desc"));
			JSONObject result = new JSONObject();
			result.put("data",data);
			return result;
		}else{
			throw new Exception("找不到类‘com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil’");
		}
	}

	@Override
	public JSONObject closeOrder(String modelName, String modelId, String fdKey) throws Exception {
		IDynamicProxy staticProxy = ModuleCenter.getStaticProxy("com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil");
		if (staticProxy != null) {
			Map resultMap = (Map)staticProxy.invoke("closeOrder", modelName,modelId,fdKey);
			if(resultMap==null){
				return null;
			}
			return new JSONObject(resultMap);
		}else{
			throw new Exception("找不到类‘com.landray.kmss.third.weixin.work.util.ThirdWxPayUtil’");
		}
	}

	private Long getPayTime(String time_end){
		if(StringUtil.isNull(time_end)){
			return null;
		}
		Date date = DateUtil.convertStringToDate(time_end,"yyyyMMddHHmmss");
		return date.getTime();
	}

	@Override
	public boolean isModuleExist() throws Exception {
		boolean moduleExist = ThirdPaymentUtil.moduleExist("/third/weixin");
		return moduleExist;
	}

	@Override
	public String getConfigUrl() {
		return "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.weixin.work.model.WeixinWorkConfig";
	}

}
