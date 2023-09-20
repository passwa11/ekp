package com.landray.kmss.km.imeeting.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingResCategory;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 获取第一个会议室分类
 */
public class ImeetingCateUtil {
	public static String getfirstCate() throws Exception {
		List<KmImeetingResCategory> cateList = new ArrayList<>();
		String fdCateId = "";
		String tempId = "";
		SysOrgElement p = UserUtil.getUser();
		List<?> authOrgIds = sysOrgCoreService.getOrgsUserAuthInfo(p)
				.getAuthOrgIds();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				"left join kmImeetingResCategory.defReaders defReaders");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("defReaders.fdId", authOrgIds));
		hqlInfo.setOrderBy("kmImeetingResCategory.fdOrder");
		cateList = kmImeetingResCategoryService
				.findValue(hqlInfo);
		if (cateList != null && !cateList.isEmpty()) {
			for (KmImeetingResCategory kmImeetingResCategory : cateList) {
				if (StringUtil.isNull(tempId)) {
					tempId = kmImeetingResCategory.getFdId();
				}
				if (kmImeetingResCategory.getFdParent() == null) {
					fdCateId = kmImeetingResCategory.getFdId();
					break;
				}
			}
			if (StringUtil.isNull(fdCateId)) {
				fdCateId = tempId;
			}
		} else {
			HQLInfo hql = new HQLInfo();
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
					SysAuthConstant.AllCheck.DEFAULT);
			hql.setOrderBy(
					" kmImeetingResCategory.fdOrder,kmImeetingResCategory.fdId ");
			cateList = kmImeetingResCategoryService
					.findValue(hql);
			if (cateList != null && cateList.size() > 0) {
				String modelName = "KmImeetingResCategory";
				String tableName = ModelUtil.getModelTableName(modelName);
				List<String> hierarchyReaderIds = ImeetingCalendarUtil
						.findHierarchyReaderIds(
								kmImeetingResCategoryService, modelName,
								tableName);
				for (KmImeetingResCategory kmImeetingResCategory : cateList) {
					boolean flag = UserUtil.getKMSSUser().isAdmin() ? true
							: hierarchyReaderIds
									.contains(kmImeetingResCategory
											.getFdHierarchyId());
					if (!flag) {
						continue;
					}
					if (StringUtil.isNull(tempId)) {
						tempId = kmImeetingResCategory.getFdId();
					}
					if (kmImeetingResCategory.getFdParent() == null) {
						fdCateId = kmImeetingResCategory.getFdId();
						break;
					}
				}

				if (StringUtil.isNull(fdCateId)) {
					fdCateId = tempId;
				}
			}
		}
		return fdCateId;
	}

	private static ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
			.getBean("sysOrgCoreService");
	private static IKmImeetingResCategoryService kmImeetingResCategoryService = (IKmImeetingResCategoryService) SpringBeanUtil
			.getBean("kmImeetingResCategoryService");

	public static String getDefCates() throws Exception {
		String cateIds = "all";
		SysOrgElement p = UserUtil.getUser();
		List<?> authOrgIds = sysOrgCoreService.getOrgsUserAuthInfo(p)
				.getAuthOrgIds();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingResCategory.fdHierarchyId");
		hqlInfo.setJoinBlock(
				"left join kmImeetingResCategory.defReaders defReaders");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("defReaders.fdId", authOrgIds));
		hqlInfo.setOrderBy("kmImeetingResCategory.fdOrder");
		List<?> cateIdList = kmImeetingResCategoryService.findValue(hqlInfo);
		StringBuffer sb = new StringBuffer();
		for (Object obj : cateIdList) {
			sb.append(obj.toString() + ";");
		}
		String sbStr = sb.toString();
		if (StringUtil.isNotNull(sbStr)) {
			cateIds = sbStr.substring(0, sbStr.length() - 1);
		}
		return cateIds;
	}
}
