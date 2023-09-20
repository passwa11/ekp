package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.component.locker.interfaces.ComponentLockerInfo;
import com.landray.kmss.component.locker.interfaces.IComponentLockService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.dao.IKmImeetingMainDao;
import com.landray.kmss.km.imeeting.forms.KmImeetingAgendaForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainCancelForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainFeedbackForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainHastenForm;
import com.landray.kmss.km.imeeting.integrate.IMeetingIntegrateThread;
import com.landray.kmss.km.imeeting.integrate.aliyun.IMeetingAliyunThread;
import com.landray.kmss.km.imeeting.integrate.kk.IMeetingKkVideoThread;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.model.KmImeetingMainHistory;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingResLock;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.model.KmImeetingTopic;
import com.landray.kmss.km.imeeting.model.KmImeetingVote;
import com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService;
import com.landray.kmss.km.imeeting.service.IKmImeetingBookService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainHistoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutCacheService;
import com.landray.kmss.km.imeeting.service.IKmImeetingOutVedioService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSeatPlanService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSummaryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTemplateService;
import com.landray.kmss.km.imeeting.service.IKmImeetingTopicService;
import com.landray.kmss.km.imeeting.service.IKmImeetingVoteService;
import com.landray.kmss.km.imeeting.synchro.SynchroConstants;
import com.landray.kmss.km.imeeting.synchro.SynchroThread;
import com.landray.kmss.km.imeeting.synchro.interfaces.IMeetingResponseType;
import com.landray.kmss.km.imeeting.synchro.interfaces.SynchroMeetingResponse;
import com.landray.kmss.km.imeeting.util.AliMeetingUtil;
import com.landray.kmss.km.imeeting.util.BoenUtil;
import com.landray.kmss.km.imeeting.util.KKUtil;
import com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainCoreService;
import com.landray.kmss.sys.agenda.interfaces.SysAgendaMainContextGeneral;
import com.landray.kmss.sys.agenda.util.SysAgendaTypeEnum;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.forms.SysNotifyRemindMainForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextForm;
import com.landray.kmss.sys.notify.model.SysNotifyRemindMain;
import com.landray.kmss.sys.notify.provider.MailContext;
import com.landray.kmss.sys.notify.provider.MailIcsInfo;
import com.landray.kmss.sys.notify.service.ISysNotifyRemindMainService;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.notify.util.SysNotifyRemindUtil;
import com.landray.kmss.sys.notify.util.SysNotifyTypeEnum;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.remind.model.SysRemindMainTask;
import com.landray.kmss.sys.remind.service.ISysRemindMainTaskService;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowRefuse;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.RecurrenceUtil;
import com.landray.kmss.util.RecurrenceUtil.RecurrenceEndType;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 会议安排业务接口实现
 */
public class KmImeetingMainServiceImp extends ExtendDataServiceImp implements
		IKmImeetingMainService, ApplicationListener, ApplicationContextAware {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingMainServiceImp.class);

	private ISysNumberFlowService sysNumberFlowService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysOrgCoreService sysOrgCoreService;
	private ISysQuartzCoreService sysQuartzCoreService;
	protected ApplicationContext applicationContext;
	private IKmImeetingOutCacheService kmImeetingOutCacheService;// 会议接出接口
	private IKmImeetingOutVedioService kmImeetingOutVedioService;// 云会议接出接口
	
	private IKmImeetingMainHistoryService kmImeetingMainHistoryService;// 操作历史
	private IKmImeetingMainFeedbackService kmImeetingMainFeedbackService;// 会议回执
	private IKmImeetingAgendaService kmImeetingAgendaService;// 议程
	private IKmImeetingTemplateService kmImeetingTemplateService;
	private IKmImeetingBookService kmImeetingBookService;

	private IBackgroundAuthService backgroundAuthService;


	public void setBackgroundAuthService(IBackgroundAuthService backgroundAuthService) {
		this.backgroundAuthService = backgroundAuthService;
	}

	private IKmImeetingTopicService kmImeetingTopicService;
	
	private ISysNotifyRemindMainService sysNotifyRemindMainService;// 提醒机制

	public ISysNotifyRemindMainService getSysNotifyRemindMainService() {
		if (sysNotifyRemindMainService == null) {
			sysNotifyRemindMainService = (ISysNotifyRemindMainService) SpringBeanUtil
					.getBean("sysNotifyRemindMainService");
		}
		return sysNotifyRemindMainService;
	}
	//提醒任务
	private ISysRemindMainTaskService sysRemindMainTaskService;

	public ISysRemindMainTaskService getSysRemindMainTaskService() {
		if (sysRemindMainTaskService == null) {
			sysRemindMainTaskService = (ISysRemindMainTaskService) SpringBeanUtil.getBean("sysRemindMainTaskService");
		}
		return sysRemindMainTaskService;
	}

	public void setKmImeetingTopicService(IKmImeetingTopicService kmImeetingTopicService) {
		this.kmImeetingTopicService = kmImeetingTopicService;
	}
	
	private IKmImeetingSummaryService kmImeetingSummaryService;
	public IKmImeetingSummaryService getKmImeetingSummaryService() {
		if (kmImeetingSummaryService == null) {
			kmImeetingSummaryService = (IKmImeetingSummaryService) SpringBeanUtil.getBean("kmImeetingSummaryService");
        }
		return kmImeetingSummaryService;
	}
	
	private IKmImeetingSeatPlanService kmImeetingSeatPlanService;

	public IKmImeetingSeatPlanService getKmImeetingSeatPlanService() {
		if (kmImeetingSeatPlanService == null) {
			kmImeetingSeatPlanService = (IKmImeetingSeatPlanService) SpringBeanUtil
					.getBean("kmImeetingSeatPlanService");
		}
		return kmImeetingSeatPlanService;
	}

	private IKmImeetingVoteService kmImeetingVoteService;

	public IKmImeetingVoteService getKmImeetingVoteService() {
		if (kmImeetingVoteService == null) {
			kmImeetingVoteService = (IKmImeetingVoteService) SpringBeanUtil
					.getBean("kmImeetingVoteService");
		}
		return kmImeetingVoteService;
	}

	private static KmssCache cache = new KmssCache(List.class);// 缓存MAP

	public IKmImeetingTemplateService getKmImeetingTemplateService() {
		return kmImeetingTemplateService;
	}

	public void setKmImeetingTemplateService(
			IKmImeetingTemplateService kmImeetingTemplateService) {
		this.kmImeetingTemplateService = kmImeetingTemplateService;
	}
	
	@Override
	protected IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
		String fdTemplateId = requestContext.getParameter("fdTemplateId");
		if (!StringUtil.isNotNull(fdTemplateId)) {
			return null;
		}
		KmImeetingMain kmImeetingMain = new KmImeetingMain();
		String fdModelId = requestContext.getParameter("fdModelId");
		String fdModelName = requestContext.getParameter("fdModelName");
		if (StringUtil.isNotNull(fdModelId)
				&& StringUtil.isNotNull(fdModelName)) {
			kmImeetingMain.setFdModelId(fdModelId);
			kmImeetingMain.setFdModelName(fdModelName);
		}

		kmImeetingMain.setFdChangeMeetingFlag(false);
		kmImeetingMain.setFdSummaryFlag(false);
		kmImeetingMain.setFdIsHurrySummary(false);
		kmImeetingMain.setIsNotify(false);

		KmImeetingTemplate kmImeetingTemplate = (KmImeetingTemplate) getKmImeetingTemplateService()
				.findByPrimaryKey(fdTemplateId);
		if (kmImeetingTemplate != null) {
			kmImeetingMain.setFdTemplate(kmImeetingTemplate);
		}
		return kmImeetingMain;
	}

	@Override
	protected void initCoreServiceFormSetting(IExtendForm form,
			IBaseModel model, RequestContext requestContext) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) model;
		dispatchCoreService.initFormSetting(form, "ImeetingMain",
				kmImeetingMain.getFdTemplate(), "ImeetingMain", requestContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setSysNumberFlowService(
			ISysNumberFlowService sysNumberFlowService) {
		this.sysNumberFlowService = sysNumberFlowService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setKmImeetingOutCacheService(
			IKmImeetingOutCacheService kmImeetingOutCacheService) {
		this.kmImeetingOutCacheService = kmImeetingOutCacheService;
	}

	public void setKmImeetingOutVedioService(
			IKmImeetingOutVedioService kmImeetingOutVedioService) {
		this.kmImeetingOutVedioService = kmImeetingOutVedioService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	public void setKmImeetingMainHistoryService(
			IKmImeetingMainHistoryService kmImeetingMainHistoryService) {
		this.kmImeetingMainHistoryService = kmImeetingMainHistoryService;
	}

	public void setKmImeetingMainFeedbackService(
			IKmImeetingMainFeedbackService kmImeetingMainFeedbackService) {
		this.kmImeetingMainFeedbackService = kmImeetingMainFeedbackService;
	}

	public void setKmImeetingAgendaService(
			IKmImeetingAgendaService kmImeetingAgendaService) {
		this.kmImeetingAgendaService = kmImeetingAgendaService;
	}

	public void
			setKmImeetingBookService(
					IKmImeetingBookService kmImeetingBookService) {
		this.kmImeetingBookService = kmImeetingBookService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		requestContext.setParameter("fdHoldDate", kmImeetingMainForm.getFdHoldDate());
		requestContext.setParameter("fdFinishDate", kmImeetingMainForm.getFdFinishDate());
		requestContext.setParameter("recurrenceStr", kmImeetingMainForm.getFdRecurrenceStr());
		if (!StringUtil.isNotNull(requestContext.getParameter("meetingId"))) {
			requestContext.setParameter("meetingId", kmImeetingMainForm.getFdId());
		}
		String fdPlaceId = StringUtil.isNotNull(kmImeetingMainForm.getFdPlaceId()) ? kmImeetingMainForm.getFdPlaceId()
				: "";
		if (StringUtil.isNotNull(fdPlaceId)) {
			IKmImeetingResService kmImeetingResService = (IKmImeetingResService) SpringBeanUtil
					.getBean("kmImeetingResService");
			// 会议室校验需要使用原始ID
			String meetingId = requestContext.getParameter("fdOriginalId");
			if (StringUtil.isNull(meetingId)) {
				meetingId = requestContext.getParameter("meetingId");
			}
			if (StringUtil.isNull(meetingId)) {
				meetingId = kmImeetingMainForm.getFdId();
			}
			if(!ImeetingConstant.STATUS_CANCEL.equals(kmImeetingMainForm.getDocStatus())) {
				//如果是取消操作，则不验证会议室是占用 
				RequestContext rc = new RequestContext(requestContext.getRequest());
				rc.setParameterMap(requestContext.getParameterMap());
				rc.setParameter("meetingId", meetingId);
				rc.setParameter("exceptMeetingId", meetingId);
				KmImeetingConfig kmImeetingConfig = new KmImeetingConfig();
				// 冲突检测配置
				Boolean check = "true".equals(kmImeetingConfig.getUnShow());
				//冲突校验为true才进行校验
				if(BooleanUtils.isTrue(check)) {
					JSONObject json = kmImeetingResService.isConflictRes(rc, fdPlaceId);
					if (StringUtil.isNotNull(fdPlaceId) && json.getBoolean("result")) {
						throw new RuntimeException("该会议室已被占用!");
					}
				}
			}
		}
		String fdId = "";
		KmImeetingResLock kmImeetingResLock = new KmImeetingResLock();
		ComponentLockerInfo lockInfo1 = getComponentLockService().hasLock(kmImeetingResLock);
		if (lockInfo1.isLocked()) {
			throw new RuntimeException("该会议室已被占用!");
		}
		try {
			if (StringUtil.isNotNull(fdPlaceId)) {
				getComponentLockService().tryLock(kmImeetingResLock, fdPlaceId);
			}
			fdId = super.add(form, requestContext);
			getComponentLockService().unLock(kmImeetingResLock);
		} catch (Exception e) {
			logger.error("会议报错错误{}",kmImeetingMainForm.getFdName(),e);
			throw e;
		} finally {
			getComponentLockService().unLock(kmImeetingResLock);
		}
		// 周期性会议变更后续会议 修改原来会议的重复规则信息，再保存新周期性的会议
		String fdOriginalId = (String) requestContext.getParameter("fdOriginalId");
		if (StringUtil.isNotNull(fdOriginalId)) {
			KmImeetingMain main = (KmImeetingMain) findByPrimaryKey(fdOriginalId, null, true);
			updateCurrentInfo(main);
			getBaseDao().update(main);
		}
		return fdId;
	}

	public void addTopicAtt(String fdMeetingId, String fdAgendaId, String fdTopicId) throws Exception {
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		List mainAtt = sysAttMainService.findByModelKey(KmImeetingTopic.class.getName(), fdTopicId, "mainonline");
		List commonAtt = sysAttMainService.findByModelKey(KmImeetingTopic.class.getName(), fdTopicId, "attachment");
		commonAtt.addAll(mainAtt);
		for (int i = 0; i < commonAtt.size(); i++) {
			SysAttMain tempAtt = (SysAttMain) commonAtt.get(i);
			SysAttMain sysAttMain = sysAttMainService.clone(tempAtt);
			sysAttMain.setFdAttType("byte");
			sysAttMain.setFdModelId(fdMeetingId);
			sysAttMain.setFdKey("ImeetingUploadAtt_" + fdAgendaId);
			sysAttMain.setFdModelName("com.landray.kmss.km.imeeting.model.KmImeetingMain");
			sysAttMainService.update(sysAttMain);
		}
	}

	IComponentLockService componentLockService = null;

	private IComponentLockService getComponentLockService() {
		if (componentLockService == null) {
			componentLockService = (IComponentLockService) SpringBeanUtil.getBean("componentLockService");
		}
		return componentLockService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) modelObj;
		String fdMainId = kmImeetingMain.getFdId();
		Boolean voteEnable = kmImeetingMain.getFdVoteEnable();
		if (voteEnable) {
			List<KmImeetingVote> kmImeetingVotes = getKmImeetingVoteService()
					.findByTemporaryId(fdMainId);
			if (kmImeetingVotes != null && !kmImeetingVotes.isEmpty()) {
				kmImeetingMain.setKmImeetingVotes(kmImeetingVotes);
			}
		}
		// 开启了议题管理，将议题附件带过来
		if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
			List<KmImeetingAgenda> kmImeetingAgendaList = kmImeetingMain.getKmImeetingAgendas();
			if (kmImeetingAgendaList != null && !kmImeetingAgendaList.isEmpty()) {
				for (int m = 0; m < kmImeetingAgendaList.size(); m++) {
					KmImeetingAgenda kmImeetingAgenda = kmImeetingAgendaList.get(m);
					String topicId = kmImeetingAgenda.getFdFromTopicId();
					addTopicAtt(kmImeetingMain.getFdId(), kmImeetingAgenda.getFdId(), topicId);
				}
			}
		}
		String fdId = "";
		try {
			// 非草稿状态才生成编号、增加会议时间轴
			if (!ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingMain.getDocStatus())) {
				if (kmImeetingMain.getFdTemplate() != null) {// 有模板（完整模式，生成会议编号）
					String newNo = sysNumberFlowService.generateFlowNumber(kmImeetingMain);
					kmImeetingMain.setFdMeetingNum(newNo);
				}
				// 增加操作历史(用于时间轴)
				addCreateHistory(kmImeetingMain);
			}
			// 如果结束时间不存在,根据开始时间+历时计算
			if (kmImeetingMain.getFdFinishDate() == null && kmImeetingMain.getFdHoldDuration() != null) {
				Date finishDate = new Date(
						kmImeetingMain.getFdHoldDate().getTime() + kmImeetingMain.getFdHoldDuration().longValue());
				kmImeetingMain.setFdFinishDate(finishDate);
			}

			// 周期性会议中如果存在冲突 用户选择占用非冲突资源时
			boolean flag = saveNotConflictImeeting(kmImeetingMain);
			if (!flag) {// false表示没有冲突
				handleRecurrence(kmImeetingMain);
				fdId = super.add(modelObj);
			}
			// 周期性会议变更当前会议时 ，创建后续周期性会议 时触发
			// 前一个周期性会议结束时，自动创建下一个新周期性会议时 触发
			String changeType = kmImeetingMain.getFdChangeType();
			if (StringUtil.isNotNull(changeType)) {
				if ("cur".equals(changeType)) {
					saveSendMeetingNotify(kmImeetingMain);
				}
			}
			// 没有模板（极简模式，马上发送会议通知单、生成日程）
			if (kmImeetingMain.getFdTemplate() == null) {
				kmImeetingMain.setFdNotifyer(kmImeetingMain.getDocCreator());// 自动发送会议默认通知人为创建者
				logger.debug("没有模板（极简模式，马上发送会议通知单、生成日程）");
				saveSendMeetingNotify(kmImeetingMain);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fdId;
	}
	
	
	public void saveSyncToBoenQuartz(KmImeetingMain kmImeetingMain) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("syncMeetingToBoen");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("addSyncToBoebQuartz");
		JSONObject parameter = new JSONObject();
		parameter.put("fdMeetingId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("同步到铂恩会议系统，标题:" + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain.getFdFeedBackDeadline(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}
	
	public void saveSyncAttFromBoenQuartz(KmImeetingMain kmImeetingMain) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("syncAttFromBoen");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("addAttFromBoenQuartz");
		JSONObject parameter = new JSONObject();
		parameter.put("fdMeetingId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(kmImeetingMain.getFdFinishDate());
		calendar.add(Calendar.HOUR, 3);
		Date finishDate = calendar.getTime();
		quartzContext.setQuartzSubject("从铂恩会议系统获取附件批注数据，标题:" + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(finishDate, 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}

	@Override
	public void syncMeetingToBoen(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(parameter.getString("fdMeetingId"),
				KmImeetingMain.class.getName(), true);
		if (kmImeetingMain != null) {
			KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
					.findByPrimaryKey(kmImeetingMain.getFdId(), KmImeetingMapping.class.getName(), true);
			if (kim == null) {
				kim = kmImeetingMappingService.findByModelId(kmImeetingMain.getFdId(), KmImeetingMain.class.getName());
			}
			if (kim != null) {
				updateSyncToBoen(kmImeetingMain);
			} else {
				addSyncToBoen(kmImeetingMain);
			}
		}
	}

	@Override
	public void syncAttFromBoen(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(parameter.getString("fdMeetingId"),
				KmImeetingMain.class.getName(), true);
		if (kmImeetingMain != null) {
			addAttFromBoen(kmImeetingMain);
		}
	}

	private boolean saveNotConflictImeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		boolean flag = false;
		String recurrenceStr = kmImeetingMain.getFdRecurrenceStr();
		if (StringUtil.isNotNull(recurrenceStr)) {
			KmImeetingMain copyMain = (KmImeetingMain) BeanUtils
					.cloneBean(kmImeetingMain);
			KmImeetingRes fdPlace = kmImeetingMain.getFdPlace();
			if (fdPlace != null) {
				String resId = fdPlace.getFdId();
				Date start = kmImeetingMain.getFdHoldDate();
				Date end = kmImeetingMain.getFdFinishDate();
				Map<Date, String> infos = getNotConflictInfos(resId, start, end,
						recurrenceStr);
				if (infos.size() > 0) {
					Set<Date> keys = infos.keySet();
					for (Date key : keys) {
						if (!flag) {// 第一个保存逻辑
							kmImeetingMain.setFdHoldDate(key);
							kmImeetingMain.setFdFinishDate(new Date(
									key.getTime() + end.getTime()
											- start.getTime()));
							kmImeetingMain.setFdRecurrenceStr(infos.get(key));
							handleRecurrence(kmImeetingMain);
							super.add(kmImeetingMain);
							flag = true;
						} else {// 后续保存逻辑
							KmImeetingMainForm newForm = new KmImeetingMainForm();
							RequestContext requestContext = new RequestContext();
							KmImeetingMainForm mainForm = new KmImeetingMainForm();
							mainForm = (KmImeetingMainForm) convertModelToForm(
									mainForm, copyMain, requestContext);
							KmImeetingMain newMain = (KmImeetingMain) BeanUtils
									.cloneBean(copyMain);
							newMain.setFdId(IDGenerator.generateID());
							newMain.setFdHoldDate(key);
							newMain.setFdFinishDate(new Date(
									key.getTime() + end.getTime()
											- start.getTime()));
							newMain.setFdRecurrenceStr(infos.get(key));
							resetModel(newMain, copyMain);
							newMain.setFdChangeType("cur");
							newForm = (KmImeetingMainForm) convertModelToForm(
									newForm, newMain, requestContext);
							resetForm(newForm, mainForm);
							initCoreServiceFormSetting(newForm, newMain,
									requestContext);
							add(newForm, requestContext);
						}
					}
				}
			}
		}
		return flag;
	}

	private Map<Date, String> getNotConflictInfos(String resId, Date start,
			Date end, String recurrenceStr) throws Exception {
		Map<Date, String> infos = new HashMap<>();
		List<Date[]> conflictTimes = new ArrayList<>();
		Date lastedDate = end;
		// 取出所有占用资源的时间段
		ArrayList<Object[]> timeArray = new ArrayList<Object[]>();
		ArrayList<Object[]> imeetingTimeArray = new ArrayList<Object[]>();
		lastedDate = RecurrenceUtil.getLastedExecuteDate(recurrenceStr, start);
		List<Date> dates = RecurrenceUtil.getExcuteDateList(
				recurrenceStr, start, start, lastedDate);
		dates.add(lastedDate);
		for (Date date : dates) {
			imeetingTimeArray.add(new Date[] { date, new Date(
					date.getTime() + end.getTime()
								- start.getTime()) });
		}
		timeArray.addAll(
				findConflictTimeInMain(resId, start, lastedDate));
		timeArray.addAll(
				findConflictTimeInBook(resId, start, lastedDate));
		Collections.sort(timeArray, new TimeComparator());

		for (Object[] times : timeArray) {
			for (Object[] imeetingTimes : imeetingTimeArray) {
				// 结束时间小于等于 所创建会议的开始时间 表示没有冲突
				if (((Date) times[1]).getTime() <= ((Date) imeetingTimes[0])
						.getTime()) {
					continue;
				}
				// 开始时间大于等于 所创建会议的结束时间 表示没有冲突
				if (((Date) times[0]).getTime() >= ((Date) imeetingTimes[1])
						.getTime()) {
					continue;
				}
				conflictTimes.add((Date[]) imeetingTimes);
			}
		}
		if (conflictTimes != null && conflictTimes.size() > 0) {
			imeetingTimeArray.removeAll(conflictTimes);
			// 需要一个变量记录最开始的时间
			// 记录最开始时间
			Date firstDate = null;


			for (Object[] times : imeetingTimeArray) {
				Date startDate = (Date) times[0];
				// 如果是(从不)结束的时间点（不考虑在内的非冲突时间），过滤 #170936
				if(RecurrenceUtil.lastedDate.getYear() == startDate.getYear() ){
                   continue;
				}
				boolean flag = hasNextStartDate(imeetingTimeArray, startDate, recurrenceStr);
				// 什么时候需要给最开始时间赋值
				if (flag == true) {
					if (firstDate == null) {
						firstDate = startDate;
					}
					continue;
				} else {
					if (firstDate == null) {
						firstDate = startDate;
					}
				}
				Map<String, String> params = RecurrenceUtil
						.parseRecurrenceStr(recurrenceStr);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(startDate);
				params.put("ENDTYPE",
						RecurrenceEndType.UNTIL.name());
				params.put("UNTIL", new SimpleDateFormat("yyyyMMdd")
						.format(calendar.getTime()));
				infos.put(firstDate, RecurrenceUtil.buildRecurrenceStr(params));
				//重置开始时间
				firstDate = null;
			}
		}
		return infos;
	}

	private boolean hasNextStartDate(ArrayList<Object[]> imeetingTimeArray,
			Date startDate, String recurrenceStr) throws Exception {
		Date nextStartDate = RecurrenceUtil.getNextExecuteDate(recurrenceStr,
				startDate);
		for (Object[] objects : imeetingTimeArray) {
			if (nextStartDate.equals(objects[0])) {
				return true;
			}
		}
		return false;
	}

	private class TimeComparator implements Comparator<Object[]> {
		@Override
		public int compare(Object[] o1, Object[] o2) {
			return ((Date) o1[0]).getTime() <= ((Date) o2[0]).getTime() ? -1
					: 1;
		}
	}

	// 在指定资源、指定时间范围内找出所有会议占用时间段
	private List<Date[]> findConflictTimeInMain(String resId, Date start,
			Date end) throws Exception {
		List result = new ArrayList();
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("resId", resId);
		requestContext.setParameter("fdStart", DateUtil
				.convertDateToString(start, DateUtil.TYPE_DATETIME,
						requestContext.getLocale()));
		requestContext.setParameter("fdEnd", DateUtil.convertDateToString(end,
				DateUtil.TYPE_DATETIME, requestContext.getLocale()));
		HQLInfo hql = buildImeetingHql(requestContext);
		hql.setSelectBlock(
				" kmImeetingMain.fdHoldDate,kmImeetingMain.fdFinishDate");
		List list = findList(hql);
		result.addAll(list);
		HQLInfo hql2 = buildImeetingRangeHql(requestContext);
		List<KmImeetingMain> matchMainModels = findList(hql2);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			long rangeTime = mainEndDateTime.getTime()
					- mainStartDateTime.getTime();
			Date searchStart = new Date(start.getTime() - rangeTime);
			Date searchEnd = new Date(end.getTime() + rangeTime);
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					mainStartDateTime, searchStart, searchEnd);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + mainEndDateTime.getTime()
								- mainStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime()
						&& newEndDate.getTime() >= start.getTime()) {
					result.add(new Date[] { newStartDate, newEndDate });
				} else {
					break;
				}
			}
		}
		return result;
	}

	// 在指定资源、指定时间范围内找出所有预约占用时间段
	private List<Date[]> findConflictTimeInBook(
			String resId, Date start, Date end) throws Exception {
		List result = new ArrayList();
		String format = ResourceUtil.getString("date.format.datetime");
		RequestContext requestContext = new RequestContext();
		requestContext.setParameter("hasExam", "true");
		requestContext.setParameter("fdStart",
				DateUtil.convertDateToString(start, format));
		requestContext.setParameter("fdEnd",
				DateUtil.convertDateToString(end, format));
		requestContext.setParameter("format", format);
		requestContext.setParameter("placeId", resId);

		HQLInfo hql1 = kmImeetingBookService
				.buildNormalBookHql(requestContext);
		hql1.setSelectBlock(
				" kmImeetingBook.fdHoldDate,kmImeetingBook.fdFinishDate");
		result.addAll(kmImeetingBookService.findList(hql1));
		HQLInfo hql2 = kmImeetingBookService
				.buildRangeBookHql(requestContext);
		List<KmImeetingBook> matchBookModels = kmImeetingBookService
				.findList(hql2);
		for (KmImeetingBook book : matchBookModels) {
			String recurrenceStr = book.getFdRecurrenceStr();
			Date bookStartDateTime = book.getFdHoldDate();
			Date bookEndDateTime = book.getFdFinishDate();
			long rangeTime = bookEndDateTime.getTime()
					- bookStartDateTime.getTime();
			Date searchStart = new Date(start.getTime() - rangeTime);
			Date searchEnd = new Date(end.getTime() + rangeTime);
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					bookStartDateTime, searchStart, searchEnd);
			for (Date date : dates) {
				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + bookEndDateTime.getTime()
								- bookStartDateTime.getTime());
				if (newStartDate.getTime() <= end.getTime()
						&& newEndDate.getTime() >= start.getTime()) {
					result.add(new Date[] { newStartDate, newEndDate });
				} else {
					break;
				}
			}
		}
		return result;
	}

	private void deleteAgendaTodos(KmImeetingMain kmImeetingMain) throws Exception {
		List AgendaTodoKey = getAgendaTodoKey(kmImeetingMain);
		if(AgendaTodoKey != null && !AgendaTodoKey.isEmpty()) {
			sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(kmImeetingMain, AgendaTodoKey, null, null);
		}
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmImeetingMainForm kmImeetingMainForm = (KmImeetingMainForm) form;
		KmImeetingMain kmImeetingMain_old = (KmImeetingMain) this.findByPrimaryKey(kmImeetingMainForm.getFdId(),
				KmImeetingMain.class.getName(), true);
		// 会议变更，删除之前的待办
		if (ImeetingConstant.IS_CHANGEMEETING_YES.equals(kmImeetingMainForm.getFdChangeMeetingFlag())) {
			deleteAgendaTodos(kmImeetingMain_old);
		}
		//会议回执
		addFeedBack(kmImeetingMainForm, kmImeetingMain_old);

		List newIds = new ArrayList();
		List oldIds = new ArrayList();
		Map map = new HashMap();
		List newKmImeetingAgenda = kmImeetingMainForm.getKmImeetingAgendaForms();
		for (int i = 0; i < newKmImeetingAgenda.size(); i++) {
			KmImeetingAgendaForm kmImeetingAgendaForm = (KmImeetingAgendaForm) newKmImeetingAgenda.get(i);
			String fdAgendaId = kmImeetingAgendaForm.getFdId();
			if (StringUtil.isNull(fdAgendaId)) {
				fdAgendaId = IDGenerator.generateID();
				kmImeetingAgendaForm.setFdId(fdAgendaId);
			}
			newIds.add(fdAgendaId);
			map.put(fdAgendaId, kmImeetingAgendaForm.getFdFromTopicId());
		}
		List oldKmImeetingAgenda = kmImeetingMain_old.getKmImeetingAgendas();
		for (int j = 0; j < oldKmImeetingAgenda.size(); j++) {
			KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) oldKmImeetingAgenda.get(j);
			oldIds.add(kmImeetingAgenda.getFdId());
			map.put(kmImeetingAgenda.getFdId(), kmImeetingAgenda.getFdFromTopicId());
		}
		List<String> addIds = new ArrayList();
		addIds.addAll(newIds);
		addIds.removeAll(oldIds);
		for (int x = 0; x < addIds.size(); x++) {
			addTopicAtt(kmImeetingMainForm.getFdId(), addIds.get(x), (String) map.get(addIds.get(x)));
		}
		KmImeetingMain newMain = null;
		// 周期性会议变更当前会议时，先复制一份新的周期性会议，并修改重复规则，然后修改当前会议的重复规则
		String changeType = kmImeetingMainForm.getFdChangeType();
		if (StringUtil.isNotNull(changeType)) {
			if ("cur".equals(changeType)) {
				KmImeetingMain main = (KmImeetingMain) findByPrimaryKey(
						kmImeetingMainForm.getFdId(), KmImeetingMain.class,
						true);
				newMain = (KmImeetingMain) BeanUtils.cloneBean(main);
			}
		}
		super.update(form, requestContext);

		if (newMain != null) {
			createAfterImeeting(newMain);
		}
	}

	private void addFeedBack(KmImeetingMainForm kmImeetingMainForm, KmImeetingMain kmImeetingMain_old) {
		AutoArrayList kmImeetingMainFeedbackForms = kmImeetingMainForm.getKmImeetingMainFeedbackForms();
		List<KmImeetingMainFeedback> kmImeetingMainFeedbacks = kmImeetingMain_old.getKmImeetingMainFeedbacks();
		for (KmImeetingMainFeedback feedbacks:kmImeetingMainFeedbacks) {
			KmImeetingMainFeedbackForm feedback = new KmImeetingMainFeedbackForm();
			feedback.setFdFeedbackCount("0");
			feedback.setFdType(feedbacks.getFdType());
			feedback.setFdFromType(feedbacks.getFdFromType());
			feedback.setFdMeetingId(kmImeetingMainForm.getFdId());
			feedback.setFdAttendAgendaId(feedbacks.getFdAttendAgendaId());
			feedback.setDocCreatorId(feedbacks.getDocCreator().getFdId());
			SysOrgPerson fdInvitePerson = feedbacks.getFdInvitePerson();
			if (fdInvitePerson != null) {
				feedback.setFdInvitePersonId(fdInvitePerson.getFdId());
				feedback.setFdType(ImeetingConstant.FEEDBACK_TYPE_INVITE);
			}
			if (StringUtil.isNotNull(feedbacks.getFdFromType()) &&
					feedbacks.getFdFromType().equals(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_PROXY)) {
				feedback.setFdFromType(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_INVITE);
			}

			kmImeetingMainFeedbackForms.add(feedback);
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) modelObj;
		String fdPlaceId = kmImeetingMain.getFdPlace() != null ? kmImeetingMain.getFdPlace().getFdId() : "";
		KmImeetingResLock kmImeetingResLock = new KmImeetingResLock();
		try {
			if (StringUtil.isNotNull(fdPlaceId)) {
				getComponentLockService().tryLock(kmImeetingResLock, fdPlaceId);
			}
			Boolean voteEnable = kmImeetingMain.getFdVoteEnable();
			if (!voteEnable) {
				kmImeetingMain.getKmImeetingVotes().clear();
			} else {
				List<KmImeetingVote> kmImeetingVotes = getKmImeetingVoteService()
						.findByTemporaryId(kmImeetingMain.getFdId());
				if (kmImeetingVotes != null && !kmImeetingVotes.isEmpty()) {
					kmImeetingMain.getKmImeetingVotes().clear();
					kmImeetingMain.getKmImeetingVotes().addAll(kmImeetingVotes);
				}
			}
			// 非草稿状态才生成编号
			if (!ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingMain
					.getDocStatus())
					&& StringUtil.isNull(kmImeetingMain.getFdMeetingNum())) {
				if (kmImeetingMain.getFdTemplate() != null) {// 有模板（完整模式，生成会议编号）
					String newNo = sysNumberFlowService
							.generateFlowNumber(kmImeetingMain);
					kmImeetingMain.setFdMeetingNum(newNo);
					// 增加操作历史(用于时间轴)
					addCreateHistory(kmImeetingMain);
				}
				// 生成取消会议定时器
				// saveCheckIsCancel(kmImeetingMain);
			}
			// 会议变更收回所有待办、所有日程
			if (kmImeetingMain.getFdChangeMeetingFlag()) {
				String changeType = kmImeetingMain.getFdChangeType();
				if (StringUtil.isNotNull(changeType)) {
					if ("cur".equals(changeType)) {
						updateCurrentInfo(kmImeetingMain);// 修改当前会议的重复规则
					}
				}
				delNotifyForChangeMeeting(kmImeetingMain);
				//删除之前同步日程
				//deleteSyncDataToCalendar(kmImeetingMain,getBeforePerson(kmImeetingMain));
				deleteSyncImeetingToCalendar(kmImeetingMain,getBeforePerson(kmImeetingMain));
			}
			// 没有模板（极简模式，马上发送会议变更单、生成日程）
			if (kmImeetingMain.getFdTemplate() == null
					&& kmImeetingMain.getFdChangeMeetingFlag()) {
				Date finishDate = new Date(kmImeetingMain.getFdHoldDate().getTime()
						+ kmImeetingMain.getFdHoldDuration().longValue());
				kmImeetingMain.setFdFinishDate(finishDate);
				sendChangeNotify(kmImeetingMain);
				
				// 生成变更历史操作
				addChangeHistory(kmImeetingMain);
			}

			// #138807 ekp会议同步到exchange，ekp中会议参与人变更后，exchange中没有变更
			if (ImeetingConstant.DOC_STATUS_PUBLISH.equals(kmImeetingMain
					.getDocStatus()) && !kmImeetingMain.getIsCloud()
					&& !kmImeetingMain.getFdIsVideo() && !kmImeetingMain.isSynchroIn()) {
				SynchroThread thread = new SynchroThread(kmImeetingMain,
						SynchroConstants.OPERATE_UPDATE);
				thread.start();
			}
			handleRecurrence(kmImeetingMain);
			super.update(modelObj);
			getComponentLockService().unLock(kmImeetingResLock);
		} catch (Exception e) {
			getComponentLockService().unLock(kmImeetingResLock);
			throw e;
		} finally {
			getComponentLockService().unLock(kmImeetingResLock);
		}
	}

	/**
	 * 变更周期性会议的重复规则
	 * 
	 * @param kmImeetingMain
	 * @param changeType
	 * @throws Exception
	 */
	private void updateCurrentInfo(KmImeetingMain kmImeetingMain)
			throws Exception {
		Date matchDate = RecurrenceUtil
				.getNextExecuteDate(
						kmImeetingMain.getFdRecurrenceStr(),
						kmImeetingMain.getFdHoldDate(),
						kmImeetingMain.getFdHoldDate());
		if (matchDate != null) {
			// 更新日历重复信息
			Map<String, String> params = RecurrenceUtil
					.parseRecurrenceStr(
							kmImeetingMain.getFdRecurrenceStr());
			if (RecurrenceEndType.COUNT.name()
					.equals(params.get("ENDTYPE"))) {
				int hasCount = RecurrenceUtil.getExecuteCount(
						kmImeetingMain.getFdRecurrenceStr(),
						kmImeetingMain.getFdHoldDate(),
						kmImeetingMain.getFdHoldDate(), matchDate);
				params.put("COUNT", Integer.toString(hasCount - 1));
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(matchDate);
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				params.put("ENDTYPE",
						RecurrenceEndType.UNTIL.name());
				params.put("UNTIL", new SimpleDateFormat("yyyyMMdd")
							.format(calendar.getTime()));
			}
			kmImeetingMain.setFdRecurrenceStr(
					RecurrenceUtil.buildRecurrenceStr(params));
		}
	}

	/**
	 * 创建新周期性会议
	 * 
	 * @throws Exception
	 */
	private void createAfterImeeting(KmImeetingMain main)
			throws Exception {
		backgroundAuthService.switchUser(main.getDocCreator(), new Runner() {
			@Override
			public Object run(Object parameter) throws Exception {
				KmImeetingMainForm newForm = new KmImeetingMainForm();
				RequestContext requestContext = new RequestContext();
				KmImeetingMainForm mainForm = new KmImeetingMainForm();
				mainForm = (KmImeetingMainForm) convertModelToForm(
						mainForm, main, requestContext);
				KmImeetingMain newMain = getNewImeeting(main);
				if (newMain != null) {
					newMain.setFdChangeType("cur");
					//清空之前的周期性会议上一次会议存在的历时时间
					Long holdDuration = main.getFdFinishDate().getTime() - main.getFdHoldDate().getTime();
					newMain.setFdHoldDuration(holdDuration.doubleValue());
					newForm = (KmImeetingMainForm) convertModelToForm(
							newForm, newMain, requestContext);
					resetForm(newForm, mainForm);
					initCoreServiceFormSetting(newForm, newMain,
							requestContext);
					newForm.setDocStatus(SysDocConstant.DOC_STATUS_PUBLISH);

					/**
					 * 周期性会议不创建流程
					 */
					newForm.getSysWfBusinessForm().setCanStartProcess("false");
					requestContext.setParameter("meetingId", main.getFdId());
					add(newForm, requestContext);
				}
				return null;
			}
		},null);
	}

	private KmImeetingMain getNewImeeting(KmImeetingMain main)
			throws Exception {
		KmImeetingMain newMain = null;
		Date holdDate = main.getFdHoldDate();
		Date matchDate = RecurrenceUtil
				.getNextExecuteDate(main.getFdRecurrenceStr(),
						holdDate, holdDate);
		if (matchDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(matchDate);
			// 生成一份新的会议代表未来的会议
			newMain = (KmImeetingMain) BeanUtils
					.cloneBean(main);
			// 更新日历重复信息
			Map<String, String> params = RecurrenceUtil
					.parseRecurrenceStr(newMain.getFdRecurrenceStr());
			if (RecurrenceEndType.COUNT.name()
					.equals(params.get("ENDTYPE"))) {
				int count = new Integer(params.get("COUNT"));
				int hasCount = RecurrenceUtil.getExecuteCount(
						newMain.getFdRecurrenceStr(),
						holdDate, holdDate, holdDate);
				if (hasCount < count) {
					params.put("COUNT",
							Integer.toString(count - hasCount));
					newMain.setFdRecurrenceStr(
							RecurrenceUtil.buildRecurrenceStr(params));
				}
			}
			newMain.setFdId(IDGenerator.generateID());
			newMain.setFdHoldDate(calendar.getTime());
			newMain.setFdFinishDate(new Date(
					calendar.getTimeInMillis()
							+ main.getFdFinishDate().getTime()
							- holdDate.getTime()));
			resetModel(newMain, main);
		}
		return newMain;
	}

	private void resetForm(KmImeetingMainForm newForm,
			KmImeetingMainForm mainForm) throws Exception {
		String beforeChangeContent = mainForm.getBeforeChangeContent();
		if (StringUtil.isNotNull(beforeChangeContent)) {
			JSONObject json = JSONObject.fromObject(beforeChangeContent);
			String fdName = (String) json.get("fdName");
			if(StringUtil.isNotNull(fdName)){
				newForm.setFdName(fdName);
			}
		}
		// 重置日程信息
		SysNotifyRemindMainContextForm newRemindMainContextForm = new SysNotifyRemindMainContextForm();
		List<SysNotifyRemindMainForm> newRremindMainForms = new ArrayList<>();
		SysNotifyRemindMainContextForm remindMainContextForm = mainForm
				.getSysNotifyRemindMainContextForm();
		List<SysNotifyRemindMainForm> remindMainForms = remindMainContextForm
				.getSysNotifyRemindMainFormList();
		for (SysNotifyRemindMainForm remindMainForm : remindMainForms) {
			SysNotifyRemindMainForm newRemindMainForm = (SysNotifyRemindMainForm) BeanUtils
					.cloneBean(remindMainForm);
			String fdId = IDGenerator.generateID();
			newRemindMainForm.setFdId(fdId);
			newRemindMainForm.setFdKey(fdId);
			newRemindMainForm.setFdModelName(newForm.getModelClass().getName());
			newRremindMainForms.add(newRemindMainForm);
		}
		newRemindMainContextForm
				.setSysNotifyRemindMainFormList(newRremindMainForms);
		newForm.setSysNotifyRemindMainContextForm(newRemindMainContextForm);
	}


	private void resetModel(KmImeetingMain newMain, KmImeetingMain main)
			throws Exception {
		// 重置变更状态
		newMain.setFdChangeMeetingFlag(false);
		// 重置变更原因
		newMain.setChangeMeetingReason(null);
		// 重置创建时间
		newMain.setDocCreateTime(new Date());
		// 重置是否通知
		newMain.setIsNotify(false);
		// 重置会议议程
		List<KmImeetingAgenda> kmImeetingAgendas = new ArrayList<>();
		newMain.setKmImeetingAgendas(kmImeetingAgendas);
		// 重置会议纪要
		List<KmImeetingSummary> kmImeetingSummarys = new ArrayList<>();
		newMain.setKmImeetingSummarys(kmImeetingSummarys);
		//重置操作历史
		List<KmImeetingMainHistory> kmImeetingMainHistorys = new ArrayList<>();
		newMain.setKmImeetingMainHistorys(kmImeetingMainHistorys);
		//重置回执
		List<KmImeetingMainFeedback> kmImeetingMainFeedbacks = new ArrayList<>();
		newMain.setKmImeetingMainFeedbacks(kmImeetingMainFeedbacks);
		// 重置纪要完成时间
		newMain.setFdSummaryCompleteTime(null);
		// 重置是否已纪要
		newMain.setFdSummaryFlag(false);
		// 重置是否催办纪要
		newMain.setFdIsHurrySummary(false);
		// 重置座席安排
		newMain.setFdIsSeatPlan(false);
		newMain.setFdSeatPlanId(null);
	}

	/**
	 * 周期性会议处理
	 */
	private void handleRecurrence(KmImeetingMain kmImeetingMain)
			throws ParseException {
		String recurrenceStr = kmImeetingMain.getFdRecurrenceStr();
		if (StringUtil.isNotNull(recurrenceStr)) {
			Date recurrenceLastStart = RecurrenceUtil.getLastedExecuteDate(
					recurrenceStr, kmImeetingMain.getFdHoldDate());
			if (recurrenceLastStart != null) {
				Long recurrenceLastEndLong = recurrenceLastStart.getTime()
						+ kmImeetingMain.getFdFinishDate().getTime()
						- kmImeetingMain.getFdHoldDate().getTime();
				kmImeetingMain.setFdRecurrenceLastStart(recurrenceLastStart);
				kmImeetingMain.setFdRecurrenceLastEnd(
						new Date(recurrenceLastEndLong));
			} else {
				kmImeetingMain.setFdRecurrenceLastStart(null);
				kmImeetingMain.setFdRecurrenceLastEnd(null);
			}
		} else {
			kmImeetingMain.setFdRecurrenceStr(null);
			kmImeetingMain.setFdRecurrenceLastStart(null);
			kmImeetingMain.setFdRecurrenceLastEnd(null);
		}
	}

	/**
	 * 提前结束会议,对应需求#27359
	 */
	@Override
	public void updateEarlyEndMeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		Date fdEarlyFinishDate = kmImeetingMain.getFdEarlyFinishDate();
		if (fdEarlyFinishDate != null) {
			if (fdEarlyFinishDate.getTime() >= kmImeetingMain.getFdHoldDate()
					.getTime()) {
				if (fdEarlyFinishDate.getTime() <= kmImeetingMain
						.getFdFinishDate()
						.getTime()) {
					// 记录操作日志
					if (UserOperHelper.allowLogOper(
							SysLogOperXml.LOGPOINT_UPDATE, getModelName())) {
						UserOperContentHelper.putUpdate(kmImeetingMain)
								.putSimple("fdFinishDate",
										kmImeetingMain.getFdFinishDate(),
										fdEarlyFinishDate);
					}
					//#165972会议结束时间按照周期性会议结束时间，不是提前结束时间
					//kmImeetingMain.setFdFinishDate(fdEarlyFinishDate);
					addEarlyEndMeetingHistory(kmImeetingMain);
					saveDeleteMeetingExpiredTodo(kmImeetingMain);// 创建删除指定会议待办定时器
					getBaseDao().update(kmImeetingMain);
				} else {
					throw new KmssRuntimeException(new RuntimeException(
							ResourceUtil.getString(
									"kmImeeting.earlyEnd.errorMsg.three",
									"km-imeeting")));
				}
			} else {
				throw new KmssRuntimeException(
						new RuntimeException(
								ResourceUtil.getString(
										"kmImeeting.earlyEnd.errorMsg.two",
										"km-imeeting")));
			}
		} else {
			throw new KmssRuntimeException(
					new RuntimeException(ResourceUtil.getString(
							"kmImeeting.earlyEnd.errorMsg.one",
							"km-imeeting")));
		}
	}

	/**
	 * 	检测是否自动取消会议定时器,对应需求#9195
	 * 	取消此功能 #20351
	 */
	public void saveCheckIsCancel(KmImeetingMain kmImeetingMain)
			throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("checkIsCancel");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("checkIsCancelQuart");
		JSONObject parameter = new JSONObject();
		parameter.put("fdId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("检测是否自动取消会议");
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain
				.getFdFinishDate(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}

	/**
	 * 检测是否自动取消会议<br/>
	 * 取消此功能 #20351
	 */
	@Override
	public void checkIsCancel(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this
				.findByPrimaryKey(parameter.getString("fdId"));
		if (kmImeetingMain != null) {
			if (kmImeetingMain.getIsNotify() == null
					|| kmImeetingMain.getIsNotify() != true) {
				kmImeetingMain.setDocStatus(ImeetingConstant.STATUS_CANCEL);
				kmImeetingMain.setCancelMeetingReason(ResourceUtil.getString(
						"kmImeetingMain.cancelReason.tip", "km-imeeting"));
				getBaseDao().update(kmImeetingMain);
				// 增加取消会议时间轴
				addCancelHistory(kmImeetingMain);
			}
		}
	}

	/**
	 * 获取会议变更前的主持人、参加人员、列席人员
	 */
	private List<SysOrgElement> getBeforePerson(KmImeetingMain kmImeetingMain)
			throws Exception {
		String beforeChangeContent = kmImeetingMain.getBeforeChangeContent();
		if(StringUtil.isNotNull(beforeChangeContent)) {
			JSONObject json = JSONObject.fromObject(beforeChangeContent);
			// 变更前人员
			List<String> beforeIds = new ArrayList<String>();
			if (json.get("fdHostId") != null) {
				beforeIds.add(json.getString("fdHostId"));
			}
			if (json.get("fdAttendPersonIds") != null
					&& StringUtil.isNotNull(json.getString("fdAttendPersonIds"))) {
				String[] ids = json.getString("fdAttendPersonIds").split(";");
				beforeIds.addAll(ArrayUtil.convertArrayToList(ids));
			}
			if (json.get("fdParticipantPersonIds") != null
					&& StringUtil.isNotNull(json
					.getString("fdParticipantPersonIds"))) {
				String[] ids = json.getString("fdParticipantPersonIds").split(";");
				beforeIds.addAll(ArrayUtil.convertArrayToList(ids));
			}
			List<SysOrgElement> persons = sysOrgCoreService
					.findByPrimaryKeys(beforeIds.toArray(new String[0]));
			return persons;
		}
		return null;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmImeetingMain kmImeetingMain = (KmImeetingMain) modelObj;
		List<KmImeetingSummary> summaryList = kmImeetingMain.getKmImeetingSummarys();
		if(summaryList != null && summaryList.size() > 0) {
			List<String> idsList = new ArrayList<String>();
			for(int i = 0; i < summaryList.size(); i++) {
				KmImeetingSummary summary = summaryList.get(i);
				if(summary != null) {
					idsList.add(summary.getFdId());
				}
				summaryList.remove(i);
				i--;
			}
			String[] summaryIdArr = new String[idsList.size()];
			idsList.toArray(summaryIdArr);
			getKmImeetingSummaryService().delete(summaryIdArr);
		}
		
		// 删除坐席安排
		getKmImeetingSeatPlanService()
				.deleteAllSeatPlan(kmImeetingMain.getFdId());

		// 删除日程
		if ("sendNotify".equals(kmImeetingMain.getSyncDataToCalendarTime())
				|| "personAttend".equals(kmImeetingMain
				.getSyncDataToCalendarTime())) {
			//删除之前的会议日程新逻辑
			//deleteSyncDataToCalendar(kmImeetingMain,getAllAttendPersons(kmImeetingMain));
			deleteSyncImeetingToCalendar(kmImeetingMain,getAllAttendPersons(kmImeetingMain));
		}
		if (!kmImeetingMain.isSynchroIn()) {
			// 删除会议到第三方应用,如exchange
			SynchroThread thread = new SynchroThread(kmImeetingMain,
					SynchroConstants.OPERATE_DELETE);
			thread.start();
		}
		// 云会议
		if (kmImeetingMain.getIsCloud()) {
			kmImeetingOutVedioService.deleteImeeting(kmImeetingMain);
		}
		super.delete(modelObj);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
            return;
        }
		Object obj = event.getSource();
		// 若该事件不是自己的域模型，不处理
		if (!(obj instanceof KmImeetingMain)) {
            return;
        }
		// 处理流程发布事件
		if (event instanceof Event_SysFlowFinish) {
			KmImeetingMain kmImeetingMain = (KmImeetingMain) obj;
			try {
				// 如果开启了议题管理，更新议题状态为是否已经上会
				if ("true".equals(KmImeetingConfigUtil.isTopicMng())
						|| (kmImeetingMain.getFdIsTopic() != null && kmImeetingMain.getFdIsTopic())) {
					List agendaList = kmImeetingMain.getKmImeetingAgendas();
					for (int i = 0; i < agendaList.size(); i++) {
						KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
						String fdTopicId = kmImeetingAgenda.getFdFromTopicId();
						KmImeetingTopic kmImeetingTopic = (KmImeetingTopic) kmImeetingTopicService
								.findByPrimaryKey(fdTopicId, KmImeetingTopic.class.getName(), true);
						if (kmImeetingTopic != null && !kmImeetingTopic.getFdIsAccept()) {
							kmImeetingTopic.setFdIsAccept(Boolean.TRUE);
							kmImeetingTopicService.update(kmImeetingTopic);
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			kmImeetingMain.setDocPublishTime(new Date());
			try {
				// 是否会议变更
				if (kmImeetingMain.getFdChangeMeetingFlag()) {
					logger.debug("会议变更...");
					// 会议未召开才发送待办
					if (kmImeetingMain.getFdHoldDate().getTime() >= new Date().getTime()) {
						// 发送变更待办、日程
						sendChangeNotify(kmImeetingMain);
					}
					// 生成变更历史操作
					addChangeHistory(kmImeetingMain);
				} else {
					// 会议未召开才发送待办，同步会议到第三方应用
					if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
						logger.debug("会议发布...");
						if (ImeetingConstant.MEETING_NOTIFY_AUTO
								.equals(kmImeetingMain.getFdNotifyType())&&(kmImeetingMain.getIsNotify() == null || !kmImeetingMain.getIsNotify())) {
							// 发送通知
							kmImeetingMain.setFdNotifyer(kmImeetingMain
									.getDocCreator());// 自动发送会议默认通知人为创建者
							logger.debug("自动发送会议默认通知人为创建者");
							saveSendMeetingNotify(kmImeetingMain);
						}
					}else{
						//会议召开时间大于当前时间（已召开）并且是周期性会议生成定时任务
						if(StringUtil.isNotNull(kmImeetingMain.getFdRecurrenceStr())) {
							// 创建删除指定会议待办定时器
							saveDeleteMeetingExpiredTodo(kmImeetingMain);
						}
					}
				}
				
				// 生成同步到铂恩定时任务
				if (BoenUtil.isBoenEnable()
						&& kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
					if (kmImeetingMain.getFdFeedBackDeadline() != null
							&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
						saveSyncToBoenQuartz(kmImeetingMain);
					}
					// 生成会后附件获取定时任务
					saveSyncAttFromBoenQuartz(kmImeetingMain);
				}
			} catch (Exception e) {
				logger.error("会议流程结束后的异常：",e);
//				e.printStackTrace();
			}
		} else if (event instanceof Event_SysFlowRefuse
				|| event instanceof Event_SysFlowDiscard) {

		}
	}

	// ************** 发送会议通知单相关业务(开始) ******************************//

	/**
	 * 初始化ICS信息
	 */
	private void initMailIcsInfo(KmImeetingMain kmImeetingMain,
			NotifyContext notifyContext) {

		try {
			KmImeetingConfig config = new KmImeetingConfig();
			String setICS = config.getSetICS();
			if ("false".equals(setICS)) {
				return;
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		MailContext mailContext = notifyContext
				.getExtendContext(MailContext.class);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

		format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String holdDate = "TZID=Asia/Shanghai:"
				+ format.format(kmImeetingMain.getFdHoldDate());
		// System.out.println(holdDate);
		String finishDate = "TZID=Asia/Shanghai:"
				+ format.format(kmImeetingMain.getFdFinishDate());
		MailIcsInfo mailIcsInfo = new MailIcsInfo(kmImeetingMain.getFdName(),
				holdDate, finishDate);
		mailIcsInfo.setAdvancedAlarmMinute(15);
		SysOrgPerson host = kmImeetingMain.getFdHost();
		if (host != null && StringUtil.isNotNull(host.getFdEmail())) {
			mailIcsInfo.setOrganizer(host.getFdEmail());
		}
		mailIcsInfo
				.setLocation(kmImeetingMain.getFdPlace() == null ? kmImeetingMain
						.getFdOtherPlace() : kmImeetingMain.getFdPlace()
						.getFdName());
		mailContext.setMailIcsInfo(mailIcsInfo);
	}



	/**
	 * 发送会议通知单
	 */
	@Override
	public void saveSendMeetingNotify(KmImeetingMain kmImeetingMain)
			throws Exception {
		// 如果是同步会议，需要判断会议召开时间是否已经结束
		if (BooleanUtils.isTrue(kmImeetingMain.isSynchroIn())
				&& (kmImeetingMain.getFdHoldDate() == null || kmImeetingMain.getFdHoldDate().before(new Date()))) {
			// 已经过时了，不需要发送待办了
			logger.debug("同步会议，召开时间已经过时了，取消发送待办：" + kmImeetingMain.getDocSubject() + " --> "
					+ kmImeetingMain.getFdHoldDate());
			return;
		}
		logger.debug("开始发送会议通知单");
		if (kmImeetingMain.getFdNotifyer() == null) {
			kmImeetingMain.setFdNotifyer(UserUtil.getUser());
		}
		// 给会议组织人发送待办
		sendMeetingNotifyToEmcee(kmImeetingMain);
		// 给会议协助人、会议室管理员发送通知单，绿色待办
		sendMeetingNotifyToAssist(kmImeetingMain);
		// 给主持人、参与人、列席人员发送通知单，红色待办
		sendMeetingNotifyToAttendPerson(kmImeetingMain);

		// 给抄送人发送通知单，绿色待办
		sendMeetingNotifyToCC(kmImeetingMain);

		// 通知纪要录入人录入纪要
		sendMeetingNotifySummaryInputPerson(kmImeetingMain);

		// 开启了议题管理，不需要给材料负责人
		if (!"true".equals(KmImeetingConfigUtil.isTopicMng())
				|| (kmImeetingMain.getFdIsTopic() != null && !kmImeetingMain.getFdIsTopic())) {
			// 通知材料负责人上传材料,生成催办上会材料定时器
			sendAttNotifyToResponser(kmImeetingMain);
			saveHastenUploadAtt(kmImeetingMain);
		}

		// 增加“发送会议通知”历史记录
		addNotifyHistory(kmImeetingMain);
		if (kmImeetingMain.getFdNeedFeedback()) {
			// 初始化回执单
			kmImeetingMainFeedbackService.saveFeedbacks(kmImeetingMain);
		}
		// 同步机制为“发送会议通知后同步”
		if (ImeetingConstant.MEETING_SYNC_SENDNOTIFY.equals(kmImeetingMain
				.getSyncDataToCalendarTime())) {
			// #141688 会议日程同步使用新逻辑
			//addSyncDataToCalendar(kmImeetingMain);
			addSyncImeetingToCal(kmImeetingMain,getAllAttendPersons(kmImeetingMain));
		}

		kmImeetingMain.setIsNotify(true);
		saveDeleteMeetingExpiredTodo(kmImeetingMain);// 创建删除指定会议待办定时器
		getBaseDao().update(kmImeetingMain);// 重新计算权限

		if (!kmImeetingMain.isSynchroIn()) {
			SynchroThread synchroThread = new SynchroThread(kmImeetingMain,
					SynchroConstants.OPERATE_ADD);
			synchroThread.start();
		}

		// 接出到云会议
		if (kmImeetingMain.getIsCloud()) {
			IMeetingIntegrateThread integrateThread = new IMeetingIntegrateThread(
					kmImeetingMain);
			integrateThread.start();
		}

		// 对接到阿里云视频会议,不需要回执时，直接同步对接
		if (kmImeetingMain.getFdIsVideo()
				&& AliMeetingUtil.isAliyunEnable()) {
			if ("0".equals(AliMeetingUtil.getServiceType())) {
				if (!kmImeetingMain.getFdNeedFeedback()) {
					IMeetingKkVideoThread integrateThread = new IMeetingKkVideoThread(kmImeetingMain,
							SynchroConstants.OPERATE_ADD);
					integrateThread.start();
				} else {
					// 生成同步到kk定时任务
					if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
						if (kmImeetingMain.getFdFeedBackDeadline() != null
								&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
							saveSyncToKkQuartz(kmImeetingMain);
						}
					}
				}
			} else if ("1".equals(AliMeetingUtil.getServiceType())) { // 如果没有集成KK，启用阿里云视频会议
				if (!kmImeetingMain.getFdNeedFeedback()) {
					IMeetingAliyunThread integrateThread = new IMeetingAliyunThread(kmImeetingMain,
							SynchroConstants.OPERATE_ADD);
					integrateThread.start();
				} else {
					// 生成同步到阿里云定时任务
					if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
						if (kmImeetingMain.getFdFeedBackDeadline() != null
								&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
							saveSyncMeetingToAliyunQuartz(kmImeetingMain);
						}
					}
				}
			}
		}
	}

	/**
	 * 添加日程提醒 1.所有日程提醒都未过期，给所有人发送日程提醒 2.所有日程提醒都过期，给发起人发送过期日程提醒
	 * 3.部分日程提醒过期，只给未过期的日程提醒创建提醒
	 *
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	private void addSyncDataToCalendar(KmImeetingMain kmImeetingMain)
			throws Exception {
		addSyncDataToCalendar(kmImeetingMain,
				getAllAttendPersons(kmImeetingMain));
	}

	public void saveSyncToKkQuartz(KmImeetingMain kmImeetingMain) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("syncMeetingToKk");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("addSyncToKkQuartz");
		JSONObject parameter = new JSONObject();
		parameter.put("fdMeetingId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("同步到Kk阿里云会议，标题:" + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain.getFdFeedBackDeadline(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}

	@Override
	public void syncMeetingToKk(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(parameter.getString("fdMeetingId"),
				KmImeetingMain.class.getName(), true);
		if (kmImeetingMain != null) {
			KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
					.findByPrimaryKey(kmImeetingMain.getFdId(), KmImeetingMapping.class.getName(), true);
			if (kim == null) {
				kim = kmImeetingMappingService.findByModelId(kmImeetingMain.getFdId(), KmImeetingMain.class.getName());
			}
			if (kim == null) {
				addSyncToKk(kmImeetingMain);
			}
		}
	}

	/**
	 * 给会议组织人发送待办，绿色待办
	 */
	private void sendMeetingNotifyToEmcee(KmImeetingMain kmImeetingMain)
			throws Exception {
		if (kmImeetingMain.getFdEmcee() != null && kmImeetingMain.getFdEmcee()
				.getFdId() != kmImeetingMain.getDocCreator().getFdId()) {
			NotifyContext notifyContext = null;
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			// 是否有会议室
			if (kmImeetingMain.getFdPlace() == null
					&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.emcee.notify");
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.emcee.notify.place");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			}
			List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
			receiver.add(kmImeetingMain.getFdEmcee());
			notifyContext.setKey("EmceeImeetingKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
			notifyContext.setNotifyTarget(receiver);// 通知人员
			notifyContext.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=editEmcee&fdId="
					+ kmImeetingMain.getFdId());
			notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
			// .getFdName());// 会议名称
			// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
			// .convertDateToString(kmImeetingMain.getFdHoldDate(),
			// DateUtil.PATTERN_DATETIME));// 召开时间
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
							.getFdName());
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	/**
	 * 给会议协助人、会议室管理员发送通知单，绿色待办
	 */
	private void sendMeetingNotifyToAssist(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
		// 会议协助人
		if (kmImeetingMain.getFdAssistPersons() != null
				&& !kmImeetingMain.getFdAssistPersons().isEmpty()) {
			receiver.addAll(kmImeetingMain.getFdAssistPersons());
		}
		// 会议室管理员
		if (kmImeetingMain.getFdPlace() != null
				&& kmImeetingMain.getFdPlace().getDocKeeper() != null) {
			receiver.add(kmImeetingMain.getFdPlace().getDocKeeper());
		}
		if (!receiver.isEmpty()) {
			// 展开成人员
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.assist.notify");
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			// 是否有会议室
			if (kmImeetingMain.getFdPlace() == null
					&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.assist.notify");
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.assist.notify.place");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			}
			notifyContext.setKey("AssistImeetingKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
			notifyContext.setNotifyTarget(receiver);// 通知人员
			notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&type=assist&fdId="
							+ kmImeetingMain.getFdId());
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
			// .getFdName());
			// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
			// .convertDateToString(kmImeetingMain.getFdHoldDate(),
			// DateUtil.PATTERN_DATETIME));// 召开时间
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
					.getFdName());
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	/**
	 * 给主持人、参与人、列席人员发送通知单，红色待办
	 */
	private void sendMeetingNotifyToAttendPerson(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
		// 主持人
		if (kmImeetingMain.getFdHost() != null) {
			receiver.add(kmImeetingMain.getFdHost());
		}
		// 与会人员
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
			receiver.addAll(kmImeetingMain.getFdAttendPersons());
		}
		// 列席人员
		if (kmImeetingMain.getFdParticipantPersons() != null
				&& !kmImeetingMain.getFdParticipantPersons().isEmpty()) {
			receiver.addAll(kmImeetingMain.getFdParticipantPersons());
		}
		List agendaList = kmImeetingMain.getKmImeetingAgendas();
		for (int i = 0; i < agendaList.size(); i++) {
			KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
			// 汇报人
			if (kmImeetingAgenda.getDocReporter() != null) {
				receiver.add(kmImeetingAgenda.getDocReporter());
			}
		}
		if (!receiver.isEmpty()) {
			sendMeetingNotifyToAttendPersonInner(kmImeetingMain, receiver);
		}
	}

	private void sendMeetingNotifyToAttendPersonInner(KmImeetingMain kmImeetingMain, List<SysOrgElement> receiver)
			throws Exception {
		NotifyContext notifyContext = null;
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// 是否有会议室
		if (kmImeetingMain.getFdPlace() == null && StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
			notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.attend.notify");
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace",
					ResourceUtil.getString("kmImeetingMain.isCloud", "km-imeeting"));
		} else {
			notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.attend.notify.place");
			// 获取会议地址多语言信息
			KmImeetingRes place = kmImeetingMain.getFdPlace();
			setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
		}
		notifyContext.setKey("AttendImeetingKey");
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setNotifyTarget(receiver);// 通知人员
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		notifyContext
				.setLinkSubject(ResourceUtil.getString("kmImeetingMain.email.attend.notify.subject", "km-imeeting"));// 邮件格式
		if (kmImeetingMain.getFdTemplate() != null || kmImeetingMain.getFdNeedFeedback()) {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setLink(
					"/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&_todo=1&type=attend&meetingId="
							+ kmImeetingMain.getFdId());
		} else {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		}

		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName", kmImeetingMain.getFdName());
		notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate", kmImeetingMain.getFdHoldDate(),
				DateUtil.TYPE_DATETIME);
		String fdHost = "";
		if (kmImeetingMain.getFdHost() != null) {
			fdHost = kmImeetingMain.getFdHost().getFdName();
		}
		if (StringUtil.isNotNull(kmImeetingMain.getFdOtherHostPerson())) {
			fdHost += kmImeetingMain.getFdOtherHostPerson();
		}
		// hashMap.put("km-imeeting:kmImeetingMain.fdHost", fdHost);// 主持人
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdHost", fdHost);
		// #26739 初始化ics信息
		initMailIcsInfo(kmImeetingMain, notifyContext);
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext, notifyReplace);
	}

	/**
	 * 给抄送人发送通知单，绿色待办
	 */
	private void sendMeetingNotifyToCC(KmImeetingMain kmImeetingMain)
			throws Exception {
		if (kmImeetingMain.getFdCopyToPersons() != null
				&& !kmImeetingMain.getFdCopyToPersons().isEmpty()) {
			List<SysOrgElement> receiver = kmImeetingMain.getFdCopyToPersons();
			NotifyContext notifyContext = null;
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			// 是否有会议室
			if (kmImeetingMain.getFdPlace() == null
					&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.cc.notify");
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.cc.notify.place");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			}
			notifyContext.setKey("CCImeetingKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
			notifyContext.setNotifyTarget(receiver);// 通知人员
			notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&type=cc&fdId="
							+ kmImeetingMain.getFdId());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
							.getFdName());
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	/**
	 * 删除指定会议待办定时器,对应需求#18967
	 */
	public void saveDeleteMeetingExpiredTodo(KmImeetingMain kmImeetingMain)
			throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("deleteMeetingExpiredTodo");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("deleteMeetingExpiredTodo");
		JSONObject parameter = new JSONObject();
		parameter.put("fdId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzRequired(true);
		quartzContext.setQuartzSubject(ResourceUtil.getString("kmImeeting.expiredTodo.delete", "km-imeeting") + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(
				kmImeetingMain.getFdFinishDate(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}

	/**
	 * 删除指定会议待办
	 */
	@Override
	public void deleteMeetingExpiredTodo(SysQuartzJobContext context)
			throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this
				.findByPrimaryKey(parameter.getString("fdId"), null, true);
		if (kmImeetingMain != null) {
			String recurrenceStr = kmImeetingMain.getFdRecurrenceStr();
			if (StringUtil.isNotNull(recurrenceStr)) {
				createAfterImeeting(kmImeetingMain);
				updateCurrentInfo(kmImeetingMain);
			}
			String[] todos = new String[] { "AssistImeetingKey",
					"EmceeImeetingKey", "SummaryInputPersonImeetingKey",
					"ImeetingUploadAttKey", "AttendImeetingKey",
					"CCImeetingKey", "HastenImeetingKey", "CancelImeetingKey",
					"changeImeetingKey", "kmImeetingSummaryKey",
					"kmImeetingHastenSummaryKey" };// 会议中涉及到的待办
			for (String todo : todos) {
				sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(
						kmImeetingMain, todo, null, null);
			}
			deleteAgendaTodos(kmImeetingMain);

			kmImeetingMain.setFdCleanTodoFlag(true);
			this.getBaseDao().update(kmImeetingMain);
		}
	}

	// ************** 发送会议通知单相关业务(结束) ******************************//

	// ************** 催办会议相关业务(开始) ******************************//
	/**
	 * 催办会议
	 */
	@Override
	public void saveHastenMeeting(KmImeetingMain kmImeetingMain,
			KmImeetingMainHastenForm kmImeetingMainHastenForm) throws Exception {
		sendHastenNotify(kmImeetingMain, kmImeetingMainHastenForm);
		UserOperHelper.logFind(kmImeetingMain);
	}

	/**
	 * 催办会议待办
	 */
	private void sendHastenNotify(KmImeetingMain kmImeetingMain,
			KmImeetingMainHastenForm kmImeetingMainHastenForm) throws Exception {
		if (StringUtil.isNotNull(kmImeetingMainHastenForm
				.getHastenNotifyPersonIds())) {
			String[] ids = kmImeetingMainHastenForm.getHastenNotifyPersonIds()
					.split(";");
			List<SysOrgElement> targets = sysOrgCoreService
					.findByPrimaryKeys(ids);
			NotifyContext notifyContext = null;
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			if (kmImeetingMain.getFdPlace() == null
					&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.hasten.notify");
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.hasten.notify.place");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			}
			notifyContext.setKey("HastenImeetingKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyType(kmImeetingMainHastenForm
					.getFdNotifyType());// 通知方式
			notifyContext.setNotifyTarget(targets);
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
			// .getFdName());// 会议名称
			// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
			// .convertDateToString(kmImeetingMain.getFdHoldDate(),
			// DateUtil.PATTERN_DATETIME));// 召开时间
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
					.getFdName());// 会议名称
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	// ************** 催办会议相关业务(结束) ******************************//

	// ************** 取消会议相关业务(开始) ******************************//
	/**
	 * 取消会议
	 */
	@Override
	public void updateCancelMeeting(KmImeetingMain kmImeetingMain,
			KmImeetingMainCancelForm kmImeetingMainCancelForm) throws Exception {
		if (UserOperHelper.allowLogOper("cancelMeeting", getModelName())) {
			UserOperContentHelper.putUpdate(kmImeetingMain)
					.putSimple("docStatus", kmImeetingMain.getDocStatus(),
							ImeetingConstant.STATUS_CANCEL)
					.putSimple("cancelMeetingReason",
							kmImeetingMain.getCancelMeetingReason(),
							kmImeetingMainCancelForm.getCancelReason());
		}
		String cancelType = kmImeetingMainCancelForm.getFdCancelType();
		if (StringUtil.isNotNull(cancelType)) {
			if ("cur".equals(cancelType)) {
				createAfterImeeting(kmImeetingMain);
				updateCurrentInfo(kmImeetingMain);
				handleRecurrence(kmImeetingMain);
			} else if ("after".equals(cancelType)) {
				cancelAfterImeeting(kmImeetingMain, kmImeetingMainCancelForm);
				return;
			}
		}
		// 修改会议状态
		kmImeetingMain.setDocStatus(ImeetingConstant.STATUS_CANCEL);
		kmImeetingMain.setCancelMeetingReason(kmImeetingMainCancelForm
				.getCancelReason());
		// 增加”取消会议“历史操作
		addCancelHistory(kmImeetingMain);
		// 格式化“取消会议”内容
		String cancelReasonContent = ResourceUtil.getString(
				"kmImeetingMain.cancelMeetingReason.content", "km-imeeting")
				.replace("%cancelPerson%", UserUtil.getUser().getFdName())
				.replace(
						"%cancelDate%",
						DateUtil.convertDateToString(new Date(),
								DateUtil.PATTERN_DATETIME))
				.replace(
						"%cancelReason%",
						kmImeetingMain.getCancelMeetingReason());
		kmImeetingMain.setCancelMeetingReason(cancelReasonContent);
		// 删除日程
		if (!"noSync".equals(kmImeetingMain.getSyncDataToCalendarTime())) {
			//删除同步会议用新接口
			deleteSyncImeetingToCalendar(kmImeetingMain,getAllAttendPersons(kmImeetingMain));
//			deleteSyncDataToCalendar(kmImeetingMain,
//					getAllAttendPersons(kmImeetingMain));
		}
		this.getBaseDao().update(kmImeetingMain);
		// 删除过往待办
		delNotifyForCancelMeeting(kmImeetingMain);
		// 发送取消代办
		sendCancelNotify(kmImeetingMain, kmImeetingMainCancelForm);
		//删除提醒定时任务
		deletRemindTask(kmImeetingMain);
		SynchroThread thread = new SynchroThread(kmImeetingMain,
				SynchroConstants.OPERATE_CANCEL);
		thread.start();

		// 云会议
		if (kmImeetingMain.getIsCloud()) {
			kmImeetingOutVedioService.cacelImeeting(kmImeetingMain);
		}
		
	}
	//删除提醒定时任务
	private void deletRemindTask(KmImeetingMain kmImeetingMain) throws Exception {
		String fdId = kmImeetingMain.getFdId();
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("sysRemindMainTask.fdModelId=:fdModelId");
		hql.setParameter("fdModelId",fdId);
		List list = getSysRemindMainTaskService().findList(hql);
		if(!ArrayUtil.isEmpty(list)){
			SysRemindMainTask task= (SysRemindMainTask) list.get(0);
			sysQuartzCoreService.delete(task, "remindJob");
		}
	}

	private void cancelAfterImeeting(KmImeetingMain main,
			KmImeetingMainCancelForm kmImeetingMainCancelForm)
			throws Exception {
		KmImeetingMainForm newForm = new KmImeetingMainForm();
		RequestContext requestContext = new RequestContext();
		KmImeetingMainForm mainForm = new KmImeetingMainForm();
		mainForm = (KmImeetingMainForm) convertModelToForm(
				mainForm, main, requestContext);
		KmImeetingMain newMain = getNewImeeting(main);
		if (newMain != null) {
			// 关联原始会议
			newMain.setFdOriId(main.getFdId());
			newMain.setFdChangeType("after");
			// 修改会议状态
			newMain.setDocStatus(ImeetingConstant.STATUS_CANCEL);
			newMain.setCancelMeetingReason(kmImeetingMainCancelForm
					.getCancelReason());
			// 增加”取消会议“历史操作
			addCancelHistory(newMain);
			// 格式化“取消会议”内容
			String cancelReasonContent = ResourceUtil.getString(
					"kmImeetingMain.cancelMeetingReason.content",
					"km-imeeting")
					.replace("%cancelPerson%",
							UserUtil.getUser().getFdName())
					.replace(
							"%cancelDate%",
							DateUtil.convertDateToString(new Date(),
									DateUtil.PATTERN_DATETIME))
					.replace(
							"%cancelReason%",
							newMain.getCancelMeetingReason());
			newMain.setCancelMeetingReason(cancelReasonContent);
			// 删除日程
			if (!"noSync"
					.equals(newMain.getSyncDataToCalendarTime())) {
				deleteSyncDataToCalendar(newMain,
						getAllAttendPersons(newMain));
			}
			newForm = (KmImeetingMainForm) convertModelToForm(
					newForm, newMain, requestContext);
			resetForm(newForm, mainForm);
			add(newForm, requestContext);
			// 删除过往待办
			delNotifyForCancelMeeting(newMain);
			// 发送取消代办
			sendCancelNotify(newMain, kmImeetingMainCancelForm);
			SynchroThread thread = new SynchroThread(newMain,
					SynchroConstants.OPERATE_CANCEL);
			thread.start();
			updateCurrentInfo(main);
			handleRecurrence(main);
			getBaseDao().update(main);
		}
	}

	/**
	 * 取消会议时收回以前的待办，定时器
	 */
	private void delNotifyForCancelMeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		// 删除待办
		String[] todos = new String[] { "AssistImeetingKey",
				"EmceeImeetingKey", "SummaryInputPersonImeetingKey",
				"ImeetingUploadAttKey", "AttendImeetingKey", "CCImeetingKey",
				"HastenImeetingKey", "kmImeetingSummaryKey",
				"kmImeetingHastenSummaryKey" };// 会议中涉及到的待办
		for (String todo : todos) {
			sysNotifyMainCoreService.getTodoProvider().remove(kmImeetingMain,
					todo);
		}
		deleteAgendaTodos(kmImeetingMain);
		// 删除定时器
		for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
				.getKmImeetingAgendas()) {
			sysQuartzCoreService.delete(kmImeetingAgenda,
					"hastenUploadAttQuart");
		}
	}

	/**
	 * 发送取消会议待办
	 */
	private void sendCancelNotify(KmImeetingMain kmImeetingMain,
			KmImeetingMainCancelForm kmImeetingMainCancelForm) throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-imeeting:kmImeetingMain.cancel.notify");
		NotifyReplace notifyReplace = new NotifyReplace();

		if (kmImeetingMain.getFdPlace() != null || StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.cancel.place.notify");
		}
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		notifyContext.setKey("CancelImeetingKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyType(kmImeetingMainCancelForm.getFdNotifyType());// 通知类型
		notifyContext.setNotifyTarget(getAllPerson(kmImeetingMain));// 通知人#7680
		// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
		// .getFdName());// 会议名称
		// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
		// .convertDateToString(kmImeetingMain.getFdHoldDate(),
		// DateUtil.PATTERN_DATETIME));
		String place = "";
		if (kmImeetingMain.getFdPlace() != null) {
			place += kmImeetingMain.getFdPlace().getFdName();
		}
		if (StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			place += kmImeetingMain.getFdOtherPlace();
		}
		if (StringUtil.isNotNull(place)) {
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace", place);
		}
		// hashMap.put("km-imeeting:kmImeetingMain.fdPlace", place);

		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
				kmImeetingMain
						.getFdName());
		notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
				kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);

		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	// 获取会议相关的所有人
	private List<SysOrgElement> getAllPerson(KmImeetingMain kmImeetingMain) throws Exception {
		List<SysOrgElement> all = new ArrayList<SysOrgElement>();
		if (kmImeetingMain.getFdEmcee() != null) {// 会议组织人
			all.add(kmImeetingMain.getFdEmcee());
		}
		if (kmImeetingMain.getFdHost() != null) {// 会议主持人
			all.add(kmImeetingMain.getFdHost());
		}
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {// 会议参与人
			all.addAll(kmImeetingMain.getFdAttendPersons());
		}
		if (kmImeetingMain.getFdParticipantPersons() != null
				&& !kmImeetingMain.getFdParticipantPersons().isEmpty()) {// 会议列席人
			all.addAll(kmImeetingMain.getFdParticipantPersons());
		}
		if (kmImeetingMain.getFdOtherPlace() != null
				&& !kmImeetingMain.getFdOtherPersons().isEmpty()) {
			all.addAll(kmImeetingMain.getFdOtherPersons());
		}
		if (kmImeetingMain.getFdCopyToPersons() != null
				&& !kmImeetingMain.getFdCopyToPersons().isEmpty()) {
			all.addAll(kmImeetingMain.getFdCopyToPersons());
		}
		if (kmImeetingMain.getFdAssistPersons() != null
				&& !kmImeetingMain.getFdAssistPersons().isEmpty()) {// 会议协助人
			all.addAll(kmImeetingMain.getFdAssistPersons());
		}
		if (kmImeetingMain.getFdPlace() != null
				&& kmImeetingMain.getFdPlace().getDocKeeper() != null) {// 会议室保管员
			all.add(kmImeetingMain.getFdPlace().getDocKeeper());
		}
		if (kmImeetingMain.getFdSummaryInputPerson() != null) {// 会议纪要人员
			all.add(kmImeetingMain.getFdSummaryInputPerson());
		}

		for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
				.getKmImeetingAgendas()) {
			// 汇报人
			if (kmImeetingAgenda.getDocReporter() != null) {
				all.add(kmImeetingAgenda.getDocReporter());
			}
			if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
				// 建议列席单位的会议联络员
				if (kmImeetingAgenda.getFdAttendUnit() != null && !kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
					List fdAttendUnit = kmImeetingAgenda.getFdAttendUnit();
					for (int m = 0; m < fdAttendUnit.size(); m++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdAttendUnit.get(m);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							all.addAll(kmImissiveUnit.getFdMeetingLiaison());
						}
					}
				}
				// 建议旁听单位的会议联络员
				if (kmImeetingAgenda.getFdListenUnit() != null && !kmImeetingAgenda.getFdListenUnit().isEmpty()) {
					List fdListenUnit = kmImeetingAgenda.getFdListenUnit();
					for (int m = 0; m < fdListenUnit.size(); m++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdListenUnit.get(m);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							all.addAll(kmImissiveUnit.getFdMeetingLiaison());
						}
					}
				}
			} else {
				// 上会材料责任人
				if (kmImeetingAgenda.getDocRespons() != null) {
					all.add(kmImeetingAgenda.getDocRespons());
				}
			}
		}
		return all;
	}

	// ************** 取消会议相关业务(结束) ******************************//

	// ************** 会议变更相关业务(开始) ******************************//

	/**
	 * 变更会议待办(需求关联单#7674)
	 */
	private void sendChangeNotify(KmImeetingMain kmImeetingMain)
			throws Exception {
		String beforeChangeContent = kmImeetingMain.getBeforeChangeContent();
		if(StringUtil.isNull(beforeChangeContent)){
			return;
		}

		saveDeleteMeetingExpiredTodo(kmImeetingMain);// 创建删除指定会议待办定时器

		// 重发纪要录入人录入纪要待办
		sendMeetingNotifySummaryInputPerson(kmImeetingMain);
		if (!"true".equals(KmImeetingConfigUtil.isTopicMng())
				|| (kmImeetingMain.getFdIsTopic() != null && !kmImeetingMain.getFdIsTopic())) {
			// 重发通知材料负责人上传材料待办
			sendAttNotifyToResponser(kmImeetingMain);
		}

		List newIds = new ArrayList();
		List oldIds = new ArrayList();
		List<KmImeetingAgenda> newKmImeetingAgenda = kmImeetingMain.getKmImeetingAgendas();
		for (int i = 0; i < newKmImeetingAgenda.size(); i++) {
			KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) newKmImeetingAgenda.get(i);
			String fdAgendaId = kmImeetingAgenda.getFdId();
			newIds.add(fdAgendaId);
		}
		JSONArray jsonArr = new JSONArray();
		JSONObject json = JSONObject.fromObject(beforeChangeContent);
		if (json.get("agenda") != null) {
			jsonArr = JSONArray.fromObject(json.get("agenda"));
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jsonA = jsonArr.getJSONObject(i);
				oldIds.add(jsonA.get("agendaId"));
			}
		}
		// 增加的议题,汇报人
		List addAgenda = new ArrayList();
		addAgenda.addAll(newIds);
		addAgenda.removeAll(oldIds);
		for (int a = 0; a < addAgenda.size(); a++) {
			String agendaId = (String) addAgenda.get(a);
			for (KmImeetingAgenda kmImeetingAgenda : newKmImeetingAgenda) {
				if (agendaId.equals(kmImeetingAgenda.getFdId())) {
					SysOrgElement docReporter = kmImeetingAgenda.getDocReporter();
					if (docReporter != null) {
						HQLInfo hqlInfo = new HQLInfo();
						String whereBlock = "kmImeetingMainFeedback.fdMeeting.fdId =:fdMeetingId and kmImeetingMainFeedback.docCreator.fdId=:elementId and kmImeetingMainFeedback.fdType != '"
								+ ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON
								+ "' and kmImeetingMainFeedback.fdType != '"
								+ ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON + "'";
						hqlInfo.setParameter("fdMeetingId", kmImeetingMain.getFdId());
						hqlInfo.setParameter("elementId", docReporter.getFdId());
						hqlInfo.setWhereBlock(whereBlock);
						List<KmImeetingMainFeedback> list = kmImeetingMainFeedbackService.findList(hqlInfo);
						if (list.size() > 0 && !list.isEmpty()) {
							KmImeetingMainFeedback fk = list.get(0);
							String fdType = fk.getFdType();
							String fdAttendAgendaId = fk.getFdAttendAgendaId();
							if (fdType.indexOf(ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER) == -1) {
								fk.setFdType(fdType + ";" + ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER);
							}
							fdAttendAgendaId = StringUtil.linkString(fdAttendAgendaId, ";",
									kmImeetingAgenda.getFdId());
							fk.setFdAttendAgendaId(fdAttendAgendaId);
							kmImeetingMainFeedbackService.update(fk);
						} else {
							List<SysOrgElement> receiver = new ArrayList();
							receiver.add(docReporter);
							sendMeetingNotifyToAttendPersonInner(kmImeetingMain, receiver);
							/*kmImeetingMainFeedbackService.addFeedBack(kmImeetingMain, docReporter,
									ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER,
									kmImeetingAgenda.getFdId());*/
						}
					}
				}
			}
		}

		// 保留的议题
		List retainAgenda = new ArrayList();
		retainAgenda.addAll(oldIds);
		retainAgenda.retainAll(newIds);
		if (!retainAgenda.isEmpty() && !CollectionUtils.isEmpty(jsonArr)) {
			sendChangeNotifyToRetainTopic(kmImeetingMain, jsonArr, retainAgenda);
		}
		// 移除的议题
		List removeAgenda = new ArrayList();
		removeAgenda.addAll(oldIds);
		removeAgenda.removeAll(newIds);
		if (!removeAgenda.isEmpty() && !CollectionUtils.isEmpty(jsonArr)) {
			sendChangeNotifyToRemoveTopic(kmImeetingMain, jsonArr, removeAgenda);
		}

		Map<String, List<SysOrgElement>> changePersons = getChangePersons(kmImeetingMain);
		// 给新增人员发送待办
		List<SysOrgElement> addPerson = changePersons.get("add");
		if (!addPerson.isEmpty()) {
			sendChangeNotifyToNewPerson(kmImeetingMain, addPerson);
		}
		// 给不变人员发送待办
		List<SysOrgElement> retainPerson = changePersons.get("retain");
		if (!retainPerson.isEmpty()) {
			sendChangeNotifyToStaticPerson(kmImeetingMain, retainPerson);
		}
		// 给剔除的人员发送待办
		List<SysOrgElement> removePerson = changePersons.get("remove");
		if (!removePerson.isEmpty()) {
			sendChangeNotifyToRemovePerson(kmImeetingMain, removePerson);
			//剔除人员的回执删除
			deleteFeedback(kmImeetingMain, removePerson);
		}
		// 获取变更类型："all":变更了时间和地点 、"date":变更了时间、"place":变更了地点、"none":时间和地点均未变更
		String changeType = getChangeType(kmImeetingMain);
		// 变更了地点就将坐席设置清空
		if("all".equals(changeType)||"place".equals(changeType)){
			kmImeetingMain.setFdIsSeatPlan(false);
			kmImeetingMain.setFdSeatPlanId(null);
		}
		// 其他人
		sendChangeNotifyToOtherPerson(kmImeetingMain);

		kmImeetingMain.setIsNotify(true);
		// 删除旧回执
		//kmImeetingMainFeedbackService.deleteFeedbacks(kmImeetingMain);
		if (kmImeetingMain.getFdNeedFeedback()) {
			// 重新生成回执
			kmImeetingMainFeedbackService.saveFeedbacks(kmImeetingMain);
			// 同步机制为“发送会议通知后同步”
			if (ImeetingConstant.MEETING_SYNC_SENDNOTIFY.equals(kmImeetingMain.getSyncDataToCalendarTime())) {
				//#141688 会议日程同步使用新逻辑
				//addSyncDataToCalendar(kmImeetingMain);
				addSyncImeetingToCal(kmImeetingMain,
						getAllAttendPersons(kmImeetingMain));

			}
		}

		// 将变更标志置为非变更状态
		kmImeetingMain.setFdChangeMeetingFlag(false);
		if (!kmImeetingMain.isSynchroIn()) {
			SynchroThread thread = new SynchroThread(kmImeetingMain,
					SynchroConstants.OPERATE_UPDATE);
			thread.start();
		}
		// 云会议
		if (kmImeetingMain.getIsCloud()) {
			kmImeetingOutVedioService.updateImeeting(kmImeetingMain);
		}
		// 对接到阿里云视频会议,不需要回执时，直接同步对接
		if (kmImeetingMain.getFdIsVideo()
				&& KKUtil.isKkVideoMeetingEnable()) {
			// 0 为KK方式
			if ("0".equals(AliMeetingUtil.getServiceType())) {
				KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
						.findByPrimaryKey(kmImeetingMain.getFdId(), KmImeetingMapping.class.getName(), true);
				if (kim == null) {
					kim = kmImeetingMappingService.findByModelId(kmImeetingMain.getFdId(),
							KmImeetingMain.class.getName());
				}
				// 变更时如果没同步过，则新增同步
				if (kim == null) {
					if (!kmImeetingMain.getFdNeedFeedback()) {
						IMeetingKkVideoThread integrateThread = new IMeetingKkVideoThread(kmImeetingMain,
								SynchroConstants.OPERATE_ADD);
						integrateThread.start();
					} else {
						// 生成同步到kk定时任务
						if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
							if (kmImeetingMain.getFdFeedBackDeadline() != null
									&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
								saveSyncToKkQuartz(kmImeetingMain);
							}
						}
					}
				}
			} else {
				
				// 获取阿里云视频回调信息，如果阿里aliMeetingUUID为空，则重新创建阿里云视频会议
				String aliMeetingUUID = AliMeetingUtil.getAliMeetingInfo(kmImeetingMain.getFdId());
				
				if (StringUtil.isNotNull(aliMeetingUUID)) {
					if (!kmImeetingMain.getFdNeedFeedback()) {
						IMeetingAliyunThread integrateThread = new IMeetingAliyunThread(kmImeetingMain,
								SynchroConstants.OPERATE_UPDATE);
						integrateThread.start();
					} else {
						// 生成同步到阿里云定时任务
						if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
							if (kmImeetingMain.getFdFeedBackDeadline() != null
									&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
								saveSyncPersonToAliyunQuartz(kmImeetingMain, true);
							}
						}
					}
				} else {
					if (!kmImeetingMain.getFdNeedFeedback()) {
						IMeetingAliyunThread integrateThread = new IMeetingAliyunThread(kmImeetingMain,
								SynchroConstants.OPERATE_ADD);
						integrateThread.start();
					} else {
						// 生成同步到阿里云定时任务
						if (kmImeetingMain.getFdHoldDate().getTime() > new Date().getTime()) {
							if (kmImeetingMain.getFdFeedBackDeadline() != null
									&& kmImeetingMain.getFdFeedBackDeadline().getTime() > new Date().getTime()) {
								saveSyncMeetingToAliyunQuartz(kmImeetingMain);
							}
						}
					}
				}
			}
		 }
	}

	private void deleteFeedback(KmImeetingMain kmImeetingMain, List<SysOrgElement> removePerson) {
		List<String> personIds = new ArrayList<>();
		for (SysOrgElement sysOrgElement: removePerson) {
			personIds.add(sysOrgElement.getFdId());
		}
		String hql = "delete from KmImeetingMainFeedback kmImeetingMainFeedback where kmImeetingMainFeedback.fdMeeting.fdId=:fdId and " + HQLUtil.buildLogicIN("kmImeetingMainFeedback.docCreator.fdId", personIds);
		Query query = getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setString("fdId",  kmImeetingMain.getFdId());
		query.executeUpdate();
	}

	private List<SysOrgElement> getTargetByAgenda(JSONObject jsonA) throws Exception {
		List<SysOrgElement> targets = new ArrayList();
		if (jsonA.get("reporterId") != null && StringUtil.isNotNull(jsonA.getString("reporterId"))) {
			targets.add(sysOrgCoreService.findByPrimaryKey(jsonA.getString("reporterId")));
		}
		if (jsonA.get("relatedPersonIds") != null && StringUtil.isNotNull(jsonA.getString("relatedPersonIds"))) {
			String[] ids = jsonA.getString("relatedPersonIds").split(";");
			List<SysOrgElement> relatedPersonPerson = sysOrgCoreService
					.expandToPerson(sysOrgCoreService.findByPrimaryKeys(ids));
			targets.addAll(relatedPersonPerson);
		}
		return targets;

	}

	private void sendChangeNotifyToRetainTopic(KmImeetingMain kmImeetingMain, JSONArray jsonArr, List retainAgenda)
			throws Exception {
		for (int i = 0; i < retainAgenda.size(); i++) {
			String agendaId = (String) retainAgenda.get(i);
			for (int j = 0; j < jsonArr.size(); j++) {
				JSONObject jsonA = jsonArr.getJSONObject(j);
				if (agendaId.equals(jsonA.getString("agendaId"))) {
					List<SysOrgElement> targets = getTargetByAgenda(jsonA);
					NotifyContext notifyContext = null;
					NotifyReplace notifyReplace = new NotifyReplace();
					// 获取变更类型："all":变更了时间和地点
					// 、"date":变更了时间、"place":变更了地点、"none":时间和地点均未变更
					String changeType = getChangeType(kmImeetingMain);
					if ("all".equals(changeType)) {
						if (kmImeetingMain.getFdPlace() != null
								|| StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.holdDateAndFdPlace.notify");
							// 获取会议地址多语言信息
							KmImeetingRes place = kmImeetingMain.getFdPlace();
							setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
						} else {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.holdDate.notify");
						}
						notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
								kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
					}

					if ("date".equals(changeType)) {
						notifyContext = sysNotifyMainCoreService
								.getContext("km-imeeting:kmImeetingMain.change.holdDate.notify");
						notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
								kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
					}

					if ("place".equals(changeType)) {
						if (kmImeetingMain.getFdPlace() != null
								|| StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.fdPlace.notify");
							// 获取会议地址多语言信息
							KmImeetingRes place = kmImeetingMain.getFdPlace();
							setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
						} else {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.notify");
							notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
									kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
						}
					}
					if ("none".equals(changeType)) {
						// 是否有会议室
						if (kmImeetingMain.getFdPlace() == null
								&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.notify");
						} else {
							notifyContext = sysNotifyMainCoreService
									.getContext("km-imeeting:kmImeetingMain.change.place.notify");
							// 获取会议地址多语言信息
							KmImeetingRes place = kmImeetingMain.getFdPlace();
							setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
						}

						notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
								kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
					}
					notifyContext.setKey("AttendImeetingKey");
					notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
					notifyContext.setNotifyTarget(targets);// 通知人员
					notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
					if (kmImeetingMain.getFdTemplate() != null || kmImeetingMain.getFdNeedFeedback()) {
						notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
					} else {
						notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
					}
					String nameTxt = kmImeetingMain.getFdName();
					if (jsonA.get("agendaName") != null && StringUtil.isNotNull(jsonA.getString("agendaName"))) {
						nameTxt += "(" + jsonA.getString("agendaName") + ")";
					}
					notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName", nameTxt);
					// #26739 初始化ics信息
					initMailIcsInfo(kmImeetingMain, notifyContext);
					sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext, notifyReplace);

				}
			}
		}

	}

	private void sendChangeNotifyToRemoveTopic(KmImeetingMain kmImeetingMain, JSONArray jsonArr, List removeAgenda)
			throws Exception {
		for (int i = 0; i < removeAgenda.size(); i++) {
			String agendaId = (String) removeAgenda.get(i);
			for (int j = 0; j < jsonArr.size(); j++) {
				JSONObject jsonA = jsonArr.getJSONObject(j);
				if (agendaId.equals(jsonA.getString("agendaId"))) {
					List<SysOrgElement> targets = getTargetByAgenda(jsonA);
					//剔除人员的回执删除
					deleteFeedback(kmImeetingMain, targets);
					NotifyContext notifyContext = null;
					NotifyReplace notifyReplace = new NotifyReplace();
					// 是否有会议室
					if (kmImeetingMain.getFdPlace() == null && StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
						notifyContext = sysNotifyMainCoreService
								.getContext("km-imeeting:kmImeetingMain.change.delete.notify");
					} else {
						notifyContext = sysNotifyMainCoreService
								.getContext("km-imeeting:kmImeetingMain.change.delete.notify.place");
						// 获取会议地址多语言信息
						KmImeetingRes place = kmImeetingMain.getFdPlace();
						setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
					}
					notifyContext.setKey("changeImeetingKey");
					notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
					notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
					notifyContext.setNotifyTarget(targets);// 通知人员
					notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
					notifyContext.setLink("/km/imeeting/km_imeeting_calendar/mycalendar.jsp");
					String nameTxt = kmImeetingMain.getFdName();
					if (jsonA.get("agendaName") != null && StringUtil.isNotNull(jsonA.getString("agendaName"))) {
						nameTxt += "(" + jsonA.getString("agendaName") + ")";
					}
					notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName", nameTxt);
					notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate", kmImeetingMain.getFdHoldDate(),
							DateUtil.TYPE_DATETIME);
					// #26739 初始化ics信息
					initMailIcsInfo(kmImeetingMain, notifyContext);
					sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext, notifyReplace);
				}
			}
		}
	}

	/**
	 *发送待办给变更后新增人员
	 */
	private void sendChangeNotifyToNewPerson(KmImeetingMain kmImeetingMain,
			List<SysOrgElement> targets) throws Exception {
		NotifyContext notifyContext = null;
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// 是否有会议室
		if (kmImeetingMain.getFdPlace() == null
				&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
			notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.attend.notify");
		} else {
			notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.attend.notify.place");
			// 获取会议地址多语言信息
			KmImeetingRes place = kmImeetingMain.getFdPlace();
			setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
		}
		notifyContext.setKey("AttendImeetingKey");
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		notifyContext.setNotifyTarget(targets);// 通知人员
		if (kmImeetingMain.getFdTemplate() != null || kmImeetingMain.getFdNeedFeedback()) {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&_todo=1&type=attend&meetingId="
							+ kmImeetingMain.getFdId());
		} else {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		}
		// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
		// .getFdName());// 会议名称
		// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
		// .convertDateToString(kmImeetingMain.getFdHoldDate(),
		// DateUtil.PATTERN_DATETIME));// 召开时间
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
				kmImeetingMain
						.getFdName());
		notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
				kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
		// #26739 初始化ics信息
		initMailIcsInfo(kmImeetingMain, notifyContext);
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	/**
	 *发送待办给变更后还存在的人
	 */
	private void sendChangeNotifyToStaticPerson(KmImeetingMain kmImeetingMain,
			List<SysOrgElement> targets) throws Exception {
		NotifyContext notifyContext = null;
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// 获取变更类型："all":变更了时间和地点 、"date":变更了时间、"place":变更了地点、"none":时间和地点均未变更
		String changeType = getChangeType(kmImeetingMain);

		if ("all".equals(changeType)) {
			if (kmImeetingMain.getFdPlace() != null || StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.change.holdDateAndFdPlace.notify");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.change.holdDate.notify");
			}
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
		}

		if ("date".equals(changeType)) {
			notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.change.holdDate.notify");
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
		}

		if ("place".equals(changeType)) {
			if (kmImeetingMain.getFdPlace() != null || StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.change.fdPlace.notify");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			} else {
				notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.change.notify");
				notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate", kmImeetingMain.getFdHoldDate(),
						DateUtil.TYPE_DATETIME);
			}
		}
		if ("none".equals(changeType)) {
			// 是否有会议室
			if (kmImeetingMain.getFdPlace() == null
					&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.change.notify");
			} else {
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain.change.place.notify");
				// 获取会议地址多语言信息
				KmImeetingRes place = kmImeetingMain.getFdPlace();
				setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
			}
			notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
					kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
		}

		notifyContext.setKey("AttendImeetingKey");
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setNotifyTarget(targets);// 通知人员
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		if (kmImeetingMain.getFdTemplate() != null || kmImeetingMain.getFdNeedFeedback()) {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&_todo=1&type=attend&meetingId="
							+ kmImeetingMain.getFdId());
		} else {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		}
		// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
		// .getFdName());// 会议名称
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName", kmImeetingMain.getFdName());
		// #26739 初始化ics信息
		initMailIcsInfo(kmImeetingMain, notifyContext);
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	// 获取变更类型："all":变更了时间和地点 、"date":变更了时间、"place":变更了地点、"none":时间和地点均未变更
	private String getChangeType(KmImeetingMain kmImeetingMain) {
		String type = "none";// 默认什么都没变更
		String beforeChangeContent = kmImeetingMain.getBeforeChangeContent();
		if (StringUtil.isNull(beforeChangeContent)) {
			return type;
		}
		JSONObject json = JSONObject.fromObject(beforeChangeContent);

		String beforeHoldDate = json.getString("fdHoldDate");
		String afterHoldDate = DateUtil.convertDateToString(kmImeetingMain
				.getFdHoldDate(), DateUtil.PATTERN_DATETIME);
		// 变更了时间
		if (!beforeHoldDate.equals(afterHoldDate)) {
			type = "date";
		}

		String beforePlace = json.has("fdPlaceId") ? json
				.getString("fdPlaceId") : "";
		String afterPlace = kmImeetingMain.getFdPlace() != null ? kmImeetingMain
				.getFdPlace().getFdId()
				: "";
		// 变更了地点
		if (!beforePlace.equals(afterPlace)) {
			// 变更了时间+地点
			if ("date".equals(type)) {
				type = "all";
			} else {
				type = "place";
			}
		}
		return type;
	}

	/**
	 *发送待办给变更后剔除人员
	 */
	private void sendChangeNotifyToRemovePerson(KmImeetingMain kmImeetingMain,
			List<SysOrgElement> targets) throws Exception {
		NotifyContext notifyContext = null;
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// 是否有会议室
		if (kmImeetingMain.getFdPlace() == null
				&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
			notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.change.delete.notify");
		} else {
			notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.change.delete.notify.place");
			// 获取会议地址多语言信息
			KmImeetingRes place = kmImeetingMain.getFdPlace();
			setPlaceByLang(notifyReplace, place, kmImeetingMain.getFdOtherPlace());
		}
		notifyContext.setKey("changeImeetingKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setNotifyTarget(targets);// 通知人员
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		notifyContext
				.setLink("/km/imeeting/km_imeeting_calendar/mycalendar.jsp");
		// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
		// .getFdName());// 会议名称
		// hashMap.put("km-imeeting:kmImeetingMain.fdDate", DateUtil
		// .convertDateToString(kmImeetingMain.getFdHoldDate(),
		// DateUtil.PATTERN_DATETIME));// 召开时间
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
				kmImeetingMain
						.getFdName());
		notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate",
				kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME);
		// #26739 初始化ics信息
		initMailIcsInfo(kmImeetingMain, notifyContext);
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	/**
	 * 给会议组织人、协助人员、管理员、抄送人员等人发送变更待阅
	 */
	private void sendChangeNotifyToOtherPerson(KmImeetingMain kmImeetingMain)
			throws Exception {
		// 给会议组织人发送待办
		sendMeetingNotifyToEmcee(kmImeetingMain);
		// 给会议协助人、会议室管理员发送通知单，绿色待办
		sendMeetingNotifyToAssist(kmImeetingMain);
		// 给抄送人发送通知单，绿色待办
		sendMeetingNotifyToCC(kmImeetingMain);
	}


	@Override
	public List findFeedBackByAgenda(String fdMeetingId, String fdAgendaId, Boolean isGetId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		if (isGetId) {
			hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdId");
		}
		hqlInfo.setJoinBlock(" left join kmImeetingMainFeedback.fdMeeting fdMeeting ");
		hqlInfo.setWhereBlock("fdMeeting.fdId=:meetingId and kmImeetingMainFeedback.fdAgendaId=:agendaId");
		hqlInfo.setParameter("meetingId", fdMeetingId);
		hqlInfo.setParameter("agendaId", fdAgendaId);
		List feedbacks = kmImeetingMainFeedbackService.findList(hqlInfo);
		return feedbacks;
	}

	/**
	 * 获取议题相关的待办key
	 * 
	 * @throws Exception
	 */
	private List getAgendaTodoKey(KmImeetingMain kmImeetingMain) throws Exception {
		List todoKeyList = new ArrayList();
		List agendaList = kmImeetingMain.getKmImeetingAgendas();
		if (agendaList.size() > 0) {
			for (int i = 0; i < agendaList.size(); i++) {
				KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
				List feedBackList = findFeedBackByAgenda(kmImeetingMain.getFdId(),
						kmImeetingAgenda.getFdId(), true);
				if (feedBackList.size() > 0) {
					for (int j = 0; j < feedBackList.size(); j++) {
						todoKeyList.add("AttendImeetingKey_" + feedBackList.get(j));
					}
				}
			}
		}
		return todoKeyList;
	}

	/**
	 * 变更会议时收回以前的待办、定时器
	 */
	private void delNotifyForChangeMeeting(KmImeetingMain kmImeetingMain)
			throws Exception {
		String[] todos = new String[] { "AssistImeetingKey",
				"EmceeImeetingKey", "SummaryInputPersonImeetingKey",
				"ImeetingUploadAttKey", "AttendImeetingKey", "CCImeetingKey",
				"HastenImeetingKey", "CancelImeetingKey",
				"kmImeetingSummaryKey", "kmImeetingHastenSummaryKey", };// 会议中涉及到的待办
		for (String todo : todos) {
			sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(
					kmImeetingMain, todo, null, null);
		}
	}

	/**
	 * 获取变更前后人员的变化：String[0]新增人员、String[1]不变人员、String[2]剔除人员
	 */
	private Map<String, List<SysOrgElement>> getChangePersons(KmImeetingMain kmImeetingMain) throws Exception {
		Map<String, List<SysOrgElement>> result = new HashMap<String, List<SysOrgElement>>();
		String beforeChangeContent = kmImeetingMain.getBeforeChangeContent();
		if (StringUtil.isNull(beforeChangeContent)) {
			return result;
		}
		JSONObject json = JSONObject.fromObject(beforeChangeContent);
		// 变更前人员
		List<SysOrgElement> beforeIds = new ArrayList<SysOrgElement>();
		if (json.get("fdHostId") != null) {
			beforeIds.add(sysOrgCoreService
					.findByPrimaryKey(json.getString("fdHostId")));
		}
		if (json.get("fdAttendPersonIds") != null
				&& StringUtil.isNotNull(json.getString("fdAttendPersonIds"))) {
			String[] ids = json.getString("fdAttendPersonIds").split(";");
			List<SysOrgElement> attendPerson = sysOrgCoreService
					.expandToPerson(sysOrgCoreService.findByPrimaryKeys(ids));
			beforeIds.addAll(attendPerson);
		}
		if (json.get("fdParticipantPersonIds") != null
				&& StringUtil.isNotNull(json
						.getString("fdParticipantPersonIds"))) {
			String[] ids = json.getString("fdParticipantPersonIds").split(";");
			List<SysOrgElement> participantPerson = sysOrgCoreService
					.expandToPerson(sysOrgCoreService.findByPrimaryKeys(ids));
			beforeIds.addAll(participantPerson);
		}
		if (json.get("fdOtherPersonIds") != null
				&& StringUtil.isNotNull(json.getString("fdOtherPersonIds"))) {
			String[] ids = json.getString("fdOtherPersonIds").split(";");
			List<SysOrgElement> otherPerson = sysOrgCoreService
					.expandToPerson(sysOrgCoreService.findByPrimaryKeys(ids));
			beforeIds.addAll(otherPerson);
		}
		// 变更后人员
		List<SysOrgElement> afterIds = new ArrayList<SysOrgElement>();
		if (kmImeetingMain.getFdHost() != null) {
			afterIds.add(kmImeetingMain.getFdHost());
		}
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
			List<SysOrgElement> attendPerson = sysOrgCoreService
					.expandToPerson(kmImeetingMain.getFdAttendPersons());
			afterIds.addAll(attendPerson);
		}
		if (kmImeetingMain.getFdParticipantPersons() != null
				&& !kmImeetingMain.getFdParticipantPersons().isEmpty()) {
			List<SysOrgElement> participantPerson = sysOrgCoreService
					.expandToPerson(kmImeetingMain.getFdParticipantPersons());
			afterIds.addAll(participantPerson);
		}
		if (kmImeetingMain.getFdOtherPersons() != null
				&& !kmImeetingMain.getFdOtherPersons().isEmpty()) {
			List<SysOrgElement> otherPerson = sysOrgCoreService
					.expandToPerson(kmImeetingMain.getFdOtherPersons());
			afterIds.addAll(otherPerson);
		}

		List<SysOrgElement> addTmp = new ArrayList<SysOrgElement>();
		addTmp.addAll(afterIds);
		addTmp.removeAll(beforeIds);
		result.put("add", addTmp);

		List<SysOrgElement> retainTmp = new ArrayList<SysOrgElement>();
		retainTmp.addAll(beforeIds);
		retainTmp.retainAll(afterIds);
		result.put("retain", retainTmp);

		List<SysOrgElement> removeTmp = new ArrayList<SysOrgElement>();
		removeTmp.addAll(beforeIds);
		removeTmp.removeAll(afterIds);
		result.put("remove", removeTmp);

		return result;
	}

	// ************** 会议变更相关业务(结束) ******************************//

	// ************** 增加历史操作相关业务(开始) ******************************//
	/**
	 * 增加“创建会议安排”历史操作
	 */
	private void addCreateHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		String fdTemplateName = "";
		if (kmImeetingMain.getFdTemplate() != null) {
			fdTemplateName = kmImeetingMain.getFdTemplate().getFdName();
		}
		jsonObject.put("fdTemplate", fdTemplateName);// 会议类型
		jsonObject.put("fdMeetingNum", kmImeetingMain.getFdMeetingNum());// 会议编号
		jsonObject.put("fdName", kmImeetingMain.getFdName());// 会议名称
		jsonObject.put("fdMeetingAim", kmImeetingMain.getFdMeetingAim());// 会议目的
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_ADD);// 类型:创建
		history.setFdOptPerson(kmImeetingMain.getDocCreator());// 操作人
		kmImeetingMainHistoryService.add(history);
	}

	/**
	 * 增加”发送会议通知“历史操作
	 */
	private void addNotifyHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		logger.debug("增加发送会议通知历史记录...");
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		jsonObject.put("fdName", kmImeetingMain.getFdName());
		String date = DateUtil.convertDateToString(kmImeetingMain
				.getFdHoldDate(), DateUtil.PATTERN_DATETIME)
				+ "~"
				+ DateUtil.convertDateToString(
						kmImeetingMain.getFdFinishDate(),
						DateUtil.PATTERN_DATETIME);
		jsonObject.put("date", date);// 会议召开时间
		if (kmImeetingMain.getFdPlace() != null
				|| StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			String place = kmImeetingMain.getFdPlace() == null ? kmImeetingMain
					.getFdOtherPlace() : kmImeetingMain.getFdPlace()
					.getFdName();
			jsonObject.put("fdPlace", place);// 会议地点
		}
		JSONArray agendas = new JSONArray();
		for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
				.getKmImeetingAgendas()) {
			agendas.add(kmImeetingAgenda.getDocSubject());
		}
		jsonObject.put("agendas", agendas);
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_NOTIFY);// 类型:发送通知
		if (kmImeetingMain.getFdHost() != null) {
			history.setFdOptPerson(kmImeetingMain.getFdHost());
		}
		kmImeetingMainHistoryService.add(history);
	}

	/**
	 * 增加”取消会议“历史操作
	 */
	private void addCancelHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		jsonObject.put("cancelReason", kmImeetingMain.getCancelMeetingReason());
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_CANCEL);// 类型:取消会议
		if (UserUtil.getAnonymousUser() == UserUtil.getKMSSUser()) {
			history.setFdOptPerson(null);
		} else {
			history.setFdOptPerson(UserUtil.getUser());
		}
		kmImeetingMainHistoryService.add(history);
	}

	/**
	 * 增加“变更会议”历史操作
	 */
	private void addChangeHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		jsonObject.put("changeReason", kmImeetingMain.getChangeMeetingReason());// 变更原因
		if (kmImeetingMain.getFdHost() != null) {
			jsonObject.put("fdHostId", kmImeetingMain.getFdHost().getFdId());// 主持人ID
			jsonObject
					.put("fdHostName", kmImeetingMain.getFdHost().getFdName());// 主持人Name
		}
		String date = DateUtil.convertDateToString(kmImeetingMain
				.getFdHoldDate(), DateUtil.PATTERN_DATETIME)
				+ "~"
				+ DateUtil.convertDateToString(
						kmImeetingMain.getFdFinishDate(),
						DateUtil.PATTERN_DATETIME);
		jsonObject.put("date", date);// 会议召开时间
		if (kmImeetingMain.getFdPlace() != null
				|| StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			String place = kmImeetingMain.getFdPlace() == null ? kmImeetingMain
					.getFdOtherPlace() : kmImeetingMain.getFdPlace()
					.getFdName();
			jsonObject.put("fdPlace", place);// 会议地点
		}
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_CHANGE);// 类型:变更会议'
		ILbpmProcessService lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
				.getBean("lbpmProcessService");
		if (kmImeetingMain.getFdTemplate() != null) {
			LbpmProcess lbpmProcess = (LbpmProcess) lbpmProcessService.findByPrimaryKey(kmImeetingMain.getFdId());
			history.setFdOptPerson(lbpmProcess.getFdCreator());// 变更人
		} else {
			history.setFdOptPerson(UserUtil.getUser());// 变更人
		}
		kmImeetingMainHistoryService.add(history);
	}

	// 增加“提前结束会议”历史操作
	private void addEarlyEndMeetingHistory(KmImeetingMain kmImeetingMain)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		if (kmImeetingMain.getFdHost() != null) {
			jsonObject.put("fdHostId", kmImeetingMain.getFdHost().getFdId());// 主持人ID
			jsonObject
					.put("fdHostName", kmImeetingMain.getFdHost().getFdName());// 主持人Name
		}
		String date = DateUtil.convertDateToString(kmImeetingMain
				.getFdHoldDate(), DateUtil.PATTERN_DATETIME)
				+ "~"
				+ DateUtil.convertDateToString(
						kmImeetingMain.getFdFinishDate(),
						DateUtil.PATTERN_DATETIME);
		jsonObject.put("date", date);// 会议召开时间
		if (kmImeetingMain.getFdPlace() != null
				|| StringUtil.isNotNull(kmImeetingMain.getFdOtherPlace())) {
			String place = kmImeetingMain.getFdPlace() == null ? kmImeetingMain
					.getFdOtherPlace() : kmImeetingMain.getFdPlace()
							.getFdName();
			jsonObject.put("fdPlace", place);// 会议地点
		}
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingMain);
		history.setFdOptDate(kmImeetingMain.getFdEarlyFinishDate());// 会议结束时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_EARLYEND);// 类型:提前结束
		history.setFdOptPerson(UserUtil.getKMSSUser().getPerson());// 操作人
		kmImeetingMainHistoryService.add(history);
	}

	// ************** 增加历史操作相关业务(结束) ******************************//

	// ************** 上会材料相关业务(开始) ******************************//

	/**
	 * 给材料责任人发送上传材料待办
	 */
	private void sendAttNotifyToResponser(KmImeetingMain kmImeetingMain)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(KmImeetingAgenda.class.getName());
		hqlInfo.setWhereBlock("kmImeetingAgenda.fdMain.fdId=:meetingId");
		hqlInfo.setParameter("meetingId", kmImeetingMain.getFdId());
		List<KmImeetingAgenda> kmImeetingAgendas = findList(hqlInfo);
		if (kmImeetingAgendas.size() > 0) {
			List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
			for (KmImeetingAgenda kmImeetingAgenda : kmImeetingAgendas) {
				// 存在材料负责人，发送待办
				if (kmImeetingAgenda.getDocRespons() != null
						&& !receiver.contains(kmImeetingAgenda.getDocRespons())) {
					receiver.add(kmImeetingAgenda.getDocRespons());
				}
			}
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.agenda.att");
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			notifyContext.setKey("ImeetingUploadAttKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
			notifyContext.setNotifyTarget(receiver);
			notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=viewUpdateAtt&fdId="
							+ kmImeetingMain.getFdId());
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
			// .getFdName());// 会议名称
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
							.getFdName());
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	/**
	 * 生成催办上会材料定时器
	 */
	public void saveHastenUploadAtt(KmImeetingMain kmImeetingMain)
			throws Exception {
		if (kmImeetingMain.getKmImeetingAgendas() != null) {
			for (KmImeetingAgenda kmImeetingAgenda : kmImeetingMain
					.getKmImeetingAgendas()) {
				if (kmImeetingAgenda.getDocRespons() != null
						&& kmImeetingAgenda.getAttachmentSubmitTime() != null) {
					SysQuartzModelContext quartzContext = new SysQuartzModelContext();
					quartzContext.setQuartzJobMethod("sendHastenUploadAttTodo");
					quartzContext.setQuartzJobService("kmImeetingMainService");
					quartzContext.setQuartzKey("hastenUploadAttQuart");
					// JSON字符串:{meetingId:会议ID , meetingName:会议名称
					// ,fdId:议程ID,responId:材料责任人ID
					// }
					JSONObject parameter = new JSONObject();
					parameter.put("meetingId", kmImeetingMain.getFdId());
					parameter.put("meetingName", kmImeetingMain.getFdName());
					parameter.put("fdId", kmImeetingAgenda.getFdId());
					parameter.put("responId", kmImeetingAgenda.getDocRespons()
							.getFdId());
					quartzContext.setQuartzParameter(parameter.toString());
					quartzContext.setQuartzSubject(kmImeetingMain
							.getDocSubject());
					quartzContext.setQuartzCronExpression(getCronExpression(
							kmImeetingMain.getFdHoldDate(), kmImeetingAgenda
									.getAttachmentSubmitTime()));
					sysQuartzCoreService.saveScheduler(quartzContext,
							kmImeetingAgenda);
				}
			}
		}
	}

	/**
	 * cronExpression表达式
	 */
	private String getCronExpression(Date date, int beforeDay) {
		StringBuffer cronExpression = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0 - beforeDay);
		cronExpression.append("0" + " ").append(
				calendar.get(Calendar.MINUTE) + " ").append(
				calendar.get(Calendar.HOUR_OF_DAY) + " ").append(
				calendar.get(Calendar.DAY_OF_MONTH) + " ").append(
				(calendar.get(Calendar.MONTH) + 1) + " ").append(
				"? " + calendar.get(Calendar.YEAR));
		return cronExpression.toString();
	}

	/**
	 * 发送催办上会材料待办
	 */
	@Override
	public void sendHastenUploadAttTodo(SysQuartzJobContext context)
			throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) kmImeetingAgendaService
				.findByPrimaryKey(parameter.getString("fdId"));
		if (kmImeetingAgenda != null) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.agenda.hastenAtt");
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyContext.setKey("ImeetingHastenUploadAttKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyType(SysNotifyConfigUtil
					.getNotifyDefaultValue());// 通知方式
			// 发起人为原始文档创建者
			notifyContext.setDocCreator(kmImeetingAgenda.getFdMain()
					.getDocCreator());
			List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
			targets.add(kmImeetingAgenda.getDocRespons());
			notifyContext.setNotifyTarget(targets);
			notifyContext
					.setLink("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=viewUpdateAtt&fdId="
							+ parameter.getString("meetingId"));
			// hashMap.put("km-imeeting:kmImeetingMain.attachmentSubmitTime",
			// kmImeetingAgenda.getAttachmentSubmitTime().toString());
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", parameter
			// .getString("meetingName"));// 会议名称
			notifyReplace.addReplaceText(
					"km-imeeting:kmImeetingMain.attachmentSubmitTime",
					kmImeetingAgenda.getAttachmentSubmitTime().toString());
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					parameter
							.getString("meetingName"));
			sysNotifyMainCoreService.sendNotify(kmImeetingAgenda, notifyContext,
					notifyReplace);
		}
	}

	// ************** 上会材料相关业务(结束) ******************************//

	// ************** 日程机制接口(开始) ******************************//

	/**
	 * 初始化日程数据
	 * 
	 * 1.所有日程提醒都未过期，给所有人发送日程提醒 2.所有日程提醒都过期，给发起人发送过期日程提醒
	 * 3.部分日程提醒过期，只给未过期的日程提醒创建提醒
	 * 
	 * @throws Exception
	 */
	private SysAgendaMainContextGeneral initSysAgendaMainContextGeneral(
			IBaseModel model) throws Exception {
		List<String> notifyPersonIds = new ArrayList<>();
		KmImeetingMain kmImeetingMain = (KmImeetingMain) model;
		SysAgendaMainContextGeneral sysAgendaMainContextGeneral = new SysAgendaMainContextGeneral();
		sysAgendaMainContextGeneral.setDocSubject(kmImeetingMain.getFdName());// 会议名称
		sysAgendaMainContextGeneral.setEventStartTime(kmImeetingMain
				.getFdHoldDate());// 会议召开时间
		sysAgendaMainContextGeneral.setEventFinishTime(kmImeetingMain
				.getFdFinishDate());// 会议结束日期
		sysAgendaMainContextGeneral.setLunar(false);
		String fdPlace = kmImeetingMain.getFdPlace() != null ? kmImeetingMain
				.getFdPlace().getFdName() : kmImeetingMain.getFdOtherPlace();
		sysAgendaMainContextGeneral.setEventLocation(fdPlace);// 会议地点
		sysAgendaMainContextGeneral
				.setDocUrl("/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId="
						+ kmImeetingMain.getFdId());// 关联URL
		sysAgendaMainContextGeneral
				.setCalType(SysAgendaTypeEnum.fdCalendarType.EVENT.getKey());
		// 所有日程提醒是否过期
		boolean result = false;
		List sysNotifyRemindMainList = getSysNotifyRemindMainService()
				.getSysNotifyRemindMainList(kmImeetingMain.getFdId());
		Date eventStartTime = kmImeetingMain.getFdHoldDate();
		if (sysNotifyRemindMainList != null
				&& !sysNotifyRemindMainList.isEmpty()) {
			for (int i = 0; i < sysNotifyRemindMainList.size(); i++) {
				SysNotifyRemindMain sysNotifyRemindMainItem = (SysNotifyRemindMain) sysNotifyRemindMainList
						.get(i);
				String fdBeforeTime = sysNotifyRemindMainItem
						.getFdBeforeTime();
				String fdTimeUnit = sysNotifyRemindMainItem.getFdTimeUnit();
				boolean flag = checkRemindTimeAvailable(eventStartTime,
						fdBeforeTime, fdTimeUnit);
				if (flag) {
					result = true;
				}
			}
		} else {
			result = true;
		}
		// 日程提醒是否全部过期,全部过期只通知会议发起人
		if (!result) {
			notifyPersonIds.add(kmImeetingMain.getDocCreator().getFdId());
			sysAgendaMainContextGeneral.setSendNotifyPersonIds(notifyPersonIds);
		}
		return sysAgendaMainContextGeneral;
	}

	private boolean checkRemindTimeAvailable(Date eventStartTime,
			String fdBeforeTime, String fdTimeUnit) {
		Long executeTimeStamp = SysNotifyRemindUtil
				.getTimeInMillisByFdTimeUnit(eventStartTime.getTime(), 0,
						Double.parseDouble(fdBeforeTime),
						SysNotifyTypeEnum.fdTimeUnit.getList().get(fdTimeUnit),
						0);
		if (executeTimeStamp <= new Date().getTime()) {
			return false;
		}
		return true;
	}

	/**
	 * 新增同步(针对普通模块)
	 */
	public void addSyncDataToCalendar(IBaseModel model,
			List<SysOrgElement> ownerTarget) throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		SysAgendaMainContextGeneral sysAgendaMainContextGeneral = initSysAgendaMainContextGeneral(mainModel);
		// 如果日程都过期，给发起人也创建日程并创建日程提醒
		List<String> notifyPersonIds = sysAgendaMainContextGeneral
				.getSendNotifyPersonIds();
		if (notifyPersonIds != null && !notifyPersonIds.isEmpty()) {
			ownerTarget.add(mainModel.getDocCreator());
		}
		sysAgendaMainContextGeneral.setOwnerTarget(ownerTarget);
		sysAgendaMainCoreService.addSyncDataToCalendar(
				sysAgendaMainContextGeneral, mainModel);
	}

	/**
	 * 新增同步(新逻辑)
	 * 新逻辑：会议同步日程，只生成一条日程记录，创建人是日程的拥有者和创建人，其余人员放在日程相关人中
	 */
	public void addSyncImeetingToCal(IBaseModel model,
									 List<SysOrgElement> ownerTarget) throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		SysAgendaMainContextGeneral sysAgendaMainContextGeneral = initSysAgendaMainContextGeneral(mainModel);
		// 如果日程都过期，给发起人也创建日程并创建日程提醒
		List<String> notifyPersonIds = sysAgendaMainContextGeneral
				.getSendNotifyPersonIds();
		if (notifyPersonIds != null && !notifyPersonIds.isEmpty()) {
			ownerTarget.add(mainModel.getDocCreator());
		}
		sysAgendaMainContextGeneral.setOwnerTarget(ownerTarget);
		sysAgendaMainCoreService.addSyncImeetingToCalendar(
				sysAgendaMainContextGeneral, mainModel);
	}

	/**
	 * 更新同步(针对普通模块)
	 */
	public void updateDataSyncDataToCalendar(IBaseModel model) throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		SysAgendaMainContextGeneral sysAgendaMainContextGeneral = initSysAgendaMainContextGeneral(mainModel);
		List<SysOrgElement> ownerTarget = new ArrayList<SysOrgElement>();
		if ("sendNotify".equals(mainModel.getSyncDataToCalendarTime())) {
			ownerTarget.addAll(getAllAttendPersons(mainModel));
		} else if ("personAttend".equals(mainModel.getSyncDataToCalendarTime())) {
			ownerTarget.addAll(getHasAttendPersons(mainModel));
		}
		sysAgendaMainContextGeneral.setOwnerTarget(ownerTarget);
		sysAgendaMainCoreService.updateDataSyncDataToCalendar(
				sysAgendaMainContextGeneral, mainModel);
	}

	/**
	 * 删除同步(针对普通模块) 是否全部
	 */
	public void deleteSyncDataToCalendar(IBaseModel model,
			List<SysOrgElement> ownerTargetList) throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		ownerTargetList = sysOrgCoreService.expandToPerson(ownerTargetList);
		sysAgendaMainCoreService.deleteSyncDataToCalendar(ownerTargetList,
				mainModel);
	}

	/**
	 * 删除同步（会议新逻辑）
	 * @param model
	 * @param ownerTargetList
	 */
	private void deleteSyncImeetingToCalendar(IBaseModel model, List<SysOrgElement> ownerTargetList) throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		sysAgendaMainCoreService.deleteSyncImeetingToCal(mainModel);
	}

	/**
	 * 取需要参加会议的所有人，包括主持人，参加人员、列席人员
	 */
	@Override
	public List<SysOrgElement> getAllAttendPersons(KmImeetingMain kmImeetingMain)
			throws Exception {
		List<SysOrgElement> relList = new ArrayList<SysOrgElement>();
		// 参加人
		List<SysOrgElement> attendSysOrgElement = kmImeetingMain
				.getFdAttendPersons();
		if (!attendSysOrgElement.isEmpty()) {
			relList.addAll(attendSysOrgElement);
		}
		// 列席人
		ArrayUtil.concatTwoList(kmImeetingMain.getFdParticipantPersons(),
				relList);
		// 主持人
		if (kmImeetingMain.getFdHost() != null
				&& !relList.contains(kmImeetingMain.getFdHost())) {
			relList.add(kmImeetingMain.getFdHost());
		}
		if ("true".equals(KmImeetingConfigUtil.isTopicMng())
				|| (kmImeetingMain.getFdIsTopic() != null && kmImeetingMain.getFdIsTopic())) {
			// 议题汇报汇报人
			List agendaList = kmImeetingMain.getKmImeetingAgendas();
			for (int j = 0; j < agendaList.size(); j++) {
				KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(j);
				if (kmImeetingAgenda.getDocReporter() != null) {
					relList.add(kmImeetingAgenda.getDocReporter());
				}
			}
		}
		return relList;
	}

	/**
	 * 获取已确认参与的人员
	 */
	private List<SysOrgElement> getHasAttendPersons(
			KmImeetingMain kmImeetingMain) throws Exception {
		List<SysOrgElement> attendPersons = kmImeetingMainFeedbackService
				.getPersonsByOptType(kmImeetingMain.getFdId(),
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);// 已报名参加的人
		return attendPersons;
	}

	/**
	 * @deprecated 获取主持人及记录人
	 */
	private List<SysOrgElement> getOtherAttendPersons(
			KmImeetingMain kmImeetingMain) throws Exception {
		List<SysOrgElement> otherAttendPersons = new ArrayList<SysOrgElement>();
		if (kmImeetingMain.getFdHost() != null) {
			otherAttendPersons.add(kmImeetingMain.getFdHost());// 主持人
		}
		if (kmImeetingMain.getFdSummaryInputPerson() != null
				&& kmImeetingMain.getFdSummaryInputPerson() != kmImeetingMain
						.getFdHost()) {
			otherAttendPersons.add(kmImeetingMain.getFdSummaryInputPerson());// 纪要录入人员
		}
		return otherAttendPersons;
	}

	// ************** 日程机制接口(结束) ******************************//

	// ************** 其他(开始) ******************************//

	/**
	 * 给纪要录入人发送待办
	 */
	private void sendMeetingNotifySummaryInputPerson(
			KmImeetingMain kmImeetingMain) throws Exception {
		if (kmImeetingMain.getFdSummaryInputPerson() != null) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext("km-imeeting:kmImeetingMain.summary.notify");
			// HashMap<String, String> hashMap = new HashMap<String, String>();
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyContext.setKey("SummaryInputPersonImeetingKey");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
			List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
			receiver.add(kmImeetingMain.getFdSummaryInputPerson());
			notifyContext.setNotifyTarget(receiver);
			notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
			// hashMap.put("km-imeeting:kmImeetingMain.fdName", kmImeetingMain
			// .getFdName());// 会议名称
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName",
					kmImeetingMain
							.getFdName());
			sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
					notifyReplace);
		}
	}

	/**
	 * 删除过期会议的待办（系统定时任务）
	 */
	@Override
	public void deleteExpiredTodo(SysQuartzJobContext context) throws Exception {
		String[] todos = new String[] { "AssistImeetingKey",
				"EmceeImeetingKey", "SummaryInputPersonImeetingKey",
				"ImeetingUploadAttKey", "AttendImeetingKey", "CCImeetingKey",
				"HastenImeetingKey", "CancelImeetingKey", "changeImeetingKey",
				"kmImeetingSummaryKey", "kmImeetingHastenSummaryKey" };// 会议中涉及到的待办
		Date now = new Date();
		HQLInfo hqlInfo = new HQLInfo();
		List<String> keys = Arrays.asList(todos);
		String whereBlock = "kmImeetingMain.fdHoldDate<=:fdHoldDate and kmImeetingMain.docStatus=:docStatus and kmImeetingMain.fdCleanTodoFlag is null";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdHoldDate", now);
		hqlInfo.setParameter("docStatus", "30");

		// 分页信息,先取第1页的数据
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(5);
		// 定时任务取数据，不需要权限过滤
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);

		// 这里通过分页方式去取数据，否则会因为取出来的数据过多而导致内存溢出
		Page page = findPage(hqlInfo);

		if (logger.isDebugEnabled()) {
			logger.debug("总找到需要清理的会议数量：" + page.getTotalrows());
		}

		List<KmImeetingMain> list = null;
		// 获取所有分页页码数据，如[0, 1, 2, 3]
		List<Integer> pagingList = new ArrayList<Integer>(page.getPagingList());
		for (int p : pagingList) {
			// 跳过第0页，因为第0页和第1页是一样的
			if (p == 0) {
                continue;
            }
			if (logger.isDebugEnabled()) {
				logger.debug("正在处理第" + p + "页的数据，每页5条记录。");
			}

			// 第1页可以直接使用上面取出来的数据
			if (p == 1) {
				list = page.getList();
			} else
			// 从第2页开始，需要去数据库取数据
			if (p > 1) {
				hqlInfo.setPageNo(p);
				list = findPage(hqlInfo).getList();
			}

			if (list != null && !list.isEmpty()) {
				clearImeeting(keys, list);
			}
		}
		// 更新已清除待办标志
		String hql = "update KmImeetingMain  kmImeetingMain set kmImeetingMain.fdCleanTodoFlag=:fdCleanTodoFlag "
				+ " where kmImeetingMain.fdHoldDate<=:fdHoldDate and kmImeetingMain.docStatus=:docStatus and kmImeetingMain.fdCleanTodoFlag is null";
		Query query = this.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdCleanTodoFlag", true);
		query.setParameter("fdHoldDate", now);
		query.setParameter("docStatus", "30");
		query.executeUpdate();
	}

	private void clearImeeting(List todos, List<KmImeetingMain> list) throws Exception {
		for (KmImeetingMain kmImeetingMain : list) {
			deleteAgendaTodos(kmImeetingMain);
			sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(kmImeetingMain, todos, null, null);
		}
	}

	// ************** 其他(结束) ******************************//

	// ************** 与第三方应用同步业务,如exchange(开始) ******************************//

	// 同步接入定时器
	private void saveSynchroInQuart(KmImeetingMain kmImeetingMain)
			throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("synchroInQuart");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("synchroInQuart");
		JSONObject parameter = new JSONObject();
		parameter.put("fdId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("会议同步接入定时器");
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain
				.getFdHoldDate(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}

	@Override
	public void synchroInQuart(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this
				.findByPrimaryKey(parameter.getString("fdId"));
		if (kmImeetingMain != null) {
			Query query = getBaseDao()
					.getHibernateSession()
					.createQuery(
							"select kmImeetingMainFeedback.docCreator.fdId,kmImeetingMainFeedback.docAlterTime from "
									+ KmImeetingMainFeedback.class.getName()
									+ " kmImeetingMainFeedback where kmImeetingMainFeedback.fdMeeting.fdId=:meetingId");
			query.setCacheable(true);
			query.setCacheMode(CacheMode.NORMAL);
			query.setCacheRegion("km-imeeting");
			query.setParameter("meetingId", kmImeetingMain.getFdId());
			List<Object[]> original = query.list();
			Map<String, Date> map = new HashMap<String, Date>();
			for (Object[] object : original) {
				if (object[1] != null) {
					map.put((String) object[0], (Date) object[1]);
				}
			}
			List<SynchroMeetingResponse> result = kmImeetingOutCacheService
					.getMeetingResponseList(kmImeetingMain);
			List<String> attendIds = new ArrayList<String>();
			List<String> unAttendIds = new ArrayList<String>();
			for (SynchroMeetingResponse response : result) {
				String personId = response.getPersonId();
				if (response.getLastResponseTime() == null) {
                    continue;
                }
				if (map.get(personId) == null
						|| map.get(personId).getTime() < response
								.getLastResponseTime().getTime()) {
					// 同意参加
					if (IMeetingResponseType.ACCEPT.equals(response
							.getResponseType())) {
						attendIds.add(response.getPersonId());
					}
					// 不同意参加
					if (IMeetingResponseType.DECLINE.equals(response
							.getResponseType())) {
						unAttendIds.add(response.getPersonId());
					}
				}
			}
			if (!attendIds.isEmpty()) {
				String inBlock = HQLUtil.buildLogicIN(
						"kmImeetingMainFeedback.docCreator.fdId", attendIds);
				query = getBaseDao()
						.getHibernateSession()
						.createQuery(
								"update "
										+ KmImeetingMainFeedback.class
												.getName()
										+ " kmImeetingMainFeedback set kmImeetingMainFeedback.fdOperateType='01'  where kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and "
										+ inBlock);
				query.setParameter("meetingId", kmImeetingMain.getFdId());
				query.executeUpdate();
			}
			if (!unAttendIds.isEmpty()) {
				String inBlock = HQLUtil.buildLogicIN(
						"kmImeetingMainFeedback.docCreator.fdId", unAttendIds);
				query = getBaseDao()
						.getHibernateSession()
						.createQuery(
								"update "
										+ KmImeetingMainFeedback.class
												.getName()
										+ " kmImeetingMainFeedback set kmImeetingMainFeedback.fdOperateType='02'  where kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and "
										+ inBlock);
				query.setParameter("meetingId", kmImeetingMain.getFdId());
				query.executeUpdate();
			}
		}
	}

	// ************** 与第三方应用同步业务,如exchange(结束) ******************************//

	// 分类转移
	@Override
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception {
		// 添加日志信息
		if (UserOperHelper.allowLogOper("changeTemplate", getModelName())) {
			for (String id : ids.split(";")) {
				KmImeetingMain main = (KmImeetingMain) findByPrimaryKey(id);
				UserOperContentHelper
						.putUpdate(id, main.getDocSubject(), getModelName())
						.putSimple("templateId", null, templateId);
			}
		}
		return ((IKmImeetingMainDao) this.getBaseDao()).updateDocumentTemplate(
				ids, templateId);
	}

	// 会议组织人承接
	@Override
	public void updateEmcc(KmImeetingMainForm kmImeetingMainForm, RequestContext requestContext) throws Exception {
		String fdId = requestContext.getParameter("fdId");
		if (StringUtil.isNull(fdId)) {
			fdId = kmImeetingMainForm.getFdId();
		}
		KmImeetingMain kmImeetingMain = (KmImeetingMain) findByPrimaryKey(
				fdId, KmImeetingMain.class, false);
		// 记录操作日志
		if (UserOperHelper.allowLogOper("updateEmcc", getModelName())) {
			UserOperContentHelper.putUpdate(kmImeetingMain).putSimple(
					"emccType", kmImeetingMain.getEmccType(), "EmccDone");
		}
		kmImeetingMain.setEmccType("EmccDone");
		// 待办置为已办
		sysNotifyMainCoreService.getTodoProvider().removePerson(kmImeetingMain, "EmceeImeetingKey", UserUtil.getUser());
	}

	/**
	 * 检查潜在与会者的忙闲状态,对应需求#9043
	 */
	@Override
	public List<Map<String, Object>> checkFree(RequestContext request)
			throws Exception {
		List<Map<String, Object>> result = new ArrayList<>();
		String recurrenceStr = request.getParameter("recurrenceStr");
		Date fdHoldDate = DateUtil.convertStringToDate(request
				.getParameter("fdHoldDate"), DateUtil.TYPE_DATETIME, request
				.getLocale());
		Date fdFinishDate = DateUtil.convertStringToDate(request
				.getParameter("fdFinishDate"), DateUtil.TYPE_DATETIME, request
				.getLocale());
		if (StringUtil.isNotNull(recurrenceStr)) {
			Map<String, String> recurrenceMap = RecurrenceUtil
					.parseRecurrenceStr(recurrenceStr);
			// 是否为从不结束，为从不结束不做检查，性能不好
			if ("NEVER".equals(recurrenceMap.get("ENDTYPE"))) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", "04");
				result.add(map);
			} else {
				Date lastEndDate = RecurrenceUtil
						.getLastedExecuteDate(recurrenceStr, fdHoldDate);
				List<Date> dates = RecurrenceUtil.getExcuteDateList(
						recurrenceStr,
						fdHoldDate, fdHoldDate, lastEndDate);
				dates.add(lastEndDate);
				if (dates.size() > 100) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("type", "04");
					result.add(map);
				}else{
					boolean notCheck = false;
					for (Date date : dates) {
						Date newStartDate = date;
						Date newEndDate = new Date(
								date.getTime() + fdFinishDate.getTime()
										- fdHoldDate.getTime());
						Map<String, Object> map = checkFree(request, newStartDate,
								newEndDate);
						if (map.get("type")
								.equals(ImeetingConstant.MEETING_CHECKFREE_NOTCHECK)) {
							result.add(map);
							notCheck = true;
							break;
						} else if (map.get("type")
								.equals(ImeetingConstant.MEETING_CHECKFREE_CONFLICT)) {
							result.add(map);
						}
					}
					if (!notCheck && result.size() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type",
								ImeetingConstant.MEETING_CHECKFREE_NOCONFLICT);
						result.add(map);
					}
				}
			}
		} else {
			Map<String, Object> map = checkFree(request, fdHoldDate,
					fdFinishDate);
			result.add(map);
		}
		return result;
	}

	public Map<String, Object> checkFree(RequestContext request,
			Date fdHoldDate, Date fdFinishDate) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmImeetingMain.fdHoldDate<:fdFinishDate and kmImeetingMain.fdFinishDate>:fdHoldDate and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30' )";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null) ");
		String fdId = request.getParameter("fdId");
		if (StringUtil.isNotNull(fdId)) {
			whereBlock += " and kmImeetingMain.fdId<>:fdId";
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdHoldDate", fdHoldDate);
		hqlInfo.setParameter("fdFinishDate", fdFinishDate);
		List<KmImeetingMain> list = findList(hqlInfo);
		List<String> persons = new ArrayList<String>();
		// 处理已参会人员
		handleFeedback(list, persons);
		List<String> attendIds = ArrayUtil.convertArrayToList(request
				.getParameter("attendIds").split(";"));
		attendIds = sysOrgCoreService.expandToPersonIds(attendIds);// 本次会议要参加的人
		// 去重
		ArrayUtil.unique(attendIds);
		// 周期性会议
		HQLInfo hqlInfo2 = new HQLInfo();
		String rangeBlock = hqlInfo2.getWhereBlock();
		rangeBlock = StringUtil.linkString(rangeBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null) ");
		rangeBlock = StringUtil.linkString(rangeBlock, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and kmImeetingMain.fdRecurrenceLastEnd>:fdHoldDate and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		rangeBlock = StringUtil.linkString(rangeBlock, " and ", HQLUtil
				.buildLogicIN("kmImeetingMain.fdAttendPersons.fdId",
						attendIds));
		hqlInfo2.setWhereBlock(rangeBlock);
		hqlInfo2.setParameter("fdHoldDate", fdHoldDate);
		hqlInfo2.setParameter("fdFinishDate", fdFinishDate);
		List<KmImeetingMain> rangeList = findList(hqlInfo2);
		// 取每一条周期性会议解析
		for (KmImeetingMain kmImeetingMain : rangeList) {
			List<KmImeetingMain> _rangeList = new ArrayList<KmImeetingMain>();
			String recurrenceStr = kmImeetingMain.getFdRecurrenceStr();
			Date reStartDate = kmImeetingMain.getFdHoldDate();
			Date reFinishDate = kmImeetingMain.getFdFinishDate();
			Date LastStartDate = kmImeetingMain.getFdRecurrenceLastStart();
			Date LastEndDate = kmImeetingMain.getFdRecurrenceLastEnd();
			// 取所有的周期起始时间
			List<Date> allSatrtList = RecurrenceUtil.getExcuteDateList(
					recurrenceStr, reStartDate, reStartDate, LastStartDate);
			// 取所有的周期结束时间
			List<Date> allEndList = RecurrenceUtil.getExcuteDateList(
					recurrenceStr, reFinishDate, reFinishDate, LastEndDate);
			// 判断当前新会议和周期性会议是否冲突
			// 不冲突条件：周期会议起始时间 <=新会议的结束时间 and 周期会议的结束时间>=新会议起始时间
			for (int i = 0; i < allSatrtList.size(); i++) {
				Date startDate = allSatrtList.get(i);
				Date endDate = allEndList.get(i);
				if (startDate.compareTo(fdFinishDate) == -1
						&& endDate.compareTo(fdHoldDate) == 1) {
					_rangeList.add(kmImeetingMain);
					handleFeedback(_rangeList, persons);
					break;
				}
			}
		}
		if (attendIds.size() > 100) {
			// 检测人数大于100不做检测
			result.put("type", ImeetingConstant.MEETING_CHECKFREE_NOTCHECK);
		} else {
			// 交集，即冲突集合
			attendIds.retainAll(persons);
			if (attendIds.size() > 0) {
				result.put("type", ImeetingConstant.MEETING_CHECKFREE_CONFLICT);
				JSONArray array = new JSONArray();
				for (String id : attendIds) {
					SysOrgElement e = sysOrgCoreService.findByPrimaryKey(id);
					array.add(e.getFdName());
				}
				result.put("array", array);
				result.put("startDate", DateUtil.convertDateToString(fdHoldDate,
						DateUtil.PATTERN_DATETIME));
				result.put("endDate", DateUtil.convertDateToString(fdFinishDate,
						DateUtil.PATTERN_DATETIME));
			} else {
				result.put("type",
						ImeetingConstant.MEETING_CHECKFREE_NOCONFLICT);
			}
		}
		return result;
	}

	/**
	 * 处理确认参加会议的人员
	 * 
	 * @param meetings
	 * @param persons
	 */
	private void handleFeedback(List<KmImeetingMain> meetings, List<String> persons) {
		for (KmImeetingMain kmImeetingMain : meetings) {
			List<KmImeetingMainFeedback> feedbacks = kmImeetingMain.getKmImeetingMainFeedbacks();
			if (!CollectionUtils.isEmpty(feedbacks)) {
				for (KmImeetingMainFeedback feedback : feedbacks) {
					// 仅加入已经确认参加会议的人员
					if ("01".equals(feedback.getFdOperateType())) {
						persons.add(feedback.getDocCreator().getFdId());
					}
				}
			}
		}
	}

	@Override
	public String putToKmImeetingList(HttpServletRequest request) throws Exception {
		String userId=UserUtil.getUser().getFdId();
		String meetingId = request.getParameter("fdId");
		KmImeetingMain kmImeeting = (KmImeetingMain) this.findByPrimaryKey(meetingId,null,true);
		if(kmImeeting!=null){
		List<SysOrgElement> persons =kmImeeting.getFdAttendPersons();
		if(persons.size()>0){
		for (SysOrgElement sysOrgElement : persons) {
			if(userId.equals(sysOrgElement.getFdId())){
				return "exist";
			}
		}
		}
		}
		String key = request.getParameter("key");
		Object obj = cache.get(key+meetingId);
		if(obj!=null){
			List list = (List)obj;
			Boolean flag = false;
			for (int i = 0; i < list.size(); i++) {
				if(userId.equals(list.get(i))){
					flag=true;
				}
			}
			if(!flag){
				list.add(userId);
				cache.put(meetingId,list);
				return "success";
			}
				return "exist";
		}else{
			List list = new ArrayList();
			list.add(userId);
			cache.put(key+meetingId,list);
			return "success";
		}
		
			
	}
	
	@Override
	public List getKmImeetingList(HttpServletRequest request) throws Exception {
		String meetingId = request.getParameter("fdId");
		String key = request.getParameter("key");
		Object obj = cache.get(key+meetingId);
		List list = new ArrayList();
		if(obj!=null){
			list = (List)obj;
		}
		return list;
	}
	
	@Override
	public void setCache(String key, String value) throws Exception {
		cache.put(key,value);
	}
	
	@Override
	public void cleanCacheByKey(String key) throws Exception {
		cache.remove(key);
	}
	
	@Override
	public Object getCache(String key) throws Exception {
		Object obj = cache.get(key);
		return obj;
	}

	@Override
	public HQLInfo buildImeetingHql(RequestContext requestContext)
			throws Exception {
		HQLInfo hql = buildHql(requestContext);
		String format = ResourceUtil.getString("date.format.datetime");
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ", " (kmImeetingMain.fdRecurrenceStr is null) ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdFinishDate>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) ");
		whereBlock = StringUtil.linkString(whereBlock," and "," kmImeetingMain.fdPlace is not null");
		//#171614 变更中的会议室也校验冲突
		whereBlock = StringUtil.linkString(whereBlock," and "," (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30' or (kmImeetingMain.docStatus='10' and kmImeetingMain.fdChangeMeetingFlag = :fdChangeMeetingFlag))");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		Date startDateTime = DateUtil.convertStringToDate(fdStart, format);
		Date endDateTime = DateUtil.convertStringToDate(fdEnd, format);

		hql.setParameter("fdHoldDate", startDateTime);
		hql.setParameter("fdFinishDate", endDateTime);
		// 会议变更中的资源也要校验
		hql.setParameter("fdChangeMeetingFlag", true);
		hql.setWhereBlock(whereBlock);
		return hql;
	}

	private HQLInfo buildHql(RequestContext requestContext) {
		HQLInfo hql = new HQLInfo();
		String whereBlock = "";
		String resId = requestContext.getParameter("resId");
		String exceptMeetingId = requestContext.getParameter("exceptMeetingId");
		if (StringUtil.isNotNull(resId)) {
			hql.setJoinBlock("left join kmImeetingMain.fdVicePlaces fdVicePlaces left join kmImeetingMain.fdPlace fdPlace");
			whereBlock = StringUtil.linkString(whereBlock, " and ", "(fdPlace.fdId =:resId or fdVicePlaces.fdId =:resId )");
			hql.setParameter("resId", resId);
		}
		if (StringUtil.isNotNull(exceptMeetingId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMain.fdId<>:exceptMeetingId ");
			hql.setParameter("exceptMeetingId", exceptMeetingId);
		}
		hql.setWhereBlock(whereBlock);
		return hql;
	}

	@Override
	public HQLInfo buildImeetingRangeHql(RequestContext requestContext)
			throws Exception {
		HQLInfo hql = buildHql(requestContext);
		String format = ResourceUtil.getString("date.format.datetime");
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null) ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" kmImeetingMain.fdHoldDate<:fdFinishDate and ((kmImeetingMain.fdEarlyFinishDate is null and kmImeetingMain.fdRecurrenceLastEnd>:fdHoldDate) or (kmImeetingMain.fdEarlyFinishDate is not null and kmImeetingMain.fdEarlyFinishDate>:fdHoldDate)) and kmImeetingMain.fdPlace is not null and (kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30')");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		Date startDateTime = DateUtil.convertStringToDate(fdStart, format);
		Date endDateTime = DateUtil.convertStringToDate(fdEnd, format);

		hql.setParameter("fdHoldDate", startDateTime);
		hql.setParameter("fdFinishDate", endDateTime);
		hql.setWhereBlock(whereBlock);
		return hql;
	}

	public List<KmImeetingMain>
			findMyImeetingMain(RequestContext requestContext)
					throws Exception {
		String format = ResourceUtil.getString("date.format.date");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType");// 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart, format);
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd, format);
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthOrgIds();
		HQLInfo hql = buildMyMainHql(requestContext);
		String whereBlock = hql.getWhereBlock();
		//#107160 过滤重复值
		hql.setDistinctType(1);
		hql.setJoinBlock(StringUtil.linkString(hql.getJoinBlock(), " ", " left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdEmcee fdEmcee left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson"));
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<:fdEnd and (kmImeetingMain.fdFinishDate>:fdStart or kmImeetingMain.fdRecurrenceLastEnd>=:fdStart) and kmImeetingMain.docStatus = '30' and (fdHost.fdId=:userid or fdEmcee.fdId=:fdEmceeId or fdSummaryInputPerson.fdId=:userid or "
						+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", findMyfeedback())
						+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
						+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
		hql.setParameter("fdNeedFeedback", false);
		hql.setParameter("userid", UserUtil.getUser().getFdId());
		hql.setParameter("fdEmceeId", UserUtil.getUser().getFdId());
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		if (hql.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		List<KmImeetingMain> result = new ArrayList<KmImeetingMain>();
		List<KmImeetingMain> matchMainModels = findList(hql);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			if (StringUtil.isNotNull(recurrenceStr)) {
				Date mainStartDateTime = main.getFdHoldDate();
				Date mainEndDateTime = main.getFdFinishDate();
				List<Date> dates = RecurrenceUtil.getExcuteDateList(
						recurrenceStr,
						mainStartDateTime, startDateTime,
						endDateTime.getTime() > mainEndDateTime.getTime()
								? endDateTime : mainEndDateTime);
				for (int i = 0; i < dates.size(); i++) {
					Date date = dates.get(i);
					if (i == 0
							&& startDateTime.getTime() < mainStartDateTime
									.getTime()
							&& date.getTime() != mainStartDateTime.getTime()) {
						if (mainEndDateTime.getTime() > main
								.getFdRecurrenceLastEnd().getTime()) {
							break;
						}
						KmImeetingMain newmain = (KmImeetingMain) BeanUtils
								.cloneBean(main);
						newmain.setFdHoldDate(mainStartDateTime);
						newmain.setFdFinishDate(mainEndDateTime);
						result.add(newmain);
					}

					Date newStartDate = date;
					Date newEndDate = new Date(
							date.getTime() + mainEndDateTime.getTime()
									- mainStartDateTime.getTime());
					if (newEndDate.getTime() > main.getFdRecurrenceLastEnd()
							.getTime()) {
						break;
					}
					KmImeetingMain newmain = (KmImeetingMain) BeanUtils
							.cloneBean(main);
					newmain.setFdHoldDate(newStartDate);
					newmain.setFdFinishDate(newEndDate);
					result.add(newmain);
				}
			} else {
				result.add(main);
			}
		}
		return result;
	}

	@Override
	public List<KmImeetingMain> findMyNormalMain(RequestContext requestContext)
			throws Exception {
		HQLInfo hql = buildNormalMainHql(requestContext);
		List<KmImeetingMain> mainList = findList(hql);
		return mainList;
	}

	private HQLInfo buildNormalMainHql(RequestContext requestContext)
			throws Exception {
		String format = ResourceUtil.getString("date.format.date");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType");// 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart, format);
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd, format);
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		HQLInfo hql = buildMyMainHql(requestContext);
		String whereBlock = hql.getWhereBlock();
		hql.setJoinBlock(StringUtil.linkString(hql.getJoinBlock(), " ", " left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdEmcee fdEmcee left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson"));
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null) ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdFinishDate>:fdStart and kmImeetingMain.docStatus!='41' and (fdHost.fdId=:userid or fdEmcee.fdId=:fdEmceeId or fdSummaryInputPerson.fdId=:userid or "
						+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", findMyfeedback()) + " or ("
						+ HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
						+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
		hql.setParameter("fdNeedFeedback", false);
		hql.setParameter("userid", UserUtil.getUser().getFdId());
		hql.setParameter("fdEmceeId", UserUtil.getUser().getFdId());
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		return hql;
	}

	private List findMyfeedback() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		hqlInfo.setJoinBlock(" left join kmImeetingMainFeedback.docCreator docCreator");
		hqlInfo.setWhereBlock(" docCreator.fdId=:userId");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		// 增加场所过滤
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AllCheck.DEFAULT);
		List fs = kmImeetingMainFeedbackService.findList(hqlInfo);
		return fs;
	}

	private List findMyfeedback(String fdOperateType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMainFeedback.fdMeeting.fdId");
		String where = " kmImeetingMainFeedback.docCreator.fdId=:userId ";
		if("myHaveAttend".equals(fdOperateType)){
			where += " and kmImeetingMainFeedback.fdOperateType='"+ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND+"' ";
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List fs = kmImeetingMainFeedbackService.findList(hqlInfo);
		return fs;
	}

	private List findMyAgenda() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setJoinBlock(" left join kmImeetingAgenda.fdMain fdMain left join kmImeetingAgenda.docReporter docReporter");
		hqlInfo.setSelectBlock("fdMain.fdId");
		hqlInfo.setWhereBlock("docReporter.fdId=:userId");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		List fs = kmImeetingAgendaService.findList(hqlInfo);
		return fs;
	}

	private HQLInfo buildMyMainHql(RequestContext request) {
		HQLInfo hqlInfo = new HQLInfo();
		String placeId = request.getParameter("placeId");
		String isCloud = request.getParameter("isCloud");// 是否云会议
		// hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		String whereBlock = null;
		if (StringUtil.isNotNull(isCloud)) {
			whereBlock = "kmImeetingMain.isCloud = :isCloud";
			hqlInfo.setParameter("isCloud", Boolean.parseBoolean(isCloud));
		}
		if (StringUtil.isNotNull(placeId)) {
			hqlInfo.setJoinBlock(" left join kmImeetingMain.fdPlace fdPlace");
			whereBlock = StringUtil.linkString(whereBlock, " and ", "fdPlace.fdId = :placeId");
			hqlInfo.setParameter("placeId", placeId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(" kmImeetingMain.fdHoldDate ");
		return hqlInfo;
	}

	@Override
	public List<KmImeetingMain> findMyRangeMain(RequestContext requestContext)
			throws Exception {
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType"); // 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		HQLInfo hql = buildRangeMainHql(requestContext);
		List<KmImeetingMain> result = new ArrayList<KmImeetingMain>();
		List<KmImeetingMain> matchMainModels = findList(hql);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					mainStartDateTime, startDateTime,
					endDateTime.getTime() > mainEndDateTime.getTime()
							? endDateTime : mainEndDateTime);
			for (int i = 0; i < dates.size(); i++) {
				Date date = dates.get(i);
				if (i == 0
						&& startDateTime.getTime() < mainStartDateTime
								.getTime()
						&& date.getTime() != mainStartDateTime.getTime()) {
					if (mainEndDateTime.getTime() > main
							.getFdRecurrenceLastEnd().getTime()) {
						break;
					}
					KmImeetingMain newmain = (KmImeetingMain) BeanUtils
							.cloneBean(main);
					newmain.setFdHoldDate(mainStartDateTime);
					newmain.setFdFinishDate(mainEndDateTime);
					result.add(newmain);
				}

				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + mainEndDateTime.getTime()
								- mainStartDateTime.getTime());
				if (newEndDate.getTime() > main.getFdRecurrenceLastEnd()
						.getTime()) {
					break;
				}
				KmImeetingMain newmain = (KmImeetingMain) BeanUtils
						.cloneBean(main);
				newmain.setFdHoldDate(newStartDate);
				newmain.setFdFinishDate(newEndDate);
				result.add(newmain);
			}
		}
		return result;
	}

	private HQLInfo buildRangeMainHql(RequestContext requestContext)
			throws Exception {
		String format = ResourceUtil.getString("date.format.date");
		if (StringUtil.isNotNull(requestContext.getParameter("format"))) {
			format = requestContext.getParameter("format");
		}
		String fdStart = requestContext.getParameter("fdStart");// 开始时间
		String fdEnd = requestContext.getParameter("fdEnd");// 结束时间
		String viewType = requestContext.getParameter("viewType"); // 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (requestContext.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart, format);
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (requestContext.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd, format);
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		HQLInfo hql = buildMyMainHql(requestContext);
		String whereBlock = hql.getWhereBlock();
		hql.setJoinBlock(StringUtil.linkString(hql.getJoinBlock(), " ", " left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdEmcee fdEmcee left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson"));
		whereBlock = StringUtil.linkString(whereBlock, " and ", " (kmImeetingMain.fdRecurrenceStr is not null) ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdRecurrenceLastEnd>=:fdStart and kmImeetingMain.docStatus!='41' and (fdHost.fdId=:userid or fdEmcee.fdId=:fdEmceeId  or fdSummaryInputPerson.fdId=:userid or "
						+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", findMyfeedback()) + " )");
		hql.setParameter("userid", UserUtil.getUser().getFdId());
		hql.setParameter("fdEmceeId", UserUtil.getUser().getFdId());
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		return hql;
	}

	private List<KmImeetingMain> findImeetingMain(RequestContext request,
			Boolean isMyCalendar) throws Exception {
		HQLInfo hql = new HQLInfo();
		String fdStart = request.getParameter("fdStart");// 开始时间
		String fdEnd = request.getParameter("fdEnd");// 结束时间
		String viewType = request.getParameter("viewType"); // 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (request.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (request.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		if (isMyCalendar) {// 我的会议取发布的会议,点击已参加的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and (kmImeetingMain.fdFinishDate>:fdStart or kmImeetingMain.fdRecurrenceLastEnd>:fdStart) and kmImeetingMain.docStatus='30' ");
		} else {// 资源会议取待审、发布的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and (kmImeetingMain.fdFinishDate>:fdStart or kmImeetingMain.fdRecurrenceLastEnd>:fdStart) and ( kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30') ");
		}
		String whereBlock = hql.getWhereBlock();
		if (isMyCalendar) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		} else {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setOrderBy(" kmImeetingMain.fdHoldDate ");
		hql.setWhereBlock(whereBlock);
		List<KmImeetingMain> result = new ArrayList<KmImeetingMain>();
		List<KmImeetingMain> mainList = findList(hql);
		for (KmImeetingMain main : mainList) {
			String recurrenceStr = main.getFdRecurrenceStr();
			if (StringUtil.isNotNull(recurrenceStr)) {
				Date mainStartDateTime = main.getFdHoldDate();
				Date mainEndDateTime = main.getFdFinishDate();
				List<Date> dates = RecurrenceUtil.getExcuteDateList(
						recurrenceStr,
						mainStartDateTime, startDateTime,
						endDateTime.getTime() > mainEndDateTime.getTime()
								? endDateTime : mainEndDateTime);
				for (int i = 0; i < dates.size(); i++) {
					Date date = dates.get(i);
					if (i == 0
							&& startDateTime.getTime() < mainStartDateTime
									.getTime()
							&& date.getTime() != mainStartDateTime.getTime()) {
						if (mainEndDateTime.getTime() > main
								.getFdRecurrenceLastEnd().getTime()) {
							break;
						}
						KmImeetingMain newmain = (KmImeetingMain) BeanUtils
								.cloneBean(main);
						newmain.setFdHoldDate(mainStartDateTime);
						newmain.setFdFinishDate(mainEndDateTime);
						result.add(newmain);
					}

					Date newStartDate = date;
					Date newEndDate = new Date(
							date.getTime() + mainEndDateTime.getTime()
									- mainStartDateTime.getTime());
					if (newEndDate.getTime() > main.getFdRecurrenceLastEnd()
							.getTime()) {
						break;
					}
					KmImeetingMain newmain = (KmImeetingMain) BeanUtils
							.cloneBean(main);
					newmain.setFdHoldDate(newStartDate);
					newmain.setFdFinishDate(newEndDate);
					result.add(newmain);
				}
			} else {
				result.add(main);
			}
		}
		return result;
	}

	@Override
	public List<KmImeetingMain> findNormalMain(RequestContext request,
			Boolean isMyCalendar) throws Exception {
		HQLInfo hql = new HQLInfo();
		String fdStart = request.getParameter("fdStart");// 开始时间
		String fdEnd = request.getParameter("fdEnd");// 结束时间
		String viewType = request.getParameter("viewType"); // 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (request.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (request.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		if (isMyCalendar) {// 我的会议取发布的会议,点击已参加的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdFinishDate>:fdStart and kmImeetingMain.docStatus='30' ");
		} else {// 资源会议取待审、发布的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdFinishDate>:fdStart and ( kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30') ");
		}
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is null) ");
		if (isMyCalendar) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		} else {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setOrderBy(" kmImeetingMain.fdHoldDate ");
		hql.setWhereBlock(whereBlock);
		List<KmImeetingMain> mainList = findList(hql);
		return mainList;
	}

	@Override
	public List<KmImeetingMain> findRangeMain(RequestContext request,
			Boolean isMyCalendar) throws Exception {
		HQLInfo hql = new HQLInfo();
		String fdStart = request.getParameter("fdStart");// 开始时间
		String fdEnd = request.getParameter("fdEnd");// 结束时间
		String viewType = request.getParameter("viewType"); // 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (request.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (request.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		if (isMyCalendar) {// 我的会议取发布的会议,点击已参加的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdRecurrenceLastEnd>:fdStart and kmImeetingMain.docStatus='30' ");
		} else {// 资源会议取待审、发布的会议
			hql.setWhereBlock(
					" kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdRecurrenceLastEnd>:fdStart and ( kmImeetingMain.docStatus='20' or kmImeetingMain.docStatus='30') ");
		}
		String whereBlock = hql.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				" (kmImeetingMain.fdRecurrenceStr is not null) ");
		if (isMyCalendar) {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		} else {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
		}
		hql.setOrderBy(" kmImeetingMain.fdHoldDate ");
		hql.setParameter("fdStart", startDateTime);
		hql.setParameter("fdEnd", endDateTime);
		hql.setWhereBlock(whereBlock);
		List<KmImeetingMain> result = new ArrayList<KmImeetingMain>();
		List<KmImeetingMain> matchMainModels = findList(hql);
		for (KmImeetingMain main : matchMainModels) {
			String recurrenceStr = main.getFdRecurrenceStr();
			Date mainStartDateTime = main.getFdHoldDate();
			Date mainEndDateTime = main.getFdFinishDate();
			List<Date> dates = RecurrenceUtil.getExcuteDateList(recurrenceStr,
					mainStartDateTime, startDateTime,
					endDateTime.getTime() > mainEndDateTime.getTime()
							? endDateTime : mainEndDateTime);
			for (int i = 0; i < dates.size(); i++) {
				Date date = dates.get(i);
				if (i == 0
						&& startDateTime.getTime() < mainStartDateTime
								.getTime()
						&& date.getTime() != mainStartDateTime.getTime()) {
					if (mainEndDateTime.getTime() > main
							.getFdRecurrenceLastEnd().getTime()) {
						break;
					}
					KmImeetingMain newmain = (KmImeetingMain) BeanUtils
							.cloneBean(main);
					newmain.setFdHoldDate(mainStartDateTime);
					newmain.setFdFinishDate(mainEndDateTime);
					result.add(newmain);
				}

				Date newStartDate = date;
				Date newEndDate = new Date(
						date.getTime() + mainEndDateTime.getTime()
								- mainStartDateTime.getTime());
				if (newEndDate.getTime() > main.getFdRecurrenceLastEnd()
						.getTime()) {
					break;
				}
				KmImeetingMain newmain = (KmImeetingMain) BeanUtils
						.cloneBean(main);
				newmain.setFdHoldDate(newStartDate);
				newmain.setFdFinishDate(newEndDate);
				result.add(newmain);
			}
		}
		return result;
	}

	@Override
	public Map<String, ?> listPortlet(RequestContext request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<>();
		Page page = Page.getEmptyPage();// 简单列表使用
		String rowsize = request.getParameter("rowsize");
		String type = request.getParameter("type");
		String datetype = request.getParameter("datetype");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("1=1");
		// 去重复
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate desc");
		// 我的会议
		if (StringUtil.isNotNull(type)) {
			String[] types = type.split(";");
			buildHqlByType(request, hqlInfo, types);
			UserOperHelper.setEventType(ResourceUtil.getString(
					"kmImeeting.tree.meetings.my", "km-imeeting"));
		}
		// 会议类型
		if (StringUtil.isNotNull(datetype)) {
			buildHqlByDateType(request, hqlInfo, datetype);
		}

		// 信息门户中只查发布状态的
		hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
				+ " and kmImeetingMain.docStatus=:status");
		hqlInfo.setParameter("status", SysDocConstant.DOC_STATUS_PUBLISH);
		if (!StringUtil.isNull(rowsize)) {
            hqlInfo.setRowSize(Integer.parseInt(rowsize));
        }
		hqlInfo.setGetCount(false);
		// 时间范围参数
		String scope = request.getParameter("scope");
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			String block = hqlInfo.getWhereBlock();
			hqlInfo.setWhereBlock(StringUtil.linkString(block, " and ",
					"kmImeetingMain.docPublishTime > :fdEndTime"));
			hqlInfo.setParameter("fdEndTime", PortletTimeUtil
					.getDateByScope(scope));
		}

		page = findPage(hqlInfo);
		JSONArray datas = genArray((List<KmImeetingMain>) page.getList(),
				request);
		rtnMap.put("datas", datas);
		rtnMap.put("page", page);
		UserOperHelper.logFindAll(page.getList(), getModelName());
		return rtnMap;
	}

	private JSONArray genArray(List<KmImeetingMain> list,
			RequestContext request) throws Exception {
		JSONArray datas = new JSONArray();
		for (KmImeetingMain kmImeetingMain : list) {
			JSONObject data = new JSONObject();
			data.put("text", kmImeetingMain.getFdName());
			Date created = kmImeetingMain.getDocPublishTime();
			if (created == null) {
				created = kmImeetingMain.getDocCreateTime();
			}

			String cateNameKey = "catename";
			String cateHrefKey = "catehref";
			boolean isNew = "true".equals(request.getParameter("isNew"));
			if (request.isCloud()) {
				Date now = new Date();
				if(kmImeetingMain.getDocStatus().equals(SysDocConstant.DOC_STATUS_DISCARD) ||
						"41".equals(kmImeetingMain.getDocStatus())){
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.cancel", "km-imeeting"), null, "weaken", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.cancel", "km-imeeting"));
						data.put("statusColor", "weaken");
					}
				} else if (kmImeetingMain.getDocStatus()
						.equals(SysDocConstant.DOC_STATUS_DRAFT) ||
						kmImeetingMain.getDocStatus().equals(SysDocConstant.DOC_STATUS_REFUSE) ||
						kmImeetingMain.getDocStatus().equals(SysDocConstant.DOC_STATUS_EXAMINE) ){
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.examing", "km-imeeting"), null, "weaken", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.examing", "km-imeeting"));
						data.put("statusColor", "warning");
					}
				} else if (kmImeetingMain.getDocStatus()
						.equals(SysDocConstant.DOC_STATUS_PUBLISH)) {
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.unHold", "km-imeeting"), null, "success", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.unHold", "km-imeeting"));
						data.put("statusColor", "success");
					}
				}
				if (kmImeetingMain.getFdFinishDate().before(now)) {
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.hold", "km-imeeting"), null, "info", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.hold", "km-imeeting"));
						data.put("statusColor", "info");
					}
				} else if (kmImeetingMain.getFdHoldDate().before(now)) {
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.holding", "km-imeeting"), null, "primary", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.holding", "km-imeeting"));
						data.put("statusColor", "primary");
					}
				}
				data.put("creator", ListDataUtil
						.buildCreator(kmImeetingMain.getDocCreator()));
				if (isNew) {
					data.put("created", ListDataUtil.buildIinfo(created.getTime()));
				} else {
					data.put("created", created.getTime());
				}
				// List<IconDataVO> icons = new ArrayList<>(1);
				// IconDataVO icon = new IconDataVO();
				// icon.setName("tree-navigation");
				// icons.add(icon);
				// data.put("icons", icons);
				// 与会人员
				List<SysOrgElement> fdAttendPersons = kmImeetingMain
						.getFdAttendPersons();
				JSONArray attendPersons = new JSONArray();
				for (SysOrgElement attendPerson : fdAttendPersons) {
					attendPersons.add(ListDataUtil.buildCreator(attendPerson));
				}
				data.put("person", attendPersons);
				data.put("place", kmImeetingMain.getFdPlace() != null
						? kmImeetingMain.getFdPlace().getFdName()
						: kmImeetingMain.getFdOtherPlace());
				cateNameKey = "cateName";
				cateHrefKey = "cateHref";
				data.put("startDate", kmImeetingMain.getFdHoldDate().getTime());
				data.put("endDate", kmImeetingMain.getFdFinishDate().getTime());
				// 会议地点
				KmImeetingRes fdPlace = kmImeetingMain.getFdPlace();
				if (fdPlace != null) {
                    data.put("place", fdPlace.getFdName());
                }
			} else {
				data.put("created", DateUtil.convertDateToString(created,
						DateUtil.TYPE_DATE, ResourceUtil.getLocaleByUser()));
				data.put("creator", kmImeetingMain.getDocCreator().getFdName());
				data.put("person", ArrayUtil.joinProperty(
						kmImeetingMain.getFdAttendPersons(), "fdName", ";"));
				// 会议时间
				String fdHoldDate = DateUtil.convertDateToString(
						kmImeetingMain.getFdHoldDate(), DateUtil.TYPE_DATETIME,
						ResourceUtil.getLocaleByUser());
				String fdFinishDate = DateUtil.convertDateToString(
						kmImeetingMain.getFdFinishDate(),
						DateUtil.TYPE_DATETIME,
						ResourceUtil.getLocaleByUser());
				String holdDate = StringUtil.linkString(fdHoldDate, " ~ ",
						fdFinishDate);
				data.put("holdDate", holdDate);
				// 会议地点
				KmImeetingRes fdPlace = kmImeetingMain.getFdPlace();
				if (fdPlace != null) {
                    data.put("placeName", fdPlace.getFdName());
                }
			}
			if (kmImeetingMain.getFdTemplate() != null) {
				if (isNew) {
					data.put(cateNameKey,ListDataUtil.buildIinfo(null,
							kmImeetingMain.getFdTemplate().getFdName(), "/km/imeeting/index.jsp?#j_path=/kmImeeting_fixed&cri.q=fdTemplate:"
									+ kmImeetingMain.getFdTemplate().getFdId(), null, null));
				} else {
					data.put(cateNameKey,
							kmImeetingMain.getFdTemplate().getFdName());
					data.put(cateHrefKey,
							"/km/imeeting/index.jsp?#j_path=/kmImeeting_fixed&cri.q=fdTemplate:"
									+ kmImeetingMain.getFdTemplate().getFdId());
				}
			} else {
				if (kmImeetingMain.getIsCloud()) {
					if (isNew) {
						data.put(cateNameKey, ListDataUtil.buildIinfo(ResourceUtil.getString(
								"kmImeetingMain.isCloud", "km-imeeting")));
					} else {
						data.put(cateNameKey, ResourceUtil.getString(
								"kmImeetingMain.isCloud", "km-imeeting"));
					}
				} else {
					if (isNew) {
						data.put(cateNameKey, ListDataUtil.buildIinfo(ResourceUtil.getString(
								"kmImeetingMain.simpleMeeting", "km-imeeting")));
					} else {
						data.put(cateNameKey, ResourceUtil.getString(
								"kmImeetingMain.simpleMeeting", "km-imeeting"));
					}
				}

			}

			StringBuffer sb = new StringBuffer();
			sb.append(
					"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view");
			sb.append("&fdId=" + kmImeetingMain.getFdId());
			data.put("href", sb.toString());
			datas.add(data);
		}
		return datas;
	}

	protected void buildHqlByType(RequestContext request, HQLInfo hqlInfo,
			String[] types) {
		String typeWhereBlock = "";
		String typeJoinBlock = "";
		for (String type : types) {
			// 我参加的
			if ("myAttend".equals(type)) {
				if(!typeJoinBlock.contains("kmImeetingMain.fdAttendPersons")) {
					typeJoinBlock = StringUtil.linkString(typeJoinBlock, " ", "left join kmImeetingMain.fdAttendPersons fdAttendPersons");
				}
				typeWhereBlock = StringUtil.linkString(typeWhereBlock, " or ", HQLUtil.buildLogicIN(
						"fdAttendPersons.fdId", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds()));
				// 代理人、邀请参加人
				if(!typeJoinBlock.contains("kmImeetingMain.fdOtherPersons")) {
					typeJoinBlock = StringUtil.linkString(typeJoinBlock, " ", " left join kmImeetingMain.fdOtherPersons fdOtherPersons");
				}
				typeWhereBlock = StringUtil.linkString(typeWhereBlock, " or ", "fdOtherPersons.fdId = :fdOtherPerson");
				hqlInfo.setParameter("fdOtherPerson", UserUtil.getUser().getFdId());
			}
			// 我主持的
			if ("myHost".equals(type)) {
				if(!typeJoinBlock.contains("kmImeetingMain.fdHost")) {
					typeJoinBlock = StringUtil.linkString(typeJoinBlock, " ", " left join kmImeetingMain.fdHost fdHost");
				}
				typeWhereBlock = StringUtil.linkString(typeWhereBlock, " or ", " fdHost.fdId=:fdHost ");
				hqlInfo.setParameter("fdHost", UserUtil.getUser().getFdId());
			}
			// 我组织的
			if ("myEmcee".equals(type)) {
				if(!typeJoinBlock.contains("kmImeetingMain.fdEmcee")) {
					typeJoinBlock = StringUtil.linkString(typeJoinBlock, " ", " left join kmImeetingMain.fdEmcee fdEmcee");
				}
				typeWhereBlock = StringUtil.linkString(typeWhereBlock, " or ", " fdEmcee.fdId=:fdEmcee ");
				hqlInfo.setParameter("fdEmcee", UserUtil.getUser().getFdId());
			}
			// 我纪要的
			if ("mySummary".equals(type)) {
				if(!typeJoinBlock.contains("kmImeetingMain.fdSummaryInputPerson")) {
					typeJoinBlock = StringUtil.linkString(typeJoinBlock, " ", " left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
				}
				typeWhereBlock = StringUtil.linkString(typeWhereBlock, " or ", " fdSummaryInputPerson.fdId=:summaryPerson ");
				hqlInfo.setParameter("summaryPerson", UserUtil.getUser().getFdId());
			}
		}
		if (StringUtil.isNotNull(typeJoinBlock)) {
			hqlInfo.setJoinBlock(typeJoinBlock);
		}
		if (StringUtil.isNotNull(typeWhereBlock)) {
			typeWhereBlock = "( " + typeWhereBlock + " )";
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					typeWhereBlock);
			hqlInfo.setWhereBlock(whereBlock);
		}
	}

	protected void buildHqlByDateType(RequestContext request,
			HQLInfo hqlInfo, String datetype) {
		String whereBlock = "";
		// 今日会议
		if ("today".equals(datetype)) {
			Calendar today = clearTime(Calendar.getInstance());// 今天
			Calendar tomorrow = clearTime(Calendar.getInstance());// 明天
			tomorrow.add(Calendar.DATE, 1);
			whereBlock = "kmImeetingMain.fdHoldDate<=:tomorrow and kmImeetingMain.fdFinishDate>=:today";
			hqlInfo.setParameter("today", today.getTime());
			hqlInfo.setParameter("tomorrow", tomorrow.getTime());
			hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate asc");
		}
		// 明日会议
		if ("tomorrow".equals(datetype)) {
			Calendar tomorrow = clearTime(Calendar.getInstance());// 明天
			tomorrow.add(Calendar.DATE, 1);
			Calendar afterTomorrow = clearTime(Calendar.getInstance());// 后天
			afterTomorrow.add(Calendar.DATE, 2);
			whereBlock = "kmImeetingMain.fdHoldDate<=:afterTomorrow and kmImeetingMain.fdFinishDate>=:tomorrow";
			hqlInfo.setParameter("tomorrow", tomorrow.getTime());
			hqlInfo.setParameter("afterTomorrow", afterTomorrow.getTime());
			hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate asc");
		}
		// 近期会议
		if ("afterDay".equals(datetype)) {
			Date now = new Date();
			whereBlock = "kmImeetingMain.fdHoldDate>=:now";
			hqlInfo.setParameter("now", now);
			hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate asc");
		}
		hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(),
				" and ", whereBlock));
	}

	private Calendar clearTime(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	@Override
	public List<KmImeetingMain> findKmIMeetingMain(RequestContext request,
			Boolean isMyCalendar) throws Exception {
		List<KmImeetingMain> mains = new ArrayList<KmImeetingMain>();
		if (isMyCalendar) {
			String maxhub = request.getParameter("maxhub");
			if ("true".equals(maxhub)) {
				mains = mhuGetMyKmImeetingMains(request);
			} else {
				mains = getMyKmImeetingMains(request);
			}
		} else {
			mains.addAll(findImeetingMain(request, isMyCalendar));
			// mains.addAll(findNormalMain(request, isMyCalendar));
			// mains.addAll(findRangeMain(request, isMyCalendar));
		}
		UserOperHelper.logFindAll(mains, getModelName());
		return mains;
	}

	private List<KmImeetingMain> mhuGetMyKmImeetingMains(RequestContext request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String placeId = request.getParameter("placeId");
		String isCloud = request.getParameter("isCloud");// 是否云会议
		String fdStart = request.getParameter("fdStart");// 开始时间
		String viewType = request.getParameter("viewType");// 视图类型
		Date startDateTime = null;
		if (StringUtil.isNotNull(fdStart)) {
			if (request.isCloud()) {
                startDateTime = new Date(Long.parseLong(fdStart));
            } else {
                startDateTime = DateUtil.convertStringToDate(fdStart,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                startDateTime = DateUtil.getBeginDayOfWeek();
            } else {
                startDateTime = DateUtil.getBeginDayOfMonth();
            }
		}
		String fdEnd = request.getParameter("fdEnd");// 结束时间
		Date endDateTime = null;
		if (StringUtil.isNotNull(fdEnd)) {
			if (request.isCloud()) {
                endDateTime = new Date(Long.parseLong(fdEnd));
            } else {
                endDateTime = DateUtil.convertStringToDate(fdEnd,
                        ResourceUtil.getString("date.format.date"));
            }
		} else {
			if ("week".equals(viewType)) {
                endDateTime = DateUtil.getEndDayOfWeek();
            } else {
                endDateTime = DateUtil.getEndDayOfMonth();
            }
		}
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		List listFeed = findMyfeedback();
		String whereBlock = null;
		List<KmImeetingMain> list = new ArrayList<KmImeetingMain>();
		if (listFeed.size() > 0) {
			whereBlock = "kmImeetingMain.fdHoldDate<:fdEnd and kmImeetingMain.fdFinishDate>:fdStart and kmImeetingMain.docStatus!='41'";
			if (StringUtil.isNotNull(isCloud)) {
				whereBlock = whereBlock
						+ " and kmImeetingMain.isCloud = :isCloud";
				hqlInfo.setParameter("isCloud", Boolean.parseBoolean(isCloud));
			}
			if (StringUtil.isNotNull(placeId)) {
				hqlInfo.setJoinBlock(" left join kmImeetingMain.fdPlace fdPlace ");
				whereBlock = whereBlock + " and fdPlace.fdId = :placeId";
				hqlInfo.setParameter("placeId", placeId);
			}

			hqlInfo.setParameter("fdStart", startDateTime);
			hqlInfo.setParameter("fdEnd", endDateTime);
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setOrderBy(" kmImeetingMain.fdHoldDate ");
			// 增加场所过滤
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AllCheck.DEFAULT);
			list = findValue(hqlInfo);
		}
		return list;
	}

	private List<KmImeetingMain> getMyKmImeetingMains(RequestContext request)
			throws Exception {
		List listFeed = findMyfeedback();
		List<KmImeetingMain> list = new ArrayList<KmImeetingMain>();
		if (listFeed.size() > 0) {
			list.addAll(findMyImeetingMain(request));
			// list.addAll(findMyNormalMain(request));
			// list.addAll(findMyRangeMain(request));
		}
		return list;
	}

	@Override
	public Integer getAttendStatCount(String myType, String mydoc, Date start,
			Date end)
			throws Exception {
		// 普通会议数量
		Integer countNormal = countNormal(myType, mydoc, start, end);
		// 周期性会议数量
		Integer countRange = countRange(myType, mydoc, start, end);
		return countNormal + countRange;
	}

	/**
	 * 时间段内周期会议数量
	 *
	 * @param mydoc
	 *            我XX的
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private Integer countRange(String myType, String mydoc, Date start, Date end) throws Exception {
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingMain.fdId");
		String whereBlock = " kmImeetingMain.fdRecurrenceStr is not null";
		List l = findMyfeedback("myHaveAttend");
		hqlInfo.setJoinBlock(" left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson left join kmImeetingMain.fdEmcee fdEmcee ");
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' ");
		hqlInfo.setParameter("fdStart", start);
		hqlInfo.setParameter("fdEnd", end);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		hqlInfo.setWhereBlock(whereBlock);
		if ("myAttend".equals(myType)) { // 我要参加
			if (StringUtil.isNull(hqlInfo.getJoinBlock()) || !hqlInfo.getJoinBlock().contains("kmImeetingMain.fdAttendPersons")) {
				hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join kmImeetingMain.fdAttendPersons attendPersons"));
			}
			if (CollectionUtils.isEmpty(l)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or ("
								+ HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ "))");
			}else{
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ "))");
			}
			whereBlock += " and kmImeetingMain.docStatus=:status";
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("status", SysDocConstant.DOC_STATUS_PUBLISH);
		} else if ("myHaveAttend".equals(myType)) { // 我已参加
			if (StringUtil.isNull(hqlInfo.getJoinBlock()) || !hqlInfo.getJoinBlock().contains("kmImeetingMain.fdAttendPersons")) {
				hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join kmImeetingMain.fdAttendPersons attendPersons"));
			}
			if (CollectionUtils.isEmpty(l)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ " and kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			}else{
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds)
								+ " and kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			}
			whereBlock += " and kmImeetingMain.fdFinishDate<=:attendDate ";
			hqlInfo.setParameter("fdNeedFeedback", false);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("attendDate", new Date());
		}
		if ("myPart".equals(mydoc)) { // 我参与的（作为与会人员）
			hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", " left join kmImeetingMain.fdAttendPersons attendPersons "));
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds));
		} else if ("myEmcc".equals(mydoc)) { // 我组织的
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" fdEmcee.fdId=:fdEmceeId");
			hqlInfo.setParameter("fdEmceeId", UserUtil.getUser().getFdId());
		} else if ("myHost".equals(mydoc)) { // 我主持的
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" fdHost.fdId = :hostId");
			hqlInfo.setParameter("hostId", UserUtil.getUser().getFdId());
		} else if ("myReport".equals(mydoc)) { // 我汇报的
			List r = findMyAgenda();
			if (CollectionUtils.isEmpty(r)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"1 = 2");
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						HQLUtil.buildLogicIN(
								"kmImeetingMain.fdId",
								r));
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<String> list = findValue(hqlInfo);
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		if ("myAttend".equals(myType)) { // 我要参加
			int result = 0;
			Date now = new Date();
			for (String fdId : list) {
				KmImeetingMain main = (KmImeetingMain) findByPrimaryKey(fdId);
				String recurrenceStr = main.getFdRecurrenceStr();
				Date mainStartDateTime = main.getFdHoldDate();
				Date mainEndDateTime = main.getFdFinishDate();
				List<Date> dates = RecurrenceUtil.getExcuteDateList(
						recurrenceStr,
						mainStartDateTime, start,
						end.getTime() > mainEndDateTime.getTime()
								? end : mainEndDateTime);
				for (Date date : dates) {
					Date newEndDate = new Date(
							date.getTime() + mainEndDateTime.getTime()
									- mainStartDateTime.getTime());
					if (newEndDate.getTime() > main.getFdRecurrenceLastEnd()
							.getTime()) {
						break;
					}
					if (newEndDate.getTime() > now.getTime()) {
						result++;
					}
				}
			}
			return result;
		} else if ("myHaveAttend".equals(myType)) { // 我已参加，已经生成会议，不需要再算周期会议的时间
			return list.size();
		}
		return list.size();
	}

	/**
	 * 时间段内普通会议数量
	 *
	 * @param mydoc
	 *            我XX的
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	private Integer countNormal(String myType, String mydoc, Date start, Date end) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
		hqlInfo.setSelectBlock("kmImeetingMain.fdId");
		String whereBlock = " kmImeetingMain.fdRecurrenceStr is null";
		List l = findMyfeedback("myHaveAttend");
		if("myAttend".equals(myType)){
			hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
			if (CollectionUtils.isEmpty(l)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' and (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds) + " ))");
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' and ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds) + " ))");
			}
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("fdStart", start);
			hqlInfo.setParameter("fdEnd", end);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		}else if("myHaveAttend".equals(myType)) {
			hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
			if (CollectionUtils.isEmpty(l)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' and (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid "
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds) + " and kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' and ( fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or (" + HQLUtil.buildLogicIN("attendPersons.fdId", authOrgIds) + " and kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
			}
			hqlInfo.setParameter("fdNeedFeedback", false);
			hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());
			hqlInfo.setParameter("fdStart", start);
			hqlInfo.setParameter("fdEnd", end);
			hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		} else {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmImeetingMain.fdHoldDate<=:fdEnd and kmImeetingMain.fdFinishDate>=:fdStart and kmImeetingMain.docStatus!='41' ");
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdFinishDate<=:attendDate ");
			hqlInfo.setParameter("attendDate", new Date());
			hqlInfo.setParameter("fdStart", start);
			hqlInfo.setParameter("fdEnd", end);
		}
		if ("myAttend".equals(myType)) { // 我要参加
			whereBlock += " and kmImeetingMain.fdFinishDate>:attendDate ";
			hqlInfo.setParameter("attendDate", new Date());
			whereBlock += " and kmImeetingMain.docStatus=:status";
			hqlInfo.setParameter("status", SysDocConstant.DOC_STATUS_PUBLISH);
		} else if ("myHaveAttend".equals(myType)) { // 我已参加
			// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMain.fdFinishDate<=:attendDate ");
			hqlInfo.setParameter("attendDate", new Date());
			hqlInfo.setWhereBlock(whereBlock);
		}
		if ("myPart".equals(mydoc)) { // 我参与的（作为与会人员）
			if (StringUtil.isNull(hqlInfo.getJoinBlock()) || !hqlInfo.getJoinBlock().contains("kmImeetingMain.fdAttendPersons")) {
				hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join kmImeetingMain.fdAttendPersons attendPersons"));
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					HQLUtil.buildLogicIN(
							"attendPersons.fdId",
							authOrgIds));
		} else if ("myEmcc".equals(mydoc)) { // 我组织的
			if (StringUtil.isNull(hqlInfo.getJoinBlock()) || !hqlInfo.getJoinBlock().contains("kmImeetingMain.fdEmcee")) {
				hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join kmImeetingMain.fdEmcee fdEmcee"));
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ", " fdEmcee.fdId=:fdEmceeId ");
			hqlInfo.setParameter("fdEmceeId", UserUtil.getUser().getFdId());
		} else if ("myHost".equals(mydoc)) { // 我主持的
			if (StringUtil.isNull(hqlInfo.getJoinBlock()) || !hqlInfo.getJoinBlock().contains("kmImeetingMain.fdHost")) {
				hqlInfo.setJoinBlock(StringUtil.linkString(hqlInfo.getJoinBlock(), " ", "left join kmImeetingMain.fdHost fdHost"));
			}
			whereBlock = StringUtil.linkString(whereBlock, " and ", " fdHost.fdId = :hostId");
			hqlInfo.setParameter("hostId", UserUtil.getUser().getFdId());
		} else if ("myReport".equals(mydoc)) { // 我汇报的
			List r = findMyAgenda();
			if (CollectionUtils.isEmpty(r)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"1 = 2");
			} else {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						HQLUtil.buildLogicIN(
								"kmImeetingMain.fdId",
								r));
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		List<String> list = findValue(hqlInfo);
		if (CollectionUtils.isEmpty(list)) {
			return 0;
		}
		return list.size();
	}

	private IKmImeetingMappingService kmImeetingMappingService;

	public void setKmImeetingMappingService(IKmImeetingMappingService kmImeetingMappingService) {
		this.kmImeetingMappingService = kmImeetingMappingService;
	}

	// 报错铂恩议题附件
	private void doSaveAtt(String fdKey, String fdName, String fdOrder, byte[] buffer, KmImeetingMain kmImeetingMain)
			throws Exception {
		try {
			SysAttMain sysAttMain = new SysAttMain();
			sysAttMain.setFdKey(fdKey);
			sysAttMain.setFdFileName(fdName);
			sysAttMain.setFdModelId(kmImeetingMain.getFdId());
			sysAttMain.setFdModelName(KmImeetingMain.class.getName());
			sysAttMain.setFdCreatorId(UserUtil.getUser().getFdId());
			sysAttMain.setFdContentType(FileMimeTypeUtil.getContentType(fdName));
			sysAttMain.setFdAttType("byte");
			if (sysAttMain.getDocCreateTime() == null) {
				sysAttMain.setDocCreateTime(new Date());
			}
			InputStream in = new ByteArrayInputStream(buffer);
			sysAttMain.setInputStream(in);
			sysAttMain.setFdSize(Double.valueOf(in.available()));
			ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			sysAttMainService.add(sysAttMain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public JSONObject buildBoenMeetingInfo(KmImeetingMain kmImeetingMain) throws Exception {
		JSONObject meetingObj = new JSONObject();
		// baseInfo
		JSONObject meetingBaseInfo = buildBaseInfo(kmImeetingMain);
		JSONArray meetRoles = new JSONArray();
		if (!kmImeetingMain.getFdAttendPersons().isEmpty()) { // 与会人员
			JSONObject role = buildRole(kmImeetingMain, "attend", ImeetingConstant.FEEDBACK_TYPE_ATTEND);
			meetRoles.add(role);
		}
		if (!kmImeetingMain.getFdParticipantPersons().isEmpty()) { // 列席人员
			JSONObject role = buildRole(kmImeetingMain, "nonvotor", ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT);
			meetRoles.add(role);
		}
		if (!kmImeetingMain.getFdInvitePersons().isEmpty()) { // 邀请人员
			JSONObject role = buildRole(kmImeetingMain, "invite", ImeetingConstant.FEEDBACK_TYPE_INVITE);
			meetRoles.add(role);
		}
		meetingBaseInfo.put("meetRoles", meetRoles);
		meetingObj.put("baseinfo", meetingBaseInfo);
		// feature
		if (!kmImeetingMain.getFdAssistPersons().isEmpty()) {
			JSONObject feature = new JSONObject();
			feature.put("hasMeetService", true);
			String meetServiceUserIds = "";
			for (SysOrgElement s : kmImeetingMain.getFdAssistPersons()) {
				meetServiceUserIds += s.getFdId() + ",";
			}
			feature.put("meetServiceUserIds", meetServiceUserIds.substring(0, meetServiceUserIds.length() - 1));
			meetingObj.put("feature", feature);
		}
		JSONArray processes = new JSONArray();
		JSONObject processe = buildProcess(kmImeetingMain);
		processes.add(processe);
		meetingObj.put("processes", processes);
		return meetingObj;
	}

	// 同步会议数据到铂恩会议系统
	@Override
	public void updateSyncToBoen(KmImeetingMain kmImeetingMain) throws Exception {
		try {
			JSONObject meetingObj = buildBoenMeetingInfo(kmImeetingMain);
			String url = BoenUtil.getBoenUrl() + "/openapi/meets/" + kmImeetingMain.getFdId()
					+ "?dataFrom=02";
			String result = BoenUtil.sendPut(url, meetingObj.toString());
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 200) {
					String fdMeetingId = res.get("data").toString();
					KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
							.findByPrimaryKey(kmImeetingMain.getFdId(), KmImeetingMapping.class.getName(), true);
					if (kim == null) {
						kim = kmImeetingMappingService.findByModelId(kmImeetingMain.getFdId(),
								KmImeetingMain.class.getName());
					}
					// 如果不存在直接新增
					if (kim == null) {
						kim = new KmImeetingMapping();
						kim.setFdModelId(kmImeetingMain.getFdId());
						kim.setFdModelName(KmImeetingMain.class.getName());
						kim.setFdThirdSysId(fdMeetingId);
						kmImeetingMappingService.add(kim);
					} else {
						// 如果存在且id不一致，则更新
						if (!fdMeetingId.equals(kim.getFdThirdSysId())) {
							kim.setFdThirdSysId(fdMeetingId);
							kmImeetingMappingService.update(kim);
						}
					}

					// 同步投票，表决开关
					syncVoteAndBallotSwitchToBoen(kmImeetingMain);
					Boolean voteEnable = kmImeetingMain.getFdVoteEnable();
					if (voteEnable) {
						updateVoteTemplatesToBoen(kmImeetingMain, fdMeetingId);
					} else {
						deleteVoteTemplatesToBoen(fdMeetingId);
					}
				} else {
					String message = (String) res.get("message");
					throw new RuntimeException(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 同步会议数据到铂恩会议系统
	@Override
	public void addSyncToBoen(KmImeetingMain kmImeetingMain) throws Exception {
		// {
		// "baseinfo": {
		// "meetName": "",
		// "expectStartTime": "",
		// "expectEndTime": "",
		// "meetRoomId": "",
		// "meetTypeId": "",
		// "meetSummary": "",
		// "creatorId": "",
		// "controlerId": "",
		// "hostId": "",
		// "inspectorId": "",
		// "meetRoles": [{
		// "meetRoleCode": "",
		// "customizeDisplay": "",
		// "userInfos": [{
		// "userId": 0,
		// "orderNum": 0
		// }]
		// }]
		// },
		// "feature": {
		// "hasMeetService": false,
		// "meetServiceUserIds": ""
		// },
		// "processes": [{
		// "processTypeCode": "issue",
		// "orderNum": 1,
		// "issues": [{
		// "issueName": "",
		// "organizationName": "",
		// "issueManagerId": 0,
		// "issueReporterId": 0,
		// "expectStartTime": "",
		// "orderNum": 0,
		// "issueSummary": "",
		// "issueRoles": [{
		// "issueRoleCode": "",
		// "customizeDisplay": "",
		// "userInfos": [{
		// "userId": 0,
		// "orderNum": 0
		// }]
		// }],
		// "attachments": [{
		// "attachmentName": "",
		// "attachmentUrl": "",
		// "attachmentMD5": "",
		// "orderNum": 0
		// }]
		// }]
		// }]
		// }

		try {
			JSONObject meetingObj = buildBoenMeetingInfo(kmImeetingMain);
			String url = BoenUtil.getBoenUrl() + "/openapi/meets/";
			String result = BoenUtil.sendPost(url, meetingObj.toString());
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 201) {
					String meetingId = res.get("data").toString();
					KmImeetingMapping kim = new KmImeetingMapping();
					kim.setFdModelId(kmImeetingMain.getFdId());
					kim.setFdModelName(KmImeetingMain.class.getName());
					kim.setFdThirdSysId(meetingId);
					kmImeetingMappingService.add(kim);
					// 同步投票、表决功能开关
					syncVoteAndBallotSwitchToBoen(kmImeetingMain);
					// 同步投票模板
					Boolean voteEnable = kmImeetingMain.getFdVoteEnable();
					if (voteEnable) {
						addVoteTemplatesToBoen(kmImeetingMain, meetingId);
					}
				} else {
					String message = (String) res.get("message");
					throw new RuntimeException(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private void syncVoteAndBallotSwitchToBoen(KmImeetingMain kmImeetingMain)
			throws Exception {
		// http:/IP:port/meeting-web/openapi/meetFeatures/votes/16df3d6528175d23fb47bf9a7024?open=true
		Boolean voteEnable = kmImeetingMain.getFdVoteEnable();
		String voteUrl = BoenUtil.getBoenUrl() + "/openapi/meetFeatures/votes/"
				+ kmImeetingMain.getFdId() + "?open=" + voteEnable;
		String voteResult = BoenUtil.sendPut(voteUrl, "");
		if (StringUtil.isNotNull(voteResult)) {
			JSONObject res = JSONObject.fromObject(voteResult);
			if (res.getInt("status") != 200) {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}

		// http:/IP:PORT/meeting-web/openapi/meetFeatures/balots/16df3d6528175d23fb47bf9a7024?open=true
		Boolean ballotEnable = kmImeetingMain.getFdBallotEnable();
		String ballotUrl = BoenUtil.getBoenUrl()
				+ "/openapi/meetFeatures/ballots/" + kmImeetingMain.getFdId()
				+ "?open=" + ballotEnable;
		String ballotResult = BoenUtil.sendPut(ballotUrl, "");
		if (StringUtil.isNotNull(ballotResult)) {
			JSONObject res = JSONObject.fromObject(ballotResult);
			if (res.getInt("status") != 200) {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
	}

	/**
	 * 铂恩新增投票模板
	 * 
	 * @param kmImeetingMain
	 * @param meetingId
	 * @throws Exception
	 */
	private void addVoteTemplatesToBoen(KmImeetingMain kmImeetingMain,
			String meetingId) throws Exception {
		List<KmImeetingVote> kmImeetingVotes = kmImeetingMain
				.getKmImeetingVotes();
		JSONArray voteTemplates = new JSONArray();
		for (KmImeetingVote kmImeetingVote : kmImeetingVotes) {
			JSONObject voteTemplateObj = buildVoteTemplateInfo(
					kmImeetingVote, meetingId);
			voteTemplates.add(voteTemplateObj);
		}
		String url = BoenUtil.getBoenUrl() + "/openapi/voteTemplate/batchAll";
		String result = BoenUtil.sendPost(url, voteTemplates.toString());
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") == 201) {
				JSONArray templateArr = res.getJSONArray("data");
				for (int i = 0; i < templateArr.size(); i++) {
					JSONObject obj = templateArr.getJSONObject(i);
					String thirdVoteTemplateId = obj
							.getString("thirdVoteTemplateId");
					String meetVoteTemplateId = obj
							.getString("meetVoteTemplateId");
					KmImeetingVote kmImeetingVote = (KmImeetingVote) getKmImeetingVoteService()
							.findByPrimaryKey(thirdVoteTemplateId, null, true);
					if (kmImeetingVote != null) {
						KmImeetingMapping kim = new KmImeetingMapping();
						kim.setFdModelId(kmImeetingVote.getFdId());
						kim.setFdModelName(
								KmImeetingVote.class.getName());
						kim.setFdThirdSysId(meetVoteTemplateId);
						kmImeetingMappingService.add(kim);
					}
				}

			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
	}


	private void addVoteTemplate(KmImeetingVote kmImeetingVote,
			String meetingId) throws Exception {
		JSONObject voteTemplateObj = buildVoteTemplateInfo(
				kmImeetingVote, meetingId);
		String url = BoenUtil.getBoenUrl() + "/openapi/voteTemplate/";
		String result = BoenUtil.sendPost(url,
				voteTemplateObj.toString());
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") == 201) {
				String templateId = res.get("data").toString();
				KmImeetingMapping kim = new KmImeetingMapping();
				kim.setFdModelId(kmImeetingVote.getFdId());
				kim.setFdModelName(KmImeetingVote.class.getName());
				kim.setFdThirdSysId(templateId);
				kmImeetingMappingService.add(kim);
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
	}

	private void updateVoteTemplatesToBoen(KmImeetingMain kmImeetingMain,
			String meetingId) throws Exception {
		List<KmImeetingVote> kmImeetingVotes = kmImeetingMain
				.getKmImeetingVotes();
		JSONArray voteTemplates = new JSONArray();
		for (KmImeetingVote kmImeetingVote : kmImeetingVotes) {
			KmImeetingMapping kim = (KmImeetingMapping) kmImeetingMappingService
					.findByModelId(kmImeetingVote.getFdId(),
							KmImeetingVote.class.getName());
			if (kim != null) {
				updateVoteTemplate(kmImeetingVote, meetingId,
						kim.getFdThirdSysId());
			} else {
				addVoteTemplate(kmImeetingVote, meetingId);
			}
		}

	}

	private void updateVoteTemplate(KmImeetingVote kmImeetingVote,
			String meetingId, String tempId) throws Exception {
		JSONObject voteTemplateObj = buildVoteTemplateInfo(
				kmImeetingVote, meetingId);
		String url = BoenUtil.getBoenUrl() + "/openapi/voteTemplate/" + tempId;
		String result = BoenUtil.sendPut(url,
				voteTemplateObj.toString());
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") == 200) {
				String templateId = res.get("data").toString();
				KmImeetingMapping kim = kmImeetingMappingService.findByModelId(
						kmImeetingVote.getFdId(),
						KmImeetingVote.class.getName());
				// 如果不存在直接新增
				if (kim == null) {
					kim = new KmImeetingMapping();
					kim.setFdModelId(kmImeetingVote.getFdId());
					kim.setFdModelName(KmImeetingMain.class.getName());
					kim.setFdThirdSysId(templateId);
					kmImeetingMappingService.add(kim);
				} else {
					// 如果存在且id不一致，则更新
					if (!templateId.equals(kim.getFdThirdSysId())) {
						kim.setFdThirdSysId(templateId);
						kmImeetingMappingService.update(kim);
					}
				}
			} else {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}

	}

	/**
	 * 删除投票模板配置
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	private void deleteVoteTemplatesToBoen(String meetingId) throws Exception {
		String url = BoenUtil.getBoenUrl() + "/openapi/voteTemplate/meetId/"
				+ meetingId;
		String result = BoenUtil.sendDelete(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") != 200) {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
	}

	/**
	 * { "metId": 16, "voteTitle": "投票模板23 ", "voteTargets ": [{ "voteTarget ":
	 * "投票对象 1 " }, { "voteTarget": "投票对象2 " }], "voteChoices ": [{ "voteChoice
	 * ": "自定义选项 " }, { "voteChoice": " 自定义选项2 " }, { "voteChoice ": "自定义选项 2 "
	 * }] }
	 * 
	 * @param kmImeetingVote
	 * @return
	 */
	private JSONObject buildVoteTemplateInfo(KmImeetingVote kmImeetingVote,
			String meetingId) {
		JSONObject voteTemplateObj = new JSONObject();
		voteTemplateObj.put("meetId", meetingId);
		voteTemplateObj.put("thirdVoteTemplateId", kmImeetingVote.getFdId());
		voteTemplateObj.put("voteTitle", kmImeetingVote.getDocSubject());

		JSONArray voteTargets = new JSONArray();
		List<String> voteObjs = kmImeetingVote.getFdVoteObjs();
		for (String obj : voteObjs) {
			JSONObject target = new JSONObject();
			target.put("voteTarget", obj);
			voteTargets.add(target);
		}
		voteTemplateObj.put("voteTargets", voteTargets);

		JSONArray voteChoices = new JSONArray();
		List<String> voteOptions = kmImeetingVote.getFdVoteOptions();
		for (String option : voteOptions) {
			JSONObject choice = new JSONObject();
			choice.put("voteChoice", option);
			voteChoices.add(choice);
		}
		voteTemplateObj.put("voteChoices", voteChoices);

		return voteTemplateObj;
	}

	private JSONObject buildBaseInfo(KmImeetingMain kmImeetingMain) throws Exception {
		JSONObject meetingBaseInfo = new JSONObject();
		meetingBaseInfo.put("thirdSystemUuid", kmImeetingMain.getFdId());
		meetingBaseInfo.put("dataFrom", "02");
		meetingBaseInfo.put("meetName", kmImeetingMain.getDocSubject());
		meetingBaseInfo.put("expectStartTime", kmImeetingMain.getFdHoldDate().getTime());
		meetingBaseInfo.put("expectEndTime", kmImeetingMain.getFdFinishDate().getTime());
		meetingBaseInfo.put("meetRoomId", kmImeetingMain.getFdPlace().getFdId());
		meetingBaseInfo.put("meetTypeId", kmImeetingMain.getFdTemplate().getFdId());
		meetingBaseInfo.put("meetSummary", kmImeetingMain.getFdMeetingAim());
		meetingBaseInfo.put("creatorId", BoenUtil.getUnitAdmin());
		meetingBaseInfo.put("controlerId",
				kmImeetingMain.getFdControlPerson() != null ? kmImeetingMain.getFdControlPerson().getFdId() : "");
		meetingBaseInfo.put("hostId", kmImeetingMain.getFdHost().getFdId());
		List<SysOrgElement> fdBallotPersons = kmImeetingMain.getFdBallotPersons();
		if (fdBallotPersons.size() > 0) {
			meetingBaseInfo.put("inspectorId", fdBallotPersons.get(0).getFdId()); // 监票人员，非必填
		}

		return meetingBaseInfo;
	}

	private JSONObject buildRole(KmImeetingMain kmImeetingMain, String meetingRole, String fdType) throws Exception {
		JSONObject role = new JSONObject();
		role.put("meetRoleCode", meetingRole);
		// role.put("customizeDisplay", meetingRole);
		JSONArray userInfos = new JSONArray();
		IKmImeetingMainFeedbackService kmImeetingMainFeedbackService = (IKmImeetingMainFeedbackService) SpringBeanUtil
				.getBean("kmImeetingMainFeedbackService");
		List<KmImeetingMainFeedback> l = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
						fdType, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
		for (KmImeetingMainFeedback f : l) {
			JSONObject userInfo = new JSONObject();
			userInfo.put("userId", f.getDocAttend().getFdId());
			userInfo.put("userName", f.getDocAttend().getFdName());
			userInfo.put("orderNum", f.getDocAttend().getFdOrder());
			userInfos.add(userInfo);
		}
		role.put("userInfos", userInfos);
		return role;
	}

	private JSONObject buildProcess(KmImeetingMain kmImeetingMain) throws Exception {
		JSONObject processe = new JSONObject();
		processe.put("processTypeCode", "issue");
		processe.put("orderNum", "");
		JSONArray issues = new JSONArray();
		List<KmImeetingAgenda> agendas = kmImeetingMain.getKmImeetingAgendas();
		for (int i = 0 ;i<agendas.size();i++) {
			KmImeetingAgenda agenda = agendas.get(i);
			JSONObject issue = buildIssue(agenda,i);
			issues.add(issue);
		}
		processe.put("issues", issues);
		return processe;
	}

	private JSONObject buildIssue(KmImeetingAgenda kmImeetingAgenda, int order) throws Exception {
		JSONObject issue = new JSONObject();
		issue.put("thirdSystemUuid", kmImeetingAgenda.getFdId());
		issue.put("issueName", kmImeetingAgenda.getDocSubject());
		issue.put("organizationName",
				kmImeetingAgenda.getFdChargeUnit() != null ? kmImeetingAgenda.getFdChargeUnit().getFdName() : "");// 承办部门
		// issue.put("issueManagerId", "issue"); 议题管理员，非必填
		issue.put("issueReporterId", kmImeetingAgenda.getDocReporter().getFdId());
		issue.put("expectStartTime", kmImeetingAgenda.getFdExpectStartTime().getTime());
		issue.put("orderNum", order);
		// issue.put("issueSummary", "issue"); 议题备注，非必填

		JSONArray issueRoles = new JSONArray();
		if (!kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
			JSONObject issueRole = buildIssueRole(kmImeetingAgenda, ImeetingConstant.ROLE_ISSUE_ATTEND,
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON);
			issueRoles.add(issueRole);

		}
		if (!kmImeetingAgenda.getFdListenUnit().isEmpty()) {
			JSONObject issueRole = buildIssueRole(kmImeetingAgenda, ImeetingConstant.ROLE_ISSUE_NONVOTOR,
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON);
			issueRoles.add(issueRole);
		}
		issue.put("issueRoles", issueRoles);

		JSONArray issueAttachments = new JSONArray();
		JSONObject issueAttachment = new JSONObject();
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		List<SysAttMain> attList = sysAttMainService.findByModelKey(KmImeetingMain.class.getName(),
				kmImeetingAgenda.getFdMain().getFdId(), "ImeetingUploadAtt_" + kmImeetingAgenda.getFdId());
		String BaseUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		for (int i = 0; i < attList.size(); i++) {
			SysAttMain s = attList.get(i);
			SysAttFile sysAttFile = sysAttMainService.getFile(s.getFdId());
			issueAttachment.put("thirdSystemUuid", s.getFdId());
			issueAttachment.put("attachmentName", s.getFdFileName());
			issueAttachment.put("attachmentUrl",
					BaseUrl.toLowerCase() + "/api/third-meeting/MeetingRestService/downloadFile?fdId="
							+ s.getFdId());
			issueAttachment.put("attachmentMD5", sysAttFile.getFdMd5());
			issueAttachment.put("orderNum", i);
			issueAttachments.add(issueAttachment);
		}
		issue.put("attachments", issueAttachments);
		return issue;
	}

	private JSONObject buildIssueRole(KmImeetingAgenda kmImeetingAgenda, String role, String fdType) throws Exception {
		JSONObject issueRole = new JSONObject();
		issueRole.put("issueRoleCode", role);
		// issueRole.put("customizeDisplay", role);
		JSONArray isUserInfos = new JSONArray();
		List<KmImeetingMainFeedback> l = kmImeetingMainFeedbackService
				.findFeedBackByType(kmImeetingAgenda.getFdMain().getFdId(),
						fdType, kmImeetingAgenda.getFdId(),
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
		for (KmImeetingMainFeedback f : l) {
			JSONObject isUserInfo = new JSONObject();
			isUserInfo.put("userId", f.getDocAttend().getFdId());
			isUserInfo.put("userName", f.getDocAttend().getFdName());
			isUserInfo.put("orderNum", f.getDocAttend().getFdOrder());
			isUserInfos.add(isUserInfo);
		}
		issueRole.put("userInfos", isUserInfos);
		return issueRole;
	}

	@Override
	public String updateBeginMeeting(KmImeetingMain kmImeetingMain) throws Exception {
		String rtn = "success";
		try {
			String url = BoenUtil.getBoenUrl() + "/openapi/meetControl/open/"
					+ kmImeetingMain.getFdId();
			String result = BoenUtil.sendPut(url, "");
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") != 200) {
					String message = (String) res.get("message");
					rtn = (String) res.get("message");
					throw new RuntimeException(message);
				} else {
					kmImeetingMain.setIsBegin(true);
					this.update(kmImeetingMain);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtn = e.getMessage();
			throw new RuntimeException(e.getMessage());
		}
		return rtn;
	}

	private String formatAttUrl(String str) throws UnsupportedEncodingException {
		String zhPattern = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(zhPattern);
		Matcher m = p.matcher(str);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(b, URLEncoder.encode(m.group(0), "UTF-8"));
		}
		m.appendTail(b);

		return b.toString();
	}
	
	@Override
	public String addAttFromBoenTest(HttpServletRequest request) throws Exception {
		String rtn = "success";
		String fdMeetingId  = request.getParameter("fdId");
		String attachmentName =  request.getParameter("attachmentName");
		String attachmentUrl =  request.getParameter("attachmentUrl");
		String orderNum =  request.getParameter("orderNum");
		String fdKey = request.getParameter("fdKey");
		KmImeetingMain kmImeetingMain = (KmImeetingMain)this.findByPrimaryKey(fdMeetingId);
		if (kmImeetingMain != null) {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			InputStream is = null;
			try {
				URL attUrl = new URL(formatAttUrl(attachmentUrl));
				is = attUrl.openStream();
				byte[] byt = null;

				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = is.read(buff, 0, 100)) > 0) {
					bao.write(buff, 0, rc);
				}
				byt = bao.toByteArray();
				this.doSaveAtt(fdKey, attachmentName, orderNum, byt, kmImeetingMain);
			} catch (Exception e) {
				e.printStackTrace();
				rtn = e.getMessage();
				if (is != null) {
					is.close();
				}
				bao.close();
			} finally {
				if (is != null) {
					is.close();
				}
				bao.close();
			}
		}else{
			String message = "会议不存在";
			rtn =message;
			throw new RuntimeException(message);
		}
		return rtn;
	}

	@Override
	public String addAttFromBoen(KmImeetingMain kmImeetingMain) throws Exception {
		String rtn = "success";
		try {
			String url = BoenUtil.getBoenUrl() + "/openapi/meets/" + kmImeetingMain.getFdId() + "?dataFrom=02";
			String result = BoenUtil.sendGet(url);
			if (StringUtil.isNotNull(result)) {
				JSONObject res = JSONObject.fromObject(result);
				if (res.getInt("status") == 200) {
					JSONObject data = JSONObject.fromObject(res.get("data"));
					if (data.get("processes") != null) {
						JSONArray processes = data.getJSONArray("processes");
						for (int i = 0; i < processes.size(); i++) {
							JSONObject processe = processes.getJSONObject(i);
							if (processe.get("processTypeCode") != null
									&& "issue".equals((String) processe.get("processTypeCode"))
									&& processe.get("issues") != null) {
								JSONArray issues = processe.getJSONArray("issues");
								for (int j = 0; j < issues.size(); j++) {
									JSONObject issue = issues.getJSONObject(j);
									String issueId = issue.getString("thirdSystemUuid");
									if (issue.get("attachments") != null) {
										JSONArray issueAttachments = issue.getJSONArray("attachments");
										for (int k = 0; k < issueAttachments.size(); k++) {
											JSONObject issueAttachment = issueAttachments.getJSONObject(k);
											String attachmentName = issueAttachment.getString("attachmentName");
											String attachmentUrl = issueAttachment.getString("attachmentUrl");
											String orderNum = issueAttachment.getString("orderNum");
											ByteArrayOutputStream bao = new ByteArrayOutputStream();
											InputStream is = null;
											try {
												URL attUrl = new URL(formatAttUrl(attachmentUrl));
												is = attUrl.openStream();
												byte[] byt = null;

												byte[] buff = new byte[100];
												int rc = 0;
												while ((rc = is.read(buff, 0, 100)) > 0) {
													bao.write(buff, 0, rc);
												}
												byt = bao.toByteArray();
												this.doSaveAtt("issueAttBoen_" + issueId, attachmentName, orderNum, byt,
														kmImeetingMain);
											} catch (Exception e) {
												e.printStackTrace();
												rtn =e.getMessage();
												if (is != null) {
													is.close();
												}
												bao.close();
											} finally {
												if (is != null) {
													is.close();
												}
												bao.close();
											}
										}
									}
								}
							}
						}
					}
				}else{
					String message = (String) res.get("message");
					rtn = message;
					throw new RuntimeException(message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtn =e.getMessage();
			throw new RuntimeException(e.getMessage());
		}
		return rtn;
	}

	public static String sendPostKK(String url, String content) throws Exception {
		String result = "";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		JSONObject tokenObj = KKUtil.getKKToken();
		httpPost.setHeader("push-id", tokenObj.getString("push-id"));
		httpPost.setHeader("push-sign", tokenObj.getString("push-sign"));
		StringEntity postingString = new StringEntity(content, "utf-8");
		httpPost.setEntity(postingString);
		CloseableHttpResponse response = null;
		HttpEntity httpEntity = null;
		try {
			logger.warn("kk创建调用开始：" + url + "," + content);
			response = client.execute(httpPost);
			try {
				httpEntity = response.getEntity();
				result = EntityUtils.toString(httpEntity);
			} catch (Exception e) {
				logger.error("", e);
				logger.error("kk创建调用出错：" + url + "\n result:" + result);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			logger.error("", e);
			logger.error("kk创建调用出错：" + url + "\n result:" + result);
		} finally {
			client.close();
		}
		return result;
	}

	/*
	 * { roomType : 1 // int类型 1 p2p , 2多人，3会议 mediaType : 1 // int类型 1音频 2 音视频
	 * roomName : “12345” // 房间名称，string类型 userType :
	 * 传入用户类型，1传入的是用户id，2用户登录账号。默认1 creater : 12 //int类型 发起者的userId，注意不一定是int类型。
	 * createrNimAccount : //string 类型 发起者的nimAccount。（去掉） members:
	 * “1,4,6723,22” // string类型 成员列表 逗号分隔,不包含创建者!!! membersName: “甲,乙,丙,丁” //
	 * string类型 成员姓名列表，逗号分隔 relativeSessionId ： 123 // int类型 创建room所在的sessionId
	 * }
	 */
	public JSONObject buildKKMeetingInfo(KmImeetingMain kmImeetingMain) throws Exception {
		JSONObject meetingObj = new JSONObject();
		meetingObj.put("roomType", 3);
		meetingObj.put("mediaType", 2);
		meetingObj.put("roomName", kmImeetingMain.getDocSubject());
		meetingObj.put("userType", 2);
		if (kmImeetingMain.getFdHost() != null) {
			meetingObj.put("creater", kmImeetingMain.getFdHost().getFdLoginName());
		} else {
			meetingObj.put("creater", kmImeetingMain.getDocCreator().getFdLoginName());
		}
		JSONObject memberObj = buildMembers(kmImeetingMain);
		meetingObj.put("members", memberObj.get("members"));
		meetingObj.put("membersName", memberObj.get("membersName"));
		return meetingObj;
	}

	@Override
	public Boolean canEnterKkVedio(KmImeetingMain kmImeetingMain) throws Exception {
		Boolean result = false;
		JSONObject menberObj = buildMembers(kmImeetingMain);
		if (menberObj.get("members") != null) {
			String fdMembers = (String) menberObj.get("members");
			String[] fdMemberArr = fdMembers.split(",");
			List fdMemberList = ArrayUtil.asList(fdMemberArr);
			if (fdMemberList.contains(UserUtil.getUser().getFdLoginName())) {
				result = true;
			}
			if (kmImeetingMain.getFdHost() != null && 
					UserUtil.getUser().getFdLoginName().equals(kmImeetingMain.getFdHost().getFdLoginName())) {
				result = true;
			}
		}
		return result;
	}

	public JSONObject buildMembers(KmImeetingMain kmImeetingMain) throws Exception {
		JSONObject menberObj = new JSONObject();
		List allMemberList = new ArrayList();
		List fdMembers = new ArrayList();
		List fdMembersName = new ArrayList();
		// 若会议需要回执，则取回执的人员，否则取选择的组织架构人员
		if (kmImeetingMain.getFdNeedFeedback()) {
			List<KmImeetingMainFeedback> allF = new ArrayList();
			// 与会人员
			List<KmImeetingMainFeedback> l1 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_ATTEND, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 列席人员
			List<KmImeetingMainFeedback> l2 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 邀请人员
			List<KmImeetingMainFeedback> l3 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_INVITE, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l1.isEmpty()) {
				ArrayUtil.concatTwoList(l1, allF);
			}
			if (!l2.isEmpty()) {
				ArrayUtil.concatTwoList(l2, allF);
			}
			if (!l3.isEmpty()) {
				ArrayUtil.concatTwoList(l3, allF);
			}
			if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
				List<KmImeetingMainFeedback> l4 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
				if (!l4.isEmpty()) {
					ArrayUtil.concatTwoList(l4, allF);
				}
				List<KmImeetingMainFeedback> l5 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);

				if (!l5.isEmpty()) {
					ArrayUtil.concatTwoList(l5, allF);
				}
			}
			List<KmImeetingMainFeedback> l6 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l6.isEmpty()) {
				ArrayUtil.concatTwoList(l6, allF);
			}
			if (!allF.isEmpty()) {
				for (KmImeetingMainFeedback f : allF) {
					if (f.getDocAlterTime().getTime() <= kmImeetingMain.getFdFeedBackDeadline().getTime()) {
						allMemberList.add(f.getDocAttend());
					}
				}
			}
		} else {
			// 参会人
			List fdAttendPersons = kmImeetingMain.getFdAttendPersons();
			if (fdAttendPersons != null && !fdAttendPersons.isEmpty()) {
				allMemberList.addAll(fdAttendPersons);
			}

			// 列席
			List fdParticipantPersons = kmImeetingMain.getFdParticipantPersons();
			if (fdParticipantPersons != null && !fdParticipantPersons.isEmpty()) {
				allMemberList.addAll(fdParticipantPersons);
			}

			List fdOtherPersons = kmImeetingMain.getFdOtherPersons();
			if (fdOtherPersons != null && !fdOtherPersons.isEmpty()) {
				allMemberList.addAll(fdOtherPersons);
			}
			// 汇报人
			List agendaList = kmImeetingMain.getKmImeetingAgendas();
			if (agendaList != null && !agendaList.isEmpty()) {
				for (int i = 0; i < agendaList.size(); i++) {
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
					if (kmImeetingAgenda.getDocReporter() != null) {
						allMemberList.add(kmImeetingAgenda.getDocReporter());
					}
				}
			}
		}
		// 主持人，组织人，纪要人，汇报人，协助人,汇报人
		// 主持人
		if (kmImeetingMain.getFdHost() != null) {
			allMemberList.add(kmImeetingMain.getFdHost());
		}
		// 组织人
		if (kmImeetingMain.getFdEmcee() != null) {
			allMemberList.add(kmImeetingMain.getFdEmcee());
		}
		// 纪要人
		if (kmImeetingMain.getFdSummaryInputPerson() != null) {
			allMemberList.add(kmImeetingMain.getFdSummaryInputPerson());
		}
		// 会议协助人员
		List fdAssistPersons = kmImeetingMain.getFdAssistPersons();
		if (fdAssistPersons != null && !fdAssistPersons.isEmpty()) {// 会议协助人
			allMemberList.addAll(fdAssistPersons);
		}

		List<SysOrgPerson> allMembers = sysOrgCoreService.expandToPerson(allMemberList);
		for (SysOrgPerson person : allMembers) {
			if (kmImeetingMain.getFdHost() != null) {
				if (!person.getFdLoginName().equals(kmImeetingMain.getFdHost().getFdLoginName())) {
					fdMembers.add(person.getFdLoginName());
					fdMembersName.add(person.getFdName());
				}
			} else {
				fdMembers.add(person.getFdLoginName());
				fdMembersName.add(person.getFdName());
			}
		}
		menberObj.put("members", StringUtils.join(fdMembers, ","));
		menberObj.put("membersName", StringUtils.join(fdMembersName, ","));
		return menberObj;
	}

	@Override
	public String addSyncToKk(KmImeetingMain kmImeetingMain) throws Exception {
		String rtn = "false";
		try {
			if (KKUtil.isKkVideoMeetingEnable()) {
				JSONObject meetingObj = buildKKMeetingInfo(kmImeetingMain);
				String url = KKUtil.getKKUrl() + "/serverj/nim/createRoom2.ajax";
				String result = KKUtil.sendPost(url, meetingObj.toString());
				if (StringUtil.isNotNull(result)) {
					JSONObject res = JSONObject.fromObject(result);
					if (res.getInt("retCode") == 0) {
						rtn = "true";
						JSONObject data = JSONObject.fromObject(res.get("data"));
						String meetingId = data.getString("roomId");
						KmImeetingMapping kim = new KmImeetingMapping();
						kim.setFdAppKey("kk");
						kim.setFdModelId(kmImeetingMain.getFdId());
						kim.setFdModelName(KmImeetingMain.class.getName());
						kim.setFdThirdSysId(meetingId);
						kmImeetingMappingService.add(kim);
					} else {
						String message = (String) res.get("message");
						logger.error(message);
					}
				}
			} else {
				logger.error("kk对接阿里云视频会议没有开启，不进行同步");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		}
		return rtn;
	}

	@Override
	public List<KmImeetingMain> findKmIMeetingListMain(RequestContext request)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		buildMyMeetingHql(hqlInfo, request);
		List<KmImeetingMain> mains = findList(hqlInfo);
		return mains;
	}

	private void buildMyMeetingHql(HQLInfo hqlInfo,
			RequestContext request) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		List authOrgIds = UserUtil.getKMSSUser().getUserAuthInfo()
				.getAuthOrgIds();
		CriteriaValue cv = new CriteriaValue(request.getRequest());
		String mydoc = cv.poll("mymeeting");
		List l = findMyfeedback();
		if ("myAttend".equals(mydoc)) { // 我要参加
			if (l.size() > 0) {
				// 去除重复
				hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
				// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or ("
								+ HQLUtil.buildLogicIN(
										"attendPersons.fdId",
										authOrgIds)
								+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
				hqlInfo.setParameter("fdNeedFeedback", false);
				hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());

				String mydate = cv.poll("datemeeting");
				if (StringUtil.isNotNull(mydate)) {
					Calendar current = Calendar.getInstance();
					Date startDate = null;
					Date endDate = null;
					if ("all".equals(mydate)) {
						whereBlock = StringUtil.linkString(whereBlock, " and ",
								" kmImeetingMain.fdFinishDate>:attendDate ");
						hqlInfo.setParameter("attendDate", current.getTime());
						hqlInfo.setWhereBlock(whereBlock);
					} else {
						if ("today".equals(mydate)) {// 今天
							startDate = current.getTime();
							current.set(Calendar.HOUR_OF_DAY, 0);
							current.set(Calendar.MINUTE, 0);
							current.set(Calendar.SECOND, 0);
							current.add(Calendar.DATE, 1);
							endDate = current.getTime();
						} else if ("tomorrow".equals(mydate)) {
							current.set(Calendar.HOUR_OF_DAY, 23);
							current.set(Calendar.MINUTE, 59);
							current.set(Calendar.SECOND, 59);
							startDate = current.getTime();
							current.add(Calendar.DATE, 1);
							endDate = current.getTime();
						} else if ("week".equals(mydate)) {
							startDate = current.getTime();
							endDate = DateUtil.getEndDayOfWeek();
						}
						whereBlock = StringUtil.linkString(whereBlock, " and ",
								" kmImeetingMain.fdFinishDate>=:startDate ");
						whereBlock = StringUtil.linkString(whereBlock, " and ",
								" kmImeetingMain.fdHoldDate<:endDate ");
						hqlInfo.setParameter("startDate", startDate);
						hqlInfo.setParameter("endDate", endDate);
					}
				}

				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate asc");
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
						+ " and kmImeetingMain.docStatus=:status");
				hqlInfo.setParameter("status",
						SysDocConstant.DOC_STATUS_PUBLISH);
				if (hqlInfo.getCheckParam(
						SysAuthConstant.CheckType.AllCheck) == null) {
					hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
							SysAuthConstant.AllCheck.DEFAULT);
				}
			} else {
				hqlInfo.setWhereBlock("1 = 2");
			}

		} else if ("myHaveAttend".equals(mydoc)) { // 我已参加
			if (l.size() > 0) {
				// 去除重复
				hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
				hqlInfo.setJoinBlock(" left join kmImeetingMain.fdAttendPersons attendPersons left join kmImeetingMain.fdHost fdHost left join kmImeetingMain.fdSummaryInputPerson fdSummaryInputPerson");
				// 参加的所有会议（包括参与者中有我/TA，我/TA主持，我/TA纪要）
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (fdHost.fdId=:userid or fdSummaryInputPerson.fdId=:userid or "
								+ HQLUtil.buildLogicIN("kmImeetingMain.fdId", l)
								+ " or ("
								+ HQLUtil.buildLogicIN(
										"attendPersons.fdId",
										authOrgIds)
								+ " and  kmImeetingMain.fdNeedFeedback = :fdNeedFeedback ))");
				hqlInfo.setParameter("fdNeedFeedback", false);
				hqlInfo.setParameter("userid", UserUtil.getUser().getFdId());

				hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate desc");

				String mydate = cv.poll("datemeeting");
				if (StringUtil.isNotNull(mydate)) {
					Calendar current = Calendar.getInstance();
					Date startDate = null;
					Date endDate = null;
					if ("month".equals(mydate)) {
						endDate = current.getTime();
						current.add(Calendar.MONTH, -1);
						startDate = current.getTime();
					} else if ("threeMonth".equals(mydate)) {
						endDate = current.getTime();
						current.add(Calendar.MONTH, -3);
						startDate = current.getTime();
					} else if ("halfYear".equals(mydate)) {
						endDate = current.getTime();
						current.add(Calendar.MONTH, -6);
						startDate = current.getTime();
					}
					whereBlock = StringUtil.linkString(whereBlock, " and ",
							" kmImeetingMain.fdFinishDate<=:endDate and kmImeetingMain.fdFinishDate >=:startDate ");
					hqlInfo.setParameter("startDate", startDate);
					hqlInfo.setParameter("endDate", endDate);
				}

				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
						+ " and kmImeetingMain.docStatus=:status");
				hqlInfo.setParameter("status",
						SysDocConstant.DOC_STATUS_PUBLISH);
				if (hqlInfo.getCheckParam(
						SysAuthConstant.CheckType.AllCheck) == null) {
					hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
							SysAuthConstant.AllCheck.DEFAULT);
				}
			} else {
				hqlInfo.setWhereBlock("1 = 2");
			}
		} else if ("myCreate".equals(mydoc)) {
			// 我发起的
			logger.debug("进入我发起的");
			hqlInfo.setJoinBlock(" left join kmImeetingMain.docCreator docCreator ");
			whereBlock = StringUtil.linkString(whereBlock, " and ", "docCreator.fdId=:docCreator");
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMain.docStatus != '00'");
			hqlInfo.setParameter("docCreator", UserUtil.getUser().getFdId());
			logger.debug("docCreator:" + UserUtil.getUser().getFdId());

			String myCreateDate = cv.poll("createdatemeeting");
			if (StringUtil.isNotNull(myCreateDate)) {
				Calendar current = Calendar.getInstance();
				current.setTime(new Date());
				if ("month".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -1);
				} else if ("threeMonth".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -3);
				} else if ("halfYear".equals(myCreateDate)) {
					current.add(Calendar.MONTH, -6);
				}
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmImeetingMain.docCreateTime>:createDate ");
				hqlInfo.setParameter("createDate", current.getTime());
				logger.debug("createDate:" + current.getTime());
			}

			hqlInfo.setWhereBlock(whereBlock);
			logger.debug("whereBlock:" + whereBlock);
			hqlInfo.setOrderBy("kmImeetingMain.fdHoldDate desc");
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.NO);
			logger.debug("endMyCreate");
		}
	}
	
	public void saveSyncMeetingToAliyunQuartz(KmImeetingMain kmImeetingMain) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("syncMeetingInfoAliyunQuartz");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("addSyncMeetingToAliyunQuartz");
		JSONObject parameter = new JSONObject();
		parameter.put("fdMeetingId", kmImeetingMain.getFdId());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("同步到阿里云视频会议系统，标题:" + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain.getFdFeedBackDeadline(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}
	
	/**
	 * @param checkAliMeeting 是否检查阿里云会议同步成功
	 */
	public void saveSyncPersonToAliyunQuartz(KmImeetingMain kmImeetingMain, boolean checkAliMeeting) throws Exception {
		SysQuartzModelContext quartzContext = new SysQuartzModelContext();
		quartzContext.setQuartzJobMethod("syncMeetingPersonAliyunQuartz");
		quartzContext.setQuartzJobService("kmImeetingMainService");
		quartzContext.setQuartzKey("addSyncPersonToAliyunQuartz");
		JSONObject parameter = new JSONObject();
		parameter.put("fdMeetingId", kmImeetingMain.getFdId());
		parameter.put("checkAliMeeting", new Boolean(checkAliMeeting).toString());
		quartzContext.setQuartzParameter(parameter.toString());
		quartzContext.setQuartzSubject("参会人员同步到阿里云视频会议系统，标题:" + kmImeetingMain.getDocSubject());
		quartzContext.setQuartzCronExpression(getCronExpression(kmImeetingMain.getFdFeedBackDeadline(), 0));
		sysQuartzCoreService.saveScheduler(quartzContext, kmImeetingMain);
	}
	
	@Override
	public void syncMeetingInfoAliyunQuartz(SysQuartzJobContext context) throws Exception {
		
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(parameter.getString("fdMeetingId"),
				KmImeetingMain.class.getName(), true);
		if (kmImeetingMain != null) {
			List<SysOrgPerson> meetingPersonList = buildAliyunMembers(kmImeetingMain, false);
			Boolean personResult = AliMeetingUtil.syncMeetingPersonToAliyun(meetingPersonList);
			if (personResult) {
				List<SysOrgElement> aliyunPersonList = buildAliyunPersons(kmImeetingMain);
				if (kmImeetingMain.getFdHost() != null) {
					AliMeetingUtil.createAliyunMeeting(kmImeetingMain.getFdHost().getFdId(),
							kmImeetingMain.getFdId(), kmImeetingMain.getFdName(), aliyunPersonList);
				} else {
					AliMeetingUtil.createAliyunMeeting(kmImeetingMain.getDocCreator().getFdId(),
							kmImeetingMain.getFdId(), kmImeetingMain.getFdName(), aliyunPersonList);
				}
			}
		}
		
	}
	
	/**
	 * 构建会议人员，用于阿里云视频会议人员同步
	 * 
	 * @param kmImeetingMain
	 * @param checkCanEnter 是否是校验参会人员的操作,如果是同步操作为false
	 * @return List<SysOrgPerson>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysOrgPerson> buildAliyunMembers(KmImeetingMain kmImeetingMain, boolean checkCanEnter) throws Exception {
		
		List<SysOrgElement> meetingPersons = new ArrayList<SysOrgElement>();
		
		if (kmImeetingMain.getFdNeedFeedback()) {
			List<KmImeetingMainFeedback> allF = new ArrayList();
			// 与会人员
			List<KmImeetingMainFeedback> l1 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_ATTEND, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 列席人员
			List<KmImeetingMainFeedback> l2 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 邀请人员
			List<KmImeetingMainFeedback> l3 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_INVITE, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l1.isEmpty()) {
				ArrayUtil.concatTwoList(l1, allF);
			}
			if (!l2.isEmpty()) {
				ArrayUtil.concatTwoList(l2, allF);
			}
			if (!l3.isEmpty()) {
				ArrayUtil.concatTwoList(l3, allF);
			}
			if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
				List<KmImeetingMainFeedback> l4 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
				if (!l4.isEmpty()) {
					ArrayUtil.concatTwoList(l4, allF);
				}
				List<KmImeetingMainFeedback> l5 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);

				if (!l5.isEmpty()) {
					ArrayUtil.concatTwoList(l5, allF);
				}
			}
			List<KmImeetingMainFeedback> l6 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l6.isEmpty()) {
				ArrayUtil.concatTwoList(l6, allF);
			}
			if (!allF.isEmpty()) {
				for (KmImeetingMainFeedback f : allF) {
					if (f.getDocAlterTime() == null || kmImeetingMain.getFdFeedBackDeadline() == null) {
						continue;
					}
					if (f.getDocAlterTime().getTime() <= kmImeetingMain.getFdFeedBackDeadline().getTime()) {
						SysOrgPerson attendPerson = f.getDocAttend();
						meetingPersons.add(attendPerson);
					}
				}
			}
		} else {
			
			if (kmImeetingMain.getFdHost() != null) {
				if (!meetingPersons.contains(kmImeetingMain.getFdHost())) {
					meetingPersons.add(kmImeetingMain.getFdHost());
				}
			} else if (!checkCanEnter) {
				if (!meetingPersons.contains(kmImeetingMain.getDocCreator())) {
					meetingPersons.add(kmImeetingMain.getDocCreator());
				}
			}
			// 参会人
			List fdAttendPersons = kmImeetingMain.getFdAttendPersons();
			if (fdAttendPersons != null && !fdAttendPersons.isEmpty()) {
				meetingPersons.addAll(fdAttendPersons);
			}

			// 列席
			List fdParticipantPersons = kmImeetingMain.getFdParticipantPersons();
			if (fdParticipantPersons != null && !fdParticipantPersons.isEmpty()) {
				meetingPersons.addAll(fdParticipantPersons);
			}

			List fdOtherPersons = kmImeetingMain.getFdOtherPersons();
			if (fdOtherPersons != null && !fdOtherPersons.isEmpty()) {
				meetingPersons.addAll(fdOtherPersons);
			}
			// 汇报人
			List agendaList = kmImeetingMain.getKmImeetingAgendas();
			if (agendaList != null && !agendaList.isEmpty()) {
				for (int i = 0; i < agendaList.size(); i++) {
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
					if (kmImeetingAgenda.getDocReporter() != null) {
						meetingPersons.add(kmImeetingAgenda.getDocReporter());
					}
				}
			}
		}
		
		// 主持人、组织人、纪要人、汇报人、协助人、汇报人
		
		// 主持人 --- 没有主持人用创建人代替
		if (kmImeetingMain.getFdHost() != null) {
			meetingPersons.add(kmImeetingMain.getFdHost());
		} else if (!checkCanEnter) {
			meetingPersons.add(kmImeetingMain.getDocCreator());
		}
		
		// 组织人
		if (kmImeetingMain.getFdEmcee() != null) {
			meetingPersons.add(kmImeetingMain.getFdEmcee());
		}
		// 纪要人
		if (kmImeetingMain.getFdSummaryInputPerson() != null) {
			meetingPersons.add(kmImeetingMain.getFdSummaryInputPerson());
		}
		// 会议协助人员
		List fdAssistPersons = kmImeetingMain.getFdAssistPersons();
		if (fdAssistPersons != null && !fdAssistPersons.isEmpty()) {// 会议协助人
			meetingPersons.addAll(fdAssistPersons);
		}

		List<SysOrgPerson> allMembers = sysOrgCoreService.expandToPerson(meetingPersons);
		
		return allMembers;
	}
	
	/**
	 * 会议人员，用于创建阿里云视频会议回调保存数据
	 * 
	 * @param kmImeetingMain
	 * @return List<SysOrgElement>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<SysOrgElement> buildAliyunPersons(KmImeetingMain kmImeetingMain) throws Exception {
		
		List<SysOrgElement> meetingPersons = new ArrayList<SysOrgElement>();
		
		if (kmImeetingMain.getFdNeedFeedback()) {
			List<KmImeetingMainFeedback> allF = new ArrayList();
			// 与会人员
			List<KmImeetingMainFeedback> l1 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_ATTEND, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 列席人员
			List<KmImeetingMainFeedback> l2 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			// 邀请人员
			List<KmImeetingMainFeedback> l3 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_INVITE, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l1.isEmpty()) {
				ArrayUtil.concatTwoList(l1, allF);
			}
			if (!l2.isEmpty()) {
				ArrayUtil.concatTwoList(l2, allF);
			}
			if (!l3.isEmpty()) {
				ArrayUtil.concatTwoList(l3, allF);
			}
			if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
				List<KmImeetingMainFeedback> l4 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
				if (!l4.isEmpty()) {
					ArrayUtil.concatTwoList(l4, allF);
				}
				List<KmImeetingMainFeedback> l5 = kmImeetingMainFeedbackService.findFeedBackByType(
						kmImeetingMain.getFdId(), ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON, "",
						ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);

				if (!l5.isEmpty()) {
					ArrayUtil.concatTwoList(l5, allF);
				}
			}
			List<KmImeetingMainFeedback> l6 = kmImeetingMainFeedbackService.findFeedBackByType(kmImeetingMain.getFdId(),
					ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER, "", ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND);
			if (!l6.isEmpty()) {
				ArrayUtil.concatTwoList(l6, allF);
			}
			if (!allF.isEmpty()) {
				for (KmImeetingMainFeedback f : allF) {
					if (f.getDocAlterTime().getTime() <= kmImeetingMain.getFdFeedBackDeadline().getTime()) {
						SysOrgPerson attendPerson = f.getDocAttend();
						if (!meetingPersons.contains(attendPerson)) {
							meetingPersons.add(attendPerson);
						}
					}
				}
			}
		} else {
			
			// 参会人
			List fdAttendPersons = kmImeetingMain.getFdAttendPersons();
			if (fdAttendPersons != null && !fdAttendPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdAttendPersons, meetingPersons);
			}

			// 列席
			List fdParticipantPersons = kmImeetingMain.getFdParticipantPersons();
			if (fdParticipantPersons != null && !fdParticipantPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdParticipantPersons, meetingPersons);
			}

			List fdOtherPersons = kmImeetingMain.getFdOtherPersons();
			if (fdOtherPersons != null && !fdOtherPersons.isEmpty()) {
				ArrayUtil.concatTwoList(fdOtherPersons, meetingPersons);
			}
			// 汇报人
			List agendaList = kmImeetingMain.getKmImeetingAgendas();
			if (agendaList != null && !agendaList.isEmpty()) {
				for (int i = 0; i < agendaList.size(); i++) {
					KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
					if (kmImeetingAgenda.getDocReporter() != null 
							&& !meetingPersons.contains(kmImeetingAgenda.getDocReporter())) {
						meetingPersons.add(kmImeetingAgenda.getDocReporter());
					}
				}
			}
		}
		
		// ---------- 组织人、纪要人、汇报人、协助人、汇报人------------------------
		
		// 主持人
		if (kmImeetingMain.getFdHost() != null) {
			if (!meetingPersons.contains(kmImeetingMain.getFdHost())) {
				meetingPersons.add(kmImeetingMain.getFdHost());
			}
		} else {
			if (!meetingPersons.contains(kmImeetingMain.getDocCreator())) {
				meetingPersons.add(kmImeetingMain.getDocCreator());
			}
		}
		
		// 组织人
		if (kmImeetingMain.getFdEmcee() != null
				&& !meetingPersons.contains(kmImeetingMain.getFdEmcee())) {
			meetingPersons.add(kmImeetingMain.getFdEmcee());
		}
		// 纪要人
		if (kmImeetingMain.getFdSummaryInputPerson() != null
				&& !meetingPersons.contains(kmImeetingMain.getFdSummaryInputPerson())) {
			meetingPersons.add(kmImeetingMain.getFdSummaryInputPerson());
		}
		// 会议协助人员
		List fdAssistPersons = kmImeetingMain.getFdAssistPersons();
		if (fdAssistPersons != null && !fdAssistPersons.isEmpty()) {
			ArrayUtil.concatTwoList(fdAssistPersons, meetingPersons);
		}
		
		// ---------- 组织人、纪要人、汇报人、协助人、汇报人------------------------
		
		return meetingPersons;
	}

	@Override
	public void addSyncMeetingInfoToAliyun(KmImeetingMain kmImeetingMain) throws Exception {
		if (kmImeetingMain != null) {
			List<SysOrgPerson> meetingPersonList = buildAliyunMembers(kmImeetingMain, false);
			Boolean personResult = AliMeetingUtil.syncMeetingPersonToAliyun(meetingPersonList);
			if (personResult) {
				List<SysOrgElement> aliyunPersonList = buildAliyunPersons(kmImeetingMain);
				
				String fdAliCreatorId = "";
				if (kmImeetingMain.getFdHost() != null) {
					fdAliCreatorId = kmImeetingMain.getFdHost().getFdId();
				} else {
					fdAliCreatorId = kmImeetingMain.getDocCreator().getFdId();
				}
				
				AliMeetingUtil.createAliyunMeeting(fdAliCreatorId,
						kmImeetingMain.getFdId(), kmImeetingMain.getFdName(), aliyunPersonList);
			}
		}
	}

	@Override
	public void addSyncMeetingPersonToAliyun(KmImeetingMain kmImeetingMain) throws Exception {
		if (kmImeetingMain != null) {
			List<SysOrgPerson> meetingPersonList = buildAliyunMembers(kmImeetingMain, false);
			Boolean personResult = AliMeetingUtil.syncMeetingPersonToAliyun(meetingPersonList);
		}
	}

	@Override
	public void syncMeetingPersonAliyunQuartz(SysQuartzJobContext context) throws Exception {
		JSONObject parameter = JSONObject.fromObject(context.getParameter());
		KmImeetingMain kmImeetingMain = (KmImeetingMain) this.findByPrimaryKey(parameter.getString("fdMeetingId"),
				KmImeetingMain.class.getName(), true);
		if (kmImeetingMain != null) {
			
			// 同步人员
			List<SysOrgPerson> meetingPersonList = buildAliyunMembers(kmImeetingMain, false);
			AliMeetingUtil.syncMeetingPersonToAliyun(meetingPersonList);
			
			// 是否新增阿里云会议
			String checkAliMeeting = parameter.getString("checkAliMeeting");
			if (StringUtil.isNotNull(checkAliMeeting)) {
				if (Boolean.TRUE.toString().equals(checkAliMeeting)) {
					String aliMeetingStr =  AliMeetingUtil.getAliMeetingInfo(parameter.getString("checkAliMeeting"));
					if (StringUtil.isNull(aliMeetingStr)) {
						this.addSyncMeetingInfoToAliyun(kmImeetingMain);
					}
				}
			}
		}
	}

	@Override
	public Boolean canEnterAliMeeting(KmImeetingMain kmImeetingMain) throws Exception {
		List<SysOrgPerson> aliyunMembers = buildAliyunMembers(kmImeetingMain, true);
		SysOrgPerson fdCurUser = UserUtil.getUser();
		if (!aliyunMembers.isEmpty()) {
			if (aliyunMembers.contains(fdCurUser)) {
				return true;
			}
		}
		
		// 主持人不会空，强行入会，与KK方式保持一致
		if (kmImeetingMain.getFdHost() != null) {
			if (fdCurUser.getFdId().equals(
					kmImeetingMain.getFdHost().getFdId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取支持的多语言
	 * 
	 * @return
	 */
	private List<String> getSupportedLangs() {
		String langSupport = ResourceUtil.getKmssConfigString("kmss.lang.support");
		if (StringUtil.isNotNull(langSupport)) {
			List<String> list = new ArrayList<String>();
			String[] langs = langSupport.trim().split(";");
			for (String langStr : langs) {
				String[] langPair = langStr.split("\\|");
				if (langPair.length == 2) {
					list.add(langPair[1]);
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 设置会议室地址多语言
	 * 
	 * @param notifyReplace
	 * @param place
	 * @param value
	 */
	private void setPlaceByLang(NotifyReplace notifyReplace, KmImeetingRes place, String value) {
		// 获取会议地址多语言信息
		if(place != null){
			List<String> langs = getSupportedLangs();
			if (!CollectionUtils.isEmpty(langs)) {
				for (String lang : langs) {
					String temp = "";
					Map<String, String> dynamicMap = place.getDynamicMap();
					if (lang.contains("-")) {
						temp = lang.split("-")[1];
					}
					String name = dynamicMap.get("fdName" + temp);
					if (StringUtil.isNotNull(name)) {
						notifyReplace.addReplaceField("km-imeeting:kmImeetingMain.fdPlace", lang, name);
					} else {
						notifyReplace.addReplaceField("km-imeeting:kmImeetingMain.fdPlace", lang, place.getFdName());
					}
				}
			} 
			else {
				notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace",
						StringUtil.linkString(place.getFdName(), ";", value));
			}
		}
		else if(StringUtil.isNotNull(value)){
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace", value);
		}
	}

	@Override
	public void updateMeetingFlag(KmImeetingMain kmImeetingMain) throws Exception {
		kmImeetingMain.setFdChangeMeetingFlag(Boolean.TRUE);
		super.update(kmImeetingMain);
	}

}