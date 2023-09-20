package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCurrencyDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:货币dao实现类
 * @date 2021/5/7
 */
public class EopBasedataCurrencyDaoImp extends BaseDaoImp implements IEopBasedataCurrencyDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCurrency eopBasedataCurrency = (EopBasedataCurrency) modelObj;
        if (eopBasedataCurrency.getDocCreator() == null) {
            eopBasedataCurrency.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCurrency.getDocCreateTime() == null) {
            eopBasedataCurrency.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCurrency);
    }
}
