<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

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
	
	<div class="comment_paging_box" id="comment_paging_box" 
		comment-model-id="${param.fdModelId }" comment-model-name="${param.fdModelName}">
		<ul class="comment_paging_contentBox" id="comment_paging_${param.fdModelId}" >
		</ul>
	</div>
	
	<c:import url="/kms/common/kms_comment/import/templ/kmsCommentMain_view_temp_include.jsp" charEncoding="UTF-8">
		<c:param name="fdModelName" value="${param.fdModelName }"></c:param>
		<c:param name="fdModelId" value="${param.fdModelId }"></c:param>
	</c:import>
</div>
