package com.landray.kmss.hr.function;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.fssc.budget.model.FsscBudgetData;
import com.landray.kmss.fssc.budget.service.IFsscBudgetDataService;
import com.landray.kmss.hr.staff.model.HrNumberConfig;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.comparator.ChinesePinyinComparator;


public class HrFunctions {
	public static String getPinyinStringWithDefaultFormat(Object str) throws Exception {
		String result="";
		if(str instanceof String){
			result=ChinesePinyinComparator.getPinyinStringWithDefaultFormat((String)str);
		}
		return result;
	}
	private static ISysOrgElementService sysOrgElementService;

	public static ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");

		}
		return sysOrgElementService;
	}

	private static ISysOrgPersonService sysOrgPersonService;

	public static ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}
	public static String getUserNameByPinyin(Object str) throws Exception {
		String fdLoginName="";
		if(str instanceof String){
			fdLoginName=(String)str;
			//去找组织架构中查看是否重复名称
			List<SysOrgPerson> list=getSysOrgPersonService().findList("sysOrgPerson.fdLoginName like '" + str + "%'",null);
			if(list!=null&list.size()>0){
				int num=0;
				for (SysOrgPerson sysOrgPerson : list) {
					String name=sysOrgPerson.getFdLoginName();
					String endStr=name.substring(name.indexOf(fdLoginName)+fdLoginName.length(),name.length());
					if(StringUtil.isNotNull(endStr)){//如果后面不是数字类型那么不加1
						try {
							Integer.valueOf(endStr);
						} catch (Exception e) {
							continue;
						}
					}
					num++;
				}
				fdLoginName+=num+"";
			}
		}
		return fdLoginName;
	}

	//获取组织架构自定义字段的值
	public static Object getOrgCustomByKey(Object org,Object key) throws Exception {
		Object result=null;
		if(org instanceof SysOrgElement){
			SysOrgElement fdOrg=(SysOrgElement)org;
			Map<String, Object> map=fdOrg.getCustomPropMap();
			result=map.get(key);
			if(result==null){
				fdOrg=fdOrg.getFdParent();
				if(fdOrg==null){
					return result;
				}
				map=fdOrg.getCustomPropMap();
				result=map.get(key);
			}
		}
		return result;
	}

	//获取hr当前人员需要使用的编号
	public static synchronized String getHrNumber(Object number) throws Exception {
		String fdhrNumber="";
		if(number==null){
			HrNumberConfig hrNumberConfig = new HrNumberConfig();
			fdhrNumber=hrNumberConfig.getFdHrNumber();//获取当前人员
		}else if(number instanceof String){
			fdhrNumber=(String)number;
			if(StringUtil.isNull(fdhrNumber)){//说明是空值可以取值
				HrNumberConfig hrNumberConfig = new HrNumberConfig();
				fdhrNumber=hrNumberConfig.getFdHrNumber();//获取当前人员
			}
		}
		return fdhrNumber;
	}


	private static IFsscBudgetDataService fsscBudgetDataService;

	public static IFsscBudgetDataService getFsscBudgetDataService() {
		if (fsscBudgetDataService == null) {
			fsscBudgetDataService = (IFsscBudgetDataService) SpringBeanUtil.getBean("fsscBudgetDataService");
		}
		return fsscBudgetDataService;
	}
	//获取指定人员有预算的上级部门
	public static Object getDeptHaveBudget(Object org) throws Exception {
		String fdParentsName="无预算部门";
		boolean haveBudget=false;
		if(org instanceof SysOrgElement){
			SysOrgElement element=(SysOrgElement)org;
			if (element != null){
				List<FsscBudgetData> budgetDatas= getFsscBudgetDataService().findByFdDept(element);
				if(budgetDatas!=null&&budgetDatas.size()>0){
					fdParentsName=element.getFdName();
				}else{
					SysOrgElement parent=element.getFdParent();
					while (parent != null&&!haveBudget) {
						budgetDatas= getFsscBudgetDataService().findByFdDept(parent);
						if(budgetDatas!=null&&budgetDatas.size()>0){
							fdParentsName=parent.getFdName();
							haveBudget=true;
						}else{
							parent = parent.getFdParent();
						}
					}
				}
			}
		}
		return fdParentsName;
	}

	//获取指定人员有预算的上级部门
	public static Object getNumByList(Object obj) throws Exception {
		Double allScore=0d;
		if(obj instanceof List){
			List<Double> list=(List<Double>) obj;
			for (Double score : list) {
				allScore+=score;
			}
		}

		return allScore;
	}
//自定义方法.获取成本中心对应行政组织
	public static Object getPersonsByCostCenter(Object obj) throws Exception {
		List<SysOrgElement> result=new ArrayList<>();

		if(obj instanceof List){
			List list=(List)obj;
			for (Object obj2 : list) {
				result.addAll(getOneCostCenterPerson(obj2));
			}
		}else{
			result.addAll(getOneCostCenterPerson(obj));
		}
		//去重
		HashSet<SysOrgElement> set = new HashSet<>(result);
		result=new ArrayList<>(set);
		return result;
	}

	private static List getOneCostCenterPerson(Object obj){
		List<SysOrgElement> result=new ArrayList<>();
		if(obj instanceof EopBasedataCostCenter){
			EopBasedataCostCenter costCenter=(EopBasedataCostCenter)obj;
			result=costCenter.getFdEkpOrg();
//			List<SysOrgElement> fdEkpOrg=costCenter.getFdEkpOrg();
//			if(fdEkpOrg!=null&&fdEkpOrg.size()>0){
//				for (SysOrgElement sysOrgElement : fdEkpOrg) {
//					// 取部门领导
//					SysOrgElement deptLeaderOrg = sysOrgElement.getHbmThisLeader();
//					if (deptLeaderOrg == null) { // 如果本部门领导为空，则取上级领导
//						deptLeaderOrg = sysOrgElement.getHbmSuperLeader();
//					}
//					result.add(deptLeaderOrg);
//				}
//			}
		}
		return result;
	}
	
	/**
	 * 判断对象某个字段是否等于某个值
	 * @param obj 对象可以是list
	 * @param field 字段名
	 * @param str 比较的字符串
	 * @return
	 */
	public static boolean isContainStr(Object obj,Object field,Object str){
		if(obj instanceof List){
			List obs=(List)obj;
			for (Object object : obs) {
				Object value=getValueByKey(object, field);
				if(str.equals(value)){
					return true;
				}
			}
		}else{
			Object value=getValueByKey(obj, field);
			if(str.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	private static Object getValueByKey(Object obj, Object field){
		// 1.根据属性名称就可以获取其get方法
		String getMethodName = "get"
				+ field.toString().substring(0, 1).toUpperCase()
				+ field.toString().substring(1);
		//2.获取方法对象
		Class c = obj.getClass();
		try {
			//get方法都是public的且无参数
			Method m= c.getMethod(getMethodName);
			//3 通过方法的反射操作方法
			Object value = m.invoke(obj);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * 获取角色线对应人员
	 * @param obj RoleConfObj
	 * @param field 字段名
	 * @param str 比较的字符串
	 * @return
	 * @throws Exception 
	 */
	public static List getSysOrgRoleTest(Object obj,String confName, String roleName) throws Exception{
		List<SysOrgElement> result=new ArrayList<>();
		if(obj instanceof List){
			List list=(List)obj;
			for (Object object : list) {
				if(object instanceof SysOrgElement){
					result.addAll(parseSysOrgRole((SysOrgElement)object, confName, roleName));
				}
			}
		}else if(obj instanceof SysOrgElement){
			result=parseSysOrgRole((SysOrgElement)obj, confName, roleName);
		}
		Set<SysOrgElement> setData=new HashSet<>(result);
		result=new ArrayList<>(setData);
		return result;
	}
	
	/**
	 * 解析角色线对应人员
	 * @method parseSysOrgRole Create on 2020年7月24日 下午4:20:31 
	 * @author hexy
	 * @param element
	 * @param confName
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	public static List parseSysOrgRole(SysOrgElement element, String confName, String roleName) throws Exception {
		if (null == element) {
			return new ArrayList();
		}
		SysOrgElement role = getSysOrgCoreService().getRoleByName(confName, roleName);
		if (null == role) {
			return new ArrayList();
		}
		return getSysOrgCoreService().parseSysOrgRole(role, element);
	}
	
	private static ISysOrgCoreService sysOrgCoreService;
	public static ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
			sysOrgCoreService = (ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
}
