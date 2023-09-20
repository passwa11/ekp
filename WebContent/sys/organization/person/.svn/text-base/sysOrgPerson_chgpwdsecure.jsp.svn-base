<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super/> - <bean:message bundle="sys-organization" key="sysOrgPerson.button.passwordSecure"/>
	</template:replace>
	<template:replace name="content">
		<%
			SysOrgPerson user = UserUtil.getUser();
			if(null==user.getFdPassword()||StringUtil.isNull(user.getFdPassword().trim())){
				request.setAttribute("d2e", "1");
			}
		%>
		<script src="${KMSS_Parameter_ContextPath}sys/organization/sys_org_person/pwdstrength.js"></script>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/org.css"/>
<script>
    seajs.use(['theme!module']);
	Com_IncludeFile("security.js");
	//var errLen = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdLength" />';
	//var errPwd = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStrong" />';
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
		var isAdmin = "<%=UserUtil.getKMSSUser().isAdmin() + ""%>";
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
			var d2e = "${d2e}";
			var value = $('#oldPwd').val();
			if(!value&&d2e!='1'){
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

		<c:if test="${needShowExpire==true}">
		//温馨提示：您的密码即将到期
		var pwdExpireDays = '<%=request.getSession().getAttribute("pwdExpireDays")%>';	
		var expireTips = '<bean:message bundle="sys-organization" key="sysOrgPerson.pwd.expire" />';
        if (pwdExpireDays && pwdExpireDays != 'null') {
        	var pwdExpire= LUI.$("#pwdExpire");
        	expireTips=expireTips.replace("{0}",pwdExpireDays);  
        	pwdExpire.before('<div class="lightRed"></div>').html(expireTips);
        }
        </c:if>
        
        seajs.use('lui/jquery',function($){
			$(".lui_person_per_tab .lui_person_per_header").click(function(){
				 if($(this).next().hasClass("lui_person_edit_content_hide")){
					 $(this).next().show();
					 $(this).next().removeClass("lui_person_edit_content_hide");
					 $(this).find(".arrow").html("<bean:message bundle='sys-organization' key='sysOrgPerson.pwd.secure.hide' />");
				 }else{
					 $(this).next().hide();
					 $(this).next().addClass("lui_person_edit_content_hide");
					 $(this).find(".arrow").html("<bean:message bundle='sys-organization' key='sysOrgPerson.pwd.secure.expand' />");
				 }
			 });
		});
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
			     $('.icon span').removeClass();
			     $('.psw_intensity ul').removeClass().addClass("intensity");
			     document.getElementById("passwordForm").reset();
			     dialog["success"]("${lfn:message('sys-organization:sysOrgPerson.pwd.secure.suc.tip')}",  $('#sysOrgPersonInfoForm'));
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
					}else if(pwdErrorType!=""){
						setPwdTip('newPwdTip','redIcon',pwdErrorType);
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
<ui:tabpanel layout="sys.ui.tabpanel.list">
<ui:content title="${lfn:message('sys-organization:sysOrgPerson.button.passwordSecure') }">
<c:if test="${authDisable==false}">
<div class="lui_person_per_right">
	<ul class="lui_person_per_tab">
		<li class="lui_person_per_header">
			<span class="title">${lfn:message('sys-organization:sysOrgPerson.button.changePassword')}</span>
			<div class="content-r lui_person_baseInfo_title">
				<span id="pwdExpire" ></span>
			</div>
			<span  class="lui_person_base_up arrow">${lfn:message('sys-organization:sysOrgPerson.pwd.secure.expand') }</span>
		</li>
		<li class="lui_person_edit_content_show lui_person_edit_content_hide">
		    <input type="hidden" id="_oldPassword" value=""/>
			<input type="hidden" id="_newPassword" value=""/>
			<input type="hidden" id="_comparePassword" value=""/>
			<form name="sysOrgPersonInfoForm" method="post" autocomplete="off" 
				action="${LUI_ContextPath}/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" 
				onsubmit="return checkInput();" id="passwordForm" styleId="sysOrgPersonInfoForm">
				<input type="password" style="display: none;">
				<xform:text property="fdId" showStatus="noShow"/>
				<table class="tb_simple" style="width: 60%" >
					<tr>
						<td class="td_normal_title width200">
							<SPAN><bean:message bundle="sys-organization" key="sysOrgPerson.oldPassword"/></SPAN>
						</td>
						<td width="10%">
							<input type="password" id="oldPwd" name="fdOldPassword" style="width:196px;"  class="inputsgl" autocomplete="off" />
						</td>
						<td>
							<div class="pwdTip oldPwdTip">
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
							<bean:message bundle="sys-organization" key="sysOrgPerson.old.password.no"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title width200">
							<bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/>
						</td>
						<td>
							<input type="password" id="newPwd" name="fdNewPassword" style="width:196px;"  class="inputsgl" autocomplete="off" />
						</td>
						<td rowspan="2" style="vertical-align: top;padding-top:10px;">
							<div class="pwdTip newPwdTip" >
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
						</td>
					</tr>
					<tr style="height: 37px;">
						<td class="td_normal_title width200">
							<bean:message bundle="sys-organization" key="sysOrgPerson.pwdIntensity"/>
						</td>
						<td>
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
						<td class="td_normal_title width200">
							<bean:message bundle="sys-organization" key="sysOrgPerson.confirmPassword"/>
						</td>
						<td>
							<input type="password" id="comparePwd" name="fdConfirmPassword" style="width:196px;" class="inputsgl" autocomplete="off" />
						</td>
						<td>
							<div class="pwdTip comparePwdTip">
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
						</td>
					</tr>
					<tr>
					     <td colspan="3" style="padding-left: 270px;">
					     	 <ui:button onclick="if(checkInput())document.forms[0].submit();" title="${lfn:message('button.submit') }" text="${lfn:message('button.submit') }" />
					    </td>						
				    </tr>
				</table>
			</form>  
		</li>
	</ul>
</div>
</c:if>
<div class="pwdLoginHis">   
	<div><span class="pwd-score-title">${lfn:message('sys-organization:sysOrgPerson.login.log')}</span></div>
	<div>
	   <span class="pwd-score-titleinfo"><bean:message bundle="sys-organization" key="sysOrgPerson.login.log.msg" arg0="${logLoginCount}" /></span>
	</div>
 	<div>
	    <table class="tb_normal" width="100%">
			<tr class="tr_normal_title">
				<td width="15%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.time')}</td>
				<td width="13%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.ip')}</td>
				<td width="18%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.browser')}</td>
				<td width="12%" align="center">${lfn:message('sys-organization:sysOrgPerson.verification')}</td>
				<td width="21%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.equipment')}</td>
				<td width="21%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.location')}</td>
			</tr>
			<c:forEach items="${logLoginDatas}" var="login" varStatus="vstatus">
			<tr KMSS_IsContentRow="1"  >
				<td align="center">
					${login.fdCreateTime}
				</td>
				<td align="center">
					${login.fdIp}
				</td>
				<td align="center">
					${login.fdBrowser}
				</td>
				<td align="center">
				<sunbor:enumsShow
					value="${login.fdVerification}"
					enumsType="sys_log_login_verification" />
				</td>
				<td align="left">
					${login.fdEquipment}
				</td>
				<td align="left">
					${login.fdLocation}
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
</div>

<div class="pwdOpSecure">
	<div><span class="pwd-score-title">${lfn:message('sys-organization:sysOrgPerson.sensitive.operate.log')}</span></div>
	<div>
		<span class="pwd-score-titleinfo">${lfn:message('sys-organization:sysOrgPerson.sensitive.operate.log.msg')}</span>
	</div>
	<div>
	    <table class="tb_normal" width="100%">
			<tr class="tr_normal_title">
				<td width="15%" align="center">${lfn:message('sys-organization:sysOrgPerson.sensitive.operate.time')}</td>
				<td width="12%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.ip')}</td>
				<td width="17%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.browser')}</td>	
				<td width="16%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.equipment')}</td>
				<td width="26%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.location')}</td>
				<td width="14%" align="center">${lfn:message('sys-organization:sysOrgPerson.login.details')}</td>			
			</tr>
			<c:forEach items="${LogChangePwdDatas}" var="cp" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" >
				<td align="center">
					${cp.fdCreateTime}
				</td>
				<td align="center">
					${cp.fdIp}
				</td>
				<td align="center">
					${cp.fdBrowser}
				</td>
				<td align="left">
					${cp.fdEquipment}
				</td>
				<td align="left">
					${cp.fdLocation}
				</td>
				<td align="center">
					${cp.fdDetails}
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
</div>
</ui:content>
</ui:tabpanel>
	</template:replace>
</template:include>