package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.rest.util.ControllerHelper;
import com.landray.kmss.common.dto.HttpRequestParameterWrapper;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/data/sys-organization/organizationUserDept", method = RequestMethod.POST)
public class OrganizationUserDept implements IXMLDataBean, SysOrgConstant {
	private ISysOrgElementService sysOrgElementService;

	@ResponseBody
	@RequestMapping("getDataList")
	public RestResponse<?> getDataList(@RequestBody Map<String, Object> vo, HttpServletRequest request) throws Exception {
		HttpRequestParameterWrapper wrapper = ControllerHelper.buildRequestParameterWrapper(request, vo);
		return RestResponse.ok(getDataList(new RequestContext(wrapper)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getDataList(RequestContext xmlContext) throws Exception {
		// 判断是否有扩充的配置，注意扩充时必须将该部分代码删除
		IXMLDataBean extension = OrgDialogUtil.getExtension("userDept");
		if (extension != null && extension != this) {
            return extension.getDataList(xmlContext);
        }
		// 查找当前用户的部门信息
		SysOrgElement element = sysOrgElementService.format(UserUtil.getUser());
		// 根据配置参数定位到用户所在部门的第？级单位
		// String navigateTo = ResourceUtil
		// .getKmssConfigString("kmss.org.addrBookMyDeptNavigateTo");
		element = navigateTo(element, null);

		List rtnMapList = new ArrayList();
		String startWith = xmlContext.getParameter("startWith");
		if (element != null && !element.getFdId().toString().equals(startWith)) {
			for (; element != null; element = element.getFdParent()) {
				if (element.getFdId().toString().equals(startWith)) {
                    break;
                }
				HashMap map = new HashMap();
				map.put("value", element.getFdId());
				map.put("text", element.getFdName());
				map.put("title", element.getFdName());
				map.put("isAvailable", element.getFdIsAvailable().toString());
				rtnMapList.add(map);
			}
			// 开启生态组织时，外部人员在最外层需要增加一个虚拟机构
			if (SysOrgEcoUtil.IS_ENABLED_ECO && SysOrgEcoUtil.isExternal(element)) {
				HashMap map = new HashMap();
				map.put("value", "eco_org");
				map.put("text", ResourceUtil.getString("sys-organization:sysOrgEco.name"));
				map.put("title", ResourceUtil.getString("sys-organization:sysOrgEco.name"));
				map.put("isAvailable", "true");
				rtnMapList.add(map);
			}
		}
		return rtnMapList;
	}

	/**
	 * 根据配置定位到用户所在部门的第？级单位
	 * 
	 * @param element
	 *            当前用户
	 * @param navigateTo
	 *            定位到第几级 （正数是从上往下推，负数是从下往上推，为0或没配置时默认定位到本部门）
	 *            例如：“蓝凌机构/深圳蓝凌/研发中心/产品部”中的一员工在产品部
	 *            ，当查看地址本中我的部门，navigateTo为2的时候，定位到“深圳蓝凌”，
	 *            当navigateTo为-2的时候，定位到“研发中心”。
	 * @return
	 */
	private SysOrgElement navigateTo(SysOrgElement element, String navigateTo) {
		if (StringUtil.isNotNull(navigateTo) && !"0".equals(navigateTo)) {
			HashMap<Integer, SysOrgElement> map = new HashMap<Integer, SysOrgElement>();
			int i = 0;
			for (element = element.getFdParent(); element != null; element = element
					.getFdParent()) {
				i++;
				map.put(i, element);// 设定实际部门级数：“蓝凌机构(4)/深圳蓝凌(3)/研发中心(2)/产品部(1)”
			}
			int location = Integer.parseInt(navigateTo);
			if (location > 0) {
				// 当设置的级数大于部门实际级数时，定位到本部门，否则定位到(i + 1 - location)
				element = (i + 1 - location) <= 0 ? map.get(1) : map.get(i + 1
						- location);
			} else {
				location = Math.abs(location);
				// 当设置的级数绝对值大于部门实际级数时，定位到最高一级部门，否则定位到location
				element = location > i ? map.get(i) : map.get(location);
			}
		} else {
			element = element.getFdParent();// 如果没有配置或配置为0，默认定位到本部门
		}
		return element;
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

}
