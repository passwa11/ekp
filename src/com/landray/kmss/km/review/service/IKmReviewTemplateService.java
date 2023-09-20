package com.landray.kmss.km.review.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;


/**
 * 创建日期 2007-Aug-30
 * @author 舒斌
 * 审批流程模板业务对象接口
 */
public interface IKmReviewTemplateService  extends IBaseService {
	
	/**
	 * 不填写名称拷贝
	 */
	public IExtendForm cloneModelToFormNoName(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception;

	/**
	 * 钉钉高级审批分类下的模版迁移
	 * 
	 * @param oldCategoryId
	 *            旧分类
	 * @param newCategoryId
	 *            新分类
	 * @return
	 */
	public boolean updateTemplate2OtherCategory(String oldCategoryId,
			String newCategoryId);

}
