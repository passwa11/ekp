<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="person.home" spa="true">

	<template:replace name="title">
		${lfn:message('kms-common:table.kmsCourseNotes') }
	</template:replace>
	<template:replace name="head">
		<style>
			.com_content{
				display: block;
				overflow: hidden;
			    white-space: nowrap;
			    text-overflow: ellipsis;
			    width: 500px;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<c:set var="navTitle" value="${ lfn:message('kms-common:module.kms.allNotes') }"></c:set>
		<c:if test="${not empty param.navTitle}">
			<c:set var="navTitle" value="${param.navTitle}"></c:set>
		</c:if>
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${navTitle}">
				<%--模块筛选 --%>
				<list:criteria id="module_sort" expand="false"  multi="true">		
					<list:cri-criterion title="${lfn:message('kms-common:kmsCourseNotes.isType') }" key="isShare" multi="false">
						<list:box-select>
							<list:item-select >
								<ui:source type="Static">
									[
									{text:"${lfn:message('kms-common:kmsCommon.share_Notes') }", value: 'true'},
									{text:"${lfn:message('kms-common:kmsCommon.private_Notes') }", value:'false'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-auto modelName="com.landray.kmss.kms.common.model.KmsCourseNotes" property="docCreateTime"/>
				</list:criteria>
				
				
				<%-- 按钮 --%>
				<div class="lui_list_operation">
					<div class="lui_list_operation_order_text"> ${ lfn:message('kms-common:kmsCourseNotes.list.orderType') }：</div>
					<%--排序按钮  --%>
					<div class="lui_list_operation_order_btn">
						<ui:toolbar layout="sys.ui.toolbar.sort" >
							<list:sortgroup>
								<list:sort property="kmsCourseNotes.docCreateTime" text="${lfn:message('kms-common:kmsCourseNotes.docCreateTime') }" group="sort.list" value="down"></list:sort>
								<list:sort property="kmsCourseNotes.docEvalCount" 
									   text="${lfn:message('kms-common:kmsCourseNotes.evalCount') }" 
									   group="sort.list" />	
								<list:sort property="kmsCourseNotes.docPraiseCount" 
									   text="${lfn:message('kms-common:kmsCourseNotes.praiseCount') }" 
									   group="sort.list" />	
							</list:sortgroup>
						</ui:toolbar>
					</div>
					<div class="lui_list_operation_order_btn">
						<list:selectall></list:selectall>
					</div>
					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>
					
					<%--操作按钮  --%>
					<div class="lui_list_operation_toolbar">
						<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=delete" requestMethod="GET">
							<ui:toolbar count="2" id="knowledge_toolbar">
								<ui:button text="${lfn:message('button.delete')}" 
										   onclick="delDoc()" id="deleteall" order="5">
								</ui:button>
							</ui:toolbar>
						</kmss:auth>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
					<%--list视图--%>
					<list:listview id="listview">
						<ui:source type="AjaxJson">
							{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=dataIndex&orderby=docCreateTime'}
						</ui:source>
						<%--列表形式--%>
						<list:colTable layout="sys.ui.listview.columntable" name="columntable" 
							rowHref="/kms/common/kms_notes/kmsCourseNotes.do?method=view&fdId=!{fdId}">
								<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
								<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
								<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docSubject')}" headerStyle="width:40%" style="text-align:left;padding:0 8px">
									{$
										<span class="com_content">{%row['fdNotesContent']%}</span>
									$}
								</list:col-html>
								<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.sourse')}" >
									{$
										<span class="com_type">{%row['fdCourse']%}</span> 
									$}
								</list:col-html> 
								<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.isType')}" >
									{$
										<span class="com_type">{%row['fdType']%}</span> 
									$}
								</list:col-html> 
								<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docCreateTime')}" >
									{$
										<span class="com_type">{%row['docCreateTime']%}</span> 
									$}
								</list:col-html> 
								<list:col-auto props="docEvalCount;docPraiseCount"></list:col-auto>
						</list:colTable>
					</list:listview> 
				<list:paging></list:paging>	
			 	<portal:footer var-width="100%" />
		 		<%@ include file="/kms/common/kms_course_notes/resource/js/notes_index_js.jsp"%>
			</ui:content>
		</ui:tabpanel>
		
	</template:replace>
</template:include>

