package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElementExtProp;
import com.landray.kmss.sys.organization.service.ISysOrgElementExtPropService;

public class SysOrgElementExtPropServiceImp extends BaseServiceImp implements ISysOrgElementExtPropService{
	
	@Override
    public void updatePropStatus(String id, boolean statue) throws Exception {
		SysOrgElementExtProp prop = (SysOrgElementExtProp)this.findByPrimaryKey(id);
		prop.setFdStatus(statue);
		update(prop);
	}

}
