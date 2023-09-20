<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<template:replace name="head">
        <script type="text/javascript">
       		 var editOption = {
		        formName: 'kmArchivesAppraiseForm',
		        modelName: 'com.landray.kmss.km.archives.model.KmArchivesAppraise'
		    };
		    Com_IncludeFile("security.js");
		    Com_IncludeFile("domain.js");
		    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/km/archives/resource/js/", 'js', true);
        </script>
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:if test="${kmArchivesAppraiseForm.method_GET=='edit'}">
				<c:if test="${ kmArchivesAppraiseForm.docStatus=='10' || kmArchivesAppraiseForm.docStatus=='11' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" onclick="_updateDraft()" />
                </c:if>
                <c:if test="${kmArchivesAppraiseForm.docStatus=='10'||kmArchivesAppraiseForm.docStatus=='11'||kmArchivesAppraiseForm.docStatus=='20'}">
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
			<c:if test="${kmArchivesAppraiseForm.method_GET=='add'}">
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