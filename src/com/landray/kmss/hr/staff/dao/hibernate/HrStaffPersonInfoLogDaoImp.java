package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.dao.IHrStaffPersonInfoLogDao;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.util.UserUtil;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public class HrStaffPersonInfoLogDaoImp extends BaseDaoImp implements
		IHrStaffPersonInfoLogDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 增加操作者和操作时间
		if (modelObj instanceof HrStaffPersonInfoLog) {
			HrStaffPersonInfoLog log = (HrStaffPersonInfoLog) modelObj;
			log.setFdCreator(UserUtil.getUser());
			log.setFdCreateTime(new Date());
		}
		return super.add(modelObj);
	}
}
