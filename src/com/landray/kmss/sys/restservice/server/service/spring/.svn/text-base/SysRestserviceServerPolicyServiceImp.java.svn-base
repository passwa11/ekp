package com.landray.kmss.sys.restservice.server.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.restservice.server.model.SysRestserviceServerPolicy;
import com.landray.kmss.sys.restservice.server.service.ISysRestserviceServerPolicyService;
import com.landray.kmss.util.StringUtil;
import org.hibernate.type.StandardBasicTypes;

/**
 * 用户帐号设置
 * 
 * @author  
 */
public class SysRestserviceServerPolicyServiceImp extends BaseServiceImp implements
		ISysRestserviceServerPolicyService {

	@Override
    public SysRestserviceServerPolicy findUser(String policyName, String loginId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereStr =new StringBuffer();
		if(StringUtil.isNotNull(policyName)){
			whereStr.append("sysRestserviceServerPolicy.fdName=:fdName");
			hqlInfo.setParameter("fdName", policyName, StandardBasicTypes.STRING);
		}
		if(StringUtil.isNotNull(loginId)){
			if(StringUtil.isNotNull(whereStr.toString())){
				whereStr.append(" and ");
			}
			whereStr.append("sysRestserviceServerPolicy.fdLoginId=:fdLoginId");
			hqlInfo.setParameter("fdLoginId", loginId, StandardBasicTypes.STRING);
		}
		hqlInfo.setWhereBlock(whereStr.toString());
		SysRestserviceServerPolicy model = (SysRestserviceServerPolicy)getBaseDao().findFirstOne(hqlInfo);
		return model;
	}
}
