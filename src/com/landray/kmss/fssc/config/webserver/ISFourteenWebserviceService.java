package com.landray.kmss.fssc.config.webserver;

import javax.jws.WebMethod;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

public interface ISFourteenWebserviceService extends ISysWebservice{
	
	/**
	 * S14流程接口
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String getData(String startDate,String endDate) throws Exception;

}

