<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdEkpJavaNotifyQueErr" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyQueErr.docSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyQueErr.fdNotifyId')}" />
        <list:data-column col="fdMethod.name" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyQueErr.fdMethod')}">
            <sunbor:enumsShow value="${thirdEkpJavaNotifyQueErr.fdMethod}" enumsType="third_ekp_java_notify_method" />
        </list:data-column>
        <list:data-column col="fdMethod">
            <c:out value="${thirdEkpJavaNotifyQueErr.fdMethod}" />
        </list:data-column>
        <list:data-column property="fdRepeatHandle" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyQueErr.fdRepeatHandle')}" />
        <list:data-column col="docAlterTime" title="${lfn:message('third-ekp-java-notify:thirdEkpJavaNotifyQueErr.docAlterTime')}">
            <kmss:showDate value="${thirdEkpJavaNotifyQueErr.docAlterTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
