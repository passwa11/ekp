package com.landray.kmss.third.payment.service.spring;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.third.payment.interfaces.ThirdPaymentWayVo;
import com.landray.kmss.third.payment.model.ThirdPaymentCallLog;
import com.landray.kmss.third.payment.model.ThirdPaymentConfig;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.third.payment.model.ThirdPaymentUnifiedOrderVo;
import com.landray.kmss.third.payment.plugin.ThirdPaymentPlugin;
import com.landray.kmss.third.payment.plugin.ThirdPaymentPluginData;
import com.landray.kmss.third.payment.service.*;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


public class ThirdPaymentApiServiceImp  implements IThirdPaymentApiService {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(ThirdPaymentApiServiceImp.class);

	private ISysOrgPersonService sysOrgPersonService;

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService){
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IThirdPaymentProvider getPayProvider(String providerKey) throws Exception {
		IThirdPaymentProvider provider = getProvider(providerKey);
		if(provider==null){
			throw new Exception("找不到 "+providerKey+" 对应的扩展实现");
		}
		boolean iePayEnable = provider.isPayEnable();
		if(iePayEnable==false){
			throw new Exception(providerKey+" 未启用支付功能");
		}
		return provider;
	}

	@Override
	public JSONObject unifiedorder(ThirdPaymentUnifiedOrderVo vo) throws Exception {
		if(com.landray.kmss.util.StringUtil.isNull(vo.getModelName())|| com.landray.kmss.util.StringUtil.isNull(vo.getModelId()) || StringUtil.isNull(vo.getDesc()) || vo.getMoney()==null){
			throw new Exception("modelName、modelId、desc、money 都不能为空");
		}
		ThirdPaymentConfig config = new ThirdPaymentConfig();
		Integer paymentService = vo.getPaymentService();
		if(paymentService==null){
			paymentService = 1;
		}
		JSONObject returnObj = new JSONObject();
		if(!config.isPaymentServiceEnable(paymentService)){
			returnObj.put("errcode",103);
			returnObj.put("errmsg","服务 "+getPaymentServiceName(paymentService)+" 未启用");
			return returnObj;
		}
		if(vo.getDesc().length()>128){
			//throw new Exception("付款描述的长度不能大于128");
			returnObj.put("errcode",101);
			returnObj.put("errmsg","付款描述的长度不能大于128");
			return returnObj;
		}
		IThirdPaymentProvider provider = getPayProvider(vo.getPaymentWay());
		ThirdPaymentOrder order = thirdPaymentOrderService.findOrder(vo.getModelName(),vo.getModelId(),vo.getFdKey());
		if(order!=null){
			//throw new Exception("已存在该文档的订单，不能重复下单");
			returnObj.put("errcode",102);
			returnObj.put("errmsg","已存在该文档的订单，不能重复下单");
			return returnObj;
		}
		String errorMsg = null;
		JSONObject result = null;
		try {
			result = provider.unifiedorder(vo.getModelName(),vo.getModelId(),vo.getFdKey(),vo.getDesc(),vo.getMoney(),vo.getToUser(), vo.getMerchantId(), vo.getTradeType());
			addThirdPaymentOrder(vo,result);
			returnObj.put("data",result);
			returnObj.put("errcode",0);
			return returnObj;
		}catch (Exception e){
			errorMsg = e.getMessage();
			throw e;
		}finally {
			addThirdPaymentCallLog(vo,"unifiedorder",errorMsg,result);
		}
	}

	@Override
	public JSONObject unifiedorder(String modelName, String modelId, String fdKey, String paymentWay, Integer paymentService, String desc, Double money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception {
		ThirdPaymentUnifiedOrderVo vo = new ThirdPaymentUnifiedOrderVo(modelName,modelId,fdKey,desc,paymentWay,paymentService,money,toUser,merchantId);
		if(StringUtil.isNotNull(tradeType)){
			vo.setTradeType(tradeType);
		}
		return unifiedorder(vo);
	}

	@Override
	public JSONObject orderQuery(String modelName, String modelId, String fdKey) throws Exception {
		ThirdPaymentOrder order = thirdPaymentOrderService.findOrder(modelName,modelId,fdKey);
		if(order==null){
			throw new Exception("找不到对应的订单");
		}
		IThirdPaymentProvider provider = getPayProvider(order.getFdPayType());
		return provider.queryOrder(modelName,modelId,fdKey);
	}

	@Override
	public JSONObject closeOrder(String modelName, String modelId, String fdKey) throws Exception {
		ThirdPaymentOrder order = thirdPaymentOrderService.findOrder(modelName,modelId,fdKey);
		if(order==null){
			throw new Exception("找不到对应的订单");
		}
		IThirdPaymentProvider provider = getPayProvider(order.getFdPayType());
		return provider.closeOrder(modelName,modelId,fdKey);
	}

	@Override
	public JSONObject updateOrder(String modelName, String modelId, String fdKey) throws Exception {
		ThirdPaymentOrder order = thirdPaymentOrderService.findOrder(modelName,modelId,fdKey);
		if(order==null){
			throw new Exception("找不到对应的订单");
		}
		IThirdPaymentProvider provider = getPayProvider(order.getFdPayType());
		JSONObject result = provider.updateOrder(modelName,modelId,fdKey);
		JSONObject orderData = result.getJSONObject("data");
		order.setFdPaymentStatus(orderData.getString("payment_status"));
		Long pay_time = orderData.getLong("pay_time");
		if(pay_time!=null){
			order.setFdPayTime(new Date(pay_time));
		}
		order.setFdPaymentStatusDesc(orderData.getString("payment_status_desc"));
		if(StringUtil.isNotNull(orderData.getString("ekpId"))){
			order.setFdPayer((SysOrgPerson)sysOrgPersonService.findByPrimaryKey(orderData.getString("ekpId")));
		}
		thirdPaymentOrderService.update(order);
		return result;
	}

	@Override
    public Map<String,String> getEnabledPaymentWays() throws Exception {
		Map<String,String> enabledPaymentWays = new HashMap<>();
		List<ThirdPaymentPluginData> pluginDatas = ThirdPaymentPlugin.getExtensionList();
		for(ThirdPaymentPluginData pluginData:pluginDatas){
			IThirdPaymentProvider provider = pluginData.getPaymentProvider();
			if(!provider.isPayEnable()){
				continue;
			}
			enabledPaymentWays.put(pluginData.getKey(), ResourceUtil.getString(pluginData.getName()));
		}
		return enabledPaymentWays;
	}

	@Override
    public boolean isPayEnabled(String key) throws Exception {
		ThirdPaymentPluginData pluginData = ThirdPaymentPlugin.getPluginData(key);
		if(pluginData==null){
			logger.warn("找不到扩展："+key);
			return false;
		}
		IThirdPaymentProvider provider = pluginData.getPaymentProvider();
		return provider.isPayEnable();
	}

	private IThirdPaymentProvider getProvider(String key) {
		ThirdPaymentPluginData data = ThirdPaymentPlugin.getPluginData(key);
		if(data==null){
			return null;
		}
		return data.getPaymentProvider();
	}


	private IThirdPaymentCallLogService thirdPaymentCallLogService;

	public void setThirdPaymentCallLogService(IThirdPaymentCallLogService thirdPaymentCallLogService) {
		this.thirdPaymentCallLogService = thirdPaymentCallLogService;
	}

	public void setThirdPaymentOrderService(IThirdPaymentOrderService thirdPaymentOrderService) {
		this.thirdPaymentOrderService = thirdPaymentOrderService;
	}

	public void setThirdPaymentMerchantService(IThirdPaymentMerchantService thirdPaymentMerchantService) {
		this.thirdPaymentMerchantService = thirdPaymentMerchantService;
	}

	private IThirdPaymentOrderService thirdPaymentOrderService = null;

	private IThirdPaymentMerchantService thirdPaymentMerchantService = null;

	private String addThirdPaymentOrder(ThirdPaymentUnifiedOrderVo vo, JSONObject result) throws Exception {
		ThirdPaymentOrder order = new ThirdPaymentOrder();
		order.setDocAlterTime(new Date());
		order.setDocCreateTime(new Date());
		order.setDocCreator(UserUtil.getUser());
		order.setFdKey(vo.getFdKey());
		order.setFdModelId(vo.getModelId());
		order.setFdModelName(vo.getModelName());
		order.setFdOrderDesc(vo.getDesc());
		order.setFdTotalMoney(vo.getMoney());
		order.setFdRelateOrderId(result.getString("orderId"));
		order.setFdOrderNo(result.getString("orderNo"));
		order.setFdPaymentStatus("NOTPAY");
		order.setFdPaymentService(vo.getPaymentService());
		order.setFdPayType(vo.getPaymentWay());
		order.setFdCodeUrl(result.getString("codeUrl"));
		return thirdPaymentOrderService.add(order);
	}

	private void addThirdPaymentCallLog(ThirdPaymentUnifiedOrderVo vo, String callMethod, String errorMsg, JSONObject result){
		try {
			ThirdPaymentCallLog callLog = new ThirdPaymentCallLog();
			callLog.setDocCreateTime(new Date());
			callLog.setDocCreator(UserUtil.getUser());
			callLog.setFdKey(vo.getFdKey());
			callLog.setFdOrderDesc(vo.getDesc());
			callLog.setFdModelId(vo.getModelId());
			callLog.setFdModelName(vo.getModelName());
			callLog.setFdTotalMoney(vo.getMoney());
			callLog.setFdOrderNo(result==null?null:result.getString("orderId"));
			callLog.setFdPayType(vo.getPaymentWay());
			callLog.setFdPaymentService(vo.getPaymentService());
			callLog.setFdCallMethod(callMethod);
			callLog.setFdErrorMsg(errorMsg);
			callLog.setFdReqData(vo.toString());
			callLog.setFdResData(result==null?null:result.toString());
			if(errorMsg!=null){
				callLog.setFdCallResult(2);
			}else{
				callLog.setFdCallResult(1);
			}
			thirdPaymentCallLogService.add(callLog);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
    public List<ThirdPaymentWayVo> getPaymentWays(HttpServletRequest request) throws Exception {
		List<ThirdPaymentWayVo> ways = new ArrayList<>();
		List<ThirdPaymentPluginData> pluginDatas = ThirdPaymentPlugin.getExtensionList();
		for(ThirdPaymentPluginData pluginData:pluginDatas){
			IThirdPaymentProvider provider = pluginData.getPaymentProvider();
			if(!provider.isModuleExist()){
				continue;
			}
			ways.add(new ThirdPaymentWayVo(pluginData.getKey(),ResourceUtil.getString(pluginData.getName()),provider.isPayEnable(),request.getContextPath()+provider.getConfigUrl()));
		}
		return ways;
	}

	private String getPaymentServiceName(Integer paymentService){
		return ResourceUtil.getString("enums.service."+paymentService);
	}

	@Override
	public ThirdPaymentOrder getOrder(String modelName, String modelId, String fdKey) throws Exception {
		ThirdPaymentOrder order = thirdPaymentOrderService.findOrder(modelName,modelId,fdKey);
		if(order==null){
			throw new Exception("找不到对应的订单");
		}
		return order;
	}
}
