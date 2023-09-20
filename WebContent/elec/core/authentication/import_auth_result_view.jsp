<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page
	import="com.landray.kmss.elec.core.authentication.*"%>
<%@ page import="com.landray.kmss.elec.core.ElecPlugin"%>
<%@page import="java.util.*"%>
<%@page import="com.landray.kmss.common.exception.KmssRuntimeException"%>
<%@page import="com.landray.kmss.util.KmssMessage"%>
<%-- 
这是ELEC认证结果展示的代理页面，它相当于一个参数接口
这里通过扩展点的方式来引用真正的参数填充页面 
参数说明
必要参数:

<c:param name="fdCharacter" value=""/>
<c:param name="fdModelId" value=""/>
<c:param name="fdModelName" value=""/>
--%>
<%
//必填参数  区分1个人还是0机构
String character = request.getParameter("fdCharacter");
String formBeanName = request.getParameter("formBeanName");
String _item_name = "";
if(CharacterTypeEnum.ORG.getCode().equals(character)){
    _item_name = ElecPlugin.EX_ITEM_NAME_MNGT_PRISE_SERVICE;
}else if(CharacterTypeEnum.PERSON.getCode().equals(character)){
    _item_name = ElecPlugin.EX_ITEM_NAME_MNGT_PERSON_SERVICE;
}

Map<String,String> providers = 
ElecPlugin.getAvailableProviderNameOf(
        ElecPlugin.EXTENSION_ELEC_AUTHENTICATION_SERVICE,
        _item_name);


//如果没有管理接口，那么就不提供按钮展示
boolean hasService = !providers.isEmpty();
if(hasService){
  //如果有管理接口的实现，那么从扩展点获取到它的view页面
  Iterator<String> it = providers.keySet().iterator();
  it.hasNext();
  String providerName = it.next();    
  String paramUrl = (String)ElecPlugin.getParamValue(
          ElecPlugin.EXTENSION_ELEC_AUTHENTICATION_SERVICE, 
          _item_name,
          providerName,
          ElecPlugin.P_authResultViewUrl);
  pageContext.setAttribute("paramUrl",paramUrl);
}
pageContext.setAttribute("hasService",hasService);

%>
<c:if test="${hasService}">
<c:import url="${paramUrl}" charEncoding="UTF-8">
    <c:param name="fdModelId" value="${param.fdModelId}"/>
    <c:param name="fdModelName" value="${param.fdModelName}"/>
    <c:param name="formBeanName" value="${param.formBeanName }"/>
</c:import>
</c:if>
