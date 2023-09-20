package com.landray.kmss.sys.oms.out.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.OrgLevelSort;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * OMS组织机构同步接出提供者，批量导出抽象基类
 * 
 * @author 刘声斌 2010-04-14
 * 
 */
public abstract class AbstractOMSSynchroOutProviderBatch implements
		IOMSSynchroOutProvider {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private SysQuartzJobContext jobContext;

	private IOMSSynchroOutContext context;

	private String orgId = "";

	private ISysOrgElementService sysOrgElementService = null;

	private ISysOrgCoreService sysOrgCoreService = null;

	/**
	 * 同步数据前，做一些初始化操作
	 * 
	 * @throws Exception
	 */
	protected abstract void init() throws Exception;

	/**
	 * 同步数据后，做一些收尾操作
	 * 
	 * @throws Exception
	 */
	protected abstract void destory() throws Exception;

	/**
	 * 增加根部门
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void addRootDept(OrgElementContext element)
			throws Exception;

	/**
	 * 修改根部门
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void updateOrg(OrgElementContext element)
			throws Exception;

	/**
	 * 删除部门，不包含子部门
	 * 
	 * @param dept
	 * @throws Exception
	 */
	protected abstract void deleteDeptNotChild(SysOrgElement dept)
			throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param person
	 * @throws Exception
	 */
	protected abstract void deletePerson(SysOrgPerson person) throws Exception;

	/**
	 * 批量增加部门
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void addDeptBatch(List<OrgElementContext> element)
			throws Exception;

	/**
	 * 批量修改部门
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void updateDeptBatch(List<OrgElementContext> element)
			throws Exception;

	/**
	 * 批量增加用户
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void addPersonBatch(List<OrgElementContext> element)
			throws Exception;

	/**
	 * 批量修改用户
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void updatePersonBatch(List<OrgElementContext> element)
			throws Exception;

	@Override
	public void synchro(IOMSSynchroOutContext context,
						SysQuartzJobContext jobContext) throws Exception {
		this.context = context;
		this.jobContext = jobContext;
		jobContext.logMessage("需要增加的组织机构数："
				+ context.getAddOrgElements().size());
		jobContext.logMessage("需要修改的组织机构数："
				+ context.getUpdateOrgElements().size());
		jobContext.logMessage("需要删除的组织机构数："
				+ context.getDeleteOrgElements().size());

		try {
			// 同步前的一些初始化操作
			init();
			setOrgId();
			List availableElements = getAvailableElements(context
					.getUpdateOrgElements());
			addRootOrgFun(context.getAddOrgElements());
			updateRootOrgFun(availableElements);

			addDeptBatch(context.getAddOrgElements());
			updateDeptBatch(availableElements);
			addPersonBatch(context.getAddOrgElements());
			updatePersonBatch(availableElements);

			deleteElements(context.getDeleteOrgElements());
			deleteCache((OrgElementContext[]) context.getAddOrgElements()
					.toArray(new OrgElementContext[] {}));
			deleteCache((OrgElementContext[]) availableElements
					.toArray(new OrgElementContext[] {}));
			deleteCache(context.getDeleteOrgElements());

		} catch (Exception e) {
			jobContext.logError(e);
		} finally {
			// 同步后一些收尾工作
			destory();
			this.jobContext = null;
			this.context = null;
		}
	}

	/**
	 * 获取有效的用户
	 * 
	 * @param elements
	 * @return
	 */
	private List getAvailableElements(List elements) {
		List l = new ArrayList();
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			OrgElementContext el = (OrgElementContext) iter.next();
			if (el.getFdIsAvailable()) {
				l.add(el);
			}
			if (!el.getFdIsAvailable()
					&& el.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON) {
				l.add(el);
			}
		}
		return l;
	}

	/**
	 * 获取根机构ID
	 * 
	 * @throws Exception
	 */
	private void setOrgId() throws Exception {
		List list = sysOrgElementService
				.findList("hbmParent is null and fdOrgType="
						+ SysOrgElement.ORG_TYPE_ORG, null);
		if (!list.isEmpty()) {
			orgId = ((SysOrgElement) list.get(0)).getFdId();
		}
	}

	/**
	 * 删除组织架构元素
	 * 
	 * @param rtx
	 * @param elementIds
	 * @throws Exception
	 */
	private void deleteElements(List elementIds) throws Exception {
		for (Iterator iter = elementIds.iterator(); iter.hasNext();) {
			SysOrgElement element = null;
			try {
				element = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey((String) iter.next());
			} catch (Exception ex) {
				if (logger.isDebugEnabled()) {
                    logger.debug(ex.getMessage());
                }
			}

			if (element != null) {
				switch (element.getFdOrgType().intValue()) {
				case SysOrgConstant.ORG_TYPE_ORG:
					if (element.getFdId().equals(orgId)) {
						continue;
					}
					deleteOrgFun(element);
					break;
				case SysOrgConstant.ORG_TYPE_DEPT:
					deleteDeptFun(element);
					break;
				case SysOrgConstant.ORG_TYPE_PERSON:
					element = sysOrgElementService.format(element);
					deletePerson((SysOrgPerson) element);
					break;
				}
			}
		}
	}

	/**
	 * 删除机构
	 * 
	 * @param rtx
	 * @param org
	 * @throws Exception
	 */
	private void deleteOrgFun(SysOrgElement org) throws Exception {
		List persons = sysOrgCoreService.findAllChildren(org,
				SysOrgElement.ORG_TYPE_PERSON);
		for (Iterator iter = persons.iterator(); iter.hasNext();) {
			deletePerson((SysOrgPerson) iter.next());
		}
		List depts = sysOrgCoreService.findAllChildren(org,
				SysOrgElement.ORG_TYPE_DEPT);
		List<OrgLevelSort> dsorts = getLevelSortBySysOrg(depts);
		Collections.sort(dsorts);
		for (int i = dsorts.size(); i >= 0; i--) {
			deleteDeptNotChild((SysOrgElement) dsorts.get(i).getTarget());
		}
		for (int i = 0; i < org.getFdChildren().size(); i++) {
			SysOrgElement element = (SysOrgElement) org.getFdChildren().get(i);
			if (element.getFdOrgType().intValue() == SysOrgElement.ORG_TYPE_ORG) {
				deleteOrgFun(element);
			}
			deleteDeptNotChild(element);
		}
		deleteDeptNotChild(org);
	}

	/**
	 * 删除部门
	 * 
	 * @param rtx
	 * @param dept
	 * @throws Exception
	 */
	private void deleteDeptFun(SysOrgElement dept) throws Exception {
		List persons = sysOrgCoreService.findAllChildren(dept,
				SysOrgElement.ORG_TYPE_PERSON);
		for (Iterator iter = persons.iterator(); iter.hasNext();) {
			deletePerson((SysOrgPerson) iter.next());
		}
		List depts = sysOrgCoreService.findAllChildren(dept,
				SysOrgElement.ORG_TYPE_DEPT);
		List<OrgLevelSort> dsorts = getLevelSortBySysOrg(depts);
		Collections.sort(dsorts);
		for (int i = dsorts.size() - 1; i >= 0; i--) {
			deleteDeptNotChild((SysOrgElement) dsorts.get(i).getTarget());
		}
		deleteDeptNotChild(dept);
	}

	/**
	 * 增加根机构，不适合多个根机构
	 * 
	 * @param sysOrgElementList
	 * @throws Exception
	 */
	private void addRootOrgFun(List sysOrgElementList) throws Exception {
		int iRet = -1;
		for (Iterator iter = sysOrgElementList.iterator(); iter.hasNext();) {
			OrgElementContext element = (OrgElementContext) iter.next();
			if (element.getFdOrgType() == SysOrgConstant.ORG_TYPE_ORG
					&& element.getFdParent() == null) {
				logger.debug("增加根部门'" + element.getFdName());
				addRootDept(element);
			}
		}
	}

	/**
	 * 修改根部门
	 * 
	 * @param rtxSrvApi
	 * @param sysOrgElementList
	 * @throws Exception
	 */
	private void updateRootOrgFun(List sysOrgElementList) throws Exception {
		for (Iterator iter = sysOrgElementList.iterator(); iter.hasNext();) {
			OrgElementContext element = (OrgElementContext) iter.next();
			if (element.getFdOrgType() == SysOrgConstant.ORG_TYPE_ORG
					&& element.getFdParent() == null) {

				updateOrg(element);
			}
		}
	}

	private List<OrgLevelSort> getLevelSort(List elements) {
		List<OrgLevelSort> sorts = new ArrayList<OrgLevelSort>();
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			OrgElementContext element = (OrgElementContext) iter.next();
			String hierarchyId = element.getFdHierarchyId();
			if (hierarchyId.length() <= 1) {
				sorts.add(new OrgLevelSort(element, 0));
				continue;
			}
			hierarchyId = hierarchyId.substring(1);
			hierarchyId = hierarchyId.substring(0, hierarchyId.length() - 1);
			int level = hierarchyId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT).length;
			sorts.add(new OrgLevelSort(element, level));
		}
		return sorts;
	}

	private List<OrgLevelSort> getLevelSortBySysOrg(List elements) {
		List<OrgLevelSort> sorts = new ArrayList<OrgLevelSort>();
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			SysOrgElement element = (SysOrgElement) iter.next();
			String hierarchyId = element.getFdHierarchyId();
			if (hierarchyId.length() <= 1) {
				sorts.add(new OrgLevelSort(element, 0));
				continue;
			}
			hierarchyId = hierarchyId.substring(1);
			hierarchyId = hierarchyId.substring(0, hierarchyId.length() - 1);
			int level = hierarchyId.split(BaseTreeConstant.HIERARCHY_ID_SPLIT).length;
			sorts.add(new OrgLevelSort(element, level));
		}
		return sorts;
	}

	private List<OrgLevelSort> getLevelSortById(List elements) {
		List<OrgLevelSort> sorts = new ArrayList<OrgLevelSort>();
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			int level = 0;
			OrgElementContext element = (OrgElementContext) iter.next();
			OrgElementContext parent = element.getFdParent();
			while (parent != null) {
				level++;
				parent = parent.getFdParent();
			}
			sorts.add(new OrgLevelSort(element, level));
		}
		return sorts;
	}

	private void deleteCache(OrgElementContext[] elements) throws Exception {
		for (int i = 0; i < elements.length; i++) {
			context.deleteLocalOMSCache(elements[i].getFdId());
		}
	}

	private void deleteCache(List elementIds) throws Exception {
		for (int i = 0; i < elementIds.size(); i++) {
			context.deleteLocalOMSCache((String) elementIds.get(i));
		}
	}

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

}
