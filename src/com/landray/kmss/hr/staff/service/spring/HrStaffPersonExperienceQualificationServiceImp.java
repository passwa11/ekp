package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceQualificationService;

/**
 * 资格证书
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceQualificationServiceImp extends
		HrStaffPersonExperienceBaseServiceImp implements
		IHrStaffPersonExperienceQualificationService {

	@Override
	public String[] getImportFields() {
		// 证书名称、颁发日期、失效日期、颁发单位
		return new String[] { "fdCertificateName", "fdBeginDate", "fdEndDate",
				"fdAwardUnit" };
	}

	@Override
	public String getTypeString() {
		return "资格证书";
	}

}
