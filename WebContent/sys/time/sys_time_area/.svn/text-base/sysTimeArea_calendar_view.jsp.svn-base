<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">

	<%-- 样式 --%>
	<template:replace name="head">
	
		<template:super/>

		<link rel="Stylesheet" href="resource/libs/bootstrap/bootstrap.min.css" />
		<link rel="Stylesheet" href="resource/libs/yearcalendar/yearcalendar.css" />
		<link href="resource/css/calendar_view.css" rel="stylesheet">

	</template:replace>
	
	<%--内容区--%>
	<template:replace name="body">
	
		<div class="toolbar">
		
			<table width="100%" align="center" class="tb_simple">
	
				<tr>
					<td width="10px" style="white-space: nowrap; vertical-align: top; top: 1px; position: relative;">
						注：
					</td>
					<td align="left" id="classes">
						
					</td>
				</tr>
				
				<tr>
					<td width="10px" style="white-space: nowrap; vertical-align: top; position: relative; top: 2px;">

					</td>
					<td align="left">		
						<div class="tag">
							<span class="tag-flag-2" style="border-top-color: #E60000;"></span><span class="tag-label"><bean:message  bundle="sys-time" key="calendar.data.list.holiday"/></span>
						</div>
						<div class="tag">
							<span class="tag-flag-2" style="border-top-color: #2B9AE0;"></span><span class="tag-label"><bean:message  bundle="sys-time" key="calendar.data.list.pach"/></span>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div class="main">
			<div id="calendar" class="calendar"></div>
		</div>
		
		<%@include file="/sys/time/sys_time_area/sysTimeArea_calendar_view_js.jsp"%>

	</template:replace>
</template:include>