package com.landray.kmss.sys.mportal.actions;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mportal.forms.SysMportalCompositeForm;
import com.landray.kmss.sys.mportal.model.SysMportalComposite;
import com.landray.kmss.sys.mportal.model.SysMportalCpageRelation;
import com.landray.kmss.sys.mportal.service.ISysMportalCompositeService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageRelationService;
import com.landray.kmss.sys.mportal.service.ISysMportalCpageService;
import com.landray.kmss.sys.mportal.util.SysMportalConstant;
import com.landray.kmss.sys.mportal.util.SysMportalImgUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysMportalCompositeAction extends ExtendAction {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysMportalCompositeAction.class);

    private ISysMportalCompositeService sysMportalCompositeService;

    @Override
	public ISysMportalCompositeService getServiceImp(HttpServletRequest request) {
        if (sysMportalCompositeService == null) {
            sysMportalCompositeService = (ISysMportalCompositeService) getBean("sysMportalCompositeService");
        }
        return sysMportalCompositeService;
    }
    
    private ISysMportalCpageRelationService sysMportalCpageRelationService;
    public ISysMportalCpageRelationService getSysMportalCpageRelationService() {
        if (sysMportalCpageRelationService == null) {
        	sysMportalCpageRelationService = (ISysMportalCpageRelationService) SpringBeanUtil.getBean("sysMportalCpageRelationService");
        }
        return sysMportalCpageRelationService;
    }
    
    private ISysMportalCpageService sysMportalCpageService;
    public ISysMportalCpageService getSysMportalCpageService() {
        if (sysMportalCpageService == null) {
        	sysMportalCpageService = (ISysMportalCpageService) SpringBeanUtil.getBean("sysMportalCpageService");
        }
        return sysMportalCpageService;
    }
    
    @Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysMportalCompositeForm xform = (SysMportalCompositeForm) super.createNewForm(mapping, form, request,
				response);
		xform.setDocCreatorId(UserUtil.getUser().getFdId());
		xform.setDocCreatorName(UserUtil.getUser().getFdName());
		xform.setDocCreateTime(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATETIME,
				request.getLocale()));
		xform.setFdEnabled("true");
		return xform;
	}
	

	/**
	 * 门户选择数据源
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward loadPages(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-loadPages", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy("sysMportalCpage.fdOrder asc, sysMportalCpage.docCreateTime desc");
			hqlInfo.setWhereBlock("sysMportalCpage.fdEnabled=:fdEnabled");
			hqlInfo.setParameter("fdEnabled", Boolean.TRUE);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			hqlInfo.setSelectBlock(
					"sysMportalComposite.fdId, sysMportalCpage.fdName,sysMportalCpage.fdType,sysMportalCpage.fdIcon,sysMportalCpage.fdImg");

			String rowsize = request.getParameter("rowsize");

			List<Object[]> list = null;
			if (StringUtil.isNotNull(rowsize)) {
				hqlInfo.setRowSize(Integer.valueOf(rowsize));
				list = this.getServiceImp(request).findPage(hqlInfo).getList();
			} else {
				list = this.getServiceImp(request).findList(hqlInfo);
			}

			JSONArray rtnArray = covertListToJSONArray(list);
			request.setAttribute("lui-source", rtnArray);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-loadPages", false, getClass());
		if (messages.hasError()) {
            return getActionForward("lui-failure", mapping, form, request, response);
        } else {
            return getActionForward("lui-source", mapping, form, request, response);
        }
	}

	private JSONArray covertListToJSONArray(List<Object[]> list) throws Exception {
		JSONArray rtnArray = new JSONArray();
		for (Object[] obj : list) {
			JSONArray innerArray = new JSONArray();
			innerArray.add(obj[0]);
			innerArray.add(obj[1]);
			innerArray.add(obj[2]);
			innerArray.add(SysMportalImgUtil.logo(obj[0].toString()));
			innerArray.add(obj[3]);
			innerArray.add(obj[4]);
			rtnArray.add(innerArray);
		}
		return rtnArray;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo)
			throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysMportalComposite.class);

		// 列表页过滤可编辑者
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck,
				SysAuthConstant.AuthCheck.SYS_EDITOR);

	}

	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionForward forward = super.list(mapping, form, request, response);
		String contentType = request.getParameter("contentType");
		if (!"failure".equals(forward.getName()) && "json".equals(contentType)) {
			return getActionForward("data", mapping, form, request, response);
		}
		return forward;
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			SysMportalCompositeForm pageForm = (SysMportalCompositeForm) form;
			request.setAttribute("fdCompositeId", pageForm.getFdId());
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}


	public ActionForward disableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateInvalidatedAll(ids, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
		// KmssReturnPage.BUTTON_RETURN).save(request);
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward enableAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-invalidated", true, getClass());
		KmssMessages messages = new KmssMessages();
		String[] ids = request.getParameterValues("List_Selected");
		try {
			if (ids != null) {
				getServiceImp(request).updateValidatedAll(ids, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-invalidated", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysMportalCompositeForm sysMportalCompositeForm = (SysMportalCompositeForm) form;
			sysMportalCompositeForm.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
			// 是否从暂存页面保存
			BaseModel model = (BaseModel) getServiceImp(request)
					.findByPrimaryKey(sysMportalCompositeForm.getFdId(), null,
							true);
			if (model != null) {
				getServiceImp(request).update(sysMportalCompositeForm,
						new RequestContext(request));
			} else {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveadd", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysMportalCompositeForm sysMportalCompositeForm = (SysMportalCompositeForm) form;
			sysMportalCompositeForm.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
			// 是否从暂存页面保存
			BaseModel model = (BaseModel) getServiceImp(request)
					.findByPrimaryKey(sysMportalCompositeForm.getFdId(), null,
							true);
			if (model != null) {
				getServiceImp(request).update(sysMportalCompositeForm,
						new RequestContext(request));
			} else {
				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else {
            return add(mapping, form, request, response);
        }
	}
	

	public ActionForward saveDraftAndPerview(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		PrintWriter out1 = response.getWriter();
		PrintWriter out2 = response.getWriter();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysMportalCompositeForm sysMportalCompositeForm = (SysMportalCompositeForm) form;
			String method_GET = sysMportalCompositeForm.getMethod_GET();
			if ("add".equals(method_GET)) {
				sysMportalCompositeForm
						.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			} else {
				sysMportalCompositeForm
						.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
			}
			// 判断是否已经暂存
			BaseModel model = (BaseModel) getServiceImp(request)
					.findByPrimaryKey(sysMportalCompositeForm.getFdId(), null,
							true);
			if (model != null) {
				JSONObject json1 = new JSONObject();
				json1.accumulate("fdId", model.getFdId());
				json1.accumulate("success", true);
				getServiceImp(request).update(
						(IExtendForm) sysMportalCompositeForm,
						new RequestContext(request));
				out1.print(json1);
			} else {
				JSONObject json2 = new JSONObject();
				String fdId = getServiceImp(request).add(
						(IExtendForm) sysMportalCompositeForm,
						new RequestContext(request));
				json2.accumulate("fdId", fdId);
				json2.accumulate("success", true);
				out2.print(json2);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		return null;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysMportalCompositeForm sysMportalCompositeForm = (SysMportalCompositeForm) form;
			sysMportalCompositeForm.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
			getServiceImp(request).update((IExtendForm) form, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {

			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null){
				HQLInfo hqlInfo = new HQLInfo();
		 		hqlInfo.setWhereBlock("sysMportalComposite.fdId=:fdId");
		 		hqlInfo.setParameter("fdId", model.getFdId());
		 		hqlInfo.setOrderBy("fdOrder");
		 		List<SysMportalCpageRelation> pages = getSysMportalCpageRelationService().findList(hqlInfo);
		 		for(SysMportalCpageRelation page : pages) {	
		 			//页签类型
		 			if(SysMportalConstant.MPORTAL_PAGE_TYPE_2.equals(page.getFdType())) {
		 				hqlInfo = new HQLInfo();
				 		hqlInfo.setWhereBlock("fdParent.fdId=:fdId");
				 		hqlInfo.setParameter("fdId", page.getFdId());
				 		hqlInfo.setOrderBy("fdOrder");
				 		List<SysMportalCpageRelation> childs = getSysMportalCpageRelationService().findList(hqlInfo);
				 		page.setChildPageRelations(childs);
		 			}
		 		}
		 		request.setAttribute("pages", pages);
		 		SysMportalComposite sysMportalComposite = (SysMportalComposite)model;
		 		sysMportalComposite.setPages(pages);
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				UserOperHelper.logFind(model);						
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}
    
	/**
	 * 读取数据保存到页面缓存
	 */
	public ActionForward initParams(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		KmssMessages messages = new KmssMessages();

		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		try {
			JSONObject jsonObject = new JSONObject();
			// 获取页面缓存数据
			((ISysMportalCompositeService) getServiceImp(request)).initCompositeMessage(request, jsonObject, false);
			out.println(jsonObject.toString());
		} catch (Exception e) {
			messages.addError(e);
			logger.error("移动端首页init数据加载失败", e);
		}

		return null;
	}


	/**
	 * 读取门户预览数据
	 */
	public ActionForward initPreviewParams(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		KmssMessages messages = new KmssMessages();

		response.setContentType("application/jsonp");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		try {
			JSONObject jsonObject = new JSONObject();
			// 获取页面缓存数据
			((ISysMportalCompositeService) getServiceImp(request)).initCompositeMessage(request, jsonObject, true);
			out.println(jsonObject.toString());
		} catch (Exception e) {
			messages.addError(e);
			logger.error("移动端首页init数据加载失败", e);
		}

		return null;
	}
	
}
