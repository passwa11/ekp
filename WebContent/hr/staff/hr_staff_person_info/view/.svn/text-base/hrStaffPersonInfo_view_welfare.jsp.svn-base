<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="lui-personnel-file-staffInfo" id="salaryAdjustRecord">
    <div class="lui-personnel-file-header-title">
        <div class="lui-personnel-file-header-title-left">
            <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:hr.staff.nav.benefits') }</div>
        </div>
        
    </div>
    <table>
        <tbody>
        <tr>
            <td width="160"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollName" /></td><td width="240">${ hrStaffEmolumentWelfare.fdPayrollName }</td>
            <td width="160"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollBank" /></td><td width="160">${ hrStaffEmolumentWelfare.fdPayrollBank }</td>
        </tr>
        <tr>
            <td width="160"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollAccount" /></td><td width="240">${ hrStaffEmolumentWelfare.fdPayrollAccount }</td>
            <td width="160"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSurplusAccount" /></td><td width="160">${ hrStaffEmolumentWelfare.fdSurplusAccount }</td>
        </tr>
        <tr>
            <td width="160"><bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSocialSecurityNumber" /></td><td width="240">${ hrStaffEmolumentWelfare.fdSocialSecurityNumber }</td>
            <td width="160"><bean:message bundle="hr-staff" key="" /></td><td width="160"></td>
        </tr>
        </tbody>
    </table>
    <div class="salaryAdjustRecordList">
        <list:listview channel="hrStaffEmolumentWelfareDetalied">
            <ui:source type="AjaxJson">
                {url:'/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfareDetalied.do?method=list&personInfoId=${ JsParam.personInfoId }'}
            </ui:source>
            <list:colTable isDefault="false" layout="sys.ui.listview.columntable" channel="hrStaffEmolumentWelfareDetalied" name="columntable">
                <list:col-serial></list:col-serial>
                <list:col-auto props="fdRelatedProcess;fdAdjustDate;fdBeforeEmolument;fdAdjustAmount;fdAfterEmolument;fdIsEffective"></list:col-auto>
            </list:colTable>
        </list:listview>
        <list:paging channel="hrStaffEmolumentWelfareDetalied" />
    </div>
</div>
<script>
    seajs.use(['lui/topic'],function(topic){
        topic.publish('welfare/onCompleted',true);
    });
</script>