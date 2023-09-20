package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryTargetChangeDao;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTargetChange;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * 考勤组考勤对象 组织架构变更存储
 * @author wj
 */
public class SysAttendCategoryTargetChangeDaoImp extends BaseDaoImp implements ISysAttendCategoryTargetChangeDao {
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendCategoryTargetChange model = (SysAttendCategoryTargetChange) modelObj;
        if (model.getDocAlteror() == null) {
            model.setDocAlteror(UserUtil.getUser());
        }
        if (model.getDocAlterTime() == null) {
            model.setDocAlterTime(new Date());
        }
        return super.add(model);
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        SysAttendCategoryTargetChange model = (SysAttendCategoryTargetChange) modelObj;
        if (model.getDocAlteror() == null) {
            model.setDocAlteror(UserUtil.getUser());
        }
        if (model.getDocAlterTime() == null) {
            model.setDocAlterTime(new Date());
        }
        super.update(model);
    }

}
