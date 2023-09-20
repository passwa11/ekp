package com.landray.kmss.km.imeeting.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.CategoryNodeAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.elec.device.client.IElecChannelRequestMessage;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingSummaryForm;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingOutsign;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutsignService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSummaryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTemplateService;
import com.landray.kmss.km.imeeting.service.IKmImeetingYqqSignService;
import com.landray.kmss.km.imeeting.service.spring.KmImeetingYqqSignServiceImp;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.Attachment;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.category.model.SysCategoryBaseModel;
import com.landray.kmss.sys.category.model.SysCategoryMain;
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
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 会议纪要 Action
 */
public class KmImeetingSummaryAction extends CategoryNodeAction {
	protected IKmImeetingSummaryService kmImeetingSummaryService;
	private IKmImeetingTemplateService kmImeetingTemplateService;
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;
	private ICoreOuterService dispatchCoreService;
	private IKmsMultidocSubsideService kmsMultidocSubsideService;

	private ISysOrgCoreService sysOrgCoreService;
	protected ISysAttMainCoreInnerService sysAttMainService;
	protected IKmsMultidocSubsideService getKmsMultidocSubsideService() {
		if (kmsMultidocSubsideService == null) {
			kmsMultidocSubsideService = (IKmsMultidocSubsideService) getBean(
					"kmsMultidocSubsideService");
		}
		return kmsMultidocSubsideService;
	}
	
	private ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingSummaryService == null) {
            kmImeetingSummaryService = (IKmImeetingSummaryService) getBean("kmImeetingSummaryService");
        }
		return kmImeetingSummaryService;
	}

	protected IKmImeetingTemplateService getKmImeetingTemplateService(
			HttpServletRequest request) {
		if (kmImeetingTemplateService == null) {
			kmImeetingTemplateService = (IKmImeetingTemplateService) getBean("kmImeetingTemplateService");
		}
		return kmImeetingTemplateService;
	}

	public IKmImeetingMainFeedbackService getKmImeetingMainFeedbackService() {
		if (kmImeetingMainFeedbackService == null) {
			kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) getBean("kmImeetingMainFeedbackService");
		}
		return kmImeetingMainFeedbackService;
	}

	protected ICoreOuterService getDispatchCoreService() {
		if (dispatchCoreService == null) {
			dispatchCoreService = (ICoreOuterService) getBean("dispatchCoreService");
		}
		return dispatchCoreService;
	}

	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		ActionForward actionForward = super.save(mapping, form, request,
				response);
		KmImeetingSummaryForm kmImeetingSummaryForm = (KmImeetingSummaryForm) form;
		// 草稿状态跳到编辑页面
		if (ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingSummaryForm
				.getDocStatus())) {
			KmssReturnPage
					.getInstance(request)
					.addMessages(messages)
					.addButton(
							"button.back",
							"kmImeetingSummary.do?method=edit&fdId="
									+ kmImeetingSummaryForm.getFdId(), false)
					.save(request);
		}
		return actionForward;
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		ActionForward actionForward = super.update(mapping, form, request,
				response);
		KmImeetingSummaryForm kmImeetingSummaryForm = (KmImeetingSummaryForm) form;
		// 草稿状态跳到编辑页面
		if (ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingSummaryForm
				.getDocStatus())) {
			KmssReturnPage
					.getInstance(request)
					.addMessages(messages)
					.addButton(
							"button.back",
							"kmImeetingSummary.do?method=edit&fdId="
									+ kmImeetingSummaryForm.getFdId(), false)
					.save(request);
		}
		return actionForward;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmImeetingSummaryForm kmImeetingSummaryForm = (KmImeetingSummaryForm) super
				.createNewForm(mapping, form, request, response);
		String meetingId = request.getParameter("meetingId");

		kmImeetingSummaryForm.setDocCreatorId(UserUtil.getUser().getFdId());
		kmImeetingSummaryForm.setDocCreatorName(UserUtil.getUser().getFdName());
		kmImeetingSummaryForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), DateUtil.TYPE_DATETIME, request.getLocale()));

		if (StringUtil.isNotNull(meetingId)) {
			// 从会议安排中进入纪要录入
			KmImeetingMain kmImeetingMain = (KmImeetingMain) getServiceImp(
					request).findByPrimaryKey(meetingId, KmImeetingMain.class,
					false);
			if (kmImeetingMain != null) {
				((IKmImeetingSummaryService) getServiceImp(request))
						.saveAttachment(meetingId, kmImeetingSummaryForm);

				// 从会议安排中继承属性
				kmImeetingSummaryForm.setFdMeetingId(kmImeetingMain.getFdId());
				kmImeetingSummaryForm.setFdName(kmImeetingMain.getFdName());
				// 主持人
				if (kmImeetingMain.getFdHost() != null) {
					kmImeetingSummaryForm.setFdHostId(kmImeetingMain
							.getFdHost().getFdId());
					kmImeetingSummaryForm.setFdHostName(kmImeetingMain
							.getFdHost().getFdName());
				}
				kmImeetingSummaryForm.setFdOtherHostPerson(kmImeetingMain
						.getFdOtherHostPerson());
				// 地点
				if (kmImeetingMain.getFdPlace() != null) {
					kmImeetingSummaryForm.setFdPlaceId(kmImeetingMain
							.getFdPlace().getFdId());
					kmImeetingSummaryForm.setFdPlaceName(kmImeetingMain
							.getFdPlace().getFdName());
				}
				kmImeetingSummaryForm.setFdOtherPlace(kmImeetingMain
						.getFdOtherPlace());
				kmImeetingSummaryForm.setFdOtherPlaceCoordinate(
						kmImeetingMain.getFdOtherPlaceCoordinate());
				// 分会场
				if (kmImeetingMain.getFdVicePlaces() != null
						&& kmImeetingMain.getFdVicePlaces().size() > 0) {
					String[] properties = ArrayUtil.joinProperty(
							kmImeetingMain.getFdVicePlaces(), "fdId:fdName",
							";");
					kmImeetingSummaryForm.setFdVicePlaceIds(properties[0]);
					kmImeetingSummaryForm.setFdVicePlaceNames(properties[1]);
				}
				kmImeetingSummaryForm.setFdOtherVicePlace(
						kmImeetingMain.getFdOtherVicePlace());
				kmImeetingSummaryForm.setFdOtherVicePlaceCoord(
						kmImeetingMain.getFdOtherVicePlaceCoord());
				// 时间
				kmImeetingSummaryForm.setFdHoldDate(DateUtil
						.convertDateToString(kmImeetingMain.getFdHoldDate(),
								DateUtil.TYPE_DATETIME, request.getLocale()));
				kmImeetingSummaryForm.setFdFinishDate(DateUtil
						.convertDateToString(kmImeetingMain.getFdFinishDate(),
								DateUtil.TYPE_DATETIME, request.getLocale()));
				kmImeetingSummaryForm.setFdHoldDuration(kmImeetingMain
						.getFdHoldDuration().toString());
				// 会议类型
				if (kmImeetingMain.getFdTemplate() != null) {
					KmImeetingTemplate kmImeetingTemplate = kmImeetingMain
							.getFdTemplate();
					kmImeetingSummaryForm.setFdTemplateId(kmImeetingTemplate
							.getFdId());
					kmImeetingSummaryForm.setFdTemplateName(kmImeetingTemplate
							.getFdName());
					kmImeetingSummaryForm.setFdSignEnable(
							kmImeetingTemplate.getFdSignEnable().toString());
					request.setAttribute("fdNeedMultiRes",
							kmImeetingTemplate.getFdNeedMultiRes());
				}
				// 参加人员
				if (kmImeetingMain.getFdAttendPersons() != null
						&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
					String[] strIdsNames = ArrayUtil.joinProperty(
							kmImeetingMain.getFdAttendPersons(), "fdId:fdName",
							";");
					kmImeetingSummaryForm
							.setFdPlanAttendPersonIds(strIdsNames[0]);
					kmImeetingSummaryForm
							.setFdPlanAttendPersonNames(strIdsNames[1]);
				}
				kmImeetingSummaryForm.setFdPlanOtherAttendPerson(kmImeetingMain
						.getFdOtherAttendPerson());
				// 列席人员
				if (kmImeetingMain.getFdParticipantPersons() != null
						&& !kmImeetingMain.getFdParticipantPersons().isEmpty()) {
					String[] strIdsNames = ArrayUtil.joinProperty(
							kmImeetingMain.getFdParticipantPersons(),
							"fdId:fdName", ";");
					kmImeetingSummaryForm
							.setFdPlanParticipantPersonIds(strIdsNames[0]);
					kmImeetingSummaryForm
							.setFdPlanParticipantPersonNames(strIdsNames[1]);
				}
				kmImeetingSummaryForm
						.setFdPlanOtherParticipantPersons(kmImeetingMain
								.getFdOtherParticipantPerson());
				// 实际参与人员继承自参加人员
				ArrayUtil.concatTwoList(kmImeetingMain
						.getFdParticipantPersons(), kmImeetingMain
						.getFdAttendPersons());
				if (kmImeetingMain.getFdAttendPersons() != null
						&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
					String[] strIdsNames = ArrayUtil.joinProperty(
							kmImeetingMain.getFdAttendPersons(), "fdId:fdName",
							";");
					kmImeetingSummaryForm
							.setFdActualAttendPersonIds(strIdsNames[0]);
					kmImeetingSummaryForm
							.setFdActualAttendPersonNames(strIdsNames[1]);
				}
				String otherPerson = "";
				if (StringUtil.isNotNull(kmImeetingMain
						.getFdOtherAttendPerson())) {
					otherPerson += kmImeetingMain.getFdOtherAttendPerson()+" ";
				}
				if (StringUtil.isNotNull(kmImeetingMain
						.getFdOtherParticipantPerson())) {
					otherPerson += kmImeetingMain.getFdOtherParticipantPerson()
							+ " ";
				}
				kmImeetingSummaryForm
						.setFdActualOtherAttendPersons(otherPerson);
				// 抄送人员
				if (kmImeetingMain.getFdCopyToPersons() != null
						&& !kmImeetingMain.getFdCopyToPersons().isEmpty()) {
					String[] strIdsNames = ArrayUtil.joinProperty(
							kmImeetingMain.getFdCopyToPersons(), "fdId:fdName",
							";");
					kmImeetingSummaryForm.setFdCopyToPersonIds(strIdsNames[0]);
					kmImeetingSummaryForm
							.setFdCopyToPersonNames(strIdsNames[1]);
				}
				// 会议组织人
				if (kmImeetingMain.getFdEmcee() != null) {
					kmImeetingSummaryForm.setFdEmceeId(kmImeetingMain
							.getFdEmcee().getFdId());
					kmImeetingSummaryForm.setFdEmceeName(kmImeetingMain
							.getFdEmcee().getFdName());
				}
				// 组织部门
				if (kmImeetingMain.getDocDept() != null) {
					kmImeetingSummaryForm.setDocDeptId(kmImeetingMain
							.getDocDept().getFdId());
					kmImeetingSummaryForm.setDocDeptName(kmImeetingMain
							.getDocDept().getFdName());
				}
				// 会议纪要内容
				if (kmImeetingMain.getFdTemplate() != null) {
					kmImeetingSummaryForm.setDocContent(kmImeetingMain
							.getFdTemplate().getFdSummaryContent());
				}
				// 调用模板流程
				getDispatchCoreService().initFormSetting(kmImeetingSummaryForm,
						"ImeetingSummary", kmImeetingMain.getFdTemplate(),
						"ImeetingSummary", new RequestContext(request));
			}
			
		} else {
			// 直接新建纪要
			String fdTemplateId = request.getParameter("fdTemplateId");
			if (StringUtil.isNotNull(fdTemplateId)) {
				KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) getKmImeetingTemplateService(
						request).findByPrimaryKey(fdTemplateId);
				if (kmImeetingTemplate != null) {
					kmImeetingSummaryForm.setFdTemplateId(fdTemplateId);
					kmImeetingSummaryForm.setFdTemplateName(kmImeetingTemplate
							.getFdName());
					// 会议纪要内容
					kmImeetingSummaryForm.setDocContent(kmImeetingTemplate
							.getFdSummaryContent());
					kmImeetingSummaryForm.setFdSignEnable(
							kmImeetingTemplate.getFdSignEnable().toString());
					// 调用模板流程
					getDispatchCoreService().initFormSetting(
							kmImeetingSummaryForm, "ImeetingSummary",
							kmImeetingTemplate, "ImeetingSummary",
							new RequestContext(request));
					request.setAttribute("fdNeedMultiRes",
							kmImeetingTemplate.getFdNeedMultiRes());
				}
				
				
				
			}
		}
		KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
		String notifyPerson = kmImeetingConfig.getSummaryNotifyPerson();
		if (StringUtil.isNotNull(notifyPerson)) {
			String[] notifyPersonList = notifyPerson.split(";");
			kmImeetingSummaryForm.setFdNotifyPersonList(notifyPersonList);
		}
		
		//WPS加载项使用
		if(SysAttWpsoaassistUtil.isEnable()) {
			Date currTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String date = sdf.format(currTime);
			String docuName = "会议管理" + date;				
			SysAttMain sam = new SysAttMain();
			sam.setFdModelId(kmImeetingSummaryForm.getFdId());
			sam.setFdModelName("com.landray.kmss.km.imeeting.model.KmImeetingSummary");
            sam.setFdKey("editonline");
			sam.setFdFileName(docuName);
			SysAttMain attMainFile = getSysAttMainService().addWpsOaassistOnlineFile(sam);
			setAttForm(kmImeetingSummaryForm,attMainFile,"editonline");

		}
		
		return kmImeetingSummaryForm;
	}

	public void setAttForm(KmImeetingSummaryForm templateForm, SysAttMain sysAttMain, String settingKey)
			throws Exception {
		IAttachment att = new Attachment();
		Map attForms = att.getAttachmentForms();
		AttachmentDetailsForm attForm = (AttachmentDetailsForm) attForms.get(settingKey);
		attForm.setFdModelId("");
		attForm.setFdModelName("com.landray.kmss.km.imeeting.forms.KmImeetingSummaryForm");
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
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.loadActionForm(mapping, form, request, response);
		KmImeetingSummaryForm summaryForm = (KmImeetingSummaryForm) form;
		String templateId = summaryForm.getFdTemplateId();
		if (StringUtil.isNotNull(templateId)) {
			KmImeetingTemplate template = (KmImeetingTemplate) getKmImeetingTemplateService(
					request).findByPrimaryKey(templateId);
			request.setAttribute("fdNeedMultiRes",
					template.getFdNeedMultiRes());
		}

		// 判断是否集成了易企签
		Boolean yqqFlag = false;
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.elec.device.contractService",
				IElecChannelRequestMessage.class.getName(), "convertor");
		for (IExtension extension : extensions) {
			String channel = Plugin.getParamValueString(extension, "channel");
			if ("yqq".equals(channel)) {
				yqqFlag = true;
				break;
			}
		}
		request.setAttribute("yqqFlag", String.valueOf(yqqFlag));
	}

	public ActionForward operateSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String meetingId = request.getParameter("meetingId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(" kmImeetingSummary.fdMeeting.fdId=:meetingId ");
		hqlInfo.setParameter("meetingId", meetingId);
		List<KmImeetingSummary> summarys = getServiceImp(request).findValue(
				hqlInfo);
		UserOperHelper.logFindAll(summarys,
				getServiceImp(request).getModelName());
		String linkStr = "";
		if (summarys != null && !summarys.isEmpty()) {
			KmImeetingSummary kmImeetingSummary = summarys.get(0);
			String fdId = kmImeetingSummary.getFdId();
			linkStr = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view&fdId="
					+ fdId;
		} else {
			linkStr = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=add&meetingId="
					+ meetingId;
		}
		request.setAttribute("redirectto", linkStr);
		return new ActionForward("/resource/jsp/redirect.jsp");
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
			HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		CriteriaValue cv = new CriteriaValue(request);

		// 分类导航查询条件
		String categoryId = cv.poll("fdTemplate");
		if (StringUtil.isNotNull(categoryId)) {
			// 默认 show all
			SysCategoryMain category = (SysCategoryMain) getCategoryMainService()
					.findByPrimaryKey(categoryId, null, true);
			if (category != null) {
				hqlInfo
						.setWhereBlock(StringUtil
								.linkString(hqlInfo.getWhereBlock(), " and ",
										" kmImeetingSummary.fdTemplate.docCategory.fdHierarchyId like :category "));
				hqlInfo.setParameter("category", category.getFdHierarchyId()
						+ "%");
			} else {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo
						.getWhereBlock(), " and ",
						" kmImeetingSummary.fdTemplate.fdId = :template "));
				hqlInfo.setParameter("template", categoryId);
			}
		}
		
		String except = cv.poll("except");
		String[] exceptValue = null;
		if(StringUtil.isNotNull(except)&&except.indexOf(":")>-1){
			exceptValue=except.split(":");
		}
		
		if(exceptValue!=null){
			String whereBlock=hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = " 1=1 ";
			}
			if (exceptValue[1].indexOf("_") > -1) {
				String[] _exceptValue = exceptValue[1].split("_");
				for (int i = 0; i < _exceptValue.length; i++) {
					whereBlock = whereBlock + " and kmImeetingSummary.docStatus != :docStatus" + i;
					hqlInfo.setParameter("docStatus" + i, _exceptValue[i]);
				}
			} else {
				whereBlock = whereBlock + " and kmImeetingSummary.docStatus != :docStatus";
				hqlInfo.setParameter("docStatus", exceptValue[1]);
			}
			if(StringUtil.isNotNull(whereBlock)){
				hqlInfo.setWhereBlock(whereBlock);
			}
			
		}
		String docStatus = cv.poll("docStatus");
		if(StringUtil.isNull(docStatus)){
			cv.remove("docStatus");
			}else{
				String whereBlock = hqlInfo.getWhereBlock();
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" kmImeetingSummary.docStatus = :docStatus ");
					hqlInfo.setParameter("docStatus", docStatus);
					hqlInfo.setWhereBlock(whereBlock);
			}

		// 我的纪要查询条件
		String mysummary = cv.poll("mysummary");
		if (StringUtil.isNotNull(mysummary)) {
			buildMySummaryHql(request, hqlInfo, mysummary);
		} else {
			String tasummary = cv.poll("tasummary");
			if (StringUtil.isNotNull(tasummary)) {
				buildTASummaryHql(request, hqlInfo, tasummary);
			}
		}

		CriteriaUtil.buildHql(cv, hqlInfo, KmImeetingSummary.class);
	}

	/**
	 * 我的纪要HQL
	 */
	private void buildMySummaryHql(HttpServletRequest request, HQLInfo hqlInfo,
			String mysummary) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		// 我录入的
		if ("myCreate".equals(mysummary)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingSummary.docCreator.fdId=:creatorId ");
			hqlInfo.setParameter("creatorId", UserUtil.getUser().getFdId());
			hqlInfo.setWhereBlock(whereBlock);
		} else if ("myApproved".equals(mysummary)) {
			// 待我审的
			SysFlowUtil.buildLimitBlockForMyApproved("kmImeetingSummary",
					hqlInfo);
		} else if ("myApproval".equals(mysummary)) {
			// 我已审的
			SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingSummary",
					hqlInfo);
		} else if ("myAttend".equals(mysummary)) {
			// 我参与的
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingSummary.fdActualAttendPersons.fdId=:personId ");
			hqlInfo.setParameter("personId", UserUtil.getUser().getFdId());
			hqlInfo.setWhereBlock(whereBlock);
		}
	}

	/**
	 * TA的纪要HQL
	 */
	private void buildTASummaryHql(HttpServletRequest request, HQLInfo hqlInfo,
			String tasummary) throws Exception {
		String userid = request.getParameter("userid");
		if (StringUtil.isNull(userid)) {
			return;
		}
		String whereBlock = hqlInfo.getWhereBlock();
		// 我录入的
		if ("create".equals(tasummary)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingSummary.docCreator.fdId=:creatorId ");
			hqlInfo.setParameter("creatorId", userid);
			hqlInfo.setWhereBlock(whereBlock);
		}
	}

	// 返回会议类别完整路径
	private String getTemplatePath(KmImeetingTemplate kmImeetingTemplate) {
		String templatePath = "";
		if (kmImeetingTemplate != null) {
			SysCategoryMain sysCategoryMain = kmImeetingTemplate
					.getDocCategory();
			SysCategoryBaseModel sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryMain
					.getFdParent();
			if (sysCategoryBaseModel != null) {
				do {
					templatePath = sysCategoryBaseModel.getFdName() + "/"
							+ templatePath;
					sysCategoryBaseModel = (SysCategoryBaseModel) sysCategoryBaseModel
							.getFdParent();
				} while (sysCategoryBaseModel != null);
				templatePath = templatePath + sysCategoryMain.getFdName() + "/"
						+ kmImeetingTemplate.getFdName();
			} else {
				templatePath = sysCategoryMain.getFdName() + "/"
						+ kmImeetingTemplate.getFdName();
			}
		}
		return templatePath;
	}

	// 分类扩充权限文档维护
	public ActionForward optAllList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-optAllList", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			super.listChildren(mapping, form, request, response);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-optAllList", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("optAllList", mapping, form, request,
					response);
		}
	}

	/**
	 * 分类转移
	 */
	public ActionForward changeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		String templateId = request.getParameter("templateId");
		String ids = ArrayUtil.concat(
				request.getParameterValues("List_Selected"), ';');
		try {
			((IKmImeetingSummaryService) getServiceImp(request))
					.updateDucmentTemplate(ids, templateId);
		} catch (Exception e) {
			messages.addError(e);
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			getActionForward("failure", mapping, form, request, response);
		}
		KmssReturnPage.getInstance(request).addMessages(messages)
				.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
		return getActionForward("success", mapping, form, request, response);
	}

	@Override
	protected String getParentProperty() {
		return "fdTemplate";
	}
	
	/**
	 * 打印会议纪要
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
		return mapping.findForward("print");
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
			KmImeetingSummary mainModel = (KmImeetingSummary) getServiceImp(
					request)
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
            return getActionForward("subFilePrint", mapping, form, request,
                    response);
        }
	}

	public ActionForward openYqqExtendInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-openYqqExtendInfo", true,
				getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) getServiceImp(
					request).findByPrimaryKey(signId);
			SysOrgPerson user = UserUtil.getUser();
			List<SysOrgPerson> signPersons = kmImeetingSummary
					.getFdSignPersons();
			request.setAttribute("fdSigner", user);
			request.setAttribute("phone", user.getFdMobileNo());
			request.setAttribute("signPersons", signPersons);
			request.setAttribute("kmImeetingSummary", kmImeetingSummary);
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

	public ActionForward sendEasyYqq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-sendYqq", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject rtnObject = new JSONObject();
		Boolean sendStatus = true;
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
			KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) getServiceImp(
					request).findByPrimaryKey(signId);
			
			// 发送易企签调用
			IKmImeetingYqqSignService kmImeetingYqqSignService = (IKmImeetingYqqSignService) SpringBeanUtil
					.getBean("kmImeetingYqqSignService");

			sendStatus = kmImeetingYqqSignService.sendEasyYqq(
					kmImeetingSummary, phone,
					fdEnterprise);
			rtnObject.put("signId", signId);
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-sendYqq", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.save(request);
			return getActionForward("failure", mapping, form, request,
					response);
		} else {
			rtnObject.put("sendStatus", String.valueOf(sendStatus));
			response.setCharacterEncoding("utf-8");
			response.getWriter().println(rtnObject.toString());
			return null;
		}
	}

	/**
	 * 调用易企签服务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendYqq(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-sendYqq", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			String signId = request.getParameter("signId");
			KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) getServiceImp(
					request).findByPrimaryKey(signId);
			int processType = getProcessType(kmImeetingSummary.getFdId());
			if (processType == 0) {// 串行
				sendSerialYqq(request, "0");
			} else if (processType == 1) {// 并行
				sendSerialYqq(request, "1");
			} else if (processType == 2) {// 会审
				sendOtherYqq(request);
			}

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
			return getActionForward("yqqLoading", mapping, form, request,
					response);
		}
	}

	private void sendOtherYqq(HttpServletRequest request) throws Exception {
		String signId = request.getParameter("signId");
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) getServiceImp(
				request).findByPrimaryKey(signId);
		if (kmImeetingSummary != null) {
			List<SysOrgElement> currentHandlers = getCurrentHandlers(signId);
			if (currentHandlers != null && currentHandlers.size() > 0) {
				List<SysOrgPerson> persons = getSysOrgCoreService()
						.expandToPerson(currentHandlers);
				// 发送易企签调用
				IKmImeetingYqqSignService kmImeetingYqqSignService = (IKmImeetingYqqSignService) SpringBeanUtil
						.getBean("kmImeetingYqqSignService");
				kmImeetingYqqSignService.sendYqq(kmImeetingSummary, null,
						persons, null);
				for (SysOrgElement person : persons) {
					addOutSign(signId, person);
				}
			}
		}

	}

	private void sendSerialYqq(HttpServletRequest request, String processType)
			throws Exception {
		String signId = request.getParameter("signId");
		String phone = request.getParameter("phone");
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) getServiceImp(
				request).findByPrimaryKey(signId);
		// 更新我方签约人手机信息到sys_org_person
		SysOrgPerson singer = UserUtil.getUser();
		ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
				.getBean("sysOrgPersonService");
		singer.setFdMobileNo(phone);
		sysOrgPersonService.update(singer);

		// 发送易企签调用
		IKmImeetingYqqSignService kmImeetingYqqSignService = (IKmImeetingYqqSignService) SpringBeanUtil
				.getBean("kmImeetingYqqSignService");

		kmImeetingYqqSignService.sendYqq(kmImeetingSummary, phone, null,
				processType);
		addOutSign(signId, UserUtil.getUser());
	}

	private void addOutSign(String signId, SysOrgElement docCreator)
			throws Exception {
		// 记录签订状态
		IKmImeetingOutsignService kmImeetingOutsignService = (IKmImeetingOutsignService) SpringBeanUtil
				.getBean("kmImeetingOutsignService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmImeetingOutsign.fdMainid=:fdMainid and kmImeetingOutsign.docCreator.fdId =:docCreatorId");
		hqlInfo.setParameter("fdMainid", signId);
		hqlInfo.setParameter("docCreatorId", docCreator.getFdId());
		List<KmImeetingOutsign> kmImeetingOutsignList = kmImeetingOutsignService
				.findList(hqlInfo);
		if (kmImeetingOutsignList != null
				&& kmImeetingOutsignList.size() > 0) {
			return;
		}
		KmImeetingOutsign outsign = new KmImeetingOutsign();
		outsign.setFdMainid(signId);
		outsign.setFdStatus(KmImeetingYqqSignServiceImp.status_code_init);// 发起签订(初始状态)
		outsign.setFdType(KmImeetingYqqSignServiceImp.outsign_type_yqq);
		outsign.setDocCreator(docCreator);
		kmImeetingOutsignService.add(outsign);
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
	 * @param process
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

	/**
	 * 校验是否有手机号
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkPhoneNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-checkPhoneNo", true, getClass());
		KmssMessages messages = new KmssMessages();
		JSONObject json = new JSONObject();
		try {
			String attendPersonIds = request.getParameter("attendPersonIds");
			String[] personIds = attendPersonIds.split(";");
			List<SysOrgElement> elements = getSysOrgCoreService()
					.findByPrimaryKeys(personIds);
			List<SysOrgPerson> persons = getSysOrgCoreService()
					.expandToPerson(elements);
			StringBuilder sb = new StringBuilder();
			for (SysOrgPerson sysOrgPerson : persons) {
				if (StringUtil.isNull(sysOrgPerson.getFdMobileNo())) {
					sb.append(sysOrgPerson.getFdName()).append(";");
				}
			}
			if (StringUtil.isNotNull(sb.toString())) {
				json.put("names", sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			messages.addError(e);
		}
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().write(json.toString());// 结果
		TimeCounter.logCurrentTime("Action-checkPhoneNo", false, getClass());
		return null;
	}
}
