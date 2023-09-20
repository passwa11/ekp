<%-- 试用期内工作总结 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "95%");%>
<div id="_fdLeaveReason"  xform_type="select">
<xform:select property="fdLeaveReason" showStatus="edit" required="true" style="<%=parse.getStyle()%>" mobile="${param.mobile eq 'true'? 'true':'false'}">
	<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'" orderBy="fdOrder"></xform:beanDataSource>
</xform:select>
   </div>