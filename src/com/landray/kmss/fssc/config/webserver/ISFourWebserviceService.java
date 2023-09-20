package com.landray.kmss.fssc.config.webserver;

import javax.jws.WebMethod;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

public interface ISFourWebserviceService extends ISysWebservice{
	
	/**
	 * S04流程接口（资产领用）
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String getData(String startDate,String endDate,String dataType) throws Exception;

}

