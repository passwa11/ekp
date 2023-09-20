package com.landray.kmss.third.feishu.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.feishu.dao.IThirdFeishuPersonMappingDao;
import com.landray.kmss.third.feishu.model.ThirdFeishuPersonMapping;

public class ThirdFeishuPersonMappingDaoImp extends BaseDaoImp implements IThirdFeishuPersonMappingDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdFeishuPersonMapping thirdFeishuPersonMapping = (ThirdFeishuPersonMapping) modelObj;
        if (thirdFeishuPersonMapping.getDocCreateTime() == null) {
            thirdFeishuPersonMapping.setDocCreateTime(new Date());
        }
		thirdFeishuPersonMapping.setDocAlterTime(new Date());
        return super.add(thirdFeishuPersonMapping);
    }
}
