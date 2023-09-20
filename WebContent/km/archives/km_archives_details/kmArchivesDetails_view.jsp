<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.km.archives.forms.KmArchivesDetailsForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	KmArchivesDetailsForm form = (KmArchivesDetailsForm)request.getAttribute("kmArchivesDetailsForm");
	Date returnDate = DateUtil.convertStringToDate(form.getFdReturnDate(), DateUtil.TYPE_DATETIME, request.getLocale());
	int expired = new Date().compareTo(returnDate) >= 0 ? 1 : 0;
	String url = request.getContextPath()+"/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId="+form.getFdArchId()+"&expired="+expired;
	// 页面跳转
	//request.getRequestDispatcher(url).forward(request, response);
%>
<script>
window.location.href="<%=url%>";
</script>