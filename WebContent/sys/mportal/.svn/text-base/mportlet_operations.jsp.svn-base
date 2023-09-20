<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page
	import="com.landray.kmss.sys.mportal.xml.SysMportalMportlet"%>
<%@page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil"%>
<%
	String fdId = request.getParameter("fdId");
	SysMportalMportlet mportlet = MportalMportletUtil.getPortletById(fdId);
	if(mportlet != null) {
		request.setAttribute("operations",
				JSONArray.fromObject(mportlet.getFdOperations()));
%>
	<table class='tb_normal' width="100%">
		<tr class="tr_normal_title">
			<td colspan="2"><bean:message bundle="sys-mportal" key="sysMportalCard.operations.title"/></td>
		</tr>
		<tr>
			<td><bean:message bundle="sys-mportal" key="sysMportalCard.operations.toolBar"/></td>
			<td>
				<label>
				<input type="checkbox" name="___operation" value="toolbar" checked="checked">&nbsp;&nbsp;<bean:message bundle="sys-mportal" key="sysMportalCard.operations.enable"/></label></td>
		</tr>
		<c:if test="${param.fdId!='kms.common.knowledgeIntro'}">
		<tr>
			<td><bean:message bundle="sys-mportal" key="sysMportalCard.operations.more"/></td>
			<td>
				<label>
				<input type="checkbox" name="___operation" value="more" checked="checked">&nbsp;&nbsp;<bean:message bundle="sys-mportal" key="sysMportalCard.operations.enable"/></label></td>
		</tr>
		</c:if>
		<c:forEach items="${operations }" var="operation">
			<c:if test="${operation.type ne 'random'}">
				<tr>
					<td>${operation.name }</td>
					<td>
						<label>
							<input type="checkbox" name="___operation" value="${operation.type }" checked="checked">
							&nbsp;&nbsp;
							<bean:message bundle="sys-mportal" key="sysMportalCard.operations.enable"/>
						</label>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
<%
	}
%>