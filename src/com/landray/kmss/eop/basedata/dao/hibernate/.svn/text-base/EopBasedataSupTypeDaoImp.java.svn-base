package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataSupTypeDao;
import com.landray.kmss.eop.basedata.model.EopBasedataSupType;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:供应商类型dao实现类
 * @date 2021/5/7
 */
public class EopBasedataSupTypeDaoImp extends BaseDaoImp implements IEopBasedataSupTypeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataSupType eopBasedataSupType = (EopBasedataSupType) modelObj;
        if (eopBasedataSupType.getDocCreator() == null) {
            eopBasedataSupType.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataSupType.getDocCreateTime() == null) {
            eopBasedataSupType.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataSupType);
    }
}
