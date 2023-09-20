<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">

	<%-- 样式 --%>
	<template:replace name="head">
		<link rel="Stylesheet" href="resource/libs/bootstrap/bootstrap.min.css" />
		<link rel="Stylesheet" href="resource/libs/yearcalendar/yearcalendar.css" />
		<link href="resource/css/calendar_edit.css" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<ui:button text="清空全部" order="1" onclick="window.clearAllCustomData()">
			</ui:button>
			<ui:button text="${lfn:message('button.save') }" order="2" onclick="window.submit()">
			</ui:button>
			<ui:button text="${ lfn:message('button.close') }" order="3"  onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		
		<div class="toolbar">
			<div class="top">
				<div id="typeTabs" class="tabs" data-content-class="tab-content">
					<div class="tab active" data-type="1" data-content-id="classTab">
						<span></span>
						工作日
					</div>
					<div class="tab" data-type="2">
						<span></span>
						假期
					</div>
					<div class="tab" data-type="3" data-content-id="classTab">
						<span></span>
						补班
					</div>
				</div>
				<div class="selectors">
				
					<span style="margin: 0 10px 0 17px;">所属节假日：</span>
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
					
				</div>
				
			</div>
			<div class="middle">
				<div class="tab-content" id="classTab">
				</div>
			</div>
		</div>
		
		<div class="main">
			<div id="calendar" class="calendar"></div>
		</div>
		
		<%@include file="/sys/time/sys_time_area/sysTimeArea_calendar_edit_js.jsp"%>

	</template:replace>
</template:include>