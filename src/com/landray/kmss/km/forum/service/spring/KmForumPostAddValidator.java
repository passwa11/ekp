package com.landray.kmss.km.forum.service.spring;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.km.forum.model.KmForumCategory;
import com.landray.kmss.km.forum.service.IKmForumCategoryService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

public class KmForumPostAddValidator implements IAuthenticationValidator {

	private IKmForumCategoryService kmForumCategoryService;

	public void setKmForumCategoryService(
			IKmForumCategoryService kmForumCategoryService) {
		this.kmForumCategoryService = kmForumCategoryService;
	}

	@Override
	public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String fdForumId = validatorContext.getValidatorParaValue("recid");
		// 期望当fdForumId为空时，进入新建页面选分类
		if (StringUtil.isNotNull(fdForumId)) {
			KmForumCategory category = (KmForumCategory) kmForumCategoryService
					.findByPrimaryKey(fdForumId);
			boolean flag = false;
			List authAllReaders = category.getAuthAllReaders();
			List<String> authOrgIds = validatorContext.getUser()
					.getUserAuthInfo().getAuthOrgIds();
			if (authAllReaders.isEmpty()) {
				List authReaders = category.getAuthReaders();
				if (authReaders.isEmpty()) {
					if (!BooleanUtils.isTrue(validatorContext.getUser().getPerson().getFdIsExternal())) {
						flag = true;
					}
				} else {
					authReaders = ArrayUtil.convertArrayToList(
							ArrayUtil.joinProperty(authReaders, "fdId", ";")[0]
									.split(";"));
					flag = ArrayUtil.isListIntersect(authReaders, authOrgIds);
				}
			} else {
				authAllReaders = ArrayUtil.convertArrayToList(
						ArrayUtil.joinProperty(authAllReaders, "fdId", ";")[0]
								.split(";"));
				flag = ArrayUtil.isListIntersect(authAllReaders, authOrgIds);
			}
			return validatorContext.getUser().isAdmin() || flag;
		}
		return true;
	}

}
