<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.button.changePassword}")%></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="./retrievePassword.css">
<script type="text/javascript">
function sendMobileValidationCode(){
	$("#button_sendCode").attr("disabled","disabled");
	$("#button_sendCode").addClass("btn-disabled");

	$("#m_code").removeAttr("disabled");

	$.ajax({
		type : "POST",
		dataType : 'json',
		url : "<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=sendMobileValidationCode",
		success : function(data) {
			if (data.result==true) {
				$("#msg_block").text("短信已发出");
			} else {
				$("#msg_block").html(data.errMsg);
			}
		},
		error : function(data) {
			alert(data);
			$("#msg_block").html(data.errMsg);
		}
	});
	times();
	
}
var s = ${reSentIntervalTime}, t;
function times(){
 	s--;
 	$("#button_sendCode").val(s+"秒后重新发送");
	t = setTimeout('times()', 1000);
	if ( s <= 0 ){
		 s = ${reSentIntervalTime};
		 clearTimeout(t);
		 $("#button_sendCode").val("获取验证码");
		 $("#button_sendCode").attr("disabled","");
		 $("#button_sendCode").removeAttr("disabled");
		 $("#button_sendCode").removeClass("btn-disabled");
	}
}

function returnFailMsg(flag){
	if(flag == "true"){
		seajs.use(['lui/dialog'],function(dialog){
		       dialog.iframe("/sys/organization/sys_org_retrieve_password/retrievePasswordFail.jsp",null,null,{width:240,height:120})
		   });
	}
}

window.onload = function(){
	returnFailMsg('${failFlag}');
}
</script>
</head>
<body>
<div id="wrapper">
<div class="common-head">


<div id="bdw" class="bdw" data-action="findpwd">
<div id="bd" class="cf">
<div class="content">
<div class="common-tip J-page-error-message" style="display: none">
<div class="sysmsg">
<p><span class="tip-status tip-status--error"></span> <span
	class="J-error-content"></span></p>
</div>
</div>

<h3 class="headline"><span class="headline__content"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.mobile" /></span>
</h3>

<ul class="steps-bar steps-bar--dark cf">

	<li class="step step--post"
		style="z-index: 3; width: 33.333333333333336%;"><span
		class="step__num">1.</span> <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.select" /></span> <span
		class="arrow__background"></span> <span class="arrow__foreground"></span>
	</li>




	<li class="step step--current"
		style="z-index: 2; width: 33.333333333333336%;"><span
		class="step__num">2.</span> <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.update" /></span> <span
		class="arrow__background"></span> <span class="arrow__foreground"></span>
	</li>




	<li class="step step--pre"
		style="z-index: 1; width: 33.333333333333336%;"><span
		class="step__num">3.</span> <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.complete" /></span> <span
		class="arrow__background"></span> <span class="arrow__foreground"></span>
	</li>


</ul>
<p class="verify-tip-title"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.sendValidationCode.tip" /></p>

<div class="form__wrapper">
<div style="padding-left: 220px; margin-top: -100px;">

<form class="verify-cont verify--info" method="POST"
	action="<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateMobileCode"><input
	type="hidden" name="id" value="4">

<div class="verify-help-title cf" style="height: 100px;"><span
	class="tip-status tip-status--large tip-status--large--info"></span>
<h3 class="title title-singleline"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.mobileNo" /></h3>
</div>



<div class="mobile-verify-info">
<div class="form-field form-field--text form-field--smssender"><label><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobileNo.name" /></label>
<div>
<p><span class="text color-highlight">${mobileNo}</span></p>
<div class="smssender-cont">
<div class="J-resend-error-tip resend-error-tip" style="display: none;">
<span class="tip-status tip-status--error"></span> <span
	class="J-content"></span></div>

<input type="button" class="btn btn-normal btn-mini J-send-message-btn"
	value="<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.mobile.getCode" />" id="button_sendCode" onclick="sendMobileValidationCode();" > 
	<span style="padding-left: 30px;"><font color="red" size="3pt" id="msg_block">${errMsg}</font></span>
	<%--
                <div class="resend-wrapper">
                    <a class="resend-message J-resend-message" href="javascript:void(0);">没收到短信动态码？</a>

                    <div class="resend-message-tip J-resend-tip common-bubble" style="display:none">
                        <div class="arrow--background"></div>

                        <div class="arrow--prospect"></div>

                        <div class="common-close--small J-close close"></div>

                        <ul class="resend-message-tip__list">
                            <li class="resend-message-tip__head">网络通讯异常可能会造成短信丢失，请重新发送短信</li>
                            <li>请核实手机是否已欠费停机，或者屏蔽了系统短信</li>
                            <li>如果手机135*****442已丢失或停用，请选择<a href="">选择其它验证方式</a></li>
                        </ul>
                    </div>
                </div>
                 --%></div>
</div>

</div>
<div class="form-field"><label><bean:message  bundle="sys-organization" key="" /></label> 
<input type="text"
	class="f-text" name="m_code" id="m_code" required <c:if test="${errCode eq 'mobileCodeOutOfDate' }">disabled</c:if> ></div>

</div>

    <div class="form-field">
        <input type="submit" class="btn next-step" value="<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.next" />">
       <%-- <a href="" data-mtevent="prevstep">上一步</a> --%>
    </div>
</form>
</div>
</div>
</div>

</div>
</div>
	</template:replace>
</template:include>
