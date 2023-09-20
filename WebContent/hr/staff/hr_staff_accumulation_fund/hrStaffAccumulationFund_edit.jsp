<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffAccumulationFundForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffAccumulationFund') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffAccumulationFundForm.fdPersonInfoName } - ${ lfn:message('hr-staff:table.hrStaffAccumulationFund') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffAccumulationFundForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="Com_Submit(document.hrStaffAccumulationFundForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffAccumulationFundForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="Com_Submit(document.hrStaffAccumulationFundForm, 'update');" order="1">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffAccumulationFund') }" href="/hr/staff/hr_staff_accumulation_fund/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<div class="lui_form_content_frame">
		<html:form action="/hr/staff/hr_staff_accumulation_fund/hrStaffAccumulationFund.do" >
			<html:hidden property="fdId" />
			<html:hidden property="fdPersonInfoId" />
			<html:hidden property="fdPersonInfoName" />
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffAccumulationFundForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffAccumulationFund') }"></c:out>	
						</c:when>
						<c:otherwise>
							${ hrStaffAccumulationFundForm.fdPersonInfoName }
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<table class="tb_normal" width=98%>
				<tr>
					<!-- 姓名 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
					</td>
					<td width="35%">
						<c:choose>
							<c:when test="${ hrStaffAccumulationFundForm.method_GET == 'add' }">
								<xform:address propertyId="fdOrgPersonId" idValue="${ hrStaffAccumulationFundForm.fdPersonInfoId }" 
								propertyName="fdOrgPersonName" nameValue="${ hrStaffAccumulationFundForm.fdPersonInfoName }" 
								validators="required" orgType="ORG_TYPE_PERSON" style="width:95%" onValueChange="personInfoChange"></xform:address>
								<span class="txtstrong">*</span>
							</c:when>
							<c:otherwise>
								<xform:text property="fdPersonInfoId" showStatus="noShow"></xform:text>
								<xform:text property="fdPersonInfoName" showStatus="view"></xform:text>
							</c:otherwise>
						</c:choose>
						
					</td>
					<!-- 工资账户名 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdIdCard" />
					</td>
					<td width="35%">
						<xform:text property="fdIdCard" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					
					
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdPlaceOfInsurancePayment" />
					</td>
					<td width="35%">
						<xform:text property="fdPlaceOfInsurancePayment" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdRemark" />
					</td>
					<td width="35%">
						<xform:text property="fdRemark" style="width:95%;" className="inputsgl" />
					</td>
					
				</tr>
				
				
				
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdIndividualProvidentFundAccount" />
					</td>
					<td width="35%">
						<xform:text property="fdIndividualProvidentFundAccount" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdProvidentFundInsuranceCompany" />
					</td>
					<td width="35%">
						<xform:text property="fdProvidentFundInsuranceCompany" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdPaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdPaymentBase" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdPersonalDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdPersonalDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdDeliveryAmount" />
					</td>
					<td width="35%">
						<xform:text property="fdDeliveryAmount" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAccumulationFund.fdDeliveryDate" />
					</td>
					<td width="35%">
							<xform:datetime property="fdDeliveryDate" dateTimeType="date"></xform:datetime>
			                  </td>
					</tr>
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			$KMSSValidation(document.forms['hrStaffAccumulationFundForm']);

			// 修改员工信息回调
			function personInfoChange(value) {
				var fdPersonInfoId = value[0];
				$("input[name=fdPersonInfoId]").val(fdPersonInfoId);
				$.post("${LUI_ContextPath}/hr/staff/hr_staff_accumulation_fund/hrStaffAccumulationFund.do?method=checkPerson",
						{"fdPersonInfoId": fdPersonInfoId}, function(value) {
					if(!value.isOk) {
						seajs.use(['lui/dialog'], function(dialog) {
							dialog.alert(value.message);
							$("input[name=fdPersonInfoId]").val('');
							$("input[name=fdOrgPersonId]").val('');
							$("input[name=fdOrgPersonName]").val('');
						});
					}
				}, "json");
			}
		</script>
	</template:replace>
</template:include>