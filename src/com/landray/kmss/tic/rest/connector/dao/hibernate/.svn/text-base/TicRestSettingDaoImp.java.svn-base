package com.landray.kmss.tic.rest.connector.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.tic.rest.connector.dao.ITicRestSettingDao;
import com.landray.kmss.tic.rest.connector.model.TicRestSetting;
import com.landray.kmss.util.UserUtil;


/**
 * REST服务配置数据访问接口实现
 * 
 */
public class TicRestSettingDaoImp extends BaseDaoImp implements ITicRestSettingDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 增加创建人和创建时间
		TicRestSetting docModel = (TicRestSetting)modelObj;
		if (docModel.getDocCreator() == null) {
			docModel.setDocCreator(UserUtil.getUser());
		}
		if (docModel.getDocCreateTime() == null) {
			docModel.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		TicRestSetting docModel = (TicRestSetting)modelObj;
		docModel.setDocAlterTime(new Date());
		super.update(modelObj);
	}
	
}
