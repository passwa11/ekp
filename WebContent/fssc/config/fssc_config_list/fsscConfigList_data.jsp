<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscConfigList" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-config:fsscConfigList.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('fssc-config:fsscConfigList.fdCode')}" />
        <list:data-column property="fdGoodsType" title="${lfn:message('fssc-config:fsscConfigList.fdGoodsType')}" />
        <list:data-column property="fdGoodsProperty" title="${lfn:message('fssc-config:fsscConfigList.fdGoodsProperty')}" />
        <list:data-column property="fdMinNum" title="${lfn:message('fssc-config:fsscConfigList.fdMinNum')}" />
        <list:data-column property="fdUnit" title="${lfn:message('fssc-config:fsscConfigList.fdUnit')}" />
        <list:data-column property="fdPrice" title="${lfn:message('fssc-config:fsscConfigList.fdPrice')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-config:fsscConfigList.docCreator')}" escape="false">
            <c:out value="${fsscConfigList.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscConfigList.docCreator.fdId}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
