package com.landray.kmss.hr.staff.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.model.HrStaffBaseModel;
import com.landray.kmss.util.UserUtil;

/**
 * 人事档案基类
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public class HrStaffBaseDaoImp extends BaseDaoImp {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 增创建者和创建时间
		if (modelObj instanceof HrStaffBaseModel) {
			HrStaffBaseModel baseModel = (HrStaffBaseModel) modelObj;
			baseModel.setFdCreator(UserUtil.getUser());
			baseModel.setFdCreateTime(new Date());
		}
		return super.add(modelObj);
	}

}
