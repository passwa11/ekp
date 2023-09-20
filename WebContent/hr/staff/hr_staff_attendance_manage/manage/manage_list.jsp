<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffAttendanceManage" %>

<list:data>
	<list:data-columns var="manage" list="${queryPage.list }" varIndex="status">
		<%
			Date expirationDate = ((HrStaffAttendanceManage)pageContext.getAttribute("manage")).getFdExpirationDate();
			boolean isExpiration = false;
			if(expirationDate != null && new Date().after(expirationDate)){
				isExpiration = true;
			}
			pageContext.setAttribute("isExpiration", isExpiration);
		%>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="personInfoId">
			${manage.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<!-- 姓名（账号）-->
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		    ${manage.fdPersonInfo.nameAccount}
		</list:data-column>
		<!--年份-->
		<list:data-column headerClass="width100" property="fdYear" title="${ lfn:message('hr-staff:hrStaffAttendanceManage.fdYear') }">
		</list:data-column>
		<!--失效日期-->
		<list:data-column headerClass="width120" col="fdExpirationDate" title="${ lfn:message('hr-staff:hrStaffAttendanceManage.fdExpirationDate') }" escape="false">
		    <kmss:showDate value="${manage.fdExpirationDate}" type="date" />
		    <c:if test="${isExpiration}">
		    	<span style="color: red;">(${ lfn:message('hr-staff:hrStaffAttendanceManage.isExpiration') })</span>
		    </c:if>
		</list:data-column>
		<!--剩余年假天数-->
		<list:data-column headerClass="width120" col="fdDaysOfAnnualLeave" title="${ lfn:message('hr-staff:hrStaffAttendanceManage.fdDaysOfAnnualLeave') }">
			${manage.daysOfAnnualLeave}
		</list:data-column> 
		<!--剩余可调休天数-->
		<list:data-column headerClass="width120" col="fdDaysOfTakeWorking" title="${ lfn:message('hr-staff:hrStaffAttendanceManage.fdDaysOfTakeWorking') }">
			${manage.daysOfTakeWorking}
		</list:data-column>
		<!--当前剩余带薪病假天数-->
		<list:data-column headerClass="width200" col="fdDaysOfSickLeave" title="${ lfn:message('hr-staff:hrStaffAttendanceManage.fdDaysOfSickLeave') }">
			${manage.daysOfSickLeave}
		</list:data-column> 
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:_edit('${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/hrStaffAttendanceManage.do?method=edit&fdId=${manage.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/hrStaffAttendanceManage.do?method=deleteall', '${manage.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>