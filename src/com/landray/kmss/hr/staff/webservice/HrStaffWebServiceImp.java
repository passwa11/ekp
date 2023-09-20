package com.landray.kmss.hr.staff.webservice;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/hr-staff/hrStaffWebService", method = RequestMethod.POST)
@RestApi(docUrl = "/hr/staff/rest/hr_staff_out_rest_help.jsp", name = "hrStaffWebServiceImp", resourceKey = "hr-staff:hrStaffGetOrg.title")
public class HrStaffWebServiceImp implements IHrStaffWebService,SysOrgConstant,HrStaffWebServiceConstant{
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffWebServiceImp.class);
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/getHrStaffElements", method = RequestMethod.POST)
	public HrStaffOrgResult getHrStaffElements(@RequestBody HrStaffGetOrgContext hrStaffGetOrgContext) throws Exception {
		List<Integer> orgRtnType = new ArrayList<Integer>();
		List<String> orgRtnInfo = new ArrayList<String>();
		HrStaffOrgResult hrStaffOrgResult = new HrStaffOrgResult();
		hrStaffOrgResult.setReturnState(RETURN_CONSTANT_STATUS_NOOPT);
		if (!checkNullIfNecessary(hrStaffGetOrgContext, hrStaffOrgResult, orgRtnType, orgRtnInfo)) {
			return hrStaffOrgResult;
		}
		logger.debug("开始读取人员档案基本信息。");
		
		int count2=hrStaffGetOrgContext.getCount();
		List<HrStaffPersonInfo> hrstaffPersonInfoList = hrStaffPersonInfoService.findList(getHqlByOrgContext(
				hrStaffGetOrgContext));
		JSONArray jsonArr = new JSONArray();
		int count = 0;
		logger.debug("所有人员共计" + hrstaffPersonInfoList.size() + "条。");
		Date fdTimeOfEnterprise = new Date();
		for (int i = 0; i < hrstaffPersonInfoList.size(); i++) {
			HrStaffPersonInfo hrstaffPersonInfo = hrstaffPersonInfoList.get(i);
			JSONObject jsonObj = new JSONObject();
			if ((i + 1) > count2){			
				break;
			}
			jsonObj.put("fdid", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdId())));
			jsonObj.put("fdOrgParentsName", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdOrgParentsName())));
			jsonObj.put("fdStaffingLevel", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdStaffingLevel()))));
			jsonObj.put("fdName", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdName())));
			jsonObj.put("fdSex", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdSex())));
			jsonObj.put("fdDateOfBirth", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdDateOfBirth()))));
			jsonObj.put("fdBirthdayOfYear", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdBirthdayOfYear()))));
			jsonObj.put("fdAge", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdAge()))));
			jsonObj.put("fdStaffNo", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdStaffNo())));
			jsonObj.put("fdIdCard", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdIdCard())));
			jsonObj.put("fdWorkTime", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdWorkTime()))));
			jsonObj.put("fdUninterruptedWorkTime", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdUninterruptedWorkTime())));
			jsonObj.put("fdTimeOfEnterprise", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdTimeOfEnterprise()))));
			jsonObj.put("fdWorkingYears", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdWorkingYears())));
			jsonObj.put("fdTrialExpirationTime", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdTrialExpirationTime()))));
			jsonObj.put("fdEmploymentPeriod", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdEmploymentPeriod()))));
			jsonObj.put("fdStaffType", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdStaffType())));
			jsonObj.put("fdNameUsedBefore", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdNameUsedBefore())));
			jsonObj.put("fdNation", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdNation())));
			jsonObj.put("fdPoliticalLandscape", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdPoliticalLandscape())));
			jsonObj.put("fdDateOfParty", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdDateOfParty()))));
			jsonObj.put("fdHighestEducation", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdHighestEducation())));
			jsonObj.put("fdMaritalStatus", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdMaritalStatus())));
			jsonObj.put("fdHealth", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdHealth())));
			jsonObj.put("fdStature", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdStature()))));
			jsonObj.put("fdWeight", String.valueOf(StringUtil.getString(String.valueOf(hrstaffPersonInfo.getFdWeight()))));
			jsonObj.put("fdLivingPlace", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdLivingPlace())));
			jsonObj.put("fdNativePlace", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdHomeplace())));
			jsonObj.put("fdHomeplace", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdHomeplace())));
			jsonObj.put("fdAccountProperties", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdAccountProperties())));
			jsonObj.put("fdRegisteredResidence", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdRegisteredResidence())));
			jsonObj.put("fdResidencePoliceStation", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdResidencePoliceStation())));
			jsonObj.put("fdMobileNo", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdMobileNo())));
			jsonObj.put("fdEmail", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdEmail())));
			jsonObj.put("fdWorkPhone", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdWorkPhone())));
			jsonObj.put("fdEmergencyContact", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdEmergencyContact())));
			jsonObj.put("fdEmergencyContactPhone", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdEmergencyContactPhone())));
			jsonObj.put("fdRelatedProcess", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdRelatedProcess())));
			jsonObj.put("fdStatus", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdStatus())));
			jsonObj.put("fdHierarchyId", String.valueOf(StringUtil.getString(hrstaffPersonInfo.getFdHierarchyId())));
			count++;
			jsonArr.add(jsonObj);
			fdTimeOfEnterprise = hrstaffPersonInfo.getFdTimeOfEnterprise();
		}
		hrStaffOrgResult.setTimeStamp(fdTimeOfEnterprise.getTime() + "");
		hrStaffOrgResult.setMessage(jsonArr.toJSONString());
		hrStaffOrgResult.setCount(count);
		if(jsonArr!=null && jsonArr.size() != 0){
			hrStaffOrgResult.setReturnState(RETURN_CONSTANT_STATUS_SUCESS);
		}
		
		logger.debug("人员基本信息读取结束。");
		return hrStaffOrgResult;
	}
	private HQLInfo getHqlByOrgContext(HrStaffGetOrgContext hrStaffGetOrgContext) {
		HQLInfo hqlInfo = new HQLInfo();
		Date beginTimeStamp = hrStaffGetOrgContext.getBeginTimeStamp();
		// where
		StringBuffer whereBlock = new StringBuffer();
		//查询在职员工		 
		logger.debug("搜索where语句：" + whereBlock.toString());
		whereBlock.append("hrStaffPersonInfo.fdStatus='official' and hrStaffPersonInfo.fdTimeOfEnterprise!=null");
		if (beginTimeStamp != null) {
			whereBlock.append(" and hrStaffPersonInfo.fdTimeOfEnterprise >= :beginTime");
			hqlInfo.setParameter("beginTime", beginTimeStamp);
		}
		
		hqlInfo.setWhereBlock(whereBlock.toString());
		// order
		hqlInfo.setOrderBy("hrStaffPersonInfo.fdTimeOfEnterprise asc");
		return hqlInfo;
	}

	private boolean checkNullIfNecessary(HrStaffGetOrgContext hrStaffGetOrgContext,
			HrStaffOrgResult hrStaffOrgResult, List<Integer> orgRtnType,
			List<String> orgRtnInfo) {
		if (hrStaffGetOrgContext == null) {
			hrStaffOrgResult.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
			hrStaffOrgResult.setMessage("请求人员档案数据上下文不能为空!");
			logger.debug("请求人员档案数据上下文不能为空!");
			return false;

		}
		if ((hrStaffGetOrgContext instanceof HrStaffGetOrgContext)) {
			if (((HrStaffGetOrgContext) hrStaffGetOrgContext).getCount() <= 0) {
				hrStaffOrgResult.setReturnState(RETURN_CONSTANT_STATUS_FAIL);
				hrStaffOrgResult.setMessage("请求人员档案数据的条目数不能为空!");
				logger.debug("请求人员档案数据的条目数不能为空!");
				return false;
			}
		}
	
		return true;
	}  
}
