package com.landray.kmss.third.pda.actions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.category.model.ISysCategoryBaseModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.pda.model.PdaModuleLabelList;
import com.landray.kmss.third.pda.service.IPdaModuleLabelListService;
import com.landray.kmss.third.pda.util.PdaPlugin;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 列表页签配置信息 Action
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleLabelListAction extends ExtendAction {
	protected IPdaModuleLabelListService pdaModuleLabelListService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (pdaModuleLabelListService == null) {
            pdaModuleLabelListService = (IPdaModuleLabelListService) getBean("pdaModuleLabelListService");
        }
		return pdaModuleLabelListService;
	}

	public ActionForward tainsit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lableId = request.getParameter("fdLabelId");
		String flag = request.getParameter("isAppflag");
		if (StringUtil.isNull(flag)) {
            flag = "0";
        }
		KmssMessages messages = new KmssMessages();
		String createUrl = null;
		if (StringUtil.isNotNull(lableId)) {
			try {
				PdaModuleLabelList label = (PdaModuleLabelList) getServiceImp(
						request).findByPrimaryKey(lableId, null, true);
				UserOperHelper.logFind(label);
				String dataUrl = label.getFdDataUrl();
				Map<String, String> valMap = null;
				if (StringUtil.isNotNull(dataUrl)) {
					valMap = PdaPlugin.getPdaExtendInfo(request, dataUrl,
							PdaPlugin.PARAM_PDA_EXTEND_CREATEURL + ";"
									+ PdaPlugin.PARAM_PDA_EXTEND_TEMPLATECLASS);
					createUrl = valMap
							.get(PdaPlugin.PARAM_PDA_EXTEND_CREATEURL);
				}
				if (StringUtil.isNotNull(createUrl)) {
					if (createUrl.indexOf("!{") > -1) {
						request.setAttribute("fdCreateUrl", createUrl);
						String tempClass = valMap
								.get(PdaPlugin.PARAM_PDA_EXTEND_TEMPLATECLASS);
						if (StringUtil.isNotNull(tempClass)) {
							Object tmpObj = com.landray.kmss.util.ClassUtils.forName(tempClass)
									.newInstance();
							if (tmpObj instanceof IBaseTemplateModel) {
								createUrl = "/sys/category/pda/sysCategory.do?method=list"
										+ "&modelName="
										+ tempClass
										+ "&isAppflag=" + flag;
							} else if (tmpObj instanceof ISysCategoryBaseModel) {
								createUrl = "/sys/simplecategory/pda/sysSimpleCategory.do?method=list"
										+ "&modelName="
										+ tempClass
										+ "&isAppflag=" + flag;
							}
						}
					}
				} else {
					messages.addError(new KmssMessage("检测发现该模块未能支持新建功能。"));
				}
			} catch (Exception e) {
				messages.addError(e);
			}
		} else {
			messages.addError(new KmssMessage("非法的请求。"));
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (createUrl.startsWith("/")) {
                return new ActionForward(createUrl);
            } else {
                return new ActionForward(createUrl, true);
            }
		}
	}

	
}
