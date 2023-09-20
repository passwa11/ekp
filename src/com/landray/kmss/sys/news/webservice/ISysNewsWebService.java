package com.landray.kmss.sys.news.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
@WebService
public interface ISysNewsWebService extends ISysWebservice{

	public String addNews(SysNewsParamterForm form) throws Exception;
}
