<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<script type="text/javascript">
			seajs.use( [ 'lui/jquery', 'lui/dialog', 'hr/staff/resource/js/dateUtil' ], function($, dialog, dateUtil) {
				window.viewFirstInfo = function () {
					var fdPersonId = '${param.fdPersonInfoId}';
					var s = $("[name='fdIsSecondEntry']").val();
					if(s == '是'){
						var url = Com_Parameter.ContextPath + 'hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=getPersonInfo';
						$.post(url,{fdPersonId:fdPersonId},function (data) {
							console.log(data);
							var iframeUrl = Com_Parameter.ContextPath + 'hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=viewRecord&fdId=' + data.fdId;
							dialog.iframe(iframeUrl,"第一次任职记录",null,{width:800,height : 600});
						})
					}
				}
			})
		</script>
	</template:replace>
	<template:replace name="content">
		<center>	
		<html:form action="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=save" >
			<input type="hidden" name="fdPersonInfoId" value="${param.fdPersonInfoId}" />
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.startTime')}
					</td>
					<td width="35%" >
						<xform:datetime required="true" dateTimeType="date" showStatus="true" property="fdEntranceBeginDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.finishTime')}
					</td>
					<td width="35%">
						<xform:datetime required="true"  dateTimeType="date" showStatus="true" property="fdEntranceEndDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdStartDateOfInternship')}
					</td>
					<td width="35%" >
						<xform:datetime  dateTimeType="date" showStatus="true" property="fdStartDateOfInternship" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdEndDateOfInternship')}
					</td>
					<td width="35%">
						<xform:datetime   dateTimeType="date" showStatus="true" property="fdEndDateOfInternship" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdInternshipStartDate')}
					</td>
					<td width="35%" >
						<xform:datetime  dateTimeType="date" showStatus="true" property="fdInternshipStartDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdInternshipEndDate')}
					</td>
					<td width="35%">
						<xform:datetime   dateTimeType="date" showStatus="true" property="fdInternshipEndDate" style="width:98%;" validators="compareEntranceDate"></xform:datetime>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffPersonInfo.fdStaffingLevel')}
					</td>
					<td width="35%">
						<xform:staffingLevel showStatus="true" propertyName="fdStaffingLevelName" propertyId="fdStaffingLevelId" style="width:98%;"></xform:staffingLevel>
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:mobile.hr.staff.list.5')}
					</td>
					<td width="35%">
						<c:choose>
							<c:when test="${hrToEkpEnable == true }">
								<xform:address isHrAddress="true" required="true" orgType="ORG_TYPE_DEPT" showStatus="true"  propertyName="fdHrOrgDeptName" propertyId="fdHrOrgDeptId" style="width:98%;"></xform:address>
							</c:when>
							<c:otherwise>
								<xform:address required="true" orgType="ORG_TYPE_DEPT" showStatus="true"  propertyName="fdRatifyDeptName" propertyId="fdRatifyDeptId" style="width:98%;"></xform:address>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts')}
					</td>
					<td width="35%" >
						<c:choose>
							<c:when test="${hrToEkpEnable == true }">
								<xform:address isHrAddress="true"  required="true"  showStatus="true" orgType="ORG_TYPE_POST"  propertyName="fdHrOrgPostName" propertyId="fdHrOrgPostId" ></xform:address>
							</c:when>
							<c:otherwise>
								<xform:address required="true" style="width:88%;"  showStatus="true" orgType="ORG_TYPE_POST"  propertyName="fdOrgPostsNames" propertyId="fdOrgPostsIds"></xform:address>
							</c:otherwise>
						</c:choose>
					</td>
						
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-organization:hrOrganizationConPost.fdType')}
					</td>
					<td width="35%">
						<xform:text showStatus="true" property="fdStatus" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdIsInspection')}
					</td>
					<td width="35%">
					<xform:select property="fdIsInspection" showStatus="edit">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdYesNo1"></xform:enumsDataSource>
							</xform:select >
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdAppointmentCategory')}
					</td>
					<td width="35%">
						<xform:text showStatus="true" property="fdAppointmentCategory" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdIsSecondEntry')}
					</td>
					<td width="35%">
						<xform:select property="fdIsSecondEntry" showStatus="edit" onValueChange="viewFirstInfo();" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdYesNo1" ></xform:enumsDataSource>
							</xform:select >
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdChangeType')}
					</td>
					<td width="35%">
						<xform:text showStatus="true" property="fdChangeType" style="width:98%;height:50px;"/>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdContractChangeRecord')}
					</td>
					<td width="35%">
						<xform:text showStatus="true" property="fdContractChangeRecord" style="width:98%;height:50px;"/>
					</td>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffTrackRecord.fdType')}
					</td>
					<td width="35%">
						<xform:text showStatus="true" property="fdType" style="width:98%;height:50px;"/>
					</td>
					</tr>
					<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('hr-staff:hrStaffPersonExperience.fdMemo')}
					</td>
					<td colspan="3">
						<xform:textarea showStatus="true" property="fdMemo" style="width:98%;height:50px;"/>
					</td>
				</tr>
			</table>
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_experience/experience_common_add.jsp"%>
		</center>
	</template:replace>
</template:include>