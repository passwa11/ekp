package com.landray.kmss.km.review.actions;

import com.landray.kmss.common.actions.CategoryNodeAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTreeModel;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.component.locker.interfaces.ComponentLockerVersionException;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.elec.device.service.IElecChannelContractService;
import com.landray.kmss.elec.device.vo.IElecChannelUrlVO;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.review.ConvertUtil;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewConfigNotify;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewOutSign;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewFeedbackInfoService;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewOutSignService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.km.review.service.IKmReviewYqqSignService;
import com.landray.kmss.km.review.service.spring.KmReviewYqqSignServiceImp;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainCoreService;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainForm;
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
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.circulation.service.ISysCirculationOpinionService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.builder.NodeInstance;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessDefinition;
import com.landray.kmss.sys.lbpm.engine.integrate.expecterlog.ILbpmExpecterLogService;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmExpecterLog;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmWorkitem;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpm.engine.support.execute.InternalProcessInfoLoader;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.lbpmservice.node.support.AbstractManualNode;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmAuditNoteService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessForm;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.mobile.annotation.Separater;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogCoreService;
import com.landray.kmss.sys.print.interfaces.ISysPrintMainCoreService;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.sys.workflow.webservice.DefaultStartParameter;
import com.landray.kmss.sys.workflow.webservice.WorkFlowParameterInitializer;
import com.landray.kmss.sys.xform.XFormConstant;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌
 */
@Separater
public class KmReviewMainAction extends CategoryNodeAction {
	protected IKmReviewMainService kmReviewMainService;

	protected IKmReviewTemplateService kmReviewTemplateService;

	protected ISysOrgCoreService sysOrgCoreService;

	private ICoreOuterService dispatchCoreService;

	protected ILbpmAuditNoteService lbpmAuditNoteService;

	protected ISysPrintMainCoreService sysPrintMainCoreService;

	protected ISysPrintLogCoreService sysPrintLogCoreService;

	private IKmsMultidocSubsideService kmsMultidocSubsideService;

	private IKmReviewFeedbackInfoService kmReviewFeedbackInfoService;
	
	protected ISysAttMainCoreInnerService sysAttMainService;
	
	private ProcessExecuteService processExecuteService;
	
	protected ProcessExecuteService getProcessExecuteService() {
		if (processExecuteService == null) {
			processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteServiceTarget");
		}
		return processExecuteService;
	}
	
	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	

	protected IKmsMultidocSubsideService getKmsMultidocSubsideService() {
		if (kmsMultidocSubsideService == null) {
			kmsMultidocSubsideService = (IKmsMultidocSubsideService) getBean(
					"kmsMultidocSubsideService");
		}
		return kmsMultidocSubsideService;
	}

	protected ILbpmAuditNoteService getLbpmAuditNoteService(
			HttpServletRequest request) {
		if (lbpmAuditNoteService == null) {
            lbpmAuditNoteService = (ILbpmAuditNoteService) getBean(
                    "lbpmAuditNoteService");
        }
		return lbpmAuditNoteService;
	}

	@Override
	protected IKmReviewMainService getServiceImp(HttpServletRequest request) {
		if (kmReviewMainService == null) {
            kmReviewMainService = (IKmReviewMainService) getBean(
                    "kmReviewMainService");
        }
		return kmReviewMainService;
	}

	public IKmReviewTemplateService getKmReviewTemplateService() {
		if (kmReviewTemplateService == null) {
            kmReviewTemplateService = (IKmReviewTemplateService) getBean(
                    "kmReviewTemplateService");
        }
		return kmReviewTemplateService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) getBean(
                    "sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	public ISysPrintMainCoreService getSysPrintMainCoreService() {
		if (sysPrintMainCoreService == null) {
            sysPrintMainCoreService = (ISysPrintMainCoreService) getBean(
                    "sysPrintMainCoreService");
        }
		return sysPrintMainCoreService;
	}

	public ISysPrintLogCoreService getSysPrintLogCoreService() {
		if (sysPrintLogCoreService == null) {
            sysPrintLogCoreService = (ISysPrintLogCoreService) getBean(
                    "sysPrintLogCoreService");
        }
		return sysPrintLogCoreService;
	}

	public IKmReviewFeedbackInfoService getKmReviewFeedbackInfoService() {
		if (kmReviewFeedbackInfoService == null) {
            kmReviewFeedbackInfoService = (IKmReviewFeedbackInfoService) getBean(
                    "kmReviewFeedbackInfoService");
        }
		return kmReviewFeedbackInfoService;
	}
	

	private IKmReviewYqqSignService kmReviewYqqSignService = null;

	public IKmReviewYqqSignService getKmReviewYqqSignService() {
		if (kmReviewYqqSignService == null) {
			kmReviewYqqSignService = (IKmReviewYqqSignService) SpringBeanUtil
					.getBean("kmReviewYqqSignService");
		}
		return kmReviewYqqSignService;
	}

	private IKmReviewOutSignService kmReviewOutSignService = null;

	public IKmReviewOutSignService getKmReviewOutSignService() {
		if (kmReviewOutSignService == null) {
			kmReviewOutSignService = (IKmReviewOutSignService) SpringBeanUtil
					.getBean("kmReviewOutSignService");
		}
		return kmReviewOutSignService;
	}

	protected ISysCirculationOpinionService sysCirculationOpinionService;

	public ISysCirculationOpinionService getSysCirculationOpinionService() {
		if (sysCirculationOpinionService == null) {
            sysCirculationOpinionService = (ISysCirculationOpinionService) getBean(
                    "sysCirculationOpinionService");
        }
		return sysCirculationOpinionService;
	}

	private ISysFormTemplateService sysFormTemplateService;

	public ISysFormTemplateService getSysFormTemplateService() {
		if (sysFormTemplateService == null) {
			sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
					.getBean("sysFormTemplateService");
		}
		return sysFormTemplateService;
	}

	/**
	 * 沉淀模板页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printSubsideDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String fdId = request.getParameter("fdId");
			KmReviewMain mainModel = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(fdId);
			List list = getKmsMultidocSubsideService()
					.getCoreModels(mainModel.getFdTemplate(), null);
			boolean saveApproval = false;
			if (list != null && list.size() > 0) {
				KmsMultidocSubside fileTemplate = (KmsMultidocSubside) list
						.get(0);
				if (fileTemplate.getCategory() != null) {
					saveApproval = fileTemplate.getFdSaveApproval();
				} else {
					String fdSaveApproval = request
							.getParameter("fdSaveApproval");
					if (StringUtil.isNotNull(fdSaveApproval)) {
						if ("true".equals(fdSaveApproval)) {
							saveApproval = true;
						} else {
							saveApproval = false;
						}
					}
				}
			}
			request.setAttribute("saveApproval", saveApproval);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("filePrint", mapping, form, request,
                    response);
        }
	}

	/**
	 * 归档模板页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printFileDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			HtmlToMht.setLocaleWhenExport(request);
			loadActionForm(mapping, form, request, response);
			String saveApprovalStr = request.getParameter("saveApproval");
			boolean saveApproval = "1".equals(saveApprovalStr);
			request.setAttribute("saveApproval", saveApproval);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("filePrint", mapping, form, request,
                    response);
        }
	}

	public ActionForward jsonCreateDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 表单参数
		String formJson = request.getParameter("formJson");
		// 模板id
		String templateId = request.getParameter("templateId");
		// 流程参数
		String flowJson = request.getParameter("flowJson");

		// templateId = "16961331f6e818c87ae2b4346eb8b55f";
		// JSONObject fj = new JSONObject();
		// fj.put("docSubject", "test");
		// fj.put("fd_test", "1111");
		// formJson = fj.toString();

		// 返回结果
		JSONObject rtnJson = new JSONObject();
		// 0成功 非0 失败
		rtnJson.put("status", "0");
		// ===========start设置表单参数 ================
		ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
				.getBean("sysMetadataParser");
		ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
				.getBean("sysFormTemplateService");
		HQLInfo hql = new HQLInfo();
		hql.setSelectBlock("fdFormFileName");
		hql.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName");
		hql.setParameter("fdModelId", templateId);
		hql.setParameter("fdModelName", KmReviewTemplate.class.getName());
		List<String> values = sysFormTemplateService
				.findValue(hql);
		KmReviewMain km = new KmReviewMain();
		km.setExtendFilePath(values.get(0));

		SysDictModel dict = sysMetadataParser.getDictModel(km);
		RequestContext requestContext = new RequestContext();
		Map<String, Object> formValues = new HashMap<String, Object>();
		requestContext.setAttribute(ISysMetadataService.INIT_MODELDATA_KEY,
				formValues);
		formValues.put("docStatus", SysDocConstant.DOC_STATUS_EXAMINE);
		formValues.put("docCreator", UserUtil.getUser());
		requestContext.setParameter("fdTemplateId", templateId);
		// requestContext.setAttribute("docSubject", "test1");
		if (StringUtil.isNotNull(formJson)) {
			Map<String, Object> formMap = SysWsUtil.json2map(formJson, dict);
			formValues.putAll(formMap);
		}
		// ===========end设置表单参数 ================

		// ===========start设置流程参数 ================
		DefaultStartParameter flowParam = new DefaultStartParameter();
		flowParam.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		flowParam.setDrafterId(UserUtil.getUser().getFdId());
		if (StringUtil.isNotNull(flowJson)) {
			JSONObject jsonObj = JSONObject.fromObject(flowJson);
			if (!jsonObj.isNullObject() && !jsonObj.isEmpty()) {
				Object auditNode = jsonObj.get("auditNode"); // 审批意见
				if (auditNode != null) {
					flowParam.setAuditNode(auditNode.toString());
				}
				Object futureNodeId = jsonObj.get("futureNodeId"); // 人工决策节点
				if (futureNodeId != null) {
					flowParam.setFutureNodeId(futureNodeId.toString());
				}
				Object handlers = jsonObj.get("changeNodeHandlers"); // “必须修改节点处理人”节点
				if (handlers != null) {
					JSONArray jsonArr = JSONArray.fromObject(handlers);
					flowParam.setChangeNodeHandlers(jsonArr);
				}
			}
		}
		// ===========end设置流程参数 ================

		String modelId = null;
		try {
			// 启动流程
			IExtendForm extendForm = kmReviewMainService
					.initFormSetting(
							null, requestContext);
			// 设置流程API
			WorkFlowParameterInitializer.initialize(
					(ISysWfMainForm) extendForm, flowParam);
			modelId = kmReviewMainService.add(extendForm,
					requestContext);
			if (logger.isDebugEnabled()) {
				logger.debug("流程启动完毕！");
			}
		} catch (Exception e) {
			rtnJson.put("status", "1");
			rtnJson.put("msg", "启动流程失败");
			response.getWriter().print(rtnJson.toString());
			e.printStackTrace();
			return null;

		}
		rtnJson.put("modelId", modelId);

		response.getWriter().print(rtnJson.toString());
		return null;

	}

	/**
	 * 发布草稿文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String referer = request.getHeader("referer");
		String id = request.getParameter("fdId");
		String dbDocStatus = SysDocConstant.DOC_STATUS_EXAMINE;
		String creatorId = "";
		if (StringUtil.isNotNull(id)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("kmReviewMain.docStatus");
			hqlInfo.setModelName(KmReviewMain.class.getName());
			hqlInfo.setWhereBlock("kmReviewMain.fdId=:fdId");
			hqlInfo.setParameter("fdId", id);
			List value = getServiceImp(request).findValue(hqlInfo);
			dbDocStatus = value.size() > 0 ? (String) value.get(0) :dbDocStatus;

			HQLInfo hqlInfoCreator = new HQLInfo();
			hqlInfoCreator.setSelectBlock("kmReviewMain.docCreator.fdId");
			hqlInfoCreator.setModelName(KmReviewMain.class.getName());
			hqlInfoCreator.setWhereBlock("kmReviewMain.fdId=:fdId");
			hqlInfoCreator.setParameter("fdId", id);
			List creatorValue = getServiceImp(request)
					.findValue(hqlInfoCreator);
			creatorId = creatorValue.size() > 0 ? (String) creatorValue.get(0)
					: "";
		}
		
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		if (StringUtils.isNotBlank(referer) && null != mainForm.getSysWfBusinessForm()
				&& StringUtils.isBlank(mainForm.getSysWfBusinessForm().getFdParameterJson())
				&& referer.contains("method=view")) {
			request.setAttribute("operation", "button.back");// 反馈页面根据内容，来判断是否回到之前页面
			request.setAttribute("redirectUrl", "kmReviewMain.do?method=view&fdId=" + id);
		}
		// 在文档状态是10，11时限制只有创建者可以改变文档状态
		SysOrgPerson person = UserUtil.getUser();
		if (person != null && !("".equals(creatorId))
				&& (SysDocConstant.DOC_STATUS_DRAFT.equals(dbDocStatus)
						|| SysDocConstant.DOC_STATUS_REFUSE.equals(dbDocStatus))
				&& (person.getFdId()).equals(creatorId)) {
			mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		} else {
			// 审批状态时，则有权限可以修改文档状态
			mainForm.setDocStatus(dbDocStatus);
		}
		ActionForward af = super.update(mapping, form, request, response);

		// 下面的逻辑是在审批操作中，如果遇到版本冲突时，才会处理的逻辑，#66698
		KmssReturnPage returnPage = (KmssReturnPage) request
				.getAttribute("KMSS_RETURNPAGE");
		KmssMessages kmssMessages = returnPage.getMessages();
		if (kmssMessages != null && kmssMessages.hasError()) {
			List<KmssMessage> msgs = kmssMessages.getMessages();
			if (msgs != null && msgs.size() > 0) {
				KmssMessage msg = msgs.get(0);
				Throwable t = msg.getThrowable();
				if (t != null && t instanceof ComponentLockerVersionException) {
					if (!StringUtil.isNull(id)) {
						IBaseModel model = getServiceImp(request)
								.findByPrimaryKey(id, null, true);
						if (model != null) {
							// model转form后会更新数据库的docstatus，不是这个操作的目的
							String returnStatus = mainForm.getDocStatus();
							mainForm.setDocStatus(((KmReviewMain) model).getDocStatus());
							// 先将页面的数据转为model
							getServiceImp(request).convertFormToModel(
									(IExtendForm) form, model,
									new RequestContext(request));
							// 再将model数据转为form
							getServiceImp(request).convertModelToForm(
									(IExtendForm) form, model,
									new RequestContext(request));
							mainForm.setDocStatus(returnStatus);
						}
					}

					// 版本冲突，这里判断的是审批操作，需要跳回到view页面
					af = getActionForward("view", mapping, form, request,
							response);
				}
			}
		}
		
		// 针对版本锁冲突后选择覆盖数据的提交的操作，因为机制操作原因，需要还原数据库的文档状态变更
		if (SysDocConstant.DOC_STATUS_PUBLISH.equals(dbDocStatus) && !kmssMessages.hasError()) {
			IBaseModel mainModel = getServiceImp(request)
					.findByPrimaryKey(id, null, true);
			((KmReviewMain) mainModel).setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);
			getServiceImp(request).update(mainModel);
		}
		return af;
	}

	/**
	 * 检测违反约束性错误
	 */
	private void checkConstraintException(KmssMessages messages) {
		if (messages.hasError()) {
			List<KmssMessage> ms = messages.getMessages();
			for (KmssMessage message : ms) {
				Throwable e = message.getThrowable();

				if (e.getCause() instanceof DataException
						|| e.getCause() instanceof GenericJDBCException) {
					messages.addError(new KmssMessage(
							"km-review:kmReviewMain.save.fail.fdNumber"));
					break;
				} else if (e instanceof DataException
						|| e instanceof GenericJDBCException) {
					messages.addError(new KmssMessage(
							"km-review:kmReviewMain.save.fail.fdNumber"));
					break;
				}
			}
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		KmReviewMainForm mainForm = (KmReviewMainForm) form;

		if(logger.isInfoEnabled()){
			logger.info("kmreviewmainAction===save begin fdId:"+mainForm.getFdId());
		}

		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		ActionForward actionForward = super.save(mapping, form, request,
				response);

		if(logger.isInfoEnabled()){
			logger.info("kmreviewmainAction===save end");
		}

		checkConstraintException(
				KmssReturnPage.getInstance(request).getMessages());

		return actionForward;
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		ActionForward actionForward = super.saveadd(mapping, form, request,
				response);
		checkConstraintException(
				KmssReturnPage.getInstance(request).getMessages());
		return actionForward;
	}

	/**
	 * 新建草稿文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		KmssMessages messages = new KmssMessages();
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
		try {
			String fdId = this.getServiceImp(request).add(
					(KmReviewMainForm) form, new RequestContext(request));
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton("button.back",
							"kmReviewMain.do?method=edit&fdId=" + fdId, false)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			request.setAttribute("operation", "button.back");// 反馈页面根据内容，来判断是否回到之前页面
			request.setAttribute("redirectUrl", "kmReviewMain.do?method=edit&fdId=" + fdId);
			return mapping.findForward("km_review_success");
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("failure");

	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		String fdForward = "success";
		try {
			this.getServiceImp(request).update((KmReviewMainForm) form,
					new RequestContext(request));
			if (com.landray.kmss.km.review.Constant.STATUS_DRAFT
					.equals(mainForm.getDocStatus())) {
				KmssReturnPage.getInstance(request).addButton(
						"button.back",
						"kmReviewMain.do?method=edit&fdId="
								+ ((KmReviewMainForm) form).getFdId(),
						false)
						.save(request);
				fdForward="km_review_success";
			}
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			request.setAttribute("operation", "button.back");// 反馈页面根据内容，来判断是否回到之前页面
			request.setAttribute("redirectUrl",
					"kmReviewMain.do?method=edit&fdId=" + ((KmReviewMainForm) form).getFdId());
			return mapping.findForward(fdForward);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		return getActionForward("edit", mapping, form, request, response);
	}

	public ActionForward updateDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		SysWfBusinessForm sysWfBusinessForm = mainForm.getSysWfBusinessForm();
		sysWfBusinessForm.setCanStartProcess("false");
		mainForm.setDocStatus(SysDocConstant.DOC_STATUS_REFUSE);
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			((IKmReviewMainService) getServiceImp(request)).update(mainForm,
					new RequestContext(request));
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
			return getActionForward("success", mapping, form, request,
					response);
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
	public ActionForward changeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String templateId = request.getParameter("fdTemplateId");
		String ids = request.getParameter("values");
		try {
			((IKmReviewMainService) getServiceImp(request))
					.updateDucmentTemplate(ids, templateId);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);

	}

	/**
	 * 打开列表页面。<br>
	 * 该操作一般以HTTP的GET方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回list页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
	public ActionForward list(ActionMapping mapping, ActionForm form,
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
			// 添加list页面搜索sql语句
			changeSearchInfoFindPageHQLInfo(request, hqlInfo);
			Page page = getServiceImp(request).findPage(hqlInfo);
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
			return getActionForward("list", mapping, form, request, response);
		}
	}

	// 重写SimpleCategoryNodeAction中的listChildren方法、manageList方法和listChildrenBase方法
	@Override
	public ActionForward listChildren(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return listChildrenBase(mapping, form, request, response,
				"listChildren", null);
	}

	@Override
	public ActionForward manageList(ActionMapping mapping, ActionForm form,
									HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return listChildrenBase(mapping, form, request, response, "manageList",
				SysAuthConstant.AUTH_CHECK_NONE);
	}

	/**
	 * 高级审批查看数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward manageListData(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*148677【A计划V16兼容测试 - 流程引擎】无数据时，提示语不正确，*/
		return listChildrenBase(mapping, form, request, response, "manageList",null);
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
			String nodeType = request.getParameter("nodeType");
			String excepteIds = request.getParameter("excepteIds");
			if (StringUtil.isNull(nodeType)) {
                nodeType = "node";
            }
			String s_IsShowAll = request.getParameter("isShowAll");
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
			// 添加list页面搜索sql语句
			changeSearchInfoFindPageHQLInfo(request, hqlInfo);
			String whereBlock = hqlInfo.getWhereBlock();
			if (!StringUtil.isNull(parentId)) {
				if (StringUtil.isNull(whereBlock)) {
                    whereBlock = "";
                } else {
                    whereBlock = "(" + whereBlock + ") and ";
                }
				String tableName = ModelUtil.getModelTableName(getServiceImp(
						request).getModelName());
				if (nodeType.indexOf("CATEGORY") == -1) {
					if ("propertyNode".equals(nodeType)) {
						whereBlock += tableName
								+ ".docProperties.fdId= :parentId";
						hqlInfo.setParameter("parentId", parentId);
					} else {
						whereBlock += tableName + "." + getParentProperty()
								+ ".fdId= :parentId";
						hqlInfo.setParameter("parentId", parentId);
					}
				} else if (isShowAll) {
					IBaseTreeModel treeModel = (IBaseTreeModel) getCategoryMainService()
							.findByPrimaryKey(parentId);
					// 优化
					whereBlock += tableName + "." + getParentProperty()
							+ ".docCategory.fdHierarchyId like :fdHierarchyId";
					hqlInfo.setParameter("fdHierarchyId",
							treeModel.getFdHierarchyId() + "%");
				} else {
					whereBlock += tableName + "." + getParentProperty()
							+ ".docCategory.fdId= :parentId";
					hqlInfo.setParameter("parentId", parentId);
				}
				if (StringUtil.isNotNull(excepteIds)) {
					whereBlock += " and "
							+ HQLUtil.buildLogicIN(tableName + ".fdId not",
									ArrayUtil.convertArrayToList(excepteIds
											.split("\\s*[;,]\\s*")));
				}
				// if (("manageList").equals(forwordPage)) {
				// whereBlock += " and " + tableName + ".docStatus <>'"
				// + SysDocConstant.DOC_STATUS_DRAFT + "'";
				// }
			}
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
			UserOperHelper.logFindAll(page.getList(),
					getServiceImp(request).getModelName());
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

	// list页面搜索查询条件
	protected void changeSearchInfoFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		String docSubject = request.getParameter("docSubject");// 主题
		String fdNumber = request.getParameter("fdNumber");// 申请单编号
		String docStartdate = request.getParameter("docStartdate");// 创建时间（起始）
		String docFinishdate = request.getParameter("docFinishdate");// 创建时间（截止）
		String docCreatorId = request.getParameter("docCreatorId");// 申请人
		String docCreatorName = request.getParameter("docCreatorName");
		String fdTemplateId = request.getParameter("fdTemplateId");// 模板名称
		String fdTemplateName = request.getParameter("fdTemplateName");

		if (StringUtil.isNotNull(docStartdate)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.docCreateTime >= '" + docStartdate + "'");
			// StringBuffer sbf = new StringBuffer();
			// sbf.append("kmReviewMain.docCreateTime >=
			// '").append(docStartdate)
			// .append("'");
			// whereBlock = StringUtil.linkString(whereBlock, " and ", sbf
			// .toString().trim());
			request.setAttribute("docStartdate", docStartdate);
		}
		if (StringUtil.isNotNull(docFinishdate)) {
			String endTimeNextDay = getCreatTimeNextDay(docFinishdate);
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.docCreateTime <= '" + endTimeNextDay + "'");
			// StringBuffer sbf = new StringBuffer();
			// sbf.append(" kmReviewMain.docCreateTime <= '").append(
			// endTimeNextDay).append("'");
			// whereBlock = StringUtil.linkString(whereBlock, " and ", sbf
			// .toString().trim());
			request.setAttribute("docFinishdate", docFinishdate);
		}
		if (StringUtil.isNotNull(docCreatorId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.docCreator.fdId = :docCreatorId");
			hqlInfo.setParameter("docCreatorId", docCreatorId);
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			try {
				SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
						.findByPrimaryKey(docCreatorId);
				if (docCreatorId != null && !"".equals(docCreatorId)) {
					request.setAttribute("docCreatorId", docCreatorId);
					request.setAttribute("docCreatorName", person.getFdName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isNotNull(fdTemplateId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.fdTemplate.fdId = :fdTemplateId");
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);
			try {
				KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
						.findByPrimaryKey(fdTemplateId);
				if (fdTemplateId != null && !"".equals(fdTemplateId)) {
					request.setAttribute("fdTemplateId", fdTemplateId);
					request
							.setAttribute("fdTemplateName", template
									.getFdName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (StringUtil.isNotNull(docSubject)) {
			request.setAttribute("docSubject", docSubject);
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.docSubject like '%" + docSubject + "%'");
		}
		if (StringUtil.isNotNull(fdNumber)) {
			request.setAttribute("fdNumber", fdNumber);
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmReviewMain.fdNumber like '%" + fdNumber + "%'");
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	/**
	 * 返回查询条件中结束时间的下一天 author wanghj
	 * 
	 * @param endTime
	 * @return
	 */
	public String getCreatTimeNextDay(String endTime) {
		Date date = DateUtil
				.convertStringToDate(endTime, DateUtil.PATTERN_DATE);
		Calendar cla = Calendar.getInstance();
		cla.setTime(date);
		cla.add(Calendar.DAY_OF_MONTH, 1);
		String endTimeNextDay = DateUtil.convertDateToString(cla.getTime(),
				DateUtil.PATTERN_DATE);
		return endTimeNextDay;
	}

	/**
	 * 按状态和分类查询文档
	 */
	protected String changeFindPageWhereBlock(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		String type = request.getParameter("type");
		String departmentId = request.getParameter("departmentId");
		String status = request.getParameter("status");
		String fdTemplateId = request.getParameter("fdTemplateId");
		if (null != status) {
			String owner = request.getParameter("mydoc");
			StringBuilder hql = new StringBuilder();
			if ("all".equals(status)) {
				request.setAttribute("docStatus", "true");
				request.setAttribute("publishTime", "true");
				if (!"true".equals(owner)) {
					hql.append("kmReviewMain.docStatus<>'").append(
							SysDocConstant.DOC_STATUS_DRAFT).append("'");
				} else {
					hql.append("1=1");
				}
			} else {
				hql.append("kmReviewMain.docStatus=:status");
				hqlInfo.setParameter("status", status);
			}
			if ("true".equals(owner)) {
				hql.append(" AND kmReviewMain.docCreator.fdId=:userid");
				hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			}
			if (StringUtil.isNotNull(fdTemplateId)) {
				hql.append(" and kmReviewMain.fdTemplate.fdId=:fdTemplateId");
				hqlInfo.setParameter("fdTemplateId", fdTemplateId);
			}
			return hql.toString();
		}
		if ("department".equals(type)) {
			hqlInfo.setParameter("departmentId", "%" + departmentId + "%");
			return "kmReviewMain.docStatus<>'"
					+ SysDocConstant.DOC_STATUS_DRAFT
					+ "' AND kmReviewMain.docCreator.fdHierarchyId LIKE :departmentId";
		}
		if (StringUtil.isNotNull(fdTemplateId)
				&& "true".equals(request.getParameter("mydoc"))) {
			StringBuilder hql = new StringBuilder();
			hql.append("kmReviewMain.docStatus<>'").append(
					SysDocConstant.DOC_STATUS_DRAFT).append("'");
			hql.append(" AND kmReviewMain.docCreator.fdId=:userid");
			hql.append(" and kmReviewMain.fdTemplate.fdId=:fdTemplateId");
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);
			return hql.toString();
		}
		return null;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		hqlInfo.setWhereBlock(changeFindPageWhereBlock(request, hqlInfo));
		String myFlow = request.getParameter("type");
		if ("executed".equals(myFlow)) {
			SysFlowUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		if ("unExecuted".equals(myFlow)) {
			SysFlowUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		String status = request.getParameter("status");
		String fdTemplateId = request.getParameter("fdTemplateId");
		if ("true".equals(request.getParameter("mydoc"))
				&& (status != null || StringUtil.isNotNull(fdTemplateId))) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		String whereBlock = hqlInfo.getWhereBlock();
		CriteriaValue cv = new CriteriaValue(request);
		hqlInfo.setWhereBlock(whereBlock);
		// 当前处理人
		String[] fdCurrentHandler = cv.polls("fdCurrentHandler");
		if (fdCurrentHandler != null) {
			LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo,
					getServiceImp(request).getOrgAndPost(request,
							fdCurrentHandler));
			hqlInfo.setAuthCheckType(null);
		}
		// 已处理人
		String fdAlreadyHandler = cv.poll("fdAlreadyHandler");
		if (StringUtil.isNotNull(fdAlreadyHandler)) {
			LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo,
					fdAlreadyHandler);
			hqlInfo.setAuthCheckType(null);
		}
		CriteriaUtil.buildHql(cv, hqlInfo, KmReviewMain.class);
	}

	public ActionForward findOwnerList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HQLInfo hqlInfo = getHQLInfo(request);
		hqlInfo.setModelName(" KmReviewMain ");
		hqlInfo
				.setWhereBlock(
						"kmReviewMain.docCreator.fdId=:userId or flowHisAuditor.fdId=:userId or kmReviewMain.authAllReaders.fdId=:userId ");
		hqlInfo
				.setJoinBlock(
						" left join kmReviewMain.sysFlowModel.hbmHisAuditorList flowHisAuditor ");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		Page page = new Page();
		page.setRowsize(hqlInfo.getRowSize());
		page.setPageno(hqlInfo.getPageNo());
		page.excecute();
		List list = this.getServiceImp(request).findList(hqlInfo);
		page.setList(list);
		page.setTotalrows(list.size());
		request.setAttribute("queryPage", page);
		request.setAttribute("publishTime", "true");
		return mapping.findForward("list");
	}

	private HQLInfo getHQLInfo(HttpServletRequest request) {
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
		hqlInfo.setDistinctType(1);
		hqlInfo.setOrderBy(orderby);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);
		return hqlInfo;
	}
	
	@Override
	@Separater(value = "/km/review/mobile/view.jsp",viewName = "view_normal")
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Boolean editFlag=getEditFlag(request,form);
		request.setAttribute("editFlag", editFlag);
		return super.view(mapping, form, request, response);
	}
	
	
	/**
	 * 返回正文编辑权限
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	private Boolean getEditFlag(HttpServletRequest request, ActionForm form)
			throws Exception {
		Boolean handlerFlag = false;
		// 只有当前审批人才可以编辑(基本前提)
		String fdId = request.getParameter("fdId");
		
		List<SysOrgElement> currentHandlers=this.getCurrentHandlers(fdId);
		
		for (SysOrgElement hander : currentHandlers) {
			if (UserUtil.getUser().getFdId().equals(hander.getFdId())) {
				handlerFlag = true;
				break;
			}
		}
		
		if (handlerFlag) {
			// 如果是当前审批人，再判断是否当前节点是可编辑节点
			ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
					.getBean("lbpmProcessExecuteService");
			ProcessInstanceInfo processInfo = processExecuteService.load(fdId);
			ProcessDefinition processDefinition = processInfo
					.getProcessDefinitionInfo()
					.getDefinition();
			AbstractManualNode curNode=null;
			List<NodeInstance> currentNodes = processInfo.getCurrentNodes();
			for (NodeInstance node : currentNodes) {
				NodeDefinition nodeDefinition = processDefinition
						.findActivity(node.getFdActivityId());
				if (nodeDefinition instanceof AbstractManualNode) {
					curNode = (AbstractManualNode) nodeDefinition;
					break;
				}
			}
			
			//判断是否当前节点设置了主文档可编辑状态
			boolean canModifyMainDoc=false;
			if(curNode!=null) {
				if (curNode.isCanModifyMainDoc()) {
					canModifyMainDoc= true;
				}
			}
			
			if(canModifyMainDoc) {
				return true;
			}else {
				// 如果不在可编辑节点，再判断是否勾选附加选项
				KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) form;
				if ("true".equals(kmReviewMainForm.getSysWfBusinessForm()
						.getFdNodeAdditionalInfo().get("allowEdit"))) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	
	
	@Override
	@Separater("/km/review/mobile/edit.jsp")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return super.edit(mapping, form, request, response);
	}

	@Override
	@Separater(value = "/km/review/mobile/add.jsp",viewName = "add_normal")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-add", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String templateId = request.getParameter("fdTemplateId");
			KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
					.findByPrimaryKey(templateId);

			// 外部流程
			if (null != template) {
				if (null != template.getFdIsExternal()
						&& template.getFdIsExternal()) {
					// 模板是否被禁用
					if(template.getFdIsAvailable()!=null && !template.getFdIsAvailable()) {
						request.setAttribute("fdIsAvailable", "false");
						return getActionForward("invalid",mapping,form,request,response);
					}
					// 支持相对路径和绝对路径
					if (template.getFdExternalUrl().indexOf("://") > 0) {
						
						String path = request.getPathInfo();
						if (StringUtil.isNull(path)) {
                            path = request.getServletPath();
                        }
						path = "/".equals(path) ? "" : path;
						path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_HOMEURL : path;
						Boolean isPdaAccess = PdaFlagUtil.checkClientIsPda(request);
						if (isPdaAccess) {
							PrintWriter print=response.getWriter();
							print.write("{\"dataUrl\":\""+template.getFdExternalUrl()+"\"}");
							return null;
						}else {
							response.sendRedirect(template.getFdExternalUrl());
						}
						return null;
					} else {
						String path = request.getPathInfo();
						if (StringUtil.isNull(path)) {
                            path = request.getServletPath();
                        }
						path = "/".equals(path) ? "" : path;
						path = StringUtil.isNull(path) ? PdaFlagUtil.CONST_HOMEURL : path;
						Boolean isPdaAccess = PdaFlagUtil.checkClientIsPda(request);
						if (isPdaAccess) {
							PrintWriter print=response.getWriter();
							print.write("{\"dataUrl\":\""+template.getFdExternalUrl()+"\"}");
							return null;
						}else {
							return new ActionForward(template.getFdExternalUrl(),
									true);
						}
					}
				}
			}

			ActionForm newForm = createNewForm(mapping, form, request,
					response);
			// 设置场所
			if (form instanceof ISysAuthAreaForm) {
				ISysAuthAreaForm sysAuthAreaForm = (ISysAuthAreaForm) form;
				KMSSUser user = UserUtil.getKMSSUser();
				sysAuthAreaForm.setAuthAreaId(user.getAuthAreaId());
				sysAuthAreaForm.setAuthAreaName(user.getAuthAreaName());
			}
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
			Object isAvailable = request.getAttribute("fdIsAvailable");
			if (isAvailable != null
					&& !Boolean.parseBoolean(isAvailable.toString())) {
				return getActionForward("invalid",mapping,form,request,response);
			}
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	/**
	 * 复制流程时检查流程模板表单是否有更新
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void checkTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.getWriter().print(
				checkTemplateUpdate(mapping, form, request, response, null));
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 暂存流程时更新流程实例的版本继承路径
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void updateExtendFilePath(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request, HttpServletResponse response)throws Exception{
		String fdReviewId = request.getParameter("fdReviewId");
		IBaseModel model = getServiceImp(request).findByPrimaryKey(fdReviewId);
		if(model instanceof KmReviewMain){
			KmReviewMain kmReviewMain = (KmReviewMain)model;
			String fdModelId = kmReviewMain.getFdTemplate().getFdId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName("com.landray.kmss.sys.xform.base.model.SysFormTemplate");
			hqlInfo.setSelectBlock(" fdFormFileName ");
			hqlInfo.setWhereBlock(" fdModelId =: fdModelId and fdPcFormId is null");
			hqlInfo.setParameter("fdModelId",fdModelId);
			List list = getSysFormTemplateService().findValue(hqlInfo);
			String fdFilePath = null;
			if(!list.isEmpty()){
				fdFilePath = list.get(0).toString();
			}
			kmReviewMain.setExtendFilePath(fdFilePath);
			getServiceImp(request).update(kmReviewMain);
		}
	}
	/**
	 * 暂存流程时判断流程实例中extendFilePath
	 * 和 表单模板中 fdFormFileName 路径是否相同
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void isSameFilePath(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request, HttpServletResponse response)throws Exception{
		String fdReviewId = request.getParameter("fdReviewId");
		IBaseModel model = getServiceImp(request).findByPrimaryKey(fdReviewId);
		if(model instanceof KmReviewMain) {
			KmReviewMain kmReviewMain = (KmReviewMain) model;
			String fdModelId = kmReviewMain.getFdTemplate().getFdId();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setModelName("com.landray.kmss.sys.xform.base.model.SysFormTemplate");
			hqlInfo.setSelectBlock(" fdFormFileName ");
			hqlInfo.setWhereBlock(" fdModelId =: fdModelId and fdPcFormId is null");
			hqlInfo.setParameter("fdModelId", fdModelId);
			List list = getSysFormTemplateService().findValue(hqlInfo);
			String fdFilePath = null;
			if (!list.isEmpty()) {
				fdFilePath = list.get(0).toString();
			}
			String extendFilePath = kmReviewMain.getExtendFilePath();
			if(extendFilePath.equals(fdFilePath)){
				response.getWriter().print("1");
			}else{
				response.getWriter().print("0");
			}
		}
	}

	private String checkTemplateUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> newDataMap) throws Exception {
		// 初始化模板数据
		((IKmReviewMainService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));
		KmReviewMainForm newForm = (KmReviewMainForm) form;

		// 原文档数据
		String fdReviewId = request.getParameter("fdReviewId");
		KmReviewMainForm copyForm = null;
		KmReviewMain kmReviewMain = (KmReviewMain) getServiceImp(request)
				.findByPrimaryKey(fdReviewId);
		if (kmReviewMain != null) {
			copyForm = (KmReviewMainForm) getServiceImp(request)
					.convertModelToForm(new KmReviewMainForm(), kmReviewMain,
							new RequestContext());
		}
		if (newDataMap != null) {
			newDataMap.putAll(newForm.getExtendDataFormInfo().getFormData());
		}
		return isUpdate(newForm, copyForm);
	}

	/**
	 * 检查模板是否有更新
	 * 
	 * @return
	 */
	private String isUpdate(KmReviewMainForm newForm,
			KmReviewMainForm copyForm) {
		String isUpdate = "false";

		// 新模板数据
		String newFilePath = newForm.getExtendDataFormInfo()
				.getExtendFilePath();
		// 原文档数据
		String copyFilePath = copyForm.getExtendDataFormInfo()
				.getExtendFilePath();

		// 如果新模板没有表单数据，说明新模板是“富文本”模式
		if (StringUtil.isNotNull(newFilePath)) {
			// 如果，新模板是“表单”模式，检查原文档表单与模板表单是否一样
			// 如果表单数据路径不一样，则原文档表单数据有新版本
			if (!newFilePath.equals(copyFilePath)) {
				isUpdate = "true";
			}
		} else {
			// 如果最新模板是富文本，则判断原文档是不是表单
			if (StringUtil.isNotNull(copyFilePath)) {
				isUpdate = "true";
			}
		}
		return isUpdate;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.createNewForm(mapping, form, request, response);
		// 初始化数据
		((IKmReviewMainService) getServiceImp(request)).initFormSetting(
				(IExtendForm) form, new RequestContext(request));

		KmReviewMainForm newForm = (KmReviewMainForm) form;
		newForm.setFdCanCircularize(true);
		// 流程复制
		String fdReviewId = request.getParameter("fdReviewId");
		// 复制流程时，尽可能复制原流程文档的数据，但是对模板表单数据有所处理
		if (StringUtil.isNotNull(fdReviewId)) {
			KmReviewMain kmReviewMain = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(fdReviewId);
			// 判断模板是否禁止复制流程
			if (!kmReviewMain.getFdTemplate().getFdIsCopyDoc()) {
				throw new KmssException(new KmssMessage(
						"km-review:kmReviewTemplate.msg.notCopyDoc"));
			}

			// 复制流程里，不复制文档的“可阅读者”，而需要继承模板中的“可阅读者”
			String authReaderIds = newForm.getAuthReaderIds();
			String authReaderNames = newForm.getAuthReaderNames();
			String authReaderFlag = newForm.getAuthReaderFlag();

			//复制流程里，不复制文档的“附件权限”，而需要继承模板中的“附件权限”
			String authAttCopyIds = newForm.getAuthAttCopyIds();
			String authAttCopyNames = newForm.getAuthAttCopyNames();
			String authAttNoCopy = newForm.getAuthAttNocopy();
			String authAttDownloadIds = newForm.getAuthAttDownloadIds();
			String authAttDownloadNames = newForm.getAuthAttDownloadNames();
			String authAttNodownload = newForm.getAuthAttNodownload();
			String authAttPrintIds = newForm.getAuthAttPrintIds();
			String authAttPrintNames = newForm.getAuthAttPrintNames();
			String authAttNoprint = newForm.getAuthAttNoprint();

			// 新模板是否使用表单
			String fdUseForm = newForm.getFdUseForm();
			// 新模板的流程内容
			String docContent = newForm.getDocContent();
			// 新模板的表单路径
			String extendFilePath = newForm.getExtendDataFormInfo()
					.getExtendFilePath();
			// 新模板的表单内容
			Map<String, Object> newDataMap = new HashMap<String, Object>();
			boolean isUpdate = Boolean.valueOf(checkTemplateUpdate(mapping,
					form, request, response, newDataMap));

			KmReviewMainForm copyForm = null;
			if (kmReviewMain != null) {
				if (!UserUtil.checkAuthentication(
						ModelUtil.getModelUrl(kmReviewMain), "GET")) {
					throw new KmssRuntimeException(new KmssMessage(
							"km-review:kmReviewMain.auth.copy.error"));
				}
				copyForm = (KmReviewMainForm) getServiceImp(request)
						.cloneModelToForm(newForm, kmReviewMain,
								new RequestContext(request));
			}
			// 初始化
			newForm.setFdNumber(null);
			newForm.setDocCreateTime(DateUtil.convertDateToString(new Date(),
					DateUtil.TYPE_DATETIME, request.getLocale()));
			newForm.setDocCreatorId(UserUtil.getUser().getFdId());
			newForm.setDocCreatorName(UserUtil.getUserName(request));
			newForm.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			newForm.setDocPublishTime(null);
			
			// 获取原文档表单数据
			Map<String, Object> oldDataMap = copyForm.getExtendDataFormInfo()
					.getFormData();
			// 如果模板没有更新，则获取文档数据
			if (isUpdate) {
				for (String key : oldDataMap.keySet()) {
					// 从新模板中获取数据
					Object value = newDataMap.get(key);
					if (isNull(value)) {
						// 如果新模板中没有数据，则查询原文档数据（如果新模板有数据，则使用新模板数据）
						value = oldDataMap.get(key);
						if (!isNull(value)) {
							// 如果在原文档中找到数据，则复制到文档中
							newDataMap.put(key, value);
						}
					}
				}
			} else {
				// 模板没有更新，直接获取文档数据
				newDataMap = oldDataMap;
				// 取原文档的RTF内容
				docContent = copyForm.getDocContent();
			}

			// 复制流程时，若表单中含有明细表，明细表的fdId需重新赋值，否则数据映射时会覆盖原有的数据
			DictLoadService sysFormDictLoadService = (DictLoadService) SpringBeanUtil
					.getBean("sysFormDictLoadService");
			SysDictExtendModel dictExtendModel = sysFormDictLoadService
					.loadDictByFileName(extendFilePath);
			Map<String, SysDictCommonProperty> propertyMap = dictExtendModel
					.getPropertyMap();
			for (String key : newDataMap.keySet()) {
				if (propertyMap.containsKey(key) && propertyMap
						.get(key) instanceof SysDictExtendSubTableProperty) {
					List<?> list = (List<?>) newDataMap.get(key);
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i) instanceof Map) {
							((Map) list.get(i)).put("fdId",
									IDGenerator.generateID());
						}
					}
				}
			}
			

			// 加载内容（表单数据）
			newForm.setFdUseForm(fdUseForm);
			newForm.setDocContent(docContent);
			newForm.getExtendDataFormInfo().setExtendFilePath(extendFilePath);
			newForm.getExtendDataFormInfo().getFormData().putAll(newDataMap);

			// 加载模板“可阅读者”权限
			newForm.setAuthReaderFlag(authReaderFlag);
			newForm.setAuthReaderIds(authReaderIds);
			newForm.setAuthReaderNames(authReaderNames);

			//加载模板“附件权限”权限
			newForm.setAuthAttCopyIds(authAttCopyIds);
			newForm.setAuthAttCopyNames(authAttCopyNames);
			newForm.setAuthAttNocopy(authAttNoCopy);
			newForm.setAuthAttDownloadIds(authAttDownloadIds);
			newForm.setAuthAttDownloadNames(authAttDownloadNames);
			newForm.setAuthAttNodownload(authAttNodownload);
			newForm.setAuthAttPrintIds(authAttPrintIds);
			newForm.setAuthAttPrintNames(authAttPrintNames);
			newForm.setAuthAttNoprint(authAttNoprint);
		}
		
		
		if (StringUtil.isNotNull(newForm.getFdTemplateId())) {
			String templateId = newForm.getFdTemplateId();
			KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
					.findByPrimaryKey(templateId);
			Boolean fdIsImport = template.getFdIsImport();
			newForm.setEditDocSubject(template.getEditDocSubject());
			if (fdIsImport != null && fdIsImport ){
				newForm.setFdIsImportXFormData("true");
			}else{
				newForm.setFdIsImportXFormData("false");
			}
			//签章
			newForm.setFdSignEnable(template.getFdSignEnable());
		}
		
		//复制表单数据
		String fdCopyExtendDataId = request.getParameter("fdCopyId");
		if (StringUtil.isNotNull(fdCopyExtendDataId)){
			KmReviewMain kmReviewMain = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(fdCopyExtendDataId);
			copyExtendDataToForm(kmReviewMain,newForm,request);
		}

		// 从模块分类中获取日程机制同步时机，初始化到主文档
		String templateId = newForm.getFdTemplateId();
		if (StringUtil.isNotNull(templateId)) {
			KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
					.findByPrimaryKey(templateId);
			// 复制时可阅读者的权限取模板的设置
			if(StringUtil.isNotNull(fdReviewId) && template != null) {
				((KmReviewMainForm) newForm).setAuthRBPFlag(template.getAuthRBPFlag().toString());
			}
			request.setAttribute("fdIsAvailable", template.getFdIsAvailable());
			// 历史模板默认可传阅 http://rdm.landray.com.cn/issues/40191
			Boolean canCirculate = true;
			if (template.getFdCanCircularize() != null
					&& template.getFdCanCircularize() == false) {
                canCirculate = false;
            }
			// 从模板继承 是否可传阅 http://rdm.landray.com.cn/issues/39773
			newForm.setFdCanCircularize(canCirculate);
			String categoryPath = getCategoryPath(template);
			request.setAttribute("categoryPath", categoryPath);
			request.setAttribute("templateName", template.getFdName());
			String templatePath = StringUtil.isNull(categoryPath)?template.getFdName():categoryPath+"/"+template.getFdName();
			newForm.setFdTemplateName(templatePath);
			newForm.setTitleRegulation(template.getTitleRegulation());
			if (form instanceof ISysAgendaMainForm) {
				newForm.setSyncDataToCalendarTime(template
						.getSyncDataToCalendarTime());
			}
			// 解析实施反馈人
			List fdFeedbacks = template.getFdFeedback();
			List list = null;
			if (fdFeedbacks != null) {
				list = getSysOrgCoreService().parseSysOrgRole(fdFeedbacks,
						getSysOrgCoreService()
								.findByPrimaryKey(
										UserUtil.getUser().getFdId()));
			}
			if (list != null && !list.isEmpty()) {
				String[] orgArray = ArrayUtil
						.joinProperty(list, "fdId:deptLevelNames", ";");
				newForm.setFdFeedbackIds(orgArray[0]);
				newForm.setFdFeedbackNames(orgArray[1]);
			} else {
				newForm.setFdFeedbackIds(null);
				newForm.setFdFeedbackNames(null);
			}
		}
		
		
		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable() && "true".equals(newForm.getFdUseWord())) {
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "流程管理" + date;				
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(templateId);
			sam.setFdModelName("com.landray.kmss.km.review.model.KmReviewTemplate");
            sam.setFdKey("mainContent");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().setWpsOnlineFile(sam, newForm.getFdId(),"com.landray.kmss.km.review.model.KmReviewMain");
			getSysAttMainService().add(attMainFile);
			setAttForm(newForm,attMainFile,"mainContent");

		}
		
		
		
		String fdWorkId = request.getParameter("fdWorkId");
		String fdModelId = request.getParameter("fdModelId");
		String fdModelName = request.getParameter("fdModelName");
		String fdPhaseId = request.getParameter("fdPhaseId");
		if (StringUtil.isNotNull(fdWorkId)) {
			newForm.setFdWorkId(fdWorkId);
			newForm.setFdModelId(fdModelId);
			newForm.setFdModelName(fdModelName);
			newForm.setFdPhaseId(fdPhaseId);
			return newForm;
		} else {
			return newForm;
		}
	}
	
	
	public void setAttForm(KmReviewMainForm reviewMainForm, SysAttMain sysAttMain, String settingKey)
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
		Map reviewAttForms = new HashMap();
		reviewAttForms.put(settingKey, attForms.get(settingKey));
		
		reviewMainForm.getAttachmentForms().putAll(reviewAttForms);
	}
	
	
	/**
	 * 复制历史表单数据到新建文档中
	 * @param kmReviewMain
	 * @param form
	 * @param request
	 * @throws Exception
	 */
	public void copyExtendDataToForm(KmReviewMain kmReviewMain,KmReviewMainForm form,HttpServletRequest request) throws Exception{
		// 判断新旧模板是否使用表单
		String fdUseForm = form.getFdUseForm();
		if (!"true".equalsIgnoreCase(fdUseForm) 
				|| (!kmReviewMain.getFdUseForm())){
			return;
		}
		
		KmReviewTemplate fdTemplate = kmReviewMain.getFdTemplate();
		
		String fdUnImportFieldIds = fdTemplate.getFdUnImportFieldIds();
		List<String> fdUnImportIdList = new ArrayList<String>();
		if (StringUtil.isNotNull(fdUnImportFieldIds)){
			String[] idArr = fdUnImportFieldIds.split(";");
			fdUnImportIdList = ArrayUtil.asList(idArr);
		}
		
		// 新模板的表单路径
		String extendFilePath = form.getExtendDataFormInfo()
							.getExtendFilePath();
		// 新模板的表单内容
		Map<String, Object> newDataMap = new HashMap<String, Object>();

		KmReviewMainForm copyForm = new KmReviewMainForm();
		if (kmReviewMain != null) {
			if (!UserUtil.checkAuthentication(
					ModelUtil.getModelUrl(kmReviewMain), "GET")) {
				throw new KmssRuntimeException(new KmssMessage(
						"km-review:kmReviewMain.auth.copy.error"));
			}
			copyForm = (KmReviewMainForm) getServiceImp(request)
					.cloneModelToForm(copyForm, kmReviewMain,
							new RequestContext(request));
		}

		// 获取原文档表单数据
		Map<String, Object> oldDataMap = copyForm.getExtendDataFormInfo()
				.getFormData();

		// 复制流程时，若表单中含有明细表，明细表的fdId需重新赋值，否则数据映射时会覆盖原有的数据
		DictLoadService sysFormDictLoadService = (DictLoadService) SpringBeanUtil
				.getBean("sysFormDictLoadService");
		SysDictExtendModel dictExtendModel = sysFormDictLoadService
				.loadDictByFileName(extendFilePath);
		Map<String, SysDictCommonProperty> propertyMap = dictExtendModel
				.getPropertyMap();
		for (String key : oldDataMap.keySet()) {
			//移除不需要导入的字段
			String canApplyName = getCanApplyName(key);
			if (fdUnImportIdList.contains(canApplyName)){
				continue;
			}
			if (propertyMap
					.get(key) instanceof SysDictExtendSubTableProperty) {
				List<?> list = (List<?>) oldDataMap.get(key);
				List<Map<String, Object>> subVals = new ArrayList<Map<String,Object>>();
				newDataMap.put(key, subVals);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i) instanceof Map) {
						Map<String,Object> map = (Map<String,Object>)list.get(i);
						Map<String,Object> oneRowVal = new HashMap<String,Object>();
						//移除不需要导入的字段
						for (String subKey : map.keySet()) {
							canApplyName = getCanApplyName(subKey);
							if (fdUnImportIdList.contains(key + "." + canApplyName)){
								continue;
							}
							oneRowVal.put(subKey, map.get(subKey));
						}
						subVals.add(oneRowVal);
						((Map) list.get(i)).put("fdId",
								IDGenerator.generateID());
						
					}
				}
			}else{
				newDataMap.put(key, oldDataMap.get(key));
			}
		}

		// 加载内容（表单数据）
		form.setFdUseForm(fdUseForm);
		form.getExtendDataFormInfo().setExtendFilePath(extendFilePath);
		form.getExtendDataFormInfo().getFormData().putAll(newDataMap);
		
	}
	
	public String getCanApplyName(String name){
		if (StringUtil.isNull(name)){
			return "";
		}
		name = name.replace("_text", "");
		name = name.replace("_name", "");
		name = name.replaceAll("_data\\S+", "");
		return name;
	}

	/**
	 * 判断Object是否为空
	 * 
	 * @param obj
	 * @return
	 */
	private boolean isNull(Object obj) {
		if (obj == null) {
            return true;
        }
		if (obj instanceof String) {
			return StringUtil.isNull((String) obj);
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).size() == 0;
		}
		if (obj instanceof Map) {
			return ((Map) obj).size() == 0;
		}
		return true;
	}

	private List getTemplatePathList(KmReviewTemplate kmReviewTemplate) {
		List pathList = new ArrayList();
		if (kmReviewTemplate != null) {
			pathList.add(kmReviewTemplate.getFdName());
			SysCategoryMain sysCategoryMain = kmReviewTemplate.getDocCategory();
			pathList.add(sysCategoryMain.getFdName());
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
					.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					pathList.add(sysCategoryBaseModel.getFdName());
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
							.getFdParent();
				} while (sysCategoryBaseModel != null);
			}
		}
		Collections.reverse(pathList);
		return pathList;
	}

	private String getCategoryPath(KmReviewTemplate kmReviewTemplate) {
		String categoryPath = "";
		if (kmReviewTemplate != null) {
			SysCategoryMain sysCategoryMain = kmReviewTemplate.getDocCategory();
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					categoryPath = sysCategoryBaseModel.getFdName() + "/" + categoryPath;
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel.getFdParent();
				} while (sysCategoryBaseModel != null);
				categoryPath = categoryPath + sysCategoryMain.getFdName();
			} else {
				categoryPath = sysCategoryMain.getFdName();
			}

		}
		return categoryPath;
	}

	/**
	 * 打印流程文档
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
		HtmlToMht.setLocaleWhenExport(request);
		view(mapping, form, request, response);
		String subject = "";
		String base = "";
		String info = "";
		String note = "";
		String qrcode = "";
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		String statusFlag = "";
		/*#139365-打印时能够打印出来文档的状态标记-结束*/
		KmReviewConfigNotify kmReviewConfigNotify = new KmReviewConfigNotify();
		subject = kmReviewConfigNotify.getSubject();
		base = kmReviewConfigNotify.getBase();
		info = kmReviewConfigNotify.getInfo();
		note = kmReviewConfigNotify.getNote();
		qrcode = kmReviewConfigNotify.getQrcode();
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		statusFlag = kmReviewConfigNotify.getStatusFlag();
		/*#139365-打印时能够打印出来文档的状态标记-结束*/
		request.setAttribute("subject", subject);
		request.setAttribute("base", base);
		request.setAttribute("info", info);
		request.setAttribute("note", note);
		request.setAttribute("qrcode", qrcode);
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		request.setAttribute("statusFlag", statusFlag);
		/*#139365-打印时能够打印出来文档的状态标记-结束*/
		// 引入打印机制
		KmReviewMainForm reviewForm = (KmReviewMainForm) form;
		/*#139365-打印时能够打印出来文档的状态标记-开始*/
		//如果文档为“结束/废弃”状态时，才出现“状态标记”复选框选项（#145079-新增“已反馈”的标记状态）
		if("30".equals(reviewForm.getDocStatus()) || "00".equals(reviewForm.getDocStatus()) || "31".equals(reviewForm.getDocStatus())){
			request.setAttribute("statusFlagShow", "block");
		}
		/*#139365-打印时能够打印出来文档的状态标记-结束*/

		//#107963 文档关联主数据时打印会报错 
		reviewForm.setSysRelationMainForm(null);
		KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
				.findByPrimaryKey(reviewForm.getFdTemplateId());
		boolean enable = getSysPrintMainCoreService()
				.isEnablePrintTemplate(template, null, request);
		KmReviewMain main = (KmReviewMain) getServiceImp(null)
				.convertFormToModel(reviewForm, null,
						new RequestContext(request));
		
		getSysPrintMainCoreService().initPrintData(main, reviewForm, request);
		if (enable) {
			request.setAttribute("isShowSwitchBtn", "true");
		}
		// 打印日志
		getSysPrintLogCoreService().addPrintLog(main,
				new RequestContext(request));
		//
		String printPageType = request.getParameter("_ptype");
		if (enable && !"old".equals(printPageType)) {
			return mapping.findForward("sysprint");
		}
		return mapping.findForward("print");
	}
	
	

	/**
	 * 点阵打印流程文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printLattice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HtmlToMht.setLocaleWhenExport(request);
		view(mapping, form, request, response);
		KmReviewMainForm reviewForm = (KmReviewMainForm) form;
		KmReviewMain main = (KmReviewMain) getServiceImp(null)
				.convertFormToModel(reviewForm, null,
						new RequestContext(request));
		// 打印日志
		getSysPrintLogCoreService().addPrintLog(main,
				new RequestContext(request));
		return mapping.findForward("printLattice");
	}

	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		IBaseModel model = null;
		if (!StringUtil.isNull(id)) {
			model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
                rtnForm = getServiceImp(request).convertModelToForm(
                        (IExtendForm) form, model, new RequestContext(request));
            }
			UserOperHelper.logFind(model);
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		} else {
			KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) rtnForm;
			String templateId = kmReviewMainForm.getFdTemplateId();
			if (StringUtil.isNotNull(templateId)) {
				KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
						.findByPrimaryKey(templateId);
				// 获取路径模板的分类路径
				if (template != null) {
					request.setAttribute("isTempAvailable",template.getFdIsAvailable());
					String categoryPath = getCategoryPath(template);
					request.setAttribute("categoryPath", categoryPath);
					request.setAttribute("templateName", template.getFdName());
					String templatePath = StringUtil.isNull(categoryPath)?template.getFdName():categoryPath+"/"+template.getFdName();
					((KmReviewMainForm) form).setFdTemplateName(templatePath);
					if (!template.getFdIsAvailable()) {
						request.setAttribute("templatePath",
								getTemplatePathList(template));
					}
					// 如果同步时机为空，将模板的同步时机赋予主文档
					if (StringUtil.isNull(kmReviewMainForm
							.getSyncDataToCalendarTime())) {
						((KmReviewMainForm) form)
								.setSyncDataToCalendarTime(template
										.getSyncDataToCalendarTime());
					}
					String disableMobileForm = "false";
					if (template.getFdDisableMobileForm() != null) {
						disableMobileForm = template.getFdDisableMobileForm()
								.toString();
					}
					kmReviewMainForm.setFdDisableMobileForm(disableMobileForm);

					// 默认为支持移动端
					String isMobileCreate = "true";
					String isMobileApprove = "true";
					String fdIsMobileView = "true";
					// 是否可复制流程
					String fdIsCopyDoc = "true";
					if (template.getFdIsMobileCreate() != null) {
						isMobileCreate = template.getFdIsMobileCreate()
								.toString();
					}
					if (template.getFdIsMobileApprove() != null) {
						isMobileApprove = template.getFdIsMobileApprove()
								.toString();
					}
					if (template.getFdIsMobileView() != null) {
						fdIsMobileView = template.getFdIsMobileView()
								.toString();
					}
					if (template.getFdIsCopyDoc() != null) {
						fdIsCopyDoc = template.getFdIsCopyDoc().toString();
					}
					// 支持移动端新建
					kmReviewMainForm.setFdIsMobileCreate(isMobileCreate);
					// 支持移动端审批
					kmReviewMainForm.setFdIsMobileApprove(isMobileApprove);
					// 支持移动端查阅
					kmReviewMainForm.setFdIsMobileView(fdIsMobileView);
					// 是否可复制流程
					kmReviewMainForm.setFdIsCopyDoc(fdIsCopyDoc);

					// 获得反馈数
					int fdReviewFeedbackInfoCount = getKmReviewFeedbackInfoService()
							.getKmReviewFeedbackInfoCount(
									kmReviewMainForm.getFdId());
					if (fdReviewFeedbackInfoCount > 0) {
						kmReviewMainForm.setFdReviewFeedbackInfoCount(
								"(" + String.valueOf(fdReviewFeedbackInfoCount)
										+ ")");
					}
					
					Boolean fdIsImport = template.getFdIsImport();
					if (fdIsImport != null && fdIsImport){
						kmReviewMainForm.setFdIsImportXFormData("true");
					}else{
						kmReviewMainForm.setFdIsImportXFormData("false");
					}
					// 打印多表单上下文
					this.getSysPrintMainCoreService().setTemplateContext(model,
							template, request);
				}
			}
		}
		request
				.setAttribute("pdaViewSubmitAction",
						"/km/review/km_review_main/kmReviewMain.do?method=publishDraft");
		// 判断是否集成了易企签
		Boolean yqqFlag = false;
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		for (IExtension extension : extensions) {
			String channel = Plugin.getParamValueString(extension,
					"channel");
			if ("yqq".equals(channel)) {
				yqqFlag = true;
				break;
			}
		}
		request.setAttribute("yqqFlag", String.valueOf(yqqFlag));
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
		boolean existOpinion = getSysCirculationOpinionService()
				.updateExistOpinion(
						rtnForm.getFdId(), KmReviewMain.class.getName());
		if ("false"
				.equals(new KmReviewConfigNotify().getEnableSysCirculation())) {
			existOpinion = false;
		}
		request.setAttribute("existOpinion", existOpinion);
		if (existOpinion) {
			KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) rtnForm;
			LbpmProcessForm processForm = kmReviewMainForm
					.getSysWfBusinessForm()
					.getInternalForm();
			if ("false".equals(processForm.getFdIsAdmin())
					&& "false".equals(processForm.getFdIsBranchAdmin())
					&& "false".equals(processForm.getFdIsDrafter())
					&& "false".equals(processForm.getFdIsHander())
					&& "false".equals(processForm.getFdIsHistoryHandler())) {
				request.setAttribute("existOpinionAndNoIdentity", true);
			}
		}
		
		//E签宝
		setEqbSignFlag(request, rtnForm, id);
		
	}

	/**
	 * E签宝签署按钮标志
	 * @param request
	 * @param rtnForm
	 * @param id
	 * @throws Exception
	 */
	private boolean setEqbSignFlag(HttpServletRequest request, IExtendForm rtnForm, String id) throws Exception {
		KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) rtnForm;
		request.setAttribute("eqbSignButtonFlag", false);
		ProcessExecuteService processExecuteService = (ProcessExecuteService) SpringBeanUtil
				.getBean("lbpmProcessExecuteService");
		InternalProcessInfoLoader internalProcessInfoLoader = (InternalProcessInfoLoader) SpringBeanUtil
				.getBean("lbpmProcessInfoLoader");
		ProcessInstanceInfo info = processExecuteService
				.load(kmReviewMainForm.getFdId());
		//#170896 兼容历史数据可能存在没有流程实例的情况
		if(info == null){
			logger.warn("对应的流程实例不存在，fdId = {}", kmReviewMainForm.getFdId());
			return false;
		}
		// 判断是否是当前用户
		if (info.isHandler()) {
			// 判断当前用户是否是当前工作项的节点处理人
			LbpmWorkitem workitem = ((LbpmProcess)info.getProcessInstance()).getFdWorkitems().stream().filter(lbpmWorkitem -> internalProcessInfoLoader.isHandler(lbpmWorkitem)).findFirst().orElse(null);
			if(workitem!=null){
				InternalLbpmProcessForm internalForm = new InternalLbpmProcessForm(
						info);
				Map<String,String> nodeIds = internalForm.getNodeAdditionalInfo(workitem.getFdNode().getFdFactNodeId());
				// 是否勾选了E签宝签署附加选项
				if(nodeIds.containsKey("eqbSign")){
					String printConfig = nodeIds.get("eqbSign");
					request.setAttribute("eqbSignButtonFlag", Boolean.valueOf(printConfig));
					return Boolean.valueOf(printConfig);
				}
			}

		}
		/*HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdMainId=:fdMainId");
		hqlInfo.setParameter("fdMainId", id);
		KmReviewOutSign kmReviewOutSign = (KmReviewOutSign) getKmReviewOutSignService().findFirstOne(hqlInfo);
		if(null != kmReviewOutSign) {
			String fdExtmsg = kmReviewOutSign.getFdExtmsg();
			if(StringUtil.isNotNull(fdExtmsg)) {
				com.alibaba.fastjson.JSONObject fdExtmsgMsg = com.alibaba.fastjson.JSONObject.parseObject(fdExtmsg);
				Object obj = fdExtmsgMsg.get("waitingToSignAccount");
				if (obj != null  && obj instanceof List) {
					List<String> waitingToSignAccount = (List<String>) obj;
					KmReviewMainForm kmReviewMainForm = (KmReviewMainForm) rtnForm;
					LbpmProcessForm processForm = kmReviewMainForm.getSysWfBusinessForm().getInternalForm();
					if ("true".equals(processForm.getFdIsHander())
							&& waitingToSignAccount.contains(UserUtil.getUser().getFdId())) {
						request.setAttribute("eqbSignButtonFlag", true);
						return true;
					}
				}
			}
		}*/


		return false;
	}

	/**
	 * 修改反馈人
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeFeedback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			String feedbackIds = ((KmReviewMainForm) form).getFdFeedbackIds();
			String mainId = request.getParameter("fdId");
			KmReviewMain main = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(mainId);

			List feedbackList = main.getFdFeedback();

			Object oldObj = UserOperContentHelper.cloneValue(feedbackList);

			List notifyList = new ArrayList();
			String[] ids = feedbackIds.split(";");
			for (int i = 0; i < ids.length; i++) {
				SysOrgElement feedbackMan = (SysOrgElement) getSysOrgCoreService()
						.findByPrimaryKey(ids[i]);
				if (!feedbackList.contains(feedbackMan)) {
					feedbackList.add(feedbackMan);
					notifyList.add(feedbackMan);
				}
			}
			if (UserOperHelper.allowLogOper("changeFeedback",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putUpdate(main).putSimple("fdFeedback",
						oldObj, feedbackList);
			}

			main.setFdNotifyType(((KmReviewMainForm) form).getFdNotifyType());
			main.setFdFeedbackExecuted(new Long(1));
			kmReviewMainService.updateFeedbackPeople(main, notifyList);
		} catch (Exception e) {
			messages.addError(e);
			e.printStackTrace();
			return mapping.findForward("failure");
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");

	}

	/**
	 * 编辑权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mainId = request.getParameter("fdId");
		List list, returnList;
		if (StringUtil.isNotNull(mainId)) {
			KmReviewMain main = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(mainId);
			list = main.getAuthReaders();
			returnList = ConvertUtil.convertIdsAndNames(list,
					SysOrgElement.class);
			if (returnList.size() > 0) {
                request.setAttribute("oldAuthReaders", returnList.get(1));
            }
			list = main.getFdFeedback();
			returnList = ConvertUtil.convertIdsAndNames(list,
					SysOrgElement.class);
			if (returnList.size() > 0) {
                request.setAttribute("oldFdFeedback", returnList.get(1));
            }
			list = main.getFdLableReaders();
			returnList = ConvertUtil.convertIdsAndNames(list,
					SysOrgElement.class);
			if (returnList.size() > 0) {
                request.setAttribute("oldFdLableReaders", returnList.get(1));
            }
		}
		super.view(mapping, form, request, response);
		return mapping.findForward("editRight");
	}

	/**
	 * 保存权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateRight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysOrgElement user;
		KmReviewMainForm mainForm = (KmReviewMainForm) form;
		String mainId = mainForm.getFdId();
		KmssMessages messages = new KmssMessages();
		try {
			KmReviewMain mainModel = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(mainId);
			List authReaders = new ArrayList();
			if (StringUtil.isNull(mainForm.getAuthReaderIds())) {
                mainModel.setAuthReaders(authReaders);
            } else {
				String ids[] = mainForm.getAuthReaderIds().split(";");
				for (int i = 0; i < ids.length; i++) {
					user = (SysOrgElement) getSysOrgCoreService()
							.findByPrimaryKey(ids[i]);
					authReaders.add(user);
				}
				mainModel.setAuthReaders(authReaders);
			}
			List fdFeedback = new ArrayList();
			if (StringUtil.isNull(mainForm.getFdFeedbackIds())) {
                mainModel.setFdFeedback(fdFeedback);
            } else {
				String ids[] = mainForm.getFdFeedbackIds().split(";");
				for (int i = 0; i < ids.length; i++) {
					user = (SysOrgElement) getSysOrgCoreService()
							.findByPrimaryKey(ids[i]);
					fdFeedback.add(user);
				}
				mainModel.setFdFeedback(fdFeedback);
			}
			List fdLableReaders = new ArrayList();
			if (StringUtil.isNull(mainForm.getFdLableReaderIds())) {
                mainModel.setFdLableReaders(fdLableReaders);
            } else {
				String ids[] = mainForm.getFdLableReaderIds().split(";");
				for (int i = 0; i < ids.length; i++) {
					user = (SysOrgElement) getSysOrgCoreService()
							.findByPrimaryKey(ids[i]);
					fdLableReaders.add(user);
				}
				mainModel.setFdLableReaders(fdLableReaders);
			}
			getServiceImp(request).update(mainModel);
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
			return mapping.findForward("failure");
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		return mapping.findForward("success");
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
			String curOrderBy) throws Exception {
		String orderBy = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		String orderById = ",kmReviewMain.fdId";
		if (StringUtil.isNotNull(orderBy)) {
			String orderbyDesc = orderBy + " desc" + orderById + " desc";
			String orderbyAll = isReserve ? orderbyDesc : orderBy + orderById;
			return orderbyAll;
		}
		return " kmReviewMain.docCreateTime desc" + orderById + " desc ";

	}

	public ActionForward addRelationDoc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("add");
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean(
					"dispatchCoreService");
		}
		return dispatchCoreService;
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return update(mapping, form, request, response);
	}

	// ********** 以下的代码为日程机制需要的代码，删除从业务模板同步数据到时间管理模块 开始**********
	private ISysAgendaMainCoreService sysAgendaMainCoreService;

	public ISysAgendaMainCoreService getSysAgendaMainCoreService() {
		if (sysAgendaMainCoreService == null) {
            sysAgendaMainCoreService = (ISysAgendaMainCoreService) getBean(
                    "sysAgendaMainCoreService");
        }
		return sysAgendaMainCoreService;
	}

	/**
	 * 在list列表中批量删除选定的多条记录。<br>
	 * 表单中，复选框的域名必须为“List_Selected”，其值为记录id。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
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
				StringBuffer buf = new StringBuffer();
				for (String id : ids) {
					if (UserUtil.checkAuthentication(
							"/km/review/km_review_main/kmReviewMain.do?method=delete&fdId="
									+ id,
							"post")) {
						buf.append(id).append(";");
					}
				}
				if (StringUtil.isNotNull(buf.toString())) {
					String[] newids = buf.toString().split(";");
					getServiceImp(request).delete(newids);
				}
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request,
                    response);
        } else {
            return getActionForward("success", mapping, form, request,
                    response);
        }
	}

	// ********** 以下的代码为日程机制需要的代码，删除从业务模板同步数据到时间管理模块 开始**********

	/**
	 * 
	 * 检验是否有模板使用权限
	 * 
	 */
	public ActionForward checkAuth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String hasAuth = "false";
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String fdTempid = request.getParameter("fdTempid");
			KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
					.findByPrimaryKey(fdTempid);
			Boolean flag = template.getFdIsAvailable();
			if (flag == true) {
				if (StringUtil.isNotNull(fdTempid)) {
					if (UserUtil.checkAuthentication(
							"/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="
									+ fdTempid,
							"post")) {
						hasAuth = "true";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-save", false, getClass());
		JSONObject json = new JSONObject();
		json.put("value", hasAuth);
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
		return null;

	}

	public ActionForward showKeydataUsed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-list", true, getClass());
		KmssMessages messages = new KmssMessages();

		String whereBlock = "";
		String keydataIdStr = "";
		String keydataId = request.getParameter("keydataId");
		if (StringUtil.isNotNull(keydataId)) {
			keydataIdStr = " and kmKeydataUsed.keydataId = '" + keydataId + "'";
		}
		// 从kmKeydataUsed表中查找使用了‘keydataId’数据的对应主文档ID（这里指流程管理主文档ID），“kmReviewMainForm”指的是模块的form名称
		whereBlock += "kmReviewMain.fdId in (select kmKeydataUsed.modelId from com.landray.kmss.km.keydata.base.model.KmKeydataUsed kmKeydataUsed"
				+ " where kmKeydataUsed.formName='kmReviewMainForm'"
				+ keydataIdStr + ")";

		// 以下部分可直接参考list中的逻辑代码
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
			// 添加list页面搜索sql语句
			// changeSearchInfoFindPageHQLInfo(request, hqlInfo);
			hqlInfo.setWhereBlock(whereBlock);
			Page page = getServiceImp(request).findPage(hqlInfo);
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
			return getActionForward("list", mapping, form, request, response);
		}
	}

	/**
	 * 处理冒号
	 */
	public ActionForward repairColon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		// 控件ID
		String fdControlId = request.getParameter("fdControlId");
		// 模板ID
		String fdTemplateId = request.getParameter("fdTemplateId");
		// 时间
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 3, 1);

		Date fdDate = cal.getTime();

		if (StringUtil.isNull(fdControlId) || StringUtil.isNull(fdTemplateId)) {
			messages.addError(new Exception("控件 ID 或模板 ID 为空"));
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		}
		IKmReviewMainService service = (IKmReviewMainService) SpringBeanUtil
				.getBean("kmReviewMainService");
		int i = 1;
		int totalRow = 0;
		HQLInfo hql = new HQLInfo();
		hql.setRowSize(1);
		hql.setWhereBlock(
				"kmReviewMain.fdTemplate.fdId = :fdTemplateId and kmReviewMain.docCreateTime>=:fdDate");
		hql.setParameter("fdTemplateId", fdTemplateId);
		hql.setParameter("fdDate", fdDate);
		do {
			hql.setPageNo(i);
			Page page = service.findPage(hql);
			List<KmReviewMain> list = page.getList();
			executeUpdateColon(list, fdControlId, messages);
			totalRow = page.getEndPagingNo();
			i++;
		} while (i <= totalRow);
		// key id value 是 map 的值
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("success");
		}
	}

	private void executeUpdateColon(List<KmReviewMain> list, String fdControlId,
			KmssMessages messages) {
		IKmReviewMainService service = (IKmReviewMainService) SpringBeanUtil
				.getBean("kmReviewMainService");
		try {
			for (KmReviewMain kmReviewMain : list) {
				Map<String, Object> map = kmReviewMain.getExtendDataModelInfo()
						.getModelData();
				Set<String> setKey = map.keySet();
				for (String key : setKey) {
					if (key.equals(fdControlId)) {
						Object obj = map.get(key);
						if (obj instanceof String) {
							map.put(key, obj.toString().replaceAll(":", ""));
						}
					} else if (map.get(key) instanceof List) {
						try {
							// 动态行
							List<Map<String, String>> _multiList = (List<Map<String, String>>) map
									.get(key);
							if (_multiList != null && _multiList.size() > 0) {
								for (Map<String, String> _multiMap : _multiList) {
									Set<String> setMultiKey = _multiMap
											.keySet();
									for (String multikey : setMultiKey) {
										if (multikey.equals(fdControlId)) {
											Object obj = _multiMap
													.get(multikey);
											if (obj instanceof String) {
												_multiMap.put(multikey,
														obj.toString()
																.replaceAll(":",
																		""));
											}
										}
									}
								}
							}
						} catch (Exception e) {
						}
					}
				}
				service.update(kmReviewMain);
			}
		} catch (Exception e) {
			messages.addError(e);
		}
	}

	private ISysCategoryMainService sysCategoryMainService;

	@Override
	public ISysCategoryMainService getCategoryMainService() {
		if (sysCategoryMainService == null) {
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}

	public ActionForward getCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-save", true, getClass());
		KmssMessages messages = new KmssMessages();
		String count = "0";
		int sum = 0;
		JSONObject json = new JSONObject();
		String type = request.getParameter("type");
		HQLInfo hqlInfo = new HQLInfo();
		try {
			hqlInfo.setGettingCount(true);
			if ("draft".equals(type)) {
				StringBuilder whereBlock = new StringBuilder();
				whereBlock.append(" kmReviewMain.docCreator.fdId=:createorId ");
				hqlInfo.setWhereBlock(whereBlock.toString());
				hqlInfo.setParameter("createorId",
						UserUtil.getUser().getFdId());
			} else if ("approved".equals(type)) {
				sum = getServiceImp(request).getApprovedTotal(new RequestContext(request));
			} else {
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.DEFAULT);
			}
			if("approved".equals(type)){
				json.put("count",String.valueOf(sum));
			}else {
				count = getServiceImp(request).getCount(hqlInfo);
				json.put("count", count);
			}
			request.setAttribute("lui-source", json.toString());
		} catch (Exception e) {
			messages.addError(e);
		}
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("lui-source");
		}
	}
	
	
	/**
	 * 自定义模板批量打印
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cusprint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HtmlToMht.setLocaleWhenExport(request);
		view(mapping, form, request, response);
		String subject = "";
		String base = "";
		String info = "";
		String note = "";
		String qrcode = "";
		KmReviewConfigNotify kmReviewConfigNotify = new KmReviewConfigNotify();
		subject = kmReviewConfigNotify.getSubject();
		base = kmReviewConfigNotify.getBase();
		info = kmReviewConfigNotify.getInfo();
		note = kmReviewConfigNotify.getNote();
		qrcode = kmReviewConfigNotify.getQrcode();
		request.setAttribute("subject", subject);
		request.setAttribute("base", base);
		request.setAttribute("info", info);
		request.setAttribute("note", note);
		request.setAttribute("qrcode", qrcode);
		// 引入打印机制
		KmReviewMainForm reviewForm = (KmReviewMainForm) form;
		KmReviewTemplate template = (KmReviewTemplate) getKmReviewTemplateService()
				.findByPrimaryKey(reviewForm.getFdTemplateId());
		boolean enable = getSysPrintMainCoreService()
				.isEnablePrintTemplate(template, null, request);

		KmReviewMain main = (KmReviewMain) getServiceImp(null)
				.convertFormToModel(reviewForm, null,
						new RequestContext(request));
		getSysPrintMainCoreService().initPrintData(main, reviewForm, request);
		request.setAttribute("isShowSwitchBtn", "false");
		// 打印日志
		getSysPrintLogCoreService().addPrintLog(main,
				new RequestContext(request));
		//
		String printPageType = request.getParameter("_ptype");
		return mapping.findForward("cusprint");
	}
	
	
	/**
	 * 批量打印流程文档
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printBatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String xForm = request.getParameter("getXForm");
		if ("1".equals(xForm)) {
			view(mapping, form, request, response);
			return mapping.findForward("printBatch_xform");
		}

		String fdIds = request.getParameter("fdIds");
		String[] fdId = fdIds != null ? fdIds.split(",") : null;
		String base = "";
		String info = "";
		String note = "";
		String qrcode = "";
		KmReviewConfigNotify kmReviewConfigNotify = new KmReviewConfigNotify();
		base = kmReviewConfigNotify.getBase();
		info = kmReviewConfigNotify.getInfo();
		note = kmReviewConfigNotify.getNote();
		qrcode = kmReviewConfigNotify.getQrcode();
		request.setAttribute("base", base);
		request.setAttribute("info", info);
		request.setAttribute("note", note);
		request.setAttribute("qrcode", qrcode);
		List<KmReviewMainForm> reviewFormList = new ArrayList<KmReviewMainForm>();
		List<KmReviewMainForm> reviewFormListCus = new ArrayList<KmReviewMainForm>();
		List<KmReviewMainForm> reviewFormListWord = new ArrayList<KmReviewMainForm>();
		//不支持批量打印的模板
		List<String> subjects = new ArrayList<String>();
		KmReviewMain reviewMain = null;
		for (String id : fdId) {
			// 引入打印机制
			reviewMain = null;
			KmReviewMainForm reviewForm = null;
			reviewMain = (KmReviewMain) getServiceImp(request)
					.findByPrimaryKey(id, KmReviewMain.class, true);
			if (UserOperHelper.allowLogOper("Action_printBatch",
					getServiceImp(request).getModelName())) {
				UserOperContentHelper.putFind(reviewMain);
			}
			KmReviewTemplate template = reviewMain.getFdTemplate();
			
			boolean isEnable = getSysPrintMainCoreService()
					.isEnablePrint(template, null, request);
			
			if (!isEnable && reviewMain != null) {
				//旧版打印
				reviewForm = (KmReviewMainForm) getServiceImp(request)
						.convertModelToForm((IExtendForm) reviewForm,
								reviewMain, new RequestContext(request));
				reviewFormList.add(reviewForm);
			}
			
			if (isEnable && reviewMain != null) {
				if(null!=getSysPrintMainCoreService().getSysPrintTemplate(template, null)){
					String fdPrintMode = getSysPrintMainCoreService().getSysPrintTemplate(template, null).getFdPrintMode();
					//新版打印
					reviewForm = (KmReviewMainForm) getServiceImp(request)
							.convertModelToForm((IExtendForm) reviewForm,
									reviewMain, new RequestContext(request));
					switch (fdPrintMode) {
					case "1":
						//html自定义模板
						reviewFormListCus.add(reviewForm);
						break;
					case "2":
						//Word自定义模板
						reviewFormListWord.add(reviewForm);
						subjects.add(reviewMain.getDocSubject());
						break;
					default:
						break;
					}
				}
			}
		}
		request.setAttribute("isPrintBatch", "true");
		request.setAttribute("kmReviewFormList", reviewFormList);
		request.setAttribute("kmReviewFormListCus", reviewFormListCus);
		request.setAttribute("kmReviewFormListWord", reviewFormListWord);
		request.setAttribute("printSubList", subjects);
		
//		if(reviewFormListCus.size()>0&&reviewFormListWord.size()==0&&reviewFormList.size()==0){
//			//新版HTML自定义模板批量打印
//			return mapping.findForward("printBatchCus");
//		}
		//旧版批量打印或者HTML混合批量打印
		return mapping.findForward("printBatch");
	}

	/**
	 * 获取当前用户最近使用的模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getRecentTemplate(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json; charset=utf-8");
		// 返回结果
		JSONObject rtnJson = new JSONObject();
		// 0成功 非0 失败
		rtnJson.put("errcode", "0");
		rtnJson.put("errmsg", "");
		try {
			String count = request.getParameter("count");
			int cnt = 5;
			if (StringUtil.isNotNull(count)) {
				cnt = Integer.parseInt(count);
			}
			List<Map<String, String>> retVal = ((IKmReviewMainService) getServiceImp(
					request)).getRecentTemplate(cnt);
			rtnJson.put("data", retVal);
		} catch (Exception e) {
			rtnJson.put("errcode", "1");
			rtnJson.put("errmsg", "获取最近使用模板出错");
			logger.error("获取模板过程中出错", e);
		}
		response.getWriter().print(rtnJson.toString());
		return null;
	}

	// ==================================AI接口开始===================================//
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmReviewMainAction.class);

	public ActionForward order(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-update", true, getClass());
		JSONObject json = new JSONObject();
		try {
			String order = request.getParameter("order");
			// type=0是查(默认),type=1是指令
			String type = request.getParameter("type");
			if (StringUtil.isNull(type)) {
                type = "0";
            }
			if (StringUtil.isNotNull(order)) {
				JSONObject jo = null;
				JSONArray ja = new JSONArray();
				HQLInfo info = new HQLInfo();
				info.setWhereBlock("fdName like :name and fdIsAvailable=:fdIsAvailable");
				info.setParameter("fdIsAvailable",Boolean.TRUE);
				info.setParameter("name", "%" + order.trim() + "%");
				info.setOrderBy("fdName desc");
				List<KmReviewTemplate> list = getKmReviewTemplateService()
						.findList(info);
				for (KmReviewTemplate temp : list) {
					jo = new JSONObject();
					jo.put("name", temp.getFdName());
					jo.put("id", temp.getFdId());
					ja.add(jo);
				}
				json.put("errcode", 0);
				json.put("errmsg", "ok");
				json.put("data", ja.toString());
			} else {
				json.put("errcode", 1);
				json.put("errmsg", "指令为空");
				json.put("data", "");
			}
		} catch (Exception e) {
			ExceptionUtils.printRootCauseStackTrace(e);
			json.put("errcode", 1);
			json.put("errmsg", e.getMessage());
			json.put("data", "");
		}

		TimeCounter.logCurrentTime("Action-update", false, getClass());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		logger.debug(json.toString());
		response.getWriter().println(json.toString());
		return null;
	}
	// ==================================AI接口结束===================================//
	
	
	public ActionForward openYqqExtendInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			KmReviewMain kmReviewMain = (KmReviewMain) getServiceImp(
					request).findByPrimaryKey(signId);

			if (kmReviewMain != null) {
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
				request.setAttribute("kmReviewMain", kmReviewMain);
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
		KmssMessages messages = new KmssMessages();
		response.setCharacterEncoding("utf-8");
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
			KmReviewMain kmReviewMain = (KmReviewMain) getServiceImp(
					request).findByPrimaryKey(signId);
			if (kmReviewMain != null) {
				int processType = getProcessType(kmReviewMain.getFdId());
				if (processType == 0) {// 串行
					sendStatus = getKmReviewYqqSignService().sendYqq(
							kmReviewMain,
							phone, fdEnterprise, null);
					if (sendStatus) {
						addOutSign(signId, singer);
					}
				} else if (processType == 1) {// 并行
					sendStatus = getKmReviewYqqSignService().sendYqq(
							kmReviewMain,
							phone, fdEnterprise, null);
					if (sendStatus) {
						addOutSign(signId, singer);
					}
				} else if (processType == 2) {// 会审
					List<SysOrgElement> currentHandlers = getCurrentHandlers(
							signId);
					if (currentHandlers != null && currentHandlers.size() > 0) {
						List<SysOrgPerson> persons = getSysOrgCoreService()
								.expandToPerson(currentHandlers);
						sendStatus = getKmReviewYqqSignService().sendYqq(
								kmReviewMain,
								null, fdEnterprise, persons);
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
				"kmReviewOutSign.fdMainId=:fdMainId and kmReviewOutSign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainId", signId);
		hqlInfo.setParameter("docCreatorId", docCreator.getFdId());
		
		KmReviewOutSign kmReviewOutsignList = (KmReviewOutSign) getKmReviewOutSignService()
				.findFirstOne(hqlInfo);
		if (kmReviewOutsignList != null) {
			return;
		}
		KmReviewOutSign outsign = new KmReviewOutSign();
		outsign.setFdMainId(signId);
		outsign.setFdStatus(KmReviewYqqSignServiceImp.status_code_init);// 发起签订(初始状态)
		outsign.setFdType(KmReviewYqqSignServiceImp.outsign_type_yqq);
		outsign.setDocCreator(docCreator);
		getKmReviewOutSignService().add(outsign);
	}
	
	/**
	 * 点击E签宝去签署按钮
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward eqbSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-eqbSign", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject rtnData = new JSONObject();
		try {
			String signId = request.getParameter("signId");
			rtnData.put("isSigner", false);
			ProcessInstanceInfo processInstanceInfo = getProcessExecuteService().load(signId);
			if (processInstanceInfo != null) {
				if(processInstanceInfo.isHandler()) {
					HQLInfo hqlInfo = new HQLInfo();
					hqlInfo.setWhereBlock("fdMainId=:fdMainId");
					hqlInfo.setParameter("fdMainId", signId);
					KmReviewOutSign kmReviewOutSign = (KmReviewOutSign)getKmReviewOutSignService().findFirstOne(hqlInfo);
					if(null != kmReviewOutSign) {
						String fdExtmsg = kmReviewOutSign.getFdExtmsg();
						if(StringUtil.isNotNull(fdExtmsg)) {
							com.alibaba.fastjson.JSONObject fdExtmsgMsg = com.alibaba.fastjson.JSONObject.parseObject(fdExtmsg);
							Object obj = fdExtmsgMsg.get("waitingToSignAccount");
							com.alibaba.fastjson.JSONObject eqbSignInfo = fdExtmsgMsg.getJSONObject("eqbSignInfo");
							if (obj != null) {
								List<String> waitingToSignAccount = (List<String>) obj;
								if(waitingToSignAccount.contains(UserUtil.getUser().getFdId())) {
									rtnData.put("isSigner", true);
									rtnData.put("signUrl", eqbSignInfo.getString(UserUtil.getUser().getFdId()));
								}
							}
						}
					}
				}
			} 
		} catch (Exception e) {
			messages.addError(e);
			log.error("error", e);
			rtnData.put("error", e.getMessage());
		}
		TimeCounter.logCurrentTime("Action-importSealInfos", false, getClass());
		if (messages.hasError()) {
			rtnData.put("success", false);
		} else {
			rtnData.put("success", true);
		}
		request.setAttribute("lui-source", rtnData);
		return mapping.findForward("lui-source");
	}

	/**
	 * 移动端和Pc端公用此逻辑<br/>
	 * 主要是将签署链接放入request的eqbSignUrl中
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getEqbSignPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-getEqbSignPage", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			request.setAttribute("eqbSignUrl", "");
			ProcessInstanceInfo processInstanceInfo = getProcessExecuteService().load(signId);
			if (processInstanceInfo != null) {
				if(processInstanceInfo.isHandler()) {
					IElecChannelContractService elecEqbSignatureService = null;
					//通过扩展点获取签署接口
					IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.elec.device.contractService",
							IElecChannelRequestMessage.class.getName(), "convertor");
					for (IExtension extension : extensions) {
						String channel = Plugin.getParamValueString(extension,"channel");
						if ("eqbSign".equals(channel)) {
							elecEqbSignatureService = Plugin.getParamValue(extension,"bean");
							break;
						}
					}
					if("host".equals(elecEqbSignatureService.getChannelFlag())){
						//本地化签署链接
						request.setAttribute("eqbSignUrl", getEqbHostSignPage(request, signId));
					} else if ("saas".equals(elecEqbSignatureService.getChannelFlag())) {
						//saas签署链接
						request.setAttribute("eqbSignUrl", getEqbSaasSignPage(request, signId));
					}
				}
			}
		} catch (Exception e) {
			messages.addError(e);
			log.error("error", e);
		}
		TimeCounter.logCurrentTime("Action-getEqbSignPage", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		}
		return getActionForward("sign_eqb", mapping, form, request, response);
	}

	/**
	 *
	 * 获取E签宝本地化签署链接
	 * @param request
	 * @param signId
	 * @return
	 * @throws Exception
	 */
	private String getEqbHostSignPage(HttpServletRequest request, String signId) throws Exception {
		logger.debug("进入Action-getEqbHostSignPage查询签署链接列表方法");
		String eqbSignUrl = "";
		IElecChannelContractService elecEqbSignatureService = null;
		//通过扩展点获取签署接口
		IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		for (IExtension extension : extensions) {
			String channel = Plugin.getParamValueString(extension,"channel");
			if ("eqbSign".equals(channel)) {
				elecEqbSignatureService = Plugin.getParamValue(extension,"bean");
				break;
			}
		}
		logger.debug("Action-getEqbHostSignPage查询签署链接列表signId:{}", signId);
		ElecChannelResponseMessage<List<IElecChannelUrlVO>> responseMessage = elecEqbSignatureService.querySignUrls(signId,null);
		List<IElecChannelUrlVO> elecChannelUrlVOs = responseMessage.getData();
		logger.debug("Action-getEqbHostSignPage查询签署链接列表结果:{}",elecChannelUrlVOs);
		if(CollectionUtils.isNotEmpty(elecChannelUrlVOs)) {
			//如果用户是内部就根据登录名对应，如果是生态组织，就是通过fdId(临时映射表使用的是fdId)。
			if(!UserUtil.getUser().getFdIsExternal()){
				for(IElecChannelUrlVO vo:elecChannelUrlVOs){
					if(UserUtil.getUser().getFdLoginName().equals(vo.getSignUserKey())){
						eqbSignUrl = vo.getSignUrl();
						break;
					}
				}
			} else {
				for(IElecChannelUrlVO vo:elecChannelUrlVOs){
					if(UserUtil.getUser().getFdId().equals(vo.getSignUserKey())){
						eqbSignUrl = vo.getSignUrl();
						break;
					}
				}
			}
		}
		return eqbSignUrl;
	}

	/**
	 *
	 * 获取E签宝SaaS签署链接
	 * @param request
	 * @param signId
	 * @return
	 * @throws Exception
	 */
	private String getEqbSaasSignPage(HttpServletRequest request, String signId) throws Exception {
		logger.debug("进入Action-getEqbSaasSignPage查询签署链接列表方法");
		String eqbSignUrl = "";
		//是当前处理人
		IElecChannelContractService elecEqbSignatureService = null;
		//通过扩展点获取签署接口
		IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		for (IExtension extension : extensions) {
			String channel = Plugin.getParamValueString(extension,"channel");
			if ("eqbSign".equals(channel)) {
				elecEqbSignatureService = Plugin.getParamValue(extension,"bean");
				break;
			}
		}
		logger.debug("Action-getEqbSaasSignPage查询签署链接列表signId:{}", signId);
		ElecChannelResponseMessage<List<IElecChannelUrlVO>> responseMessage = elecEqbSignatureService.querySignUrls(signId,null);
		List<IElecChannelUrlVO> elecChannelUrlVOs = responseMessage.getData();
		logger.debug("Action-getEqbSaasSignPage查询签署链接列表结果:{}",elecChannelUrlVOs);
		if(CollectionUtils.isNotEmpty(elecChannelUrlVOs)) {
			for(IElecChannelUrlVO vo:elecChannelUrlVOs){
				String userName = vo.getSignUserName();//姓名
				String userLicenseNO = vo.getSignUserLicenseNO();//证件号
				if(UserUtil.getUser().getFdName().equals(userName)){
					//TODO 这里应该是通过名称和证件号两个进行比对才行，但是这里取不到当前签署人的证件号，暂时就是以名字为依据。若签署流程中同一个人有多个签署节点就会有问题
					eqbSignUrl = vo.getSignUrl();
					break;
				}
			}
		}
		return eqbSignUrl;
	}

	/**
	 * 检查表单模板是否出了新版本
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void checkIsNewFormVersion(ActionMapping mapping, ActionForm form,
							 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processId = request.getParameter("processId");
		boolean result = false;
		IBaseModel model = kmReviewMainService.findByPrimaryKey(processId);
		KmReviewMain reviewMain = null;
		if(model instanceof KmReviewMain){
			reviewMain = (KmReviewMain) model;
		}
		String filePath = "";
		String commonFilePath = "";
		int formType = 0;
		if(null != reviewMain){
			HQLInfo hql = new HQLInfo();
			hql.setModelName(SysFormTemplate.class.getName());
			hql.setWhereBlock("sysFormTemplate.fdModelId=:fdModelId and sysFormTemplate.fdIsDefWebForm=:fdIsDefWebForm");
			hql.setParameter("fdIsDefWebForm",Boolean.FALSE);
			hql.setParameter("fdModelId", reviewMain.getFdTemplate().getFdId());
			List list = getSysFormTemplateService().findValue(hql);
			if(list.size()>0 && list.get(0) instanceof SysFormTemplate){
				SysFormTemplate sysFormTemplate = (SysFormTemplate)list.get(0);
				filePath = sysFormTemplate.getFdFormFileName();
				if(sysFormTemplate.getFdCommonTemplate() != null) {
					commonFilePath = sysFormTemplate.getFdCommonTemplate().getFdFormFileName();
				}
				formType = sysFormTemplate.getFdMode();
			}
		}
		//表单引用方式为引用其他模板时，表单通用模板走通用模板的路径比对方式
		if(formType == XFormConstant.TEMPLATE_OTHER && reviewMain.getExtendFilePath().equals(commonFilePath)){
			result = true;
		}
		if(formType != XFormConstant.TEMPLATE_OTHER && reviewMain.getExtendFilePath().equals(filePath)){
			result = true;
		}
		response.getWriter()
				.print(result);
	}

}
