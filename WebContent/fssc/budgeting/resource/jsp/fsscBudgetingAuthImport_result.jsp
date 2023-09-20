<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<template:include ref="default.dialog" sidebar="auto">
<template:replace name="content">
<script type="text/javascript">
Com_IncludeFile("jquery.js");
</script>
<center>
<c:if test="${empty validateInfoList}">
	<c:set var="sucessFlag" value="true"></c:set>
</c:if>
<div id="div_main" class="div_main">
	<c:forEach items="${validateInfoList}" var="validateInfo" varStatus="vstatus">
		<div class="failDiv" >
			<div onclick="FSSC_ToggleNextELment(this)" class="failTitle">
				<div class="title">${lfn:message('fssc-budgeting:message.export.tips.di') }${vstatus.index+1}${lfn:message('fssc-budgeting:message.export.tips.message') }</div>
				<div id="arrow"><c:if test="${fn:length(validateInfo)>0}">︽</c:if></div>
			</div>
			<table>
				<c:forEach items="${validateInfo}" var="validate" varStatus="status">
				<tr>
					<td align="left">${status.index+1}、${validate}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>
<script>
	seajs.use(['lui/dialog'],function(dialog){
		$(function(){
			if("${sucessFlag}"=="true"){
			window.parent.dialog.hide(true);
			}
		});
	});
</script>
</center>
</template:replace>
</template:include>
