package com.landray.kmss.hr.staff.util;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPrivateChange;
import com.landray.kmss.hr.staff.model.HrStaffPrivateConfig;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffPrivateChangeService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

public class HrStaffPrivateUtil {
	public static IHrStaffPersonInfoService hrStaffPersonInfoService = null ;
	public static IHrStaffPersonInfoService getIHrStaffPersonInfoService(){
		if(hrStaffPersonInfoService==null){
			hrStaffPersonInfoService = ((IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService"));
		}
		return hrStaffPersonInfoService ;
	}
	@SuppressWarnings("unchecked")
	public static Map<String, String> getPrivateConfig() throws Exception {
		Map<String, String> config = ((ISysAppConfigService) SpringBeanUtil
				.getBean("sysAppConfigService"))
				.findByKey(HrStaffPrivateConfig.class.getName());
		return config;
	}

	private static HrStaffPrivateChange getPersonStaffPrivateChange(String fdPersonId)
			throws Exception {
		IHrStaffPrivateChangeService  hrStaffPrivateChangeService =  ((IHrStaffPrivateChangeService) SpringBeanUtil.getBean("hrStaffPrivateChangeService"));
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffPrivateChange.fdPersonId =:fdPersonId");
		hqlInfo.setParameter("fdPersonId", fdPersonId);
		List<HrStaffPrivateChange> list = hrStaffPrivateChangeService.findList(hqlInfo);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public static Boolean isALlBriefPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isBriefPrivate"));
	}

	public static Boolean isAllProjectPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isProjectPrivate"));
	}

	public static Boolean isAllWorkPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isWorkPrivate"));
	}

	public static Boolean isAllEducationPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isEducationPrivate"));
	}
	public static Boolean isAllTrainingPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isTrainingPrivate"));
	}
	public static Boolean isAllBonusPrivate() throws Exception {
		return "1".equals(getPrivateConfig().get("isBonusPrivate"));
	}
	

	public static Boolean isBriefPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsBriefPrivate();
		}
		return isALlBriefPrivate() ||  (flag == null ? false : flag) ;
	}

	public static Boolean isProjectPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsProjectPrivate();
		}
		return isAllProjectPrivate() || (flag == null ? false : flag);
	}

	public static Boolean isWorkPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsWorkPrivate();
		}
		return isAllWorkPrivate() || (flag == null ? false : flag);
	}

	public static Boolean isEducationPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsEducationPrivate();
		}
		return isAllEducationPrivate() || (flag == null ? false : flag);
	}
	public static Boolean isTrainingPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsTrainingPrivate();
		}
		return isAllTrainingPrivate() || (flag == null ? false : flag);
	}
	public static Boolean isBonusPrivate(String fdPersonId) throws Exception {
		Boolean flag = false;
		HrStaffPrivateChange  hrStaffPrivateChange = getPersonStaffPrivateChange(fdPersonId);
		if(hrStaffPrivateChange!=null){
			flag = hrStaffPrivateChange.getIsBonusPrivate();
		}
		return isAllBonusPrivate() || (flag == null ? false : flag);
	}
	
	public static Boolean isSelf(String fdPersonId) throws Exception {
		Boolean flag = false;
		if(fdPersonId.equals(UserUtil.getUser().getFdId())){
			flag =true;
		}
		return flag;
	}
	public static Boolean isExist(String fdPersonId) throws Exception {
		Boolean flag = false;
		IHrStaffPersonInfoService  hrStaffPersonInfoService =  getIHrStaffPersonInfoService();
		Session session = hrStaffPersonInfoService.getBaseDao().getHibernateSession();
		// 查询需要同步的数据
		String selectSql = "select fd_id from hr_staff_person_info  where fd_id = ?";
		List<String> list = session.createNativeQuery(selectSql).setParameter(0,fdPersonId).list();
		if (list.size() > 0) {
			flag = true;
		}else{

			String orgSql = "select fd_id from sys_org_element  where fd_id = ? and  fd_is_business = ?";
			List<String> orglist = session.createNativeQuery(orgSql).setParameter(0,fdPersonId).setParameter(1, Boolean.TRUE).list();
			if(orglist .size()>0){

				HrStaffPersonInfo hrStaffPersonInfo = new HrStaffPersonInfo();
				hrStaffPersonInfo.setFdId(fdPersonId);
				SysOrgPerson sop = new SysOrgPerson();
				sop.setFdId(fdPersonId);
				hrStaffPersonInfo.setFdOrgPerson(sop);
				hrStaffPersonInfo.setFdStatus("official");
				hrStaffPersonInfoService.add(hrStaffPersonInfo);
				flag = true;
			}else{
				flag = true;
			}
		}
		return flag;
	}
}
