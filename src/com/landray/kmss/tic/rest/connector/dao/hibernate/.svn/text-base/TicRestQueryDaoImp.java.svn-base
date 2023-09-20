package com.landray.kmss.tic.rest.connector.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.tic.rest.connector.dao.ITicRestQueryDao;
import com.landray.kmss.tic.rest.connector.model.TicRestQuery;
import com.landray.kmss.util.UserUtil;


/**
 * 函数查询数据访问接口实现
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicRestQueryDaoImp extends BaseDaoImp implements ITicRestQueryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		TicRestQuery TicRestQuery = (TicRestQuery) modelObj;
		if (TicRestQuery.getDocCreateTime() == null) {
			TicRestQuery.setDocCreateTime(new Date());
		}
		if (TicRestQuery.getDocCreator() == null) {
			TicRestQuery.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}
	
}
