package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.dao.IHrStaffPersonReportDao;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.UserUtil;

/**
 * 统计报表
 * 
 * @author 潘永辉 2017-1-17
 * 
 */
public class HrStaffPersonReportDaoImp extends BaseDaoImp implements
		IHrStaffPersonReportDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 增创建者和创建时间
		if (modelObj instanceof BaseAuthModel) {
			BaseAuthModel baseModel = (BaseAuthModel) modelObj;
			baseModel.setDocCreator(UserUtil.getUser());
			baseModel.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

}
