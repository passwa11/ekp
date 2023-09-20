package com.landray.kmss.sys.organization.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgRoleLine;
import com.landray.kmss.sys.organization.service.ISysOrgRoleLineService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 读取角色线人员树
 * 
 * @author 叶中奇
 * @version 创建时间：2008-11-21 下午04:47:25
 */
public class SysOrgRoleLineTree implements IXMLDataBean {

	private ISysOrgRoleLineService sysOrgRoleLineService = null;

	public void setSysOrgRoleLineService(
			ISysOrgRoleLineService sysOrgRoleLineService) {
		this.sysOrgRoleLineService = sysOrgRoleLineService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List getDataList(RequestContext requestInfo) throws Exception {
		String confId = requestInfo.getParameter("confId");
		String lineId = requestInfo.getParameter("lineId");
		String nodept = requestInfo.getParameter("nodept");

		String orgId = requestInfo.getParameter("orgId");
		if (StringUtil.isNotNull(orgId)) {
			return getDataList(confId, orgId);
		}
		String key = requestInfo.getParameter("key");
		if (StringUtil.isNotNull(key)) {
			return getDataListByKey(confId, key, nodept);
		}
		List rtnList = new ArrayList();
		StringBuffer whereBlock = new StringBuffer(
				"sysOrgRoleLine.sysOrgRoleConf.fdId=:confId and sysOrgRoleLine.hbmParent");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setParameter("confId", confId);
		if (StringUtil.isNull(lineId)) {
			whereBlock.append(" is null");
		} else {
			whereBlock.append(".fdId=:lineId");
			hqlInfo.setParameter("lineId", lineId);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy("sysOrgRoleLine.fdOrder");
		List<SysOrgRoleLine> list = sysOrgRoleLineService.findList(hqlInfo);
		for (int i = 0; i < list.size(); i++) {
			SysOrgRoleLine roleLine = list.get(i);
			Map map = new HashMap();
			
			SysOrgElement sysOrgRoleMember = roleLine.getSysOrgRoleMember();
			
			String fdName = roleLine.getFdName();
			if (sysOrgRoleMember != null) {
				if(SysOrgConstant.ORG_TYPE_POST== roleLine.getSysOrgRoleMember().getFdOrgType()){

						String disName = sysOrgRoleMember.getFdName()+"<"+ sysOrgRoleMember.getFdParentsName() +">";
						if (sysOrgRoleMember.getFdIsAvailable() != null && !sysOrgRoleMember.getFdIsAvailable()) // 无效
                        {
                            disName += "(" + ResourceUtil.getString("sysOrg.address.info.disable", "sys-organization") + ")";
                        }
						if (StringUtil.isNotNull(fdName)) {
                            disName = fdName + "(" + disName + ")";
                        }

						map.put("text", disName);
				}else {
                    map.put("text", roleLine.getDisName());
                }
			}else{
				map.put("text", roleLine.getDisName());
			}
			
			map.put("value", roleLine.getFdId());
			if (roleLine.getSysOrgRoleMember() != null) {
				int orgType = roleLine.getSysOrgRoleMember().getFdOrgType();
				map.put("nodeType", orgType);
				if (orgType == SysOrgConstant.ORG_TYPE_DEPT
						|| orgType == SysOrgConstant.ORG_TYPE_ORG) {
					if ("true".equals(nodept)) {
						continue;
					}
					map.put("isAutoFetch", "false");
				}
				map.put("isExternal", roleLine.getSysOrgRoleMember().getFdIsExternal());
			}
			rtnList.add(map);
		}
		return rtnList;
	}

	/**
	 * 根据用户Id,角色线配置ID展示定位当前角色
	 * 
	 */
	private List getDataList(String confId, String orgId) throws Exception {
		StringBuffer whereBlock = new StringBuffer(
				"sysOrgRoleLine.sysOrgRoleConf.fdId=:confId and sysOrgRoleMember.fdId=:orgId");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setParameter("confId", confId);
		hqlInfo.setParameter("orgId", orgId);
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy("sysOrgRoleLine.fdOrder");
		List list = sysOrgRoleLineService.findList(hqlInfo);

		SysOrgRoleLine currentRoleLine = (SysOrgRoleLine) list.get(0);
		List rtnMapList = new ArrayList();
		while (currentRoleLine != null) {
			HashMap map = new HashMap();
			map.put("value", currentRoleLine.getFdId());
			map.put("text", currentRoleLine.getDisName());
			map.put("hierarchyId", currentRoleLine.getFdHierarchyId());
			rtnMapList.add(map);
			currentRoleLine = (SysOrgRoleLine) currentRoleLine.getFdParent();
		}
		return rtnMapList;
	}

	private List getDataListByKey(String confId, String key, String nodept)
			throws Exception {
		// StringBuffer whereBf = new StringBuffer();

		StringBuffer whereBlock = new StringBuffer(
				"sysOrgRoleLine.sysOrgRoleConf.fdId=:confId");
		HQLInfo hqlInfo = new HQLInfo();

		// whereBf.append("e1.fdName like :key");
		// whereBf.append(" or e1.fdLoginName like :key");
		// whereBf.append(" or p1.fdNamePinYin like :key");

		String where_lang = "";
		String currentLocaleCountry = null;
		if (SysLangUtil.isLangEnabled()) {
			currentLocaleCountry = SysLangUtil
					.getCurrentLocaleCountry();
		}
		if (StringUtil.isNotNull(currentLocaleCountry) && !currentLocaleCountry
				.equals(SysLangUtil.getOfficialLang())) {
			where_lang = " or fdName" + currentLocaleCountry + " like :key";
		}

		whereBlock
				.append(" and (sysOrgRoleLine.sysOrgRoleMember.fdId in (select p1.fdId from com.landray.kmss.sys.organization.model.SysOrgPerson p1 where p1.fdLoginName like :key) or (sysOrgRoleLine.sysOrgRoleMember.fdId in (select e2.fdId from com.landray.kmss.sys.organization.model.SysOrgElement e2 where (e2.fdName like :key or e2.fdNamePinYin like :key"
						+ where_lang + "))))");
		hqlInfo.setParameter("key", "%" + key + "%");
		hqlInfo.setParameter("confId", confId);
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setOrderBy("sysOrgRoleLine.fdOrder");
		List<SysOrgRoleLine> list = sysOrgRoleLineService.findList(hqlInfo);
		List rtnList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			SysOrgRoleLine roleLine = list.get(i);
			Map map = new HashMap();
			map.put("text", roleLine.getDisName());
			map.put("value", roleLine.getFdId());
			map.put("hierarchyId", roleLine.getFdHierarchyId());
			if (roleLine.getSysOrgRoleMember() != null) {
				int orgType = roleLine.getSysOrgRoleMember().getFdOrgType();
				map.put("nodeType", orgType);
				if (orgType == SysOrgConstant.ORG_TYPE_DEPT
						|| orgType == SysOrgConstant.ORG_TYPE_ORG) {
					if ("true".equals(nodept)) {
						continue;
					}
					map.put("isAutoFetch", "false");
				}
			}
			rtnList.add(map);
		}

		return rtnList;
	}

}
