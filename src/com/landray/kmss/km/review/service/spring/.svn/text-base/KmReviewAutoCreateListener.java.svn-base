package com.landray.kmss.km.review.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.ICoreOuterService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.review.forms.KmReviewMainForm;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.lbpm.engine.builder.WorkitemInstance;
import com.landray.kmss.sys.lbpm.engine.manager.event.IStartEventListener;
import com.landray.kmss.sys.lbpm.engine.manager.event.StartEventContext;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmTemplate;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.workflow.webservice.DefaultStartParameter;
import com.landray.kmss.sys.workflow.webservice.WorkFlowParameterInitializer;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
 * 创建日期 2016-08-26
 * 
 * @author 林彬彬 流程管理自动触发新建流程监听器
 */
public class KmReviewAutoCreateListener implements IStartEventListener {
	private IKmReviewMainService kmReviewMainService;
	protected IKmReviewTemplateService kmReviewTemplateService;
	private ISysOrgCoreService sysOrgCoreService;
	protected ICoreOuterService dispatchCoreService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private IBackgroundAuthService backgroundAuthService;
	/** 流程运行服务 */
	private ProcessExecuteService processExecuteService;
	
	public void setProcessExecuteService(
			ProcessExecuteService processExecuteService) {
		this.processExecuteService = processExecuteService;
	}

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	public void setKmReviewTemplateService(
			IKmReviewTemplateService kmReviewTemplateService) {
		this.kmReviewTemplateService = kmReviewTemplateService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setDispatchCoreService(ICoreOuterService dispatchCoreService) {
		this.dispatchCoreService = dispatchCoreService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setBackgroundAuthService(
			IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(StartEventContext startContext,
			String parameter) throws Exception {
		JSONObject parm = JSONObject.fromObject(parameter);// 参数信息
		if (parm.has("personIds")) {
			String personIds = parm.getString("personIds");
			if (StringUtil.isNotNull(personIds)) {
				List<String> personIdArray = ArrayUtil
						.convertArrayToList(personIds
						.split(";"));
				List<SysOrgPerson> persons = sysOrgCoreService
						.expandToPerson(personIdArray);
				LbpmTemplate lbpmTemplate = (LbpmTemplate) startContext
						.getProcessTemplate();
				KmReviewTemplate kmReviewTemplate = (KmReviewTemplate) kmReviewTemplateService
						.findByPrimaryKey(lbpmTemplate.getFdModelId());
				// 判断模板是否有效，不是不自动创建流程
				if (!kmReviewTemplate.getFdIsAvailable()){
					return;
				}

				TransactionStatus status = null;
				int count = 0;

				for (SysOrgPerson person : persons) {
					if (person == null || !person.getFdIsAvailable()) {
                        continue;
                    }
					if (status == null) {
						status = TransactionUtils.beginTransaction();
					}
					try {
						backgroundAuthService.switchUser(person, new Runner() {
							@Override
							public Object run(Object parameter)
									throws Exception {
								Object[] arguments = (Object[]) parameter;
								SysOrgPerson person = (SysOrgPerson) arguments[0];
								KmReviewTemplate kmReviewTemplate = (KmReviewTemplate) arguments[1];
								JSONObject parm = (JSONObject) arguments[2];
								// 判断是否模板可使用者，不是不自动创建流程
								List<?> authReaders = kmReviewTemplate
										.getAuthReaders();
								if (!authReaders.isEmpty()
										&& !UserUtil
												.checkUserModels(authReaders)
										|| kmReviewTemplate
												.getAuthNotReaderFlag()
										|| !UserUtil
												.checkAuthentication(
														"/km/review/km_review_main/kmReviewMain.do?method=add",
														"post")) {
                                    return null;
                                }

								KmReviewMainForm kmReviewMainForm = new KmReviewMainForm();
								
								String titleRegulation = parm.getString("titleRegulation");
								kmReviewMainForm.setTitleRegulation(strDeEscape(titleRegulation));
								String oldTitleRegulation = kmReviewTemplate.getTitleRegulation();
								kmReviewTemplate
										.setTitleRegulation(kmReviewMainForm
												.getTitleRegulation());
								
								// 是否跳过起草节点
								if (parm.has("skipDraftNode") && "true".equals(
										parm.getString("skipDraftNode"))) {
									kmReviewMainForm.setDocStatus(
											SysDocConstant.DOC_STATUS_EXAMINE);
								} else {
									kmReviewMainForm.setDocStatus(
											SysDocConstant.DOC_STATUS_DRAFT); 
								}

								setKmReviewTemplate(kmReviewMainForm,
										kmReviewTemplate, person);
								KmReviewMain kmReviewMain = (KmReviewMain) kmReviewMainService
										.findByPrimaryKey(kmReviewMainForm
												.getFdId());
								
								if (parm.has("skipDraftNode") && "true".equals(
										parm.getString("skipDraftNode"))) {
									sendAutoCreateToRead(kmReviewMain,
											parm.getString("notifyTypes"));
								} else {
									sendAutoCreateNotify(kmReviewMain,
											parm.getString("notifyTypes"));
								}
								kmReviewTemplate.setTitleRegulation(oldTitleRegulation);
								return null;
							}
						}, new Object[] { person, kmReviewTemplate, parm });
						count++;
						// 平均100次提交一次事务
						if (count == 100) {
							TransactionUtils.getTransactionManager().commit(
									status);
							status = null;
							count = 0;
						}
					} catch (Exception e) {
						TransactionUtils.rollback(status);
						throw e;
					}
				}
				if (status != null) {
					try{
						TransactionUtils.getTransactionManager().commit(status);
					}
					catch (Exception e){
						TransactionUtils.rollback(status);
					}
				}
			}
		}
	}

	private void setKmReviewTemplate(KmReviewMainForm kmReviewMainForm,
			KmReviewTemplate kmReviewTemplate, SysOrgPerson person)
			throws Exception {
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("fdTemplateId", kmReviewTemplate.getFdId());
		// 初始化
		DefaultStartParameter param = new DefaultStartParameter();
		param.setDocStatus(kmReviewMainForm.getDocStatus());

		kmReviewMainService.initFormSetting(kmReviewMainForm, requestContext);
		WorkFlowParameterInitializer.initialize(kmReviewMainForm,
				param);
		// 设置流程类型
		kmReviewMainForm.setFdTemplateId(kmReviewTemplate.getFdId());
		kmReviewMainForm.setFdTemplateName(kmReviewTemplate.getFdName());
		 
		// 创建时间
		kmReviewMainForm.setDocCreateTime(DateUtil.convertDateToString(
				new Date(), DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
						.getLocale()));
		// 设置主题
		kmReviewMainForm.setDocSubject("");
		
		// 设置流程创建者
		kmReviewMainForm.setDocCreatorId(person.getFdId());
		kmReviewMainForm.setDocCreatorName(person.getFdName());
		// 设置所属部门
		SysOrgElement dept = person.getFdParent();
		if (dept != null) {
			kmReviewMainForm.setFdDepartmentName(dept.getFdName());
		}
		// 设置所在岗位
		List<?> posts = person.getFdPosts();
		if (posts != null && !posts.isEmpty()) {
			String[] fdPostNames = ArrayUtil.joinProperty(posts, "fdId:fdName",
					";");
			kmReviewMainForm.setFdPostIds(fdPostNames[0]);
			kmReviewMainForm.setFdPostNames(fdPostNames[1]);
		}
		kmReviewMainService.add(kmReviewMainForm, requestContext);
	}

	
	private void sendAutoCreateToRead(KmReviewMain kmReviewMain, String notifyType) throws Exception {

		NotifyContext notifyContext = sysNotifyMainCoreService.getContext("km-review:kmReviewMain.autoCreateToRead");
		// 待阅

		// HashMap<String, String> hashMap = new HashMap<String, String>();
		// hashMap.put("km-review:kmReviewMain.docSubject", kmReviewMain
		// .getDocSubject());
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-review:kmReviewMain.docSubject", kmReviewMain.getDocSubject());
		List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
		receiver.add(kmReviewMain.getDocCreator());
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyType(notifyType);// 通知方式
		notifyContext.setNotifyTarget(receiver);// 通知人员
		ProcessInstanceInfo processInstanceInfo = processExecuteService.load(kmReviewMain.getFdId());
		if (processInstanceInfo != null) {
			List<WorkitemInstance> workitems = processInstanceInfo.getCurrentWorkitems();
			for (WorkitemInstance instance : workitems) {
				if ("draftWorkitem".equals(instance.getFdActivityType())) {
					String taskId = instance.getFdId();
					String nodeId = instance.getFdNode().getFdId();
					notifyContext.setKey(kmReviewMain.getFdId());
					notifyContext.setParameter1(nodeId);
					notifyContext.setParameter2(taskId);
					break;
				}
			}
		} else {
			notifyContext.setKey("ReviewAutoCreateKey");
		}
		sysNotifyMainCoreService.sendNotify(kmReviewMain, notifyContext, notifyReplace);
	}
	
	
	private void sendAutoCreateNotify(KmReviewMain kmReviewMain,
			String notifyType) throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-review:kmReviewMain.autocreate");
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		// hashMap.put("km-review:kmReviewMain.docSubject", kmReviewMain
		// .getDocSubject());
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("km-review:kmReviewMain.docSubject",
				kmReviewMain
				.getDocSubject()); 
		List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
		receiver.add(kmReviewMain.getDocCreator());
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext.setNotifyType(notifyType);// 通知方式
		notifyContext.setNotifyTarget(receiver);// 通知人员
		ProcessInstanceInfo processInstanceInfo= processExecuteService.load(kmReviewMain.getFdId());
		if(processInstanceInfo != null){
			List<WorkitemInstance> workitems= processInstanceInfo.getCurrentWorkitems();
			for(WorkitemInstance instance : workitems){
				if("draftWorkitem".equals(instance.getFdActivityType())){
					String taskId = instance.getFdId();
					String nodeId = instance.getFdNode().getFdId();
					notifyContext.setKey(kmReviewMain.getFdId());
					notifyContext.setParameter1(nodeId);
					notifyContext.setParameter2(taskId);
					break;
				}
			}
		} else {
			notifyContext.setKey("ReviewAutoCreateKey");
		}
		sysNotifyMainCoreService.sendNotify(kmReviewMain, notifyContext,
				notifyReplace);
	}
	
	public String strDeEscape(String s){
		if(StringUtil.isNull(s)) {
            return "";
        }
		String re = "<BR>";
		s = s.replace(re, "\r\n");
		re = "&amp;g";
		s = s.replace(re, "&");
		re = "&quot;";
		s = s.replace(re, "\"");
		re = "&#39;";
		s = s.replace(re, "'");	
		re = "&lt;";
		s = s.replace(re, "<");	
		re = "&gt;";
		return s.replace(re, ">");
	}
}
