<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%
	//默认今年
	Calendar c = Calendar.getInstance();
	c.setTime(new Date());
	int endYear = c.get(Calendar.YEAR);
	int startYear = 2018;
	JSONArray jsonArr = new JSONArray();
	JSONObject jsonObj = new JSONObject();
	for(int i=endYear; i>=startYear; i--){
		String text = i + ResourceUtil.getString("calendar.year"); 
		jsonObj.put("text", text);
		jsonObj.put("value", i);
		jsonArr.add(jsonObj);
	}
	request.setAttribute("year", endYear);
	request.setAttribute("yearArr", jsonArr.toString());
	//默认当月
	c = Calendar.getInstance();
	c.setTime(new Date());
	int currentMonth = c.get(Calendar.MONTH)+1;
	int endMonth = 12;
	int startMonth = 1;
	jsonArr = new JSONArray();
	jsonObj = new JSONObject();
	for(int i=endMonth; i>=startMonth; i--){
		String text = i + ResourceUtil.getString("calendar.month"); 
		jsonObj.put("text", text);
		jsonObj.put("value", i);
		jsonArr.add(jsonObj);
	}
	request.setAttribute("month", currentMonth);
	request.setAttribute("monthArr", jsonArr.toString());
%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogNotify') }</template:replace>
	<template:replace name="content">

		<!-- 筛选 -->
		<list:criteria channel="sysLogNotify">
			<list:cri-ref expand="true" key="fdSubject" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogNotify.fdSubject') }" />
			<!-- 年份 -->
			<list:cri-criterion title="${lfn:message('sys-log:sysLogBak.fdYear') }" key="fdYear" expand="true">
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue="${year}">
						<ui:source type="Static">
							${yearArr}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 月份 -->
			<list:cri-criterion title="${lfn:message('sys-log:sysLogBak.fdMonth') }" key="fdMonth" expand="true">
				<list:box-select>
					<list:item-select cfg-required="true" cfg-defaultValue="${month}">
						<ui:source type="Static">
							${monthArr}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
        </list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall channel="sysLogNotify"></list:selectall>
			</div>
			
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogNotify">
					<list:sortgroup>
					<list:sort channel="sysLogNotify" property="fdMonth" text="${lfn:message('sys-log:sysLogBak.fdMonth') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="sysLogNotify"> 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5" channel="sysLogNotify">
	                    <%-- 日志导出按钮 --%>
						<c:import url="/sys/log/sys_log_export/ui/buttons.jsp">
							<c:param name="listMethod" value="listNotify"/>
							<c:param name="eventType" value="notify"/>
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<!-- 内容列表 -->
		<list:listview id="sysLogNotify" channel="sysLogNotify">
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_notify/sysLogNotify.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_notify/sysLogNotify.do?method=view&fdId=!{fdId}" channel="sysLogNotify">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdSubject;fdNotifyTargets;fdNotifyType;fdCreateTime;"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
	 			/* 隐藏最后一页按钮及翻页功能 */
	 			$('.lui_paging_jump_left').hide();
	 			if($('.lui_paging_ellipsis_left').length > 0) $('.lui_paging_num_left:last').hide();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging channel="sysLogNotify"/>
	</template:replace>
</template:include>
