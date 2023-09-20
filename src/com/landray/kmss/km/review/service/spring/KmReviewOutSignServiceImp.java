package com.landray.kmss.km.review.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.review.model.KmReviewOutSign;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;

public class KmReviewOutSignServiceImp extends BaseServiceImp implements IKmReviewOutSignService {
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
	public Integer findBySignId(String modelId) throws Exception {
		HQLInfo hql = new HQLInfo();
		String whereBlock = " kmReviewOutSign.fdMainId=:fdMainId";
		hql.setParameter("fdMainId", modelId);
		hql.setWhereBlock(whereBlock);
		List<KmReviewOutSign> findList = this.findList(hql);
		return findList.size();
	}
}
