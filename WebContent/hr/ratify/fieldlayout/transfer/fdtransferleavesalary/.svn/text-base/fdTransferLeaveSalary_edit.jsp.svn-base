<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "45%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "false"));
	%>
	<div id="_fdTransferLeaveSalary"  xform_type="xtext">
<xform:xtext property="fdTransferLeaveSalary" mobile="${param.mobile eq 'true'? 'true':'false'}" validators="digits min(0)" required="<%=required%>" style="<%=parse.getStyle()%>" htmlElementProperties="id='fdTransferLeaveSalary'"/>
 </div>	