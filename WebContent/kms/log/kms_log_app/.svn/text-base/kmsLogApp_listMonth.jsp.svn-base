<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('kms-log:kmsLogApp.search.org') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1" expand="true">
			<list:cri-criterion title="${ lfn:message('kms-log:kmsLogApp.fdCreateTime') }" multi="false" expand="false" key="fdMonthInfo">
				<list:varDef name="selectBox">
				<list:box-select>
					<list:item-select 
						type="kms/log/kms_log_app/js/criterion_calendar_list_month!CriterionDateDatas" >
					</list:item-select>
				</list:box-select>
				</list:varDef>
			</list:cri-criterion>
			<list:cri-ref key="fdOperator" ref="criterion.sys.person" multi="false"
				title="${lfn:message('kms-log:kmsLogAppHistory.fdOperator') }">
			</list:cri-ref>
		</list:criteria>
		
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
 
			<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>  
			 
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/kms/log/kms_log_app/kmsLogApp.do?method=listMonth'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" >
				
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdCreateTime,fdOperatorName,operateText,moduleName,fdIp,fdSubject,fdTargetId"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	</template:replace>
</template:include>
