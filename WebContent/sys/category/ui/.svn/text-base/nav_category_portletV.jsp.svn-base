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
<ui:dataview>
	<ui:source type="AjaxJson">
		{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=index&modelName=${JsParam.modelName }&parentId=!{value}&nodeType=!{nodeType}&currId=${JsParam.categoryId}&pAdmin=!{pAdmin}&showTemp=${JsParam.showTemp}&expand=true&authType=2"}
	</ui:source>
	<ui:render ref="sys.ui.cate.default" var-href="${href }" var-target="${(empty param.target) ? '_self' : (param.target)}">
	</ui:render>
</ui:dataview>
</ui:ajaxtext>