package com.landray.kmss.fssc.budget.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.fssc.budget.service.IFsscBudgetMainService;
import com.landray.kmss.util.DateUtil;

public class FsscBudgetDataQuartzJobService extends BaseServiceImp {
	
	IFsscBudgetMainService fsscBudgetMainService;

	public void setFsscBudgetMainService(IFsscBudgetMainService fsscBudgetMainService) {
		this.fsscBudgetMainService = fsscBudgetMainService;
	}


	public void createBudgetData() throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		StringBuilder whereBlock=new StringBuilder();
		whereBlock.append("fsscBudgetMain.fdEnableDate=:fdEnableDate ");
		whereBlock.append(" and substring(cast(fsscBudgetMain.fdEnableDate as string),1,10)!=substring(cast(fsscBudgetMain.docCreateTime as string),1,10)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdEnableDate", DateUtil.convertStringToDate(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE), DateUtil.PATTERN_DATE) );
		List<FsscBudgetMain> mainList=fsscBudgetMainService.findList(hqlInfo);
		for (FsscBudgetMain main : mainList) {
			fsscBudgetMainService.addBudgetData(main);
		}
	}
}
