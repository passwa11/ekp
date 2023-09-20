package com.landray.kmss.fssc.fee.actions;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.fee.model.FsscFeeMobileConfig;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.fssc.fee.service.IFsscFeeMobileConfigService;

public class FsscFeeMobileConfigDataAction extends BaseAction {

    private IFsscFeeMobileConfigService fsscFeeMobileConfigService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscFeeMobileConfigService == null) {
            fsscFeeMobileConfigService = (IFsscFeeMobileConfigService) getBean("fsscFeeMobileConfigService");
        }
        return fsscFeeMobileConfigService;
    }
}
