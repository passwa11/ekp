package com.landray.kmss.sys.simplecategory.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.component.bklink.util.CompBklinkUtil;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.IBaseAuthTmpForm;
import com.landray.kmss.sys.simplecategory.forms.ISysSimpleCategoryForm;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 简单分类
 * 
 * @author wubing
 */
public abstract class SysSimpleCategoryAction extends ExtendAction

{

	/**
	 * 执行添加操作主要的业务代码。<br>
	 * 仅用于add的操作。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ISysSimpleCategoryForm categoryForm = (ISysSimpleCategoryForm) form;

		String selectedId = categoryForm.getFdParentId();
		if (StringUtil.isNull(selectedId)) {
            selectedId = request.getParameter("parentId");
        }
		categoryForm.reset(mapping, request);

		if (StringUtil.isNotNull(selectedId)) {
			return getNewFormFromCate(selectedId, request, true);
		}

		// 当前用户默认设置为可维护者
		if (form instanceof IBaseAuthTmpForm) {
			if (!UserUtil.getKMSSUser().isAdmin()) {
				IBaseAuthTmpForm baseAuthTmpForm = (IBaseAuthTmpForm) form;
				baseAuthTmpForm.setAuthEditorIds(UserUtil.getUser().getFdId());
				baseAuthTmpForm.setAuthEditorNames(UserUtil.getUser()
						.getFdName());
			}
		}

		// 设置场所
		if (categoryForm instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) categoryForm;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		return (ActionForm) categoryForm;
	}

	/**
	 * 添加操作调用的函数,在有父类别参数时调用<br>
	 * 仅用于add的操作。
	 * 
	 * @param parentId
	 * @param request
	 * @param isParent
	 * @return
	 * @throws Exception
	 */
	protected ActionForm getNewFormFromCate(String parentId,
			HttpServletRequest request, boolean isParent) throws Exception {
		ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) getServiceImp(
				request).findByPrimaryKey(parentId);
		UserOperHelper.logFind(category);
		ISysSimpleCategoryForm categoryForm = (ISysSimpleCategoryForm) getServiceImp(
				request).cloneModelToForm(null, category,
				new RequestContext(request));
		if (isParent) {
			categoryForm.setFdParentId(category.getFdId().toString());
			categoryForm.setFdParentName(category.getFdName());
//			categoryForm.setAuthReaderIds(null);
//			categoryForm.setAuthReaderNames(null);
			categoryForm.setAuthEditorIds(null);
			categoryForm.setAuthEditorNames(null);
			categoryForm.setFdOrder(null);
			categoryForm.setFdName(null);
			categoryForm.setAuthNotReaderFlag("false");
			categoryForm.getDynamicMap().clear();
		} else {
			categoryForm.setFdOrder(null);
			categoryForm.setFdName(ResourceUtil.getString(
					"sysSimpleCategory.copyOf", "sys-simplecategory")
					+ " "
					+ categoryForm.getFdName());

		}
		categoryForm.setFdDesc(null);
		categoryForm.setFdIsinheritMaintainer("true");
		categoryForm.setFdIsinheritUser("false");
		// ((ExtendForm) categoryForm).setFdId(null);
		((ExtendForm) categoryForm).setMethod("add");
		((ExtendForm) categoryForm).setMethod_GET("add");
		if ("true".equals(categoryForm.getFdIsinheritMaintainer())) {
			request.setAttribute("parentMaintainer",
					getParentMaintainer(category, request, isParent));
		}

		// 设置场所
		if (categoryForm instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) categoryForm;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		return (ExtendForm) categoryForm;
	}

	private String getParentMaintainer(ISysSimpleCategoryModel category,
			HttpServletRequest request, boolean isParent) throws Exception {
		List allEditors = new ArrayList();
		if (!isParent) {
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		while (category != null && category.getFdIsinheritMaintainer() != null
				&& category.getFdIsinheritMaintainer().booleanValue()) {
			ArrayUtil.concatTwoList(category.getAuthEditors(), allEditors);
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		return ArrayUtil.joinProperty(allEditors, "fdName", ";")[0];
	}

	public ActionForward getParentMaintainer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String parentId = request.getParameter("parentId");
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) getServiceImp(
					request).findByPrimaryKey(parentId,null,true);
			response(response, getParentMaintainer(category, request, true));
		} catch (Exception e) {
			e.printStackTrace();
			response(response, "");
		}
		return null;
	}
	
	/**
	 * 获取父分类的可维护者和可使用者
	 * 
	 */
	public ActionForward getAllParentMaintainer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TimeCounter.logCurrentTime("Action-getAllParentMaintainer", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String parentId = request.getParameter("parentId");
			ISysSimpleCategoryModel category = (ISysSimpleCategoryModel) getServiceImp(
					request).findByPrimaryKey(parentId,null,true);
			JSONObject jsonObj  = getAllParentMaintainer(category,request,true);
			request.setAttribute("lui-source", jsonObj);

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-getAllParentMaintainer", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("lui-source", mapping, form, request, response);
		}
	}
	
	/**
	 * 获取父分类的可维护者和可使用者
	 * 
	 */
	private JSONObject getAllParentMaintainer(ISysSimpleCategoryModel category,
			HttpServletRequest request, boolean isParent) throws Exception {
		List allEditors = new ArrayList();
		List allReaders = new ArrayList();
		JSONObject  jsonObj = new JSONObject();
		if (!isParent) {
			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		while (category != null && category.getFdIsinheritMaintainer() != null
				&& category.getFdIsinheritMaintainer().booleanValue()) {
			ArrayUtil.concatTwoList(category.getAuthEditors(), allEditors);
			ArrayUtil.concatTwoList(category.getAuthReaders(), allReaders); //此处暂定获取维护者的同时也获取可使用者

			category = (ISysSimpleCategoryModel) category.getFdParent();
		}
		
		
		if(!ArrayUtil.isEmpty(allEditors)){
			String[] editors = ArrayUtil.joinProperty(allEditors, "fdId:fdName", ";");
			String editorIds = editors[0];
			String editorNames = editors[1];
			jsonObj.put("editorIds",editorIds);
			jsonObj.put("editorNames",editorNames);

		}
		
		if(!ArrayUtil.isEmpty(allReaders)){
			ISysOrgCoreService sysOrgCoreService =  (ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
			SysOrgPerson everyOne  = sysOrgCoreService.getEveryonePerson();
			if(allReaders.contains(everyOne)){ //去除“所有人”
				allReaders.remove(everyOne);
			}
			if(!ArrayUtil.isEmpty(allReaders)){
				String[] readers = ArrayUtil.joinProperty(allReaders, "fdId:fdName", ";");
				//过滤所有人
				String readerIds = readers[0];
				String readerNames = readers[1];
				jsonObj.put("readerIds",readerIds);
				jsonObj.put("readerNames",readerNames);
			}
			
		}

		
		return jsonObj;
	}


	private void response(HttpServletResponse response, String message)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.getOutputStream().write(message.getBytes("UTF-8"));
	}

	/**
	 * 拷贝现有的分类生成一个新的分类文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-copy", true, getClass());
		String fdId = request.getParameter("fdCopyId");
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = getNewFormFromCate(fdId, request, false);
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-add", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ISysSimpleCategoryForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			ISysSimpleCategoryModel model = (ISysSimpleCategoryModel) getServiceImp(
					request).findByPrimaryKey(id);
			if (model != null) {
				UserOperHelper.logFind(model);// 添加日志信息
				rtnForm = (ISysSimpleCategoryForm) getServiceImp(request)
						.convertModelToForm((IExtendForm) form, model,
								new RequestContext(request));
				if ("true".equals(rtnForm.getFdIsinheritMaintainer())) {
					request.setAttribute("parentMaintainer",
							getParentMaintainer(model, request, false));
				}
			}
			if (rtnForm != form) {
                request.setAttribute(getFormName(rtnForm, request), rtnForm);
            }
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
	}

	/**
	 * 根据http请求，返回执行list操作需要用到的where语句。
	 * 
	 * @param request
	 * @throws Exception
	 */
	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String strPara = request.getParameter("parentId");
		String tableName = ModelUtil.getModelTableName(this.getServiceImp(
				request).getModelName());
		if (strPara != null) {
			whereBlock += " and " + tableName + ".hbmParent.fdId = :strPara ";
			hqlInfo.setParameter("strPara", strPara);
		}
		hqlInfo.setWhereBlock(whereBlock);

	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		String modelName = request.getParameter("modelName");
		String forward = "failure";
		String[] ids = null;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			ids = request.getParameterValues("List_Selected");
			if (ids != null) {
                getServiceImp(request).delete(ids);
            }
		} catch (Exception e) {
			messages.addError(e);
			String forwardTemp = CompBklinkUtil.isDeleteConstraintException(e);
			String tempIds = CompBklinkUtil.getIds(ids);
			String httpURL = request.getRequestURI();
			request.setAttribute("httpURL", httpURL);
			request.setAttribute("ids", tempIds);
			request.setAttribute("modelName", modelName);
			// request.setAttribute("searchCondition", "fdModelName:" +
			// modelName);
			if (StringUtil.isNotNull(forwardTemp)) {
				forward = forwardTemp;
			}
		}

		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward(forward, mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String url = new StringBuilder()
				.append(request.getRequestURI().replace(
						request.getContextPath(), "")).append("?method=add")
				.toString();
		ISysSimpleCategoryForm rtnForm = (ISysSimpleCategoryForm) form;

		if ((StringUtil.isNull(rtnForm.getFdParentId()) && !UserUtil
				.checkAuthentication(url, "post"))
				&& StringUtil.isNull(request.getParameter("fdCopyId"))) { // 增加多一个判断，有复制权限也可以新增
			messages.addError(new KmssMessage(
					"sys-simplecategory:sysSimpleCategory.noAuth"));
		} else {
			try {
				if (!"POST".equals(request.getMethod())) {
                    throw new UnexpectedRequestException();
                }

				getServiceImp(request).add((IExtendForm) form,
						new RequestContext(request));

			} catch (Exception e) {
				messages.addError(e);
			}
		}

		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}
}
