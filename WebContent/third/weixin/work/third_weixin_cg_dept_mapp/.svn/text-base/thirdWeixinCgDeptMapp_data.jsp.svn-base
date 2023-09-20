<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWeixinCgDeptMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdDeptName" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.fdDeptName')}" />
        <list:data-column property="fdEkpId" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.fdEkpId')}" />
        <list:data-column property="fdWxDeptId" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.fdWxDeptId')}" />
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.fdIsAvailable')}">
            <sunbor:enumsShow value="${thirdWeixinCgDeptMapp.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${thirdWeixinCgDeptMapp.fdIsAvailable}" />
        </list:data-column>
        <list:data-column property="fdCorpId" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.fdCorpId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.docCreateTime')}">
            <kmss:showDate value="${thirdWeixinCgDeptMapp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docAlterTime" title="${lfn:message('third-weixin-work:thirdWeixinCgDeptMapp.docAlterTime')}">
            <kmss:showDate value="${thirdWeixinCgDeptMapp.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
