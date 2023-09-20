package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPersonAddressType;
import com.landray.kmss.sys.organization.service.ISysOrgPersonAddressTypeService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/data/sys-organization/organizationDialogMyAddress", method = RequestMethod.POST)
public class OrgDialogMyAddress implements IXMLDataBean, SysOrgConstant {
	private ISysOrgPersonAddressTypeService sysOrgPersonAddressTypeService;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	private IOrgRangeService orgRangeService;

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String id = requestInfo.getParameter("id");
		String ids = requestInfo.getParameter("ids");
		if (StringUtil.isNotNull(ids)) {
			String[] ids_array = ids.trim().split(";");
			List elemList = sysOrgPersonAddressTypeService
					.findByPrimaryKeys(ids_array);

			return getResultEntries(elemList, requestInfo
					.getContextPath());
		}
		if (StringUtil.isNull(id)) {
			return getTreeData();
		} else {
			int orgType = ORG_TYPE_DEFAULT;
			String para = requestInfo.getParameter("orgType");
			if (StringUtil.isNotNull(para)) {
				try {
					orgType = Integer.parseInt(para);
				} catch (NumberFormatException e) {
				}
			}
			return getListData(id, orgType, requestInfo);
		}
	}

	private List getTreeData() throws Exception {
		List<SysOrgPersonAddressType> addressTypeList = sysOrgPersonAddressTypeService
				.findList("docCreator.fdId='" + UserUtil.getUser().getFdId()
						+ "'", "fdOrder");
		List rtnMapList = new ArrayList();
		for (SysOrgPersonAddressType addressType : addressTypeList) {
			Map map = new HashMap();
			map.put("text", addressType.getFdName());
			map.put("value", addressType.getFdId());
			map.put("nodeType", "cate"); // #41144：此处的个人常用群组不属于组织机构，所以不能直接使用，只能归属于“分类”
			rtnMapList.add(map);
		}
		return rtnMapList;
	}

	private List getListData(String id, int orgType, RequestContext requestInfo)
			throws Exception {
		SysOrgPersonAddressType addressType = (SysOrgPersonAddressType) sysOrgPersonAddressTypeService
				.findByPrimaryKey(id);
		List<SysOrgElement> elemList = addressType.getSysOrgPersonTypeList();
		elemList = sysOrganizationStaffingLevelService
				.getStaffingLevelFilterResult(elemList);
		List rtnMapList = new ArrayList();
		String isExternal = requestInfo.getParameter("isExternal");
		for (SysOrgElement elem : elemList) {
			if (elem.getFdIsAvailable() && (elem.getFdOrgType() & orgType) > 0) {
				if (SysOrgEcoUtil.IS_ENABLED_ECO) {
					// 内外组织隔离
					if (StringUtil.isNotNull(isExternal)) {
						if (!Boolean.valueOf(isExternal).equals(elem.getFdIsExternal())) {
							continue;
						}
					}
				} else {
					// 未开启生态组织，过滤外部组织
					if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
						continue;
					}
				}
				Map<String, String> rtnMap = OrgDialogUtil.getResultEntry(elem,
						requestInfo.getContextPath());
				// #89249 添加一个canShowMore字段让前端标示是不是可以选择下一级
				rtnMap.put("canShowMore", "false");
				rtnMapList.add(rtnMap);
			}
		}
		return rtnMapList;
	}

	public void setSysOrgPersonAddressTypeService(
			ISysOrgPersonAddressTypeService sysOrgPersonAddressTypeService) {
		this.sysOrgPersonAddressTypeService = sysOrgPersonAddressTypeService;
	}
	// 个人常用群组获取群组名
	public static List getResultEntries(List groupList, String contextPath)
			throws Exception {
		List entries = new ArrayList();
		for (int i = 0; i < groupList.size(); i++) {
			entries.add(
					getResultEntry((SysOrgPersonAddressType) groupList.get(i),
							contextPath));
		}
		return entries;
	}
	public static Map<String, String> getResultEntry(
			SysOrgPersonAddressType group,
			String contextPath) throws Exception {
		HashMap<String, String> entry = new HashMap<String, String>();
		String path = contextPath;
		String img = path
				+ "/sys/ui/extend/theme/default/images/address/address_orgType_group.png";
		entry.put("id", group.getFdId());
		entry.put("name", group.getFdName());
		entry.put("img", img);

		return entry;
	}


}
