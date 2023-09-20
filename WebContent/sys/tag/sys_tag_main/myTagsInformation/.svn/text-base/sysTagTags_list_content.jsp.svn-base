<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.tag.service.ISysTagMainService"%>

<div style="width:100%;margin:10px auto;">
	<!-- 筛选器 -->
	<list:criteria channel="tag">
		<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('sys-tag:sysTagMain.fdTagName')}"></list:cri-ref>
	</list:criteria>
	
	<!-- 操作栏 -->
	<div class="lui_list_operation">
		<!-- 排序 -->
		<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
		<div class="lui_list_operation_order_btn">
			<ui:toolbar layout="sys.ui.toolbar.sort" channel="tag">
				<list:sortgroup>
			   		<list:sort property="docCreateTime" text="${ lfn:message('sys-tag:sysTagMain.docCreateTime')}" group="sort.list" value="down" channel="tag"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
		<!-- mini分页 -->
		<div class="lui_list_operation_page_top">
			<list:paging layout="sys.ui.paging.top" channel="tag"></list:paging>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
	</div>
	<!-- 内容列表 -->
	<list:listview channel="tag">
		<ui:source type="AjaxJson">
			{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=listOverView&userId=${KMSS_Parameter_CurrentUserId}'}
		</ui:source>
			<%--列表形式--%>
		<list:colTable layout="sys.ui.listview.columntable" name="columntable">
				<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
				<list:col-auto props="fdIsSpecial;fdName;fdStatus;fdIsPrivate;fdQuoteTimes;docCreateTime;docCreator.fdName"></list:col-auto>
		</list:colTable>
	</list:listview>
	<!-- 分页 -->
 	<list:paging channel="tag"/>
</div>
