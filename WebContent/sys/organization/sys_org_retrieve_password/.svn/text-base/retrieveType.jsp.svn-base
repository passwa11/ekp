<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>

<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.button.changePassword}")%></title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="./retrievePassword.css">
</head>
<body>

<div id="bdw" class="bdw">
<div id="bd" class="cf">
<div class="content">
<h3 class="headline"><span class="headline__content"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.retrievePassword" /></span></h3>

<ul class="steps-bar steps-bar--dark cf">




	<li class="step step--current"
		style="z-index: 3; width: 33.333333333333336%;"><span
		class="step__num">1.</span> <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.select" /></span> <span
		class="arrow__background"></span> <span class="arrow__foreground"></span>
	</li>




	<li class="step step--pre"
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

<p class="verify-tip-title">${tip}</p>

<div class="form__wrapper">
<div style="padding-left: 220px;">

<ul class="find-ways">

	<li class="way way--last">
	<form class="way__content cf"
		action="<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=toSendValidationCode" method="POST"
		data-id="0">
		<input class="btn immi-retrieve" type="submit"
		value="立即找回" data-mtevent="nextstep"> <input type="hidden"
		value="4" name="id"> <i class="icon icon--mobile"></i> <span
		class="title"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.mobile" /></span> <span class="description"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.mobile.tip" /></span>
	</form>
	</li>
</ul>
<br>
<span style="padding-left: 5px;"><font color="red" size="3pt" id="msg_block">${errMsg}</font></span>
	
</div>
</div>
</div>
</div>
</div>




</body>
</html>
