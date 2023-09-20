<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld"
	prefix="portal"%>
<c:choose>
	<c:when
		test="${ (not empty param['j_iframe'] && param['j_iframe'] eq 'true') || not empty param['j_rIframe'] && param['j_rIframe'] eq 'true' }">
		<%-- 不需要页眉、页脚,适用于极速门户下嵌入2级页面 --%>
		<%@ include file="/sys/zone/template/module/iframe.jsp"%>
	</c:when>
	<c:otherwise>
		<%-- 完整页面,适用于直接打开完整2级页面 --%>
		<%@ include file="/sys/zone/template/module/all.jsp"%>
	</c:otherwise>
</c:choose>