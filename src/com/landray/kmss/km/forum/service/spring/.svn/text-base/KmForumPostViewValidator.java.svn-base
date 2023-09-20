package com.landray.kmss.km.forum.service.spring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.landray.kmss.km.forum.model.KmForumTopic;
import com.landray.kmss.km.forum.service.IKmForumTopicService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;

public class KmForumPostViewValidator implements IAuthenticationValidator {

	private IKmForumTopicService kmForumTopicService;

	public void
			setKmForumTopicService(IKmForumTopicService kmForumTopicService) {
		this.kmForumTopicService = kmForumTopicService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		boolean isAdmin = validatorContext.getUser().isAdmin();
		String fdTopicId = validatorContext.getValidatorParaValue("recid");
		KmForumTopic kmForumTopic = (KmForumTopic) kmForumTopicService
				.findByPrimaryKey(fdTopicId);
		List<String> authOrgIds = validatorContext.getUser()
				.getUserAuthInfo().getAuthOrgIds();
		return isAdmin
				|| kmForumTopic.getKmForumCategory().getAuthReaders().isEmpty()
				|| ArrayUtil.isListIntersect(getVisitIds(kmForumTopic),
						authOrgIds);
	}

	private List<String> getVisitIds(KmForumTopic kmForumTopic) {
		List<String> fdVisitIds = new ArrayList<String>();
		Set<SysOrgElement> visits = new HashSet<SysOrgElement>();
		visits.add(kmForumTopic.getFdPoster());
		visits.addAll(kmForumTopic.getKmForumCategory().getAuthReaders());
		visits.addAll(kmForumTopic.getKmForumCategory().getAuthAllEditors());
		visits.addAll(kmForumTopic.getKmForumCategory().getAuthAllReaders());
		Iterator<SysOrgElement> iter = visits.iterator();
		while (iter.hasNext()) {
			SysOrgElement ele = iter.next();
			fdVisitIds.add(ele.getFdId());
		}
		return fdVisitIds;
	}

}
