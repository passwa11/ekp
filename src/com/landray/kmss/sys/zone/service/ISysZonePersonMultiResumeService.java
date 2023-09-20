package com.landray.kmss.sys.zone.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;

public interface ISysZonePersonMultiResumeService  extends IBaseService {
	
	/**
	 * 
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public String  addResumes (IExtendForm form, RequestContext requestContext) throws Exception;
}
