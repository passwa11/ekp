<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${not empty param['j_content'] && param['j_content']  eq 'true' }">
		<%-- 只返回HTML片段，适用于极速门户下嵌入门户片段 --%>
		<%@ include file="/sys/person/template/person_base_template/content.jsp"%>
	</c:when>
	<c:when test="${ (not empty param['j_iframe'] && param['j_iframe'] eq 'true') ||  (not empty param['j_rIframe'] && param['j_rIframe']  eq 'true' )  }">
		<%-- 适用于工作台模板下打开完整网页(不带页眉、页脚) --%>
		<%@ include file="/sys/person/template/person_base_template/iframe.jsp"%>
	</c:when>
	<c:otherwise>
		<%-- 完整页面 --%>
		<%@ include file="/sys/person/template/person_base_template/all.jsp"%>
	</c:otherwise>
</c:choose>