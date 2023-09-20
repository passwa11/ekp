package com.landray.kmss.km.imeeting.subordinate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.imeeting.model.KmImeetingBook;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;

/**
 * 会议预约提供者
 * 
 * @author 潘永辉 2019年3月13日
 *
 */
public class KmImeetingBookProvider extends AbstractSubordinateProvider {

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 发起的预约
		items.add(new PropertyItem("docCreator", ""));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo) throws Exception {
		String status = request.getParameter("q.status");
		if (StringUtil.isNotNull(status)) {
			if ("wait".equals(status)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmImeetingBook.fdHasExam is null"));
			}
			if ("yes".equals(status)){
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmImeetingBook.fdHasExam =:fdHasExam"));
				hqlInfo.setParameter("fdHasExam", Boolean.TRUE);
			}
			if ("no".equals(status)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmImeetingBook.fdHasExam =:fdHasExam"));
				hqlInfo.setParameter("fdHasExam", Boolean.FALSE);
			}
		}
		
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo, KmImeetingBook.class);
	}

}
