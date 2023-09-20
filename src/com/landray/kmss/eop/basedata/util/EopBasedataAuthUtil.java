package com.landray.kmss.eop.basedata.util;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.eop.basedata.service.IEopBasedataCostCenterService;
import com.landray.kmss.eop.basedata.service.IEopBasedataProjectService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
/**
 * 实现和财务档案权限相关的类
 * @author XIEXX
 *
 */
public class EopBasedataAuthUtil{
	
	public static IEopBasedataCompanyService eopBasedataCompanyService;

	public static IEopBasedataCompanyService getEopBasedataCompanyService() {
		if(eopBasedataCompanyService==null){
			eopBasedataCompanyService=(IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
		}
		return eopBasedataCompanyService;
	}
	
	public static IEopBasedataCostCenterService eopBasedataCostCenterService;
	
	public static IEopBasedataCostCenterService getEopBasedataCostCenterService() {
		if(eopBasedataCostCenterService==null){
			eopBasedataCostCenterService=(IEopBasedataCostCenterService) SpringBeanUtil.getBean("eopBasedataCostCenterService");
		}
		return eopBasedataCostCenterService;
	}
	
	public static IEopBasedataProjectService eopBasedataProjectService;
	
	public static IEopBasedataProjectService getEopBasedataProjectService() {
		if(eopBasedataProjectService==null){
			eopBasedataProjectService=(IEopBasedataProjectService) SpringBeanUtil.getBean("eopBasedataProjectService");
		}
		return eopBasedataProjectService;
	}
	
	public static ISysOrgCoreService sysOrgCoreService;
	
	public static ISysOrgCoreService getSysOrgCoreService() {
		if(sysOrgCoreService==null){
			sysOrgCoreService=(ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
		}
		return sysOrgCoreService;
	}
	/**
	 * 获取公司下的财务人员列表，若公司为空，则查找所有公司财务人员
	 * @return
	 * @throws Exception
	 */
	public static List getFinanceSaffList(String fdCompanyId) throws Exception{
		List staffList=new ArrayList<>();
		HQLInfo hqlInfo=new HQLInfo();
		String whereBlock="eopBasedataCompany.fdIsAvailable=:fdIsAvailable";
		if(StringUtil.isNotNull(fdCompanyId)){
			whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(whereBlock);
		List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findList(hqlInfo);
		for(EopBasedataCompany company:companyList){
			ArrayUtil.concatTwoList(company.getFdFinancialStaff(), staffList);
		}
		return getSysOrgCoreService().expandToPerson(staffList);
	}
	/**
	 * 获取公司下的财务管理员列表，若公司为空，则查找所有公司财务管理员
	 * @return
	 * @throws Exception
	 */
	public static List getFinanceManagerList(String fdCompanyId) throws Exception{
		List managerList=new ArrayList<>();
		HQLInfo hqlInfo=new HQLInfo();
		String whereBlock="eopBasedataCompany.fdIsAvailable=:fdIsAvailable";
		if(StringUtil.isNotNull(fdCompanyId)){
			whereBlock=StringUtil.linkString(whereBlock, " and ", "eopBasedataCompany.fdId=:fdCompanyId");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(whereBlock);
		List<EopBasedataCompany> companyList=getEopBasedataCompanyService().findList(hqlInfo);
		for(EopBasedataCompany company:companyList){
			ArrayUtil.concatTwoList(company.getFdFinancialManager(), managerList);
		}
		return getSysOrgCoreService().expandToPerson(managerList);
	}
	/**
	 * 获取公司下的财务人员和财务管理员列表，若公司为空，则查找所有公司财务人员和财务管理员
	 * @return
	 * @throws Exception
	 */
	public static List getFinanceStaffAndManagerList(String fdCompanyId) throws Exception{
		List rtnList=getFinanceSaffList(fdCompanyId);
		ArrayUtil.concatTwoList(getFinanceManagerList(fdCompanyId), rtnList);
		return rtnList;
	}
	/**
	 * 判断当前登录用户是否为财务人员，传公司则判断是否为该公司财务人员，不传，则判断其是否为财务人员，不区分公司
	 * @return
	 * @throws Exception
	 */
	public static boolean isStaff(String fdCompanyId) throws Exception{
		SysOrgElement user=UserUtil.getUser();
		return getFinanceSaffList(fdCompanyId).contains(user);
	}
	/**
	 * 判断当前登录用户是否为财务管理员，传公司则判断是否为该公司财务管理员，不传，则判断其是否为财务管理员，不区分公司
	 * @return
	 * @throws Exception
	 */
	public static boolean isManager(String fdCompanyId) throws Exception{
		SysOrgElement user=UserUtil.getUser();
		return getFinanceManagerList(fdCompanyId).contains(user);
	}
	/**
	 * 判断当前登录用户是否为财务管理员或者财务人员，传公司则判断是否为该公司财务管理员或者财务人员，不传，则判断其是否为财务管理员或者财务人员，不区分公司
	 * @return
	 * @throws Exception
	 */
	public static boolean isManagerOrStaff(String fdCompanyId) throws Exception{
		return isStaff(fdCompanyId)||isManager(fdCompanyId);
	}
	
	/**
	 * 返回当前登录人为公司财务人员、财务管理员的公司
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCompanyListAuth(String fdUserId) throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("eopBasedataCompany.fdId");
		hqlInfo.setFromBlock("EopBasedataCompany eopBasedataCompany");
		hqlInfo.setJoinBlock(" left join eopBasedataCompany.fdFinancialStaff staff left join  eopBasedataCompany.fdFinancialManager manager ");
		StringBuilder where=new StringBuilder();
		where.append("(staff.fdId=:userId or manager.fdId=:userId)");
		hqlInfo.setParameter("userId", fdUserId);
		hqlInfo.setWhereBlock(where.toString());
		return getEopBasedataCompanyService().findList(hqlInfo);
	}
	/**
	 * 返回当前登录人为公司财务人员、财务管理员的公司(包含财务人员，财务管理员为部门或岗位的情况)
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCompanyListAuthByOrg() throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("eopBasedataCompany.fdId");
		hqlInfo.setFromBlock("EopBasedataCompany eopBasedataCompany");
		hqlInfo.setJoinBlock(" left join eopBasedataCompany.fdFinancialStaff staff left join  eopBasedataCompany.fdFinancialManager manager ");
		StringBuilder where=new StringBuilder();
		where.append("(staff.fdId in (:myFlowIds) or manager.fdId in (:myFlowIds))");
		hqlInfo.setParameter("myFlowIds", UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds());
		hqlInfo.setWhereBlock(where.toString());
		return getEopBasedataCompanyService().findList(hqlInfo);
	}
	
	/**
	 * 返回当前登录人为成本中心负责人，预算管理员
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCostCenterAuth(String fdCompanyId) throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("eopBasedataCostCenter.fdId");
		hqlInfo.setFromBlock("EopBasedataCostCenter eopBasedataCostCenter");
		StringBuilder joinBlock=new StringBuilder();
		joinBlock.append(" left join eopBasedataCostCenter.fdCompanyList company ");
		joinBlock.append(" left join eopBasedataCostCenter.fdFirstCharger first left join  eopBasedataCostCenter.fdSecondCharger second ");
		joinBlock.append(" left join eopBasedataCostCenter.fdBudgetManager manager left join eopBasedataCostCenter.fdManager manager_ ");
		hqlInfo.setJoinBlock(joinBlock.toString());
		StringBuilder where=new StringBuilder();
		where.append("(first.fdId=:userId or second.fdId=:userId or manager.fdId=:userId or manager_.fdId=:userId)");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		if(StringUtil.isNotNull(fdCompanyId)){
			where.append(" and (company.fdId=:fdCompanyId or company is null)");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		hqlInfo.setWhereBlock(where.toString());
		return getEopBasedataCostCenterService().findList(hqlInfo);
	}
	
	/**
	 * 返回当前登录人为项目经理的项目
	 * @param fdCompanyId
	 * @return
	 * @throws Exception
	 */
	public static List<String> getProjectAuth(String fdCompanyId) throws Exception{
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setSelectBlock("eopBasedataProject.fdId");
		hqlInfo.setFromBlock("EopBasedataProject eopBasedataProject");
		hqlInfo.setJoinBlock(" left join eopBasedataProject.fdCompanyList company left join eopBasedataProject.fdProjectManager manager");
		StringBuilder where=new StringBuilder();
		where.append(" manager.fdId=:userId and (eopBasedataProject.fdType=:fdType or eopBasedataProject.fdType=:fdType3)");
		hqlInfo.setParameter("userId", UserUtil.getUser().getFdId());
		hqlInfo.setParameter("fdType", EopBasedataConstant.FSSC_BASE_PROJECT_1);
		hqlInfo.setParameter("fdType3", EopBasedataConstant.FSSC_BASE_PROJECT_3);
		if(StringUtil.isNotNull(fdCompanyId)){
			where.append(" and (company.fdId=:fdCompanyId or company is null)");
			hqlInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		hqlInfo.setWhereBlock(where.toString());
		return getEopBasedataProjectService().findList(hqlInfo);
	}
}
