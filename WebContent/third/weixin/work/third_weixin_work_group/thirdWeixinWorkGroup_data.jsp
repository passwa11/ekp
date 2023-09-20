<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinWorkCallback" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdModelName" title="${lfn:message('third-weixin-work:thirdWeixinWorkGroup.fdModelName')}" />
        <list:data-column property="fdModelId" title="${lfn:message('third-weixin-work:thirdWeixinWorkGroup.fdModelId')}" />
        <list:data-column property="fdGroupId" title="${lfn:message('third-weixin-work:thirdWeixinWorkGroup.fdGroupId')}" />


    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
