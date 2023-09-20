<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgRelation"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>



<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-relation:title.sysRelationMain.overView') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1" channel="main">
			<list:cri-ref key="relateDocSubject" ref="criterion.sys.docSubject" title="${ lfn:message('sys-relation:sysRelationMain.helptips11') }"></list:cri-ref>
			<list:cri-criterion title="${lfn:message('sys-relation:sysRelationEntry.fdSearchScope')}" key="searchRange">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url:'/sys/relation/sys_relation_main/sysRelationOverView.do?method=getAppModules'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
				<list:cri-criterion title="${lfn:message('sys-relation:relationOverView.search.object')}" key="fdSearchTarget">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url:'/sys/relation/sys_relation_main/sysRelationOverView.do?method=getSearchTarget'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
				
		</list:criteria>
		
		
		<!-- 操作栏 -->
		<!-- <div class="lui_list_operation"> -->
			<!-- 全选 -->
			<%-- <div class="lui_list_operation_order_btn">
				<list:selectall channel="main"></list:selectall>
			</div> --%>
			<!-- 分页 -->
			<%-- <div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" channel="main"> 		
				</list:paging >
			</div> --%>
		<!-- </div> -->
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<div>
				<list:listview id="main_overView" channel="main" >
				<ui:source type="AjaxJson">
					{url:'/sys/relation/sys_relation_main/sysRelationOverView.do?method=listOverView&rowsize=8'}
				</ui:source>
				<%--列表形式--%>
				<list:colTable layout="sys.ui.listview.columntable" name="columntable"   style="text-align: center;"
						rowHref="">
					<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%" style="text-align: center;"></list:col-serial>
					<list:col-html title="${lfn:message('sys-relation:relationOverView.source.doc2')}" style="text-align: center;width:40%;padding:0 8px">
					{$
						{%row['fdSourceDocSubject']%}
					$}
					</list:col-html>
					<list:col-html title="${lfn:message('sys-relation:relationOverView.related.doc')}" style="text-align: center;width:40%;padding:0 8px">
					{$
						{%row['fdRelatedDocSubject']%}
					$}
					</list:col-html>
					<list:col-auto props="fdRelatedType"></list:col-auto>
				</list:colTable>
				<ui:event topic="list.loaded" args="vt"> 
				</ui:event>
			</list:listview> 
		 	<list:paging channel="main"></list:paging>
		</div>
		<br>
		
	 	
	</template:replace>
</template:include>
