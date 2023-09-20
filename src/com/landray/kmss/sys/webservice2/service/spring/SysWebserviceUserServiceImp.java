package com.landray.kmss.sys.webservice2.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.webservice2.model.SysWebserviceUser;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceUserService;
import com.landray.kmss.util.StringUtil;
import org.hibernate.type.StandardBasicTypes;

/**
 * 用户帐号设置
 * 
 * @author Jeff
 */
public class SysWebserviceUserServiceImp extends BaseServiceImp implements
		ISysWebserviceUserService {

	@Override
    public SysWebserviceUser findUser(String userName, String loginId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereStr =new StringBuffer();
		if(StringUtil.isNotNull(userName)){
			whereStr.append("sysWebserviceUser.fdName=:fdName or sysWebserviceUser.fdUserName=:fdUserName ");
			hqlInfo.setParameter("fdName", userName, StandardBasicTypes.STRING);
			hqlInfo.setParameter("fdUserName", userName, StandardBasicTypes.STRING);
		}
		
		if(StringUtil.isNotNull(loginId)){
			if(whereStr.length()>0){
				whereStr.append("or ");
			}
			whereStr.append("sysWebserviceUser.fdLoginId=:fdLoginId");
			hqlInfo.setParameter("fdLoginId", loginId, StandardBasicTypes.STRING);
		}
		hqlInfo.setWhereBlock(whereStr.toString());
		SysWebserviceUser user = (SysWebserviceUser)getBaseDao().findFirstOne(hqlInfo);
		return user;
	}
}
