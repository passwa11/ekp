package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.time.dao.ISysTimeBusinessExDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.time.model.SysTimeBusinessEx;
import com.landray.kmss.common.dao.BaseDaoImp;

public class SysTimeBusinessExDaoImp extends BaseDaoImp implements ISysTimeBusinessExDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysTimeBusinessEx sysTimeBusinessEx = (SysTimeBusinessEx) modelObj;
        if (sysTimeBusinessEx.getDocCreator() == null) {
            sysTimeBusinessEx.setDocCreator(UserUtil.getUser());
        }
        if (sysTimeBusinessEx.getDocCreateTime() == null) {
            sysTimeBusinessEx.setDocCreateTime(new Date());
        }
        return super.add(sysTimeBusinessEx);
    }
}
