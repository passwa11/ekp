<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>

<c:set var="docAdd" value="false"></c:set>

<div data-lui-type="lui/toolbar!AbstractButton">
	<script type="text/config">
	{
		if: "criteria('type')[0]!='myBookmark'&&criteria('type')[0]!='myEval'"
	}
	</script>
<ui:toolbar count="0" cfg-if="" cfg-moreText="${lfn:message('button.add') }" >
	<kmss:auth
			requestURL="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=addTest"
			requestMethod="GET">
		<c:set var="docAdd" value="true"></c:set>
	</kmss:auth>
	<ui:button
			cfg-disabled="${ docAdd == 'true'? '' : true }"
			title="${ docAdd == 'true'? '' : \"${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.disable.pmt1')}\" }"
			text="${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.add.muti')}" order="2"
			onclick="window.moduleAPI.kmsKnowledge.addDoc('1')">
	</ui:button>
	<ui:button
			cfg-disabled="${ docAdd == 'true'? '' : true }"
			title="${ docAdd == 'true'? '' : \"${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.disable.pmt2')}\" }"
			text="${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.add.wiki')}"
			order="2"
			onclick="window.moduleAPI.kmsKnowledge.addDoc('2')">
	</ui:button>

	<c:if test="${ kms_professional }">
		<%-- 维基导入 --%>
		<kmss:ifModuleExist path="/kms/wiki/">
			<ui:button
				cfg-disabled="${ docAdd == 'true'? '' : true }"
				title="${ docAdd == 'true'? '' : \"${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.disable.pmt3')}\" }"
				text="${lfn:message('kms-wiki:kmsWikiMain.button.wikiImport')}"
				order="2"
				onclick="window.moduleAPI.kmsKnowledge.wikiImport()">
			</ui:button>
		</kmss:ifModuleExist>
	</c:if>
</ui:toolbar>
</div>
<ui:toolbar id="kmsKnowledgeMoreBtn" count="1" cfg-moreText="${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.more.pmt')}" >

	<c:if test="${kms_professional}">
		<ui:button text="${lfn:message('kms-knowledge:kms.knowledge.borrow')}" order="1"
				   style='<%=KmsKnowledgeBorrowUtil.checkBorrowOpen(request)?"":"display:none;"%>'
				   onclick="window.moduleAPI.kmsKnowledge.docBorrow()"></ui:button>
	</c:if>
	<ui:button text="${lfn:message('button.delete')}"
			   cfg-if="(auth(/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=deleteall&categoryId=!{docCategory})||criteria('myDraft')[0]=='myDraft'||(param['myDraft']&&param['myDraft'].indexOf('myDraft')>=0))&&criteria('type')[0]!='myBookmark'&&criteria('type')[0]!='myEval'"
			   order="5" onclick="window.moduleAPI.kmsKnowledge.deleteall()"></ui:button>

	<ui:button text="${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.cancel.bookmark') }"
			   cfg-if="criteria('type')[0]=='myBookmark'"
			   order="2" onclick="window.moduleAPI.kmsKnowledge.deleteBookMarks()"></ui:button>

	<c:if test="${kms_professional}">
		<ui:button text="${lfn:message('kms-knowledge:button.changeProperty')}"
				   cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=editPropertys&templateId=!{docCategory}"
				   onclick="window.moduleAPI.kmsKnowledge.editProperty()"></ui:button>
	</c:if>

	<ui:button onclick="window.moduleAPI.kmsKnowledge.setTop()"
			   cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=setTop&local=index&categoryId=!{docCategory}"
			   text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.setTop')}"></ui:button>


	<%-- 修改权限 --%>
	<c:import url="/sys/right/import/doc_right_change_button.jsp"
			  charEncoding="UTF-8">
		<c:param name="modelName"
				 value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
		<c:param name="spa" value="true" />
	</c:import>

	<%-- 取消推荐 --%>
	<c:import url="/kms/knowledge/kms_knowledge_ui/kmsKnowledge_cancel_introduce.jsp"
			  charEncoding="UTF-8">
		<c:param name="fdModelName"
				 value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc" />
	</c:import>

	<c:if test="${kms_professional}">
		<ui:button
				cfg-auth="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=exportExcel&categoryId=!{docCategory}"
				text="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.export.btn') }"
				onclick="window.moduleAPI.kmsKnowledge.exportToExcel()"></ui:button>



		<form name="exportData" action="" method="post">
			<input type="hidden" name="List_Selected" value="">
		</form>
		<kmss:ifModuleExist path="/kms/multidoc">
			<kmss:auth
					requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add"
					requestMethod="GET">
				<c:import
						url="/kms/knowledge/kms_knowledge_multiple_upload/kms_multiple_upload.jsp"
						charEncoding="UTF-8">
					<c:param name="fdCategoryModelName"
							 value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeCategory" />
					<c:param name="fdMainModelName"
							 value="com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" />
					<c:param name="cfgIf"
							 value="criteria('type')[0]!='myBookmark'&&criteria('type')[0]!='myEval'" />
					<c:param name="categoryIndicateName" value="fdTemplateId" />
					<c:param name="fdKey" value="attachment" />
					<c:param name="pathTitle" value="kms-multidoc:title.kms.multidoc" />
				</c:import>
			</kmss:auth>
		</kmss:ifModuleExist>
	</c:if>

</ui:toolbar>

<script>
	//搜索本模块变量
	var SYS_SEARCH_MODEL_NAME = "com.landray.kmss.kms.wiki.model.KmsWikiMain;com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge";
</script>

