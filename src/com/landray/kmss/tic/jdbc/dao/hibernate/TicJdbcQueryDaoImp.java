package com.landray.kmss.tic.jdbc.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.tic.jdbc.dao.ITicJdbcQueryDao;
import com.landray.kmss.tic.jdbc.model.TicJdbcQuery;
import com.landray.kmss.util.UserUtil;


/**
 * 函数查询数据访问接口实现
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicJdbcQueryDaoImp extends BaseDaoImp implements ITicJdbcQueryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		TicJdbcQuery TicJdbcQuery = (TicJdbcQuery) modelObj;
		if (TicJdbcQuery.getDocCreateTime() == null) {
			TicJdbcQuery.setDocCreateTime(new Date());
		}
		if (TicJdbcQuery.getDocCreator() == null) {
			TicJdbcQuery.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}
	
}
