package com.landray.kmss.km.review.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

@WebService
public interface IKmReviewWebserviceService extends ISysWebservice {

	/**
	 * 启动流程
	 * @throws Exception 
	 */
	public String addReview(KmReviewParamterForm webParamForm) throws Exception;
	
	/**
	 * 审批流程（包括通过，驳回等等操作）
	 * @throws Exception 
	 */
	public String approveProcess(KmReviewParamterForm webParamForm) throws Exception;
	
	/**
	 * 修改表单数据
	 * @throws Exception 
	 */
	public String updateReviewInfo(KmReviewParamterForm webParamForm) throws Exception;
}
