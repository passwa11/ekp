package com.landray.kmss.hr.ratify.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveDRForm;
import com.landray.kmss.hr.ratify.model.HrRatifyLeave;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.sunbor.web.tag.Page;

public interface IHrRatifyLeaveService extends IHrRatifyMainService {

	public void personSchedulerJob(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 员工关系-离职管理
	 * 
	 * @param hqlInfo
	 * @return
	 * @throws Exception
	 */
	public Page getLeaveManagePage(HttpServletRequest request, HQLInfo hqlInfo)
			throws Exception;

	public void saveLeave(HrRatifyLeaveDRForm drForm) throws Exception;

	public void updateStaffLeaveJob(SysQuartzJobContext context)
			throws Exception;

	public void updateAbandonLeave(String fdStaffId, String fdStatus)
			throws Exception;
	 
	/**
	 * 根据人员修改人员离职信息数据
	 * 如果是待离职的，并且离职时间小于当前，则改成已离职
	 * @param personId
	 * @throws Exception
	 */ 
	public void updateStaffLeavelStatus(HrRatifyLeave level) throws Exception;
	/**
	 * 获取用户实际离职日期小于等于当前，并且状态是1或者空的人员
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public List<HrRatifyLeave> getLevelLists() throws Exception; 
}
