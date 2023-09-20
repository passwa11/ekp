package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_Common;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveLastAmount;
import com.landray.kmss.sys.time.model.SysTimeLeaveResume;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveDetailService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveLastAmountService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveResumeService;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.sys.time.util.SysTimeLeaveTimeDto;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-15
 */
public class SysTimeLeaveResumeServiceImp extends BaseServiceImp
		implements ISysTimeLeaveResumeService, ApplicationContextAware {

	private ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService;

	public void setSysTimeLeaveAmountItemService(
			ISysTimeLeaveAmountItemService sysTimeLeaveAmountItemService) {
		this.sysTimeLeaveAmountItemService = sysTimeLeaveAmountItemService;
	}

	private ISysTimeLeaveDetailService sysTimeLeaveDetailService;

	public void setSysTimeLeaveDetailService(
			ISysTimeLeaveDetailService sysTimeLeaveDetailService) {
		this.sysTimeLeaveDetailService = sysTimeLeaveDetailService;
	}

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

	private ISysTimeLeaveLastAmountService sysTimeLeaveLastAmountService;

	public ISysTimeLeaveLastAmountService getSysTimeLeaveLastAmountService() {
		if (sysTimeLeaveLastAmountService == null) {
			sysTimeLeaveLastAmountService = (ISysTimeLeaveLastAmountService) SpringBeanUtil
					.getBean("sysTimeLeaveLastAmountService");
		}
		return sysTimeLeaveLastAmountService;
	}
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String resumeId = super.add(modelObj);
		// 更新请假明细,更新额度数据
		updateLeave(resumeId);
		// 更新考勤
		SysTimeLeaveResume resume = (SysTimeLeaveResume) modelObj;
		if (Boolean.TRUE.equals(resume.getFdIsUpdateAttend())) {
			updateAttend(resumeId);
		}
		return resumeId;
	}

	@Override
	public void updateLeave(String id, Map dataMap)
			throws Exception {
		SysTimeLeaveResume leaveResume = (SysTimeLeaveResume) findByPrimaryKey(
				id);
		// 销假失败，请假明细不存在
		if (leaveResume.getFdLeaveDetail() == null) {
			String msg = ResourceUtil.getString(
					"sys-time:sysTimeLeaveResume.reason.detailNotExist");
			updateResume(leaveResume, 2, msg);
			throw new Exception("销假失败:" + msg);
		}
		// 销假失败，日期重复
		if (checkDateRepeat(leaveResume)) {
			String msg = ResourceUtil.getString(
					"sys-time:sysTimeLeaveDetail.reason.dateRepeat");
			updateResume(leaveResume, 2, msg);
			throw new Exception("销假失败:" + msg);
		}
		// 更新请假明细
		SysTimeLeaveDetail leaveDetail = leaveResume.getFdLeaveDetail();
		// 销假失败，请假明细的分钟数为0
		Float fdTotalTime = leaveDetail.getFdTotalTime();
		if (fdTotalTime == 0) {
			String msg = ResourceUtil.getString(
					"sys-time:sysTimeLeaveDetail.reason.notEnough");
			updateResume(leaveResume, 2,msg);
			throw new Exception("销假失败:" + msg);
		}
		// 销假分钟数
		Float resumeMinus = leaveResume.getFdTotalTime();
		// 销假天数
		Float resumeDays = leaveResume.getFdLeaveTime();
		if (fdTotalTime < resumeMinus) {
			//请假的时长，小于销假的时长 则属于全部销
			resumeMinus = fdTotalTime;
			leaveResume.setFdLeaveTime(leaveDetail.getFdLeaveTime());
			leaveResume.setFdTotalTime(leaveDetail.getFdTotalTime());
		}
		DecimalFormat df = new DecimalFormat("#.000");
		//剩余分钟数 = 请假分钟数 - 销假分钟数
		Double restMinus = Double.parseDouble(df.format(fdTotalTime - resumeMinus));
		// 请假剩余分钟数
		leaveDetail.setFdTotalTime(restMinus.floatValue());
		//剩余分钟数 = 请假分钟数 - 销假分钟数
		Double restDays = Double.parseDouble(df.format(leaveDetail.getFdLeaveTime() - resumeDays));
		//剩余天 =  请假天 - 销假天
		leaveDetail.setFdLeaveTime(restDays.floatValue());
		// 销假总分钟数
		Float resumeRest = leaveDetail.getFdResumeTime() + resumeMinus;
		// 销假总天数
		Float resumeRestDays = leaveDetail.getFdResumeDays() + resumeDays;
		leaveDetail.setFdResumeDays(resumeRestDays);
		leaveDetail.setFdResumeTime(resumeRest);

		SysTimeLeaveRule leaveRule = sysTimeLeaveRuleService.getLeaveRuleByType(
				Integer.valueOf(leaveDetail.getFdLeaveType()));
		if (leaveRule != null
				&& !Boolean.TRUE.equals(leaveRule.getFdIsAmount())) {
			sysTimeLeaveDetailService.update(leaveDetail);
			// 销假成功，未开启额度管理
			updateResume(leaveResume, 1, ResourceUtil.getString(
					"sys-time:sysTimeLeaveDetail.reason.notSetAmount"));
			return;
		}
		// 更新额度数据
		Calendar cal = Calendar.getInstance();
		cal.setTime(leaveDetail.getFdEndTime());
		Integer endYear = cal.get(Calendar.YEAR);
		cal.setTime(new Date());
		Integer nowYear = cal.get(Calendar.YEAR);
		String amountItemId = leaveDetail
				.getSysTimeLeaveAmountItemId();
		SysTimeLeaveAmountItem amountItem = null;
		if (StringUtil.isNotNull(amountItemId)) {
			amountItem = (SysTimeLeaveAmountItem) sysTimeLeaveAmountItemService
					.findByPrimaryKey(amountItemId);
		}
		if (amountItem == null) {
			int year = Math.max(nowYear, endYear);
			amountItem = getAmountItem(leaveDetail.getFdPerson().getFdId(), year,leaveRule.getFdSerialNo());
			if (amountItem == null) {
				amountItem = getAmountItem(
						leaveDetail.getFdPerson().getFdId(), year - 1,
						leaveRule.getFdSerialNo());
			}
		}
		updateAmountItem(amountItem, leaveResume, leaveDetail, resumeMinus,resumeDays, leaveRule);
	}

	/**
	 * 更新假期额度
	 * @param amountItem
	 * @param leaveResume
	 * @param daysMin
	 * @param days
	 * @throws Exception
	 */
	private void updateAmountItem(SysTimeLeaveAmountItem amountItem,
			SysTimeLeaveResume leaveResume, SysTimeLeaveDetail leaveDetail,
			Float daysMin, Float days, SysTimeLeaveRule leaveRule)
			throws Exception {
		if (amountItem == null) {
			sysTimeLeaveDetailService.update(leaveDetail);
			// 销假失败，找不到额度信息
			String msg = ResourceUtil.getString(
					"sys-time:sysTimeLeaveDetail.reason.noAmount");
			updateResume(leaveResume, 2, msg);
			throw new Exception("销假失败:" + msg);
		}
		//上周期剩余额度天
		Float lastRestDay = amountItem.getFdLastRestDay() == null ? 0
				: amountItem.getFdLastRestDay();
		//上周期使用额度天
		Float lastUsedDay = amountItem.getFdLastUsedDay() == null ? 0
				: amountItem.getFdLastUsedDay();
		//剩余额度天
		Float restDay = amountItem.getFdRestDay() == null ? 0
				: amountItem.getFdRestDay();
		//使用额度天
		Float usedDay = amountItem.getFdUsedDay() == null ? 0
				: amountItem.getFdUsedDay();
		// 判断当前销假时额度恢复给上周期还是本周期
		Integer fdStatType = leaveDetail.getFdStatType();
		Integer startNoon = leaveResume.getFdStartNoon();
		Integer endNoon = leaveResume.getFdEndNoon();
		List<Date> dateList = SysTimeUtil.getDateList(fdStatType,
				leaveResume.getFdStartTime(),
				leaveResume.getFdEndTime(), startNoon, endNoon);
		if (dateList.size() < 2) {
			return;
		}
		List<SysTimeLeaveLastAmount> lastAmountList = getSysTimeLeaveLastAmountService()
				.findUserLeaveList(leaveDetail.getFdPerson().getFdId(),
						leaveDetail.getFdId());
		// 上周期应恢复额度
		Float fdShouldLastDays = 0f;
		if (lastAmountList != null && !lastAmountList.isEmpty()) {
			for (int i = 0; i < dateList.size() - 1; i++) {
				Date startTime = dateList.get(i);
				Date date = SysTimeUtil.getDate(startTime, 0);
				Date endTime = SysTimeUtil.getEndDate(date, 0);
				boolean isStartDate = i == 0;
				boolean isEndDate = date
						.equals(SysTimeUtil.getDate(leaveResume.getFdEndTime(),
								0));
				endTime = isEndDate ? leaveResume.getFdEndTime() : endTime;
				// 计算当天销假对应额度
				SysTimeLeaveTimeDto dto = SysTimeUtil.getLeaveTimes(
						leaveDetail.getFdPerson(),
						startTime, endTime,
						isStartDate && fdStatType == 2 ? startNoon : 1,
						isEndDate && fdStatType == 2 ? endNoon : 2,
						leaveRule.getFdStatDayType(), fdStatType,leaveRule.getFdSerialNo());
				Float todayDays = dto.getLeaveTimeDays();
				// 请假时该天扣减上周期额度
				Float lastDay = this.getLastAmountDay(lastAmountList, date);
				if (lastDay == null
						|| SysTimeUtil.compareDecimal(lastDay, 0f) == 0) {
					continue;
				}
				fdShouldLastDays += Math.min(lastDay, todayDays);
				// 计算该天销假时长
				SysTimeLeaveLastAmount amount = this
						.getLeaveLastAmount(lastAmountList, date);
				if (todayDays >= lastDay) {
					amount.setFdTotalDay(0f);
				} else {
					amount.setFdTotalDay(Math.abs(todayDays - lastDay));
				}
				getSysTimeLeaveLastAmountService().update(amount);
			}
		}
		if (fdShouldLastDays > 0 && lastUsedDay > 0) {
			float fdRestDay = days - fdShouldLastDays;
			if (fdRestDay > 0) {
				amountItem.setFdLastRestDay(lastRestDay + fdShouldLastDays);
				amountItem.setFdLastUsedDay(lastUsedDay - fdShouldLastDays);
				amountItem.setFdRestDay(restDay + fdRestDay);
				amountItem.setFdUsedDay(usedDay - fdRestDay);
			} else {
				amountItem.setFdLastRestDay(
						lastRestDay + days);
				amountItem.setFdLastUsedDay(lastUsedDay - days);
			}
		} else {
			amountItem.setFdRestDay(restDay + days);
			amountItem.setFdUsedDay(usedDay - days);
		}
		sysTimeLeaveDetailService.update(leaveDetail);
		sysTimeLeaveAmountItemService.update(amountItem);
		// 销假成功
		updateResume(leaveResume, 1, null);
	}

	/**
	 * 获取某天上周期扣减的额度(天)
	 * 
	 * @param lastAmountList
	 * @param date
	 * @return
	 */
	private Float getLastAmountDay(List<SysTimeLeaveLastAmount> lastAmountList,
			Date date) {
		Float days = 0f;
		if (lastAmountList == null || lastAmountList.isEmpty()) {
			return days;
		}
		for (SysTimeLeaveLastAmount lastAmount : lastAmountList) {
			Date fdStartDate = lastAmount.getFdStartDate();
			Float fdTotalDay = lastAmount.getFdTotalDay();
			if (SysTimeUtil.getDate(date, 0)
					.equals(SysTimeUtil.getDate(fdStartDate, 0))) {
				return fdTotalDay;
			}
		}
		return days;
	}

	private SysTimeLeaveLastAmount getLeaveLastAmount(
			List<SysTimeLeaveLastAmount> lastAmountList, Date date) {
		SysTimeLeaveLastAmount amount = null;
		if (lastAmountList == null || lastAmountList.isEmpty()) {
			return amount;
		}
		for (SysTimeLeaveLastAmount lastAmount : lastAmountList) {
			Date fdStartDate = lastAmount.getFdStartDate();
			if (SysTimeUtil.getDate(date, 0)
					.equals(SysTimeUtil.getDate(fdStartDate, 0))) {
				return lastAmount;
			}
		}
		return amount;
	}

	private SysTimeLeaveAmountItem getAmountItem(String personId, Integer year,
			String fdLeaveType) throws Exception {
		if (year != null && StringUtil.isNotNull(fdLeaveType)
				&& StringUtil.isNotNull(personId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdAmount.fdPerson.fdId = :personId "
							+ "and sysTimeLeaveAmountItem.fdAmount.fdYear = :year "
							+ "and sysTimeLeaveAmountItem.fdLeaveType = :fdLeaveType");
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("year", year);
			hqlInfo.setParameter("fdLeaveType", fdLeaveType);
			SysTimeLeaveAmountItem sysTimeLeaveAmountItem = (SysTimeLeaveAmountItem) sysTimeLeaveAmountItemService.findFirstOne(hqlInfo);
			if (sysTimeLeaveAmountItem != null) {
				return sysTimeLeaveAmountItem;
			}
		}
		return null;
	}

	private Boolean checkDateRepeat(SysTimeLeaveResume leaveResume)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysTimeLeaveResume.fdLeaveDetail.fdId=:detailId "
						+ "and sysTimeLeaveResume.fdId != :exceptId and sysTimeLeaveResume.fdOprStatus = :oprStatus and sysTimeLeaveResume.fdLeaveTime > 0");
		hqlInfo.setParameter("detailId",
				leaveResume.getFdLeaveDetail().getFdId());
		hqlInfo.setParameter("exceptId", leaveResume.getFdId());
		hqlInfo.setParameter("oprStatus", 1);
		List<SysTimeLeaveResume> list = findList(hqlInfo);
		boolean isRepeat = false;
		if (!list.isEmpty()) {
			SysTimeLeaveDetail leaveDetail = leaveResume.getFdLeaveDetail();
			Integer statType = leaveDetail.getFdStatType();
			Map<String, Date> map = getStartAndEndTime(
					leaveResume.getFdStartTime(), leaveResume.getFdEndTime(),
					statType, leaveResume.getFdStartNoon(),
					leaveResume.getFdEndNoon());
			// 当前销假时间区间
			Date resumeStart = (Date) map.get("resumeStart");
			Date resumeEnd = (Date) map.get("resumeEnd");

			for (SysTimeLeaveResume resume : list) {
				Map<String, Date> _map = getStartAndEndTime(
						resume.getFdStartTime(),
						resume.getFdEndTime(), statType,
						resume.getFdStartNoon(), resume.getFdEndNoon());
				Date _resumeStart = (Date) _map.get("resumeStart");
				Date _resumeEnd = (Date) _map.get("resumeEnd");
				if (resumeEnd.getTime() > _resumeStart.getTime()
						&& resumeStart.getTime() < _resumeEnd.getTime()) {
					isRepeat = true;
					break;
				}

			}
		}
		return isRepeat;
	}

	private Map<String, Date> getStartAndEndTime(Date startTime, Date endTime,
			Integer statType, Integer startNoon, Integer endNoon) {
		Map<String, Date> timeMap = new HashMap<String, Date>();
		Date resumeStart = null;
		Date resumeEnd = null;
		if (statType == null || statType == 3) {
			resumeStart = startTime;
			resumeEnd = endTime;
		} else if (statType == 1) {
			resumeStart = SysTimeUtil.getDate(startTime, 0);
			resumeEnd = SysTimeUtil.getDate(endTime, 1);
		} else if (statType == 2) {
			Calendar cal = Calendar.getInstance();
			if (startNoon != null && endNoon != null) {
				resumeStart = SysTimeUtil.getDate(startTime, 0);
				if (startNoon == 2) {
					cal.setTime(resumeStart);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					resumeStart = cal.getTime();
				}
				resumeEnd = SysTimeUtil.getDate(endTime, 0);
				if (endNoon == 1) {
					cal.setTime(resumeEnd);
					cal.set(Calendar.HOUR_OF_DAY, 12);
					resumeEnd = cal.getTime();
				} else {
					cal.setTime(resumeEnd);
					cal.add(Calendar.DATE, 1);
					resumeEnd = cal.getTime();
				}
			} else {
				resumeStart = SysTimeUtil.getDate(startTime, 0);
				resumeEnd = SysTimeUtil.getDate(endTime, 1);
			}
		}
		timeMap.put("resumeStart", resumeStart);
		timeMap.put("resumeEnd", resumeEnd);
		return timeMap;
	}

	private void updateResume(SysTimeLeaveResume leaveResume, Integer oprStatus,
			String oprDesc) throws Exception {
		leaveResume.setFdOprStatus(oprStatus);
		leaveResume.setFdOprDesc(oprDesc);
		update(leaveResume);
	}

	@Override
	public void updateAttend(String id) throws Exception {
		SysTimeLeaveResume leaveResume = (SysTimeLeaveResume) findByPrimaryKey(
				id);
		if (leaveResume == null) {
			throw new Exception("找不到销假明细");
		}
		if (Integer.valueOf(1).equals(leaveResume.getFdOprStatus())) {
			Integer updateStatus = leaveResume.getFdUpdateAttendStatus();
			if (Integer.valueOf(1).equals(updateStatus)) {
				// 已更新过一次的，不再更新
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("leaveResumeId", leaveResume.getFdId());
			applicationContext
					.publishEvent(new Event_Common("updateResume", params));
		}
	}

	@Override
	public void updateLeave(String id) throws Exception {
		updateLeave(id, null);
	}

	@Override
	public List findResumeList(String fdPersonId, String fdLeaveId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysTimeLeaveResume.fdLeaveDetail.fdId=:detailId and sysTimeLeaveResume.fdPerson.fdId=:fdPersonId "
						+ " and sysTimeLeaveResume.fdOprStatus = :oprStatus and sysTimeLeaveResume.fdLeaveTime > 0");
		hqlInfo.setParameter("detailId", fdLeaveId);
		hqlInfo.setParameter("fdPersonId", fdPersonId);
		hqlInfo.setParameter("oprStatus", 1);
		List<SysTimeLeaveResume> list = findList(hqlInfo);
		return list;
	}

}
