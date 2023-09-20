package com.landray.kmss.hr.config.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.config.dao.IHrConfigOvertimeConfigDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.config.model.HrConfigOvertimeConfig;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 加班规则配置 Dao层实现
  */
public class HrConfigOvertimeConfigDaoImp extends BaseDaoImp implements IHrConfigOvertimeConfigDao {

    public String add(IBaseModel modelObj) throws Exception {
        HrConfigOvertimeConfig hrConfigOvertimeConfig = (HrConfigOvertimeConfig) modelObj;
        if (hrConfigOvertimeConfig.getDocCreator() == null) {
            hrConfigOvertimeConfig.setDocCreator(UserUtil.getUser());
        }
        if (hrConfigOvertimeConfig.getDocCreateTime() == null) {
            hrConfigOvertimeConfig.setDocCreateTime(new Date());
        }
        return super.add(hrConfigOvertimeConfig);
    }
}
