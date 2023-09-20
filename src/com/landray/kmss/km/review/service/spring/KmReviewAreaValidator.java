package com.landray.kmss.km.review.service.spring;

import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 校验文档所属场所和当前场所是否一致
 * 
 * @author suyb
 *
 */
public class KmReviewAreaValidator implements IAuthenticationValidator {
	protected IKmReviewMainService kmReviewMainService;

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	@Override
    public boolean validate(ValidatorRequestContext validatorContext)
			throws Exception {
		String id = validatorContext.getParameter("fdId");
		String reviewId = validatorContext.getParameter("fdReviewId");
		if (StringUtil.isNotNull(reviewId) && StringUtil.isNull(id)) {
			id = reviewId;
		}
		if (StringUtil.isNotNull(id) && ISysAuthConstant.IS_AREA_ENABLED) {
			KmReviewMain kmReviewMain = (KmReviewMain) this.kmReviewMainService
					.findByPrimaryKey(id);
			// 文档所在场所id
			String authAreaId = kmReviewMain.getAuthArea().getFdId();
			// 获取当前场所id
			String currentAuthAreaId = UserUtil.getKMSSUser().getAuthAreaId();
			if (StringUtil.isNotNull(authAreaId)
					&& authAreaId.equals(currentAuthAreaId)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
