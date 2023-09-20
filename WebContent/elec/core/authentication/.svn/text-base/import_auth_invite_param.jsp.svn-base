<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page
	import="com.landray.kmss.elec.core.authentication.IAuthenticationService"%>
<%@ page import="com.landray.kmss.elec.core.ElecPlugin"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.common.exception.KmssRuntimeException"%>
<%@page import="com.landray.kmss.util.KmssMessage"%>
<%-- 
这是ELEC认证参数收集页面的代理页面，它相当于一个参数接口
这里通过扩展点的方式来引用真正的参数填充页面 
参数说明
必要参数:
<c:param name="authority" value=""/> 认证机构标识
<c:param name="personalAuthType" value=""/> 个人认证方式
<c:param name="enterpriseAuthType" value=""/> 企业认证方式
<c:param name="legalPersonAuthType" value=""/>法人认证方式  在企业认证时有效
<c:param name="authorizerAuthType" value=""/>授权人认证方式 在企业认证时有效
<c:param name="enableBankAuth" value=""/> 是否启用银行认证
<c:param name="fdCharacter" value=""/> 相对方性质：企业0|个人1

当是机构型相对方认证时:
<c:param name="fdPrincipalName" value=""/>
<c:param name="fdCreditCode" value=""/>
//法人信息
<c:param name="fdLegalPerson" value=""/>
<c:param name="fdLegalTelephone" value=""/>
<c:param name="fdIdentificationNo" value=""/>
//授权人信息
<c:param name="fdAuthorizer" value=""/>
<c:param name="fdAuthorizerIdentificationNo" value=""/>
<c:param name="fdAuthorizerPhone" value=""/>
//银行信息 start 
<c:param name="fdBank" value=""/>
<c:param name="fdBankCardNo" value=""/>

当是个人型相对方认证时:
<c:param name="fdPrincipalName" value=""/>
<c:param name="fdIdentificationNo" value=""/>
<c:param name="fdPhone" value=""/>
<c:param name="fdBank" value=""/>
<c:param name="fdBankCardNo" value=""/>
--%>
<%
    String authority = request.getParameter("authority");
//String authority = "yqql";
    String paramUrl = (String)ElecPlugin.getParamValue(
            ElecPlugin.EXTENSION_ELEC_AUTHENTICATION_SERVICE, 
            authority, 
            ElecPlugin.P_paramInputJsp);
    pageContext.setAttribute("paramUrl",paramUrl);
    
    String fdCharacter = request.getParameter("fdCharacter");
%>
<%if(paramUrl!=null) {%>
<c:import url="${paramUrl}" charEncoding="UTF-8">
    <c:param name="authority" value="${param.authority}"></c:param>
    <c:param name="fdThirdNum" value="${param.fdThirdNum}"></c:param>
    <c:param name="personalAuthType" value="${param.personalAuthType}"></c:param>
    <c:param name="enterpriseAuthType" value="${param.enterpriseAuthType}"></c:param>
    <c:param name="legalPersonAuthType" value="${param.legalPersonAuthType}"></c:param>
    <c:param name="authorizerAuthType" value="${param.authorizerAuthType}"></c:param>
    <c:param name="enableBankAuth" value="${param.enableBankAuth}"></c:param>
    <c:param name="fdCharacter" value="${param.fdCharacter}"></c:param>
    <c:param name="fdLegalPerson" value="${param.fdLegalPerson}"></c:param>
    <c:param name="fdLegalTelephone" value="${param.fdLegalTelephone}"/>
    <c:param name="fdIdentificationNo" value="${param.fdIdentificationNo}"></c:param>
    <c:param name="fdPhone" value="${param.fdPhone}"></c:param>
    <c:param name="fdAuthorizer" value="${param.fdAuthorizer}"></c:param>
    <c:param name="fdAuthorizerIdentificationNo" value="${param.fdAuthorizerIdentificationNo}"></c:param>
    <c:param name="fdAuthorizerPhone" value="${param.fdAuthorizerPhone}"></c:param>
    <c:param name="fdPrincipalName" value="${param.fdPrincipalName}"></c:param>
    <c:param name="fdCreditCode" value="${param.fdCreditCode}"></c:param>
    <c:param name="fdBank" value="${param.fdBank}"></c:param>
    <c:param name="fdBankCardNo" value="${param.fdBankCardNo}"></c:param>
    <%--  notify --%>
    <c:param name="fdNotifyMobile" value="${param.fdNotifyMobile}"></c:param>
    <c:param name="fdNotifyMail" value="${param.fdNotifyMail}"></c:param>
</c:import>
<%}else{ %>
<table class="tb_normal" width="100%">
   <tr>
       <td class="td_normal_title" width="15%">注意</td>
       <td width="85%">
           未找到提供认证邀请能力的模块，请与系统管理员联系。
       </td>
   </tr>
</table>
<%}%>
