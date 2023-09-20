<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/portal/sys_portal_page/page.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<!doctype html>
<html class="lui_portal_quick">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<meta name="renderer" content="webkit" />
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<title>
			<portal:title/>
		</title>
		<script>
			seajs.use(['theme!list', 'theme!form','theme!portal']);
		</script>
	</head>
	<body class="lui_portal_body">
		<portal:header scene="anonymous" var-width="100%" />
		<c:choose>
			<c:when test="${not empty param.j_start }">
				<ui:view
					mode="quick" 
					cfg-startPage="${param.j_start}">
				</ui:view>
			</c:when>
			<c:otherwise>
				<ui:view mode="quick"></ui:view>
			</c:otherwise>
		</c:choose>
		<ui:top id="top"></ui:top>
		<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp"></c:import>
	</body>
</html>