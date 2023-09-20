package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataSupGradeDao;
import com.landray.kmss.eop.basedata.model.EopBasedataSupGrade;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

/**
 * @author wangwh
 * @description:供应商等级dao实现类
 * @date 2021/5/7
 */
public class EopBasedataSupGradeDaoImp extends BaseDaoImp implements IEopBasedataSupGradeDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataSupGrade eopBasedataSupGrade = (EopBasedataSupGrade) modelObj;
        if (eopBasedataSupGrade.getDocCreator() == null) {
            eopBasedataSupGrade.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataSupGrade.getDocCreateTime() == null) {
            eopBasedataSupGrade.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataSupGrade);
    }
}
