<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<%-- 标签页标题--%>
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	
	<%--导航路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffSocialSecurity')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<script type="text/javascript">
			Com_IncludeFile("calendar.js",null,"js");
			seajs.use(['lui/jquery','sys/ui/js/dialog'],function($,dialog){
				//删除
				window.deleteDoc=function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};

				window.sendEmailChoose=function(val){
					if(val && val.indexOf('email') > -1){
						$("#sendEmailChoose").show();
					}else{
						$("#sendEmailChoose").hide();
					}
				};
			});
		</script>  
		
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--删除--%>
			<ui:button text="${lfn:message('button.delete') }" onclick="deleteDoc('hrStaffSecurity.do?method=delete&fdId=${param.fdId}');" order="4">
			</ui:button>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<%--资源借用单信息--%>
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_security/hrStaffSecurity.do">		
			<html:hidden property="fdId" />
			<div class="lui_form_content_frame">
				<p class="lui_form_subject">
					<bean:message bundle="hr-staff" key="table.hrStaffSocialSecurity" />
				</p>
				<table class="tb_normal" width=100%>

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
			</div>
			</html:form>
	</template:replace>
</template:include>