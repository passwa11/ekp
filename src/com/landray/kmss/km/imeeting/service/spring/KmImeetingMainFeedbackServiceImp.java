package com.landray.kmss.km.imeeting.service.spring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.dao.IKmImeetingMainFeedbackDao;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainFeedbackForm;
import com.landray.kmss.km.imeeting.model.KmImeetingAgenda;
import com.landray.kmss.km.imeeting.model.KmImeetingConfig;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.km.imeeting.service.IKmImeetingAgendaService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainFeedbackService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainCoreService;
import com.landray.kmss.sys.agenda.interfaces.SysAgendaMainContextGeneral;
import com.landray.kmss.sys.agenda.util.SysAgendaTypeEnum;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.provider.MailContext;
import com.landray.kmss.sys.notify.provider.MailIcsInfo;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.profile.util.OrgImportExportUtil;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONObject;

/**
 * 会议回执业务接口实现
 */
public class KmImeetingMainFeedbackServiceImp extends BaseServiceImp implements
		IKmImeetingMainFeedbackService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmImeetingMainFeedbackServiceImp.class);

	private ISysOrgCoreService sysOrgCoreService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	private IKmImeetingAgendaService kmImeetingAgendaService;

	public void setKmImeetingAgendaService(
			IKmImeetingAgendaService kmImeetingAgendaService) {
		this.kmImeetingAgendaService = kmImeetingAgendaService;
	}

	/**
	 * 新建会议时初始化会议回执
	 */
	@Override
	public void saveFeedbacks(KmImeetingMain kmImeetingMain) throws Exception {
		List agendaList = kmImeetingMain.getKmImeetingAgendas();
		List<SysOrgPerson> feddbackPerson = new ArrayList<>();
		List<KmImeetingMainFeedback> kmImeetingMainFeedbacks = kmImeetingMain.getKmImeetingMainFeedbacks();
		for (int i = 0; i < kmImeetingMainFeedbacks.size(); i++) {
			KmImeetingMainFeedback feedback = kmImeetingMainFeedbacks.get(i);
			feddbackPerson.add(feedback.getDocCreator());
		}
		if ("true".equals(KmImeetingConfigUtil.isTopicMng())) {
			for (int i = 0; i < agendaList.size(); i++) {
				KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(i);
				// 建议列席单位的会议联络员发待办
				if (kmImeetingAgenda.getFdAttendUnit() != null && !kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
					List fdAttendUnit = kmImeetingAgenda.getFdAttendUnit();
					for (int m = 0; m < fdAttendUnit.size(); m++) {
						List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdAttendUnit.get(m);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							ArrayUtil.concatTwoList(kmImissiveUnit.getFdMeetingLiaison(), targets);
							// 展开成人员
							targets = sysOrgCoreService.expandToPerson(targets);
							for (int j = 0; j < targets.size(); j++) {
								if (!feddbackPerson.contains(targets.get(j))){
									feddbackPerson.add((SysOrgPerson) targets.get(j));
									addFeedBackAgenda(kmImeetingMain, targets.get(j), kmImeetingAgenda, kmImissiveUnit,
											ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON);
								}
							}
						}
					}
				}
				// 建议旁听单位的会议联络员发待办
				if (kmImeetingAgenda.getFdListenUnit() != null && !kmImeetingAgenda.getFdListenUnit().isEmpty()) {
					List fdListenUnit = kmImeetingAgenda.getFdListenUnit();
					for (int m = 0; m < fdListenUnit.size(); m++) {
						List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdListenUnit.get(m);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							ArrayUtil.concatTwoList(kmImissiveUnit.getFdMeetingLiaison(), targets);
							// 展开成人员
							targets = sysOrgCoreService.expandToPerson(targets);
							for (int k = 0; k < targets.size(); k++) {
								if (!feddbackPerson.contains(targets.get(k))){
									feddbackPerson.add((SysOrgPerson) targets.get(k));
									addFeedBackAgenda(kmImeetingMain, targets.get(k), kmImeetingAgenda, kmImissiveUnit,
											ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON);
								}
							}
						}
					}
				}
			}
		}

		List<SysOrgElement> allargets = new ArrayList<SysOrgElement>();
		JSONObject json = new JSONObject();
		// 主持人、与会人员、列席人员需要会议回执单
		if (kmImeetingMain.getFdHost() != null) {
			allargets.add(kmImeetingMain.getFdHost());
			List targets = new ArrayList();
			targets.add(kmImeetingMain.getFdHost());
			// 展开成人员
			targets = sysOrgCoreService.expandToPersonIds(targets);
			for (int i = 0; i < targets.size(); i++) {
				List types = new ArrayList();
				types.add(ImeetingConstant.FEEDBACK_TYPE_HOST);
				json.put(targets.get(i), types);
			}
		}
		if (kmImeetingMain.getFdAttendPersons() != null
				&& !kmImeetingMain.getFdAttendPersons().isEmpty()) {
			allargets.addAll(kmImeetingMain.getFdAttendPersons());
			List targets = new ArrayList();
			targets.addAll(kmImeetingMain.getFdAttendPersons());
			// 展开成人员
			targets = sysOrgCoreService.expandToPersonIds(targets);
			for (int i = 0; i < targets.size(); i++) {
				List types = null;
				if (json.get(targets.get(i)) != null) {
					types = (List) json.get(targets.get(i)) ;
				} else {
					types = new ArrayList();
				}
				types.add(ImeetingConstant.FEEDBACK_TYPE_ATTEND);
				json.put(targets.get(i), types);
			}
		}
		if (kmImeetingMain.getFdParticipantPersons() != null && !kmImeetingMain.getFdParticipantPersons().isEmpty()) {
			allargets.addAll(kmImeetingMain.getFdParticipantPersons());
			List targets = new ArrayList();
			targets.addAll(kmImeetingMain.getFdParticipantPersons());
			// 展开成人员
			targets = sysOrgCoreService.expandToPersonIds(targets);
			for (int i = 0; i < targets.size(); i++) {
				List types = null;
				if (json.get(targets.get(i)) != null) {
					types = (List) json.get(targets.get(i)) ;
				} else {
					types = new ArrayList();
				}
				types.add(ImeetingConstant.FEEDBACK_TYPE_PARTICIPANT);
				json.put(targets.get(i), types);
			}
		}
		if (kmImeetingMain.getFdOtherPersons() != null && !kmImeetingMain.getFdOtherPersons().isEmpty()) {
			allargets.addAll(kmImeetingMain.getFdOtherPersons());
			List targets = new ArrayList();
			targets.addAll(kmImeetingMain.getFdOtherPersons());
			// 展开成人员
			targets = sysOrgCoreService.expandToPersonIds(targets);

			// #104289 修复 议题相关人员回执时邀请其他人参加会议，被邀请人员不能看到对应议题
			String beforeChangeContent = kmImeetingMain.getBeforeChangeContent();
			JSONObject beforeContent = JSONObject.fromObject(beforeChangeContent);
			String fdOtherPersonAgenda = (String) beforeContent.get("fdOtherPersonAgenda");
			Map<String, String> otherPersonAgendaMap = new HashMap<String, String>();
			
			if (StringUtil.isNotNull(fdOtherPersonAgenda)) {
				String[] otherPersonAgendaArr = fdOtherPersonAgenda.split(",");
				for (int i = 0; i<otherPersonAgendaArr.length; i++) {
					String tempInfo =otherPersonAgendaArr[i];
		            if(StringUtil.isNotNull(tempInfo)) {
		            	String[] agendaInfo = tempInfo.split(":");
		            	if(agendaInfo.length ==2) {
		            		otherPersonAgendaMap.put(agendaInfo[0], agendaInfo[1]);
		            	}
		            }
				}
			}
			
			for (int i = 0; i < targets.size(); i++) {
				List types = null;
				if (json.get(targets.get(i)) != null) {
					types = (List) json.get(targets.get(i)) ;
				} else {
					types = new ArrayList();
				}
				types.add(ImeetingConstant.FEEDBACK_TYPE_INVITE);
				json.put(targets.get(i), types);
				if (!otherPersonAgendaMap.isEmpty()) {
					if (otherPersonAgendaMap.containsKey(targets.get(i))) {
						json.put(targets.get(i) + "_attendAgendaId",
								otherPersonAgendaMap.get(targets.get(i)));
					}
				}
			}
		}
		for (int j = 0; j < agendaList.size(); j++) {
			KmImeetingAgenda kmImeetingAgenda = (KmImeetingAgenda) agendaList.get(j);
			// 汇报人
			if (kmImeetingAgenda.getDocReporter() != null) {
				List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
				allargets.add(kmImeetingAgenda.getDocReporter());
				targets.add(kmImeetingAgenda.getDocReporter());
				// 展开成人员
				targets = sysOrgCoreService.expandToPersonIds(targets);
				for (int i = 0; i < targets.size(); i++) {
					List types = null;
					if (json.get(targets.get(i)) != null) {
						types = (List) json.get(targets.get(i)) ;
					} else {
						types = new ArrayList();
					}
					types.add(ImeetingConstant.FEEDBACK_TYPE_TOPIC_REPORTER);
					json.put(targets.get(i), types);
					// 议题Id
					String fdAttendAgendaId = (String) json.get(targets.get(i) + "_attendAgendaId");
					fdAttendAgendaId = StringUtil.linkString(fdAttendAgendaId, ";",
							kmImeetingAgenda.getFdId());
					json.put(targets.get(i) + "_attendAgendaId", fdAttendAgendaId);
				}
			}
		}

		// 展开成人员
		List<SysOrgPerson> allargetPersons = sysOrgCoreService.expandToPerson(allargets);
		for (SysOrgPerson sysOrgPerson : allargetPersons) {
			if (json.get(sysOrgPerson.getFdId()) != null) {
				if (!feddbackPerson.contains(sysOrgPerson)){
					List types = (List) json.get(sysOrgPerson.getFdId());
					String fdAttendAgendaId = (String) json.get(sysOrgPerson.getFdId() + "_attendAgendaId");
					addFeedBack(kmImeetingMain, sysOrgPerson, StringUtils.join(types.toArray(), ";"), fdAttendAgendaId);
				}
			}
		}
	}



	private void sendNotifyToUnit(KmImeetingMain kmImeetingMain, List<SysOrgElement> receiver,
			String fdFeedBackId, String agendaId) throws Exception {
		NotifyContext notifyContext = null;
		NotifyReplace notifyReplace = new NotifyReplace();
		// 是否有会议室
		if (kmImeetingMain.getFdPlace() == null && StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
			notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.unit.notify");
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace",
					ResourceUtil.getString("kmImeetingMain.isCloud", "km-imeeting"));
		} else {
			notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.unit.notify.place");
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace", kmImeetingMain.getFdPlace() == null
					? kmImeetingMain.getFdOtherPlace() : kmImeetingMain.getFdPlace().getFdName());
		}
		// 列席和旁听单位，待办Key做区分
		notifyContext.setKey("AttendImeetingKey_" + fdFeedBackId);
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setNotifyTarget(receiver);// 通知人员
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		notifyContext
				.setLinkSubject(ResourceUtil.getString("kmImeetingMain.email.attend.notify.subject", "km-imeeting"));// 邮件格式
		if (kmImeetingMain.getFdTemplate() != null || kmImeetingMain.getFdNeedFeedback()) {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
			// 发送议题通知待办
			notifyContext.setLink(
					"/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&_todo=1&type=attend&fdId="
							+ fdFeedBackId);
		} else {
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		}
		if (StringUtil.isNotNull(agendaId)) {
			KmImeetingAgenda agenda = (KmImeetingAgenda) kmImeetingAgendaService
					.findByPrimaryKey(agendaId);
			if (agenda != null) {
				notifyReplace.addReplaceText(
						"km-imeeting:kmImeetingTopic.docSubject",
						agenda.getDocSubject());
			}
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
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdHost", fdHost);
		// #26739 初始化ics信息
		initMailIcsInfo(kmImeetingMain, notifyContext);
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext, notifyReplace);
	}

	/**
	 * 初始化ICS信息
	 */
	private void initMailIcsInfo(KmImeetingMain kmImeetingMain, NotifyContext notifyContext) {

		try {
			KmImeetingConfig config = new KmImeetingConfig();
			String setICS = config.getSetICS();
			if ("false".equals(setICS)) {
				return;
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		MailContext mailContext = notifyContext.getExtendContext(MailContext.class);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		String holdDate = "TZID=Asia/Shanghai:"
				+ format.format(kmImeetingMain.getFdHoldDate());
		String finishDate = "TZID=Asia/Shanghai:"
				+ format.format(kmImeetingMain.getFdFinishDate());
		MailIcsInfo mailIcsInfo = new MailIcsInfo(kmImeetingMain.getFdName(), holdDate, finishDate);
		mailIcsInfo.setAdvancedAlarmMinute(15);
		SysOrgPerson host = kmImeetingMain.getFdHost();
		if (host != null && StringUtil.isNotNull(host.getFdEmail())) {
			mailIcsInfo.setOrganizer(host.getFdEmail());
		}
		mailIcsInfo.setLocation(kmImeetingMain.getFdPlace() == null ? kmImeetingMain.getFdOtherPlace()
				: kmImeetingMain.getFdPlace().getFdName());
		mailContext.setMailIcsInfo(mailIcsInfo);
	}

	private void addFeedBackAgenda(KmImeetingMain kmImeetingMain, SysOrgElement sysOrgElement,
			KmImeetingAgenda kmImeetingAgenda, KmImissiveUnit kmImissiveUnit, String type) throws Exception {
		KmImeetingMainFeedback feedback = new KmImeetingMainFeedback();
		feedback.setFdFeedbackCount(0);
		feedback.setFdType(type);
		feedback.setFdAgendaId(kmImeetingAgenda.getFdId());
		String unitName = kmImissiveUnit.getFdName();
		String topicName = kmImeetingAgenda.getDocSubject();
		if (type.equals(ImeetingConstant.FEEDBACK_TYPE_TOPIC_ATTENDUNITLIAISON)) {
			unitName += ResourceUtil.getOfficialString("kmImeetingTopic.unitType.attend", "km-imeeting");
		}
		if (type.equals(ImeetingConstant.FEEDBACK_TYPE_TOPIC_LISTENUNITLIAISON)) {
			unitName += ResourceUtil.getOfficialString("kmImeetingTopic.unitType.listen", "km-imeeting");
		}
		feedback.setFdUnitName(unitName);
		feedback.setFdFromType(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_SYSTEM);
		feedback.setFdMeeting(kmImeetingMain);
		feedback.setDocCreateTime(new Date());
		feedback.setDocCreator((SysOrgPerson) sysOrgElement);
		String fdId = this.getBaseDao().add(feedback);
		// 给建议列席和旁听单位的会议联络员发送通知单，红色待办
		List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
		receiver.add(sysOrgElement);
		sendNotifyToUnit(kmImeetingMain, receiver, fdId,
				kmImeetingAgenda.getFdId());
	}

	@Override
    public void addFeedBack(KmImeetingMain kmImeetingMain, SysOrgElement sysOrgElement, String type, String agendaId) throws Exception {
		KmImeetingMainFeedback feedback = new KmImeetingMainFeedback();
		SysOrgPerson docCreator = null;
		feedback.setFdFeedbackCount(0);
		feedback.setFdType(type);
		feedback.setFdFromType(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_SYSTEM);
		feedback.setFdMeeting(kmImeetingMain);
		if (StringUtil.isNotNull(agendaId)) {
			feedback.setFdAttendAgendaId(agendaId);
		}
		feedback.setDocCreateTime(new Date());
		if (sysOrgElement instanceof SysOrgPerson) {
			docCreator = (SysOrgPerson) sysOrgElement;
		} else {
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			docCreator = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(sysOrgElement.getFdId());
		}
		feedback.setDocCreator(docCreator);
		this.getBaseDao().add(feedback);
	}

	/**
	 * 批量删除会议回执
	 */
	@Override
	public void deleteFeedbacks(KmImeetingMain kmImeetingMain) throws Exception {
		String meetingId = kmImeetingMain.getFdId();
		((IKmImeetingMainFeedbackDao) this.getBaseDao())
				.deleteFeedbackByMeeting(meetingId);
	}

	/**
	 * 根据会议ID和人员ID获取回执操作
	 */
	@Override
	public String getOptTypeByPerson(String meetingId, String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock(" kmImeetingMainFeedback.fdMeeting.fdId=:meetingId and kmImeetingMainFeedback.docCreator.fdId=:personId");
		hqlInfo.setParameter("meetingId", meetingId);
		hqlInfo.setParameter("personId", personId);
		List<KmImeetingMainFeedback> feedbacks = findList(hqlInfo);
		if (feedbacks != null && !feedbacks.isEmpty()) {
			KmImeetingMainFeedback feedback = feedbacks.get(0);
			if (StringUtil.isNotNull(feedback.getFdOperateType())) {
				return feedback.getFdOperateType();
			} else {
				// 未回执
				return ImeetingConstant.MEETING_FEEDBACK_OPT_NOT;
			}
		}
		return null;
	}

	/**
	 * 根据回执类型获得人员列表
	 */
	@Override
	public List<SysOrgElement> getPersonsByOptType(String meetingId,
			String optType) throws Exception {
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();
		if (StringUtil.isNotNull(optType)) {
			String[] optTypes = optType.split(";");
			HQLInfo hqlInfo = new HQLInfo();
			String whereBlock = "";
			for (int i = 0; i < optTypes.length; i++) {
				if (!"04".equals(optTypes[i])) {
					whereBlock = StringUtil.linkString(whereBlock, "or ",
							" kmImeetingMainFeedback.fdOperateType=:fdOperateType"
									+ i + " ");
					hqlInfo.setParameter("fdOperateType" + i, optTypes[i]);
				} else {
					whereBlock = StringUtil.linkString(whereBlock, "or ",
							" kmImeetingMainFeedback.fdOperateType is null");
				}
			}
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock = "(" + whereBlock + ")";
			}
			whereBlock += " and kmImeetingMainFeedback.fdMeeting.fdId=:meetingId";
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setWhereBlock(whereBlock);
			List<KmImeetingMainFeedback> kmImeetingMainFeedbacks = findList(hqlInfo);
			for (KmImeetingMainFeedback kmImeetingMainFeedback : kmImeetingMainFeedbacks) {
				persons.add(kmImeetingMainFeedback.getDocCreator());
			}
		}
		return persons;
	}

	@Override
	public List<String> getPersonIdsByOptType(String meetingId, String optType)
			throws Exception {
		List<String> personIds = new ArrayList<String>();
		if (StringUtil.isNotNull(optType)) {
			HQLInfo hqlInfo = new HQLInfo();
			String[] optTypes = optType.split(";");
			String whereBlock = "";
			for (int i = 0; i < optTypes.length; i++) {
				if (!"04".equals(optTypes[i])) {
					whereBlock = StringUtil.linkString(whereBlock, "or ",
							" kmImeetingMainFeedback.fdOperateType=:fdOperateType"
									+ i + " ");
					hqlInfo.setParameter("fdOperateType" + i, optTypes[i]);
				} else {
					whereBlock = StringUtil.linkString(whereBlock, "or ",
							" kmImeetingMainFeedback.fdOperateType is null ");
				}
			}
			if (StringUtil.isNotNull(whereBlock)) {
				whereBlock = "(" + whereBlock + ")";
			}
			whereBlock += " and kmImeetingMainFeedback.fdMeeting.fdId=:meetingId";
			hqlInfo.setParameter("meetingId", meetingId);
			hqlInfo.setWhereBlock(whereBlock);
			List<KmImeetingMainFeedback> kmImeetingMainFeedbacks = findList(hqlInfo);
			for (KmImeetingMainFeedback kmImeetingMainFeedback : kmImeetingMainFeedbacks) {
				personIds.add(kmImeetingMainFeedback.getDocCreator().getFdId());
			}
		}
		return personIds;
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmImeetingMainFeedbackForm feedbackForm = (KmImeetingMainFeedbackForm) form;
		String attendOther = requestContext.getParameter("attendOther");// 是否邀请他人参加
		String attendOtherIds = requestContext.getParameter("attendOtherIds");
		String attendOtherNames = requestContext
				.getParameter("attendOtherNames");
		boolean flag = "true".equals(attendOther)
				&& StringUtil.isNotNull(attendOtherIds);
		if (flag) {
			// 邀请其他人时，默认自己是回执为参加状态
			feedbackForm.setFdOperateType("01");
		}
		if (flag && StringUtil.isNull(feedbackForm.getFdReason())) {
			String reason = ResourceUtil.getString(
					"kmImeetingMain.attendother.reason", "km-imeeting")
					.replace("%attendOther%", attendOtherNames);
			feedbackForm.setFdReason(reason);
		}
		// 更新自己的回执单
		super.update(form, requestContext);
		// 邀请他人参加（新建他人的回执单），需求来源:#19440
		if (flag) {
			String[] attendOtherIdArray = attendOtherIds.split(";");
			KmImeetingMainFeedback kmImeetingMainFeedback = (KmImeetingMainFeedback) this
					.findByPrimaryKey(form.getFdId(), null, true);
			List<String> personIds = getPersonIdsByOptType(
					kmImeetingMainFeedback.getFdMeeting().getFdId(),
					"01;02;03;04"); // 01已参加 02未参加 03找人代理 04未回执
			for (String attendOtherId : attendOtherIdArray) {
				String fdType = feedbackForm.getFdType();
				// 06和07代表的是议题回执，议题回执邀请的人员直接生成回执
				if (!personIds.contains(attendOtherId) || ("06".equals(fdType) || "07".equals(fdType))) {
					SysOrgPerson attendPerson = (SysOrgPerson) this
							.findByPrimaryKey(attendOtherId,
									SysOrgPerson.class, false);
					KmImeetingMainFeedback feedback = new KmImeetingMainFeedback();
					feedback.setFdFeedbackCount(0);
					feedback.setFdType(feedbackForm.getFdType());
					feedback.setFdAgendaId(feedbackForm.getFdAgendaId());
					feedback.setFdUnitName(feedbackForm.getFdUnitName());
					feedback.setFdFromType(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_INVITE);
					feedback.setFdMeeting(kmImeetingMainFeedback.getFdMeeting());
					feedback.setDocCreateTime(new Date());
					feedback.setDocCreator(attendPerson);
					feedback.setFdInvitePerson(UserUtil.getUser());

					if (StringUtil.isNotNull(kmImeetingMainFeedback
							.getFdAttendAgendaId())) {
						// 议题ID
						feedback.setFdAttendAgendaId(kmImeetingMainFeedback
								.getFdAttendAgendaId());
					}
					
					this.getBaseDao().add(feedback);
					// 发送待办, 议题类型
					if ("06".equals(fdType) || "07".equals(fdType)) {
						List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
						receiver.add(attendPerson);
						sendNotifyToUnit(kmImeetingMainFeedback.getFdMeeting(),
								receiver, feedback.getFdId(),
								feedbackForm.getFdAgendaId());
					} else {
						sendMeetingNotifyToAttendPerson(kmImeetingMainFeedback.getFdMeeting(), attendPerson,
								ImeetingConstant.FEEDBACK_NOTIFY_INVITE);
					}
					// 如果同步方式为发送会议通知后同步，给实际参与人增加日程
					if (ImeetingConstant.MEETING_SYNC_SENDNOTIFY
							.equals(kmImeetingMainFeedback.getFdMeeting()
									.getSyncDataToCalendarTime())) {
						addSyncDataToCalendar(
								kmImeetingMainFeedback.getFdMeeting(),
								attendPerson);
					}
					// 给代理人开放访问会议权限，#9376
					addAuthToMeeting(kmImeetingMainFeedback.getFdMeeting()
							.getFdId(), attendPerson);
				}
			}
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingMainFeedback kmImeetingMainFeedback = (KmImeetingMainFeedback) modelObj;
		String fdType = kmImeetingMainFeedback.getFdType();
		String extKey = "";
		if (StringUtil.isNotNull(fdType) && ("06".equals(fdType) || "07".equals(fdType))) {
			extKey = kmImeetingMainFeedback.getFdId();
		}
		removePerson(kmImeetingMainFeedback.getFdMeeting(), extKey);

		// 找人代理，为代理人新建回执单
		if (ImeetingConstant.MEETING_FEEDBACK_OPT_PROXY
				.equals(kmImeetingMainFeedback.getFdOperateType())) {
			// 更新反馈时间
			kmImeetingMainFeedback.setDocAlterTime(new Date());
			// 尝试删除自己的日程
			deleteSyncDataToCalendar(kmImeetingMainFeedback.getFdMeeting());

			List<String> personIds = getPersonIdsByOptType(
					kmImeetingMainFeedback.getFdMeeting().getFdId(),
					"01;02;03;04");
			// 未包含在回执列表的才生成回执单
			if (!personIds.contains(kmImeetingMainFeedback.getDocAttend()
					.getFdId()) || ("06".equals(fdType) || "07".equals(fdType))) {
				KmImeetingMainFeedback feedback = new KmImeetingMainFeedback();
				feedback.setFdFeedbackCount(0);
				feedback.setFdType(fdType);
				feedback.setFdAgendaId(kmImeetingMainFeedback.getFdAgendaId());
				feedback.setFdUnitName(kmImeetingMainFeedback.getFdUnitName());
				feedback.setFdMeeting(kmImeetingMainFeedback.getFdMeeting());
				feedback.setFdFromType(ImeetingConstant.MEETING_FEEDBACK_FROMTYPE_PROXY);
				feedback.setDocCreateTime(new Date());
				feedback.setDocCreator((SysOrgPerson) kmImeetingMainFeedback
						.getDocAttend());
				feedback.setFdInvitePerson(UserUtil.getUser());
				if (StringUtil.isNotNull(kmImeetingMainFeedback
						.getFdAttendAgendaId())) {
					// 议题ID
					feedback.setFdAttendAgendaId(kmImeetingMainFeedback
							.getFdAttendAgendaId());
				}
				this.getBaseDao().add(feedback);
				// 发送待办, 议题类型
				if ("06".equals(fdType) || "07".equals(fdType)) {
					List<SysOrgElement> receiver = new ArrayList<SysOrgElement>();
					receiver.add(kmImeetingMainFeedback.getDocAttend());
					sendNotifyToUnit(kmImeetingMainFeedback.getFdMeeting(),
							receiver, feedback.getFdId(),
							kmImeetingMainFeedback.getFdAgendaId());
				} else {
					sendMeetingNotifyToAttendPerson(kmImeetingMainFeedback.getFdMeeting(),
							kmImeetingMainFeedback.getDocAttend(), ImeetingConstant.FEEDBACK_NOTIFY_PROXY);
				}
				// 如果同步方式为发送会议通知后同步，给实际参与人增加日程
				if (ImeetingConstant.MEETING_SYNC_SENDNOTIFY
						.equals(kmImeetingMainFeedback.getFdMeeting()
								.getSyncDataToCalendarTime())) {
					addSyncDataToCalendar(
							kmImeetingMainFeedback.getFdMeeting(),
							kmImeetingMainFeedback.getDocAttend());
				}
				// 给代理人开放访问会议权限，#9376
				addAuthToMeeting(kmImeetingMainFeedback.getFdMeeting()
						.getFdId(),
						kmImeetingMainFeedback.getDocAttend());
			}
		} else {
			if (!ImeetingConstant.MEETING_FEEDBACK_OPT_UNATTEND
					.equals(kmImeetingMainFeedback.getFdOperateType())) {
				kmImeetingMainFeedback.setDocAttend(kmImeetingMainFeedback
						.getDocCreator());
			} else {
				kmImeetingMainFeedback.setDocAttend(null);
			}
			kmImeetingMainFeedback.setDocAlterTime(new Date());
			KmImeetingMain kmImeetingMain = kmImeetingMainFeedback
					.getFdMeeting();
			// 参加且同步时机为确认后再同步，同步日程
			if (ImeetingConstant.MEETING_SYNC_PERSONATTEND
					.equals(kmImeetingMain.getSyncDataToCalendarTime())) {
				if (ImeetingConstant.MEETING_FEEDBACK_OPT_ATTEND
						.equals(kmImeetingMainFeedback.getFdOperateType())
						&& "personAttend".equals(kmImeetingMain
								.getSyncDataToCalendarTime())) {
					addSyncDataToCalendar(kmImeetingMain, UserUtil.getUser());
				}
			}
			// 不参加，尝试删除日程
			if (ImeetingConstant.MEETING_FEEDBACK_OPT_UNATTEND
					.equals(kmImeetingMainFeedback.getFdOperateType())) {
				deleteSyncDataToCalendar(kmImeetingMain);
			}
		}
		getBaseDao().update(modelObj);
	}

	private void removePerson(KmImeetingMain kmImeetingMain, String extKey) throws Exception {
		String fdKey = "AttendImeetingKey";
		if (StringUtil.isNotNull(extKey)) {
			fdKey += "_" + extKey;
		}
		// 待办置为已办
		sysNotifyMainCoreService.getTodoProvider().removePerson(kmImeetingMain,
				fdKey, UserUtil.getUser());
	}


	/**
	 * 给代理人开放访问会议权限，#9376
	 */
	private void addAuthToMeeting(String meetingId, SysOrgElement person)
			throws Exception {
		IKmImeetingMainService kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
				.getBean("kmImeetingMainService");
		IBaseModel model = kmImeetingMainService
				.findByPrimaryKey(meetingId);
		if (model != null && model instanceof KmImeetingMain) {
			KmImeetingMain kmImeetingMain = (KmImeetingMain) model;
			kmImeetingMain.getAuthOtherReaders().add(person);
			kmImeetingMain.getFdOtherPersons().add(person);
			getBaseDao().update(kmImeetingMain);
		}
	}

	// 给实际参与人发送待办
	private void sendMeetingNotifyToAttendPerson(KmImeetingMain kmImeetingMain,
			SysOrgElement attender, String type) throws Exception {
		List<SysOrgElement> target = new ArrayList<SysOrgElement>();
		target.add(attender);
		NotifyContext notifyContext = null;
		NotifyReplace notifyReplace = new NotifyReplace();
		// 是否有会议室
		if (kmImeetingMain.getFdPlace() == null
				&& StringUtil.isNull(kmImeetingMain.getFdOtherPlace())) {
			if (StringUtil.isNotNull(type)) { // 邀请或代理的待办通知
				notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain." + type + ".notify");
				notifyReplace.addReplaceText("operatePerson", UserUtil.getUser().getFdName());
			} else {
				notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.attend.notify");
			}
		} else {
			if (StringUtil.isNotNull(type)) {// 邀请或代理的待办通知
				notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingMain." + type + ".notify.place");
				notifyReplace.addReplaceText("operatePerson", UserUtil.getUser().getFdName());
			} else {
				notifyContext = sysNotifyMainCoreService.getContext("km-imeeting:kmImeetingMain.attend.notify.place");
			}
			notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdPlace",
					kmImeetingMain
					.getFdPlace() == null ? kmImeetingMain.getFdOtherPlace()
					: kmImeetingMain.getFdPlace().getFdName());
		}
		notifyReplace.addReplaceText("km-imeeting:kmImeetingMain.fdName", kmImeetingMain.getFdName());
		notifyReplace.addReplaceDate("km-imeeting:kmImeetingMain.fdDate", kmImeetingMain.getFdHoldDate(),
				DateUtil.TYPE_DATETIME);
		notifyContext.setKey("AttendImeetingKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());// 通知方式
		notifyContext.setNotifyTarget(target);// 通知人员
		notifyContext.setDocCreator(kmImeetingMain.getFdNotifyer());
		notifyContext
				.setLink(
						"/km/imeeting/km_imeeting_main_feedback/kmImeetingMainFeedback.do?method=edit&_todo=1&type=attend&meetingId="
						+ kmImeetingMain.getFdId());
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	@Override
	public Long getCountByMeetingAndType(String meetingId, String optType)
			throws Exception {
		return ((IKmImeetingMainFeedbackDao) this.getBaseDao())
				.getCountByMeetingAndType(meetingId, optType);
	}

	@Override
	public Long getAttendCountByMeeting(String meetingId) throws Exception {
		return ((IKmImeetingMainFeedbackDao) this.getBaseDao())
				.getAttendCountByMeeting(meetingId);
	}

	@Override
	public Long getFeedbackCountByMeeting(String meetingId) throws Exception {
		return ((IKmImeetingMainFeedbackDao) this.getBaseDao())
				.getFeedbackCountByMeeting(meetingId);
	}

	@Override
	public Long getUnAttendCountByMeeting(String meetingId) throws Exception {
		return ((IKmImeetingMainFeedbackDao) this.getBaseDao())
				.getUnAttendCountByMeeting(meetingId);
	}

	// ************** 日程机制接口(开始) ******************************//

	/**
	 * 初始化日程数据
	 */
	private SysAgendaMainContextGeneral initSysAgendaMainContextGeneral(
			IBaseModel model) {
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
		return sysAgendaMainContextGeneral;
	}

	/**
	 * 日程同步
	 */
	public void addSyncDataToCalendar(IBaseModel model, SysOrgPerson person)
			throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		SysAgendaMainContextGeneral sysAgendaMainContextGeneral = initSysAgendaMainContextGeneral(mainModel);
		if (UserUtil.getUser() != mainModel.getFdSummaryInputPerson() || mainModel.getFdHost() == UserUtil.getUser()
				|| (mainModel.getFdAttendPersons() != null && mainModel.getFdAttendPersons().contains(UserUtil.getUser()))
				|| (mainModel.getFdParticipantPersons() != null && mainModel.getFdParticipantPersons().contains(UserUtil.getUser()))) {
			List<SysOrgElement> ownerTarget = new ArrayList<SysOrgElement>();
			ownerTarget.add(person);
			sysAgendaMainCoreService.deleteSyncDataToCalendar(ownerTarget,
					mainModel);
			sysAgendaMainContextGeneral.setOwnerTarget(ownerTarget);
			sysAgendaMainCoreService.addSyncDataToCalendar(
					sysAgendaMainContextGeneral, mainModel);
		}

	}

	/**
	 * 删除同步(针对普通模块)
	 */
	public void deleteSyncDataToCalendar(IBaseModel model)
			throws Exception {
		ISysAgendaMainCoreService sysAgendaMainCoreService = (ISysAgendaMainCoreService) SpringBeanUtil
				.getBean("sysAgendaMainCoreService");
		KmImeetingMain mainModel = (KmImeetingMain) model;
		List<SysOrgElement> ownerTargetList = new ArrayList<SysOrgElement>();
		ownerTargetList.add(UserUtil.getUser());
		sysAgendaMainCoreService.deleteSyncDataToCalendar(ownerTargetList,
				mainModel);
	}

	@Override
	public void getFeedBackExport(KmImeetingMain kmImeetingMain,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String meetingId = kmImeetingMain.getFdId();
		// 通知人数（即回执单总数）
		Long notifyCount = getFeedbackCountByMeeting(meetingId);
		// 参加人数
		Long attendCount = getAttendCountByMeeting(meetingId);
		// 代理人数
		Long proxyCount = getCountByMeetingAndType(meetingId, "03");
		// 不参加人数
		Long unAttendCount = getUnAttendCountByMeeting(meetingId) + proxyCount;
		// 已回执人数
		Long feedbackCount = attendCount + unAttendCount;
		StringBuffer sb = new StringBuffer();
		sb.append(getStr("kmImeetingMainFeedback.notifyCount") + ":"
				+ notifyCount + " ");
		sb.append(getStr("kmImeetingMainFeedback.feedbackCount") + ":"
				+ feedbackCount + " ");
		sb.append(getStr("kmImeetingMainFeedback.attendCount") + ":"
				+ attendCount + " ");
		sb.append(getStr("kmImeetingMainFeedback.unAttendCount") + ":"
				+ unAttendCount);
		List headContentList = new ArrayList();
		headContentList.add(sb.toString());
		String meetingName = kmImeetingMain.getFdName();
		String fileName = "(" + meetingName + ")"
				+ getStr("kmImeetingMainFeedback.title.stat");
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename=\""
				+ OrgImportExportUtil.encodeFileName(request, fileName)
				+ ".xls\"");
		WorkBook wb = new WorkBook();
		wb.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		sheet.setTitle(fileName);
		sheet.setIfCreateSheetTitleLine(true);
		sheet.setHeadContentList(headContentList);

		String[] baseColumns = new String[] {
				getStr("kmImeetingMainFeedback.docCreator"),
				getStr("kmImeetingMainFeedback.dept"),
				getStr("kmImeetingMainFeedback.filed.opttype"),
				getStr("kmImeetingMainFeedback.filed.attend"),
				getStr("kmImeetingMainFeedback.filed.reason") };

		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			sheet.addColumn(col);
		}

		List contentList = new ArrayList();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setRowSize(Integer.MAX_VALUE);
		hqlInfo.setOrderBy("docCreator.hbmParent asc");
		hqlInfo.setWhereBlock(
				" kmImeetingMainFeedback.fdMeeting=:kmImeetingMain");
		hqlInfo.setParameter("kmImeetingMain", kmImeetingMain);
		List<KmImeetingMainFeedback> feedbackList = (List<KmImeetingMainFeedback>) findPage(
				hqlInfo).getList();
		UserOperHelper.logFindAll(feedbackList, getModelName());
		for (KmImeetingMainFeedback feedback : feedbackList) {
			Object[] objs = new Object[sheet.getColumnList().size()];
			SysOrgElement docCreator = feedback.getDocCreator();
			objs[0] = docCreator.getFdName();
			SysOrgElement fdParent = docCreator.getFdParent();
			objs[1] = fdParent != null ? fdParent.getDeptLevelNames() : "";
			String fdOperateType = feedback.getFdOperateType();
			String type;
			if (StringUtil.isNull(fdOperateType)) {
				type = getStr(
						"enumeration_km_imeeting_main_feedback_fd_operate_type_noopt");
			} else {
				switch (fdOperateType) {
				case "01":
					type = getStr(
							"enumeration_km_imeeting_main_feedback_fd_operate_type_attend");
					break;
				case "02":
					type = getStr(
							"enumeration_km_imeeting_main_feedback_fd_operate_type_unattend");
					break;
				case "03":
					type = getStr(
							"enumeration_km_imeeting_main_feedback_fd_operate_type_proxy");
					break;
				default:
					type = getStr(
							"enumeration_km_imeeting_main_feedback_fd_operate_type_attendother");
					break;
				}
			}
			objs[2] = type;
			SysOrgPerson docAttend = feedback.getDocAttend();
			objs[3] = docAttend != null ? docAttend.getFdName() : "";
			String fdReason = feedback.getFdReason();
			objs[4] = fdReason != null ? fdReason : "";
			contentList.add(objs);
		}
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(fileName);

		ExcelOutput output = new ExcelOutputImp();
		output.output(wb, response);
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "km-imeeting");
	}

	// ************** 日程机制接口(结束) ******************************//
	
	
	
	@Override
	public List<KmImeetingMainFeedback> findFeedBackByType(String fdMeetingId,
			String fdType, String fdAgendaId, String fdOperateType)
			throws Exception {
		List<KmImeetingMainFeedback> l = new ArrayList();
		HQLInfo hqlInfo = new HQLInfo();
		// hqlInfo.setSelectBlock("kmImeetingMainFeedback.docAttend.fdId,kmImeetingMainFeedback.docAttend.fdOrder");
		String whereBlock = "kmImeetingMainFeedback.fdMeeting.fdId =:fdMeetingId and kmImeetingMainFeedback.fdType like:fdType ";
		hqlInfo.setParameter("fdMeetingId", fdMeetingId);
		hqlInfo.setParameter("fdType", "%" + fdType + "%");
		if (StringUtil.isNotNull(fdOperateType)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" kmImeetingMainFeedback.fdOperateType=:fdOperateType");
			hqlInfo.setParameter("fdOperateType", fdOperateType);
		}
		if (StringUtil.isNotNull(fdAgendaId)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmImeetingMainFeedback.fdAgendaId=:fdAgendaId");
			hqlInfo.setParameter("fdAgendaId", fdAgendaId);
		}
		hqlInfo.setOrderBy("kmImeetingMainFeedback.docAttend.fdOrder asc");
		hqlInfo.setWhereBlock(whereBlock);
		l = this.findList(hqlInfo);
		return l;
	}

	@Override
	public KmImeetingMainFeedback findFeedBackByElement(String fdMeetingId,
			String elementId) throws Exception {
		KmImeetingMainFeedback result = null;
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmImeetingMainFeedback.fdMeeting.fdId =:fdMeetingId and kmImeetingMainFeedback.docCreator.fdId=:elementId";
		hqlInfo.setParameter("fdMeetingId", fdMeetingId);
		hqlInfo.setParameter("elementId", elementId);
		hqlInfo.setWhereBlock(whereBlock);
		List<KmImeetingMainFeedback> list = this.findList(hqlInfo);
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}

	@Override
	public String checkIsSameTime(String fdMeetingId, String fdPersonIds) throws Exception {
		
		JSONObject result = new JSONObject();
		
		if (StringUtil.isNull(fdMeetingId) || StringUtil.isNull(fdPersonIds)) {
			result.put("flag", "false");
			result.put("fdPersonName", "");
			return result.toString();
		}
		
		String fdPersonName = "";
		String[] fdPersonIdArray = fdPersonIds.split(";");
		List<String> fdPersonIdList =  ArrayUtil.asList(fdPersonIdArray);
		IKmImeetingMainService kmImeetingMainService = (IKmImeetingMainService) SpringBeanUtil
				.getBean("kmImeetingMainService");
		KmImeetingMain kmImeetingMain = (KmImeetingMain) kmImeetingMainService
				.findByPrimaryKey(fdMeetingId);
		Date fdHoldTime = kmImeetingMain.getFdHoldDate();
		Date fdEndTime = kmImeetingMain.getFdFinishDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fdHoldDate = dateFormat.parse(dateFormat.format(fdHoldTime));
		Date fdEndDate = dateFormat.parse(dateFormat.format(fdEndTime));
		
		for (String fdPersonId : fdPersonIdList) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(" left join kmImeetingMainFeedback.docCreator docCreator left join kmImeetingMainFeedback.docAttend docAttend left join kmImeetingMainFeedback.fdMeeting fdMeeting");
			String whereBlock = " docCreator.fdId = :fdAttendPersonId"
					+ " and"
					+ " docAttend.fdId = :fdAttendPersonId"
					+ " and"
					+ " kmImeetingMainFeedback.fdOperateType = :fdOperateType"
					+ " and"
					+ " ((:fdHoldTime < fdMeeting.fdHoldDate and fdMeeting.fdHoldDate <:fdEndTime)"
					+ " or"
					+ " (:fdHoldTime < fdMeeting.fdFinishDate and  fdMeeting.fdFinishDate <:fdEndTime))"
					+ " and"
					+ " fdMeeting.fdId <> :fdMeetingId"
					+ " and"
					+ " fdMeeting.docStatus <> :docStatus";
			hqlInfo.setParameter("fdAttendPersonId", fdPersonId);
			hqlInfo.setParameter("fdHoldTime", fdHoldDate);
			hqlInfo.setParameter("fdEndTime", fdEndDate);
			hqlInfo.setParameter("fdMeetingId", fdMeetingId);
			hqlInfo.setParameter("fdOperateType", "01");
			hqlInfo.setParameter("docStatus", "41");
			hqlInfo.setWhereBlock(whereBlock);
			List feedbackList = this.findList(hqlInfo);
			
			if (feedbackList != null && feedbackList.size() > 0) {
				SysOrgPerson person = ((KmImeetingMainFeedback) feedbackList.get(0)).getDocCreator();
				fdPersonName += person.getFdName() + ";";
			}
		}
		
		if (StringUtil.isNotNull(fdPersonName) && fdPersonName.endsWith(";")) {
			fdPersonName = fdPersonName.substring(0, fdPersonName.length() - 1);
			result.put("flag", "true");
			result.put("fdPersonName", fdPersonName);
		} else {
			result.put("flag", "false");
			result.put("fdPersonName", "");
		}
		
		return result.toString();
	}
	
}
