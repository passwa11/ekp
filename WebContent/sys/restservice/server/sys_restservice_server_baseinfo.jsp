<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message key="module.sys.restservice.base" bundle="sys-restservice-server"/></template:replace>
	<template:replace name="head">
		<script type="text/javascript"
			src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
	Com_IncludeFile(
			"validator.jsp|validation.js|plugin.js|validation.jsp|xform.js",
			null, "js");
</script>
		<style type="text/css">
.tb_normal td {
	padding: 5px; //
	border: 1px #d2d2d2 solid; //
	word-break: break-all;
}
</style>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0"><span
			style="color: #35a1d0;"><bean:message key="module.sys.restservice.base" bundle="sys-restservice-server"/></span></h2>

		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateSubmitForm();">
			<center>
			<table class="tb_normal" width=95%>
				<tr>
		<td class="td_normal_title" width="26%"><bean:message key="sysRestService.log.backupDay" bundle="sys-restservice-server"/></td>
		<td>
			<xform:text property="value(daysOfBackupLog)" subject="${ lfn:message('sys-restservice-server:sysRestService.log.backupDay') }" required="true" style="width:150px" showStatus="edit"/><bean:message key="sysRestService.log.day" bundle="sys-restservice-server"/><br>
			<span class="message"><bean:message key="sysRestService.log.backupDay.description" bundle="sys-restservice-server"/></span>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width="26%"><bean:message key="sysRestService.log.cleanBackupDay" bundle="sys-restservice-server"/></td>
		<td>
			<xform:text property="value(daysOfClearLog)" subject="${ lfn:message('sys-restservice-server:sysRestService.log.cleanBackupDay') }" required="true" style="width:150px" showStatus="edit"/><bean:message key="sysRestService.log.day" bundle="sys-restservice-server"/><br>
			<span class="message"><bean:message key="sysRestService.log.cleanBackupDay.description" bundle="sys-restservice-server"/></span>
		</td>
	</tr>		
			</table>
			

			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName"
				value="com.landray.kmss.sys.restservice.server.model.SysRestServiceBaseInfo" />

			<center style="margin-top: 10px;"><!-- 保存 --> <ui:button
				text="${lfn:message('button.save')}" height="35" width="120"
				onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
	$KMSSValidation();
	function validateSubmitForm(){ 
        
    	var daysOfBackupLog = document.getElementsByName("value(daysOfBackupLog)")[0].value;
    	if(daysOfBackupLog == ""){
    		alert("<bean:message key='sysRestService.log.notNullMsg' bundle='sys-restservice-server'/>");
    		return false;    	
    	} else if(!isInteger(daysOfBackupLog)){		 		
    		alert("<bean:message key='sysRestService.log.mustNumMsg' bundle='sys-restservice-server'/>");
    		return false;
    	}  
    	
    	var daysOfClearLog = document.getElementsByName("value(daysOfClearLog)")[0].value;
    	if(daysOfClearLog == ""){
    		alert("<bean:message key='sysRestService.log.cleanNotNullMsg' bundle='sys-restservice-server'/>");
    		return false;  
    	} else if(!isInteger(daysOfClearLog)){
    		alert("<bean:message key='sysRestService.log.cleanMustNumMsg' bundle='sys-restservice-server'/>");
    		return false;
    	}  

    	return true;
    }

    function isInteger(str) {
		var reg = /^-?([1-9]\d*|0)$/;  
		
		return reg.test(str);
    }    
</script>
	</template:replace>
</template:include>
