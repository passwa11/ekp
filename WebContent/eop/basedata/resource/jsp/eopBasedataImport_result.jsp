<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/importResult.css"/>" />
<script>
	Com_IncludeFile("jquery.js");
</script>
<center>
<p class="txtTitle"><bean:message bundle="eop-basedata" key="message.import.reslut"/></p>
<div id="div_main" class="div_main">
	<c:forEach items="${messages}" var="fsBudgetImportMessage" varStatus="vstatus">
	<c:if test="${fsBudgetImportMessage.messageType eq '0'}">
		<div class="failDiv" >
			<div onclick="FSSC_ToggleNextELment(this)" class="failTitle">
				<div class="title">${fsBudgetImportMessage.message}</div>
				<div id="arrow"><c:if test="${fn:length(fsBudgetImportMessage.moreMessages)>0}">︽</c:if></div>
			</div>
			<table>
				<c:forEach items="${fsBudgetImportMessage.moreMessages}" var="message" varStatus="vstatus2">
				<tr>
					<td align="left">${vstatus2.index+1}、${message}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
	</c:forEach>
</div>
</center>
<script>
	function FSSC_ToggleNextELment(obj){
		$(obj).next("table").toggle();
	}
	Com_AddEventListener(window,'load',function(){
		if('${messages}'!=='[]'){
			return;
		}
		if(window.parent.dia){
			window.parent.dia.hide(true)
		}else{
			top.$('.lui_profile_moduleMain_frame')[0].contentWindow.document.viewFrame.dia.hide(true)
		}
	})
	
	
</script>
<%@ include file="/resource/jsp/list_down.jsp"%>
