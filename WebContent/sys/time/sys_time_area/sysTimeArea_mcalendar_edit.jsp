<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">

	<%-- 样式 --%>
	<template:replace name="head">
		<link rel="Stylesheet" href="resource/libs/bootstrap/bootstrap.min.css" />
		<link rel="Stylesheet" href="resource/libs/yearcalendar/yearcalendar.css" />
		<link href="resource/css/calendar_edit.css" rel="stylesheet">
		<link href="resource/css/mcalendar.css" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<ui:button text="${ lfn:message('sys-time:button.clear.all') }" order="1" onclick="window.clearAllCustomData()">
			</ui:button>
			<ui:button text="${lfn:message('button.save') }" order="2" onclick="window.submit()">
			</ui:button>
			<ui:button text="${ lfn:message('button.close') }" order="3"  onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		<html:form action="/sys/time/sys_time_area/sysTimeArea.do">
		
		<html:hidden property="method_GET"/>
		<html:hidden property="fdId"/>
		<html:hidden property="areaMemberIds"/>
		<html:hidden property="areaMemberNames"/>
		<html:hidden property="areaAdminIds"/>
		<html:hidden property="areaAdminNames"/>
		<html:hidden property="orgElementData"/>
		
		<div class="lui_mcalendar_edit lui_mcalendar_search">
			<div>
				<input onkeydown="if(event.keyCode==13){this.blur();return false;}" type="text" name="search_name" style="width: 320px;padding: 3px;" placeholder="请输入关键字(可根据姓名、登录名、部门名查询)"> 
				<ui:button text="${ lfn:message('button.search') }" onclick="onCalendarSearch()"></ui:button>
			</div>
		</div>
		<div class="toolbar">
			<div class="top">
				<div id="typeTabs" class="tabs" data-content-class="tab-content">
					<div class="tab active" data-type="1" data-content-id="classTab">
						<span></span>
						${ lfn:message('sys-time:sysTimeArea.work') }
					</div>
					<div class="tab" data-type="2">
						<span></span>
						${ lfn:message('sys-time:sysTimeArea.vacation') }
					</div>
					<div class="tab" data-type="3" data-content-id="classTab">
						<span></span>
						${ lfn:message('sys-time:sysTimeArea.pach') }
					</div>
				</div>
				<div class="selectors">
				
					<span style="margin: 0 10px 0 17px;">所属节假日：</span>
					
					<html:hidden property="fdHolidayName"/>
					<xform:select property="fdHolidayId" showStatus="edit" onValueChange="handleHolidayChange" value="${sysTimeAreaForm.fdHolidayId}">
						<xform:beanDataSource serviceBean="sysTimeHolidayService" />
					</xform:select>
				
					<span style="margin: 0 10px 0 17px;">默认工作日：</span>
					<select name="fromWeek">
						<option value="1">周日</option>
						<option value="2" selected>周一</option>
						<option value="3">周二</option>
						<option value="4">周三</option>
						<option value="5">周四</option>
						<option value="6">周五</option>
						<option value="7">周六</option>
					</select>
					<span style="margin: 0 4px;">至</span>
					<select name="toWeek">
						<option value="1">周日</option>
						<option value="2">周一</option>
						<option value="3">周二</option>
						<option value="4">周三</option>
						<option value="5">周四</option>
						<option value="6" selected>周五</option>
						<option value="7">周六</option>
					</select>
					
					<span style="margin: 0 10px 0 17px;">日期：</span>
					<select name="year">
					</select>
					<span style="margin: 0 4px;">年</span>
					<select name="month">
						<option value="0">1</option>
						<option value="1">2</option>
						<option value="2">3</option>
						<option value="3">4</option>
						<option value="4">5</option>
						<option value="5">6</option>
						<option value="6">7</option>
						<option value="7">8</option>
						<option value="8">9</option>
						<option value="9">10</option>
						<option value="10">11</option>
						<option value="11">12</option>
					</select>
					<span style="margin: 0 4px;">月</span>
					
				</div>
				
			</div>
			<div class="middle">
				<div class="tab-content" id="classTab">
				</div>
				<div>
					<p style="color: red; margin-top: 4px;">
						<bean:message bundle="sys-time" key="sysTimeArea.message.prefix"/>
						<c:if test="${sysTimeAreaForm.fdIsBatchSchedule=='true' || not empty sysTimeAreaForm.sysTimeWorkList }">
						<a style="color: #337ab7;text-decoration: none;" href="#" onclick="redirectToWorkView()"><bean:message bundle="sys-time" key="sysTimeArea.button.workView"/></a>
							</c:if>
						<c:if test="${sysTimeAreaForm.fdIsBatchSchedule=='false' && empty sysTimeAreaForm.sysTimeWorkList }"><bean:message bundle="sys-time" key="sysTimeArea.button.workView"/></c:if>
						<bean:message bundle="sys-time" key="sysTimeArea.message.suffix"/>
					</p>
				</div>
			</div>
		</div>
		
		<div class="main">
			<div id="calendar"></div>
		</div>
		</html:form>
		
		<%@include file="/sys/time/sys_time_area/sysTimeArea_mcalendar_edit_js.jsp"%>

	</template:replace>
</template:include>