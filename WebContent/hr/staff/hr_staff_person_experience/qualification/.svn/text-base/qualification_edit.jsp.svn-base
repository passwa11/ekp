<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/hr/staff/hr_staff_person_experience/qualification/hrStaffPersonExperienceQualification.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 证书名称-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdCertificateName') }
					</td>
					<td colspan="3">
						<xform:text property="fdCertificateName" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 颁发日期-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdBeginDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdBeginDate" dateTimeType="date" validators="compareDate"></xform:datetime>
					</td>
					<!-- 失效日期-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdEndDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdEndDate" dateTimeType="date" validators="compareDate"></xform:datetime>
					</td>
				</tr>
				<tr>
					<!-- 颁发单位-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.qualification.fdAwardUnit') }
					</td>
					<td colspan="3">
						<xform:text property="fdAwardUnit" style="width:98%;"/>
					</td>
				</tr>
			</table>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</center>
	</template:replace>
</template:include>
