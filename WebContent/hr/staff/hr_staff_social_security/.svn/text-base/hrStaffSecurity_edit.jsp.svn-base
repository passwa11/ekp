<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffSecurityForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffSecurity') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffSecurityForm.fdPersonInfoName } - ${ lfn:message('hr-staff:table.hrStaffSecurity') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffSecurityForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="Com_Submit(document.hrStaffSecurityForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffSecurityForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="Com_Submit(document.hrStaffSecurityForm, 'update');" order="1">
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
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffSocialSecurity') }" href="/hr/staff/hr_staff_security/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<div class="lui_form_content_frame">
		<html:form action="/hr/staff/hr_staff_security/hrStaffSecurity.do" >
			<html:hidden property="fdId" />
			<html:hidden property="fdPersonInfoId" />
			<html:hidden property="fdPersonInfoName" />
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffSecurityForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffSecurity') }"></c:out>	
						</c:when>
						<c:otherwise>
							${ hrStaffSecurityForm.fdPersonInfoName }
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
							<c:when test="${ hrStaffSecurityForm.method_GET == 'add' }">
								<xform:address propertyId="fdOrgPersonId" idValue="${ hrStaffSecurityForm.fdPersonInfoId }" 
								propertyName="fdOrgPersonName" nameValue="${ hrStaffSecurityForm.fdPersonInfoName }" 
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
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdIdCard" />
					</td>
					<td width="35%">
						<xform:text property="fdIdCard" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					
					<!-- 工资账号 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdDisabilityInsurance" />
					</td>
					<td width="35%">
						<xform:text property="fdDisabilityInsurance" style="width:95%;" className="inputsgl" />
					</td>
					<!-- 社保号码 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdPlaceOfInsurancePayment" />
					</td>
					<td width="35%">
						<xform:text property="fdPlaceOfInsurancePayment" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdSocialInsuranceCompany" />
					</td>
					<td width="35%">
						<xform:text property="fdSocialInsuranceCompany" style="width:95%;" className="inputsgl" />
					</td>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdRemark" />
					</td>
					<td width="35%">
						<xform:text property="fdRemark" style="width:95%;" className="inputsgl" />
					</td>
					
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdAmountReceivablePersonalTotal" />
					</td>
					<td width="35%">
						<xform:text property="fdAmountReceivablePersonalTotal" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdAmountReceivableUnitTotal" />
					</td>
					<td width="35%">
						<xform:text property="fdAmountReceivableUnitTotal" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
				
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdAmountReceivableTotalReceivable" />
					</td>
					<td width="35%">
						<xform:text property="fdAmountReceivableTotalReceivable" style="width:95%;" className="inputsgl" />
					</td>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdEndowmentInsurancePaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdEndowmentInsurancePaymentBase" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdEndowmentInsurancePersonalDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdEndowmentInsurancePersonalDelivery" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdEndowmentInsuranceUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdEndowmentInsuranceUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdIndustrialAndCommercialInsurancePaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdIndustrialAndCommercialInsurancePaymentBase" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdIndustrialAndCommercialInsuranceUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdIndustrialAndCommercialInsuranceUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdUnemploymentInsurancePaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdUnemploymentInsurancePaymentBase" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdUnemploymentInsurancePersonalDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdUnemploymentInsurancePersonalDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdUnemploymentInsuranceUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdUnemploymentInsuranceUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdReproductiveMedicinePaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdReproductiveMedicinePaymentBase" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdReproductiveMedicinePersonalDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdReproductiveMedicinePersonalDelivery" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdReproductiveMedicineUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdReproductiveMedicineUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdBirthPaymentBase" />
					</td>
					<td width="35%">
						<xform:text property="fdBirthPaymentBase" style="width:95%;" className="inputsgl" />
					</td>
					
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdBirthUnitDelivery" />
					</td>
					<td width="35%">
						<xform:text property="fdBirthUnitDelivery" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
				
					<!-- 工资账户名 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffSecurity.fdAccount" />
					</td>
					<td width="35%">
						<xform:text property="fdAccount" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			$KMSSValidation(document.forms['hrStaffSecurityForm']);

			// 修改员工信息回调
			function personInfoChange(value) {
				var fdPersonInfoId = value[0];
				$("input[name=fdPersonInfoId]").val(fdPersonInfoId);
				$.post("${LUI_ContextPath}/hr/staff/hr_staff_security/hrStaffSecurity.do?method=checkPerson",
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