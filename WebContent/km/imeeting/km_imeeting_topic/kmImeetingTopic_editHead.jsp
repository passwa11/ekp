<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="title">
	<c:choose>
		<c:when test="${ kmImeetingTopicForm.method_GET == 'add' }">
			<c:out value="${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>	
		</c:when>
		<c:otherwise>
			<c:out value="${kmImeetingTopicForm.docSubject} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
		</c:otherwise>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
  <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6"> 
  	<c:choose>
		<c:when test="${ kmImeetingTopicForm.method_GET == 'add' }">
			<ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('save','true');">
			</ui:button>
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save','false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
					</ui:button>
				</c:when>
				<c:otherwise>
					<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('save','false');">
					</ui:button>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${ kmImeetingTopicForm.method_GET == 'edit' }">
			<c:if test="${kmImeetingTopicForm.docStatusFirstDigit=='1'}">
				<ui:button text="${lfn:message('button.savedraft') }" order="2" onclick="commitMethod('update','true');">
				</ui:button>
			</c:if>
			<c:choose>
				<c:when test="${param.approveModel eq 'right'}">
					<c:if test="${kmImeetingTopicForm.docStatusFirstDigit<'3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:if>
					<c:if test="${kmImeetingTopicForm.docStatusFirstDigit>='3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.kmImeetingTopicForm, 'update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true">
						</ui:button>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${kmImeetingTopicForm.docStatusFirstDigit<'3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="commitMethod('update','false');">
						</ui:button>
					</c:if>
					<c:if test="${kmImeetingTopicForm.docStatusFirstDigit>='3'}">
						<ui:button text="${lfn:message('button.submit') }" order="2" onclick="Com_Submit(document.kmImeetingTopicForm, 'update');">
						</ui:button>
					</c:if>	
				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>
     <ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
	 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="path">
	<ui:combin ref="menu.path.simplecategory">
		<ui:varParams 
			moduleTitle="${ lfn:message('km-imeeting:module.km.imeeting') }"
			modulePath="/km/imeeting/" 
			modelName="com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory" 
			autoFetch="false"
			target="_blank"
			categoryId="${kmImeetingTopicForm.fdTopicCategoryId}" />
	</ui:combin>
</template:replace>	
