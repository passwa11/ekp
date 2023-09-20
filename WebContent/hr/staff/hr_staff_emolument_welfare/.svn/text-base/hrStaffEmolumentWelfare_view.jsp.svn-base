<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:content expand="${'emolumentWelfare' eq JsParam.anchor ? 'true' : 'false'}" title="${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }">
	<table class="staff_resume_simple_tb" id="emolumentWelfare">
		<tr>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollName" />
			</td>
			<td width="35%">
				${ hrStaffEmolumentWelfare.fdPayrollName }
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollBank" />
			</td>
			<td width="35%">
				${ hrStaffEmolumentWelfare.fdPayrollBank }
			</td>
		</tr>
		<tr>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollAccount" />
			</td>
			<td width="35%">
				${ hrStaffEmolumentWelfare.fdPayrollAccount }
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSurplusAccount" />
			</td>
			<td width="35%">
				${ hrStaffEmolumentWelfare.fdSurplusAccount }
			</td>
		</tr>
		<tr>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSocialSecurityNumber" />
			</td>
			<td colspan="3">
				${ hrStaffEmolumentWelfare.fdSocialSecurityNumber }
			</td>
		</tr>
		
		<tr class="tr_normal_title">
			<td align="left" colspan="4">
				<label>
					<input type="checkbox" checked="true" onclick="this.checked ? $('#hrStaffEmolumentWelfareDetalied').show() : $('#hrStaffEmolumentWelfareDetalied').hide();">
					<bean:message bundle="hr-staff" key="table.hrStaffEmolumentWelfareDetalied" />
				</label>
			</td>
		</tr>
		<tr id="hrStaffEmolumentWelfareDetalied">
			<td colspan="4">
				<list:listview channel="hrStaffEmolumentWelfareDetalied">
					<ui:source type="AjaxJson">
						{url:'/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfareDetalied.do?method=list&personInfoId=${ JsParam.personInfoId }'}
					</ui:source>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" channel="hrStaffEmolumentWelfareDetalied" name="columntable">
						<list:col-serial></list:col-serial> 
						<list:col-auto props="fdRelatedProcess;fdAdjustDate;fdBeforeEmolument;fdAdjustAmount;fdAfterEmolument"></list:col-auto>
					</list:colTable>
				</list:listview> 
				<list:paging channel="hrStaffEmolumentWelfareDetalied" />
			</td>
		</tr>
	</table>
</ui:content>
