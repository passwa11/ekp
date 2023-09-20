package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendCategoryExcNewDao;
import com.landray.kmss.sys.attend.model.SysAttendCategoryExcNew;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wj
 */
public class SysAttendCategoryExcNewDaoImp extends BaseDaoImp implements ISysAttendCategoryExcNewDao {
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendCategoryExcNew model = (SysAttendCategoryExcNew) modelObj;
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
        SysAttendCategoryExcNew model = (SysAttendCategoryExcNew) modelObj;
        if (model.getDocAlteror() == null) {
            model.setDocAlteror(UserUtil.getUser());
        }
        if (model.getDocAlterTime() == null) {
            model.setDocAlterTime(new Date());
        }
        super.update(model);
    }
}
