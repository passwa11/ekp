package com.landray.kmss.fssc.voucher.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.voucher.dao.IFsscVoucherMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscVoucherMainDaoImp extends BaseDaoImp implements IFsscVoucherMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscVoucherMain fsscVoucherMain = (FsscVoucherMain) modelObj;
        if (fsscVoucherMain.getDocCreator() == null) {
            fsscVoucherMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscVoucherMain.getDocCreateTime() == null) {
            fsscVoucherMain.setDocCreateTime(new Date());
        }
        if(fsscVoucherMain.getFdIsAmortize() == null){//摊销凭证,默认为否
            fsscVoucherMain.setFdIsAmortize(false);
        }
        fsscVoucherMain.setDocAlterTime(new Date());
        fsscVoucherMain.setDocAlterTime(new Date());
        fsscVoucherMain.setDocAlteror(UserUtil.getUser());
        return super.add(fsscVoucherMain);
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        FsscVoucherMain fsscVoucherMain = (FsscVoucherMain) modelObj;
        if (fsscVoucherMain.getDocCreator() == null) {
            fsscVoucherMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscVoucherMain.getDocCreateTime() == null) {
            fsscVoucherMain.setDocCreateTime(new Date());
        }
        if(fsscVoucherMain.getFdIsAmortize() == null){//摊销凭证,默认为否
            fsscVoucherMain.setFdIsAmortize(false);
        }
        fsscVoucherMain.setDocAlterTime(new Date());
        fsscVoucherMain.setDocAlteror(UserUtil.getUser());
        super.update(fsscVoucherMain);
    }
}
