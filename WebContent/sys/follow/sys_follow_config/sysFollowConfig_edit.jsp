<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-follow" key="table.sysFollowConfig"/></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin:10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-follow" key="table.sysFollowConfig"/></span>
		</h2>
		
		<html:form action="/sys/follow/sys_follow_config/sysFollowConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-follow" key="sysFollowConfig.alreadFollowDay"/>
						</td><td width="85%">
							<xform:text property="alreadFollowDay" style="width:85%" validators="noSpecialChar"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-follow" key="sysFollowConfig.unreadFollowDay"/>
						</td><td width="85%">
							<xform:text property="unReadFollowDay" style="width:85%" validators="noSpecialChar"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-follow" key="sysFollowConfig.isSendUndoNotify"/>
						</td><td width="85%">
							<c:forEach items="${notifyTypeArr}" var="notifyType">
								<input name="notifyType" type=checkbox value="${notifyType}"/>
								<bean:message bundle="sys-notify" key="sysNotify.type.${notifyType}" />&nbsp;
								<bean:message bundle="sys-follow" key="sysFollow.notigyType.tip" />
							</c:forEach>
							<html:hidden property="defaultNotifyType" />
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<c:if test="${sysFollowConfigForm.method_GET=='edit'}">
			<kmss:authShow roles="ROLE_SYSFOLLOW_MANAGER">
				<center style="margin:10px 0;">
					<!-- 保存 -->
					<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="setNotifyType();Com_Submit(document.sysFollowConfigForm, 'update');"></ui:button>
				</center>
			</kmss:authShow>
			</c:if>
		</html:form>
		
	 	<script type="text/javascript">
			$KMSSValidation();
			var validations = {
					'noSpecialChar':
					{
						error:"<span style='color:#cc0000;'>保留期限</span>&nbsp;必须为数字",
						test:function(v,e,o) {
							v = $.trim(v);
							var reg = /^\d+$/;
							if(reg.test(v)) {
								return true;
							}
							return false;
						}
					}
			}
			$KMSSValidation().addValidators(validations);
				
			function setNotifyType(){
				var defaultNotifyType = "";
				$("input[name='notifyType']").each(function(){
					if(this.checked){
						defaultNotifyType = defaultNotifyType + ";" + this.value;
					}
				})
				$("input[name=defaultNotifyType]").val(defaultNotifyType);
			}

			(function initNotifyType(){
				var defaultNotifyType = $("input[name=defaultNotifyType]").val();
				$("input[name='notifyType']").each(function(){
					if(~defaultNotifyType.indexOf(this.value)){
						this.checked = true;
					}
				});
			})();
		</script>
	</template:replace>
</template:include>
