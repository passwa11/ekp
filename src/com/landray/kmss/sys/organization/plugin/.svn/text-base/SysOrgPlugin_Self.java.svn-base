package com.landray.kmss.sys.organization.plugin;

import java.util.ArrayList;
import java.util.List;

public class SysOrgPlugin_Self implements ISysOrgRolePlugin {
	@Override
	public List parse(SysOrgRolePluginContext context) throws Exception {
		List rtnList = new ArrayList();
		if (context == null) {
            return rtnList;
        }
		rtnList.add(context.getBaseElement());
		return rtnList;
	}
}
