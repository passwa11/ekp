package com.landray.kmss.km.review.service.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmAuditNoteService;

public class KmReviewDraftValidator implements IAuthenticationValidator {
	private ILbpmAuditNoteService lbpmAuditNoteService;
	protected IKmReviewMainService kmReviewMainService;

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	public void setLbpmAuditNoteService(
			ILbpmAuditNoteService lbpmAuditNoteService) {
		this.lbpmAuditNoteService = lbpmAuditNoteService;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewDraftValidator.class);

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String id = validatorContext.getParameter("fdId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("lbpmAuditNote.fdProcess.fdId = :processId and lbpmAuditNote.fdActionKey is not null");
		hqlInfo.setParameter("processId", id);
		hqlInfo.setOrderBy("lbpmAuditNote.fdCreateTime,lbpmAuditNote.fdId");
		LbpmAuditNote noteList =(LbpmAuditNote) lbpmAuditNoteService.findFirstOne(hqlInfo);
		if (null != noteList) {
            return false;
        }
		return true;
	}

}
