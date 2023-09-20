<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.DateUtil,java.util.Date,java.text.DecimalFormat,java.util.Map,java.util.List" %>
<%@ page import="com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.time.util.SysTimeUtil,com.landray.kmss.util.NumberUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,net.sf.json.JSONObject" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>

		<c:set var="_docCreatorId" value="${model.docCreator.fdId }"></c:set>
		<%
			String _docCreatorId = (String) pageContext.getAttribute("_docCreatorId");
			Map<String, JSONObject> personInfoMap = (Map<String, JSONObject>)request.getAttribute("personInfoMap");
			if(null!=personInfoMap){
				JSONObject obj = personInfoMap.get(_docCreatorId);
				pageContext.setAttribute("obj", obj);
			}
		%>
		<c:if test="${fn:contains(fdShowCols, 'fdAffiliatedCompany')}">
			<list:data-column col="fdAffiliatedCompany" title="${lfn:message('sys-attend:sysAttendStatMonth.fdAffiliatedCompany') }" escape="false" headerStyle="min-width: 65px;">
				${obj.fdAffiliatedCompany}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdFirstLevelDepartment')}">
			<list:data-column col="fdFirstLevelDepartment" title="一级部门" escape="false" headerStyle="min-width: 65px;">
				${obj.fdFirstLevelDepartment}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdSecondLevelDepartment')}">
			<list:data-column col="fdSecondLevelDepartment" title="二级部门" escape="false" headerStyle="min-width: 65px;">
				${obj.fdSecondLevelDepartment}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdThirdLevelDepartment')}">
			<list:data-column col="fdThirdLevelDepartment" title="三级部门" escape="false" headerStyle="min-width: 65px;">
				${obj.fdThirdLevelDepartment}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdStaffNo')}">
            <list:data-column col="fdStaffNo" title="${lfn:message('sys-attend:sysAttendStatMonth.fdStaffNo') }" escape="false" headerStyle="min-width: 65px;">
                ${obj.fdStaffNo}
            </list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOrgPost')}">
			<list:data-column col="fdOrgPost" title="岗位" escape="false" headerStyle="min-width: 65px;">
				${obj.fdOrgPost}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdStaffingLevel')}">
			<list:data-column col="fdStaffingLevel" title="职务" escape="false" headerStyle="min-width: 65px;">
				${obj.fdStaffingLevel}
			</list:data-column>
		</c:if>

		<c:if test="${fn:contains(fdShowCols, 'fdResignationDate')}">
			<list:data-column col="fdResignationDate" title="离职时间" escape="false" headerStyle="min-width: 65px;">
				${obj.fdResignationDate}
			</list:data-column>
		</c:if>

		<c:if test="${fn:contains(fdShowCols, 'fdStaffType')}">
			<list:data-column col="fdStaffType" title="人员类别" escape="false" headerStyle="min-width: 65px;">
				${obj.fdStaffType}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdTotalDays')}">
			<list:data-column col="fdTotalDays" title="年假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdTotalDays}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdUsedDays')}">
			<list:data-column col="fdUsedDays" title="已休年假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdUsedDays}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdRestDays')}">
			<list:data-column col="fdRestDays" title="剩余年假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdRestDays}
			</list:data-column>
		</c:if>

		<c:if test="${fn:contains(fdShowCols, 'fdTxTotalDays')}">
			<list:data-column col="fdTxTotalDays" title="调休假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdTxTotalDays}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdTxUsedDays')}">
			<list:data-column col="fdTxUsedDays" title="已休调休假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdTxUsedDays}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdTxRestDays')}">
			<list:data-column col="fdTxRestDays" title="剩余调休假天数" escape="false" headerStyle="min-width: 65px;">
				${obj.fdTxRestDays}
			</list:data-column>
		</c:if>

		<list:data-column col="docCreator.fdName" title="${ lfn:message('sys-attend:sysAttendStatMonth.docCreatorName') }" escape="false" headerStyle="min-width: 65px;">
			<c:if test="${model.docCreator.fdIsAvailable}">
				${model.docCreator.fdName}
			</c:if>
			<c:if test="${!model.docCreator.fdIsAvailable}">
				${model.docCreator.fdName}${ lfn:message('sys-attend:sysAttendStatDetail.alreadyQuit') }
			</c:if>
		</list:data-column>
		
		<list:data-column col="docCreator.fdLoginName" title="${ lfn:message('sys-attend:sysAttendStatMonth.docCreatorLoginName') }" escape="false" headerStyle="min-width: 65px;">
			<c:if test="${model.docCreator.fdIsAvailable}">
				${model.docCreator.fdLoginName}
			</c:if>
			<c:if test="${!model.docCreator.fdIsAvailable}">
				${model.docCreator.fdName}${ lfn:message('sys-attend:sysAttendStatDetail.alreadyQuit') }
			</c:if>
		</list:data-column>
		<c:if test="${fn:contains(fdShowCols, 'fdDept')}">
		<list:data-column col="fdDept" title="${ lfn:message('sys-attend:sysAttendStatMonth.dept') }" escape="false" headerStyle="min-width: 100px;">
			${model.docCreator.fdParent != null ? model.docCreator.fdParent.fdName : ""}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdEntryTime')}">
		<list:data-column col="fdEntryTime" title="${lfn:message('sys-attend:sysAttendStatMonth.fdEntryTime') }" escape="false" headerStyle="min-width: 100px;">
			${obj.fdEntryTime}
		</list:data-column>
		</c:if>
				<c:if test="${fn:contains(fdShowCols, 'enterDays')}">
		<list:data-column col="enterDays" title="${lfn:message('sys-attend:sysAttendStatMonth.enterDays') }" escape="false" headerStyle="min-width: 100px;">
			${obj.enterDays}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'leaveDays')}">
		<list:data-column col="leaveDays" title="${lfn:message('sys-attend:sysAttendStatMonth.leaveDays') }" escape="false" headerStyle="min-width: 100px;">
			${obj.leaveDays}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdCategoryName')}">
		<list:data-column col="fdCategoryName" title="${ lfn:message('sys-attend:sysAttendStatMonth.category') }" escape="false" headerStyle="min-width: 100px;">
			${model.fdCategoryName}
		</list:data-column>
		</c:if>
		<c:choose>
			<c:when test="${fdDateType == '2' }">
				<list:data-column col="fdMonth" title="${ lfn:message('sys-attend:sysAttendStatMonth.period') }" escape="false" headerStyle="min-width: 180px;">
					${obj.fdStartTime}~${obj.fdEndTime}
				</list:data-column>
			</c:when>
			<c:otherwise>
				<list:data-column col="fdMonth" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMonth') }" escape="false" headerStyle="min-width: 65px;">
					<c:set var="_fdMonth" value="${model.fdMonth }"></c:set>
					<%
						Date _fdMonth = (Date) pageContext.getAttribute("_fdMonth");
						pageContext.setAttribute("__fdMonth", DateUtil.convertDateToString(_fdMonth, "yyyy-MM"));
					%>
					${__fdMonth}
				</list:data-column>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${fn:contains(fdShowCols, 'fdShouldDays')}">
		<list:data-column col="fdShouldDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDays') }" escape="false">
			${model.fdShouldDays}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidays')}">
		<list:data-column col="fdHolidays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdShouldDaysAndHolidays') }" escape="false">
			<c:if test="${model.fdHolidays != null }">
				${model.fdShouldDays+model.fdHolidays}
			</c:if>
			<c:if test="${model.fdHolidays == null }">
				${model.fdShouldDays}
			</c:if>
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdActualDays')}">
		<list:data-column col="fdActualDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdActualDays') }" escape="false">
			${model.fdActualDays}
		</list:data-column>
		</c:if>
		
		<c:if test="${fn:contains(fdShowCols, 'fdStatusDays')}">
		<list:data-column col="fdStatusDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdStatusDays') }" escape="false">
			${model.fdStatusDays}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkDateDays')}">
			<list:data-column col="fdWorkDateDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkDateDays') }" escape="false">
				<c:if test="${not empty model.fdWorkDateDays}">${model.fdWorkDateDays }</c:if>
				<c:if test="${empty model.fdWorkDateDays}">0</c:if>
			</list:data-column>
		</c:if>
		
		<c:set var="_fdAbsentDaysCount" value="${model.fdAbsentDaysCount }"></c:set>
		<c:set var="_fdAbsentDays" value="${model.fdAbsentDays}"></c:set>
		<%
			DecimalFormat df = new DecimalFormat("#.#");
			Float _fdAbsentDaysCount = (Float) pageContext.getAttribute("_fdAbsentDaysCount");
			Integer _fdAbsentDays = (Integer) pageContext.getAttribute("_fdAbsentDays");
			pageContext.setAttribute("__fdAbsentDaysCount", _fdAbsentDaysCount == null ? (_fdAbsentDays == null ? 0 : _fdAbsentDays) : df.format(_fdAbsentDaysCount));
		%>
		<c:if test="${fn:contains(fdShowCols, 'fdAbsentDays')}">
		<list:data-column col="fdAbsentDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdAbsentDays') }" escape="false">
			${__fdAbsentDaysCount}
		</list:data-column>
		</c:if>
		
		<c:set var="_fdTripDays" value="${model.fdTripDays }"></c:set>
		<%
			Float __fdTripDays = (Float) pageContext.getAttribute("_fdTripDays");
			pageContext.setAttribute("__fdTripDays", df.format(__fdTripDays));
		%>
		<c:if test="${fn:contains(fdShowCols, 'fdTripDays')}">
		<list:data-column col="fdTripDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTripDays') }" escape="false">
			${__fdTripDays}
		</list:data-column>
		</c:if>
<%-- 		<c:if test="${fn:contains(fdShowCols, 'fdOffDays')}"> --%>
<%-- 		<list:data-column col="fdOffDays" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOffDays') }" escape="false"> --%>
<%-- 			<c:set var="_fdLeaveDays" value="${model.fdLeaveDays }"></c:set> --%>
<%-- 			<c:set var="_fdOffDays" value="${model.fdOffDays }"></c:set> --%>
<%-- 			<c:set var="_fdOffTime" value="${model.fdOffTimeHour }"></c:set> --%>
<%-- 			<% --%>
// 				Float __fdLeaveDays = (Float) pageContext.getAttribute("_fdOffDays");
// 				if(__fdLeaveDays !=null && __fdLeaveDays > 0){
// 					//汇总中，如果直接统计了请假天，则直接使用。否则使用拼接，兼容历史数据
// 					pageContext.setAttribute("__fdOffDays", NumberUtil.roundDecimal(__fdLeaveDays,3));
// 				}else {
// 					Float __fdOffDays = (Float) pageContext.getAttribute("_fdOffDays");
// 					__fdOffDays = __fdOffDays == null ? 0f : __fdOffDays;

// 					Float __fdOffTime = (Float) pageContext.getAttribute("_fdOffTime");
// 					__fdOffTime = __fdOffTime == null ? 0 : __fdOffTime;
// 					pageContext.setAttribute("__fdOffDays", SysTimeUtil.formatLeaveTimeStr(__fdOffDays, __fdOffTime));
// 				}
<%-- 			%> --%>
<%-- 			${__fdOffDays} --%>
<%-- 		</list:data-column> --%>
<%-- 		</c:if> --%>
		<c:if test="${fn:contains(fdShowCols, 'fdLateCount')}">
		<list:data-column col="fdLateCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateCount') }" escape="false">
			${model.fdLateCount}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdLateTime')}">
		<list:data-column col="fdLateTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateTime') }" escape="false">
			${model.fdLateTime}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdLeftCount')}">
		<list:data-column col="fdLeftCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftCount') }" escape="false">
			${model.fdLeftCount}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdLeftTime')}">
		<list:data-column col="fdLeftTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftTime') }" escape="false">
			${model.fdLeftTime}
		</list:data-column>
		</c:if>
<%-- 		<c:if test="${fn:contains(fdShowCols, 'fdOutsideCount')}"> --%>
<%-- 		<list:data-column col="fdOutsideCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutsideCount') }" escape="false"> --%>
<%-- 			${model.fdOutsideCount} --%>
<%-- 		</list:data-column> --%>
<%-- 		</c:if> --%>
		<c:if test="${fn:contains(fdShowCols, 'fdMissedCount')}">
		<list:data-column col="fdMissedCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedCount') }" escape="false">
			${model.fdMissedCount}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdTotalTime')}">
		<list:data-column col="fdTotalTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdTotalTime') }" escape="false">
			<c:set var="_fdTotalTime" value="${model.fdTotalTime }"></c:set>
			<%
				Long __fdTotalTime = (Long) pageContext.getAttribute("_fdTotalTime");
			
				Integer totalTime = __fdTotalTime==null ? 0:__fdTotalTime.intValue();
	  			int hour = totalTime/60;
				int mins = totalTime%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
				DecimalFormat    df22   = new DecimalFormat("######0.00");  
				pageContext.setAttribute("__fdTotalTime",df22.format( totalTime/60.0));
// 				pageContext.setAttribute("__fdTotalTime", hourTxt);
			%>
			${__fdTotalTime}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOverTime')}">
		<list:data-column col="fdOverTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOverTime') }" escape="false">
			<c:set var="_fdOverTime" value="${model.fdOverRestTime }"></c:set>
			<%
				Long __fdOverTime = (Long) pageContext.getAttribute("_fdOverTime");
				Integer overTime = __fdOverTime==null ? 0:__fdOverTime.intValue();
	  			int hour = overTime/60;
				int mins = overTime%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
				DecimalFormat    df22   = new DecimalFormat("######0.00");  
				pageContext.setAttribute("__fdOverTime", df22.format( overTime/60.0));
// 				pageContext.setAttribute("__fdOverTime", hourTxt);
			%>
			${__fdOverTime}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverTime')}">
		<list:data-column col="fdWorkOverTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdWorkOverTime') }" escape="false">
			<c:set var="_fdWorkOverTime" value="${model.fdWorkOverRestTime }"></c:set>
			<%
				Long __fdWorkOverTime = (Long) pageContext.getAttribute("_fdWorkOverTime");
				Integer workOverTime = __fdWorkOverTime==null ? 0:__fdWorkOverTime.intValue();
	  			int hour = workOverTime/60;
				int mins = workOverTime%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
				pageContext.setAttribute("__fdWorkOverTime", NumberUtil.roundDecimal(workOverTime/60.0,2));
// 				pageContext.setAttribute("__fdWorkOverTime", hourTxt);
			%>
			${__fdWorkOverTime}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverTime')}">
		<list:data-column col="fdOffOverTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOffOverTime') }" escape="false">
			<c:set var="_fdOffOverTime" value="${model.fdOffOverRestTime }"></c:set>
			<%
				Long __fdOffOverTime = (Long) pageContext.getAttribute("_fdOffOverTime");
				Integer OffOverTime = __fdOffOverTime==null ? 0:__fdOffOverTime.intValue();
	  			int hour = OffOverTime/60;
				int mins = OffOverTime%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
// 				pageContext.setAttribute("__fdOffOverTime",hourTxt);
				pageContext.setAttribute("__fdOffOverTime",NumberUtil.roundDecimal(OffOverTime/60.0,2));
			%>
			${__fdOffOverTime}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverTime')}">
		<list:data-column col="fdHolidayOverTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdHolidayOverTime') }" escape="false">
			<c:set var="_fdHolidayOverTime" value="${model.fdHolidayOverRestTime }"></c:set>
			<%
				Long __fdHolidayOverTime = (Long) pageContext.getAttribute("_fdHolidayOverTime");
				Integer holidayOverTime = __fdHolidayOverTime==null ? 0:__fdHolidayOverTime.intValue();
	  			int hour = holidayOverTime/60;
				int mins = holidayOverTime%60;
				String hourTxt = "";
				String hTxt = ResourceUtil.getString("date.interval.hour");
				String mTxt = ResourceUtil.getString("date.interval.minute");
				if(hour>0){
					hourTxt+=hour+hTxt;
				}
				if(mins>0){
					hourTxt+=mins+mTxt;
				}
				pageContext.setAttribute("__fdHolidayOverTime", NumberUtil.roundDecimal(holidayOverTime/60.0,2));
// 				pageContext.setAttribute("__fdHolidayOverTime", hourTxt);
			%>
			${__fdHolidayOverTime}
		</list:data-column>
		</c:if>

		<%--加班申请--%>
		<c:if test="${fn:contains(fdShowCols, 'fdOverApplyTime')}">
			<list:data-column col="fdOverApplyTime" title="加班申请工时" escape="false">
				<c:set var="_fdOverApplyTime" value="${model.fdRestTurnTime+model.fdOverRestTime }"></c:set>
				<%
					Long __fdOverApplyTime = (Long) pageContext.getAttribute("_fdOverApplyTime");
					Integer overApplyTime = __fdOverApplyTime==null ? 0:__fdOverApplyTime.intValue();
					int hour = overApplyTime / 60;
					int mins = overApplyTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt += hour+hTxt;
					}
					if(mins>0){
						hourTxt += mins+mTxt;
					}
					pageContext.setAttribute("__fdOverApplyTime", NumberUtil.roundDecimal(overApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdOverApplyTime", hourTxt);
				%>
				
				${__fdOverApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverApplyTime')}">
			<list:data-column col="fdWorkOverApplyTime" title="申请工作日加班" escape="false">
				<c:set var="_fdWorkOverApplyTime" value="${model.fdWorkOverRestTime+model.fdWorkRestTurnTime }"></c:set>
				<%
					Long __fdWorkOverApplyTime = (Long) pageContext.getAttribute("_fdWorkOverApplyTime");
					Integer workOverApplyTime = __fdWorkOverApplyTime==null ? 0:__fdWorkOverApplyTime.intValue();
					int hour = workOverApplyTime/60;
					int mins = workOverApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverApplyTime", NumberUtil.roundDecimal(workOverApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverApplyTime", hourTxt);
				%>
				${__fdWorkOverApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverApplyTime')}">
			<list:data-column col="fdOffOverApplyTime" title="休息日加班申请工时" escape="false">
				<c:set var="_fdOffOverApplyTime" value="${model.fdOffRestTurnTime+model.fdOffOverRestTime }"></c:set>
				<%
					Long __fdOffOverApplyTime = (Long) pageContext.getAttribute("_fdOffOverApplyTime");
					Integer OffOverApplyTime = __fdOffOverApplyTime==null ? 0:__fdOffOverApplyTime.intValue();
					int hour = OffOverApplyTime/60;
					int mins = OffOverApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverApplyTime",OffOverApplyTime/60.0);
// 					pageContext.setAttribute("__fdOffOverApplyTime",hourTxt);
				%>
				${__fdOffOverApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverApplyTime')}">
			<list:data-column col="fdHolidayOverApplyTime" title="节假日加班申请工时" escape="false">
				<c:set var="_fdHolidayOverApplyTime" value="${model.fdHolidayOverRestTime+model.fdHolidayRestTurnTime }"></c:set>
				<%
					Long __fdHolidayOverApplyTime = (Long) pageContext.getAttribute("_fdHolidayOverApplyTime");
					Integer holidayOverApplyTime = __fdHolidayOverApplyTime==null ? 0:__fdHolidayOverApplyTime.intValue();
					int hour = holidayOverApplyTime/60;
					int mins = holidayOverApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverApplyTime", NumberUtil.roundDecimal(holidayOverApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverApplyTime", hourTxt);
				%>
				${__fdHolidayOverApplyTime}
			</list:data-column>
		</c:if>

		<%--加班调休申请工时--%>
		<c:if test="${fn:contains(fdShowCols, 'fdOverTurnApplyTime')}">
			<list:data-column col="fdOverTurnApplyTime" title="加班调休申请工时" escape="false">
				<c:set var="_fdOverTurnApplyTime" value="${model.fdOverTurnApplyTime }"></c:set>
				<%
					Long __fdOverTurnApplyTime = (Long) pageContext.getAttribute("_fdOverTurnApplyTime");
					Integer overTurnApplyTime = __fdOverTurnApplyTime==null ? 0:__fdOverTurnApplyTime.intValue();
					int hour = overTurnApplyTime / 60;
					int mins = overTurnApplyTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt += hour+hTxt;
					}
					if(mins>0){
						hourTxt += mins+mTxt;
					}
					pageContext.setAttribute("__fdOverTurnApplyTime", NumberUtil.roundDecimal(overTurnApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdOverTurnApplyTime", hourTxt);
				%>
				${__fdOverTurnApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverTurnApplyTime')}">
			<list:data-column col="fdWorkOverTurnApplyTime" title="工作日调休申请工时" escape="false">
				<c:set var="_fdWorOverTurnApplyTime" value="${model.fdWorkOverTurnApplyTime}"></c:set>
				<%
					Long __fdWorkOverTurnApplyTime = (Long) pageContext.getAttribute("_fdWorOverTurnApplyTime");
					Integer workOverTurnApplyTime = __fdWorkOverTurnApplyTime==null ? 0:__fdWorkOverTurnApplyTime.intValue();
					int hour = workOverTurnApplyTime/60;
					int mins = workOverTurnApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverTurnApplyTime", NumberUtil.roundDecimal(workOverTurnApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverTurnApplyTime", hourTxt);
				%>
				${__fdWorkOverTurnApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverTurnApplyTime')}">
			<list:data-column col="fdOffOverTurnApplyTime" title="休息日加班调休申请工时" escape="false">
				<c:set var="_fdOffOverTurnApplyTime" value="${model.fdOffOverTurnApplyTime }"></c:set>
				<%
					Long __fdOffOverTurnApplyTime = (Long) pageContext.getAttribute("_fdOffOverTurnApplyTime");
					Integer OffOverApplyTime = __fdOffOverTurnApplyTime==null ? 0:__fdOffOverTurnApplyTime.intValue();
					int hour = OffOverApplyTime/60;
					int mins = OffOverApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverTurnApplyTime",NumberUtil.roundDecimal(OffOverApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdOffOverTurnApplyTime",hourTxt);
				%>
				${__fdOffOverTurnApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverTurnApplyTime')}">
			<list:data-column col="fdHolidayOverTurnApplyTime" title="节假日加班调休申请工时" escape="false">
				<c:set var="_fdHolidayOverTurnApplyTime" value="${model.fdHolidayOverTurnApplyTime }"></c:set>
				<%
					Long __fdHolidayOverTurnApplyTime = (Long) pageContext.getAttribute("_fdHolidayOverTurnApplyTime");
					Integer holidayOverTurnApplyTime = __fdHolidayOverTurnApplyTime==null ? 0:__fdHolidayOverTurnApplyTime.intValue();
					int hour = holidayOverTurnApplyTime/60;
					int mins = holidayOverTurnApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverTurnApplyTime", NumberUtil.roundDecimal(holidayOverTurnApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverTurnApplyTime", hourTxt);
				%>
				${__fdHolidayOverTurnApplyTime}
			</list:data-column>
		</c:if>

		<%--加班调休工时--%>
		<c:if test="${fn:contains(fdShowCols, 'fdOverTurnTime')}">
			<list:data-column col="fdOverTurnTime" title="加班调休工时" escape="false">
				<c:set var="_fdOverTurnTime" value="${model.fdOverTurnTime }"></c:set>
				<%
					Long __fdOverTurnTime = (Long) pageContext.getAttribute("_fdOverTurnTime");
					Integer overTurnTime = __fdOverTurnTime==null ? 0:__fdOverTurnTime.intValue();
					int hour = overTurnTime / 60;
					int mins = overTurnTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt += hour+hTxt;
					}
					if(mins>0){
						hourTxt += mins+mTxt;
					}
					pageContext.setAttribute("__fdOverTurnTime", NumberUtil.roundDecimal(overTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdOverTurnTime", hourTxt);
				%>
				${__fdOverTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverTurnTime')}">
			<list:data-column col="fdWorkOverTurnTime" title="工作日调休工时" escape="false">
				<c:set var="_fdWorkOverTurnTime" value="${model.fdWorkOverTurnTime }"></c:set>
				<%
					Long __fdWorkOverTurnTime = (Long) pageContext.getAttribute("_fdWorkOverTurnTime");
					Integer workOverTurnTime = __fdWorkOverTurnTime==null ? 0:__fdWorkOverTurnTime.intValue();
					int hour = workOverTurnTime/60;
					int mins = workOverTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverTurnTime", NumberUtil.roundDecimal(workOverTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverTurnTime", hourTxt);
				%>
				${__fdWorkOverTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverTurnTime')}">
			<list:data-column col="fdOffOverTurnTime" title="休息日加班调休工时" escape="false">
				<c:set var="_fdOffOverTurnTime" value="${model.fdOffOverTurnTime }"></c:set>
				<%
					Long __fdOffOverTurnTime = (Long) pageContext.getAttribute("_fdOffOverTurnTime");
					Integer OffOverTurnTime = __fdOffOverTurnTime==null ? 0:__fdOffOverTurnTime.intValue();
					int hour = OffOverTurnTime/60;
					int mins = OffOverTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverTurnTime",NumberUtil.roundDecimal(OffOverTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdOffOverTurnTime",hourTxt);
				%>
				${__fdOffOverTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverTurnTime')}">
			<list:data-column col="fdHolidayOverTurnTime" title="节假日加班调休工时" escape="false">
				<c:set var="_fdHolidayOverTurnTime" value="${model.fdHolidayOverTurnTime }"></c:set>
				<%
					Long __fdHolidayOverTurnTime = (Long) pageContext.getAttribute("_fdHolidayOverTurnTime");
					Integer holidayOverTurnTime = __fdHolidayOverTurnTime==null ? 0:__fdHolidayOverTurnTime.intValue();
					int hour = holidayOverTurnTime/60;
					int mins = holidayOverTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverTurnTime", NumberUtil.roundDecimal(holidayOverTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverTurnTime", hourTxt);
				%>
				${__fdHolidayOverTurnTime}
			</list:data-column>
		</c:if>

		<%--加班加班费申请工时--%>
		<c:if test="${fn:contains(fdShowCols, 'fdOverPayApplyTime')}">
			<list:data-column col="fdOverPayApplyTime" title="加班加班费申请工时" escape="false">
				<c:set var="_fdOverPayApplyTime" value="${model.fdOverPayApplyTime }"></c:set>
				<%
					Long __fdOverPayApplyTime = (Long) pageContext.getAttribute("_fdOverPayApplyTime");
					Integer overPayApplyTime = __fdOverPayApplyTime==null ? 0:__fdOverPayApplyTime.intValue();
					int hour = overPayApplyTime / 60;
					int mins = overPayApplyTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt += hour+hTxt;
					}
					if(mins>0){
						hourTxt += mins+mTxt;
					}
					pageContext.setAttribute("__fdOverPayApplyTime",NumberUtil.roundDecimal( overPayApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdOverPayApplyTime", hourTxt);
				%>
				${__fdOverPayApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverPayApplyTime')}">
			<list:data-column col="fdWorkOverPayApplyTime" title="工作日加班费申请工时" escape="false">
				<c:set var="_fdWorkOverPayApplyTime" value="${model.fdWorkOverPayApplyTime }"></c:set>
				<%
					Long __fdWorkOverPayApplyTime = (Long) pageContext.getAttribute("_fdWorkOverPayApplyTime");
					Integer workOverPayApplyTime = __fdWorkOverPayApplyTime==null ? 0:__fdWorkOverPayApplyTime.intValue();
					int hour = workOverPayApplyTime / 60;
					int mins = workOverPayApplyTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverPayApplyTime", NumberUtil.roundDecimal(workOverPayApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverPayApplyTime", hourTxt);
				%>
				${__fdWorkOverPayApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverPayApplyTime')}">
			<list:data-column col="fdOffOverPayApplyTime" title="休息日加班加班费申请工时" escape="false">
				<c:set var="_fdOffOverPayApplyTime" value="${model.fdOffOverPayApplyTime }"></c:set>
				<%
					Long __fdOffOverPayApplyTime = (Long) pageContext.getAttribute("_fdOffOverPayApplyTime");
					Integer OffOverApplyTime = __fdOffOverPayApplyTime==null ? 0:__fdOffOverPayApplyTime.intValue();
					int hour = OffOverApplyTime/60;
					int mins = OffOverApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverPayApplyTime",NumberUtil.roundDecimal(OffOverApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdOffOverPayApplyTime",hourTxt);
				%>
				${__fdOffOverPayApplyTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverPayApplyTime')}">
			<list:data-column col="fdHolidayOverPayApplyTime" title="节假日加班加班费申请工时" escape="false">
				<c:set var="_fdHolidayOverPayApplyTime" value="${model.fdHolidayOverPayApplyTime }"></c:set>
				<%
					Long __fdHolidayOverPayApplyTime = (Long) pageContext.getAttribute("_fdHolidayOverPayApplyTime");
					Integer fdHolidayOverPayApplyTime = __fdHolidayOverPayApplyTime==null ? 0:__fdHolidayOverPayApplyTime.intValue();
					int hour = fdHolidayOverPayApplyTime/60;
					int mins = fdHolidayOverPayApplyTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverPayApplyTime", NumberUtil.roundDecimal(fdHolidayOverPayApplyTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverPayApplyTime", hourTxt);
				%>
				${__fdHolidayOverPayApplyTime}
			</list:data-column>
		</c:if>

		<%--加班加班费实际工时--%>
		<c:if test="${fn:contains(fdShowCols, 'fdOverPayTime')}">
			<list:data-column col="fdOverPayTime" title="加班加班费实际工时" escape="false">
				<c:set var="_fdOverPayTime" value="${model.fdOverPayTime }"></c:set>
				<%
					Long __fdOverPayTime = (Long) pageContext.getAttribute("_fdOverPayTime");
					Integer fdOverPayTime = __fdOverPayTime==null ? 0:__fdOverPayTime.intValue();
					int hour = fdOverPayTime / 60;
					int mins = fdOverPayTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt += hour+hTxt;
					}
					if(mins>0){
						hourTxt += mins+mTxt;
					}
					pageContext.setAttribute("__fdOverPayTime", NumberUtil.roundDecimal(fdOverPayTime/60.0,2));
// 					pageContext.setAttribute("__fdOverPayTime", hourTxt);
				%>
				${__fdOverPayTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverPayTime')}">
			<list:data-column col="fdWorkOverPayTime" title="工作日加班费工时" escape="false">
				<c:set var="_fdWorkOverPayTime" value="${model.fdWorkOverPayTime }"></c:set>
				<%
					Long __fdWorkOverPayTime = (Long) pageContext.getAttribute("_fdWorkOverPayTime");
					Integer workOverPayTime = __fdWorkOverPayTime == null ? 0 : __fdWorkOverPayTime.intValue();
					int hour = workOverPayTime / 60;
					int mins = workOverPayTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverPayTime", NumberUtil.roundDecimal(workOverPayTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverPayTime", hourTxt);
				%>
				${__fdWorkOverPayTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverPayTime')}">
			<list:data-column col="fdOffOverPayTime" title="休息日加班费工时" escape="false">
				<c:set var="_fdOffOverPayTime" value="${model.fdOffOverPayTime }"></c:set>
				<%
					Long __fdOffOverPayTime = (Long) pageContext.getAttribute("_fdOffOverPayTime");
					Integer OffOverPayTime = __fdOffOverPayTime == null ? 0 : __fdOffOverPayTime.intValue();
					int hour = OffOverPayTime / 60;
					int mins = OffOverPayTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverPayTime",NumberUtil.roundDecimal(OffOverPayTime/60.0,2));
// 					pageContext.setAttribute("__fdOffOverPayTime",hourTxt);
				%>
				${__fdOffOverPayTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverPayTime')}">
			<list:data-column col="fdHolidayOverPayTime" title="节假日加班费工时" escape="false">
				<c:set var="_fdHolidayOverPayTime" value="${model.fdHolidayOverPayTime }"></c:set>
				<%
					Long __fdHolidayOverPayTime = (Long) pageContext.getAttribute("_fdHolidayOverPayTime");
					Integer holidayOverPayTime = __fdHolidayOverPayTime==null ? 0:__fdHolidayOverPayTime.intValue();
					int hour = holidayOverPayTime / 60;
					int mins = holidayOverPayTime % 60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverPayTime", NumberUtil.roundDecimal(holidayOverPayTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverPayTime", hourTxt);
				%>
				${__fdHolidayOverPayTime}
			</list:data-column>
		</c:if>

		<!-- 加班结转时长-->
		<c:if test="${fn:contains(fdShowCols, 'fdOverRestTime')}">
			<list:data-column col="fdOverRestTime" title="加班结转时长" escape="false">
				<c:set var="_fdOverRestTime" value="${model.fdOverRestTime }"></c:set>
				<%
					Long __fdOverRestTime = (Long) pageContext.getAttribute("_fdOverRestTime");
					Integer fdOverRestTime = __fdOverRestTime==null ? 0:__fdOverRestTime.intValue();
					int hour = fdOverRestTime/60;
					int mins = fdOverRestTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOverRestTime", NumberUtil.roundDecimal(fdOverRestTime/60.0,2));
// 					pageContext.setAttribute("__fdOverRestTime", hourTxt);
				%>
				${__fdOverRestTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkOverRestTime')}">
			<list:data-column col="fdWorkOverRestTime" title="工作日加班结转时长" escape="false">
				<c:set var="_fdWorkOverRestTime" value="${model.fdWorkOverRestTime }"></c:set>
				<%
					Long __fdWorkOverRestTime = (Long) pageContext.getAttribute("_fdWorkOverRestTime");
					Integer fdWorkOverRestTime = __fdWorkOverRestTime==null ? 0:__fdWorkOverRestTime.intValue();
					int hour = fdWorkOverRestTime/60;
					int mins = fdWorkOverRestTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkOverRestTime", NumberUtil.roundDecimal(fdWorkOverRestTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkOverRestTime", hourTxt);
				%>
				${__fdWorkOverRestTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffOverRestTime')}">
			<list:data-column col="fdOffOverRestTime" title="休息日加班结转工时" escape="false">
				<c:set var="_fdOffOverRestTime" value="${model.fdOffOverRestTime }"></c:set>
				<%
					Long __fdOffOverRestTime = (Long) pageContext.getAttribute("_fdOffOverRestTime");
					Integer fdOffOverRestTime = __fdOffOverRestTime==null ? 0:__fdOffOverRestTime.intValue();
					int hour = fdOffOverRestTime/60;
					int mins = fdOffOverRestTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffOverRestTime",NumberUtil.roundDecimal(fdOffOverRestTime/60.0,2));
// 					pageContext.setAttribute("__fdOffOverRestTime",hourTxt);
				%>
				${__fdOffOverRestTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayOverRestTime')}">
			<list:data-column col="fdHolidayOverRestTime" title="节假日加班结转工时" escape="false">
				<c:set var="_fdHolidayOverRestTime" value="${model.fdHolidayOverRestTime }"></c:set>
				<%
					Long __fdHolidayOverRestTime = (Long) pageContext.getAttribute("_fdHolidayOverRestTime");
					Integer fdHolidayOverRestTime = __fdHolidayOverRestTime==null ? 0:__fdHolidayOverRestTime.intValue();
					int hour = fdHolidayOverRestTime/60;
					int mins = fdHolidayOverRestTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayOverRestTime", NumberUtil.roundDecimal(fdHolidayOverRestTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayOverRestTime", hourTxt);
				%>
				${__fdHolidayOverRestTime}
			</list:data-column>
		</c:if>

		<c:if test="${fn:contains(fdShowCols, 'fdOutgoingTime')}">
		<list:data-column col="fdOutgoingTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutgoingTime') }" escape="false">
			<c:set var="_fdOutgoingTime" value="${model.fdOutgoingTime }"></c:set>
			<c:set var="_fdOutgoingDay" value="${model.fdOutgoingDay }"></c:set>
			<%
				Float _fdOutgoingDay = (Float) pageContext.getAttribute("_fdOutgoingDay");
				if(_fdOutgoingDay ==null){
					Float _fdOutgoingTime = (Float) pageContext.getAttribute("_fdOutgoingTime");
					_fdOutgoingTime = _fdOutgoingTime==null ? 0f:_fdOutgoingTime;

					pageContext.setAttribute("__fdOutgoingTime", SysTimeUtil.formatLeaveTimeStr(0f, _fdOutgoingTime));
				} else {
					pageContext.setAttribute("__fdOutgoingTime", NumberUtil.roundDecimal(_fdOutgoingDay, 2) );
				}
			%>
			${__fdOutgoingTime}
		</list:data-column>
		</c:if>
		
		
		
		
		
		
		
		
		<!-- 结转调休-->
		<c:if test="${fn:contains(fdShowCols, 'fdRestTurnTime')}">
			<list:data-column col="fdRestTurnTime" title="加班结转时长" escape="false">
				<c:set var="_fdRestTurnTime" value="${model.fdRestTurnTime }"></c:set>
				<%
					Long __fdRestTurnTime = (Long) pageContext.getAttribute("_fdRestTurnTime");
					Integer fdRestTurnTime = __fdRestTurnTime==null ? 0:__fdRestTurnTime.intValue();
					int hour = fdRestTurnTime/60;
					int mins = fdRestTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdRestTurnTime",NumberUtil.roundDecimal( fdRestTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdRestTurnTime", hourTxt);
				%>
				${__fdRestTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdWorkRestTurnTime')}">
			<list:data-column col="fdWorkRestTurnTime" title="工作日加班结转时长" escape="false">
				<c:set var="_fdWorkRestTurnTime" value="${model.fdWorkRestTurnTime }"></c:set>
				<%
					Long __fdWorkRestTurnTime = (Long) pageContext.getAttribute("_fdWorkRestTurnTime");
					Integer fdWorkRestTurnTime = __fdWorkRestTurnTime==null ? 0:__fdWorkRestTurnTime.intValue();
					int hour = fdWorkRestTurnTime/60;
					int mins = fdWorkRestTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdWorkRestTurnTime", NumberUtil.roundDecimal(fdWorkRestTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdWorkRestTurnTime", hourTxt);
				%>
				${__fdWorkRestTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdOffRestTurnTime')}">
			<list:data-column col="fdOffRestTurnTime" title="休息日加班结转工时" escape="false">
				<c:set var="_fdOffRestTurnTime" value="${model.fdOffRestTurnTime }"></c:set>
				<%
					Long __fdOffRestTurnTime = (Long) pageContext.getAttribute("_fdOffRestTurnTime");
					Integer fdOffRestTurnTime = __fdOffRestTurnTime==null ? 0:__fdOffRestTurnTime.intValue();
					int hour = fdOffRestTurnTime/60;
					int mins = fdOffRestTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdOffRestTurnTime",NumberUtil.roundDecimal(fdOffRestTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdOffRestTurnTime",hourTxt);
				%>
				${__fdOffRestTurnTime}
			</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdHolidayRestTurnTime')}">
			<list:data-column col="fdHolidayRestTurnTime" title="节假日加班结转工时" escape="false">
				<c:set var="_fdHolidayRestTurnTime" value="${model.fdHolidayRestTurnTime }"></c:set>
				<%
					Long __fdHolidayRestTurnTime = (Long) pageContext.getAttribute("_fdHolidayRestTurnTime");
					Integer fdHolidayRestTurnTime = __fdHolidayRestTurnTime==null ? 0:__fdHolidayRestTurnTime.intValue();
					int hour = fdHolidayRestTurnTime/60;
					int mins = fdHolidayRestTurnTime%60;
					String hourTxt = "";
					String hTxt = ResourceUtil.getString("date.interval.hour");
					String mTxt = ResourceUtil.getString("date.interval.minute");
					if(hour>0){
						hourTxt+=hour+hTxt;
					}
					if(mins>0){
						hourTxt+=mins+mTxt;
					}
					pageContext.setAttribute("__fdHolidayRestTurnTime", NumberUtil.roundDecimal(fdHolidayRestTurnTime/60.0,2));
// 					pageContext.setAttribute("__fdHolidayRestTurnTime", hourTxt);
				%>
				${__fdHolidayRestTurnTime}
			</list:data-column>
		</c:if>

		<c:if test="${fn:contains(fdShowCols, 'fdOutgoingTime')}">
		<list:data-column col="fdOutgoingTime" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdOutgoingTime') }" escape="false">
			<c:set var="_fdOutgoingTime" value="${model.fdOutgoingTime }"></c:set>
			<c:set var="_fdOutgoingDay" value="${model.fdOutgoingDay }"></c:set>
			<%
				Float _fdOutgoingDay = (Float) pageContext.getAttribute("_fdOutgoingDay");
				if(_fdOutgoingDay ==null){
					Float _fdOutgoingTime = (Float) pageContext.getAttribute("_fdOutgoingTime");
					_fdOutgoingTime = _fdOutgoingTime==null ? 0f:_fdOutgoingTime;

					pageContext.setAttribute("__fdOutgoingTime", SysTimeUtil.formatLeaveTimeStr(0f, _fdOutgoingTime));
				} else {
					pageContext.setAttribute("__fdOutgoingTime", NumberUtil.roundDecimal(_fdOutgoingDay, 2) );
				}
			%>
			${__fdOutgoingTime}
		</list:data-column>
		</c:if>
		
		<c:if test="${fn:contains(fdShowCols, 'fdMissedExcCount')}">
		<list:data-column col="fdMissedExcCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdMissedExcCount') }" escape="false">
			${model.fdMissedExcCount==null?0:model.fdMissedExcCount}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdLateExcCount')}">
		<list:data-column col="fdLateExcCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLateExcCount') }" escape="false">
			${model.fdLateExcCount==null?0:model.fdLateExcCount}
		</list:data-column>
		</c:if>
		<c:if test="${fn:contains(fdShowCols, 'fdLeftExcCount')}">
		<list:data-column col="fdLeftExcCount" title="${ lfn:message('sys-attend:sysAttendStatMonth.fdLeftExcCount') }" escape="false">
			${model.fdLeftExcCount==null?0:model.fdLeftExcCount}
		</list:data-column>
		</c:if>
		
		<c:forEach items="${model.fdOffDaysDetailJson }" var="detail" varStatus="vstatus">
			<c:if test="${fn:contains(fdShowCols, detail.key)}">
				<list:data-column col="${detail.key}" title="${detail.key}" escape="false">
					${detail.value}
				</list:data-column>
			</c:if>
		</c:forEach>
			<c:if test="${fn:contains(fdShowCols, 'fdDateDetail')}">
			<c:set var="__fdId" value="${model.fdId }"></c:set>
			<c:set var="__url" value="${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain_index.jsp#cri.q=docCreateTime:statDate;docCreateTime:statDate;docCreator:${model.docCreator.fdId}"/>
			<%
				String fdId = (String)pageContext.getAttribute("__fdId");
				Map<String, List> statMap = (Map<String, List>)request.getAttribute("statMap");
				pageContext.setAttribute("__statList",statMap.get(fdId));
			%>
			<c:forEach items="${__statList }" var="stat" varStatus="vstatus">
				<list:data-column col="${stat.key}" title="${stat.title}" escape="false">
					<c:set var="statusValue" value="${stat.value}"></c:set>
					<c:set var="__statDate" value="${stat.statDate}"></c:set>
					<%
						String statDate = (String)pageContext.getAttribute("__statDate");
						String url = (String)pageContext.getAttribute("__url");
						pageContext.setAttribute("statusUrl",url.replaceAll("statDate", statDate));
					%>
					<c:choose>
					    <%--全天旷工 --%>
						<c:when test="${statusValue==',03'}">
							<a href="javascript:void(0)" class="lui_stat_status_red" style="cursor: auto;font-weight: bold;"><bean:message bundle="sys-attend" key="sysAttendStatMonth.fdAbsent"/></a>
						</c:when>
						<%--休息 --%>
						<c:when test="${statusValue==',02'}">
							<a href="javascript:void(0)" class="lui_stat_status_light" style="cursor: auto;"><bean:message bundle="sys-attend" key="sysAttendReport.fdStatus.rest"/></a>
						</c:when>
						<c:otherwise>
							<%--正常 --%>
							<c:if test="${fn:contains(statusValue,',01')}">
								<a href="${statusUrl}" target="_blank" class="lui_stat_status_normal">
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
								</a>
							</c:if>
							<c:if test="${!fn:contains(statusValue,',01')}">
							<a href="${statusUrl}" target="_blank" class="lui_stat_status_red">
							<%--缺卡、迟到早退、外勤 、正常--%>
							
							<%--缺卡1 --%>
							<c:if test="${fn:contains(statusValue,',071') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.unSign"/>
								</div>
							</c:if>
							<%--迟到早退1--%>
							<c:if test="${fn:contains(statusValue,',081')||fn:contains(statusValue,',091') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.late"/>
									<c:if test="${fn:contains(statusValue,',091') }">
									&nbsp
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutside"/>
									</c:if>
								</div>
							</c:if>
							<%-- 外勤1--%>
							<c:if test="${ fn:contains(statusValue,',141')}">
								<div style="white-space: nowrap;color: #4285f4;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.outside"/>
								</div>
							</c:if>
							<%--正常1--%>
							<c:if test="${fn:contains(statusValue,',211') }">
								<div style="color: #343434;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
								</div>
							</c:if>
							
							<%--缺卡2 --%>
							<c:if test="${ fn:contains(statusValue,',101')}">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.unSign"/>
								</div>
							</c:if>		
							<%--迟到早退2--%>
							<c:if test="${fn:contains(statusValue,',111')||fn:contains(statusValue,',121') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.left"/>
									<c:if test="${fn:contains(statusValue,',121') }">
									&nbsp
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutside"/>
									</c:if>
								</div>
							</c:if>
							<%-- 外勤2--%>
							<c:if test="${fn:contains(statusValue,',151') }">
								<div style="white-space: nowrap;color: #4285f4;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.outside"/>
								</div>
							</c:if>		
							<%--正常2--%>
							<c:if test="${fn:contains(statusValue,',212') }">
								<div style="color: #343434;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
								</div>
							</c:if>
												
							<%--缺卡3 --%>
							<c:if test="${fn:contains(statusValue,',072') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.unSign"/>
								</div>
							</c:if>
							<%--迟到早退3--%>
							<c:if test="${fn:contains(statusValue,',082')||fn:contains(statusValue,',092') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.late"/>
									<c:if test="${fn:contains(statusValue,',092') }">
									&nbsp
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutside"/>
									</c:if>
								</div>
							</c:if>
							<%-- 外勤3--%>
							<c:if test="${ fn:contains(statusValue,',142')}">
								<div style="white-space: nowrap;color: #4285f4;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.outside"/>
								</div>
							</c:if>		
							<%--正常3--%>
							<c:if test="${fn:contains(statusValue,',213') }">
								<div style="color: #343434;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.onwork"/>
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
								</div>
							</c:if>
							
							<%--缺卡4 --%>
							<c:if test="${ fn:contains(statusValue,',102')}">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.unSign"/>
								</div>
							</c:if>
							<%--迟到早退4--%>	
							<c:if test="${fn:contains(statusValue,',112')||fn:contains(statusValue,',122') }">
								<div style="white-space: nowrap;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.left"/>
									<c:if test="${fn:contains(statusValue,',122') }">
									&nbsp
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutside"/>
									</c:if>
								</div>
							</c:if>	
							<%-- 外勤4--%>			
							<c:if test="${fn:contains(statusValue,',152') }">
								<div style="white-space: nowrap;color: #4285f4;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendMain.outside"/>
								</div>
							</c:if>	
							<%--正常4--%>
							<c:if test="${fn:contains(statusValue,',214') }">
								<div style="color: #343434;">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdWorkType.offwork"/>
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdStatus"/>
								</div>
							</c:if>
							
							
							<%-- 请假、出差、外出--%>
							<c:if test="${fn:contains(statusValue,',04') }">
								
									<c:if test="${not empty stat.offMap }">
									<c:forEach items="${stat.offMap.values() }" var="offValue" >
										<c:set var="fdOffTypeText" value="${offValue.fdOffTypeText}"></c:set>
										<c:set var="fdStatType" value="${offValue.fdStatType}"></c:set>
										<c:set var="fdNoonText" value="${offValue.fdNoonText}"></c:set>
										<c:set var="fdOffType" value="${offValue.fdOffType }"></c:set>
										<div style="white-space: nowrap;color: #4285f4;"><bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.askforleave"/>
										<c:if test="${not empty fdOffTypeText}">
											(${fdOffTypeText})
										</c:if>
										<c:if test="${fdStatType=='2' and not empty fdNoonText}">
											(${fdNoonText})
										</c:if>
										 <c:if test="${not empty stat.fdOffTime}">
											<c:forEach items="${stat.fdOffTime }" var="offTime" >
												<c:if test="${fn:substringBefore(offTime.key,'_') eq fn:substringBefore(fdOffType,'_') }">
													(${offTime.value})
												</c:if>
											</c:forEach>
										</c:if>
										</div>
									</c:forEach>
									</c:if>
								
							</c:if>
							<c:if test="${ fn:contains(statusValue,',05')}">
								<div style="white-space: nowrap;color: #4285f4;"><bean:message bundle="sys-attend" key="sysAttendStatMonth.fdTrip"/>
								 <c:if test="${not empty stat.fdTripTime}">
										<c:forEach items="${stat.fdTripTime }" var="tripTime" >
													(${tripTime.value})
											</c:forEach>
								 </c:if>
								</div>
							</c:if>
							<c:if test="${fn:contains(statusValue,',06') }">
								<div style="white-space: nowrap;color: #4285f4;"><bean:message bundle="sys-attend" key="sysAttendStatMonth.fdOutgoing"/>
								    <c:if test="${not empty stat.fdOutgoingTime}">
										<c:forEach items="${stat.fdOutgoingTime }" var="outTime" >
													(${outTime})
											</c:forEach>
									</c:if>
								</div>
							</c:if>
							</a>
							
							<%--非全天旷工 --%>
							<c:if test="${fn:contains(statusValue,',03') }">
								<a href="javascript:void(0)" class="lui_stat_status_red" style="cursor: auto;">
									<bean:message bundle="sys-attend" key="sysAttendStatMonth.fdAbsent"/>
								</a>
							</c:if>
							
							</c:if>
							<%-- 加班 --%>
							<c:if test="${fn:contains(statusValue,',13') }">
								<div style="white-space: nowrap;color:#4285f4;font-weight: 600;">
								<a href="${statusUrl}" target="_blank" class="lui_stat_status_normal">
									<bean:message bundle="sys-attend" key="sysAttendMain.fdStatus.overtime"/>
								</a>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
				</list:data-column>
			</c:forEach>
		</c:if>
		
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>