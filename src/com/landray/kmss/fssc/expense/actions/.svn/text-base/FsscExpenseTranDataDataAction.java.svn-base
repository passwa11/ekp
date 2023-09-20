package com.landray.kmss.fssc.expense.actions;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTranDataService;

import javax.servlet.http.HttpServletRequest;

public class FsscExpenseTranDataDataAction extends BaseAction {

    private IFsscExpenseTranDataService fsscExpenseTranDataService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscExpenseTranDataService == null) {
            fsscExpenseTranDataService = (IFsscExpenseTranDataService) getBean("fsscExpenseTranDataService");
        }
        return fsscExpenseTranDataService;
    }
}
