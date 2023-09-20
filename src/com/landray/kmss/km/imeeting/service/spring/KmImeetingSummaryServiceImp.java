package com.landray.kmss.km.imeeting.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.dao.IKmImeetingSummaryDao;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainHistory;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainHistoryService;
import com.landray.kmss.km.imeeting.service.IKmImeetingMainService;
import com.landray.kmss.km.imeeting.service.IKmImeetingSummaryService;
import com.landray.kmss.kms.multidoc.interfaces.IFileDataService;
import com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge;
import com.landray.kmss.kms.multidoc.model.KmsMultidocSubside;
import com.landray.kmss.kms.multidoc.service.IKmsMultidocSubsideService;
import com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.category.interfaces.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.ProcessDiscardEvent;
import com.landray.kmss.sys.lbpm.engine.manager.event.ProcessFinishedEvent;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.portal.cloud.util.ListDataUtil;
import com.landray.kmss.sys.portal.util.PortletTimeUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议纪要业务接口实现
 */
public class KmImeetingSummaryServiceImp extends BaseServiceImp implements
		IKmImeetingSummaryService, ApplicationListener, IFileDataService {

	private IKmImeetingMainHistoryService kmImeetingMainHistoryService;
	private IKmImeetingMainService kmImeetingMainService;
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private ISysAttMainCoreInnerService sysAttMainCoreInnerService;
	private ISysOrgCoreService sysOrgCoreService;
	private IKmsMultidocSubsideService kmsMultidocSubsideService;

	public void setKmsMultidocSubsideService(
			IKmsMultidocSubsideService kmsMultidocSubsideService) {
		this.kmsMultidocSubsideService = kmsMultidocSubsideService;
	}

	private ISysNotifyTodoService notifyTodoService;
	public ISysNotifyTodoService getNotifyTodoService() {
		if (notifyTodoService == null) {
			notifyTodoService = (ISysNotifyTodoService) SpringBeanUtil.getBean("sysNotifyTodoService");
        }
		return notifyTodoService;
	}
	
	public void setKmImeetingMainHistoryService(
			IKmImeetingMainHistoryService kmImeetingMainHistoryService) {
		this.kmImeetingMainHistoryService = kmImeetingMainHistoryService;
	}

	public void setKmImeetingMainService(
			IKmImeetingMainService kmImeetingMainService) {
		this.kmImeetingMainService = kmImeetingMainService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setSysAttMainCoreInnerService(
			ISysAttMainCoreInnerService sysAttMainCoreInnerService) {
		this.sysAttMainCoreInnerService = sysAttMainCoreInnerService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) modelObj;
		if (kmImeetingSummary.getFdMeeting() != null) {
			String meetingId = kmImeetingSummary.getFdMeeting().getFdId();
			KmImeetingMain kmImeetingMain = (KmImeetingMain) kmImeetingMainService
					.findByPrimaryKey(meetingId);
			// 如果提交为草稿，则不保存标记
			if (!ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingMain
					.getDocStatus())) {
				kmImeetingMain.setFdSummaryFlag(new Boolean(true));
				kmImeetingMainService.update(kmImeetingMain);
			}
			// 若纪要提交为草稿，不消除待办
			if(!ImeetingConstant.DOC_STATUS_DRAFT.equals(kmImeetingSummary.
					getDocStatus())) {
				// #51376 关联会议后待办置已办
				if(kmImeetingMain != null) {
					removeManFormTodo(kmImeetingMain);
				}
			}
		}
		List attendPersons = kmImeetingSummary.getFdActualAttendPersons();
		List signPersons = sysOrgCoreService.expandToPerson(attendPersons);
		kmImeetingSummary.setFdSignPersons(signPersons);
		return super.add(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) modelObj;
		if (kmImeetingSummary.getFdMeeting() != null) {
			KmImeetingMain kmImeetingMain = kmImeetingSummary.getFdMeeting();
			kmImeetingMain.setFdSummaryFlag(new Boolean(false));
			kmImeetingMainService.update(kmImeetingMain);
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
		if (!(obj instanceof KmImeetingSummary)) {
            return;
        }
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) obj;
		// 纪要审批通过后
		if (event instanceof ProcessFinishedEvent) {
			try {
				kmImeetingSummary.setDocPublishTime(new Date());
				getBaseDao().update(kmImeetingSummary);
				// 发送待办
				sendSummaryNotify(kmImeetingSummary);
				// 增加历史记录
				addSummaryHistory(kmImeetingSummary);
			} catch (Exception e) {
				throw new KmssRuntimeException(e);
			}
		}
		// 处理流程废弃事件
		if (event instanceof ProcessDiscardEvent) {
			if (kmImeetingSummary.getFdMeeting() != null) {
				KmImeetingMain kmImeetingMain = kmImeetingSummary
						.getFdMeeting();
				kmImeetingMain.setFdSummaryFlag(new Boolean(false));
				try {
					// 这里是特别要注意的地方，不能直接调用service的update方法保存域模型，否则会产生死循环！切记
					kmImeetingMainService.update(kmImeetingMain);
				} catch (Exception e) {
					throw new KmssRuntimeException(e);
				}
			}
		}
	}

	/**
	 * 纪要通过后向主持人、参与人员、列席人员发送待办
	 */
	private void sendSummaryNotify(KmImeetingSummary kmImeetingSummary)
			throws Exception {
		String fdNotifyType = kmImeetingSummary.getFdNotifyType();
		if (StringUtil.isNotNull(fdNotifyType)) {
			List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
			String notifyPerson = kmImeetingSummary.getFdNotifyPerson();
			if (StringUtil.isNotNull(notifyPerson)) {
				if (notifyPerson.indexOf("3") != -1) {
					// 实际与会人员
					if (kmImeetingSummary.getFdActualAttendPersons() != null) {
						targets.addAll(
								kmImeetingSummary.getFdActualAttendPersons());
					}
				}
				if (notifyPerson.indexOf("5") != -1) {
					// 与会人员
					if (kmImeetingSummary.getFdPlanAttendPersons() != null) {
						targets.addAll(
								kmImeetingSummary.getFdPlanAttendPersons());
					}
					// 列席人员
					if (kmImeetingSummary
							.getFdPlanParticipantPersons() != null) {
						targets.addAll(
								kmImeetingSummary
										.getFdPlanParticipantPersons());
					}
				}
				if (notifyPerson.indexOf("1") != -1) {
					// 会议组织人
					if (kmImeetingSummary.getFdEmcee() != null) {
						targets.add(kmImeetingSummary.getFdEmcee());
					}
				}
				if (notifyPerson.indexOf("2") != -1) {
					// 主持人
					if (kmImeetingSummary.getFdHost() != null) {
						targets.add(kmImeetingSummary.getFdHost());
					}
				}
				if (notifyPerson.indexOf("4") != -1) {
					// 抄送人员
					if (kmImeetingSummary.getFdCopyToPersons() != null) {
						targets.addAll(kmImeetingSummary.getFdCopyToPersons());
					}
				}
			}
			if (!targets.isEmpty()) {
				NotifyContext notifyContext = sysNotifyMainCoreService
						.getContext("km-imeeting:kmImeetingSummary.summary.notify");
				notifyContext.setKey("kmImeetingSummaryKey");
				notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
				notifyContext
						.setNotifyType(kmImeetingSummary.getFdNotifyType());
				notifyContext.setNotifyTarget(targets);
				notifyContext.setDocCreator(kmImeetingSummary.getDocCreator());
				// HashMap<String, String> hashMap = new HashMap<String,
				// String>();
				NotifyReplace notifyReplace = new NotifyReplace();
				// hashMap.put("km-imeeting:kmImeetingSummary.fdName",
				// kmImeetingSummary.getFdName());
				notifyReplace.addReplaceText(
						"km-imeeting:kmImeetingSummary.fdName",
						kmImeetingSummary.getFdName());
				sysNotifyMainCoreService.sendNotify(kmImeetingSummary,
						notifyContext,
						notifyReplace);
			}
		}
	}

	// 增加纪要历史记录
	private void addSummaryHistory(KmImeetingSummary kmImeetingSummary)
			throws Exception {
		JSONObject jsonObject = new JSONObject();// 操作内容JSON
		String date = DateUtil.convertDateToString(kmImeetingSummary
				.getFdHoldDate(), DateUtil.PATTERN_DATETIME)
				+ "~"
				+ DateUtil.convertDateToString(kmImeetingSummary
						.getFdFinishDate(), DateUtil.PATTERN_DATETIME);
		jsonObject.put("date", date);
		String[] strIdsNames = ArrayUtil.joinProperty(kmImeetingSummary
				.getFdActualAttendPersons(), "fdId:fdName", ";");
		jsonObject.put("attendPersonIds", strIdsNames[0]);
		if (StringUtil.isNotNull(kmImeetingSummary.getFdActualOtherAttendPersons())) {
			strIdsNames[1] += ";" + kmImeetingSummary.getFdActualOtherAttendPersons();
		}
		jsonObject.put("attendPersonNames", strIdsNames[1]);
		KmImeetingMainHistory history = new KmImeetingMainHistory();
		history.setFdMeeting(kmImeetingSummary.getFdMeeting());
		history.setFdOptDate(new Date());// 操作时间
		history.setFdOptContent(jsonObject.toString());// 内容
		history.setFdOptType(ImeetingConstant.MEETING_MAIN_SUMMARY);// 类型:会议纪要
		history.setFdOptPerson(UserUtil.getUser());
		kmImeetingMainHistoryService.add(history);
	}

	@Override
	public void hastenMeetingSummary(SysQuartzJobContext context)
			throws Exception {
		List<KmImeetingMain> list = kmImeetingMainService.findList(
				"docStatus=30 and fdIsHurrySummary=true and fdSummaryInputPerson is not null and fdSummaryCompleteTime is not null and fdHurryDate is not null", null);
		for (KmImeetingMain kmImeetingMain : list) {
			Long days = kmImeetingMain.getFdHurryDate();
			Date completeTime = kmImeetingMain.getFdSummaryCompleteTime();
			// 如果纪要完成时间或者提前天数为空则不发送催办信息
			if (completeTime == null || days == null) {
                continue;
            }
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(completeTime);
			cal2.add(Calendar.DATE, days.intValue());
			cal1.set(Calendar.HOUR_OF_DAY, 0);
			cal1.set(Calendar.MINUTE, 0);
			cal1.set(Calendar.SECOND, 0);
			cal1.set(Calendar.MILLISECOND, 0);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
				sendhastenNotifyType(kmImeetingMain);
			}
		}
	}

	/**
	 * 催办会议纪要
	 */
	private void sendhastenNotifyType(KmImeetingMain kmImeetingMain)
			throws Exception {
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("km-imeeting:kmImeetingSummary.summary.hasten");
		notifyContext.setKey("kmImeetingHastenSummaryKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyType(kmImeetingMain.getFdNotifyWay());
		List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
		targets.add(kmImeetingMain.getFdSummaryInputPerson());
		notifyContext.setNotifyTarget(targets);
		// HashMap<String, String> hashMap = new HashMap<String, String>();
		NotifyReplace notifyReplace = new NotifyReplace();
		// hashMap.put("km-imeeting:kmImeetingSummary.fdName",
		// kmImeetingMain
		// .getFdName());
		notifyReplace.addReplaceText("km-imeeting:kmImeetingSummary.fdName",
				kmImeetingMain
				.getFdName());
		sysNotifyMainCoreService.sendNotify(kmImeetingMain, notifyContext,
				notifyReplace);
	}

	@Override
	public int updateDucmentTemplate(String ids, String templateId)
			throws Exception {
		return ((IKmImeetingSummaryDao) this.getBaseDao())
				.updateDocumentTemplate(
				ids, templateId);
	}

	/**
	 * 会议附件转纪要
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void saveAttachment(String modelId,
			IAttachmentForm attchmentForm) throws Exception {
		ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
				.getBean("sysAttMainService");
		List<SysAttMain> attMains = ((ISysAttMainCoreInnerDao) sysAttMainService
				.getBaseDao()).findByModelKey(
						"com.landray.kmss.km.imeeting.model.KmImeetingMain",
						modelId, "tmpAttachment");
		List<SysAttMain> newAttMains = new ArrayList<SysAttMain>();
		String[] newAttMainIds = new String[attMains.size()];
		int index = 0;
		if (attMains != null && !attMains.isEmpty()) {
			for (SysAttMain sysAttMain : attMains) {
				SysAttMain newSysAttMain = (SysAttMain) sysAttMain.clone();
				newSysAttMain.setFdKey("attachment");
				newSysAttMain.setFdModelId(modelId);
				newSysAttMain.setFdModelName(
						"com.landray.kmss.km.imeeting.model.KmImeetingSummary");
				newSysAttMain.setDocCreateTime(new Date());
				newSysAttMain.setFdCreatorId(UserUtil.getUser().getFdId());
				sysAttMainService.update(newSysAttMain);
				newAttMains.add(newSysAttMain);
				newAttMainIds[index] = newSysAttMain.getFdId();
				index++;
			}
		}
		AttachmentDetailsForm attForm = new AttachmentDetailsForm();
		attForm.setFdKey("attachment");
		attForm.setAttachmentIds(ArrayUtil.concat(newAttMainIds, ';'));
		attForm.getAttachments().addAll(newAttMains);
		attchmentForm.getAttachmentForms().put("attachment", attForm);
	}

	@Override
	public String addSubsideFileMainDoc(HttpServletRequest request, String fdId,
			KmsMultidocSubside subside) throws Exception {
		KmImeetingSummary mainModel = (KmImeetingSummary) findByPrimaryKey(fdId);
		KmImeetingTemplate tempalte = mainModel.getFdTemplate();
		List list = kmsMultidocSubsideService.getCoreModels(tempalte, null);
		KmsMultidocKnowledge kmsMultidocKnowledge = new KmsMultidocKnowledge();
		kmsMultidocKnowledge.setSubModelId(fdId);
		kmsMultidocKnowledge.setSubModelName(
				"com.landray.kmss.km.imeeting.model.KmImeetingSummary");
		if (subside != null) {
			kmsMultidocSubsideService.addFileField(kmsMultidocKnowledge,
					subside, mainModel);
			String url = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=printSubsideDoc&fdId="
					+ mainModel.getFdId() + "&fdSaveApproval="
					+ subside.getFdSaveApproval();
			String fileName = mainModel.getDocSubject() + ".html";
			kmsMultidocSubsideService.addFilePrintPageZoom(kmsMultidocKnowledge,
					request, url, fileName, "1.0");
			kmsMultidocSubsideService.addFileAttachement(kmsMultidocKnowledge,
					mainModel);// 保存会议附件
			kmsMultidocSubsideService.addSubside(kmsMultidocKnowledge, request);
			update(mainModel);
			return kmsMultidocKnowledge.getFdId();
		} else {
			if (list.size() > 0 && list != null) {
				KmsMultidocSubside kmsMultidocSubside = (KmsMultidocSubside) list
						.get(0);
				kmsMultidocSubsideService.addFileField(kmsMultidocKnowledge,
						kmsMultidocSubside, mainModel);
				String url = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=printSubsideDoc&fdId="
						+ mainModel.getFdId();
				String fileName = mainModel.getDocSubject() + ".html";
				kmsMultidocSubsideService.addFilePrintPageZoom(kmsMultidocKnowledge,
						request, url, fileName, "1.0");
				kmsMultidocSubsideService.addFileAttachement(
						kmsMultidocKnowledge, mainModel);// 保存会议附件
				kmsMultidocSubsideService.addSubside(kmsMultidocKnowledge,
						request);
				update(mainModel);
				if (kmsMultidocSubside.getCategory() != null) {
					return kmsMultidocKnowledge.getFdId();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, ?> listPortlet(RequestContext request) throws Exception {
		Map<String, Object> rtnMap = new HashMap<>();
		Page page = Page.getEmptyPage();// 简单列表使用
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "1=1";
		// 选择的分类
		String fdCategoryId = request.getParameter("fdCategoryId");
		if (StringUtil.isNotNull(fdCategoryId)) {
			String templateProperty = "kmImeetingSummary.fdTemplate";
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					CategoryUtil.buildChildrenWhereBlock(
							fdCategoryId, null, templateProperty,hqlInfo));
		}
		// 行数
		String para = request.getParameter("rowsize");
		int rowsize = 10;
		if (!StringUtil.isNull(para)) {
			rowsize = Integer.parseInt(para);
		}
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(rowsize);
		// 时间范围参数
		String scope = request.getParameter("scope");
		if (StringUtil.isNotNull(scope) && !"no".equals(scope)) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"kmImeetingSummary.docPublishTime > :fdStartTime");
			hqlInfo.setParameter("fdStartTime",
					PortletTimeUtil.getDateByScope(scope));
		}
		hqlInfo.setWhereBlock(whereBlock);
		String type = request.getParameter("type");
		if (StringUtil.isNotNull(type)) {
			// 所有的
			if ("all".equals(type)) {
				whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(),
						" and ", "kmImeetingSummary.docStatus = '30'");
				hqlInfo.setWhereBlock(whereBlock);
			}
			// 我创建的
			if ("myCreate".equals(type)) {
				whereBlock = StringUtil.linkString(hqlInfo.getWhereBlock(),
						" and ", "kmImeetingSummary.docCreator.fdId = :userId");
				hqlInfo.setWhereBlock(whereBlock);
				hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
			}
			// 待我审的
			if ("unExecuted".equals(type)) {
				SysFlowUtil.buildLimitBlockForMyApproval("kmImeetingSummary",
						hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			}
			// 我已审的
			if ("executed".equals(type)) {
				SysFlowUtil.buildLimitBlockForMyApproved("kmImeetingSummary",
						hqlInfo);
				hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
						SysAuthConstant.AllCheck.NO);
			}
		}
		hqlInfo.setOrderBy("kmImeetingSummary.docCreateTime desc");
		// 去重复
		hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
		// hqlInfo.setWhereBlock(whereBlock);
		page = findPage(hqlInfo);
		JSONArray datas = genArray((List<KmImeetingSummary>) page.getList(),
				request);
		rtnMap.put("datas", datas);
		rtnMap.put("page", page);
		// 记录操作日志
		UserOperHelper.logFindAll(page.getList(), getModelName());
		return rtnMap;
	}

	private JSONArray genArray(List<KmImeetingSummary> list,
			RequestContext request) {
		JSONArray datas = new JSONArray();
		for (KmImeetingSummary kmImeetingSummary : list) {
			JSONObject data = new JSONObject();
			data.put("text", kmImeetingSummary.getFdName());
			if (request.isCloud()) {
				boolean isNew = "true".equals(request.getParameter("isNew"));
				if(kmImeetingSummary.getDocStatus().equals(SysDocConstant.DOC_STATUS_PUBLISH)){
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.holding","km-imeeting"), null, "success", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.holding","km-imeeting"));
						data.put("statusColor","success");
					}
				}
				if(kmImeetingSummary.getDocStatus().equals(SysDocConstant.DOC_STATUS_DRAFT) || 
						kmImeetingSummary.getDocStatus().equals(SysDocConstant.DOC_STATUS_REFUSE) ||
						kmImeetingSummary.getDocStatus().equals(SysDocConstant.DOC_STATUS_EXAMINE)){
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.publish.examing",
								"km-imeeting"), null, "warning", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.publish.examing",
								"km-imeeting"));
						data.put("statusColor","warning");
					}
				}
				if("41".equals(kmImeetingSummary.getDocStatus()) ||
						kmImeetingSummary.getDocStatus().equals(SysDocConstant.DOC_STATUS_DISCARD)){
					if (isNew) {
						data.put("statusInfo", ListDataUtil.buildIinfo(null, ResourceUtil.getString(
								"kmImeeting.status.cancel", "km-imeeting"), null, "weaken", null));
					} else {
						data.put("statusInfo", ResourceUtil.getString(
								"kmImeeting.status.cancel", "km-imeeting"));
						data.put("statusColor","weaken");
					}
				}
				data.put("creator", ListDataUtil
						.buildCreator(kmImeetingSummary.getDocCreator()));
				// List<IconDataVO> icons = new ArrayList<>(1);
				// IconDataVO icon = new IconDataVO();
				// icon.setName("tree-navigation");
				// icons.add(icon);
				// data.put("icons", icons);
				if (isNew) {
					data.put("created", ListDataUtil.buildIinfo(kmImeetingSummary.getDocCreateTime().getTime()));
					data.put("cateName", ListDataUtil.buildIinfo(null, kmImeetingSummary.getFdTemplate() != null
							? kmImeetingSummary.getFdTemplate().getFdName() : null,
							"/km/imeeting/#j_path=/myHandleSummary&cri.q=fdTemplate:"
									+ kmImeetingSummary.getFdTemplate().getFdId(),
							null, null));
				} else {
					data.put("created", kmImeetingSummary.getDocCreateTime().getTime());
					data.put("cateName", kmImeetingSummary.getFdTemplate() != null
							? kmImeetingSummary.getFdTemplate().getFdName() : null);
					data.put("cateHref",
							"/km/imeeting/#j_path=/myHandleSummary&cri.q=fdTemplate:"
									+ kmImeetingSummary.getFdTemplate().getFdId());
				}
			} else {
				data.put("created", DateUtil.convertDateToString(
						kmImeetingSummary.getDocCreateTime(),
						DateUtil.TYPE_DATE, ResourceUtil.getLocaleByUser()));
				data.put("creator",
						kmImeetingSummary.getDocCreator().getFdName());
				data.put("catename", kmImeetingSummary.getFdTemplate() != null
						? kmImeetingSummary.getFdTemplate().getFdName() : null);
				data.put("catehref",
						"/km/imeeting/#j_path=/myHandleSummary&cri.q=fdTemplate:"
								+ kmImeetingSummary.getFdTemplate().getFdId());
			}

			StringBuffer sb = new StringBuffer();
			sb.append(
					"/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=view");
			sb.append("&fdId=" + kmImeetingSummary.getFdId());
			data.put("href", sb.toString());
			datas.add(data);
		}
		return datas;
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmImeetingSummary kmImeetingSummary = (KmImeetingSummary) modelObj;
		KmImeetingMain kmImeetingMain = kmImeetingSummary.getFdMeeting();
		// 若关联会议且待办未置为已办，消除待办
		if(kmImeetingMain != null) {
			if(checkManSummaryTodo(kmImeetingMain)) {
				removeManFormTodo(kmImeetingMain);
			}
		}
		List attendPersons = kmImeetingSummary.getFdActualAttendPersons();
		List signPersons = sysOrgCoreService.expandToPerson(attendPersons);
		kmImeetingSummary.setFdSignPersons(signPersons);
		super.update(modelObj);
	}

	/**
	 * 待办置为已办
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	private void removeManFormTodo(KmImeetingMain kmImeetingMain) throws Exception {
		sysNotifyMainCoreService.getTodoProvider().removePerson(kmImeetingMain,
				"SummaryInputPersonImeetingKey", UserUtil.getUser());
	}
	
	/**
	 * 查询当前登录人是否具有某一会议的录入纪要待办
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Boolean checkManSummaryTodo(KmImeetingMain kmImeetingMain) throws Exception{
		Boolean result = false;
		NativeQuery query = getNotifyTodoService().getBaseDao().getHibernateSession().createNativeQuery("select fd_todoid from sys_notify_todotarget t "
				+ "left join sys_notify_todo d on t.fd_todoid = d.fd_id "
				+ "where t.fd_orgid = :fdPersonId "
				+ "and d.fd_model_id = :fdModelId "
				+ "and d.fd_model_name = :fdModelName "
				+ "and d.fd_key = :fdKey");
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("km-imeeting");
		query.addScalar("fd_todoid", StandardBasicTypes.STRING);
		List<String> ids = query.setParameter("fdPersonId",UserUtil.getUser().getFdId())
				.setParameter("fdModelId", kmImeetingMain.getFdId())
				.setParameter("fdModelName",
						ModelUtil.getModelClassName(kmImeetingMain))
				.setParameter("fdKey", "SummaryInputPersonImeetingKey").list();
		if(ids != null && ids.size() > 0) {
			result = true;
		}
		return result;
	}
}
