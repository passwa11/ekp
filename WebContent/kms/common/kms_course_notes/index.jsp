<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.list" >

	<template:replace name="head">
	<%@ include file="/kms/common/kms_course_notes/resource/js/notes_index_js.jsp"%>
			<style>
			.lui_list_left_sidebar_td{
				display:none;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		${lfn:message('kms-common:table.kmsCourseNotes') }
	</template:replace>
	<template:replace name="content">
		<portal:header var-width="100%" />
		<%--模块筛选 --%>
		<div style="width:90%; width:980px; margin:4px auto 15px auto;">
			<list:criteria id="module_sort" expand="true"  multi="true" channel="notes_index">		
				
	
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
					<div style='color: #979797;float: left;padding-top:1px;'> 
					
						${ lfn:message('kms-common:kmsCourseNotes.list.orderType') }：
					</div>
					<%--排序按钮  --%>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar layout="sys.ui.toolbar.sort" >
									<list:sort property="kmsCourseNotes.docCreateTime" text="${lfn:message('kms-common:kmsCourseNotes.docCreateTime') }" group="sort.list" value="down"></list:sort>
									<list:sort property="kmsCourseNotes.docEvalCount" 
										   text="${lfn:message('kms-common:kmsCourseNotes.evalCount') }" 
										   group="sort.list" />	
									<list:sort property="kmsCourseNotes.docPraiseCount" 
										   text="${lfn:message('kms-common:kmsCourseNotes.praiseCount') }" 
										   group="sort.list" />	
						</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">
						<list:paging layout="sys.ui.paging.top">		
						</list:paging>
					</div>
					<%--操作按钮  --%>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
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
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<%--list视图--%>
			<list:listview id="listview" channel="notes_index">
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
		<list:paging channel="notes_index"></list:paging>	
	 	</div>
	 	<portal:footer var-width="100%" />
	</template:replace>
</template:include>

