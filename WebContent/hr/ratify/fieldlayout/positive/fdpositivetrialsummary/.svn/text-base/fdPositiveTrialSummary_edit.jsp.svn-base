<%-- 试用期内工作总结 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "95%");%>
<c:choose>
	<c:when test="${param.mobile eq 'true'}">
	<div id="_fdPositiveTrialSummary"  xform_type="textarea">
		<xform:textarea property="fdPositiveTrialSummary" showStatus="edit" mobile="true" style="<%=parse.getStyle() %>"></xform:textarea>
	</div>	
	</c:when>
	<c:otherwise>
		<div id="_fdPositiveTrialSummary"  xform_type="rtf">
		<xform:rtf property="fdPositiveTrialSummary" showStatus="edit"></xform:rtf>
		</div>	
	</c:otherwise>
</c:choose>