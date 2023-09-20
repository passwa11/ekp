package com.landray.kmss.tic.soap.connector.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.tic.soap.connector.dao.ITicSoapQueryDao;
import com.landray.kmss.tic.soap.connector.model.TicSoapQuery;
import com.landray.kmss.util.UserUtil;


/**
 * 函数查询数据访问接口实现
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicSoapQueryDaoImp extends BaseDaoImp implements ITicSoapQueryDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		TicSoapQuery TicSoapQuery = (TicSoapQuery) modelObj;
		if (TicSoapQuery.getDocCreateTime() == null) {
			TicSoapQuery.setDocCreateTime(new Date());
		}
		if (TicSoapQuery.getDocCreator() == null) {
			TicSoapQuery.setDocCreator(UserUtil.getUser());
		}
		return super.add(modelObj);
	}
	
}
