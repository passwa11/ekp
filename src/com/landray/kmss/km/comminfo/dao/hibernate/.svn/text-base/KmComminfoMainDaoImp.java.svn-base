package com.landray.kmss.km.comminfo.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.comminfo.dao.IKmComminfoMainDao;
import com.landray.kmss.km.comminfo.model.KmComminfoMain;
import com.landray.kmss.sys.doc.dao.hibernate.SysDocBaseInfoDaoImp;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料主表数据访问接口实现
 */
public class KmComminfoMainDaoImp extends SysDocBaseInfoDaoImp implements
		IKmComminfoMainDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmComminfoMain kmComminfoMain = (KmComminfoMain) modelObj;
		if (kmComminfoMain.getDocCreator() == null) {
			// 创建者
			kmComminfoMain.setDocCreator(UserUtil.getUser());
		}
		if (kmComminfoMain.getDocCreateTime() == null) {
			// 创建时间
			kmComminfoMain.setDocCreateTime(new Date());
		}

		return super.add(modelObj);
	}
}
