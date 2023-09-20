<%-- 入职职级 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%
parse.addStyle("width", "control_width", "45%");
boolean requiredLevel = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
%>
<c:choose>
	<c:when test="${param.mobile eq 'true'}">
		<div id="_fdTransferOldLevelId"  xform_type="select">
		<xform:select property="fdTransferOldLevelId" required="<%=requiredLevel %>" showStatus="edit" showPleaseSelect="true" mobile="true" htmlElementProperties="id='fdTransferOldLevel'">
			<xform:beanDataSource serviceBean="sysOrganizationStaffingLevelService" selectBlock="fdId,fdName"></xform:beanDataSource>
		</xform:select>
			</div>	
	</c:when>
	<c:otherwise>
	<div id="_fdTransferOldLevel"  valField="fdTransferOldLevelId" xform_type="staffingLevel">
		<xform:staffingLevel propertyName="fdTransferOldLevelName" required="<%=requiredLevel %>" propertyId="fdTransferOldLevelId" style="<%=parse.getStyle()%>"></xform:staffingLevel>
	    </div>	
	</c:otherwise>
</c:choose>