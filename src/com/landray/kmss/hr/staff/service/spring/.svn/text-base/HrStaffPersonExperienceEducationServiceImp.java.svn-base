package com.landray.kmss.hr.staff.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceEducationService;
import com.landray.kmss.util.ResourceUtil;

/**
 * 教育记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceEducationServiceImp extends
		HrStaffPersonExperienceBaseServiceImp implements
		IHrStaffPersonExperienceEducationService { 
 
	@Override
	public String[] getImportFields() {
		// 学校名称、 专业、学历、学位、入学时间、毕业时间、备注
		return new String[] {"fdBeginDate", "fdEndDate", "fdSchoolName", "fdMajor", "fdEducation","fdDegree",
				 "fdMemo" };
	}
	@Override
	public List<String> getCheckFields() {
		// 学历、学位 
		List<String> checkFileds = new ArrayList<String>();
		checkFileds.add("fdEducation");
		checkFileds.add("fdDegree");
		return checkFileds;
	} 
	
	@Override
	public List<String> getItemNode() {
		List<String> item = super.getItemNode();
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.import.sheet2.item.node4"));
		return item;
	}

	@Override
	public String getTypeString() {
		return "教育记录";
	} 
}
