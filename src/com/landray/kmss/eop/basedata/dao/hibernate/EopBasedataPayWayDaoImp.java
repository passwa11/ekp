package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataPayWayDao;
import com.landray.kmss.eop.basedata.model.EopBasedataPayWay;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:付款方式dao实现类
 * @date 2021/5/7
 */
public class EopBasedataPayWayDaoImp extends BaseDaoImp implements IEopBasedataPayWayDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataPayWay eopBasedataPayWay = (EopBasedataPayWay) modelObj;
        if (eopBasedataPayWay.getDocCreator() == null) {
            eopBasedataPayWay.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataPayWay.getDocCreateTime() == null) {
            eopBasedataPayWay.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataPayWay);
    }
}
