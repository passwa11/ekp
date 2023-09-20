<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="auditNoteSignView"
	data-dojo-type='sys/lbpmservice/mobile/audit_note_ext/sign/AuditNoteSign' style="display:none"
	data-dojo-props='fdKey:"${param.auditNoteFdId}_sg",
				fdAttType:"pic",
				fdModelId:"${param.modelId}",
				fdModelName:"${param.modelName}",
				lbpmViewName:"${param.lbpmViewName}",
				showType:"${param.showType }",
				backTo:"${param.backTo }",
				buttonDiv:"#commonUsagesDiv",
				descriptionDiv: "#descriptionDiv"'>
	<c:import url="/sys/lbpmservice/mobile/audit_note_ext/canvas.jsp" charEncoding="UTF-8"> 
	</c:import>
</div>
