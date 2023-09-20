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
	<!-- <div style="text-align:right;color:#37ace1;font-size:12px;">
		<span style="cursor:pointer;" onclick="iWebRevision_ViewCompleteSignture(this);">查看完整手写签批</span>
	</div> -->
</div>
<script>
	function iWebRevision_ViewCompleteSignture(dom){
		var $div = $(dom).closest('.iWebRevision_ViewWrap');
		if($div){
			var webRevision = $div.find("[name='iWebRevisionObject']");
			if(webRevision.length > 0){
				try{
					webRevision[0].Enabled = "1";
					webRevision[0].LoadSignature();
					webRevision[0].OpenSignature();
					webRevision[0].Enabled = "0";
				}catch(e){
					
				}		
			}
		}
	}
</script>
<%
}else{
%>
<c:import url="/sys/lbpmservice/signature/handSignture/iWebRevision_noSupportTip.jsp" charEncoding="UTF-8"/>
<%}%>