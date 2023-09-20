package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingErrorDao;
import com.landray.kmss.third.ding.model.ThirdDingError;

public class ThirdDingErrorDaoImp extends BaseDaoImp implements IThirdDingErrorDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		ThirdDingError error = (ThirdDingError) modelObj;
		if (error.getFdCount() == null) {
            error.setFdCount(1);
        }
		return super.add(modelObj);
	}
}
