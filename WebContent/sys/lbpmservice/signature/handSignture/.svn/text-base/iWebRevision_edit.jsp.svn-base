<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
.iWebRevisionOperation{
	display:inline-block;
	margin-bottom:3px;
}
.iWebRevisionOperation a{
	margin-left:5px;
}
</style>
<%
String userAgent = request.getHeader("User-Agent");
// IE11的header已经去掉MSIE这个属性，需对ie11以上版本做个特殊判断
if (userAgent.contains("MSIE")
		|| (userAgent.contains("rv") && userAgent.contains("Trident"))) {
%>

<div class="iWebRevisionOperation">
	<a href="javascript:void(0);" class="com_btn_link" onclick="iWebRevision_OpenSignture(this);">打开手写签批窗口</a>
	<a href="javascript:void(0);" class="com_btn_link" onclick="iWebRevision_ClearAllSignture(this);">清空签批信息</a>
	<a href="javascript:void(0);" class="com_btn_link" onclick="iWebRevision_Undo(this);">撤销上笔</a>
	<a href="javascript:void(0);" class="com_btn_link" onclick="iWebRevision_Redo(this);">恢复下笔</a>
</div>
<div style="width:100%;height:100px;border:1px solid #dfdfdf;display:block;">
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