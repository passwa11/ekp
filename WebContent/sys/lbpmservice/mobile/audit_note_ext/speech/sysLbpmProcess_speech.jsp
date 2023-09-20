<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div 
	data-dojo-type='sys/lbpmservice/mobile/audit_note_ext/speech/AuditNoteSpeech' style="display:none"
	data-dojo-props='fdKey:"${param.auditNoteFdId}_sp",
				fdModelId:"${param.modelId}",
				fdModelName:"${param.modelName}",
				buttonDiv:"#commonUsagesDiv",
				descriptionDiv: "#descriptionDiv"'>
</div>
