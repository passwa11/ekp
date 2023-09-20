<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="moduleItem" list="${moduleList}" varIndex="status">
		<list:data-column col="fdId"  >
		<%
			Object item = pageContext.getAttribute("moduleItem");
			Map result = (Map)item;
			System.out.println(result.get("fdId"));
			out.print(result.get("fdId"));
		%>			
		</list:data-column>
		<list:data-column col="id"  >
		<%
			Object item = pageContext.getAttribute("moduleItem");
			Map result = (Map)item;
			out.print(result.get("fdId"));
		%>			
		</list:data-column>
		<list:data-column col="name" title="类型">
			<%
				Object item = pageContext.getAttribute("moduleItem");
				Map result = (Map)item;
				out.print(result.get("fdName"));
			%>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
