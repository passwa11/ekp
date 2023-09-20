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

<%-- 业务模块通过import该jsp来展示认证相关的按钮--%>

<%--
<c:param name="fdCharacter" value="类型 企业0|个人1"/>
<c:param name="fdModelId" value="$模型id"/>
<c:param name="fdModelName" value="模型名称"/>
<c:param name="formBeanName" value="xxForm"/>页面表单Bean的名称，注意，这个form必须是IAuthableForm
 --%>
<%
String fdCharacter = request.getParameter("fdCharacter");
IAuthenticationManagementService mngService = null;
if(CharacterTypeEnum.ORG.getCode().equals(fdCharacter)){
    mngService = ElecPlugin.getEnterpriseAuthenticationManagementService();
}else if(CharacterTypeEnum.PERSON.getCode().equals(fdCharacter)){
    mngService = ElecPlugin.getPersonAuthenticationManagementService();
}
//如果没有管理接口，那么就不提供按钮展示
boolean hasMngService = mngService!=null;
if(hasMngService){
    pageContext.setAttribute("import_btn_jsp_path",mngService.getImportBtnPageUrl());
}
pageContext.setAttribute("hasMngService",hasMngService);
%>
<%-- 有管理服务模块 --%>
<c:if test="${hasMngService}">
    <c:import url="${import_btn_jsp_path}" charEncoding="UTF-8">
      <c:param name="fdCharacter" value="${param.fdCharacter}"/>
      <c:param name="fdModelId" value="${param.fdModelId}"/>
      <c:param name="fdModelName" value="${param.fdModelName}"/>
      <c:param name="formBeanName" value="${param.formBeanName}"/>
    </c:import>
</c:if>
