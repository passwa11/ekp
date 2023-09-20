<%@ page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style>
.expand{
	display: none;
}
</style>
	<%
		String key = SysFileLocationUtil.getKey(null);
		boolean isClear = false;
		if ("server".equals(key)) {
			isClear = true;
		}
		request.setAttribute("isClear",isClear);
	%>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysAttMain.view.history.config" bundle="sys-attachment" /></p>
<center>
<table class="tb_normal" width=90%>
	<c:if test="${isClear == true}">
	<tr>
		<td class="td_normal_title" width="35%">${lfn:message('sys-attachment:sysAttMain.view.history.config.clear')}</td>
		<td>
			<ui:switch property="value(attClearEnable)" checked="${attClearEnable}" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
			<input type="text" style="display: none;" />
			<div>
				${lfn:message('sys-attachment:sysAttMain.view.history.config.clearCount')}：<xform:text property="value(attClearCount)" validators="required digits min(1)"></xform:text>
			</div>
			<div>
					${lfn:message('sys-attachment:sysAttMain.view.history.config.keepDays')}：<xform:text property="value(attKeepDays)" validators="required digits min(1)"></xform:text>
			</div>
			<br>
			<div>
					${lfn:message('sys-attachment:sysAttMain.view.history.config.movingPathTips')}：
			</div>
			<xform:radio property="value(rootType)" showStatus="edit" onValueChange="selectRootType(this.value);">
				<xform:simpleDataSource value="0">
					<bean:message key="sysAttMain.view.history.config.movingPath.default" bundle="sys-attachment" /><br>
				</xform:simpleDataSource>
				<xform:simpleDataSource value="1">
					<bean:message key="sysAttMain.view.history.config.movingPath.custom" bundle="sys-attachment" />
					<xform:text property="value(rootPath)" validators="maxLength(100)" style="display:none;"></xform:text>
				</xform:simpleDataSource>
			</xform:radio>
			<br>
			<br>
			<div>
					${lfn:message('sys-attachment:sysAttMain.view.history.config.clearDesc')}
			</div>
			<br>
			<div>
				${lfn:message('sys-attachment:sysAttMain.view.history.config.clearTips')}
			</div>
		</td>
	</tr>
	</c:if>
		<tr>
			<td class="td_normal_title" width="35%">${lfn:message('sys-attachment:sysAttMain.view.history.config.confirm')}</td>
			<td>
				<ui:switch property="value(attHistoryEnable)" checked="${attHistoryEnable}" onValueChange="config_clear();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>

				<div id='lab_historyVersion' style="display:none">
					<xform:checkbox  property="value(attHistoryConfigEnableModules)" showStatus="edit">
						<xform:customizeDataSource className="com.landray.kmss.sys.attachment.service.spring.SysAttMainHistoryDataSource"></xform:customizeDataSource>
					</xform:checkbox >
			    </div>
			</td>
		</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="commitMethod();" order="1" ></ui:button>
</div>
</center>
</div>
</html:form> 
<script>
Com_IncludeFile("jquery.js");
</script>
<script>
$KMSSValidation();

function validateAppConfigForm(thisObj){
	return true;
}
	
function commitMethod(value){
	var isClear = '${isClear}';
	if (isClear == 'true') {
		let rootType = $("input[name='value\\\(rootType\\\)']:checked").val().trim();
		let rootPath = $("input[name='value\\\(rootPath\\\)']").val().trim();
		if (rootType == '1' && (rootPath == null || rootPath == '')) {
			alert("${lfn:message('sys-attachment:sysAttMain.view.history.config.movingPath.customMsg')}");
			return;
		}
	}
	 Com_Submit(document.sysAppConfigForm, 'update');
}

function config_clear() {
	var isChecked = "true" == $("input[name='value\\\(attHistoryEnable\\\)']").val();
	if (isChecked) {
		$("#lab_historyVersion").show();
	} else {
		$("#lab_historyVersion").hide();
	}
}

LUI.ready(function() {	
	config_clear();
	var isClear = '${isClear}';
	if (isClear == 'true') {
		selectRootType($("input[name='value\\\(rootType\\\)']:checked").val().trim());
	}
});

function selectRootType(v){
	if (v == '1') {
		$("input[name='value\\\(rootPath\\\)']").show();
	}else{
		$("input[name='value\\\(rootPath\\\)']").hide();
	}
}
</script>
</template:replace>
</template:include>