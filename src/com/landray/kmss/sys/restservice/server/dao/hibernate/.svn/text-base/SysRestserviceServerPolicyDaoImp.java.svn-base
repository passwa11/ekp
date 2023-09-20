package com.landray.kmss.sys.restservice.server.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.restservice.server.dao.ISysRestserviceServerPolicyDao;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.util.SysRsUtil;
import com.landray.kmss.util.UserUtil;

/**
 * RestService帐号管理数据访问接口实现
 * 
 * @author  
 */
public class SysRestserviceServerPolicyDaoImp extends BaseDaoImp implements
		ISysRestserviceServerPolicyDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysRestserviceServerPolicy model = (SysRestserviceServerPolicy) modelObj;

		if (model.getDocCreator() == null) {
			// 创建者
			model.setDocCreator(UserUtil.getUser());
		}
		if (model.getDocCreateTime() == null) {
			// 创建时间
			model.setDocCreateTime(new Date());
		}

		// 密码加密后保存
		String cipherText = SysRsUtil.encryptPwd(model.getFdPassword());
		model.setFdPassword(cipherText);

		return super.add(model);
	}

}
