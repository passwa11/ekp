<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffEmolumentWelfareForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffEmolumentWelfareForm.fdPersonInfoName } - ${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffEmolumentWelfareForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="Com_Submit(document.hrStaffEmolumentWelfareForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffEmolumentWelfareForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="Com_Submit(document.hrStaffEmolumentWelfareForm, 'update');" order="1">
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
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }" href="/hr/staff/hr_staff_emolument_welfare/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<div class="lui_form_content_frame">
		<html:form action="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do" >
			<html:hidden property="fdId" />
			<html:hidden property="fdPersonInfoId" />
			<html:hidden property="fdPersonInfoName" />
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffEmolumentWelfareForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }"></c:out>	
						</c:when>
						<c:otherwise>
							${ hrStaffEmolumentWelfareForm.fdPersonInfoName }
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
							<c:when test="${ hrStaffEmolumentWelfareForm.method_GET == 'add' }">
								<xform:address propertyId="fdOrgPersonId" idValue="${ hrStaffEmolumentWelfareForm.fdPersonInfoId }" 
								propertyName="fdOrgPersonName" nameValue="${ hrStaffEmolumentWelfareForm.fdPersonInfoName }" 
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
						<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollName" />
					</td>
					<td width="35%">
						<xform:text property="fdPayrollName" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 工资银行 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollBank" />
					</td>
					<td width="35%">
						<xform:text property="fdPayrollBank" style="width:95%;" className="inputsgl" />
					</td>
					<!-- 工资账号 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdPayrollAccount" />
					</td>
					<td width="35%">
						<xform:text property="fdPayrollAccount" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 公积金账户 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSurplusAccount" />
					</td>
					<td width="35%">
						<xform:text property="fdSurplusAccount" style="width:95%;" className="inputsgl" />
					</td>
					<!-- 社保号码 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffEmolumentWelfare.fdSocialSecurityNumber" />
					</td>
					<td width="35%">
						<xform:text property="fdSocialSecurityNumber" style="width:95%;" className="inputsgl" />
					</td>
				</tr>
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			$KMSSValidation(document.forms['hrStaffEmolumentWelfareForm']);

			// 修改员工信息回调
			function personInfoChange(value) {
				var fdPersonInfoId = value[0];
				$("input[name=fdPersonInfoId]").val(fdPersonInfoId);
				$.post("${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=checkPerson",
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