package com.landray.kmss.hr.ratify.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.ratify.dao.IHrRatifyMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;

public class HrRatifyMainDaoImp extends ExtendDataDaoImp implements IHrRatifyMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrRatifyMain hrRatifyMain = (HrRatifyMain) modelObj;
        if (hrRatifyMain.getDocCreator() == null) {
            hrRatifyMain.setDocCreator(UserUtil.getUser());
        }
        if (hrRatifyMain.getDocCreateTime() == null) {
            hrRatifyMain.setDocCreateTime(new Date());
        }
        return super.add(hrRatifyMain);
    }
}
