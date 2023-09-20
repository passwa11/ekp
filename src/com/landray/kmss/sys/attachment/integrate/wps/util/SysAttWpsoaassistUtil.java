package com.landray.kmss.sys.attachment.integrate.wps.util;

import com.landray.kmss.sys.attachment.model.SysAttConfig;
import com.landray.kmss.sys.attachment.util.JgWebOffice;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SysAttWpsoaassistUtil {
	public static Boolean isEnable() throws Exception {
		return SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST
				.equals(SysAttConfigUtil.getOnlineToolType());
	}

	public static Boolean isWPSOAassistEmbed() throws Exception {
		Boolean isEmbed = SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST
				.equals(SysAttConfigUtil.getOnlineToolType());

		SysAttConfig config = new SysAttConfig();
		String wpsoaassistEmbed="0";
		Map map = config.getDataMap();
		if (!map.isEmpty()) {
			wpsoaassistEmbed = (String) map.getOrDefault("wpsoaassistEmbed", "0");
		}
		return isEmbed && "1".equals(wpsoaassistEmbed);
	}

	public static Boolean isWPSOAassistEmbed(HttpServletRequest request) throws Exception {
		Boolean isWindows = Boolean.FALSE;
		if("windows".equals(JgWebOffice.getOSType(request))){
			isWindows = Boolean.TRUE;
		}
		return isWPSOAassistEmbed() && !isWindows;
	}
}
