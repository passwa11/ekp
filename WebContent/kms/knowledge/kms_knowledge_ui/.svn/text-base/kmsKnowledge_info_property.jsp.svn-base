<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>

<%
	IExtendDataForm dataform = (IExtendDataForm) request.getAttribute("kmsKnowledgeBaseDocForm");
	Boolean isExist = false;	
	if (StringUtil.isNotNull(dataform.getExtendDataFormInfo().getExtendFilePath())) {
		isExist = true;
	}
	request.setAttribute("isExist",isExist);
%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<script >
			Com_IncludeFile("validation.js|plugin.js|eventbus.js|xform.js", null, "js");
			seajs.use(['theme!form' ]);
		</script>
	</template:replace>
	<template:replace name="body">
		<div style="padding-top:20px">
			<html:form action="/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do"
			  		onsubmit="return true;">
				<html:hidden property="docSubject"  styleId="docSubject" value="${kmsKnowledgeBaseDoc.docSubject}"/> 
				<div style="float:left; margin: 0px 26px 5px;">
					<c:choose>
						<c:when test="${isExist}">
							<nobr>
								<strong>${lfn:message('kms-multidoc:kmsMultidoc.docProperty') }</strong>
								<span aling="left">${lfn:message('kms-knowledge:kmsKnowledge.editPropertyTip') }</span>
							</nobr>
						</c:when>
						<c:otherwise>
							<nobr>
								<strong>${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.editProperty.prompt')}：</strong>
								<span aling="left">${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.editPropertyTip2')}</span>
								<input type="hidden" name="hasProperty" value="${isExist}">
							</nobr>
						</c:otherwise>
					</c:choose>
				</div>
				<table class="tb_simple" width="100%" >
					<!-- 属性 -->
					<c:import url="/sys/property/include/sysProperty_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmsKnowledgeBaseDocForm" />
						<c:param name="fdDocTemplateId" value="${kmsKnowledgeBaseDocForm.docCategoryId}" />
					</c:import>
				</table> 
				<html:hidden property="idList"/>
			</html:form>
		</div>
	</template:replace>
</template:include>
<script>
$KMSSValidation();
</script>