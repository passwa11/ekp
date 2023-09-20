<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.elec.core.authentication.IAuthenticationService"%>
<%@ page import="com.landray.kmss.elec.core.authentication.IAuthenticationManagementService"%>
<%@ page import="com.landray.kmss.elec.core.authentication.CharacterTypeEnum"%>
<%@ page import="com.landray.kmss.elec.core.ElecPlugin"%>
<%@ page import="com.landray.kmss.common.exception.KmssRuntimeException"%>
<%@ page import="com.landray.kmss.util.KmssMessage"%>
<%-- 认证邀请按钮导入页--%>


<%--
<c:param name="fdCharacter" value="类型 企业0|个人1"/>
<c:param name="fdModelId" value="$模型id"/>
<c:param name="fdModelName" value="模型名称"/>
<c:param name="formBeanName" value="xxForm"/>页面表单Bean的名称
 --%>
<%
String fdCharacter = request.getParameter("fdCharacter");
String formBeanName = request.getParameter("formBeanName");
IAuthenticationManagementService mngService = null;
if(CharacterTypeEnum.ORG.getCode().equals(fdCharacter)){
    mngService = ElecPlugin.getEnterpriseAuthenticationManagementService();
}else if(CharacterTypeEnum.PERSON.getCode().equals(fdCharacter)){
    mngService = ElecPlugin.getPersonAuthenticationManagementService();
}

//如果没有管理接口，那么就不提供按钮展示
boolean hasService = mngService!=null;
if(hasService){
    IAuthenticationService authService = mngService.getAuthenticationService();
    if(authService==null || !authService.isProxy()){
        //如果不是代理，那么该服务被视为不支持邀请类接口
        hasService = false;
    }else{
        Object _formBean = pageContext.findAttribute(formBeanName);
        pageContext.setAttribute("_formBean", _formBean);
    }
}
pageContext.setAttribute("hasService",hasService);
%>
<%-- TODO 暂时由相对方模块提供的代码，后续应该移植到认证模块中 --%>
<c:if test="${hasService}">
<c:choose>
<c:when test="${_formBean.fdAuthentication == null or _formBean.fdAuthentication.fdAuthStatus=='41' }">
<kmss:auth requestURL="/km/relative/km_relative_main/kmRelativeMain.do?method=updateAuthInvite&fdId=${_formBean.fdId}">
    <ui:button text="${lfn:message('km-relative:py.FaQiRenZhengYao')}" onclick="doCustomOpt('${_formBean.fdId}','updateAuthInvite');" order="4" />
</kmss:auth>
</c:when>
</c:choose>
<c:if test="${param.fdCharacter=='0'}">
	<c:if test="${_formBean.fdAuthentication != null && _formBean.fdAuthentication.fdAuthStatus!='20' && _formBean.fdAuthentication.authorizeAuthStatus=='30'}">
	<%-- 授权书确认 --%>
	<kmss:auth requestURL="/km/relative/km_relative_main/kmRelativeMain.do?method=authorizeApproved&fdId=${_formBean.fdId}">
	    <ui:button text="${lfn:message('km-relative:py.ShouQuanShuQueRen')}" 
	       onclick="doCustomOpt('${_formBean.fdId}','authorizeApprove');" order="5" />
	</kmss:auth>
	<%-- 授权书驳回  --%>
	<kmss:auth requestURL="/km/relative/km_relative_main/kmRelativeMain.do?method=authorizeReject&fdId=${_formBean.fdId}">
	    <ui:button text="${lfn:message('km-relative:py.ShouQuanShuBoHui')}" 
	       onclick="doCustomOpt('${_formBean.fdId}','authorizeReject');" order="5" />
	</kmss:auth>
	</c:if>
</c:if>
</c:if>
