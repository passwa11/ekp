package com.landray.kmss.fssc.eas.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscEasSwitchService extends IExtendDataService {

    public void updateSwitch(IExtendForm form, RequestContext requestContext) throws Exception;

}
