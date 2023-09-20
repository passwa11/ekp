<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<script type="text/javascript"
			src="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/att_auth/resource/js/doc_list.js?s_cache=${ LUI_Cache }"></script>

		<!-- 筛选器  -->
		<list:criteria id="criteria">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"
				title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects') }">
			</list:cri-ref>
		</list:criteria>

        <%-- 多选选中组件 --%>
        <div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
            <%--已经选中的值 --%>
            <script type="text/config">
                {
                    values:[{'id':"${JsParam.curId}",name:'${JsParam.docSubject}'}]
                }
            </script>
        </div>

		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">

				<ui:toolbar layout="sys.ui.toolbar.sort">
					<list:sortgroup>
						<list:sort property="kmsKnowledgeBaseDoc.docCreateTime"
							text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCreateTime') }"
							group="sort.list" />
					</list:sortgroup>
				</ui:toolbar>
			</div>

			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>

		</div>

		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=selectDataInBorrow&q.docStatus=30&rowsize=8&orderby=kmsKnowledgeBaseDoc.docCreateTime&ordertype=down'}
            </ui:source>

			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				onRowClick="selectItem('!{fdId}','!{docSubject}')">
				<list:col-serial title="${ lfn:message('page.serial') }"
					headerStyle="width:5%"></list:col-serial>
				<list:col-html title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}" style="width:40%" >
					{$
						{%row['docSubject']%}
					$}
		  	 	</list:col-html>
				<list:col-html title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus')}" style="width:5%" >
					{$
						{%row['docBorrowFlagName']%}
					$}
				</list:col-html>
				<list:col-html title="下载权限" style="width:10%" >
					if(row['authAttDownload'] == '1'){
						{$ 是 $}
					}else{
						{$ 否 $}
					}
				</list:col-html>
				<list:col-html title="拷贝权限" style="width:10%" >
					if(row['authAttCopy'] == '1'){
						{$ 是 $}
					}else{
						{$ 否 $}
					}
				</list:col-html>
				<list:col-html title="打印权限" style="width:10%" >
					if(row['authAttPrint'] == '1'){
						{$ 是 $}
					}else{
						{$ 否 $}
					}
				</list:col-html>
			  	<list:col-auto props="docCreateTime;" />
			</list:colTable>
		</list:listview>
		<list:paging></list:paging>
	</template:replace>
</template:include>