<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	Object _varParams = request.getAttribute("varParams");
	if (_varParams != null) {
		Map<String, Object> varParams = (Map) _varParams;
		// 扩展查询字段
		Object extProps = varParams.get("extProps");
		if (extProps != null) {
			JSONObject ___obj = JSONObject.fromObject(extProps);
			Iterator it = ___obj.keys();
			JSONArray array = new JSONArray();
			while (it.hasNext()) {
				Object key = it.next();
				array.add(("qq." + key + "=" + ___obj.getString(key.toString())).toString());
			}
			String ___extProps = StringUtil.join(array, "&");
			pageContext.setAttribute("extProps", "&" + ___extProps);
		}
	}
%>



<ui:dataview>

	<ui:source type="AjaxJson">
		{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=index&modelName=${varParams.modelName }&parentId=!{value}&level=4&expand=true&authType=2${extProps}"}
	</ui:source>

	<ui:render ref="sys.ui.cate.knowledge.all"
		cfg-modelName="${varParams.modelName }">
	</ui:render>

</ui:dataview>