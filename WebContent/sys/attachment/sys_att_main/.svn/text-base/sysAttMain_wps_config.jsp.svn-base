<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style>
.expand{
	display: none;
}
</style>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysAttachment.config" bundle="sys-attachment" /></p>
<center>
		<table class="tb_normal" width=80%>
					<%-- <tr >
						<td class="td_normal_title" width=40%>WPS客户端心跳时间</td>
						<td>
						<html:text property="value(wpsHeartTime)" style="width:90%"/>秒
						</td>
					</tr> --%>
					<tr>
						<td class="td_normal_title" width=15%><bean:message key="sysAttachment.config" bundle="sys-attachment" /></td>
						<td>
							<ui:switch property="value(thirdWpsWebOfficeEnabled)"  enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" disabledText="${lfn:message('sys-ui:ui.switch.disabled')}" >
							</ui:switch>
						</td>
					</tr>
					<tr >
						<td class="td_normal_title" width=30%><bean:message key="sysAttachment.config.wpsDocumentLife" bundle="sys-attachment" /></td>
						<td>
							<html:text property="value(wpsDocumentLife)"  style="width:90%"/><bean:message key="sysAttachmentPlayLog.audio.unit" bundle="sys-attachment" />
							
							<div class="validation-advice" id="wpsDocumentLife_validate" _reminder="true" style="display:none">
							  <table class="validation-table">
							    <tbody>
								 <tr>
								  <td>
								   <div class="lui_icon_s lui_icon_s_icon_validator">
								   </div></td>
								  <td class="validation-advice-msg">
								   <span class="validation-advice-title"><bean:message key="sysAttachment.config.wpsDocumentLife" bundle="sys-attachment" /></span> <bean:message key="sysAttachment.config.digital" bundle="sys-attachment" /></td>
								   </tr></tbody></table>
							</div>
							<div>
								<font size="0.5" color="#C0C0C0">
									<span>
									<bean:message key="sysAttachment.config.file.status.tip" bundle="sys-attachment" />
									</span>
								</font>
							</div>
						</td>
					</tr>
					<tr >
						<td class="td_normal_title" width=30%><bean:message key="sysAttachment.config.wpsWebDocumentHeart" bundle="sys-attachment" /></td>
						<td>
							<html:text property="value(wpsWebDocumentHeart)" style="width:90%"/><bean:message key="sysAttachmentPlayLog.audio.unit" bundle="sys-attachment" />
							<div class="validation-advice" id="wpsWebDocumentHeart_validate" _reminder="true" style="display:none">
							  <table class="validation-table">
							    <tbody>
								 <tr>
								  <td>
								   <div class="lui_icon_s lui_icon_s_icon_validator">
								   </div></td>
								  <td class="validation-advice-msg">
								   <span class="validation-advice-title"><bean:message key="sysAttachment.config.wpsWebDocumentHeart" bundle="sys-attachment" /></span> <bean:message key="sysAttachment.config.digital" bundle="sys-attachment" /></td>
								   </tr></tbody></table>
							</div>
							
							<div class="validation-advice" id="wpsWebDocumentHeart_less_validate" _reminder="true" style="display:none">
							  <table class="validation-table">
							    <tbody>
								 <tr>
								  <td>
								   <div class="lui_icon_s lui_icon_s_icon_validator">
								   </div></td>
								  <td class="validation-advice-msg"><bean:message key="sysAttachment.config.wpsWebDocumentHeart" bundle="sys-attachment" /><bean:message key="sysAttachment.config.less.than" bundle="sys-attachment" /><bean:message key="sysAttachment.config.wpsDocumentLife" bundle="sys-attachment" /></td>
								   </tr></tbody></table>
							</div>
							
							<div>
								<font size="0.5" color="#C0C0C0">
									<span>
									<bean:message key="sysAttachment.config.file.heart.tip" bundle="sys-attachment" />
									</span>
								</font>
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
	document.getElementById("wpsDocumentLife_validate").style.display = 'none';
	document.getElementById("wpsWebDocumentHeart_validate").style.display = 'none';
	document.getElementById("wpsWebDocumentHeart_less_validate").style.display = 'none';
	var wpsDocumentLife = $("input[name='value\\\(wpsDocumentLife\\\)']").val();
	var wpsWebDocumentHeart = $("input[name='value\\\(wpsWebDocumentHeart\\\)']").val();
	if(!isDigital(wpsDocumentLife) && !isDigital(wpsWebDocumentHeart))
	{
		document.getElementById("wpsDocumentLife_validate").style.display = 'block';
		document.getElementById("wpsWebDocumentHeart_validate").style.display = 'block';
		return;
	}
	if(!isDigital(wpsDocumentLife))
	{
		document.getElementById("wpsDocumentLife_validate").style.display = 'block';
		return;
	}
	
	if(!isDigital(wpsWebDocumentHeart))
	{
		document.getElementById("wpsWebDocumentHeart_validate").style.display = 'block';
		return;
	}
	
	if(parseInt(wpsWebDocumentHeart) >= parseInt(wpsDocumentLife))
	{
		document.getElementById("wpsWebDocumentHeart_less_validate").style.display = 'block';
		return;
	}

	 Com_Submit(document.sysAppConfigForm, 'update');
	 
	 var thirdWpsWebOfficeEnabled = $("input[name='value\\\(thirdWpsWebOfficeEnabled\\\)']").val();
		
	<%-- 获取DNS --%>
	var url = location.href;
	var i = url.indexOf("sys/appconfig");
	var cookieAdmain = url.substring(0, i-1);
	 $.ajax({
			url: Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttConfig.do?method=handleWpsOfficeToken",
			data: {'cookieAdmain': cookieAdmain, 'thirdWpsWebOfficeEnabled': thirdWpsWebOfficeEnabled},
			dataType: 'json',
			type: 'POST',
			success: function(data) {
			},
			error: function(req) {
			}
		});
}

function isDigital(value)
{
	var reg = /(^[1-9]\d*$)/
    if(reg.test(value)) return true;
	return false;
}



LUI.ready(function() {	
	document.getElementById("wpsDocumentLife_validate").style.display = 'none';
	document.getElementById("wpsWebDocumentHeart_validate").style.display = 'none';
	document.getElementById("wpsWebDocumentHeart_less_validate").style.display = 'none';
});
</script>
</template:replace>
</template:include>