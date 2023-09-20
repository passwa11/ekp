<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/organization/sys_org_retrieve_password/mobile/resource/jsp/template.jsp" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.retrievePassword"/>
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/sys_org_retrieve_password/mobile/resource/css/retrieve.css" />
	</template:replace>
	<template:replace name="content">
		<form action="<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateAccount" onsubmit="return validateForm();" method="post">
			<div class="muiPanelContent">
				<%-- 账号 --%>
				<div class="muiCell">
					<div class="muiCellHd"><label class="muiLabel"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.account" /></label></div>
					<div class="muiCellBd">
						<input class="muiInput" name="userid" type="text"
						placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.moblieNo.loginName.placeholder') }" />
					</div>
				</div>
				<%-- 验证码 --%>
				<div class="muiCell muiCellVcode">
					<div class="muiCellHd">
						<label class="muiLabel"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.code" /></label>
					</div>
					<div class="muiCellBd">
						<input class="muiInput" name="v_code" type="text"
						placeholder="${lfn:message('sys-organization:sysOrgRetrievePassword.validate.code.placeholder') }" />
					</div>
					<div class="muiCellft">
	                	<img onclick="this.src='<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/vcode.jsp?xx='+Math.random()" style='cursor: pointer;' 
	                		src='<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/vcode.jsp'/>
					</div>
				</div>
			</div>
			<div class="div_error">
					<c:if test="${not empty errMsg }">
						<i class="mui mui-wrong"></i>${errMsg}
					</c:if>
			</div>
			<div class="muiBtnBar">
				<input id='submit_btn' class="muiBtn" type="submit" value='<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.next" />'>
			</div>
		</form>
	</template:replace>
</template:include>
<script type="text/javascript">
require(['dojo/ready', 'dojo/query','mui/dialog/Tip', 'dojo/dom-class','dojo/dom-attr','dojo/NodeList-dom'],function(ready, query, Tip, domClass, domAttr){

	ready(function(){
		checkSubmitButton();
		query('[name="userid"]').on('keyup', checkSubmitButton);
		query('[name="v_code"]').on('keyup', checkSubmitButton);
	});

	var checkSubmitButton = function(){
		var account = query('[name="userid"]'),
			vcode = query('[name="v_code"]'),
			submitBtn = query('#submit_btn');
		if(account[0].value && vcode[0].value) {
			domClass.remove(submitBtn[0],'disable');
			domAttr.remove(submitBtn[0], 'disabled');
		} else {
			domClass.add(submitBtn[0],'disable');
			submitBtn.attr('disabled','disabled');
		}
	};
	
	window.validateForm = function(){
		var account = query('[name="userid"]')[0],
		vcode = query('[name="v_code"]')[0];
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