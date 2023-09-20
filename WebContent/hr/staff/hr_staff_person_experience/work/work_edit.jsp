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
		<html:form action="/hr/staff/hr_staff_person_experience/work/hrStaffPersonExperienceWork.do?method=save" >
			<html:hidden property="fdId"/>
			<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 公司-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdCompany') }
					</td>
					<td width="35%">
						<xform:text property="fdCompany" style="width:95%;"/>
					</td>
					<!-- 职位-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdPosition') }
					</td>
					<td width="35%">
						<xform:text property="fdPosition" style="width:98%;"/>
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
					<!-- 工作描述-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdDescription') }
					</td>
					<td colspan="3">
						<xform:textarea property="fdDescription" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<!-- 离开原因-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.work.fdReasons') }
					</td>
					<td colspan="3">
						<xform:textarea property="fdReasons" style="width:98%;height:50px;"/>
					</td>
				</tr>
			</table>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</center>
	</template:replace>
</template:include>
