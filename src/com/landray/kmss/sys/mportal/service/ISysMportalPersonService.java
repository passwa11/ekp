package com.landray.kmss.sys.mportal.service;

import net.sf.json.JSONObject;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.mportal.forms.SysMportalPersonForm;

public interface ISysMportalPersonService extends IBaseService {

	SysMportalPersonForm findByPersonId(String id, RequestContext request) throws Exception;

	JSONObject findById(String id, RequestContext request) throws Exception;

}
