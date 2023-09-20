<%-- 入职人员姓名 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
	%>
<%-- 通用的组织架构编号编辑逻辑 --%>
<%-- 组织架构(机构、部门、人员、岗位、常用群组)编号是否必填 --%>
<%-- 如开启必填后，可以按类型（机构、部门、人员、岗位）自定义编号，前端录入的时候按类型校验唯一性，高级数据导入时同样需校验唯一性 --%>

<% if (new SysOrganizationConfig().isNoRequired()) { %>
<xform:text property="fdNo" mobile="${param.mobile eq 'true'? 'true':'false'}" validators="uniqueNo" required="true" style="<%=parse.getStyle()%>"></xform:text>
<% } else { %>
<xform:text property="fdNo" mobile="${param.mobile eq 'true'? 'true':'false'}" style="<%=parse.getStyle()%>"></xform:text>
<% } %>