<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="java.util.Map,com.landray.kmss.sys.attend.model.SysAttendCategory,net.sf.json.JSONObject" %>

<list:data>
	<list:data-columns var="sysAttendCategory" list="${queryPage.list}" varIndex="status">
		<%
		SysAttendCategory category = (SysAttendCategory)pageContext.getAttribute("sysAttendCategory");
		Map map = (Map)request.getAttribute("countMap");
		JSONObject json = (JSONObject)map.get(category.getFdId());
		pageContext.setAttribute("fdTargetsCount", json.getInt("count"));
		pageContext.setAttribute("fdSignCount", json.getInt("signCount"));
		pageContext.setAttribute("fdUnSignCount", json.getInt("unsignCount"));
		%>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="fdName" title="${ lfn:message('sys-attend:sysAttendCategory.custom.fdName') }">
		</list:data-column>
		<list:data-column col="fdStatus" title="">
			<c:out value="${sysAttendCategory.fdStatus}" />
		</list:data-column>
		<list:data-column col="fdStatusTxt" title="">
			<sunbor:enumsShow value="${sysAttendCategory.fdStatus}" enumsType="sysAttendCategory_fdStatus" />
		</list:data-column>
		<list:data-column col="fdTargetsCount" title="">
			<c:out value="${fdTargetsCount}" />
		</list:data-column>
		<list:data-column col="fdSignCount" title="">
			<c:out value="${fdSignCount}" />
		</list:data-column>
		<list:data-column col="fdUnSignCount" title="">
			<c:out value="${fdUnSignCount}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>