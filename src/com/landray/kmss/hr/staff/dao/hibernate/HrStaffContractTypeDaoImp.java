package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.dao.IHrStaffContractTypeDao;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.util.UserUtil;

public class HrStaffContractTypeDaoImp extends BaseTreeDaoImp
		implements IHrStaffContractTypeDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrStaffContractType hrStaffContractType = (HrStaffContractType) modelObj;
		hrStaffContractType.setDocCreateTime(new Date());
		hrStaffContractType.setDocCreator(UserUtil.getUser());
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrStaffContractType hrStaffContractType = (HrStaffContractType) modelObj;
		hrStaffContractType.setDocAlterTime(new Date());
		hrStaffContractType.setDocAlteror(UserUtil.getUser());
		super.update(modelObj);
	}
}
