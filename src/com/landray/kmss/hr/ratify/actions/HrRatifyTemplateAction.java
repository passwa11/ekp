package com.landray.kmss.hr.ratify.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.ratify.forms.HrRatifyTemplateForm;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.ratify.util.HrRatifyTemplateFdType;
import com.landray.kmss.hr.ratify.util.PluginUtil;
import com.landray.kmss.sys.authentication.intercept.IAuthenticationValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.design.SysCfgFlowDef;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.right.interfaces.IBaseAuthTmpForm;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

public class HrRatifyTemplateAction extends ExtendAction {

    private IHrRatifyTemplateService hrRatifyTemplateService;

	private ISysCategoryMainService categoryMainService;

    @Override
    public IBaseService getServiceImp(HttpServletRequest request) {
        if (hrRatifyTemplateService == null) {
            hrRatifyTemplateService = (IHrRatifyTemplateService) getBean("hrRatifyTemplateService");
        }
        return hrRatifyTemplateService;
    }

	protected IBaseService getTreeServiceImp(HttpServletRequest request) {
		if (categoryMainService == null) {
			categoryMainService = (ISysCategoryMainService) getBean(
					"sysCategoryMainService");
		}
		return categoryMainService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, HrRatifyTemplate.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.hr.ratify.model.HrRatifyTemplate.class);
        com.landray.kmss.hr.ratify.util.HrRatifyUtil.buildHqlInfoModel(hqlInfo, request);
    }

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HrRatifyTemplateForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			rtnForm = (HrRatifyTemplateForm) getServiceImp(request)
					.convertModelToForm(
							(IExtendForm) form, model,
							new RequestContext(request));
			String fdKey = rtnForm.getFdTempKey();
			String fdType = rtnForm.getFdType();
			if (StringUtil.isNull(fdKey) && StringUtil.isNotNull(fdType)) {
				fdKey = StringUtil.linkString(
						fdType.substring(0, fdType.indexOf("TemplateForm")), "",
						"Doc");
				rtnForm.setFdTempKey(fdKey);
			}
			String modelName = PluginUtil.getConfigByModelName(
					PluginUtil.EXTENSION_TEMPLATE_POINT_ID, fdKey,
					PluginUtil.PARAM_MODELNAME);
			String title = PluginUtil.getConfigByModelName(
					PluginUtil.EXTENSION_TEMPLATE_POINT_ID, fdKey,
					PluginUtil.PARAM_NAME);
			request.setAttribute("fdName",
					ResourceUtil.getString(
							"enums.template_fd_type." + rtnForm.getFdType(),
							"hr-ratify", request.getLocale()));
			request.setAttribute("modelName", modelName);
			request.setAttribute("title", title);
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HrRatifyTemplateForm hrRatifyTemplateForm = (HrRatifyTemplateForm) super.createNewForm(mapping, form, request, response);
		String fdKey = request.getParameter("fdkey");
		String fdType = HrRatifyTemplateFdType.getValue(fdKey);
		hrRatifyTemplateForm.setFdType(fdType);
		hrRatifyTemplateForm.setDocCreatorName(UserUtil.getUser().getFdName());
		hrRatifyTemplateForm
				.setDocCreateTime(DateUtil.convertDateToString(new Date(),
						DateUtil.TYPE_DATETIME, request.getLocale()));
		String docCategoryId = request.getParameter("parentId");
		String modelName = PluginUtil.getConfigByModelName(
				PluginUtil.EXTENSION_TEMPLATE_POINT_ID, fdKey,
				PluginUtil.PARAM_MODELNAME);
		String title = PluginUtil.getConfigByModelName(
				PluginUtil.EXTENSION_TEMPLATE_POINT_ID, fdKey,
				PluginUtil.PARAM_NAME);
		request.setAttribute("fdName", EnumerationTypeUtil
				.getColumnEnumsLabel("hr_ratify_template_fd_type", fdType));
		request.setAttribute("modelName", modelName);
		request.setAttribute("title", title);
		hrRatifyTemplateForm.setFdTempKey(fdKey);
		if (StringUtil.isNotNull(docCategoryId)) {
			SysCategoryMain sysCategoryMain = (SysCategoryMain) getTreeServiceImp(
					request).findByPrimaryKey(docCategoryId);
			if (UserUtil.checkAuthentication(
					"/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=save&docCategoryId="
							+ docCategoryId,
					"post")) {
				hrRatifyTemplateForm.setDocCategoryId(docCategoryId);
				hrRatifyTemplateForm
						.setDocCategoryName(sysCategoryMain.getFdName());
			} else {
				request.setAttribute("noAccessCategory", sysCategoryMain
						.getFdName());
			}
		}
		// 当前用户默认设置为可维护者
		if (form instanceof IBaseAuthTmpForm) {
			if (!UserUtil.getKMSSUser().isAdmin()) {
				IBaseAuthTmpForm baseAuthTmpForm = (IBaseAuthTmpForm) form;
				baseAuthTmpForm.setAuthEditorIds(UserUtil.getUser().getFdId());
				baseAuthTmpForm
						.setAuthEditorNames(UserUtil.getUser().getFdName());
			}
		}
        return hrRatifyTemplateForm;
    }

	public ActionForward listUsual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		Map jsonMap = new HashMap();
		try {

			// 最常用的流程
			if ("frequent".equals(request.getParameter("type"))) {
				jsonMap.put("title", ResourceUtil.getString(
						"lbpmperson.createDoc.frequent", "sys-lbpmperson"));
			}
			// 最近使用的流程
			else {
				jsonMap.put("title", ResourceUtil.getString(
						"lbpmperson.createDoc.recent", "sys-lbpmperson"));
			}
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "hrRatifyTemplate.fdTempKey=:fdTempKey";
			hqlInfo.setWhereBlock(whereBlock);
			String fdTempKey = request.getParameter("fdTempKey");
			hqlInfo.setParameter("fdTempKey", fdTempKey);
			hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_READER);
			List<HrRatifyTemplate> templates = getServiceImp(request)
					.findList(hqlInfo);
			List<Map<String, Object>> listRecent = new ArrayList<Map<String, Object>>();
			for (HrRatifyTemplate template : templates) {
				// 业务模板ID
				String templateId = template.getFdId();
				// 主文档model名称
				String modelName = null;

				if (StringUtil
						.isNotNull(request.getParameter("mainModelName"))) {
					modelName = request.getParameter("mainModelName");
				}
				Map<String, Object> m1 = new HashMap<String, Object>();
				// m1.put("countNumber", obj[0]);
				m1.put("fdTemplateId", templateId);
				m1.put("fdModelName", modelName);

				SysDictModel dict = SysDataDict.getInstance()
						.getModel(modelName);
				if (dict != null && StringUtil.isNotNull(templateId)
						&& StringUtil.isNotNull(dict.getUrl())) {
					SysCfgFlowDef config = SysConfigs.getInstance()
							.getFlowDefByMain(modelName);

					String templateDesc = "";
					if (StringUtil
							.isNull(request.getParameter("mainModelName"))) {
						if (StringUtil.isNull(config.getModuleMessageKey())) {
							continue;
						}
						templateDesc = ResourceUtil
								.getString(config.getModuleMessageKey());

						if (StringUtil.isNull(templateDesc)) {
							templateDesc = config.getModuleMessageKey();
						}
						if (StringUtil.isNotNull(templateDesc)) {
							templateDesc += " >> ";
						}
					}

					String templateName = config.getTemplateModelName();
					IBaseModel templateModel = this.getServiceImp(request)
							.getBaseDao().findByPrimaryKey(templateId,
									templateName, false);
					if (templateModel == null) {
						continue;
					}

					if (templateModel instanceof IBaseTemplateModel) {
						// 获取模板对应分类信息
						IBaseTemplateModel baseTemplateModel = (IBaseTemplateModel) templateModel;
						SysCategoryMain categoryMain = baseTemplateModel
								.getDocCategory();
						if (categoryMain != null) {
							m1.put("cateName", categoryMain.getFdName());
						}

					} else if (templateModel instanceof ISysSimpleCategoryModel) {
						// 获取模板父分类信息
						ISysSimpleCategoryModel baseTemplateModel = (ISysSimpleCategoryModel) templateModel;
						if (baseTemplateModel != null) {
							IBaseModel fdParent = baseTemplateModel
									.getFdParent();
							if (fdParent != null) {
								Object templateModelNameObj = null;
								try {
									templateModelNameObj = PropertyUtils
											.getProperty(fdParent, "fdName");
								} catch (Exception e) {
									templateModelNameObj = PropertyUtils
											.getProperty(fdParent,
													"docSubject");
								}

								if (templateModelNameObj == null) {
									continue;
								} else {
									m1.put("cateName",
											templateModelNameObj.toString());
								}
							}
						}
					}

					// 获取各个模板中显示的字段名
					String dispField = dict.getDisplayProperty();
					try {
						HibernateUtil
								.getColumnName(ClassUtils.forName(templateName),
										dispField);
					} catch (Exception e) {
						try {
							dispField = "fdName";
							HibernateUtil
									.getColumnName(ClassUtils.forName(templateName),
											dispField);
						} catch (Exception e2) {
							try {
								dispField = "docSubject";
								HibernateUtil.getColumnName(
										ClassUtils.forName(templateName), dispField);
							} catch (Exception e3) {
								continue;
							}
						}
					}

					Object templateModelNameObj = null;
					try {
						templateModelNameObj = PropertyUtils
								.getProperty(templateModel, dispField);
					} catch (Exception e) {
						// continue;
					}

					if (templateModelNameObj == null) {
						continue;
					}
					templateDesc += templateModelNameObj.toString();
					m1.put("templateDesc", templateDesc);

					String addUrl = getAddURL(templateId, dict);
					m1.put("addUrl", addUrl);

					listRecent.add(m1);
				}

			}

			jsonMap.put("list", listRecent);
			// 最近使用的
			request.setAttribute("lui-source",
					net.sf.json.JSONObject.fromObject(jsonMap).toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}

	private String getAddURL(String templateId, SysDictModel dict) {
		String url = dict.getUrl();
		String addUrl = url.substring(0, url.indexOf(".do"))
				+ ".do?method=add&i.docTemplate=" + templateId;
		if (addUrl.startsWith("/")) {
			addUrl = addUrl.substring(1);
		}
		return addUrl;
	}

	private boolean isEditor(HttpServletRequest request) throws Exception {
		ValidatorRequestContext validatorContext = new ValidatorRequestContext(
				request, "");
		validatorContext.setValidatorPara("cateid", "parentId");
		IAuthenticationValidator authCategoryEditorValidator = (IAuthenticationValidator) SpringBeanUtil
				.getBean("authCategoryEditorValidator");
		return authCategoryEditorValidator.validate(validatorContext);
	}

	/**
	 * 查询列表JSON页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回data页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward data(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0
					&& Integer.parseInt(s_pageno) > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0
					&& Integer.parseInt(s_rowsize) > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
				orderby += " desc";
			}
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			if (isEditor(request)) {
				hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
			}
			changeFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("data", mapping, form, request, response);
		}
	}
}
