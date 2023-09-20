<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<list:criteria id="criteria" expand="true">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject"
				title="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.fdName') }">
			</list:cri-ref>
			<list:cri-auto
				modelName="com.landray.kmss.kms.knowledge.batch.model.KmsKnowledgeBatchLog"
				property="fdStatus;docCreateTime" />
		</list:criteria>
		<div class="lui_list_operation">
			<div style="float: left">
				<div style="display: inline-block; vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort">
						<list:sort property="docCreateTime"
							text="${lfn:message('kms-knowledge-batch:kmsKnowledgeBatchLog.docCreateTime') }"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<div style="float: left;">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>

		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
                    {url:'/kms/knowledge/kms_knowledge_batch/kmsKnowledgeBatchLog.do?method=data&orderby=docCreateTime&ordertype=down'}
                </ui:source>
			<!-- 列表视图 -->
			<list:colTable isDefault="false" name="columntable">
				<list:col-serial />
				<list:col-auto
					props="fdName;fdUrl;fdModelName;fdCategoryId;docCreator.fdName;docCreateTime;fdStatus,fdRemarks" />
			</list:colTable>
		</list:listview>
		<!-- 翻页 -->
		<list:paging></list:paging>
	</template:replace>
</template:include>

