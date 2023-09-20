<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					    <list:sortgroup>
							<list:sort property="fdCreateTime" text="${lfn:message('km-review:kmReviewTemplate.docCreateTime') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getProcessAnalysisList&fdTempletModelId=${param.fdTempletModelId}&fdCreateTime=${param.fdCreateTime}&status=${param.status}&excuteType=${param.excuteType}&ower=1'}
			</ui:source>
			
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" rowHref="!{url}" name="columntable">
		
		<list:col-serial title="${ lfn:message('page.serial')}"
			headerStyle="width:20px"></list:col-serial>

		<list:col-auto props="subject,fdStatus,fdIdentity.fdName,fdCreateTime,fdEndedTime,fdCostTime"></list:col-auto>

		</list:colTable>
		<ui:event topic="list.loaded">
					 
		</ui:event>
	
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
		
	 	
	 	
	</template:replace>
</template:include>
