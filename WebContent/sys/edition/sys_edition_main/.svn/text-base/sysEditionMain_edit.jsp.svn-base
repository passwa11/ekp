<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<center>
	<table class="tb_normal" style="margin: 10px;width:95%">
		<tr>
			<td valign="center" width="40%" class="td_normal_title">
				<bean:message key="sysEditionMain.fdNewVersion" bundle="sys-edition" />
			</td>
			<td valign="center" width="60%">
				<table width="100" class="tb_noborder">
					<tr>
						<td>
							<INPUT type="radio" name="fdNewVersion" value="${HtmlParam['max']}" checked>
							${HtmlParam.max}
							<input type="radio" name="fdNewVersion" value="${HtmlParam['min']}">
							${HtmlParam.min}
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="center" colspan="2">
				<table cellspacing="17" cellpadding="0" class="tb_noborder"  style="width:100%">
					<tr>
						<td valign="center" >
							<div style="color:red;margin:5px;"><bean:message key="sysEditionMain.Note" bundle="sys-edition" /></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<br>
	<table class="tb_optbar">
		<tr>
			<td>
				<ui:button text="${ lfn:message('button.ok') }"   onclick="commitForm();">
				</ui:button>
				<ui:button text="${ lfn:message('button.cancel') }"  styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
				</ui:button>
			</td>
		</tr>
	</table>
</center>
<script type="text/javascript">
	Com_Parameter.CloseInfo = null;
	function commitForm(){
		var version = document.getElementsByName("fdNewVersion");
		for(var i = 0; i < version.length; i++){
			if(version[i].checked){
				top.returnValue=version[i].value;
				break;
			}
		}
		Com_CloseWindow();
	}
</script>
	</template:replace>
</template:include>
