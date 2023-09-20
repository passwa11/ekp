<%-- 入职职级 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<c:choose>
	<c:when test="${param.mobile eq 'true'}">
	<div id="_fdEntryLevelId"  xform_type="select">
		<xform:select property="fdEntryLevelId" showStatus="edit" showPleaseSelect="true" mobile="true" required="<%=required %>">
			<xform:beanDataSource serviceBean="sysOrganizationStaffingLevelService" selectBlock="fdId,fdName"></xform:beanDataSource>
		</xform:select>
		</div>
	</c:when>
	<c:otherwise>
	   <div id="_dEntryLevelId"  xform_type="staffingLevel">
		<xform:staffingLevel propertyName="fdEntryLevelName" propertyId="fdEntryLevelId" style="<%=parse.getStyle()%>" required="<%=required %>" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryLevel') }"></xform:staffingLevel>
	  		</div>
	</c:otherwise>
</c:choose>