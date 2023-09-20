<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/supervise/resource/css/supervise.css?s_cache=${LUI_Cache}"/>
        <script type="text/javascript">
            Com_IncludeFile("calendar.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("doclist.js");
            Com_IncludeFile("jquery.js");
            
            $KMSSValidation();
        </script>
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:if test="${kmArchivesDestroyForm.method_GET=='edit'}">
				<c:if test="${ kmArchivesDestroyForm.docStatus=='10' || kmArchivesDestroyForm.docStatus=='11' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" onclick="_updateDraft()" />
                </c:if>
				<c:if test="${kmArchivesDestroyForm.docStatus=='10'||kmArchivesDestroyForm.docStatus=='11'||kmArchivesDestroyForm.docStatus=='20'}">
					<c:choose>
						<c:when test="${param.approveModel eq 'right'}">
							<ui:button text="${ lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true" 
									onclick="commitMethod('update','false');">
							</ui:button>
						</c:when>
						<c:otherwise>
							<ui:button text="${ lfn:message('button.submit') }" order="2"  
									onclick="commitMethod('update','false');">
							</ui:button>
						</c:otherwise>
					</c:choose>
				</c:if> 
			</c:if>
			
			
			<c:if test="${kmArchivesDestroyForm.method_GET=='add'}">
				<ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="_saveDraft();" />
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<ui:button text="${ lfn:message('button.submit') }" order="2" styleClass="lui_widget_btn_primary" isForcedAddClass="true"
								onclick="commitMethod('save','false');">
						</ui:button>
					</c:when>
					<c:otherwise>
						<ui:button text="${ lfn:message('button.submit') }" order="2"  
								onclick="commitMethod('save','false');">
						</ui:button>
					</c:otherwise>
				</c:choose>
			</c:if>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>