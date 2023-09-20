package com.landray.kmss.sys.portal.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempProvider;
import com.landray.kmss.util.ResourceUtil;

/**
 * 分类/模板provider
 * 
 * @author tanyouhao
 * @date 2014-11-13
 * @version 1.0
 * 
 */
public class SysPortalCateTempProvider extends AbstractCateTempProvider {

	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(items, "defReaders", ResourceUtil
				.getString("sys-portal:sysPortalMain.defReader"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_READERS,
				ResourceUtil.getString("sys-portal:sysPortalPage.fdReader"));
		addHandoverItem(items, AbstractCateTempHandler.AUTH_EDITORS,
				ResourceUtil.getString("sys-portal:sysPortalPage.fdEditor"));
		return items;
	}
}
