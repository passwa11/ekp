package com.landray.kmss.km.imeeting.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempHandler;
import com.landray.kmss.sys.handover.support.config.catetemplate.AbstractCateTempProvider;
import com.landray.kmss.util.ResourceUtil;

public class KmImeetingSummaryDocAuthProvider extends AbstractCateTempProvider{
	@Override
    public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(items, AbstractCateTempHandler.AUTH_READERS,
				getMessage("authReaders"));
		addHandoverItem(items, "authAttPrints", getMessage("authAttPrints"));
		addHandoverItem(items, "authAttCopys", getMessage("authAttCopys"));
		addHandoverItem(items, "authAttDownloads",
				getMessage("authAttDownloads"));
		return items;
	}

	private String getMessage(String suffix) {
		String pre = "sys-handover-support-config-auth:sysHandoverDocAuth.";
		return ResourceUtil.getString(pre + suffix);
	}

}
