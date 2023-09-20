package com.landray.kmss.eop.basedata.service;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.model.EopBasedataPayment;

import net.sf.json.JSONObject;

public interface IEopBasedataPaymentDataService{
	/**
	 * 初始化付款单
	 * @param request
	 * @param payment
	 * @param fdModelId
	 * @throws Exception
	 */
	public void initPaymentData(HttpServletRequest request,EopBasedataPayment payment,String fdModelId) throws Exception;
	
	/**
	 * 校验付款单金额与源单据是否匹配
	 * @param fdModelId 源单据ID
	 * @param info  付款单据信息
	 * @return  校验结果，json格式:{result:[success/failure],message:提示信息}
	 * @throws Exception
	 */
	public String checkMoney(String fdModelId,JSONObject info) throws Exception;

	/**
	 * 批量确认付款
	 * @param ids
	 * @param fdModelName
	 * @throws Exception
	 */
	public JSONObject updatePyament(String ids,String type) throws Exception;
	
}
