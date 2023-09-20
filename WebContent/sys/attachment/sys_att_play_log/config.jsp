<%@page
	import="com.landray.kmss.sys.attachment.service.ISysAttPlayLogType"%>
<%@page import="java.util.List"%>
<%@page
	import="com.landray.kmss.sys.attachment.service.spring.SysAttPlayLogTypeFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit">
	<template:replace name="content">

		<h2 align="center" style="margin: 10px 0">
			<span style="color: #35a1d0;">${lfn:message("sys-attachment:table.sysAttachmentPlayLog.config") }</span>
		</h2>


		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">


			<center>
				<table class="tb_normal" width=95%>

					<%
						List<ISysAttPlayLogType> types = SysAttPlayLogTypeFactory.getTypes();

									for (ISysAttPlayLogType type : types) {
					%>
					<tr>
						<td class="td_normal_title" width=15%><%=type.getTitle()%></td>

						<%
							String prop = "value(kmss.sys.attachment.play.log." + type.getType() + ".enabled)";
						%>
						<td><ui:switch property="<%=prop%>"
								enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
							<span class="message"><font color="red"><%=type.getDesc()%></font></span></td>
					</tr>
					<%
						}
					%>
				</table>
			</center>

			<center style="margin-top: 10px;">
				<ui:button text="${lfn:message('button.submit') }" height="35"
					width="120" onclick="submit()"></ui:button>
			</center>

			<script>
				function submit() {
					Com_Submit(document.sysAppConfigForm, 'update')
				}
			</script>

		</html:form>

	</template:replace>

</template:include>