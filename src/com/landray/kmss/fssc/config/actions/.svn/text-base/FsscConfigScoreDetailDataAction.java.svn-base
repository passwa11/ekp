package com.landray.kmss.fssc.config.actions;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreDetailService;

/**
  * 积分使用记录 数据源Action
  */
public class FsscConfigScoreDetailDataAction extends BaseAction {

    private IFsscConfigScoreDetailService fsscConfigScoreDetailService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscConfigScoreDetailService == null) {
            fsscConfigScoreDetailService = (IFsscConfigScoreDetailService) getBean("fsscConfigScoreDetailService");
        }
        return fsscConfigScoreDetailService;
    }
}
