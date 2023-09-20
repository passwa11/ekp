package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountItemService;
import com.landray.kmss.util.StringUtil;
import org.hibernate.query.Query;

import java.util.List;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-13
 */
public class SysTimeLeaveAmountItemServiceImp extends BaseServiceImp
		implements ISysTimeLeaveAmountItemService {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeLeaveAmountItem item = (SysTimeLeaveAmountItem) modelObj;
		// 更新下一年的数据
		if (item.getFdAmount() != null) {
			SysTimeLeaveAmountItem nextItem = getAmountItem(
					item.getFdAmount().getFdPerson().getFdId(),
					item.getFdAmount().getFdYear() + 1,
					item.getFdLeaveType());
			if (nextItem != null) {
				nextItem.setFdLastTotalDay(item.getFdRestDay());
				nextItem.setFdLastRestDay(item.getFdRestDay());
				nextItem.setFdLastUsedDay(0f);
				nextItem.setFdLastValidDate(item.getFdValidDate());
				nextItem.setFdIsLastAvail(Boolean.TRUE.equals(item.getFdIsAvail()));
				getBaseDao().update(nextItem);
			}
		}
		return super.add(item);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysTimeLeaveAmountItem item = (SysTimeLeaveAmountItem) modelObj;
		// 更新下一年的数据
		if (item.getFdAmount() != null) {
			SysTimeLeaveAmountItem nextItem = getAmountItem(
					item.getFdAmount().getFdPerson().getFdId(),
					item.getFdAmount().getFdYear() + 1,
					item.getFdLeaveType());
			if (nextItem != null) {
				Float usedDay = nextItem.getFdLastUsedDay();
				// 下一年已使用这一年的假期
				usedDay = usedDay != null && usedDay > 0 ? usedDay : 0f;
				Float restDay = item.getFdRestDay();
				restDay = restDay != null && restDay > 0 ? restDay : 0f;
				Float preUsedDay = item.getFdUsedDay();
				preUsedDay = preUsedDay != null && preUsedDay > 0 ? preUsedDay : 0f;
				//上周期已用额度大于前一年额度的剩余天数，并且上周期已用额度大于等于前一年额度的已用额度时，
				//将上周期已用额度改为上周期已用额度-前一年额度的已用额度
				usedDay = usedDay > restDay && usedDay >= preUsedDay ? usedDay - preUsedDay : usedDay;
				nextItem.setFdLastTotalDay(restDay);
				nextItem.setFdLastRestDay(
						restDay - usedDay);
				nextItem.setFdLastUsedDay(usedDay);
				nextItem.setFdLastValidDate(item.getFdValidDate());
				nextItem.setFdIsLastAvail(Boolean.TRUE.equals(item.getFdIsAvail()));
				getBaseDao().update(nextItem);
			}
		}
		super.update(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysTimeLeaveAmountItem item = (SysTimeLeaveAmountItem) modelObj;
		// 更新下一年的数据
		if (item.getFdAmount() != null) {
			SysTimeLeaveAmountItem nextItem = getAmountItem(
					item.getFdAmount().getFdPerson().getFdId(),
					item.getFdAmount().getFdYear() + 1,
					item.getFdLeaveType());
			if (nextItem != null) {
				nextItem.setFdLastTotalDay(null);
				nextItem.setFdLastRestDay(null);
				nextItem.setFdLastUsedDay(null);
				nextItem.setFdLastValidDate(null);
				nextItem.setFdIsLastAvail(false);
				getBaseDao().update(nextItem);
			}
		}
		super.delete(modelObj);
	}

	/**
	 * 获取人员的假期类型对应的明细
	 * @param personId 人员id
	 * @param year 年份
	 * @param leaveType 假期类型CODE
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysTimeLeaveAmountItem getAmountItem(String personId, Integer year,
			String leaveType) throws Exception {
		if (year != null && StringUtil.isNotNull(leaveType)
				&& StringUtil.isNotNull(personId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdAmount.fdPerson.fdId = :personId "
							+ "and sysTimeLeaveAmountItem.fdAmount.fdYear = :year "
							+ "and sysTimeLeaveAmountItem.fdLeaveType = :fdLeaveType");
			hqlInfo.setParameter("personId", personId);
			hqlInfo.setParameter("year", year);
			hqlInfo.setParameter("fdLeaveType", leaveType);
			SysTimeLeaveAmountItem sysTimeLeaveAmountItem = (SysTimeLeaveAmountItem) findFirstOne(hqlInfo);
			if (sysTimeLeaveAmountItem != null) {
				return sysTimeLeaveAmountItem;
			}
		}
		return null;
	}

	@Override
	public void deleteByName(String leaveName) throws Exception {
		if (StringUtil.isNotNull(leaveName)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"sysTimeLeaveAmountItem.fdLeaveName = :leaveName");
			hqlInfo.setParameter("leaveName", leaveName);
			List<SysTimeLeaveAmountItem> list = findList(hqlInfo);
			if (!list.isEmpty()) {
				for (SysTimeLeaveAmountItem item : list) {
					delete(item);
				}
			}
		}
	}

	@Override
	public void deleteByLeaveType(String leaveType) throws Exception {
		if (StringUtil.isNotNull(leaveType)) {
			String hql = "delete from " + SysTimeLeaveAmountItem.class.getName()
					+ " sysTimeLeaveAmountItem "
					+ " where sysTimeLeaveAmountItem.fdLeaveType = :fdLeaveType";
			Query query = getBaseDao().getHibernateSession().createQuery(hql);
			query.setParameter("fdLeaveType", leaveType);
			query.executeUpdate();
		}
	}
}
