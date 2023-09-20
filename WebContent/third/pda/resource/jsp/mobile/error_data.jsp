<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.util.KmssMessageWriter,com.landray.kmss.util.KmssReturnPage"%>
<%
	if (request.getHeader("accept") != null) {
		if (request.getHeader("accept").indexOf("application/json") >= 0) {

		}
	}
	KmssMessageWriter msgWriter = null;
	if (request.getAttribute("KMSS_RETURNPAGE") != null) {
		msgWriter = new KmssMessageWriter(request, (KmssReturnPage) request.getAttribute("KMSS_RETURNPAGE"));
	} else {
		msgWriter = new KmssMessageWriter(request, null);
	}
	if (msgWriter != null) {
		String messages = msgWriter.DrawTitle(true);
		pageContext.setAttribute("messages", messages);
	}
%>

<style>
body {
	font-size: 16px !important;
	background-color: #eee !important;
}

.muiLoginPage {
	background-color: #f0eff5;
}

.msgtitle {
	padding: 2rem;
}

.errortitle {
	padding: 2rem;
}
</style>
<div class="noDataTipWrapper">
	<div class="icon">
		<i class="mui mui-noFind"></i>
	</div>
	<c:if test="${not empty messages}">
		${messages }
		<div>
			<%=msgWriter.DrawMessages()%>
		</div>
	</c:if>
	<c:if test="${empty messages}">
		<p class="tips">
			<bean:message key="return.optFailure" />
		</p>

	</c:if>

	<a class="btnReturn" onclick="redirectTo()"><i class="mui mui-back"></i></i>
		<bean:message key="phone.view.back" bundle="third-pda" /></a>
</div>
<script type="text/javascript">
	require([ 'mui/device/adapter' ], function(adapter) {
		window.redirectTo = function() {
			var rtn = adapter.goBack();
			if (rtn == null) {
				history.back();
			}
		};
	});
</script>
