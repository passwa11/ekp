<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<%@ include file="/kms/category/kms_category_main_ui/kms_category_knowledge_index.jsp" %>
<%
	}else{
%>
	<%@ include file="/kms/category/notOpenCategory.jsp" %>
<%
	}
%>
