package com.landray.kmss.km.imeeting.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempProvider;
import com.landray.kmss.util.ResourceUtil;

/**
 * 工作交接后台配置类 会议室信息 可维护者可使用者权限
 * @author zqx5253
 *
 */
public class KmImeetingResConfigProvider extends AbstractCateTempProvider {
	
	@Override
	public List<HandoverItem> items() throws Exception {
		String messagePre = "sys-handover-support-config-catetemplate:sysHandoverConfigHandler.";
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(items, AbstractCateTempHandler.AUTH_READERS,
				ResourceUtil.getString(messagePre + "authReaders"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_EDITORS,
				ResourceUtil.getString(messagePre + "authEditors"));
		return items;
	}

}
