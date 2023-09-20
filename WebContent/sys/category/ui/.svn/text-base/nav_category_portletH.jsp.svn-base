<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.Map"%>
<ui:ajaxtext>
<%
	
	Object url = request.getParameter("url");
	if (url != null) {
		String urlParam = "cri.q=fdTemplate:!{value}";
		String href = String.valueOf(url);
		if (href.indexOf('#') > 0) {
			href +=  urlParam;
		} else {
			href += "#" + urlParam;
		}
		request.setAttribute("href", href);
	}
%>
<ui:menu layout="sys.ui.menu.ver.default">
	<ui:menu-source href="${href}" target="${(empty param.target) ? '_self' : (param.target)}">
		<ui:source type="AjaxJson">
			{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=index2&modelName=${JsParam.modelName}&parentId=!{value}&nodeType=!{nodeType}&currId=${JsParam.categoryId}&pAdmin=!{pAdmin}&showTemp=${JsParam.showTemp}&authType=2"}
		</ui:source>
	</ui:menu-source>
</ui:menu>
</ui:ajaxtext>