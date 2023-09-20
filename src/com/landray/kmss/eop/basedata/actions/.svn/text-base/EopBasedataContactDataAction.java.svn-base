package com.landray.kmss.eop.basedata.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.service.IEopBasedataContactService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangwh
 * @description:供应商联系人数据action
 * @date 2021/5/7
 */
public class EopBasedataContactDataAction extends BaseAction {

    private IEopBasedataContactService eopBasedataContactService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (eopBasedataContactService == null) {
            eopBasedataContactService = (IEopBasedataContactService) getBean("eopBasedataContactService");
        }
        return eopBasedataContactService;
    }
}
