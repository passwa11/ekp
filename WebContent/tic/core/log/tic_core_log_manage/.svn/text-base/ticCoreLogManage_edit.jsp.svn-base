<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-organization" key="sysOrgConfig" /></template:replace>
	<template:replace name="head">
		<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/common.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="tic-core-log" key="table.ticCoreLogManage"/></span>
		</h2>
		
		<html:form action="/tic/core/log/tic_core_log_manage/ticCoreLogManage.do">
			<center>
				<table class="tb_normal" width=95% >
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogTime"/>
		</td><td colspan="3" width="85%">
			<xform:text property="fdLogTime" onValueChange="fdLogTimeChecked();"/>
			<bean:message bundle="tic-core-log" key="ticCoreLogManage.day"/>
			<!-- 操作日志保存时间  -->
			<jsp:include page="../../resource/jsp/checkedValue.jsp">
				<jsp:param value="fdLogTimeHiddenId" name="hiddenId"/>
				<jsp:param value="fdLogTimeTextId" name="textId"/>
			</jsp:include>
		</td>
	</tr>
	
	<tr style="display:none;">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogLastTime"/>
		</td><td colspan="3" width="85%">
			<xform:text property="fdLogLastTime"  onValueChange="fdLogLastTimeChecked();"/>
			<bean:message bundle="tic-core-log" key="ticCoreLogManage.day"/>
			<!-- 日志归档时间  -->
			<jsp:include page="../../resource/jsp/checkedValue.jsp">
				<jsp:param value="fdLogLastTimeHiddenId" name="hiddenId"/>
				<jsp:param value="fdLogLastTimeTextId" name="textId"/>
			</jsp:include>
		</td>
	</tr>
<%-- 	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogType"/>
		</td><td colspan="3" width="85%">
			<xform:radio property="fdLogType">
				<xform:enumsDataSource enumsType="fd_log_type"/>
			</xform:radio>
		</td>
	</tr> --%>
</table>
			</center>
			<html:hidden property="method_GET" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="waitSubmit('update');"></ui:button>
			</center>
			
			<input type="hidden" name="fdId" value="${ticCoreLogManageForm.fdId}" />
		</html:form>
		

	 	<script>
	Com_IncludeFile("calendar.js");
	$KMSSValidation();
	function waitSubmit(method) {
		if (fdLogTimeChecked() && fdLogLastTimeChecked()){
			Com_Submit(document.ticCoreLogManageForm, method);
		}
	}
	
	var reg = /^[0-9]+$/;
	function fdLogTimeChecked() {
		var fdLogTime= document.getElementsByName("fdLogTime")[0].value;//document.getElementById("fdLogTime").value;
		if(!reg.test(fdLogTime)) {
			document.getElementById("fdLogTimeHiddenId").style.display = "block";
			document.getElementById("fdLogTimeTextId").innerHTML = "<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogTimeTextId"/>";
			return false;
		} else {
			document.getElementById("fdLogTimeHiddenId").style.display = "none";
			return true;
		}
	}
	
	function fdLogLastTimeChecked() {
		var fdLogLastTime= document.getElementsByName("fdLogLastTime")[0].value;//document.getElementById("fdLogLastTime").value;
		if(!reg.test(fdLogLastTime)) {
			document.getElementById("fdLogLastTimeHiddenId").style.display = "block";
			document.getElementById("fdLogLastTimeTextId").innerHTML = "<bean:message bundle="tic-core-log" key="ticCoreLogManage.fdLogLastTimeTextId"/>";
			return false;
		} else {
			document.getElementById("fdLogLastTimeHiddenId").style.display = "none";
			return true;
		}
	}

	seajs.use(['lui/dialog'],function(dialog) {
		window.onload = function(){
			document.getElementsByTagName('body')[0].style.height = '300px';
			if(${message eq 'success'})
			     dialog.alert('保存成功');
		}
	});
</script>
	</template:replace>
</template:include>

