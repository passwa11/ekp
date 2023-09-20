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
		<html:form action="/hr/staff/hr_staff_person_experience/brief/hrStaffPersonExperienceBrief.do?method=save" >
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<input type="hidden" name="fdPersonInfoId" value="${HtmlParam.personInfoId}" />
				<tr>
					<td>
						<textarea name="fdContent"  subject="${ lfn:message('hr-staff:hrStaffPersonExperience.type.brief') }"  placeholder="${ lfn:message('hr-staff:hrStaffPersonExperience.type.brief.tips') }" validate="required maxLength(2000)" style="width:98%;height:200px;">${hrStaffPersonExperienceBriefForm.fdContent}</textarea><span class="txtstrong">*</span>
					</td>
				</tr>
			</table>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</center>
	</template:replace>
</template:include>
