<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<% if(UserUtil.getKMSSUser().isAnonymous()) { %>
<script type="text/javascript">
	// 匿名用户不可访问
	location.href = '<%=request.getContextPath()%>/pda/login.jsp';
</script>
<% } %>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.sensitive.operate.content"/>
	</template:replace>
	
	<template:replace name="head">
		<link type="text/css" rel="Stylesheet" href="${LUI_ContextPath}/sys/common/changePwd/mobile/resource/person.css?s_cache=${MUI_Cache}" />
		<script type="text/javascript" src="${LUI_ContextPath}/sys/common/changePwd/js/pwdstrength.js"></script>
	</template:replace>
	
	<template:replace name="content">
		<div class="pwdbox">
			<c:choose>
				<c:when test="${'true' eq compulsoryChangePassword}">
					<div class="error-msg">
						<div class="error"><bean:message bundle="sys-organization" key="sysOrgPerson.compulsoryChangePassword"/></div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="change_password_form">
						<h1><bean:message bundle="sys-organization" key="sysOrgPerson.sensitive.operate.content"/></h1>
					</div>
				</c:otherwise>
			</c:choose>
			<div class="change_password_form">
				<!-- 修改密码 Starts-->
				<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN || TripartiteAdminUtil.isGeneralUser()) { // 未开启三员 或 非三员管理 才显示的内容 %>
				<form id="change_password_form" action="<%=request.getContextPath()%>/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" method="post" onsubmit="return false;">
				<% } else { %>
				<form id="change_password_form" action="<%=request.getContextPath()%>/sys/profile/tripartiteAdminChangePwd.do?method=saveMyPwd" method="post" onsubmit="return false;">
				<% } %>
					<div class="lui_changepwd_panel">
						<div class="content">
							<table>
								<!-- 原密码 -->
								<tr class="tr-opt">
									<td class="td_title">
										<span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.oldPassword"/></span>
									</td>
									<td class="td_input">
										<div class="input"><input type="password" name="fdOldPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip1') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
									</td>
									<td><div class="password-img"></div></td>
								</tr>
								<tr class="oldPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip">${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip1') }</p>
											<p class="textTip"><bean:message bundle="sys-organization" key="sysOrgPerson.old.password.no"/></p>
										</div>
									</td>
								</tr>
								<!-- 新密码 -->
								<tr class="tr-opt">
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/></span>
			                        </td>
			                        <td class="td_input">
			                            <div class="input"><input type="password" name="fdNewPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip2') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
			                        </td>
			                        <td><div class="password-img"></div></td>
								</tr>
								<tr class="newPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip">${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip2') }</p>
				                            <p class="textTip"></p>
										</div>
									</td>
								</tr>
								<!-- 密码强度提示 -->
			                    <tr>
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.pwdIntensity"/></span>
			                        </td>
			                        <td class="td_input" colspan="2">
			                            <div class="intension">
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip1') }</span>
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip2') }</span>
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip3') }</span>
			                            </div>
			                        </td>
			                    </tr>
			                    <!-- 密码确认 -->
								<tr class="tr-opt">
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.confirmPassword"/></span>
			                        </td>
			                        <td class="td_input">
			                            <div class="input"><input type="password" name="fdConfirmPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip3') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
			                        </td>
			                       <td><div class="password-img"></div></td>
								</tr>
								<tr class="confirmPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip"></p>
										</div>
									</td>
								</tr>
							</table>
							<div class="btnW"><a class="btn_submit_1" id="btn_submit"><bean:message key="button.submit"/></a></div>
						</div>
					</div>
				</form>
				<!-- 修改密码 Ends-->
	    	</div>
	    </div>
	    <!-- 修改密码 Ends-->
		<%@ include file="/sys/common/changePwd/mobile/change_pwd_script.jsp" %>
	</template:replace>
	
</template:include>