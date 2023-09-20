<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="title">
		${lfn:message('hr-staff:hr.staff.tree.privacy.settings') }
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<kmss:auth requestURL="/hr/staff/hr_staff_private_change/hrStaffPrivateChange.do?method=edit">
				<ui:button onclick="Com_Submit(document.hrStaffPrivateChangeForm,'updatePrivate');" 
						   text="${lfn:message('button.submit') }"></ui:button>
			</kmss:auth>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form  action="/hr/staff/hr_staff_private_change/hrStaffPrivateChange.do" method="POST">
			<p class="txttitle">${lfn:message('hr-staff:hr.staff.tree.privacy.settings') }</p>
			<center>
				<table class="tb_normal" width=95%>
					<html:hidden property="fdId" />
					<tr>
						<td width="20%" class="td_normal_title">
							修改人员
						</td>
						<td width="80%">
							<html:hidden property="fdPersonId"/>
							${hrStaffPrivateChangeForm.fdPersonName}
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.brief" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isBriefPrivate" value="1" >
								<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.project" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isProjectPrivate" value="1"  >
									<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.work" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isWorkPrivate" value="1" >
								<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.education" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isEducationPrivate" value="1" >
									<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.training" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isTrainingPrivate" value="1" >
									<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
					<tr>
						<td width=20% class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.bonusMalus" />
						</td>
						<td width="80%">
							<label>
								<html:checkbox property="isBonusPrivate" value="1" >
									<bean:message bundle="hr-staff" key="hr.staff.btn.hide" />
								</html:checkbox>
							</label>
						</td>
					</tr>
				</table>
			</center>
		</html:form>
	</template:replace>
</template:include>
