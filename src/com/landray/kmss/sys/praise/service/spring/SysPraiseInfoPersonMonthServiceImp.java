package com.landray.kmss.sys.praise.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.praise.model.SysPraiseInfoPersonMonth;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoPersonMonthService;
import com.landray.kmss.sys.praise.util.SysPraiseInfoCommonUtil;

public class SysPraiseInfoPersonMonthServiceImp extends BaseServiceImp implements ISysPraiseInfoPersonMonthService {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private static String MONTH_SQL = " select fd_person , sum(fd_praise_num) as fd_praise_num , sum(fd_praised_num) as fd_praised_num , sum(fd_pay_num) as fd_pay_num , sum(fd_receive_num) as fd_receive_num , sum(fd_rich_pay) as fd_rich_pay , sum(fd_rich_get) as fd_rich_get , sum(fd_oppose_num) as fd_oppose_num , sum(fd_opposed_num) as fd_opposed_num "
			+ " from sys_praise_i_p_detail  where fd_yearmonth = :str group by fd_person ";

	@Override
	public void executeDetail(Date lastTime, Date nowTime, List<String> orgIds) throws Exception {
		if (orgIds == null || orgIds.isEmpty() || lastTime == null || nowTime == null) {
			return;
		}

		List<String> yearMonthList = SysPraiseInfoCommonUtil.getYearMonthList(lastTime, nowTime);

		for (String str : yearMonthList) {
			updatePersonMonthInfo(str);
		}
		getBaseDao().getHibernateSession().flush();
	}

	private void updatePersonMonthInfo(String ym) throws Exception {
		List<Object[]> list = getBaseDao().getHibernateSession().createNativeQuery(MONTH_SQL).setParameter("str", ym)
				.list();
		String userId = null;
		SysPraiseInfoPersonMonth sysPraiseInfoPersonMonth = null;
		for (Object[] obj : list) {
			userId = obj[0].toString();
			sysPraiseInfoPersonMonth = findInfoByUserTime(userId, ym);
			sysPraiseInfoPersonMonth.setFdPraiseNum(Integer.parseInt(obj[1].toString()));
			sysPraiseInfoPersonMonth.setFdPraisedNum(Long.valueOf(obj[2].toString()));
			sysPraiseInfoPersonMonth.setFdPayNum(Integer.parseInt(obj[3].toString()));
			sysPraiseInfoPersonMonth.setFdReceiveNum(Long.valueOf(obj[4].toString()));
			sysPraiseInfoPersonMonth.setFdRichPay(Integer.parseInt(obj[5].toString()));
			sysPraiseInfoPersonMonth.setFdRichGet(Long.valueOf(obj[6].toString()));
			sysPraiseInfoPersonMonth.setFdOpposeNum(Integer.parseInt(obj[7].toString()));
			sysPraiseInfoPersonMonth.setFdOpposedNum(Long.valueOf(obj[8].toString()));
			update(sysPraiseInfoPersonMonth);
		}
		

	}

	private SysPraiseInfoPersonMonth findInfoByUserTime(String userId, String ym) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysPraiseInfoPersonMonth.fdPerson.fdId = :fdPerson and sysPraiseInfoPersonMonth.fdYearMonth = :fdYearMonth");
		hqlInfo.setRowSize(1);
		hqlInfo.setParameter("fdPerson", userId);
		hqlInfo.setParameter("fdYearMonth", ym);
		SysPraiseInfoPersonMonth sysPraiseInfoPersonMonth = (SysPraiseInfoPersonMonth)findFirstOne(hqlInfo);
		if (sysPraiseInfoPersonMonth == null) {
			sysPraiseInfoPersonMonth = new SysPraiseInfoPersonMonth();
			sysPraiseInfoPersonMonth.setFdPerson(sysOrgCoreService.findByPrimaryKey(userId));
			sysPraiseInfoPersonMonth.setFdYearMonth(ym);
		}

		return sysPraiseInfoPersonMonth;
	}

}
