package com.landray.kmss.sys.attachment.integrate.wps.service;

import com.landray.kmss.sys.attachment.integrate.wps.model.ThirdWpsOfficeToken;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;


public interface IThirdWpsOfficeTokenService extends IExtendDataService {
	
	void addData(ThirdWpsOfficeToken thirdWpsOfficeToken);
}
