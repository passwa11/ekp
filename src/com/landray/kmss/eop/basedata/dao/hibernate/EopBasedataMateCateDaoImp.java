package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataMateCateDao;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:物料类别dao实现类
 * @date 2021/5/7
 */
public class EopBasedataMateCateDaoImp extends BaseDaoImp implements IEopBasedataMateCateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataMateCate eopBasedataMateCate = (EopBasedataMateCate) modelObj;
        if (eopBasedataMateCate.getDocCreator() == null) {
            eopBasedataMateCate.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataMateCate.getDocCreateTime() == null) {
            eopBasedataMateCate.setDocCreateTime(new Date());
        }
        if(eopBasedataMateCate.getFdStatus() == null){
            eopBasedataMateCate.setFdStatus(0);
        }
        return super.add(eopBasedataMateCate);
    }
}
