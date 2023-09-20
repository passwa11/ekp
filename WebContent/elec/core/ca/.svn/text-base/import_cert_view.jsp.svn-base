<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.elec.core.certification.ICertificationManagementService"%>
<%@ page import="com.landray.kmss.elec.core.ElecPlugin"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.common.exception.KmssRuntimeException"%>
<%@ page import="com.landray.kmss.util.KmssMessage"%>

<%--
用于引入已绑定的证书列表
 --%>
<%

Map<String,String> providers = 
    ElecPlugin.getAvailableProviderNameOf(
            ElecPlugin.EXTENSION_ELEC_CERTIFICATION_SERVICE,
            ElecPlugin.EX_ITEM_NAME_CORE_SERVICE);
//如果没有管理接口，那么就不提供按钮展示
boolean hasService = !providers.isEmpty();
if(hasService){
    //如果有管理接口的实现，那么从扩展点获取到它的新建（申请）页面coreService
    Iterator<String> it = providers.keySet().iterator();
    it.hasNext();
    String providerName = it.next();    
    String paramUrl = (String)ElecPlugin.getParamValue(
            ElecPlugin.EXTENSION_ELEC_CERTIFICATION_SERVICE, 
            ElecPlugin.EX_ITEM_NAME_CORE_SERVICE,
            providerName,
            ElecPlugin.P_importCertViewUrl);
    pageContext.setAttribute("paramUrl",paramUrl);
}
pageContext.setAttribute("hasService",hasService);
%>

<c:if test="${hasService}">
<c:import url="${paramUrl}" charEncoding="UTF-8">
    <c:param name="fdModelId" value="${param.fdModelId}"/>
    <c:param name="usage" value="${param.usage}"/>
	<c:param name="fdModelName" value="${param.fdModelName}"/>
</c:import>
</c:if>
<%--@ include file="/sys/bookmark/import/bookmark_script.jsp" --%>
