<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
function initialPage(){
	try {
		var arguObj = document.getElementById("auditNoteTable");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj.offsetHeight + 0;
			if(height>0)
				window.frameElement.style.height = height + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}
function setWindowHight(td_historyTD) {
	var height = document.body.scrollHeight;
	if (height < 20) {
		setTimeout(function() {setWindowHight(td_historyTD);}, 200);
	} else {
		td_historyTD.style.height = document.body.scrollHeight + 50;
	}
}
Com_AddEventListener(window, "load", initialPage);

</script>
</head>
<body>
<html:form action="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do">
	<table class="tb_normal" width="100%" id="auditNoteTable">
		<tr class="tr_normal">
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.date" />
			</td>
			<td width="10%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdFactNodeName" />
			</td>
			<td width="10%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdHandlerId" />
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdActionKey" />
			</td>
			
			<td width="20%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdActionInfo" />
			</td>
			<td width="10%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdNotifyType" />
			</td>
			
		</tr>
		<c:forEach items="${flowLog}" var="lbpmAuditNote" varStatus="vStatus">
			<tr>
				<td>
					<kmss:showDate type="datetime" value="${lbpmAuditNote.fdCreateTime}"/>
				</td>
				<td>
					<c:out value="${lbpmAuditNote.fdFactNodeName}" />
				</td>
				<td>
					<c:out value="${lbpmAuditNote.handlerName}" />
				</td>
				<td style="word-wrap: break-word;word-break: break-all;">
					<c:out value="${lbpmAuditNote.fdActionInfo}" />
				</td>

			
				<td>
					<c:out value="${lbpmAuditNote.fdActionName}" />
				</td>
				<td>
					<c:if test="${empty lbpmAuditNote.fdNotifyType}">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdNotifyType.default" />
					</c:if>
					<c:if test="${not empty lbpmAuditNote.fdNotifyType}">
						<kmss:showNotifyType value="${lbpmAuditNote.fdNotifyType}" />
					</c:if>
				</td>
			
			</tr>
		</c:forEach>
	</table>
</html:form>
<br>
</body>
</html>