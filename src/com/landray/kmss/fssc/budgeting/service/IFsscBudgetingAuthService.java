package com.landray.kmss.fssc.budgeting.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingAuthForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscBudgetingAuthService extends IExtendDataService {

    public abstract List<FsscBudgetingAuth> findByFdCostCenterList(EopBasedataCostCenter fdCostCenterList) throws Exception;

    public abstract List<FsscBudgetingAuth> findByFdBudgetItemList(EopBasedataBudgetItem fdBudgetItemList) throws Exception;

    public abstract List<FsscBudgetingAuth> findByFdProjectList(EopBasedataProject fdProjectList) throws Exception;

	public abstract void saveImport(FsscBudgetingAuthForm mainForm,
			HttpServletRequest request)  throws Exception;
}
