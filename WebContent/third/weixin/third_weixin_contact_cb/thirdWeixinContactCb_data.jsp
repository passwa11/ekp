<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinContactCb" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdEventType" title="${lfn:message('third-weixin:thirdWeixinContactCb.fdEventType')}" />
        <list:data-column property="fdRecordId" title="${lfn:message('third-weixin:thirdWeixinContactCb.fdRecordId')}" />
        <list:data-column col="fdHandleResult.name" title="${lfn:message('third-weixin:thirdWeixinContactCb.fdHandleResult')}">
            <sunbor:enumsShow value="${thirdWeixinContactCb.fdHandleResult}" enumsType="third_weixin_cb_result" />
        </list:data-column>
        <list:data-column col="fdHandleResult">
            <c:out value="${thirdWeixinContactCb.fdHandleResult}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin:thirdWeixinContactCb.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinContactCb.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin:thirdWeixinContactCb.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinContactCb.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin:thirdWeixinContactCb.fdCorpId')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
