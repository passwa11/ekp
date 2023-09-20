package com.landray.kmss.sys.oms.out;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.oms.SysOMSConstant;
import com.landray.kmss.sys.oms.out.interfaces.IOMSSynchroOutContext;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOMSCache;
import com.landray.kmss.sys.organization.service.ISysOMSCacheService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;

/**
 * OMS同步上下文内容
 * 
 * @author 吴兵
 * @version 1.0 2006-11-29
 */
public class OMSSynchroOutContextImp implements IOMSSynchroOutContext,
		SysOMSConstant,Cloneable {

	/*
	 * 应用系统标识
	 */
	private String appName;

	@Override
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return appName;
	}

	/*
	 * 需要增加的组织元素
	 */
	private List addOrgElements = new ArrayList();

	public void setAddOrgElements(List addOrgElements) {
		this.addOrgElements = addOrgElements;
	}

	@Override
	public List getAddOrgElements() {
		return addOrgElements;
	}

	/*
	 * 需要更新的组织元素
	 */
	private List updateOrgElements = new ArrayList();

	public void setUpdateOrgElements(List updateOrgElements) {
		this.updateOrgElements = updateOrgElements;
	}

	@Override
	public List getUpdateOrgElements() {
		return updateOrgElements;
	}

	/*
	 * 需要删除的组织元素
	 */
	private List deleteOrgElements = new ArrayList();

	public void setDeleteOrgElements(List deleteOrgElements) {
		this.deleteOrgElements = deleteOrgElements;
	}

	@Override
	public List getDeleteOrgElements() {
		return deleteOrgElements;
	}

	/**
	 * 如是同步成功，则删除相应的本地缓存记录
	 * 
	 */
	@Override
	public void deleteLocalOMSCache() throws Exception {
		sysOMSCacheService.deleteSysOMSCache(appName);
	}

	/**
	 * 如是同步成功，则删除相应的本地缓存记录
	 * 
	 * @param fdElementId
	 *            要删除的元素
	 */
	@Override
	public void deleteLocalOMSCache(String fdElementId) throws Exception {
		// 根据orgElementId和appType删除OMSCache中的数据
		SysOMSCache sysOMSCache = sysOMSCacheService.findSysOMSCache(
				fdElementId, appName);
		if (sysOMSCache != null) {
            sysOMSCacheService.delete(sysOMSCache);
        }
	}

	/**
	 * 更新相应的本地缓存记录
	 * 
	 * @param fdElementId
	 *            要删除的元素
	 */
	@Override
	public void updateLocalOMSCache(String fdElementId) throws Exception {
		SysOMSCache sysOMSCache = sysOMSCacheService.findSysOMSCache(
				fdElementId, appName);
		if (sysOMSCache != null) {
			sysOMSCache.setFdOpType(new Integer(
					SysOMSConstant.OMS_OP_FLAG_UPDATE));
			sysOMSCacheService.update(sysOMSCache);
		}
	}

	private ISysOMSCacheService sysOMSCacheService;

	public void setSysOMSCacheService(ISysOMSCacheService sysOMSCacheService) {
		this.sysOMSCacheService = sysOMSCacheService;
	}

	private List services;

	public void setServices(List services) {
		this.services = services;
	}

	private void init() {
		addOrgElements = new ArrayList();
		updateOrgElements = new ArrayList();
		deleteOrgElements = new ArrayList();
	}

	@Override
	public void initIncContext() throws Exception {
		if (appName == null) {
            return;
        }
		init();
		for (int i = 0; i < services.size(); i++) {
			addOrgElements.addAll(getSysOrgElement(
					((ISysOrgElementService) services.get(i)).getModelName(),
					appName, OMS_OP_FLAG_ADD));
		}
		for (int i = 0; i < services.size(); i++) {
			updateOrgElements.addAll(getSysOrgElement(
					((ISysOrgElementService) services.get(i)).getModelName(),
					appName, OMS_OP_FLAG_UPDATE));
		}

		deleteOrgElements.addAll(findSysOrgElementIds(appName,
				OMS_OP_FLAG_DELETE));

	}

	private List getSysOrgElement(String className, String appName, int type)
			throws Exception {
		return SysOMSFacade.getOMSObject(sysOMSCacheService.findSysOrgELements(
				className, appName, type));
	}

	private List findSysOrgElementIds(String appName, int type)
			throws Exception {
		return sysOMSCacheService.findSysOrgElementIds(appName, type);
	}

	@Override
	public void initAllContext() throws Exception {
		if (appName == null) {
            return;
        }
		init();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdIsBusiness=:isBusiness and fdIsAvailable=:isAvailable");
		hqlInfo.setParameter("isBusiness", Boolean.TRUE);
		hqlInfo.setParameter("isAvailable", Boolean.TRUE);
		for (int i = 0; i < services.size(); i++) {
			addOrgElements.addAll(SysOMSFacade
					.getOMSObject(((ISysOrgElementService) services.get(i)).findList(hqlInfo)));
		}
	}
	
	@Override
	public Object clone() {  
		OMSSynchroOutContextImp o = null;  
        try {  
            o = (OMSSynchroOutContextImp) super.clone();  
            o.addOrgElements=new ArrayList();
            o.updateOrgElements = new ArrayList();
            o.deleteOrgElements = new ArrayList();
            o.appName = new String();
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }  

}
