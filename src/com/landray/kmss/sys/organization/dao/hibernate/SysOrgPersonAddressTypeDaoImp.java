package com.landray.kmss.sys.organization.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonAddressTypeDao;
import com.landray.kmss.sys.organization.model.SysOrgPersonAddressType;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮 个人地址本分类数据访问接口实现
 */
public class SysOrgPersonAddressTypeDaoImp extends BaseDaoImp implements
		ISysOrgPersonAddressTypeDao {
	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysOrgPersonAddressType type = (SysOrgPersonAddressType) modelObj;
		type.setDocCreateTime(new Date());
		type.setDocCreator(UserUtil.getUser());
		super.update(modelObj);
	}
}
