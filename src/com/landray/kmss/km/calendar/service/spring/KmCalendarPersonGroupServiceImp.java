package com.landray.kmss.km.calendar.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService;
import com.landray.kmss.km.calendar.util.CalendarSysOrgUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONObject;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;

import java.util.*;
import java.util.stream.Collectors;

public class KmCalendarPersonGroupServiceImp extends BaseServiceImp
		implements IKmCalendarPersonGroupService, IXMLDataBean {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private IKmCalendarMainService kmCalendarMainService;

	public void setKmCalendarMainService(
			IKmCalendarMainService kmCalendarMainService) {
		this.kmCalendarMainService = kmCalendarMainService;
	}

	private List<KmCalendarPersonGroup> getMyIsPerson(String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				"left join kmCalendarPersonGroup.fdPersonGroup fdPersonGroup");
		hqlInfo.setWhereBlock("fdPersonGroup.fdId in(:personId)");
		hqlInfo.setParameter("personId",
				UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return findList(hqlInfo);
	}

	private List<KmCalendarPersonGroup> getMyIsReader(String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				"left join kmCalendarPersonGroup.authReaders authReader");
		hqlInfo.setWhereBlock("authReader.fdId in (:readerId)");
		hqlInfo.setParameter("readerId", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return findList(hqlInfo);
	}

	private List<KmCalendarPersonGroup> getMyIsEditor(String personId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(
				"left join kmCalendarPersonGroup.authEditors authEditors");
		hqlInfo.setWhereBlock("authEditors.fdId in (:editorId)");
		hqlInfo.setParameter("editorId", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		return findList(hqlInfo);
	}

	@Override
	public List<KmCalendarPersonGroup> getUserPersonGroup(String personId)
			throws Exception {
		Set<KmCalendarPersonGroup> set = new HashSet<KmCalendarPersonGroup>();
		// 我是群组成员
		set.addAll(getMyIsPerson(personId));
		// 我是可阅读者
		set.addAll(getMyIsReader(personId));
		// 我是可编辑者
		set.addAll(getMyIsEditor(personId));
		List<KmCalendarPersonGroup> groups = new ArrayList<KmCalendarPersonGroup>(
				set);
		return groups;
	}

	@Override
	public Map<String, List<SysOrgElement>> getFdPersonGroup(
			RequestContext requestContext,
			Boolean loadAll) throws Exception {
		String personGroupId = requestContext.getParameter("personGroupId");// 群组id
		String personId = requestContext.getParameter("personsId");// 查询人员
		String loadAllPara = requestContext.getParameter("loadAll");// 加载所有标示位
		String performanceApprove = (String) requestContext.getAttribute("performanceApprove"); //执行性能优化标记
		if (StringUtil.isNotNull(loadAllPara)) {
			loadAll = new Boolean(loadAllPara);
		}
		KmCalendarPersonGroup kmCalendarPersonGroup = (KmCalendarPersonGroup) findByPrimaryKey(
				personGroupId);
		List<SysOrgElement> list = new ArrayList<SysOrgElement>();
		if(StringUtil.isNotNull(performanceApprove) && "y".equalsIgnoreCase(performanceApprove)){
			Set<String> hierarchyIds = kmCalendarPersonGroup.getFdPersonGroup()
					.stream()
					.filter(org->StringUtil.isNotNull(org.getFdHierarchyId()))
					.map(org->org.getFdHierarchyId())
					.collect(Collectors.toSet());
			boolean searchParent = true;
			list = getSysOrgElements(hierarchyIds, searchParent);
		}
		else{
			list = sysOrgCoreService.expandToPerson(kmCalendarPersonGroup.getFdPersonGroup());// 群组成员
		}
		List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();// 全部日程人员
		List<SysOrgElement> persons = new ArrayList<SysOrgElement>();// 当前查询日程人员

		if (list != null && !list.isEmpty()) {
			// 移除无效成员
			for (int i = 0; i < list.size(); i++) {
				SysOrgElement person = list.get(i);
				if (person.getFdIsAvailable()) {
					totalPersons.add(person);
				}
			}

			// 筛选指定人员
			if (StringUtil.isNotNull(personId)) {
				for (SysOrgElement person : totalPersons) {
					if (personId.indexOf(person.getFdId()) > -1) {
						persons.add(person);
					}
				}
			} else if (loadAll) {
				persons = totalPersons;
			} else {
				int fromIndex = 0;// 开始index
				int toIndex = fromIndex + 15 > totalPersons.size()
						? totalPersons
								.size()
						: fromIndex + 15;// 结束index
				Iterator<SysOrgElement> iterator = totalPersons.iterator();
				int index = 0;
				while (iterator.hasNext()) {
					SysOrgElement person = iterator.next();
					if (index >= fromIndex) {
						persons.add(person);
					}
					index++;
					if (index >= toIndex) {
                        break;
                    }
				}
			}
		}
		Map<String, List<SysOrgElement>> rtnMap = new HashMap<String, List<SysOrgElement>>();
		rtnMap.put("totalPersons", totalPersons);
		rtnMap.put("persons", persons);

		return rtnMap;
	}

	@Override
	public List<SysOrgElement> getSysOrgElements(Set<String> hierarchyIds, boolean searchParent) throws Exception {
		List<SysOrgElement> list = new ArrayList<>();
		hierarchyIds = CalendarSysOrgUtil.filterSubHierarchy(hierarchyIds);
		if(hierarchyIds != null && hierarchyIds.size() > 0){
			StringBuilder baseSqlBuilder = new StringBuilder();
			baseSqlBuilder.append("SELECT fd_id, fd_name, fd_org_type, fd_is_available, fd_parentid, fd_order ");
			baseSqlBuilder.append("FROM sys_org_element ");
			baseSqlBuilder.append("WHERE fd_is_available="+ SysOrgHQLUtil.toBooleanValueString(true) + " ");
			StringBuilder sqlBuilder = new StringBuilder(baseSqlBuilder);
			sqlBuilder.append("AND (fd_org_type=" + SysOrgConstant.ORG_TYPE_PERSON);
			sqlBuilder.append("		 OR fd_org_type = " + SysOrgConstant.ORG_TYPE_POST +") ");
			sqlBuilder.append("AND ( ");
			String OR = "";
			Iterator<String> it = hierarchyIds.iterator();
			while (it.hasNext()) {
				sqlBuilder.append(OR + " fd_hierarchy_id like '" + it.next() + "%' ");
				if(OR.length() == 0){
					OR = " or ";
				}
			}
			sqlBuilder.append(")");
			doSearchSysOrgElements(list, sqlBuilder.toString(), searchParent);
			List<SysOrgElement> sysOrgPost = list.stream().filter(s->s.getFdOrgType() == SysOrgConstant.ORG_TYPE_POST).collect(Collectors.toList());
			if(sysOrgPost.size() > 0){
				list.removeAll(sysOrgPost);
				List<String> sysOrgPostIds = sysOrgPost.stream().map(p->p.getFdId()).collect(Collectors.toList());
				sqlBuilder.setLength(0);
				sqlBuilder.append("SELECT distinct fd_personid FROM sys_org_post_person WHERE ");
				sqlBuilder.append(HQLUtil.buildLogicIN("fd_postid", sysOrgPostIds));
				Session session = this.getBaseDao().getHibernateSession();
				NativeQuery query = session.createNativeQuery(sqlBuilder.toString());
				query.setCacheable(true);
				query.setCacheMode(CacheMode.NORMAL);
				query.setCacheRegion("km-calendar");
				query.addScalar("fd_personid", StandardBasicTypes.STRING);
				List<String> result = query.list();
				if(result != null && result.size() > 0){
					sqlBuilder.setLength(0);
					sqlBuilder.append(baseSqlBuilder);
					sqlBuilder.append("AND " + HQLUtil.buildLogicIN("fd_id", result) + " ");
					sqlBuilder.append("AND fd_org_type = " + SysOrgConstant.ORG_TYPE_PERSON);
					doSearchSysOrgElements(list, sqlBuilder.toString(), searchParent);
				}
			}
		}
		return list;
	}

	private void doSearchSysOrgElements(List<SysOrgElement> list, String sql, boolean searchParent) throws Exception {
		NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery(sql);
		List result = query.list();
		if(result != null && result.size() > 0){
			Set<SysOrgElement> eleSet = new HashSet<>(list);
			if(!list.isEmpty()){
				list.clear();
			}
			Iterator<Object[]> it = result.iterator();
			while(it.hasNext()){
				Object[] orgEleInfo = it.next();
				SysOrgElement sysOrgElement = new SysOrgElement();
				sysOrgElement.setFdId(String.valueOf(orgEleInfo[0]));
				sysOrgElement.setFdName(String.valueOf(orgEleInfo[1]));
				sysOrgElement.setFdOrgType(Integer.valueOf(orgEleInfo[2].toString()));
				//是否有效-兼容oracle-Boolean数据 #170390
				boolean isAvailable = Boolean.valueOf(orgEleInfo[3].toString()) || "1".equals(orgEleInfo[3].toString());
				sysOrgElement.setFdIsAvailable(isAvailable);
				if (null != orgEleInfo[5]) {
					sysOrgElement.setFdOrder(Integer.valueOf(orgEleInfo[5].toString()));
				}
				if(searchParent && sysOrgElement.getFdOrgType() == SysOrgConstant.ORG_TYPE_PERSON
						&& orgEleInfo[4] != null){
					SysOrgElement parent = sysOrgCoreService.findByPrimaryKey(String.valueOf(orgEleInfo[4]));
					sysOrgElement.setFdParent(parent);
				}
				eleSet.add(sysOrgElement);
			}
			list.addAll(eleSet);
		}
	}

	@Override
	public Map<String, List<SysOrgElement>>
			getFdPersonGroup(RequestContext requestContext)
			throws Exception {
		return getFdPersonGroup(requestContext, false);
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		String personGroupId = requestInfo.getParameter("personGroupId");
		List list = new ArrayList();
		KmCalendarPersonGroup personGroup = (KmCalendarPersonGroup) findByPrimaryKey(
				personGroupId);
		List<SysOrgElement> personList = personGroup.getFdPersonGroup();
		for (SysOrgElement person : personList) {
			Map map = new HashMap();
			map.put("id", person.getFdId());
			map.put("name", person.getFdName());
			list.add(map);
		}
		return list;
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmCalendarPersonGroup kmCalendarPersonGroup = (KmCalendarPersonGroup) modelObj;
		String beforeChangePerson = kmCalendarPersonGroup.getBeforeChangePerson();
		if (StringUtil.isNotNull(beforeChangePerson) && beforeChangePerson.startsWith("{")
				&& beforeChangePerson.endsWith("}")) {
			JSONObject json = JSONObject.fromObject(beforeChangePerson);
			String beforeIds = (String) json.get("fdPersonGroupIds");
			if (StringUtil.isNotNull(beforeIds)) {
				String[] ids = beforeIds.split(";");
				List<SysOrgElement> personListNew = kmCalendarPersonGroup.getFdPersonGroup();
				List<SysOrgElement> personListOld = sysOrgCoreService.findByPrimaryKeys(ids);
				List<SysOrgElement> removeTmp = new ArrayList<SysOrgElement>();
				removeTmp.addAll(personListOld);
				removeTmp.removeAll(personListNew);
				kmCalendarMainService.deletePersonGroupEvent(kmCalendarPersonGroup, removeTmp);
			}
		}
		super.update(modelObj);
	}
	
	@Override
	public void deleteMainGroup(String fdId) throws Exception {
		String sql = "delete from km_calendar_main_group where fd_group in (?)";
		NativeQuery query = getBaseDao().getHibernateSession().createNativeQuery(sql);
		query.addSynchronizedQuerySpace("km_calendar_main_group");
		query.setString(0, fdId);
		query.executeUpdate();
	}
	
	@Override
	public  void deleteMainGroup(String[] ids) throws Exception {
		for (int i = 0; i < ids.length; i++) {
			if (i > 0) {
                flushHibernateSession();
            }
			deleteMainGroup(ids[i]);
		}
	}
}
