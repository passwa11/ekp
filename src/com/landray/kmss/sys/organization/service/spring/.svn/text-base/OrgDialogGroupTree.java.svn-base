package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgGroupCate;
import com.landray.kmss.sys.organization.service.ISysOrgGroupCateService;
import com.landray.kmss.sys.organization.service.ISysOrgGroupService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;

@Controller
@RequestMapping(value = "/data/sys-organization/organizationDialogGroupTree", method = RequestMethod.POST)
public class OrgDialogGroupTree implements IXMLDataBean, SysOrgConstant {

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
    @SuppressWarnings("unchecked")
	public List getDataList(RequestContext requestInfo) throws Exception {
		String parent = requestInfo.getParameter("parent");
		List<Map<String, Object>> rtnValue = new ArrayList<Map<String, Object>>();
		HQLInfo cateHqlInfo = new HQLInfo();
		cateHqlInfo.setJoinBlock("left join sysOrgGroupCate.hbmParent hbmParent");
		HQLInfo groupInfo = new HQLInfo();
		groupInfo.setJoinBlock("left join sysOrgGroup.hbmGroupCate hbmGroupCate");
		if (StringUtil.isNull(parent)) {
			cateHqlInfo.setWhereBlock("hbmParent is null");
			groupInfo
					.setWhereBlock("sysOrgGroup.hbmGroupCate is null and sysOrgGroup.fdIsAvailable= :fdIsAvailable");
			groupInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		} else {
			cateHqlInfo
					.setWhereBlock("hbmParent.fdId = :hbmParentId");
			cateHqlInfo.setParameter("hbmParentId", parent);
			groupInfo
					.setWhereBlock("hbmGroupCate.fdId = :hbmGroupCateId and sysOrgGroup.fdIsAvailable= :fdIsAvailable");
			groupInfo.setParameter("hbmGroupCateId", parent);
			groupInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		}
		// 查找群组类别
		List<SysOrgGroupCate> categoriesList = sysOrgGroupCateService
				.findList(cateHqlInfo);
		for (int i = 0; i < categoriesList.size(); i++) {
			SysOrgGroupCate sysOrgGroupCate = categoriesList
					.get(i);
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("text", sysOrgGroupCate.getFdName());
			node.put("value", sysOrgGroupCate.getFdId());
			node.put("nodeType", "cate");
			rtnValue.add(node);
		}
		groupInfo.setAuthCheckType("DIALOG_GROUP_READER");
		// 如果是外部组织，需要判断是否有指定权限
		if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal()) {
			groupInfo.setJoinBlock(StringUtil.linkString(groupInfo.getJoinBlock(), " ",
					"left join sysOrgGroup.authReaders authReaders"));
			groupInfo.setWhereBlock(StringUtil.linkString(groupInfo.getWhereBlock(), " and ",
					"authReaders.fdId in (:orgIds)"));
			groupInfo.setParameter("orgIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		}
		// 按“排序号”进行顺序排序，如果“排序号”相同，则按ID排序
		groupInfo.setOrderBy("sysOrgGroup.fdOrder, sysOrgGroup.fdId");
		List<SysOrgGroup> moduleList = sysOrgGroupService.findList(groupInfo);
		// 查找群组
		for (int i = 0; i < moduleList.size(); i++) {
			SysOrgGroup sysOrgGroup = moduleList.get(i);
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("text", sysOrgGroup.getFdName());
			node.put("value", sysOrgGroup.getFdId());
			node.put("nodeType", "group");
			node.put("isAutoFetch", false);
			node.put("isAvailable", sysOrgGroup.getFdIsAvailable().toString());
			rtnValue.add(node);
		}
		return rtnValue;
	}

	private ISysOrgGroupCateService sysOrgGroupCateService;

	public void setSysOrgGroupCateService(
			ISysOrgGroupCateService sysOrgGroupCateService) {
		this.sysOrgGroupCateService = sysOrgGroupCateService;
	}

	private ISysOrgGroupService sysOrgGroupService;

	public void setSysOrgGroupService(ISysOrgGroupService sysOrgGroupService) {
		this.sysOrgGroupService = sysOrgGroupService;
	}
}
