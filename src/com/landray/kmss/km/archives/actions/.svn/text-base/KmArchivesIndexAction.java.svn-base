package com.landray.kmss.km.archives.actions;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmArchivesIndexAction extends ExtendAction {

    private IKmArchivesMainService kmArchivesMainService;

	@Override
    public IKmArchivesMainService getServiceImp(HttpServletRequest request) {
        if (kmArchivesMainService == null) {
            kmArchivesMainService = (IKmArchivesMainService) getBean("kmArchivesMainService");
        }
        return kmArchivesMainService;
    }

	private IKmArchivesCategoryService kmArchivesCategoryService;

	public IKmArchivesCategoryService getCategoryServiceImp() {
		if (kmArchivesCategoryService == null) {
			kmArchivesCategoryService = (IKmArchivesCategoryService) getBean(
					"kmArchivesCategoryService");
		}
		return kmArchivesCategoryService;
	}

    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		String whereBlock = hqlInfo.getWhereBlock();
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesMain.fdDestroyed = false)");
		CriteriaValue cv = new CriteriaValue(request);
		String dateType = cv.poll("dateType");
		// 过期范围
		String expireRange = new KmArchivesConfig().getFdSoonExpireDate();
		int range = StringUtil.isNull(expireRange) ? 0
				: Integer.parseInt(expireRange);
		// 即将到期
		if ("soon".equals(dateType)) {
			if (range == 0) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						"1 = 2");
			} else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, range);
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" (kmArchivesMain.fdValidityDate > :minValidityDate and kmArchivesMain.fdValidityDate < :maxValidityDate)");
				hqlInfo.setParameter("minValidityDate", new Date());
				hqlInfo.setParameter("maxValidityDate", cal.getTime());
			}
		} else if ("already".equals(dateType)) {
			// 已经到期
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"(kmArchivesMain.fdValidityDate <= :fdValidityDate)");
			hqlInfo.setParameter("fdValidityDate", new Date());
		}
		String mydoc = cv.poll("mydoc");
		if (!"create".equals(mydoc) && !"approved".equals(mydoc) && !"approval".equals(mydoc)) {
			// 未审批通过的不能进行鉴定和销毁
			whereBlock = StringUtil.linkString(whereBlock, " and ", "(kmArchivesMain.docStatus = :docStatus)");
			hqlInfo.setParameter("docStatus", SysDocConstant.DOC_STATUS_PUBLISH);
			// 查询只显示最新的版本
			whereBlock += " and kmArchivesMain.docIsNewVersion = '1'";
		}
		hqlInfo.setWhereBlock(whereBlock);
		HQLHelper.by(request).buildHQLInfo(hqlInfo, KmArchivesMain.class);
		hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
    }
}
