package com.landray.kmss.sys.news.service.spring;


import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.news.service.ISysNewsOutSignService;

public class SysNewsOutSignServiceImp extends BaseServiceImp implements ISysNewsOutSignService{

	@Override
	public IExtendForm initFormSetting(IExtendForm form,
			RequestContext requestContext) throws Exception {
		return null;
	}

	@Override
	public IBaseModel initModelSetting(RequestContext requestContext)
			throws Exception {
		return null;
	}
}
