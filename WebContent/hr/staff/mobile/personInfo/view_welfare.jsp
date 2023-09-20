<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="personal_profile_content_card">
	<div class="ppc_c_title">
      <div>
        <i class="ppc_c_title-icon"></i>
        <span><bean:message bundle="hr-staff" key="hr.staff.nav.benefits"/></span>
      </div>
      <i class="ppc_c_more-icon" onclick="expandContent(this);"></i>
    </div>
    <div class="ppc_c_content">
        <div class="ppc_c_list">
          <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollName" /></div>
          <div class="ppc_c_list_body">
    		${ hrStaffEmolumentWelfare.fdPayrollName }
       </div>
     </div>
     <div class="ppc_c_list">
       <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollBank" /></div>
       <div class="ppc_c_list_body">
       	${ hrStaffEmolumentWelfare.fdPayrollBank }
       </div>
     </div>
     <div class="ppc_c_list">
       <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollAccount" /></div>
       <div class="ppc_c_list_body">
       	${ hrStaffEmolumentWelfare.fdPayrollAccount }
       </div>
     </div>
     <div class="ppc_c_list">
       <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSurplusAccount" /></div>
       <div class="ppc_c_list_body">
       	${ hrStaffEmolumentWelfare.fdSurplusAccount }
       </div>
     </div>
     <div class="ppc_c_list">
       <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSocialSecurityNumber" /></div>
       <div class="ppc_c_list_body">
       	${ hrStaffEmolumentWelfare.fdSocialSecurityNumber }
       </div>
     </div>
   </div> 
</div>
<div class="personal_profile_content_card" data-dojo-type="mui/list/JsonStoreList"
	data-dojo-mixins="hr/staff/mobile/resource/js/list/HrStaffSalaryAdjustRecordListMixin"
	data-dojo-props="url:'/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfareDetalied.do?method=list&personInfoId=${ JsParam.personInfoId }',lazy:false">
	<div class="ppc_c_title">
		<div>
			<i class="ppc_c_title-icon"></i>
	        <span><bean:message bundle="hr-staff" key="table.hrStaffEmolumentWelfareDetalied"/></span>
	    </div>
  	</div>
</div>