package com.landray.kmss.fssc.expense.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.fssc.common.util.FsscCommonProcessUtil;
import com.landray.kmss.fssc.expense.forms.FsscExpenseShareMainForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareMain;
import com.landray.kmss.fssc.expense.service.IFsscExpenseShareMainService;
import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class FsscExpenseShareMainAction extends ExtendAction {

	private IFsscExpenseShareMainService fsscExpenseShareMainService;

	@Override
    public IBaseService getServiceImp(HttpServletRequest request) {
		if (fsscExpenseShareMainService == null) {
			fsscExpenseShareMainService = (IFsscExpenseShareMainService) getBean("fsscExpenseShareMainService");
		}
		return fsscExpenseShareMainService;
	}

	@Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscExpenseShareMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
		FsscCommonProcessUtil.buildLbpmHanderHql(hqlInfo, request, "fsscExpenseShareMain");
	}

	@Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
		FsscExpenseShareMainForm fsscExpenseShareMainForm = (FsscExpenseShareMainForm) super.createNewForm(mapping,
				form, request, response);
		((IFsscExpenseShareMainService) getServiceImp(request)).initFormSetting((IExtendForm) form,
				new RequestContext(request));
		String docTemplateName = fsscExpenseShareMainForm.getDocTemplateName();
		FsscExpenseShareCategory tem=(FsscExpenseShareCategory) getServiceImp(request).findByPrimaryKey(fsscExpenseShareMainForm.getDocTemplateId(),FsscExpenseShareCategory.class,true);
		while (tem.getFdParent() != null) {
			tem=(FsscExpenseShareCategory)tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		return fsscExpenseShareMainForm;
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.loadActionForm(mapping, form, request, response);
		String fdId = request.getParameter("fdId");
		FsscExpenseShareMain main = (FsscExpenseShareMain) getServiceImp(request).findByPrimaryKey(fdId, null, true);
		FsscExpenseShareCategory tem = main.getDocTemplate();
		String docTemplateName = tem.getFdName();
		while (tem.getFdParent() != null) {
			tem = (FsscExpenseShareCategory) tem.getFdParent();
			docTemplateName = tem.getFdName() + "  >  " + docTemplateName;
		}
		request.setAttribute("docTemplateName", docTemplateName);
		request.setAttribute("docTemplate", main.getDocTemplate());
	}

	/**
	 * 打印单据。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回print页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			FsscExpenseShareMainForm rtnForm = null;
			String id = request.getParameter("fdId");
			if (!StringUtil.isNull(id)) {
				FsscExpenseShareMain model = (FsscExpenseShareMain) getServiceImp(request).findByPrimaryKey(id, null,
						true);
				if (model != null) {
					request.setAttribute("fdShareType", model.getDocTemplate().getFdShareType()); //分摊类型
					rtnForm = (FsscExpenseShareMainForm) getServiceImp(request).convertModelToForm((IExtendForm) form,
							model, new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	/**
	 * 校验是否选中待审、发布单据
	 */
	public ActionForward checkDeleteAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		if (!EopBasedataFsscUtil.checkStatus(ids, FsscExpenseShareMain.class.getName(), "docStatus", "20;30")) {
			messages.addError(new KmssMessage(ResourceUtil.getString("fssc.common.examine.or.publish.delete.tips", "fssc-common")));
		}
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
