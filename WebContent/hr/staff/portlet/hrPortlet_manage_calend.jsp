<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link type="text/css" rel="stylesheet" href="<c:url value="/hr/staff/portlet/hr_portlet.css"/>?s_cache=${LUI_Cache}"/>
<script type="text/javascript" src="<c:url value="/hr/staff/portlet/hr_portlet.js"/>?s_cache=${LUI_Cache}"></script>
<div>
	<div class="lui_hr_platform_component">
		<!-- 日历 -->
		<div class="lui_hr_platform_calendar_container">
			<div class="lui_hr_platform_calendar_wrap">
				<div class="lui_hr_platform_calendar_top">

					<div class="lui_hr_platform_calendar_top_r">
						<div class="lui_hr_platform_calendar_top_r1">
							<span onclick ="calendarChange(10,'${type}')">今天</span> 
							<span  onclick ="calendarChange(0,'${type}')">
								刷新</span>
						</div>
						<div class="lui_hr_platform_calendar_top_r2">
							<span class="lui_hr_platform_calendar_pre" onclick ="calendarChange(-1,'${type}')" ><</span>
							<span class="lui_hr_platform_calendar_date_txt" onclick ="calendarChange(0,'${type}')"></span> 
							<span class="lui_hr_platform_calendar_next" onclick ="calendarChange(1,'${type}')">></span>
						</div>
					</div>
					<div class="lui_hr_platform_calendar_top_l">${data.calendFormat.day }</div>
				</div>
				<div class="lui_hr_platform_calendar_table">
					<ul>
						<li class="lui_hr_platform_calendar_table_week">日</li>
						<li class="lui_hr_platform_calendar_table_week">一</li>
						<li class="lui_hr_platform_calendar_table_week">二</li>
						<li class="lui_hr_platform_calendar_table_week">三</li>
						<li class="lui_hr_platform_calendar_table_week">四</li>
						<li class="lui_hr_platform_calendar_table_week">五</li>
						<li class="lui_hr_platform_calendar_table_week">六</li>
					
					</ul>
					<ul class="lui_hr_platform_calendar_table_content">
						
					</ul>
				</div>
			</div>
			<div class="lui_hr_platform_calendar_schedule">
				<h4 class="lui_hr_platform_calendar_schedule_title">当日事项</h4>
				<ul>
					 <ul>
					 	
					 		<kmss:auth requestURL="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=list" requestMethod="POST">
					 		<%	
                           		request.setAttribute("entryAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_entry" onclick="onclickSchedule('entry','${entryAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">待入职</span>
                                    <em class="schedule_num">0</em>
                            </li>
                       		
                       		<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list" requestMethod="POST">
                            <%	
                           		request.setAttribute("leaveAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_leave" onclick="onclickSchedule('leave','${leaveAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">离职</span>
                                    <em class="schedule_num">0</em>
                            </li>
                            
                            <kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list" requestMethod="POST">
					 		<%	
                           		request.setAttribute("positiveAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_positive" onclick="onclickSchedule('positive','${positiveAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">转正</span>
                                    <em class="schedule_num">0</em>
                            </li>
                          	
                          	<kmss:auth requestURL="/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=list" requestMethod="POST">
					 		<%	
                           		request.setAttribute("contractAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_contract" onclick="onclickSchedule('contract','${contractAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">合同到期</span>
                                    <em class="schedule_num">0</em>
                            </li>
                            
                            <kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list" requestMethod="POST">
					 		<%	
                           		request.setAttribute("annualAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_annual" onclick="onclickSchedule('annual','${annualAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">周年</span>
                                    <em class="schedule_num">0</em>
                            </li>
                            
                            <kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list" requestMethod="POST">
					 		<%	
                           		request.setAttribute("birthdayAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_birthday" onclick="onclickSchedule('birthday','${birthdayAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">生日</span>
                                    <em class="schedule_num">0</em>
                            </li>
                            
                            <kmss:auth requestURL="/hr/staff/portlet.do?method=listTransfer" requestMethod="POST">
					 		<%	
                           		request.setAttribute("transferAuth", "true");
                            %>
                            </kmss:auth>
                            <li class="lui_hr_platform_calendar_schedule_li" data-lui-mark="lui_hr_schedule_transfer" onclick="onclickSchedule('transfer','${transferAuth}')">
                                    <span class="schedule_icon"></span>
                                    <span class="schedule_title">调岗</span>
                                    <em class="schedule_num">0</em>
                            </li>                              
                     </ul>

				</ul>
			</div>
		</div>
	</div>
</div>
<script>
hrPlatformCalendarInit(${data},'${type}')
</script>