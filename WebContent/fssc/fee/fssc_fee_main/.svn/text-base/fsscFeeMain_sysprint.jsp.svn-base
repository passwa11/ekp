<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include ref="default.print">
<template:replace name="head">
</template:replace>
	<template:replace name="title">
		<c:out value="${fsscFeeMainForm.docSubject }"></c:out>
	</template:replace>
<template:replace name="content">
	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>
			<p class="txttitle"><c:out value="${fsscFeeMainForm.docSubject }"></c:out></p>
		</div>
		<%--条形码--%>
		<div id="barcodeTarget" style="float:right;margin-right:40px;margin-top: -20px;" ></div>
	</div>
	<!-- 打印机制 -->
	<c:import url="/sys/print/import/sysPrintMain_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="fsscFeeMainForm"/>
		<c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate"/>
		<c:param name="docSubject" value=""/>
	</c:import>
	<script>
	function outputPDF() {
		seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
			exp.exportPdf($(".lui_form_content_td")[0],{callback:function() {
			}});
		});
	}
	</script>
	<%-- 条形码公共页面 --%>
	<c:import url="/eop/basedata/resource/jsp/barcode.jsp" charEncoding="UTF-8">
		<c:param name="docNumber">${fsscFeeMainForm.docNumber }</c:param>
	</c:import>
</template:replace>
		
</template:include>

