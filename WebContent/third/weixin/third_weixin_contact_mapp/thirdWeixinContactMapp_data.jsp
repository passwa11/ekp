<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinContactMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdContactName" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdContactName')}" />
        <list:data-column property="fdContactUserId" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdContactUserId')}" />
        <list:data-column property="fdExternalId" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdExternalId')}" />
        <list:data-column property="fdTagId" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdTagId')}" />
        <list:data-column property="fdTagName" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdTagName')}" />
        <list:data-column property="fdOrgTypeId" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdOrgTypeId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinContactMapp.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinContactMapp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin:thirdWeixinContactMapp.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinContactMapp.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIsDelete" title="${lfn:message('third-weixin:thirdWeixinContactMapp.fdIsDelete')}" >
            <sunbor:enumsShow value="${thirdWeixinContactMapp.fdIsDelete}" enumsType="common_yesno" />
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
