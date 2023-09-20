<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/error_import.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="lbpm"%>
<lbpm:lbpmApproveModel modelName="com.landray.kmss.km.review.model.KmReviewMain"></lbpm:lbpmApproveModel>

<c:choose>
	<%--钉钉端--%>
	<c:when test='<%=("true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))) && "ding".equals(request.getParameter("flag"))%>'>
		<c:import url="/km/review/km_review_notify_config/km_review_notify_config_ding.jsp" charEncoding="UTF-8"/>
	</c:when>

	<c:otherwise>
		<c:import url="/km/review/km_review_notify_config/km_review_notify_config.jsp" charEncoding="UTF-8"/>
	</c:otherwise>
</c:choose>