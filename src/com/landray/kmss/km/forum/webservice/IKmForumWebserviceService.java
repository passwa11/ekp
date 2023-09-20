package com.landray.kmss.km.forum.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

/**
 * WebService推送话题
 * 
 * @author 潘永辉 2017-2-9
 * 
 */
@WebService
public interface IKmForumWebserviceService extends ISysWebservice {
	/**
	 * 增加话题
	 * 
	 * @param webForm
	 * @return
	 * @throws Exception
	 */
	public String addTopic(KmForumPostParamterForm webForm) throws Exception;
}
