<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/lbpmservice/mobile/audit_note_ext/canvas.css?s_cache=${MUI_Cache}"></link>
	<div id="auditNoteHandlerView_${param.fdId}" data-dojo-type="sys/xform/mobile/controls/newAuditNote/NewAuditNote" style="display:none"
		data-dojo-props='fdKey:"${param.auditNoteFdId}_hw",
				fdAttType:"pic",
				fdModelId:"${param.modelId}",
				fdModelName:"${param.modelName}",
				lbpmViewName:"${param.lbpmViewName}",
				fdId:"${param.fdId}",
				mould:"${param.mould}",
				value:"${param.value}",
				resValue:"${param.resValue}",
				buttonDiv:"",
				descriptionDiv:"" '>
		<c:import url="/sys/lbpmservice/mobile/audit_note_ext/canvas.jsp" charEncoding="UTF-8"> 
		</c:import>
	</div>
