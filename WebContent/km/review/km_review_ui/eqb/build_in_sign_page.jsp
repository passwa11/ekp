<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:if test="${param.enable ne 'false'}">
	<ui:content title="${ lfn:message('km-review:kmReviewMain.eqb.sign.page.table.title') }" expand="false">
		<ui:iframe src="${eqbSignUrl}">
		</ui:iframe>
	</ui:content>
</c:if>

