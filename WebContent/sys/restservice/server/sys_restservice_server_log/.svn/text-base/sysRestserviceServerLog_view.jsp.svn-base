<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>	
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/log/resource/import/jshead.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
<%
String modelName = "com.landray.kmss.sys.restservice.server.model.SysRestserviceServerLogConfig";
ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
Map map = sysAppConfigService.findByKey(modelName);
request.setAttribute("sysRestserviceServerLogConfig", map);
%>
</script>
<div id="optBarDiv">
	<!-- 开启三员后不能删除 -->
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
	<kmss:auth requestURL="/sys/restservice/server/sys_restservice_server_log/sysRestserviceServerLog.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('sysRestserviceServerLog.do?method=delete&fdId=${JsParam.fdId}','_self');">
	</kmss:auth>
	<% } %>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-restservice-server" key="table.sysRestserviceServerLog"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdServiceName"/>
		</td><td width="35%">
			<xform:text property="fdServiceName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdOriginUri"/>
		</td><td width="35%">
			<xform:text property="fdOriginUri" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdUserName"/>
		</td><td width="35%">
			<xform:text property="fdUserName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdClientIp"/>
		</td><td width="35%">
			<xform:text property="fdClientIp" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdStartTime"/>
		</td><td width="35%">
			<xform:datetime property="fdStartTime" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdEndTime"/>
		</td><td width="35%">
			<xform:datetime property="fdEndTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdRunTime"/>
		</td><td width="35%">
			<xform:text property="fdRunTime" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdRunTimeMillis"/>
		</td><td width="35%">
			<xform:text property="fdRunTimeMillis" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdExecResult"/>
		</td><td width="85%" colspan="3">
			<xform:radio property="fdExecResult">
				<xform:enumsDataSource enumsType="sys_restservice_server_log_fd_exec_result" />
			</xform:radio> 
		</td>
	</tr>
	<c:if test="${sysRestserviceServerLogConfig.dataType eq '1' }">
		<%-- 请求报头 --%>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdRequestHeader"/>
			</td><td width="85%" colspan="3" class="valign_top">
				<xform:textarea property="fdRequestHeader" style="width:85%"/>
			</td>
		</tr>
		<%-- 响应报头 --%>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdResponseHeader"/>
			</td><td width="85%" colspan="3" class="valign_top">
				<xform:textarea property="fdResponseHeader" style="width:85%"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${sysRestserviceServerLogConfig.dataType eq '2' }">	
		<%-- 请求报头、报文 --%>
		<tr>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdRequestHeader"/>
			</td>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdRequestMsg"/>
			</td>
		</tr>
		<tr>
			<td width=50% colspan="2" class="valign_top">
				<xform:textarea property="fdRequestHeader" style="width:85%"/>
			</td>
			<td width=50% colspan="2" class="valign_top">
				<div class="pre_hide">
					<xform:textarea property="fdRequestMsg" style="width:85%"/>
				</div>
			</td>
		</tr>
		<%-- 响应报头、报文 --%>
		<tr>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdResponseHeader"/>
			</td>
			<td class="td_normal_title" width=50% colspan="2">
				<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdResponseMsg"/>
			</td>
		</tr>
		<tr>
			<td width=50% colspan="2" class="valign_top">
				<xform:textarea property="fdResponseHeader" style="width:85%"/>
			</td>
			<td width=50% colspan="2" class="valign_top">
				<div class="pre_hide">
					<xform:textarea property="fdResponseMsg" style="width:85%"/>
				</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-restservice-server" key="sysRestserviceServerLog.fdErrorMsg"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdErrorMsg" style="width:85%"/>
		</td>
	</tr>
</table>
</center>
<link type="text/css" rel="styleSheet"  href="${LUI_ContextPath}/sys/log/resource/css/systemLog_view.css" />
<script type="text/javascript" src="${LUI_ContextPath}/sys/log/resource/js/systemLog_view.js"></script>
<%@ include file="/resource/jsp/view_down.jsp"%>
