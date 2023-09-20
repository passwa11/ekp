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
import com.landray.kmss.hr.staff.model.Ekp_H14_S;
import com.landray.kmss.hr.staff.model.Ekp_H14_S_detail;
import com.landray.kmss.hr.staff.model.Ekp_H14_nS;
import com.landray.kmss.hr.staff.model.Ekp_H14_nS_detail;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.Ekp_H14_S;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.hr.staff.service.IHrStaffAccumulationFundService;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_SService;
import com.landray.kmss.hr.staff.service.IHrStaffEkp_H14_nSService;
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

public class HrStaffEkp_H14_nSServiceImp extends HrStaffImportServiceImp
		implements IHrStaffEkp_H14_nSService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = getBaseDao().findPage(hqlInfo);
		List<Ekp_H14_nS_detail> list = page.getList();
		for(Ekp_H14_nS_detail ekp_H14_nS_detail : list){
			Ekp_H14_nS ekp_H14_nS = ekp_H14_nS_detail.getEkp_H14_nS();
			ekp_H14_nS_detail.setFdBeiKaoHeRenXingMing(ekp_H14_nS.getFdBeiKaoHeRenXingMing());
			ekp_H14_nS_detail.setFdErJiBuMen(ekp_H14_nS.getFdErJiBuMen());
			ekp_H14_nS_detail.setFdZiWoPingJia(ekp_H14_nS.getFdZiWoPingJia());
			ekp_H14_nS_detail.setFdQiWangJiGaiShan(ekp_H14_nS.getFdQiWangJiGaiShan());
			ekp_H14_nS_detail.setFdYouShiYuLieShi(ekp_H14_nS.getFdYouShiYuLieShi());
			ekp_H14_nS_detail.setFdMianTanJieGuo(ekp_H14_nS.getFdMianTanJieGuo());
			ekp_H14_nS_detail.setFdNianDuJiXiaoDengJi(ekp_H14_nS.getFdNianDuJiXiaoDengJi());
			ekp_H14_nS_detail.setFdKaoHeJieShuShiJian(ekp_H14_nS.getFdKaoHeJieShuShiJian());
			ekp_H14_nS_detail.setFdKaoHeKaiShiShiJian(ekp_H14_nS.getFdKaoHeKaiShiShiJian());
			ekp_H14_nS_detail.setFdKaoHeZhouQi(ekp_H14_nS.getFdKaoHeZhouQi());
			ekp_H14_nS_detail.setFdFenQiDian(ekp_H14_nS.getFdFenQiDian());
			ekp_H14_nS_detail.setFdRuZhiRiQi(ekp_H14_nS.getFdRuZhiRiQi());
			ekp_H14_nS_detail.setFdJiDuJiXiaoDengJi(ekp_H14_nS.getFdJiDuJiXiaoDengJi());
			ekp_H14_nS_detail.setFdYJKHJGYY_qita(ekp_H14_nS.getFdYJKHJGYY_qita());
			ekp_H14_nS_detail.setFdShenQingRiQi(ekp_H14_nS.getFdShenQingRiQi());
			ekp_H14_nS_detail.setFdChengJiYuBuZu(ekp_H14_nS.getFdChengJiYuBuZu());
			ekp_H14_nS_detail.setFdYiJiBuMen(ekp_H14_nS.getFdYiJiBuMen());
			ekp_H14_nS_detail.setFdJiXiaoDengJi(ekp_H14_nS.getFdJiXiaoDengJi());
			ekp_H14_nS_detail.setFdJiXiaoPingYu(ekp_H14_nS.getFdJiXiaoPingYu());
			ekp_H14_nS_detail.setFdYeJiKaoHeJieGuoYingYong(ekp_H14_nS.getFdYeJiKaoHeJieGuoYingYong());
			ekp_H14_nS_detail.setFdZhiJieShangJi(ekp_H14_nS.getFdZhiJieShangJi());
			ekp_H14_nS_detail.setFdBeiKaoHeRenGangWei(ekp_H14_nS.getFdBeiKaoHeRenGangWei());
			ekp_H14_nS_detail.setFdYeJiShiFuDaBiao(ekp_H14_nS.getFdYeJiShiFuDaBiao());
			ekp_H14_nS_detail.setFdZiPingDeFen(ekp_H14_nS.getFdZiPingDeFen());
			ekp_H14_nS_detail.setFdShangJiPingJiaDeFen(ekp_H14_nS.getFdShangJiPingJiaDeFen());
			ekp_H14_nS_detail.setFdZhuYaoGongZuoYeJi(ekp_H14_nS.getFdZhuYaoGongZuoYeJi());
			ekp_H14_nS_detail.setFdZiPingDengJi(ekp_H14_nS.getFdZiPingDengJi());
		}
		
		return page;
	}
	   public List<String[]> modelToArrayList(List<Ekp_H14_nS_detail> rtnList) throws Exception {
	        List<String[]> returnList = new ArrayList<String[]>();
	        if (ArrayUtil.isEmpty(rtnList)) {
	            String[] listdata = new String[9];
	            for (int i = 0; i < listdata.length; i++) {
	                listdata[i] = "";
	            }
	            returnList.add(listdata);
	            return returnList;
	        }
//	        List<HrStaffPersonInfoSettingNew> hrStaffPersonInfoSettingNewList = getHrStaffPersonInfoSettingNewServiceImp().findList("", "");
//	        Map<String, ekp_H14_S_detail> ekp_H14_S_detail = new HashMap<String, ekp_H14_S_detail>();
//	        if (null != hrStaffPersonInfoSettingNewList && !hrStaffPersonInfoSettingNewList.isEmpty()) {
//	            for (HrStaffPersonInfoSettingNew hrStaffPersonInfoSettingNew : hrStaffPersonInfoSettingNewList) {
//	                hrStaffPersonInfoSettingNewMap.put(hrStaffPersonInfoSettingNew.getFdId(), hrStaffPersonInfoSettingNew);
//	            }
//	        }

	        for (int i = 0; i < rtnList.size(); i++) {
	        	Ekp_H14_nS_detail ekp_H14_nS_detail = rtnList.get(i);
	            returnList.add(buildExcelDate(ekp_H14_nS_detail));
	        }
	     
	        return returnList;
	    }
	   private String[] buildExcelDate(Ekp_H14_nS_detail ekp_H14_nS_detail) throws Exception {

	        String[] listdata = new String[47];
	        String fdNo = ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenXingMing() == null ? "" : ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenXingMing();
	        String fdLoginName = ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenXingMing();
//	        String fdStaffNo = ekp_H14_S.getFdStaffNo();//员工号
	        String fdName = ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenXingMing();//姓名
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
	        listdata[0] = ekp_H14_nS_detail.getFdId();
	        listdata[1] = ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenXingMing();
	        listdata[2] = ekp_H14_nS_detail.getEkp_H14_nS().getFdYiJiBuMen();
	        listdata[3] = ekp_H14_nS_detail.getEkp_H14_nS().getFdErJiBuMen();
	        listdata[4] = ekp_H14_nS_detail.getEkp_H14_nS().getFdZhuYaoGongZuoYeJi();
	        listdata[5] = ekp_H14_nS_detail.getEkp_H14_nS().getFdZiWoPingJia();
	        listdata[6] = ekp_H14_nS_detail.getEkp_H14_nS().getFdJiXiaoDengJi();
	        listdata[7] = ekp_H14_nS_detail.getEkp_H14_nS().getFdJiXiaoPingYu();
	        listdata[8] = ekp_H14_nS_detail.getEkp_H14_nS().getFdShangJiPingJiaDeFen();
	        listdata[9] = ekp_H14_nS_detail.getEkp_H14_nS().getFdBeiKaoHeRenGangWei();
	        listdata[10] = ekp_H14_nS_detail.getEkp_H14_nS().getFdJiDuJiXiaoDengJi();
	        listdata[11] = ekp_H14_nS_detail.getEkp_H14_nS().getFdNianDuJiXiaoDengJi();
	        listdata[12] = ekp_H14_nS_detail.getEkp_H14_nS().getFdYeJiShiFuDaBiao();
//	        listdata[11] = DateUtil.convertDateToString(ekp_H14_S_detail.getEkp_H14_S().getFdRuZhiRiQi(),DateUtil.PATTERN_DATE);
	        listdata[13] = ekp_H14_nS_detail.getEkp_H14_nS().getFdYeJiKaoHeJieGuoYingYong()==null?"":ekp_H14_nS_detail.getEkp_H14_nS().getFdYeJiKaoHeJieGuoYingYong().toString();
	
	        listdata[14] = ekp_H14_nS_detail.getEkp_H14_nS().getFdYJKHJGYY_qita();
	        listdata[15] = ekp_H14_nS_detail.getEkp_H14_nS().getFdChengJiYuBuZu();
//	        listdata[15] = DateUtil.convertDateToString(ekp_H14_S_detail.getEkp_H14_S().getFdShenQingRiQi(),DateUtil.PATTERN_DATE);
	        listdata[16] = ekp_H14_nS_detail.getEkp_H14_nS().getFdFenQiDian();
	        
//	        listdata[19] = ekp_H14_S_detail.getEkp_H14_S().getFdZiPingDeFen()==null?"":ekp_H14_S_detail.getEkp_H14_S().getFdZiPingDeFen().toString();

	        listdata[17] = DateUtil.convertDateToString(ekp_H14_nS_detail.getEkp_H14_nS().getFdKaoHeJieShuShiJian(),DateUtil.PATTERN_DATE);
	        listdata[18] = DateUtil.convertDateToString(ekp_H14_nS_detail.getEkp_H14_nS().getFdKaoHeKaiShiShiJian(),DateUtil.PATTERN_DATE);
	        listdata[19] = ekp_H14_nS_detail.getEkp_H14_nS().getFdMianTanJieGuo()==null?"":ekp_H14_nS_detail.getEkp_H14_nS().getFdMianTanJieGuo().toString();

	        listdata[20] = ekp_H14_nS_detail.getEkp_H14_nS().getFdQiWangJiGaiShan();
	        listdata[21] = ekp_H14_nS_detail.getEkp_H14_nS().getFdYouShiYuLieShi();
	        listdata[22] = ekp_H14_nS_detail.getFdGaiJinShiXiang();
	        listdata[23] = DateUtil.convertDateToString(ekp_H14_nS_detail.getFdJiHuaWanChengShiJian(),DateUtil.PATTERN_DATE);
		    listdata[24] = ekp_H14_nS_detail.getFdKunNanHuoSuoXuZhiChi();
	        //		    listdata[23] = ekp_H14_S_detail.getFdMuBiaoZhi()==null?"":ekp_H14_S_detail.getFdMuBiaoZhi().toString();
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
	                           HttpServletResponse response, List<Ekp_H14_nS_detail> rtnList) {
	        try {

	            List<String[]> modelList = this.modelToArrayList(rtnList);
	            String fileName = ResourceUtil.getString("ekp_H14_nS.exportFileName", "hr.staff");
//				fileName = URLEncoder.encode(fileName, "UTF-8");
	            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	            } else {
	                fileName = URLEncoder.encode(fileName, "UTF-8");
	            }
	            // 类名 用于
	            String className = Ekp_H14_nS.class.getName();
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
			        		"hrStaffEkp_H14_nS.fdBeiKaoHeRenXingMing",
	        				"hrStaffEkp_H14_nS.fdYiJiBuMen",
	        				"hrStaffEkp_H14_nS.fdErJiBuMen",
	        				"hrStaffEkp_H14_nS.fdZhuYaoGongZuoYeJi",
			        		"hrStaffEkp_H14_nS.fdZiWoPingJia",
	        				"hrStaffEkp_H14_nS.fdJiXiaoDengJi",
	        				"hrStaffEkp_H14_nS.fdJiXiaoPingYu",
	        				"hrStaffEkp_H14_nS.fdShangJiPingJiaDeFen",
	        				"hrStaffEkp_H14_nS.fdBeiKaoHeRenGangWei",
	        				"hrStaffEkp_H14_nS.fdJiDuJiXiaoDengJi",
	        				"hrStaffEkp_H14_nS.fdNianDuJiXiaoDengJi",
	        				"hrStaffEkp_H14_nS.fdYeJiShiFuDaBiao",
	        				"hrStaffEkp_H14_nS.fdYeJiKaoHeJieGuoYingYong",
	        				"hrStaffEkp_H14_nS.fdYJKHJGYY_qita",
	        				"hrStaffEkp_H14_nS.fdChengJiYuBuZu",
	        				"hrStaffEkp_H14_nS.fdFenQiDian",
	        				"hrStaffEkp_H14_nS.fdKaoHeJieShuShiJian",
	        				"hrStaffEkp_H14_nS.fdKaoHeKaiShiShiJian",
	        				"hrStaffEkp_H14_nS.fdMianTanJieGuo",
	        				"hrStaffEkp_H14_nS.fdQiWangJiGaiShan",
	        				"hrStaffEkp_H14_nS.fdYouShiYuLieShi",
	        				"hrStaffEkp_H14_nS.fdGaiJinShiXiang",
	        				"hrStaffEkp_H14_nS.fdJiHuaWanChengShiJian",
	        				"hrStaffEkp_H14_nS.fdKunNanHuoSuoXuZhiChi",
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
				,"fdFenQiDian","fdKaoHeJieShuShiJian","fdKaoHeKaiShiShiJian","fdQiWangJiGaiShan","fdYouShiYuLieShi","fdMianTanJieGuo","fdGaiJinShiXiang","fdJiHuaWanChengShiJian","fdKunNanHuoSuoXuZhiChi"
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 妫�鏌ヨ鍛樺伐鏄惁宸茬粡鏈夋暟鎹�
		Ekp_H14_nS model = (Ekp_H14_nS) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", model.getFdId());
		List<Ekp_H14_nS> list = findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			throw new KmssException(new KmssMessage(ResourceUtil
					.getString("hr-staff:hrStaffEkp_H14_nS.exist")));
		}

		return super.add(modelObj);
	}
}
