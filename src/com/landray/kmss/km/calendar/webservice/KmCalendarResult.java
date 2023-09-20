package com.landray.kmss.km.calendar.webservice;
/**
 * 结果返回值
 */
public class KmCalendarResult {
	/**
	 * 操作结果状态
	 * 		说明：
	 * 			0.失败:RETURN_CONSTANT_STATUS_FAIL
	* 			1.成功:RETURN_CONSTANT_STATUS_SUCESS
	 */
	private int returnState=KmCalendarWebServiceConstant.RETURN_CONSTANT_STATUS_SUCESS;
	
	/**
	 * 返回的相关信息
	 * 		说明：
	 * 			1.返回值为0，message为出错信息
	 * 			2.返回值为1，message为日程/笔记列表信息,列表信息为JSON格式，如下：
	 * 			{
	 * 				"datas":
	 * 				[
	 * 					{
	 * 						"fdId":"ABC"     //日程ID
	 * 						"docStartTime":2014-2-11 14:00:00,   //日程开始时间
	 * 						"docFinishTime":2014-2-11 15:00:00,  //日程结束时间
	 * 						"docSubject":"2月11号下午开例会",    //标题
	 *                      "docContent":"YY" ,            //内容
	 * 						"person":{"loginName":"admin"},       //人员
	 * 						"appKey":"com.landray.kmss.km.calendar.model.KmCalendarMain",  //日程来源
	 * 					},
	 * 					………………
	 * 				]
	 * 			}
	 */
	private String message;

	public int getReturnState() {
		return returnState;
	}

	public void setReturnState(int returnState) {
		this.returnState = returnState;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
