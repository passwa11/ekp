package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttachmentService;
import com.landray.kmss.sys.attend.forms.SysAttendMainForm;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendConfig;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.model.SysAttendSignLog;
import com.landray.kmss.sys.attend.model.SysAttendSignPatch;
import com.landray.kmss.sys.attend.model.SysAttendSynDing;
import com.landray.kmss.sys.attend.service.ISysAttendBusinessService;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendConfigService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendSignLogService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingService;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到表业务接口实现
 * 
 * @author
 * @version 1.0 2017-05-24
 */
/**
 * @author linxiuxian
 *
 */
public class SysAttendMainServiceImp extends BaseServiceImp
		implements ISysAttendMainService,IEventMulticasterAware, ApplicationListener<Event_Common> {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainServiceImp.class);
	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendBusinessService sysAttendBusinessService;

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ISysAttendMainExcService sysAttendMainExcService;

	private ISysAttendStatJobService sysAttendStatJobService;
	
	private IEventMulticaster multicaster;


	private ISysAttendSignLogService sysAttendSignLogService;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	/**
	 * 转换from到model
	 * 重写转换 考勤组 和签到组的存储对象
	 * @param model
	 * @param form
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
										 RequestContext requestContext) throws Exception {
		IBaseModel baseModel=super.convertFormToModel(form,model,requestContext);
		SysAttendMainForm mainForm= (SysAttendMainForm) form;
		SysAttendMain main=(SysAttendMain)baseModel;
		String categoryId = mainForm.getFdCategoryId();
		SysAttendHisCategory hisCategory = CategoryUtil.getHisCategoryById(categoryId);
		if(hisCategory ==null){
			SysAttendCategory category = (SysAttendCategory) sysAttendCategoryService.findByPrimaryKey(categoryId);
			main.setFdHisCategory(null);
			main.setFdCategory(category);
		} else {
			main.setFdHisCategory(hisCategory);
			main.setFdCategory(null);
		}
		return baseModel;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String id=this.add(modelObj, DbUtils.getDbTime());
		SysAttendMain sysAttendMain =(SysAttendMain)modelObj;
		SysAttendCategory category=null;
		if(sysAttendMain.getFdCategory() ==null){
			category= CategoryUtil.getFdCategoryInfo(sysAttendMain);
			if(category !=null) {
				sysAttendMain.setFdHisCategory(CategoryUtil.getHisCategoryById(category.getFdId()));
			}
		}
		//考勤组才有的操作，签到组不执行
		if(category!=null && CategoryUtil.CATEGORY_FD_TYPE_TRUE.equals(category.getFdType())) {
			if(Boolean.TRUE.equals(category.getFdNotifyAttend()) ) {
				sendNotify(sysAttendMain);
			}
		}
		return id;
	}
	
	private void sendNotify(SysAttendMain sysAttendMain)
			throws Exception {
		if (sysAttendMain == null) {
			logger.debug("发送考勤打卡结果提示:没有考勤记录");
			return;
		}
		try{
			NotifyContext notifyContext = sysNotifyMainCoreService.getContext(null);
			// 设置通知方式
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			List<SysOrgElement> targets = new ArrayList<>();
			targets.add(sysAttendMain.getDocCreator());
			// 设置发布通知人
			notifyContext
					.setNotifyTarget(targets);
			String signTimeTxt = DateUtil.convertDateToString(sysAttendMain.getDocCreateTime(), "MM/dd HH:mm");
			if(sysAttendMain.getFdWorkType().intValue()==0) {
				signTimeTxt+=ResourceUtil.getString("sysAttendCategory.fdNotifyAttend.onTime.tips", "sys-attend");
			}else {
				signTimeTxt+=ResourceUtil.getString("sysAttendCategory.fdNotifyAttend.offTime.tips", "sys-attend");
			}
			notifyContext.setSubject(signTimeTxt);
			notifyContext.setContent(signTimeTxt);
			notifyContext.setLink(
					"/sys/attend/sys_attend_main/sysAttendMain_calendar_toward.jsp?categoryId="
							+ sysAttendMain.getFdHisCategory().getFdId() + "&fdId="
							+ sysAttendMain.getFdId());

			sysNotifyMainCoreService.sendNotify(sysAttendMain,
					notifyContext,
					null);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String add(IBaseModel modelObj, Date signTime) throws Exception {
		SysAttendMain main = (SysAttendMain) modelObj;
		main.setDocCreateTime(signTime);
		main.setDocCreator(UserUtil.getUser());
		main.setDocCreatorHId(UserUtil.getUser().getFdHierarchyId());
		if(main.getDocAlterTime() ==null){
			//记录产生的时间
			main.setDocAlterTime(new Date());
		}
		// 发送签到结果通知
		sendCustomResultNotify(main);
		// 若是出差/请假/外出,则只更新数据
		String fdStatus = main.getFdStatus() != null
				? main.getFdStatus().toString() : "";
		if (AttendUtil.isAttendBuss(fdStatus)) {
			if (StringUtil.isNotNull(main.getFdId())) {
				SysAttendMain oldMain = (SysAttendMain) this
						.findByPrimaryKey(main.getFdId());
				if (oldMain != null && AttendUtil
						.isAttendBuss(oldMain.getFdStatus().toString())) {
					return updateAttendMainBuss(main, oldMain);
				}
			}
		}
		// 外勤打卡校验
		validateAttendOutside(main);
		String id = super.add(modelObj);
		this.getBaseDao().getHibernateSession().flush();
		return id;
	}

	private ISysAttachmentService sysAttachmentService;

	protected ISysAttachmentService getSysAttachmentService() {
		if (sysAttachmentService == null) {
			sysAttachmentService = (ISysAttachmentService) SpringBeanUtil.getBean("sysAttachmentService");
		}
		return sysAttachmentService;
	}

	private ISysAttendSynDingService sysAttendSynDingService;

	public ISysAttendSynDingService getSysAttendSynDingService() {
		if(sysAttendSynDingService ==null){
			sysAttendSynDingService= (ISysAttendSynDingService) SpringBeanUtil.getBean("sysAttendSynDingService");
		}
		return sysAttendSynDingService;
	}

	/**
	 * 监听有效考勤中有附件，增加到原始考勤中
	 * @param event
	 */
	@Override
	public void onApplicationEvent(Event_Common event) {
		if ("addAttendMainAtts".equals(event.getSource().toString())) {
			Map params = ((Event_Common) event).getParams();
			String sysAttendId = params.get("sysAttendId").toString();
			String synDingId = params.get("synDingId").toString();
			try {
				//保存完成以后，再来进行附件的操作
				getBaseDao().flushHibernateSession();
				ISysAttendMainService tempThisService=this;
				multicaster.attatchEvent(
						new EventOfTransactionCommit(StringUtils.EMPTY),
						new IEventCallBack() {
							@Override
							public void execute(ApplicationEvent arg0)
									throws Throwable {
								SysAttendMain sysAttendMain= (SysAttendMain) tempThisService.findByPrimaryKey(sysAttendId);
								Map attachmentMap =getSysAttachmentService().getCloneAttachmentMap(sysAttendMain, SysAttendSynDing.class.getName());
								if(attachmentMap !=null && attachmentMap.size() > 0) {
									SysAttendSynDing synDing = (SysAttendSynDing) getSysAttendSynDingService().findByPrimaryKey(synDingId);
									AutoHashMap autoHashMap = synDing.getAttachmentForms();
									autoHashMap.clear();
									autoHashMap.putAll(attachmentMap);
									getSysAttachmentService().updateCloneAttachmentMap(synDing);
								}
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		setFormValue(form, requestContext);

		return super.add(form, requestContext);
	}
	class SysAttendSignLogTask implements Runnable{

		SysAttendSignLog sysAttendSignlog;

		ISysAttendSignLogService sysAttendSignLogServiceTemp;

		public SysAttendSignLogTask(SysAttendSignLog sysAttendSignlog, ISysAttendSignLogService sysAttendSignLogServiceTemp) {
			this.sysAttendSignlog = sysAttendSignlog;
			this.sysAttendSignLogServiceTemp = sysAttendSignLogServiceTemp;
		}

		@Override
		public void run() {
			try {
				TransactionUtils.doInNewTransaction(new TransactionUtils.TransactionAction() {
					@Override
					public void doAction() {
						try {
							sysAttendSignLogServiceTemp.add(sysAttendSignlog);
						}catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
			}catch (Exception e){
				logger.error("记录打卡信息异常");
				e.printStackTrace();
			}
		}
	}
	/**
	 * 保存用户打卡的记录
	 * 只保存考勤签到
	 * @param main
	 * @throws Exception
	 */
	@Override
	public void addSignLog(SysAttendMainForm main) throws Exception {
		SysAttendSignLog sysAttendSignlog=new SysAttendSignLog();
		Date date=DbUtils.getDbTime();
		//所属考勤日期
		if( main.getFdBaseWorkTime() !=null) {
			sysAttendSignlog.setFdBaseDate(AttendUtil.getDate(main.getFdBaseWorkTime(), 0));
		} else {
			sysAttendSignlog.setFdBaseDate(AttendUtil.getDate(date, 0));
		}
		//因为异步的，这个时间跟统计的实际打卡时间可能存在差异，但是差异很小
		sysAttendSignlog.setDocCreateTime(date);
		sysAttendSignlog.setDocCreator(UserUtil.getUser());
		sysAttendSignlog.setFdAddress(main.getFdLocation());
		sysAttendSignlog.setFdWifiName(main.getFdWifiName());
		sysAttendSignlog.setFdIsAvailable(Boolean.TRUE);
		//打卡类型
		if(StringUtil.isNotNull(main.getFdWifiName())){
			sysAttendSignlog.setFdType("2");
		} else {
			sysAttendSignlog.setFdType("1");
		} 
		//暂存考勤组ID
		sysAttendSignlog.setFdGroupId(main.getFdCategoryId());
		//异步任务
		KMSSCommonThreadUtil.execute(new SysAttendSignLogTask(sysAttendSignlog,sysAttendSignLogService));
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysAttendMain main = (SysAttendMain) modelObj;
		main.setDocCreateTime(DbUtils.getDbTime());
		super.update(modelObj);
	}

	// 是否允许外勤
	private void validateAttendOutside(SysAttendMain main) throws Exception {
		if(main.getFdHisCategory() !=null) {
			SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
			if (category.getFdType() == AttendConstant.FDTYPE_ATTEND
					&& Boolean.TRUE.equals(main.getFdOutside())) {
				List<SysAttendCategoryRule> ruleList = category.getFdRule();
				if (ruleList != null && !ruleList.isEmpty()) {
					SysAttendCategoryRule rule = ruleList.get(0);
					if (!Boolean.TRUE.equals(rule.getFdOutside())) {
						String errrMsg = "考勤打卡失败,原因:考勤组设置不允许外勤打卡,userName:"
								+ main.getDocCreator().getFdName();
						logger.error(errrMsg);
						throw new Exception(errrMsg);
					}
				}
			}
		}
	}

	private String updateAttendMainBuss(SysAttendMain main,
			SysAttendMain oldMain) throws Exception {
		oldMain.setDocAlterTime(new Date());
		oldMain.setDocCreateTime(main.getDocCreateTime());
		oldMain.setDocAlteror(UserUtil.getUser());
		oldMain.setFdAddress(main.getFdAddress());
		oldMain.setFdClientInfo(main.getFdClientInfo());
		oldMain.setFdDesc(main.getFdDesc());
		oldMain.setFdDeviceId(main.getFdDeviceId());
		oldMain.setFdLat(main.getFdLat());
		oldMain.setFdLatLng(main.getFdLatLng());
		oldMain.setFdLng(main.getFdLng());
		oldMain.setFdLocation(main.getFdLocation());
		oldMain.setFdOutside(main.getFdOutside());
		oldMain.setFdWifiMacIp(main.getFdWifiMacIp());
		oldMain.setFdWifiName(main.getFdWifiName());

		this.update(oldMain);
		return oldMain.getFdId();
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		setFormValue(form, requestContext);
		super.update(form, requestContext);
	}

	@Override
	public void updateByAdmin(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysAttendMainForm mainForm = (SysAttendMainForm) form;
		String oldStatus = mainForm.getFdOldStatus();
		String newStatus = "11".equals(mainForm.getFdStatus()) ? "1"
				: mainForm.getFdStatus();
		String fdOutSide = mainForm.getFdOutside();
		String newFdOutSide = "11".equals(mainForm.getFdStatus()) ? "true"
				: "false";
		boolean isNoExc = false;

		boolean isToOk = false;
		// 状态由异常，外勤 置为 正常，出差，请假，外勤
		if (("0".equals(oldStatus) || "2".equals(oldStatus)
				|| "3".equals(oldStatus)
				|| ("1".equals(oldStatus) && "true".equals(fdOutSide)))
				&& ("1".equals(newStatus) || "4".equals(newStatus)
						|| "5".equals(newStatus) || "6".equals(newStatus))) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
			hqlInfo.setParameter("fdAttendMainId", mainForm.getFdId());
			List excList = sysAttendMainExcService
					.findList(hqlInfo);
			if (excList != null && !excList.isEmpty()) {
				// 异常单已通过则不处理
				if (!Integer.valueOf(2).equals(mainForm.getFdState())) {
					for (int k = 0; k < excList.size(); k++) {
						SysAttendMainExc exc = (SysAttendMainExc) excList
								.get(k);
						try {
							// 特权通过
							sysAttendMainExcService.passByAdmin(exc.getFdId());
						} catch (Exception e) {
							// 特权通过失败
							mainForm.setFdState(2);
						}
					}
				}
				// 异常置为正常，状态不变，方便统计，置为出差、请假不处理
				if ("1".equals(newStatus) && "false".equals(newFdOutSide)) {
					mainForm.setFdStatus(oldStatus);
				} else {
					mainForm.setFdStatus(newStatus);
					// 用户打卡时间恢复为上下班时间(由缺卡,迟到,早退调整为正常)
					if ("1".equals(newStatus) && ("0".equals(oldStatus)
							|| "2".equals(oldStatus)
							|| "3".equals(oldStatus))) {
						isToOk = true;
					}
				}
			} else {
				mainForm.setFdStatus(newStatus);
				// 用户打卡时间恢复为上下班时间(由缺卡,迟到,早退调整为正常)
				if ("1".equals(newStatus) && ("0".equals(oldStatus)
						|| "2".equals(oldStatus) || "3".equals(oldStatus))) {
					isToOk = true;
				}
				isNoExc = true;
			}
		} else {
			mainForm.setFdStatus(newStatus);
			isNoExc = true;
		}

		mainForm.setFdOutside(newFdOutSide);
		UserOperHelper.logUpdate(getModelName());// 添加日志信息
		final SysAttendMain main = (SysAttendMain) convertFormToModel(mainForm, null,
				requestContext);
		if (isNoExc) {
			main.setFdState(2);
		}
		//保存打卡记录信息
		main.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp","sys-attend"));
		main.setDocAlteror(UserUtil.getUser());
		main.setDocAlterTime(new Date());
		if (isToOk) {
			Date oldSignTime = main.getDocCreateTime();
			List signList = sysAttendCategoryService
					.getAttendSignTimes(CategoryUtil.getFdCategoryInfo(main),
							main.getDocCreateTime(), main.getDocCreator());
			SysAttendCategoryWorktime workTime = this.sysAttendCategoryService
					.getWorkTimeByRecord(signList, oldSignTime,
							main.getFdWorkType());
			if (workTime != null) {
				Date _signTime = workTime.getFdStartTime();
				if (Integer.valueOf(1)
						.equals(main.getFdWorkType())) {
					_signTime = workTime.getFdEndTime();
				}
				main.setDocCreateTime(AttendUtil
						.joinYMDandHMS(main.getDocCreateTime(), _signTime));
				logger.warn(
						"用户考勤异常状态置为正常操作通过!打卡时间调整为:" + oldSignTime
								+ "--->" + main.getDocCreateTime()
								+ ";userName:"
								+ main.getDocCreator());
			}
		}
		main.setFdAlterRecord(ResourceUtil.getString(
				"sysAttendMain.fdAlterRecord.content", "sys-attend")
				.replace("%status1%", "true".equals(fdOutSide)
						&& "1".equals(oldStatus) ? ResourceUtil.getString(
								"sysAttendMain.outside", "sys-attend")
								: EnumerationTypeUtil.getColumnEnumsLabel(
										"sysAttendMain_fdStatus",
										oldStatus + ""))
				.replace("%status2%",
						"1".equals(newStatus)
								&& "true".equals(newFdOutSide)
										? ResourceUtil.getString(
												"sysAttendMain.outside",
												"sys-attend")
										: EnumerationTypeUtil
												.getColumnEnumsLabel(
														"sysAttendMain_fdStatus",
														newStatus + "")));
		super.update(main);
		// 删除待办
		sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(main,
				"sendUnSignNotify", null, null);
		// 重新统计用户数据
		getBaseDao().flushHibernateSession();
		multicaster.attatchEvent(
				new EventOfTransactionCommit(StringUtils.EMPTY),
				new IEventCallBack() {
					@Override
					public void execute(ApplicationEvent arg0)
							throws Throwable {
						sysAttendStatJobService.stat(
								main.getDocCreator(),
								main.getDocCreateTime(),null);
					}
				});

	}

	@Override
	public void resetUpdateDayReport(final SysOrgPerson p, final Date time) {
		// TODO Auto-generated method stub

		multicaster.attatchEvent(
				new EventOfTransactionCommit(StringUtils.EMPTY),
				new IEventCallBack() {
					@Override
					public void execute(ApplicationEvent arg0)
							throws Throwable {
						sysAttendStatJobService.stat(
								p,
								time,null);
					}
				});
	}
	
	private void setFormValue(IExtendForm form, RequestContext requestContext)
			throws Exception {
		SysAttendMainForm mainForm = (SysAttendMainForm) form;
		String fdType = requestContext.getParameter("fdType");
		if (AttendConstant.FDTYPE_ATTEND == Integer.valueOf(fdType)) {
			// 日期类型
			String isRestDay = requestContext.getParameter("isRestDay");
			mainForm.setFdDateType(
					"true".equals(isRestDay) ? AttendConstant.FD_DATE_TYPE[1]
							: AttendConstant.FD_DATE_TYPE[0]);
			// 打卡状态
			setAttendStatus(mainForm, requestContext);
		}
		// 客户端信息
		String fdClientType = AttendUtil.getClientType(requestContext);
		mainForm.setFdClientInfo(fdClientType);
		// 设备号
		String fdDeviceId = requestContext.getParameter("fdDeviceId");
		// 设备信息
		if (StringUtil.isNotNull(fdDeviceId)) {
			String fdDeviceInfo = AttendUtil.getOperatingSystem(requestContext);
			fdDeviceInfo = fdDeviceInfo + ", " + fdClientType
					+ ResourceUtil.getString(
							"sysAttendMain.export.fdDeviceInfo.fdDeviceId",
							"sys-attend")
					+ ": " + fdDeviceId;
			mainForm.setFdDeviceInfo(fdDeviceInfo);
		} else {
			mainForm.setFdDeviceInfo(
					AttendUtil.getOperatingSystem(requestContext));
		}
		// 排班制
		mainForm.setFdWorkKey(mainForm.getFdWorkTimeId());
	}

	/**
	 * 判断是否出差/请假/外出
	 * 
	 * @param requestContext
	 * @return
	 */
	private boolean isAttendBuss(RequestContext requestContext) {
		String __fdStatus = requestContext.getParameter("fdStatus");
		return AttendUtil.isAttendBuss(__fdStatus);
	}

	private void setAttendStatus(IExtendForm form,
			RequestContext requestContext) throws Exception {
		SysAttendMainForm mainForm = (SysAttendMainForm) form;
		String isRestDay = requestContext.getParameter("isRestDay");
		String fdType = requestContext.getParameter("fdType");
		if (isAttendBuss(requestContext)) {
			return;
		}

		if (AttendConstant.FDTYPE_ATTEND == Integer.valueOf(fdType)) {

			String fdStatus = "1";
			Date now = new Date();
			int mins = now.getHours() * 60 + now.getMinutes();
			Integer fdWorkType = Integer
					.valueOf(requestContext.getParameter("fdWorkType"));
			Integer fdLateTime = StringUtil
					.isNotNull(requestContext.getParameter("fdLateTime"))
							? Integer.valueOf(
									requestContext.getParameter("fdLateTime"))
							: 0;
			Integer fdLeftTime = StringUtil
					.isNotNull(requestContext.getParameter("fdLeftTime"))
							? Integer.valueOf(
									requestContext.getParameter("fdLeftTime"))
							: 0;
			Integer signTime = StringUtil
					.isNotNull(requestContext.getParameter("signTime"))
							? Integer.valueOf(
									requestContext.getParameter("signTime"))
							: 0;
			String fdIsAcross = requestContext.getParameter("fdIsAcross");
			String fdIsFlex = requestContext.getParameter("fdIsFlex");
			Integer fdFlexTime = StringUtil
					.isNotNull(requestContext.getParameter("fdFlexTime"))
							? Integer.valueOf(
									requestContext.getParameter("fdFlexTime"))
							: 0;
			Integer lastSignedTime = StringUtil
					.isNotNull(requestContext.getParameter("lastSignedTime"))
							? Integer.valueOf(
									requestContext
											.getParameter("lastSignedTime"))
							: 0;
//			String lastSignedStatus = requestContext
//					.getParameter("lastSignedStatus");
//			String lastSignedState = requestContext
//					.getParameter("lastSignedState");
//			Integer workTimeMins = StringUtil
//					.isNotNull(requestContext.getParameter("workTimeMins"))
//							? Integer.valueOf(
//									requestContext
//											.getParameter("workTimeMins"))
//							: 0;
			String fdOverTimeType = requestContext.getParameter("fdOverTimeType");
			String fdWorkTimeId = requestContext.getParameter("fdWorkTimeId");
			int _goWorkTimeMins =0;
			SysAttendCategory category =CategoryUtil.getCategoryById(mainForm.getFdCategoryId());
			if (AttendConstant.SysAttendMain.FD_WORK_TYPE[1].equals(fdWorkType)) {
				//获取当前班次的标准上班时间。
				SysAttendCategoryWorktime workTime = sysAttendCategoryService.getCurrentWorkTime(category, fdWorkTimeId,String.valueOf(fdWorkType),String.valueOf(signTime));
				if (workTime != null) {
					//标准的打卡时间
					_goWorkTimeMins = workTime.getFdStartTime().getHours() * 60 +  workTime.getFdStartTime().getMinutes();
				}
			}
			if ("true".equals(fdIsAcross)) {
				//跨天打卡加上24小时的分钟数
				mins +=24*60;
			}

			if ("true".equals(fdIsFlex)) {// 是否弹性上下班
				if (AttendConstant.SysAttendMain.FD_WORK_TYPE[0].equals(fdWorkType)) {
					// 上班
					if (mins > (signTime + fdFlexTime)) {
						fdStatus = "2";
					}
				} else {
					//下班
					//已打卡的上班时间
					//提前多少分钟上班的（-30）
					Integer goWrokFlexMin = _goWorkTimeMins - lastSignedTime;
					//最大弹性时间为设置的。超过最大以设置最大的弹性分钟数为准
					if(goWrokFlexMin < fdFlexTime){
						//如果提前打卡的分钟数，在弹性时间范围内，则以多大的分钟数作为弹性分钟数
						if(goWrokFlexMin < 0){
							//比如设置30分钟，迟到1个小时，则没有弹性分钟数的概念
							if(goWrokFlexMin * -1  > fdFlexTime ){
								fdFlexTime =0;
							}else{
								fdFlexTime =goWrokFlexMin;
							}
						} else {
							fdFlexTime =goWrokFlexMin;
						}
					}
					//如果弹性时间小于0.则表示 上班迟到，则要下班标准时间 加上 迟到的时间，才不会早退
					if(fdFlexTime < 0 ){
						fdFlexTime = fdFlexTime * -1;
						if(mins < signTime){
							//打卡时间是标准打卡时间之前，则是迟到
							fdStatus = "3";
						}else if(mins <  signTime + fdFlexTime ){
							//打卡时间，小于弹性时间
							fdStatus = "3";
						}
					}else{
						//提前上班的弹性时间计算。
						if(mins < signTime - fdFlexTime ){
							//打卡时间是标准打卡时间 减去弹性时间 之前。算迟到
							fdStatus = "3";
						}
					}

//					// 下班
//					if ("1".equals(lastSignedStatus)
//							|| "2".equals(lastSignedState)) {// 上班是否正常
//
//						if ("true".equals(fdIsAcross)) {
//							// 次日打卡
//							if ((lastSignedTime + workTimeMins) > 1440) {
//								if("2".equals(fdOverTimeType)) {
//									if (lastSignedTime
//											+ workTimeMins < (signTime - fdFlexTime)) {
//										if (mins < (signTime - fdFlexTime)) {
//											fdStatus = "3";
//										}
//									} else if (mins < (lastSignedTime
//											+ workTimeMins)) {
//										fdStatus = "3";
//									}
//								}else {
//									if (mins < (lastSignedTime + workTimeMins
//											- 1440)) {
//										fdStatus = "3";
//									}
//								}
//							}
//						} else {
//							//标准打卡时间 - 上班打卡时间
//							// 当天打卡
//							if (lastSignedTime
//									+ workTimeMins < (signTime - fdFlexTime)) {
//								if (mins < (signTime - fdFlexTime)) {
//									fdStatus = "3";
//								}
//							} else if (mins < (lastSignedTime
//									+ workTimeMins)) {
//								fdStatus = "3";
//							}
//						}
//
//					} else {
//
//						if ("true".equals(fdIsAcross)) {// 次日打卡
//							fdStatus = "1";
//							if("2".equals(fdOverTimeType)) {
//								if (mins < (signTime - fdLeftTime)) {
//									fdStatus = "3";
//								}
//							}
//						} else {// 当天打卡
//							if (mins < (signTime - fdLeftTime)) {
//								fdStatus = "3";
//							}
//						}
//					}
				}
			} else {
				// 不是弹性上下班
				if (AttendConstant.SysAttendMain.FD_WORK_TYPE[0]
						.equals(fdWorkType)) {
					if (mins > (signTime + fdLateTime)) {
						fdStatus = "2";
					}
				} else {
					if (mins < (signTime - fdLeftTime)) {
						fdStatus = "3";
					}
				}
			}
			// 节假日
			if ("true".equals(isRestDay)) {
				fdStatus = "1";
			}
			mainForm.setFdStatus(fdStatus);
		}
	}



	private void sendCustomResultNotify(SysAttendMain main) throws Exception {
		SysAttendCategory category = main.getFdCategory();
		if (category != null
				&& AttendConstant.FDTYPE_CUST == category.getFdType()
				&& Boolean.TRUE.equals(category.getFdNotifyResult())) {
			NotifyContext notifyContext = sysNotifyMainCoreService
					.getContext(null);
			String nowTime = DateUtil.convertDateToString(new Date(), "HH:mm");
			String docCreatorName = "";
			if (main.getFdOutPerson() != null) {
				docCreatorName = main.getFdOutPerson().getFdName();
			} else {
				docCreatorName = main.getDocCreator().getFdName();
			}
			String subject = nowTime + " " + docCreatorName + " "
					+ ResourceUtil.getString("sysAttendMain.sign.sucess",
							"sys-attend");
			notifyContext.setSubject(subject);
			notifyContext.setContent(subject);
			if (category.getFdManager() == null) {
				return;
			}
			List<SysOrgElement> targets = new ArrayList<SysOrgElement>();
			targets.add(category.getFdManager());
			notifyContext.setNotifyTarget(targets);
			notifyContext.setNotifyType("todo");
			notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
			notifyContext.setLink(
					"/sys/attend/mobile/index_stat.jsp?categoryId="
							+ category.getFdId());
			sysNotifyMainCoreService.sendNotify(main, notifyContext, null);

		}

	}


	/**
	 * 获取签到的有效记录
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public List findCustList(String categoryId, Date date) throws Exception {
		return findAttendMainList(categoryId,date,false);
	}

	/**
	 * 根据考勤组和日期查询对应的有效考勤记录
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@Override
	public List findList(String categoryId, Date date) throws Exception {
		return findAttendMainList(categoryId,date,true);
	}

	/**
	 * 获取签到记录
	 * @param categoryId 考勤组、签到组
	 * @param date 日期
	 * @param isAttend 是否是考勤组
	 * @return
	 * @throws Exception
	 */
	private List findAttendMainList(String categoryId, Date date,boolean isAttend) throws Exception {
		StringBuffer sql = new StringBuffer();
		String userId = UserUtil.getKMSSUser().getUserId();
		//2021-09-30冲刺修改
		sql.append(
				"select fd_id,fd_status,fd_desc,fd_lng,fd_lat,fd_location,fd_category_his_id,fd_work_type,"
						+ "fd_outside,fd_work_id,fd_state,doc_creator_id,doc_create_time,fd_business_id,fd_wifi_name,fd_is_across,fd_work_key")
				.append(" from sys_attend_main m ")
				.append(" where doc_creator_id=:docCreatorId  and (doc_status=0 or doc_status is null)  ");
		if(isAttend){
			sql.append(" and fd_category_his_id=:categoryId ");
		}else{
			sql.append(" and fd_category_id=:categoryId ");
		}
		StringBuffer sql1 = new StringBuffer();
		sql1.append(sql);
		sql1.append("  and doc_create_time>=:beginTime and doc_create_time<:endTime and (fd_is_across is null or fd_is_across=:fdIsAcross0)");

		StringBuffer sql2 = new StringBuffer();
		sql2.append(sql);
		sql2.append(" and fd_is_across=:fdIsAcross1 and doc_create_time>=:nextBegin and doc_create_time<:nextEnd ");
		List list1 = this.getBaseDao().getHibernateSession()
				.createNativeQuery(sql1.toString())
				.setParameter("docCreatorId", userId)
				.setParameter("categoryId", categoryId)
				.setParameter("fdIsAcross0", false)
				.setParameter("beginTime", AttendUtil.getDate(date, 0))
				.setParameter("endTime", AttendUtil.getDate(date, 1))
				.list();

		List list2 = this.getBaseDao().getHibernateSession()
				.createNativeQuery(sql2.toString())
				.setParameter("docCreatorId", userId)
				.setParameter("categoryId", categoryId)
				.setParameter("fdIsAcross1", true)
				.setParameter("nextBegin", AttendUtil.getDate(date, 1))// 跨天加班的数据
				.setParameter("nextEnd", AttendUtil.getDate(date, 2))
				.list();
		List list =new ArrayList();
		if(CollectionUtils.isNotEmpty(list1)){
			list.addAll(list1);
		}
		if(CollectionUtils.isNotEmpty(list2)){
			list.addAll(list2);
		}
		UserOperHelper.logFindAll(list, getModelName());// 添加日志信息
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Object[] record = (Object[]) list.get(i);
			Map<String, Object> m = new HashMap<String, Object>();

			m.put("fdId", (String) record[0]);
			Number fdStatus = (Number) record[1];
			m.put("fdStatus", fdStatus == null ? null : fdStatus.intValue());
			m.put("fdDesc", (String) record[2]);
			m.put("fdLng", (String) record[3]);
			m.put("fdLat", (String) record[4]);
			m.put("fdLocation", (String) record[5]);
			m.put("fdCategoryId", (String) record[6]);
			Number fdWorkType = (Number) record[7];
			m.put("fdWorkType",
					fdWorkType == null ? null : fdWorkType.intValue());
			m.put("fdOutside", getBooleanField(record[8]));
			m.put("fdWorkId", (String) record[9]);
			Number fdState = (Number) record[10];
			m.put("fdState", fdState == null ? null : fdState.intValue());
			m.put("docCreatorId", (String) record[11]);

			m.put("docCreateTime", null);
			if(record[12]  !=null) {
				if(record[12] instanceof String) {
					m.put("docCreateTime", new Timestamp(DateUtil.convertStringToDate(record[12].toString()).getTime()));
				}else if (record[12] instanceof Timestamp){
					m.put("docCreateTime", (Timestamp)record[12]);
				}
			}
			m.put("fdBusinessId", (String) record[13]);
			m.put("fdWifiName", (String) record[14]);
			m.put("fdIsAcross", getBooleanField(record[15]));
			m.put("fdWorkKey", (String) record[16]);
			mapList.add(m);
		}
		return mapList;
	}

	@Override
	public JSONObject formatCalendarData(List<SysAttendMain> list,SysAttendCategory category) throws Exception {
		JSONObject object = new JSONObject();
		List<JSONObject> result = new ArrayList<JSONObject>();
		if (category.getFdType() == 2) {
			result = getSignFreelyList(category, list);
		} else {
			result = getSignList(category, list);
		}
		object.put("calendars", result);
		// object.put("signInfo", getSignTimeList(category));

		return object;
	}

	private List getSignRecordInfo(SysAttendCategory category,
			List<Map<String, Object>> recordList) {
		Map<String, Object> m = new HashMap<String, Object>();
		List<SysAttendCategoryRule> ruleList = category.getFdRule();
		if (!ruleList.isEmpty()) {
			SysAttendCategoryRule rule = ruleList.get(0);
			m.put("fdOutside", rule.getFdOutside());
			m.put("fdLateTime", rule.getFdLateTime());
			m.put("fdLeftTime", rule.getFdLeftTime());
			m.put("fdLimit", rule.getFdLimit());
		}
		m.put("categoryId", category.getFdId());
		m.put("fdStartTime", category.getFdStartTime());
		m.put("fdEndTime", category.getFdEndTime());
		m.put("fdType", category.getFdType());
		m.put("fdLocations", category.getFdLocations());
		m.put("fdNotifyResult", category.getFdNotifyResult());
		// 签到记录
		JSONArray recordArr = new JSONArray();
		for (Map<String, Object> record : recordList) {
			JSONObject json = new JSONObject();
			// 签到记录信息
			Timestamp fdSignedTime = (Timestamp) record
					.get("docCreateTime");
			json.put("fdSignedTime", fdSignedTime.getTime());
			json.put("fdSignedStatus", (Integer) record.get("fdStatus"));
			json.put("fdSignedLocation", (String) record.get("fdLocation"));
			json.put("fdSignedOutside", (Boolean) record.get("fdOutside"));
			recordArr.add(json);
		}
		Collections.sort(recordArr, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Long signTime1 = (Long) o1.get("fdSignedTime");
				Long signTime2 = (Long) o2.get("fdSignedTime");
				return signTime1.compareTo(signTime2);
			}
		});
		m.put("records", recordArr);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(m);
		return list;
	}

	private List<JSONObject> getSignFreelyList(SysAttendCategory category,
			List<SysAttendMain> list) throws Exception {
		List<JSONObject> result = new ArrayList<JSONObject>();
		for (SysAttendMain main : list) {
			JSONObject json = new JSONObject();
			Date signTime = main.getDocAlterTime() == null
					? main.getDocCreateTime() : main.getDocAlterTime();
			json.put("fdId", main.getFdId());
			json.put("fdSignedTime", signTime.getTime());
			json.put("fdSignedStatus", main.getFdStatus());
			json.put("fdDesc", main.getFdDesc());
			json.put("fdSignedLocation", main.getFdLocation());
			json.put("fdAppName", main.getFdAppName());
			json.put("fdSignedOutside", main.getFdOutside());
			json.put("fdWorkType", main.getFdWorkType());
			json.put("fdType", 2);
			json.put("docCreatorId", main.getDocCreator().getFdId());
			json.put("categoryId", category.getFdId());
			json.put("categoryName", category.getFdName());
			json.put("start", DateUtil.convertDateToString(signTime,
					DateUtil.TYPE_DATETIME, null));
			json.put("end", DateUtil.convertDateToString(signTime,
					DateUtil.TYPE_DATETIME, null));

			result.add(json);
		}
		return result;
	}

	/**
	 * 转换签到记录
	 * @param currentCategory 当前最新的签到组ID
	 * @param list 考勤列表
	 * @return
	 * @throws Exception
	 */
	private List<JSONObject> getSignList(SysAttendCategory currentCategory,List<SysAttendMain> list) throws Exception {
		List<JSONObject> result = new ArrayList<JSONObject>();
		List<SysAttendMain> todayList = new ArrayList<SysAttendMain>();
		long start = DateUtil.getDate(0).getTime();
		long end = DateUtil.getDate(1).getTime();
		for (SysAttendMain main : list) {
			SysAttendCategory category = CategoryUtil.getCategoryById(main.getFdHisCategory().getFdId());
			// 是否排班制
			boolean isTimeArea = Integer.valueOf(1)
					.equals(category.getFdShiftType());

			JSONObject json = new JSONObject();
			Date signTime = main.getDocCreateTime();
			json.put("fdId", main.getFdId());
			long fdSignedTime = signTime.getTime();
			if (main.getFdStatus() == 0
					&& !Integer.valueOf(2).equals(main.getFdState())
					&& !isTimeArea) {
				SysAttendCategoryWorktime workTime = main.getWorkTime();
				if(workTime!=null) {
					Date fdStartTime = workTime.getFdStartTime();
					Date fdEndTime = workTime.getFdEndTime();
					if (main.getFdWorkType() == 0 && fdStartTime != null) {
						signTime.setHours(fdStartTime.getHours());
						signTime.setMinutes(fdStartTime.getMinutes());
						signTime.setSeconds(0);
						fdSignedTime = signTime.getTime();
					}
					if (main.getFdWorkType() == 1 && fdEndTime != null) {
						signTime.setHours(fdEndTime.getHours());
						signTime.setMinutes(fdEndTime.getMinutes());
						signTime.setSeconds(0);
						fdSignedTime = signTime.getTime();
					}
				}
			}
			json.put("fdSigned", true);
			json.put("fdSignedTime", fdSignedTime);
			json.put("fdSignedStatus", main.getFdStatus());
			json.put("fdSignedStatusTxt",
					EnumerationTypeUtil.getColumnEnumsLabel(
							"sysAttendMain_fdStatus", main.getFdStatus() + ""));
			json.put("fdDesc", main.getFdDesc());
			json.put("fdSignedLocation", main.getFdLocation());
			json.put("fdSignedOutside", main.getFdOutside());
			json.put("fdSignedAcross", main.getFdIsAcross());
			json.put("fdWorkType", main.getFdWorkType());
			json.put("fdAppName", main.getFdAppName());
			if (main.getFdIsAcross() != null && main.getFdIsAcross()) {
				// 把跨天打卡的数据移动到日历的前一天
				Calendar cal = Calendar.getInstance();
				cal.setTime(signTime);
				cal.add(Calendar.DATE, -1);
				if (main.getFdWorkType() != null && main.getFdWorkType() == 1) {
					cal.set(Calendar.HOUR_OF_DAY, 23);
				}
				if (main.getFdWorkType() != null && main.getFdWorkType() == 0) {
					cal.set(Calendar.HOUR_OF_DAY, 22);
				}
				json.put("start", DateUtil.convertDateToString(cal.getTime(),
						DateUtil.TYPE_DATETIME, null));
				json.put("end", DateUtil.convertDateToString(cal.getTime(),
						DateUtil.TYPE_DATETIME, null));
			} else {
				Date baseTime=main.getFdBaseWorkTime() == null ? main.getDocCreateTime()
						: main.getFdBaseWorkTime();
				long workDate=AttendUtil.getDate(main.getFdWorkDate(), 0).getTime();
				if(main.getFdBaseWorkTime() != null&& AttendUtil.getDate(main.getFdBaseWorkTime(), 0).getTime()>workDate) {
					// 把跨天排班打卡的数据移动到日历的前一天
					Calendar cal = Calendar.getInstance();
					cal.setTime(baseTime);
					cal.add(Calendar.DATE, -1);
					if (main.getFdWorkType() != null && main.getFdWorkType() == 1) {
						cal.set(Calendar.HOUR_OF_DAY, 23);
					}
					if (main.getFdWorkType() != null
							&& main.getFdWorkType() == 0) {
						cal.set(Calendar.HOUR_OF_DAY, 22);
					}
					baseTime=cal.getTime();
				}
				json.put("start", DateUtil.convertDateToString(baseTime,
						DateUtil.TYPE_DATETIME, null));
				json.put("end", DateUtil.convertDateToString(baseTime,
						DateUtil.TYPE_DATETIME, null));
			}
			json.put("fdState", main.getFdState());
			json.put("categoryId", category.getFdId());
			json.put("fdOsdReviewType", category.getFdOsdReviewType() == null
					? 0 : category.getFdOsdReviewType());
			JSONArray busArray = new JSONArray();
			for (SysAttendCategoryBusiness bus : category.getBusSetting()) {
				JSONObject busJson = new JSONObject();
				busJson.put("fdBusName", bus.getFdBusName());
				busJson.put("fdBusType", bus.getFdBusType());
				busJson.put("fdReviewUrl",
						"/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="
								+ bus.getFdTemplateId());
				busArray.add(busJson);
			}
			json.put("fdBusSetting",
					JSONArray.fromObject(busArray));
			json.put("fdWorkTimeId", isTimeArea || main.getWorkTime()==null ? main.getFdWorkKey()
					: main.getWorkTime().getFdId());
			json.put("fdType", 1);
			if (main.getFdBusiness() != null) {
				json.put("fdBusUrl", main.getFdBusiness().getDocUrl());
			}
			if (main.getFdState() != null) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setSelectBlock(" sysAttendMainExc.fdId ");
				hqlInfo.setOrderBy(
						"sysAttendMainExc.docCreateTime desc");
				hqlInfo.setWhereBlock(
						"sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
				hqlInfo.setParameter("fdAttendMainId", main.getFdId());
				String excId = (String) sysAttendMainExcService.findFirstOne(hqlInfo);
				if (excId != null && !excId.isEmpty()) {
					json.put("fdMainExcId", excId);
				}
			}

			long _signTime = signTime.getTime();
			if (_signTime >= start && _signTime < end) {
				todayList.add(main);
			}
			result.add(json);
		}
		//根据打卡日期、打卡人、考勤组 获取对应时期的签到时间
		List<Map<String, Object>> signTimeList = getSignTimeList(currentCategory,new Date(), UserUtil.getUser());
		if (!todayList.isEmpty()) {
			// 排班班次渲染
			sysAttendCategoryService.doWorkTimesRender(signTimeList, todayList);
		}
		for (Map<String, Object> m : signTimeList) {
			String fdWorkTimeId = (String) m.get("fdWorkTimeId");
			Integer fdWorkType = (Integer) m.get("fdWorkType");
			boolean signed = false;
			for (SysAttendMain main : todayList) {
				if (sysAttendCategoryService.isSameWorkTime(m,
						main.getWorkTime() == null ? ""
						: main.getWorkTime().getFdId(),
						main.getFdWorkType(), main.getFdWorkKey())) {
					signed = true;
					break;
				}
			}
			// 补全记录
			if (!signed) {
				Object _nextSignTime = m.get("nextSignTime");
				Object _nextOverTimeType = m.get("nextOverTimeType");
				if (_nextSignTime instanceof Date) {
					Date nextSignTime = (Date) _nextSignTime;
					int mins = nextSignTime.getHours() * 60
							+ nextSignTime.getMinutes();
					Date now = new Date();
					int nowTime = now.getHours() * 60 + now.getMinutes();
					Integer nextOverTimeType=1;
					if(_nextOverTimeType instanceof Integer) {
						nextOverTimeType=(Integer)_nextOverTimeType;
					}
					if(Integer.valueOf(2).equals(nextOverTimeType)) {
						mins+=1440;
					}
					if (nowTime > mins) {
						// 缺卡处理
						JSONObject json = new JSONObject();
						json.put("fdId", "");
						Date _signTime = (Date) m.get("signTime");
						now.setHours(_signTime.getHours());
						now.setMinutes(_signTime.getMinutes());
						json.put("fdSignedTime", now.getTime());
						json.put("fdSignedStatus", 0);
						json.put("fdSignedStatusTxt",
								EnumerationTypeUtil.getColumnEnumsLabel(
										"sysAttendMain_fdStatus", "0"));
						json.put("fdDesc", "");
						json.put("fdSignedLocation", "");
						json.put("fdAppName", "");
						json.put("fdSignedOutside", "");
						json.put("fdWorkType", fdWorkType);
						json.put("start", DateUtil.convertDateToString(now,
								DateUtil.TYPE_DATETIME, null));
						json.put("end", DateUtil.convertDateToString(now,
								DateUtil.TYPE_DATETIME, null));
						
						json.put("categoryId", currentCategory.getFdId());
						JSONArray busArray = new JSONArray();
						for (SysAttendCategoryBusiness bus : currentCategory.getBusSetting()) {
							JSONObject busJson = new JSONObject();
							busJson.put("fdBusName", bus.getFdBusName());
							busJson.put("fdBusType", bus.getFdBusType());
							busJson.put("fdReviewUrl",
									"/km/review/km_review_main/kmReviewMain.do?method=add&fdTemplateId="
											+ bus.getFdTemplateId());
							busArray.add(busJson);
						}
						json.put("fdBusSetting",
								JSONArray.fromObject(busArray));
						json.put("fdWorkTimeId", fdWorkTimeId);
						json.put("fdType", 1);
						result.add(json);
					}
				}
			}
		}

		Collections.sort(result, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Long signTime1 = (Long) o1.get("fdSignedTime");
				Long signTime2 = (Long) o2.get("fdSignedTime");
				Integer fdWorkType1 = (Integer) o1.get("fdWorkType");
				Integer fdWorkType2 = (Integer) o2.get("fdWorkType");

				if (signTime1.equals(signTime2)) {
					return fdWorkType2.compareTo(fdWorkType1);
				}
				return signTime1.compareTo(signTime2);
			}
		});
		return result;
	}

	@Override
	public List format(List<Map<String, Object>> list,
			SysAttendCategory category, Date date) throws Exception {
		if(category ==null){
			return null;
		}
		int fdType = category.getFdType();
		if (fdType == 2) {
			return this.getSignRecordInfo(category, list);
		}
		SysOrgPerson user =UserUtil.getUser();
		List<Map<String, Object>> signTimeList = getSignTimeList(category, date, user);
		if (signTimeList.isEmpty() && Integer.valueOf(1).equals(category.getFdShiftType())) {
			// 排班休息日打卡
			signTimeList = this.getAttendAreaRestSignTimes(category,AttendUtil.getDate(date, 1), user);
			//159406 加入CollectionUtils.isEmpty(list) 判断
			if (CollectionUtils.isNotEmpty(signTimeList) && CollectionUtils.isEmpty(list)) {
				List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
				newList.add(signTimeList.get(0));
				return newList;
			}
			return this.getAttendAreaRestRecordInfo(signTimeList, list);
		}
		if (CollectionUtils.isEmpty(list)) {
			return signTimeList;
		}
		// 排班班次渲染
		this.sysAttendCategoryService.doWorkTimesRender(signTimeList, list);

		//signTimeList有缓存。这里克隆修改
		List<Map<String, Object>> returlList =new ArrayList<>();
		for (Map<String, Object> valueMap: signTimeList) {
			Map<String, Object> m=new HashMap<>();
			m.putAll(valueMap);
			String fdWorkTimeId = (String) m.get("fdWorkTimeId");
			Integer fdWorkType = (Integer) m.get("fdWorkType");
			for (Map<String, Object> record : list) {
				if (sysAttendCategoryService.isSameWorkTime(m,
						(String) record.get("fdWorkId"),
						(Integer) record.get("fdWorkType"),
						(String) record.get("fdWorkKey"))) {
					// 签到记录信息
					m.put("fdId", (String) record.get("fdId"));
					m.put("fdSigned", true);
					Timestamp fdSignedTime = (Timestamp) record.get("docCreateTime");
					Integer fdStatus = (Integer) record.get("fdStatus");
					if (fdStatus == 0) {
						Date signTime = (Date) m.get("signTime");
						fdSignedTime = new Timestamp(signTime.getTime());
					}
					m.put("fdSignedTime", fdSignedTime);
					m.put("fdSignedStatus", (Integer) record.get("fdStatus"));
					m.put("fdSignedLocation",
							(String) record.get("fdLocation"));
					m.put("fdSignedWifi", (String) record.get("fdWifiName"));
					m.put("fdSignedOutside", (Boolean) record.get("fdOutside"));
					m.put("fdSignedAcross", (Boolean) record.get("fdIsAcross"));
					m.put("fdState", (Integer) record.get("fdState"));
					String fdBusinessId = (String) record.get("fdBusinessId");
					if (StringUtil.isNotNull(fdBusinessId)) {
						SysAttendBusiness business = (SysAttendBusiness) sysAttendBusinessService
								.findByPrimaryKey(fdBusinessId);
						m.put("fdBusUrl", business.getDocUrl());
					}
					if (record.get("fdState") != null) {
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setSelectBlock(" sysAttendMainExc.fdId ");
						hqlInfo.setOrderBy(
								"sysAttendMainExc.docCreateTime desc");
						hqlInfo.setWhereBlock(
								"sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
						hqlInfo.setParameter("fdAttendMainId",
								(String) record.get("fdId"));
						String excId = (String) sysAttendMainExcService.findFirstOne(hqlInfo);
						if (excId != null && !excId.isEmpty()) {
							m.put("fdMainExcId", excId);
						}
					}
					break;
				}
			}
			if (AttendConstant.SysAttendMain.FD_WORK_TYPE[1]
					.equals(fdWorkType)) {
				for (Map<String, Object> record : list) {
					if (fdWorkTimeId.equals((String) record.get("fdWorkId"))
							&& AttendConstant.SysAttendMain.FD_WORK_TYPE[0]
									.equals((Integer) record
											.get("fdWorkType"))) {
						m.put("lastSignedTime",
								(Timestamp) record.get("docCreateTime"));
						m.put("lastSignedStatus",
								(Integer) record.get("fdStatus"));
						m.put("lastSignedState",
								(Integer) record.get("fdState"));
						break;
					}
				}
			}

			returlList.add(m);
		}
		for (int k = returlList.size() - 1; k >= 0; k--) {
			Map<String, Object> map = returlList.get(k);
			if (map.containsKey("fdSigned")
					&& ((boolean) map.get("fdSigned"))) {
				// 最后一个已打卡
				map.put("isLastSigned", true);
				break;
			}
		}
		return returlList;
	}

	/**
	 * 获取排班休息日打卡班次信息(原因:排班制时休息日没有班次信息)
	 * 
	 * @param category
	 * @param date
	 * @param ele
	 * @return
	 * @throws Exception
	 */
	private List getAttendAreaRestSignTimes(SysAttendCategory category,Date date, SysOrgElement ele) throws Exception {
		//往前找半个月
		for (int i = 0; i < 15; i++) {// 尝试获取最近一次的班次信息
			List<Map<String, Object>> list = this.sysAttendCategoryService
					.getAttendSignTimes(
					category, AttendUtil.getDate(date, -i), ele);
			if (CollectionUtils.isNotEmpty(list)) {
				return list;
			}
		}
		//往后找半个月
		for (int i = 0; i < 15; i++) {
			// 尝试获取最近一次的班次信息
			List<Map<String, Object>> list = this.sysAttendCategoryService
					.getAttendSignTimes(
							category, AttendUtil.getDate(date, i), ele);
			if (CollectionUtils.isNotEmpty(list)) {
				return list;
			}
		}
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		List<SysAttendCategoryRule> ruleList = category.getFdRule();
		SysAttendCategoryRule rule = ruleList.get(0);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("categoryId", category.getFdId());
		m.put("fdType", category.getFdType());
		m.put("fdOutside", rule.getFdOutside());
		m.put("fdLateTime", rule.getFdLateTime());
		m.put("fdLeftTime", rule.getFdLeftTime());
		m.put("fdLimit", rule.getFdLimit());
		m.put("fdLocations", category.getFdLocations());
		m.put("fdBusSetting", category.getBusSetting());
		m.put("fdWifiConfigs", category.getFdWifiConfigs());
		m.put("fdIsFlex", category.getFdIsFlex());
		m.put("fdFlexTime", category.getFdFlexTime());
		m.put("fdOsdReviewType", category.getFdOsdReviewType() == null ? 0
				: category.getFdOsdReviewType());
		m.put("fdShiftType", category.getFdShiftType());
		m.put("fdSecurityMode", AttendUtil.isEnableKKConfig()
				? category.getFdSecurityMode() : "");
		ISysAttendConfigService sysAttendConfigService = (ISysAttendConfigService) SpringBeanUtil
				.getBean("sysAttendConfigService");
		SysAttendConfig config = sysAttendConfigService.getSysAttendConfig();
		JSONObject cfgJson = new JSONObject();
		if (config != null) {
			boolean isEnableKK = AttendUtil.isEnableKKConfig();
			cfgJson.put("fdClientLimit",
					isEnableKK ? config.getFdClientLimit() : false);
			cfgJson.put("fdClient", config.getFdClient());
			cfgJson.put("fdDeviceLimit",
					isEnableKK ? config.getFdDeviceLimit() : false);
			cfgJson.put("fdDeviceCount", config.getFdDeviceCount());
			cfgJson.put("fdDeviceExcMode", config.getFdDeviceExcMode());
		}
		m.put("attendCfgJson", cfgJson.toString());
		m.put("fdWorkTimeId", IDGenerator.generateID());
		m.put("signTime", new Date());
		m.put("fdWorkType",
				AttendConstant.SysAttendMain.FD_WORK_TYPE[0]);
		newList.add(m);
		return newList;
	}

	private List getAttendAreaRestRecordInfo(
			List<Map<String, Object>> signTimes,
			List<Map<String, Object>> list) {
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		Map<String, Object> onWorkMap = signTimes.get(0);
		for (Map<String, Object> record : list) {
			// 签到记录信息
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("fdId", (String) record.get("fdId"));
			m.put("fdSigned", true);
			Timestamp fdSignedTime = (Timestamp) record.get("docCreateTime");
			m.put("fdSignedTime", fdSignedTime);
			m.put("fdSignedStatus", (Integer) record.get("fdStatus"));
			m.put("fdSignedLocation",
					(String) record.get("fdLocation"));
			m.put("fdSignedWifi", (String) record.get("fdWifiName"));
			m.put("fdSignedOutside", (Boolean) record.get("fdOutside"));
			m.put("fdSignedAcross", (Boolean) record.get("fdIsAcross"));
			m.putAll(onWorkMap);
			m.put("fdWorkTimeId", (String) record.get("fdWorkKey"));
			m.put("signTime", new Date());
			m.put("fdWorkType", (Integer) record.get("fdWorkType"));
			newList.add(m);
		}
		// 最后打卡记录
		Map<String, Object> lastRecord = newList.get(newList.size() - 1);
		Integer fdWorkType = (Integer) lastRecord.get("fdWorkType");
		if (AttendConstant.SysAttendMain.FD_WORK_TYPE[1].equals(fdWorkType)) {
			newList.add(onWorkMap);
		} else {
			onWorkMap.put("fdWorkTimeId",
					(String) lastRecord.get("fdWorkTimeId"));
			onWorkMap.put("fdWorkType",
					AttendConstant.SysAttendMain.FD_WORK_TYPE[1]);
			newList.add(onWorkMap);
		}
		return newList;
	}



	/**
	 * 获取某天考勤组打卡时间点 (可用于排班制)
	 * 
	 * @param category
	 * @param date
	 * @param ele
	 * @return
	 * @throws Exception
	 */
	private List getSignTimeList(SysAttendCategory category, Date date,
			SysOrgElement ele)
			throws Exception {
		Integer fdShiftType = category.getFdShiftType();
		if (Integer.valueOf(1).equals(fdShiftType)) {
			return sysAttendCategoryService.getAttendSignTimes(category, date,
					ele);
		}
		return getAttendSignTimes(category, date,ele);
	}

	private List getAttendSignTimes(SysAttendCategory category, Date date,SysOrgElement ele)
			throws Exception {
		return sysAttendCategoryService.getAttendSignTimes(category, date, ele,false);
	}

	@Override
	public List getAttendSignTimes(SysAttendCategory category, Date date)
			throws Exception {
		return sysAttendCategoryService.getAttendSignTimes(category, date);
	}

	@Override
	public JSONArray formatTrailData(List<SysAttendMain> list,
									 RequestContext requestContext)
			throws Exception {
		JSONArray array = new JSONArray();
		for (SysAttendMain main : list) {
			JSONObject json = new JSONObject();
			Date signTime = main.getDocAlterTime() == null
					? main.getDocCreateTime() : main.getDocAlterTime();
			json.put("fdId", main.getFdId());
			json.put("fdSignedDatetime", DateUtil.convertDateToString(signTime,
					DateUtil.PATTERN_DATETIME));
			json.put("fdSignedTime", DateUtil.convertDateToString(signTime,
					"HH:mm"));
			json.put("fdStatus", main.getFdStatus());
			json.put("fdStatusTxt",
					EnumerationTypeUtil.getColumnEnumsLabel(
							"sysAttendMain_fdStatus",
							String.valueOf(main.getFdStatus())));
			json.put("fdDesc", main.getFdDesc());
			json.put("fdLocation", main.getFdLocation());
			json.put("fdLng", main.getFdLng());
			json.put("fdLat", main.getFdLat());
			if (main.getFdHisCategory() != null) {
				json.put("fdCategoryName", main.getFdHisCategory().getFdName());
			}
			// 获取附件
			JSONArray picArray = new JSONArray();
			ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
			List<SysAttMain> attList = sysAttMainCoreInnerService
					.findByModelKey(
							ModelUtil.getModelClassName(main),
							main.getFdId(), "Attachment");
			for (SysAttMain sysAttMain : attList) {
				String picLink = requestContext.getContextPath()
						+ "/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="
						+ sysAttMain.getFdId();
				picArray.add(picLink);
			}
			json.put("picArr", picArray);
			array.add(json);
		}
		return array;
	}

	@Override
	public HSSFWorkbook buildCustomWorkBook(List list)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.createFreezePane(0, 1);
		
		// 是否会议签到
		boolean isExtendSign = false;
		if(!list.isEmpty()){
			SysAttendMain main = (SysAttendMain) list.get(0);
			SysAttendCategory category =CategoryUtil.getFdCategoryInfo(main);
			if (category != null && StringUtil
					.isNotNull(category.getFdAppId())) {
				isExtendSign = true;
			}
		}
		

		int colNum = 14;

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 10000);
		sheet.setColumnWidth(9, 12000);
		if (isExtendSign) {
			sheet.setColumnWidth(10, 5000);
			sheet.setColumnWidth(11, 5000);
			colNum = 12;
		} else {
			sheet.setColumnWidth(10, 5000);
			sheet.setColumnWidth(11, 5000);
			sheet.setColumnWidth(12, 5000);
			sheet.setColumnWidth(13, 5000);
			// 隐藏标准签到时间列
			sheet.setColumnHidden(4, true);
		}

		workbook.setSheetName(0,
				ResourceUtil.getString("sysAttendMain.export.filename.custom",
						"sys-attend"));

		int rowIndex = 0;
		/* 标题行 */
		HSSFRow titlerow = sheet.createRow(rowIndex++);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);

		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		titleCells[0].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.docCreatorName"));
		titleCells[1].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.dept"));
		titleCells[2].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.category.custom"));
		titleCells[3].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.date"));

		titleCells[6].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdStatus1"));
		titleCells[7].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdSignType1"));
		titleCells[8].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdLocation1"));
		titleCells[9].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.lnglat"));
		titleCells[4].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.shouldTime.custom1"));
		if (isExtendSign) {
			titleCells[5].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.signTime1"));
			titleCells[10].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdClientInfo"));
			titleCells[11].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdDeviceInfo"));
		} else {
			titleCells[5].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.signTime2"));
			titleCells[10].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdWifi"));
			titleCells[11].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdDesc"));
			titleCells[12].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdClientInfo"));
			titleCells[13].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.export.fdDeviceInfo"));
		}

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		contentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				HSSFRow contentrow = sheet.createRow(rowIndex++);
				contentrow.setHeight((short) 400);
				HSSFCell[] contentcells = new HSSFCell[colNum];
				for (int j = 0; j < contentcells.length; j++) {
					contentcells[j] = contentrow.createCell(j);
					contentcells[j].setCellStyle(contentCellStyle);
					contentcells[j].setCellType(CellType.STRING);
				}

				SysAttendMain main = (SysAttendMain) list.get(i);
				SysAttendCategory category =CategoryUtil.getFdCategoryInfo(main);
				contentcells[0]
						.setCellValue(main.getDocCreator().getFdName());
				contentcells[1]
						.setCellValue(
								main.getDocCreator().getFdParent() == null ? ""
										: main.getDocCreator().getFdParent()
												.getFdName());
				contentcells[2]
						.setCellValue(
								category == null ? ""
										: category.getFdName());
				contentcells[3]
						.setCellValue(DateUtil.convertDateToString(
								main.getDocCreateTime(), DateUtil.TYPE_DATE,
								null));
				Date shouldTime = null;
				if (category != null) {
					List rules = category.getFdRule();
					if (rules != null && !rules.isEmpty()) {
						SysAttendCategoryRule rule = (SysAttendCategoryRule) rules
								.get(0);
						if (rule.getFdInTime() != null) {
							shouldTime = rule.getFdInTime();
						} else {
							shouldTime = category.getFdStartTime();
						}
					} else {
						shouldTime = category.getFdStartTime();
					}
				}
				contentcells[4]
						.setCellValue(DateUtil.convertDateToString(
								shouldTime, DateUtil.TYPE_TIME, null));
				contentcells[5]
						.setCellValue(DateUtil.convertDateToString(
								main.getDocCreateTime(), DateUtil.TYPE_TIME,
								null));
				contentcells[6].setCellValue(EnumerationTypeUtil
						.getColumnEnumsLabel("sysAttendMain_fdStatus",
								String.valueOf(main.getFdStatus()), null));
				String signType = "";
				if (StringUtil
						.isNotNull(main.getFdWifiName())) {
					signType = ResourceUtil.getString(
							"sys-attend:sysAttendMain.export.fdSignType.wifi");
				} else if (StringUtil
						.isNotNull(main.getFdLocation())) {
					signType = ResourceUtil.getString(
							"sys-attend:sysAttendMain.export.fdSignType.map");
				}
				contentcells[7].setCellValue(signType);
				contentcells[8].setCellValue(main.getFdLocation());
				contentcells[9].setCellValue(main.getFdLatLng());
				if (isExtendSign) {
					contentcells[10].setCellValue(main.getFdClientInfo());
					contentcells[11].setCellValue(main.getFdDeviceInfo());
				} else {
					contentcells[10].setCellValue(main.getFdWifiName());
					contentcells[11].setCellValue(main.getFdDesc());
					contentcells[12].setCellValue(main.getFdClientInfo());
					contentcells[13].setCellValue(main.getFdDeviceInfo());
				}
			}
		}

		return workbook;
	}
	
	@Override
	public int buildAttendTitle(HSSFWorkbook workbook, HSSFSheet sheet,
								int rowStartIdx) throws Exception {
		if (workbook == null || sheet == null) {
			return -1;
		}
		int colNum = 15;

		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 3000);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 3000);
		sheet.setColumnWidth(9, 10000);
		sheet.setColumnWidth(10, 12000);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 3000);
		sheet.setColumnWidth(14, 5000);

		/* 标题行 */
		sheet.createFreezePane(0, 1);
		HSSFRow titlerow = sheet.createRow(rowStartIdx);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);

		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		titleCells[0].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.docCreatorName"));
		titleCells[1].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.dept"));
		titleCells[2].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.category.attend"));
		titleCells[3].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.date"));
		titleCells[4].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendMain.export.shouldTime.attend"));
		titleCells[5].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.signTime"));
		titleCells[6].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdStatus"));
		titleCells[7].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.fdState"));
		titleCells[8].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdSignType"));
		titleCells[9].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdLocation"));
		titleCells[10].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.lnglat"));
		titleCells[11].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdWifi"));
		titleCells[12].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdDesc"));
		titleCells[13].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdClientInfo"));
		titleCells[14].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendMain.export.fdDeviceInfo"));

		return sheet.getLastRowNum();
	}
	
	@Override
	public int buildAttendContent(HSSFWorkbook workbook, HSSFSheet sheet,
								  int rowStartIdx, List list) throws Exception {
		try {
			if (workbook == null || sheet == null) {
				return -1;
			}
			int colNum = 15;

			/* 内容行 */
			HSSFCellStyle contentCellStyle = workbook.createCellStyle();
			contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
			contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
			contentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));

			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					HSSFRow contentrow = sheet.createRow(rowStartIdx++);
					contentrow.setHeight((short) 400);
					HSSFCell[] contentcells = new HSSFCell[colNum];
					for (int j = 0; j < contentcells.length; j++) {
						contentcells[j] = contentrow.createCell(j);
						contentcells[j].setCellStyle(contentCellStyle);
						contentcells[j].setCellType(CellType.STRING);
					}

					SysAttendMain main = (SysAttendMain) list.get(i);
					if(main ==null){
						continue;
					}
					if(main.getDocCreator() ==null){
						continue;
					}
					SysAttendCategory category =CategoryUtil.getFdCategoryInfo(main);
					if(category ==null){
						continue;
					}
					contentcells[0].setCellValue(main.getDocCreator().getFdName());
					contentcells[1].setCellValue(main.getDocCreator().getFdParent() == null ? "" : main.getDocCreator().getFdParent().getFdName());
					contentcells[2].setCellValue(category== null ? "":category.getFdName());
					contentcells[3].setCellValue(DateUtil.convertDateToString(main.getFdWorkDate(), DateUtil.TYPE_DATE,null));
					Date shouldTime = main.getFdBaseWorkTime();
					boolean isCrossConfig = false;
					boolean isCrossSign = main.getFdIsAcross() != null ? main.getFdIsAcross() : false;
					if (shouldTime != null) {
						isCrossConfig = AttendUtil.getDate(shouldTime, 0).getTime() != AttendUtil.getDate(main.getFdWorkDate(), 0).getTime();
					}
					boolean isTimeArea = category != null && Integer.valueOf(1).equals(category.getFdShiftType());
					if (shouldTime == null) {
						if (isTimeArea) {
							// 排班
							List signList = sysAttendCategoryService
									.getAttendSignTimes(category,
											main.getFdWorkDate(),
											main.getDocCreator(), true);
							if (signList != null && !signList.isEmpty()) {
								SysAttendCategoryWorktime workTime = sysAttendCategoryService
										.getWorkTimeByRecord(signList,
												main.getDocCreateTime(),
												main.getFdWorkType());
								if (workTime != null) {
									if(Integer.valueOf("0").equals(main.getFdWorkType())){
										shouldTime =workTime.getFdStartTime();
									} else {
										shouldTime = workTime.getFdEndTime();
										if (Integer.valueOf(2).equals(
												workTime.getFdOverTimeType())) {
											isCrossConfig = true;
										}
									}
								}
							}

						}
					}
					String shouldStr = DateUtil.convertDateToString(shouldTime, DateUtil.TYPE_TIME, null);
					String signStr = DateUtil.convertDateToString(main.getDocCreateTime(), DateUtil.TYPE_TIME, null);
					// 跨天排班后缀加上 （次日）
					if (isCrossConfig) {
						shouldStr = shouldStr + "(" + ResourceUtil
								.getString(
										"sys-attend:sysAttendMain.fdIsAcross.nextday")
								+ ")";
					}
					// 跨天打卡后缀加上 （次日）
					if (isCrossSign) {
						signStr = signStr + "(" + ResourceUtil
								.getString(
										"sys-attend:sysAttendMain.fdIsAcross.nextday")
								+ ")";
					}
					contentcells[4].setCellValue(shouldStr);
					contentcells[5].setCellValue((main.getFdStatus() != null
									&& main.getFdStatus().intValue() == 0
									&& (main.getFdState() == null
											|| main.getFdState()
													.intValue() != 2))
															? "" : signStr);
					String fdStatusStr = (main.getFdStatus() == 0
							|| main.getFdStatus() == 2
							|| main.getFdStatus() == 3)
							&& main.getFdState() != null
							&& main.getFdState() == 2 ? ResourceUtil.getString(
									"sys-attend:sysAttendMain.fdStatus.ok")
									: (main.getFdStatus() == 1
											&& Boolean.TRUE.equals(main.getFdOutside())
													? ResourceUtil.getString(
															"sys-attend:sysAttendMain.outside")
													: EnumerationTypeUtil
															.getColumnEnumsLabel(
																	"sysAttendMain_fdStatus",
																	String.valueOf(
																			main.getFdStatus()),
																	null));
					if (main.getFdStatus() == 5
							&& main.getFdOffType() != null) {
						String offText = AttendUtil
								.getLeaveTypeText(main.getFdOffType());
						if (StringUtil.isNotNull(offText)) {
							fdStatusStr += "(" + offText + ")";
						}
					}
					contentcells[6].setCellValue(fdStatusStr);
					String fdStateStr = main.getFdState() == null
							&& (main.getFdStatus() == 0
									|| main.getFdStatus() == 2
									|| main.getFdStatus() == 3)
											? ResourceUtil.getString(
													"sys-attend:sysAttendMain.fdState.undo")
											: EnumerationTypeUtil
													.getColumnEnumsLabel(
															"sysAttendMain_fdState",
															String.valueOf(
																	main.getFdState()),
															null);
					contentcells[7].setCellValue(fdStateStr);
					String signType = "";
					if (StringUtil.isNotNull(main.getFdWifiName())) {
						signType = ResourceUtil.getString(
								"sys-attend:sysAttendMain.export.fdSignType.wifi");
					} else if (StringUtil.isNotNull(main.getFdLocation())) {
						signType = ResourceUtil.getString(
								"sys-attend:sysAttendMain.export.fdSignType.map");
					}
					contentcells[8].setCellValue(signType);
					contentcells[9].setCellValue(main.getFdLocation());
					contentcells[10].setCellValue(main.getFdLatLng());
					String wifiInfo = "";
					if (StringUtil.isNotNull(main.getFdWifiName())) {
						wifiInfo += main.getFdWifiName();
					}
					if (StringUtil.isNotNull(main.getFdWifiMacIp())) {
						wifiInfo += "(" + main.getFdWifiMacIp() + ")";
					}
					contentcells[11].setCellValue(wifiInfo);
					contentcells[12].setCellValue(main.getFdDesc());
					contentcells[13].setCellValue(main.getFdClientInfo());
					contentcells[14].setCellValue(main.getFdDeviceInfo());
				}
			}

			return sheet.getLastRowNum();
		} catch (Exception e) {
			logger.error("buildAttendContent Error:" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public HSSFWorkbook buildAttendWorkBook(List list)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(
				ResourceUtil.getString("sysAttendMain.export.filename.attend",
						"sys-attend"));
		int lastIdx = buildAttendTitle(workbook, sheet, 0);
		buildAttendContent(workbook, sheet, lastIdx + 1, list);
		return workbook;
	}

	@Override
	public HSSFWorkbook buildExtendWorkBook(RequestContext requestContext)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		buildNotSignSheet(workbook, requestContext);
		buildSignedSheet(workbook, requestContext);
		return workbook;
	}

	private void buildNotSignSheet(HSSFWorkbook workbook,
			RequestContext requestContext) throws Exception {
		HSSFSheet sheet = workbook.createSheet(ResourceUtil
				.getString("sys-attend:sysAttendMain.notSign.people"));
		sheet.createFreezePane(0, 1);
		sheet.setColumnWidth(0, 4000);
		/* 标题行 */
		int rowIndex = 0;
		HSSFRow titlerow = sheet.createRow(rowIndex++);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);
		HSSFCell titleCell = titlerow.createCell(0);
		titleCell.setCellStyle(titleCellStyle);
		titleCell.setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendCategory.importView.docCreator"));

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		String categoryId = requestContext.getParameter("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category= CategoryUtil.getCategoryById(categoryId);
			HQLInfo hqlInfo = new HQLInfo();
			StringBuilder whereTemp=new StringBuilder("sysAttendMain.fdStatus=0  and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			if(category ==null){
				whereTemp.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
			}else{
				whereTemp.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
			}
			hqlInfo.setWhereBlock(whereTemp.toString());
			hqlInfo.setParameter("categoryId", categoryId);
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime desc,sysAttendMain.fdId desc");
			List list = findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					HSSFRow contentrow = sheet.createRow(rowIndex++);
					contentrow.setHeight((short) 400);
					HSSFCell contentcell = contentrow.createCell(0);
					contentcell.setCellStyle(contentCellStyle);

					SysAttendMain main = (SysAttendMain) list.get(i);
					contentcell.setCellValue(main.getDocCreator().getFdName());
				}
			}
		}
	}

	private void buildSignedSheet(HSSFWorkbook workbook,
			RequestContext requestContext) throws Exception {
		HSSFSheet sheet = workbook.createSheet(ResourceUtil
				.getString("sys-attend:sysAttendMain.signed.people"));
		sheet.createFreezePane(0, 1);

		int colNum = 8;

		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);

		/* 标题行 */
		int rowIndex = 0;
		HSSFRow titlerow = sheet.createRow(rowIndex++);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);

		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		titleCells[0].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendCategory.importView.docCreator"));
		titleCells[1].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendCategory.importView.signTime"));
		titleCells[2].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendMain.fdLocation"));
		titleCells[3].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendCategory.importView.signStatus"));
		titleCells[4].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendMain.fdPersonType"));
		titleCells[5].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendSignPatch.fdIsPatch"));
		titleCells[6].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendSignPatch.fdPatchPerson"));
		titleCells[7].setCellValue(ResourceUtil.getString(
				"sys-attend:sysAttendSignPatch.fdPatchTime"));

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		String categoryId = requestContext.getParameter("categoryId");
		if (StringUtil.isNotNull(categoryId)) {
			SysAttendCategory category= CategoryUtil.getCategoryById(categoryId);
			HQLInfo hqlInfo = new HQLInfo();
			StringBuilder whereTemp=new StringBuilder("  sysAttendMain.fdStatus>0  and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
			if(category ==null){
				whereTemp.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
			}else{
				whereTemp.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
			}
			hqlInfo.setWhereBlock(whereTemp.toString());
			hqlInfo.setParameter("categoryId", categoryId);
			hqlInfo.setOrderBy("sysAttendMain.docCreateTime desc,sysAttendMain.fdId desc");
			List list = findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					HSSFRow contentrow = sheet.createRow(rowIndex++);
					contentrow.setHeight((short) 400);
					HSSFCell[] contentcells = new HSSFCell[colNum];
					for (int j = 0; j < colNum; j++) {
						contentcells[j] = contentrow.createCell(j);
						contentcells[j].setCellStyle(contentCellStyle);
					}

					SysAttendMain main = (SysAttendMain) list.get(i);

					String docCreatorName = "";
					if (main.getFdOutPerson() != null) {
						docCreatorName = main.getFdOutPerson().getFdName() + "("
								+ main.getFdOutPerson().getFdPhoneNum() + ")";
					} else {
						docCreatorName = main.getDocCreator().getFdName();
						if (Boolean.TRUE.equals(main.getFdOutTarget())) {
							docCreatorName += "("
									+ ResourceUtil.getString(
											"sys-attend:sysAttendCategory.importView.outOfZone")
									+ ")";
						}
					}
					contentcells[0].setCellValue(docCreatorName);
					contentcells[1].setCellValue(DateUtil.convertDateToString(
							main.getDocCreateTime(), DateUtil.TYPE_DATETIME,
							null));
					contentcells[2].setCellValue(main.getFdLocation());
					contentcells[3].setCellValue(EnumerationTypeUtil
							.getColumnEnumsLabel("sysAttendMain_fdStatus",
									main.getFdStatus() + ""));
					String fdPersonType = "";
					if(main.getFdOutPerson() != null){
						fdPersonType = ResourceUtil.getString("sys-attend:sysAttendMain.fdPersonType.outer");
					} else if(Boolean.TRUE.equals(main.getFdOutTarget())){
						fdPersonType = ResourceUtil.getString("sys-attend:sysAttendMain.fdPersonType.inner.out");
					} else {
						fdPersonType = ResourceUtil.getString("sys-attend:sysAttendMain.fdPersonType.inner");
					}
					contentcells[4].setCellValue(fdPersonType);
					SysAttendSignPatch signPatch = main.getFdSignPatch();
					contentcells[5].setCellValue(signPatch != null
							? ResourceUtil.getString(
									"sys-attend:sysAttendSignPatch.addPatch")
							: "");
					contentcells[6].setCellValue(signPatch != null
							&& signPatch.getFdPatchPerson() != null
									? signPatch.getFdPatchPerson().getFdName()
									: "");
					contentcells[7].setCellValue(
							signPatch != null ? DateUtil.convertDateToString(
							signPatch.getFdPatchTime(), DateUtil.TYPE_DATETIME,
									null) : "");
				}
			}
		}
	}

	@Override
	public JSONArray findAttendBussList(String categoryId, Date date)
			throws Exception {
		JSONArray result = new JSONArray();
		List<Map<String, Object>> recordList = this.findList(categoryId, date);
		if (!recordList.isEmpty()) {
			List<String> procList = new ArrayList<String>();
			for (Map<String, Object> record : recordList) {
				Integer fdStatus = (Integer) record.get("fdStatus");
				String fdBusinessId = (String) record.get("fdBusinessId");
				if (StringUtil.isNotNull(fdBusinessId) && fdStatus != null
						&& (fdStatus.intValue() == 4 || fdStatus.intValue() == 5
								|| fdStatus.intValue() == 6)) {
					if (!procList.contains(fdBusinessId)) {
						procList.add(fdBusinessId);
						JSONObject json = new JSONObject();
						SysAttendBusiness business = (SysAttendBusiness) sysAttendBusinessService
								.findByPrimaryKey(fdBusinessId);
						json.put("fdBusUrl", business.getDocUrl());
						json.put("fdName", StringUtil
								.getString(business.getFdProcessName()));

						// 判断日期类型
						String pattent = DateUtil.TYPE_DATE;
						if (business.getFdType() == 5
								&& business.getFdStatType() == 3) {
							pattent = DateUtil.PATTERN_DATETIME;
						}
						if (business.getFdType() == 7) {// 注意与打卡状态外出值不一致
							pattent = DateUtil.TYPE_TIME;
						}
						String fdStartTime = business
								.getFdBusStartTime() == null
										? ""
										: DateUtil.convertDateToString(
												business.getFdBusStartTime(),
												pattent, null);

						String fdEndTime = business.getFdBusEndTime() == null
								? ""
								: DateUtil.convertDateToString(
										business.getFdBusEndTime(), pattent,
										null);
						if (business.getFdType() == 5
								&& business.getFdStatType() == 2
								&& StringUtil.isNotNull(fdStartTime)) {
							String am = ResourceUtil.getString(
									"sysAttendMain.buss.am", "sys-attend");
							String pm = ResourceUtil.getString(
									"sysAttendMain.buss.pm", "sys-attend");
							fdStartTime = fdStartTime + " "
									+ (business.getFdStartNoon() == 1 ? am
											: pm);
							fdEndTime = fdEndTime + " "
									+ (business.getFdEndNoon() == 1 ? am : pm);
						}
						json.put("fdType", business.getFdType());
						json.put("fdStartTime",fdStartTime);
						json.put("fdEndTime",fdEndTime);
						json.put("fdType", business.getFdType());
						json.put("fdId", fdBusinessId);
						result.add(json);
					}
				}
			}
		}
		return result;
	}

	@Override
	public JSONArray findAttendBussinessList(Date date)
			throws Exception {
		ISysTimeLeaveDetailService sysTimeLeaveDetailService = (ISysTimeLeaveDetailService) SpringBeanUtil
				.getBean("sysTimeLeaveDetailService");
		JSONArray result = new JSONArray();
		List<String> orgList = new ArrayList<String>();
		String userId = UserUtil.getKMSSUser().getUserId();
		orgList.add(userId);
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[4]);// 出差
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[5]);// 请假
		fdTypes.add(AttendConstant.FD_ATTENDBUS_TYPE[7]);// 外出 注意与打卡状态外出值不一致
		//查询两天的数据
		List<SysAttendBusiness> businessList = sysAttendBusinessService
				.findBussList(orgList, AttendUtil.getDate(date, 0),
						AttendUtil.getDate(date, 2),
						fdTypes);
		// 过滤无效数据
		List<SysAttendBusiness> recordList = new ArrayList<SysAttendBusiness>();
		for (SysAttendBusiness leaveBus : businessList) {
			String leaveId = leaveBus.getFdBusDetailId();
			if (StringUtil.isNull(leaveId)) {
				recordList.add(leaveBus);
				continue;
			}
			if (!Integer.valueOf(5).equals(leaveBus.getFdType())) {
				recordList.add(leaveBus);
				continue;
			}
			SysTimeLeaveDetail leaveDetail = (SysTimeLeaveDetail) sysTimeLeaveDetailService
					.findByPrimaryKey(leaveId, null, true);
			if (leaveDetail != null) {
				if (leaveDetail.getFdTotalTime() > 0) {
					recordList.add(leaveBus);
				}
			}
		}

		if (!recordList.isEmpty()) {
			List<SysAttendBusiness> busList = sysAttendBusinessService
					.genUserBusiness(UserUtil.getUser(userId), date,
							recordList);
			for (SysAttendBusiness business : busList) {
				JSONObject json = new JSONObject();
				json.put("fdBusUrl", business.getDocUrl());
				json.put("fdName", StringUtil
						.getString(business.getFdProcessName()));

				// 判断日期类型
				String pattent = DateUtil.TYPE_DATE;
				if (AttendConstant.FD_ATTENDBUS_TYPE[5]
						.equals(business.getFdType())
						&& AttendConstant.FD_STAT_TYPE[3]
								.equals(business.getFdStatType())) {
					pattent = DateUtil.PATTERN_DATETIME;
				}
				if(AttendConstant.FD_ATTENDBUS_TYPE[4]
						.equals(business.getFdType())
						&& (!AttendUtil.isDay(business.getFdBusStartTime())
						|| !AttendUtil.isDay(business.getFdBusEndTime()))) {
					pattent = DateUtil.PATTERN_DATETIME;
				}
				if (AttendConstant.FD_ATTENDBUS_TYPE[7]
						.equals(business.getFdType())) {// 注意与打卡状态外出值不一致
					pattent = DateUtil.TYPE_TIME;
				}
				String fdStartTime = business
						.getFdBusStartTime() == null
								? ""
								: DateUtil.convertDateToString(
										business.getFdBusStartTime(),
										pattent, null);

				String fdEndTime = business.getFdBusEndTime() == null
						? ""
						: DateUtil.convertDateToString(
								business.getFdBusEndTime(), pattent,
								null);
				if (DateUtil.TYPE_TIME.equals(pattent) && business.getFdBusEndTime() != null && business.getFdBusEndTime()
						.after(AttendUtil.getDate(date, 1))) {
					fdEndTime = fdEndTime + "(" + ResourceUtil.getString(
							"sysAttendMain.fdIsAcross.nextday",
							"sys-attend") + ")";
				}
				if (AttendConstant.FD_ATTENDBUS_TYPE[5]
						.equals(business.getFdType())
						&& AttendConstant.FD_STAT_TYPE[2]
								.equals(business.getFdStatType())
						&& StringUtil.isNotNull(fdStartTime)) {
					String am = ResourceUtil.getString(
							"sysAttendMain.buss.am", "sys-attend");
					String pm = ResourceUtil.getString(
							"sysAttendMain.buss.pm", "sys-attend");
					fdStartTime = fdStartTime + " "
							+ (AttendConstant.FD_NOON_TYPE[1]
									.equals(business.getFdStartNoon()) ? am
									: pm);
					fdEndTime = fdEndTime + " "
							+ (AttendConstant.FD_NOON_TYPE[1]
									.equals(business.getFdEndNoon()) ? am : pm);
				}
				json.put("fdType", business.getFdType());
				json.put("fdStartTime", fdStartTime);
				json.put("fdEndTime", fdEndTime);
				json.put("fdType", business.getFdType());
				json.put("fdId", business.getFdId());
				result.add(json);
			}
		}
		return result;
	}

	@Override
	public List findList(String docCreatorId, Date startTime, Date endTime)
			throws Exception {
		List<String> orgList = new ArrayList<String>();
		orgList.add(docCreatorId);
		List<SysAttendMain> recordList = findList(orgList, startTime, endTime,
				null);
		return recordList;
	}
	
	@Override
	public Map findList(List orgList, Date startTime, Date endTime)
			throws Exception {
		Map<String, List<SysAttendMain>> userMainMap = new HashMap<String, List<SysAttendMain>>();
		List<SysAttendMain> mainList = findList(orgList, startTime, endTime,
				null);
		for (SysAttendMain main : mainList) {
			String orgId = main.getDocCreator().getFdId();
			String fdWorkId = main.getWorkTime() != null ? main.getWorkTime().getFdId() : main.getFdWorkKey();
			if (StringUtil.isNull(fdWorkId) || main.getFdWorkType() == null) {
				continue;
			}
			if (!userMainMap.containsKey(orgId)) {
				userMainMap.put(orgId, new ArrayList<SysAttendMain>());
			}
			List<SysAttendMain> userMainList = userMainMap.get(orgId);
			userMainList.add(main);
		}
		return userMainMap;
	}


	@Override
	public List<SysAttendMain> findList(List orgList, Date startDate,
			Date endDate, List statusList) throws Exception {
		// 在时间区间内获取相关用户的打卡记录
		HQLInfo hqlInfoTwo = new HQLInfo();
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer();
		//去掉考勤历史表关联，加快查询速度
		whereBlock.append(" 1=1 and sysAttendMain.fdWorkType is not null ");
		whereBlock.append(" and (sysAttendMain.fdWorkKey is not null or sysAttendMain.workTime is not null)");
		whereBlock.append(" and " + HQLUtil .buildLogicIN("sysAttendMain.docCreator.fdId", orgList));
		// 考勤状态过滤
		if (statusList != null && !statusList.isEmpty()) {
			whereBlock.append(" and " + HQLUtil.buildLogicIN("sysAttendMain.fdStatus", statusList));
		}
		whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");

		//性能调整、OR的查询分成2次查询
		StringBuffer selectOne=new StringBuffer();
		StringBuffer selectTwo=new StringBuffer();
		selectOne.append(whereBlock);
		selectOne.append(" and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross=:fdIsAcross0)");
		hqlInfo.setParameter("fdIsAcross0", false);
		hqlInfo.setParameter("startTime", AttendUtil.getDate(startDate, 0));
		hqlInfo.setParameter("endTime", AttendUtil.getDate(endDate, 1));
		hqlInfo.setWhereBlock(selectOne.toString());
		hqlInfo.setOrderBy("sysAttendMain.docCreateTime asc");
		List mainListOne = findList(hqlInfo);

		selectTwo.append(whereBlock);
		selectTwo.append(" and sysAttendMain.docCreateTime>=:nextStart and sysAttendMain.docCreateTime<:nextEnd and sysAttendMain.fdIsAcross=:fdIsAcross1 ");
		hqlInfoTwo.setParameter("fdIsAcross1", true);
		hqlInfoTwo.setParameter("nextStart", AttendUtil.getDate(startDate, 1));
		hqlInfoTwo.setParameter("nextEnd", AttendUtil.getDate(endDate, 2));
		hqlInfoTwo.setWhereBlock(selectTwo.toString());
		hqlInfoTwo.setOrderBy("sysAttendMain.docCreateTime asc");
		List mainListTwo = findList(hqlInfoTwo);
		List mainList =new ArrayList();
		if(CollectionUtils.isNotEmpty(mainListOne)){
			mainList.addAll(mainListOne);
		}
		if(CollectionUtils.isNotEmpty(mainListTwo)){
			mainList.addAll(mainListTwo);
		}
		return mainList;
	}
	
	@Override
	public long isExistRecord(List<String> userIds) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" select count(*) from sys_attend_main sysAttendMain ");
		sb.append(" where sysAttendMain.fd_work_type is not null ");
		sb.append(" and (sysAttendMain.fd_work_key is not null or sysAttendMain.fd_work_id is not null)");
		sb.append(" and sysAttendMain.doc_create_time>=? and sysAttendMain.doc_create_time<?");
		sb.append(" and "+HQLUtil.buildLogicIN("sysAttendMain.doc_creator_id",
				userIds));
		NativeQuery sqlQuery = getBaseDao().getHibernateSession().createNativeQuery(sb.toString());
		sqlQuery.setParameter(0, AttendUtil.getDate(new Date(), 0));
		sqlQuery.setParameter(1, AttendUtil.getDate(new Date(), 1));
		List<Object> dlist = sqlQuery.list();
		for (int i = 0; i < dlist.size(); i++) {
			Object o = (Object) dlist.get(i);
			if (o == null) {
				continue;
			} else {
				return Long.parseLong(o.toString());
			}
		}
		return 0L;
	}


	/**
	 * 某些版本sqlserver获取布尔类型为数字
	 * 
	 * @param bValue
	 * @return
	 */
	private Boolean getBooleanField(Object bValue) {
		if (bValue == null) {
			return false;
		}
		if (bValue instanceof Number) {
			return ((Number) bValue).intValue() == 1;
		}
		if (bValue instanceof Boolean) {
			return ((Boolean) bValue).booleanValue();
		}
		return false;
	}


	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	public void setSysAttendBusinessService(
			ISysAttendBusinessService sysAttendBusinessService) {
		this.sysAttendBusinessService = sysAttendBusinessService;
	}

	public void setSysAttendMainExcService(
			ISysAttendMainExcService sysAttendMainExcService) {
		this.sysAttendMainExcService = sysAttendMainExcService;
	}

	public void setSysAttendStatJobService(
			ISysAttendStatJobService sysAttendStatJobService) {
		this.sysAttendStatJobService = sysAttendStatJobService;
	}

	public void setSysAttendSignLogService(ISysAttendSignLogService sysAttendSignLogService) {
		this.sysAttendSignLogService = sysAttendSignLogService;
	}

}
