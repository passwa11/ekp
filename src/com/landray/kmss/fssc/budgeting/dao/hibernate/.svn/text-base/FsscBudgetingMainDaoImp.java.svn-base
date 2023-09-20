package com.landray.kmss.fssc.budgeting.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.fssc.budgeting.constant.FsscBudgetingConstant;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingMainDao;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class FsscBudgetingMainDaoImp extends BaseDaoImp implements IFsscBudgetingMainDao {
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingMain fsscBudgetingMain = (FsscBudgetingMain) modelObj;
        if (fsscBudgetingMain.getDocCreator() == null) {
            fsscBudgetingMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingMain.getDocCreateTime() == null) {
            fsscBudgetingMain.setDocCreateTime(new Date());
        }
        if (fsscBudgetingMain.getDocSubject() == null) {
        	fsscBudgetingMain.setDocSubject(fsscBudgetingMain.getFdYear()+fsscBudgetingMain.getFdOrgName()
        		+ResourceUtil.getString("module.fssc.budgeting", "fssc-budgeting"));
        }
        String docStatus=fsscBudgetingMain.getDocStatus();
        if(StringUtil.isNotNull(docStatus)){
        	if(SysDocConstant.DOC_STATUS_DRAFT.equals(docStatus)){
        		//预算编制，设置预算状态为草稿
        		fsscBudgetingMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_DRAFT);
        	}else if(SysDocConstant.DOC_STATUS_EXAMINE.equals(docStatus)){
        		//审核中
        		fsscBudgetingMain.setFdStatus(FsscBudgetingConstant.FD_STATUS_EXAMINE);
        	}
        }
        return super.add(fsscBudgetingMain);
    }
	
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		FsscBudgetingMain fsscBudgetingMain = (FsscBudgetingMain) modelObj;
		if (fsscBudgetingMain.getDocCreator() == null) {
			fsscBudgetingMain.setDocCreator(UserUtil.getUser());
		}
		if (fsscBudgetingMain.getDocCreateTime() == null) {
			fsscBudgetingMain.setDocCreateTime(new Date());
		}
		if (fsscBudgetingMain.getDocSubject() == null) {
			fsscBudgetingMain.setDocSubject(fsscBudgetingMain.getFdYear()+fsscBudgetingMain.getFdOrgName()
			+ResourceUtil.getString("module.fssc.budgeting", "fssc-budgeting"));
		}
	}
}
