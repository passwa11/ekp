package com.landray.kmss.sys.praise.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.model.SysPraiseInfoDetail;
import com.landray.kmss.sys.praise.model.SysPraiseInfoPersonal;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoDetailService;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonalService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoCommonUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;
import org.hibernate.query.NativeQuery;

public class SysPraiseInfoPersonalServiceImp extends BaseServiceImp implements ISysPraiseInfoPersonalService {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysPraiseInfoDetailService sysPraiseInfoDetailService;

	public void setSysPraiseInfoDetailService(ISysPraiseInfoDetailService sysPraiseInfoDetailService) {
		this.sysPraiseInfoDetailService = sysPraiseInfoDetailService;
	}

	private static String BASE_SQL = "update sys_praise_i_detail set fd_praise_num=:fdPraiseNum , fd_praised_num=:fdPraisedNum , fd_oppose_num=:fdOpposeNum,"
			+ "fd_opposed_num=:fdOpposedNum , fd_rich_pay=:fdRichPay , fd_rich_get=:fdRichGet , fd_pay_num=:fdPayNum , fd_receive_num =:fdReceiveNum ";

	private static String SQL_WEEK = " select  sum(fd_praise_num) as fd_praise_num , sum(fd_praised_num) as fd_praised_num , sum(fd_pay_num) as fd_pay_num , sum(fd_receive_num) as fd_receive_num , sum(fd_rich_pay) as fd_rich_pay , sum(fd_rich_get) as fd_rich_get , sum(fd_oppose_num) as fd_oppose_num , sum(fd_opposed_num) as fd_opposed_num "
			+ " from sys_praise_i_p_detail  where fd_person =:fdPerson and doc_create_time >= :startTime and doc_create_time < :endTime ";

	private static String SQL_MONTH = " select  sum(fd_praise_num) as fd_praise_num , sum(fd_praised_num) as fd_praised_num , sum(fd_pay_num) as fd_pay_num , sum(fd_receive_num) as fd_receive_num , sum(fd_rich_pay) as fd_rich_pay , sum(fd_rich_get) as fd_rich_get , sum(fd_oppose_num) as fd_oppose_num , sum(fd_opposed_num) as fd_opposed_num "
			+ " from sys_praise_i_p_mon  where fd_person =:fdPerson and fd_yearmonth =:ymInfo ";

	private static String SQL_YEAR = " select  sum(fd_praise_num) as fd_praise_num , sum(fd_praised_num) as fd_praised_num , sum(fd_pay_num) as fd_pay_num , sum(fd_receive_num) as fd_receive_num , sum(fd_rich_pay) as fd_rich_pay , sum(fd_rich_get) as fd_rich_get , sum(fd_oppose_num) as fd_oppose_num , sum(fd_opposed_num) as fd_opposed_num "
			+ " from sys_praise_i_p_mon  where fd_person =:fdPerson and fd_yearmonth in (:ymList) ";

	private static String SQL_TOTAL = " select  sum(fd_praise_num) as fd_praise_num , sum(fd_praised_num) as fd_praised_num , sum(fd_pay_num) as fd_pay_num , sum(fd_receive_num) as fd_receive_num , sum(fd_rich_pay) as fd_rich_pay , sum(fd_rich_get) as fd_rich_get , sum(fd_oppose_num) as fd_oppose_num , sum(fd_opposed_num) as fd_opposed_num "
			+ " from sys_praise_i_p_mon  where fd_person =:fdPerson ";

	@Override
	public void executeDetail(Date lastTime, Date nowTime, List<String> orgIds) throws Exception {
		
		updateOldInfos(nowTime, lastTime);
		
		if (lastTime == null || nowTime == null || orgIds == null || orgIds.isEmpty()) {
			return;
		}

		for (String orgId : orgIds) {
			SysPraiseInfoPersonal sysPraiseInfoPersonal = getInfoByPerson(orgId, nowTime);
			updateInfo(sysPraiseInfoPersonal, SysPraiseInfoCommonUtil.WEEK, nowTime);
			updateInfo(sysPraiseInfoPersonal, SysPraiseInfoCommonUtil.MONTH, nowTime);
			updateInfo(sysPraiseInfoPersonal, SysPraiseInfoCommonUtil.YEAR, nowTime);
			updateInfo(sysPraiseInfoPersonal, SysPraiseInfoCommonUtil.TOTAL, nowTime);
			update(sysPraiseInfoPersonal);
		}

	}

	/**
	 * 更新明细信息
	 * 
	 * @param sysPraiseInfoPersonal
	 * @param nowTime
	 * @param total
	 * @throws Exception
	 */
	private void updateInfo(SysPraiseInfoPersonal sysPraiseInfoPersonal, String fdType, Date nowTime) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		if (fdType.equals(SysPraiseInfoCommonUtil.WEEK)) {
			Date start = SysPraiseInfoCommonUtil.getWeekTime(nowTime);
			list = getBaseDao().getHibernateSession().createNativeQuery(SQL_WEEK).setTimestamp("startTime", start)
					.setTimestamp("endTime", nowTime)
					.setParameter("fdPerson", sysPraiseInfoPersonal.getFdPerson().getFdId()).list();
			updateFillInfo(list, sysPraiseInfoPersonal.getFdWeek());
		} else if (fdType.equals(SysPraiseInfoCommonUtil.MONTH)) {
			String ymInfo = SysPraiseInfoCommonUtil.getYearMonth(nowTime);
			list = getBaseDao().getHibernateSession().createNativeQuery(SQL_MONTH).setParameter("ymInfo", ymInfo)
					.setParameter("fdPerson", sysPraiseInfoPersonal.getFdPerson().getFdId()).list();
			updateFillInfo(list, sysPraiseInfoPersonal.getFdMonth());
		} else if (fdType.equals(SysPraiseInfoCommonUtil.YEAR)) {
			Date yearStart = SysPraiseInfoCommonUtil.getNowYearDate(nowTime);
			List<String> ymList = SysPraiseInfoCommonUtil.getYearMonthList(yearStart, nowTime);
			list = getBaseDao().getHibernateSession().createNativeQuery(SQL_YEAR).setParameterList("ymList", ymList)
					.setParameter("fdPerson", sysPraiseInfoPersonal.getFdPerson().getFdId()).list();
			updateFillInfo(list, sysPraiseInfoPersonal.getFdYear());
		} else if (fdType.equals(SysPraiseInfoCommonUtil.TOTAL)) {
			list = getBaseDao().getHibernateSession().createNativeQuery(SQL_TOTAL)
					.setParameter("fdPerson", sysPraiseInfoPersonal.getFdPerson().getFdId()).list();
			updateFillInfo(list, sysPraiseInfoPersonal.getFdTotal());
		}

	}

	private void updateFillInfo(List<Object[]> list, SysPraiseInfoDetail detail) throws Exception {
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			detail.getFdId();
			detail.setFdPraiseNum(obj[0] != null ? Integer.parseInt(obj[0].toString()) : 0);
			detail.setFdPraisedNum(obj[1] != null ? Long.valueOf(obj[1].toString()) : 0L);
			detail.setFdPayNum(obj[2] != null ? Integer.parseInt(obj[2].toString()) : 0);
			detail.setFdReceiveNum(obj[3] != null ? Long.valueOf(obj[3].toString()) : 0L);
			detail.setFdRichPay(obj[4] != null ? Integer.parseInt(obj[4].toString()) : 0);
			detail.setFdRichGet(obj[5] != null ? Long.valueOf(obj[5].toString()) : 0L);
			detail.setFdOpposeNum(obj[6] != null ? Integer.parseInt(obj[6].toString()) : 0);
			detail.setFdOpposedNum(obj[7] != null ? Long.valueOf(obj[7].toString()) : 0L);
			sysPraiseInfoDetailService.update(detail);
		}

	}

	private SysPraiseInfoPersonal getInfoByPerson(String orgId, Date nowTime) throws Exception {
		SysPraiseInfoPersonal sysPraiseInfoPersonal = null;
		List<SysPraiseInfoPersonal> list = getPersonalInfo(orgId);
		if (list.size() > 0) {
			sysPraiseInfoPersonal = list.get(0);
		} else {
			sysPraiseInfoPersonal = createNewPersonal(orgId, nowTime);
		}

		return sysPraiseInfoPersonal;
	}

	private List<SysPraiseInfoPersonal> getPersonalInfo(String orgId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysPraiseInfoPersonal.fdPerson.fdId =:fdPersonId");
		hqlInfo.setRowSize(1);
		hqlInfo.setParameter("fdPersonId", orgId);
		List<SysPraiseInfoPersonal> list = findList(hqlInfo);
		return list;
	}

	/**
	 * 创建新的personal数据
	 * 
	 * @param orgId
	 * @param nowTime
	 * @return
	 * @throws Exception
	 */
	private SysPraiseInfoPersonal createNewPersonal(String orgId, Date nowTime) throws Exception {
		SysPraiseInfoPersonal sysPraiseInfoPersonal = new SysPraiseInfoPersonal();
		sysPraiseInfoPersonal.setFdUpdateTime(nowTime);
		SysOrgElement element = sysOrgCoreService.findByPrimaryKey(orgId);
		sysPraiseInfoPersonal.setFdPerson(element);
		sysPraiseInfoPersonal.setFdWeek(new SysPraiseInfoDetail());
		sysPraiseInfoPersonal.setFdMonth(new SysPraiseInfoDetail());
		sysPraiseInfoPersonal.setFdYear(new SysPraiseInfoDetail());
		sysPraiseInfoPersonal.setFdTotal(new SysPraiseInfoDetail());
		return sysPraiseInfoPersonal;
	}

	/**
	 * 根据时间进行判断是否需要清零行为
	 * 
	 * @param nowTime
	 * @param lastTime
	 */
	private void updateOldInfos(Date nowTime, Date lastTime) {
		// 判断本次更新与上次更新是否同一周
		Boolean isSameWeek = SysPraiseInfoCommonUtil.timeCheck(nowTime, lastTime, SysPraiseInfoCommonUtil.WEEK);
		// 判断本次更新与上次更新是否同一月
		Boolean isSameMonth = SysPraiseInfoCommonUtil.timeCheck(nowTime, lastTime, SysPraiseInfoCommonUtil.MONTH);
		// 判断本次更新与上次更新是否同一年
		Boolean isSameYear = SysPraiseInfoCommonUtil.timeCheck(nowTime, lastTime, SysPraiseInfoCommonUtil.YEAR);

		String sql = null;
		NativeQuery nativeQuery;
		if (!isSameWeek) {
			sql = BASE_SQL
					+ " where sys_praise_i_detail.fd_id in (select sys_praise_i_personal.fd_week from sys_praise_i_personal )";
			nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
			nativeQuery.setInteger("fdPraiseNum", 0);
			nativeQuery.setInteger("fdOpposeNum", 0);
			nativeQuery.setInteger("fdRichPay", 0);
			nativeQuery.setInteger("fdPayNum", 0);
			nativeQuery.setLong("fdPraisedNum", 0L);
			nativeQuery.setLong("fdOpposedNum", 0L);
			nativeQuery.setLong("fdRichGet", 0L);
			nativeQuery.setLong("fdReceiveNum", 0L);
			nativeQuery.addSynchronizedQuerySpace("sys_praise_i_detail");
			nativeQuery.executeUpdate();
		}

		if (!isSameMonth) {
			sql = BASE_SQL
					+ " where sys_praise_i_detail.fd_id in (select sys_praise_i_personal.fd_month from sys_praise_i_personal )";
			nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
			nativeQuery.setInteger("fdPraiseNum", 0);
			nativeQuery.setInteger("fdOpposeNum", 0);
			nativeQuery.setInteger("fdRichPay", 0);
			nativeQuery.setInteger("fdPayNum", 0);
			nativeQuery.setLong("fdPraisedNum", 0L);
			nativeQuery.setLong("fdOpposedNum", 0L);
			nativeQuery.setLong("fdRichGet", 0L);
			nativeQuery.setLong("fdReceiveNum", 0L);
			nativeQuery.addSynchronizedQuerySpace("sys_praise_i_detail");
			nativeQuery.executeUpdate();
		}

		if (!isSameYear) {
			sql = BASE_SQL
					+ " where sys_praise_i_detail.fd_id in (select sys_praise_i_personal.fd_year from sys_praise_i_personal )";
			nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql);
			nativeQuery.setInteger("fdPraiseNum", 0);
			nativeQuery.setInteger("fdOpposeNum", 0);
			nativeQuery.setInteger("fdRichPay", 0);
			nativeQuery.setInteger("fdPayNum", 0);
			nativeQuery.setLong("fdPraisedNum", 0L);
			nativeQuery.setLong("fdOpposedNum", 0L);
			nativeQuery.setLong("fdRichGet", 0L);
			nativeQuery.setLong("fdReceiveNum", 0L);
			nativeQuery.addSynchronizedQuerySpace("sys_praise_i_detail");
			nativeQuery.executeUpdate();
		}
		
		if(!isSameWeek||!isSameMonth||!isSameYear){
			getBaseDao().flushHibernateSession();
		}
	}

	@Override
	public JSONObject getPersonalInfo(String userId, String fdTimeType) throws Exception {
		JSONObject obj = null;
		SysPraiseInfoDetail detail = null;
		if(StringUtil.isNull(fdTimeType)){
			fdTimeType = SysPraiseInfoCommonUtil.TOTAL;
		}
		if (StringUtil.isNotNull(userId)) {
			obj = new JSONObject();
			obj.accumulate("userId", userId);
			Integer fdPraiseNum = 0, fdOpposeNum = 0, fdPayNum = 0, fdRichPay = 0;
			Long fdPraisedNum = 0L, fdOpposedNum = 0L, fdReceiveNum = 0L, fdRichGet = 0L;
			List<SysPraiseInfoPersonal> list = getPersonalInfo(userId);
			if (list.size() > 0) {
				SysPraiseInfoPersonal sysPraiseInfoPersonal = list.get(0);
				if(fdTimeType.equals(SysPraiseInfoCommonUtil.WEEK)){
					detail = sysPraiseInfoPersonal.getFdWeek();
				}else if(fdTimeType.equals(SysPraiseInfoCommonUtil.MONTH)){
					detail = sysPraiseInfoPersonal.getFdMonth();
				}else if(fdTimeType.equals(SysPraiseInfoCommonUtil.YEAR)){
					detail = sysPraiseInfoPersonal.getFdYear();
				}else if(fdTimeType.equals(SysPraiseInfoCommonUtil.TOTAL)){
					detail = sysPraiseInfoPersonal.getFdTotal();
				}
				if(detail!=null){
					fdPraiseNum = detail.getFdPraiseNum();
					fdPraisedNum = detail.getFdPraisedNum();
					fdOpposeNum = detail.getFdOpposeNum();
					fdOpposedNum = detail.getFdOpposedNum();
					fdPayNum = detail.getFdPayNum();
					fdReceiveNum = detail.getFdReceiveNum();
					fdRichPay = detail.getFdRichPay();
					fdRichGet = detail.getFdRichGet();
				}
			}
			obj.accumulate("fdPraiseNum", fdPraiseNum);
			obj.accumulate("fdPraisedNum", fdPraisedNum);
			obj.accumulate("fdOpposeNum", fdOpposeNum);
			obj.accumulate("fdOpposedNum", fdOpposedNum);
			obj.accumulate("fdPayNum", fdPayNum);
			obj.accumulate("fdReceiveNum", fdReceiveNum);
			obj.accumulate("fdRichPay", fdRichPay);
			obj.accumulate("fdRichGet", fdRichGet);

		}
		return obj;
	}

}
