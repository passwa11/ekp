<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.print">
<template:replace name="head">
</template:replace>
	<template:replace name="title">
		<c:out value="${hrStaffPersonExperienceContractForm.docSubject }"></c:out>
	</template:replace>
<template:replace name="content">

	<!-- 打印机制 -->
	<c:import url="/sys/print/import/sysPrintMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="hrStaffPersonExperienceContractForm" />
		<c:param name="modelName" value="com.landray.kmss.hr.staff.model.HrStaffContractType"></c:param>
		<c:param name="load" value="true"></c:param>
		<c:param name="docSubject" value="${hrStaffPersonExperienceContractForm.docSubject }"></c:param>
	</c:import>
<center>
<%
	String agent = request.getHeader("User-Agent").toLowerCase();
	if(agent.indexOf("msie 8") < 0){ 
%>
	<c:import url="/hr/staff/hr_staff_person_experience/contract/contract_printQrCode.jsp" charEncoding="UTF-8">
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

