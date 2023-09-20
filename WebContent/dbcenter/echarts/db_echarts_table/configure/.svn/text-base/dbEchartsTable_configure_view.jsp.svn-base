<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.forms.DbEchartsTabelInfo"%>
<template:include ref="default.simple">
	<template:replace name="title">
		<c:out value="${info.docSubject}" />
	</template:replace>
	<template:replace name="body">
		<style>
			.lui_listview_columntable_table thead th {
				white-space: normal;
				line-height:20px;
			}
			.close-detail {
				display:inline-block;
				float:right;
				margin-right:5px;
				padding:5px;
			}
		</style>
		<form name="dbEchartsTableForm" method="post">
			<input type="hidden" name="fdId" value="${param.fdId}">
		</form>
		<script type="text/javascript">
		</script>
			<p class="txttitle"><c:out value="${info.docSubject}" /></p>
			<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/criteria.jsp" />
			<div class="lui_list_operation">
				<div style='color: #979797;float: left;padding-top:1px;'>
					${ lfn:message('list.orderType') }：
				</div>
				<div style="float:left">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" >
							<c:forEach items="${info.config.columns}" var="column">
								<c:if test="${column.sort }">
									<list:sort property="${column.field}" text="${column.columnName!=''?column.columnName:column.text}" group="sort.list" />
								</c:if>
							</c:forEach>
						</ui:toolbar>
					</div>
				</div>
				<c:if test="${info.config.listview.page == 'true'}">
					<div>	
						<list:paging layout="sys.ui.paging.top" />
					</div>
				</c:if>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<list:listview id="listview1">
				<ui:source type="AjaxJson">{url:'/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=data&fdId=${param.fdId}'}</ui:source>
				<list:colTable layout="sys.ui.listview.columntable"  name="columntable">
					<list:col-serial></list:col-serial>
					<c:forEach items="${table.columns}" var="column">
						<c:if test="${column.hidden != true}">
							<list:col-column property="${column.key}" title="${column.name!=''?column.name:column.text}" style="text-align:${column.align}" headerStyle="width:${column.width};"/>
						</c:if>
					</c:forEach>
				</list:colTable>
				<ui:event topic="list.loaded">  
				   seajs.use(['lui/jquery'],function($){
						if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
							window.frameElement.style.height =  $(document.body).height() +10+ "px";
						}
					});
				</ui:event>
			</list:listview> 
			<c:if test="${info.config.listview.page == 'true'}">
		 		<list:paging></list:paging>
		 	</c:if>
		</div>
		<br>
	</template:replace>
</template:include>