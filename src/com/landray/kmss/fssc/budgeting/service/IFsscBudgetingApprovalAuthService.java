package com.landray.kmss.fssc.budgeting.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingApprovalAuthForm;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IFsscBudgetingApprovalAuthService extends IExtendDataService {

    public abstract List<FsscBudgetingApprovalAuth> findByFdCostCenterList(EopBasedataCostCenter fdCostCenterList) throws Exception;

    public abstract List<FsscBudgetingApprovalAuth> findByFdBudgetItemList(EopBasedataBudgetItem fdBudgetItemList) throws Exception;

    public abstract List<FsscBudgetingApprovalAuth> findByFdProjectList(EopBasedataProject fdProjectList) throws Exception;

	public abstract void saveImport(FsscBudgetingApprovalAuthForm mainForm,
			HttpServletRequest request)  throws Exception;
}
