<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffAttendanceManageForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffAttendanceManageForm.fdPersonInfoName } - ${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffAttendanceManageForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="Com_Submit(document.hrStaffAttendanceManageForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffAttendanceManageForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="Com_Submit(document.hrStaffAttendanceManageForm, 'update');" order="1">
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
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }" href="/hr/staff/hr_staff_attendance_manage/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<div class="lui_form_content_frame">
		<html:form action="/hr/staff/hr_staff_attendance_manage/hrStaffAttendanceManage.do" >
			<html:hidden property="fdId" />
			<html:hidden property="fdPersonInfoId" />
			<html:hidden property="fdPersonInfoName" />
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffAttendanceManageForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffAttendanceManage') }"></c:out>	
						</c:when>
						<c:otherwise>
							<c:out value="${ lfn:message('button.edit') } - ${ lfn:message('hr-staff:table.hrStaffAttendanceManage') } (${ hrStaffAttendanceManageForm.fdPersonInfoName })"></c:out>	
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
							<c:when test="${ hrStaffAttendanceManageForm.method_GET == 'add' }">
								<xform:address propertyId="fdOrgPersonId" idValue="${ hrStaffAttendanceManageForm.fdPersonInfoId }" 
								propertyName="fdOrgPersonName" nameValue="${ hrStaffAttendanceManageForm.fdPersonInfoName }" 
								validators="required" orgType="ORG_TYPE_PERSON" style="width:95%" onValueChange="personInfoChange"></xform:address>
								<span class="txtstrong">*</span>
							</c:when>
							<c:otherwise>
								<xform:text property="fdPersonInfoId" showStatus="noShow"></xform:text>
								<xform:text property="fdPersonInfoName" showStatus="view"></xform:text>
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 年份 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.fdYear" />
					</td>
					<td width="35%">
						<xform:text property="fdYear" style="width:95%;" validators="required digits min(0)" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 失效日期 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.fdExpirationDate" />
					</td>
					<td width="35%">
						<xform:datetime property="fdExpirationDate" dateTimeType="date"></xform:datetime>
					</td>
					<!-- 剩余年假天数 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.fdDaysOfAnnualLeave" />
					</td>
					<td width="35%">
						<xform:text property="fdDaysOfAnnualLeave" style="width:95%;" validators="number" className="inputsgl" />
					</td>
				</tr>
				<tr>
					<!-- 剩余调休天数 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.fdDaysOfTakeWorking" />
					</td>
					<td width="35%">
						<xform:text property="fdDaysOfTakeWorking" style="width:95%;" validators="number" className="inputsgl" />
					</td>
					<!-- 剩余带薪病假天数 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffAttendanceManage.fdDaysOfSickLeave" />
					</td>
					<td width="35%">
						<xform:text property="fdDaysOfSickLeave" style="width:95%;" validators="number" className="inputsgl" />
					</td>
				</tr>
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			$KMSSValidation(document.forms['hrStaffAttendanceManageForm']);

			// 修改员工信息回调
			function personInfoChange(value) {
				var fdPersonInfoId = value[0];
				$("input[name=fdPersonInfoId]").val(fdPersonInfoId);
				$.post("${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=isExist",
						{"fdPersonInfoId": fdPersonInfoId}, function(value) {
					if(value == 'false') {
						seajs.use(['lui/dialog'], function(dialog) {
							dialog.alert('<bean:message key="hrStaffEmolumentWelfare.fdPersonInfo.nofind" bundle="hr-staff"/>');
							$("input[name=fdPersonInfoId]").val('');
							$("input[name=fdOrgPersonId]").val('');
							$("input[name=fdOrgPersonName]").val('');
						});
					}
				});
			}
		</script>
	</template:replace>
</template:include>