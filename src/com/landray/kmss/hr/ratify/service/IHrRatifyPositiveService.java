package com.landray.kmss.hr.ratify.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.excel.WorkBook;

public interface IHrRatifyPositiveService extends IHrRatifyMainService {

	public void schedulerOfficial(SysQuartzJobContext context) throws Exception;

	/**
	 * <p>导出</p>
	 * @return
	 * @throws Exception
	 * @author sunj
	 */
	public WorkBook export(List<HrStaffPersonInfo> personInfos, HttpServletRequest request) throws Exception;

	/**
	 * <p>批量调整转正日期</p>
	 * @param fdIds
	 * @param date
	 * @throws Exception
	 * @author sunj
	 */
	public void updatePositiveDate(String fdIds, Date date) throws Exception;

	/**
	 * <p>批量调整试用期限</p>
	 * @param fdIds
	 * @param fdPositiveTrialPeriod
	 * @throws Exception
	 * @author sunj
	 */
	public void updatePositiveTrialPeriod(String fdIds, String fdPositiveTrialPeriod) throws Exception;

	/**
	 * <p>办理转正</p>
	 * @param personId
	 * @param convertStringToDate
	 * @param fdPositiveRemark
	 * @author sunj
	 */
	public void saveTransactionPositive(String personId, Date fdActualPositiveTime, String fdPositiveRemark)
			throws Exception;
}
