<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.print">
<template:replace name="head">
</template:replace>
	<template:replace name="title">
		<c:out value="${modelingAppModelMainForm.docSubject }"></c:out>
	</template:replace>
<template:replace name="content">

	<!-- 打印机制 -->
	<c:import url="/sys/print/import/sysPrintMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="modelingAppModelMainForm" />
		<c:param name="modelName" value="com.landray.kmss.sys.modeling.base.model.ModelingAppModel"></c:param>
	</c:import>
<center>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){ 
%>
	<div id="qrcodexDiv">
		<c:set var="modelViewUrl" value="sys/modeling/main/modelingAppModelMain.do?method=view&fdId=${param.fdId }"/>
		<%@ include file="/sys/modeling/main/formview/model_printQrCode.jsp"%>
	</div>
<% 
	}
%>
</center>
<script>
function outputPDF() {
	seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
		$(".qrcodeArea").hide();
		exp.exportPdf($(".lui_form_content_td")[0],{callback:function() {
			$(".qrcodeArea").show();
		}});
	});
}
</script>
</template:replace>
		
</template:include>

