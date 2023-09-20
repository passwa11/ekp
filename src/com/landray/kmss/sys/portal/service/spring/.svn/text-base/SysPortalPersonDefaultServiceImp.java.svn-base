package com.landray.kmss.sys.portal.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.portal.model.SysPortalPersonDefault;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalPersonDefaultService;
import com.landray.kmss.util.UserUtil;

/**
 * 个人默认门户
 *
 */
public class SysPortalPersonDefaultServiceImp extends BaseServiceImp
		implements ISysPortalPersonDefaultService {

	public ISysPortalMainService sysPortalMainService;

	public void setSysPortalMainService(ISysPortalMainService sysPortalMainService) {
		this.sysPortalMainService = sysPortalMainService;
	}

	@Override
	public SysPortalPersonDefault getPersonDefaultPortal() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"sysPortalPersonDefault.fdPerson.fdId = :fdPersonId and sysPortalPersonDefault.fdIsDefault=:fdIsDefault");
		hqlInfo.setParameter("fdPersonId", UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdIsDefault", Boolean.TRUE);
		Object obj = this.findFirstOne(hqlInfo);
		if (obj!=null) {
			SysPortalPersonDefault spd = (SysPortalPersonDefault) obj;
			return spd;
		}
		return null;
	}
}
