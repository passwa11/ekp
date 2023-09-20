<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataMaterialList" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdCode')}" />
        <list:data-column property="fdSpecs" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdSpecs')}" />
        <list:data-column col="fdPrice" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdPrice')}" escape="false">
            <kmss:showNumber value="${eopBasedataMaterialList.fdPrice }" pattern="0.0#####"></kmss:showNumber>
        </list:data-column>
        <list:data-column property="fdType.fdName" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}" />
        <list:data-column property="fdUnit.fdName" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}" />
        <list:data-column property="docCreateTime" title="${lfn:message('eop-basedata:eopBasedataMaterial.docCreateTime')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
