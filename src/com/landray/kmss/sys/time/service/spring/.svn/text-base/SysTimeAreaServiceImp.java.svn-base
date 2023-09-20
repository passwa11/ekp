package com.landray.kmss.sys.time.service.spring;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.forms.SysTimeAreaForm;
import com.landray.kmss.sys.time.interfaces.ISysTimeCountService;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.model.SysTimeConvert;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeOrgElementTime;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.sys.time.model.SysTimePatchworkTime;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.sys.time.model.SysTimeWorkDetail;
import com.landray.kmss.sys.time.model.SysTimeWorkTime;
import com.landray.kmss.sys.time.service.ISysTimeAreaService;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.sys.time.util.SysTimePersonUtil;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 区域组设置业务接口实现
 */
public class SysTimeAreaServiceImp extends BaseServiceImp implements
		ISysTimeAreaService, ApplicationContextAware {
	private ISysOrgCoreService sysOrgCoreService;
	private ApplicationContext applicationContext;
	private ISysTimeCountService sysTimeCountService;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	public ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil.getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysTimeAreaServiceImp.class);

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {
		if (!(UserUtil.getKMSSUser().isAdmin() || UserUtil
				.checkRole("ROLE_SYS_TIME_TIMEAREA_EDIT"))) {
			String whereBlock = hqlInfo.getWhereBlock();
			if (StringUtil.isNull(whereBlock)) {
				whereBlock = "sysTimeArea.areaAdmins.fdId = :orgId or sysTimeArea.docCreator.fdId = :orgId";
			} else {
				whereBlock = StringUtil
						.linkString(
								whereBlock,
								" and ",
								"(sysTimeArea.areaAdmins.fdId = :orgId or sysTimeArea.docCreator.fdId = :orgId)");
			}

			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("orgId", UserUtil.getKMSSUser()
					.getUserId());
		}

		return super.findPage(hqlInfo);
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeArea sysTimeArea = (SysTimeArea) modelObj;
		SysOrgPerson docCreator = UserUtil.getUser();
		Date now = new Date();
		sysTimeArea.setDocCreator(docCreator);
		sysTimeArea.setDocCreateTime(now);
		String revalue = super.add(sysTimeArea);
		List<SysOrgElement> areaMembers = sysTimeArea.getAreaMembers();
		publishAttendEvent(areaMembers);
		//清空排班组的相关缓存
		clearCache();
		return revalue;
	}

	/**
	 * 排班组相关的缓存
	 */
	private void clearCache(){
		SysTimeUtil.SysTimeAreaOrgChache.clear();
		SysTimeUtil.SysTimeAreaChache.clear();
		SysTimeUtil.SysTimeAreaOrgHolidayChache.clear();
		SysTimeUtil.updateSignTimesCatch();
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {

		SysTimeArea sysTimeArea = (SysTimeArea) modelObj;
		List<SysOrgElement> areaMembers = sysTimeArea.getAreaMembers();
		super.update(modelObj);
		publishAttendEvent(areaMembers);
		//清空排班组的相关缓存
		clearCache();
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		SysTimeArea sysTimeArea = (SysTimeArea) modelObj;
		List<SysOrgElement> areaMembers = sysTimeArea.getAreaMembers();
		publishAttendEvent(areaMembers);
		//清空排班组的相关缓存
		clearCache();
	}

	private void publishAttendEvent(List<SysOrgElement> areaMembers) {
		// 发送事件通知
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fdTimeAreaChange", "true");
		params.put("areaMembers", areaMembers);
		applicationContext.publishEvent(new Event_Common(
				"regenUserAttendMain", params));
	}

	@Override
	public SysTimeAreaForm cloneModel(IBaseModel sysTimeArea, IExtendForm form)
			throws Exception {
		SysTimeArea model = (SysTimeArea) sysTimeArea;
		SysTimeAreaForm sysTimeAreaForm = (SysTimeAreaForm) form;
		List<SysTimeWork> sysTimeWorks = new ArrayList<SysTimeWork>();
		List<SysTimeVacation> sysTimeVacations = new ArrayList<SysTimeVacation>();
		List<SysTimePatchwork> sysTimePatchworks = new ArrayList<SysTimePatchwork>();
		for (SysTimeWork sysTimeWork : model.getSysTimeWorkList()) {
			List<SysTimeWorkTime> sysTimeWorkTimes = new ArrayList<SysTimeWorkTime>();
			SysTimeWork tw = new SysTimeWork();
			tw = (SysTimeWork) sysTimeWork.clone();
			tw.setFdId(
					sysTimeWork.getClass().newInstance().getFdId());
			for (SysTimeWorkTime sysTimeWorkTime : tw
					.getSysTimeWorkTimeList()) {
				SysTimeWorkTime twt = new SysTimeWorkTime();
				twt = (com.landray.kmss.sys.time.model.SysTimeWorkTime) sysTimeWorkTime
						.clone();
				twt.setFdId(
						sysTimeWorkTime.getClass().newInstance().getFdId());
				sysTimeWorkTimes.add(twt);
			}
			tw.setSysTimeWorkTimeList(sysTimeWorkTimes);
			tw.setDocCreateTime(new Date());
			tw.setDocCreator(UserUtil.getKMSSUser().getPerson());

			sysTimeWorks.add(tw);
		}
		for (SysTimePatchwork sysTimePatchwork : model
				.getSysTimePatchworkList()) {
			List<SysTimePatchworkTime> sysTimePatchworkTimes = new ArrayList<SysTimePatchworkTime>();
			SysTimePatchwork pw = new SysTimePatchwork();
			pw = (SysTimePatchwork) sysTimePatchwork.clone();
			pw.setFdId(
					pw.getClass().newInstance().getFdId());
			for (SysTimePatchworkTime sysTimePatchworkTime : pw
					.getSysTimePatchworkTimeList()) {
				SysTimePatchworkTime pwt = new SysTimePatchworkTime();
				pwt = (SysTimePatchworkTime) sysTimePatchworkTime
						.clone();
				pwt.setFdId(
						pwt.getClass().newInstance().getFdId());
				sysTimePatchworkTimes.add(pwt);
			}
			pw.setSysTimePatchworkTimeList(sysTimePatchworkTimes);
			pw.setDocCreateTime(new Date());
			pw.setDocCreator(UserUtil.getKMSSUser().getPerson());
			sysTimePatchworks.add(pw);
		}
		for (SysTimeVacation sysTimeVacation : model
				.getSysTimeVacationList()) {
			SysTimeVacation tv = new SysTimeVacation();
			tv = (SysTimeVacation) sysTimeVacation.clone();
			tv.setFdId(
					tv.getClass().newInstance().getFdId());
			tv.setDocCreateTime(new Date());
			tv.setDocCreator(UserUtil.getKMSSUser().getPerson());
			sysTimeVacations.add(tv);
		}
		sysTimeAreaForm.setSysTimeWorkList(sysTimeWorks);
		sysTimeAreaForm.setSysTimePatchworkList(sysTimePatchworks);
		sysTimeAreaForm.setSysTimeVacationList(sysTimeVacations);
		return sysTimeAreaForm;
	}

	@Override
	public boolean equalList(List<SysTimeWorkDetail> a,
							 List<SysTimeWorkDetail> b) {
		if (a == b) {
			return true;
		}
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				// System.out.println(a.get(i).getHbmWorkStartTime()
				// .equals(b.get(j).getHbmWorkStartTime())
				// + "" +
				// a.get(i).getHbmWorkEndTime()
				// .equals(b.get(j).getHbmWorkEndTime()));
				if (a.get(i).getHbmWorkStartTime()
						.equals(b.get(j).getHbmWorkStartTime()) &&
						a.get(i).getHbmWorkEndTime()
								.equals(b.get(j).getHbmWorkEndTime())) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean dateToWeek(String datetime, String fromWeek, String toWeek)
			throws ParseException {
		boolean isClude = false;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "1", "2", "3", "4", "5", "6", "7" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		datet = f.parse(datetime);
		cal.setTime(datet);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0) {
			w = 0;
		}
		if (Integer.parseInt(fromWeek) <= Integer.parseInt(weekDays[w])
				&& Integer.parseInt(weekDays[w]) <= Integer.parseInt(toWeek)) {
			isClude = true;
		}
		return isClude;
	}

	// 365天数据进行合并
	public List divideDatas(List<SysTimeConvert> list) throws Exception {
		List<SysTimeConvert> converts = new ArrayList<>();
		ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
				.getBean("sysTimeCommonTimeService");
		List<SysTimeCommonTime> commonTimes = commonTimeService
				.findList(new HQLInfo());
		for (int i = 0; i < list.size(); i++) {
			int g = 0;
			SysTimeConvert convertI = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				SysTimeConvert convertJ = list.get(j);
				Date today = DateUtil.convertStringToDate(
						convertJ.getStartDate(), DateUtil.PATTERN_DATE, null);
				Date yesterday = DateUtil.convertStringToDate(
						list.get(j - 1).getStartDate(), DateUtil.PATTERN_DATE,
						null);

				if (convertI == null) {
					break;
				}

				if (convertJ == null || ((DateUtil.getDateNumber(today)
						- DateUtil.getDateNumber(yesterday))
						/ (1000 * 3600 * 24)) != 1) {
					convertI.setEndDate(list.get(j - 1).getEndDate());
					converts.add(convertI);
					i = j - 1;
					break;
				}

				// 相邻两天类型不同
				if (!convertJ.getType().equals(convertI.getType())) {
					convertI.setEndDate(list.get(j - 1).getEndDate());
					converts.add(convertI);
					i = j - 1;
					break;
				}
				// 工作日,补班
				if (("1").equals(convertI.getType())
						&& convertI.getType().equals(convertJ.getType())
						&& equalList(convertI.getClazz().getTimes(),
								convertJ.getClazz().getTimes())) {
					if (!convertI.getFromWeek()
							.equals(convertJ.getFromWeek())
							|| !convertI.getClazz().getName()
									.equals(convertJ.getClazz().getName())
							|| !convertI.getToWeek()
									.equals(convertJ.getToWeek())) {
						i = j - 1;
						convertI.setEndDate(list.get(j - 1).getEndDate());
						converts.add(convertI);
						break;
					}
				}

				// 工作日,补班
				if (convertI.getType().equals(convertJ.getType())
						&& !equalList(convertI.getClazz().getTimes(),
								convertJ.getClazz().getTimes())
						&& !convertI.getClazz().getName()
								.equals(convertJ.getClazz().getName())) {
					if (!convertI.getClazz().getName()
							.equals(convertJ.getClazz().getName())) {

						i = j - 1;
						convertI.setEndDate(list.get(j - 1).getEndDate());
						converts.add(convertI);
						break;
					}
				}
				// J为最后一天
				if (j == list.size() - 1) {
					convertI.setEndDate(list.get(j).getEndDate());
					converts.add(convertI);
					return converts;
				}
			}
			// I为最后一天
			if (i == list.size() - 1
			// && !"true".equals(list.get(i).getInvalid())
			) {
				converts.add(convertI);
				return converts;
			}
		}
		return converts;
	}

	public void addCommonTimes(List<SysTimeConvert> list) throws Exception {
		ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
				.getBean("sysTimeCommonTimeService");
		List<SysTimeCommonTime> commonTimes = commonTimeService
				.findList(new HQLInfo());
		for (SysTimeConvert convert : list) {
			if ("2".equals(convert.getType())
					|| "2".equals(convert.getClazz().getType())) {
				continue;
			}
			int g = 0;
			for (SysTimeCommonTime commonTime : commonTimes) {
				if (equalList(convert.getClazz().getTimes(),
						commonTime.getSysTimeWorkDetails())) {
					g++;
				}
			}
			if (g == 0) {
				SysTimeCommonTime newCommon = new SysTimeCommonTime();
				SysTimeWorkDetail detail = new SysTimeWorkDetail();
				List<SysTimeWorkDetail> details = convert
						.getClazz().getTimes();
				newCommon.setSysTimeWorkDetails(details);
				newCommon.setFdName(convert.getClazz().getName());
				newCommon.setFdWorkTimeColor(
						convert.getClazz().getColor());
				newCommon.setType(convert.getType());
				commonTimeService.add(newCommon);
			}
		}

	}

	private ISysTimeHolidayService holidayService;
	private ISysTimeHolidayService getSysTimeHolidayService() {
		if(holidayService ==null) {
			holidayService = (ISysTimeHolidayService) SpringBeanUtil
					.getBean("sysTimeHolidayService");
		}
		return holidayService;
	}

	private ISysTimeCommonTimeService sysTimeCommonTimeService;
	private ISysTimeCommonTimeService getSysTimeCommonTimeService() {
		if(sysTimeCommonTimeService ==null) {
			sysTimeCommonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
					.getBean("sysTimeCommonTimeService");
		}
		return sysTimeCommonTimeService;
	}
	
	// 完成日历JSON数据存储
	@Override
	public void updateCalendar(String json, String fdId, String fdHolidayId,
							   String fdOperType)
			throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, SysTimeConvert.class);
		List<SysTimeConvert> list = mapper.readValue(json, javaType);
		SysTimeArea sysTimeArea = (SysTimeArea) findByPrimaryKey(fdId);

		if (StringUtil.isNotNull(fdHolidayId)) {
			sysTimeArea.setFdHoliday((SysTimeHoliday) getSysTimeHolidayService().findByPrimaryKey(fdHolidayId));
		}
		List<SysTimeWork> workList = null;
		List<SysTimeVacation> vacationList = null;
		List<SysTimePatchwork> patchList = null;
		Map<String, SysTimeCommonTime> commonName = new HashMap();
		if (sysTimeArea.getFdIsBatchSchedule() || "2".equals(fdOperType)) {
			//按人员排班
			sysTimeArea.setFdIsBatchSchedule(true);
			List<?> orgElementList = SysTimePersonUtil.expandToPerson(sysTimeArea.getAreaMembers());
			List<String> orgIds = SysTimePersonUtil.expandToPersonIds(orgElementList);
			//当前已经存在的排班 人员关系
			List<SysTimeOrgElementTime> currentOrgElementTimeList = this.getOrgElementTimes(orgIds);
			Map<String,SysTimeOrgElementTime> orgElementTimeMap = convertOrgElementTimes(currentOrgElementTimeList);
			List<SysTimeOrgElementTime> orgElementTimeList = new ArrayList<>();
			for (Object object : orgElementList) {
				workList = new ArrayList<>();
				vacationList = new ArrayList<>();
				patchList = new ArrayList<>();
				SysOrgElement sysOrgElement = (SysOrgElement) object;
				//人员跟排班的关系
				SysTimeOrgElementTime orgElementTime =orgElementTimeMap.get(sysOrgElement.getFdId());
				if(orgElementTime ==null) {
					orgElementTime = new SysTimeOrgElementTime();
				}
				orgElementTime.setSysOrgElement(sysOrgElement);

				if (list == null || list.size() == 0) {
					orgElementTime.setSysTimeWorkList(workList);
					orgElementTime.setSysTimePatchworkList(patchList);
					orgElementTime.setSysTimeVacationList(vacationList);
				} else {
					resolveDatas(list, workList, vacationList, patchList,
							commonName, getSysTimeCommonTimeService(), orgElementTime,
							sysOrgElement, true);
					orgElementTime.setSysTimeWorkList(workList);
					orgElementTime.setSysTimePatchworkList(patchList);
					orgElementTime.setSysTimeVacationList(vacationList);
				}
				//区域组
				orgElementTime.setSysTimeArea(sysTimeArea);
				orgElementTimeList.add(orgElementTime);
			}
			sysTimeArea.setOrgElementTimeList(orgElementTimeList);
			update(sysTimeArea);
		} else {
			workList = new ArrayList<>();
			vacationList = new ArrayList<>();
			patchList = new ArrayList<>();
			if (list == null || list.size() == 0) {
				sysTimeArea.setSysTimeWorkList(workList);
				sysTimeArea.setSysTimePatchworkList(patchList);
				sysTimeArea.setSysTimeVacationList(vacationList);
			} else {
				list = divideDatas(list);
				resolveDatas(list, workList, vacationList, patchList,
						commonName,
						getSysTimeCommonTimeService(), null, null, false);
				sysTimeArea.setSysTimeWorkList(workList);
				sysTimeArea.setSysTimePatchworkList(patchList);
				sysTimeArea.setSysTimeVacationList(vacationList);
			}
			update(sysTimeArea);
		}
		if (UserOperHelper.allowLogOper("updateCalendar", getModelName())) {
			UserOperContentHelper.putUpdate(sysTimeArea);
			UserOperHelper.setOperSuccess(true);
		}
	}

	/**
	 * 将json数据解析
	 * 
	 * @param list
	 * @param workList
	 * @param vacationList
	 * @param patchList
	 * @param commonName
	 * @param commonTimeService
	 * @param sysTimeOrgElementTime
	 * @param sysOrgElement
	 * @param isBatch
	 * @throws Exception
	 */
	private void resolveDatas(List<SysTimeConvert> list,
			List<SysTimeWork> workList, List<SysTimeVacation> vacationList,
			List<SysTimePatchwork> patchList,
			Map<String, SysTimeCommonTime> commonName,
			ISysTimeCommonTimeService commonTimeService,
			SysTimeOrgElementTime sysTimeOrgElementTime,
			SysOrgElement sysOrgElement,
			boolean isBatch) throws Exception {
		for (SysTimeConvert convert : list) {
			switch (convert.getType()) {
			// 工作时间
			case "1":
				SysTimeWork tw = new SysTimeWork();
				if (isBatch && sysOrgElement != null) {
					if (!sysOrgElement.getFdId()
							.equals(convert.getElementId())) {
						break;
					}
					tw.setSysTimeOrgElementTime(sysTimeOrgElementTime);
					tw.setFdScheduleDate(DateUtil.convertStringToDate(
							convert.getDate(), DateUtil.TYPE_DATE,null));
				} else {
					tw.setFdWeekStartTime(
							Long.parseLong(convert.getFromWeek()));
					tw.setFdWeekEndTime(Long.parseLong(convert.getToWeek()));
					tw.setFdStartTime(DateUtil
							.convertStringToDate(convert.getStartDate(),
									DateUtil.TYPE_DATE,null));
					tw.setFdEndTime(DateUtil
							.convertStringToDate(convert.getEndDate(),
									DateUtil.TYPE_DATE,null));
				}
				tw.setDocCreator(UserUtil.getKMSSUser().getPerson());
				tw.setTimeType(convert.getClazz().getType());
				tw.setFdTimeWorkColor(
						convert.getClazz().getColor());
				if ("1".equals(convert.getClazz().getType())) {
					//通用
					SysTimeCommonTime ct = (SysTimeCommonTime) commonTimeService
							.findByPrimaryKey(convert.getClazz().getFdId());
					tw.setSysTimeCommonTime(ct);
					tw.setTimeType("1");
				} else {
					//自定义
					if (!commonName
							.containsKey(convert.getClazz().getName())) {
						if ("cla"
								.equals(convert.getClazz().getFdId().substring(0, 3))) {
							SysTimeCommonTime newCommon = new SysTimeCommonTime();
							newCommon.setSysTimeWorkDetails(
									convert.getClazz().getTimes());
							newCommon.setFdName(convert.getClazz().getName());
							newCommon.setStatus("true");
							newCommon.setFdWorkTimeColor(convert.getClazz().getColor());
							newCommon.setType("2");

							setNewProperty(newCommon,convert);
							commonName.put(convert.getClazz().getName(),newCommon);
							commonTimeService.add(newCommon);

							tw.setSysTimeCommonTime(newCommon);
						} else {
							SysTimeCommonTime common = (SysTimeCommonTime) commonTimeService
									.findByPrimaryKey(
											convert.getClazz().getFdId());
							common.setSysTimeWorkDetails(
									convert.getClazz().getTimes());
							common.setFdName(
									convert.getClazz().getName());
							common.setFdWorkTimeColor(
									convert.getClazz().getColor());

							setNewProperty(common,convert);
							tw.setSysTimeCommonTime(common);
						}
					}
					else{
						tw.setSysTimeCommonTime(
								commonName.get(convert.getClazz().getName()));
					}
				}
				workList.add(tw);
				break;
			// 假期
			case "2":
				SysTimeVacation tv = new SysTimeVacation();
				if (isBatch && sysOrgElement != null) {
					if (!sysOrgElement.getFdId()
							.equals(convert.getElementId())) {
						break;
					}
					tv.setSysTimeOrgElementTime(sysTimeOrgElementTime);
					tv.setFdScheduleDate(DateUtil.convertStringToDate(
							convert.getDate(), DateUtil.TYPE_DATE,null));
				} else {
					tv.setFdStartDate(DateUtil
							.convertStringToDate(convert.getStartDate(),
									DateUtil.TYPE_DATE,null));
					tv.setFdEndDate(DateUtil
							.convertStringToDate(convert.getEndDate(),
									DateUtil.TYPE_DATE,null));
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil
							.convertStringToDate(convert.getEndDate(),
									DateUtil.TYPE_DATE,null));
					cal.add(Calendar.HOUR, 23);
					cal.add(Calendar.MINUTE, 59);
					cal.add(Calendar.SECOND, 59);
					tv.setFdEndTime(cal.getTime());
				}
				tv.setFdName(convert.getName());
				tv.setDocCreator(UserUtil.getKMSSUser().getPerson());
				vacationList.add(tv);
				break;
			// 补班
			case "3":
				SysTimePatchwork tp = new SysTimePatchwork();
				if (isBatch && sysOrgElement != null) {
					if (!sysOrgElement.getFdId()
							.equals(convert.getElementId())) {
						break;
					}
					tp.setSysTimeOrgElementTime(sysTimeOrgElementTime);
					tp.setFdScheduleDate(DateUtil.convertStringToDate(
							convert.getDate(), DateUtil.TYPE_DATE,null));
				} else {
					tp.setFdStartTime(DateUtil
							.convertStringToDate(convert.getStartDate(),
									DateUtil.TYPE_DATE,null));
					tp.setFdEndTime(DateUtil
							.convertStringToDate(convert.getEndDate(),
									DateUtil.TYPE_DATE,null));
				}
				tp.setFdPatchWorkColor(convert.getClazz().getColor());
				tp.setFdName(convert.getClazz().getName());
				tp.setDocCreator(UserUtil.getKMSSUser().getPerson());
				if ("1".equals(convert.getClazz().getType())) {
					SysTimeCommonTime ct = (SysTimeCommonTime) commonTimeService
							.findByPrimaryKey(convert.getClazz().getFdId());
					tp.setSysTimeCommonTime(ct);
					tp.setTimeType("1");
				} else {
					if (!commonName
							.containsKey(convert.getClazz().getName())) {
						if ("cla"
								.equals(convert.getClazz().getFdId().substring(0, 3))) {
							SysTimeCommonTime newCommon = new SysTimeCommonTime();
							newCommon.setSysTimeWorkDetails(
									convert.getClazz().getTimes());
							newCommon.setFdName(convert.getClazz().getName());
							newCommon.setStatus("true");
							newCommon.setFdWorkTimeColor(
									convert.getClazz().getColor());
							newCommon.setType("2");
							setNewProperty(newCommon,convert);
							commonName.put(convert.getClazz().getName(),
									newCommon);
									commonTimeService.add(newCommon);
							tp.setSysTimeCommonTime(newCommon);
						} else {
							SysTimeCommonTime common = (SysTimeCommonTime) commonTimeService
									.findByPrimaryKey(
											convert.getClazz()
													.getFdId());
							common.setSysTimeWorkDetails(
									convert.getClazz().getTimes());
							common.setFdName(
									convert.getClazz().getName());
							common.setFdWorkTimeColor(
									convert.getClazz().getColor());
							setNewProperty(common,convert);
							tp.setSysTimeCommonTime(
									common);
								}
					} else {
						tp.setSysTimeCommonTime(
								commonName.get(convert.getClazz().getName()));
					}
				}
				patchList.add(tp);
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 赋值新增加的字段
	 *
	 * @param common
	 * @param convert
	 */
	private void setNewProperty(SysTimeCommonTime common,SysTimeConvert convert){
		//午休开始时间
		if(StringUtil.isNotNull(convert.getClazz().getRestStart())) {
			common.setFdRestStartTime(
					DateUtil.convertStringToDate(
							convert.getClazz().getRestStart(), DateUtil.TYPE_DATE, null));
			common.setFdRestStartType(convert.getClazz().getFdRestStartType());
		}else{
			common.setFdRestStartTime(null);

		}
		//午休结束时间
		if(StringUtil.isNotNull(convert.getClazz().getRestEnd())) {
			common.setFdRestEndTime(
					DateUtil.convertStringToDate(
							convert.getClazz().getRestEnd(), DateUtil.TYPE_DATE, null));
			common.setFdRestEndType(convert.getClazz().getFdRestEndType());
		}else{
			common.setFdRestEndTime(null);
		}
		//统计时 按天还是半天
		if(convert.getClazz().getFdTotalDay() !=null) {
			common.setFdTotalDay(Float.valueOf(convert.getClazz().getFdTotalDay()));
		}else{
			common.setFdTotalDay(1F);
		}
	}


	/**
	 * 将人员跟排班关系对应 到MAP中
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private Map<String,SysTimeOrgElementTime> convertOrgElementTimes(List<SysTimeOrgElementTime> list) throws Exception {
		//保证1个人1条。主要兼容历史数据
		Map<String,SysTimeOrgElementTime> checkMap =new HashMap<>();
		if(CollectionUtils.isNotEmpty(list)) {
			for (SysTimeOrgElementTime sysTimeOrgElementTime : list) {
				String orgId = sysTimeOrgElementTime.getSysOrgElement().getFdId();
				if (checkMap.get(orgId) == null) {
					checkMap.put(orgId, sysTimeOrgElementTime);
				}
			}
		}
		return checkMap;
	}
	/**
	 * 获取人员排班的人员对应关系列表
	 * 只获取按个人排班的数据
	 * @param orgElementList
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysTimeOrgElementTime> getOrgElementTimes(List<String> orgElementList) throws Exception {
		//所有的人员ID
		if(CollectionUtils.isNotEmpty(orgElementList)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(" INNER JOIN sysTimeOrgElementTime.sysTimeArea sysTimeArea  ");
			StringBuilder whereBlock = new StringBuilder();
			whereBlock.append(" sysTimeArea.fdId is not null");
			whereBlock.append(" and ");
			whereBlock.append(HQLUtil.buildLogicIN("sysTimeOrgElementTime.sysOrgElement.fdId", orgElementList));
			hqlInfo.setWhereBlock(whereBlock.toString());
			hqlInfo.setOrderBy("sysTimeOrgElementTime.fdId desc");
			List<SysTimeOrgElementTime>  list = getSysTimeOrgElementDao().findList(hqlInfo);
			if(CollectionUtils.isNotEmpty(list)){
				List<SysTimeOrgElementTime>  resultList=new ArrayList<>();
				//保证1个人1条。主要兼容历史数据,取fdId最大的1条。
				Map<String,Boolean> checkMap =new HashMap<>();
				for (SysTimeOrgElementTime sysTimeOrgElementTime:list ) {
					String orgId = sysTimeOrgElementTime.getSysOrgElement().getFdId();
					if(checkMap.get(orgId)==null){
						checkMap.put(orgId,Boolean.TRUE);
						resultList.add(sysTimeOrgElementTime);
					}
				}
				return resultList;
			}
		}
		return new ArrayList<>();
	}

	/**
	 * 排班人员对象的dao
	 */
	private IBaseDao sysTimeOrgElementDao = null;
	public IBaseDao getSysTimeOrgElementDao() {
		if (sysTimeOrgElementDao == null) {
			sysTimeOrgElementDao = (IBaseDao) SpringBeanUtil
					.getBean("sysTimeOrgElementDao");
		}
		return sysTimeOrgElementDao;
	}

	public ISysTimeCountService getSysTimeCountService(){
		if(sysTimeCountService ==null){
			sysTimeCountService = (ISysTimeCountService) SpringBeanUtil.getBean("sysTimeCountService");
		}
		return sysTimeCountService;
	}
}
