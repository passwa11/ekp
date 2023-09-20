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
		<html:form action="/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${param.fdPersonInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 家庭关系-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.related') }
					</td>
					<td colspan="3">
						<xform:text property="fdRelated" required="true" style="width:95%;"/>
					</td>
				</tr>
				<tr>
					<!-- 名字 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.name') }
					</td>
					<td width="35%">
						<xform:text property="fdName" required="true" style="width:95%;"/>
					</td>
					<!-- 职业 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.occupation') }
					</td>
					<td width="35%">
						<xform:text property="fdOccupation" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 任职单位 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.company') }
					</td>
					<td width="35%">
						<xform:text property="fdCompany" style="width:98%;"/>
					</td>
					<!-- 联系信息 -->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.connect') }
					</td>
					<td width="35%">
						<xform:text property="fdConnect" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 备注-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPerson.family.fdMemo') }
					</td>
					<td colspan="3">
						<xform:text property="fdMemo" style="width:98%;height:50px;"/>
					</td>
				</tr>
			</table>
			<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</html:form>
		
		</center>
	</template:replace>
</template:include>
