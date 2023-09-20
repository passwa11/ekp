<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdEkpJavaNotifyMapp" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyMapp.docSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyMapp.fdNotifyId')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyMapp.docCreateTime')}">
            <kmss:showDate value="${thirdEkpJavaNotifyMapp.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
