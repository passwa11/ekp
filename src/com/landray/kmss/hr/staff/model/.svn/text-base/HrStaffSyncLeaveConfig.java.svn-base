package com.landray.kmss.hr.staff.model;

import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 离职日期同步
 *
 * @author 王进府
 * @creation 2021-08-31
 */
public class HrStaffSyncLeaveConfig extends BaseAppConfig {
    //最后同步日期 yyyy-MM-dd HH:mm:ss

    public HrStaffSyncLeaveConfig() throws Exception {
        super();
        String staffLastSyncLeaveDate = super.getValue("staffLastSyncLeaveDate");
        if (StringUtil.isNull(staffLastSyncLeaveDate)) {
            //默认时间
            staffLastSyncLeaveDate = "2001-01-01 00:00:00";
        }
        super.setValue("staffLastSyncLeaveDate", staffLastSyncLeaveDate);
    }

    public String getStaffLastSyncLeaveDate() {
        if(StringUtil.isNull(super.getValue("staffLastSyncLeaveDate"))){
            return "2001-01-01 00:00:00";
        }
        return super.getValue("staffLastSyncLeaveDate");
    }

    public void setStaffLastSyncLeaveDate(String staffLastSyncLeaveDate) {
        super.setValue("staffLastSyncLeaveDate", staffLastSyncLeaveDate);
    }

    @Override
    public String getJSPUrl() {
        return null;
    }

    @Override
    public String getModelDesc() {
        return ResourceUtil.getString("hr-staff:hrStaffPersonInfo.fdResignation");
    }
}
