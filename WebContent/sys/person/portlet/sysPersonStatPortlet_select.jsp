<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="record" list="${queryList}">
		<list:data-column col="fdId">
			${record.modelName}
		</list:data-column>
		<list:data-column col="modelName">
			${record.modelName}
		</list:data-column>
		<list:data-column col="moduleName" title="名称">
			${record.moduleName}
		</list:data-column>
	</list:data-columns>
</list:data>