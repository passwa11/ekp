<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<template:include ref="expand.list" rwd="true" spa="true" >
	<template:replace name="title">${lfn:message('kms-common:module.kms.allNotes') }</template:replace>
	<%-- 当前路径 --%>
    <template:replace name="path">
    
    	<c:import url="/kms/lservice/index/student/path.jsp">
    		<c:param name="modelName" value="com.landray.kmss.kms.common.model.KmsCourseNotes" />
    		<c:param name="title" value="${lfn:message('kms-lservice:lservice.role.student')}" />
    		<c:param name="moduleTitle" value="${lfn:message('kms-common:kmsCommon.courseNotes') }"></c:param>
    	</c:import>
    	
	</template:replace>
	<%-- 左边栏 --%>
	<template:replace name="nav">
		<c:import url="/kms/lservice/index/student/nav.jsp"
			charEncoding="UTF-8">
			<c:param name="modelName"
				value="com.landray.kmss.kms.common.model.KmsCourseNotes" />	
		</c:import>
	</template:replace>
	
	<template:replace name="banner">
		<c:import url="/kms/lservice/index/common/banner.jsp">
			<c:param name="modelName"
				value="com.landray.kmss.kms.common.model.KmsCourseNotes" />	
			<c:param name="type" value="student"/>
		</c:import>
	</template:replace>
	<%-- 右边栏 --%>
	<template:replace name="content">
	  	<list:criteria  expand="false"  multi="true" channel="notes_index">		
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
				
				<div class="lui_list_operation_order_btn">
					<list:selectall channel="notes_index"></list:selectall>
				</div>
				
				<div class="lui_list_operation_line"/>
				
				<div class="lui_list_operation_sort_toolbar">
					<div class="lui_list_operation_order_text" >${ lfn:message('list.orderType') }：</div>
					
					<div class="lui_list_operation_order_btn" >
						<ui:toolbar layout="sys.ui.toolbar.sort" channel="notes_index">
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
				</div>
				
				
				<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top" channel="notes_index">		
						</list:paging>
				</div>
						
				<div class="lui_list_operation_toolbar">
					<kmss:auth requestURL="/kms/common/kms_notes/kmsCourseNotes.do?method=delete" requestMethod="GET">
						<ui:toolbar count="4" id="knowledge_toolbar">
							<%-- 删除--%>
							<ui:button text="${lfn:message('button.delete')}" 
							   onclick="delDoc()" 
							   id="deleteall"
							   order="5">
							 </ui:button>
						</ui:toolbar>
					</kmss:auth>
				</div>
			</div>
			<%--list视图--%>
			<list:listview channel="notes_index">
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
								<div class="com_content"title="{%row['fdNotesContent']%}">{%row['fdNotesContent']%}</span>
							$}
						</list:col-html>
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.sourse')}" >
							{$
								<span class="com_type" title="{%row['fdCourse']%}">{%row['fdCourse']%}</span> 
							$}
						</list:col-html> 
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.isType')}" >
							{$
								<span class="com_type" title="{%row['fdType']%}">{%row['fdType']%}</span> 
							$}
						</list:col-html> 
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docCreateTime')}" >
							{$
								<span class="com_type" title="{%row['docCreateTime']%}">{%row['docCreateTime']%}</span> 
							$}
						</list:col-html> 
						<list:col-auto props="docEvalCount;docPraiseCount"></list:col-auto>
					
				</list:colTable>
			</list:listview> 
		<list:paging channel="notes_index"></list:paging>	
		<%@ include file="/kms/common/kms_course_notes/resource/js/notes_index_js.jsp"%>
	</template:replace>
</template:include>