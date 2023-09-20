<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<c:out value="${lfn:message('kms-wiki:kmsKnowledgeBaseDoc.fdAllTop')}"></c:out>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<c:if test="${type eq '2' }">
			<kmss:auth
			requestURL="/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=cancelTop&local=view&fdId=${param.fdId}"
			requestMethod="GET">
				<ui:button text="${lfn:message('kms-wiki:kmsWiki.cancelSetTop')}" onclick="cancelWikiTop();"></ui:button>
			</kmss:auth>
			</c:if>
			<c:if test="${type eq '1' }">
			<kmss:auth
			requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=cancelTop&local=view&fdId=${param.fdId}"
			requestMethod="GET">
				<ui:button text="${lfn:message('kms-wiki:kmsWiki.cancelSetTop')}" onclick="cancelMultidocTop();"></ui:button>
			</kmss:auth>
			</c:if>
			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubject') }
				</td><td width="35%" colspan="3">
					<xform:text property="docSubject"  style="width:85%" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.topInfoURL') }
				</td><td width="35%" colspan="3">
					<a href="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId=${docId }&fdKnowledgeType=${type}"  target="_blank" class="com_btn_link">${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.turnToDoc') }</a>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.fdSetTopReason') }
				</td><td width="35%" colspan="3" style="word-break:break-all">
					<xform:text property="fdSetTopReason"   style="width:85%;" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.docCreator"/>
				</td><td width="35%">
					<xform:text property="docAuthorName"  style="width:85%" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="kms-knowledge" key="kmsKnowledgeDocTemplate.docCreateTime"/>
				</td><td width="35%">
					<xform:datetime property="docPublishTime" />
				</td>
			</tr>
		</table>
		<script>
function cancelWikiTop(){
	seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery', 'lui/util/env'],
			function(dialog, topic, $, env) {
				dialog.confirm("${lfn:message('kms-wiki:kmsWiki.cancelTop')}", function(flag, d) {
					if (flag) {
						var loading = dialog.loading();
						$.post(env.fn.formatUrl('/kms/wiki/kms_wiki_main_index/kmsWikiMianIndex.do?method=cancelTop&templateId=${docCategoryId}&docIds=${docId}'),
								null, function(data, textStatus,
												xhr) {
											if (data["hasRight"]== true) {
												loading.hide();
												dialog.success("${lfn:message('kms-wiki:kmsWiki.executeSucc')}")
												setTimeout(function(){
													Com_CloseWindow();
												}, 1200);
											} else {
												loading.hide();
						                		dialog.alert("${lfn:message('kms-wiki:kmsWiki.noRight')}");
											}
										}, 'json');
					}
				});
			}
	);
}
function cancelMultidocTop(){
	seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery', 'lui/util/env'],
			function(dialog, topic, $, env) {
				dialog.confirm("${lfn:message('kms-multidoc:kmsMultidoc.cancelTop')}", function(flag, d) {
					if (flag) {
						var loading = dialog.loading();
						$.post(env.fn.formatUrl('/kms/multidoc/kms_multidoc_index/kmsMultidocKnowledgeIndex.do?method=cancelTop&templateId=${docCategoryId}&docIds=${docId}'),
								null, function(data, textStatus,
												xhr) {
											if (data["hasRight"]== true) {
												loading.hide();
												dialog.success("${lfn:message('kms-wiki:kmsWiki.executeSucc')}")
												setTimeout(function(){
													Com_CloseWindow();
												}, 1200);
											} else {
												loading.hide();
						                		dialog.alert("${lfn:message('kms-multidoc:kmsMultidoc.noRight')}");
											}
										}, 'json');
					}
				});
			}
	);
}
</script>
	</template:replace>
</template:include>