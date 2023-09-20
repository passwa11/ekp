<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style>
			.inputselectsgl{
				width:200px!important;
			}
			.hr_select{
				width:150px;
			}
		</style>
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<center>
		<html:form action="/hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<!-- 奖惩名称-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusMalusName') }
					</td>
					<td colspan="3">
						<xform:text property="fdBonusMalusName" style="width:98%;"/>
					</td>
				</tr>
				<tr>
					<!-- 奖惩日期-->
					<td width="15%" class="td_normal_title">
						${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusMalusDate') }
					</td>
					<td width:="35%">
						<xform:datetime property="fdBonusMalusDate" dateTimeType="date"></xform:datetime>
					</td>
					<td width="15%">
						${ lfn:message('hr-staff:hrStaffPersonExperience.bonusMalus.fdBonusType') }
					</td>
					<td width="35%">
						<%=HrStaffPersonUtil.buildBonusMalusTypeHtml("fdBonusMalusType", request)%>
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