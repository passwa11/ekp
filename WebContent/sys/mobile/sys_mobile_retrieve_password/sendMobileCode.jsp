<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/mobile/sys_mobile_retrieve_password/resource/template/template.jsp" compatibleMode="true">
	<template:replace name="title">
		${lfn:message('sys-organization:sysOrgRetrievePassword.retrievePassword') }
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/resource/css/sendMobileCode.css" />
	</template:replace>
	<template:replace name="content">
	    <div class="validateMobileCodeContainer">
			<form action="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/sysMobileRetrievePassword.do?method=validateMobileCode" onsubmit="return validateForm();" method="POST" autocomplete="off">
			    <%-- 标题:“短信验证” --%>
			    <div class="muiTitle muiFontSizeXXXXL">短信验证</div>
			    
				<div class="muiPanelContent">
				    <ul>
				        <%-- 账号 --%>
		                <li class="accountRow">
                             <div class="muiPhoneNum muiFontSizeM"><span class="loginName">${loginName}</span><span class="mobileNo">(${mobileNo})</span></div>
		                </li>
		                <%-- 手机短信验证码 --%>
		                <li class="validationCodeRow">
		                	<input type="text" name="vcode" class="muiInput muiFontSizeM" maxlength="8" placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.mobile.code.placeholder') }" onkeyup="checkSubmitButton()" />
		                    <div class="muiVerificationCode">
						       <button id="sendvcode_btn" class="muiVcodeBtn" type="button" onclick="sendVcode()"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobile.getCode" /></button>
		                    </div>
		                </li>
				    </ul>
				</div>
				<div class="muiSubmitContainer">
					<%-- 错误提醒文字展示区域 --%>
		            <div class="muiErrorContent muiFontSizeS">
						<c:if test="${not empty errMsg }">
						<span>${errMsg}</span>
					    </c:if>
		            </div>
					<%-- “下一步”按钮 --%>
					<div class="muiBtnBar">
						 <input type="submit" id="submit_btn" class="muiSubmitBtn muiFontSizeXL" value="${lfn:message('sys-organization:sysOrgRetrievePassword.validation.next') }" />
					</div>
				</div>
			</form>
		</div>
	</template:replace>
</template:include>
<script type="text/javascript">
require(['dojo/ready', "dojo/on", 'dojo/query','mui/dialog/Tip', 'dojo/dom-class','dojo/dom-attr','dojo/request','dojo/NodeList-dom'], function(ready, on, query, Tip, domClass, domAttr, request){

	var isFirstTimeLoad = '${isFirstTimeLoad}';
	
	// “下一步”按钮是否可用
	var checkSubmitButton = function(){
		var vcode = query('input[name="vcode"]'),
			submitBtn = query('#submit_btn');
		if(vcode[0].value) {
			domClass.add(submitBtn[0],'clickable'); // 允许点击“下一步”按钮
		} else {
			domClass.remove(submitBtn[0],'clickable'); // 不允许点击“下一步”按钮
		}
	};

	window.checkSubmitButton = checkSubmitButton;
	
	// 绑定输入框输入事件
	var bindInputEvent = function(){
		var vcode = query('input[name="vcode"]');
	    on(vcode, "input,propertychange", function(){
	    	checkSubmitButton();
	    });
	};
	
	bindInputEvent();
	

	ready(function(){
		checkSubmitButton();
		if(isFirstTimeLoad == 'true') {
			sendVcode();
		}
	});

	window.validateForm = function(){
		var vcode = query('[name="vcode"]')[0];
		if(!vcode.value){
			Tip.fail({
				text:'<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.mobileCodeNull"/>'
			});
			return false;
		}
		return true;
	};

	//-----发送短信验证码--------------
	var sendVcode = function(){
		var sendvcode_btn = query('#sendvcode_btn');
		domClass.add(sendvcode_btn[0], 'btn-disabled');
		sendvcode_btn.attr('disabled','disabled');
		var promise = request.post('<%=request.getContextPath()%>/sys/mobile/sys_mobile_retrieve_password/sysMobileRetrievePassword.do?method=sendMobileCode',
				{ handleAs : 'json'});
		promise.then(
			function(data){
				if (data.result) {
					Tip.success({
						text: '<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.message.sent"/>'	
					});
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
			btn[0].innerHTML = '<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobile.reGetCode" />'; // 重新获取验证码
		}
	};
	window.sendVcode  = sendVcode;
	//----------------------------------
	
});
</script>