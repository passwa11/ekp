<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdNumberMain.fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-number:sysNumberMainFlow.fdNumberMain') }">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/number/sys_number_main_flow/sysNumberMainFlow.do?method=listByCreateSqlQuery&modelName=${param.modelName}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdNumberMain.fdName,fdVirtualNumberValue,fdFlowNum,fdLimitsValue,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 	   // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/number/sys_number_main_flow/sysNumberMainFlow.do" />?method=edit&fdId=' + id);
		 		};
		 	});
	 	</script>
	</template:replace>
</template:include>
