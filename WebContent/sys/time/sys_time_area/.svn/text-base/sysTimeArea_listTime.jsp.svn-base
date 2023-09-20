<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
	
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<td>
					区域组名称
				</td>
				<td>
					用户名称
				</td>
				<td>
					日期
				</td>
				<td>
					工作状态
				</td>
				<td>
					工作时间
				</td>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysTimeArea" varStatus="vstatus">
			<tr>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysTimeArea.name}" />
				</td>
				<td>
					<c:out value="${sysTimeArea.uname}" />
				</td>
				<td>
					<c:out value="${sysTimeArea.time}" />
				</td>
				<td>
					<c:out value="${sysTimeArea.state}" />
				</td>
				<td>
					<c:out value="${sysTimeArea.wtime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>

	</template:replace>
</template:include>