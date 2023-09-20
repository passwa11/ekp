<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/kms/common/kms_comment/import/kmsCommentMain_view_js.jsp"%>

<script type="text/javascript">
	var defaultShow = "${param.defaultShow}";
	if(defaultShow=="true"){
		var positionId = "${param.positionId}"; 
		if(positionId!=""){
			showComment($("#"+positionId));
		}
	}
		

</script>
<kmss:authShow roles="ROLE_KMSCOMMON_COMMENT_ADMIN">
	<c:set var="commentAuth" value="true" />
</kmss:authShow>
<c:if test="${param.defaultShow ne true}">
	<c:set var="defaultShow" value="display:none;"/>
</c:if>
<div class="kms_comment" id="comment_${param.fdModelId}" style="${defaultShow}">
	<div class="trig_box"><span class="trig"></span></div>
	<div id="commentList">
	</div>
	<c:import url="/kms/common/kms_comment/import/kmsCommentMain_view_include.jsp" charEncoding="UTF-8">
		<c:param name="fdModelName" value="${param.fdModelName }"></c:param>
		<c:param name="fdModelId" value="${param.fdModelId }"></c:param>
	</c:import>
</div>
