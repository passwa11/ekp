<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
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
	<template:replace name="title">${ lfn:message('sys-log:table.sysLogSystem') }</template:replace>
	<template:replace name="content">
		<script>
			//根据fdType改变fdSuccess的可选值
		 	seajs.use(['lui/jquery', 'lui/dialog','lui/topic'], function($, dialog , topic) {
				topic.channel('sysLogSystem').subscribe('criteria.spa.changed',function(evt){
					var fdTypeChanged = false;
	        		for(var i=0;i<evt['criterions'].length;i++){
		             	if(evt['criterions'][i].key=="fdType"){
			                var v = evt['criterions'][i].value[0];
			                if(v == 0){
				                //定时任务
			                	LUI('fdSuccessCriteria').source.source = [{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.m1') }", value:'-1'},
			                	      									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.0') }", value:'0'}]
			                	LUI('fdSuccessCriteria').isDrawed = false;
			                	LUI('fdSuccessCriteria').draw();
			                	fdTypeChanged = true;
				                
			                }else if(v == 1){
			                	//webservice
			                	LUI('fdSuccessCriteria').source.source = [{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.0') }", value:'0'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.1') }",value:'1'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.2') }",value:'2'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.3') }",value:'3'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.4') }",value:'4'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.5') }",value:'5'}]
			                	LUI('fdSuccessCriteria').isDrawed = false;
			                	LUI('fdSuccessCriteria').draw();
			                	fdTypeChanged = true;
			                }else if(v == 2){
			                	//restservice
			                	LUI('fdSuccessCriteria').source.source = [{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.0') }", value:'0'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.1') }",value:'1'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.2') }",value:'2'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.3') }",value:'3'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.4') }",value:'4'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.5') }",value:'5'},
			                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.6') }",value:'6'}]
			                	LUI('fdSuccessCriteria').isDrawed = false;
			                	LUI('fdSuccessCriteria').draw();
			                	fdTypeChanged = true;
			                }
		             	}
					}
	        		if(!fdTypeChanged){
	        			//不限
	                	LUI('fdSuccessCriteria').source.source = [{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.m1') }", value:'-1'},
	                	      									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.0') }", value:'0'},
	                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.1') }",value:'1'},
	                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.2') }",value:'2'},
	                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.3') }",value:'3'},
	                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.4') }",value:'4'},
	                	    									{text:"${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.5') }",value:'5'}]
	                	LUI('fdSuccessCriteria').isDrawed = false;
	                	LUI('fdSuccessCriteria').draw();
	                }
	        	});
	    	});
		</script>
		<!-- 筛选 -->
		<list:criteria channel="sysLogSystem">
			<list:cri-ref expand="true" key="fdSubject" ref="criterion.sys.string" title="${lfn:message('sys-log:sysLogSystem.fdSubject') }" />
			<list:cri-criterion title="${lfn:message('sys-log:sysLogSystem.fdSuccess') }" key="fdSuccess"  expand="true"> 
				<list:box-select>
					<list:item-select id="fdSuccessCriteria">
						<ui:source type="Static">
							[{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.m1') }', value:'-1'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.0') }', value:'0'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.1') }',value:'1'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.2') }',value:'2'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.3') }',value:'3'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.4') }',value:'4'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.5') }',value:'5'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdSuccess.6') }',value:'6'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-criterion title="${lfn:message('sys-log:sysLogSystem.fdType') }" key="fdType" expand="true"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${lfn:message('sys-log:sysLogSystem.enum.fdType.0') }', value:'0'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdType.1') }',value:'1'},
							{text:'${lfn:message('sys-log:sysLogSystem.enum.fdType.2') }',value:'2'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
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
				<list:selectall channel="sysLogSystem"></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="sysLogSystem">
					<list:sortgroup>
						<list:sort channel="sysLogSystem" property="fdStartTime" text="${lfn:message('sys-log:sysLogSystem.fdStartTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="sysLogSystem"> 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="5">
	                    <%-- 日志导出按钮 --%>
						<c:import url="/sys/log/sys_log_export/ui/buttons.jsp">
							<c:param name="listMethod" value="listSystem"/>
							<c:param name="eventType" value="system"/>
						</c:import>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<!-- 内容列表 -->
		<list:listview id="sysLogSystem" channel="sysLogSystem">
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_system/sysLogSystem.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_system/sysLogSystem.do?method=view&fdId=!{fdId}" channel="sysLogSystem">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<% if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
				<list:col-auto props="fdStartTime,fdEndTime,fdTaskDuration,fdSuccess"></list:col-auto>
				<% } else { %>
				<list:col-auto props="fdStartTime,fdEndTime,fdTaskDuration,fdSubject,fdSuccess"></list:col-auto>
				<% } %>
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
	 	<list:paging channel="sysLogSystem"/>
	</template:replace>
</template:include>
