<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page import="com.landray.kmss.elec.core.certification.ICertificationManagementService"%>
<%@ page import="com.landray.kmss.elec.core.ElecPlugin"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.common.exception.KmssRuntimeException"%>
<%@ page import="com.landray.kmss.util.KmssMessage"%>

<%--
用于引入证书申请按钮的页面，参数说明
    <c:param name="fdModelId" value="${param.fdModelId}"/>  必填
    <c:param name="fdModelName" value="${param.fdModelName}"/>  必填
    <c:param name="formBeanName" value="xxForm"/>页面表单Bean的名称
    <c:param name="fdKey" value="elecCaApply"/>  默认elecCaApply
    <c:param name="fdRange" value="${param.fdRange}"/>  适用范围，必填， 0内部， 1外部相对方
    <c:param name="fdCaAuthenType" value="${param.fdCaAuthenType}"/> 证书类型，必填1个人、2企业
    <c:param name="fdOrgName" value="${param.fdOrgName}"/>机构全称，非必填，当fdCaAuthenType=2时有效
    <c:param name="fdOrgCode" value="${param.fdOrgCode}"/>统一信用代码，非必填，当fdCaAuthenType=2时有效
    <c:param name="fdIdCardName" value="${param.fdIdCardName}"/>证件上的名称，一般为身份证上的姓名，非必填，当fdCaAuthenType=1时有效
    <c:param name="fdIdCardType" value="${param.fdIdCardType}"/>证件类型，非必填，当fdCaAuthenType=1时有效， 默认00身份证
    <c:param name="fdIdCardNumber" value="${param.fdIdCardNumber}"/>证件号码，非必填，当fdCaAuthenType=1时有效
    <c:param name="fdThirdNum" value="${param.fdThirdNum}"/>第三方平台的唯一标识
    
 --%>
 
<%
boolean hasService = false;
{
    String formBeanName = request.getParameter("formBeanName");
    Map<String,String> providers = 
            ElecPlugin.getAvailableProviderNameOf(
                    ElecPlugin.EXTENSION_ELEC_CERTIFICATION_SERVICE,
                    ElecPlugin.EX_ITEM_NAME_CORE_SERVICE);
    //如果没有管理接口，那么就不提供按钮展示
    hasService = !providers.isEmpty();
    if(hasService){
        //如果有管理接口的实现，那么从扩展点获取到它的新建（申请）页面coreService
        Iterator<String> it = providers.keySet().iterator();
        it.hasNext();
        String providerName = it.next();    
        String paramUrl = (String)ElecPlugin.getParamValue(
                ElecPlugin.EXTENSION_ELEC_CERTIFICATION_SERVICE, 
                ElecPlugin.EX_ITEM_NAME_CORE_SERVICE,
                providerName,
                ElecPlugin.P_importApplyUrl);
        pageContext.setAttribute("paramUrl",paramUrl);
    }
}
pageContext.setAttribute("hasService",hasService);
%>

<c:if test="${hasService }">
    <c:import url="${paramUrl}" charEncoding="UTF-8">
	    <c:param name="fdModelId" value="${param.fdModelId}"/>
	    <c:param name="fdModelName" value="${param.fdModelName}"/>
	    <c:param name="fdKey" value="elecCaApply"/>
	    <c:param name="fdCaAuthenType" value="${param.fdCaAuthenType}"/>
	    <c:param name="fdRange" value="${param.fdRange}"/>
	    <c:param name="fdOrgName" value="${param.fdOrgName}"/>
	    <c:param name="fdOrgCode" value="${param.fdOrgCode}"/>
	    <c:param name="fdIdCardName" value="${param.fdIdCardName}"/>
	    <c:param name="fdIdCardType" value="00"/>
	    <c:param name="fdIdCardNumber" value="${param.fdIdCardNumber}"/>
	    <c:param name="fdThirdNum" value="${param.fdThirdNum}"/>
	    <c:param name="buttonShowStatus" value="${param.buttonShowStatus}"/>
	</c:import>
</c:if>
<%--@ include file="/sys/bookmark/import/bookmark_script.jsp" --%>
