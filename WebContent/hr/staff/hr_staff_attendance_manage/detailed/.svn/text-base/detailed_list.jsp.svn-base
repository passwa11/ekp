<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="detailed" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${detailed.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		    ${detailed.fdPersonInfo.nameAccount}
		</list:data-column>
		<!--请假天数-->
		<list:data-column headerClass="width80" property="fdLeaveDays" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdLeaveDays') }"> 
		</list:data-column>
		<!--加班天数-->
		<list:data-column headerClass="width80" col="fdLeaveDays_o" title="${ lfn:message('hr-staff:hrStaff.robot.overtime.days') }">
			${detailed.fdLeaveDays}
		</list:data-column>
		<!--请假相关流程-->
		<list:data-column headerClass="width200" col="fdRelatedProcess" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdRelatedProcess') }" escape="false">
			<c:if test="${fn:length(detailed.fdRelatedProcess) > 0}">
				<a href="${LUI_ContextPath}${detailed.fdRelatedProcess}" target="_blank">
					<c:choose>
						<c:when test="${fn:length(detailed.fdSubject) > 0}">
							${detailed.fdSubject}
						</c:when>
						<c:otherwise>
					    	<bean:message bundle="hr-staff" key="hrStaffAttendanceManageDetailed.relatedProcess" arg0="${detailed.fdPersonInfo.fdName}" arg1="${detailed.leaveType}"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${detailed.fdException}">
			    	<span style="color: red;">(<bean:message bundle="hr-staff" key="hrStaffAttendanceManageDetailed.robot.leaveDays.error.title"/>)</span>
			    	</c:if>
				</a>
		   	</c:if>
		</list:data-column>
		<!--加班相关流程-->
		<list:data-column headerClass="width200" col="fdRelatedProcess_o" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdRelatedProcess') }" escape="false">
			<c:if test="${fn:length(detailed.fdRelatedProcess) > 0}">
			    <a href="${LUI_ContextPath}${detailed.fdRelatedProcess}" target="_blank">
			    	<c:choose>
						<c:when test="${fn:length(detailed.fdSubject) > 0}">
							${detailed.fdSubject}
						</c:when>
						<c:otherwise>
					    	<bean:message bundle="hr-staff" key="hrStaffAttendanceManageDetailed.overtime.relatedProcess" arg0="${detailed.fdPersonInfo.fdName}"/>
					    </c:otherwise>
					</c:choose>
					<c:if test="${detailed.fdException}">
			    	<span style="color: red;">(<bean:message bundle="hr-staff" key="hrStaffAttendanceManageDetailed.robot.leaveDays.error.title"/>)</span>
			    	</c:if>
			   	</a>
		   	</c:if>
		</list:data-column>
		<!--开始时间-->
		<list:data-column headerClass="width100" col="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdBeginDate') }">
			${detailed.beginDateForString}
		</list:data-column> 
		<!--结束时间-->
		<list:data-column headerClass="width100" col="fdEndDate" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdEndDate') }">
			${detailed.endDateForString}
		</list:data-column> 
		<!--请假类型-->
		<list:data-column headerClass="width100" col="fdLeaveType" title="${ lfn:message('hr-staff:hrStaffAttendanceManageDetailed.fdLeaveType') }">
			${detailed.leaveType}
		</list:data-column> 
		<!--补休类型-->
		<list:data-column headerClass="width100" col="fdLeaveType_o" title="${ lfn:message('hr-staff:hrStaff.robot.overtime.fdLeaveType') }">
			${detailed.leaveType}
		</list:data-column> 
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 删除 -->
					<kmss:auth requestURL="/hr/staff/hr_staff_attendance_manage/detailed/hrStaffAttendanceManageDetailed.do?method=deleteall">
						<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/detailed/hrStaffAttendanceManageDetailed.do?method=deleteall', '${detailed.fdId}')">${ lfn:message('button.delete') }</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>