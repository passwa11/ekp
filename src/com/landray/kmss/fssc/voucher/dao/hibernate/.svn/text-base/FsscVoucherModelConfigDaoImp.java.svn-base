package com.landray.kmss.fssc.voucher.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.voucher.dao.IFsscVoucherModelConfigDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscVoucherModelConfigDaoImp extends BaseDaoImp implements IFsscVoucherModelConfigDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscVoucherModelConfig fsscVoucherModelConfig = (FsscVoucherModelConfig) modelObj;
        if (fsscVoucherModelConfig.getDocCreator() == null) {
            fsscVoucherModelConfig.setDocCreator(UserUtil.getUser());
        }
        if (fsscVoucherModelConfig.getDocCreateTime() == null) {
            fsscVoucherModelConfig.setDocCreateTime(new Date());
        }
        return super.add(fsscVoucherModelConfig);
    }
}
