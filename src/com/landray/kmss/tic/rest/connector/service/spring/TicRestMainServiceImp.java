package com.landray.kmss.tic.rest.connector.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.tic.rest.connector.forms.TicRestMainForm;
import com.landray.kmss.tic.rest.connector.model.TicRestMain;
import com.landray.kmss.tic.rest.connector.service.ITicRestMainService;
import com.landray.kmss.tic.rest.executor.IRestDataExecutor;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 *  Rest服务请尔方法配置数据访问
 */
public class TicRestMainServiceImp extends BaseCoreInnerServiceImp implements
		ITicRestMainService {

	@Override
    public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		TicRestMainForm ticRestMainForm = (TicRestMainForm) form;
		if (StringUtil.isNull(ticRestMainForm.getFdAppType())) {
			ticRestMainForm.setFdAppType("REST");
		}
		TicRestMain ticRestMain = (TicRestMain) convertFormToModel(
				ticRestMainForm, null, requestContext);
		add(ticRestMain);
		return null;
	}

	@Override
    public String doTest(String params, TicRestMain restMain) throws Exception {
		IRestDataExecutor rde = (IRestDataExecutor) SpringBeanUtil
				.getBean("restDispatcherExecutor");
		return rde.doTest(params, restMain);
	}

	@Override
    public boolean validateRestAuth(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"ticRestMain.ticRestAuth.fdId =:ticRestAuthId");
		hqlInfo.setParameter("ticRestAuthId", fdId);
		List list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list);
	}

	@Override
    public boolean validateRestCookieSetting(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"ticRestMain.ticRestCookieSetting.fdId =:ticRestCookieSettingId");
		hqlInfo.setParameter("ticRestCookieSettingId", fdId);
		List list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list);
	}
	
	@Override
    public boolean validateRestPrefixReqSetting(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"ticRestMain.ticRestPrefixReqSetting.fdId =:ticRestPrefixReqSettingId");
		hqlInfo.setParameter("ticRestPrefixReqSettingId", fdId);
		List list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list);
	}
}
