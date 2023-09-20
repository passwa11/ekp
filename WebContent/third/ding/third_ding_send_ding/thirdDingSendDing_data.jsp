<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdDingSendDing" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSubject" title="${lfn:message('third-ding:thirdDingSendDing.fdSubject')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ding:thirdDingSendDing.docCreateTime')}">
            <kmss:showDate value="${thirdDingSendDing.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdAgentid" title="${lfn:message('third-ding:thirdDingSendDing.fdAgentid')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('third-ding:thirdDingSendDing.fdStatus')}">
            <sunbor:enumsShow value="${thirdDingSendDing.fdStatus}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${thirdDingSendDing.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
