package com.landray.kmss.km.comminfo.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.comminfo.dao.IKmComminfoCategoryDao;
import com.landray.kmss.km.comminfo.model.KmComminfoCategory;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 徐乃瑞 常用资料类别数据访问接口实现
 */
public class KmComminfoCategoryDaoImp extends BaseTreeDaoImp implements
		IKmComminfoCategoryDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmComminfoCategory kmComminfoCategory = (KmComminfoCategory) modelObj;
		if (kmComminfoCategory.getDocCreator() == null) {
			// 创建者
			kmComminfoCategory.setDocCreator(UserUtil.getUser());
		}
		if (kmComminfoCategory.getDocCreateTime() == null) {
			// 创建时间
			kmComminfoCategory.setDocCreateTime(new Date());
		}

		return super.add(modelObj);
	}

}
