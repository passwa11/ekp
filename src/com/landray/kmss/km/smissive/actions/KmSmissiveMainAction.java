package com.landray.kmss.km.smissive.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.actions.SimpleCategoryNodeAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.smissive.forms.KmSmissiveMainForm;
import com.landray.kmss.km.smissive.forms.KmSmissiveTemplateForm;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.model.KmSmissiveTemplate;
import com.landray.kmss.km.smissive.service.IKmSmissiveMainService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;
import com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.number.model.SysNumberMainMapp;
import com.landray.kmss.sys.number.service.ISysNumberMainMappService;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.sunbor.web.tag.Page;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn
 */
public class KmSmissiveMainAction extends SimpleCategoryNodeAction

{
	protected IKmSmissiveMainService kmSmissiveMainService;

	@Override
	protected IKmSmissiveMainService getServiceImp(HttpServletRequest request) {
		if (kmSmissiveMainService == null) {
            kmSmissiveMainService = (IKmSmissiveMainService) getBean("kmSmissiveMainService");
        }
		return kmSmissiveMainService;
	}

	// 获取类别
	protected IKmSmissiveTemplateService kmSmissiveTemplateService;

	protected IKmSmissiveTemplateService getTreeServiceImp() {
		if (kmSmissiveTemplateService == null) {
            kmSmissiveTemplateService = (IKmSmissiveTemplateService) getBean("kmSmissiveTemplateService");
        }
		return kmSmissiveTemplateService;
	}
	protected ISysNumberMainMappService sysNumberMainMappService;

	protected ISysNumberMainMappService getSysNumberMainMappImp() {
		if (sysNumberMainMappService == null) {
            sysNumberMainMappService = (ISysNumberMainMappService) getBean("sysNumberMainMappService");
        }
		return sysNumberMainMappService;
	}

	// 打印日志
	protected ISysPrintLogCoreService sysPrintLogCoreService;

	public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
            sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
                    "sysPrintLogCoreService");
        }
		return sysPrintLogCoreService;
	}

	protected ISysAttMainCoreInnerService sysAttMainService;
	
	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	@Override
	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getTreeServiceImp();
	}

	/**
	 * 注入com.landray.kmss.common.service.ICoreOuterService接口
	 */
	private ICoreOuterService dispatchCoreService;

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}


	@Override
	public ActionForward manageList(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return listChildrenBase(mapping, form, request, response, "manageList",
				SysAuthConstant.AUTH_CHECK_NONE);
	}
	private ActionForward listChildrenBase(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String forwordPage, String checkAuth)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			String parentId = request.getParameter("categoryId");
			String s_IsShowAll = request.getParameter("isShowAll");
			String excepteIds = request.getParameter("excepteIds");
			boolean isShowAll = true;
			if (StringUtil.isNotNull(s_IsShowAll)
					&& "false".equals(s_IsShowAll)) {
                isShowAll = false;
            }
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = SysConfigParameters.getRowSize();
			if (s_pageno != null && s_pageno.length() > 0) {
				pageno = Integer.parseInt(s_pageno);
			}
			if (s_rowsize != null && s_rowsize.length() > 0) {
				rowsize = Integer.parseInt(s_rowsize);
			}
			if (isReserve) {
                orderby += " desc";
            }
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setOrderBy(orderby);
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			if (checkAuth != null) {
                hqlInfo.setAuthCheckType(checkAuth);
            }
			changeFindPageHQLInfo(request, hqlInfo);
			// 插入搜索条件查询语句
			String whereBlock = hqlInfo.getWhereBlock();
			if (!StringUtil.isNull(parentId)) {
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(getServiceImp(
						request).getModelName());
				if (isShowAll) {
					IBaseTreeModel treeModel = (IBaseTreeModel) getSysSimpleCategoryService()
							.findByPrimaryKey(parentId);

					if (StringUtil.isNull(treeModel.getFdHierarchyId())) {
						whereBlock += tableName + "." + getParentProperty()
								+ ".fdId=:_treeFdId";
						hqlInfo.setParameter("_treeFdId", treeModel.getFdId());
					} else {
						// whereBlock += "substring(" + tableName + "."
						// + getParentProperty() + ".fdHierarchyId,1,"
						// + treeModel.getFdHierarchyId().length()
						// + ")= :treeHierarchyId";
						whereBlock += tableName + "." + getParentProperty()
								+ ".fdHierarchyId like :_treeHierarchyId";
						hqlInfo.setParameter("_treeHierarchyId", treeModel
								.getFdHierarchyId()
								+ "%");
					}
				} else {
					whereBlock += tableName + "." + getParentProperty()
							+ ".fdId=:_treeParentId";
					hqlInfo.setParameter("_treeParentId", parentId);
				}
				if (StringUtil.isNotNull(excepteIds)) {
					whereBlock += " and "
							+ HQLUtil.buildLogicIN(tableName + ".fdId not",
									ArrayUtil.convertArrayToList(excepteIds
											.split("\\s*[;,]\\s*")));
				}
				// if (("manageList").equals(forwordPage)) {
				// whereBlock += " and " + tableName
				// + ".docStatus <> :_treeDocStatus";
				// hqlInfo.setParameter("_treeDocStatus",
				// SysDocConstant.DOC_STATUS_DRAFT);
				// }
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			request.setAttribute("queryPage", page);
			
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return getActionForward(forwordPage, mapping, form, request,
					response);
		}
	}
	
	
	/**
	 * 打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-print", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String fdId = request.getParameter("fdId");
			KmSmissiveMain model = (KmSmissiveMain) getServiceImp(request)
					.findByPrimaryKey(fdId);
			// 记录打印日志
			getSysPrintLogCoreService().addPrintLog(model,
					new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-print", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("print", mapping, form, request, response);
		}
	}

	/**
	 * 修改附件的权限
	 */
	public ActionForward modifyAttachmentRight(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IKmSmissiveMainService) getServiceImp(request))
					.updateAttachmentRight(mainForm,
							new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("modifyAttRight", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 修改文件的权限
	 */
	public ActionForward modifyRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IKmSmissiveMainService) getServiceImp(request)).updateRight(
					mainForm, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("modifyRight", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 传阅文档
	 */
	public ActionForward circulate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IKmSmissiveMainService) getServiceImp(request)).addCirculation(
					mainForm, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("circulate", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 修改签发人
	 */
	public ActionForward modifyIssuer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IKmSmissiveMainService) getServiceImp(request)).updateIssuer(
					mainForm, new RequestContext(request));
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("modifyIssuer", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 显示传阅记录
	 */
	@SuppressWarnings("unchecked")
	public ActionForward listCirculation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			fdId = StringUtil.getString(fdId);

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("kmSmissiveCirculation");
			hqlInfo.setFromBlock("KmSmissiveCirculation kmSmissiveCirculation");
			StringBuilder sb = new StringBuilder();
			sb.append("kmSmissiveCirculation.fdSmissiveMain.fdId=:fdId");
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setWhereBlock(sb.toString());

			List list = getServiceImp(request).findValue(hqlInfo);
			request.setAttribute("circulationList", list);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-list", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("listCirculation", mapping, form, request,
					response);
		}
	}

	/**
	 * 根据类别模板来创建文档
	 */
	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) super.createNewForm(
				mapping, form, request, response);
		String templateId = request.getParameter("categoryId");// fdTemplateId，简单分类中都采用categoryId
		if (StringUtil.isNull(templateId)) {
			templateId = request.getParameter("fdTemplateId");
		}
		if (StringUtil.isNotNull(templateId)) {
			KmSmissiveTemplate smissiveTemplate = (KmSmissiveTemplate) getTreeServiceImp()
					.findByPrimaryKey(templateId);
			if (smissiveTemplate != null) {
				/*** 返回文档对应的编号规则Id，前端cookie缓存用开始 **/
				String fdNoId = "";
				SysNumberMainMapp sysNumberMainMapp = (SysNumberMainMapp) getSysNumberMainMappImp()
						.getSysNumberMainMapp(
								"com.landray.kmss.km.smissive.model.KmSmissiveMain",
								smissiveTemplate.getFdId());
				if (sysNumberMainMapp != null) {
					fdNoId = sysNumberMainMapp.getFdNumber().getFdId();
					request.setAttribute("fdNoId", fdNoId);
				}
				/*** 返回文档对应的编号规则Id，前端cookie缓存用结束 **/
				mainForm.setFdNeedContent(smissiveTemplate.getFdNeedContent());
				// 写类别信息
				mainForm.setFdTemplateId(templateId);
				mainForm.setFdTemplateName(SimpleCategoryUtil
						.getCategoryPathName(smissiveTemplate));

				// 写入辅助类别信息
				if (smissiveTemplate.getDocProperties() != null) {
					String[] properties = ArrayUtil.joinProperty(
							smissiveTemplate.getDocProperties(), "fdId:fdName",
							";");
					mainForm.setDocPropertyIds(properties[0]);
					mainForm.setDocPropertyNames(properties[1]);
				}

				mainForm.setFdTitle(smissiveTemplate.getFdTmpTitle());
				if (smissiveTemplate.getFdTmpFlowFlag()) {
					mainForm.setFdFlowFlag("true");
				}
				mainForm.setFdUrgency(smissiveTemplate.getFdTmpUrgency());
				mainForm.setFdSecret(smissiveTemplate.getFdTmpSecret());
				if (smissiveTemplate.getFdTmpMainDept() != null) {
					mainForm.setFdMainDeptId(smissiveTemplate
							.getFdTmpMainDept().getFdId());
					mainForm.setFdMainDeptName(smissiveTemplate
							.getFdTmpMainDept().getFdName());
				}

				if (smissiveTemplate.getFdTmpIssuer() != null) {
					mainForm.setFdIssuerId(smissiveTemplate.getFdTmpIssuer()
							.getFdId());
					mainForm.setFdIssuerName(smissiveTemplate.getFdTmpIssuer()
							.getFdName());
				}

				// 初始化机制
				getDispatchCoreService().initFormSetting(mainForm,
						"smissiveDoc", smissiveTemplate, "smissiveDoc",
						new RequestContext(request));

				// 将模板加入request中，用于从模板中获取在线编辑的正文和参考附件，目前只是在新建的时候可以看见参考附件
				KmSmissiveTemplateForm templateForm = new KmSmissiveTemplateForm();
				getTreeServiceImp().convertModelToForm(templateForm,
						smissiveTemplate, new RequestContext(request));

				mainForm.setFdSendDeptIds(templateForm.getFdTmpSendDeptIds());
				mainForm.setFdSendDeptNames(templateForm
						.getFdTmpSendDeptNames());

				mainForm.setFdCopyDeptIds(templateForm.getFdTmpCopyDeptIds());
				mainForm.setFdCopyDeptNames(templateForm
						.getFdTmpCopyDeptNames());
				Map<String, Map<String, String>> a1 = new HashMap<String, Map<String, String>>();
				a1 = mainForm.getSysWfBusinessForm()
						.getAllNodeExtAttributeInfo();
				Collection<Map<String, String>> aa = a1.values();
				request.setAttribute("aaa", aa.toArray()[0]);
				request.setAttribute("kmSmissiveTemplateForm", templateForm);

			}
			
			//WPS加载项使用
			if(SysAttWpsoaassistUtil.isEnable()) {		
				SysAttMain sam = new SysAttMain();
				sam.setFdModelId(templateId);
				sam.setFdModelName("com.landray.kmss.km.smissive.model.KmSmissiveTemplate");
	            sam.setFdKey("mainContent");
				sam.setFdFileName("mainContent");
				SysAttMain attMainFile = getSysAttMainService().setWpsOnlineFile(sam, mainForm.getFdId(),"com.landray.kmss.km.smissive.model.KmSmissiveMain");
				attMainFile.setFdKey("mainOnline");
				attMainFile.setFdFileName(attMainFile.getFdFileName());
				getSysAttMainService().add(attMainFile);
				setAttForm(mainForm,attMainFile,"mainOnline");

			}
		}
		//mainForm.setDocCreateTime(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE,request.getLocale())); 
		mainForm.setDocAuthorId(UserUtil.getUser().getFdId());
		mainForm.setDocAuthorName(UserUtil.getUser().getFdName());
		
		
		return mainForm;
	}

	public void setAttForm(KmSmissiveMainForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId(sysAttMain.getFdModelId());
		attForm.setFdModelName("com.landray.kmss.km.smissive.model.KmSmissiveTemplate");
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
		Map newAttForms = new HashMap();
		newAttForms.put(settingKey, attForms.get(settingKey));
		
		templateForm.getAttachmentForms().putAll(newAttForms);
	}
	
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdId = getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			
			//WPS加载项时执行
			if(SysAttWpsoaassistUtil.isEnable() && mainForm.getAttachmentForms() != null)
			{
				AutoHashMap autoHashMap = mainForm.getAttachmentForms();
				AttachmentDetailsForm attachmentDetailForm = (AttachmentDetailsForm) autoHashMap.get("mainOnline");
				if(attachmentDetailForm != null)
				{
					List<SysAttMain> atts = getSysAttMainService()
							.findList("sysAttMain.fdKey = 'mainOnline' and sysAttMain.fdModelId = '"+attachmentDetailForm.getFdModelId()+"' "
									+ "and sysAttMain.fdFileName = 'editonline.docx'", "");
					if (!atts.isEmpty()) 
					{
						SysAttMain att = atts.get(0);
						String fileName = att.getFdFileName();
						String suffix =fileName.substring(fileName.lastIndexOf("."), fileName.length()); 
						att.setFdFileName(mainForm.getDocSubject() + suffix);
						getSysAttMainService().update(att); 
					}
				}
				
			}
			if (mainForm.getDocStatus().equals(SysDocConstant.DOC_STATUS_DRAFT)) {
                KmssReturnPage.getInstance(request).addMessages(messages)
                        .addButton("button.back",
                                "kmSmissiveMain.do?method=edit&fdId=" + fdId,
                                false).save(request);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
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

		KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
		returnPage.addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmSmissiveMainForm mainForm = (KmSmissiveMainForm) form;
			if (SysDocConstant.DOC_STATUS_DRAFT.equals(mainForm.getDocStatus())) {
				returnPage.addButton("button.back",
						"kmSmissiveMain.do?method=edit&fdId="
								+ mainForm.getFdId(), false);
			}
			returnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
      super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = " 1=1 ";
		}
		String ownerId = request.getParameter("ownerId");
		if (!StringUtil.isNull(ownerId)) {
			whereBlock += " and (kmSmissiveMain.docCreator.fdId = :ownerId or kmSmissiveMain.docAuthor.fdId = :ownerId)";
			request.setAttribute("ownerId", ownerId);
			hqlInfo.setParameter("ownerId", ownerId);
		}

		String departmentId = request.getParameter("departmentId");
		if (!StringUtil.isNull(departmentId)) {
			whereBlock += " and (kmSmissiveMain.docDept.fdId = :deparatmentId)";
			request.setAttribute("deparatmentId", departmentId);
			hqlInfo.setParameter("deparatmentId", departmentId);
		}

		String propertyId = request.getParameter("propertyId");
		if (!StringUtil.isNull(propertyId)) {
			whereBlock += " and (kmSmissiveMain.docProperties.fdId = :propertyId)";
			hqlInfo.setParameter("propertyId", propertyId);
		}

		String myDoc = request.getParameter("mydoc");
		if (!StringUtil.isNull(myDoc)){
			whereBlock += " and (kmSmissiveMain.docCreator.fdId = :mydoc)";
			hqlInfo.setParameter("mydoc", UserUtil.getUser().getFdId());
		} 
		
		String status = request.getParameter("status");
		if (!StringUtil.isNull(status) && !"all".equals(status)) {
			whereBlock += " and (kmSmissiveMain.docStatus = :status)";
			hqlInfo.setParameter("status", status);
		}
		hqlInfo.setWhereBlock(whereBlock);
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, KmSmissiveMain.class);
		String myFlow = request.getParameter("myflow");
		if ("1".equals(myFlow)) {
			SysFlowUtil.buildLimitBlockForMyApproved("kmSmissiveMain", hqlInfo);
		}
		if ("0".equals(myFlow)) {
			SysFlowUtil.buildLimitBlockForMyApproval("kmSmissiveMain", hqlInfo);
		}

		// 我的文档或者流程，不作过滤
		if (StringUtil.isNotNull(myFlow) || StringUtil.isNotNull(myDoc)) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
	}
  
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmSmissiveMainForm kmSmissiveMainForm = (KmSmissiveMainForm) form;
		// 模板
		KmSmissiveTemplate kmSmissiveTemplate = (KmSmissiveTemplate) getTreeServiceImp()
				.findByPrimaryKey(kmSmissiveMainForm.getFdTemplateId());
		/*** 返回文档对应的编号规则Id，前端cookie缓存用开始 **/
		String fdNoId = "";
		SysNumberMainMapp sysNumberMainMapp = (SysNumberMainMapp) getSysNumberMainMappImp()
				.getSysNumberMainMapp(
						"com.landray.kmss.km.smissive.model.KmSmissiveMain",
						kmSmissiveTemplate.getFdId());
		if (sysNumberMainMapp != null) {
			fdNoId = sysNumberMainMapp.getFdNumber().getFdId();
			request.setAttribute("fdNoId", fdNoId);
		}
		/*** 返回文档对应的编号规则Id，前端cookie缓存用结束 **/
		String isShowImg = request.getParameter("isShowImg");
		if (StringUtil.isNull(isShowImg)) {
			// 是否满足现实图片条件
			request.setAttribute("isShowImg", KmSmissiveConfigUtil
					.isShowImg(kmSmissiveMainForm));
		} else {
			request.setAttribute("isShowImg", !Boolean.parseBoolean(isShowImg));
		}
	}
	
	public ActionForward generateNumByNumberId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String fdNumberId = request.getParameter("fdNumberId");
		String docNum = "";
		try {
			if (StringUtil.isNotNull(fdId)) {
				KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) getServiceImp(
						request).findByPrimaryKey(fdId);
				docNum = getServiceImp(request).initdocNumByNumberId(
						kmSmissiveMain, fdNumberId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (StringUtil.isNotNull(docNum)) {
			JSONObject json = new JSONObject();
			json.put("docNum", docNum);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
		return null;

	}
	
	
	public ActionForward generateNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		String docNum = "";
		try {
			if (StringUtil.isNotNull(fdId)) {
				KmSmissiveMain kmSmissiveMain = (KmSmissiveMain) getServiceImp(
						request).findByPrimaryKey(fdId);
				docNum = getServiceImp(request).getdocNum(kmSmissiveMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		if (StringUtil.isNotNull(docNum)) {
			JSONObject json = new JSONObject();
			json.put("docNum", docNum);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
		return null;		
	}
	public ActionForward saveDocNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String a = "";
		try {
			a = getServiceImp(request).updateDocNum(request);
			UserOperHelper.setOperSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().append(a);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}

	//校验文号是否重复
		public ActionForward checkUniqueNum(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			TimeCounter.logCurrentTime("Action-checkUniqueNum", true, getClass());
			KmssMessages messages = new KmssMessages();
			String fdNo = request.getParameter("fdNo");
			String fdId = request.getParameter("fdId");
			String fdTempId = request.getParameter("fdTempId");
			Boolean unique = true;
			try {
				if (StringUtil.isNotNull(fdNo)) {
					unique = getServiceImp(request).checkUniqueNum(
							fdId, fdTempId,fdNo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				messages.addError(e);
			}
			KmssReturnPage.getInstance(request).addMessages(messages).addButton(
					KmssReturnPage.BUTTON_RETURN).save(request);
			TimeCounter.logCurrentTime("Action-checkUniqueNum", false, getClass());
			JSONObject json = new JSONObject();
			json.put("unique", unique.toString());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().append(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
			return null;

		}
}
