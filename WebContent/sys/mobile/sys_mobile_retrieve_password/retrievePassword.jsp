<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/mobile/sys_mobile_retrieve_password/resource/template/template.jsp" compatibleMode="true">
	<template:replace name="title">
		${lfn:message('sys-organization:sysOrgRetrievePassword.retrievePassword') }
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/resource/css/retrievePassword.css" />
		<script type="text/javascript" src="${LUI_ContextPath}/sys/common/changePwd/js/pwdstrength.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/security.js"></script>
	</template:replace>
	<template:replace name="content">
	    <div class="validateAccountContainer">
			<form action="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/sysMobileRetrievePassword.do?method=savePwd" onsubmit="return false;" method="POST">
				<%-- 标题:“重置密码” --%>
			    <div class="muiTitle muiFontSizeXXXXL">重置密码</div>
				<div class="muiPanelContent">
				    <ul>
				        <%-- 新密码 --%>
		                <li class="newPasswordRow">
		                    <input type="password" name="fdNewPassword" class="muiInput muiPasswordInput muiFontSizeM" placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.newpassword.placeholder')}">
		                    <div class="muiInputDeleteIcon newPasswordDeleteIcon"></div>
		                </li>
		                <%-- 确认新密码 --%>
		                <li class="confirmPasswordRow">
		                    <input type="password" name="fdConfirmPassword" class="muiInput muiPasswordInput muiFontSizeM" placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.repassword.placeholder')}">
		                    <div class="muiInputDeleteIcon confirmPasswordDeleteIcon"></div>
		                </li>
				    </ul>
				</div>
				<div class="muiSubmitContainer">
				    <%-- 错误提醒文字展示区域 --%>
					<div class="muiErrorContent muiFontSizeS"></div>
					<%-- “提交”按钮 --%>
					<div class="muiBtnBar">
						<input type="submit" id="submit_btn" class="muiSubmitBtn muiFontSizeXL" value="${lfn:message('button.submit')}" onclick="doSubmit()" />
					</div>
				</div>
			</form>
		</div>
	</template:replace>
</template:include>
<script type="text/javascript">
require(['dojo/ready', "dojo/on", 'dojo/query','mui/dialog/Tip', "dojo/dom-style", 'dojo/dom-class','dojo/dom-attr','dojo/dom-construct','dojo/request','dojo/NodeList-dom'], function(ready, on, query, Tip, domStyle, domClass, domAttr, domConstruct, request){
	ready(function(){
		checkSubmitButton();
	});

	/** “提交”按钮是否可用 **/
	var checkSubmitButton = function(){
		var	psd = query('input[name="fdNewPassword"]'),
			repsd = query('input[name="fdConfirmPassword"]'),
			submitBtn = query('#submit_btn');
		if(psd[0].value && repsd[0].value) {
			domClass.add(submitBtn[0],'clickable'); // 允许点击“提交”按钮
		} else {
			domClass.remove(submitBtn[0],'clickable'); // 不允许点击“提交”按钮
		}
	};
	window.checkSubmitButton = checkSubmitButton;
	
	/** 绑定输入框输入事件 **/
	var bindInputEvent = function(){
		var newPassword = query('input[name="fdNewPassword"]');
		var confirmPassword = query('input[name="fdConfirmPassword"]');
		var newPasswordDeleteIcon = query('.newPasswordDeleteIcon'); // 新密码删除图标
		var confirmPasswordDeleteIcon = query('.confirmPasswordDeleteIcon'); // 确认密码删除图标
	    on(newPassword, "input,propertychange", function(e){
	        if (newPassword[0].value == '') {
	        	domStyle.set(newPasswordDeleteIcon[0],{"opacity":"0"}); // 隐藏删除图标（调整透明度） 
	        } else {
	        	domStyle.set(newPasswordDeleteIcon[0],{"opacity":"1"}); // 显示删除图标
	        }
	    	checkSubmitButton();
	    });
	    on(newPassword, "blur", function(e){
	        domStyle.set(newPasswordDeleteIcon[0],{"opacity":"0"});
	    });
	    on(newPasswordDeleteIcon, "click", function(e){
	    	newPassword[0].value="";
	        domStyle.set(newPasswordDeleteIcon[0],{"opacity":"0"});
	        checkSubmitButton();
	    });
	    on(confirmPassword, "input,propertychange", function(){
	        if (confirmPassword[0].value == '') {
	        	domStyle.set(confirmPasswordDeleteIcon[0],{"opacity":"0"}); // 隐藏删除图标（调整透明度） 
	        } else {
	        	domStyle.set(confirmPasswordDeleteIcon[0],{"opacity":"1"}); // 显示删除图标
	        }	    	
	    	checkSubmitButton();
	    });
	    on(confirmPassword, "blur", function(e){
	        domStyle.set(confirmPasswordDeleteIcon[0],{"opacity":"0"});
	    });
	    on(confirmPasswordDeleteIcon, "click", function(e){
	    	confirmPassword[0].value="";
	        domStyle.set(confirmPasswordDeleteIcon[0],{"opacity":"0"});
	        checkSubmitButton();
	    });
	};
	
	bindInputEvent();

	/** 构建错误消息提醒 **/
	var doError = function(errMsg){
		var errDiv = query('div.muiErrorContent')[0];
		domConstruct.empty(errDiv);
		domConstruct.place('<span>'+errMsg+'</span>', errDiv, 'last');
	};
	
	
	/** 提交按钮事件 **/
	window.doSubmit = function(){
		var psd = query('[name="fdNewPassword"]'),
			repsd = query('[name="fdConfirmPassword"]');
		var password  = psd[0].value,
			repassword = repsd[0].value;
		if( password !== repassword ){
			doError('<bean:message bundle="sys-organization" key="sysOrgPerson.error.comparePwd" />');
			return false;
		}
		if(!checkPsd(password)){
			return false;
		}

		var cipher = window.desEncrypt(password);
		repsd[0].value = psd[0].value = cipher.substring(0,10);

		var promise = request.post('<%=request.getContextPath()%>/sys/mobile/sys_mobile_retrieve_password/sysMobileRetrievePassword.do?method=savePwd',
				{
					handleAs : 'json',
					data : {fdNewPassword:cipher}
				});
		promise.then(
				function(data){
					if(data.result){
						Tip.success({
							text : '<bean:message bundle="sys-organization" key="sysOrgPerson.chgMyPwd.success" />',
							callback : function() {
									location.href = "<%=request.getContextPath()%>/third/pda/login.jsp";
								}
							});
					} else {

						psd[0].value = '';
						repsd[0].value = '';
						checkSubmitButton();
						
						switch(data.errCode){
						case '3':
						case '5':
							doError('<bean:message bundle="sys-organization" key="sysOrgPerson.error.same" />');
							break;
						case '10': 
							doError('<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.noUserInSession" />');
							break;
						default:
							if(window.console)
								console.error(data);
							break;
						}
					}
				},
				function(err){
					if(window.console)
						console.error(err);
				} 
		);
			
	};

	var checkPsd = function(password){

		var kmssOrgPasswordlength = <%=com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordlength()%>;
		var kmssOrgPasswordstrength = <%=com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordstrength()%>;

		var pwdlen =  kmssOrgPasswordlength ? kmssOrgPasswordlength : 1;
		var pwdsth = kmssOrgPasswordstrength ? kmssOrgPasswordstrength : 0;
				
		var isAdmin = <%=UserUtil.getUser().getFdLoginName().equals("admin")%>;
		var loginName = "<%=UserUtil.getUser().getFdLoginName()%>";
		if(isAdmin) {
			// 管理员的密码需要加强处理，如果设置的强度大于管理员默认强度，则取更大的强度
			pwdlen = pwdlen < 8 ? 8 : pwdlen;
			pwdsth = pwdsth < 3 ? 3 : pwdsth;
		}
		var msgLen = "<bean:message bundle='sys-organization' key='sysOrgPerson.error.pwdStructure1' />",
			msgSth = "<bean:message bundle='sys-organization' key='sysOrgPerson.error.pwdStructure2' />";
		msgLen = pwdlen + msgLen;
		msgSth = pwdlen + (msgSth.replace("#len#", pwdsth));
		
		if(loginName == password) {
			doError('<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdCanNotSameLoginName" />');
			return false;
		}
		if(password.length < pwdlen){
			doError(msgLen);
			return false;
		}
		if(window.pwdstrength(password) < pwdsth){
			doError(msgSth);
			return false;
		}
		return true;
	};
});
</script>