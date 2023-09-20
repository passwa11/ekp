<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="head">
		<template:super/>
	</template:replace>
	<template:replace name="body"> 
		<div style="padding: 10px">
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" style="text-align: center;" colspan="2">
						<b><label>${ moduleName }</label></b>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" style="text-align: center;" colspan="2">
						<b><label>${ messageKey }</label></b>
					</td>
				</tr>
				<c:forEach items="${messageMap}" var="message">
				<tr>
					<td class="td_normal_title" width="25%">
						${ message.key }
					</td>
					<td>
						<c:out value="${ message.value }" escapeXml="true"></c:out>
					</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</template:replace>
</template:include>