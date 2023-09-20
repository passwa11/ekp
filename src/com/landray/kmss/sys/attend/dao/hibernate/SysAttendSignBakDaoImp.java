package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.sys.attend.dao.ISysAttendSignBakDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.sys.attend.model.SysAttendSignBak;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
 * 签到记录日志
 * @author wj
 * @date 2021-10-19
 */
public class SysAttendSignBakDaoImp extends BaseDaoImp implements ISysAttendSignBakDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendSignBak sysAttendSignBak = (SysAttendSignBak) modelObj;
        if (sysAttendSignBak.getDocCreator() == null) {
            sysAttendSignBak.setDocCreator(UserUtil.getUser());
        }
        if (sysAttendSignBak.getDocCreateTime() == null) {
            sysAttendSignBak.setDocCreateTime(new Date());
        }
        return super.add(sysAttendSignBak);
    }
}
