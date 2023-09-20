package com.landray.kmss.sys.oms.out.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.OrgLevelSort;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.StringUtil;

/**
 * OMS组织机构同步接出提供者抽象基类
 * 
 * @author 刘声斌 2010-04-14
 * 
 */
public abstract class AbstractOMSSynchroOutProvider implements
		IOMSSynchroOutProvider {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private SysQuartzJobContext jobContext;

	private IOMSSynchroOutContext context;

	private ISysOrgElementService sysOrgElementService = null;

	private ISysOrgCoreService sysOrgCoreService = null;

	public void setSysOrgElementService(
			ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

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
	 * 增加某个组织架构
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void addOrgElement(OrgElementContext element)
			throws Exception;

	/**
	 * 修改某个组织架构
	 * 
	 * @param element
	 * @throws Exception
	 */
	protected abstract void updateOrgElement(OrgElementContext element)
			throws Exception;

	/**
	 * 删除某个组织架构
	 * 
	 * @param dept
	 * @throws Exception
	 */
	protected abstract void deleteOrgElement(String keyWord) throws Exception;

	/**
	 * 同步執行方法
	 */
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

			List availableUpdateElements = getAvailableElements(context
					.getUpdateOrgElements());
			List addElements = context.getAddOrgElements();
			List deleteElements = context.getDeleteOrgElements();
			// 增加新的组织架构
			addOrgElements(addElements);
			// 更新组织架构
			updateOrgElements(availableUpdateElements);
			// 删除需要删除的组织架构
			deleteOrgElements(deleteElements);
			// 删除缓存
			deleteCache((OrgElementContext[]) addElements
					.toArray(new OrgElementContext[] {}));
			deleteCache((OrgElementContext[]) availableUpdateElements
					.toArray(new OrgElementContext[] {}));
			deleteCache(deleteElements);

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
	 * 获取有效的组织架构
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
	 * 新增传入的组织架构
	 * 
	 * @param elementList
	 * @throws Exception
	 */
	private void addOrgElements(List elementList) throws Exception {
		if(elementList!=null && !elementList.isEmpty()){
			elementList=getLevelSortById(elementList);
			Collections.sort(elementList);
		}
		for (Iterator iter = elementList.iterator(); iter.hasNext();) {
			OrgLevelSort sortObj = (OrgLevelSort) iter.next();
			addOrgElement((OrgElementContext)sortObj.getTarget());
		}
	}

	/**
	 * 更新传入的组织架构
	 * 
	 * @param elementList
	 * @throws Exception
	 */
	private void updateOrgElements(List elementList) throws Exception {
		for (Iterator iter = elementList.iterator(); iter.hasNext();) {
			OrgElementContext element = (OrgElementContext) iter.next();
			updateOrgElement(element);
		}
	}

	/**
	 * 删除组织架构元素
	 * 
	 * @param rtx
	 * @param elementIds
	 * @throws Exception
	 */
	private void deleteOrgElements(List elementIdsList) throws Exception {
		// 传入的List中的对象是一个拼装的关键字的字符串，例如删除了一个人员，
		// 字符串可能是fdNo:22;fdLoginName:zhangshan;fdId:1fsdfdsfsdfdsf;fdOrgType:SysOrgPerson
		for (Iterator iter = elementIdsList.iterator(); iter.hasNext();) {
			String delKeyWords = (String) iter.next();
			if (delKeyWords != null) {
				deleteOrgElement(delKeyWords);
				if (logger.isDebugEnabled()) {
					String orgType = getDelKeyWord(delKeyWords, "fdOrgType");
					String fdId = getDelKeyWord(delKeyWords, "fdId");
					logger.debug("同步删除：orgType->" + orgType);
					logger.debug("同步删除：fdId->" + fdId);
				}
			}
		}
	}

	/**
	 * 从删除关键字字符串中，返回查找key对应的值,用于OMS接出方法中
	 * 
	 * @param delKeywords
	 * @param findKey
	 * @return
	 */
	protected String getDelKeyWord(String delKeywords, String findKey) {
		if (StringUtil.isNull(delKeywords)) {
            return null;
        }
		if (StringUtil.isNull(findKey)) {
            return delKeywords;
        }
		String[] keyWords = delKeywords.split(";");
		for (int i = 0; i < keyWords.length; i++) {
			String key = keyWords[i].split(":")[0];
			if (findKey.equals(key)) {
				String value = keyWords[i].split(":")[1];
				return value;
			}
		}
		return delKeywords;
	}
	
	/**
	 * 删除缓存
	 * @param elements
	 * @throws Exception
	 */
	private void deleteCache(OrgElementContext[] elements) throws Exception {
		for (int i = 0; i < elements.length; i++) {
			context.deleteLocalOMSCache(elements[i].getFdId());
		}
	}
	
	/**
	 * 删除缓存
	 * @param elementIds
	 * @throws Exception
	 */
	private void deleteCache(List elementIds) throws Exception {
		for (int i = 0; i < elementIds.size(); i++) {
			context.deleteLocalOMSCache((String) elementIds.get(i));
		}
	}
	
	/**
	 * 重构一个可以排序的list对象
	 * @param elements
	 * @return
	 */
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

}
