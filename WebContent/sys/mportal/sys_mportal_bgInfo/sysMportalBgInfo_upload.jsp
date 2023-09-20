<%@page import="com.landray.kmss.sys.ui.plugin.SysUiTools"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="title">上传背景图</template:replace>

	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"
			var-navwidth="95%">
			<ui:button text="上传" onclick="submit()"></ui:button>
		</ui:toolbar>

		<style>
.msg {
	color: #808080;
	padding-top: 10px;
}

.container {
	padding-top: 50px;
	text-align: left;
	margin: 0 auto;
	width: 280px;
}
</style>
		<script>
			function submit() {
				var file = $("input[name=file]").val();
				if(file && file.length > 0)
					document.getElementsByName('sysUiLogoForm')[0].submit();
				else
					alert("请选择要上传的文件。");
			}
		</script>
	</template:replace>
	<template:replace name="content">
		<div class="container">
			<html:form enctype="multipart/form-data"
				action="/sys/mportal/sys_mportal_bgInfo/sysMportalBgInfo.do"
				method="post">
				<div>
					<input type="file" name="file" />
				</div>
				<input type="hidden" name="method" value="upload" />
			</html:form>
		</div>
	</template:replace>
</template:include>