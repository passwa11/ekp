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
	<div id="_fdTransferNewLevelId"  xform_type="select">
		<xform:select property="fdTransferNewLevelId" required="<%=requiredLevel %>" showStatus="edit" showPleaseSelect="true" mobile="true">
			<xform:beanDataSource serviceBean="sysOrganizationStaffingLevelService" selectBlock="fdId,fdName"></xform:beanDataSource>
		</xform:select>
		</div>	
	</c:when>
	<c:otherwise>
		<div id="_fdTransferNewLevel"  valField="fdTransferNewLevelId" xform_type="staffingLevel">
		<xform:staffingLevel propertyName="fdTransferNewLevelName" required="<%=requiredLevel %>" propertyId="fdTransferNewLevelId" style="<%=parse.getStyle()%>"></xform:staffingLevel>
	    </div>	
	</c:otherwise>
</c:choose>