<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>

<kmss:auth
	requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add"
	requestMethod="GET">
	<c:set var="hasAdd" value="true"></c:set>
</kmss:auth>

<kmss:auth
	requestURL="/kms/wiki/kms_wiki_main/kmsWikiMain.do?method=add"
	requestMethod="GET">
	<c:set var="hasAdd" value="true"></c:set>
</kmss:auth>

<kmss:auth
	requestURL="/kms/kem/kms_kem_main/kmsKemMain.do?method=add"
	requestMethod="GET">
	<c:set var="hasAdd" value="true"></c:set>
</kmss:auth>
<c:if test="${hasAdd}">
	<ui:button text="${lfn:message('button.add')}" order="2"
		onclick="window.moduleAPI.kmsKnowledge.addDoc()"></ui:button>
</c:if>

<%-- <ui:button text="${lfn:message('button.delete')}"
	cfg-if="auth(/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteall&categoryId=!{docCategory})||criteria('docStatus')[0]=='10'||param['docStatus']&&param['docStatus'].indexOf('10')>=0"
	order="5" onclick="window.moduleAPI.kmsKnowledge.deleteall()"></ui:button> --%>


<%-- <ui:button text="${lfn:message('kms-knowledge:button.changeProperty')}"
	cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=editPropertys&templateId=!{docCategory}"
	onclick="window.moduleAPI.kmsKnowledge.editProperty()"></ui:button> --%>

<%-- <ui:button onclick="window.moduleAPI.kmsKnowledge.setTop()"
	cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=setTop&local=index&categoryId=!{docCategory}"
	text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.setTop')}"></ui:button> --%>

<%-- 修改权限 --%>
<%-- <c:import url="/sys/right/import/doc_right_change_button.jsp"
	charEncoding="UTF-8">
	<c:param name="modelName"
		value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
	<c:param name="spa" value="true" />
</c:import> --%>

<%-- 取消推荐 --%>
<%-- <c:import url="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_cancel_introduce.jsp"
	charEncoding="UTF-8">
	<c:param name="fdModelName"
		value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
</c:import> --%>

<%-- <ui:button
	cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=exportExcel&categoryId=!{docCategory}"
	text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.export.btn') }"
	onclick="window.moduleAPI.kmsKnowledge.exportToExcel()"></ui:button> --%>


<form name="exportData" action="" method="post">
	<input type="hidden" name="List_Selected" value="">
</form>

<kmss:ifModuleExist path="/kms/multidoc">
	<kmss:auth
		requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add"
		requestMethod="GET">
		<c:import
			url="/kms/category/kms_category_main_ui/kms_multiple_upload.jsp"
			charEncoding="UTF-8">
			<c:param name="fdCategoryModelName"
				value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
			<c:param name="fdMainModelName"
				value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
			<c:param name="categoryIndicateName" value="fdTemplateId" />
			<c:param name="fdKey" value="attachment" />
			<c:param name="pathTitle" value="kms-multidoc:title.kms.multidoc" />
		</c:import>
	</kmss:auth>
</kmss:ifModuleExist>
<%--维基导入 --%>
<kmss:ifModuleExist path="/kms/wiki/">
	<kmss:auth
		requestURL="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=add"
		requestMethod="GET">
		<ui:button
			text="${lfn:message('kms-wiki:kmsWikiMain.button.wikiImport')}"
			order="2" onclick="window.moduleAPI.kmsKnowledge.wikiImport()" cfg-if="criteria('template')[0]==2"></ui:button>
	</kmss:auth>
</kmss:ifModuleExist>

<script>
	//搜索本模块变量
	var SYS_SEARCH_MODEL_NAME = "com.landray.kmss.kms.wiki.model.KmsWikiMain;com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge";
</script>

