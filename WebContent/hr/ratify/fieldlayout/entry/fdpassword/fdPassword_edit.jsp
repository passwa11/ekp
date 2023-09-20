<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
	%>
	<div id="_fdPassword" xform_type="xtext">
<xform:xtext property="fdPassword" mobile="${param.mobile eq 'true'? 'true':'false'}" required="true" style="<%=parse.getStyle()%>"/>	
</div>	