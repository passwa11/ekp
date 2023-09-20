package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.time.dao.ISysTimeElementExDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.time.model.SysTimeElementEx;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysTimeElementExDaoImp extends BaseDaoImp implements ISysTimeElementExDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysTimeElementEx sysTimeElementEx = (SysTimeElementEx) modelObj;
        if (sysTimeElementEx.getDocCreator() == null) {
            sysTimeElementEx.setDocCreator(UserUtil.getUser());
        }
        if (sysTimeElementEx.getDocCreateTime() == null) {
            sysTimeElementEx.setDocCreateTime(new Date());
        }
        return super.add(sysTimeElementEx);
    }
}
