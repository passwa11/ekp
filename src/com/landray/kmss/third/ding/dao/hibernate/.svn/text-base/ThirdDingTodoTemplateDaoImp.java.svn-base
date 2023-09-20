package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingTodoTemplateDao;
import com.landray.kmss.third.ding.model.ThirdDingTodoTemplate;
import com.landray.kmss.util.UserUtil;

public class ThirdDingTodoTemplateDaoImp extends BaseDaoImp implements IThirdDingTodoTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) modelObj;
        if (thirdDingTodoTemplate.getDocCreator() == null) {
            thirdDingTodoTemplate.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingTodoTemplate.getDocCreateTime() == null) {
            thirdDingTodoTemplate.setDocCreateTime(new Date());
        }
        return super.add(thirdDingTodoTemplate);
    }

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		ThirdDingTodoTemplate thirdDingTodoTemplate = (ThirdDingTodoTemplate) modelObj;
		thirdDingTodoTemplate.setDocAlterTime(new Date());
		super.update(thirdDingTodoTemplate);
	}
}
