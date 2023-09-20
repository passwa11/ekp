<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.print">
<template:replace name="head">
</template:replace>
	<template:replace name="title">
		<c:out value="${kmReviewMainForm.docSubject }"></c:out>
	</template:replace>
<template:replace name="content">

	<!-- 打印机制 -->
	<c:import url="/sys/print/import/sysPrintMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmReviewMainForm" />
		<c:param name="modelName" value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
	</c:import>
	<script type="text/javascript">
    	Com_IncludeFile("printDLMix.js",Com_Parameter.ContextPath+"km/review/resource/js/","js",true);
    </script>
<center>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){ 
%>
	<c:import url="/km/review/km_review_main/kmReviewMain_printQrCode.jsp" charEncoding="UTF-8">
		<c:param name="hideCode" value="true" />   
	</c:import>
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

