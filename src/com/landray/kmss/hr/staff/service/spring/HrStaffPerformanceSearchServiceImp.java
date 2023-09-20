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
import com.landray.kmss.hr.staff.model.Ekp_H14_M;
import com.landray.kmss.hr.staff.model.Ekp_H14_M_detailed;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffPerformanceSearch;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.hr.staff.service.IHrStaffAccumulationFundService;
import com.landray.kmss.hr.staff.service.IHrStaffPerformanceSearchService;
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

public class HrStaffPerformanceSearchServiceImp extends HrStaffImportServiceImp
		implements IHrStaffPerformanceSearchService {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	@Override
    public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = getBaseDao().findPage(hqlInfo);
		List<Ekp_H14_M_detailed> list = page.getList();
//		for(Ekp_H14_M_detailed ekp_H14_M_detailed : list){
//			Ekp_H14_M ekp_H14_M = ekp_H14_M_detailed.getEkp_H14_M();
//			ekp_H14_M_detailed.setFdBeiKaoHeRenXingMing(ekp_H14_M.getFdBeiKaoHeRenXingMing());
//			ekp_H14_M_detailed.setFdErJiBuMen(ekp_H14_M.getFdErJiBuMen());
//			ekp_H14_M_detailed.setFdGangWeiMingChen(ekp_H14_M.getFdGangWeiMingChen());
//			ekp_H14_M_detailed.setFdGangWeiXingZhi(ekp_H14_M.getFdGangWeiXingZhi());
//			ekp_H14_M_detailed.setFdKaoHeJieShuShiJian(ekp_H14_M.getFdKaoHeJieShuShiJian());
//			ekp_H14_M_detailed.setFdKaoHeKaiShiShiJian(ekp_H14_M.getFdKaoHeKaiShiShiJian());
//			ekp_H14_M_detailed.setFdKaoHeZhouQi(ekp_H14_M.getFdKaoHeZhouQi());
//			ekp_H14_M_detailed.setFdRenYuanLeiBie(ekp_H14_M.getFdRenYuanLeiBie());
//			ekp_H14_M_detailed.setFdRuZhiRiQi(ekp_H14_M.getFdRuZhiRiQi());
//			ekp_H14_M_detailed.setFdRenYuanLeiBie(ekp_H14_M.getFdRenYuanLeiBie());
//			ekp_H14_M_detailed.setFdSanJiBuMen(ekp_H14_M.getFdSanJiBuMen());
//			ekp_H14_M_detailed.setFdShenQingRiQi(ekp_H14_M.getFdShenQingRiQi());
//			ekp_H14_M_detailed.setFdSuoShuFenBu(ekp_H14_M.getFdSuoShuFenBu());
//			ekp_H14_M_detailed.setFdYiJiBuMen(ekp_H14_M.getFdYiJiBuMen());
//			ekp_H14_M_detailed.setFdYuanGongBianHao(ekp_H14_M.getFdYuanGongBianHao());
//			ekp_H14_M_detailed.setFdZhiJi(ekp_H14_M.getFdZhiJi());
//			ekp_H14_M_detailed.setFdZhiJieShangJi(ekp_H14_M.getFdZhiJieShangJi());
//			ekp_H14_M_detailed.setFdZhiJiXiShu(ekp_H14_M.getFdZhiJiXiShu());
//			ekp_H14_M_detailed.setFdZhiLei(ekp_H14_M.getFdZhiLei());
//			ekp_H14_M_detailed.setFdZiPingDeFen(ekp_H14_M.getFdZiPingDeFen());
//			ekp_H14_M_detailed.setFdZiPingDengJi(ekp_H14_M.getFdZiPingDengJi());
//		}
		
		return page;
	}
	   public List<String[]> modelToArrayList(List<Ekp_H14_M_detailed> useList) throws Exception {
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
//	        Map<String, Ekp_H14_M_detailed> ekp_H14_M_detailed = new HashMap<String, Ekp_H14_M_detailed>();
//	        if (null != hrStaffPersonInfoSettingNewList && !hrStaffPersonInfoSettingNewList.isEmpty()) {
//	            for (HrStaffPersonInfoSettingNew hrStaffPersonInfoSettingNew : hrStaffPersonInfoSettingNewList) {
//	                hrStaffPersonInfoSettingNewMap.put(hrStaffPersonInfoSettingNew.getFdId(), hrStaffPersonInfoSettingNew);
//	            }
//	        }

	        for (int i = 0; i < useList.size(); i++) {
	        	Ekp_H14_M_detailed ekp_H14_M_detailed = useList.get(i);
	            returnList.add(buildExcelDate(ekp_H14_M_detailed));
	        }
	     
	        return returnList;
	    }
	   private String[] buildExcelDate(Ekp_H14_M_detailed ekp_H14_M_detailed) throws Exception {

	        String[] listdata = new String[60];
	        String fdNo = ekp_H14_M_detailed.getFdBeiKaoHeRenXingMing() == null ? "" : ekp_H14_M_detailed.getFdBeiKaoHeRenXingMing().getFdNo();
	        String fdLoginName = ekp_H14_M_detailed.getFdBeiKaoHeRenXingMing().getFdLoginName();
//	        String fdStaffNo = ekp_H14_M.getFdStaffNo();//员工号
	        String fdName = ekp_H14_M_detailed.getFdBeiKaoHeRenXingMing().getFdName();//姓名
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
	        listdata[0] = ekp_H14_M_detailed.getFdId();
	        listdata[1] = ekp_H14_M_detailed.getFdBeiKaoHeRenXingMing().getFdName();
	        listdata[2] = ekp_H14_M_detailed.getFdSuoShuFenBu().getFdName();
	        listdata[3] = ekp_H14_M_detailed.getFdYiJiBuMen();
	        listdata[4] = ekp_H14_M_detailed.getFdErJiBuMen();
	        listdata[5] = ekp_H14_M_detailed.getFdSanJiBuMen();
	        listdata[6] = ekp_H14_M_detailed.getFdYuanGongBianHao();
	        listdata[7] = ekp_H14_M_detailed.getFdGangWeiMingChen();
	        listdata[8] = ekp_H14_M_detailed.getFdZhiLei();
	        listdata[9] = ekp_H14_M_detailed.getFdZhiJiXiShu();
	        listdata[10] = ekp_H14_M_detailed.getFdZhiJi();
	        listdata[11] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdRuZhiRiQi(),DateUtil.PATTERN_DATE);
	        listdata[12] = ekp_H14_M_detailed.getFdRenYuanLeiBie();
	        listdata[13] = ekp_H14_M_detailed.getFdGangWeiXingZhi();
	        listdata[14] = ekp_H14_M_detailed.getFdZhiJieShangJi();
	        listdata[15] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdShenQingRiQi(),DateUtil.PATTERN_DATE);
	        listdata[16] = ekp_H14_M_detailed.getFdKaoHeZhouQi();
	        listdata[17] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdKaoHeKaiShiShiJian(),DateUtil.PATTERN_DATE);
	        listdata[18] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdKaoHeJieShuShiJian(),DateUtil.PATTERN_DATE);
	        listdata[19] = ekp_H14_M_detailed.getFdZiPingDeFen()==null?"":ekp_H14_M_detailed.getFdZiPingDeFen().toString();
	        listdata[20] = ekp_H14_M_detailed.getFdZiPingDengJi();
	        listdata[21] = ekp_H14_M_detailed.getFdKaoPingWeiDu();
	        listdata[22] = ekp_H14_M_detailed.getFdKaoPingZhiBiao();
	        listdata[23] = ekp_H14_M_detailed.getFdMuBiaoZhi()==null?"":ekp_H14_M_detailed.getFdMuBiaoZhi().toString();
	        listdata[24] = ekp_H14_M_detailed.getFdQuanZhong()==null?"":ekp_H14_M_detailed.getFdQuanZhong().toString();
	        listdata[25] = ekp_H14_M_detailed.getFdShiJiZhi()==null?"":ekp_H14_M_detailed.getFdShiJiZhi().toString();
	        listdata[26] = ekp_H14_M_detailed.getFdJiXiaoDaChengQingKuangShuoM();
	        listdata[27] = ekp_H14_M_detailed.getFdZiPingDeFen1()==null?"":ekp_H14_M_detailed.getFdZiPingDeFen1().toString();
	        listdata[28] = ekp_H14_M_detailed.getFdGongZuoshixian1();
	        listdata[29] = ekp_H14_M_detailed.getFdGongZuoMuBiao1();
	        listdata[30] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdQiWangWanChengShiJian1(),DateUtil.PATTERN_DATE);
	        listdata[31] = ekp_H14_M_detailed.getFdGongZuoshixian3();
	        listdata[32] = ekp_H14_M_detailed.getFdGongZuoMuBiao3();
	        listdata[33] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdQiWangWanChengShiJian3(),DateUtil.PATTERN_DATE);
	        listdata[34] = ekp_H14_M_detailed.getFdGongZuoshixian2();
	        listdata[35] = ekp_H14_M_detailed.getFdGongZuoMuBiao2();
	        listdata[36] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdQiWangWanChengShiJian2(),DateUtil.PATTERN_DATE);
	        listdata[37] = DateUtil.convertDateToString(ekp_H14_M_detailed.getFdKaohenianfen(),DateUtil.PATTERN_DATE);
	        listdata[38] = ekp_H14_M_detailed.getFdZhiJieShangJiPingJia();
	        listdata[39] = ekp_H14_M_detailed.getFdZhiJieShangJiKaoHeDeFen();
	        listdata[40] = ekp_H14_M_detailed.getFdZhiJieShangJiKaoHeDengJi();
	        listdata[41] = ekp_H14_M_detailed.getFdErJiBuMenFuZeRenPingYu();
	        listdata[42] = ekp_H14_M_detailed.getFdErJiBuMenFuZeRenKaoHeDeFen();
	        listdata[43] = ekp_H14_M_detailed.getFdErJiBuMenFuZeRenKaoHeDengJi();
	        listdata[44] = ekp_H14_M_detailed.getFd3b267ef4984e98_text();
	        listdata[45] = ekp_H14_M_detailed.getFdYiJiBuMenFuZeRenPingYu();
	        listdata[46] = ekp_H14_M_detailed.getFdYiJiBuMenFuZeRenKaoHeDeFen();
	        listdata[47] = ekp_H14_M_detailed.getFdYiJiBuMenFuZeRenKaoHeDengJi();
	        listdata[48] = ekp_H14_M_detailed.getFdXinChouJiXiaoFuZeRenShenPi();
	        listdata[49] = ekp_H14_M_detailed.getFdRenLiXingZhengZhongXinFuZeR();
	        listdata[50] = ekp_H14_M_detailed.getFdFenGuanFuZeRenShenPi();
	        listdata[51] = ekp_H14_M_detailed.getFdFenGuanFuZeRenKaoHePingFen();
	        listdata[52] = ekp_H14_M_detailed.getFdFenGuanFuZeRenKaoHeDengJi();
	        listdata[53] = ekp_H14_M_detailed.getFdZongCaiShenPi();
	        listdata[54] = ekp_H14_M_detailed.getFdZongCaiKaoHePingFen();
	        listdata[55] = ekp_H14_M_detailed.getFdZongCaiKaoHeDengJi();
	        listdata[56] = ekp_H14_M_detailed.getFdDongShiChangShenPi();
	        listdata[57] = ekp_H14_M_detailed.getFdDongShiChangKaoHeDengJi();
	        listdata[58] = ekp_H14_M_detailed.getFdDongShiChangKaoHePingFen();
		                     

	        return listdata;
	    }
	    @Override
	    @SuppressWarnings("unchecked")
	    public void exportList(HttpServletRequest request,
	                           HttpServletResponse response, List<Ekp_H14_M_detailed> rtnList) {
	        try {

	            List<String[]> modelList = this.modelToArrayList(rtnList);
	            String fileName = ResourceUtil.getString("ekp_H14_M.exportFileName", "hr.staff");
//				fileName = URLEncoder.encode(fileName, "UTF-8");
	            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	            } else {
	                fileName = URLEncoder.encode(fileName, "UTF-8");
	            }
	            // 类名 用于
	            String className = Ekp_H14_M.class.getName();
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
			        		"hrStaffPerformanceSearch.fdBeiKaoHeRenXingMing",
		    				"hrStaffPerformanceSearch.fdSuoShuFenBu",
	        				"hrStaffPerformanceSearch.fdYiJiBuMen",
	        				"hrStaffPerformanceSearch.fdErJiBuMen",
	        				"hrStaffPerformanceSearch.fdSanJiBuMen",
	        				"hrStaffPerformanceSearch.fdYuanGongBianHao",
	        				"hrStaffPerformanceSearch.fdGangWeiMingChen",
			        		"hrStaffPerformanceSearch.fdZhiLei",
	        				"hrStaffPerformanceSearch.fdZhiJiXiShu",
	        				"hrStaffPerformanceSearch.fdZhiJi",
	        				"hrStaffPerformanceSearch.fdRuZhiRiQi",
	        				"hrStaffPerformanceSearch.fdRenYuanLeiBie",
	        				"hrStaffPerformanceSearch.fdGangWeiXingZhi",
	        				"hrStaffPerformanceSearch.fdZhiJieShangJi",
	        				"hrStaffPerformanceSearch.fdShenQingRiQi",
	        				"hrStaffPerformanceSearch.fdKaoHeZhouQi",
	        				"hrStaffPerformanceSearch.fdKaoHeKaiShiShiJian",
	        				"hrStaffPerformanceSearch.fdKaoHeJieShuShiJian",
	        				"hrStaffPerformanceSearch.fdZiPingDeFen",
	        				"hrStaffPerformanceSearch.fdZiPingDengJi",
	        				"hrStaffPerformanceSearch.fdKaoPingWeiDu",
	        				"hrStaffPerformanceSearch.fdKaoPingZhiBiao",
	        				"hrStaffPerformanceSearch.fdMuBiaoZhi",
	        				"hrStaffPerformanceSearch.fdQuanZhong",
	        				"hrStaffPerformanceSearch.fdShiJiZhi",
	        				"hrStaffPerformanceSearch.fdJiXiaoDaChengQingKuangShuoM",
	        				"hrStaffPerformanceSearch.fdZiPingDeFen1",
			        		"hrStaffPerformanceSearch.fdGongZuoshixian1",
		    				"hrStaffPerformanceSearch.fdGongZuoMuBiao1",
	        				"hrStaffPerformanceSearch.fdQiWangWanChengShiJian1",
	        				"hrStaffPerformanceSearch.fdGongZuoshixian3",
	        				"hrStaffPerformanceSearch.fdGongZuoMuBiao3",
	        				"hrStaffPerformanceSearch.fdQiWangWanChengShiJian3",
	        				"hrStaffPerformanceSearch.fdGongZuoshixian2",
			        		"hrStaffPerformanceSearch.fdGongZuoMuBiao2",
	        				"hrStaffPerformanceSearch.fdQiWangWanChengShiJian2",
	        				"hrStaffPerformanceSearch.fdKaohenianfen",
	        				"hrStaffPerformanceSearch.fdZhiJieShangJiPingJia",
	        				"hrStaffPerformanceSearch.fdZhiJieShangJiKaoHeDeFen",
	        				"hrStaffPerformanceSearch.fdZhiJieShangJiKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fdErJiBuMenFuZeRenPingYu",
	        				"hrStaffPerformanceSearch.fdErJiBuMenFuZeRenKaoHeDeFen",
	        				"hrStaffPerformanceSearch.fdErJiBuMenFuZeRenKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fd3b267ef4984e98_text",
	        				"hrStaffPerformanceSearch.fdYiJiBuMenFuZeRenPingYu",
	        				"hrStaffPerformanceSearch.fdYiJiBuMenFuZeRenKaoHeDeFen",
	        				"hrStaffPerformanceSearch.fdYiJiBuMenFuZeRenKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fdXinChouJiXiaoFuZeRenShenPi",
	        				"hrStaffPerformanceSearch.fdRenLiXingZhengZhongXinFuZeR",
	        				"hrStaffPerformanceSearch.fdFenGuanFuZeRenShenPi",
	        				"hrStaffPerformanceSearch.fdFenGuanFuZeRenKaoHePingFen",
	        				"hrStaffPerformanceSearch.fdFenGuanFuZeRenKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fdZongCaiShenPi",
	        				"hrStaffPerformanceSearch.fdZongCaiKaoHePingFen",
	        				"hrStaffPerformanceSearch.fdZongCaiKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fdDongShiChangShenPi",
	        				"hrStaffPerformanceSearch.fdDongShiChangKaoHeDengJi",
	        				"hrStaffPerformanceSearch.fdDongShiChangKaoHePingFen",
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
				"fdFirstLevelDepartment","fdSecondLevelDepartment","fdThirdLevelDepartment",
				"fdJobNature","fdBeginDate","fdExpiryDate","fdEvaluationDimension","fdEvaluationIndex","fdTargetValue","fdWeight"
				};
	}

	@SuppressWarnings("unchecked")
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 妫�鏌ヨ鍛樺伐鏄惁宸茬粡鏈夋暟鎹�
		HrStaffPerformanceSearch model = (HrStaffPerformanceSearch) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", model.getFdId());
		List<HrStaffPerformanceSearch> list = findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			throw new KmssException(new KmssMessage(ResourceUtil
					.getString("hr-staff:hrStaffPerformanceSearch.exist")));
		}

		return super.add(modelObj);
	}
}
