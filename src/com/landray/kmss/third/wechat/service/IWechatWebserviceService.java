package com.landray.kmss.third.wechat.service;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
import com.landray.kmss.third.wechat.forms.SearchParamForm;
import com.landray.kmss.third.wechat.forms.WeChatConfigParamterForm;
import com.landray.kmss.third.wechat.forms.WeChatNotifyForm;
import com.landray.kmss.third.wechat.forms.WeChatNotifyParamterForm;
import com.landray.kmss.third.wechat.forms.WeChatParamterForm;

@WebService
public interface IWechatWebserviceService extends ISysWebservice {

	public String addForum(WeChatParamterForm webForm) throws Exception;
	
	public String findPhone(WeChatParamterForm webForm) throws Exception;
   
	public String findSysNotifyTodo(WeChatNotifyParamterForm webForm) throws Exception;
	
	public String findSysNewsTodo(WeChatNotifyParamterForm webForm) throws Exception;
	
	public String findSysKnowledgeTodo(WeChatNotifyParamterForm webForm) throws Exception;
	
	public String search4Wechat(SearchParamForm searchParamForm )throws Exception ;
	
	public String receiveMessage(WeChatNotifyForm weChatNotifyForm)throws Exception ;
	
	public String addWechatConfig(String param)throws Exception;
	
	public String myReviewList(WeChatNotifyParamterForm webForm)throws Exception;
	
	public String insertWeChatConfig(WeChatConfigParamterForm webForm)throws Exception;
	
	public String insertWeChatConfigByWeiYun(WeChatConfigParamterForm webForm)throws Exception;
	
	/**
	 * 获取附件流
	 * @param fdFileId	附件的fdFileId
	 * @return
	 * @throws Exception
	 */
	public Object getAttachement(String fdFileId)throws Exception;
}
