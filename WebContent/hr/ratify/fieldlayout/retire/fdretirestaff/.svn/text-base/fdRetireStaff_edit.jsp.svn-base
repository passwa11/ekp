<%-- 入职部门 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<c:set var="haveRight" value="false" />
<kmss:authShow roles="ROLE_HRRATIFY_CREATE">
	<c:set var="haveRight" value="true" />
</kmss:authShow>
<c:choose>
	<c:when test="${haveRight eq 'true'}">
		<div id="_fdRetireStaff" valField="fdRetireStaffId" xform_type="address">
			<xform:address
					isLoadDataDict="false"
					showStatus="edit"
					htmlElementProperties="id='fdSalaryStaff'"
					mobile="${param.mobile eq 'true'? 'true':'false'}"
					required="true" style="<%=parse.getStyle()%>"
					subject="${lfn:message('hr-ratify:hrRatifyRetire.fdRetireStaff')}"
					propertyId="fdRetireStaffId" propertyName="fdRetireStaffName"
					orgType='ORG_TYPE_PERSON' className="input" onValueChange="addDeptPost">
			</xform:address>
		</div>
	</c:when>
	<c:otherwise>
		<!--没有权限默认是自己-->
		<html:hidden property="fdRetireStaffName" value="<%=UserUtil.getUser().getFdName()%>" />
		<html:hidden property="fdRetireStaffId" value="<%=UserUtil.getUser().getFdId()%>" />
		<c:out value="<%=UserUtil.getUser().getFdName()%>"/>
	</c:otherwise>
</c:choose>