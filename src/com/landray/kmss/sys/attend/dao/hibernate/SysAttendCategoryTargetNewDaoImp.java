package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryTargetNewDao;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTargetNew;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wj
 */
public class SysAttendCategoryTargetNewDaoImp extends BaseDaoImp implements ISysAttendCategoryTargetNewDao {
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendCategoryTargetNew model = (SysAttendCategoryTargetNew) modelObj;
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
        SysAttendCategoryTargetNew model = (SysAttendCategoryTargetNew) modelObj;
        if (model.getDocAlteror() == null) {
            model.setDocAlteror(UserUtil.getUser());
        }
        if (model.getDocAlterTime() == null) {
            model.setDocAlterTime(new Date());
        }
        super.update(model);
    }

}
