package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataVehicleDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataVehicleDaoImp extends BaseDaoImp implements IEopBasedataVehicleDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataVehicle eopBasedataVehicle = (EopBasedataVehicle) modelObj;
        if (eopBasedataVehicle.getDocCreator() == null) {
            eopBasedataVehicle.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataVehicle.getDocCreateTime() == null) {
            eopBasedataVehicle.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataVehicle);
    }
}
