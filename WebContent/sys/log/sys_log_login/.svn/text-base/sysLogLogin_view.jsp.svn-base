<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<div class="txttitle"><bean:message bundle="sys-log" key="table.sysLogLogin"/></div>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysLogLoginForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdCreateTime"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdCreateTime"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdOperator"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdOperator"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdIp"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdIp"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdLocation"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdLocation"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdBrowser"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdBrowser"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdEquipment"/>
		</td><td width=35%>
			<bean:write name="sysLogLoginForm" property="fdEquipment"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdActionUrl"/>
		</td><td colspan="3" width=85%>
			<bean:write name="sysLogLoginForm" property="fdUrl"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdUserAgent"/>
		</td><td colspan="3" width=85%>
			<bean:write name="sysLogLoginForm" property="fdUserAgent"/>
			<c:if test="${!empty uaInfoMap}">
				<br><br>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-log" key="sysLogLogin.ua.operatingSystem"/>
						</td><td width=85%>
							${uaInfoMap.operatingSystem}
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-log" key="sysLogLogin.ua.browserName"/>
						</td><td width=85%>
							${uaInfoMap.browserName}
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-log" key="sysLogLogin.ua.browserVersion"/>
						</td><td width=85%>
							${uaInfoMap.browserVersion}
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-log" key="sysLogLogin.ua.browserRenderingEngine"/>
						</td><td width=85%>
							${uaInfoMap.browserRenderingEngine}
						</td>
					</tr>
				</table>
			</c:if>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdVerification"/>
		</td><td width=35%>
			<sunbor:enumsShow
				value="${sysLogLoginForm.fdVerification}"
				enumsType="sys_log_login_verification" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-log" key="sysLogLogin.fdType"/>
		</td><td width=35%>
			<sunbor:enumsShow
				value="${sysLogLoginForm.fdType}"
				enumsType="sys_log_login_type" />
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>