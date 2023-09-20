package com.landray.kmss.fssc.fee.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.fee.dao.IFsscFeeTemplateDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscFeeTemplateDaoImp extends BaseDaoImp implements IFsscFeeTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscFeeTemplate fsscFeeTemplate = (FsscFeeTemplate) modelObj;
        if (fsscFeeTemplate.getDocCreator() == null) {
            fsscFeeTemplate.setDocCreator(UserUtil.getUser());
        }
        if (fsscFeeTemplate.getDocCreateTime() == null) {
            fsscFeeTemplate.setDocCreateTime(new Date());
        }
        return super.add(fsscFeeTemplate);
    }
}
