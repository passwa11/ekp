package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingTodoCardDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingTodoCard;

public class ThirdDingTodoCardDaoImp extends BaseDaoImp implements IThirdDingTodoCardDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingTodoCard thirdDingTodoCard = (ThirdDingTodoCard) modelObj;
        if (thirdDingTodoCard.getDocCreateTime() == null) {
            thirdDingTodoCard.setDocCreateTime(new Date());
        }
        return super.add(thirdDingTodoCard);
    }
}
