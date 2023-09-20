<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:cri-criterion title="${criterionAttrs['title']}" multi="false" key="partition" expand="true">
	<list:varDef name="title"></list:varDef>
	<list:varDef name="selectBox">
	<list:box-select>
		<list:varDef name="select">
			<%
			Map attrs = (Map)request.getAttribute("criterionAttrs");
			pageContext.setAttribute("defaultValue",SqlPartitionConfig.getInstance().getModel(attrs.get("modelName").toString()).getDefaultValue());
			%>
			<list:item-select cfg-required="true" cfg-defaultValue="${ defaultValue }">
				<ui:source type="Static">
					<% 
					List list = SqlPartitionConfig.getInstance().getModel(attrs.get("modelName").toString()).getPartitions();
					out.print(JSONArray.fromObject(list).toString()); 
					%>
				</ui:source>
			</list:item-select>
		</list:varDef>
	</list:box-select>
	</list:varDef>
</list:cri-criterion>