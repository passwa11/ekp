<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
	%>
	<div id="_fdSignName"  xform_type="xtext">
<xform:xtext property="fdSignName" mobile="${param.mobile eq 'true'? 'true':'false'}" validators="maxLength(200)" required="true" style="<%=parse.getStyle()%>"/>	
 </div>   