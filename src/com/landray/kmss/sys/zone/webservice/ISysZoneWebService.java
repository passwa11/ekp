package com.landray.kmss.sys.zone.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
import com.landray.kmss.sys.zone.webservice.exception.SysZoneFaultException;
@WebService
public interface ISysZoneWebService extends ISysWebservice {
	/**
	 * 更换用户头像
	 * @param userId
	 * @param imagebyte
	 * @throws KmsFaultException
	 */
	public void updateUserImage(String userId,AttachImage imagebyte) throws SysZoneFaultException;
}
