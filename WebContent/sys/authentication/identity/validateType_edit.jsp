<%@ page language="java" pageEncoding="UTF-8"
	import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm"%>
<%@ page
	import="com.landray.kmss.util.SpringBeanUtil,
                java.util.List,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityPlugin,
                com.landray.kmss.sys.authentication.identity.plugin.IdentityValidatePluginData,
                java.util.Map,
                java.util.TreeMap"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page
	import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>
<%
	PasswordSecurityConfig securityConfig = new PasswordSecurityConfig();
	String isEnable = securityConfig.getMobileNoUpdateCheckEnable();
	String isEmailEnable = securityConfig.getAlterEmailEnabled();
%>
<%
	List<IdentityValidatePluginData> identityList = IdentityPlugin.getExtensionList();
	/* Map<String, String> returnMap = new TreeMap<String, String>();
	for(IdentityValidatePluginData data : identityList) {
		String key = String.valueOf(data.getOrder());
		String value =data.getName();
		returnMap.put(key, value);
	} */
	request.setAttribute("identityList", identityList);
%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super /> - ${lfn:message('sys-zone:sysZonePersonInfo') }
	</template:replace>
	<template:replace name="head">
		<script>
			window.CKEDITOR_BASEPATH = '${ LUI_ContextPath}/resource/ckeditor/';
		</script>
		<script>
			seajs.use([ 'theme!zone' ]);
			Com_IncludeFile("security.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content
				title="${ lfn:message('sys-authentication-identity:my.authentication.information')}">
				<div class="lui_zone_per_right">
					<ul class="lui_zone_per_tab">
						<c:forEach items="${identityList}" var="list">
							<c:if test="${not empty list.settingTitle}">
								<li class="lui_zone_per_header">
									<span class="title">${list.settingTitle}</span>
									<span class="lui_zone_base_up arrow" id="${list.key}"
										onclick="settringFunction('${list.validateEditPath}')"> 
										<c:choose>
												<c:when test="${list.validateBean.isSettting()==true}">
										         ${ lfn:message('sys-authentication-identity:my.authentication.perfect')}
										       	</c:when>
												<c:otherwise>
										         ${ lfn:message('sys-authentication-identity:my.authentication.setUp')}
										       </c:otherwise>
										</c:choose>
									</span>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</ui:content>
		</ui:tabpanel>
		<script type="text/javascript">
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|doclist.js|dialog.js");
		</script>
		<!-- 引入通用的手机号校验规则 -->
		<c:import
			url="/sys/organization/sys_org_person/common_mobileNo_check.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="sysZonePersonInfoForm" />
		</c:import>
		<script>
			var dia;
			function settringFunction(path) {//根据path弹出配置界面
				/* var dialog = new KMSSDialog();
				dialog.URL = Com_Parameter.ContextPath +path;
				dialog.Show(window.screen.width*400/800,window.screen.height*400/800); */
				var viewUrl = Com_Parameter.ContextPath + path;
				seajs.use("lui/dialog", function(dialog) {
					dia = dialog.iframe(path, " ", null, {
						width : 600,
						height : 300,
						topWin : window,
						close : true
					});
				});
			}

			function close(id) {
				$("#" + id)
						.text(
								"${ lfn:message('sys-authentication-identity:my.authentication.perfect')}");
				dia.hide();
			}
		</script>
	</template:replace>
</template:include>
