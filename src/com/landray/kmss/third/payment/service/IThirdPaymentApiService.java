package com.landray.kmss.third.payment.service;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.payment.interfaces.ThirdPaymentWayVo;
import com.landray.kmss.third.payment.model.ThirdPaymentOrder;
import com.landray.kmss.third.payment.model.ThirdPaymentUnifiedOrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IThirdPaymentApiService {

	/**
	 * 支付下单
	 *
	 * @param modelName  主文档类名，不能为空
	 * @param modelId    主文档ID，不能为空
	 * @param fdKey      业务标识，可为空
	 * @param paymentWay   支付方式，不能为空，比如wxworkpay
	 * @param desc       支付服务，参考ThirdPaymentConstant
	 * @param desc       付款描述，不能为空
	 * @param money      金额，以分为单位
	 * @param toUser     付款人，可以为空
	 * @param merchantId 微信商户ID，可为空
	 *                   <p>
	 *                   return 订单单号或者支付二维码地址
	 */
	JSONObject unifiedorder(String modelName, String modelId, String fdKey, String paymentWay, Integer paymentService, String desc, Double money, SysOrgPerson toUser, String merchantId, String tradeType) throws Exception;

	JSONObject unifiedorder(ThirdPaymentUnifiedOrderVo vo) throws Exception;
	/**
	 * 查询订单信息
	 * @param modelName 主文档类名
	 * @param modelId 主文档ID
	 * @param fdKey 业务标识，可为空
	 * @return
	 * @throws Exception
	 */
	JSONObject orderQuery(String modelName, String modelId, String fdKey) throws Exception;

	/**
	 * 关闭订单
	 * @param modelName 主文档类名
	 * @param modelId 主文档ID
	 * @param fdKey 业务标识，可为空
	 * @return
	 * @throws Exception
	 */
	JSONObject closeOrder(String modelName,String modelId,String fdKey) throws Exception;


	/**
	 * 更新订单信息
	 * @param modelName 主文档类名
	 * @param modelId 主文档ID
	 * @param fdKey 业务标识，可为空
	 * @return
	 * @throws Exception
	 */
	JSONObject updateOrder(String modelName,String modelId,String fdKey) throws Exception;

	Map<String,String> getEnabledPaymentWays() throws Exception;

	boolean isPayEnabled(String key) throws Exception;

	List<ThirdPaymentWayVo> getPaymentWays(HttpServletRequest request) throws Exception;

	ThirdPaymentOrder getOrder(String modelName, String modelId, String fdKey) throws Exception;

}