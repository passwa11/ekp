package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataItemFieldDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataItemField;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataItemFieldDaoImp extends BaseDaoImp implements IEopBasedataItemFieldDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataItemField eopBasedataItemField = (EopBasedataItemField) modelObj;
        if (eopBasedataItemField.getDocCreator() == null) {
            eopBasedataItemField.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataItemField.getDocCreateTime() == null) {
            eopBasedataItemField.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataItemField);
    }
}
