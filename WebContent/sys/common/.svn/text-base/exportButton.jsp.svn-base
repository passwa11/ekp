<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.util.SysFormDingUtil"%>
<%
	String showHtml = request.getParameter("showHtml")==null?"true":request.getParameter("showHtml");
	String showPdf = request.getParameter("showPdf")==null?"true":request.getParameter("showPdf");
	pageContext.setAttribute("showHtml", showHtml);
	pageContext.setAttribute("showPdf", showPdf);
	//是否钉钉高级审批模式
	pageContext.setAttribute("ddMode", SysFormDingUtil.getEnableDing());
%>
<c:if test="${'true' ne isPrintBatch }">
<script>
function outputMHT() {
	seajs.use(['lui/export/export'],function(exp) {
		exp.exportMht();
	});
}
function outputPDF() {
	seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
		exp.exportPdf();
	});
}
</script>
<c:choose>
	<c:when test="${'true' eq param.oldStyle }">
		<!-- 导出mht -->
		<c:if test="${'true' eq showHtml }">
		<c:if test="${'true' ne  ddMode}">
			<input class="lui_form_button" type="button" value="<bean:message key="button.export.mht"/>" onclick="outputMHT();">
	    </c:if>
		</c:if>
		<!-- 导出pdf -->
		<c:if test="${'true' eq showPdf}">
			<input class="lui_form_button" type="button" value="<bean:message key="button.export.pdf"/>" onclick="outputPDF();">
		</c:if>
	</c:when>
	<c:otherwise>
		<!-- 导出MHT -->
		<c:if test="${'true' eq showHtml }">
		<c:if test="${'true' ne  ddMode}">
			<ui:button text="${ lfn:message('button.export.mht') }" onclick="outputMHT();">
			</ui:button>
		</c:if>
		</c:if>
		<!-- 导出PDF -->
		<c:if test="${'true' eq showPdf }">
			<ui:button text="${ lfn:message('button.export.pdf') }" onclick="outputPDF();">
			</ui:button>
		</c:if>
	</c:otherwise>
</c:choose>
<style>
.tb_normal>tbody>tr{border-bottom: 0px solid #d2d2d2 !important;border-top: 0px solid #d2d2d2 !important;}
.tb_normal>tbody>tr>td {padding: 8px; word-break:break-word; border: 1px #d2d2d2 solid;}
</style>
</c:if>