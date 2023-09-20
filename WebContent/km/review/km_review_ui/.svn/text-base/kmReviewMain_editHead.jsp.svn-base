<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmReviewMainForm.method_GET == 'add' }">
			<c:out value="${lfn:message('km-review:kmReviewMain.opt.create') } - ${ lfn:message('km-review:module.km.review') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmReviewMainForm.docSubject} - ${ lfn:message('km-review:table.kmReviewMain') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
<c:if test="${kmReviewMainForm.docDeleteFlag ==1}">
	<ui:toolbar id="toolbar" style="display:none;" count="7"></ui:toolbar>
</c:if>
<c:if test="${kmReviewMainForm.docDeleteFlag !=1}">
	<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">  
		<c:if test="${kmReviewMainForm.method_GET=='edit' && kmReviewMainForm.docStatus=='10'}">
			<ui:button text="${ lfn:message('button.savedraft') }" order="2" 
					onclick="_updateDoc();">
			</ui:button>
		</c:if>
		<c:if test="${kmReviewMainForm.method_GET=='edit' && kmReviewMainForm.docStatus=='11'}">
			<ui:button text="${ lfn:message('button.savedraft') }" order="2" 
					onclick="_updateDraft();">
			</ui:button>
		</c:if>
		<c:if test="${kmReviewMainForm.method_GET=='edit'&&(kmReviewMainForm.docStatus=='10'
					||kmReviewMainForm.docStatus=='11'||kmReviewMainForm.docStatus=='20')}">
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<ui:button text="${ lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true" 
							onclick="_publishDraft();">
					</ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${ lfn:message('button.submit') }" order="2"  
							onclick="_publishDraft();">
					</ui:button>
				</c:otherwise>
			</c:choose>
		</c:if> 
		<c:if test="${kmReviewMainForm.method_GET=='add'}">
			<ui:button text="${ lfn:message('button.savedraft') }" order="2"  
					onclick="_saveDoc();">
			</ui:button>
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<ui:button text="${ lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
							onclick="_submitDoc();">
					</ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${ lfn:message('button.submit') }" order="2"  
							onclick="_submitDoc();">
					</ui:button>
				</c:otherwise>
			</c:choose>
		</c:if>
		<!-- 表单数据导入 -->
		<c:if test="${kmReviewMainForm.docStatus=='10' && kmReviewMainForm.fdIsImportXFormData == 'true'}">
			<ui:button id="importXFormData" text="${ lfn:message('km-review:kmReviewMain.importFormDataByExcel') }" order="2"  
					style="display:none;" onclick="importXFormDataByExcel();">
			</ui:button>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		</ui:button>
	</ui:toolbar>  
</c:if>
</template:replace>
<template:replace name="path">
	<ui:combin ref="menu.path.category">
		<ui:varParams 
		    moduleTitle="${ lfn:message('km-review:table.kmReviewMain') }" 
		    modulePath="/km/review/" 
			modelName="com.landray.kmss.km.review.model.KmReviewTemplate" 
			autoFetch="false"	
			target="_blank"
			categoryId="${kmReviewMainForm.fdTemplateId}" />
	</ui:combin>
</template:replace>	