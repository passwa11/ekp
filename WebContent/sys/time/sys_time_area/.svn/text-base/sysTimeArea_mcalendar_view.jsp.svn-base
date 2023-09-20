<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<%-- 样式 --%>
	<template:replace name="head">
		<template:super/>
		<link href="resource/css/calendar_view.css" rel="stylesheet">
		<link href="resource/css/mcalendar.css" rel="stylesheet">
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="body">
	
		<div class="toolbar">
			<table width="100%" align="center" class="tb_simple">
				<tr>
					<td width="10px" colspan="2">
						<div class="lui_mcalendar_search">
							<div>
								<input type="text" name="search_name" style="width: 320px;padding: 3px;" placeholder="请输入关键字(可根据姓名、登录名、部门名查询)"> 
								<ui:button text="查询" onclick="onCalendarSearch()"></ui:button>
							</div>
						</div>
						
					</td>
				</tr>
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
						
						<div class="selector" style="float: right;">
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
					</td>
				</tr>
			</table>
		</div>
		
		<div style="float:right">
			<div style="display: inline-block;vertical-align: middle;">
				<ui:toolbar id="Btntoolbar">
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=exportExcel&fdId=${sysTimeAreaForm.fdId}" requestMethod="GET">
						<!-- 下载导入模版-->
						<ui:button text="${lfn:message('sys-time:sysTimeArea.oper.template.download')}" onclick="exportExcel(true)" order="1" ></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=importExcel&fdId=${sysTimeAreaForm.fdId}" requestMethod="GET">
						<!-- 批量导入 -->
						<ui:button text="${lfn:message('sys-time:sysTimeArea.oper.batch.import')}" onclick="importExcel()" order="2" ></ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_area/sysTimeArea.do?method=exportExcel&fdId=${sysTimeAreaForm.fdId}" requestMethod="GET">
						<!-- 导出排班明细 -->
						<ui:button text="${lfn:message('sys-time:sysTimeArea.oper.export.detail')}" onclick="exportExcel(false)" order="3" ></ui:button>
					</kmss:auth>
				</ui:toolbar>
			</div>
		</div>
			
		<div class="calendar-wrapper" style="margin-top: 48px;">
			<div id="calendar"></div>
		</div>
		<%@include file="/sys/time/sys_time_area/sysTimeArea_mcalendar_view_js.jsp"%>
	</template:replace>
</template:include>