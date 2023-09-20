<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdFeishuNotifyLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.docSubject')}" />
        <list:data-column property="fdNotifyId" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.fdNotifyId')}" />
        <list:data-column property="fdMessageId" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.fdMessageId')}" />
        <list:data-column col="typeText" title="${lfn:message('third-feishu:third.feishu.notify.log.type')}">
            <c:if test="${thirdFeishuNotifyLog.fdType != 2}">
                ${lfn:message('third-feishu:third.feishu.notify.log.type1')}
            </c:if>
            <c:if test="${thirdFeishuNotifyLog.fdType == 2}">
                ${lfn:message('third-feishu:third.feishu.notify.log.type2')}
            </c:if>
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.docCreateTime')}">
            <kmss:showDate value="${thirdFeishuNotifyLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column property="fdExpireTime" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.fdExpireTime')}" />
        <list:data-column col="fdResult.name" title="${lfn:message('third-feishu:thirdFeishuNotifyLog.fdResult')}">
            <sunbor:enumsShow value="${thirdFeishuNotifyLog.fdResult}" enumsType="third_feishu_result" />
        </list:data-column>
        <list:data-column col="fdResult">
            <c:out value="${thirdFeishuNotifyLog.fdResult}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
