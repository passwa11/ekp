package com.landray.kmss.km.smissive.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.smissive.dao.IKmSmissiveNumberDao;
import com.landray.kmss.km.smissive.model.KmSmissiveNumber;
import com.landray.kmss.util.UserUtil;


public class KmSmissiveNumberDaoImp extends BaseDaoImp implements
		IKmSmissiveNumberDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmSmissiveNumber kmSmissiveNumber = (KmSmissiveNumber) modelObj;
		kmSmissiveNumber.setDocCreator(UserUtil.getUser());
		kmSmissiveNumber.setDocCreateTime(new Date());
		return super.add(kmSmissiveNumber);
	}
}
