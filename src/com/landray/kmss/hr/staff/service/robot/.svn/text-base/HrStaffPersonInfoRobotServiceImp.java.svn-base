package com.landray.kmss.hr.staff.service.robot;

import java.util.*;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.model.*;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareService;
import com.landray.kmss.hr.staff.util.AreasUtil;
import com.landray.kmss.hr.staff.util.CitiesUtil;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.hr.staff.util.ProvinceUtil;
import com.landray.kmss.km.calendar.util.JsonUtil;
import com.landray.kmss.sys.organization.forms.SysOrgPersonForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工信息机器人
 * @author 邓超
 *
 */
public class HrStaffPersonInfoRobotServiceImp extends AbstractRobotNodeServiceImp {
	private Logger logger = LoggerFactory.getLogger(HrStaffPersonInfoRobotServiceImp.class);
	// 员工信息
	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}
	protected IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
		return hrStaffPersonInfoService;
	}
	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService(){
		if(sysOrgElementService == null){
			sysOrgElementService = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService(){
		if(sysOrgPersonService == null){
			sysOrgPersonService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}
	private ISysOrgPostService sysOrgPostService;

	public ISysOrgPostService getSysOrgPostService(){
		if(sysOrgPostService == null){
			sysOrgPostService = (ISysOrgPostService)SpringBeanUtil.getBean("sysOrgPostService");
		}
		return sysOrgPostService;
	}

	private IHrStaffEmolumentWelfareService hrStaffEmolumentWelfareService;

	public IHrStaffEmolumentWelfareService getHrStaffEmolumentWelfareService(){
		if(hrStaffEmolumentWelfareService == null){
			hrStaffEmolumentWelfareService = (IHrStaffEmolumentWelfareService)SpringBeanUtil.getBean("hrStaffEmolumentWelfareService");
		}
		return hrStaffEmolumentWelfareService;
	}


	@Override
	public void execute(TaskExecutionContext context) throws Exception {
		JSONObject json = (JSONObject) JSONValue
				.parse(getConfigContent(context));
		saveMainModel(context, json);
	}

	private void saveMainModel(TaskExecutionContext context, JSONObject json)
			throws Exception {
		JSONObject parameters = (JSONObject) json.get("params");
		IBaseModel mainModel = context.getMainModel();
		if (mainModel instanceof IExtendDataModel) {
			IExtendDataModel model = (IExtendDataModel) mainModel;
			Map<String, Object> modelData = model.getExtendDataModelInfo().getModelData();
			//logger.warn("parameters +========>"+ parameters);
			logger.warn("modelData +========>"+ JsonUtil.map2json(modelData));
			//定制开始，根据身份证获取人事档案员工信息 -- by liuyang
			String fdIdCard = (String) modelData.get(parameters.get("fdIdCard"));
			HrStaffPersonInfo detailed = HrStaffPersonUtil.getPersonInfoByIdCard(fdIdCard);
//			String fdApplicantId = BeanUtils.getProperty(fdApplicant, "id");
//			String fdApplicantName = BeanUtils.getProperty(fdApplicant, "name");
//			HrStaffPersonInfo detailed = (HrStaffPersonInfo) hrStaffPersonInfoService
//					.findByPrimaryKey(fdApplicantId, null, true);
			if(detailed == null){
				detailed = new HrStaffPersonInfo();
				//设置入职日期
				detailed.setFdEntryTime(new Date());
			}
			//是否二次入职
			String fdSecondEntry = (String)modelData.get(parameters.get("fdSecondEntry"));
			//根据入职流程中的“是否二次入职”和入职日期计算就可以
			if(detailed.getFdEntryTime() != null && "1".equals(fdSecondEntry)){
				detailed.setFdTrialExpirationTime(detailed.getFdEntryTime());
				detailed.setFdProbationPeriod("0");
			}
			//detailed = getPersonInfo((String) parameters.get("fdApplicant"), modelData);
			//定制开始，根据身份证获取人事档案员工信息 -- by liuyang
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			//姓名
			BeanUtils.setProperty(detailed, "fdName", modelData.get(parameters.get("fdApplicant")));
			// 员工状态
			String fdStatus = (String)modelData.get(parameters.get("fdStatus"));
			if(StringUtil.isNull(fdStatus)){
				detailed.setFdStatus("trial");
			}else{
				BeanUtils.setProperty(detailed, "fdStatus", modelData.get(parameters.get("fdStatus")));
			}
//			// 年纪
//			BeanUtils.setProperty(detailed, "fdAge", modelData
//					.get(parameters.get("fdAge")));
			// 出身日期
			BeanUtils.setProperty(detailed, "fdDateOfBirth", modelData
					.get(parameters.get("fdDateOfBirth")));
			// 身份证
			BeanUtils.setProperty(detailed, "fdIdCard", modelData
					.get(parameters.get("fdIdCard")));
			// 员工号
			BeanUtils.setProperty(detailed, "fdStaffNo", modelData
					.get(parameters.get("fdStaffNo")));
			// 参加工作时间
			BeanUtils.setProperty(detailed, "fdWorkTime", modelData
					.get(parameters.get("fdWorkTime")));
			// 到本单位时间
			BeanUtils.setProperty(detailed, "fdTimeOfEnterprise", modelData
					.get(parameters.get("fdTimeOfEnterprise")));
			// 试用期限（月）
			BeanUtils.setProperty(detailed, "fdTrialOperationPeriod",
					modelData.get(parameters.get("fdTrialOperationPeriod")));
//			// 办公地点
//			BeanUtils.setProperty(detailed, "fdOfficeLocation",
//					modelData.get(parameters.get("fdOfficeLocation")));
			// 入职日期
			BeanUtils.setProperty(detailed, "fdEntryTime",
					modelData.get(parameters.get("fdEntryTime")));
			// 转正日期
			BeanUtils.setProperty(detailed, "fdPositiveTime",
					modelData.get(parameters.get("fdPositiveTime")));
			// 试用到期时间
			BeanUtils.setProperty(detailed, "fdTrialExpirationTime", modelData
					.get(parameters.get("fdTrialExpirationTime")));
			// 用工期限（年）
			BeanUtils.setProperty(detailed, "fdEmploymentPeriod", modelData
					.get(parameters.get("fdEmploymentPeriod")));
			Object c = modelData.get(parameters.get("fdStaffType"));
			//员工类型
			BeanUtils.setProperty(detailed, "fdStaffType", modelData
					.get(parameters.get("fdStaffType")));
			// 曾用名
			BeanUtils.setProperty(detailed, "fdNameUsedBefore", modelData
					.get(parameters.get("fdNameUsedBefore")));
			// 民族：后台配置项			
			BeanUtils.setProperty(detailed, "fdNation", modelData
					.get(parameters.get("fdNation")));
			// 政治面貌：后台配置项
			BeanUtils.setProperty(detailed, "fdPoliticalLandscape", modelData
					.get(parameters.get("fdPoliticalLandscape")));
			// 入团日期
			BeanUtils.setProperty(detailed, "fdDateOfGroup", modelData
					.get(parameters.get("fdDateOfGroup")));
			// 入党日期
			BeanUtils.setProperty(detailed, "fdDateOfParty", modelData
					.get(parameters.get("fdDateOfParty")));
			// 最高学位：后台配置项
			BeanUtils.setProperty(detailed, "fdHighestDegree", modelData
					.get(parameters.get("fdHighestDegree")));
			// 最高学历：后台配置项
			BeanUtils.setProperty(detailed, "fdHighestEducation", modelData
					.get(parameters.get("fdHighestEducation")));
			// 婚姻情况：后台配置项
			BeanUtils.setProperty(detailed, "fdMaritalStatus", modelData
					.get(parameters.get("fdMaritalStatus")));
			// 健康情况：后台配置项
			BeanUtils.setProperty(detailed, "fdHealth", modelData
					.get(parameters.get("fdHealth")));
			// 身高（厘米）
			BeanUtils.setProperty(detailed, "fdStature", modelData
					.get(parameters.get("fdStature")));
			// 体重（千克）
			BeanUtils.setProperty(detailed, "fdWeight", modelData
					.get(parameters.get("fdWeight")));
			// 现居地
			BeanUtils.setProperty(detailed, "fdLivingPlace", modelData
					.get(parameters.get("fdLivingPlace")));
			// 籍贯
			BeanUtils.setProperty(detailed, "fdNativePlace", modelData
					.get(parameters.get("fdNativePlace")));
			// 出生地
			BeanUtils.setProperty(detailed, "fdHomeplace", modelData
					.get(parameters.get("fdHomeplace")));
			// 户口性质
			BeanUtils.setProperty(detailed, "fdAccountProperties", modelData
					.get(parameters.get("fdAccountProperties")));
			// 户口所在地
			BeanUtils.setProperty(detailed, "fdRegisteredResidence", modelData
					.get(parameters.get("fdRegisteredResidence")));
			// 户口所在派出所
			BeanUtils.setProperty(detailed, "fdResidencePoliceStation", modelData.get(parameters.get("fdResidencePoliceStation")));
			/** 定制新增，人事档案机器人节点添加字段 start by liuyang at 2022/08/21 **/
			// 通讯地址
			BeanUtils.setProperty(detailed, "fdPostalAddressProvinceId", modelData.get(parameters.get("fdPostalAddressProvinceId")));
			BeanUtils.setProperty(detailed, "fdPostalAddressCityId", modelData.get(parameters.get("fdPostalAddressCityId")));
			BeanUtils.setProperty(detailed, "fdPostalAddressAreaId", modelData.get(parameters.get("fdPostalAddressAreaId")));
			//添加显示值
			List<Province> byType = ProvinceUtil.getProvinceService().getByType("");
			String fdPostalAddressProvinceId = (String)modelData.get(parameters.get("fdPostalAddressProvinceId"));
			for (Province province : byType) {
				if(province.getProvinceId().equals(fdPostalAddressProvinceId)){
					BeanUtils.setProperty(detailed, "fdPostalAddressProvinceName", province.getProvince());
				}
			}
			//市

			String fdPostalAddressCityId = (String)modelData.get(parameters.get("fdPostalAddressCityId"));
			List<Cities> citiesList = CitiesUtil.getCitiesService().getByType("", fdPostalAddressProvinceId);
			for (Cities cities : citiesList) {
				if(cities.getCityId().equals(fdPostalAddressCityId)){
					BeanUtils.setProperty(detailed, "fdPostalAddressCityName", cities.getCity());
				}
			}

			List<Areas> areasList = AreasUtil.getAreasService().getByType("", fdPostalAddressCityId);
			String fdPostalAddressAreaId = (String)modelData.get(parameters.get("fdPostalAddressAreaId"));
			for (Areas areas : areasList) {
				if(areas.getAreaId().equals(fdPostalAddressAreaId)){
					BeanUtils.setProperty(detailed, "fdPostalAddressAreaName",areas.getArea() );
				}
			}


			BeanUtils.setProperty(detailed, "fdPostalAddress", modelData.get(parameters.get("fdPostalAddress")));
			//家庭地址
			String fdHomeAddressProvinceId = (String)modelData.get(parameters.get("fdHomeAddressProvinceId"));
			for (Province province : byType) {
				if(province.getProvinceId().equals(fdHomeAddressProvinceId)){
					BeanUtils.setProperty(detailed, "fdHomeAddressProvinceName",province.getProvince() );
				}
			}

			List<Cities> citiesHomeList = CitiesUtil.getCitiesService().getByType("", fdHomeAddressProvinceId);
			String fdHomeAddressCityId = (String)modelData.get(parameters.get("fdHomeAddressCityId"));
			for (Cities cities : citiesHomeList) {
				if(cities.getCityId().equals(fdHomeAddressCityId)){
					BeanUtils.setProperty(detailed, "fdHomeAddressCityName",cities.getCity() );
				}
			}

			String fdHomeAddressAreaId = (String)modelData.get(parameters.get("fdHomeAddressAreaId"));
			List<Areas> areasHomeList = AreasUtil.getAreasService().getByType("", fdPostalAddressCityId);
			for (Areas areas : areasHomeList) {
				if(areas.getAreaId().equals(fdHomeAddressAreaId)){
					BeanUtils.setProperty(detailed, "fdHomeAddressAreaName", areas.getArea());
				}
			}
			BeanUtils.setProperty(detailed, "fdHomeAddressProvinceId", modelData.get(parameters.get("fdHomeAddressProvinceId")));
			BeanUtils.setProperty(detailed, "fdHomeAddressCityId", modelData.get(parameters.get("fdHomeAddressCityId")));
			BeanUtils.setProperty(detailed, "fdHomeAddressAreaId", modelData.get(parameters.get("fdHomeAddressAreaId")));

			BeanUtils.setProperty(detailed, "fdHomeAddress", modelData.get(parameters.get("fdHomeAddress")));
			//外语级别
			BeanUtils.setProperty(detailed, "fdForeignLanguageLevel", modelData.get(parameters.get("fdForeignLanguageLevel")));
			//是否退役军人
			BeanUtils.setProperty(detailed, "fdIsRetiredSoldier", modelData.get(parameters.get("fdIsRetiredSoldier")));
			//一级部门
			Map fdFirstLevelDepartment = (Map)modelData.get(parameters.get("fdFirstLevelDepartment"));
			if(fdFirstLevelDepartment != null && fdFirstLevelDepartment.containsKey("id")){
				BeanUtils.setProperty(detailed, "fdFirstLevelDepartment", getSysOrgElementService().findByPrimaryKey((String) fdFirstLevelDepartment.get("id")));
			}
			//二级部门
			Map fdSecondLevelDepartment = (Map)modelData.get(parameters.get("fdSecondLevelDepartment"));
			if(fdSecondLevelDepartment != null && fdSecondLevelDepartment.containsKey("id")){
				BeanUtils.setProperty(detailed, "fdSecondLevelDepartment", getSysOrgElementService().findByPrimaryKey((String) fdSecondLevelDepartment.get("id")));
			}
			//三级部门
			Map fdThirdLevelDepartment = (Map)modelData.get(parameters.get("fdThirdLevelDepartment"));
			if(fdThirdLevelDepartment != null && fdThirdLevelDepartment.containsKey("id")){
				BeanUtils.setProperty(detailed, "fdThirdLevelDepartment", getSysOrgElementService().findByPrimaryKey((String) fdThirdLevelDepartment.get("id")));
			}
			//所属公司
			BeanUtils.setProperty(detailed, "fdAffiliatedCompany", modelData.get(parameters.get("fdAffiliatedCompany")));
			//考勤卡号
			BeanUtils.setProperty(detailed, "fdTimeCardNo", modelData.get(parameters.get("fdTimeCardNo")));
			String fdOrgRankId = (String)modelData.get(parameters.get("fdOrgRankId"));
			//职级
			logger.warn("职级 fdOrgRankId===>"+fdOrgRankId);
			if(fdOrgRankId!=null && fdOrgRankId.length()>4){
				BeanUtils.setProperty(detailed, "fdOrgRank", getRankServiceImp().findByPrimaryKey(fdOrgRankId));
			}
			String fdStaffingLevel = (String)modelData.get(parameters.get("fdStaffingLevel"));

			logger.warn("职务 fdStaffingLevel===>"+fdStaffingLevel);
			if(fdStaffingLevel!=null && fdStaffingLevel.length() >0){
				//职务
				SysOrganizationStaffingLevel staffingLevel = getStaffingLevelServiceImp().findStaffLevelByName(fdStaffingLevel);
				if(null != staffingLevel){
					BeanUtils.setProperty(detailed, "fdStaffingLevel", staffingLevel);
				}
			}
			//职类
			BeanUtils.setProperty(detailed, "fdCategory", modelData.get(parameters.get("fdCategory")));

			//所在部门
			Map<String,String> fdOrgParent = (Map) modelData.get(parameters.get("fdOrgParentId"));
			if(fdOrgParent != null && fdOrgParent.containsKey("id")){
				BeanUtils.setProperty(detailed, "fdOrgParent", getSysOrgElementService().findByPrimaryKey(fdOrgParent.get("id")));
			}
			//岗位名称
			Map<String,String> fdOrgPost = (Map) modelData.get(parameters.get("fdOrgPostIds"));
			if(fdOrgPost != null && fdOrgPost.containsKey("id")){
				List<SysOrgPost> posts = new ArrayList<>();
				posts.add((SysOrgPost) getSysOrgPostService().findByPrimaryKey(fdOrgPost.get("id")));
				BeanUtils.setProperty(detailed, "fdOrgPosts",posts);
				//detailed.getFdOrgPosts().addAll(posts);
			}
			//直接上级
			Map<String,String> fdReportLeader = (Map) modelData.get(parameters.get("fdReportLeaderId"));
			if(fdReportLeader != null && fdReportLeader.containsKey("id")){
				BeanUtils.setProperty(detailed, "fdReportLeader", getSysOrgElementService().findByPrimaryKey(fdReportLeader.get("id")));
			}
			//费用归属
			BeanUtils.setProperty(detailed, "fdCostAttribution", modelData.get(parameters.get("fdCostAttribution")));
			//OA账号
			BeanUtils.setProperty(detailed, "fdOAAccount", modelData.get(parameters.get("fdOAAccount")));
			//是否OA用户
			BeanUtils.setProperty(detailed, "fdIsOAUser", modelData.get(parameters.get("fdIsOAUser")));
			//私人邮箱
			BeanUtils.setProperty(detailed, "fdPrivateMailbox", modelData.get(parameters.get("fdPrivateMailbox")));
			//手机号码
			BeanUtils.setProperty(detailed, "fdMobileNo", modelData.get(parameters.get("fdMobileNo")));
			//紧急联系人姓名
			BeanUtils.setProperty(detailed, "fdEmergencyContact", modelData.get(parameters.get("fdEmergencyContact")));
			//紧急联系人电话
			BeanUtils.setProperty(detailed, "fdEmergencyContactPhone", modelData.get(parameters.get("fdEmergencyContactPhone")));
			//紧急联系人与员工关系
			BeanUtils.setProperty(detailed, "fdRelationsOfEmergencyContactAndEmployee", modelData.get(parameters.get("fdRelationsOfEmergencyContactAndEmployee")));
			//紧急联系人地址
			BeanUtils.setProperty(detailed, "fdEmergencyContactAddress", modelData.get(parameters.get("fdEmergencyContactAddress")));
			//性别
			BeanUtils.setProperty(detailed, "fdSex", modelData.get(parameters.get("fdSex")));
			//密码
			BeanUtils.setProperty(detailed, "fdNewPassword", modelData.get(parameters.get("fdNewPassword")));
			//公司邮箱
			BeanUtils.setProperty(detailed, "fdEmail", modelData.get(parameters.get("fdEmail")));
			//系统账号
			BeanUtils.setProperty(detailed, "fdLoginName", modelData.get(parameters.get("fdLoginName")));
			//是否考勤
			BeanUtils.setProperty(detailed, "fdIsAttendance", modelData.get(parameters.get("fdIsAttendance")));
			//固定班次
			BeanUtils.setProperty(detailed, "fdFixedShift", modelData.get(parameters.get("fdFixedShift")));
			//拟转正时间
			BeanUtils.setProperty(detailed, "fdProposedEmploymentConfirmationDate", modelData.get(parameters.get("fdProposedEmploymentConfirmationDate")));
			//试用期限
			BeanUtils.setProperty(detailed, "fdProbationPeriod", modelData.get(parameters.get("fdProbationPeriod")));
			//办公所属区域
			BeanUtils.setProperty(detailed, "fdOfficeAreaProvinceId", modelData.get(parameters.get("fdOfficeAreaProvinceId")));
			BeanUtils.setProperty(detailed, "fdOfficeAreaCityId", modelData.get(parameters.get("fdOfficeAreaCityId")));
			BeanUtils.setProperty(detailed, "fdOfficeAreaAreaId", modelData.get(parameters.get("fdOfficeAreaAreaId")));

			String fdOfficeAreaProvinceId= (String)modelData.get(parameters.get("fdOfficeAreaProvinceId"));
			//省
			for (Province province : byType) {
				if(province.getProvinceId().equals(fdOfficeAreaProvinceId)){
					BeanUtils.setProperty(detailed, "fdOfficeAreaProvinceName", province.getProvince());
				}
			}
			//市
			String fdOfficeAreaCityId = (String)modelData.get(parameters.get("fdOfficeAreaCityId"));
			List<Cities> fdOfficeAreaCityList = CitiesUtil.getCitiesService().getByType("", fdOfficeAreaProvinceId);
			for (Cities cities : fdOfficeAreaCityList) {
				if(cities.getCityId().equals(fdOfficeAreaCityId)){
					BeanUtils.setProperty(detailed, "fdOfficeAreaCityName", cities.getCity());
				}
			}

			String fdOfficeAreaAreaId = (String)modelData.get(parameters.get("fdOfficeAreaAreaId"));
			List<Areas> fdOfficeAreaAreaList = AreasUtil.getAreasService().getByType("", fdOfficeAreaCityId);
			for (Areas areas : fdOfficeAreaAreaList) {
				if(areas.getAreaId().equals(fdOfficeAreaAreaId)){
					BeanUtils.setProperty(detailed, "fdOfficeAreaAreaName",areas.getArea() );
				}
			}

			BeanUtils.setProperty(detailed, "fdOfficeLocation", modelData.get(parameters.get("fdOfficeLocation")));

			/** 定制新增，人事档案机器人节点添加字段 end by liuyang at 2022/08/21 **/
			
			// 流程URL
			detailed.setFdRelatedProcess(getUrl(model));
			
//			hrStaffPersonInfoService.update(detailed);
//			if(detailed.getFdOrgPerson()==null){
//				SysOrgPerson fdOrgPerson = new SysOrgPerson();
//				fdOrgPerson.setFdId(detailed.getFdId());
//				detailed.setFdOrgPerson(fdOrgPerson);
//			}
			SysOrgPerson sysOrgPerson = new SysOrgPerson();
			sysOrgPerson.setFdId(detailed.getFdId());
			sysOrgPerson.setFdName(detailed.getFdName());
//			getSysOrgPersonService().add(sysOrgPerson);
			getSysOrgPersonService().getBaseDao().add(sysOrgPerson);
//			getSysOrgPersonService().getBaseDao().clearHibernateSession();
//			getSysOrgPersonService().add(sysOrgPerson);
			detailed.setFdOrgPerson(sysOrgPerson);
			hrStaffPersonInfoService.add(detailed);
//			getHrStaffPersonInfoService().add(detailed);
//			hrStaffPersonInfoService.update(detailed);
			//插入薪酬福利社保，公积金信息
			HrStaffEmolumentWelfare emolumentWelfare = findEmolumentWelfareByPerson(detailed);
			if(emolumentWelfare == null){
				emolumentWelfare = new HrStaffEmolumentWelfare();
			}
			BeanUtils.setProperty(emolumentWelfare, "fdPersonInfo", detailed);
			//社保号码
			BeanUtils.setProperty(emolumentWelfare, "fdSocialSecurityNumber", modelData.get(parameters.get("fdSocialSecurityNumber")));
			//公积金账户
			BeanUtils.setProperty(emolumentWelfare, "fdSurplusAccount", modelData.get(parameters.get("fdSurplusAccount")));
			getHrStaffEmolumentWelfareService().update(emolumentWelfare);
		}
	}

	/**
	 * 获取请假人
	 * 
	 * @param fieldValue
	 * @param modelData
	 * @return
	 * @throws Exception
	 */
	private HrStaffPersonInfo getPersonInfo(String fieldValue,
			Map<String, Object> modelData) throws Exception {
		Object fdApplicant = modelData.get(fieldValue);
		String fdApplicantId = BeanUtils.getProperty(fdApplicant, "id");
		String fdApplicantName = BeanUtils.getProperty(fdApplicant, "name");
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) hrStaffPersonInfoService
				.findByPrimaryKey(fdApplicantId, null, true);
		if (personInfo == null) {
			throw new KmssException(new KmssMessage(ResourceUtil.getString(
					"hrStaffAttendanceManageDetailed.robot.fdApplicant.nofind",
					"hr-staff", null, fdApplicantName)));
		}
		return personInfo;
	}

	/**
	 * 获取流程URL
	 * 
	 * @param mainModel
	 * 
	 * @return
	 */
	private String getUrl(IBaseModel mainModel) throws Exception {
		String modelName = mainModel.getClass().getName();
		if (modelName.contains("$$")) {
			modelName = modelName.substring(0, modelName.indexOf("$$"));
		}
		SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
		String url = dictModel.getUrl();
		if (url != null) {
			url = url.replace("${fdId}", mainModel.getFdId());
		}
		return url;
	}

	/**
	 * 根据人员类型获取薪酬福利
	 * @param hrStaffPersonInfo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private HrStaffEmolumentWelfare findEmolumentWelfareByPerson(HrStaffPersonInfo hrStaffPersonInfo) throws Exception {
		if (hrStaffPersonInfo == null) {
			return null;
		}
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("hrStaffEmolumentWelfare.fdPersonInfo.fdId=:fdId");
		info.setParameter("fdId", hrStaffPersonInfo.getFdId());
		List<HrStaffEmolumentWelfare> list = getHrStaffEmolumentWelfareService().findList(info);
		return !ArrayUtil.isEmpty(list) ? list.get(0) : null;
	}

	private IHrOrganizationRankService hrOrganizationRankService;

	private IHrOrganizationRankService getRankServiceImp() {
		if (hrOrganizationRankService == null) {
			hrOrganizationRankService = (IHrOrganizationRankService)SpringBeanUtil.getBean("hrOrganizationRankService");
		}
		return hrOrganizationRankService;
	}
	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;
	private ISysOrganizationStaffingLevelService getStaffingLevelServiceImp() {
		if (sysOrganizationStaffingLevelService == null) {
			sysOrganizationStaffingLevelService = (ISysOrganizationStaffingLevelService)SpringBeanUtil.getBean("sysOrganizationStaffingLevelService");
		}
		return sysOrganizationStaffingLevelService;
	}
}
