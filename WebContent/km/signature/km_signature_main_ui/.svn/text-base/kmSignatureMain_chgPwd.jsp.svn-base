<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/km/signature/resource/css/signature.css"/>
	<script>
		Com_IncludeFile("security.js");
		var pwdInput = '<bean:message bundle="km-signature" key="signature.warn16" />';
		var confirmInput='<bean:message bundle="km-signature" key="signature.warn17" />';
		var confirmErrorInput='<bean:message bundle="km-signature" key="signature.warn5" />';
		
		LUI.ready(function(){
			window.setPwdTip = function(className,icon,text){
				$('.' + className + ' .icon span').removeClass().addClass(icon);
				$('.' + className + ' .textTip').html(text);
			};
			$('#fdNewPassword').focus(function(){
				setPwdTip('newPwdTip','blueIcon',pwdInput);
			});
			$('#fdConfirmPassword').focus(function(){
				setPwdTip('comparePwdTip','blueIcon',confirmInput);
			});

			window.newPwdBlur = function(){
				var value = $('#fdNewPassword').val();
				if(value){
					setPwdTip('newPwdTip','greenIcon','');
					return true;
				}
				return false;
			};
			window.confirmBlur = function(){
				var confirmPwd = $('#fdConfirmPassword').val();
				var newPwd = $('#fdNewPassword').val();
				if ((confirmPwd != null) && (confirmPwd != "")){
					setPwdTip('comparePwdTip','greenIcon',"");
				}
				if ((newPwd ==null) || (newPwd=="")){
					setPwdTip('newPwdTip','blueIcon',pwdInput);
					return false;
				}				
				if (confirmPwd != newPwd){
					setPwdTip('comparePwdTip','redIcon',confirmErrorInput);
					return false;
				}
				setPwdTip('comparePwdTip','greenIcon','');
				return true;
			};

			$('#fdNewPassword').blur(newPwdBlur);
			$('#fdConfirmPassword').blur(confirmBlur);
		});
		
		//更新密码
		window.saveKmSignatureChgPwd=function(formObj,method){
			var ret1 = newPwdBlur();
			var ret2 = confirmBlur();
			if(!ret1 || !ret2 ){
				return false;
			}	
			// 加密传输密码
			$("#_fdNewPassword").val(document.getElementsByName("fdNewPassword")[0].value);
  			$("#_fdConfirmPassword").val(document.getElementsByName("fdConfirmPassword")[0].value);
  			document.getElementsByName("fdNewPassword")[0].value = desEncrypt(document.getElementsByName("fdNewPassword")[0].value);
  			document.getElementsByName("fdConfirmPassword")[0].value = desEncrypt(document.getElementsByName("fdConfirmPassword")[0].value);
  			  
			Com_Submit(formObj, method);
		};
		
		</script>
		<html:form action="/km/signature/km_signature_main/kmSignatureMain.do" >
			<input type="hidden" name="fdId" value="${JsParam.fdId}" />
			<input type="hidden" id="_fdNewPassword" value=""/>
 		    <input type="hidden" id="_fdConfirmPassword" value=""/>
			<center>
			<br/>
			<table class="tb_simple" style="margin-left: 140px;">
				<tr>
					<td class="td_normal_title width100" >
						<bean:message bundle="km-signature" key="signature.newPassword"/>
					</td>
					<td class="width200">
						<input type="password" id="fdNewPassword" name="fdNewPassword" size="50" maxlength="32"  required="required" style="width:196px" class="inputsgl" />
					</td>
					<td class="width250" >
						<div class="pwdTip newPwdTip">
							<p class="icon"><span></span></p>
							<p class="textTip"></p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title width100" >
						<bean:message bundle="km-signature" key="signature.confirmPassword"/>
					</td>
					<td class="width200" >
						<input type="password" id="fdConfirmPassword" name="fdConfirmPassword" size="50" maxlength="32"  required="required" style="width:196px" class="inputsgl">
					</td>
					<td class="width250" >
						<div class="pwdTip comparePwdTip">
							<p class="icon"><span></span></p>
							<p class="textTip"></p>
						</div>
					</td>
				</tr>
			</table>
			<div style="text-align: center;padding-top: 10px;">
   				<ui:button  text="${lfn:message('button.save')}"  onclick="saveKmSignatureChgPwd(document.kmSignatureMainForm, 'savePwd');" style="width:70px;"/>
   				
   				<ui:button  text="${lfn:message('button.cancel') }" onclick="window.$dialog.hide(null);" styleClass="lui_toolbar_btn_gray" style="width:70px;"/>
						
   			</div>
			</center>
			
		</html:form>
	</template:replace>
</template:include>