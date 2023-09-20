<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<div id="exportDefinition" style="display:none;">
	<c:choose>
		<c:when test="${param.newUi == 'true'}">
			<ui:button text="${ lfn:message('sys-lbpmservice-support:lbpmTemplate.export.button') }" onclick="exportDefinition();"></ui:button>
		</c:when>
		<c:otherwise>
			<input
				type="button"
				value="<kmss:message key="sys-lbpmservice-support:lbpmTemplate.export.button" />"
				onclick="exportDefinition();">
		</c:otherwise>
	</c:choose>
</div>
<script>
function exportDefinition() {
	var selected=false;
	var select = document.getElementsByName("List_Selected");
	for(var i=0;i<select.length;i++) {
		if(select[i].checked){
			selected=true;
			var q = "List_Selected=" + select[i].value;
			window.open('<c:url value="/sys/lbpmservice/support/lbpm_template_exportandimport/lbpmTemplateExportAndImport.do">
					<c:param name="method" value="exportDefinition" />
					<c:param name="fdModelName" value="${param.fdModelName}" />
			</c:url>&'+q);
		}
	}
	if(selected) {
		return;
	}
	alert("<kmss:message key="dialog.requiredSelect" />");
	return false;
}
<c:if test="${param.newUi != 'true'}">
OptBar_AddOptBar("exportDefinition");
</c:if>
</script>