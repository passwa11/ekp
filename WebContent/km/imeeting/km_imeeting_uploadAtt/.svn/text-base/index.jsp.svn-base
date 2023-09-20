<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 上会材料 --%>
<template:include ref="default.simple" spa="true">
	<%-- 右侧内容区域 --%>
	<template:replace name="body"> 
		<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<%-- 筛选器 --%>
		<list:criteria id="imeetingCriteria">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-imeeting:kmImeetingMain.fdName') }" />
			<%-- 分类导航 --%>
			<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" title="${lfn:message('sys-category:menu.sysCategory.index') }" expand="false">
			  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"/>
			</list:cri-ref>
			<%-- 召开时间 --%>
			<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingMain" property="fdHoldDate" expand="false"/>
		</list:criteria>
		
		<%-- 操作栏 --%>
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDate') }" group="sort.list" value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=listChildren&isUploadAtt=true&categoryId=${JsParam.categoryId}&nodeType=${JsParam.nodeType}&q.except=isCloud:1'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=viewUpdateAtt&fdId=!{fdId}" name="columntable">
				<list:col-checkbox name="List_Selected"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview>
	 	<list:paging></list:paging>
		
	</template:replace>
</template:include>