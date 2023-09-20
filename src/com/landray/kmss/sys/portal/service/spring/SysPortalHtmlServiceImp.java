package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.portal.forms.SysPortalHtmlForm;
import com.landray.kmss.sys.portal.service.ISysPortalHtmlService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.filter.security.ConverterContext;
import com.landray.kmss.web.filter.security.ConvertorBase64x;
import com.landray.kmss.web.filter.security.IConvertor;

/**
 * 自定义页面业务接口实现
 * 
 * @author 
 * @version 1.0 2013-09-23
 */
public class SysPortalHtmlServiceImp extends BaseServiceImp implements ISysPortalHtmlService {
	private final IConvertor convert = new ConvertorBase64x();
	
	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		SysPortalHtmlForm sysPortalHtmlForm = (SysPortalHtmlForm)form;
		String fdContent = sysPortalHtmlForm.getFdContent();
		fdContent = StringUtil.isNotNull(fdContent)
				? fdContent.replace("<p>", "").replace("</p>", "") : fdContent;
		if (StringUtil.isNotNull(fdContent)
				&& fdContent.startsWith(convert.getPrefix())) {//单独加密
			sysPortalHtmlForm.setFdContent(convert.convert(fdContent,
					new ConverterContext("fdContent", requestContext
							.getRequest())));
		}
		return super.convertFormToModel(form, model, requestContext);
	}
	
}
