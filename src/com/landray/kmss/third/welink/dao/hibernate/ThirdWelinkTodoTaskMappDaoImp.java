package com.landray.kmss.third.welink.dao.hibernate;

import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.welink.dao.IThirdWelinkTodoTaskMappDao;
import com.landray.kmss.third.welink.model.ThirdWelinkTodoTaskMapp;

public class ThirdWelinkTodoTaskMappDaoImp extends BaseDaoImp implements IThirdWelinkTodoTaskMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWelinkTodoTaskMapp thirdWelinkTodoTaskMapp = (ThirdWelinkTodoTaskMapp) modelObj;
        if (thirdWelinkTodoTaskMapp.getDocCreateTime() == null) {
            thirdWelinkTodoTaskMapp.setDocCreateTime(new Date());
        }
        return super.add(thirdWelinkTodoTaskMapp);
    }

	@Override
	public void deleteByTaskId(String taskId) throws Exception {
		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.welink.model.ThirdWelinkTodoTaskMapp where fdTaskId=:fdTaskId");
		query.setParameter("fdTaskId", taskId);
		query.executeUpdate();

	}
}
