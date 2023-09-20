package com.landray.kmss.km.review.service;

import java.util.List;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

public interface IKmReviewYqqSignService {
	public Boolean sendYqq(KmReviewMain kmReviewMain, String phone,
			String fdEnterprise,
			List<SysOrgPerson> elements) throws Exception;
}
