<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
	
		<html:form action="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do" onsubmit="return checkData();">
			<html:hidden property="method_GET"/>
			
			<div style="margin-top:25px">
			
				<p class="configtitle">
					<bean:message key="kmCalendarBaseConfig.timeParameter.setting" bundle="km-calendar"/>
				</p>
					
				<center>
					<table class="tb_normal" width=90%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmCalendarBaseConfig.calendar.minuteStep" bundle="km-calendar"/>
							 </td>
							<td colspan="3">
								<xform:radio property="calendarMinuteStep" showStatus="edit">
									<xform:simpleDataSource value="1">
										1<bean:message key="date.interval.minute" />
									</xform:simpleDataSource>
									<xform:simpleDataSource value="5">
										5<bean:message key="date.interval.minute" />
									</xform:simpleDataSource>
									<xform:simpleDataSource value="10">
										10<bean:message key="date.interval.minute" />
									</xform:simpleDataSource>
									<xform:simpleDataSource value="15">
										15<bean:message key="date.interval.minute" />
									</xform:simpleDataSource>
								</xform:radio><br>
								<font color="red" ><bean:message bundle="km-calendar" key="kmCalendarBaseConfig.calendar.minuteStep.tips"/></font>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmCalendarBaseConfig.calendar.weekStartDate" bundle="km-calendar"/>
							</td>
							<td colspan=3>
					 			<xform:select property="calendarWeekStartDate" value="${calendarWeekStartDate }" showStatus="edit" showPleaseSelect="false">
									<xform:simpleDataSource value="1"><bean:message key="date.weekDay0" /></xform:simpleDataSource>
									<xform:simpleDataSource value="2"><bean:message key="date.weekDay1" /></xform:simpleDataSource>
									<xform:simpleDataSource value="3"><bean:message key="date.weekDay2" /></xform:simpleDataSource>
									<xform:simpleDataSource value="4"><bean:message key="date.weekDay3" /></xform:simpleDataSource>
									<xform:simpleDataSource value="5"><bean:message key="date.weekDay4" /></xform:simpleDataSource>
									<xform:simpleDataSource value="6"><bean:message key="date.weekDay5" /></xform:simpleDataSource>
									<xform:simpleDataSource value="7"><bean:message key="date.weekDay6" /></xform:simpleDataSource>
								</xform:select><br/>
								<font><bean:message bundle="km-calendar" key="kmCalendarBaseConfig.calendar.weekStartDate.tips"/></font>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmCalendarBaseConfig.calendar.displayType" bundle="km-calendar"/>
							</td>
							<td colspan=3>
								<xform:select property="calendarDisplayType" value="${calendarDisplayType }" showStatus="edit" showPleaseSelect="false">
									<xform:enumsDataSource enumsType="common_calendar_type"></xform:enumsDataSource>
								</xform:select><br/>
								<font><bean:message bundle="km-calendar" key="kmCalendarBaseConfig.calendar.displayType.tips"/></font>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message key="kmCalendarBaseConfig.calendar.updateAuthDate" bundle="km-calendar"/>
							</td>
							<td>
								<xform:select property="updateAuthDate" value="${updateAuthDate }" showStatus="edit" showPleaseSelect="false">
									<xform:simpleDataSource value="month"><bean:message key="kmCalendarBaseConfig.calendar.updateAuthDate.month" bundle="km-calendar" /></xform:simpleDataSource>
									<xform:simpleDataSource value="week"><bean:message key="kmCalendarBaseConfig.calendar.updateAuthDate.week" bundle="km-calendar" /></xform:simpleDataSource>
									<xform:simpleDataSource value="day"><bean:message key="kmCalendarBaseConfig.calendar.updateAuthDate.day" bundle="km-calendar" /></xform:simpleDataSource>
									<xform:simpleDataSource value="now"><bean:message key="kmCalendarBaseConfig.calendar.updateAuthDate.now" bundle="km-calendar" /></xform:simpleDataSource>
								</xform:select><br/>
								<font><bean:message bundle="km-calendar" key="kmCalendarBaseConfig.calendar.updateAuthDate.tips"/></font>
							</td>
						</tr>
					</table>
					
					<div style="margin-bottom: 10px;margin-top:25px">
						<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmCalendarBaseConfigForm, 'updateTimeParameter');" order="1" ></ui:button>
					</div>
					
				</center>
				
			</div>
			
		</html:form>
		
		<script>
			
			window.checkData = function(){
				
				var calendarMinuteStep = $('input[name=calendarMinuteStep]:checked').val();
				var calendarWeekStartDate = $('select[name=calendarWeekStartDate]').val();
				var calendarDisplayType = $('select[name=calendarDisplayType]').val();
				
				var checked = calendarMinuteStep && calendarWeekStartDate && calendarDisplayType;
				
				if(!checked) {				
					//TODO 校验不通过提示
				}
				
				return checked;				
			}
		
		</script>
		
		
	</template:replace>
</template:include>
<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>
