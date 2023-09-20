<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingError" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('third-ding:thirdDingError.fdName')}" />
        <list:data-column property="fdContent" title="${lfn:message('third-ding:thirdDingError.fdContent')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-ding:thirdDingError.fdModelId')}" />
        <list:data-column property="fdModelName" title="${lfn:message('third-ding:thirdDingError.fdModelName')}" />
        <list:data-column property="fdServiceName" title="${lfn:message('third-ding:thirdDingError.fdServiceName')}" />
        <list:data-column property="fdServiceMethod" title="${lfn:message('third-ding:thirdDingError.fdServiceMethod')}" />
        <list:data-column property="fdMethodParam" title="${lfn:message('third-ding:thirdDingError.fdMethodParam')}" />
        <list:data-column property="fdCount" title="${lfn:message('third-ding:thirdDingError.fdCount')}" />
        <list:data-column col="fdCreateTime" title="${lfn:message('third-ding:thirdDingError.fdCreateTime')}">
            <kmss:showDate value="${thirdDingError.fdCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
