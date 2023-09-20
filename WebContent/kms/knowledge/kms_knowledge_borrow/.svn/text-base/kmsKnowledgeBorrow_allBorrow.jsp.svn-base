<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<ui:tabpanel id="tabpanel" layout="sys.ui.tabpanel.list" >
		<ui:content id="common"  title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.index.allBorrow')}"  cfg-route="{path:'/enroll'}">
			<c:import url="/kms/knowledge/kms_knowledge_borrow/import/kmsKnowledgeBorrow_fileBorrow_uiContent.jsp" charEncoding="UTF-8">
				<c:param name="channel" value="myFileBorrow"></c:param>
			</c:import>
		</ui:content>
		<ui:content id="recall"  title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrowAttAuth.index.allBorrow')}"  cfg-route="{path:'/all'}">
			<c:import url="/kms/knowledge/kms_knowledge_borrow/import/kmsKnowledgeBorrow_attAuth_uiContent.jsp" charEncoding="UTF-8">
				<c:param name="channel" value="myAttApplication"></c:param>
			</c:import>
		</ui:content>
		</ui:tabpanel>

        <script type="text/javascript" src="${ LUI_ContextPath}/kms/knowledge/kms_knowledge_borrow/resource/js/list.js?s_cache=${ LUI_Cache }"></script>
    </template:replace>
</template:include>