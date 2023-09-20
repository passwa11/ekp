package com.landray.kmss.sys.attend.service.spring;

import com.google.api.client.util.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendAuthSettingService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendOrgService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.organization.dao.ISysOrgElementDao;
import com.landray.kmss.sys.organization.dao.ISysOrgGroupDao;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author linxiuxian
 *
 */
public class SysAttendOrgServiceImp extends BaseServiceImp
		implements ISysAttendOrgService, SysOrgConstant {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendOrgServiceImp.class);

	private ISysOrgCoreService sysOrgCoreService;
	protected ISysAttendMainService sysAttendMainService;
	private ISysOrgElementService sysOrgElementService;
	private ISysAttendAuthSettingService sysAttendAuthSettingService;

	public void
			setSysOrgElementService(
					ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	private IBaseDao baseDao;
	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void
			setSysAttendMainService(
					ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}

	public void setSysAttendAuthSettingService(
			ISysAttendAuthSettingService sysAttendAuthSettingService) {
		this.sysAttendAuthSettingService = sysAttendAuthSettingService;
	}

	@Override
	public List findDeptsByLeader(SysOrgElement element) throws Exception {
		List<String> deptIds1 = findOrgIdsByLeader(element,
				new Integer[] { 1, 2 });
		List<String> deptIds2 = sysAttendAuthSettingService.findAuthListByUser(
				element,
				new Integer[] { 1, 2 });
		ArrayUtil.concatTwoList(deptIds1, deptIds2);
		return deptIds2;
	}

	@Override
	public List findPersonsByLeader(SysOrgElement element) throws Exception {
		List<String> postIds = findOrgIdsByLeader(element, new Integer[] { 4 });
		List<String> postPersonIds = sysAttendAuthSettingService
				.findAuthListByUser(
				element,
				new Integer[] { 4, 8 });
		ArrayUtil.concatTwoList(postIds, postPersonIds);
		return expandToPersonIds(postPersonIds);
	}

	@Override
	public List findOrgIdsByLeader(SysOrgElement element) throws Exception {
		List<String> orgIds1 = findOrgIdsByLeader(element,
				new Integer[] { 1, 2, 4 });
		List<String> orgIds2 = sysAttendAuthSettingService.findAuthListByUser(
				element,
				new Integer[] { 1, 2, 4, 8 });
		ArrayUtil.concatTwoList(orgIds1, orgIds2);
		return orgIds2;
	}

	@SuppressWarnings("unchecked")
	private List findOrgIdsByLeader(SysOrgElement element, Integer[] orgTypes) {
		List<String> orgIds = new ArrayList<String>();
		try {
			List<String> list = new ArrayList<String>();
			list.add(element.getFdId());
			List fdPost = element.getFdPosts();
			if (fdPost != null && !fdPost.isEmpty()) {// 领导可以是个人或岗位
				for (int i = 0; i < fdPost.size(); i++) {
					SysOrgElement ele = (SysOrgElement) fdPost.get(i);
					list.add(ele.getFdId());
				}
			}
			StringBuffer sb = new StringBuffer();
			sb.append(
					"select fd_id from sys_org_element where fd_org_type in (:orgTypes) and fd_is_available=1 ");
			sb.append(" and fd_this_leaderid in (:leaderIds)");
			orgIds = this.baseDao.getHibernateSession().createNativeQuery(sb.toString()).setParameterList("leaderIds", list).setParameterList("orgTypes", orgTypes).list();
		} catch (Exception e) {
		}
		return orgIds;
	}

	@Override
	public List findChildDeptsInfo(List<SysOrgElement> orgList) throws Exception {
		if (orgList == null || orgList.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> fdHierarchyIds = new ArrayList<String>();
		for (SysOrgElement org : orgList) {
			fdHierarchyIds.add(org.getFdHierarchyId());
		}

		StringBuffer sb = new StringBuffer();
		sb.append(
				"select fd_id,fd_name,fd_this_leaderid from sys_org_element where fd_org_type in (1,2,4) and fd_is_available=1 ");
		sb.append(" and "
				+ AttendUtil.buildLikeHql("fd_hierarchy_id", fdHierarchyIds));
		return this.baseDao.getHibernateSession().createNativeQuery(sb.toString()).list();
	}

	@Override
	public List addressList(RequestContext xmlContext) throws Exception {
		String fdCategoryId = xmlContext.getParameter("fdCategoryId");
		String fdDate = xmlContext.getParameter("fdDate");
		if (StringUtil.isNull(fdDate)) {
			fdDate = new Date().getTime() + "";
		}
		SysAttendCategory category= CategoryUtil.getCategoryById(fdCategoryId);
		HQLInfo hqlInfo1 = new HQLInfo();
		StringBuilder whereTemp=new StringBuilder( "sysAttendMain.docCreateTime>=:beginTime and sysAttendMain.docCreateTime<:endTime and sysAttendMain.fdStatus>0 and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)" );
		if(category ==null){
			whereTemp.append(" and sysAttendMain.fdCategory.fdId=:categoryId ");
		}else{
			whereTemp.append(" and sysAttendMain.fdHisCategory.fdId=:categoryId ");
		}
		hqlInfo1.setWhereBlock(whereTemp.toString());
		hqlInfo1.setParameter("categoryId", fdCategoryId);
		hqlInfo1.setSelectBlock("distinct sysAttendMain.docCreator.fdId");

		hqlInfo1.setParameter("beginTime",
				AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 0));
		hqlInfo1.setParameter("endTime",
				AttendUtil.getDate(new Date(Long.valueOf(fdDate)), 1));
		List signedList = sysAttendMainService.findValue(hqlInfo1);

		String whereBlock;
		HQLInfo hqlInfo = new HQLInfo();
		whereBlock = HQLUtil.buildLogicIN("sysOrgElement.fdId ", signedList);
		// 组织类型
		int orgType = ORG_TYPE_PERSON;
		// 构建组织架构类型where语句
		whereBlock = SysOrgHQLUtil
				.buildWhereBlock(
						(orgType | ORG_TYPE_ORGORDEPT)
								& (ORG_TYPE_ALLORG | ORG_FLAG_AVAILABLEALL
										| ORG_FLAG_BUSINESSALL),
						whereBlock, "sysOrgElement");
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(
				"sysOrgElement.fdOrgType desc,sysOrgElement.fdNamePinYin,sysOrgElement.fdOrder");
		hqlInfo.setAuthCheckType("DIALOG_READER");
		// 查询结果
		List<SysOrgElement> elemList = sysOrgElementService.findList(hqlInfo);
		List personList = new ArrayList();
		for (SysOrgElement org : elemList) {
			personList.add(formatElement(org, false));
		}
		return personList;
	}

	protected Map formatElement(SysOrgElement orgElem, boolean needDetail) {
		Map tmpMap = new HashMap();
		tmpMap.put("fdId", orgElem.getFdId());
		tmpMap.put("label", orgElem.getFdName());
		tmpMap.put("type", orgElem.getFdOrgType());
		tmpMap.put("order", orgElem.getFdOrder());
		tmpMap.put("pinyin", orgElem.getFdNamePinYin());
		if (orgElem.getFdOrgType().equals(ORG_TYPE_PERSON)
				|| orgElem.getFdOrgType().equals(ORG_TYPE_POST)) {
			tmpMap.put("parentNames", StringUtil.isNotNull(orgElem
					.getFdParentsName("_")) ? orgElem.getFdParentsName("_")
							: "");
		}
		if (needDetail) {
			if (orgElem.getFdParent() != null) {
				tmpMap.put("parentId", orgElem.getFdParent().getFdId());
			}
		}
		if (orgElem.getFdOrgType() == ORG_TYPE_PERSON) {
			tmpMap.put("icon", PersonInfoServiceGetter
					.getPersonHeadimageUrl(orgElem.getFdId()));
		}
		return tmpMap;
	}


	private  ThreadLocal<Integer> countThreadLocal =  new ThreadLocal<Integer>();
	private  ThreadLocal<Map<String,SysOrgElement>> personCacheLocal = new ThreadLocal<Map<String,SysOrgElement>>();

	/**
	 * 入参的ID，解析到最终人员的id列表。
	 * 用于同1线程，不在重复获取
	 */
	private  ThreadLocal<Map<String,List<String>>> personIdsCacheLocal = new ThreadLocal<Map<String,List<String>>>();

	private ISysOrgElementDao sysOrgElementDao;
	private ISysOrgPersonDao personDao;
	private ISysOrgGroupDao groupDao;

	public  ISysOrgElementDao getSysOrgElementDao() {
		if (sysOrgElementDao == null) {
			sysOrgElementDao = (ISysOrgElementDao) SpringBeanUtil.getBean("sysOrgElementDao");
		}
		return sysOrgElementDao;
	}

	public  ISysOrgPersonDao getPersonDao() {
		if (personDao == null) {
			personDao = (ISysOrgPersonDao) SpringBeanUtil.getBean("sysOrgPersonDao");
		}
		return personDao;
	}

	public  ISysOrgGroupDao getSysOrgGroupDao() {
		if (groupDao == null) {
			groupDao = (ISysOrgGroupDao) SpringBeanUtil.getBean("sysOrgGroupDao");
		}
		return groupDao;
	}


	/**
	 * 组织ID，转换成人员ID
	 * 不解析部门下的岗位
	 * @param orgList
	 * @return
	 * @throws Exception
	 */
	@Override
	public  List<String> expandToPersonIds(List orgList) throws Exception {
		if (orgList == null || orgList.isEmpty()) {
			return new ArrayList();
		}
		Session session = getSysOrgElementDao().getHibernateSession();
		List hierarchyIds = new ArrayList();
		List personIds = new ArrayList();
		List results;
		String sql, whereBlock;

		List<SysOrgElement> orgElements = Lists.newArrayList();
		List<String> userIds = Lists.newArrayList();
		for (int i = 0; i < orgList.size(); i++) {
			Object tmpOrg = orgList.get(i);
			if (tmpOrg instanceof String) {
				userIds.add(tmpOrg.toString());
			} else {
				orgElements.add((SysOrgElement) orgList.get(i));
			}
		}
		if (CollectionUtils.isNotEmpty(userIds)) {
			//查询条件分割。每次最多查询1000条
			List<List> groupLists = SysTimeUtil.splitList(userIds, 1000);
			for (List tempUserIds : groupLists) {
				whereBlock = HQLUtil.buildLogicIN("fd_id", tempUserIds);
				//将人员信息一次性查出
				sql = "select fd_id,fd_org_type,fd_hierarchy_id from sys_org_element where " + whereBlock;
				List<Object[]> resultsTemp = session.createNativeQuery(sql).list();
				if (CollectionUtils.isNotEmpty(resultsTemp)) {
					for (Object[] tempEle : resultsTemp) {
						SysOrgElement element = new SysOrgElement();
						element.setFdId(String.valueOf(tempEle[0]));
						element.setFdHierarchyId(String.valueOf(tempEle[2]));
						element.setFdOrgType(Integer.valueOf(String.valueOf(tempEle[1])));
						orgElements.add(element);
					}
				}
			}
		}

		for (SysOrgElement element : orgElements) {
			if (element != null) {
				switch (element.getFdOrgType().intValue()) {
					case ORG_TYPE_ORG:
					case ORG_TYPE_DEPT:
						hierarchyIds.add(element.getFdHierarchyId());
						break;
					case ORG_TYPE_PERSON:
						if (!personIds.contains(element.getFdId())) {
							personIds.add(element.getFdId());
						}
						break;
				}
			}
		}
		// 解释部门
		if (!hierarchyIds.isEmpty()) {
			hierarchyIds = SysOrgHQLUtil.formatHierarchyIdList(hierarchyIds);
			StringBuffer whereBf = new StringBuffer();
			for (int i = 0; i < hierarchyIds.size(); i++) {
				whereBf.append(" or fd_hierarchy_id like '").append(
						hierarchyIds.get(i)).append("%' and fd_is_available = " + SysOrgHQLUtil.toBooleanValueString(true) + " ");
			}
			// 加上fd_is_available=1条件，以兼容有些数据迁移同步过程中层级id没置空的情况
			whereBlock = "(" + whereBf.substring(4) + ")";
			sql = "select fd_id from sys_org_element where fd_org_type="  + ORG_TYPE_PERSON + " and " + whereBlock;
			if (logger.isDebugEnabled()) {
				logger.debug("部门解释个人：" + sql);
			}
			addQueryResultToList(session, sql, personIds);
		}
		return personIds;
	}

	/**
	 * 记录调用次数
	 */
	private  void logCount(List<String> orgList) {
		if(logger.isDebugEnabled()) {
			Integer count = countThreadLocal.get();
			if(count == null){
				countThreadLocal.set(Integer.valueOf(1));
				count = countThreadLocal.get();
			}
			count++;
			countThreadLocal.set(count);
			logger.debug("扩展人员,次数{}" , count);
			logger.debug("扩展人员,入参" , orgList);
		}
	}
	/**
	 * 释放数据库连接资源
	 */
	@Override
	public  void release() {
		personCacheLocal.remove();
		personCacheLocal.set(null);

		countThreadLocal.remove();
		countThreadLocal.set(null);

		personIdsCacheLocal.remove();
		personIdsCacheLocal.set(null);
	}
	/**
	 * 获取人员对象
	 * 线程级缓存来处理。调用方需要手动清除缓存
	 * @param personIds 只接收人员ID
	 * @return 返回对应组织下面所有的 人员对象(简易对象，自己new出来的对象)
	 * @throws Exception
	 */
	@Override
	public  List<SysOrgElement> expandToPersonSimple(List<String> personIds) throws Exception {
		List<SysOrgElement> rtnList = new ArrayList();
		if(CollectionUtils.isNotEmpty(personIds)){
			//日志Debug记录调用次数
			logCount(personIds);
			Map<String,SysOrgElement> cacheInfo = personCacheLocal.get();
			if(cacheInfo ==null){
				cacheInfo =new HashMap<>(personIds.size());
			} else {
				//如果缓存数据不为空，则取缓存中的数据，缓存中数据找到了，则把对象添加到返回结果并且查询人员剔除。
				Iterator<String> itr = personIds.iterator();
				while (itr.hasNext()) {
					String personId =itr.next();
					SysOrgElement orgElement = cacheInfo.get(personId);
					if(orgElement !=null){
						rtnList.add(orgElement);
						itr.remove();
					}
				}
			}
			int beginIndex = 0, endIndex = 0;
			while (endIndex < personIds.size()) {
				endIndex = beginIndex + 2000;
				if (endIndex > personIds.size()) {
					endIndex = personIds.size();
				}
				String alias ="sysOrgPerson";
				List<String> subList = personIds.subList(beginIndex, endIndex);
				HQLWrapper hqlWrapper = HQLUtil.buildPreparedLogicIN("fdId",
						alias, subList);
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(hqlWrapper.getHql());
				hqlInfo.setParameter(hqlWrapper.getParameterList());
				//局部查询个别属性字段
				hqlInfo.setSelectBlock("sysOrgPerson.fdId,sysOrgPerson.fdHierarchyId,sysOrgPerson.fdIsAvailable,sysOrgPerson.fdPreDeptId");
				List<Object[]> eleList = getPersonDao().findValue(hqlInfo);
				for (Object[] params : eleList) {
					SysOrgElement orgEle = new SysOrgElement();
					orgEle.setFdId(String.valueOf(params[0]));
					orgEle.setFdHierarchyId(String.valueOf(params[1]));
					orgEle.setFdPreDeptId(params[3] == null ? null : String.valueOf(params[3]));
					//展开为人员。默认为人员类型
					orgEle.setFdOrgType(ORG_TYPE_PERSON);
					Object fdIsAvailableObj = params[2];
					if (fdIsAvailableObj != null) {
						if (fdIsAvailableObj instanceof Boolean) {
							orgEle.setFdIsAvailable((Boolean) fdIsAvailableObj);
						} else {
							orgEle.setFdIsAvailable("0".equals(fdIsAvailableObj.toString()) ? false : true);
						}
					}
					cacheInfo.put(orgEle.getFdId(),orgEle);
					rtnList.add(orgEle);
				}
				beginIndex += 2000;
			}
		}
		return rtnList;
	}
	/**
	 * 获取人员对象
	 * @param orgList
	 * @return 返回对应组织下面所有的 人员对象
	 * @throws Exception
	 */
	@Override
	public  List<SysOrgElement> expandToPerson(List orgList) throws Exception {
		List<SysOrgElement> rtnList = new ArrayList();
		List searchObj=Lists.newArrayList();
		for (Object obj:orgList ) {
			//如果传进来的参数 直接是人员对象，则不展开
			if(obj instanceof String){
				searchObj.add(obj);
			}else{
				SysOrgElement orgElement= (SysOrgElement) obj;
				if(orgElement !=null && ORG_TYPE_PERSON ==orgElement.getFdOrgType()){
					rtnList.add(orgElement);
				}else{
					searchObj.add(orgElement);
				}
			}
		}
		List<String> personIds = expandToPersonIds(searchObj);
		if (!personIds.isEmpty()) {
			rtnList.addAll(getSysOrgElementById(personIds));
		}
		return rtnList;
	}

	/**
	 * 根据人员id获取人员对象
	 * @param personIds 人员id列表
	 * @throws Exception
	 */
	@Override
	public  List<SysOrgElement> getSysOrgElementById(List<String> personIds) throws Exception {
		List<SysOrgElement> rtnList = new ArrayList();
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
			//局部查询个别属性字段
			hqlInfo.setSelectBlock("sysOrgPerson.fdId,sysOrgPerson.fdHierarchyId,sysOrgPerson.fdIsAvailable,sysOrgPerson.fdPreDeptId,sysOrgPerson.fdName");
			List<Object[]> eleList = getPersonDao().findValue(hqlInfo);
			for (Object[] params : eleList) {
				SysOrgElement orgEle = new SysOrgElement();
				orgEle.setFdName(String.valueOf(params[4]));
				orgEle.setFdId(String.valueOf(params[0]));
				orgEle.setFdHierarchyId(String.valueOf(params[1]));
				orgEle.setFdPreDeptId(params[3] == null ? null : String.valueOf(params[3]));
				//展开为人员。默认为人员类型
				orgEle.setFdOrgType(ORG_TYPE_PERSON);
				Object fdIsAvailableObj = params[2];
				if (fdIsAvailableObj != null) {
					if (fdIsAvailableObj instanceof Boolean) {
						orgEle.setFdIsAvailable((Boolean) fdIsAvailableObj);
					} else {
						orgEle.setFdIsAvailable("0".equals(fdIsAvailableObj.toString()) ? false : true);
					}
				}
				rtnList.add(orgEle);
			}
			beginIndex += 2000;
		}
		return rtnList;
	}

	private  void addQueryResultToList(Session session, String sql, List list) {
		ArrayUtil.concatTwoList(session.createNativeQuery(sql).list(), list);
	}
}
