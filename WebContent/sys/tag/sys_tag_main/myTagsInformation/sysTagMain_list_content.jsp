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
	<list:criteria channel="main">
		<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-tag:sysTagMain.docTitle') }"></list:cri-ref>
		<list:cri-criterion title="${lfn:message('sys-tag:sysTagMain.modelName') }" key="fdModelName"> 
			<list:box-select>
				<list:item-select type="lui/criteria!CriterionSelectDatas">
					<ui:source type="AjaxJson">
						{url:'/sys/tag/sys_tag_main/sysTagMain.do?method=getAppModules'}
					</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
		<c:choose>  
		   <c:when test="${not empty JsParam.fdTagName}">
		   </c:when>  
		   <c:otherwise> 
			<list:cri-criterion title="${lfn:message('sys-tag:table.sysTagMain.fdTagName') }" channel="main" key="tagName"> 
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas">
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_main_relation/sysTagMainRelation.do?method=getTagNames'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:otherwise>  
		</c:choose>
	</list:criteria>
	
	<!-- 操作栏 -->
	<div class="lui_list_operation" channel="main">
		<!-- 排序 -->
		<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
		<div class="lui_list_operation_order_btn">
			<ui:toolbar layout="sys.ui.toolbar.sort" channel="tag">
				<list:sortgroup>
			   		<list:sort property="docCreateTime" text="${ lfn:message('sys-tag:sysTagMain.docCreateTime')}" group="sort.list" value="down" channel="main"></list:sort>
					<list:sort property="docAlterTime" text="${ lfn:message('sys-tag:sysTagMain.docAlterTime')}" channel="main"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
		<!-- mini分页 -->
		<div class="lui_list_operation_page_top">	
			<list:paging layout="sys.ui.paging.top" channel="main"></list:paging >
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
	</div>
	
	<!-- 内容列表 -->
	<list:listview id="main_overView" channel="main">
		<ui:source type="AjaxJson">
			{url:'/sys/tag/sys_tag_main/sysTagMain.do?method=listOverView&fdModelName=${JsParam.fdModelName}&fdTagName=${JsParam.fdTagName}&userId=${KMSS_Parameter_CurrentUserId}'}
		</ui:source>
			<%--列表形式--%>
		<list:colTable layout="sys.ui.listview.columntable" name="columntable"
				rowHref="'!{fdUrl}'">
				<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
				<list:col-auto props="docSubject;fdAppName;docCreateTime;docAlterTime;docCreator.fdName"></list:col-auto>
		</list:colTable>
	</list:listview>
	<!-- 分页 -->
 	<list:paging channel="main"/>
 	</div>
