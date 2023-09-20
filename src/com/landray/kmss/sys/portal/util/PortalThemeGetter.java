package com.landray.kmss.sys.portal.util;

import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.ui.util.ThemeGetter;
import com.landray.kmss.sys.ui.util.ThemeUtil;
import com.landray.kmss.util.UserUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.http.HttpServletRequest;

public class PortalThemeGetter implements ApplicationListener, ThemeGetter {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PortalThemeGetter.class);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event == null || !(event instanceof ContextRefreshedEvent)) {
            return;
        }
		ThemeUtil.registerThemeGetter(this);
	}

	@Override
	public String getTheme(HttpServletRequest request) {
		try {
			if(MobileUtil.PC == MobileUtil.getClientType(request)
					|| MobileUtil.DING_PC == MobileUtil
							.getClientType(request)
					|| MobileUtil.FEISHU_PC == MobileUtil
							.getClientType(request)
					|| MobileUtil.WXWORK_PC == MobileUtil
					.getClientType(request)) {
				return PortalUtil.getPortalInfo(request).getTheme();
			}
		} catch (Exception e) {
			KMSSUser user = UserUtil.getKMSSUser(request);
			if(!user.isAnonymous()){
				logger.warn("当前主题信息为空或获取失败，将使用默认皮肤。");
			}
		}
		return "default";
	}

}
