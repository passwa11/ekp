package com.landray.kmss.hr.organization.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.organization.dao.IHrOrganizationGradeDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.common.dao.BaseDaoImp;

public class HrOrganizationGradeDaoImp extends BaseDaoImp implements IHrOrganizationGradeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrOrganizationGrade hrOrganizationGrade = (HrOrganizationGrade) modelObj;
        if (hrOrganizationGrade.getDocCreator() == null) {
            hrOrganizationGrade.setDocCreator(UserUtil.getUser());
        }
        if (hrOrganizationGrade.getDocCreateTime() == null) {
            hrOrganizationGrade.setDocCreateTime(new Date());
        }
        return super.add(hrOrganizationGrade);
    }
}
