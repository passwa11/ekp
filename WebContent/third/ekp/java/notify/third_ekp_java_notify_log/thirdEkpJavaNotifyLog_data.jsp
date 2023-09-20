<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdEkpJavaNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyLog.docSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyLog.fdNotifyId')}" />
        <list:data-column col="fdMethod.name" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyLog.fdMethod')}">
            <sunbor:enumsShow value="${thirdEkpJavaNotifyLog.fdMethod}" enumsType="third_ekp_java_notify_method" />
        </list:data-column>
        <list:data-column col="fdMethod">
            <c:out value="${thirdEkpJavaNotifyLog.fdMethod}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyLog.docCreateTime')}">
            <kmss:showDate value="${thirdEkpJavaNotifyLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyLog.fdExpireTime')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
