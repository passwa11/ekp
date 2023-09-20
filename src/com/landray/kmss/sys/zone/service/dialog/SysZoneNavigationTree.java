/**
 * 
 */
package com.landray.kmss.sys.zone.service.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.person.service.dialog.BaseCategoryTree;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.sys.zone.service.ISysZoneNavigationService;

/**
 * @author 傅游翔
 * 
 */
public class SysZoneNavigationTree extends BaseCategoryTree implements
		IXMLDataBean {

	private ISysZoneNavigationService sysZoneNavigationService;

	public void setSysZoneNavigationService(
			ISysZoneNavigationService sysZoneNavigationService) {
		this.sysZoneNavigationService = sysZoneNavigationService;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addCategories(List<Map<String, String>> tree,
			RequestContext requestInfo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdStatus > 1");
		hqlInfo.setOrderBy("fdOrder");
		List<SysZoneNavigation> cates = sysZoneNavigationService
				.findList(hqlInfo);
		for (SysZoneNavigation cate : cates) {
			Map<String, String> node = new HashMap<String, String>();
			node.put("value", cate.getFdId());
			node.put("text", cate.getFdName());
			node.put("isAutoFetch", "0");
			tree.add(node);
		}
	}

}
