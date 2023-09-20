package com.landray.kmss.hr.turnover.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.turnover.dao.IHrTurnoverAnnualDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.turnover.model.HrTurnoverAnnual;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 年度离职率目标值 Dao层实现
  */
public class HrTurnoverAnnualDaoImp extends BaseDaoImp implements IHrTurnoverAnnualDao {

    public String add(IBaseModel modelObj) throws Exception {
        HrTurnoverAnnual hrTurnoverAnnual = (HrTurnoverAnnual) modelObj;
        if (hrTurnoverAnnual.getDocCreator() == null) {
            hrTurnoverAnnual.setDocCreator(UserUtil.getUser());
        }
        if (hrTurnoverAnnual.getDocCreateTime() == null) {
            hrTurnoverAnnual.setDocCreateTime(new Date());
        }
        return super.add(hrTurnoverAnnual);
    }
}
