<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAssetGoods" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('fssc-asset:fsscAssetGoods.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('fssc-asset:fsscAssetGoods.fdCode')}" />
        <list:data-column property="fdNum" title="${lfn:message('fssc-asset:fsscAssetGoods.fdNum')}" />
        <list:data-column property="fdAssetName" title="${lfn:message('fssc-asset:fsscAssetGoods.fdAssetName')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-asset:fsscAssetGoods.docCreator')}" escape="false">
            <c:out value="${fsscAssetGoods.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscAssetGoods.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-asset:fsscAssetGoods.docCreateTime')}">
            <kmss:showDate value="${fsscAssetGoods.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
