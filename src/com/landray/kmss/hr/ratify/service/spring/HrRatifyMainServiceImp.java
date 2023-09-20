package com.landray.kmss.hr.ratify.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyMainService;
import com.landray.kmss.hr.ratify.service.IHrRatifyTemplateService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffRatifyLogService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.hr.staff.service.spring.HrStaffEntryValidator;
import com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrRatifyMainServiceImp extends ExtendDataServiceImp
		implements IHrRatifyMainService, ApplicationListener {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    private IHrRatifyTemplateService hrRatifyTemplateService;

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}
    private ISysNumberFlowService sysNumberFlowService;

	public ISysNumberFlowService getSysNumberFlowService() {
		return sysNumberFlowService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		return hrStaffPersonInfoService;
	}

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;

	public IHrStaffPersonInfoLogService getHrStaffPersonInfoLogService() {
		return hrStaffPersonInfoLogService;
	}

	public void setHrStaffPersonInfoLogService(
			IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

	private ISysQuartzCoreService sysQuartzCoreService;

	public ISysQuartzCoreService getSysQuartzCoreService() {
		return sysQuartzCoreService;
	}

	public void setSysQuartzCoreService(
			ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	private IHrStaffRatifyLogService hrStaffRatifyLogService;

	private IHrStaffTrackRecordService hrStaffTrackRecordService;

	public void setHrStaffTrackRecordService(
			IHrStaffTrackRecordService hrStaffTrackRecordService) {
		this.hrStaffTrackRecordService = hrStaffTrackRecordService;
	}

	public IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		return hrStaffTrackRecordService;
	}

	public IHrStaffRatifyLogService getHrStaffRatifyLogService() {
		return hrStaffRatifyLogService;
	}

	public void setHrStaffRatifyLogService(
			IHrStaffRatifyLogService hrStaffRatifyLogService) {
		this.hrStaffRatifyLogService = hrStaffRatifyLogService;
	}

	private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;

	public void setHrStaffPersonExperienceContractService(
			IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService) {
		this.hrStaffPersonExperienceContractService = hrStaffPersonExperienceContractService;
	}

	public IHrStaffPersonExperienceContractService
			getHrStaffPersonExperienceContractService() {
		return hrStaffPersonExperienceContractService;
	}

	private IHrStaffEntryService hrStaffEntryService;

	public IHrStaffEntryService getHrStaffEntryService() {
		return hrStaffEntryService;
	}

	public void setHrStaffEntryService(IHrStaffEntryService hrStaffEntryService) {
		this.hrStaffEntryService = hrStaffEntryService;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext)
			throws Exception {
        HrRatifyMain hrRatifyMain = new HrRatifyMain();
        hrRatifyMain.setDocCreateTime(new Date());
        hrRatifyMain.setDocCreator(UserUtil.getUser());
        hrRatifyMain.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
        if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
            com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) hrRatifyTemplateService.findByPrimaryKey(templateId);
            if (docTemplate != null) {
                hrRatifyMain.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyMain.setDocKeyword(tKeyword);
            }
        }
        HrRatifyUtil.initModelFromRequest(hrRatifyMain, requestContext);
        if (hrRatifyMain.getDocTemplate() != null) {
			hrRatifyMain.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyMain.getDocTemplate(),
							hrRatifyMain.getDocTemplate().getFdTempKey()));
            if (Boolean.FALSE.equals(hrRatifyMain.getDocTemplate().getDocUseXform())) {
                hrRatifyMain.setDocXform(hrRatifyMain.getDocTemplate().getDocXform());
            }
            hrRatifyMain.setDocUseXform(hrRatifyMain.getDocTemplate().getDocUseXform());
        }
        return hrRatifyMain;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyMain hrRatifyMain = (HrRatifyMain) model;
        if (hrRatifyMain.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyMain.getDocTemplate().getFdTempKey(),
					hrRatifyMain.getDocTemplate(),
					hrRatifyMain.getDocTemplate().getFdTempKey(),
					requestContext);
        }
    }

    @Override
    public List<HrRatifyMain> findByDocTemplate(HrRatifyTemplate docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrRatifyMain.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    public IHrRatifyTemplateService getHrRatifyTemplateService() {
        return this.hrRatifyTemplateService;
    }

    public void setHrRatifyTemplateService(IHrRatifyTemplateService hrRatifyTemplateService) {
        this.hrRatifyTemplateService = hrRatifyTemplateService;
    }


    public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
        this.sysNumberFlowService = sysNumberFlowService;
    }

	@Override
	public String getCount(String type, Boolean isDraft) throws Exception {
		String count = "0";
		HQLInfo hql = new HQLInfo();
		hql.setGettingCount(true);
		if ("draft".equals(type)) {
			String whereBlock = " hrRatifyMain.docCreator.fdId=:createorId";
			if (isDraft) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" hrRatifyMain.docStatus=:docStatus");
				hql.setParameter("docStatus", "10");
			}
			hql.setParameter("createorId", UserUtil.getUser().getFdId());
			hql.setWhereBlock(whereBlock);
		} else if ("approved".equals(type)) {
			LbpmUtil.buildLimitBlockForMyApproved("hrRatifyMain", hql,
					UserUtil.getUser().getFdId());
		} else {
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}

		List<Long> list = this.getBaseDao().findValue(hql);
		if (list.size() > 0) {
			count = list.get(0).toString();
		} else {
			count = "0";
		}
		return count;
	}

	@Override
	public String getCount(HQLInfo hqlInfo) throws Exception {
		String count = "0";
		List<Long> list = this.getBaseDao().findValue(hqlInfo);
		if (list.size() > 0) {
			count = list.get(0).toString();
		} else {
			count = "0";
		}
		return count;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrRatifyMain mainModel = (HrRatifyMain) modelObj;
		mainModel.setTitleRegulation( mainModel.getDocTemplate().getTitleRegulation());
		setDocNumber(mainModel);
		HrRatifyTitleUtil.genTitle(mainModel);
		return super.add(modelObj);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyMain)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			HrRatifyMain main = (HrRatifyMain) obj;
			main.setDocPublishTime(new Date());
			try {
				this.update(main);
				feedbackNotify(main);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.error("", e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			HrRatifyMain main = (HrRatifyMain) obj;
			main.setDocPublishTime(new Date());
			try {
				this.update(main);
			} catch (Exception e1) {
				logger.error("", e1);
			}
		}
	}

	public void feedbackNotify(HrRatifyMain main) throws Exception {
		// 实时反馈
		List feedbackList = main.getFdFeedback();
		if (feedbackList.size() > 0) {
			NotifyReplace notifyReplace = new NotifyReplace();
			notifyReplace.addReplaceText(
					"hr-ratify:hrRatify.docSubject", main
							.getDocSubject());
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext(
							"hr-ratify:hrRatify.feedback.notify");
			notifyContext.setKey("hr-passreadKey");
			notifyContext
					.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setNotifyTarget(feedbackList);
			notifyContext.setNotifyType("todo");
			sysNotifyMainCoreService.sendNotify(main, notifyContext,
					notifyReplace);
		}
	}

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = sysNumberFlowService
					.generateFlowNumber(mainModel);
			mainModel.setDocNumber(docNumber);
		}
	}

	public void addTrackRecord(HrRatifyMain mainModel) throws Exception {

	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {

	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		super.delete(mainModel);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		HrRatifyMain mainModel = (HrRatifyMain) modelObj;
		String modelName = getSubModelName(modelObj.getFdId());
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(modelName);
		IHrRatifyMainService mainService = (IHrRatifyMainService) SpringBeanUtil
				.getBean(sysDictModel.getServiceBean());
		mainService.deleteEntity(mainModel);
	}

	@Override
	public HrRatifyMain findEntity(String fdId) throws Exception {
		String modelName = getSubModelName(fdId);
		SysDictModel sysDictModel = SysDataDict.getInstance()
				.getModel(modelName);
		IHrRatifyMainService mainService = (IHrRatifyMainService) SpringBeanUtil
				.getBean(sysDictModel.getServiceBean());
		HrRatifyMain mainModel = (HrRatifyMain) mainService
				.findByPrimaryKey(fdId);
		return mainModel;
	}

	private String getSubModelName(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String modelName = getModelName();
		modelName = modelName.substring(modelName.lastIndexOf(".") + 1);
		modelName = modelName.substring(0, 1).toLowerCase()
				+ modelName.substring(1);
		hqlInfo.setSelectBlock(modelName + ".fdSubclassModelname");
		hqlInfo.setWhereBlock(modelName + ".fdId=:fdId");
		hqlInfo.setParameter("fdId", fdId);
		List<String> list = findList(hqlInfo);
		return list.get(0);
	}

	public void addUpdateLog(String fdDetails,
			HrStaffPersonInfo hrStaffPersonInfo) throws Exception {
		HrStaffPersonInfoLog log = hrStaffPersonInfoLogService
				.buildPersonInfoLog("update", fdDetails);
		List<HrStaffPersonInfo> fdTargets = new ArrayList<HrStaffPersonInfo>();
		fdTargets.add(hrStaffPersonInfo);
		log.setFdTargets(fdTargets);
		hrStaffPersonInfoLogService.add(log);
	}

	@Override
	public void updateFeedbackPeople(HrRatifyMain main, List notifyTarget)
			throws Exception {
		super.update(main);
		if (main.getFdNotifyType() == null) {
			return;
		}
		NotifyReplace notifyReplace = new NotifyReplace();
		notifyReplace.addReplaceText("hr-ratify:hrRatify.docSubject",
				main.getDocSubject());
		NotifyContext notifyContext = sysNotifyMainCoreService
				.getContext("hr-ratify:hrRatify.feedback.notify");
		notifyContext.setKey("hr-passreadKey");
		notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
		notifyContext.setNotifyTarget(notifyTarget);
		notifyContext.setNotifyType(main.getFdNotifyType());
		sysNotifyMainCoreService.sendNotify(main, notifyContext, notifyReplace);
	}

	@Override
	public JSONArray getRatifyMobileIndex() throws Exception {
		JSONArray array = new JSONArray();
		JSONObject json = null;

		//所有待我处理的人事流程待办
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List allMain = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "approval");
		json.put("count", allMain.get(0));
		array.add(json);

		//待入职记录数
		HrStaffEntryValidator _val = (HrStaffEntryValidator)SpringBeanUtil.getBean("hrStaffEntryValidator");
		ValidatorRequestContext _ctx = new ValidatorRequestContext();
		boolean _flag = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_HRSTAFF_READALL")||_val.validate(_ctx);
		Object waitEntryCount = 0;
		if(_flag){
			hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("count(*)");
			hqlInfo.setWhereBlock("hrStaffEntry.fdStatus = '1'");
			List waitEntryList = hrStaffEntryService.findList(hqlInfo);
			waitEntryCount = waitEntryList.get(0);
		}
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock("hrStaffEntry.fdStatus = '1'");
		List waitEntryList = hrStaffEntryService.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "waitEntry");
		json.put("count", waitEntryCount);
		array.add(json);

		//入职流程数
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock("hrRatifyMain.fdSubclassModelname = 'com.landray.kmss.hr.ratify.model.HrRatifyEntry'");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List entryList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "entry");
		json.put("count", entryList.get(0));
		array.add(json);

		//转正流程数
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname = 'com.landray.kmss.hr.ratify.model.HrRatifyPositive'");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List positiveList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "positive");
		json.put("count", positiveList.get(0));
		array.add(json);

		//调动流程
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname = 'com.landray.kmss.hr.ratify.model.HrRatifyTransfer'");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List transferList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "transfer");
		json.put("count", transferList.get(0));
		array.add(json);

		//离职流程
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname = 'com.landray.kmss.hr.ratify.model.HrRatifyLeave'");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List leaveList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "leave");
		json.put("count", leaveList.get(0));
		array.add(json);

		//人事合同
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname in('com.landray.kmss.hr.ratify.model.HrRatifySign','com.landray.kmss.hr.ratify.model.HrRatifyChange','com.landray.kmss.hr.ratify.model.HrRatifyRemove')");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List contractList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "contract");
		json.put("count", contractList.get(0));
		array.add(json);

		//调薪合同
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname = 'com.landray.kmss.hr.ratify.model.HrRatifySalary'");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List salaryList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "salary");
		json.put("count", salaryList.get(0));
		array.add(json);

		//其他流程
		hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("count(*)");
		hqlInfo.setWhereBlock(
				"hrRatifyMain.fdSubclassModelname in('com.landray.kmss.hr.ratify.model.HrRatifyFire', 'com.landray.kmss.hr.ratify.model.HrRatifyRetire', 'com.landray.kmss.hr.ratify.model.HrRatifyRehire', 'com.landray.kmss.hr.ratify.model.HrRatifyOther')");
		SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain", hqlInfo);
		List otherList = this.findList(hqlInfo);
		json = new JSONObject();
		json.put("name", "other");
		json.put("count", otherList.get(0));
		array.add(json);

		return array;
	}

	@Override
	public JSONObject getCount(String modelName) throws Exception {
		List<String> list = null;
		if (StringUtil.isNotNull(modelName)) {
			list = ArrayUtil.convertArrayToList(modelName.split(";"));
		}
		JSONObject json = new JSONObject();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		json.put("create", getCountValue(hqlInfo, list, "create"));
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		json.put("approval", getCountValue(hqlInfo, list, "approval"));
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		json.put("approved", getCountValue(hqlInfo, list, "approved"));
		return json;
	}

	private Long getCountValue(HQLInfo hqlInfo, List<String> list, String mydoc)
			throws Exception {
		Long value = null;
		if (list != null && !list.isEmpty()) {
			hqlInfo.setWhereBlock(HQLUtil
					.buildLogicIN("hrRatifyMain.fdSubclassModelname", list));
		}
		if ("create".equals(mydoc)) {
			hqlInfo.setWhereBlock(
					StringUtil.linkString(hqlInfo.getWhereBlock(), " and ",
							"hrRatifyMain.docCreator=:docCreator"));
			hqlInfo.setParameter("docCreator", UserUtil.getUser());
		}
		if ("approval".equals(mydoc)) {
			SysFlowUtil.buildLimitBlockForMyApproval("hrRatifyMain",
					hqlInfo);
		}
		if ("approved".equals(mydoc)) {
			SysFlowUtil.buildLimitBlockForMyApproved("hrRatifyMain",
					hqlInfo);
		}
		List<Long> valueList = findValue(hqlInfo);
		if (!valueList.isEmpty()) {
			value = valueList.get(0);
		}
		return value;
	}
}
