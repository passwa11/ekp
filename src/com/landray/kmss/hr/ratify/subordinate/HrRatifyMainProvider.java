package com.landray.kmss.hr.ratify.subordinate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ClassUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;

public class HrRatifyMainProvider extends AbstractSubordinateProvider {

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 创建的流程
		items.add(new PropertyItem("fdDepartment", ""));
		return items;
	}

	@Override
	public void changeFindPageHQLInfo(RequestContext request, HQLInfo hqlInfo)
			throws Exception {
		String modelName = request.getParameter("modelName");
		hqlInfo.setSelectBlock(
				"hrRatifyMain.fdId,hrRatifyMain.docSubject,hrRatifyMain.docNumber,hrRatifyMain.docCreator.fdName,hrRatifyMain.docCreator.fdId,hrRatifyMain.docCreateTime,hrRatifyMain.docPublishTime,hrRatifyMain.docStatus,hrRatifyMain.fdSubclassModelname");
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo,
				com.landray.kmss.util.ClassUtils.forName(modelName));
		CriteriaValue cv = new CriteriaValue(request.getRequest());
		String whereBlock = hqlInfo.getWhereBlock();
		String fdIsFile = cv.poll("fdIsFile");
		if (StringUtil.isNotNull(fdIsFile)) {
			if ("1".equals(fdIsFile)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"hrRatifyMain.fdIsFiling =:fdIsFiling");
				hqlInfo.setParameter("fdIsFiling", true);
			} else if ("0".equals(fdIsFile)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"(hrRatifyMain.fdIsFiling =:fdIsFiling or hrRatifyMain.fdIsFiling is null)");
				hqlInfo.setParameter("fdIsFiling", false);
			}
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

}
