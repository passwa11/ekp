package com.landray.kmss.fssc.voucher.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.voucher.dao.IFsscVoucherRuleConfigDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.voucher.model.FsscVoucherRuleConfig;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscVoucherRuleConfigDaoImp extends BaseDaoImp implements IFsscVoucherRuleConfigDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscVoucherRuleConfig fsscVoucherRuleConfig = (FsscVoucherRuleConfig) modelObj;
        if (fsscVoucherRuleConfig.getDocCreator() == null) {
            fsscVoucherRuleConfig.setDocCreator(UserUtil.getUser());
        }
        if (fsscVoucherRuleConfig.getDocCreateTime() == null) {
            fsscVoucherRuleConfig.setDocCreateTime(new Date());
        }
        fsscVoucherRuleConfig.setDocAlteror(UserUtil.getUser());
        fsscVoucherRuleConfig.setDocAlterTime(new Date());
        return super.add(fsscVoucherRuleConfig);
    }
    @Override
    public void update(IBaseModel modelObj) throws Exception {
        FsscVoucherRuleConfig fsscVoucherRuleConfig = (FsscVoucherRuleConfig) modelObj;
        if (fsscVoucherRuleConfig.getDocCreator() == null) {
            fsscVoucherRuleConfig.setDocCreator(UserUtil.getUser());
        }
        if (fsscVoucherRuleConfig.getDocCreateTime() == null) {
            fsscVoucherRuleConfig.setDocCreateTime(new Date());
        }
        fsscVoucherRuleConfig.setDocAlteror(UserUtil.getUser());
        fsscVoucherRuleConfig.setDocAlterTime(new Date());
        super.update(fsscVoucherRuleConfig);
    }
}
