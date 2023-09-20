package com.landray.kmss.sys.attachment.borrow.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.borrow.dao.ISysAttBorrowDao;
import com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow;
import com.landray.kmss.util.UserUtil;

public class SysAttBorrowDaoImp extends BaseDaoImp implements ISysAttBorrowDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysAttBorrow sysAttBorrow = (SysAttBorrow) modelObj;
        if (sysAttBorrow.getDocCreator() == null) {
            sysAttBorrow.setDocCreator(UserUtil.getUser());
        }
        if (sysAttBorrow.getDocCreateTime() == null) {
            sysAttBorrow.setDocCreateTime(new Date());
        }
        return super.add(sysAttBorrow);
    }
}
