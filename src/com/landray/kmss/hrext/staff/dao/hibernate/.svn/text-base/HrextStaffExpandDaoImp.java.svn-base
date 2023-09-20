package com.landray.kmss.hrext.staff.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hrext.staff.dao.IHrextStaffExpandDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hrext.staff.model.HrextStaffExpand;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 流程人事同步 Dao层实现
  */
public class HrextStaffExpandDaoImp extends BaseDaoImp implements IHrextStaffExpandDao {

    public String add(IBaseModel modelObj) throws Exception {
        HrextStaffExpand hrextStaffExpand = (HrextStaffExpand) modelObj;
        if (hrextStaffExpand.getDocCreator() == null) {
            hrextStaffExpand.setDocCreator(UserUtil.getUser());
        }
        if (hrextStaffExpand.getDocCreateTime() == null) {
            hrextStaffExpand.setDocCreateTime(new Date());
        }
        return super.add(hrextStaffExpand);
    }
}
