package com.landray.kmss.sys.time.handover;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.config.HandoverItem;
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
public class SysTimeCateTempProvider extends AbstractCateTempProvider {

	@Override
	public List<HandoverItem> items() throws Exception {
		List<HandoverItem> items = new ArrayList<HandoverItem>();
		addHandoverItem(items, "areaMembers", ResourceUtil
				.getString("sys-time:sysTimeArea.scope"));
		addHandoverItem(items, "areaAdmins", ResourceUtil
				.getString("sys-time:sysTimeArea.timeAdmin"));
		return items;
	}
}
