<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		String newLocn="${LUI_ContextPath}/kms/category/kms_category_main_ui/kms_category_knowledge_index.jsp#j_path=%2Fmultidoc&type=multidoc%2C文档&cri.q=template%3A1";
		response.setHeader("Location",newLocn);
%>
