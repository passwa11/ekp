package com.landray.kmss.hr.ratify.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.hr.ratify.dao.IHrRatifyTemplateDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.common.dao.BaseDaoImp;

public class HrRatifyTemplateDaoImp extends BaseDaoImp implements IHrRatifyTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrRatifyTemplate hrRatifyTemplate = (HrRatifyTemplate) modelObj;
        if (hrRatifyTemplate.getDocCreator() == null) {
            hrRatifyTemplate.setDocCreator(UserUtil.getUser());
        }
        if (hrRatifyTemplate.getDocCreateTime() == null) {
            hrRatifyTemplate.setDocCreateTime(new Date());
        }
        return super.add(hrRatifyTemplate);
    }
}
