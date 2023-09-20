package com.landray.kmss.third.im.kk.service.spring;

import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonImageService;
import com.landray.kmss.third.im.kk.constant.KkNotifyConstants;
import com.landray.kmss.third.im.kk.util.KK5Util;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class KK5PersonImageBean implements PersonImageService {
	private ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
			.getBean("sysOrgPersonService");

	@Override
	public String getHeadimage(String personId) {
		return getHeadimage(personId, "90");
	}

	@Override
	public String getHeadimage(String personId, String size) {
		if ("b".equals(size)) {
			size = "120";
		} else if ("m".equals(size)) {
			size = "90";
		} else if ("s".equals(size)) {
			size = "60";
		}
		try {
			return KK5Util.getKK5Url_new(KkNotifyConstants.KK5_GETUSERIMAGE_API)
					+ "?user=" + getLoginName(personId) + "&size=" + size;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getHeadimageChangeUrl() {
		try {
			return KK5Util.getKK5Url_new(KkNotifyConstants.KK5_CHANGEUSERIMAGE_API)
					+ "?user=" + UserUtil.getUser().getFdLoginName();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return null;
	}

	private String getLoginName(String personId) throws Exception {
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
				.findByPrimaryKey(personId);
		if (person != null) {
			return person.getFdLoginName();
		} else {
			return null;
		}
	}
}