package com.landray.kmss.km.comminfo.actions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.comminfo.forms.KmComminfoMainForm;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.km.comminfo.service.IKmComminfoAltInfoService;
import com.landray.kmss.km.comminfo.service.IKmComminfoCategoryService;
import com.landray.kmss.km.comminfo.service.IKmComminfoMainService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞
 */
public class KmComminfoMainAction extends ExtendAction {
	protected IKmComminfoMainService kmComminfoMainService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmComminfoMainService == null) {
            kmComminfoMainService = (IKmComminfoMainService) getBean("kmComminfoMainService");
        }
		return kmComminfoMainService;
	}

	/**
	 * 类别
	 */
	protected IKmComminfoMainService getKmComminfoMainServiceImp(
			HttpServletRequest request) {
		if (kmComminfoMainService == null) {
            kmComminfoMainService = (IKmComminfoMainService) getBean("kmComminfoMainService");
        }
		return kmComminfoMainService;
	}

	/**
	 * 类别信息
	 */
	protected IKmComminfoCategoryService kmComminfoCategoryService;

	public IKmComminfoCategoryService getKmComminfoCategoryService(
			HttpServletRequest request) {
		if (kmComminfoCategoryService == null) {
			kmComminfoCategoryService = (IKmComminfoCategoryService) getBean("kmComminfoCategoryService");
		}
		return kmComminfoCategoryService;
	}

	/**
	 * 资料修改信息
	 */
	protected IKmComminfoAltInfoService kmComminfoAltInfoService;

	public IKmComminfoAltInfoService getKmComminfoAltInfoService(
			HttpServletRequest request) {
		if (kmComminfoAltInfoService == null) {
			kmComminfoAltInfoService = (IKmComminfoAltInfoService) getBean("kmComminfoAltInfoService");
		}
		return kmComminfoAltInfoService;
	}

	/**
	 * 初始化页面信息
	 */
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmComminfoMainForm kmComminfoMainForm = (KmComminfoMainForm) super
				.createNewForm(mapping, form, request, response);
		// 默认创建时间
		kmComminfoMainForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));
		// 默认创建者
		kmComminfoMainForm.setDocCreatorName(UserUtil.getUser().getFdName());

		// 根据传过来的categoryId，求得该类别对象，把对象的属性赋给Form表单。
		String categoryId = request.getParameter("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			KmComminfoCategory category = (KmComminfoCategory) getKmComminfoCategoryService(
					request).findByPrimaryKey(categoryId);
			kmComminfoMainForm.setDocCategoryId(category.getFdId());
			kmComminfoMainForm.setDocCategoryName(category.getFdName());
		}
		Boolean flag = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases().contains("ROLE_COMMINFO_MANAGER");
		request.setAttribute("userId", UserUtil.getUser().getFdId());
		request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());
		request.setAttribute("flag", flag);

		return kmComminfoMainForm;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String whereBlock = null;
		String docCategoryId = request.getParameter("docCategoryId");
		// 根据类别分类
		if (StringUtil.isNotNull(docCategoryId)) {
			whereBlock = "kmComminfoMain.docCategory.fdId like :docCategoryId";
			hqlInfo.setParameter("docCategoryId", "%" + docCategoryId + "%");
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 根据类别查询 覆盖父类的getFindPageWhereBlock()方法
	 */
	@Override
	protected String getFindPageWhereBlock(HttpServletRequest request)
			throws Exception {
		String whereBlock = null;
		String docCategoryId = request.getParameter("docCategoryId");
		// 根据类别分类
		if (StringUtil.isNotNull(docCategoryId)) {
			whereBlock = "kmComminfoMain.docCategory.fdId like '%"
					+ docCategoryId + "%'";
		}
		return whereBlock;
	}

	/**
	 * 新建时加载类别信息
	 */
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("KmComminfoCategory");
		// 类别的List集合传到Edit页面
		List list = getKmComminfoCategoryService(request).findList(hqlInfo);
		request.setAttribute("categoryList", list);
		return super.add(mapping, form, request, response);
	}

	/**
	 * 保存后多加一个"返回"按钮
	 */
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			// ***************************************************************
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			// ***************************************************************
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 初始化Form表单
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
                rtnForm = getServiceImp(request).convertModelToForm(
                        (IExtendForm) form, model, new RequestContext(request));
            }
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
		// #136121 常用资料，拥有某个分类维护权限的普通用户编辑文档可以修改所属分类为其它不可维护分类
		Boolean flag = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthRoleAliases().contains("ROLE_COMMINFO_MANAGER");
		request.setAttribute("userId", UserUtil.getUser().getFdId());
		request.setAttribute("isAdmin", UserUtil.getKMSSUser().isAdmin());
		request.setAttribute("flag", flag);
	}

	/**
	 * 取得修改信息
	 */
	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		String forward = request.getParameter("forward");
		if ((StringUtil.isNotNull(forward) && "viewDoc".equals(forward))
				|| MobileUtil.getClientType(request) != -1) {
			forward = "viewDoc";
			// 文档信息
			String id = request.getParameter("fdId");
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			IExtendForm rtnForm = null;
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				KmComminfoMainForm mainForm = (KmComminfoMainForm) rtnForm;
				mainForm.setDocSubject(
						StringUtil.XMLEscape(mainForm.getDocSubject()));
				UserOperHelper.logFind(model);
			}
			request.setAttribute(getFormName(rtnForm, request), rtnForm);
			request.setAttribute("categoryId", ((KmComminfoMain) model)
					.getDocCategory().getFdId());
		} else {
			forward = "view";
			HQLInfo hqlInfoCate = new HQLInfo();
			hqlInfoCate.setModelName("KmComminfoCategory");
			hqlInfoCate.setOrderBy("kmComminfoCategory.fdOrder");
			// 所有分类
			List categoryList = getKmComminfoCategoryService(request).findList(
					hqlInfoCate);
			// 单个分类文档信息
			Map cateDocMap = new HashMap();
			int total = 0;
			for (int i = 0; i < categoryList.size(); i++) {
				String categoryId = ((KmComminfoCategory) categoryList.get(i))
						.getFdId();
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(
						"kmComminfoMain.docCategory.fdId=:categoryId");
				hqlInfo.setParameter("categoryId", categoryId);
				hqlInfo.setOrderBy(
						"kmComminfoMain.fdOrder,kmComminfoMain.docCreateTime desc");
				hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
				List mainDocs = getServiceImp(request).findValue(hqlInfo);
				UserOperHelper.logFindAll(mainDocs,
						getServiceImp(request).getModelName());
				UserOperHelper.setEventType("查询");
				total += mainDocs.size();
				cateDocMap.put(categoryId, mainDocs);
			}
			request.setAttribute("categoryList", categoryList);
			request.setAttribute("cateDocMap", cateDocMap);
			request.setAttribute("total", total);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward(forward, mapping, form, request, response);
		}
	}

	/**
	 * 批量删除资料文档时删除该资料的修改记录信息
	 */
	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ids != null) {
                getKmComminfoMainServiceImp(request).deleteMainAltInfo(ids);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 单个删除资料文档时删除该资料的修改记录信息
	 */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			String[] ids = new String[1];
			if (StringUtil.isNull(id)) {
				messages.addError(new NoRecordException());
			} else {
				ids[0] = id;
				getKmComminfoMainServiceImp(request).deleteMainAltInfo(ids);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}
}
