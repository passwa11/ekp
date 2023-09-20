package com.landray.kmss.sys.organization.service;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.organization.forms.SysOrgPersonAddressTypeForm;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮 个人地址本分类业务对象接口
 */
public interface ISysOrgPersonAddressTypeService extends IBaseService {

	public void updateAddress(List<SysOrgPersonAddressTypeForm> list,
			RequestContext requestContext) throws Exception;
}
