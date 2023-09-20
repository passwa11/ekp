<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/hr/staff/hr_staff_person_experience/education/hrStaffPersonExperienceEducation.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 学校名称-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdSchoolName') }
					</td>
					<td width="30%">
						<xform:text property="fdSchoolName" style="width:90%;"/>
					</td>
					<!-- 专业-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdMajor') }
					</td>
					<td width="30%">
						<xform:text property="fdMajor" style="width:90%;" required="true"/>
					</td>
				</tr>
				<tr>
					<!-- 学历-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hr.staff.tree.education') }
					</td>
					<td width="35%">
						<%=HrStaffPersonUtil.buildEducationHtml("fdEducation", request)%>
					</td>
					<!-- 学位-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdDegree') }
					</td>
					<td width="35%">
						<!-- fdHighestDegree -->
						<%=HrStaffPersonUtil.buildDegreeHtml("fdDegree", request)%>
					</td>
				</tr>
				
				<tr>
					<!-- 入学时间-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdBeginDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdBeginDate" dateTimeType="date" validators="compareDate" required="true"></xform:datetime>
					</td>
					<!-- 毕业时间-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.education.fdEndDate') }
					</td>
					<td width="35%">
						<xform:datetime property="fdEndDate" dateTimeType="date" validators="compareDate" required="true"></xform:datetime>
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
