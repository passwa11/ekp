package com.landray.kmss.sys.attend.plugin;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;

import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.fssc.expense.model.FsscExpenseMain;
import com.landray.kmss.fssc.expense.service.spring.FsscExpenseMainServiceImp;
import com.landray.kmss.sys.attend.model.SysAttendBusiness;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryRule;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.util.AttendConstant;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy;
import _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxyServiceLocator;
import _26._174._10._10.ormrpc.services.EASLogin.EASLoginProxyProxy;
import client.WSContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
/**
 * 销外出
 * 
 * @author sunny
 * @version 创建时间：2022年11月7日下午2:34:17
 */
public class SysAttendEASListener extends SysAttendListenerCommonImp
		implements IEventListener, IEventMulticasterAware, ApplicationListener<Event_Common> {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendOutgoingResumeListener.class);
	private static final int sys_attend_main_fdType_outgoing = 6;// sys_attend_main
																	// 外出
	private static final int sys_attend_business_fdType_outgoing = 7;// sys_attend_main
																		// 外出
	private static final int fd_type_outgoing_resume = 97;// 销外出
															// sys_attend_business
															// 中 fd_type=7是外出

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter) throws Exception {
		logger.debug("receive SysAttendOutgoingResumeListener,parameter=" + parameter);
		FsscExpenseMain main = (FsscExpenseMain) execution.getMainModel();
		String fdId = main.getFdId();
		com.landray.kmss.fssc.expense.service.spring.FsscExpenseMainServiceImp fsscExpenseMainService = (FsscExpenseMainServiceImp) SpringBeanUtil.getBean("fsscExpenseMainTarget");
		FsscExpenseMain fsscExpenseMain = (FsscExpenseMain) fsscExpenseMainService.findByPrimaryKey(fdId);
		;
		List<FsscExpenseDetail> list = fsscExpenseMain.getFdDetailList();
		JSONArray ja = new JSONArray();
		JSONArray ja1 = new JSONArray();

		JSONObject mainJson = new JSONObject();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();;
		json2.put("companyCode", fsscExpenseMain.getExtendDataModelInfo()==null?"":fsscExpenseMain.getExtendDataModelInfo().getModelData().get("fd_3b4c6ff00430b0_record"));
		json2.put("claimantDept", fsscExpenseMain.getFdClaimantDept()==null?"":fsscExpenseMain.getFdClaimantDept().getFdName());
		json2.put("expenseDeptCode", fsscExpenseMain.getFdExpenseDept()==null?"":fsscExpenseMain.getFdExpenseDept().getFdId());
		json2.put("claimant", fsscExpenseMain.getFdClaimant()==null?"":fsscExpenseMain.getFdClaimant().getFdName());
		json2.put("costCenter", fsscExpenseMain.getFdCostCenter()==null?"":fsscExpenseMain.getFdCostCenter().getFdName());
		json2.put("docNumber", main.getDocNumber());
		json2.put("content", fsscExpenseMain.getFdContent());
		json2.put("content1", fsscExpenseMain.getFdCompany().getFdName());
		json2.put("content2", fsscExpenseMain.getFdCompany().getFdCode());
		json2.put("content3", fsscExpenseMain.getFdCostCenter().getFdCode());
		json2.put("content5", fsscExpenseMain.getFdClaimantDept().getFdNo());
		ExtendDataModelInfo extendDataModelInfo = fsscExpenseMain.getExtendDataModelInfo();
		Map map = fsscExpenseMain.getExtendDataModelInfo().getModelData();
		json2.put("content6", map.get("fd_3b0af60723d916_text"));
		json2.put("person", json3);
		mainJson.put("head", json2);
		for(int i=0;i<list.size();i++){

			JSONObject detail = new JSONObject();
			detail.put("expenseItemCode", list.get(i).getFdExpenseItem()==null?"":
				list.get(i).getFdExpenseItem().getFdCode());
			detail.put("costCenterCode", list.get(i).getFdCostCenter()==null?"":
				list.get(i).getFdCostCenter().getFdCode());
			detail.put("currencyCode", list.get(i).getFdCurrency()==null?"":
				list.get(i).getFdCurrency().getFdCode());
			detail.put("applyMoney", list.get(i).getFdApplyMoney());
			detail.put("invoiceMoney", list.get(i).getFdInvoiceMoney());
			detail.put("inputTaxMoney", list.get(i).getFdInputTaxMoney());
			detail.put("noTaxMoney", list.get(i).getFdNoTaxMoney());
			detail.put("standardMoney", list.get(i).getFdStandardMoney());
			detail.put("use", list.get(i).getFdUse());
			detail.put("budgetStatus", list.get(i).getFdBudgetStatus());
			detail.put("feeStatus", list.get(i).getFdFeeStatus());
			ja1.add(detail);
		}
		mainJson.put("entrys", ja1);
		EASLoginProxyProxy login = new EASLoginProxyProxy();
        WSContext ct = login.login("HG32316", "asd123", "eas",  "HAIGE", "L2", 1);
        if(ct!=null){
           System.out.println("登录成功！" + ct.getSessionId());
        }else{
           System.out.println("登录失败！" );
        }

        //通过代理服务地址类获取代理类对象
        WSOAVoucherToEASFacadeSrvProxy ws = new WSOAVoucherToEASFacadeSrvProxyServiceLocator().getWSOAVoucherToEASFacade();
        ws.voucherSync(mainJson.toString());
        
		
	}

	/**
	 * 重新更新外出数据
	 * 
	 * @param resume
	 * @throws Exception
	 */
	public void updateAttendBusinessByResume(SysAttendBusiness resume) throws Exception {
		Date fdBusStartTime = resume.getFdBusStartTime();
		Date fdBusEndTime = resume.getFdBusEndTime();
		List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(resume.getFdTargets());
		List<String> orgIdList = new ArrayList<String>();
		for (SysOrgPerson p : personList) {
			orgIdList.add(p.getFdId());
		}
		// 获取用户的外出流程
		List<Integer> fdTypes = new ArrayList<Integer>();
		fdTypes.add(sys_attend_business_fdType_outgoing);
		// Date endTime = new Date(fdBusEndTime.getTime());
		// endTime.setSeconds(endTime.getSeconds() + 1);
		List<SysAttendBusiness> busList = this.getSysAttendBusinessService().findBussList(orgIdList, fdBusStartTime,
				fdBusEndTime, fdTypes);
		for (SysOrgPerson person : personList) {
			List<SysAttendBusiness> userBusList = getUserBusList(busList, person);
			if (userBusList.isEmpty()) {
				logger.warn("用户销外出流程处理忽略,原因:该用户未找到外出记录!userName:" + person.getFdName() + ";startTime:" + fdBusStartTime
						+ ";endTime:" + fdBusEndTime);
				continue;
			}
			for (SysAttendBusiness bus : userBusList) {
				// 实际销假时间区间
				Map<String, Object> dateMap = new HashMap<String, Object>();
				boolean isUpdate = convertBusinessInfo(bus, fdBusStartTime, fdBusEndTime, dateMap);
				if (isUpdate) {
					// 保存销外出流程
					SysAttendBusiness userResume = this.cloneBusBiz(resume);
					List<SysOrgElement> newTargets = new ArrayList<SysOrgElement>();
					newTargets.add(person);
					userResume.setFdTargets(newTargets);
					getSysAttendBusinessService().add(userResume);
					// 更新外出打卡记录
					updateSysAttendMain(userResume);
				} else {
					logger.warn("用户" + person.getFdName() + "的销外出流程配置的时间区间没有找到对应的外出记录数据,忽略处理!销外出时间区间:" + fdBusStartTime
							+ "~" + fdBusEndTime);
				}
			}
		}
	}

	/**
	 * 根据本次流程业务数据 和 外出记录进行区间拆分
	 * 
	 * @param bus
	 *            外出对象
	 * @param startTime
	 *            销外出开始时间
	 * @param endTime
	 *            销外出结束时间
	 * @param dateMap
	 * @return
	 * @throws Exception
	 */
	protected boolean checkConvertBusinessInfo(SysAttendBusiness bus, Date startTime, Date endTime,
			Map<String, Object> dateMap) throws Exception {
		boolean result = false;
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		// 实际销时间区间
		if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {
			// 区间内拆分成两个区间
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", AttendUtil.addDate(endTime, Calendar.SECOND, 1));
			result = true;
		} else if (fdBusEndTime.getTime() > startTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime() && fdBusStartTime.getTime() >= startTime.getTime()) {
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < endTime.getTime()) {
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", AttendUtil.addDate(endTime, Calendar.SECOND, 1));
			result = true;
		}
		return result;
	}

	/**
	 * 根据本次流程业务数据 和 外出记录进行区间拆分
	 * 
	 * @param bus
	 *            外出对象
	 * @param startTime
	 *            销外出开始时间
	 * @param endTime
	 *            销外出结束时间
	 * @param dateMap
	 * @return
	 * @throws Exception
	 */
	protected boolean convertBusinessInfo(SysAttendBusiness bus, Date startTime, Date endTime,
			Map<String, Object> dateMap) throws Exception {
		boolean result = false;
		Date fdBusStartTime = bus.getFdBusStartTime();
		Date fdBusEndTime = bus.getFdBusEndTime();
		// 实际销时间区间
		if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {// 区间内拆分成两个区间
			SysAttendBusiness newBus = cloneBusBiz(bus);
			bus.setFdBusEndTime(startTime);
			getSysAttendBusinessService().update(bus);

			newBus.setFdBusStartTime(AttendUtil.addDate(endTime, Calendar.SECOND, 1));
			getSysAttendBusinessService().add(newBus);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", endTime);
			result = true;
		} else if (fdBusEndTime.getTime() > startTime.getTime() && fdBusStartTime.getTime() < startTime.getTime()) {//
			bus.setFdBusEndTime(startTime);
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", startTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() <= endTime.getTime() && fdBusStartTime.getTime() >= startTime.getTime()) {
			bus.setFdDelFlag(1);
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", fdBusEndTime);
			result = true;
		} else if (fdBusEndTime.getTime() > endTime.getTime() && fdBusStartTime.getTime() < endTime.getTime()) {
			// 拆分流程。将开始时间从新的结束时间+1秒开始
			bus.setFdBusStartTime(AttendUtil.addDate(endTime, Calendar.SECOND, 1));
			getSysAttendBusinessService().update(bus);
			dateMap.put("startTime", fdBusStartTime);
			dateMap.put("endTime", endTime);
			result = true;
		}
		return result;
	}

	private SysAttendBusiness cloneBusBiz(SysAttendBusiness bus) {
		if (bus == null) {
			return null;
		}
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(bus.getFdBusStartTime());
		sysAttendBusiness.setFdBusEndTime(bus.getFdBusEndTime());
		sysAttendBusiness.setFdProcessId(bus.getFdProcessId());
		sysAttendBusiness.setFdProcessName(bus.getFdProcessName());
		sysAttendBusiness.setDocUrl(bus.getDocUrl());
		List<SysOrgElement> newTargets = new ArrayList<SysOrgElement>();
		newTargets.addAll(bus.getFdTargets());
		sysAttendBusiness.setFdTargets(newTargets);
		sysAttendBusiness.setFdType(fd_type_outgoing_resume);
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}

	/**
	 * 获取指定用户的外出流程,并过滤重复
	 * 
	 * @param busList
	 * @param person
	 * @return
	 */
	protected List getUserBusList(List<SysAttendBusiness> busList, SysOrgElement person) {
		List<SysAttendBusiness> userBusList = new ArrayList<SysAttendBusiness>();
		if (busList == null || busList.isEmpty()) {
			return userBusList;
		}

		for (SysAttendBusiness bus : busList) {
			List<SysOrgElement> targets = bus.getFdTargets();
			if (targets.contains(person)) {
				userBusList.add(bus);
			}
		}
		// 过滤重复数据
		Map<String, SysAttendBusiness> maps = new HashMap<String, SysAttendBusiness>();
		for (SysAttendBusiness bus : userBusList) {
			Date startTime = bus.getFdBusStartTime();
			Date endTime = bus.getFdBusEndTime();
			// 根据时间参数判断是否重复
			String key = startTime.getTime() + "_" + endTime.getTime();
			maps.put(key, bus);
		}
		List<SysAttendBusiness> newUserBusList = new ArrayList<SysAttendBusiness>();
		for (String key : maps.keySet()) {
			newUserBusList.add(maps.get(key));
		}
		return newUserBusList;
	}

	private void updateSysAttendMain(SysAttendBusiness business) throws Exception {
		try {
			Date fdBusStartTime = business.getFdBusStartTime();
			Date fdBusEndTime = business.getFdBusEndTime();
			List<SysOrgElement> fdTargets = business.getFdTargets();
			List<SysOrgPerson> personList = getSysOrgCoreService().expandToPerson(fdTargets);
			Date today = AttendUtil.getDate(new Date(), 0);
			// 每个人
			for (SysOrgPerson person : personList) {
				List<Date> dateList = getDateList(fdBusStartTime, fdBusEndTime);
				if (dateList.size() < 2) {
					continue;
				}
				// 每天
				for (int i = 0; i < dateList.size() - 1; i++) {
					Date startTime = dateList.get(i);
					Date endTime = dateList.get(i + 1);
					Date date = AttendUtil.getDate(startTime, 0);

					List<SysAttendMain> recordList = getSysAttendMainList(person.getFdId(),
							sys_attend_main_fdType_outgoing, AttendUtil.getDate(date, 1), AttendUtil.getDate(date, 2),
							AttendUtil.getDate(date, 0), AttendUtil.getDate(date, 1), AttendUtil.isZeroDay(startTime));

					if (recordList.isEmpty()) {
						logger.warn("该用户没有查询到相应外出记录,销外出流程忽略!userName:" + person.getFdName());
					}
					for (SysAttendMain record : recordList) {
						Integer fdStatus = record.getFdStatus();
						if (fdStatus != sys_attend_main_fdType_outgoing) {
							continue;// 非外出
						}
						// 标准上下班时间
						Date workDate = record.getFdWorkDate();
						boolean isNeedUpdate = checkAttendedRecordConditions(startTime, endTime, fdBusEndTime, record);
						if (isNeedUpdate) {
							// 处于外出区间
							if (AttendUtil.getDate(workDate, 0).before(today)) {
								// 历史数据
								record.setFdStatus(getAttendStatus(record));
								record.setFdBusiness(null);
								// 休息日、节假日的缺卡记录置为无效
								if (record.getFdStatus() == 0 && record.getFdState() == null
										&& record.getFdDateType() != null && !AttendConstant.FD_DATE_TYPE[0]
												.equals(String.valueOf(record.getFdDateType()))) {
									record.setDocStatus(1);
									record.setFdAlterRecord("销外出流程同步事件置为无效记录");
								}
								getSysAttendMainService().getBaseDao().update(record);
							} else if (today.equals(AttendUtil.getDate(workDate, 0))) {
								// 当天
								record.setFdStatus(getAttendStatus(record));
								record.setFdBusiness(null);
								if (record.getFdStatus() == 0 && record.getFdState() == null) {
									record.setDocStatus(1);
									record.setFdAlterRecord("销外出流程同步事件置为无效记录");
									record.setDocAlterTime(new Date());
								}
								getSysAttendMainService().getBaseDao().update(record);
							} else {
								// 将来
								record.setDocStatus(1);
								record.setFdAlterRecord("销外出流程同步事件置为无效记录");
								record.setDocAlterTime(new Date());
								getSysAttendMainService().getBaseDao().update(record);
							}
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("销外出同步考勤更新打卡记录出错:" + e.getMessage(), e);
		}
	}

	/**
	 * 校验考勤数据是否需要更新
	 * 
	 * @param startTime
	 *            考勤开始时间
	 * @param endTime
	 *            考勤结束时间
	 * @param fdBusEndTime
	 *            外出结束时间
	 * @param record
	 *            当前考勤数据
	 * @return
	 * @throws Exception
	 */
	private boolean checkAttendedRecordConditions(Date startTime, Date endTime, Date fdBusEndTime, SysAttendMain record)
			throws Exception {
		Date docCreateTime = record.getDocCreateTime();
		if (!AttendUtil.isZeroDay(startTime) && docCreateTime.before(startTime)) {
			return false;
		}
		boolean isNeedUpdate = (!docCreateTime.before(startTime) && !docCreateTime.after(endTime));
		if (!isNeedUpdate) {
			SysAttendCategory sysAttendCategory = CategoryUtil.getFdCategoryInfo(record);
			List<Map<String, Object>> signTimeConfigurations = getSysAttendCategoryService()
					.getAttendSignTimes(sysAttendCategory, record.getFdWorkDate(), record.getDocCreator(), true);
			getSysAttendCategoryService().doWorkTimesRender(signTimeConfigurations,
					new ArrayList<SysAttendMain>(Arrays.asList(new SysAttendMain[] { record })));
			for (Map<String, Object> signTimeConfiguration : signTimeConfigurations) {
				Date fdEndTime = (Date) signTimeConfiguration.get("fdEndTime");
				boolean isSameWorkTime = getSysAttendCategoryService().isSameWorkTime(signTimeConfiguration,
						record.getWorkTime() == null ? "" : record.getWorkTime().getFdId(), record.getFdWorkType(),
						record.getFdWorkKey());
				if (isSameWorkTime && isOverTimeType(signTimeConfiguration)) {
					// 判断结束时间是23:59:59秒 是流程结束，则把跨天的也处理掉
					// fdBusEndTime = AttendUtil.removeSecond(fdBusEndTime);
					if (AttendUtil.isZeroDay(fdBusEndTime)) {
						fdBusEndTime = AttendUtil.addDate(fdBusEndTime, 1);
					} else {
						// 跨天排班的下班打卡时间
						Date lastOffworkTime = AttendUtil.joinYMDandHMS(fdBusEndTime,
								(Date) signTimeConfiguration.get("signTime"));
						// 比较销假结束时间是否在跨天排班下班打卡时间之内
						if (fdBusEndTime.before(lastOffworkTime) && docCreateTime.after(fdBusEndTime)) {
							return false;
						}
					}
					Date fdBusEndTime_ = AttendUtil.joinYMDandHMS(fdBusEndTime, fdEndTime);
					isNeedUpdate = docCreateTime.before(fdBusEndTime_);
				}
			}
		}
		return isNeedUpdate;
	}

	/**
	 * 重新计算打卡记录的状态
	 * 
	 * @param main
	 * @return
	 */
	private Integer getAttendStatus(SysAttendMain main) {
		try {
			SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
			SysAttendCategoryRule rule = category.getFdRule().get(0);
			Boolean fdIsFlex = category.getFdIsFlex();
			Integer fdFlexTime = category.getFdFlexTime();
			Integer fdLeftTime = rule.getFdLeftTime();
			Integer fdLateTime = rule.getFdLateTime();
			Date doCreateTime = main.getDocCreateTime();
			Integer fdStatus = 1;
			if (StringUtil.isNull(main.getFdLocation()) && StringUtil.isNull(main.getFdWifiName())
					&& main.getFdAppName() == null) {// 缺卡
				fdStatus = 0;
				return fdStatus;
			}
			// 正常打卡时间点
			Date normalSignTime = null;
			if (main.getWorkTime() != null) {
				if (Integer.valueOf(0).equals(main.getFdWorkType())) {
					normalSignTime = main.getWorkTime().getFdStartTime();
				} else {
					normalSignTime = main.getWorkTime().getFdEndTime();
				}
			} else if (StringUtil.isNotNull(main.getFdWorkKey())) {// 排班
				Date date = AttendUtil.getDate(main.getDocCreateTime(), 0);
				List<Map<String, Object>> signTimeList = getSignTimeList(category, date, main.getDocCreator());
				if (!signTimeList.isEmpty()) {
					normalSignTime = getNormalSignTime(main, signTimeList);
				}
			}
			if (normalSignTime != null) {
				int signMins = AttendUtil.getHMinutes(doCreateTime);
				int normalMins = AttendUtil.getHMinutes(normalSignTime);
				List<Map<String, Object>> signTimeList = getSysAttendCategoryService()
						.getAttendSignTimes(main.getDocCreator(), main.getFdWorkDate());
				getSysAttendCategoryService().doWorkTimesRender(signTimeList,
						new ArrayList<SysAttendMain>(Arrays.asList(new SysAttendMain[] { main })));
				signMins += (Boolean.TRUE.equals(main.getFdIsAcross()) ? 24 * 60 : 0);
				for (Map<String, Object> signTimeConfiguration : signTimeList) {
					boolean isSameWorkTime = getSysAttendCategoryService().isSameWorkTime(signTimeConfiguration,
							main.getWorkTime() == null ? "" : main.getWorkTime().getFdId(), main.getFdWorkType(),
							main.getFdWorkKey());
					if (isSameWorkTime && isOverTimeType(signTimeConfiguration)) {
						normalMins += 24 * 60;
						break;
					}
				}
				if (Integer.valueOf(0).equals(main.getFdWorkType())) {
					if (Boolean.TRUE.equals(fdIsFlex) && fdFlexTime != null) {
						if (signMins - normalMins > fdFlexTime) {
							fdStatus = 2;
						}
					} else if (fdLeftTime != null) {
						if (signMins - normalMins > fdLeftTime) {
							fdStatus = 2;
						}
					}
				} else if (Integer.valueOf(1).equals(main.getFdWorkType())) {
					if (fdLateTime != null) {
						if (normalMins - signMins > fdLateTime) {
							fdStatus = 3;
						}
					} else {
						if (normalMins > signMins) {
							fdStatus = 3;
						}
					}
				}
			}
			return fdStatus;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取正常的签到点
	 * 
	 * @param main
	 * @param signTimeList
	 * @return
	 */
	private Date getNormalSignTime(SysAttendMain main, List<Map<String, Object>> signTimeList) {
		Date normalSignTime = null;

		Integer fdWorkType = main.getFdWorkType();
		Timestamp fdSignedTime = new Timestamp(main.getDocCreateTime().getTime());

		int fdSignedTimeMins = AttendUtil.getHMinutes(fdSignedTime);
		int workCount = signTimeList.size() / 2;
		for (int i = 0; i < workCount; i++) {
			Map<String, Object> startMap = signTimeList.get(2 * i); // 上班
			Map<String, Object> endMap = signTimeList.get(2 * i + 1); // 下班
			Date startSignTime = (Date) startMap.get("signTime");
			Date endSignTime = (Date) endMap.get("signTime");

			if (Integer.valueOf(0).equals(fdWorkType)) {
				if (fdSignedTimeMins < AttendUtil.getHMinutes(endSignTime)) {
					normalSignTime = startSignTime;
				}
			} else {
				if ((i + 1) < workCount) {// 是否存在下班次
					Map<String, Object> nStartMap = signTimeList.get(2 * (i + 1));
					Date nStartSignTime = (Date) nStartMap.get("signTime");
					if (fdSignedTimeMins > AttendUtil.getHMinutes(startSignTime)
							&& fdSignedTimeMins < AttendUtil.getHMinutes(nStartSignTime)) {
						normalSignTime = endSignTime;
						break;
					}
				} else {
					if (fdSignedTimeMins > AttendUtil.getHMinutes(startSignTime)) {
						normalSignTime = endSignTime;
						break;
					}
				}
			}
		}

		return normalSignTime;
	}

	/**
	 * 获取班次信息
	 * 
	 * @param category
	 * @param date
	 * @param org
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> getSignTimeList(SysAttendCategory category, Date date, SysOrgElement org)
			throws Exception {
		List<Map<String, Object>> signTimeList = new ArrayList<Map<String, Object>>();
		if (category == null || date == null || org == null) {
			return signTimeList;
		}

		Date signDate = AttendUtil.getDate(date, 0);
		signTimeList = getSysAttendCategoryService().getAttendSignTimes(category, signDate, org);

		if (signTimeList.isEmpty() && Integer.valueOf(1).equals(category.getFdShiftType())) {
			for (int i = 0; i < 30; i++) {// 尝试获取最近一次的班次信息
				signTimeList = getSysAttendCategoryService().getAttendSignTimes(category,
						AttendUtil.getDate(signDate, 1), org);
				if (!signTimeList.isEmpty()) {
					return signTimeList;
				}
			}
		}
		return signTimeList;
	}

	private SysAttendBusiness getBusinessModel(Date fdBusStartTime, Date fdBusEndTime, String targetIds,
			String fdProcessId, Float fdCountHour, String fdProcessName, IBaseModel model) throws Exception {
		SysAttendBusiness sysAttendBusiness = new SysAttendBusiness();
		sysAttendBusiness.setFdBusStartTime(fdBusStartTime);
		sysAttendBusiness.setFdBusEndTime(fdBusEndTime);
		sysAttendBusiness.setFdProcessId(fdProcessId);
		sysAttendBusiness.setFdProcessName(fdProcessName);
		sysAttendBusiness.setDocUrl(AttendUtil.getDictUrl(model, fdProcessId));
		// 去重
		String[] ids = targetIds.split(";");
		List<String> list = Arrays.asList(ids);
		Set set = new HashSet(list);
		String[] sIds = (String[]) set.toArray(new String[0]);
		sysAttendBusiness.setFdTargets(getSysOrgCoreService().findByPrimaryKeys(sIds));
		sysAttendBusiness.setFdType(sys_attend_business_fdType_outgoing);
		sysAttendBusiness.setFdCountHour(fdCountHour);
		sysAttendBusiness.setDocCreateTime(new Date());
		return sysAttendBusiness;
	}

	@Override
	public void onApplicationEvent(Event_Common event) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private IEventMulticaster multicaster;

	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}

	protected List<SysAttendBusiness> getBusinessList(JSONObject params, IBaseModel mainModel) throws Exception {
		try {
			List<SysAttendBusiness> busList = new ArrayList<SysAttendBusiness>();
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();

			String docSubject = (String) getSysMetadataParser().getFieldValue(mainModel, "docSubject", false);

			JSONObject targetsJson = JSONObject.fromObject(params.get("fdOutTargets"));
			JSONObject dateJson = JSONObject.fromObject(params.get("fdOutDate"));
			JSONObject startTimeJson = JSONObject.fromObject(params.get("fdOutStartTime"));
			JSONObject endTimeJson = JSONObject.fromObject(params.get("fdOutEndTime"));
			
			String targetsFieldName = (String) targetsJson.get("value");
			String dateFieldName = (String) dateJson.get("value");
			String startFieldName = (String) startTimeJson.get("value");
			String endFieldName = (String) endTimeJson.get("value");

			boolean isOutOthersConfig = false;
			boolean isCountHourConfig = false;
			String outOthersFieldName = null;
			String countHourFieldName = null;

			JSONObject othersJson = null;
			JSONObject countHourJson = null;
			if (params.containsKey("fdOutOthers")) {// 其他人员非必填
				String fdOutOthers = params.getString("fdOutOthers");
				if (StringUtil.isNotNull(fdOutOthers)) {
					othersJson = JSONObject.fromObject(params.get("fdOutOthers"));
					outOthersFieldName = (String) othersJson.get("value");
					isOutOthersConfig = true;
				}
			}

			if (params.containsKey("fdCountHour")) {// 其他人员非必填
				String fdCountHour = params.getString("fdCountHour");
				if (StringUtil.isNotNull(fdCountHour)) {
					countHourJson = JSONObject.fromObject(params.get("fdCountHour"));
					countHourFieldName = (String) countHourJson.get("value");
					isCountHourConfig = true;
				}
			}

			// 是否明细表
			boolean isDateList = dateFieldName.indexOf(".") >= 0;
			boolean isStartList = startFieldName.indexOf(".") >= 0;
			boolean isEndList = endFieldName.indexOf(".") >= 0;

			if (isDateList && isStartList && isEndList) {
				String detailName = dateFieldName.split("\\.")[0];
				
				String dateName = dateFieldName.split("\\.")[1];
				String startName = startFieldName.split("\\.")[1];
				String endName = endFieldName.split("\\.")[1];
				String targetsName = targetsFieldName.split("\\.")[1];
				
				String outOthersName = null;
				String countHourName = null;
				
				List<HashMap<String, Object>> detailList = (ArrayList<HashMap<String, Object>>) modelData
						.get(detailName);

				for (int k = 0; k < detailList.size(); k++) {
					HashMap detail = detailList.get(k);
					// 日期
					Date date = (Date) detail.get(dateName);
					// 开始时间
					Date startTime = (Date) detail.get(startName);
					// 结束时间
					Date endTime = (Date) detail.get(endName);

					if (date == null || startTime == null || endTime == null
							|| startTime.getTime() >= endTime.getTime()) {
						continue;
					}

					Date fdStartTime = AttendUtil.joinYMDandHMS(date, startTime);
					Date fdEndTime = AttendUtil.joinYMDandHMS(date, endTime);

					// 外出工时
					Float fdCountHour = null;
					if (isCountHourConfig) {
						String fdCountHourType = (String) countHourJson.get("model");

						if ("BigDecimal[]".equals(fdCountHourType) || "Double[]".equals(fdCountHourType)) {
							fdCountHour = getFdCountHour(detail.get(countHourName));
						}
					}
					// 人员
					String targetIds = null;
					Map<String, Object> targetIdObject = (HashMap<String, Object>)detail.get(targetsName);
					if(targetIdObject != null){
						targetIds = (String)targetIdObject.get("id");
					}
					
					// 其他外出人员
					String otherIds = null;
					if (isOutOthersConfig) {
						String othersType = (String) othersJson.get("model");
						if ("SysOrgPerson[]".indexOf(othersType) > -1) {// 如果是数组
							List<HashMap<String, Object>> person_list = (List<HashMap<String, Object>>) detail
									.get(outOthersName);
							if (person_list.size() > 0) {
								String[] other_person = new String[person_list.size()];
								for (int ki = 0; ki < person_list.size(); ki++) {
									if(person_list.get(ki) != null){
										Map<String, Object> sysOrgPerson = (HashMap<String, Object>)person_list.get(ki);
										other_person[ki] = (String)sysOrgPerson.get("id");
									}
								}
								otherIds = other_person.toString().replace(",", ";");
							}
						} else {
							Map<String, Object> otherIdObject = (HashMap<String, Object>)detail.get(outOthersName);
							if(otherIdObject != null){
								otherIds = (String)otherIdObject.get("id");
							}
						}
					}

					if (targetIds == null && otherIds == null) {
						logger.warn("外出流程同步考勤事件中外出人为空!");
						return null;
					}
					if (targetIds != null && otherIds != null) {
						targetIds += ";" + otherIds;
					}
					if (targetIds == null && otherIds != null) {
						targetIds = otherIds;
					}
					if (fdCountHour == null) {
						fdCountHour = (fdEndTime.getTime() - fdStartTime.getTime()) / 3600000.0f;
					}

					busList.add(getBusinessModel(fdStartTime, fdEndTime, targetIds, model.getFdId(), fdCountHour,
							docSubject, model));
				}

			} else {
				// 日期
				Date date = (Date) getSysMetadataParser().getFieldValue(mainModel, dateFieldName, false);
				// 开始时间
				Date startTime = (Date) getSysMetadataParser().getFieldValue(mainModel, startFieldName, false);
				// 结束时间
				Date endTime = (Date) getSysMetadataParser().getFieldValue(mainModel, endFieldName, false);

				if (date == null || startTime == null || endTime == null || startTime.getTime() >= endTime.getTime()) {
					return null;
				}
				
				// 其他外出人员
				String otherIds = null;
				if (modelData.containsKey(outOthersFieldName)) {
					Object othersObj = modelData.get(outOthersFieldName);
					otherIds = BeanUtils.getProperty(othersObj, "id");
				} else {
					SysOrgElement org = (SysOrgElement) PropertyUtils.getProperty(mainModel, outOthersFieldName);
					otherIds = org.getFdId();
				}
				
				// 人员
				String targetIds = null;
				if (modelData.containsKey(targetsFieldName)) {
					Object targetsObj = modelData.get(targetsFieldName);
					targetIds = BeanUtils.getProperty(targetsObj, "id");
				} else {
					SysOrgElement org = (SysOrgElement) PropertyUtils.getProperty(mainModel, targetsFieldName);
					targetIds = org.getFdId();
				}

				if (targetIds == null && otherIds == null) {
					logger.warn("外出流程同步考勤事件中外出人为空!");
					return null;
				}
				if (targetIds != null && otherIds != null) {
					targetIds += ";" + otherIds;
				}
				if (targetIds == null && otherIds != null) {
					targetIds = otherIds;
				}
				
				Date fdStartTime = AttendUtil.joinYMDandHMS(date, startTime);
				Date fdEndTime = AttendUtil.joinYMDandHMS(date, endTime);

				// 外出工时
				Float fdCountHour = null;
				if (isCountHourConfig) {
					String fdCountHourType = (String) countHourJson.get("model");
					if ("BigDecimal".equals(fdCountHourType) || "Double".equals(fdCountHourType)) {
						Object countHour = getSysMetadataParser().getFieldValue(mainModel, countHourFieldName, false);
						fdCountHour = getFdCountHour(countHour);
					}
				}

				if (fdCountHour == null) {
					fdCountHour = (fdEndTime.getTime() - fdStartTime.getTime()) / 3600000.0f;
				}

				busList.add(getBusinessModel(fdStartTime, fdEndTime, targetIds, model.getFdId(), fdCountHour,
						docSubject, model));
			}

			return busList;
		} catch (Exception e) {
			logger.error("获取外出数据出错:" + e.getMessage(), e);
			return null;
		}
	}

	private Float getFdCountHour(Object value) {
		if (value != null) {
			if (value instanceof Double) {
				return ((Double) value).floatValue();
			}
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).floatValue();
			}
		}
		return null;
	}
}
