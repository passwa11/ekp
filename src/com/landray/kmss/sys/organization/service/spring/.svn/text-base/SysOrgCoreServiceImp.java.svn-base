package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.spring.core.ContextLoadedListener;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.authentication.user.validate.Config;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.CacheLoader;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.notify.util.MimeUtility;
import com.landray.kmss.sys.oms.in.interfaces.IOMSSynchroInProvider;
import com.landray.kmss.sys.organization.dao.ISysOrgDeptDao;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.dao.ISysOrgOrgDao;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.dao.ISysOrgPostDao;
import com.landray.kmss.sys.organization.dao.ISysOrgRoleDao;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.PersonCommunicationInfo;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgElementExternal;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.plugin.ISysOrgRolePlugin;
import com.landray.kmss.sys.organization.plugin.SysOrgRolePluginContext;
import com.landray.kmss.sys.organization.service.ISysOrgElementExternalService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.SysOrgEcoUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author 叶中奇
 * @version 1.0 2006-4-10
 */
@SuppressWarnings("unchecked")
public class SysOrgCoreServiceImp implements ISysOrgCoreService,
		SysAuthConstant, BaseTreeConstant, ApplicationContextAware, ContextLoadedListener {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgCoreServiceImp.class);

	private ApplicationContext applicationContext;

	private ISysOrgDeptDao deptDao;

	private ISysOrgElementDao elementDao;

	private ISysOrgGroupDao groupDao;

	private ISysOrgOrgDao orgDao;

	private ISysOrgPersonDao personDao;

	private ISysOrgPostDao postDao;

	private ISysOrgRoleDao roleDao;

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;
	
	private ISysOrgElementExternalService sysOrgElementExternalService;

	public void setSysOrgElementExternalService(ISysOrgElementExternalService sysOrgElementExternalService) {
		this.sysOrgElementExternalService = sysOrgElementExternalService;
	}

	private void addQueryResultToList(Session session, String sql, List list) {
		ArrayUtil.concatTwoList(session.createNativeQuery(sql).list(), list);
	}

	private void addQueryResultToList(Session session, String sql, Set list) {
		List _list = new ArrayList(list);
		ArrayUtil.concatTwoList(session.createNativeQuery(sql).list(), _list);
		list.clear();
		list.addAll(_list);
	}

	@Override
	public SysOrgPerson getAnonymousPerson() throws Exception {
		SysOrgPerson person = (SysOrgPerson) personDao.findFirstOne("sysOrgPerson.fdLoginName='anonymous'", null);
		if (person == null) {
			person = new SysOrgPerson();
			person.setFdIsBusiness(new Boolean(false));
			person.setFdLoginName("anonymous");
			person.setFdName("匿名用户");
			person.setFdPassword("0");
			person.setFdCanLogin(Boolean.FALSE);
			personDao.add(person);
		} else {
			if (person.getFdIsAvailable().equals(false)) {
				person.setFdIsAvailable(true);
				person.setFdCanLogin(Boolean.FALSE);
				personDao.update(person);
			}
		}
		person.getFdPosts();
		return person;
	}

	@Override
	public SysOrgPerson getEveryonePerson() throws Exception {
		SysOrgPerson everyone = (SysOrgPerson) personDao.findByPrimaryKey(
				SysOrgConstant.ORG_PERSON_EVERYONE_ID, null, true);
		if (everyone == null) {
			everyone = new SysOrgPerson();
			everyone.setFdId(SysOrgConstant.ORG_PERSON_EVERYONE_ID);
			everyone.setFdName(SysOrgConstant.ORG_PERSON_EVERYONE_LOGIN_NAME);
			everyone.setFdLoginName(SysOrgConstant.ORG_PERSON_EVERYONE_LOGIN_NAME);
			everyone.setFdPassword("0");
			everyone.setFdIsBusiness(new Boolean(false));
			everyone.setFdIsAvailable(new Boolean(true));
			everyone.setFdCanLogin(Boolean.FALSE);
			personDao.add(everyone);
		}
		org.hibernate.Hibernate.initialize(everyone.getFdPosts());
		return everyone;
	}

	private final static BeanCopier PERSON_COPIER = BeanCopier.create(
			SysOrgPerson.class,SysOrgPerson.class,false);
	private volatile SysOrgPerson anonymousPersonPrototype = null;
	@Override
	public SysOrgPerson getAnonymousPersonStateless() throws Exception{
		if(anonymousPersonPrototype==null){
			synchronized (this){
				if(anonymousPersonPrototype==null){
					SysOrgPerson _anonymousPerson = getAnonymousPerson();
					SysOrgPerson _anonymousPersonPrototype = new SysOrgPerson();
					PERSON_COPIER.copy(_anonymousPerson,_anonymousPersonPrototype,null);
					anonymousPersonPrototype = _anonymousPersonPrototype;
				}
			}
		}
		//为了防止返回值被外界修改，还要再copy一次
		SysOrgPerson stateless = new SysOrgPerson();
		PERSON_COPIER.copy(anonymousPersonPrototype,stateless,null);
		return stateless;
	}

	private volatile SysOrgPerson everyonePersonPrototype = null;
	@Override
	public SysOrgPerson getEveryonePersonStateless() throws Exception{
		if(everyonePersonPrototype==null){
			synchronized (this){
				if(everyonePersonPrototype==null){
					SysOrgPerson _everyonePerson = getEveryonePerson();
					SysOrgPerson _everyonePersonPrototype = new SysOrgPerson();
					PERSON_COPIER.copy(_everyonePerson,_everyonePersonPrototype,null);
					everyonePersonPrototype = _everyonePersonPrototype;
				}
			}
		}
		//为了防止返回值被外界修改，还要再copy一次
		SysOrgPerson stateless = new SysOrgPerson();
		PERSON_COPIER.copy(everyonePersonPrototype,stateless,null);
		return stateless;
	}

	@Override
	public List expandToPerson(List orgList) throws Exception {
		List rtnList = new ArrayList();
		List personIds = expandToPersonIds(orgList);

		if (!personIds.isEmpty()) {
			int beginIndex = 0, endIndex = 0;

			while (endIndex < personIds.size()) {
				endIndex = beginIndex + 2000;
				if (endIndex > personIds.size()) {
					endIndex = personIds.size();
				}

				List subList = personIds.subList(beginIndex, endIndex);
				HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fdId",
						"sysOrgPerson" + "0_", subList);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(hqlWrapper.getHql());
				hqlInfo.setParameter(hqlWrapper.getParameterList());
				List results = personDao.findList(hqlInfo);

				if (!ArrayUtil.isEmpty(results)) {
					rtnList.addAll(results);
				}

				beginIndex += 2000;
			}
		}

		return rtnList;
	}

	@Override
	public List expandToPersonIds(List orgList) throws Exception {
		if (orgList == null || orgList.isEmpty()) {
            return new ArrayList();
        }
		Session session = postDao.getHibernateSession();
		Set hierarchyIds = new HashSet(128);
		Set groupIds = new HashSet(128);
		Set postIds = new HashSet(128);
		Set personIds = new HashSet(1024);
		List results;
		String sql, whereBlock;

		for (int i = 0; i < orgList.size(); i++) {
			Object tmpOrg = orgList.get(i);
			SysOrgElement element = null;
			if (tmpOrg instanceof String) {
				element = findByPrimaryKey((String) tmpOrg);
			} else {
				element = (SysOrgElement) orgList.get(i);
			}
			if (element != null) {
				switch (element.getFdOrgType().intValue()) {
				case ORG_TYPE_ORG:
				case ORG_TYPE_DEPT:
					hierarchyIds.add(element.getFdHierarchyId());
					break;
				case ORG_TYPE_POST:
					postIds.add(element.getFdId());
					break;
				case ORG_TYPE_PERSON:
					personIds.add(element.getFdId());
					break;
				case ORG_TYPE_GROUP:
					groupIds.add(element.getFdId());
					break;
				}
			}
		}
		// 解释群组
		if (!groupIds.isEmpty()) {
			groupIds = new HashSet(groupDao.fetchChildGroupIds(groupIds));
			whereBlock = HQLUtil.buildLogicIN("fd_groupid", new ArrayList<>(groupIds));
			sql = "select fd_elementid,fd_org_type,fd_hierarchy_id from sys_org_group_element "
					+ "left join sys_org_element on fd_elementid=fd_id where fd_org_type<"
					+ ORG_TYPE_GROUP + " and " + whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("群组解释：" + sql);
            }
			results = session.createNativeQuery(sql).list();
			for (int i = 0; i < results.size(); i++) {
				Object[] objArr = (Object[]) results.get(i);
				int intValue;
				// oracle11G会将objArr[1]改为java.math.BigDecimal
				if (objArr[1] instanceof java.math.BigDecimal) {
					intValue = ((java.math.BigDecimal) objArr[1]).intValue();
				} else {
					intValue = ((Number) objArr[1]).intValue();
				}
				switch (intValue) {
				case ORG_TYPE_ORG:
				case ORG_TYPE_DEPT:
					hierarchyIds.add(objArr[2]);
					break;
				case ORG_TYPE_POST:
					postIds.add(objArr[0]);
					break;
				case ORG_TYPE_PERSON:
					personIds.add(objArr[0]);
				}
			}
		}
		// 解释部门
		if (!hierarchyIds.isEmpty()) {
			hierarchyIds = new HashSet(SysOrgHQLUtil.formatHierarchyIdList(new ArrayList(hierarchyIds)));
			StringBuffer whereBf = new StringBuffer();
			Iterator iterator = hierarchyIds.iterator();
			while (iterator.hasNext()) {
				String hierarchyId = (String) iterator.next();
				String deptIds[] = hierarchyId.split("x");
                whereBf.append(" or (fd_hierarchy_id=0 and fd_pre_dept_id like '").append(
                		deptIds[deptIds.length-1]).append("%') or fd_hierarchy_id like '").append(
								hierarchyId).append("%' and fd_is_available = "+SysOrgHQLUtil.toBooleanValueString(true)+" "); // 加上fd_is_available=1条件，以兼容有些数据迁移同步过程中层级id没置空的情况
            }
			whereBlock = "(" + whereBf.substring(4) + ")";
			sql = "select fd_id from sys_org_element where fd_org_type="
					+ ORG_TYPE_PERSON + " and " + whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("部门解释个人：" + sql);
            }
			addQueryResultToList(session, sql, personIds);
			sql = "select fd_personid from sys_org_post_person left join sys_org_element on fd_id=fd_postid where "
					+ whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("部门－岗位解释个人：" + sql);
            }
			addQueryResultToList(session, sql, personIds);
		}
		// 解释岗位
		if (!postIds.isEmpty()) {
			whereBlock = HQLUtil.buildLogicIN("fd_postid", new ArrayList<>(postIds));
			sql = "select fd_personid from sys_org_post_person where "
					+ whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("岗位解释：" + sql);
            }
			addQueryResultToList(session, sql, personIds);
		}
		return new ArrayList(personIds);
	}

	@Override
	public List expandToPostPersonIds(List orgList) throws Exception {
		if (orgList == null || orgList.isEmpty()) {
            return new ArrayList();
        }
		Session session = postDao.getHibernateSession();
		Set hierarchyIds = new HashSet(128);
		Set groupIds = new HashSet(128);
		Set postIds = new HashSet(128);
		Set personIds = new HashSet(1024);
		List results;
		String sql, whereBlock;

		for (int i = 0; i < orgList.size(); i++) {
			Object tmpOrg = orgList.get(i);
			SysOrgElement element = null;
			if (tmpOrg instanceof String) {
				element = findByPrimaryKey((String) tmpOrg);
			} else {
				element = (SysOrgElement) orgList.get(i);
			}
			if (element != null) {
				switch (element.getFdOrgType().intValue()) {
				case ORG_TYPE_ORG:
				case ORG_TYPE_DEPT:
					hierarchyIds.add(element.getFdHierarchyId());
					break;
				case ORG_TYPE_POST:
					postIds.add(element.getFdId());
					break;
				case ORG_TYPE_PERSON:
					personIds.add(element.getFdId());
					break;
				case ORG_TYPE_GROUP:
					groupIds.add(element.getFdId());
					break;
				}
			}
		}
		// 解释群组
		if (!groupIds.isEmpty()) {
			groupIds = new HashSet(groupDao.fetchChildGroupIds(groupIds));
			whereBlock = HQLUtil.buildLogicIN("fd_groupid", new ArrayList<>(groupIds));
			sql = "select fd_elementid,fd_org_type,fd_hierarchy_id from sys_org_group_element "
					+ "left join sys_org_element on fd_elementid=fd_id where fd_org_type<"
					+ ORG_TYPE_GROUP + " and " + whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("群组解释：" + sql);
            }
			results = session.createNativeQuery(sql).list();
			for (int i = 0; i < results.size(); i++) {
				Object[] objArr = (Object[]) results.get(i);
				int intValue;
				// oracle11G会将objArr[1]改为java.math.BigDecimal
				if (objArr[1] instanceof java.math.BigDecimal) {
					intValue = ((java.math.BigDecimal) objArr[1]).intValue();
				} else {
					intValue = ((Number) objArr[1]).intValue();
				}
				switch (intValue) {
				case ORG_TYPE_ORG:
				case ORG_TYPE_DEPT:
					hierarchyIds.add(objArr[2]);
					break;
				case ORG_TYPE_POST:
					postIds.add(objArr[0]);
					break;
				case ORG_TYPE_PERSON:
					personIds.add(objArr[0]);
				}
			}
		}
		// 解释部门
		if (!hierarchyIds.isEmpty()) {
			hierarchyIds = new HashSet(SysOrgHQLUtil.formatHierarchyIdList(new ArrayList(hierarchyIds)));
			StringBuffer whereBf = new StringBuffer();
			Iterator iterator = hierarchyIds.iterator();
			while (iterator.hasNext()) {
                whereBf.append(" or fd_hierarchy_id like '").append(
						iterator.next()).append("%' and fd_is_available = "+SysOrgHQLUtil.toBooleanValueString(true)+" "); // 加上fd_is_available=1条件，以兼容有些数据迁移同步过程中层级id没置空的情况
            }
			whereBlock = "(" + whereBf.substring(4) + ")";
			sql = "select fd_id from sys_org_element where fd_org_type="
					+ ORG_TYPE_PERSON + " and " + whereBlock;
			if (logger.isDebugEnabled()) {
                logger.debug("部门解释个人：" + sql);
            }
			addQueryResultToList(session, sql, personIds);
			sql = "select fd_postid from sys_org_post_person left join sys_org_element on fd_id=fd_postid where "
					+ whereBlock;
			addQueryResultToList(session, sql, postIds);
		}
		personIds.addAll(postIds);
		return new ArrayList(personIds);
	}

	@Override
	public List expandToPostPerson(List orgList) throws Exception {
		List rtnList = new ArrayList();
		List personIds = expandToPostPersonIds(orgList);

		if (!personIds.isEmpty()) {
			int beginIndex = 0, endIndex = 0;

			while (endIndex < personIds.size()) {
				endIndex = beginIndex + 2000;
				if (endIndex > personIds.size()) {
					endIndex = personIds.size();
				}

				List subList = personIds.subList(beginIndex, endIndex);
				HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fdId",
						"sysOrgElement" + "0_", subList);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(hqlWrapper.getHql());
				hqlInfo.setParameter(hqlWrapper.getParameterList());
				List results = elementDao.findList(hqlInfo);

				if (!ArrayUtil.isEmpty(results)) {
					rtnList.addAll(results);
				}

				beginIndex += 2000;
			}
		}

		return rtnList;
	}

	@Override
	public List findAllChildren(SysOrgElement element, int rtnType)
			throws Exception {
		ISysOrgElementDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		return dao.findList(SysOrgHQLUtil.buildWhereBlock(rtnType,
				SysOrgHQLUtil.buildAllChildrenWhereBlock(element, null,
						tableName), tableName), null);
	}

	@Override
	public List findAllChildren(SysOrgElement element, int rtnType,
			String whereBlock)
			throws Exception {
		ISysOrgElementDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		return dao.findList(SysOrgHQLUtil.buildWhereBlock(rtnType,
				SysOrgHQLUtil.buildAllChildrenWhereBlock(element, whereBlock,
						tableName),
				tableName), null);
	}

	@Override
	public List findAllChildrenItem(SysOrgElement element, int rtnType,
			String rtnItem) throws Exception {
		ISysOrgElementDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		if (!"sysOrgElement".equals(tableName)) {
            rtnItem = rtnItem.replaceAll("sysOrgElement", tableName);
        }
		return dao.findValue(rtnItem, SysOrgHQLUtil.buildWhereBlock(rtnType,
				SysOrgHQLUtil.buildAllChildrenWhereBlock(element, null,
						tableName), tableName), null);
	}

	@Override
	public SysOrgElement findByKeyword(String keyword) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("sysOrgElement.fdKeyword=:fdKeyword and sysOrgElement.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdKeyword", keyword);
		hqlInfo.setParameter("fdIsAvailable", true);
		List rtnList = elementDao.findList(hqlInfo);
		if (rtnList.isEmpty()) {
            return null;
        } else {
            return (SysOrgElement) rtnList.get(0);
        }
	}

	@Override
	public SysOrgElement findByImportInfo(String importInfo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgElement.fdImportInfo=:fdImportInfo");
		hqlInfo.setParameter("fdImportInfo", importInfo);
		return (SysOrgElement) elementDao.findFirstOne(hqlInfo);
	}

	@Override
	public SysOrgElement findByImportInfo(String serviceName, String keyword) {
		try {
			IOMSSynchroInProvider provider = (IOMSSynchroInProvider) applicationContext
					.getBean(serviceName);
			String importInfo = provider.getKey()
					+ provider.getLinkString(null) + keyword;
			return findByImportInfo(importInfo);
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public SysOrgElement findByImportInfo(String serviceName, String keyword,
			String type) throws Exception {
		try {
			IOMSSynchroInProvider provider = (IOMSSynchroInProvider) applicationContext
					.getBean(serviceName);
			String importInfo = provider.getKey()
					+ provider.getLinkString(type) + keyword;
			return findByImportInfo(importInfo);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * huangwq 2012-10-23 根据importInfo和组织类型查数据
	 * 
	 * @param importInfo
	 * @param orgType
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysOrgElement findByImportInfoAndOrgtype(String importInfo,
			int orgType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock("sysOrgElement.fdImportInfo=:fdImportInfo and sysOrgElement.fdOrgType=:fdOrgType");
		hqlInfo.setParameter("fdImportInfo", importInfo);
		hqlInfo.setParameter("fdOrgType", orgType);
		return (SysOrgElement) elementDao.findFirstOne(hqlInfo);
	}

	@Override
	public SysOrgPerson findByLoginName(String loginName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		loginName = loginName.trim();
		SysOrganizationConfig config = new SysOrganizationConfig();
		if (StringUtil.isNull(config.getLoginNameCase()) || "1".equals(config.getLoginNameCase())) {
			// 保持原样
			hqlInfo.setWhereBlock("lower(sysOrgPerson.fdLoginName)=:fdLoginName and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", loginName.toLowerCase());
		} else if ("2".equals(config.getLoginNameCase())) {
			// 区分大小写
			hqlInfo.setWhereBlock("sysOrgPerson.fdLoginName=:fdLoginName and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", loginName);
		} else if ("3".equals(config.getLoginNameCase())) {
			// 忽略大小写
			hqlInfo.setWhereBlock("sysOrgPerson.fdLoginNameLower=:fdLoginName and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", loginName.toLowerCase());
		}
		hqlInfo.setParameter("fdIsAvailable", true);
		// 开启三员时，需要判断是否已激活
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and sysOrgPerson.fdIsActivated = :fdIsActivated");
			hqlInfo.setParameter("fdIsActivated", true);
		}
		// 关闭生态组织时，只查询内部用户
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and (sysOrgPerson.fdIsExternal is null or sysOrgPerson.fdIsExternal = false)");
		}
		return (SysOrgPerson) personDao.findFirstOne(hqlInfo);
	}
	
	@Override
	public SysOrgPerson findByLoginNameOrMobileNo(String name) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		name = name.trim();
		SysOrganizationConfig config = new SysOrganizationConfig();
		if (StringUtil.isNull(config.getLoginNameCase()) || "1".equals(config.getLoginNameCase())) {
			// 保持原样
			hqlInfo.setWhereBlock("(lower(sysOrgPerson.fdLoginName)=:fdLoginName or sysOrgPerson.fdMobileNo=:fdMobileNo) and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", name.toLowerCase());
		} else if ("2".equals(config.getLoginNameCase())) {
			// 区分大小写
			hqlInfo.setWhereBlock("(sysOrgPerson.fdLoginName=:fdLoginName or sysOrgPerson.fdMobileNo=:fdMobileNo) and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", name);
		} else if ("3".equals(config.getLoginNameCase())) {
			// 忽略大小写
			hqlInfo.setWhereBlock("(sysOrgPerson.fdLoginNameLower=:fdLoginName or sysOrgPerson.fdMobileNo=:fdMobileNo) and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
			hqlInfo.setParameter("fdLoginName", name.toLowerCase());
		}
		hqlInfo.setParameter("fdMobileNo", name);
		hqlInfo.setParameter("fdIsAvailable", true);
		// 开启三员时，需要判断是否已激活
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and sysOrgPerson.fdIsActivated = :fdIsActivated");
			hqlInfo.setParameter("fdIsActivated", true);
		}
		// 关闭生态组织时，只查询内部用户
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and (sysOrgPerson.fdIsExternal is null or sysOrgPerson.fdIsExternal = false)");
		}
		return (SysOrgPerson) personDao.findFirstOne(hqlInfo);
	}

	@Override
	public SysOrgElement findByNo(String fdNo, int rtnType) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(rtnType,
				"sysOrgElement.fdNo=:fdNo", "sysOrgElement"));
		hqlInfo.setParameter("fdNo", fdNo);
		return (SysOrgElement) elementDao.findFirstOne(hqlInfo);
	}
	
	@Override
	public List findByName(String fdName, int rtnType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(rtnType,
				"sysOrgElement.fdName=:fdName", "sysOrgElement"));
		hqlInfo.setParameter("fdName", fdName);
		return elementDao.findList(hqlInfo);
	}

	@Override
	public SysOrgElement findByLdapDN(String dn) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereStr = "sysOrgElement.fdLdapDN=:fdLdapDN";
		hqlInfo.setParameter("fdLdapDN", dn);
		hqlInfo.setWhereBlock(whereStr);

		List<SysOrgElement> modelList = elementDao.findValue(hqlInfo);

		if (ArrayUtil.isEmpty(modelList)) {
			return null;
		}

		return modelList.get(0);
	}

	@Override
	public SysOrgElement findByPrimaryKey(String id) throws Exception {
		return findByPrimaryKey(id, null, false);
	}

	@Override
	public SysOrgElement findByPrimaryKey(String id, Class classObj)
			throws Exception {
		return findByPrimaryKey(id, classObj, false);
	}

	@Override
	public SysOrgElement findByPrimaryKey(String id, Class classObj,
			boolean noLazy) throws Exception {
		return (SysOrgElement) elementDao
				.findByPrimaryKey(id, classObj, noLazy);
	}

	@Override
	public List findByPrimaryKeys(String[] ids) throws Exception {
		return elementDao.findByPrimaryKeys(ids);
	}

	@Override
	public List findDirectChildren(String deptId, int rtnType) throws Exception {
		ISysOrgElementDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = null;
		if (deptId != null) {
			whereBlock = tableName + ".hbmParent.fdId=:deptId";
			hqlInfo.setParameter("deptId", deptId);
		} else {
			whereBlock = tableName + ".hbmParent=null";
		}
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(rtnType,
				whereBlock, tableName));
		return dao.findList(hqlInfo);
	}

	@Override
	public List findDirectChildrenItem(String deptId, int rtnType,
			String rtnItem) throws Exception {
		ISysOrgElementDao dao = getOptimalDao(rtnType);
		String tableName = ModelUtil.getModelTableName(dao.getModelName());
		if (!"sysOrgElement".equals(tableName)) {
            rtnItem = rtnItem.replaceAll("sysOrgElement", tableName);
        }
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(rtnItem);
		String whereBlock = null;
		if (deptId != null) {
			whereBlock = tableName + ".hbmParent.fdId=:deptId";
			hqlInfo.setParameter("deptId", deptId);
		} else {
			whereBlock = tableName + ".hbmParent=null";
		}
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(rtnType,
				whereBlock, tableName));
		return dao.findValue(hqlInfo);
	}

	@Override
	public SysOrgElement format(SysOrgElement element) throws Exception {
		return elementDao.format(element);
	}

	@Override
	public int getAllChildrenCount(SysOrgElement element, int rtnType)
			throws Exception {
		return elementDao.getAllChildrenCount(element, rtnType);
	}

	@Override
	public int getAllCount(int rtnType) throws Exception {
		return elementDao.getAllCount(rtnType);
	}

	@Override
	public int getAllCount(int rtnType ,String whereBlock) throws Exception {
		return elementDao.getAllCount(rtnType,whereBlock);
	}

	private ISysOrgElementDao getOptimalDao(int rtnType) {
		switch (rtnType & ORG_TYPE_ALL) {
		case ORG_TYPE_ORG:
			return orgDao;
		case ORG_TYPE_DEPT:
			return deptDao;
		case ORG_TYPE_POST:
			return postDao;
		case ORG_TYPE_PERSON:
			return personDao;
		case ORG_TYPE_GROUP:
			return groupDao;
		case ORG_TYPE_ROLE:
			return roleDao;
		default:
			return elementDao;
		}
	}

	@Override
	public UserAuthInfo getOrgsUserAuthInfo(SysOrgElement person)
			throws Exception {
		Session session = elementDao.getHibernateSession();
		UserAuthInfo rtnInfo = new UserAuthInfo();
		List hierarchyIds = new ArrayList(32);
		hierarchyIds.add(person.getFdHierarchyId());
		List ids = new ArrayList(32);
		ids.add(person.getFdId());
		// 查找岗位
		String sql = "select fd_hierarchy_id, fd_id from sys_org_element "
				+ "left join sys_org_post_person on fd_id=fd_postid where fd_personid=:fdPersonId";
		List results = session.createNativeQuery(sql).setParameter("fdPersonId",
				person.getFdId()).list();
		for (int i = 0; i < results.size(); i++) {
			Object[] result = (Object[]) results.get(i);
			hierarchyIds.add(result[0]);
			ids.add(result[1]);
		}

		// 查找直级部门和机构
		HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_id",
				"sysOrgElement" + "0_", new ArrayList<>(ids));
		sql = "select fd_parentid, fd_parentorgid from sys_org_element where "
				+ hqlWrapper.getHql();
		results = HQLUtil.setParameters(session.createNativeQuery(sql),
				hqlWrapper.getParameterList()).list();
		List parentIds = new ArrayList(16);
		List parentorgIds = new ArrayList(16);
		for (int i = 0; i < results.size(); i++) {
			Object[] idArr = (Object[]) results.get(i);
			if(!parentIds.contains(idArr[0])){
				parentIds.add(idArr[0]);
			}
			if(!parentorgIds.contains(idArr[1])){
				parentorgIds.add(idArr[1]);
			}
		}
		rtnInfo.setDirectParentIds(parentIds);
		rtnInfo.setDirectParentOrgIds(parentorgIds);

		// 查找所有父部门
		for (int i = 0; i < hierarchyIds.size(); i++) {
			String[] parentIdArr = hierarchyIds.get(i).toString().split(
					HIERARCHY_ID_SPLIT);
			for (int j = 1; j < parentIdArr.length - 1; j++) {
				String id = parentIdArr[j];
				ids.add(id);
			}
		}
		// 查找所有群组
		hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_elementid",
				"sysOrgGroupElement" + "0_", ids);
		sql = "select fd_groupid from sys_org_group_element where "
				+ hqlWrapper.getHql();
		results = HQLUtil.setParameters(session.createNativeQuery(sql),
				hqlWrapper.getParameterList()).list();
		if (!results.isEmpty()) {
			results = groupDao.fetchParentGroupIds(results);
			ids.addAll(results);
		}
		rtnInfo.setAuthOrgIds(ids);
		checkPrivilege(rtnInfo, person.getFdId());
		return rtnInfo;
	}
	
	@Override
	public UserAuthInfo getOrgsUserAuthInfo(SysOrgElement person,
			boolean isContainsPost) throws Exception {
		if (isContainsPost) {
			return getOrgsUserAuthInfo(person);
		} else {
			Session session = elementDao.getHibernateSession();
			UserAuthInfo rtnInfo = new UserAuthInfo();
			List hierarchyIds = new ArrayList();
			hierarchyIds.add(person.getFdHierarchyId());
			List ids = new ArrayList(32);
			ids.add(person.getFdId());
			// 查找直级部门和机构
			HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_id",
					"sysOrgElement" + "0_", new ArrayList<>(ids));
			String sql = "select fd_parentid, fd_parentorgid from sys_org_element where "
					+ hqlWrapper.getHql();
			List results = HQLUtil.setParameters(session.createNativeQuery(sql),
					hqlWrapper.getParameterList()).list();
			List parentIds = new ArrayList(16);
			List parentorgIds = new ArrayList(16);
			for (int i = 0; i < results.size(); i++) {
				Object[] idArr = (Object[]) results.get(i);
				parentIds.add(idArr[0]);
				parentorgIds.add(idArr[1]);
			}
			rtnInfo.setDirectParentIds(parentIds);
			rtnInfo.setDirectParentOrgIds(parentorgIds);

			// 查找所有父部门
			for (int i = 0; i < hierarchyIds.size(); i++) {
				String[] parentIdArr = hierarchyIds.get(i).toString().split(
						HIERARCHY_ID_SPLIT);
				for (int j = 1; j < parentIdArr.length - 1; j++) {
					String id = parentIdArr[j];
					ids.add(id);
				}
			}

			// 查找所有群组
			hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_elementid",
					"sysOrgGroupElement" + "0_", ids);
			sql = "select fd_groupid from sys_org_group_element where "
					+ hqlWrapper.getHql();
			results = HQLUtil.setParameters(session.createNativeQuery(sql),
					hqlWrapper.getParameterList()).list();
			if (!results.isEmpty()) {
				results = groupDao.fetchParentGroupIds(results);
				ids.addAll(results);
			}

			// 添加岗位
			sql = "select fd_hierarchy_id, fd_id from sys_org_element "
					+ "left join sys_org_post_person on fd_id=fd_postid where fd_personid=:fdPersonId";
			results = session.createNativeQuery(sql).setParameter("fdPersonId",
					person.getFdId()).list();
			for (int i = 0; i < results.size(); i++) {
				Object[] result = (Object[]) results.get(i);
				ids.add(result[1]);
			}
			rtnInfo.setAuthOrgIds(ids);
			checkPrivilege(rtnInfo, person.getFdId());
			return rtnInfo;
		}
	}

	private void checkPrivilege(UserAuthInfo rtnInfo, String userId) {
		KmssCache cache = new KmssCache(SysOrgCoreServiceImp.class);
		List<String> personIds = (List<String>) cache.get("sys_org_person_privilege_ids");
		if (personIds == null) {
			personIds = findPrivileges();
		}
		rtnInfo.setPrivilege(personIds.contains(userId));
	}

	/**
	 * 获取有效的特权用户ID
	 * @return
	 */
	private List<String> findPrivileges() {
		if (logger.isDebugEnabled()) {
			logger.debug("实时查询有效的特权用户ID");
		}
		String sql = "select fd_person_id from sys_org_person_privilege order by fd_id";
		return elementDao.getHibernateSession().createNativeQuery(sql).setMaxResults(Config.getLicPrivCount()).list();
	}

	@Override
	public List getPostAuthOrgIds(SysOrgElement post) throws Exception {
		Session session = elementDao.getHibernateSession();
		List hierarchyIds = new ArrayList();
		hierarchyIds.add(post.getFdHierarchyId());
		Set ids = new HashSet(1024);
		ids.add(post.getFdId());

		// 查找直级部门和机构
		HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_id",
				"sysOrgElement" + "0_", new ArrayList<>(ids));
		String sql = "select fd_parentid, fd_parentorgid from sys_org_element where "
				+ hqlWrapper.getHql();
		List results = HQLUtil.setParameters(session.createNativeQuery(sql),
				hqlWrapper.getParameterList()).list();
		Set parentIds = new HashSet(128);
		Set parentorgIds = new HashSet(128);
		for (int i = 0; i < results.size(); i++) {
			Object[] idArr = (Object[]) results.get(i);
			parentIds.add(idArr[0]);
			parentorgIds.add(idArr[1]);
		}

		// 查找所有父部门
		for (int i = 0; i < hierarchyIds.size(); i++) {
			String[] parentIdArr = hierarchyIds.get(i).toString().split(
					HIERARCHY_ID_SPLIT);
			for (int j = 1; j < parentIdArr.length - 1; j++) {
				String id = parentIdArr[j];
				ids.add(id);
			}
		}
		// 查找所有群组
		hqlWrapper = HQLUtil.buildPreparedLogicIN("fd_elementid",
				"sysOrgGroupElement" + "0_", new ArrayList<>(ids));
		sql = "select fd_groupid from sys_org_group_element where "
				+ hqlWrapper.getHql();
		results = HQLUtil.setParameters(session.createNativeQuery(sql),
				hqlWrapper.getParameterList()).list();
		if (!results.isEmpty()) {
			results = groupDao.fetchParentGroupIds(results);
			ids.addAll(results);
		}
		return new ArrayList(ids);
	}

	@Override
	public PersonCommunicationInfo getPersonCommunicationInfo(List personList)
			throws Exception {
		List rtnList = new ArrayList();
		List ids = new ArrayList();
		for (int i = 0; i < personList.size(); i++) {
			SysOrgPerson sysOrgPerson = (SysOrgPerson) personList.get(i);
			if (!org.hibernate.Hibernate.isInitialized(sysOrgPerson)) {
				ids.add(sysOrgPerson.getFdId());
			} else {
				if (sysOrgPerson.getFdIsAvailable()) {// update by wubing date
					// 2015-05-12,只对有效的个人发送邮件和短信
					rtnList.add(new Object[] { sysOrgPerson.getFdEmail(),
							sysOrgPerson.getFdMobileNo(),
							sysOrgPerson.getFdDefaultLang(),
							sysOrgPerson.getFdName(),
							sysOrgPerson.getFdOrder() });
				}
			}
		}
		if (ids.size() > 0) {
			int beginIndex = 0, endIndex = 0;
			while (endIndex < ids.size()) {
				endIndex = beginIndex + 2000;
				if (endIndex > ids.size()) {
					endIndex = ids.size();
				}
				List subList = ids.subList(beginIndex, endIndex);
				HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("e.fd_id",
						"sysOrgElement_0_" + beginIndex, subList);
				// String sql =
				// "select fd_email, fd_mobile_no,fd_default_lang from sys_org_person where "
				// + hqlWrapper.getHql();
				// update by wubing date 2015-05-12,只对有效的个人发送邮件和短信
				String sql = "select p.fd_email,p.fd_mobile_no,p.fd_default_lang,e.fd_name,e.fd_order from sys_org_person p,sys_org_element e where "
						+ hqlWrapper.getHql();
				sql += " and p.fd_id=e.fd_id and fd_is_available = "+SysOrgHQLUtil.toBooleanValueString(true);

				if (logger.isDebugEnabled()) {
                    logger.debug("解释个人通讯方式：" + sql);
                }

				List results = HQLUtil.setParameters(
						personDao.getHibernateSession().createNativeQuery(sql),
						hqlWrapper.getParameterList()).list();
				if (!ArrayUtil.isEmpty(results)) {
					rtnList.addAll(results);
				}
				beginIndex += 2000;
			}
		}
		final String mailOrder = ResourceUtil
				.getKmssConfigString("sys.notify.mail.order");
		if ("asc".equals(mailOrder) || "desc".equals(mailOrder)) {
			try {
				Collections.sort(rtnList, new Comparator<Object[]>() {
					@Override
					public int compare(Object[] o1, Object[] o2) {
						if (o1[4] == null && o2[4] == null) {
							return 0;
						} else if (o1[4] == null) {
							return 1;
						} else if (o2[4] == null) {
							return -1;
						} else {
							Integer i1 = (Integer) o1[4];
							Integer i2 = (Integer) o2[4];
							if ("asc".equals(mailOrder)) {
								return i1.intValue() - i2.intValue();
							} else {
								return i2.intValue() - i1.intValue();
							}
						}
					}
				});
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}

		PersonCommunicationInfo info = new PersonCommunicationInfo();

		Map<Object, List> emails = new HashMap<Object, List>();
		Map<Object, List> mobileNums = new HashMap<Object, List>();
		// 默认语言
		List defaultLangs = new ArrayList();
		// 官方语言
		String official = ResourceUtil
				.getKmssConfigString("kmss.lang.official");
		if(StringUtil.isNotNull(official) && official.contains("|")) {
			String[] off = official.split("\\|");
			official = off[1];
		}
		
		for (int i = 0; i < rtnList.size(); i++) {
			Object[] values = (Object[]) rtnList.get(i);
			if (StringUtil.isNotNull((String) values[2])) {
				if (defaultLangs.contains(values[2])) {
					if (values[0] != null
							&& !"".equals(((String) values[0]).trim())
							&& !emails.get(values[2]).contains(values[0])) {
						emails.get(values[2]).add(
								MimeUtility.encodeText(
										(String) values[3],
										"UTF-8", "B")
										+ "<" + values[0] + ">");
					}
					if (values[1] != null
							&& !"".equals(((String) values[1]).trim())
							&& !mobileNums.get(values[2]).contains(values[1])) {
						mobileNums.get(values[2]).add(values[1]);
					}
				} else {
					defaultLangs.add(values[2]);
					List tempEmails = new ArrayList();
					List tempMobileNums = new ArrayList();
					if (values[0] != null
							&& !"".equals(((String) values[0]).trim())) {
						tempEmails.add(MimeUtility
								.encodeText(
										(String) values[3],
										"UTF-8", "B")
								+ "<" + values[0] + ">");
					}
					if (values[1] != null
							&& !"".equals(((String) values[1]).trim())) {
						tempMobileNums.add(values[1]);
					}
					emails.put(values[2], tempEmails);
					mobileNums.put(values[2], tempMobileNums);
				}

			} else {
				if (defaultLangs.contains(official)) {
					if (values[0] != null
							&& !"".equals(((String) values[0]).trim())
							&& !emails.get(official).contains(values[0])) {
						emails.get(official).add(
								MimeUtility.encodeText(
										(String) values[3],
										"UTF-8", "B")
										+ "<" + values[0] + ">");
					}
					if (values[1] != null
							&& !"".equals(((String) values[1]).trim())
							&& !mobileNums.get(official).contains(values[1])) {
						mobileNums.get(official).add(values[1]);
					}
				} else {
					defaultLangs.add(official);
					List tempEmails = new ArrayList();
					List tempMobileNums = new ArrayList();
					if (values[0] != null
							&& !"".equals(((String) values[0]).trim())) {
						tempEmails.add(MimeUtility
								.encodeText(
										(String) values[3],
										"UTF-8", "B")
								+ "<" + values[0] + ">");
					}
					if (values[1] != null
							&& !"".equals(((String) values[1]).trim())) {
						tempMobileNums.add(values[1]);
					}
					emails.put(official, tempEmails);
					mobileNums.put(official, tempMobileNums);
				}
			}
		}
		info.setEmails(emails);
		info.setMobileNums(mobileNums);
		info.setDefaultLangs(defaultLangs);
		return info;
	}

	@Override
	public List parseSysOrgRole(List originElementList,
			SysOrgElement baseElement) throws Exception {
		if (originElementList == null) {
            return null;
        }
		List rtnList = new ArrayList();
		for (int i = 0; i < originElementList.size(); i++) {
			SysOrgElement element = (SysOrgElement) originElementList.get(i);
			// 作者 曹映辉 #日期 2014年2月17日 对非组织架构类型的数据直接忽略防止出现异常
			if (!(originElementList.get(i) instanceof SysOrgElement)) {
				if (logger.isDebugEnabled()) {
					logger.debug("解析组织架构中出现非组织架构类型数据:" + element.getFdId() + " - " + element.getFdOrgType() + " - " + element.getFdName());
				}
				continue;
			}

			if (element.getFdOrgType().intValue() == ORG_TYPE_ROLE) {
				SysOrgRole orgRole = (SysOrgRole) format(element);
				SysOrgRolePluginContext context = new SysOrgRolePluginContext(
						baseElement, orgRole);
				ISysOrgRolePlugin plugin = (ISysOrgRolePlugin) applicationContext
						.getBean(orgRole.getFdPlugin());
				rtnList.addAll(plugin.parse(context));
				// } else if (element.getFdOrgType().intValue() ==
				// ORG_TYPE_PERSON) { //所有类型都需要添加到返回集合 2009-09-01 by fuy
			} else {
				rtnList.add(element);
			}
		}
		return rtnList;
	}

	@Override
	public List<Object[]> getNamesByIds(List<String> ids) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgElement.fdName,sysOrgElement.fdId");
		HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN(
				"sysOrgElement.fdId", "sysOrgElement" + "0_", ids);
		hqlInfo.setWhereBlock(hqlWrapper.getHql());
		hqlInfo.setParameter(hqlWrapper.getParameterList());
		return elementDao.findValue(hqlInfo);
	}

	@Override
	public List<String> getHierarchyIdsByIds(List<String> ids) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("sysOrgElement.fdHierarchyId");
		HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN(
				"sysOrgElement.fdId", "sysOrgElement" + "0_", ids);
		hqlInfo.setWhereBlock(hqlWrapper.getHql());
		hqlInfo.setParameter(hqlWrapper.getParameterList());
		return elementDao.findValue(hqlInfo);
	}

	@Override
	public List parseSysOrgRole(SysOrgElement originElement,
			SysOrgElement baseElement) throws Exception {
		if (originElement == null) {
            return null;
        }
		List rtnList = new ArrayList();
		rtnList.add(originElement);
		return parseSysOrgRole(rtnList, baseElement);
	}

	@Override
	public SysOrgRole getRoleByName(String confName, String roleName)
			throws Exception {
		if (StringUtil.isNull(roleName)) {
            return null;
        }
		StringBuffer whereBlock = new StringBuffer(
				"sysOrgRole.fdIsAvailable=true");
		whereBlock.append(" and sysOrgRole.fdName=:roleName");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setParameter("roleName", roleName);
		if (StringUtil.isNull(confName)) {
			whereBlock.append(" and sysOrgRole.fdRoleConf is null");
		} else {
			whereBlock.append(" and sysOrgRole.fdRoleConf.fdName=:confName");
			hqlInfo.setParameter("confName", confName);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
		return (SysOrgRole) roleDao.findFirstOne(hqlInfo);
	}

	@Override
	public boolean checkPersonIsOrgAdmin(SysOrgPerson sysOrgPerson,
			SysOrgElement sysOrgElement) throws Exception {
		return checkPersonIsOrgAdmin(sysOrgPerson, sysOrgElement, null);
	}

	@Override
	public boolean checkPersonIsOrgAdmin(SysOrgPerson sysOrgPerson, SysOrgElement sysOrgElement, String method)
			throws Exception {
		// 用户ID以及岗位ID
		String authIds = "";
		List<SysOrgPost> posts = sysOrgPerson.getFdPosts();
		for (int i = 0; i < posts.size(); i++) {
			SysOrgPost post = posts.get(i);
			authIds += "'" + post.getFdId() + "',";
		}
		authIds += "'" + sysOrgPerson.getFdId() + "'";
		String hierarchyId = sysOrgElement.getFdHierarchyId();

		// 组织架构层级ID
		String orgIds = "";
		if (!HIERARCHY_INVALID_FLAG.equals(hierarchyId)) {
			hierarchyId = hierarchyId.substring(1, hierarchyId.length() - 1);
			String[] hierarchyIds = hierarchyId.split(HIERARCHY_ID_SPLIT);
			int length = hierarchyIds.length;
			for (int i = 0; i < length; i++) {
				if (i == length - 1) {
					orgIds += "'" + hierarchyIds[i] + "'";
				} else {
					orgIds += "'" + hierarchyIds[i] + "',";
				}
			}
				} else {
			orgIds = "'" + sysOrgElement.getFdId() + "'";
				}

		String sql = "select count(fdId) from  com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement where sysOrgElement.authElementAdmins.fdId in ("
				+ authIds + ") and sysOrgElement.fdId in (" + orgIds + ")";
		Query query = elementDao.getHibernateSession().createQuery(sql);
		int total = ((Long) query.iterate().next()).intValue();
		if (total == 0) {
			return false;
		}
		return true;
	}

	public void setDeptDao(ISysOrgDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setElementDao(ISysOrgElementDao elementDao) {
		this.elementDao = elementDao;
	}

	public void setGroupDao(ISysOrgGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setOrgDao(ISysOrgOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public void setPersonDao(ISysOrgPersonDao personDao) {
		this.personDao = personDao;
	}

	public void setPostDao(ISysOrgPostDao postDao) {
		this.postDao = postDao;
	}

	public void setRoleDao(ISysOrgRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public int getStaffingLevelValue(SysOrgPerson person) throws Exception {
		// TODO 自动生成的方法存根
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getStaffingLevel(person);
		if (sysOrganizationStaffingLevel != null) {
			return sysOrganizationStaffingLevel.getFdLevel();
		}
		return -1;
	}

	@Override
	public SysOrganizationStaffingLevel getStaffingLevel(SysOrgPerson person)
			throws Exception {
		// TODO 自动生成的方法存根
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = person
				.getFdStaffingLevel();
		if (sysOrganizationStaffingLevel != null) {
			return sysOrganizationStaffingLevel;
		}
		sysOrganizationStaffingLevel = sysOrganizationStaffingLevelService
				.getDefaultStaffingLevel();
		if (sysOrganizationStaffingLevel != null) {
			return sysOrganizationStaffingLevel;
		}
		return null;
	}

	@Override
	public List<SysOrgElement> findByLoginName(String[] loginName)
			throws Exception {
		SysOrganizationConfig config = new SysOrganizationConfig();
		List<String> idList = new ArrayList<String>();
		for(String name : loginName){
			name = name.trim();
			if ("2".equals(config.getLoginNameCase())) {
				// 区分大小写
				idList.add(name);
			} else {
				// 忽略大小写
				idList.add(name.toLowerCase());
			}
		}
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(config.getLoginNameCase()) || "1".equals(config.getLoginNameCase())) {
			// 保持原样
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("lower(sysOrgPerson.fdLoginName)", idList) +" and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		} else if ("2".equals(config.getLoginNameCase())) {
			// 区分大小写
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("sysOrgPerson.fdLoginName", idList) +" and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		} else if ("3".equals(config.getLoginNameCase())) {
			// 忽略大小写
			hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("sysOrgPerson.fdLoginNameLower", idList) +" and sysOrgPerson.fdIsAvailable=:fdIsAvailable");
		}
		hqlInfo.setParameter("fdIsAvailable", true);
		// 开启三员时，需要判断是否已激活
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and sysOrgPerson.fdIsActivated = :fdIsActivated");
			hqlInfo.setParameter("fdIsActivated", true);
		}
		// 关闭生态组织时，只查询内部用户
		if (!SysOrgEcoUtil.IS_ENABLED_ECO) {
			hqlInfo.setWhereBlock(hqlInfo.getWhereBlock()
					+ " and (sysOrgPerson.fdIsExternal is null or sysOrgPerson.fdIsExternal = false)");
		}
		List<SysOrgElement> rtnList = personDao.findList(hqlInfo);
		if (rtnList.isEmpty()) {
            return null;
        } else {
            return rtnList;
        }
	}

	@Override
	public List<SysOrgElement> findByKeyword(String[] keyword)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo
				.setWhereBlock(
						HQLUtil.buildLogicIN("sysOrgElement.fdKeyword",
								Arrays.asList(keyword))
								+ " and sysOrgElement.fdIsAvailable=:fdIsAvailable");
		hqlInfo.setParameter("fdIsAvailable", true);
		List<SysOrgElement> rtnList = elementDao.findList(hqlInfo);
		if (rtnList.isEmpty()) {
            return null;
        } else {
            return rtnList;
        }
	}

	@Override
	public List<SysOrgElement> findByNo(String[] fno, int rtnType)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(SysOrgHQLUtil.buildWhereBlock(rtnType,
				HQLUtil.buildLogicIN("sysOrgElement.fdNo", Arrays.asList(fno)),
				"sysOrgElement"));
		List<SysOrgElement> rtnList = elementDao.findList(hqlInfo);
		if (rtnList.isEmpty()) {
            return null;
        } else {
            return rtnList;
        }
	}

	@Override
	public List<SysOrgElement> findByLdapDN(String[] dn) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereStr = HQLUtil.buildLogicIN("sysOrgElement.fdLdapDN",
				Arrays.asList(dn));
		hqlInfo.setWhereBlock(whereStr);

		List<SysOrgElement> modelList = elementDao.findValue(hqlInfo);

		if (ArrayUtil.isEmpty(modelList)) {
			return null;
		}
		return modelList;
	}

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	public ISysOrganizationStaffingLevelService getSysOrganizationStaffingLevelService() {
		return sysOrganizationStaffingLevelService;
	}

	@Override
	public List<String> findAdminIdsByElemId(String elemId) {
		if (StringUtil.isNull(elemId)) {
			return new ArrayList();
		}
		String sql = "select fd_admin_id from sys_org_element_admins where fd_element_id = ?";
		NativeQuery query = elementDao.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("fd_admin_id", StandardBasicTypes.STRING);
		query.setParameter(0, elemId);
		return query.list();
	}
	
	@Override
	public Boolean isElemCreator(SysOrgElement elem, SysOrgElement currElem) throws Exception {	
		// 父组织所属组织类型
		String externalOrgId = getTopHierarchyId(elem);
		
		if (StringUtil.isNull(externalOrgId)) {
			return false;
		}

		//查询当前人是否是这个组织类型的的可读者
		SysOrgElementExternal external;
		List<String> readerIds = new ArrayList<String>();
		try {
			external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(externalOrgId);
			if (CollectionUtils.isEmpty(external.getAuthReaders())) {
				return false;
			}
			for (SysOrgElement reader : external.getAuthReaders()) {
				readerIds.add(reader.getFdId());
			}
		} catch (Exception e) {
			return false;
		}
		
		// 展开岗位
		readerIds = expandToPersonIds(readerIds);
		
		// 查询组织以及组织类型的创建人
		String orgIds = getHierarchyIdStr(elem);
		String sql = "select doc_creator_id from sys_org_element where fd_id in (" + orgIds + ")";
		NativeQuery query = elementDao.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("doc_creator_id",StandardBasicTypes.STRING);
		List<String> creators = query.list();
		
		boolean isOrg = elem.getFdOrgType() == ORG_TYPE_ORG;
		
		return readerIds.contains(currElem.getFdId()) && (isOrg || creators.contains(currElem.getFdId()));
	}
	
	@Override
	public Boolean isAvailablePerson(SysOrgElement elem, SysOrgElement currElem, String method) throws Exception {
		// 父组织所属组织类型
		String externalOrgId = getTopHierarchyId(elem);

		if (StringUtil.isNull(externalOrgId)) {
			return false;
		}

		//查询当前人是否是这个组织类型的的可读者
		SysOrgElementExternal external;
		List<String> readerIds = new ArrayList<String>();
		try {
			external = (SysOrgElementExternal) sysOrgElementExternalService.findByPrimaryKey(externalOrgId);
			if (CollectionUtils.isEmpty(external.getAuthReaders())) {
				return false;
			}

			for (SysOrgElement reader : external.getAuthReaders()) {
				readerIds.add(reader.getFdId());
			}
			
		} catch (Exception e) {
			return false;
		}
		
		// 展开岗位的人员id
		readerIds = expandToPersonIds(readerIds);
		
		// 查询组织以及组织类型的创建人
		String orgIds = getHierarchyIdStr(elem);
		String sql = "select doc_creator_id from sys_org_element where fd_id in (" + orgIds + ")";
		NativeQuery query = elementDao.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("doc_creator_id",StandardBasicTypes.STRING);
		List<String> creators = query.list();
		
		// 组织类型下添加人员/组织，只需要判断当前登陆人在组织类型得可使用人员就行了
		boolean isOrgAddOrSave = elem.getFdOrgType() == ORG_TYPE_ORG && ("add".equals(method) || "save".equals(method));
		
		return readerIds.contains(currElem.getFdId()) && (isOrgAddOrSave || creators.contains(currElem.getFdId()));
	}
	
	private String getHierarchyIdStr(SysOrgElement elem) {
		String orgIds = "";
		if (elem == null) {
            return orgIds;
        }
		
		String hierarchyId = elem.getFdHierarchyId();
		if (!HIERARCHY_INVALID_FLAG.equals(hierarchyId)) {
			hierarchyId = hierarchyId.substring(1, hierarchyId.length() - 1);
			String[] hierarchyIds = hierarchyId.split(HIERARCHY_ID_SPLIT);
			for (int i = 0; i < hierarchyIds.length; i++) {
				if (i == hierarchyIds.length - 1) {
					orgIds += "'" + hierarchyIds[i] + "'";
				} else {
					orgIds += "'" + hierarchyIds[i] + "',";
				}
			}
		} else {
			orgIds = "'" + elem.getFdId() + "'";
		}
		
		return orgIds;
	}
	
	private String getTopHierarchyId(SysOrgElement elem) {
		String topHierarchyId = "";
		if (elem == null) {
            return topHierarchyId;
        }
		String hierarchyId = elem.getFdHierarchyId();
		if (!HIERARCHY_INVALID_FLAG.equals(hierarchyId)) {
			hierarchyId = hierarchyId.substring(1, hierarchyId.length() - 1);
			String[] hierarchyIds = hierarchyId.split(HIERARCHY_ID_SPLIT);
			topHierarchyId = hierarchyIds[0];
		}
		
		return topHierarchyId;
	}
	
	@Override
	public List<String> findByCreator(SysOrgElement elem) {
		String sql = "select fd_id from sys_org_element where doc_creator_id=?";
		NativeQuery query = elementDao.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("fd_id",StandardBasicTypes.STRING);
		query.setParameter(0, elem.getFdId());
		return query.list();
	}
	
	@Override
	public List<String> findOrgAdminByElem(SysOrgElement elem) {
		if (elem == null) {
			return new ArrayList();
		}
		List<String> postIds = new ArrayList<>();
		// 增加岗位处理
		List<SysOrgPost> posts = elem.getFdPosts();
		for (SysOrgPost post : posts) {
			postIds.add(post.getFdId());
		}
		StringBuilder sb = new StringBuilder("('").append(elem.getFdId()).append("'");
		for (String id : postIds) {
			sb.append(",").append("'").append(id).append("'");
		}
		sb.append(")");
		
		String sql = "select fd_element_id from sys_org_element_admins where fd_admin_id in " + sb.toString();
		NativeQuery query = elementDao.getHibernateSession().createNativeQuery(sql);
		query.setCacheable(true);
		query.setCacheMode(CacheMode.NORMAL);
		query.setCacheRegion("sys-organization");
		query.addScalar("fd_element_id",StandardBasicTypes.STRING);
		return query.list();
	}

	@Override
	public int getCountByRegistered(Boolean isExternal, Boolean exclude) throws Exception {
		return elementDao.getCountByRegistered(isExternal, exclude);
	}

	@Override
	public void afterContextLoaded() {
		// 系统启动成功，加载缓存
		CacheConfig config = CacheConfig.get(SysOrgCoreServiceImp.class);
		config.setCacheLoader(new CacheLoader() {
			@Override
			public Object load(String key) throws Exception {
				// 查询不带权限数据
				return findPrivileges();
			}
		});
	}

}
