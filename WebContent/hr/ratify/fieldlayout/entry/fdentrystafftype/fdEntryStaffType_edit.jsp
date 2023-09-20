<!-- 人员类别 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "false"));
	%>
	<div id="_fdEntryStaffTypeId"  xform_type="select">
	<xform:select property="fdEntryStaffTypeId" showStatus="edit" required="<%=required%>" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryStaffType')}" mobile="${param.mobile eq 'true'? 'true':'false'}">
		<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdId,fdName" whereBlock="hrStaffPersonInfoSettingNew.fdType='fdStaffType'" orderBy="fdOrder"></xform:beanDataSource>
	</xform:select>
	</div>	