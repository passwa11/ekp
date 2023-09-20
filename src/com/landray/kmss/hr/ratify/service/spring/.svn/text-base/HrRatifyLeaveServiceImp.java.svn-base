package com.landray.kmss.hr.ratify.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveDRForm;
import com.landray.kmss.hr.ratify.model.HrRatifyAgendaConfig;
import com.landray.kmss.hr.ratify.model.HrRatifyLeave;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.hr.ratify.service.IHrRatifyLeaveService;
import com.landray.kmss.hr.ratify.util.HrRatifyTitleUtil;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffRatifyLog;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchFileDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowDiscard;
import com.landray.kmss.sys.workflow.interfaces.Event_SysFlowFinish;
import com.landray.kmss.sys.xform.interfaces.XFormUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HrRatifyLeaveServiceImp extends HrRatifyMainServiceImp
		implements IHrRatifyLeaveService, IArchFileDataService,
		ApplicationContextAware {
	/**
	 * 状态 待离职
	 */
	private static String LEAVE_STATUS_BEGIN="1";
	/**
	 * 完成离职
	 */
	private static String LEAVE_STATUS_OVER="2";
	public ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrRatifyLeave hrRatifyLeave = new HrRatifyLeave();
		hrRatifyLeave.setDocCreateTime(new Date());
		hrRatifyLeave.setDocCreator(UserUtil.getUser());
		hrRatifyLeave.setFdDepartment(UserUtil.getUser().getFdParent());
		String templateId = requestContext.getParameter("i.docTemplate");
		if (com.landray.kmss.util.StringUtil.isNotNull(templateId)) {
			com.landray.kmss.hr.ratify.model.HrRatifyTemplate docTemplate = (com.landray.kmss.hr.ratify.model.HrRatifyTemplate) getHrRatifyTemplateService()
					.findByPrimaryKey(templateId);
			if (docTemplate != null) {
				hrRatifyLeave.setDocTemplate(docTemplate);
				List<HrRatifyTKeyword> keyWordList = docTemplate
						.getDocKeyword();
				List tKeyword = new ArrayList();
				for (HrRatifyTKeyword keyWord : keyWordList) {
					HrRatifyMKeyword mKeyword = new HrRatifyMKeyword();
					mKeyword.setDocKeyword(keyWord.getDocKeyword());
					tKeyword.add(mKeyword);
				}
				hrRatifyLeave.setDocKeyword(tKeyword);
			}
		}
        HrRatifyUtil.initModelFromRequest(hrRatifyLeave, requestContext);
		if (hrRatifyLeave.getDocTemplate() != null) {
			hrRatifyLeave.setExtendFilePath(
					XFormUtil.getFileName(hrRatifyLeave.getDocTemplate(),
							hrRatifyLeave.getDocTemplate().getFdTempKey()));
			if (Boolean.FALSE.equals(
					hrRatifyLeave.getDocTemplate().getDocUseXform())) {
				hrRatifyLeave.setDocXform(
						hrRatifyLeave.getDocTemplate().getDocXform());
			}
			hrRatifyLeave.setDocUseXform(
					hrRatifyLeave.getDocTemplate().getDocUseXform());
		}
        return hrRatifyLeave;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrRatifyLeave hrRatifyLeave = (HrRatifyLeave) model;
		if (hrRatifyLeave.getDocTemplate() != null) {
			dispatchCoreService.initFormSetting(form,
					hrRatifyLeave.getDocTemplate().getFdTempKey(),
					hrRatifyLeave.getDocTemplate(),
					hrRatifyLeave.getDocTemplate().getFdTempKey(),
					requestContext);
		}
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	/*private IKmArchivesFileTemplateService kmArchivesFileTemplateService;

	public void setKmArchivesFileTemplateService(
			IKmArchivesFileTemplateService kmArchivesFileTemplateService) {
		this.kmArchivesFileTemplateService = kmArchivesFileTemplateService;
	}*/

	@Override
	public void setDocNumber(HrRatifyMain mainModel) throws Exception {
		HrRatifyLeave hrRatifyLeave = (HrRatifyLeave) mainModel;
		if (!"10".equals(mainModel.getDocStatus())) {
			String docNumber = getSysNumberFlowService()
					.generateFlowNumber(hrRatifyLeave);
			hrRatifyLeave.setDocNumber(docNumber);
		}
	}
 
	private void updatePerson(HrRatifyLeave leave) throws Exception {
		//配置中开启了设置为无效时 把人员置为无效
		HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
		if("true".equals(config.getFdFalseSysOrg())) {
			SysOrgPerson fdLeaveStaff = leave.getFdLeaveStaff();
			String id = fdLeaveStaff.getFdId();
			getSysOrgPersonService().updateInvalidated(id, new RequestContext());
		}
	}

	private void updateStaff(HrRatifyLeave leave) throws Exception {
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(leave.getFdLeaveStaff().getFdId());
		hrStaffPersonInfo.setFdStatus("leave");
		hrStaffPersonInfo.setFdLeaveStatus(LEAVE_STATUS_OVER);
		hrStaffPersonInfo.setFdLeaveTime(leave.getFdLeaveRealDate());
		hrStaffPersonInfo.setFdLeaveReason(leave.getFdLeaveReason());
		hrStaffPersonInfo.setFdLeaveRemark(leave.getFdLeaveRemark());
		hrStaffPersonInfo.setFdNextCompany(leave.getFdNextCompany());
		hrStaffPersonInfo.setFdLeaveApplyDate(leave.getFdLeaveDate());
		hrStaffPersonInfo.setFdActualLeaveTime(leave.getFdLeaveRealDate());
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
		flushHibernateSession();
		String fdDetails = "通过员工离职流程，修改了“" + leave.getFdLeaveStaff().getFdName()
				+ "”的员工信息：员工状态、离职日期。";
		addUpdateLog(fdDetails, hrStaffPersonInfo);

	}

	private void savePersonSchedulerJob(RequestContext requestContext,
			HrRatifyLeave leave) throws Exception {
		Date leaveDate = leave.getFdLeaveRealDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(leaveDate);
		String cron = HrRatifyUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("personSchedulerJob");
			quartzContext.setQuartzJobService("hrRatifyLeaveService");
			quartzContext.setQuartzKey("hrRatifyLeaveQuartzJob");
			quartzContext.setQuartzParameter(leave.getFdId().toString());
			quartzContext.setQuartzSubject(leave.getDocSubject());
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			getSysQuartzCoreService().saveScheduler(quartzContext, leave);
		}
	}

	@Override
	public void personSchedulerJob(SysQuartzJobContext context)
			throws Exception {
		HrRatifyLeave leave = (HrRatifyLeave) findByPrimaryKey(
				context.getParameter(), HrRatifyLeave.class, true);
		//修改人事档案的离职状态为完成
		this.updateStaff(leave);
		//修改系统组织架构人员状态为失效
		this.updatePerson(leave);
		//修改人员离职流程状态为 完成
		leave.setFdLeaveStatus(LEAVE_STATUS_OVER);
		update(leave);
		getSysQuartzCoreService().delete(leave, "hrRatifyLeaveQuartzJob");
	}


	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null) {
			return;
		}
		Object obj = event.getSource();
		if (!(obj instanceof HrRatifyLeave)) {
			return;
		}
		if (event instanceof Event_SysFlowFinish) {
			try {
				HrRatifyLeave leave = (HrRatifyLeave) obj;
				Date fdLeaveRealDate = leave.getFdLeaveRealDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fdLeaveRealDate);
				//时间加上23点59.59秒的时间
				Integer twelve = 24 * 60 * 60 * 1000 - 1000;
				calendar.add(Calendar.MILLISECOND,twelve);
				leave.setFdLeaveRealDate(calendar.getTime());
				Date now = new Date();
				HrRatifyAgendaConfig config = new HrRatifyAgendaConfig();
				if (now.getTime() >= calendar.getTimeInMillis()) {
					if ("true".equals(config.getFdFalseHrStaff())) {
						this.updateStaff(leave);
						this.updatePerson(leave);
						leave.setFdLeaveStatus(LEAVE_STATUS_OVER);
					}
				} else {
					if ("true".equals(config.getFdFalseHrStaff())) {
						//将人事档案员工状态置为“待离职”
						updateStaffByWaitLeave(leave);
						leave.setFdLeaveStatus(LEAVE_STATUS_BEGIN);
						savePersonSchedulerJob(new RequestContext(), leave);
					}
				}
				leave.setDocPublishTime(now);
				super.update(leave);
				feedbackNotify(leave);
				addLog(leave);
			} catch (Exception e1) {
				throw new KmssRuntimeException(e1);
			}
		} else if (event instanceof Event_SysFlowDiscard) {
			try {
				HrRatifyLeave leave = (HrRatifyLeave) obj;
				leave.setDocPublishTime(new Date());
				super.update(leave);
			} catch (Exception e2) {
				throw new KmssRuntimeException(e2);
			}
		}
	}

	/**
	 * 流程发起离职流程，还未到时间执行离职时将人事档案中的离职状态设置为空
	 * 主要场景是，先手工办理了离职，然后又走流程，这时候，如果不设置为空，待离职人员列表可能会重复
	 * @param leave
	 * @throws Exception
	 */
	private void updateStaffByWaitLeave(HrRatifyLeave leave) throws Exception {
		String personId=leave.getFdLeaveStaff().getFdId();
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(personId);
		hrStaffPersonInfo.setFdLeaveStatus(null);
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
	}

	@Override
	public void addLog(HrRatifyMain mainModel) throws Exception {
		HrRatifyLeave leave = (HrRatifyLeave) mainModel;
		HrStaffRatifyLog log = new HrStaffRatifyLog();
		log.setFdRatifyType("leave");
		log.setFdRatifyDept(leave.getFdLeaveDept());
		log.setFdRatifyDate(leave.getFdLeaveRealDate());
		JSONObject json = new JSONObject();
		json.put("docSubject", leave.getDocSubject());
		json.put("url", HrRatifyUtil.getUrl(leave));
		log.setFdRatifyProcess(json.toString());
		getHrStaffRatifyLogService().add(log);
	}

	@Override
	public void deleteEntity(HrRatifyMain mainModel) throws Exception {
		HrRatifyLeave leaveModel = (HrRatifyLeave) findByPrimaryKey(
				mainModel.getFdId());
		super.deleteEntity(leaveModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrRatifyLeave leaveModel = (HrRatifyLeave) modelObj; 
		if (StringUtil.isNull(leaveModel.getDocNumber())) {
			setDocNumber(leaveModel);
		}
		HrRatifyTitleUtil.genTitle(leaveModel);
		super.update(leaveModel);
	}

	/*历史归档机制*/
	/*@Override
	public void addFileMainDoc(HttpServletRequest request, String fdId,
			KmArchivesFileTemplate fileTemplate) throws Exception {
		HrRatifyLeave mainModel = (HrRatifyLeave) findByPrimaryKey(fdId);
		// 只有结束和已反馈的文档可以归档
		if (!"30".equals(mainModel.getDocStatus())
				&& !"31".equals(mainModel.getDocStatus())) {
			throw new KmssRuntimeException(
					new KmssMessage("km-archives:file.notsupport"));
		}
		HrRatifyTemplate tempalte = mainModel.getDocTemplate();
		// 模块支持归档
		if (KmArchivesUtil.isStartFile("hr/ratify")) {
			KmArchivesFileTemplate fTemplate = kmArchivesFileTemplateService
					.getFileTemplate(tempalte, null);
			// 有归档模板
			if (fTemplate != null) {
				addArchives(request, mainModel, fTemplate);
			} else if ("1".equals(request.getParameter("userSetting"))) { // 支持用户级配置
				addArchives(request, mainModel, fileTemplate);
			}
		}
		mainModel.setFdIsFiling(true);
		if (UserOperHelper.allowLogOper("fileDoc", "*")
				|| UserOperHelper.allowLogOper("fileDocAll", "*")) {
			UserOperContentHelper.putUpdate(mainModel);
		}
		super.update(mainModel);
	}

	private void addArchives(HttpServletRequest request,
			HrRatifyLeave mainModel, KmArchivesFileTemplate fileTemplate)
			throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		kmArchivesFileTemplateService.setFileField(kmArchivesMain, fileTemplate,
				mainModel);
		// 归档页面URL(若为多表单，暂时归档默认表单)
		int saveApproval = fileTemplate.getFdSaveApproval() != null
				&& fileTemplate.getFdSaveApproval() ? 1 : 0;
		String url = "/hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=printFileDoc&fdId="
				+ mainModel.getFdId() + "&s_xform=default&saveApproval="
				+ saveApproval;
		String fileName = mainModel.getDocSubject() + ".html";
		kmArchivesFileTemplateService.setFilePrintPage(kmArchivesMain, request,
				url, fileName);
		// 找到与主文档绑定的所有附件
		kmArchivesFileTemplateService.setFileAttachement(kmArchivesMain,
				mainModel);
		kmArchivesFileTemplateService.addFileArchives(kmArchivesMain);
		if (UserOperHelper.allowLogOper("fileDoc", "*")) {
			UserOperContentHelper.putAdd(kmArchivesMain)
					.putSimple("docTemplate", fileTemplate);
		}
	}*/

	@Override
	public void addArchFileModel(HttpServletRequest request, String fdId) throws Exception {
		if("true".equals(SysArchivesConfig.newInstance().getSysArchEnabled())&& SysArchivesUtil.isStartFile("hr/ratify")){
			HrRatifyLeave mainModel= (HrRatifyLeave) findByPrimaryKey(fdId.split(",")[0]);
			// 只有结束和已反馈的文档可以归档
			if (!"30".equals(mainModel.getDocStatus())
					&& !"31".equals(mainModel.getDocStatus())) {
				throw new KmssRuntimeException(
						new KmssMessage("sys-archives:file.notsupport"));
			}
			HrRatifyTemplate template = mainModel.getDocTemplate();
			ISysArchivesFileTemplateService sysArchivesFileTemplateService= (ISysArchivesFileTemplateService)SpringBeanUtil.getBean("sysArchivesFileTemplateService");
			SysArchivesFileTemplate fileTemp = sysArchivesFileTemplateService.getFileTemplate(template,null);
			if(fileTemp!=null){
				SysArchivesParamModel paramModel = new SysArchivesParamModel();
				paramModel.setAuto("0");
				paramModel.setFileName(mainModel.getDocSubject()+".html");
				// 归档页面URL(若为多表单，暂时归档默认表单)
				int saveApproval = fileTemp.getFdSaveApproval() != null
						&& fileTemp.getFdSaveApproval() ? 1 : 0;
				//流程自动归档
				String fdModelId = mainModel.getFdId();
				String url = "/hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=printFileDoc&fdId="
						+ mainModel.getFdId() + "&s_xform=default&saveApproval="
						+ saveApproval;
				paramModel.setUrl(url);
				sysArchivesFileTemplateService.addArchFileModel(request,mainModel,paramModel,fileTemp);

				mainModel.setFdIsFiling(true);
				super.update(mainModel);
			}else{
				return;
			}

			if (UserOperHelper.allowLogOper("fileDoc", "*")) {
				UserOperContentHelper.putAdd(mainModel)
						.putSimple("docTemplate", fileTemp);
			}
		}
	}


	@Override
	public Page getLeaveManagePage(HttpServletRequest request, HQLInfo hqlInfo)
			throws Exception {
		Page page = Page.getEmptyPage();
		String fdLeaveStatus = request.getParameter("fdLeaveStatus");
		String whereBolck = " 1=1 ";
		if ("1".equals(fdLeaveStatus)) {
			whereBolck = StringUtil.linkString(whereBolck, " and ",
					"(hrRatifyLeave.fdLeaveStatus=:fdLeaveStatus or hrRatifyLeave.fdLeaveStatus is null)");
		} else {
			whereBolck = StringUtil.linkString(whereBolck, "and ",
					"hrRatifyLeave.fdLeaveStatus=:fdLeaveStatus");
		}
		hqlInfo.setWhereBlock(whereBolck);
		hqlInfo.setParameter("fdLeaveStatus", fdLeaveStatus);

		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
		}
		String whereBlock = hqlInfo.getWhereBlock();
		Map<String, String> map = null;
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		CriteriaValue cv = new CriteriaValue(request);
		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDepts = cv.polls("_fdDept");
		String[] fdLeaveTime = cv.polls("fdLeaveTime"); 
		 
		if (StringUtil.isNotNull(_fdKey)) { 
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(hrRatifyLeave.fdLeaveStaff.fdName like :_fdKey or hrRatifyLeave.fdLeaveStaff.fdMobileNo like :_fdKey)");
			hqlInfo.setParameter("_fdKey", "%" + _fdKey + "%");
		}
		//离职日期
		if (fdLeaveTime != null && fdLeaveTime.length > 0) {
			String beginDateStr=fdLeaveTime[0];
			String endDateStr=fdLeaveTime[1];
			if(StringUtil.isNotNull(beginDateStr)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"hrRatifyLeave.fdLeaveRealDate >=:beginDate ");
				hqlInfo.setParameter("beginDate",DateUtil.convertStringToDate(beginDateStr));
			}
			if(StringUtil.isNotNull(endDateStr)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"hrRatifyLeave.fdLeaveRealDate <=:endDate ");
				hqlInfo.setParameter("endDate", DateUtil.convertStringToDate(endDateStr));
			}  
		}
		// 部门
		if (_fdDepts != null && _fdDepts.length > 0) { 
			StringBuffer sb2 = new StringBuffer("(");
			for (int i = 0; i < _fdDepts.length - 1; i++) { 
				sb2.append("hrRatifyLeave.fdLeaveDept.fdHierarchyId like:fdDept"
						+ i + " or ");
				hqlInfo.setParameter("fdDept" + i, "%" + _fdDepts[i] + "%");
			} 
			sb2.append("hrRatifyLeave.fdLeaveDept.fdHierarchyId like:fdDept"
					+ (_fdDepts.length - 1) + ")");
			hqlInfo.setParameter("fdDept" + (_fdDepts.length - 1),
					"%" + _fdDepts[_fdDepts.length - 1] + "%");
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					sb2.toString());
		}
		// 离职原因
		String fdLeaveReason = cv.poll("fdLeaveReason");
		if (StringUtil.isNotNull(fdLeaveReason)) { 
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"hrRatifyLeave.fdLeaveReason =:fdLeaveReason");
			hqlInfo.setParameter("fdLeaveReason", fdLeaveReason);
		} 
		whereBlock = StringUtil.linkString(whereBlock, " and ","hrRatifyLeave.docStatus='30' "); 
		hqlInfo.setWhereBlock(whereBlock);
		//查询离职的人员
		List<HrRatifyLeave> list = findList(hqlInfo);
		int total = list.size();
		if (total > 0) {
			for (HrRatifyLeave leave : list) { 
				map = new HashMap<String, String>();
				map.put("fdId", leave.getFdId());
				map.put("modelName", "HrRatifyLeave");
				SysOrgPerson staff = leave.getFdLeaveStaff();
				if (staff != null) {

					map.put("fdOrgPersonId", staff.getFdId());
					map.put("fdStaffId", staff.getFdId());
					map.put("fdStaffName", staff.getFdName());
					map.put("fdStaffNo", staff.getFdNo());
					SysOrgElement fdLeaveDept = leave.getFdLeaveDept();
					map.put("fdDeptName",
							fdLeaveDept != null ? fdLeaveDept.getFdName() : "");
					map.put("fdPosts",
							ArrayUtil.joinProperty(staff.getFdPosts(), "fdName",
									";")[0]);
					HrStaffPersonInfo personInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
							.findByPrimaryKey(staff.getFdId());
					if (personInfo != null) {
						//人事档案有此人，使用人事档案中的工号
						map.put("fdStaffNo", personInfo.getFdStaffNo());
						map.put("fdStaffStatus", personInfo.getFdStatus());
						map.put("fdCompanyAge", personInfo.getFdWorkingYears());
						// 离职去向
						map.put("fdNextCompany", personInfo.getFdNextCompany());
						
						//如果当前状态是离职，人员状态不是离职，解聘，退休的人员不显示
						if("2".equals(leave.getFdLeaveStatus())) {
							if(!("dismissal".equals(personInfo.getFdStatus())
									|| "leave".equals(personInfo.getFdStatus())
									||"retire".equals(personInfo.getFdStatus())
									)) {
								continue;
							}
						}
					}
					String tempFdLeaveStatus = StringUtil
							.isNull(leave.getFdLeaveStatus()) ? "1"
									: leave.getFdLeaveStatus();
					map.put("fdLeaveStatus", tempFdLeaveStatus);
					map.put("fdEntryTime", DateUtil.convertDateToString(
							leave.getFdLeaveEnterDate(), DateUtil.TYPE_DATE,
							null));
					map.put("fdLeaveDate", DateUtil.convertDateToString(
							leave.getFdLeaveDate(), DateUtil.TYPE_DATE, null));
					map.put("fdLeaveType", leave.getFdLeaveReason());
					// 实际离职日期
					map.put("fdLeaveRealDate", DateUtil.convertDateToString(
							leave.getFdLeaveRealDate(), DateUtil.TYPE_DATE,
							null));
					// 流程编号
					map.put("docNumber", leave.getDocNumber());
				}
				list2.add(map);
			}
		}
		// 如果是待离职的列表，这里增加人员中待离职的人员显示
		if(LEAVE_STATUS_BEGIN.equals(fdLeaveStatus)) {
			appendHrPerson(request, list2);
		}
		/*内存分页*/
		page.setRowsize(hqlInfo.getRowSize());
		page.setPageno(hqlInfo.getPageNo());
		page.setTotalrows(list2.size());
		page.setOrderby(hqlInfo.getOrderBy());
		page.excecute();
		page.setList(list2);
		return page;
	}

	/**
	 * 手工办理的离职操作人员加入到待离职列表
	 * 只在待离职列表时，去额外增加
	 * @param request
	 * @param list2
	 * @throws Exception
	 */
	private void appendHrPerson(HttpServletRequest request,List<Map<String, String>> list2 ) throws Exception {
		/*查询人事档案中待离职的人员列表*/
		HQLInfo hrHql = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		//人员状态是1，并且实际离职日期比当前日期大
		whereBlock.append("hrStaffPersonInfo.fdLeaveStatus =:fdLeaveStatus");
		whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hrHql);
		hrHql.setParameter("fdLeaveStatus", LEAVE_STATUS_BEGIN);
		CriteriaValue cv = new CriteriaValue(request);
		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		String[] _fdDepts = cv.polls("_fdDept");

		if (StringUtil.isNotNull(_fdKey)) {
			whereBlock.append(StringUtil.linkString(null, ""," and (hrStaffPersonInfo.fdOrgPerson.fdName like :_fdKey or hrStaffPersonInfo.fdOrgPerson.fdMobileNo like :_fdKey)"));
			hrHql.setParameter("_fdKey", "%" + _fdKey + "%");
		}

		// 部门
		if (_fdDepts != null && _fdDepts.length > 0) {
			StringBuffer sb2 = new StringBuffer(" and (");
			for (int i = 0; i < _fdDepts.length - 1; i++) {
				sb2.append("hrStaffPersonInfo.fdHierarchyId like:fdDept" + i + " or ");
				hrHql.setParameter("fdDept" + i, "%" + _fdDepts[i] + "%");
			}
			sb2.append("hrStaffPersonInfo.fdHierarchyId like:fdDept" + (_fdDepts.length - 1) + ")");
			hrHql.setParameter("fdDept" + (_fdDepts.length - 1), "%" + _fdDepts[_fdDepts.length - 1] + "%");
			whereBlock.append(StringUtil.linkString(null, "", sb2.toString()));
		}
		hrHql.setWhereBlock(whereBlock.toString());
		List<HrStaffPersonInfo> personList =getHrStaffPersonInfoService().findList(hrHql);
		for (HrStaffPersonInfo staff : personList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("fdId", staff.getFdId());
			map.put("modelName", "HrRatifyLeave");
			map.put("fdOrgPersonId", staff.getFdId());
			map.put("fdStaffId", staff.getFdId());
			map.put("fdStaffName", staff.getFdName());
			map.put("fdStaffNo", staff.getFdStaffNo());
			SysOrgElement fdLeaveDept = staff.getFdOrgParent();
			map.put("fdDeptName",fdLeaveDept != null ? fdLeaveDept.getFdName() : "");
			map.put("fdPosts", ArrayUtil.joinProperty(staff.getFdPosts(), "fdName",";")[0]);
			map.put("fdStaffStatus", staff.getFdStatus());
			map.put("fdCompanyAge", staff.getFdWorkingYears());
			// 离职去向
			map.put("fdNextCompany", staff.getFdNextCompany());
			map.put("fdLeaveStatus", staff.getFdLeaveStatus());
			map.put("fdEntryTime", DateUtil.convertDateToString(
					staff.getFdEntryTime(), DateUtil.TYPE_DATE,null));

			map.put("fdLeaveDate", DateUtil.convertDateToString(
					staff.getFdLeavePlanDate(), DateUtil.TYPE_DATE, null));
			map.put("fdLeaveType", staff.getFdLeaveReason());
			// 实际离职日期
			map.put("fdLeaveRealDate", DateUtil.convertDateToString(
					staff.getFdActualLeaveTime(), DateUtil.TYPE_DATE,
					null));
			map.put("notProcess",Boolean.TRUE.toString());
			list2.add(map);
		}
	}

	/**
	 * 手工办理离职
	 * 人事档案的人员表记录相关离职信息
	 * 人事流程离职人员表也记录对应的离职信息（主要是再人事流程中的待离职中显示）
	 * 实际离职日期在当前日期之后，则产生定时任务执行，实际离职时间在当前之前。直接生效
	 * @param drForm
	 * @throws Exception
	 */
	@Override
	public void saveLeave(HrRatifyLeaveDRForm drForm) throws Exception {
		Date now = new Date();
		Date fdLeaveApplyDate = DateUtil.convertStringToDate(
				drForm.getFdLeaveApplyDate(), DateUtil.TYPE_DATE, null);
		Date fdLeavePlanDate = DateUtil.convertStringToDate(
				drForm.getFdLeavePlanDate(), DateUtil.TYPE_DATE, null);
		Date fdLeaveRealDate = DateUtil.convertStringToDate(
				drForm.getFdLeaveRealDate(), DateUtil.TYPE_DATE, null);
		//停薪日期、目前没有跟薪资管理打通
		Date fdLeaveSalaryEndDate = DateUtil.convertStringToDate(
				drForm.getFdLeaveSalaryEndDate(), DateUtil.TYPE_DATE, null);
		String fdUserId = drForm.getFdUserId();
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByOrgPersonId(fdUserId);
		hrStaffPersonInfo.setFdLeaveStatus(LEAVE_STATUS_BEGIN);
		hrStaffPersonInfo.setFdLeaveApplyDate(fdLeaveApplyDate);
		hrStaffPersonInfo.setFdLeavePlanDate(fdLeavePlanDate);
		hrStaffPersonInfo.setFdLeaveTime(fdLeaveRealDate);
		hrStaffPersonInfo.setFdLeaveSalaryEndDate(fdLeaveSalaryEndDate);
		hrStaffPersonInfo.setFdLeaveReason(drForm.getFdLeaveReason());
		hrStaffPersonInfo.setFdLeaveRemark(drForm.getFdLeaveRemark());
		hrStaffPersonInfo.setFdNextCompany(drForm.getFdNextCompany());
		hrStaffPersonInfo.setFdActualLeaveTime(fdLeaveRealDate);
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
		String fdRatifyLeaveId = drForm.getFdRatifyLeaveId();

		if(fdLeaveRealDate!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fdLeaveRealDate);
			//时间加上23点59.59秒的时间
			Integer twelve = 24 * 60 * 60 * 1000 - 1000;
			calendar.add(Calendar.MILLISECOND,twelve);
			if (now.getTime() >= calendar.getTimeInMillis()) {
				updateStaffLeave(hrStaffPersonInfo);
				updateLeaveStatus(fdRatifyLeaveId);
			} else {
				String cron = HrRatifyUtil.getCronExpression(calendar);
				if (StringUtil.isNotNull(cron)) {
					SysQuartzModelContext quartzContext = new SysQuartzModelContext();
					quartzContext.setQuartzJobMethod("updateStaffLeaveJob");
					quartzContext.setQuartzJobService("hrRatifyLeaveService");
					quartzContext.setQuartzKey("hrRatifyLeaveDRQuartzJob");
					quartzContext
							.setQuartzParameter(fdUserId + ";" + fdRatifyLeaveId);
					quartzContext.setQuartzSubject(drForm.getFdUserName() + "办理离职");
					quartzContext.setQuartzRequired(true);
					quartzContext.setQuartzCronExpression(cron);
					getSysQuartzCoreService().saveScheduler(quartzContext,
							hrStaffPersonInfo);
				}
			}
		}
	}

	private void updateLeaveStatus(String fdRatifyLeaveId) throws Exception {
		if (StringUtil.isNotNull(fdRatifyLeaveId)) {
			HrRatifyLeave hrRatifyLeave = (HrRatifyLeave) findByPrimaryKey(
					fdRatifyLeaveId);
			hrRatifyLeave.setFdLeaveStatus("2");
			update(hrRatifyLeave);
		}
	}

	private void updateStaffLeave(HrStaffPersonInfo hrStaffPersonInfo)
			throws Exception {
		hrStaffPersonInfo.setFdStatus("leave");
		hrStaffPersonInfo.setFdLeaveStatus("2");
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
		String fdDetails = "通过员工办理离职，修改了“" + hrStaffPersonInfo.getFdName()
				+ "”的员工信息：员工状态、离职日期。";
		addUpdateLog(fdDetails, hrStaffPersonInfo);
		getSysOrgPersonService().updateInvalidated(hrStaffPersonInfo.getFdId(),
				new RequestContext());
	}

	@Override
	public void updateStaffLeaveJob(SysQuartzJobContext context)
			throws Exception {
		String[] ids = context.getParameter().split(";");
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(ids[0]);
		Date fdLeaveTime = hrStaffPersonInfo.getFdLeaveTime();

		if (fdLeaveTime !=null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fdLeaveTime);
			//时间加上23点59.59秒的时间
			Integer twelve = 24 * 60 * 60 * 1000 - 1000;
			calendar.add(Calendar.MILLISECOND,twelve);
			if(calendar.getTimeInMillis() >= new Date().getTime()) {
				updateStaffLeave(hrStaffPersonInfo);
				if (ids.length > 1 && StringUtil.isNotNull(ids[1])) {
					updateLeaveStatus(ids[1]);
				}
				getSysQuartzCoreService().delete(hrStaffPersonInfo,
						"hrRatifyLeaveDRQuartzJob");
			}
		}
	}

	@Override
	public void updateAbandonLeave(String fdStaffId, String fdStatus)
			throws Exception {
		HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) getHrStaffPersonInfoService()
				.findByPrimaryKey(fdStaffId);
		SysOrgPerson fdOrgPerson = (SysOrgPerson) getSysOrgPersonService()
				.findByPrimaryKey(fdStaffId);
		if (fdOrgPerson != null
				&& !fdOrgPerson.getFdIsAvailable().booleanValue()) {
			fdOrgPerson.setFdIsAvailable(new Boolean(true));
			getSysOrgPersonService().update(fdOrgPerson);
			if (hrStaffPersonInfo.getFdOrgPerson() == null) {
				hrStaffPersonInfo.setFdOrgPerson(fdOrgPerson);
			}
		}
		hrStaffPersonInfo.setFdStatus(fdStatus);
		hrStaffPersonInfo.setFdLeaveStatus(null);
		getHrStaffPersonInfoService().update(hrStaffPersonInfo);
	}
	/**
	 * 根据人员修改人员离职信息数据
	 * 如果是待离职的，并且离职时间小于当前，则改成已离职
	 * @param personId
	 * @throws Exception
	 */
	@Override
	public void updateStaffLeavelStatus(HrRatifyLeave leave) throws Exception { 
		if(leave !=null && ("1".equals(leave.getFdLeaveStatus()) || StringUtil.isNull(leave.getFdLeaveStatus()))){
			Date now = new Date(); 
			if (now.getTime() >= leave.getFdLeaveRealDate().getTime()) {
				//当前时间大于离职日期 则把待离职改成离职
				leave.setFdLeaveStatus("2");
				//根据配置修改人员设置为无效
				this.updatePerson(leave);
				//修改人员的离职信息
				this.updateStaff(leave);
				super.update(leave);
			}
		}
	}
	/**
	 * 获取用户离职的信息
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	private HrRatifyLeave getPersonLeave(String personId) throws Exception {
		HQLInfo hql=new HQLInfo();
		String where="hrRatifyLeave.fdLeaveStaff.fdId=:personId";
		hql.setWhereBlock(where);
		hql.setParameter("personId", personId);
		List<HrRatifyLeave> list =this.findList(hql);
		if(list !=null) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取用户实际离职日期小于等于当前，并且状态是1或者空的人员
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HrRatifyLeave> getLevelLists() throws Exception {
		HQLInfo hql=new HQLInfo();
		String whereBolck="hrRatifyLeave.fdLeaveRealDate <=:now"; 
		hql.setParameter("now", new Date()); 
		whereBolck = StringUtil.linkString(whereBolck, " and ",
					"(hrRatifyLeave.fdLeaveStatus=:fdLeaveStatus or hrRatifyLeave.fdLeaveStatus is null)");
		hql.setParameter("fdLeaveStatus", "1"); 
		whereBolck = StringUtil.linkString(whereBolck, " and ","hrRatifyLeave.docStatus='30' "); 
		
		hql.setWhereBlock(whereBolck);
		return this.findList(hql); 
	} 
}
