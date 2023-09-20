<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do">
	<c:if test="${not empty queryPage.list }">
		<div id="optBarDiv">
		<kmss:auth
			requestURL="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do?method=deleteall&fdModelId=${param.fdModelId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.hrRatifyFeedbackForm, 'deleteall');">
		</kmss:auth></div>
	</c:if>
	<script type="text/javascript">
		Com_AddEventListener(window,'load',function(){
			var feedBack = document.getElementById("div_feedBackContainer");
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				window.frameElement.style.height = (feedBack.offsetHeight + 40)+"px";
			}
		} );
	</script>
	<center>
	<div id="div_feedBackContainer">
	<%
	if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
	%>
		<bean:message bundle="hr-staff" key="message.no.feedback"/>
	<%
	} else {
	%>
		<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
		<table id="List_ViewTable">
			<tr>
				<sunbor:columnHead htmlTag="td">
					<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
					<td width="40pt"><bean:message key="page.serial" /></td>
					<sunbor:column property="fdHasAttachment"></sunbor:column>
					<sunbor:column property="fdSummary">
						<bean:message bundle="hr-staff" key="hrRatifyFeedback.fdSummary" />
					</sunbor:column>
					<sunbor:column property="fdCreator">
						<bean:message bundle="hr-staff" key="hrRatifyFeedback.docCreatorId" />
					</sunbor:column>
					<sunbor:column property="docCreatorTime">
						<bean:message bundle="hr-staff"
							key="hrRatifyFeedback.docCreatorTime" />
					</sunbor:column>
				</sunbor:columnHead>
			</tr>
			<c:forEach items="${queryPage.list}" var="hrRatifyFeedback"
				varStatus="vstatus">
				<tr
					kmss_href="<c:url value="/hr/ratify/hr_ratify_feedback/hrRatifyFeedback.do" />?method=view&fdId=${hrRatifyFeedback.fdId}">
					<td><input type="checkbox" name="List_Selected"
						value="${hrRatifyFeedback.fdId}"></td>
					<td>${vstatus.index+1}</td>
					<td>
						<c:if test="${hrRatifyFeedback.fdHasAttachment }">
							<img alt="有附件" src="../img/fj.png">
						</c:if>
					</td>
					<td><c:out value="${hrRatifyFeedback.fdSummary}" />
					</td>
					<td><c:out value="${hrRatifyFeedback.fdCreator.fdName}" />
					</td>
					<td><sunbor:date value="${hrRatifyFeedback.docCreateTime}" /></td>
				</tr>
			</c:forEach>
		</table>
		<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
	</div>
	</center>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
