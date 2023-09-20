<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/importResult.css"/>" />
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
</script>
<center>
<div style="display: none" id="resultDiv">${formList}</div>
<c:if test="${empty validateInfoList and empty templateErrorTips and empty budgetCurrencyErrorTips}">
	<c:set var="sucessFlag" value="true"></c:set>
</c:if>
<div id="div_main" class="div_main">
	<c:if test="${not empty templateErrorTips}">
		<div class="failTitle">
			<div class="title">${templateErrorTips}</div>
		</div>
	</c:if>
	<c:if test="${not empty budgetCurrencyErrorTips}">
		<div class="failTitle">
			<div class="title">${budgetCurrencyErrorTips}</div>
		</div>
	</c:if>
	<c:forEach items="${validateInfoList}" var="validateInfo" varStatus="vstatus">
		<div class="failDiv" >
			<div onclick="FSSC_ToggleNextELment(this)" class="failTitle">
				<div class="title">${lfn:message('fssc-budget:message.export.tips.di') }${validateInfo.index}${lfn:message('fssc-budget:message.export.tips.message') }</div>
				<div id="arrow"><c:if test="${fn:length(validateInfo)>0}">︽</c:if></div>
			</div>
			<table>
				<c:forEach items="${validateInfo.error}" var="validate" varStatus="status">
				<tr>
					<td align="left">${status.index+1}、${validate}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>
<script>
	function FSSC_ToggleNextELment(obj){
		$(obj).next("table").toggle();
	}
	Com_AddEventListener(window,'load',function(){
		if("${sucessFlag}"=="true"){
			window.parent.dia.hide($("#resultDiv").text());
		}
	});
</script>
</center>
<%@ include file="/resource/jsp/list_down.jsp"%>
