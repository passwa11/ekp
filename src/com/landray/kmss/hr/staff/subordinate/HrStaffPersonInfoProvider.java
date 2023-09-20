package com.landray.kmss.hr.staff.subordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.subordinate.plugin.AbstractSubordinateProvider;
import com.landray.kmss.sys.subordinate.plugin.PropertyItem;
import com.landray.kmss.sys.tag.service.ISysTagMainRelationService;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
 * 下属档案提供者
 * @author 潘永辉 2019年3月15日
 *
 */
public class HrStaffPersonInfoProvider extends AbstractSubordinateProvider {
	private ISysOrgPersonService sysOrgPersonService;
	private ISysTagMainRelationService sysTagMainRelationService;

	public ISysTagMainRelationService getSysTagMainRelationService() {
		if (sysTagMainRelationService == null) {
			sysTagMainRelationService = (ISysTagMainRelationService) SpringBeanUtil.getBean("sysTagMainRelationService");
		}
		return sysTagMainRelationService;
	}
	
	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
            sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
		return sysOrgPersonService;
	}

	@Override
	public List<PropertyItem> items() {
		List<PropertyItem> items = new ArrayList<PropertyItem>();
		// 人员
		items.add(new PropertyItem("fdOrgPerson", ""));
		return items;
	}
	
	@Override
	public Page findPage(RequestContext request, HQLInfo hqlInfo, IBaseService service) throws Exception {
		// 查询人员时，除了查询fdOrgParent，还需要查询fdOrgPerson.hbmParent
		String whereBlock = "(hrStaffPersonInfo.fdOrgPerson.fdId in (select fdId from SysOrgElement elem where elem.fdHierarchyId like '" + hierarchyId + "%')"
				+ " or hrStaffPersonInfo.fdOrgParent.fdId in (select fdId from SysOrgElement elem where elem.fdHierarchyId like '" + hierarchyId + "%'))";
		hqlInfo.setWhereBlock(whereBlock);
		HQLHelper.by(request.getRequest()).buildHQLInfo(hqlInfo, HrStaffPersonInfo.class);
		changeFindPageHQLInfo2(request, hqlInfo);
		Page page = service.findPage(hqlInfo);
		List<HrStaffPersonInfo> staffInfoList = page.getList();
		JSONObject urlJson = new JSONObject();
		String staffId = "";
		for (HrStaffPersonInfo hrStaffPersonInfo : staffInfoList) {
			staffId = hrStaffPersonInfo.getFdId();
			urlJson.put(staffId, HrStaffPersonUtil.getImgUrl(hrStaffPersonInfo, request.getRequest()));
		}
		request.setAttribute("queryPage", page);
		request.setAttribute("urlJson", urlJson);
		return page;
	}

	public void changeFindPageHQLInfo2(RequestContext request, HQLInfo hqlInfo) throws Exception {
		String _fdKey = request.getParameter("q._fdKey");
		String[] _fdLabel = request.getParameterValues("q._fdLabel");
		String[] _fdStatus = request.getParameterValues("q._fdStatus");
		StringBuffer whereBlock = new StringBuffer();
		whereBlock.append(hqlInfo.getWhereBlock());

		// 姓名、登录名、手机号或邮箱
		if (StringUtil.isNotNull(_fdKey)) {
			// 查询组织机构人员信息
			HQLInfo _hqlInfo = new HQLInfo();
			_hqlInfo.setSelectBlock("sysOrgPerson.fdId");
			_hqlInfo.setWhereBlock("sysOrgPerson.fdName like :fdKey or sysOrgPerson.fdLoginName like :fdKey or sysOrgPerson.fdMobileNo like :fdKey or sysOrgPerson.fdEmail like :fdKey");
			_hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
			List<String> ids = getSysOrgPersonService().findValue(_hqlInfo);

			whereBlock.append(" and ((hrStaffPersonInfo.fdName like :fdKey or hrStaffPersonInfo.fdMobileNo like :fdKey or hrStaffPersonInfo.fdEmail like :fdKey)");
			if (!ids.isEmpty()) {
				whereBlock.append(" or hrStaffPersonInfo.fdId in (:ids)");
				hqlInfo.setParameter("ids", ids);
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdKey", "%" + _fdKey + "%");
		}
		// 员工状态
		if (_fdStatus != null && _fdStatus.length > 0) {
			List<String> fdStatus = new ArrayList<String>();
			boolean isNull = false;
			for (String _fdStatu : _fdStatus) {
				if ("official".equals(_fdStatu)) {
					isNull = true;
				}
				fdStatus.add(_fdStatu);
			}
			whereBlock.append(" and (hrStaffPersonInfo.fdStatus in (:fdStatus)");
			if (isNull) {
				whereBlock.append(" or hrStaffPersonInfo.fdStatus is null");
			}
			whereBlock.append(")");
			hqlInfo.setParameter("fdStatus", fdStatus);
		}
		// 个人标签
		if (_fdLabel != null && _fdLabel.length > 0) {
			// 先查询有哪些员工使用这个标签
			HQLInfo tagHqlInfo = new HQLInfo();
			tagHqlInfo.setSelectBlock("sysTagMainRelation.fdMainTag.fdModelId");
			tagHqlInfo.setWhereBlock("sysTagMainRelation.fdTagName in (:fdTagNames)");
			tagHqlInfo.setParameter("fdTagNames", Arrays.asList(_fdLabel));
			List list = getSysTagMainRelationService().findValue(tagHqlInfo);
			if (list.isEmpty()) {
				// 如果根据标签没有找到相应的员工，那表示此条件无数据
				whereBlock.append(" and 1 != 1");
			} else {
				whereBlock.append(" and hrStaffPersonInfo.fdId in (:fdIds)");
				hqlInfo.setParameter("fdIds", list);
			}
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
	}

	@Override
	public String getUrlParams(RequestContext request, IExtendForm rtnForm) throws Exception {

		// 返回自定义url参数
		if(StringUtil.isNotNull(request.getRequest().getParameter("readOnly"))){
			return "readOnly="+request.getRequest().getParameter("readOnly");
		}
		return super.getUrlParams(request, rtnForm);
	}

}
