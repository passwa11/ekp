<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.profile.util.SysProfileI18nConfigUtil"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="auto">
	<template:replace name="head">
		<template:super/>
		<script>
		Com_IncludeFile("optbar.js", null, "js");
		seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			resetI18n = function() {
				var urlPrefix = $("input[name=urlPrefix]:checked").val();
				if(!urlPrefix || urlPrefix.length < 1) {
					dialog.alert("${ lfn:message('sys-profile:sys.profile.i18n.tools3.info1') }");
					return;
				}
				dialog.confirm("${ lfn:message('sys-profile:sys.profile.i18n.tools3.info2') }", function(value) {
					if(value == true) {
						window.del_load = dialog.loading();
						$.ajax({
							url: '<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=resetI18n"/>',
							type: 'POST',
							dataType: 'text',
							data:$.param({"urlPrefix": urlPrefix}, true),
							success: function(data) {
								if(window.del_load != null){
									window.del_load.hide(); 
								}
								if(data == 'true') {
									dialog.success("${ lfn:message('return.optSuccess') }");
								} else {
									dialog.failure("${ lfn:message('return.optFailure') }");
								}
							}
					   });
					}
				});
			}
		});
		</script>
	</template:replace>
	<template:replace name="body"> 
		<div style="padding: 10px">
			<!-- 操作栏 -->
			<div id="optBarDiv">
				<input type=button value="<bean:message key="button.ok"/>" onclick="resetI18n();">
				<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
			</div>
			<div>
				<h3>${ lfn:message('sys-profile:sys.profile.i18n.reset.info') }</h3>
			</div>
			<!-- 内容 -->
			<c:import url="/sys/profile/i18n/module_list.jsp?isReset=${param.isReset}" charEncoding="UTF-8"></c:import>
		</div>
	</template:replace>
</template:include>