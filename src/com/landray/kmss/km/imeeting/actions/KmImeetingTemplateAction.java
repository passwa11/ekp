package com.landray.kmss.km.imeeting.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;

import com.landray.kmss.common.actions.TemplateNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingTemplateForm;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingTemplateService;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议模板 Action
 */
public class KmImeetingTemplateAction extends TemplateNodeAction {
	private ISysCategoryMainService categoryMainService;
	protected IKmImeetingTemplateService kmImeetingTemplateService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingTemplateService == null) {
            kmImeetingTemplateService = (IKmImeetingTemplateService) getBean("kmImeetingTemplateService");
        }
		return kmImeetingTemplateService;
	}

	@Override
	protected String getParentProperty() {
		return "docCategory";
	}

	@Override
	protected IBaseService getTreeServiceImp(HttpServletRequest request) {
		if (categoryMainService == null) {
            categoryMainService = (ISysCategoryMainService) getBean("sysCategoryMainService");
        }
		return categoryMainService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		if (isEditor(request)) {
            hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        }
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		String fdIsAvailable = cv.poll("fdIsAvailable");
		if (StringUtil.isNotNull(fdIsAvailable)) {

			Boolean __isTrue = "1".equalsIgnoreCase(fdIsAvailable);
			String __whereBlock = "( kmImeetingTemplate.fdIsAvailable = :fdIsAvailable";
			if (__isTrue) {
                __whereBlock += " or kmImeetingTemplate.fdIsAvailable is null";
            }
			__whereBlock += ") ";
			hqlInfo.setParameter("fdIsAvailable", __isTrue);
			__whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", __whereBlock);
			hqlInfo.setWhereBlock(__whereBlock);

		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingTemplate.class);
	}

	private boolean isEditor(HttpServletRequest request) throws Exception {
		ValidatorRequestContext validatorContext = new ValidatorRequestContext(
				request, "");
		validatorContext.setValidatorPara("cateid", "q.docCategory");
		return getAuthCategoryEditorValidator().validate(validatorContext);
	}

	@Override
	public ActionForward listChildren(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = super.listChildren(mapping, form, request,
				response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingTemplateForm kmImeetingTemplateForm = (KmImeetingTemplateForm) super
				.createNewForm(mapping, form, request, response);
		String docCategoryId = request.getParameter("parentId");
		if (StringUtil.isNotNull(docCategoryId)) {
			SysCategoryMain sysCategoryMain = (SysCategoryMain) getTreeServiceImp(
					request).findByPrimaryKey(docCategoryId);
			if (UserUtil
					.checkAuthentication(
							"/km/imeeting/km_imeeting_template/kmImeetingTemplate.do?method=save&docCategoryId="
									+ docCategoryId, "post")) {
				kmImeetingTemplateForm.setDocCategoryId(docCategoryId);
				kmImeetingTemplateForm.setDocCategoryName(sysCategoryMain
						.getFdName());
			} else {
				request.setAttribute("noAccessCategory", sysCategoryMain
						.getFdName());
			}
		}
		return kmImeetingTemplateForm;

	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }

		} catch (Exception e) {
			messages.setHasError();
			KmImeetingTemplate template = (KmImeetingTemplate) getServiceImp(request)
					.findByPrimaryKey(request.getParameter("fdId"));
			messages.addMsg(new KmssMessage("km-imeeting:kmImeetingTemplate.delete.tip", template.getFdName()));
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								   HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids, request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage("sys-authorization:area.batch.operation.info", noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}
		} catch (Exception e) {
			messages.setHasError();
			messages.addMsg(new KmssMessage("km-imeeting:kmImeetingTemplate.deleteall.tip"));
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	public ActionForward addSyncToBoen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			((IKmImeetingTemplateService) getServiceImp(request)).addSyncToBoen();
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}
}
