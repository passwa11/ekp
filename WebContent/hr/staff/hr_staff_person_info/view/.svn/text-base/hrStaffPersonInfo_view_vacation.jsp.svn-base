<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService,com.landray.kmss.util.SpringBeanUtil,java.util.List,com.landray.kmss.sys.time.model.SysTimeLeaveRule" %>
<%
	ISysTimeLeaveAmountService sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
	List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveAmountService.getAllLeaveRule();
	String leaveNames = "";
	for(SysTimeLeaveRule leaveRule : leaveRuleList) {
		leaveNames += leaveRule.getFdName() + ";";
	}
	pageContext.setAttribute("leaveNames", leaveNames);
%>
    <div class="lui-personnel-file-staffInfo" id="holidayType">
        <div class="lui-personnel-file-header-title">
           <div class="lui-personnel-file-header-title-left">
             <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:hr.staff.nav.attendance.management') }</div><div class="lui-personnel-file-header-title-line"></div>
           </div>
         </div>
         <div class="holidayTypelist">
           <span>${ lfn:message('hr-staff:hrStaffAttendanceManage.paidHoliday') }</span>
           <span>${ lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed') }</span>
           <span>${ lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed.overtime') }</span>
         </div>
		<div style="display:block;" class="holiday-list-conent newTableList">
			<list:listview channel="hrStaffAttendanceManage">
				<ui:source type="AjaxJson">
					{url:'/sys/time/sys_time_leave_amount/sysTimeLeaveAmount.do?method=list&personId=${hrStaffPersonInfoForm.fdId}'}
				</ui:source>
				<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManage">
					<list:col-serial></list:col-serial> 
					<list:col-auto props="fdYear;${leaveNames}totalRest;"></list:col-auto>
				</list:colTable>
			</list:listview> 
			<list:paging channel="hrStaffAttendanceManage" />
		</div>
		<div style="display:none;" class="holiday-list-conent newTableList">
			<%-- 请假明细 --%>
			<list:listview channel="hrStaffAttendanceManageDetailed">
				<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&personId=${hrStaffPersonInfoForm.fdId}'}
				</ui:source>
				<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManageDetailed">
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdLeaveTime;fdReview;fdStartTime;fdEndTime;fdLeaveName;fdOprType;fdOprDesc;"></list:col-auto>
				</list:colTable>
			</list:listview>
			<list:paging channel="hrStaffAttendanceManageDetailed" />
		</div>
		<div style="display:none;" class="holiday-list-conent newTableList">
			<%-- 加班明细 --%>
			<list:listview channel="hrStaffAttendanceManageDetailed_Overtime">
				<ui:source type="AjaxJson">
					{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&personId=${ hrStaffPersonInfoForm.fdId}&fdType=2'}
				</ui:source>
				<list:colTable isDefault="false" layout="sys.ui.listview.columntable" name="columntable" channel="hrStaffAttendanceManageDetailed_Overtime">
					<list:col-serial></list:col-serial> 
					<list:col-auto props="fdLeaveTime;fdReview;fdStartTime;fdEndTime;fdLeaveName;fdOprType;fdOprDesc;"></list:col-auto>
				</list:colTable>
			</list:listview> 
			<list:paging channel="hrStaffAttendanceManageDetailed_Overtime" />
		</div>
   </div>

<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		$('.holidayTypelist span').eq(0).addClass('lui-personnel-file-active-holidayType')
		$('.holidayTypelist span').on('click',function(){
		  $('.holidayTypelist span').removeClass('lui-personnel-file-active-holidayType')
		  $(this).addClass('lui-personnel-file-active-holidayType')
		  $(".holiday-list-conent").hide()
		  $(".holiday-list-conent").eq($(this).index()).show()
		})
	})
</script>
	
