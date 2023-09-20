<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%@ page import="com.landray.kmss.util.EnumerationTypeUtil" %>
<%@ page import="com.sunbor.web.tag.enums.ValueLabel,java.util.List" %>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
		List enumList =EnumerationTypeUtil.getColumnEnumsByType("hrStaffPersonInfo_fdStatus");
		String defaultValue="";
		String method =(String) request.getParameter("method");
		if(enumList!=null && !enumList.isEmpty()&&"add".equalsIgnoreCase(method)){
			ValueLabel valueLabel = (ValueLabel) enumList.get(0);
			defaultValue=valueLabel.getValue();
		}
	%>
<c:choose>
	<c:when test="${param.mobile}">
	<div id="_fdEntryStatus" xform_type="select">
		<xform:select property="fdEntryStatus" mobile="true" style="<%=parse.getStyle()%>" value="<%=defaultValue %>">
			<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus_new"></xform:enumsDataSource>
		</xform:select>
		</div>
	</c:when>
	<c:otherwise>
		<sunbor:enums elementClass="hr_select" property="fdEntryStatus" enumsType="hrStaffPersonInfo_fdStatus_new" elementType="select" />
	</c:otherwise>
</c:choose>