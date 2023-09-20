package com.landray.kmss.sys.webservice2.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.webservice2.dao.ISysWebserviceUserDao;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.UserUtil;

/**
 * WebService帐号管理数据访问接口实现
 * 
 * @author Jeff
 */
public class SysWebserviceUserDaoImp extends BaseDaoImp implements
		ISysWebserviceUserDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysWebserviceUser model = (SysWebserviceUser) modelObj;

		if (model.getDocCreator() == null) {
			// 创建者
			model.setDocCreator(UserUtil.getUser());
		}
		if (model.getDocCreateTime() == null) {
			// 创建时间
			model.setDocCreateTime(new Date());
		}

		// 密码加密后保存
		String cipherText = SysWsUtil.encryptPwd(model.getFdPassword());
		model.setFdPassword(cipherText);

		return super.add(model);
	}

}
