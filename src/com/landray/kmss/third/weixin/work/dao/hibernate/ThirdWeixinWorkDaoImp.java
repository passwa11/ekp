package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinWorkDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinWork;
import com.landray.kmss.util.UserUtil;

/**
 * 应用配置数据访问接口实现
 * 
 * @author
 * @version 1.0 2017-05-02
 */
public class ThirdWeixinWorkDaoImp extends ExtendDataDaoImp
		implements
			IThirdWeixinWorkDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		ThirdWeixinWork work = (ThirdWeixinWork) modelObj;
		work.setDocCreateTime(new Date());
		work.setDocCreator(UserUtil.getUser());
		return super.add(modelObj);
	}
	
}
