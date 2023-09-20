package com.landray.kmss.sys.zone.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.intercept.RoleValidator;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.organization.eco.IOrgRangeService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.zone.model.SysZoneAddressCateVo;
import com.landray.kmss.sys.zone.model.SysZoneOrgOuter;
import com.landray.kmss.sys.zone.model.SysZoneOrgRelation;
import com.landray.kmss.sys.zone.service.ISysZoneAddressCateVoService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgOuterService;
import com.landray.kmss.sys.zone.service.ISysZoneOrgRelationService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysZoneAddressFinder implements IXMLDataBean, SysOrgConstant {
	private ISysOrgElementService sysOrgElementService;

	private ISysOrgPersonService sysOrgPersonService;

	private ISysZoneOrgOuterService outerService;

	public void setOuterPersonService(ISysZoneOrgOuterService outerService) {
		this.outerService = outerService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public ISysOrgPersonService getSysOrgPersonService() {
		return sysOrgPersonService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private RoleValidator roleValidator;

	public void setRoleValidator(RoleValidator roleValidator) {
		this.roleValidator = roleValidator;
	}

	public RoleValidator getRoleValidator() {
		return roleValidator;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IOrgRangeService orgRangeService;

	public void setOrgRangeService(IOrgRangeService orgRangeService) {
		this.orgRangeService = orgRangeService;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		ArrayList rtnMapList = new ArrayList();
		ISysCategoryMainService categoryMainService = (ISysCategoryMainService) SpringBeanUtil
				.getBean("sysCategoryMainService");
		ISysZoneOrgRelationService relationService = (ISysZoneOrgRelationService) SpringBeanUtil
				.getBean("sysZoneOrgRelationService");
		ISysZoneAddressCateVoService sysZoneAddressCateVoService =(ISysZoneAddressCateVoService) SpringBeanUtil
				.getBean("sysZoneAddressCateVoService");
		String cateType = requestInfo.getParameter("cateType");
		String fdCategoryId = requestInfo.getParameter("fdCategoryId");

		HashMap resultDataRow = null;
		JSONArray catePersons = null;
		JSONObject catePerson = null;

		// 取一级名称
		if(StringUtil.isNull(fdCategoryId)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysCategoryMain.fdModelName=:fdModelName");
			hqlInfo.setParameter("fdModelName", "com.landray.kmss.sys.zone.SysZoneAddressCate." + cateType);
			hqlInfo.setRowSize(Integer.MAX_VALUE);
			Page findPages = categoryMainService.findPage(hqlInfo);
			List<SysCategoryMain> finds = findPages.getList();
			for (SysCategoryMain item : finds) {
				SysOrgPerson creator = item.getDocCreator();
				SysOrgPerson person = UserUtil.getUser();
				if (!creator.getFdId().equals(person.getFdId())) {// 判断是否为创建者,不是创建者,取到当前事项类型
					SysZoneAddressCateVo cateVo = sysZoneAddressCateVoService
							.getCateVoByCateId(item.getFdId());
					if (cateVo != null) {
						String itemType = cateVo.getFdItemType();
						if ("private".equals(itemType)) {// 事项类型为个人,跳过该条记录
							continue;
						}
					}
				}
				resultDataRow = new HashMap();
				Boolean canEdit = false;
				Boolean canDel = false;
				if (UserUtil.checkAuthentication(
						"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=edit&fdId="
								+ item.getFdId() + "&cateType=" + cateType,
						"GET")) {
					canEdit = true;
				}
				if (UserUtil.checkAuthentication(
						"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=delete&fdId="
								+ item.getFdId() + "&cateType=" + cateType,
						"GET")) {
					canDel = true;
				}
				resultDataRow.put("cateName", item.getFdName());
				resultDataRow.put("cateId", item.getFdId());
				resultDataRow.put("canEdit", canEdit);
				resultDataRow.put("canDel", canDel);
				rtnMapList.add(resultDataRow);
			}
		} else {
			List<SysZoneOrgRelation> catePersonsList = relationService.findList("fdCategoryId='" + fdCategoryId + "'", "fdOrder asc");
			catePersons = new JSONArray();
			resultDataRow = new HashMap();
			if (catePersonsList.size() > 0) {
				for (SysZoneOrgRelation relationPerson : catePersonsList) {
					if ("inner".equals(relationPerson.getFdOrgType())) {
						SysOrgElement personEle = (SysOrgElement) sysOrgElementService
								.findByPrimaryKey(relationPerson.getFdOrgId());
						if (personEle != null && personEle.getFdIsAvailable()) {
							SysOrgPerson postPerson = (SysOrgPerson) sysOrgPersonService
									.findByPrimaryKey(
											relationPerson.getFdOrgId());
							catePerson = new JSONObject();
							catePerson.put("personId", personEle.getFdId());
							catePerson.put("personName", personEle.getFdName());
							if (null != postPerson.getFdStaffingLevel()) {
								catePerson.put("personDept",
										postPerson.getFdStaffingLevel()
												.getFdName());
							} else {
								catePerson.put("personDept", "");
							}
							catePerson.put("personType", "inner");
							String personImg = PersonInfoServiceGetter
									.getPersonHeadimageUrl(personEle.getFdId());
							if (!PersonInfoServiceGetter
									.isFullPath(personImg)) {
								personImg = requestInfo.getContextPath()
										+ personImg;
							}
							catePerson.put("personImg", personImg);
							catePersons.add(catePerson);
						}
					} else if ("outer".equals(relationPerson.getFdOrgType())) {
						SysZoneOrgOuter outerPerson = (SysZoneOrgOuter) outerService
								.findByPrimaryKey(relationPerson.getFdOrgId());
						catePerson = new JSONObject();
						catePerson.put("personId", outerPerson.getFdId());
						catePerson.put("personName", outerPerson.getFdName());
						catePerson.put("personDept",
								outerPerson.getFdPostDesc());
						catePerson.put("personType", "outer");
						catePerson.put("personImg", "");// 默认图片
						catePersons.add(catePerson);
					}
				}
			}
			resultDataRow.put("catePersons", catePersons);
			rtnMapList.add(resultDataRow);
		}
		return rtnMapList;
	}

}
