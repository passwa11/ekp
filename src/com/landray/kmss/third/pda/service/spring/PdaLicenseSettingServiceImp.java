package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.pda.model.PdaLicenseSetting;
import com.landray.kmss.third.pda.service.IPdaLicenseSettingService;
import com.landray.kmss.third.pda.util.LicenseUtil;

public class PdaLicenseSettingServiceImp extends BaseServiceImp implements
		IPdaLicenseSettingService {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PdaLicenseSettingServiceImp.class);

	private ISysOrgCoreService sysOrgCoreService = null;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public List getAccessorList(String sort) throws Exception {
		StringBuffer hqlInfo = new StringBuffer();
		hqlInfo.append("select pdaLicenseSetting.fdAccessor ")
		       .append("from PdaLicenseSetting pdaLicenseSetting ");
		
		//按照姓名降序排列
		if("name".equals(sort)){
			hqlInfo.append("order by pdaLicenseSetting.fdAccessor.fdName asc");
		//按照部门降序排列
		}else if("depart".equals(sort)){
			hqlInfo.append("order by pdaLicenseSetting.fdAccessor.hbmParent asc");
		}else{
			hqlInfo.append("order by pdaLicenseSetting.fdAccessor.fdName asc");		
		}
		
		List rtnList = getBaseDao().getHibernateSession().createQuery(hqlInfo.toString()).list();
		if (rtnList != null && rtnList.size() > 0) {
			Map<String, String> personMap = new HashMap<String, String>();
			for (Iterator iterator = rtnList.iterator(); iterator.hasNext();) {
				SysOrgElement orgInfos = (SysOrgElement) iterator.next();
				personMap.put(orgInfos.getFdId(), orgInfos.getFdName());
			}
			// 设置内存中授权信息
			LicenseUtil.setPersonInfoMap(personMap);
		}
		return rtnList;
	}

	@Override
	public void addAccessorList(Object[] orgIds, boolean isReplace)
			throws Exception {
		if (isReplace) {
			String hqlSql = "delete from PdaLicenseSetting pdaLicenseSetting";
			int deletCount = deleteExecuteQuery(hqlSql);
			if (logger.isDebugEnabled()) {
                logger.debug("setAccessorList先删除可访问者:" + deletCount + " 个!");
            }
			flushHibernateSession();
		}
		for (int i = 0; i < orgIds.length; i++) {
			if (i > 0) {
                flushHibernateSession();
            }
			String tmpStr = (String) orgIds[i];
			SysOrgElement org = sysOrgCoreService.findByPrimaryKey(tmpStr);
			if (org != null) {
				if (!existed(tmpStr)) {
					PdaLicenseSetting pdaSetting = new PdaLicenseSetting();
					pdaSetting.setFdAccessor(org);
					getBaseDao().add(pdaSetting);
				} else {
					if (logger.isDebugEnabled()) {
                        logger.debug("setAccessorList已存在对象:" + tmpStr);
                    }
				}
			}

		}
		//清理pdaLicense缓存
		LicenseUtil.clearKmssCachePdaLicense();
	}

	@Override
	public void deleteOrgs(String[] orgIds) throws Exception {
		HQLInfo hqInfo = new HQLInfo();
		for (int i = 0; i < orgIds.length; i++) {
			if (i > 0) {
                flushHibernateSession();
            }
			hqInfo.setWhereBlock("pdaLicenseSetting.fdAccessor.fdId='"
					+ orgIds[i] + "'");
			List modelObjList = findValue(hqInfo);
			if (modelObjList != null && modelObjList.size() > 0) {
                for (Iterator iterator = modelObjList.iterator(); iterator
                        .hasNext();) {
                    getBaseDao().delete((BaseModel) iterator.next());
                }
            }
		}
		//清理pdaLicense缓存
		LicenseUtil.clearKmssCachePdaLicense();
	}

	@Override
	public boolean existed(String orgId) throws Exception {
		HQLInfo hqInfo = new HQLInfo();
		hqInfo.setSelectBlock("pdaLicenseSetting.fdId");
		hqInfo.setWhereBlock("pdaLicenseSetting.fdAccessor.fdId='" + orgId
				+ "'");
		List list = findValue(hqInfo);
		boolean isExisted = false;
		if (list != null && list.size() > 0) {
            isExisted = true;
        }
		return isExisted;
	}

	@Override
	public Object[] praseOrgInfo(String[] orgIds) throws Exception {
		List<SysOrgElement> orglist = null;
		List<String> personList = null;
		if (orgIds.length > 0) {
			orglist = new ArrayList<SysOrgElement>();
			for (int i = 0; i < orgIds.length; i++) {
				SysOrgElement sysOrg = sysOrgCoreService
						.findByPrimaryKey(orgIds[i]);
				if (sysOrg != null) {
                    orglist.add(sysOrg);
                }
			}
			orglist = sysOrgCoreService.expandToPerson(orglist);
			personList = new ArrayList<String>();
			for (Iterator iterator = orglist.iterator(); iterator.hasNext();) {
				SysOrgElement sysOrgElement = (SysOrgElement) iterator.next();
				personList.add(sysOrgElement.getFdId());
			}
		}
		return personList != null ? personList.toArray() : null;
	}

	@Override
	public int deleteExecuteQuery(String hql) throws Exception {
		return getBaseDao().getHibernateSession().createQuery(hql)
				.executeUpdate();
	}

}
