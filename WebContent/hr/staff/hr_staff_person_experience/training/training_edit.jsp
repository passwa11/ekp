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
		<html:form action="/hr/staff/hr_staff_person_experience/training/hrStaffPersonExperienceTraining.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 培训名称-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.training.fdTrainingName') }
					</td>
					<td width="35%">
						<xform:text property="fdTrainingName" style="width:95%;"/>
					</td>
					<!-- 培训单位-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.training.fdTrainingUnit') }
					</td>
					<td width="35%">
						<xform:text property="fdTrainingUnit" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 开始时间-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.fdBeginDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdBeginDate" dateTimeType="date" validators="compareDate"></xform:datetime>
					</td>
					<!-- 结束时间-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.fdEndDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdEndDate" dateTimeType="date" validators="compareDate"></xform:datetime>
					</td>
				</tr>
				<tr>
					<!-- 培训证书-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffTrackRecord.fdTrainingCertificate') }
					</td>
					<td colspan="3">
						<xform:textarea property="fdCertificate" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<!-- 备注-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.fdMemo') }
					</td>
					<td colspan="3">
						<xform:textarea property="fdMemo" style="width:98%;height:50px;"/>
					</td>
				</tr>
			</table>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</center>
	</template:replace>
</template:include>
