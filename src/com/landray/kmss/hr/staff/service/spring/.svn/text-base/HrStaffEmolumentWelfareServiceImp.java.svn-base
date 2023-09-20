package com.landray.kmss.hr.staff.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;

/**
 * 薪酬福利
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffEmolumentWelfareServiceImp extends HrStaffImportServiceImp
		implements IHrStaffEmolumentWelfareService {

	@Override
	public String[] getImportFields() {
		// 工资账户名，工资银行，工资账号，公积金账户，社保号码
		return new String[] { "fdPayrollName", "fdPayrollBank",
				"fdPayrollAccount", "fdSurplusAccount",
				"fdSocialSecurityNumber" };
	}

	@Override
	public String getTypeString() {
		return "薪酬福利";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 检查该员工是否已经有数据
		HrStaffEmolumentWelfare model = (HrStaffEmolumentWelfare) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPersonInfo.fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", model.getFdPersonInfo().getFdId());
		List<HrStaffEmolumentWelfare> list = findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			throw new KmssException(new KmssMessage(ResourceUtil
					.getString("hr-staff:hrStaffEmolumentWelfare.exist")));
		}

		return super.add(modelObj);
	}

	/**
	 * 根据人员id获取对应的薪酬福利数据
	 * 
	 * @param personInfoId
	 * @return
	 * @throws Exception
	 */
	@Override
    public List<HrStaffEmolumentWelfare>
			getEmolumentWelfareByPerson(String personInfoId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock(
				"hrStaffEmolumentWelfare.fdPersonInfo.fdId=:personInfoId");
		hql.setParameter("personInfoId", personInfoId);
		return this.findList(hql);
	}
}
