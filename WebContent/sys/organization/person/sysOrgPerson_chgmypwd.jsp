<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super/> - <bean:message bundle="sys-organization" key="sysOrgPerson.button.changePassword"/>
	</template:replace>
	<template:replace name="content">
		<script src="${KMSS_Parameter_ContextPath}sys/organization/sys_org_person/pwdstrength.js"></script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/org.css"/>
<script>
Com_IncludeFile("security.js");
//var errLen = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure1" />';
//var errPwd = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure2" />';
var saveMyPwdSuccess = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.passwordUndercapacity" />';
var pwdStructure1 = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure1" />';
var pwdStructure2 = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure2" />';
var pwdInput = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdInput" />';
var newPwdCanNotSameOldPwd= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdCanNotSameOldPwd" />';
var newPwdCanNotSameLoginName= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdCanNotSameLoginName" />';
var newPwdCanNotSpaces= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPassword.space" />';
LUI.ready(function(){
	<% 
		String kmssOrgPasswordlength = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordlength();
		String kmssOrgPasswordstrength = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordstrength();
	%>
	var pwdlen = <%=StringUtil.isNull(kmssOrgPasswordlength) ? "1" : kmssOrgPasswordlength%>;
	var pwdsth = <%=StringUtil.isNull(kmssOrgPasswordstrength) ? "0" : kmssOrgPasswordstrength%>;
	var isAdmin = "<%=UserUtil.getKMSSUser().isAdmin()+""%>";
	var loginName = "<%=UserUtil.getUser().getFdLoginName()%>";
	var newPwdTextTip = "";
	if(isAdmin=="true"){
		// 管理员的密码需要加强处理，如果设置的强度大于管理员默认强度，则取更大的强度
		pwdlen = pwdlen < 8 ? 8 : pwdlen;
		pwdsth = pwdsth < 3 ? 3 : pwdsth;
	}
	window.setPwdTip = function(className,icon,text){
		$('.' + className + ' .icon span').removeClass().addClass(icon);
		$('.' + className + ' .textTip').html(text);
	};
	$('#oldPwd').focus(function(){
		setPwdTip('oldPwdTip','blueIcon',pwdInput);
	});
	$('#newPwd').focus(function(){
		var textTip = "";
		if(pwdsth >1){
			newPwdTextTip = pwdlen + pwdStructure2.replace("#len#", pwdsth);
		}else{
			newPwdTextTip = pwdlen + pwdStructure1;
		}
		setPwdTip('newPwdTip','blueIcon',newPwdTextTip);
	});
	$('#comparePwd').focus(function(){
		setPwdTip('comparePwdTip','blueIcon',pwdInput);
	});
	
	window.oldPwdBlur = function(){
		var value = $('#oldPwd').val();
		if(!value){
			setPwdTip('oldPwdTip','redIcon',pwdInput);
			return false;
		}
		setPwdTip('oldPwdTip','','');
		return true;
	};
	window.newPwdBlur = function(){
		var value = $('#newPwd').val();
		if(!value){
			setPwdTip('newPwdTip','redIcon',pwdInput);
			return false;
		}
		// 判断密码是否包含空格
		if (value.split(" ").length > 1){
			setPwdTip('newPwdTip','redIcon',newPwdCanNotSpaces);
			return false;
		}
		if(value.length<pwdlen){
			setPwdTip('newPwdTip','redIcon',newPwdTextTip);
			return false;
		}
		if(pwdsth>0){
			if(pwdstrength(value) < pwdsth){
				setPwdTip('newPwdTip','redIcon',newPwdTextTip);
				return false;
			}
		}
		var oldPwdValue = $('#oldPwd').val();
		if (oldPwdValue && (oldPwdValue==value)){
			setPwdTip('newPwdTip','redIcon',newPwdCanNotSameOldPwd);
			return false;
		}
		if ($.trim(loginName).toLowerCase() == $.trim(value).toLowerCase() ){
			setPwdTip('newPwdTip','redIcon',newPwdCanNotSameLoginName);
			return false;
		}
		
		setPwdTip('newPwdTip','greenIcon',"");
		return true;
	};
	
	window.newPwdKeyUp = function(){
		var value = $('#newPwd').val();
		if(value.length == 0){
			$(".psw_intensity ul").removeClass();
			return ;
		}
		var className = "";
		if(value.length < pwdlen || pwdstrength(value) ==1){
			className="weak";
		}
		if(value.length == pwdlen && pwdstrength(value) >=2){
			className="mid_weak";
		}
		if(value.length > pwdlen && pwdstrength(value) >=2){
			className="strong";
		}
		$(".psw_intensity ul").removeClass().addClass(className);
	};
	
	window.comparePwdBlur = function(){
		var newPwd = $('#newPwd').val();
		var comparePwd = $('#comparePwd').val();
		if(!comparePwd){
			setPwdTip('comparePwdTip','redIcon',pwdInput);
			return false;
		}
		if(newPwd !=comparePwd){
			setPwdTip('comparePwdTip','redIcon',"<bean:message bundle='sys-organization' key='sysOrgPerson.error.comparePwd' />");
			return false;
		}
		setPwdTip('comparePwdTip','greenIcon',"");
		return true;
	};
	
	$('#oldPwd').blur(oldPwdBlur);
	$('#newPwd').blur(newPwdBlur);
	$('#newPwd').keyup(newPwdKeyUp);
	$('#comparePwd').blur(comparePwdBlur);
});

function checkInput(){
	var ret1 = oldPwdBlur();
	var ret2 = newPwdBlur();
	var ret3 = comparePwdBlur();
	if(!ret1 || !ret2 || !ret3){
		return false;
	}
	$("#_oldPassword").val(document.getElementsByName("fdOldPassword")[0].value);
	$("#_newPassword").val(document.getElementsByName("fdNewPassword")[0].value);
	$("#_comparePassword").val(document.getElementsByName("fdConfirmPassword")[0].value);
	
	document.getElementsByName("fdOldPassword")[0].value = desEncrypt(document.getElementsByName("fdOldPassword")[0].value);
	document.getElementsByName("fdNewPassword")[0].value = desEncrypt(document.getElementsByName("fdNewPassword")[0].value);
	document.getElementsByName("fdConfirmPassword")[0].value = desEncrypt(document.getElementsByName("fdConfirmPassword")[0].value);
	ajaxUpdate();
	return false;
}
function ajaxUpdate() {
	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		var loading = dialog.loading('', $('#passwordForm'));
		var data = $("#passwordForm").serialize();
		$.ajax({
			type : "POST",
			url : $("#passwordForm").attr('action'),
			data : data,
			dataType : 'json',
			success : function(result) {
			     loading.hide();
			     dialog["success"]("${lfn:message('return.optSuccess')}",  $('body'));
			     document.getElementById("passwordForm").reset();
			},
			error : function(result) {
				loading.hide();
				if (result.responseJSON) {
					var pwdErrorType = result.responseJSON.msg;
					if("1"==pwdErrorType){
						setPwdTip('oldPwdTip','redIcon',pwdInput);
					}else if("2"==pwdErrorType){
						setPwdTip('oldPwdTip','redIcon',"<bean:message bundle='sys-organization' key='sysOrgPerson.error.curPwd' />");
					}else if("3"==pwdErrorType){
						setPwdTip('newPwdTip','redIcon',"<bean:message bundle='sys-organization' key='sysOrgPerson.error.same' />");
					}else if("4"==pwdErrorType){
						setPwdTip('newPwdTip','redIcon',newPwdCanNotSameLoginName);
					}else if("5"==pwdErrorType){
						setPwdTip('newPwdTip','redIcon',newPwdCanNotSameOldPwd);
					}else{
						newPwdBlur();
					}
				}
				document.getElementsByName("fdOldPassword")[0].value = $("#_oldPassword").val();
				document.getElementsByName("fdNewPassword")[0].value = $("#_newPassword").val();
				document.getElementsByName("fdConfirmPassword")[0].value = $("#_comparePassword").val();
			}
		});
	});
}
</script>
<div id="_container">
	<input type="hidden" id="_oldPassword" value=""/>
	<input type="hidden" id="_newPassword" value=""/>
	<input type="hidden" id="_comparePassword" value=""/>
<html:form styleId="passwordForm" action="/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" onsubmit="return checkInput();">
<ui:panel layout="sys.ui.panel.light" toggle="false" scroll="false">
<ui:content title="${lfn:message('sys-organization:sysOrgPerson.button.changePassword') }">
<%
	if(request.getParameter("login") != null && request.getParameter("login").equals("yes")){
		String redto = request.getParameter("redto");
		redto = StringEscapeUtils.escapeHtml(redto);
		redto = redto.replaceAll("&amp;", "&");
		%>
		<br><center><span style="color: red;">
		<% if (request.getSession().getAttribute("compulsoryChangePassword") != null && (Boolean)request.getSession().getAttribute("compulsoryChangePassword")) { %>
		<bean:message bundle="sys-organization" key="sysOrgPerson.compulsoryChangePassword"/>
		<% } else { %>
		<bean:message bundle="sys-organization" key="sysOrgPerson.passwordTooSimple"/>
		<% } %>
		<input type="hidden" name="redto" value="<%=redto%>" />
		 </span></center><br>
		<%
	}
%>
<table class="tb_simple" style="width: 100%;">
	<tr>
		<td class="td_normal_title width200">
			<bean:message bundle="sys-organization" key="sysOrgPerson.oldPassword"/>
		</td><td width="10%">
			<input type="password" id="oldPwd" name="fdOldPassword" style="width:196px;"  class="inputsgl"/>
		</td>
		<td>
			<div class="pwdTip oldPwdTip">
				<p class="icon"><span></span></p>
				<p class="textTip"></p>
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/>
		</td><td>
			<input type="password" id="newPwd" name="fdNewPassword" style="width:196px;"  class="inputsgl"/>
		</td>
		<td rowspan="2" style="vertical-align: top;padding-top:10px;">
			<div class="pwdTip newPwdTip" >
				<p class="icon"><span></span></p>
				<p class="textTip"></p>
			</div>
		</td>
	</tr>
	<tr style="height: 37px;">
		<td class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.pwdIntensity"/>
		</td><td>
			<div class="psw_intensity">
				<ul class="intensity">
                 <li class="unit first"></li>
                 <li class="unit second"></li>
                 <li class="unit third"></li>
            </ul>
			</div>
			
		</td>
	</tr>
	<tr>
		<td class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.confirmPassword"/>
		</td><td>
			<input type="password" id="comparePwd" name="fdConfirmPassword" style="width:196px;" class="inputsgl"/>
		</td>
		<td>
			<div class="pwdTip comparePwdTip">
				<p class="icon"><span></span></p>
				<p class="textTip"></p>
			</div>
		</td>
	</tr>
</table>
<!-- <input type="hidden" name="redto" value="/sys/person/setting.do?setting=sys_organization_chg_my_pwd" /> -->
<input type="submit" style="width: 0px;height: 0px;border: none;padding:0px;">
<html:hidden property="fdId"/>
	<div style="margin:20px 0px 20px 280px;">
		<ui:button onclick="if(checkInput())document.forms[0].submit();" title="${lfn:message('button.submit') }" text="${lfn:message('button.submit') }" />
	</div>
				
      </ui:content>
 </ui:panel>
 
</html:form>
</div>
	</template:replace>
</template:include>