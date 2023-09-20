<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*" %>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<script src="<%=request.getContextPath()%>/sys/config/resource/js/pwdstrength.js"></script> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/sys/config/resource/css/editPassWord.css"/>
<script>
	Com_IncludeFile("security.js");
	Com_IncludeFile("jquery.js");
	var saveMyPwdSuccess = '<bean:message bundle="sys-config" key="sysAdmin.error.passwordUndercapacity" />';
	var pwdStructure1 = '<bean:message bundle="sys-config" key="sysAdmin.error.pwdStructure1" />';
	var pwdStructure2 = '<bean:message bundle="sys-config" key="sysAdmin.error.pwdStructure2" />';
	var pwdInput = '<bean:message bundle="sys-config" key="sysAdmin.error.pwdInput" />';
	var codeInput = '<bean:message bundle="sys-config" key="sysAdmin.error.codeInput" />';
	var newPwdCanNotSameOldPwd= '<bean:message bundle="sys-config" key="sysAdmin.error.same" />';
	var newPwdCanNotSpaces= '<bean:message bundle="sys-config" key="sysAdmin.error.newPassword.space" />';
	
	$(document).ready(function(){

		var newPwdTextTip = "";
		var pwdlen = 8;
		var pwdsth = 3;
		
		window.setPwdTip = function(className,icon,text){
			$('.' + className + ' .icon span').removeClass().addClass(icon);
			$('.' + className + ' .textTip').html(text);
		};
		$('input[name=oldPassWord]').focus(function(){
			setPwdTip('oldPwdTip','blueIcon',pwdInput);
		});
		$('input[name=newPassWord]').focus(function(){
				newPwdTextTip = pwdlen + pwdStructure2.replace("#len#", pwdsth);
				setPwdTip('newPwdTip','blueIcon',newPwdTextTip);
		});
		$('input[name=confirmPassword]').focus(function(){
			setPwdTip('comparePwdTip','blueIcon',pwdInput);
		});
		$('input[name=validation_code]').focus(function(){
			setPwdTip('codeTip','blueIcon',codeInput);
		});
		window.oldPwdBlur = function(){
			var value = $('input[name=oldPassWord]').val();
			if(!value){
				setPwdTip('oldPwdTip','redIcon',pwdInput);
				return false;
			}
			setPwdTip('oldPwdTip','','');
			return true;
		};
		window.codeBlur = function(){
			var value = $('input[name=validation_code]').val();
			if(!value){
				setPwdTip('codeTip','redIcon',codeInput);
				return false;
			}
			setPwdTip('codeTip','','');
			return true;
		};
		
		window.newPwdBlur = function(){
			var value = $('input[name=newPassWord]').val();
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
			var oldPwdValue = $('input[name=oldPassWord]').val();
			if (oldPwdValue && (oldPwdValue==value)){
				setPwdTip('newPwdTip','redIcon',newPwdCanNotSameOldPwd);
				return false;
			}
			
			setPwdTip('newPwdTip','greenIcon',"");
			return true;
		};
		
		window.newPwdKeyUp = function(){
			var value = $('input[name=newPassWord]').val();
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
			var newPwd = $('input[name=newPassWord]').val();
			var comparePwd = $('input[name=confirmPassword]').val();
			if(!comparePwd){
				setPwdTip('comparePwdTip','redIcon',pwdInput);
				return false;
			}
			if(newPwd !=comparePwd){
				setPwdTip('comparePwdTip','redIcon',"<bean:message bundle='sys-config' key='sysAdmin.error.comparePwd' />");
				return false;
			}
			setPwdTip('comparePwdTip','greenIcon',"");
			return true;
		};
		
		$('input[name=oldPassWord]').blur(oldPwdBlur);
		$('input[name=newPassWord]').blur(newPwdBlur);
		$('input[name=newPassWord]').keyup(newPwdKeyUp);
		$('input[name=confirmPassword]').blur(comparePwdBlur);
		$('input[name=validation_code]').blur(codeBlur);
		
        
  });



	function ajaxUpdate() {
		
			var data = $("#passwordForm").serialize();
			var head=document.getElementById("url").value;
			var AjaxURL= head+"/admin.do?method=check"; 
			var flag=false;
			$.ajax({
				type : "POST",
				url : AjaxURL,
				async:false,
				data : data,
				dataType : 'json',
				success : function(result) {
				if("1"==result){
					setPwdTip('codeTip','redIcon',"<bean:message bundle='sys-config' key='sysAdmin.error.codeError' />");
					$("#editPassvcode").attr("src",'vcode.jsp?xx='+Math.random());
					return false;
				}else if("2"==result){
					$("#editPassvcode").attr("src",'vcode.jsp?xx='+Math.random());
					setPwdTip('oldPwdTip','redIcon',"<bean:message bundle='sys-config' key='sysAdmin.error.curPwd' />");
					return false;
				}else if("3"==result){
					document.forms[0].submit();
				}else if("4"==result){
					$("#editPassvcode").attr("src",'vcode.jsp?xx='+Math.random());
					setPwdTip('newPwdTip','redIcon',saveMyPwdSuccess);
					return false;
				}
				
			}
		
		});
	}

function check(){
	if(!oldPwdBlur() || !newPwdBlur()|| !comparePwdBlur()){
		return false;
	}
	 if ($('input[name=oldPassWord]').value==""){
		  setPwdTip('oldPwdTip','redIcon',pwdInput);
	        return false;
	    }
	    if ($('input[name=newPassWord]').value==""){
	    	setPwdTip('newPwdTip','redIcon',pwdInput);
	        return false;
	    }
	    if($("input[name='newPassWord']").val()==$("input[name='oldPassWord']").val()){
	    	  setPwdTip('newPwdTip','redIcon',"<bean:message bundle='sys-config' key='sysAdmin.error.same' />");
		      return false;
	      }
	    if ($('input[name=confirmPassword]').value==""){
	    	setPwdTip('comparePwdTip','redIcon',pwdInput);
	        return false;
	    }
	    if($('input[name=confirmPassword]').value != $('input[name=newPassWord]').value){
	    	setPwdTip('comparePwdTip','redIcon',"<bean:message bundle='sys-config' key='sysAdmin.error.comparePwd'/>");
	   	    return false;
	    }
	    if ($('input[name=validation_code]').value==""){
	    	setPwdTip('codeTip','redIcon',codeInput);
	        return false;
	      }
	    
	    // 加密处理
	    var __oldPassWord = document.forms[0].oldPassWord.value;
	    var __newPassWord = document.forms[0].newPassWord.value;
	    var __confirmPassword = document.forms[0].confirmPassword.value;
	    document.forms[0].oldPassWord.value = desEncrypt(document.forms[0].oldPassWord.value);
	    document.forms[0].newPassWord.value = desEncrypt(document.forms[0].newPassWord.value);
	    document.forms[0].confirmPassword.value = desEncrypt(document.forms[0].confirmPassword.value);
	    
	    var flag = ajaxUpdate();
	    if(!flag) {
	    	// 如果修改密码失败，需要将密码解析成明文
	    	document.forms[0].oldPassWord.value = __oldPassWord;
		    document.forms[0].newPassWord.value = __newPassWord;
		    document.forms[0].confirmPassword.value = __confirmPassword;
	    }
	    
	    return true;
	}
</script>
<html>
<head>
<title><bean:message bundle="sys-config" key="sysAdmin.modifyAdminPass"/></title>

</head>
<body>
<div class="lui_person_per_right" style="text-align: center;">
		
		<input type="hidden" name="redto" value="<c:out value="${redto}"/>" />
		    <input type="hidden" id="_oldPassword" value=""/>
			<input type="hidden" id="_newPassword" value=""/>
			<input type="hidden" id="_comparePassword" value=""/>
			<input type="hidden" id="url" value="<%=request.getContextPath()%>"/>
			<form name="sysOrgPersonInfoForm" method="post" autocomplete="off" 
				action="<%=request.getContextPath()%>/admin.do?method=editPassWord" 
				 id="passwordForm">
				 
				<input type="password" style="display: none;">
				<xform:text property="fdId" showStatus="noShow"/>
				<table class="tb_simple" >
					<tr>
						<td colspan="3" align="center"><span><span class="txtstrong" >${passwordStrength}</span></span></td>
					</tr>
					<tr>
						<td class="td_normal_title width200">
							<SPAN><bean:message bundle="sys-organization" key="sysOrgPerson.oldPassword"/></SPAN>
						</td>
						<td class="width200">
							<input type="password" id="passowrd" name="oldPassWord" style="width:196px;"  class="inputsgl" autocomplete="off" />
						</td>
						<td class="width250">
							<div class="pwdTip oldPwdTip">
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title width200">
							<bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/>
						</td>
						<td class="width200">
							<input type="password" id="passowrd" name="newPassWord" style="width:196px;"  class="inputsgl" autocomplete="off" />
						</td>
						<td rowspan="2" class="width250" style="vertical-align: top;padding-top:10px;">
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
						<td style="width: 220px">
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
							<input type="password" id="passowrd" name="confirmPassword" style="width:196px;" class="inputsgl" autocomplete="off" />
						</td>
						<td>
							<div class="pwdTip comparePwdTip">
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
						</td>
					</tr>
					<tr>
		<td class="td_normal_title width200">
			 <img id="editPassvcode" onclick="this.src='vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='vcode.jsp'>
		     <a class="btn_flush" 
		     	title="<%=ResourceUtil.getString(request.getSession(), "login.verifycode.refresh")%>" 
		     	onclick="$(this).prev().click()"></a>
		</td>
		<td>
			<input type="text" name='validation_code'  style="width:196px;"
					class="inputSgl" onclick="this.select();" />
		</td>
		<td>
							<div class="pwdTip codeTip">
								<p class="icon"><span></span></p>
								<p class="textTip"></p>
							</div>
						</td>
	</tr>
					<tr>
					     <td colspan="3" style="padding-left: 270px;">
					     <input type="button" class="btnopt" onclick="check();" value="<bean:message  key="button.submit"/>">
					    </td>						
				    </tr>
				</table>
			</form>  
</div>
</body>
</html>