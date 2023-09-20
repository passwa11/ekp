package com.landray.kmss.sys.organization.actions;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonAddressTypeService;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮
 */
public class SysOrgPersonAddressTypeAction extends ExtendAction

{
	protected ISysOrgPersonAddressTypeService sysOrgPersonAddressTypeService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysOrgPersonAddressTypeService == null) {
            sysOrgPersonAddressTypeService = (ISysOrgPersonAddressTypeService) getBean("sysOrgPersonAddressTypeService");
        }
		return sysOrgPersonAddressTypeService;
	}
}
