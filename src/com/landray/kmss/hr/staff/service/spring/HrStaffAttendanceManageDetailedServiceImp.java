package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.hr.staff.service.IHrStaffAttendanceManageDetailedService;
import com.landray.kmss.util.ResourceUtil;

/**
 * 请假明细
 * 
 * @author 潘永辉 2017-1-11
 * 
 */
public class HrStaffAttendanceManageDetailedServiceImp extends
		HrStaffImportServiceImp implements
		IHrStaffAttendanceManageDetailedService {

	@Override
	public String[] getImportFields() {
		// 请假天数， 相关流程， 开始时间， 结束时间， 请假类型
		return new String[] { "fdLeaveDays", "fdRelatedProcess", "fdBeginDate",
				"fdEndDate", "fdLeaveType" };
	}
	
	@Override
	public List<String> getItemNode() {
		List<String> item = super.getItemNode();
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node5"));
		return item;
	}

	@Override
	public String[] getFieldComments() {
		return new String[] {
				"",
				"这里只需要导入流程的URL即可。如：/km/review/km_review_main/kmReviewMain.do?method=view&fdId=159966af045fbe62f6104bc41129b650" };
	}
	
	@Override
	public String getTypeString() {
		return "请假明细";
	}

}
