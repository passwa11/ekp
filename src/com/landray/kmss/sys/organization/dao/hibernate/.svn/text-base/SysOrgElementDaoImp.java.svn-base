package com.landray.kmss.sys.organization.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.authentication.user.validate.Config;
import com.landray.kmss.sys.authorization.util.TripartiteAdminUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgDept;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgOrg;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPersonRestrict;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrgRole;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.landray.kmss.util.ArrayUtil.averageAssign;

public class SysOrgElementDaoImp extends BaseDaoImp implements
		ISysOrgElementDao, SysOrgConstant, BaseTreeConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgElementDaoImp.class);

	private ThreadLocal<Boolean> notToUpdateRelation = new ThreadLocal<Boolean>();
	private ThreadLocal<Boolean> notToUpdateHierarchy = new ThreadLocal<Boolean>();

	/**
	 * 获取层级ID
	 * 
	 * @param element
	 * @return
	 */
	private String getTreeHierarchyId(SysOrgElement element) {
		if (element.getFdOrgType().intValue() == ORG_TYPE_ORG
				|| element.getFdParent() == null) {
            return HIERARCHY_ID_SPLIT + element.getFdId() + HIERARCHY_ID_SPLIT;
        } else {
            return element.getFdParent().getFdHierarchyId() + element.getFdId()
                    + HIERARCHY_ID_SPLIT;
        }
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysOrgElement element = (SysOrgElement) modelObj;
		if (!element.getFdIsAvailable().booleanValue()) {
			// 无效的组织架构，清除关系
			clearRelation(element);
		} else {
			element.setFdHierarchyId(getTreeHierarchyId(element));
		}

		// 三员管理中，系统管理增加的人员信息，都是待激活的，并且没有部门、岗位、群组。只有基础信息
		if (TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			if (element instanceof SysOrgPerson) {
				SysOrgPerson person = (SysOrgPerson) element;
				String loginName = person.getFdLoginName();
				// 如果是初始化三员管理就不作处理
				// 如果是系统内置人员也不处理：admin，anonymous，everyone
				if (TripartiteAdminUtil.ADMIN_AUDITOR_NAMES.contains(loginName)
						|| TripartiteAdminUtil.ADMIN_SECURITY_NAMES.contains(loginName)
						|| TripartiteAdminUtil.ADMIN_SYSTEM_NAMES.contains(loginName)
						|| "anonymous".equals(loginName) || "admin".equals(loginName)
						/*
						 * #102818 这个id定义在com.landray.kmss.elec.core.ElecCoreConstants里，为了不增加耦合，暂时使用字面量
						 * 后续需要改成接口或者扩展点实现的方式来完成例外判断
						 */
						|| "1183b1b84ee4f581bba001c47a78b49d".equals(person.getFdId())
						|| SysOrgConstant.ORG_PERSON_EVERYONE_ID.equals(person.getFdId())
						) {
				} else {
					if (TripartiteAdminUtil.isSysadmin()) {
						// 清除所有关系
						clearRelation(element);
						// 待激活
						person.setFdIsActivated(false);
					}
				}
			}
		}

		return super.add(element);
	}

	protected void clearRelation(SysOrgElement element) {
		// 生态组织不能清除上下级关系
		if (!BooleanUtils.isTrue(element.getFdIsExternal())) {
			element.setHbmParent(null);
			element.setFdHierarchyId("0");
		}
		element.setFdGroups(null);
		element.setFdPersons(null);
		element.setFdPosts(null);
		element.setHbmThisLeader(null);
		if(element instanceof SysOrgPerson) {
			SysOrgPerson ele = (SysOrgPerson) element;
			ele.setFdStaffingLevel(null);
		}
		if (element.getHbmThisLeaderChildren() != null) {
            element.getHbmThisLeaderChildren().clear();
        }
		element.setHbmSuperLeader(null);
		if (element.getHbmSuperLeaderChildren() != null) {
            element.getHbmSuperLeaderChildren().clear();
        }

		// 移除组织机构的管理员
		element.setAuthElementAdmins(null);

		// 移除个人地址本（个人常用群组）
		String sql = "delete from sys_org_person_type where fd_person_id = :personId";
		if (logger.isDebugEnabled()) {
            logger.debug("移除个人地址本（个人常用群组），hql=" + sql);
        }
		Query q = getHibernateSession().createNativeQuery(sql);
		q.setParameter("personId", element.getFdId());
		q.executeUpdate();
	}

	@Override
    public SysOrgElement format(SysOrgElement element) throws Exception {
		if (element == null) {
            return null;
        }
		switch (element.getFdOrgType().intValue()) {
		case ORG_TYPE_ORG:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgOrg.class, false);
		case ORG_TYPE_DEPT:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgDept.class, false);
		case ORG_TYPE_POST:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgPost.class, false);
		case ORG_TYPE_PERSON:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgPerson.class, false);
		case ORG_TYPE_GROUP:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgGroup.class, false);
		case ORG_TYPE_ROLE:
			return (SysOrgElement) findByPrimaryKey(element.getFdId(),
					SysOrgRole.class, false);
		default:
			return null;
		}
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysOrgElement element = (SysOrgElement) modelObj;
		element.setFdAlterTime(new Date());
		if (!element.getFdIsAvailable().booleanValue()) {
			// 无效的组织架构，清除关系
			clearRelation(element);
			super.update(element);
			return;
		}
		// 更新层级ID
		String hierarchyId = element.getFdHierarchyId();
		element.setFdHierarchyId(getTreeHierarchyId(element));
		super.update(modelObj);

		int orgType = element.getFdOrgType().intValue();
		if (orgType > ORG_TYPE_DEPT) {
            return;
        }

		Boolean notToUpdateRelation = this.notToUpdateRelation.get();
		if (notToUpdateRelation != null && notToUpdateRelation.booleanValue()) {
            return;
        }

		// 当组织架构为部门或机构时，判断层级ID是否有改动，若已经改动，则更新所有下级关系
		// 如将无效的部门变更为有效部门时，不存在下级关系，所以不更新层级架构
		if (!hierarchyId.equals(element.getFdHierarchyId())
				&& !"0".equals(hierarchyId)
				&& StringUtil.isNotNull(hierarchyId)) {
			SysOrgElement parentOrg = orgType == ORG_TYPE_ORG ? element
					: element.getFdParentOrg();
			String hql = "update com.landray.kmss.sys.organization.model.SysOrgElement set fdAlterTime=:thisDay,";
			if (parentOrg == null) {
                hql += "hbmParentOrg=null,";
            } else {
                hql += "hbmParentOrg=:parentOrg,";
            }
			hql += "fdHierarchyId='" + element.getFdHierarchyId()
					+ "' || substring(fdHierarchyId, "
					+ (hierarchyId.length() + 1) + ", length(fdHierarchyId)) ";
			hql += "where substring(fdHierarchyId,1," + hierarchyId.length()
					+ ")='" + hierarchyId + "'";
			if (logger.isDebugEnabled()) {
                logger.debug("更新组织架构的所有下级关系，hql=" + hql);
            }
			Query q = getHibernateSession().createQuery(hql);
			if (parentOrg != null) {
                q.setParameter("parentOrg", orgType == ORG_TYPE_ORG ? element
                        : element.getFdParentOrg());
            }
			q.setParameter("thisDay", new Date());
			q.executeUpdate();
		}
	}

	// 该部分的测试代码见SysorgCoreServiceImp中的声明
	@Override
    public int getAllChildrenCount(SysOrgElement element, int rtnType)
			throws Exception {
		String whereBlock = SysOrgHQLUtil.buildWhereBlock(rtnType,
				SysOrgHQLUtil.buildAllChildrenWhereBlock(element, null,
						"sysOrgElement"), "sysOrgElement");
		whereBlock = StringUtil.isNull(whereBlock) ? "" : " where "
				+ whereBlock;
		return ((Long) super.getSession()
				.createQuery(
						"select count(*) from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement"
								+ whereBlock).iterate().next()).intValue();
	}

	@Override
    public int getAllCount(int rtnType) throws Exception {
		return getAllCount(rtnType , null);
	}

	@Override
	public int getAllCount(int rtnType ,String whereBlock) throws Exception {
		String __whereBlock = SysOrgHQLUtil.buildWhereBlock(rtnType, whereBlock,
				"sysOrgElement");
		__whereBlock = StringUtil.isNull(__whereBlock) ? "" : " where "
				+ __whereBlock;
		return ((Long) super.getSession()
				.createQuery(
						"select count(*) from com.landray.kmss.sys.organization.model.SysOrgElement sysOrgElement"
								+ __whereBlock).iterate().next()).intValue();
	}

	/**
	 * 查询注册用户数量（排除限制用户与特权用户）
	 * @param isExternal
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getCountByRegistered(Boolean isExternal, Boolean exclude) throws Exception {
		try {
			// 排除特权
			List<String> personIds = null;
			if (exclude != null && exclude) {
				personIds = getSession().createNativeQuery(new String(new byte[]{115, 101, 108, 101, 99, 116, 32, 102, 100, 95, 112, 101, 114, 115, 111, 110, 95, 105, 100, 32, 102, 114, 111, 109, 32, 115, 121, 115, 95, 111, 114, 103, 95, 112, 101, 114, 115, 111, 110, 95, 112, 114, 105, 118, 105, 108, 101, 103, 101, 32, 119, 104, 101, 114, 101, 32, 49, 32, 61, 32, 49, 32, 111, 114, 100, 101, 114, 32, 98, 121, 32, 102, 100, 95, 105, 100})).setMaxResults(Config.getLicPrivCount()).list();
			}
			String hql = "SELECT count(*) FROM " + SysOrgPerson.class.getName() + " sysOrgPerson "
					+ " WHERE sysOrgPerson.fdIsAvailable = :fdIsAvailable AND sysOrgPerson.fdIsBusiness = :fdIsBusiness AND (sysOrgPerson.fdCanLogin IS NULL OR sysOrgPerson.fdCanLogin = :fdCanLogin)"
					+ " AND sysOrgPerson.fdId NOT IN (select sysOrgPersonRestrict.fdId from " + SysOrgPersonRestrict.class.getName() + " sysOrgPersonRestrict)";
			Map<String, List<String>> params = new HashMap<>();
			if (CollectionUtils.isNotEmpty(personIds)) {
				int maxSize = 1000;
				if (personIds.size() <= maxSize) {
					// 特权用户
					hql += " AND sysOrgPerson.fdId NOT IN (:personIds)";
					params.put("personIds", personIds);
				} else {
					List<List<String>> lists = averageAssign(personIds, maxSize);
					for (int i = 0; i < lists.size(); i++) {
						List<String> list = lists.get(i);
						hql += " AND sysOrgPerson.fdId NOT IN (:personIds_" + i + ")";
						params.put("personIds_" + i, list);
					}
				}
			}
			if (isExternal != null) {
				if (isExternal) {
					hql += " AND sysOrgPerson.fdIsExternal = :isExternal";
				} else {
					hql += " AND (sysOrgPerson.fdIsExternal IS NULL OR sysOrgPerson.fdIsExternal = :isExternal)";
				}
			}
			Query query = getSession().createQuery(hql);
			query.setParameter("fdIsAvailable", true);
			query.setParameter("fdIsBusiness", true);
			query.setParameter("fdCanLogin", true);
			if (isExternal != null) {
				query.setParameter("isExternal", isExternal);
			}
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
			Object obj = query.getSingleResult();
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			logger.error("获取注册用户数失败[isExternal=" + isExternal + "]：", e);
			return 0;
		}
	}

	@Override
    public void updateRelation() throws Exception {
		int selectCount = 0;
		int updateCount = 0;
		// 更新机构和第一层级
		String hierarchyId = "concat(concat('" + HIERARCHY_ID_SPLIT
				+ "', fdId), '" + HIERARCHY_ID_SPLIT + "')";
		String hql = "update "
				+ SysOrgElement.class.getName()
				+ " set hbmParentOrg=null, fdHierarchyId="
				+ hierarchyId
				+ " where fdIsAvailable=:fdIsAvailable and "
				+ "(fdOrgType=1 or fdOrgType>1 and fdOrgType<16 and hbmParent is null) and "
				+ "(hbmParentOrg is not null or fdHierarchyId!=" + hierarchyId
				+ " or fdHierarchyId is null)";
		updateCount++;
		Query createQuery = getHibernateSession().createQuery(hql);
		createQuery.setParameter("fdIsAvailable", Boolean.TRUE);
		createQuery.executeUpdate();
		flushHibernateSession();
		// 更新其它层级
		List waitList = new ArrayList();
		for (; true;) {
			hql = "select s.fdId, p.fdId, p.fdHierarchyId, p.hbmParentOrg.fdId, p.fdOrgType from "
					+ SysOrgElement.class.getName()
					+ " s inner join s.hbmParent p"
					+ " where s.fdOrgType>1 and s.fdOrgType<16 and s.fdIsAvailable= :fdIsAvailable and "
					+ "(p.fdOrgType=1 and (s.hbmParentOrg is null or s.hbmParentOrg!=p) or "
					+ "p.fdOrgType>1 and (s.hbmParentOrg is null and p.hbmParentOrg is not null or "
					+ "s.hbmParentOrg is not null and p.hbmParentOrg is null or s.hbmParentOrg!=p.hbmParentOrg) or "
					+ "s.fdHierarchyId!=concat(concat(p.fdHierarchyId,s.fdId),'"
					+ HIERARCHY_ID_SPLIT + "'))";
			selectCount++;
			Query query2 = getHibernateSession().createQuery(hql);
			query2.setCacheable(true);
			query2.setCacheMode(CacheMode.NORMAL);
			query2.setCacheRegion("sys-organization");
			query2.setParameter("fdIsAvailable", Boolean.TRUE);
			List result = query2.list();
			// 没有需要更新的记录，退出循环
			if (result.isEmpty()) {
                break;
            }
			hql = "update "
					+ SysOrgElement.class.getName()
					+ " set hbmParentOrg=:parentOrg, fdHierarchyId=:hierarchyId where fdId=:id";
			// 将所有的id记录到待更新的列表中
			for (int i = 0; i < result.size(); i++) {
				Object[] infos = (Object[]) result.get(i);
				if (!waitList.contains(infos[0])) {
                    waitList.add(infos[0]);
                }
			}
			for (int i = 0; i < result.size(); i++) {
				Object[] infos = (Object[]) result.get(i);
				// 若父也需要更新，则不更新子
				if (!waitList.contains(infos[1])) {
					waitList.remove(infos[0]);
					Query query = getHibernateSession().createQuery(hql);
					if (((Integer) infos[4]).intValue() == 1) {
						query.setString("parentOrg", (String) infos[1]);
					} else {
						query.setString("parentOrg", (String) infos[3]);
					}
					query.setString("hierarchyId", "" + infos[2] + infos[0]
							+ HIERARCHY_ID_SPLIT);
					query.setString("id", (String) infos[0]);
					updateCount++;
					query.executeUpdate();
				}
			}
			flushHibernateSession();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("更新组织架构关系成功，执行了 " + selectCount + " 次查询和 "
					+ updateCount + " 次更新");
		}
	}

	@Override
    public void setNotToUpdateRelation(Boolean notToUpdateRelation) {
		this.notToUpdateRelation.set(notToUpdateRelation);
	}

	@Override
    public void setNotToUpdateHierarchy(Boolean notToUpdateHierarchy) {
		this.notToUpdateHierarchy.set(notToUpdateHierarchy);
	}
	
	@Override
    @SuppressWarnings("unchecked")
	public List findList(HQLInfo hqlInfo) throws Exception {
		TimeCounter.logCurrentTime("Dao-findList", true, getClass());
		List rtnList = createHbmQuery(hqlInfo).list();
		TimeCounter.logCurrentTime("Dao-findList", false, getClass());
		return rtnList;
	}
	
	/**
	 * 重写findPage方法，当不设置数据过滤类型的时候，不进行数据过滤
	 */
//	public Page findPage(HQLInfo hqlInfo) throws Exception {
//		Page page = null;
//
//		if (hqlInfo.getCheckParam(SysAuthConstant.CheckType.AllCheck) == null) {
//			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
//					SysAuthConstant.AllCheck.DEFAULT);
//		}
//
//		int total = hqlInfo.getRowSize();
//		if (hqlInfo.isGetCount()) {
//			TimeCounter.logCurrentTime("Dao-findPage-count", true, getClass());
//			hqlInfo.setGettingCount(true);
//			total = ((Long) createHbmQuery(hqlInfo).iterate().next())
//					.intValue();
//			TimeCounter.logCurrentTime("Dao-findPage-count", false, getClass());
//		}
//		TimeCounter.logCurrentTime("Dao-findPage-list", true, getClass());
//		if (total > 0) {
//			hqlInfo.setGettingCount(false);
//			// Oracle的排序列若出现重复值，那排序的结果可能不准确，为了避免该现象，若出现了排序列，则强制在最后加上按fdId排序
//			String order = hqlInfo.getOrderBy();
//			if (StringUtil.isNotNull(order)) {
//				String tableName = ModelUtil.getModelTableName(StringUtil
//						.isNotNull(hqlInfo.getModelName()) ? hqlInfo
//						.getModelName() : getModelName());
//				Pattern p = Pattern.compile(",\\s*" + tableName
//						+ "\\.fdId\\s*|,\\s*fdId\\s*");
//				if (!p.matcher("," + order).find()) {
//					hqlInfo.setOrderBy(order + "," + tableName + ".fdId desc");
//				}
//			}
//			page = new Page();
//			page.setRowsize(hqlInfo.getRowSize());
//			page.setPageno(hqlInfo.getPageNo());
//			page.setTotalrows(total);
//			page.excecute();
//			Query q = createHbmQuery(hqlInfo);
//			q.setFirstResult(page.getStart());
//			q.setMaxResults(page.getRowsize());
//			page.setList(q.list());
//		}
//		if (page == null) {
//			page = Page.getEmptyPage();
//		}
//		TimeCounter.logCurrentTime("Dao-findPage-list", false, getClass());
//		return page;
//	}

}
