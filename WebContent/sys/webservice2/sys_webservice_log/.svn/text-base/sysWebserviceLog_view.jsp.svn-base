<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>	
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
<%
String modelName = "com.landray.kmss.sys.webservice2.model.SysWebserviceLogConfig";
ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
Map map = sysAppConfigService.findByKey(modelName);
request.setAttribute("sysWebserviceLogConfig", map);
%>
</script>
<div id="optBarDiv">
	<!-- 开启三员后不能删除 -->
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
	<kmss:auth requestURL="/sys/webservice2/sys_webservice_log/sysWebserviceLog.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysWebserviceLog.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<% } %>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-webservice2" key="table.sysWebserviceLog"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdServiceName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdServiceName" style="width:85%" />
		</td>
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdServiceBean"/>
		</td><td width="35%">
			<xform:text property="fdServiceBean" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdServiceMethod"/>
		</td><td width="35%">
			<xform:text property="fdServiceMethod" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdUserName"/>
		</td><td width="35%">
			<xform:text property="fdUserName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdClientIp"/>
		</td><td width="35%">
			<xform:text property="fdClientIp" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdStartTime"/>
		</td><td width="35%">
			<xform:datetime property="fdStartTime" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdEndTime"/>
		</td><td width="35%">
			<xform:datetime property="fdEndTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdRunTime"/>
		</td><td width="35%">
			<xform:text property="fdRunTime" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdRunTimeMillis"/>
		</td><td width="35%">
			<xform:text property="fdRunTimeMillis" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdExecResult"/>
		</td><td width="85%" colspan="3">
			<xform:radio property="fdExecResult">
				<xform:enumsDataSource enumsType="sys_webservice_log_fd_exec_result" />
			</xform:radio> 
		</td>
	</tr>
	<c:if test="${sysWebserviceLogConfig.dataType eq '1' }">	
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdRequestMsg"/>
			</td><td width="85%" colspan="3">
				<c:out value="${sysWebserviceLogForm.fdRequestMsg }"></c:out>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdResponseMsg"/>
			</td><td width="85%" colspan="3">
				<c:out value="${sysWebserviceLogForm.fdResponseMsg }"></c:out>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-webservice2" key="sysWebserviceLog.fdErrorMsg"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdErrorMsg" style="width:85%"/>
			
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>