package com.landray.kmss.sys.mportal.service.spring;

import net.sf.json.JSONArray;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.mportal.forms.SysMportalTopmenuAllForm;
/**
 * 
 * @author 
 * @version 1.0 2015-11-13
 */
public interface ISysMportalTopmenuService extends IBaseService {
	
	public SysMportalTopmenuAllForm getAllMenuForm() throws Exception;
	
	
	public void updateAll(SysMportalTopmenuAllForm form) throws Exception;
	
	public JSONArray toItemData(RequestContext request) throws Exception;
}
