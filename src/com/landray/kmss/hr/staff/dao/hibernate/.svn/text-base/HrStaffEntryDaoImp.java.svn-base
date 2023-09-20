package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.dao.IHrStaffEntryDao;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.UserUtil;

public class HrStaffEntryDaoImp extends ExtendDataDaoImp implements IHrStaffEntryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrStaffEntry hrStaffEntry = (HrStaffEntry) modelObj;
		if (hrStaffEntry.getDocCreator() == null) {
            hrStaffEntry.setDocCreator(UserUtil.getUser());
        }
		if (hrStaffEntry.getDocCreateTime() == null) {
            hrStaffEntry.setDocCreateTime(new Date());
        }
		return super.add(hrStaffEntry);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrStaffEntry hrStaffEntry = (HrStaffEntry) modelObj;
		if (hrStaffEntry.getDocCreator() == null) {
            hrStaffEntry.setDocCreator(UserUtil.getUser());
        }
		if (hrStaffEntry.getDocCreateTime() == null) {
            hrStaffEntry.setDocCreateTime(new Date());
        }
		super.update(modelObj);
	}

}
