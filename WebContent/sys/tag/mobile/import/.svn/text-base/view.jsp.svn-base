<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page language="java"  import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java"  import="com.landray.kmss.sys.tag.forms.SysTagMainForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" 
	href="${LUI_ContextPath }/sys/tag/mobile/import/style/tag.css">
<c:set var="mainForm" value="${requestScope[param.formName]}" />
<c:set var="sysTagMainForm"
	value="${requestScope[param.formName].sysTagMainForm}" />
<c:set var="sysTagModelName" value="${mainForm.modelClass.name }"/>

	   <%
	   				SysTagMainForm sysTagMainForm = (SysTagMainForm)pageContext.getAttribute("sysTagMainForm");
					String fdTagNames = sysTagMainForm.getFdTagNames();
					if(fdTagNames != null && !"".equals(fdTagNames.trim())) {
						fdTagNames = StringEscapeUtils.escapeHtml(fdTagNames);
						fdTagNames = StringEscapeUtils.escapeJavaScript(fdTagNames);
						pageContext.setAttribute("fdTagNames", fdTagNames);
	   %>
						<div data-dojo-type="sys/tag/mobile/import/js/SysTag"
								data-dojo-props="modelName:'${sysTagModelName}',
												tag:'<%=fdTagNames%>'">
						</div>	
		<%
					}
		%>



