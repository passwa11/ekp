<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig"%>

<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=ResourceUtil.getMessage("{sys-organization:sysOrgRetrievePassword.retrievePassword}")%></title>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="./retrievePassword.css">
<%
    if ("false".equalsIgnoreCase(PasswordSecurityConfig.newInstance().getRetrievePasswordEnable())) {
        request.getRequestDispatcher("/resource/jsp/e403.jsp").forward(request, response);
    }
	request.getSession().setAttribute("RETRIEVE_PASSWORD_PERSON", null);
%>
<script>
function validate(){
	return true;
}
</script>
</head>
<body style="pg-retrieve theme--www">
	<!-- 修改密码 Starts-->
		<div class="content">
        <h3 class="headline"><span class="headline__content"><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.retrievePassword" /></span></h3>
        <ul class="steps-bar steps-bar--dark cf">
    <li class="step step--first step--current" style="z-index:3">
        <span class="step__num">1.</span>
        <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.confirm.account" /></span>
        <span class="arrow__background"></span><span class="arrow__foreground"></span>
    </li>
    <li class="step step--post" style="z-index:2">
        <span class="step__num">2.</span>
        <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.type.select" /></span>
        <span class="arrow__background"></span><span class="arrow__foreground"></span>
    </li>
    <li class="step step--post" style="z-index:1">
        <span class="step__num">3.</span>
        <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.update" /></span>
        <span class="arrow__background"></span><span class="arrow__foreground"></span>
    </li>
    <li class="step step--last step--post" style="z-index:0">
        <span class="step__num">4.</span>
        <span><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.complete" /></span>
        
    </li>
</ul>


        <div class="form__wrapper">
        <div style="padding-left: 180px;">
        	
            <form action="<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateUser" method="post">
                <div class="form-field">
                    <label><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.account" /></label>
                    <input class="f-text account" name="userid" type="text" placeholder="<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.moblieNo.loginName" />">
                    <span style="padding-left: 10px;font-size: 14px; color:red;">${errMsg}</span>
                </div>
                <div class="form-field captcha cf">
                    <label><bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validate.code" /></label>
                    <input class="f-text verify-code" name="v_code" type="text">
                    <img onclick="this.src='vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='vcode.jsp'>
		                
                </div>
                <div class="form-field">
                    <input type="submit" class="btn" value="<bean:message  bundle="sys-organization" key="sysOrgRetrievePassword.validation.next" />">
                </div>
            </form>
        </div>
        </div>
    </div>

	
</body>
</html>
