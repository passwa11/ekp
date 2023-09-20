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
			clearCache = function() {
				var urlPrefix = $("input[name=urlPrefix]:checked").val();
				dialog.confirm("${ lfn:message('sys-profile:sys.profile.i18n.tools1.info1') }", function(value) {
					if(value == true) {
						window.del_load = dialog.loading();
						$.ajax({
							url: '<c:url value="/sys/profile/i18n/sysProfileI18nConfig.do?method=clearCache"/>',
							type: 'POST',
							data:$.param({"urlPrefix": urlPrefix}, true),
							dataType: 'text',
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
				<input type=button value="<bean:message key="button.ok"/>" onclick="clearCache();">
				<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
			</div>
			<!-- 内容 -->
			<c:import url="/sys/profile/i18n/module_list.jsp" charEncoding="UTF-8"></c:import>
		</div>
	</template:replace>
</template:include>