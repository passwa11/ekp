package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesBorrowDao;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.util.UserUtil;

public class KmArchivesBorrowDaoImp extends BaseDaoImp implements IKmArchivesBorrowDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) modelObj;
        if (kmArchivesBorrow.getDocCreator() == null) {
            kmArchivesBorrow.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesBorrow.getDocCreateTime() == null) {
            kmArchivesBorrow.setDocCreateTime(new Date());
        }

		if (kmArchivesBorrow.getFdBorrower() == null) {
			kmArchivesBorrow.setFdBorrower(UserUtil.getUser());
			if (UserUtil.getUser().getFdParent() != null) {
				kmArchivesBorrow.setDocDept(UserUtil.getUser().getFdParent());
			}
		}
		if (kmArchivesBorrow.getFdBorrowDate() == null) {
			kmArchivesBorrow.setFdBorrowDate(new Date());
		}

        return super.add(kmArchivesBorrow);
    }
}
