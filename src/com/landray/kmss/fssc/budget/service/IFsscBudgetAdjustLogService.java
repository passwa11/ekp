package com.landray.kmss.fssc.budget.service;

import java.util.List;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustLog;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

import net.sf.json.JSONObject;

public interface IFsscBudgetAdjustLogService extends IExtendDataService {

    public abstract List<FsscBudgetAdjustLog> findByFdCompany(EopBasedataCompany fdCompany) throws Exception;

	public abstract void addAdjust(JSONObject logJson) throws Exception;
}
