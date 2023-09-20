package com.landray.kmss.km.imeeting.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.imeeting.service.IKmImeetingResCategoryService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.simplecategory.actions.SysSimpleCategoryAction;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 会议室分类 Action
 */
public class KmImeetingResCategoryAction extends SysSimpleCategoryAction {
	protected IKmImeetingResCategoryService kmImeetingResCategoryService;
	private ISysOrgCoreService sysOrgCoreService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (kmImeetingResCategoryService == null) {
            kmImeetingResCategoryService = (IKmImeetingResCategoryService) getBean("kmImeetingResCategoryService");
        }
		return kmImeetingResCategoryService;
	}

	protected ISysOrgCoreService
			getSysOrgCoreService(HttpServletRequest request) {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) getBean(
					"sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	public ActionForward defCates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysOrgElement p = UserUtil.getUser();
		List<?> authOrgIds = getSysOrgCoreService(request)
				.getOrgsUserAuthInfo(p).getAuthOrgIds();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingResCategory.fdHierarchyId");
		hqlInfo.setJoinBlock(
				"left join kmImeetingResCategory.defReaders defReaders");
		hqlInfo.setWhereBlock(
				HQLUtil.buildLogicIN("defReaders.fdId", authOrgIds));
		List<?> cateIdList = getServiceImp(request).findValue(hqlInfo);
		StringBuffer sb = new StringBuffer();
		for (Object obj : cateIdList) {
			sb.append(obj.toString() + ";");
		}
		JSONObject json = new JSONObject();
		String cateIds = sb.toString();
		if (StringUtil.isNotNull(cateIds)) {
            cateIds = cateIds.substring(0, cateIds.length() - 1);
        }
		json.put("cateIds", cateIds);
		response.getWriter().write(json.toString());
		response.setCharacterEncoding("UTF-8");
		return null;
	}
}
