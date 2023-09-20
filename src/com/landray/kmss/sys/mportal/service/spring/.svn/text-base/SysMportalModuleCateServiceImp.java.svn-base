package com.landray.kmss.sys.mportal.service.spring;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.mportal.model.SysMportalModuleCate;
import com.landray.kmss.sys.mportal.service.ISysMportalModuleCateService;
import com.landray.kmss.util.UserUtil;

public class SysMportalModuleCateServiceImp extends BaseServiceImp
		implements ISysMportalModuleCateService {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysMportalModuleCate mainModel = (SysMportalModuleCate) modelObj;
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setDocCreateTime(new Date());
		return super.add(mainModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysMportalModuleCate mainModel = (SysMportalModuleCate) modelObj;
		mainModel.setDocAlteror(UserUtil.getUser());
		mainModel.setDocAlterTime(new Date());
		super.update(mainModel);
	}

}
