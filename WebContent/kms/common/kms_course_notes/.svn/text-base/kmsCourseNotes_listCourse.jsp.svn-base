<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/kms/common/kms_course_notes/resource/js/notes_index_js.jsp"%>

<c:set var="modelName" value="${param.modelName}" />
<c:set var="modelId" value="${param.modelId}" />
<style>
	ul {
    display: block;
    margin: 0px !important;
    padding-left: 0px !important;
    /* list-style-type: disc; */
    }
    
    .lui_list_operation {
    background-color: #f5f5f5;
    border-top: 0;
    border-bottom: 1px solid #ebebeb;
    padding: 5px 10px;
    height: 40px;
    line-height: 40px;
}
 
</style>
	<ui:content title="${lfn:message('kms-common:kmsCommon.courseNotes') }"> 
		<div style="margin:4px auto 15px auto;">
		
		<%--模块筛选 --%>
		
			<list:criteria id="module_sort" expand="true"  multi="true" channel="notes_index">		
				
	
				<list:cri-criterion title="${lfn:message('kms-common:kmsCourseNotes.isType') }" key="listType" multi="false">
					<list:box-select>
						<list:item-select >
							<ui:source type="Static">
								[
								{text:"${lfn:message('kms-common:kmsCommon.myNotes') }", value: 'my_notes'},
								{text:"${lfn:message('kms-common:kmsCommon.shareNotes') }", value:'share_notes'}]
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
						<ui:toolbar layout="sys.ui.toolbar.sort" channel="notes_index">
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
<%-- <ui:fixed elem=".lui_list_operation"></ui:fixed> --%>
			<%--list视图--%>
			<list:listview id="listview" channel="notes_index">
				<ui:source type="AjaxJson">
					{url:'/kms/common/kms_notes/kmsCourseNotes.do?method=list&orderby=docCreateTime&forward=dataIndex&fdModelName=${modelName}&fdModelId=${modelId}&rowsize=5'}
				</ui:source>
				<%--列表形式--%>
				<list:colTable layout="sys.ui.listview.columntable" name="columntable" 
					rowHref="/kms/common/kms_notes/kmsCourseNotes.do?method=view&fdId=!{fdId}">
						<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
						<list:col-serial title="${ lfn:message('page.serial') }" headerStyle="width:5%"></list:col-serial>
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docSubject')}" headerStyle="width:40%" style="text-align:left;padding:0 8px">
							{$
							<span class="note_Content" title="{%row['fdNotesContent']%}">
									{%strutil.textEllipsis(row['fdNotesContent'], 80)%}</span>
							$}
						</list:col-html>
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.sourse')}" >
							{$
								<span class="note_Course" title="{%row['fdCourse']%}">{%row['fdCourse']%}</span> 
							$}
						</list:col-html> 
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.isType')}" >
							{$
								<span class="com_type" title="{%row['fdType']%}">{%row['fdType']%}</span> 
							$}
						</list:col-html> 
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docCreator')}" >
							{$
								<span class="com_type" title="{%row['docCreator.fdName']%}">{%row['docCreator.fdName']%}</span> 
							$}
						</list:col-html> 
						<list:col-html title="${ lfn:message('kms-common:kmsCourseNotes.docCreateTime')}" >
							{$
								<span class="com_type" title="{%row['docCreateTime']%}">{%row['docCreateTime']%}</span> 
							$}
						</list:col-html> 
						<list:col-auto props="docEvalCount;docPraiseCount"></list:col-auto>
					
				</list:colTable>
				<ui:event topic="list.loaded" args="vt">
								var text=$(".note_Content");
								for(var i=0;i<text.length;i++){
									var text1=$(text[i]).text().trim();
									var n=Math.floor(($(".note_Content").width()/3));
									if(text1.length>(n*7-1)){
										text1=text1.substring(0,n*7-1)+".."; 
									}
									$(text[i]).html(text1);
								}
						
				</ui:event>
			</list:listview> 
		<list:paging channel="notes_index"></list:paging>	
	 	</div>
</ui:content>
