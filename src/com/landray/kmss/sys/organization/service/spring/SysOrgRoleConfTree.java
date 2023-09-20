package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgRoleConf;
import com.landray.kmss.sys.organization.model.SysOrgRoleConfCate;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfCateService;
import com.landray.kmss.sys.organization.service.ISysOrgRoleConfService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * @author 陈亮
 * @version 创建时间：2008-11-21 下午01:50:15 类说明
 */
public class SysOrgRoleConfTree implements IXMLDataBean {

	private ISysOrgRoleConfCateService sysOrgRoleConfCateService;
	private ISysOrgRoleConfService sysOrgRoleConfService;

	public void setSysOrgRoleConfCateService(
			ISysOrgRoleConfCateService sysOrgRoleConfCateService) {
		this.sysOrgRoleConfCateService = sysOrgRoleConfCateService;
	}

	public void setSysOrgRoleConfService(
			ISysOrgRoleConfService sysOrgRoleConfService) {
		this.sysOrgRoleConfService = sysOrgRoleConfService;
	}

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		String parent = requestInfo.getParameter("parent");
		List<Map<String, Object>> rtnValue = new ArrayList<Map<String, Object>>();
		HQLInfo cateHqlInfo = new HQLInfo();
		HQLInfo roleConfInfo = new HQLInfo();
		String param = requestInfo.getParameter("auth");
		if ("1".equals(param)) {
			roleConfInfo.setAuthCheckType(HQLInfo.AUTH_CHECK_EDITOR);
		} else {
			roleConfInfo.setAuthCheckType("DIALOG_ROLE_READER");
		}
		
		roleConfInfo.setJoinBlock("left join sysOrgRoleConf.fdRoleConfCate fdRoleConfCate");
		
		if (StringUtil.isNull(parent)) {
			cateHqlInfo.setWhereBlock("sysOrgRoleConfCate.hbmParent is null");
			roleConfInfo
					.setWhereBlock("fdRoleConfCate is null and sysOrgRoleConf.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true));
		} else {
			cateHqlInfo
					.setWhereBlock("sysOrgRoleConfCate.hbmParent.fdId = :hbmParentId");
			cateHqlInfo.setParameter("hbmParentId", parent);
			roleConfInfo
					.setWhereBlock("fdRoleConfCate.fdId = :fdRoleConfCateId and sysOrgRoleConf.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true));
			roleConfInfo.setParameter("fdRoleConfCateId", parent);
		}
		roleConfInfo.setOrderBy(" sysOrgRoleConf.fdOrder asc");
		// 查找角色线类别
		List<SysOrgRoleConfCate> categoriesList = sysOrgRoleConfCateService
				.findList(cateHqlInfo);
		for (int i = 0; i < categoriesList.size(); i++) {
			SysOrgRoleConfCate sysOrgRoleConfCate = (SysOrgRoleConfCate) categoriesList
					.get(i);
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("text", sysOrgRoleConfCate.getFdName());
			node.put("value", sysOrgRoleConfCate.getFdId());
			node.put("nodeType", "cate");
			rtnValue.add(node);
		}
		// 如果是外部组织，需要判断是否有指定权限
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			roleConfInfo.setWhereBlock(StringUtil.linkString(roleConfInfo.getWhereBlock(), " and ",
					"sysOrgRoleConf.sysRoleLineReaders.fdId in (:orgIds)"));
			roleConfInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		}
		List<SysOrgRoleConf> moduleList = sysOrgRoleConfService
				.findList(roleConfInfo);
		// 查找角色线
		for (int i = 0; i < moduleList.size(); i++) {
			SysOrgRoleConf sysOrgRoleConf = (SysOrgRoleConf) moduleList.get(i);
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("text", sysOrgRoleConf.getFdName());
			node.put("value", sysOrgRoleConf.getFdId());
			node.put("nodeType", "roleConf");
			node.put("isAutoFetch", false);
			rtnValue.add(node);
		}
		return rtnValue;
	}

	public List getDataListOld(RequestContext requestInfo) throws Exception {
		List rtnList = new ArrayList();
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = null;
		String param = requestInfo.getParameter("available");
		if (!"all".equals(param)) {
			whereBlock = "sysOrgRoleConf.fdIsAvailable=" + SysOrgHQLUtil.toBooleanValueString(true);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy("sysOrgRoleConf.fdOrder");
		param = requestInfo.getParameter("auth");
		if ("1".equals(param)) {
			hqlInfo.setAuthCheckType(HQLInfo.AUTH_CHECK_EDITOR);
		} else {
			hqlInfo.setAuthCheckType("DIALOG_ROLE_READER");
		}
		List<SysOrgRoleConf> list = sysOrgRoleConfService.findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				SysOrgRoleConf roleConf = list.get(i);
				HashMap node = new HashMap();
				node.put("text", roleConf.getFdName());
				node.put("value", roleConf.getFdId());
				rtnList.add(node);
			}
		}
		return rtnList;
	}
}
