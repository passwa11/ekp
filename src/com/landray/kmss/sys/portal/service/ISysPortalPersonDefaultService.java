package com.landray.kmss.sys.portal.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.portal.model.SysPortalPersonDefault;

/**
 * 个人默认门户
 *
 */
public interface ISysPortalPersonDefaultService extends IBaseService {

	public SysPortalPersonDefault getPersonDefaultPortal() throws Exception;
}
