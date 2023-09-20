package com.landray.kmss.sys.news.actions;

import com.landray.kmss.common.actions.CategoryNodeAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttachmentService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaForm;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessDefinition;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpmservice.node.support.AbstractManualNode;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.mobile.annotation.Separater;
import com.landray.kmss.sys.news.forms.SysNewsMainForm;
import com.landray.kmss.sys.news.forms.SysNewsTemplateForm;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.model.SysNewsOutSign;
import com.landray.kmss.sys.news.model.SysNewsTemplate;
import com.landray.kmss.sys.news.model.SysNewsTemplateKeyword;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.sys.news.service.ISysNewsOutSignService;
import com.landray.kmss.sys.news.service.ISysNewsPublishMainService;
import com.landray.kmss.sys.news.service.ISysNewsTemplateService;
import com.landray.kmss.sys.news.service.ISysNewsYqqSignService;
import com.landray.kmss.sys.news.service.spring.SysNewsYqqSignServiceImp;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil;
import com.landray.kmss.sys.simplecategory.service.ISysSimpleCategoryService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.action.ActionRedirect;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.type.StandardBasicTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2007-Sep-17
 * 
 * @author 舒斌
 */
@Separater
public class SysNewsMainAction extends CategoryNodeAction {
	protected ISysNewsPublishMainService sysNewsPublishMainService;

	protected ISysNewsMainService sysNewsMainService;

	protected ISysNewsTemplateService sysNewsTemplateService;

	private ICoreOuterService dispatchCoreService;

	protected ISysAttachmentService sysAttachmentService;

	protected ISysAttachmentService getSysAttachmentServiceImp(
			HttpServletRequest request) {
		if (sysAttachmentService == null) {
            sysAttachmentService = (ISysAttachmentService) getBean("sysAttachmentService");
        }
		return sysAttachmentService;
	}
	
	protected ISysAttMainCoreInnerService sysAttMainService;

	protected ISysAttMainCoreInnerService getSysAttMainCoreInnerServiceImp(
			HttpServletRequest request) {
		if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) getBean("sysAttMainService");
        }
		return sysAttMainService;
	}

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

	protected IBaseService getSysNewsPublishMainService(
			HttpServletRequest request) {
		if (sysNewsPublishMainService == null) {
            sysNewsPublishMainService = (ISysNewsPublishMainService) getBean("sysNewsPublishMainService");
        }
		return sysNewsPublishMainService;
	}

	public ISysSimpleCategoryService getSysSimpleCategoryService() {
		return (ISysSimpleCategoryService) getSysNewsTemplateService();
	}
	
	private ISysNewsYqqSignService sysNewsYqqSignService = null;

	public ISysNewsYqqSignService getSysNewsYqqSignService() {
		if (sysNewsYqqSignService == null) {
			sysNewsYqqSignService = (ISysNewsYqqSignService) SpringBeanUtil
					.getBean("sysNewsYqqSignService");
		}
		return sysNewsYqqSignService;
	}

	private ISysNewsOutSignService sysNewsOutSignService = null;

	public ISysNewsOutSignService getSysNewsOutSignService() {
		if (sysNewsOutSignService == null) {
			sysNewsOutSignService = (ISysNewsOutSignService) SpringBeanUtil
					.getBean("sysNewsOutSignService");
		}
		return sysNewsOutSignService;
	}
	
	private ISysOrgCoreService sysOrgCoreService = null;

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}


	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		String para = request.getParameter("status");
		String m_where = "1=1";
		if (StringUtil.isNull(para)) {
			m_where = "sysNewsMain.docStatus=:docStatus";
			hqlInfo
					.setParameter("docStatus",
							SysDocConstant.DOC_STATUS_PUBLISH);
		} else if (!"all".equals(para)) {
			m_where = "sysNewsMain.docStatus=:docStatus";
			hqlInfo.setParameter("docStatus", para);
		}
		String type = request.getParameter("type");
		if ("pic".equals(type)) {
			m_where += " and sysNewsMain.fdIsPicNews=:fdIsPicNews";
			hqlInfo.setParameter("fdIsPicNews", true);
		}
		para = request.getParameter("mydoc");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.docCreator.fdId=:userId";
			// 我的文档，不需要控制权限
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
					SysAuthConstant.AreaCheck.YES);
		}

		para = request.getParameter("modelName");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdModelName=:modelName";
			hqlInfo.setParameter("modelName", para);
		}

		para = request.getParameter("modelId");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdModelId=:modelId";
			hqlInfo.setParameter("modelId", para);
		}
		// 为bam增加判断，因参数为fdModelId
		para = request.getParameter("fdModelName");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdModelName=:fdModelName";
			hqlInfo.setParameter("fdModelName", para);
		}

		para = request.getParameter("fdModelId");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdModelId=:fdModelId";
			hqlInfo.setParameter("fdModelId", para);
		}

		para = request.getParameter("top");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdIsTop=:fdIsTop";
			hqlInfo.setParameter("fdIsTop", true);
		}

		para = request.getParameter("departmentId");
		if (!StringUtil.isNull(para)) {
			m_where += " and sysNewsMain.fdDepartment.fdId =:deptId";
			hqlInfo.setParameter("deptId", para);
		}
		hqlInfo.setWhereBlock(m_where);

		para = request.getParameter("myflow");
		if (!StringUtil.isNull(para)) {
			// 我的流程，不需要控制权限
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			if ("0".equals(para)) {
				SysFlowUtil
						.buildLimitBlockForMyApproval("sysNewsMain", hqlInfo);
			} else if ("1".equals(para)) {
				SysFlowUtil
						.buildLimitBlockForMyApproved("sysNewsMain", hqlInfo);
			}
		}
		String m_order = hqlInfo.getOrderBy();
		if (m_order == null) {
            m_order = "";
        } else {
            m_order = "," + m_order;
        }
		m_order += ",sysNewsMain.fdIsTop desc, sysNewsMain.fdTopTime desc";
		if (m_order.indexOf("docAlterTime") == -1) {
			m_order += ",sysNewsMain.docAlterTime desc";
		}
		if (m_order.indexOf("docPublishTime") == -1) {
			m_order += ",sysNewsMain.docPublishTime desc";
		}
		hqlInfo.setOrderBy(m_order.substring(1));
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List tempList;
		Long importance;
		StringBuffer buffer = new StringBuffer();
		SysNewsMainForm newsMainForm = (SysNewsMainForm) form;
		String templateId = request.getParameter("fdTemplateId");
		if (StringUtil.isNull(templateId)) {
            return newsMainForm;
        }
		SysNewsTemplate template = (SysNewsTemplate) getSysNewsTemplateService()
				.findByPrimaryKey(templateId);
		if (template == null) {
			return super.createNewForm(mapping, form, request, response);
		}
		// 设置场所
		if (form instanceof ISysAuthAreaForm) {
			ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
			KMSSUser user = UserUtil.getKMSSUser();
			sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
			sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
		}
		if (template.getFdCanComment() != null) {
			newsMainForm.setFdCanComment(template.getFdCanComment().toString());
		} else {
			newsMainForm.setFdCanComment("true");
		}

		// 模板名称
		newsMainForm.setFdTemplateName(SimpleCategoryUtil
				.getCategoryPathName(template));
		// 新闻内容
		newsMainForm.setDocContent(template.getDocContent());
		// 所属部门
		String deptId = UserUtil.getUser().getFdParent() == null ? ""
				: UserUtil.getUser().getFdParent().getFdId().toString();
		String deptName = UserUtil.getUser().getFdParent() == null ? ""
				: UserUtil.getUser().getFdParent().getDeptLevelNames();
		newsMainForm.setFdDepartmentId(deptId);
		newsMainForm.setFdDepartmentName(deptName);
		//签章
		newsMainForm.setFdSignEnable(template.getFdSignEnable());
		// 标签机制
		if (UserUtil.getUser().getFdParent() != null) {
			newsMainForm.setDocCreatorDeptId(UserUtil.getUser().getFdParent()
					.getFdId());
		}

		// 关键字
		tempList = template.getDocKeyword();
		for (int i = 0; i < tempList.size(); i++) {
			SysNewsTemplateKeyword keyword = (SysNewsTemplateKeyword) tempList
					.get(i);
			buffer.append(keyword.getDocKeyword()).append(";");
		}
		if (buffer.length() > 1) {
            newsMainForm.setDocKeywordNames(buffer.substring(0,
                    buffer.length() - 1));
        }

		// 新闻重要度
		importance = template.getFdImportance();
		if (null != importance) {
            newsMainForm.setFdImportance(importance.toString());
        }

		// 新闻可阅读者
		tempList = template.getAuthTmpReaders();
		if (null != tempList && tempList.size() > 0) {
			String[] strArr = ArrayUtil.joinProperty(tempList, "fdId:fdName",
					";");
			newsMainForm.setAuthReaderIds(strArr[0]);
			newsMainForm.setAuthReaderNames(strArr[1]);
		}
		// 新闻可编辑者
		tempList = template.getAuthTmpEditors();
		if (null != tempList && tempList.size() > 0) {
			String[] strArr = ArrayUtil.joinProperty(tempList, "fdId:fdName",
					";");
			newsMainForm.setAuthEditorIds(strArr[0]);
			newsMainForm.setAuthEditorNames(strArr[1]);
		}
		newsMainForm.setFdCreatorName(UserUtil.getUser().getFdName());
		newsMainForm.setFdCreatorId((UserUtil.getUser().getFdId().toString()));
		newsMainForm.setFdAuthorId(newsMainForm.getFdCreatorId());
		newsMainForm.setFdAuthorName(newsMainForm.getFdCreatorName());
		newsMainForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
				DateUtil.TYPE_DATETIME, request.getLocale()));
		newsMainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
		newsMainForm.setFdContentType(template.getFdContentType()); // 设置编辑方式
		// 将模板加入request中，用于从模板中获取在线编辑的正文和参考附件，目前只是在新建的时候可以看见参考附件
		SysNewsTemplateForm sysNewsTemplateForm = new SysNewsTemplateForm();
		getSysNewsTemplateService().convertModelToForm(sysNewsTemplateForm,
				template, new RequestContext(request));
		request.setAttribute("sysNewsTemplateForm", sysNewsTemplateForm);
		getDispatchCoreService().initFormSetting(newsMainForm, "newsMainDoc",
				template, "newsMainDoc", new RequestContext(request));  
		
		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable()) {
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "新闻管理" + date;				
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(templateId);
			sam.setFdModelName("com.landray.kmss.sys.news.model.SysNewsTemplate");
            sam.setFdKey("editonline");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().setWpsOnlineFile(sam, newsMainForm.getFdId(),"com.landray.kmss.sys.news.model.SysNewsMain");
			getSysAttMainService().add(attMainFile);
			setAttForm(newsMainForm,attMainFile,"editonline");

		}

		// 如果编辑方式是附件上传，需要把模板的附件复制到文档中
		if("att".equals(sysNewsTemplateForm.getFdContentType())) {
			List<SysAttMain> attMains = getSysAttMainService().findByModelKey(SysNewsTemplate.class.getName(), sysNewsTemplateForm.getFdId(), "newsMain");
			if(CollectionUtils.isNotEmpty(attMains)) {
				SysAttMain sysAttMain = this.getSysAttMainService().clone(attMains.get(0));
				setAttForm(newsMainForm,sysAttMain, "newsMain");
			}
		}
		
		return newsMainForm;
	}

	public void setAttForm(SysNewsMainForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId("");
		attForm.setFdModelName("com.landray.kmss.sys.news.model.SysNewsTemplate");
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
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			SysNewsMainForm mainForm = (SysNewsMainForm) form;
			boolean isExist = getServiceImp(request).getBaseDao().isExist(SysNewsMain.class.getName(),
					mainForm.getFdId());
			if (isExist) {
				getServiceImp(request).update(mainForm, new RequestContext(request));
			} else {
				getServiceImp(request).add(mainForm, new RequestContext(request));
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-save", false, getClass());

		KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
		returnPage.addMessages(messages).save(request);
		if (messages.hasError()) {
			return getActionForward("edit", mapping, form, request, response);
		} else {
			SysNewsMainForm mainForm = (SysNewsMainForm) form;
			if (SysDocConstant.DOC_STATUS_DRAFT.equals(mainForm.getDocStatus())) {
				returnPage
						.addButton("button.back",
								"sysNewsMain.do?method=edit&fdId="
										+ mainForm.getFdId(), false);
			}
			returnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 保存到草稿并预览
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePreview(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									 HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-savePreview", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			getServiceImp(request).add((IExtendForm) form,
					new RequestContext(request));
			json.put("status", true);
			json.put("fdId", ((IExtendForm) form).getFdId());
		} catch (Exception e) {
			json.put("status", false);
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-savePreview", false, getClass());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json);
		response.getWriter().flush();

		return null;
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
			SysNewsMainForm mainForm = (SysNewsMainForm) form;
			if (SysDocConstant.DOC_STATUS_DRAFT.equals(mainForm.getDocStatus())) {
				returnPage
						.addButton("button.back",
								"sysNewsMain.do?method=edit&fdId="
										+ mainForm.getFdId(), false);
			}
			returnPage.addButton(KmssReturnPage.BUTTON_CLOSE);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	@Separater(value = "/sys/news/mobile/add.jsp",viewName = "add_normal")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.add(mapping, form, request, response);
	}

	@Override
	@Separater("/sys/news/mobile/edit.jsp")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.edit(mapping, form, request, response);
	}

	@Override
	@Separater("/sys/news/mobile/view.jsp")
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-view", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			List sysAttMains = (getSysAttMainCoreInnerServiceImp(request))
					.findByModelKey(
							"com.landray.kmss.sys.news.model.SysNewsMain",
							((SysNewsMainForm) form).getFdId(), "Attachment");
			List<SysAttMain> newsMains = sysAttMainService.findByModelKey(
					"com.landray.kmss.sys.news.model.SysNewsMain",
					((SysNewsMainForm) form).getFdId(),
					"newsMain");
			if (CollectionUtils.isNotEmpty(newsMains)) {
				String fdContentType = newsMains.get(0).getFdContentType();
				String contentType = fdContentType.split("/")[0];
				String [] fdileName = newsMains.get(0).getFdFileName().split("\\.");
				String type=fdileName[fdileName.length-1];
				//如果是图片或者csv类型不进行附件转换，不出现提示转换中的提示语
				if ("image".equals(contentType) || "csv".equalsIgnoreCase(type)) {
					request.setAttribute("isImage", true);
				} else {
					request.setAttribute("isImage", false);
				}
			} else {
				request.setAttribute("isImage", false);
			}
			boolean hasImage = sysAttMains.size()>0?true:false;
			// 判断是否开启aspose转换
			boolean isOpenAspose = ((ISysNewsMainService) getServiceImp(request))
			.isAsposeConver();
			request.setAttribute("isOpenAspose", isOpenAspose);
			request.setAttribute("hasImage",hasImage);
		} catch (Exception e) {
			messages.addError(e);
		}
		String more = request.getParameter("more");
		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			if (StringUtil.isNotNull(more)) {
				return getActionForward("view", mapping, form, request, response);
			} else {
				if (SysDocConstant.DOC_STATUS_PUBLISH.equals(((SysNewsMainForm) form).getDocStatus())) {
					SysNewsMainForm sysNewsMainForm = (SysNewsMainForm)form;
					String linkUrl = sysNewsMainForm.getFdLinkUrl();
					//linkUrl不为空，且为链接新闻才跳转
					if(StringUtil.isNotNull(linkUrl)&&"true".equals(sysNewsMainForm.getFdIsLink())) {
						Boolean isPdaAccess = PdaFlagUtil
								.checkClientIsPda(request);
						if (isPdaAccess) {
							PrintWriter print=response.getWriter();
							print.write("{\"dataUrl\":\"" + linkUrl + "\"}");
							return null;
						}
						else {
							String jType = (String) request
									.getParameter("j_dataType"); // json方式特殊处理
							if ("json".equals(jType)) {
								request.setAttribute("modelId",
										sysNewsMainForm.getFdModelId());
								request.setAttribute("modelName",
										sysNewsMainForm.getFdModelName());
							}
							
							request.setAttribute("redirectto", linkUrl);
							return mapping.findForward("redirect");
						}

					}
					return getActionForward("stylepage", mapping, form, request, response);
				} else {
					return getActionForward("view", mapping, form, request, response);
				}
				
			}
		}
	}

	/**
	 * 转移流程文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String templateId = request.getParameter("fdTemplateId");
			if (ids != null) {
                ((ISysNewsMainService) getServiceImp(request)).updateTemplate(
                        ids, templateId);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }

	}

	/**
	 * 置顶,取消置顶
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setTop(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			Long days = new Long(request.getParameter("fdDays"));
			boolean isTop = Boolean.parseBoolean(request
					.getParameter("fdIsTop"));
			if (ids != null) {
                ((ISysNewsMainService) getServiceImp(request)).updateTop(ids,
                        days, isTop);
            }
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setPublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String ops = request.getParameter("op");
			String expiredDate = request.getParameter("expiredDate");
			Date fdExpiredDate = null;
			if (StringUtil.isNotNull(expiredDate)) {
				fdExpiredDate = DateUtil.convertStringToDate(expiredDate, DateUtil.PATTERN_DATE);
			}
			if (ids != null && StringUtil.isNotNull(ops)) {
				if (log.isDebugEnabled()) {
					log.debug("setPublish op = " + ops);
				}
				boolean op = Boolean.valueOf(ops);
				((ISysNewsMainService) getServiceImp(request)).updatePublish(
						ids, fdExpiredDate, op);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysNewsMainService == null) {
            sysNewsMainService = (ISysNewsMainService) getBean("sysNewsMainService");
        }
		return sysNewsMainService;
	}

	public ISysNewsTemplateService getSysNewsTemplateService() {
		if (sysNewsTemplateService == null) {
            sysNewsTemplateService = (ISysNewsTemplateService) getBean("sysNewsTemplateService");
        }
		return sysNewsTemplateService;
	}

	/**
	 * 根据模板重新设置权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String tmpId = request.getParameter("tmpId");
			if (StringUtil.isNotNull(tmpId)) {
				((ISysNewsMainService) getServiceImp(request))
						.updateAuthWithTmp(tmpId);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id, null, true);
			if (model != null) {
				UserOperHelper.logFind(model);// 添加日志信息
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				((SysNewsMainForm) rtnForm)
						.setFdTemplateName(SimpleCategoryUtil
								.getCategoryPathName(((SysNewsMain) model)
										.getFdTemplate()));
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		// 判断是否集成了易企签
		Boolean yqqFlag = false;
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		if(null != extensions) {
			for (IExtension extension : extensions) {
				String channel = Plugin.getParamValueString(extension,
						"channel");
				if ("yqq".equals(channel)) {
					yqqFlag = true;
					break;
				}
			}
		}else {
			log.warn("系统未集成易企签");
		}
		
		request.setAttribute("yqqFlag", String.valueOf(yqqFlag));
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	/**
	 * 打开手动发布的跳转到编辑页面。<br>
	 * 
	 * @author 周超
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editManualPublish(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdModelNameParam = request.getParameter("fdModelNameParam");
		String fdModelIdParam = request.getParameter("fdModelIdParam");
		String fdKeyParam = request.getParameter("fdKeyParam");
		request.setAttribute("fdModelNameParam", fdModelNameParam);
		request.setAttribute("fdModelIdParam", fdModelIdParam);
		request.setAttribute("fdKeyParam", fdKeyParam);
		try {
			// 查找新闻记录
			if (StringUtil.isNotNull(fdModelNameParam)
					&& StringUtil.isNotNull(fdModelIdParam)) {
				List listPublishRecord = ((ISysNewsMainService) getServiceImp(request))
						.findListPublishRecord(fdModelNameParam, fdModelIdParam);
				// 添加日志信息
				UserOperHelper.logFindAll(listPublishRecord,
						getServiceImp(request).getModelName());
				request.setAttribute("listPublishRecord", listPublishRecord);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("editManualPublish", mapping, form,
                    request, response);
        }

	}

	/**
	 * view 页面 新闻记录显示
	 * 
	 * @author 周超
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewAllPublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdModelNameParam = request.getParameter("fdModelNameParam");
		String fdModelIdParam = request.getParameter("fdModelIdParam");
		String fdKeyParam = request.getParameter("fdKeyParam");
		request.setAttribute("fdModelNameParam", fdModelNameParam);
		request.setAttribute("fdModelIdParam", fdModelIdParam);
		request.setAttribute("fdKeyParam", fdKeyParam);
		try {// 查找新闻记录
			if (StringUtil.isNotNull(fdModelNameParam)
					&& StringUtil.isNotNull(fdModelIdParam)) {
				List listPublishRecord = ((ISysNewsMainService) getServiceImp(request))
						.findListPublishRecord(fdModelNameParam, fdModelIdParam);
				// 添加日志信息
				UserOperHelper.logFindAll(listPublishRecord,
						getServiceImp(request).getModelName());
				request.setAttribute("listPublishRecord", listPublishRecord);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("viewAllPublish", mapping, form, request,
                    response);
        }

	}

	/**
	 * 手动发布。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	public ActionForward manualPublishAdd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((ISysNewsPublishMainService) getSysNewsPublishMainService(request))
					.addManuaPublish((IExtendForm) form, new RequestContext(
							request));

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	public ActionForward publishAttAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((ISysNewsPublishMainService) getSysNewsPublishMainService(request))
					.addAttPublish((IExtendForm) form, new RequestContext(
							request));

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

	/**
	 * 图片新闻浏览
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward browse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-browse", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String isPage = request.getParameter("isPage");
			String s_pageno = request.getParameter("pageno");
			String s_rowsize = request.getParameter("rowsize");
			String orderby = request.getParameter("orderby");
			String ordertype = request.getParameter("ordertype");
			boolean isReserve = false;
			if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
				isReserve = true;
			}
			int pageno = 0;
			int rowsize = 0;
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
			changeFindPageHQLInfo(request, hqlInfo);
			if (StringUtil.isNotNull(isPage) && "false".equals(isPage)) {
				hqlInfo.setRowSize(getServiceImp(request).findList(hqlInfo)
						.size());
			}
			Page page = getServiceImp(request).findPage(hqlInfo);
			// 添加日志信息
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
			// 与list相比 修改的地方 ---- begin
			for (Iterator<?> it = page.getList().iterator(); it.hasNext();) {
				IAttachment attObj = (IAttachment) it.next();
				getSysAttachmentServiceImp(request).addAttachment(attObj,
						attObj);
			}
			// ---- end
			request.setAttribute("queryPage", page);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-browse", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("browse", mapping, form, request, response);
		}
	}

	/**
	 * 用于列表视图测试
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
			changeFindPageHQLInfo(request, hqlInfo);
			bulidCriteriaHQLInfo(request, hqlInfo);
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
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("index", mapping, form, request, response);
		}
	}

	/**
	 * 拼装筛选HQLInfo对象--测试使用
	 * 
	 * @param request
	 * @param hqlInfo
	 */
	private void bulidCriteriaHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) {
		String whereBlock = "1=1";
		String simpleCategory = request.getParameter("simpleCategory");
		if (StringUtil.isNotNull(simpleCategory)) {
			whereBlock += " and sysNewsMain.fdTemplate.fdId=:simpleCategory";
			hqlInfo.setParameter("simpleCategory", simpleCategory);
		}
		String docAuthor = request.getParameter("docAuthor");
		if (StringUtil.isNotNull(docAuthor)) {
			whereBlock += " and sysNewsMain.fdAuthor.fdId=:docAuthor";
			hqlInfo.setParameter("docAuthor", docAuthor);
		}
		String docDept = request.getParameter("docDept");
		if (StringUtil.isNotNull(docDept)) {
			whereBlock += " and sysNewsMain.fdDepartment.fdId=:docDept";
			hqlInfo.setParameter("docDept", docDept);
		}
		String[] docPublishTime = request.getParameterValues("docPublishTime");
		if (docPublishTime != null) {
			if (docPublishTime.length == 2) {
				whereBlock += " and sysNewsMain.docPublishTime>=:fromTime and sysNewsMain.docPublishTime<=:toTime";

				hqlInfo.setParameter("fromTime", DateUtil.convertStringToDate(
						docPublishTime[0], DateUtil.TYPE_DATE, request.getLocale()), StandardBasicTypes.TIMESTAMP);
				hqlInfo.setParameter("toTime", DateUtil.convertStringToDate(
						docPublishTime[1], DateUtil.TYPE_DATE, request.getLocale()), StandardBasicTypes.TIMESTAMP);
			}

			if (docPublishTime.length == 1) {
				whereBlock += " and sysNewsMain.docPublishTime = :time";

				hqlInfo.setParameter("time", DateUtil.convertStringToDate(
						docPublishTime[0], DateUtil.TYPE_DATE, request.getLocale()), StandardBasicTypes.TIMESTAMP);
			}
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
				" and ", whereBlock));
	}

	/**
	 * #45873 【接口提供】新闻查看提供json数据接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward simpleData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-simpleDataList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String fdId = request.getParameter("fdId");
			SysNewsMain main = (SysNewsMain) getServiceImp(request).findByPrimaryKey(fdId, SysNewsMain.class, true);
			UserOperHelper.logFind(main);// 添加日志信息
			JSONObject json = new JSONObject();
			json.put("fdId", main.getFdId());
			json.put("docSubject", main.getDocSubject());
			json.put("docCreateTime", DateUtil.convertDateToString(main.getDocCreateTime(), ResourceUtil.getString("date.format.datetime")));
			json.put("docPublishTime", DateUtil.convertDateToString(main.getDocPublishTime(), ResourceUtil.getString("date.format.datetime")));
			json.put("docContent", main.getDocContent());
			json.put("docReadCount", main.getDocReadCount());
			json.put("fdIsPicNews", main.getFdIsPicNews());
			json.put("fdSummary", main.getFdSummary());
			json.put("fdDescription", main.getFdDescription());
			json.put("fdIsLink", main.getFdIsLink());
			json.put("fdModelName", main.getFdModelName());
			json.put("docStatus", main.getDocStatus());
			json.put("fdContentType", main.getFdContentType());
			json.put("fdHtmlContent", main.getFdHtmlContent());
			json.put("docCreator", main.getDocCreator().getFdName());
			json.put("fdAuthor", main.getFdAuthor().getFdName());

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json);
			response.getWriter().flush();
		} catch (Exception e) {
			messages.addError(e);
		}finally {
			response.getWriter().close();
		}

		TimeCounter.logCurrentTime("Action-simpleDataList", false, getClass());
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward newsImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-newsImg", true, getClass());
		KmssMessages messages = new KmssMessages();
		String imgAttUrl = "";
		String fdId = request.getParameter("fdId");
		String modelName = request.getParameter("modelName");
		
		try {
			if (StringUtil.isNotNull(fdId)) {
				SysAttMain attmain = null;
				ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				List<SysAttMain> attMainList = sysAttMainCoreInnerService.findByModelKey(modelName, fdId, "Attachment");
				if (attMainList != null && attMainList.size() > 0) {
					attmain = attMainList.get(0);
					imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="
							+ attmain.getFdId();
				}
				if (StringUtil.isNull(imgAttUrl)) {
					String style = "default";
					String img = "default.png";
					imgAttUrl = "/resource/style/" + style + "/attachment/" + img;
				}
			}
			
		} catch(Exception e){
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-newsImg", false, getClass());
		ActionRedirect redirect = new ActionRedirect(imgAttUrl);
		return redirect;
	}
	
	public ActionForward openYqqExtendInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			SysNewsMain sysNewsMain = (SysNewsMain) getServiceImp(
					request).findByPrimaryKey(signId);

			if (sysNewsMain != null) {
				SysOrgPerson user = UserUtil.getUser();
				int processType = getProcessType(signId);
				List<SysOrgElement> signPersons = new ArrayList<>();
				if (processType == 0) {// 串行
					signPersons.add(user);
				} else if (processType == 1) {// 并行
					signPersons.add(user);
				} else if (processType == 2) {// 会审
					List<SysOrgElement> currentHandlers = getCurrentHandlers(
							signId);
					if (currentHandlers != null && currentHandlers.size() > 0) {
						signPersons = getSysOrgCoreService()
								.expandToPerson(currentHandlers);
					}
				}
				request.setAttribute("fdSigner", user);
				request.setAttribute("phone", user.getFdMobileNo());
				request.setAttribute("signPersons", signPersons);
				request.setAttribute("sysNewsMain", sysNewsMain);
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", false,
				getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("edit", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return getActionForward("extendinfo", mapping, form, request,
					response);
		}
	}

	/**
	 * 获取流程审批方式
	 * 
	 * @param modelId
	 * @return
	 */
	private int getProcessType(String modelId) {
		ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
				.getBean("lbpmProcessExecuteService");
		ProcessInstanceInfo processInfo = processExecuteService.load(modelId);
		ProcessDefinition processDefinition = processInfo
				.getProcessDefinitionInfo()
				.getDefinition();
		List<NodeInstance> currentNodes = processInfo.getCurrentNodes();
		for (NodeInstance node : currentNodes) {
			NodeDefinition nodeDefinition = processDefinition
					.findActivity(node.getFdActivityId());
			if (nodeDefinition instanceof AbstractManualNode) {
				AbstractManualNode curNode = (AbstractManualNode) nodeDefinition;
				return curNode.getProcessType();
			}
		}
		return -1;
	}

	/**
	 * 获取当前节点审批人
	 * 
	 * @param signId
	 * @return
	 * @throws Exception
	 */
	private List<SysOrgElement> getCurrentHandlers(String signId)
			throws Exception {
		List<SysOrgElement> handlers = new ArrayList<SysOrgElement>();
		ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
				.getBean("lbpmProcessService");
		IBaseModel model = lbpmProcessService.findByPrimaryKey(
				signId, null, true);
		if (model != null && model instanceof LbpmProcess) {
			LbpmProcess process = (LbpmProcess) model;
			ILbpmExpecterLogService lbpmExpecterLogService = (ILbpmExpecterLogService) SpringBeanUtil
					.getBean("lbpmExpecterLogService");
			for (Iterator<LbpmExpecterLog> expecterLogIter = lbpmExpecterLogService
					.findActiveByProcessId(process.getFdId(), true)
					.iterator(); expecterLogIter
							.hasNext();) {
				LbpmExpecterLog lbpmExpecterLog = expecterLogIter.next();
				if (!handlers.contains(lbpmExpecterLog.getFdHandler())) {
					handlers.add(lbpmExpecterLog.getFdHandler());
				}
			}
		}
		return handlers;
	}

	public ActionForward sendYqq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-sendYqq", true, getClass());
		response.setCharacterEncoding("utf-8");
		KmssMessages messages = new KmssMessages();
		Boolean sendStatus = true;
		JSONObject rtnObject = new JSONObject();
		try {
			String signId = request.getParameter("signId");
			String phone = request.getParameter("phone");
			String fdEnterprise = request.getParameter("fdEnterprise");
			if (StringUtil.isNotNull(phone)) {
				phone = phone.trim();
			}
			if (StringUtil.isNotNull(fdEnterprise)) {
				fdEnterprise = fdEnterprise.trim();
			}
			// 更新我方签约人手机信息到sys_org_person
			SysOrgPerson singer = UserUtil.getUser();
			SysNewsMain kmImissiveSendMain = (SysNewsMain) getServiceImp(
					request).findByPrimaryKey(signId);
			if (kmImissiveSendMain != null) {
				int processType = getProcessType(kmImissiveSendMain.getFdId());
				if (processType == 0) {// 串行
					sendStatus=getSysNewsYqqSignService().sendYqq(kmImissiveSendMain,
							phone,fdEnterprise, null);
					if (sendStatus) {
						addOutSign(signId, singer);
					}
				} else if (processType == 1) {// 并行
					sendStatus=getSysNewsYqqSignService().sendYqq(kmImissiveSendMain,
							phone,fdEnterprise, null);
					if (sendStatus) {
						addOutSign(signId, singer);
					}
				} else if (processType == 2) {// 会审
					List<SysOrgElement> currentHandlers = getCurrentHandlers(
							signId);
					if (currentHandlers != null && currentHandlers.size() > 0) {
						List<SysOrgPerson> persons = getSysOrgCoreService()
								.expandToPerson(currentHandlers);
						sendStatus=getSysNewsYqqSignService().sendYqq(
								kmImissiveSendMain,
								null, fdEnterprise,persons);
						if (sendStatus) {
							for (SysOrgElement person : persons) {
								addOutSign(signId, person);
							}
						}
						
					}
				}
				if (sendStatus) {
					ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
							.getBean("sysOrgPersonService");
					singer.setFdMobileNo(phone);
					sysOrgPersonService.update(singer);
				}
			}
			rtnObject.put("signId", signId);
			request.setAttribute("signId", signId);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-sendYqq", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("error", mapping, form, request, response);
		} else {
			rtnObject.put("sendStatus", String.valueOf(sendStatus));
			response.getWriter().println(rtnObject.toString());
			return null;
		}
	}

	private void addOutSign(String signId, SysOrgElement docCreator)
			throws Exception {
		// 记录签订状态
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysNewsOutSign.fdMainId=:fdMainId and sysNewsOutSign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", docCreator.getFdId());
		
		List<SysNewsOutSign> kmImeetingOutsignList = getSysNewsOutSignService()
				.findList(hqlInfo);
		if (kmImeetingOutsignList != null
				&& kmImeetingOutsignList.size() > 0) {
			return;
		}
		SysNewsOutSign outsign = new SysNewsOutSign();
		outsign.setFdMainId(signId);
		outsign.setFdStatus(SysNewsYqqSignServiceImp.status_code_init);// 发起签订(初始状态)
		outsign.setFdType(SysNewsYqqSignServiceImp.outsign_type_yqq);
		outsign.setDocCreator(docCreator);
		getSysNewsOutSignService().add(outsign);
	}


	public ActionForward viewAllPublishPrint(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fdModelName = (String) request.getAttribute("fdModelName");
		String fdModelId = (String)request.getAttribute("fdModelId");
		try {// 查找新闻记录
			if (StringUtil.isNotNull(fdModelId)
					&& StringUtil.isNotNull(fdModelName)) {
				List listPublishRecord = ((ISysNewsMainService) getServiceImp(request))
						.findListPublishRecord(fdModelName, fdModelId);
				// 添加日志信息
				UserOperHelper.logFindAll(listPublishRecord,
						getServiceImp(request).getModelName());
				request.setAttribute("listPublishRecord", listPublishRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
