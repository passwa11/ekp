<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link href="${LUI_ContextPath}/sys/xform/maindata/dialog/mydata/css/mydata.css" rel="stylesheet" type="text/css" />

<div id="MydataTypesDiv" class="lui_mydata_listview_container" style="height:580px;overflow-y:auto;">
	<!-- 分类导航 -->
	<div class="lui_mydata_header">
		<div class="lui_mydata_qsearch">
			<div class="lui_mydata_search_box">
				<input type="text" name="keyword" placeholder="${lfn:message('sys-xform-maindata:sysFormMainData.search.keyword') }" onkeydown="enterTrigleSelect(event);">
				<a href="javascript:;" onclick="searchData();">${lfn:message('button.search') }</a>
			</div>
		</div>
	</div>
	
	<!-- 当前路径 -->
	<div class="lui_mydata_location"><bean:message key="page.curPath"/><span id="modelPath"></span></div>
	
	<!-- 数据列表 -->
	<list:listview id="Mydata_listview">
		<ui:source type="AjaxJson">
			{url:''}
		</ui:source>
		<list:colTable onRowClick="__rowClick('!{fdId}')">
			<list:col-checkbox headerStyle="width:30px"></list:col-checkbox>
			<list:col-serial headerStyle="width:50px"></list:col-serial>
			<list:col-auto></list:col-auto> 
		</list:colTable>
	</list:listview> 
	
	<list:paging></list:paging>
	
	<div class="lui_dialog_buttons_container">
		<ui:button text="${lfn:message('sys-xform-maindata:sysFormMainData.select.back') }" styleClass="lui_toolbar_btn_def" onclick="_select()"></ui:button>
		<ui:button text="${lfn:message('button.ok') }" styleClass="lui_toolbar_btn_def" onclick="_ok()"></ui:button>
		<ui:button text="${lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="_cancel()"></ui:button>
	</div>
</div>
