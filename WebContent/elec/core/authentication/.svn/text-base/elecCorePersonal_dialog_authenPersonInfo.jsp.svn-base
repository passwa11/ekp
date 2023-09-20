<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.elec.authentication.model.ElecAuthenPersonal"%>
<list:data>
    <list:data-columns var="elecAuthenPersonal" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdId" />
        <list:data-column property="fdPrincipalName" title="${lfn:message('elec-core:elecAuthenPersonal.fdPrincipalName')}" />

       <%--  <list:data-column property="fdIdcardType" title="${lfn:message('elec-core:elecAuthenPersonal.fdIdcardType')}" /> 
        <list:data-column col="fdIdentificationType" title="${lfn:message('elec-core:elecAuthenPersonal.fdIdentificationType')}">
            <sunbor:enumsShow value="${elecAuthenPersonal.fdIdentificationType}" enumsType="elec_authen_idcard_type" />
        </list:data-column>
        <list:data-column col="fdAuthStatus" title="${lfn:message('elec-core:elecAuthenPersonal.fdAuthStatus')}">
            <sunbor:enumsShow value="${elecAuthenPersonal.fdAuthStatus}" enumsType="elec_authen_status_type" />
        </list:data-column>
        --%>
        <list:data-column property="fdIdentificationNo" title="${lfn:message('elec-core:elecAuthenPersonal.fdIdentificationNo')}" />
        <c:if test="${elecAuthenPersonal.fdSource eq '00'}">
        <list:data-column col="loginName" title="${lfn:message('elec-core:elecAuthenPersonal.fdLoginName')}">
            <% 
            	ElecAuthenPersonal elecAuthenPersonal = (ElecAuthenPersonal)pageContext.getAttribute("elecAuthenPersonal");
            	out.append(UserUtil.getUser(elecAuthenPersonal.getFdModelId()).getFdLoginName());
            %>
        </list:data-column>
        </c:if>
        <list:data-column property="fdModelId" />
        <list:data-column property="fdModelName" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
