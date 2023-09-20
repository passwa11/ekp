package com.landray.kmss.km.review.service.spring;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.category.service.ISysCategoryAuthFilter;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 分类权限过滤
 */
public class KmReviewTemplateAuthFilter implements ISysCategoryAuthFilter {

	// _sys_simple_cate_dialog\_sys_cate_dialog\_cateSelect

	private final static String OPT_CREATE = "_cateSelect"; // fdIsMobileCreate

	private final static String OPT_CRITERIA = "_sys_cate_dialog"; // fdIsMobileView

	@Override
    public void hqlFilter(RequestContext request, HQLInfo hqlInfo)
			throws Exception {
		if (MobileUtil
				.getClientType(Plugin.currentRequest()) >= MobileUtil.WEB) {
			String extPara = request.getParameter("extendPara");
			if (StringUtil.isNotNull(extPara) && extPara.indexOf("key") > -1) {
				String whereBlock = null;
				String key = getExtParams(extPara).get("key");
				if (OPT_CRITERIA.equals(key)) {// 分类筛选情况
					whereBlock = "(kmReviewTemplate.fdIsMobileView = :enable or kmReviewTemplate.fdIsMobileView is null) "
							+ "and (kmReviewTemplate.fdIsAvailable = :fdIsAvailable or kmReviewTemplate.fdIsAvailable is null) ";
				} else { // 新建选择分类情况
					whereBlock = "(kmReviewTemplate.fdIsMobileCreate = :enable or kmReviewTemplate.fdIsMobileCreate is null) "
							+ "and (kmReviewTemplate.fdIsAvailable = :fdIsAvailable or kmReviewTemplate.fdIsAvailable is null) ";
				}
				hqlInfo.setWhereBlock(
						StringUtil.isNull(hqlInfo.getWhereBlock()) ? whereBlock
								: (hqlInfo.getWhereBlock() + " and ("
										+ whereBlock + ")"));
				hqlInfo.setParameter("enable", true);
				hqlInfo.setParameter("fdIsAvailable", true);
			}
		}
	}

	private Map<String, String> getExtParams(String extPara) {
		String[] paras = extPara.split(";");
		Map<String, String> params = new HashMap<String,String>();
		for (int i = 0; i < paras.length; i++) {
			if(StringUtil.isNotNull(paras[i])){
				String[] tmpParas = paras[i].split(":");
				if (tmpParas.length > 1) {
					params.put(tmpParas[0], tmpParas[1]);
				} else {
					params.put(tmpParas[0], null);
				}
			}
		}
		return params;
	}

}
