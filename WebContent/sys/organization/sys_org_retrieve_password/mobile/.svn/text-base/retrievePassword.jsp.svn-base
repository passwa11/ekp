<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/organization/sys_org_retrieve_password/mobile/resource/jsp/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.retrievePassword"/>
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/sys_org_retrieve_password/mobile/resource/css/retrieve.css" />
		<script type="text/javascript" src="${LUI_ContextPath}/sys/common/changePwd/js/pwdstrength.js"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/security.js"></script>
	</template:replace>
	<template:replace name="content">
		<form action="<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=savePwd" onsubmit="return false;" method="POST">
			<div class="muiPanelContent">
				<div class="muiCell">
					<div class="muiCellHd">
						<label class="muiLabel"><bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo"/></label>
					</div>
					<div class="muiCellBd">
						<span class="muiPhoneNum">${loginName}(${mobileNo})</span>
					</div>
				</div>
				<div class="muiCell muiCellVcode">
					<div class="muiCellHd">
						<label class="muiLabel"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.code" /></label>
					</div>
					<div class="muiCellBd">
						<input class="muiInput" name='vcode'
							<c:if test="${errCode eq 'mobileCodeOutOfDate' }">disabled</c:if>
							placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.validate.code.placeholder') }" />
					</div>
					<div class="muiCellft">
						<button id='sendvcode_btn' class="muiVcodeBtn" type="button"">
							<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobile.getCode" />
						</button>
					</div>
				</div>
				<div class="muiCell">
					<div class="muiCellHd">
						<label class="muiLabel">
							<bean:message  bundle="sys-organization" key="sysOrgPerson.newPassword" />
						</label>
					</div>
					<div class="muiCellBd">
						<input class="muiInput" type="password" name='fdNewPassword'
							placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.newpassword.placeholder') }" />
					</div>
					<div class="muiCellft">
						<span class="muiCellIcon"><i class="muiCellIcon-eye"  id='toggleBtn'></i></span>
					</div>
				</div>
				<div class="muiCell">
					<div class="muiCellHd">
						<label class="muiLabel"><bean:message  bundle="sys-organization" key="sysOrgPerson.confirmPassword" /></label>
					</div>
					<div class="muiCellBd">
						<input class="muiInput" type="password" name='fdConfirmPassword'
							placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.repassword.placeholder') }">
					</div>
				</div>
			</div>
			<div class="div_error">
					<c:if test="${not empty errMsg }">
						<i class="mui mui-wrong"></i>${errMsg}
					</c:if>
			</div>
			<div class="muiBtnBar">
				<input id='submit_btn' class="muiBtn" type="submit" value='<bean:message key="button.submit" />' />
			</div>
		</form>
	</template:replace>
</template:include>
<script type="text/javascript">
require(['dojo/ready', 'dojo/query','mui/dialog/Tip', 'dojo/dom-class','dojo/dom-attr','dojo/request','dojo/NodeList-dom'], function(ready, query, Tip, domClass, domAttr, request){
	ready(function(){
		checkSubmitButton();
		var vcode = query('[name="vcode"]'),
			psd = query('[name="fdNewPassword"]'),
			repsd = query('[name="fdConfirmPassword"]'),
			sendvcodeBtn = query('#sendvcode_btn'),
			submitBtn = query('#submit_btn'),
			toggleBtn = query('#toggleBtn');
		vcode.on('blur', validateMobileCode);
		vcode.on('keyup', checkSubmitButton);
		psd.on('keyup', checkSubmitButton);
		repsd.on('keyup', checkSubmitButton);
		sendvcodeBtn.on('click', sendVcode);
		submitBtn.on('click', doSubmit);
		toggleBtn.on('click',toggleCipher);
		
	});

	var sendVcode = function(){
		var sendvcode_btn = query('#sendvcode_btn');
		domClass.add(sendvcode_btn[0], 'btn-disabled');
		sendvcode_btn.attr('disabled','disabled');
		var promise = request.post('<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=sendVCode',
				{ handleAs : 'json'});
		promise.then(
			function(data){
				if (data.result) {
					if(window.console)
						console.log('<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.message.sent"/>');
				} else {
					Tip.fail({
						text:data.errMsg
					});
				}
			},
			function(err){
				if(window.console)
					console.error(err);
			}
		);
		countdown(sendvcode_btn);
	};

	var time = ${reSentIntervalTime};
	var st;
	var countdown = function(btn) { 
		time--;
		btn[0].innerHTML = time + '<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.message.send.countdown" />';
		st = setTimeout(function() { 
			countdown(btn);
		},1000);
		if (time <= 0) {
			time = ${reSentIntervalTime};
			clearTimeout(st);
			domClass.remove(btn[0], 'btn-disabled');
			domAttr.remove(btn[0], 'disabled');
			btn[0].innerHTML = '<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobile.getCode" />';
		}
	};

	var validateSuccess  = false;
	var validateMobileCode = function(){
		var vcode = query('[name="vcode"]')[0].value;
		var promise = request.post('<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateVCode',
				{	handleAs : 'json',  
					data: {m_code:vcode} 
				});
		promise.then(
			function(data){
				if (data.result) {
					Tip.success({
						text:'<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.message.vcode.success" />'
					});
					validateSuccess = true;
				} else {
					Tip.fail({
						text:data.errMsg
					});
					validateSuccess = false;
				}
			},
			function(err){
				if(window.console)
					console.error(err);
				validateSuccess = false;
			}
		);
	};

	var checkSubmitButton = function(){
		var vcode = query('[name="vcode"]'),
			psd = query('[name="fdNewPassword"]'),
			repsd = query('[name="fdConfirmPassword"]'),
			submitBtn = query('#submit_btn');
		if(vcode[0].value && psd[0].value && repsd[0].value) {
			domClass.remove(submitBtn[0],'disable');
			domAttr.remove(submitBtn[0], 'disabled');
		} else {
			domClass.add(submitBtn[0],'disable');
			submitBtn.attr('disabled','disabled');
		}
	};

	var toggleCipher = function(evt){
		var psd = query('[name="fdNewPassword"]'),
			repsd = query('[name="fdConfirmPassword"]'),
			toggleBtn = query('#toggleBtn');
		switch(psd[0].type){
		case 'password':
			domClass.add(toggleBtn[0],'status-hidden');
			psd.attr('type','text');
			repsd.attr('type','text');
			break;
		case 'text':
			domClass.remove(toggleBtn[0],'status-hidden');
			psd.attr('type','password');
			repsd.attr('type','password');
			break;			
		default:
			if(window.console)
				console.error('input is of another type');
			break;
		}
	};
	
	var doSubmit = function(){
		var psd = query('[name="fdNewPassword"]'),
			repsd = query('[name="fdConfirmPassword"]');
		var password  = psd[0].value,
			repassword = repsd[0].value;
		if(!validateSuccess){
			Tip.fail({
				text:'<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.message.vcode.fail" />'
			});
			return false;
		} 
		if( password !== repassword ){
			Tip.fail({
				text: '<bean:message bundle="sys-organization" key="sysOrgPerson.error.comparePwd" />'
			});
			return false;
		}
		if(!checkPsd(password)){
			return false;
		} else {
			var cipher = window.desEncrypt(password);
			repsd[0].value = psd[0].value = cipher.substring(0,10);
			psd.attr('type','password');
			repsd.attr('type','password');
			domClass.remove(query('#toggleBtn')[0],'status-hidden');
			var promise = request.post('<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=savePwd',
					{
						handleAs : 'json',
						data : {fdNewPassword:cipher}
					});
			promise.then(
					function(data){
						if(data.result){
							Tip.success({
								text : '<bean:message bundle="sys-organization" key="sysOrgPerson.chgMyPwd.success" />',
								});
							location.href = "<%=request.getContextPath()%>/third/pda/login.jsp";
						} else {
							psd[0].value = '';
							repsd[0].value = '';
							checkSubmitButton();
							switch(parseInt(data.errCode)){
							case 3:
							case 5:
								Tip.fail({
									text : '<bean:message bundle="sys-organization" key="sysOrgPerson.error.same" />'
								});
								break;
							case 10: 
								Tip.fail({
									text : '<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.noUserInSession" />'
								});
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
		}
	};
	
	var checkPsd = function(password){

		var kmssOrgPasswordlength = <%=com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordlength()%>;
		var kmssOrgPasswordstrength = <%=com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordstrength()%>;

		var pwdlen =  kmssOrgPasswordlength ? kmssOrgPasswordlength : 1;
		var pwdsth = kmssOrgPasswordstrength ? kmssOrgPasswordstrength : 0;
				
		var isAdmin = "<%=UserUtil.getUser().getFdLoginName().equals("admin")%>";
		var loginName = "<%=UserUtil.getUser().getFdLoginName()%>";
		if(isAdmin) {
			// 管理员的密码需要加强处理，如果设置的强度大于管理员默认强度，则取更大的强度
			pwdlen = pwdlen < 8 ? 8 : pwdlen;
			pwdsth = pwdsth < 3 ? 3 : pwdsth;
		}
		var msgLen = "<bean:message bundle='sys-organization' key='sysOrgPerson.error.pwdLength' />",
			msgSth = "<bean:message bundle='sys-organization' key='sysOrgPerson.error.pwdStrong' />";
		msgLen = msgLen.replace("#len#", pwdlen);
		msgSth = msgSth.replace("#len#", pwdsth);
		
		if(loginName == password) {
			Tip.fail({
				text: "<bean:message bundle='sys-organization' key='sysOrgPerson.error.newPwdCanNotSameLoginName' />"
			});
			return false;
		}
		if(password.length < pwdlen){
			Tip.fail({
				text: msgLen
			});
			return false;
		}
		if(window.pwdstrength(password) < pwdsth){
			Tip.fail({
				text: msgSth
			});
			return false;
		}
		return true;
	};
});
</script>