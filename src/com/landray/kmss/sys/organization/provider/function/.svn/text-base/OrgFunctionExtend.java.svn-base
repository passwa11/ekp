package com.landray.kmss.sys.organization.provider.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.formula.provider.function.OtherFunction;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgGroup;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ListSortUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class OrgFunctionExtend {

	private static ISysOrgCoreService sysOrgCoreService = null;

	private static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}

	/**
	 * 获取当前用户职级
	 * 
	 * @throws Exception
	 */
	public static int getCurrentUserStaffingLevel() throws Exception {
		return getSysOrgCoreService().getStaffingLevelValue(getCurrentUser());
	}

	/**
	 * 获取当前用户职务名称
	 * 
	 * @throws Exception
	 */
	public static String getCurrentUserStaffingLevelName() throws Exception {
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getSysOrgCoreService()
				.getStaffingLevel(getCurrentUser());
		if (sysOrganizationStaffingLevel == null) {
			return "";
		} else {
			return sysOrganizationStaffingLevel.getFdName();
		}
	}

	public static SysOrgPerson getCurrentUser() throws Exception {
		return (SysOrgPerson) getSysOrgCoreService().format(UserUtil.getUser());
	}

	/**
	 * 获取指定用户职级
	 * 
	 * @throws Exception
	 */
	public static int getUserStaffingLevel(SysOrgElement element)
			throws Exception {
		if (element == null) {
			element = getCurrentUser();
		}
		return getSysOrgCoreService().getStaffingLevelValue(
				(SysOrgPerson) element);
	}

	/**
	 * 获取指定用户职务名称
	 * 
	 * @throws Exception
	 */
	public static String getUserStaffingLevelName(SysOrgElement element)
			throws Exception {
		if (element == null) {
			element = getCurrentUser();
		}
		SysOrganizationStaffingLevel sysOrganizationStaffingLevel = getSysOrgCoreService()
				.getStaffingLevel((SysOrgPerson) element);
		if (sysOrganizationStaffingLevel == null) {
			return "";
		} else {
			return sysOrganizationStaffingLevel.getFdName();
		}
	}
	
	/**
	 * 根据群组名称获取群组成员
	 * 
	 * @param fdName
	 * @return
	 * @throws Exception
	 */
	public static List getPersonByGroupName(String fdName) throws Exception {
		List rtnList = getSysOrgCoreService().findByName(fdName,
				SysOrgConstant.ORG_TYPE_GROUP);
		if (rtnList.isEmpty()) {
			return new ArrayList();
		}
		SysOrgElement element = (SysOrgElement) rtnList.get(0);
		SysOrgGroup group = (SysOrgGroup) getSysOrgCoreService()
				.format(element);
		List list = getSysOrgCoreService().expandToPerson(group.getFdMembers());
		if(!list.isEmpty()) { 
			String orderGroupPerson =new SysOrganizationConfig().getOrderGroupPerson();
			if("true".equalsIgnoreCase(orderGroupPerson)) {
				//按人员名字排序
				ListSortUtil.sort(list, "fdName",false);
				//按人员序号排序
				ListSortUtil.sort(list, "fdOrder",false); 
			}
		} 
		return list;
	}
	
	/**
	 * 根据岗位获取岗位成员
	 * 
	 * @param elements
	 *            岗位名或岗位对象，可为单值也可为列表
	 * @return
	 * @throws Exception
	 */
	public static List getPersonByPostName(Object elements) throws Exception {
		List<Object> list = OtherFunction.toList(elements);
		if(list.isEmpty()){
			return new ArrayList();
		}
		List<SysOrgElement> rtnList = new ArrayList<SysOrgElement>();
		for (int i = 0; i < list.size(); i++) {
			Object element = list.get(i);
			if (element == null) {
                continue;
            }
			if (element instanceof SysOrgElement) {
				SysOrgElement baseElement  = (SysOrgElement) element;
				if (baseElement.getFdOrgType() == null) {
					continue;
				}
				if (baseElement.getFdOrgType().intValue() == SysOrgConstant.ORG_TYPE_PERSON) {
					SysOrgPerson person = (SysOrgPerson) getSysOrgCoreService().format(baseElement);
					rtnList.add(person);
				} else if(baseElement.getFdOrgType().intValue() == SysOrgConstant.ORG_TYPE_POST){
					SysOrgPost post = (SysOrgPost) getSysOrgCoreService().format(baseElement);
					 rtnList.addAll(getSysOrgCoreService().expandToPerson(post.getFdPersons()));
				}
			} else if (element instanceof String) {
				rtnList.addAll(getPersonByPostName(element));
			}
		}
		ArrayUtil.unique(rtnList);
		if(!ArrayUtil.isEmpty(rtnList)) {
			//按人员序号排序，相同序号的再按名称拼音进行排序（升序）
			SysOrgUtil.sortByOrderAndName(rtnList);
		}
		return rtnList;
	}

	/**
	 * 根据岗位名称获取岗位成员
	 * 
	 * @param fdName
	 * @return
	 * @throws Exception
	 */
	public static List getPersonByPostName(String fdName) throws Exception {
		List rtnList = getSysOrgCoreService().findByName(fdName,
				SysOrgConstant.ORG_TYPE_POST);
		if (rtnList.isEmpty()) {
			return new ArrayList();
		}
		SysOrgElement element = (SysOrgElement) rtnList.get(0);
		SysOrgPost post = (SysOrgPost) getSysOrgCoreService().format(element);
		List list = getSysOrgCoreService().expandToPerson(post.getFdPersons());
		
		if(!ArrayUtil.isEmpty(list)) {
			//按人员序号排序，相同序号的再按名称拼音进行排序（升序）
			SysOrgUtil.sortByOrderAndName(list);
		}
		
		return list;
	}

	/**
	 * 获取指定用户上N级的部门名称
	 * 
	 * @param element,level
	 * @return
	 * @throws Exception
	 */
	public static String getParentNameByLevel(SysOrgElement element, int level)
			throws Exception {
		String rtnName = "";
		if (element == null) {
			element = getCurrentUser();
		}
		if (level < 1) {
			rtnName = element.getFdName();
		} else {
			SysOrgElement parent = element.getFdParent();
			int i = 1;
			while (i < level && parent != null && parent.getFdParent()!=null) {
				parent = parent.getFdParent();
				i++;
			}
			rtnName = parent == null ? element.getFdName() : parent.getFdName();
		}
		return rtnName;
	}

	/**
	 * 获取组织架构对象的直线领导
	 * 
	 * @param org
	 * @param n
	 * @param self
	 * @return
	 * @throws Exception
	 */
	public static List<SysOrgElement> getElementDescLeader(Object org, int n,
			Object delOrg) throws Exception {
		return listElementLeader(org, n, delOrg, 0);
	}

	/**
	 * 获取组织架构对象的领导
	 * 
	 * @param org
	 * @param n
	 * @param self
	 * @return
	 * @throws Exception
	 */
	public static List<SysOrgElement> getElementLeader(Object org, int n,
			Object delOrg) throws Exception {
		return listElementLeader(org, n, delOrg, -1);
	}

	/**
	 * 获取组织架构对象的领导具体实现方法
	 * 
	 * @param org
	 *            组织架构对象
	 * @param n
	 *            领导层级
	 * @param delOrg
	 *            不参与节点审批的人员
	 * @param symb
	 *            0为直线领导，-1为领导
	 * @return
	 * @throws Exception
	 */
	private static List<SysOrgElement> listElementLeader(Object org, int n,
			Object delOrg, int symb) throws Exception {
		if (n < 1) {
			throw new Exception("领导层级不能小于1");
		}
		// Set为了去重，如果不是多选地址本用不上，可先设置为空
		Set<SysOrgElement> setLeader = null;
		// 经过测试只能返回list不能返回Set，所以listLeader为最后返回对象
		List<SysOrgElement> listLeader = new ArrayList<SysOrgElement>();
		// 获取n-1（因为一级直线领导是0，为了方便用户理解填写，所以内部处理减一）后异或，如果是领导的话变成负数，负数为从上往下拿领导
		int level = (n - 1) ^ symb;
		// 判断地址本是多选还是单选
		if (org instanceof List) {
			// LinkedHashSet具有HashSet的查询速度，而且内部使用链表维护元素的顺序（插入的顺序），
			// 因为这里只需要增加和删除，链表性能应该更好，且可能按照选择的顺序来审批，所以需要有序的
			setLeader = new LinkedHashSet<SysOrgElement>();
			List<SysOrgElement> listOrg = (List<SysOrgElement>) org;
			for (SysOrgElement soe : listOrg) {
				SysOrgElement leader = soe.getLeader(level);
				if (leader != null){
					setLeader.add(leader);
				}
			}

			delOrgApprove(setLeader, delOrg);

		} else if (org instanceof SysOrgElement) {
			SysOrgElement leader = ((SysOrgElement) org).getLeader(level);
			if (leader != null){
				if (delOrg instanceof List) {
					if (((List<SysOrgElement>) delOrg)
							.indexOf(leader) != -1) {
						return listLeader;
					}
				} else if (delOrg instanceof SysOrgElement) {
					if (leader.getFdId().equals(
							((SysOrgElement) delOrg).getFdId())) {
						return listLeader;
					}
				}
				// 如果是地址本直接拿领导放到list就可以了，没必要浪费内存放set
				listLeader.add(leader);
			}
		} else {
			// org为空的情况
			return listLeader;
		}
		if (setLeader != null) {
			listLeader.addAll(setLeader);
		}
		return listLeader;
	}

	/**
	 * 将需要审批的组织架构列表中移除不需要参与审批的人员
	 * 
	 * @param colLeader
	 *            将要参与审批的人员列表
	 * @param delOrg
	 *            不参与审批的人员
	 * @throws Exception
	 */
	private static void delOrgApprove(Object colLeader, Object delOrg)
			throws Exception {
		if (colLeader instanceof LinkedHashSet) {
			LinkedHashSet<SysOrgElement> setLeader = (LinkedHashSet<SysOrgElement>) colLeader;
			if (delOrg instanceof List) {
				setLeader.removeAll((List<SysOrgElement>) delOrg);
			} else if (delOrg instanceof SysOrgElement) {
				setLeader.remove(delOrg);
			}
		}

	}

	public static Object getPersonDynamicValue(Object elements,
			String fieldName) throws Exception {
		Object rtnValue = "";
		List<Object> list = OtherFunction.toList(elements);
		if (ArrayUtil.isEmpty(list)){
			return rtnValue;
		}
		SysOrgElement element = (SysOrgElement) list.get(0);
		if (element == null || StringUtil.isNull(fieldName)) {
			return rtnValue;
		}
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		if (!baseDao.isExist(SysOrgElement.class.getName(), element.getFdId())) {
			return rtnValue;
		}
		element = getSysOrgCoreService().format(element);
		DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil
				.getDynamicAttributeConfig(ModelUtil.getModelClassName(element));
		List<DynamicAttributeField> enabledFields = dynamicConfig.getEnabledFields();
		for (DynamicAttributeField filed : enabledFields) {
			if (fieldName.equals(filed.getFieldName())) {
				rtnValue = element.getCustomPropMap().get(fieldName);
				if (rtnValue == null) {
					return "";
				}
				
				String displayType = filed.getDisplayType();
				if (rtnValue instanceof Date){
					Date date = (Date) rtnValue;
					if ("date".equals(displayType)){
						rtnValue = DateUtil.convertDateToString(date, DateUtil.PATTERN_DATE);
					}else if ("time".equals(displayType)){
						String timePttern = ResourceUtil.getString("date.format.time");
						rtnValue = DateUtil.convertDateToString(date, timePttern);
					}
				}
				if (!ArrayUtil.isEmpty(filed.getFieldEnums())) {
					rtnValue = filed.getFieldEnum(rtnValue.toString());
				}
				break;
			}
		}
		return rtnValue;
	}
}
