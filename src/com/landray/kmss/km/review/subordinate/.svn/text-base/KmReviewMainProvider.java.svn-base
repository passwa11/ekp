package com.landray.kmss.km.review.subordinate;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;

/**
 * 下属流程提供者
 * 
 * @author 潘永辉 2019年3月13日
 *
 */
public class KmReviewMainProvider extends AbstractSubordinateProvider {

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 创建的流程
		items.add(new PropertyItem("fdDepartment", ""));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo) throws Exception {
		String fdIsFile = request.getParameter("q.fdIsFile");
		if (StringUtil.isNotNull(fdIsFile)) {
			if ("1".equals(fdIsFile)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmReviewMain.fdIsFiling = :fdIsFiling"));
				hqlInfo.setParameter("fdIsFiling", true);
			} else if ("0".equals(fdIsFile)) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "(kmReviewMain.fdIsFiling = :fdIsFiling or kmReviewMain.fdIsFiling is null)"));
				hqlInfo.setParameter("fdIsFiling", false);
			}
		}
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo, KmReviewMain.class);
	}

}
