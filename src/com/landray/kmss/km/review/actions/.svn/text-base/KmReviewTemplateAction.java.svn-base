package com.landray.kmss.km.review.actions;

import java.io.File;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.category.service.spring.SysCategoryMainServiceImp;
import com.landray.kmss.sys.config.util.LicenseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.actions.TemplateNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.km.review.forms.KmReviewTemplateForm;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.news.forms.SysNewsTemplateForm;
import com.landray.kmss.sys.number.forms.SysNumberMainMappForm;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.number.service.ISysNumberMainMappService;
import com.landray.kmss.sys.profile.model.ShowConfig;
import com.landray.kmss.sys.xform.XFormConstant;
import com.landray.kmss.sys.xform.base.model.AbstractFormTemplate;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.sys.xform.base.service.SysFormSourcePluginUtil;
import com.landray.kmss.sys.xform.base.service.spring.SysFormModeDataSource;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.version.VersionXMLUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建日期 2007-Aug-30
 *
 * @author 舒斌
 */
public class KmReviewTemplateAction extends TemplateNodeAction

{
	protected IKmReviewTemplateService kmReviewTemplateService;

	private ISysCategoryMainService categoryMainService;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) getBean("kmReviewTemplateService");
        }
		return kmReviewTemplateService;
	}
	protected ISysAttMainCoreInnerService sysAttMainService;

	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	/**
	 * 系统分类
	 */
	private ISysCategoryMainService sysCategoryMainService;
	public ISysCategoryMainService getSysCategoryMainService() {
		if(sysCategoryMainService ==null){
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}
	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	protected ISysNumberMainMappService sysNumberMainMappService;

	protected ISysNumberMainMappService getSysNumberMainMappImp() {
		if (sysNumberMainMappService == null) {
            sysNumberMainMappService = (ISysNumberMainMappService) getBean("sysNumberMainMappService");
        }
		return sysNumberMainMappService;
	}

	private ISysFormTemplateService sysFormTemplateService;

	protected ISysFormTemplateService getSysFormTemplateService() {
		if (sysFormTemplateService == null) {
            sysFormTemplateService = (ISysFormTemplateService) getBean(
                    "sysFormTemplateService");
        }
		return sysFormTemplateService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		hqlInfo.setWhereBlock(where);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmReviewTemplate.class);
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		KmReviewTemplateForm templateForm = (KmReviewTemplateForm) form;
		templateForm.setFdLableVisiable("true");
		templateForm.setDocCreatorName(UserUtil.getUser().getFdName());
		templateForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		templateForm.setFdIcon("/km/review/img/icon_office.png");
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}

		String docCategoryId = request.getParameter("parentId");
		if (StringUtil.isNotNull(docCategoryId)) {
			SysCategoryMain sysCategoryMain = (SysCategoryMain) getTreeServiceImp(
					request).findByPrimaryKey(docCategoryId);
			if (UserUtil.checkAuthentication(
					"/km/review/km_review_template/kmReviewTemplate.do?method=save&fdCategoryId="
							+ docCategoryId, "post")) {
				templateForm.setFdCategoryId(docCategoryId);
				templateForm.setFdCategoryName(sysCategoryMain.getFdName());
			} else {
				request.setAttribute("noAccessCategory", sysCategoryMain
						.getFdName());
			}
		}
		return templateForm;
	}


	public void setAttForm(KmReviewTemplateForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId("");
		attForm.setFdModelName("com.landray.kmss.km.review.model.KmReviewTemplate");
		attForm.setFdKey(settingKey);
		if (!attForm.getAttachments().contains(sysAttMain)) {
			attForm.getAttachments().add(sysAttMain);
		}
		String attids = attForm.getAttachmentIds();
		if (StringUtil.isNull(attids)) {
			attForm.setAttachmentIds(sysAttMain.getFdId());
		} else {
			attForm.setAttachmentIds(sysAttMain.getFdId() + ";" + attids);
		}
		attForms = att.getAttachmentForms();
		Map kmReviewAttForms = new HashMap();
		kmReviewAttForms.put(settingKey, attForms.get(settingKey));

		templateForm.getAttachmentForms().putAll(kmReviewAttForms);
	}



	/**
	 * 根据http请求，获取model，将model转化为form并返回。<br>
	 * URL中必须包含fdId参数，该参数为记录id。<br>
	 * 若获取model不成功，则抛出errors.norecord的错误信息。
	 *
	 * @param form
	 * @param request
	 * @return form对象
	 * @throws Exception
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		KmReviewTemplateForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			KmReviewTemplate model = (KmReviewTemplate) getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			if (model != null) {
				rtnForm = (KmReviewTemplateForm) getServiceImp(request)
						.convertModelToForm((IExtendForm) form, model,
								new RequestContext(request));
				UserOperHelper.logFind(model);
				rtnForm.setFdCategoryName(getFdCategoryName(model
						.getDocCategory()));
			}
		}
		if (rtnForm == null) {
            throw new NoRecordException();
        }
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	private String getFdCategoryName(SysCategoryMain sysCategoryMain) {
		String fdCategoryName = sysCategoryMain.getFdName();
		SysCategoryMain fdParent = (SysCategoryMain) sysCategoryMain
				.getFdParent();
		if (fdParent != null) {
			do {
				fdCategoryName = fdParent.getFdName() + "/" + fdCategoryName;
				fdParent = (SysCategoryMain) fdParent.getFdParent();
			} while (fdParent != null);
		}
		return fdCategoryName;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

			// 处理钉钉的模板删除
			deleteDingTemplate(id);

		} catch (Exception e) {
			KmReviewTemplate template = (KmReviewTemplate) getServiceImp(
					request).findByPrimaryKey(request.getParameter("fdId"));
			messages.setHasError();
			KmssMessage message = new KmssMessage(
					"km-review:kmReviewTemplate.delete.tip", template
					.getFdName());
			message.setMessageType(KmssMessage.MESSAGE_ERROR);
			messages.addError(message, e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	@Override
	public ActionForward deleteall(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		boolean hasExcepteion = false;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, "method=delete&fdId=${id}");
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
                    getServiceImp(request).delete(authIds);
                }
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}
		} catch (Exception e) {
			hasExcepteion = true;
			messages.setHasError();
			KmssMessage message = new KmssMessage("km-review:kmReviewTemplate.deleteall.tip");
			message.setMessageType(KmssMessage.MESSAGE_ERROR);
			messages.addError(message, e);
		}

		if (!hasExcepteion) {
			deleteDingTemplates(request.getParameterValues("List_Selected"));
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return mapping.findForward("failure");
        } else {
            return mapping.findForward("success");
        }
	}

	public void deleteDingTemplates(String[] templateIds) {
		try {
			for (String id : templateIds) {
				deleteDingTemplate(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteDingTemplate(String templateId) {
		try {
			if (new File(
					PluginConfigLocationsUtil.getKmssConfigPath()
							+ "/third/ding/").exists()) {
				Object dingUtil = Class.forName(
						"com.landray.kmss.third.ding.util.DingUtil")
						.newInstance();
				Class dingUtilClazz = dingUtil.getClass();
				Method dealWithDingTemplate = dingUtilClazz.getMethod(
						"dealWithDingTemplate",
						String.class, String.class, String.class, String.class,
						String.class, List.class, String.class);
				dealWithDingTemplate.invoke(dingUtil, "delete", null,
						templateId, null, null, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		System.out.println(request.getParameter("sysRuleTemplateForm.fdId"));
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).update((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			logger.error("update error ",e);
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			if ("true".equals(SysFormDingUtil.getEnableDing())) {
				return getActionForward("successDing", mapping, form, request,
						response);
			}
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_RETURN).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String id = null;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }

			//外部流程模板
			if("true".equals(((KmReviewTemplateForm)form).getFdIsExternal())){
				((KmReviewTemplateForm)form).getSysNumberMainMappForm().setFdMainModelName(null);
			}
			addAttTransQueue(form);
			id = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
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
					.addButton(
							"button.back",
							"/km/review/km_review_template/kmReviewTemplate.do?method=edit&fdId="
									+ id, false).addButton(
					KmssReturnPage.BUTTON_CLOSE).save(request);
			if ("true".equals(SysFormDingUtil.getEnableDing())) {
				return getActionForward("successDing", mapping, form, request,
						response);
			}
			return getActionForward("success", mapping, form, request,response);
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

			//外部流程模板
			if("true".equals(((KmReviewTemplateForm)form).getFdIsExternal())){
				((KmReviewTemplateForm)form).getSysNumberMainMappForm().setFdMainModelName(null);
			}
			addAttTransQueue(form);
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-saveadd", false, getClass());
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return getActionForward("edit", mapping, form, request, response);
        } else{
			KmReviewTemplateForm newForm = (KmReviewTemplateForm)form;
			newForm.getSysNumberMainMappForm().setFdNumberId("");
			return add(mapping, newForm, request, response);
		}
	}

	/**
	*实现功能描述:新建模板加入附件转换队列
	 * （当后台预览加载编辑设置选择了WPS加载项及表单引用方式选择word模式时才加到附件转换队列）
	*@param [form]
	*@return void
	*/
	public void addAttTransQueue(ActionForm form) throws Exception {
		if(form instanceof KmReviewTemplateForm){
			KmReviewTemplateForm templateForm = (KmReviewTemplateForm)form;
			//WPS加载项使用
			if(SysAttWpsoaassistUtil.isEnable() && "true".equals(templateForm.getFdUseWord())) {
				Date currTime = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String date = sdf.format(currTime);
				String docuName = "流程管理模板" + date;
				SysAttMain sam = new SysAttMain();
				sam.setFdModelId(templateForm.getFdId());
				sam.setFdModelName("com.landray.kmss.km.review.model.KmReviewTemplate");
				sam.setFdKey("mainContent");
				sam.setFdFileName(docuName);
				SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
				setAttForm(templateForm,attMainFile,"mainContent");
			}
		}
	}

	public ActionForward clone(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-clone", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			String cloneModelId = request.getParameter("cloneModelId");
			KmReviewTemplateForm newForm = (KmReviewTemplateForm) createNewForm(
					mapping, form, request, response);
			IBaseModel cloneModel = getServiceImp(request).findByPrimaryKey(
					cloneModelId);
			UserOperHelper.logFind(cloneModel);
			RequestContext requestContext = new RequestContext(request);
			newForm = (KmReviewTemplateForm) getServiceImp(request)
					.cloneModelToForm(newForm, cloneModel, requestContext);

			List sysFormTemplateList = getSysFormTemplateService()
					.getCoreModels(cloneModel, null);
			if (!sysFormTemplateList.isEmpty()) {
				SysFormTemplate formTempl = (SysFormTemplate) sysFormTemplateList
						.get(0);
				request.setAttribute("_xformCloneTemplateId",
						formTempl.getFdId());
			}
			request.setAttribute("isCloneAction", "true");

			// 获取模板对应编号机制的相关信息
			SysNumberMainMapp mapp = (SysNumberMainMapp) getSysNumberMainMappImp()
					.getSysNumberMainMapp(
							"com.landray.kmss.km.review.model.KmReviewMain",
							cloneModelId);
			if (mapp != null) {
				SysNumberMainMappForm mappForm = newForm
						.getSysNumberMainMappForm();
				if (mappForm != null) {
					mappForm.setFdType(mapp.getFdType());
					mappForm.setFdContent(mapp.getFdContent());
					mappForm.setFdFlowContent(mapp.getFdFlowContent());
					mappForm.setFdNumberId(mapp.getFdNumber() == null ? ""
							: mapp.getFdNumber().getFdId());
				}
			}
			if (newForm != form) {
                request.setAttribute(getFormName(newForm, request), newForm);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-clone", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	/**
	 * 批量复制模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchClone(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TimeCounter.logCurrentTime("Action-clone", true, getClass());
		KmssMessages messages = new KmssMessages();

		try {
			String cloneModelId1 = request.getParameter("cloneModelIds");
			String[] cloneModelIds=new String[0];

			if(StringUtil.isNotNull(cloneModelId1)) {
				cloneModelIds=cloneModelId1.split(",");
			}

			if(cloneModelIds.length>0) {

				KmReviewTemplateForm oldForm=new KmReviewTemplateForm();
				oldForm.setFdCategoryId(((KmReviewTemplateForm)form).getFdCategoryId());
				oldForm.setFdCategoryName(((KmReviewTemplateForm)form).getFdCategoryName());
				oldForm.setAuthEditorIds(((KmReviewTemplateForm)form).getAuthEditorIds());
				oldForm.setAuthEditorNames(((KmReviewTemplateForm)form).getAuthEditorNames());
				oldForm.setAuthReaderIds(((KmReviewTemplateForm)form).getAuthReaderIds());
				oldForm.setAuthReaderNames(((KmReviewTemplateForm)form).getAuthReaderNames());

				for(int i=0;i<cloneModelIds.length;i++) {

					String cloneModelId = cloneModelIds[i];
					KmReviewTemplateForm newForm = (KmReviewTemplateForm) createNewForm(
							mapping, form, request, response);


					IBaseModel cloneModel = getServiceImp(request).findByPrimaryKey(
							cloneModelId);
					UserOperHelper.logFind(cloneModel);
					RequestContext requestContext = new RequestContext(request);
					newForm = (KmReviewTemplateForm) ((IKmReviewTemplateService)getServiceImp(request)).cloneModelToFormNoName(newForm, cloneModel, requestContext);


					newForm.setFdCategoryId(oldForm.getFdCategoryId());
					newForm.setFdCategoryName(oldForm.getFdCategoryName());

					String editType=request.getParameter("editType");
					String useType=request.getParameter("useType");

					if("2".equals(editType)) {
						newForm.setAuthEditorIds(oldForm.getAuthEditorIds());
						newForm.setAuthEditorNames(oldForm.getAuthEditorNames());
					}

					//置空可维护者
					if("3".equals(editType)) {
						newForm.setAuthEditorIds(null);
						newForm.setAuthEditorNames(null);
					}


					if("2".equals(useType)) {
						newForm.setAuthReaderIds(oldForm.getAuthReaderIds());
						newForm.setAuthReaderNames(oldForm.getAuthReaderNames());
					}

					//置空可使用者
					if("3".equals(useType)) {
						newForm.setAuthReaderIds(null);
						newForm.setAuthReaderNames(null);
					}


					// 获取模板对应编号机制的相关信息
					SysNumberMainMapp mapp = (SysNumberMainMapp) getSysNumberMainMappImp()
							.getSysNumberMainMapp(
									"com.landray.kmss.km.review.model.KmReviewMain",
									cloneModelId);
					if (mapp != null) {
						SysNumberMainMappForm mappForm = newForm
								.getSysNumberMainMappForm();
						if (mappForm != null) {
							mappForm.setFdType(mapp.getFdType());
							mappForm.setFdContent(mapp.getFdContent());
							mappForm.setFdFlowContent(mapp.getFdFlowContent());
							mappForm.setFdNumberId(mapp.getFdNumber() == null ? ""
									: mapp.getFdNumber().getFdId());
						}
					}


					//外部流程模板
					if("true".equals(((KmReviewTemplateForm)newForm).getFdIsExternal())){
						((KmReviewTemplateForm)newForm).getSysNumberMainMappForm().setFdMainModelName(null);
					}

					newForm.setDocCreateTime(null);
					newForm.setDocCreatorId(UserUtil.getUser().getFdId());
					newForm.setDocCreatorName(UserUtil.getUser().getFdName());

					getServiceImp(request).add((IExtendForm) newForm,
							new RequestContext(request));
				}
			}
		} catch (Exception e) {
			messages.setHasError();
			KmssMessage message = new KmssMessage(
					"km-review:kmReviewTemplate.message.batchCopyTemplate.failure");
			message.setMessageType(KmssMessage.MESSAGE_ERROR);
			messages.addError(message, e);
		}

		TimeCounter.logCurrentTime("Action-clone", false, getClass());

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}

	}


	@Override
	protected String getFindPageOrderBy(HttpServletRequest request, String curOrderBy) throws Exception {
		if (curOrderBy == null) {
			curOrderBy = "kmReviewTemplate.fdOrder,kmReviewTemplate.fdId";
		}
		return curOrderBy;
	}

	/**
	 * 移动模板首页
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
							   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getActionForward("index.4m", mapping, form, request, response);
	}

	/**
	 * 移动模板列表
	 */
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request,
							  HttpServletResponse response) throws Exception {
		String forwardStr = request.getParameter("forwardStr");
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String s_pagingType = request.getParameter("pagingtype");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String pagingSetting = request.getParameter("pagingSetting");
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
			changeFindPageHQLInfo(request, hqlInfo);
			if ("simple".equals(s_pagingType)
					|| ShowConfig.PAGING_SETTING_SIMPLE.equals(pagingSetting)) {
				hqlInfo.setPagingType(HQLInfo.PAGING_TYPE_SIMPLE);
			}
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
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
			if (StringUtil.isNotNull(forwardStr)) {
				return getActionForward(forwardStr, mapping, form, request,
						response);
			} else {
				return getActionForward("list", mapping, form, request,
						response);
			}
		}
	}

	public ActionForward addOld(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			ActionForm newForm = createNewForm(mapping, form, request,
					response);
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
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	private String getVersion() {
		String webContentPath = ConfigLocationsUtil.getWebContentPath();
		String file = webContentPath
				+ "/WEB-INF/KmssConfig/version/description.xml";
		String version = VersionXMLUtil.getInstance(file).getDescriprion()
				.getModule().getBaseline();
		StringBuffer buf = new StringBuffer();
		if (version != null) {
			int count = 0;
			for (int i = 0; i < version.length(); i++) {
				if (version.charAt(i) == '.') {
					count++;
					if (count > 1) {
                        break;
                    }
				}
				buf.append(version.charAt(i));
			}
		}
		return buf.toString();
	}

	/**
	 * 通过外部模板新建模板
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings({ "rawtypes"})
	public ActionForward add(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String sourceFrom = request.getParameter("sourceFrom");
		if (StringUtil.isNull(sourceFrom)) {
			return addOld(mapping, form, request, response);
		}
		TimeCounter.logCurrentTime("Action-addByMobile", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdKey = request.getParameter("sourceKey");
		KmReviewTemplateForm reviewTemplateForm = new KmReviewTemplateForm();
		createNewForm(mapping, reviewTemplateForm, request, response);
		reviewTemplateForm.setMethod_GET("add");
		reviewTemplateForm.setFdUseForm(String.valueOf(true));
		int clientType = MobileUtil.getClientType(request);
		if (MobileUtil.PC != clientType) {
			reviewTemplateForm.setFdIsMobileCreate(Boolean.TRUE.toString());
			String previewUrl = request.getParameter("previewUrl");
			if (StringUtil.isNotNull(previewUrl)) {
				previewUrl = URLDecoder.decode(previewUrl, "UTF-8");
				request.setAttribute("previewUrl", previewUrl);
			}
		}
		try {
			Map<String, Object> extension = SysFormSourcePluginUtil
					.getTemplateSourceExtensionByKey(
							fdKey);
			IXMLDataBean sourceBean = (IXMLDataBean) extension
					.get("sourceBean");
			RequestContext context = new RequestContext(request);
			context.setParameter("fdModelName",
					KmReviewTemplate.class.getName());
			List rtnData = sourceBean
					.getDataList(context);
			if (!ArrayUtil.isEmpty(rtnData)) {
				HashMap<String, Object> modelMap = new HashMap<String, Object>();
				for (Object obj : rtnData) {
					String key = ModelUtil.getModelClassName(obj);
					if (obj instanceof AbstractFormTemplate) {
						key = AbstractFormTemplate.class.getName();
					}
					modelMap.put(key, obj);
					if (obj instanceof KmReviewTemplate) {
						initFormSetting(reviewTemplateForm,
								"reviewMainDoc",
								(IBaseModel) obj, "reviewMainDoc",
								new RequestContext(request));
					}
				}
				request.setAttribute("_init_template_setting", modelMap);
			}
			getDispatchCoreService().initFormSetting(reviewTemplateForm,
					"reviewMainDoc",
					null, "reviewMainDoc", new RequestContext(request));
			request.setAttribute(getFormName(reviewTemplateForm, request),
					reviewTemplateForm);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-addByMobile", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			return getActionForward("edit", mapping, form, request,
					response);
		}
	}

	/**
	 * 通过外部模板新建模板
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes"})
	public ActionForward addBatch(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject json = new JSONObject();
		TimeCounter.logCurrentTime("Action-addBatch", true, getClass());
		KmssMessages messages = new KmssMessages();
		// 判断是否授权
		String mallUtilName = "com.landray.kmss.third.mall.util.MallUtil";
		if (ModelUtil.isExisted(mallUtilName)) {
			Class mallUtilClaa = com.landray.kmss.util.ClassUtils.forName(mallUtilName);
			Method enableMallMeth = mallUtilClaa
					.getDeclaredMethod("enableMall");
			boolean isAuth = (boolean) enableMallMeth.invoke(null);
			if (isAuth) {
				String fdKey = request.getParameter("sourceKey");
				try {
					Map<String, Object> extension = SysFormSourcePluginUtil.getTemplateSourceExtensionByKey(fdKey);
					IXMLDataBean sourceBean = (IXMLDataBean) extension.get("sourceBean");
					RequestContext context = new RequestContext(request);
					context.setParameter("fdModelName", KmReviewTemplate.class.getName());
					List rtnData = sourceBean.getDataList(context);
					if (!ArrayUtil.isEmpty(rtnData)) {
						json = (JSONObject) rtnData.get(0);
					} else {
						json.put("status", "error");
						json.put("msg", "operateNull");
					}
				} catch (Exception e) {
					json.put("status", "error");
					json.put("msg", "系统错误");
					messages.addError(e);
				}
			} else {
				json.put("errorCount","third-mall:thirdMall.noAuthUseThisTemplate");
			}
			String formSource = request.getParameter("formSource");
			if ("xfromSet".equals(formSource) || "sysXformGroup".equals(formSource)) {
				//来自云商城，返回成功失败页面
				TimeCounter.logCurrentTime("Action-addByMobile", false, getClass());
				request.setAttribute("resultData", json);
				if (messages.hasError() || !"-1".equals(json.get("status").toString())) {
					//错误数量
					if (json.has("errorCount")) {
						messages.addError(new KmssMessage(json.get("errorCount").toString()));
					}
					KmssReturnPage.getInstance(request).addMessages(messages)
							.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
					return getActionForward("failure", mapping, form, request,
							response);
				} else {
					KmssReturnPage.getInstance(request).addMessages(messages)
							.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
					return getActionForward("successMall", mapping, form, request, response);
				}
			} else {
				TimeCounter.logCurrentTime("Action-addBatch", false, getClass());
				response.setCharacterEncoding("UTF-8");
				response.getWriter().append(json.toString());
				response.getWriter().flush();
				response.getWriter().close();
				return null;
			}
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("failure", mapping, form, request,
				response);
	}
	public void initFormSetting(IExtendForm mainForm, String mainKey,
								IBaseModel settingModel, String settingKey,
								RequestContext requestContext) throws Exception {
		if (mainForm instanceof KmReviewTemplateForm &&
				settingModel instanceof KmReviewTemplate) {
			KmReviewTemplateForm reviewTemplateForm = (KmReviewTemplateForm) mainForm;
			KmReviewTemplate reviewTemplate = (KmReviewTemplate) settingModel;
			reviewTemplateForm
					.setFdName(reviewTemplate.getFdName());
			Boolean fdUseForm = reviewTemplate.getFdUseForm();
			if (fdUseForm == null) {
				fdUseForm = false;
			}
			reviewTemplateForm.setFdUseForm(String.valueOf(fdUseForm));
			if(reviewTemplate.getDocCategory() !=null) {
				reviewTemplateForm.setFdCategoryId(reviewTemplate.getDocCategory().getFdId());
				reviewTemplateForm.setFdCategoryName(reviewTemplate.getDocCategory().getFdName());
			}
		}
	}

	public boolean isReachable(String httpURL, int timeOutMillSeconds) {
		URL urlObj = null;
		HttpURLConnection oc = null;
		try {
			urlObj = new URL(httpURL);
			oc = (HttpURLConnection) urlObj.openConnection();
			oc.setUseCaches(false);
			oc.setConnectTimeout(timeOutMillSeconds); // 设置超时时间
			oc.connect();
			return true;
		} catch (Exception e) {
			if (urlObj != null) {
				urlObj = null;
			}
			if (oc != null) {
				oc = null;
			}
			return false;
		}
	}

	/**
	 * 打开模板选择页面
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward openTemplate(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-openTemplate", true, getClass());
		KmssMessages messages = new KmssMessages();
		// 获取模式
		SysFormModeDataSource sysFormModeDataSource = new SysFormModeDataSource();
		sysFormModeDataSource.setRequest(request);
		Map<String, String> options = sysFormModeDataSource.getOptions();
		String addOptionTypes = request.getParameter("addOptionType");
		if (StringUtil.isNotNull(addOptionTypes)) {
			addOptionTypes = URLDecoder.decode(addOptionTypes, "UTF-8");
			String[] optionTypeArr = addOptionTypes.split(";");
			for (int i = 0; i < optionTypeArr.length; i++) {
				String optionType = optionTypeArr[i];
				String[] option = optionType.split("\\|");
				String text = ResourceUtil.getString(option[0]);
				String val = option[1];
				options.put(val, text);
			}
		}
		Iterator<String> iterator = options.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String skipVal = String.valueOf(XFormConstant.TEMPLATE_OTHER);
			if (skipVal.equals(key)) {
				iterator.remove();
			}
		}
		String parentId = request.getParameter("parentId");
		request.setAttribute("parentId", parentId);
		JSONObject modeOption = JSONObject.fromObject(options);
		request.setAttribute("mode", modeOption.toString());

		// 测试有没有网络
		boolean reachable = isReachable("http://www.baidu.com/", 1000);
		request.setAttribute("netWork_reachable", reachable);

		// 项目根路径
		String absPath = ResourceUtil
				.getKmssConfigString("kmss.urlPrefix");
		if (!absPath.endsWith("/")) {
			absPath = absPath + "/";
		}
		request.setAttribute("__absPath", absPath);

		// 获取版本号
		String version = getVersion();
		request.setAttribute("version", version);

		// 判断是否授权
		String mallUtilName = "com.landray.kmss.third.mall.util.MallUtil";
		if (ModelUtil.isExisted(mallUtilName)) {
			Class mallUtilClaa = com.landray.kmss.util.ClassUtils.forName(mallUtilName);
			Method enableMallMeth = mallUtilClaa
					.getDeclaredMethod("enableMall");
			boolean isAuth = (boolean) enableMallMeth.invoke(null);
			request.setAttribute("isAuth", isAuth);
		}

		// 获取tab扩展点
		List<Map<String, String>> extensions = SysFormSourcePluginUtil
				.getTemplateSourceExtension(
						KmReviewTemplate.class.getName());
		JSONArray extArray = JSONArray.fromObject(extensions);
		request.setAttribute("extArr", extArray);
		request.setAttribute("extensions", extensions);
		TimeCounter.logCurrentTime("Action-openTemplate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("index", mapping, form, request, response);
		} else {
			return getActionForward("select", mapping, form, request,
					response);
		}
	}

	/**
	 * 打开云商城模板选择页面
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ActionForward openMallTemplate(ActionMapping mapping, ActionForm form,
										  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONObject jsonObject=new JSONObject();
		TimeCounter.logCurrentTime("Action-openTemplate", true, getClass());
		KmssMessages messages = new KmssMessages();


		// 项目根路径
		String absPath = ResourceUtil
				.getKmssConfigString("kmss.urlPrefix");
		if (!absPath.endsWith("/")) {
			absPath = absPath + "/";
		}
		jsonObject.accumulate("__absPath", absPath);
		// 获取版本号
		String version = getVersion();
		jsonObject.accumulate("version", version);
		// 判断是否授权
		String mallUtilName = "com.landray.kmss.third.mall.util.MallUtil";
		if (ModelUtil.isExisted(mallUtilName)) {
			Class mallUtilClaa = com.landray.kmss.util.ClassUtils.forName(mallUtilName);
			Method enableMallMeth = mallUtilClaa
					.getDeclaredMethod("enableMall");
			boolean isAuth = (boolean) enableMallMeth.invoke(null);
			jsonObject.accumulate("isAuth", isAuth);
		}
		//获取云商城的URL
		List<Map<String, String>> extensions = SysFormSourcePluginUtil
				.getTemplateSourceExtension(
						KmReviewTemplate.class.getName());
		if(CollectionUtils.isNotEmpty(extensions)){
			//第一个扩展点是云商城
			Map<String,String> firstTab =extensions.get(0);
			String mallUrl =firstTab.get("moreURL");
			jsonObject.accumulate("moreURL", mallUrl);
			// 测试有没有网络
			boolean reachable = isReachable(mallUrl, 1000);
			jsonObject.accumulate("netWork_reachable", reachable);

			String name = LicenseUtil.get("license-product-name");
			jsonObject.accumulate("productName", StringUtil.getString(name));
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(jsonObject.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	/**
	 * 来自钉钉的模板选择
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFromDing(ActionMapping mapping, ActionForm form,
									 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-openTemplate", true, getClass());
		KmssMessages messages = new KmssMessages();
		request.setAttribute("fdModelName", "com.landray.kmss.km.review.model.KmReviewTemplate");
		request.setAttribute("fdKey", "reviewMainDoc");
		request.setAttribute("fdMainModelName", "com.landray.kmss.km.review.model.KmReviewMain");
		request.setAttribute("addOptionType", "km-review:kmReviewDocumentLableName.wordStype|5");
		// 获取模式
		SysFormModeDataSource sysFormModeDataSource = new SysFormModeDataSource();
		sysFormModeDataSource.setRequest(request);
		Map<String, String> options = sysFormModeDataSource.getOptions();
		String addOptionTypes = "km-review:kmReviewDocumentLableName.wordStype|5";
		if (StringUtil.isNotNull(addOptionTypes)) {
			addOptionTypes = URLDecoder.decode(addOptionTypes, "UTF-8");
			String[] optionTypeArr = addOptionTypes.split(";");
			for (int i = 0; i < optionTypeArr.length; i++) {
				String optionType = optionTypeArr[i];
				String[] option = optionType.split("\\|");
				String text = ResourceUtil.getString(option[0]);
				String val = option[1];
				options.put(val, text);
			}
		}
		Iterator<String> iterator = options.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String skipVal = String.valueOf(XFormConstant.TEMPLATE_OTHER);
			if (skipVal.equals(key)) {
				iterator.remove();
			}
		}
		String parentId = request.getParameter("parentId");
		request.setAttribute("parentId", parentId);
		JSONObject modeOption = JSONObject.fromObject(options);
		request.setAttribute("mode", modeOption.toString());

		// 测试有没有网络
		boolean reachable = isReachable("http://www.baidu.com/", 1000);
		request.setAttribute("netWork_reachable", reachable);

		// 项目根路径
		String absPath = ResourceUtil
				.getKmssConfigString("kmss.urlPrefix");
		if (!absPath.endsWith("/")) {
			absPath = absPath + "/";
		}
		request.setAttribute("__absPath", absPath);

		// 获取版本号
		String version = getVersion();
		request.setAttribute("version", version);

		// 判断是否授权
		String mallUtilName = "com.landray.kmss.third.mall.util.MallUtil";
		if (ModelUtil.isExisted(mallUtilName)) {
			Class mallUtilClaa = com.landray.kmss.util.ClassUtils.forName(mallUtilName);
			Method enableMallMeth = mallUtilClaa
					.getDeclaredMethod("enableMall");
			boolean isAuth = (boolean) enableMallMeth.invoke(null);
			request.setAttribute("isAuth", isAuth);
		}

		// 获取tab扩展点
		List<Map<String, String>> extensions = SysFormSourcePluginUtil
				.getTemplateSourceExtension(
						KmReviewTemplate.class.getName());
		JSONArray extArray = JSONArray.fromObject(extensions);
		request.setAttribute("extArr", extArray);
		request.setAttribute("extensions", extensions);
		TimeCounter.logCurrentTime("Action-openTemplate", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("index", mapping, form, request, response);
		} else {
			return getActionForward("selectDing", mapping, form, request,
					response);
		}
	}


	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable()) {
			KmReviewTemplateForm templateForm = (KmReviewTemplateForm) form;
			String id = request.getParameter("fdId");
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "流程管理模板" + date;
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(id);
			sam.setFdModelName("com.landray.kmss.km.review.model.KmReviewTemplate");
			sam.setFdKey("mainContent");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
			setAttForm(templateForm,attMainFile,docuName);
		}
		return super.edit(mapping, form, request, response);
	}


}
