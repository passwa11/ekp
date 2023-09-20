package com.landray.kmss.km.imeeting.subordinate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.HQLHelper;

/**
 * 会议纪要提供者
 * 
 * @author 潘永辉 2019年3月13日
 *
 */
public class KmImeetingSummaryProvider extends AbstractSubordinateProvider {

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 发起的纪要
		items.add(new PropertyItem("docCreator", ""));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo) throws Exception {
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo, KmImeetingSummary.class);
	}

}
