package com.landray.kmss.hr.ratify.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.hr.ratify.model.HrRatifyOutSign;
import com.landray.kmss.hr.ratify.service.IHrRatifyOutSignService;

public class HrRatifyOutSignServiceImp extends BaseServiceImp implements IHrRatifyOutSignService{
	
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

	@Override
	public String queryCreateId(String id) throws Exception {
		HQLInfo hql = new HQLInfo();
		String whereBlock = " hrRatifyOutSign.fdMainId=:fdMainId";
		hql.setParameter("fdMainId", id);
		hql.setWhereBlock(whereBlock);
		List<HrRatifyOutSign> findList = this.findList(hql);
		String fdId=null;
		if (findList.size()>0) {
			fdId= findList.get(0).getDocCreator().getFdId();
		}
		return fdId;
	}

}
