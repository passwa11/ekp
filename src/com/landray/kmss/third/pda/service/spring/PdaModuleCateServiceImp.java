package com.landray.kmss.third.pda.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleCate;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.third.pda.model.PdaModuleLabelView;
import com.landray.kmss.third.pda.service.IPdaModuleCateService;
import com.landray.kmss.util.UserUtil;

/**
 * 模块配置分类业务接口实现
 * 
 * @author zhuhq
 * @version 1.0 2014-04-25
 */
public class PdaModuleCateServiceImp extends BaseServiceImp implements IPdaModuleCateService {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		PdaModuleCate mainModel = (PdaModuleCate) modelObj;
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setDocCreateTime(new Date());
		return super.add(mainModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		PdaModuleCate mainModel = (PdaModuleCate) modelObj;
		mainModel.setDocAlteror(UserUtil.getUser());
		mainModel.setDocAlterTime(new Date());
		super.update(mainModel);
	}

}
