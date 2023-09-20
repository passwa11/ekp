package com.landray.kmss.sys.attend.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attend.dao.ISysAttendHisCategoryDao;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wj
 */
public class SysAttendHisCategoryDaoImp extends BaseDaoImp implements ISysAttendHisCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttendHisCategory sysAttendHisCategory = (SysAttendHisCategory) modelObj;
        if (sysAttendHisCategory.getDocAlteror() == null) {
            sysAttendHisCategory.setDocAlteror(UserUtil.getUser());
        }
        if (sysAttendHisCategory.getDocAlterTime() == null) {
            sysAttendHisCategory.setDocAlterTime(new Date());
        }
        return super.add(sysAttendHisCategory);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        SysAttendHisCategory model = (SysAttendHisCategory) modelObj;
        if (model.getDocAlteror() == null) {
            model.setDocAlteror(UserUtil.getUser());
        }
        model.setDocAlterTime(new Date());
        super.update(model);
    }
}
