<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.kms.credit.service.IKmsCreditSumPersonalService"%>
<%@page import="net.sf.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%
			IKmsCreditSumPersonalService kmsCreditSumPersonalService = 
				(IKmsCreditSumPersonalService)SpringBeanUtil.getBean("kmsCreditSumPersonalService");
			JSONObject sumObj = kmsCreditSumPersonalService.getCreditSum(null);
	%>
	<li>
		<a href="${LUI_ContextPath}/kms/credit/student/" target="_blank"><%=sumObj.get("fdCreditSum")%></a>
		<p>${lfn:message('kms-loperation:kmsLoperationStuTotal.credit') }</p>
	</li>
