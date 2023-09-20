<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>
	<head>
	</head>
	<body>
		<div class="evaluation_div" id="test">
		   <c:if test="${isDelete == true}">
		   		<div class="qtip-info">
					<span> ${lfn:message('sys-evaluation:sysEvaluationNotes.hasDelete.tip')}</span>
				</div>
		   </c:if>
		   <c:if test="${empty isDelete }">
				<div class="qtip-info">
					<strong>${lfn:message('sys-evaluation:sysEvaluationNotes.original.doc')}</strong>
					<span> <strong>#</strong> ${docSubject } <strong>#</strong></span>
				</div>
				<div class="qtip-info">
					<strong>${lfn:message('sys-evaluation:sysEvaluationNotes.evaluation')}</strong>
					<span> ${docContent }</span>
				</div>
				<div class="qtip-user">
					<span>-----by</span><strong> ${fdName }</strong>
				</div>
			</c:if>
		</div>	
		
	</body>
	
</html>	

