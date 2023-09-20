package com.landray.kmss.fssc.budgeting.service.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.fssc.budgeting.service.IFsscBudgetingMainService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonBudgetingService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;

import net.sf.json.JSONObject;

public class FsscBudgetingServiceImp extends ExtendDataServiceImp implements IFsscCommonBudgetingService {
	
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(FsscBudgetingServiceImp.class);
	
	protected IFsscBudgetingMainService fsscBudgetingMainService;

	public void setFsscBudgetingMainService(IFsscBudgetingMainService fsscBudgetingMainService) {
		this.fsscBudgetingMainService = fsscBudgetingMainService;
	}

	/********************************************************
     * @param  将当前期间的预算编制置为废弃
     * return JSONObject result:success 成功，failure，失败，message：失败信息
     * ******************************************************/
	
	@Override
	public JSONObject updateBudgetingStatus() throws Exception {
		JSONObject jsonObject=new JSONObject();
		try {
			fsscBudgetingMainService.updateBudgetingStatus();
			jsonObject.put("result", "success");
		} catch (Exception e) {
			jsonObject.put("result", "failure");
			jsonObject.put("message", e);
		}
		return jsonObject;
	}
}
