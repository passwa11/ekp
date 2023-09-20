<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/mobile/sys_mobile_retrieve_password/resource/template/template.jsp" compatibleMode="true">
	<template:replace name="title">
		${lfn:message('sys-organization:sysOrgRetrievePassword.retrievePassword') }
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/resource/css/validateUser.css" />
	</template:replace>
	<template:replace name="content">
	    <div class="validateAccountContainer">
			<form action="${LUI_ContextPath}/sys/mobile/sys_mobile_retrieve_password/sysMobileRetrievePassword.do?method=validateAccount" onsubmit="return validateForm();" method="post" autocomplete="off">
			    <%-- 标题:“账号验证” --%>
			    <div class="muiTitle muiFontSizeXXXXL">账号验证</div>
			    
				<div class="muiPanelContent">
				    <ul>
				        <%-- 账号 --%>
		                <li class="accountRow">
		                    <input type="text" name="userid" class="muiInput muiFontSizeM" placeholder="请输入手机号或用户名">
		                    <div class="muiInputDeleteIcon userIdDeleteIcon"></div>
		                </li>
		                <%-- 验证码 --%>
		                <li class="validationCodeRow">
		                    <input type="text" name="vcode" class="muiInput muiFontSizeM" maxlength="6" placeholder="验证码">
		                    <div class="muiVerificationCode">
		                        <img onclick="this.src='${LUI_ContextPath}/vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='${LUI_ContextPath}/vcode.jsp'/>
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
require(['dojo/ready', "dojo/on", 'dojo/query', "dojo/dom-style", 'dojo/dom-class','dojo/dom-attr','dojo/NodeList-dom'],function(ready, on, query, domStyle, domClass, domAttr){

	/** “下一步”按钮是否可用 **/
	var checkSubmitButton = function(){
		var account = query('input[name="userid"]'),
			vcode = query('input[name="vcode"]'),
			submitBtn = query('#submit_btn');
		if(account[0].value && vcode[0].value) {
			domClass.add(submitBtn[0],'clickable'); // 允许点击“下一步”按钮
		} else {
			domClass.remove(submitBtn[0],'clickable'); // 不允许点击“下一步”按钮
		}
	};
	
	/** 绑定输入框输入事件 **/
	var bindInputEvent = function(){
		var account = query('input[name="userid"]');
		var vcode = query('input[name="vcode"]');
		var userIdDeleteIcon = query('.userIdDeleteIcon'); // 删除图标
	    on(account, "input,propertychange", function(e){
	        if (account[0].value == '') {
	        	domStyle.set(userIdDeleteIcon[0],{"opacity":"0"}); // 隐藏删除图标（调整透明度） 
	        } else {
	        	domStyle.set(userIdDeleteIcon[0],{"opacity":"1"}); // 显示删除图标
	        }
	    	checkSubmitButton();
	    });
	    on(account, "blur", function(e){
	        domStyle.set(userIdDeleteIcon[0],{"opacity":"0"});
	    });	    
	    
	    on(vcode, "input,propertychange", function(){
	    	checkSubmitButton();
	    });
	    on(userIdDeleteIcon, "click", function(e){
	    	account[0].value="";
	        domStyle.set(userIdDeleteIcon[0],{"opacity":"0"});
	    });
	};
	
	bindInputEvent();
	
	/** 校验表单输入 **/
	window.validateForm = function(){
		var account = query('[name="userid"]')[0],
		vcode = query('[name="vcode"]')[0];
		var flag = true;
		if(!account.value){
			Tip.fail({
				text:'<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.userNull"/>'
			});
			account.focus();
			flag = false;
		}
		if(!vcode.value){
			Tip.fail({
				text:'<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.vCodeNull"/>'
			});
			vcode.focus();
			flag = false;
		}
		return flag;
	};

});
</script>