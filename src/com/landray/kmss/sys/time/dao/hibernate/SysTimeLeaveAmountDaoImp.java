package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.time.dao.ISysTimeLeaveAmountDao;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmount;
import com.landray.kmss.sys.time.util.SysTimeUtil;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-12
 */
public class SysTimeLeaveAmountDaoImp extends BaseDaoImp
		implements ISysTimeLeaveAmountDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeLeaveAmount amount = (SysTimeLeaveAmount) modelObj;
		SysOrgPerson person = amount.getFdPerson();
		amount.setAuthArea(SysTimeUtil.getUserAuthArea(person.getFdId()));
		amount.setFdOperator(amount.getDocCreator());
		amount.setDocCreator(person);
		return super.add(modelObj);
	}

}
