<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataGood" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataGood.fdName')}"  />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataGood.fdCode')}"  />
        <list:data-column col="fdParent" title="${lfn:message('eop-basedata:eopBasedataGood.fdParent')}">
            <c:if test="${not empty eopBasedataGood.fdParent}">
                ${eopBasedataGood.fdParent.fdName}
            </c:if>
        </list:data-column>
        <list:data-column col="fdWithTaxFlagName" title="${lfn:message('eop-basedata:eopBasedataGood.fdWithTaxFlag')}" escape="false">
            <sunbor:enumsShow enumsType="eop_basedata_is_tax" value="${eopBasedataGood.fdWithTaxFlag }"/>
        </list:data-column>
        <list:data-column col="fdWithTaxFlag" escape="false">
            ${eopBasedataGood.fdWithTaxFlag}
        </list:data-column>
        <list:data-column col="fdTaxRate" title="${lfn:message('eop-basedata:eopBasedataGood.fdTaxRate')}">
            <c:if test="${not empty eopBasedataGood.fdTaxRate}">
                ${eopBasedataGood.fdTaxRate}%
            </c:if>
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
