package com.landray.kmss.fssc.eas.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONArray;

public interface IFsscEasParamService extends IExtendDataService {

    public void updateParam(IExtendForm form, RequestContext requestContext) throws Exception;
    
    public JSONArray getBaseCostItem() throws Exception;

}
