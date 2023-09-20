package com.landray.kmss.hr.staff.service.spring;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.staff.model.Ekp_H14_S1;
import com.landray.kmss.hr.staff.model.Ekp_H14_S1_detail;
import com.landray.kmss.hr.staff.model.Ekp_H14_S_detail;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.Ekp_H14_S;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.hr.staff.service.IHrStaffAccumulationFundService;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_S1Service;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_SService;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_SService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.BdExportConstant;
import com.landray.kmss.hr.staff.util.ExcelExportUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.mchange.lang.DoubleUtils;
import com.sunbor.web.tag.Page;

public class HrStaffEkp_H14_S1ServiceImp extends HrStaffImportServiceImp
		implements IHrStaffEkp_H14_S1Service {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = getBaseDao().findPage(hqlInfo);
		List<Ekp_H14_S1_detail> list = page.getList();
		for(Ekp_H14_S1_detail ekp_H14_S1_detail : list){
			ekp_H14_S1_detail.setFdYiJiBuMen(ekp_H14_S1_detail.getEkp_H14_S1().getFdYiJiBuMen());
			ekp_H14_S1_detail.setFdErJiBuMen(ekp_H14_S1_detail.getEkp_H14_S1().getFdErJiBuMen());
			ekp_H14_S1_detail.setFdKaoHeZhouQi(ekp_H14_S1_detail.getEkp_H14_S1().getFdKaoHeZhouQi());
			ekp_H14_S1_detail.setFdBeiKaoHeRenXingMing(ekp_H14_S1_detail.getEkp_H14_S1().getFdBeiKaoHeRenXingMing());
			ekp_H14_S1_detail.setFdBeiKaoHeRenGangWei(ekp_H14_S1_detail.getEkp_H14_S1().getFdBeiKaoHeRenGangWei());
			ekp_H14_S1_detail.setFdJiXiaoDengJi(ekp_H14_S1_detail.getEkp_H14_S1().getFdJiXiaoDengJi());
			ekp_H14_S1_detail.setFdYeJiShiFuDaBiao(ekp_H14_S1_detail.getEkp_H14_S1().getFdYeJiShiFuDaBiao());
			ekp_H14_S1_detail.setFdDuiYingYeJikhjgyy(ekp_H14_S1_detail.getEkp_H14_S1().getFdDuiYingYeJikhjgyy());
			ekp_H14_S1_detail.setFdYJKHJGYY_qita(ekp_H14_S1_detail.getEkp_H14_S1().getFdDuiYingYeJikhjgyy_qita());
			ekp_H14_S1_detail.setFdBeiKaoHeRenDeChengJiYuBuZu(ekp_H14_S1_detail.getEkp_H14_S1().getFdBeiKaoHeRenDeChengJiYuBuZu());
			ekp_H14_S1_detail.setFdMianTanJieGuo(ekp_H14_S1_detail.getEkp_H14_S1().getFdMianTanJieGuo());
			ekp_H14_S1_detail.setFdFenQiDian(ekp_H14_S1_detail.getEkp_H14_S1().getFdFenQiDian());
		}
		
		return page;
	}
	   public List<String[]> modelToArrayList(List<Ekp_H14_S1> useList) throws Exception {
	        List<String[]> returnList = new ArrayList<String[]>();
	        if (ArrayUtil.isEmpty(useList)) {
	            String[] listdata = new String[9];
	            for (int i = 0; i < listdata.length; i++) {
	                listdata[i] = "";
	            }
	            returnList.add(listdata);
	            return returnList;
	        }
//	        List<HrStaffPersonInfoSettingNew> hrStaffPersonInfoSettingNewList = getHrStaffPersonInfoSettingNewServiceImp().findList("", "");
//	        Map<String, Ekp_H14_S1_detail> Ekp_H14_S1_detail = new HashMap<String, ekp_H14_S_detail>();
//	        if (null != hrStaffPersonInfoSettingNewList && !hrStaffPersonInfoSettingNewList.isEmpty()) {
//	            for (HrStaffPersonInfoSettingNew hrStaffPersonInfoSettingNew : hrStaffPersonInfoSettingNewList) {
//	                hrStaffPersonInfoSettingNewMap.put(hrStaffPersonInfoSettingNew.getFdId(), hrStaffPersonInfoSettingNew);
//	            }
//	        }

	        for (int i = 0; i < useList.size(); i++) {
	        	Ekp_H14_S1 ekp_H14_S1 = useList.get(i);
	            returnList.add(buildExcelDate(ekp_H14_S1));
	        }
	     
	        return returnList;
	    }
	   private String[] buildExcelDate(Ekp_H14_S1 ekp_H14_S1) throws Exception {

	        String[] listdata = new String[47];
	        String fdNo = ekp_H14_S1.getFdBeiKaoHeRenXingMing() == null ? "" : ekp_H14_S1.getFdBeiKaoHeRenXingMing().getFdNo();
	        String fdLoginName = ekp_H14_S1.getFdBeiKaoHeRenXingMing().getFdLoginName();
//	        String fdStaffNo = ekp_H14_S.getFdStaffNo();//员工号
	        String fdName = ekp_H14_S1.getFdBeiKaoHeRenXingMing().getFdName();//姓名
//	        String fdNameUsedBefore = ekp_H14_M.getFdNameUsedBefore();
//	        String fdIdCard = ekp_H14_M.getFdIdCard();//身份证号
//	        // String fdOrgParent = hrStaffPersonInfo.getFdOrgParentsName();
//	        String fdOrgParent = ekp_H14_M.getFdParentsName();// 所在部门取人事组织架构
//	        // 如果开启了ekp同步人事 取ekp的组织架构
//	        HrOrganizationSyncSetting hrorganizationsyncsetting = new HrOrganizationSyncSetting();
//	        if ("true".equals(hrorganizationsyncsetting.getEkpToHrEnable())) {
//	            fdOrgParent = ekp_H14_M.getFdOrgParentsName();
//	        }
//	        String fdStaffingLevel = ekp_H14_M.getFdStaffingLevel() == null ? "" : hrStaffPersonInfo.getFdStaffingLevel().getFdName();
//	        String fdOrgPosts = "";
//	        List<HrOrganizationElement> posts = ekp_H14_M.getFdPosts();
//	        for (HrOrganizationElement element : posts) {
//	            fdOrgPosts += element.getFdName() + ";";
//	        }
//	        if ("true".equals(hrorganizationsyncsetting.getEkpToHrEnable())) {
//	            List<SysOrgPost> list = ekp_H14_M.getFdOrgPosts();
//	            fdOrgPosts = "";
//	            if (list != null) {
//	                for (SysOrgPost sysOrgPost : list) {
//	                    fdOrgPosts += sysOrgPost.getFdName() + ";";
//	                }
//	            }
//	        }
//	        String fdOrgRank = ekp_H14_M.getFdOrgRank() == null ? "" : hrStaffPersonInfo.getFdOrgRank().getFdName();
//	        String fdMobileNo = ekp_H14_M.getFdMobileNo();//手机号码
//	        String fdTimeOfEnterprise = DateUtil.convertDateToString(hrStaffPersonInfo.getFdTimeOfEnterprise(), DateUtil.PATTERN_DATE);
//	        String fdNatureWork = ekp_H14_M.getFdNatureWork();//工作性质
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdNatureWork)) {
//	            fdNatureWork = hrStaffPersonInfoSettingNewMap.get(fdNatureWork) == null ? fdNatureWork : hrStaffPersonInfoSettingNewMap.get(fdNatureWork).getFdName();
//	        }
//	        String fdWorkAddress = ekp_H14_M.getFdWorkAddress();//工作地点
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdWorkAddress)) {
//	            fdWorkAddress = hrStaffPersonInfoSettingNewMap.get(fdWorkAddress) == null ? fdWorkAddress : hrStaffPersonInfoSettingNewMap.get(fdWorkAddress).getFdName();
//	        }
//	        String sex = ekp_H14_M.getFdSex();
//	        String fdSex = "";//性别
//	        if (StringUtil.isNotNull(sex) && "F".equals(sex)) {
//	            fdSex = ResourceUtil.getString("hrStaff.overview.report.staffSex.F", "hr.staff");
//	        } else {
//	            fdSex = ResourceUtil.getString("hrStaff.overview.report.staffSex.M", "hr.staff");
//	        }
//	        String personStatus = ekp_H14_M.getFdStatus();
////	        String fdStatus = getPersonStatus(personStatus);
//	        String fdStaffType = ekp_H14_M.getFdStaffType();//人员类别
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdStaffType)) {
//	            fdStaffType = hrStaffPersonInfoSettingNewMap.get(fdStaffType) == null ? fdStaffType : hrStaffPersonInfoSettingNewMap.get(fdStaffType).getFdName();
//	        }
//	        String fdEntryTime = DateUtil.convertDateToString(ekp_H14_M.getFdEntryTime(), DateUtil.PATTERN_DATE);
//	        String fdEmploymentPeriod = ekp_H14_M.getFdEmploymentPeriod() == null ? "" : hrStaffPersonInfo.getFdEmploymentPeriod().toString();
//	        String fdTrialOperationPeriod = ekp_H14_M.getFdTrialOperationPeriod();
//	        String fdPositiveTime = DateUtil.convertDateToString(ekp_H14_M.getFdPositiveTime(), DateUtil.PATTERN_DATE);
//	        String fdLeaveTime = DateUtil.convertDateToString(ekp_H14_M.getFdLeaveTime(), DateUtil.PATTERN_DATE);
//	        String fdLeaveReason = ekp_H14_M.getFdLeaveReason();//离职原因
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdLeaveReason)) {
//	            fdLeaveReason = hrStaffPersonInfoSettingNewMap.get(fdLeaveReason) == null ? fdLeaveReason : hrStaffPersonInfoSettingNewMap.get(fdLeaveReason).getFdName();
//	        }
//
//	        String fdDateOfBirth = DateUtil.convertDateToString(ekp_H14_M.getFdDateOfBirth(), DateUtil.PATTERN_DATE);//出生日期
//	        String fdMaritalStatus = ekp_H14_M.getFdMaritalStatus();//婚姻情况
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdMaritalStatus)) {
//	            fdMaritalStatus = hrStaffPersonInfoSettingNewMap.get(fdMaritalStatus) == null ? fdLeaveReason : hrStaffPersonInfoSettingNewMap.get(fdMaritalStatus).getFdName();
//	        }
//	        String fdDateOfGroup = DateUtil.convertDateToString(hrStaffPersonInfo.getFdDateOfGroup(), DateUtil.PATTERN_DATE);
//	        String fdDateOfParty = DateUtil.convertDateToString(hrStaffPersonInfo.getFdDateOfParty(), DateUtil.PATTERN_DATE);
//	        String fdHighestEducation = ekp_H14_M.getFdHighestEducation();//最高学历
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHighestEducation)) {
//	            fdHighestEducation = hrStaffPersonInfoSettingNewMap.get(fdHighestEducation) == null ? fdHighestEducation : hrStaffPersonInfoSettingNewMap.get(fdHighestEducation).getFdName();
//	        }
//	        String fdHighestDegree = ekp_H14_M.getFdHighestDegree();//最高学位
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHighestDegree)) {
//	            fdHighestDegree = hrStaffPersonInfoSettingNewMap.get(fdHighestDegree) == null ? fdHighestDegree : hrStaffPersonInfoSettingNewMap.get(fdHighestDegree).getFdName();
//	        }
//	        String fdNation = ekp_H14_M.getFdNation();//民族
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdNation)) {
//	            fdNation = hrStaffPersonInfoSettingNewMap.get(fdNation) == null ? fdNation : hrStaffPersonInfoSettingNewMap.get(fdNation).getFdName();
//	        }
//	        String fdHealth = ekp_H14_M.getFdHealth();//健康情况
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHealth)) {
//	            fdHealth = hrStaffPersonInfoSettingNewMap.get(fdHealth) == null ? fdHealth : hrStaffPersonInfoSettingNewMap.get(fdHealth).getFdName();
//	        }
//	        String fdStature = ekp_H14_M.getFdStature() == null ? "" : hrStaffPersonInfo.getFdStature().toString();
//	        String fdWeight = ekp_H14_M.getFdWeight() == null ? "" : hrStaffPersonInfo.getFdWeight().toString();
//	        String fdPoliticalLandscape = ekp_H14_M.getFdPoliticalLandscape();//政治面貌
//	        if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdPoliticalLandscape)) {
//	            fdPoliticalLandscape = hrStaffPersonInfoSettingNewMap.get(fdPoliticalLandscape) == null ? fdPoliticalLandscape : hrStaffPersonInfoSettingNewMap.get(fdPoliticalLandscape).getFdName();
//	        }
//	        String fdNativePlace = hrStaffPersonInfo.getFdNativePlace();
//	        String fdAccountProperties = hrStaffPersonInfo.getFdAccountProperties();
//	        String fdHomeplace = hrStaffPersonInfo.getFdHomeplace();
//	        String fdRegisteredResidence = hrStaffPersonInfo.getFdRegisteredResidence();
//	        String fdResidencePoliceStation = hrStaffPersonInfo.getFdResidencePoliceStation();
//	        String fdWorkTime = DateUtil.convertDateToString(hrStaffPersonInfo.getFdWorkTime(), DateUtil.PATTERN_DATE);
//	        String fdWorkPhone = hrStaffPersonInfo.getFdWorkPhone();//工作电话
//	        String fdOfficeLocation = hrStaffPersonInfo.getFdOfficeLocation();//办公地点
//	        String fdLivingPlace = hrStaffPersonInfo.getFdLivingPlace();//现居地
//	        String fdEmergencyContact = hrStaffPersonInfo.getFdEmergencyContact();//紧急联系人
//	        String fdEmergencyContactPhone = hrStaffPersonInfo.getFdEmergencyContactPhone();//紧急联系人电话
//	        String fdOtherContact = hrStaffPersonInfo.getFdOtherContact();//其他联系方式
//
	        listdata[0] = ekp_H14_S1.getFdId();
	        listdata[1] = ekp_H14_S1.getFdBeiKaoHeRenXingMing().getFdName();
	        listdata[2] = ekp_H14_S1.getFdYiJiBuMen();
	        listdata[3] = ekp_H14_S1.getFdErJiBuMen();
	        listdata[4] = ekp_H14_S1.getFdZhiJieShangJiKaoHeDengJi()==null?"":ekp_H14_S1.getFdZhiJieShangJiKaoHeDengJi().toString();
		    listdata[5] = ekp_H14_S1.getFdSuoShuFenBu().getFdName();
		    listdata[6] = ekp_H14_S1.getFdYuanGongBianHao();
		    listdata[7] = ekp_H14_S1.getFdZhiJi();
		    listdata[8] = ekp_H14_S1.getFdKaoHeJiDu();
	        listdata[9] = DateUtil.convertDateToString(ekp_H14_S1.getFdKaoHeNianFen(),DateUtil.PATTERN_DATE);
			listdata[10] = ekp_H14_S1.getFdSanJiBuMen();
	        listdata[11] = ekp_H14_S1.getFdGangWeiMingChen();
	        listdata[12] = ekp_H14_S1.getFdZhiLei();
	        listdata[13] = ekp_H14_S1.getFdZhiJiXiShu();
//	        listdata[11] = DateUtil.convertDateToString(ekp_H14_S_detail.getEkp_H14_S().getFdRuZhiRiQi(),DateUtil.PATTERN_DATE);
//	        listdata[13] = ekp_H14_S.getFdYeJiKaoHeJieGuoYingYong()==null?"":ekp_H14_S.getFdYeJiKaoHeJieGuoYingYong().toString();
	
	        listdata[14] = ekp_H14_S1.getFdGangWeiXingZhi();
	        listdata[15] = ekp_H14_S1.getFdZhijieshangji_text();
//	        listdata[15] = DateUtil.convertDateToString(ekp_H14_S_detail.getEkp_H14_S().getFdShenQingRiQi(),DateUtil.PATTERN_DATE);
	        listdata[16] = ekp_H14_S1.getFdRenYuanLeiBie();
	        
//	        listdata[19] = ekp_H14_S_detail.getEkp_H14_S().getFdZiPingDeFen()==null?"":ekp_H14_S_detail.getEkp_H14_S().getFdZiPingDeFen().toString();

	        listdata[17] = DateUtil.convertDateToString(ekp_H14_S1.getFdRuZhiRiQi(),DateUtil.PATTERN_DATE);
	        listdata[18] = DateUtil.convertDateToString(ekp_H14_S1.getFdShenQingRiQi(),DateUtil.PATTERN_DATE);
	        listdata[19] = ekp_H14_S1.getFdZhiJieShangJiKaoHeDengJi()==null?"":ekp_H14_S1.getFdZhiJieShangJiKaoHeDengJi().toString();
	        listdata[20] = ekp_H14_S1.getFdKPIDaChengQingKuang();
	        listdata[21] = ekp_H14_S1.getFdEjbmfzrKaoHeDengJi();
//	        listdata[21] = DateUtil.convertDateToString(ekp_H14_S.getFdJiHuaWanChengShiJian(),DateUtil.PATTERN_DATE);
		    listdata[22] = ekp_H14_S1.getFdEjbmfzrPingYu();
		    listdata[23] = ekp_H14_S1.getFdYjbmfzrPingYu();
		    listdata[24] = ekp_H14_S1.getFdYjbmfzrKaoHeDeFen();
		    listdata[25] = ekp_H14_S1.getFdXcjxfzrShenPi();
		    listdata[26] = ekp_H14_S1.getFdYjbmfzrKaoHeDengJi();
		    listdata[27] = ekp_H14_S1.getFdRlxzzxfzrPingYu();
		    listdata[28] = ekp_H14_S1.getFdFgfzrShenPi();
		    listdata[29] = ekp_H14_S1.getFdFgfzrKaoHePingFen();
		    listdata[30] = ekp_H14_S1.getFdEjbmfzrKaoHeDeFen();
		    listdata[31] = ekp_H14_S1.getFdFgfzrKaoHeDengJi();
		    listdata[32] = ekp_H14_S1.getFdZongCaiShenPi();
		    listdata[33] = ekp_H14_S1.getFdZongCaiKaoHePingFen();
		    listdata[34] = ekp_H14_S1.getFdZongCaiKaoHeDengJi();
		    listdata[35] = ekp_H14_S1.getFdZhiJieShangJiKaoHeDeFen();
		    listdata[36] = ekp_H14_S1.getFdDongShiChangShenPi();
		    listdata[37] = ekp_H14_S1.getFdDongShiChangKaoHePingFen();
		    listdata[38] = ekp_H14_S1.getFdGongZuoMuBiao2();
		    listdata[39] = ekp_H14_S1.getFdGongZuoMuBiao3();
		    listdata[40] = DateUtil.convertDateToString(ekp_H14_S1.getFdQiWangWanChengShiJian3(),DateUtil.PATTERN_DATE);
		    listdata[41] = ekp_H14_S1.getFdZhongDianGongZuo3();
		    listdata[42] = DateUtil.convertDateToString(ekp_H14_S1.getFdQiWangWanChengShiJian2(),DateUtil.PATTERN_DATE);
		    listdata[43] = ekp_H14_S1.getFdZhongDianGongZuo2();
		    listdata[44] = DateUtil.convertDateToString(ekp_H14_S1.getFdQiWangWanChengShiJian1(),DateUtil.PATTERN_DATE);
		    listdata[45] = ekp_H14_S1.getFdGongZuoMuBiao1();
		    listdata[46] = ekp_H14_S1.getFdZhongDianGongZuo1();
		    listdata[47] = ekp_H14_S1.getFdDongShiChangKaoHeDengJi();
		    listdata[48] = ekp_H14_S1.getFdZhiJieShangJiPingJia();
		    listdata[49] = ekp_H14_S1.getFdZiPingDengJi();
		    
//	        listdata[23] = ekp_H14_S_detail.getFdMuBiaoZhi()==null?"":ekp_H14_S_detail.getFdMuBiaoZhi().toString();
//	        listdata[24] = ekp_H14_S_detail.getFdQuanZhong()==null?"":ekp_H14_S_detail.getFdQuanZhong().toString();
//	        listdata[25] = ekp_H14_S_detail.getFdShiJiZhi()==null?"":ekp_H14_S_detail.getFdShiJiZhi().toString();
//	        listdata[26] = ekp_H14_S_detail.getFdJiXiaoDaChengQingKuangShuoM();
//	        listdata[27] = ekp_H14_S_detail.getFdZiPingDeFen1()==null?"":ekp_H14_S_detail.getFdZiPingDeFen1().toString();
//	        listdata[28] = fdHighestEducation;
//	        listdata[29] = fdHighestDegree;
//	        listdata[30] = fdNation;
//	        listdata[31] = fdHealth;
//	        listdata[32] = fdStature;
//	        listdata[33] = fdWeight;
//	        listdata[34] = fdPoliticalLandscape;
//	        listdata[35] = fdNativePlace;
//	        listdata[36] = fdAccountProperties;
//	        listdata[37] = fdHomeplace;
//	        listdata[38] = fdRegisteredResidence;
//	        listdata[39] = fdResidencePoliceStation;
//	        listdata[40] = fdWorkTime;
//	        listdata[41] = fdWorkPhone;
//	        listdata[42] = fdOfficeLocation;
//	        listdata[43] = fdLivingPlace;
//	        listdata[44] = fdEmergencyContact;
//	        listdata[45] = fdEmergencyContactPhone;
//	        listdata[46] = fdOtherContact;

	        return listdata;
	    }
	    @Override
	    @SuppressWarnings("unchecked")
	    public void exportList(HttpServletRequest request,
	                           HttpServletResponse response, List<Ekp_H14_S1> rtnList) {
	        try {

	            List<String[]> modelList = this.modelToArrayList(rtnList);
	            String fileName = ResourceUtil.getString("ekp_H14_S1.exportFileName", "hr.staff");
//				fileName = URLEncoder.encode(fileName, "UTF-8");
	            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	            } else {
	                fileName = URLEncoder.encode(fileName, "UTF-8");
	            }
	            // 类名 用于
	            String className = Ekp_H14_S1.class.getName();
	            // 调用通用导出方法
	            this.exportDecode(response, this.getExportTitles(), modelList,
	                    fileName, BdExportConstant.FIX_EXCEL_07, className, null);
	        } catch (Exception e) {
	            logger.error("hrStaffPersonInfoServiceImp.exportList.error", e);
	        }
	    }
	    @SuppressWarnings("unused")
	    public void exportDecode(HttpServletResponse response, String[] headTitles,
	                             List<String[]> modelList, String fileName, String postfix,
	                             String fdModelName, String queryCondition) throws Exception {
	        String exportIds = "";
	        if (BdExportConstant.FIX_CSV.equals(postfix)) {// 导出CSV
	        } else {// 导出Excel
	            ExcelExportUtil excel = new ExcelExportUtil(response, fileName,
	                    headTitles, modelList, BdExportConstant.FIX_EXCEL_03.equals(postfix)
	                    ? ExcelExportUtil.PostfixEnum.ENUM_POSTFIX_03
	                    : ExcelExportUtil.PostfixEnum.ENUM_POSTFIX_07, true);
	            excel.export();
	            exportIds = excel.getExportIds();// 导出记录ID
	        }
	    }
	    public String[] getExportTitles() {
	        String[] titles = {
			        		"hrStaffEkp_H14_S1.fdBeiKaoHeRenXingMing",
	        				"hrStaffEkp_H14_S1.fdYiJiBuMen",
	        				"hrStaffEkp_H14_S1.fdErJiBuMen",
	        				"hrStaffEkp_H14_S1.fdZhiJieShangJiKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdSuoShuFenBu",
	        				"hrStaffEkp_H14_S1.fdYuanGongBianHao",
	        				"hrStaffEkp_H14_S1.fdZhiJi",
	        				"hrStaffEkp_H14_S1.fdKaoHeJiDu",
	        				"hrStaffEkp_H14_S1.fdKaoHeNianFen",
	        				"hrStaffEkp_H14_S1.fdSanJiBuMen",
	        				"hrStaffEkp_H14_S1.fdGangWeiMingChen",
	        				"hrStaffEkp_H14_S1.fdZhiLei",
	        				"hrStaffEkp_H14_S1.fdZhiJiXiShu",
	        				"hrStaffEkp_H14_S1.fdGangWeiXingZhi",
	        				"hrStaffEkp_H14_S1.fdZhijieshangji_text",
	        				"hrStaffEkp_H14_S1.fdRenYuanLeiBie",
	        				"hrStaffEkp_H14_S1.fdRuZhiRiQi",
	        				"hrStaffEkp_H14_S1.fdShenQingRiQi",
	        				"hrStaffEkp_H14_S1.fdZhiJieShangJiKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdKPIDaChengQingKuang",
	        				"hrStaffEkp_H14_S1.fdEjbmfzrKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdEjbmfzrPingYu",
	        				"hrStaffEkp_H14_S1.fdYjbmfzrPingYu",
	        				"hrStaffEkp_H14_S1.fdYjbmfzrKaoHeDeFen",
	        				"hrStaffEkp_H14_S1.fdXcjxfzrShenPi",
	        				"hrStaffEkp_H14_S1.fdYjbmfzrKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdRlxzzxfzrPingYu",
	        				"hrStaffEkp_H14_S1.fdFgfzrShenPi",
	        				"hrStaffEkp_H14_S1.fdFgfzrKaoHePingFen",
	        				"hrStaffEkp_H14_S1.fdEjbmfzrKaoHeDeFen",
	        				"hrStaffEkp_H14_S1.fdFgfzrKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdZongCaiShenPi",
	        				"hrStaffEkp_H14_S1.fdZongCaiKaoHePingFen",
	        				"hrStaffEkp_H14_S1.fdZongCaiKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdZhiJieShangJiKaoHeDeFen",
	        				"hrStaffEkp_H14_S1.fdDongShiChangShenPi",
	        				"hrStaffEkp_H14_S1.fdDongShiChangKaoHePingFen",
	        				"hrStaffEkp_H14_S1.fdGongZuoMuBiao2",
	        				"hrStaffEkp_H14_S1.fdGongZuoMuBiao3",
	        				"hrStaffEkp_H14_S1.fdQiWangWanChengShiJian3",
	        				"hrStaffEkp_H14_S1.fdZhongDianGongZuo3",
	        				"hrStaffEkp_H14_S1.fdQiWangWanChengShiJian2",
	        				"hrStaffEkp_H14_S1.fdZhongDianGongZuo2",
	        				"hrStaffEkp_H14_S1.fdQiWangWanChengShiJian1",
	        				"hrStaffEkp_H14_S1.fdGongZuoMuBiao1",
	        				"hrStaffEkp_H14_S1.fdZhongDianGongZuo1",
	        				"hrStaffEkp_H14_S1.fdDongShiChangKaoHeDengJi",
	        				"hrStaffEkp_H14_S1.fdZhiJieShangJiPingJia",
	        				"hrStaffEkp_H14_S1.fdZiPingDengJi",
	        };
	        for (int i = 0; i < titles.length; i++) {
	            titles[i] = ResourceUtil.getString(titles[i], "hr.staff");
	        }
	        return titles;
	    }
	@Override
	public String[] getImportFields() {
		// TODO Auto-generated method stub
		return new String[] {
				"fdBeiKaoHeRenXingMing","fdYiJiBuMen","fdErJiBuMen",
				"fdZhuYaoGongZuoYeJi","fdZiWoPingJia","fdJiXiaoDengJi","fdJiXiaoPingYu","fdShangJiPingJiaDeFen","fdBeiKaoHeRenGangWei","fdJiDuJiXiaoDengJi"
				,"fdNianDuJiXiaoDengJi","fdYeJiShiFuDaBiao","fdYeJiKaoHeJieGuoYingYong","fdYJKHJGYY_qita","fdChengJiYuBuZu"
				,"fdFenQiDian","fdKaoHeJieShuShiJian","fdKaoHeKaiShiShiJian","fdMianTanJieGuo","fdGaiJinShiXiang","fdJiHuaWanChengShiJian","fdKunNanHuoSuoXuZhiChi"
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 妫�鏌ヨ鍛樺伐鏄惁宸茬粡鏈夋暟鎹�
		Ekp_H14_S1 model = (Ekp_H14_S1) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", model.getFdId());
		List<Ekp_H14_S1> list = findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			throw new KmssException(new KmssMessage(ResourceUtil
					.getString("hr-staff:hrStaffEkp_H14_S1.exist")));
		}

		return super.add(modelObj);
	}
}
