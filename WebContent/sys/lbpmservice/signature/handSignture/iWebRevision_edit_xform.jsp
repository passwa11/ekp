<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String userAgent = request.getHeader("User-Agent");
// IE11的header已经去掉MSIE这个属性，需对ie11以上版本做个特殊判断
if (userAgent.contains("MSIE")
		|| (userAgent.contains("rv") && userAgent.contains("Trident"))) {
%>
<div class="iWebRevision_ViewWrap">
	<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevisionObject.jsp" charEncoding="UTF-8">
		<c:param name="iWebRevisionObjectId" value="${param.iWebRevisionObjectId }" />
		<c:param name="recordID" value="${param.recordID }" />
		<c:param name="fieldName" value="${param.fieldName }" />
		<c:param name="userName" value="${param.userName }" />
	</c:import>
</div>

<%
}else{
%>
<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevision_noSupportTip.jsp" charEncoding="UTF-8"/>
<%}%>