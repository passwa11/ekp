<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<center>
	<table class="tb_normal" width="100%">
	   <tr>
			<td valign="center" colspan="2">
				<table cellspacing="17" cellpadding="0" class="tb_noborder">
					<tr>
						<td valign="center" width="1500px;" style="text-align:center;">
							<br>
							<span class="txtstrong" id="txtstrong"><bean:message key="kmsMedalMain.haveHonoured" bundle="kms-medal" /></span>
							<br>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="center" width="113px;" class="td_normal_title">
				<bean:message key="kmsMedal.send.honoursPersonnel" bundle="kms-medal" />
			    
			</td>
			<td valign="center" width="227px;">
				<table width="100%" class="tb_noborder" id="checkbox">
					<tr>
						<td>
							<kmss:editNotifyType property="fdNotifyType" required="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
	</table>
	<%-- <br>
	<table class="tb_optbar">
		<tr>
			<td>
				<ui:button text="${ lfn:message('button.ok') }"   onclick="commitForm();">
				</ui:button>
				<ui:button text="${ lfn:message('button.cancel') }"  styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();">
				</ui:button>
			</td>
		</tr>
	</table> --%>
</center>
<script type="text/javascript">

	/* seajs.use(["lui/dialog", "lui/jquery", "lui/topic"], function(dialog, $, topic) {
	         $("input[type=checkbox]").click(function(){

	        	var checked=$(this).is(":checked");
	        	if(checked==true){
	        		$(this).attr("checked","checked");
	        	}else{
	        		$(this).removeAttr("checked");
	        	}
	         });
	}); */

/* 	Com_Parameter.CloseInfo = null;
	function commitForm(){
		var version = document.getElementsByName("fdNewVersion");
		for(var i = 0; i < version.length; i++){
			if(version[i].checked){
				top.returnValue=version[i].value;
				break;
			}
		}
		Com_CloseWindow();
	} */
</script>
	</template:replace>
</template:include>
